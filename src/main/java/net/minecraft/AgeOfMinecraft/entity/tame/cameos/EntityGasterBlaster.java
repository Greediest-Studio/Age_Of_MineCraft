package net.minecraft.AgeOfMinecraft.entity.tame.cameos;

import java.util.Collections;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGasterBlaster extends EntityLiving {
  public static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EntityGasterBlaster.class, DataSerializers.BYTE);
  
  public int warmUpTime;
  
  public int fireLength;
  
  public EntitySans shootingEntity;
  
  public double targetpointx;
  
  public double targetpointy;
  
  public double targetpointz;
  
  public EntityGasterBlaster(World worldIn) {
    super(worldIn);
    setNoGravity(true);
    this.noClip = true;
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    setSize(1.0F, 1.0F);
    setAlwaysRenderNameTag(false);
  }
  
  public EntityGasterBlaster(World worldIn, double posX, double posY, double posZ, double targetX, double targetY, double targetZ) {
    this(worldIn);
    setPosition(posX, posY, posZ);
    this.targetpointx = targetX;
    this.targetpointy = targetY;
    this.targetpointz = targetZ;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(STATUS, (byte) 0);
  }
  
  protected void hitEntities() {
    for (int i = 0; i < 80; i++) {
      Vec3d vec3 = getLook(1.0F);
      double x = this.posX + vec3.x * i;
      double y = this.posY + (isSmall() ? 1.0D : 0.5D) + vec3.y * i;
      double z = this.posZ + vec3.z * i;
      AxisAlignedBB box = (new AxisAlignedBB(x, y, z, x, y, z)).grow(isSmall() ? 0.5D : 1.0D);
      List<Entity> list = this.world.getEntitiesInAABBexcluding(this, box, Entity::canBeCollidedWith);
      if (!list.isEmpty())
        for (Entity entity1 : list) {
          if (entity1 instanceof EntityLivingBase && this.shootingEntity != null && entity1 != null && entity1.isEntityAlive() && !false) {
            EntityLivingBase hitmob = (EntityLivingBase)entity1;
            hitmob.hurtResistantTime = 0;
            hitmob.playSound(ESound.bonehit, 1.0F, 1.0F);
            this.shootingEntity.inflictEngenderMobDamage(hitmob, " was blasted by a Gaster Blaster shot by ", (new DamageSource("sans")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), 1.0F);
            this.shootingEntity.attackWithAdditionalEffects(this.shootingEntity);
            this.shootingEntity.karmicRetribution(hitmob, 10);
          } 
        }  
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setBoolean("Small", isSmall());
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    setSmall(compound.getBoolean("Small"));
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  protected void collideWithEntity(Entity entityIn) {}
  
  protected void collideWithNearbyEntities() {}
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    return true;
  }
  
  private void playParticles() {
    if (this.world instanceof WorldServer)
      ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, (this.width / 4.0F), (this.height / 4.0F), (this.width / 4.0F), 0.05D, Block.getStateId(Blocks.PLANKS.getDefaultState()));
  }
  
  protected float updateDistance(float p_110146_1_, float p_110146_2_) {
    this.prevRenderYawOffset = this.prevRotationYaw;
    this.renderYawOffset = this.rotationYaw;
    return 0.0F;
  }
  
  public float getEyeHeight() {
    return this.height * 0.325F;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (!hasNoGravity())
      super.travel(strafe, vertical, forward); 
  }
  
  public void setRenderYawOffset(float offset) {
    this.prevRenderYawOffset = this.prevRotationYaw = offset;
    this.prevRotationYawHead = this.rotationYawHead = offset;
  }
  
  public void setRotationYawHead(float rotation) {
    this.prevRenderYawOffset = this.prevRotationYaw = rotation;
    this.prevRotationYawHead = this.rotationYawHead = rotation;
  }
  
  public void onUpdate() {
    super.onUpdate();
    getLookHelper().setLookPosition(this.targetpointx, this.targetpointy, this.targetpointz, 180.0F, 90.0F);
    if (--this.warmUpTime <= -20)
      this.fireLength++; 
    if (this.fireLength == 10)
      playSound(ESound.gasterblasterfire, 3.0F, 1.0F); 
    this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    if (this.fireLength <= 15 && this.fireLength >= 10)
      hitEntities(); 
    if (this.fireLength > 20)
      this.moveForward = -1.0F; 
    if (this.fireLength > 30)
      setDead(); 
  }
  
  private float updateRotation(float p_75652_1_, float p_75652_2_) {
    float f = MathHelper.wrapDegrees(p_75652_2_ - p_75652_1_);
    return p_75652_1_ + f;
  }
  
  public boolean isChild() {
    return isSmall();
  }
  
  public void onKillCommand() {
    setDead();
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  public EnumPushReaction getPushReaction() {
    return EnumPushReaction.PUSH_ONLY;
  }
  
  public void setSmall(boolean small) {
    this.dataManager.set(STATUS, setBit(this.dataManager.get(STATUS), 1, small));
  }
  
  public boolean isSmall() {
    return ((this.dataManager.get(STATUS) & 0x1) != 0);
  }
  
  private void setMarker(boolean marker) {
    this.dataManager.set(STATUS, setBit(this.dataManager.get(STATUS), 16, marker));
    setSize(0.5F, 1.975F);
  }
  
  private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
    if (p_184797_3_) {
      p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
    } else {
      p_184797_1_ = (byte)(p_184797_1_ & (~p_184797_2_));
    } 
    return p_184797_1_;
  }
  
  public void onStruckByLightning(EntityLightningBolt lightningBolt) {}
  
  public boolean canBeHitWithPotion() {
    return false;
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    if (STATUS.equals(key))
      setSize(0.5F, 1.975F); 
    super.notifyDataManagerChange(key);
  }
  
  public boolean attackable() {
    return false;
  }
  
  public Iterable<ItemStack> getArmorInventoryList() {
    return Collections.emptyList();
  }
  
  public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
    return ItemStack.EMPTY;
  }
  
  public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}
  
  public EnumHandSide getPrimaryHand() {
    return null;
  }
}


