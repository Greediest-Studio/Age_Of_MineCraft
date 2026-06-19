package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.item.MBItems;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEndersoulFragment extends Entity {
  public static final Predicate<Entity> IS_VALID_TARGET;
  
  static {
    IS_VALID_TARGET = (entity -> (EntitySelectors.CAN_AI_TARGET.apply(entity) && entity.canBeCollidedWith()));
  }
  
  private static final DataParameter<Boolean> TAMED = EntityDataManager.createKey(EntityEndersoulFragment.class, DataSerializers.BOOLEAN);
  
  private int explodeTick = 20 + this.rand.nextInt(20);
  
  public final float[][] stickRotations = new float[8][3];
  
  private EntityMutantEnderman owner;
  
  private EntityPlayer collector;
  
  public EntityEndersoulFragment(World world) {
    super(world);
    for (int i = 0; i < this.stickRotations.length; i++) {
      for (int j = 0; j < (this.stickRotations[i]).length; j++)
        this.stickRotations[i][j] = this.rand.nextFloat() * 2.0F * 3.1415927F; 
    } 
    setSize(0.75F, 0.75F);
  }
  
  public EntityEndersoulFragment(World world, EntityMutantEnderman owner) {
    this(world);
    this.owner = owner;
  }
  
  protected void entityInit() {
    this.dataManager.register(TAMED, Boolean.FALSE);
  }
  
  public boolean isTamed() {
    return this.dataManager.get(TAMED);
  }
  
  public void setTamed(boolean tamed) {
    this.dataManager.set(TAMED, tamed);
  }
  
  public EntityPlayer getCollector() {
    return this.collector;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return isEntityAlive();
  }
  
  public boolean canBePushed() {
    return isEntityAlive();
  }
  
  public boolean canBeAttackedWithItem() {
    return !isTamed();
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 3)
      EntityUtil.spawnEndersoulParticles(this, 64, 0.8F); 
  }
  
  private void explode() {
    playSound(MBSoundEvents.ENTITY_ENDERSOUL_FRAGMENT_EXPLODE, 1.0F, 1.5F);
    this.world.setEntityState(this, (byte)3);
    for (Entity entity : this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(5.0D), IS_VALID_TARGET)) {
      if (getDistanceSq(entity) <= 25.0D) {
        boolean protectedPlayer = isProtected(entity);
        if (entity instanceof EntityLivingBase && this.owner != null && !protectedPlayer)
          this.owner.inflictEngenderMobDamage((EntityLivingBase)entity, "'s soul was flaied by the soul fragments of ", new EntityDamageSource("thrown", this.owner), 1.0F);
        if (!protectedPlayer) {
          double x = entity.posX - this.posX;
          double z = entity.posZ - this.posZ;
          double d = Math.sqrt(x * x + z * z);
          entity.motionX = 0.800000011920929D * x / d;
          entity.motionY = (this.rand.nextFloat() * 0.6F - 0.1F);
          entity.motionZ = 0.800000011920929D * z / d;
          EntityUtil.sendPlayerVelocityPacket(entity);
        } 
      } 
    } 
    setDead();
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.collector == null && this.motionY > -0.05000000074505806D && !hasNoGravity())
      this.motionY = Math.max(-0.05000000074505806D, this.motionY - 0.10000000149011612D); 
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    this.motionX *= 0.9D;
    this.motionY *= 0.9D;
    this.motionZ *= 0.9D;
    if (this.collector != null && !this.collector.isEntityAlive())
      this.collector = null; 
    if (!this.world.isRemote) {
      if (!isTamed() && --this.explodeTick == 0)
        explode(); 
      if (this.collector != null && getDistanceSq(this.collector) > 9.0D) {
        float scale = 0.05F;
        addVelocity((this.collector.posX - this.posX) * scale, (this.collector.posY + (this.collector.height / 3.0F) - this.posY) * scale, (this.collector.posZ - this.posZ) * scale);
      } 
    } 
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (isTamed()) {
      if (this.collector == null && !player.isSneaking()) {
        this.collector = player;
        playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        return true;
      } 
      if (this.collector == player && player.isSneaking()) {
        this.collector = null;
        playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.5F);
        return true;
      } 
      return false;
    } 
    if (!this.world.isRemote)
      setTamed(true); 
    this.collector = player;
    playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (!this.world.isRemote && isEntityAlive())
      explode(); 
    return true;
  }
  
  public boolean isProtected(Entity entity) {
    if (!(entity instanceof EntityLivingBase))
      return false; 
    EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
    if (this.owner != null && false)
      return false; 
    return (entityLivingBase.getHeldItemMainhand().getItem() == MBItems.ENDERSOUL_HAND || entityLivingBase.getHeldItemOffhand().getItem() == MBItems.ENDERSOUL_HAND);
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {
    compound.setBoolean("Tamed", isTamed());
    compound.setInteger("ExplodeTick", this.explodeTick);
  }
  
  protected void readEntityFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("ExplodeTick"))
      this.explodeTick = compound.getInteger("ExplodeTick"); 
    setTamed((compound.getBoolean("Collected") || compound.getBoolean("Tamed")));
  }
}


