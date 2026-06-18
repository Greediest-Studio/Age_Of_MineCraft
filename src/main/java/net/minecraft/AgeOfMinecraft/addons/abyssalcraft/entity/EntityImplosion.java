package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityImplosion extends Entity {
  private static final DataParameter<Integer> IMPLOSIONTIMER = EntityDataManager.createKey(EntityImplosion.class, DataSerializers.VARINT);
  
  public EntityTameBase shootingEntity;
  
  public EntityImplosion(World worldIn) {
    super(worldIn);
    this.preventEntitySpawning = true;
    setSize(2.0F, 2.0F);
  }
  
  public EntityImplosion(World worldIn, EntityTameBase entity) {
    this(worldIn);
    this.shootingEntity = entity;
    copyLocationAndAnglesFrom((Entity)entity);
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  protected void entityInit() {
    this.dataManager.register(IMPLOSIONTIMER, Integer.valueOf(0));
  }
  
  public int getImplosionTime() {
    return ((Integer)this.dataManager.get(IMPLOSIONTIMER)).intValue();
  }
  
  public void setImplosionTime(int time) {
    this.dataManager.set(IMPLOSIONTIMER, Integer.valueOf(time));
  }
  
  public void setDead() {
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(128.0D));
    this.world.playEvent(3000, getPosition(), 0);
    playSound(ESound.blast, 10.0F, 1.0F);
    playSound(ESound.jzaharshout, 10.0F, 1.0F);
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        double scale = (128.0D - entity.getDistance(this.posX, this.posY, this.posZ)) / 128.0D;
        Vec3d dir = new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ);
        dir = dir.normalize();
        if (this.shootingEntity != null && (entity == this.shootingEntity || (entity instanceof EntityLivingBase && false))) {
          entity.hurtResistantTime = 100;
          if (entity instanceof EntityLivingBase)
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 4)); 
        } else if (entity.isEntityAlive()) {
          if (entity.getDistanceSq(this) <= 25.0D) {
            entity.hurtResistantTime = 0;
            entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 100.0F);
            if (EngenderConfig.general.useMessage && this.shootingEntity != null && entity instanceof EntityLivingBase && !entity.isEntityAlive() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage((ITextComponent)new TextComponentTranslation(entity.getName() + " was blasted apart by an Implosion thanks to " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0])); 
          } 
          entity.addVelocity(dir.x * 10.0D * scale, 2.0D + this.rand.nextDouble(), dir.z * 10.0D * scale);
        } 
      }  
    super.setDead();
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    move(MoverType.SELF, 0.0D, 0.05D, 0.0D);
    setImplosionTime(getImplosionTime() + 1);
    setPosition(this.posX, this.posY, this.posZ);
    float size = 128.0F;
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(size));
    if (getImplosionTime() > 500)
      setDead(); 
    if (getImplosionTime() == 1)
      playSound(ACSounds.jzahar_charge, 10.0F, 1.0F); 
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        double scale = (size - entity.getDistance(this.posX, this.posY, this.posZ)) / size;
        Vec3d dir = new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ);
        dir = dir.normalize();
        if (this.shootingEntity == null && entity instanceof EntityJzahar)
          this.shootingEntity = (EntityJzahar)entity; 
        if (entity.isEntityAlive() && !(entity instanceof EntityJzahar) && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
          entity.addVelocity(dir.x * -getImplosionTime() * 0.001D * scale, dir.y * -getImplosionTime() * 0.001D * scale, dir.z * -getImplosionTime() * 0.001D * scale);
          if (entity.getDistanceSq(this) <= 4.0D) {
            entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.isEntityAlive() && this.shootingEntity != null && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage((ITextComponent)new TextComponentTranslation(entity.getName() + " was electricuted by " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0])); 
          } 
        } 
      }  
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {}
  
  protected void readEntityFromNBT(NBTTagCompound compound) {}
  
  public EnumPushReaction getPushReaction() {
    return EnumPushReaction.IGNORE;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    return true;
  }
}


