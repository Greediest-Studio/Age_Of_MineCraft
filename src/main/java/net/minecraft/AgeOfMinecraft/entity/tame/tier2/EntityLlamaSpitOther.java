package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLlamaSpitOther extends EntityLlamaSpit {
  public EntityLlama owner;
  
  private NBTTagCompound ownerNbt;
  
  public EntityLlamaSpitOther(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
  }
  
  public EntityLlamaSpitOther(World worldIn, EntityLlama p_i47273_2_) {
    super(worldIn);
    this.owner = p_i47273_2_;
    setPosition(p_i47273_2_.posX - (p_i47273_2_.width + 1.0F) * 0.5D * MathHelper.sin(p_i47273_2_.renderYawOffset * 0.017453292F), p_i47273_2_.posY + p_i47273_2_.getEyeHeight(), p_i47273_2_.posZ + (p_i47273_2_.width + 1.0F) * 0.5D * MathHelper.cos(p_i47273_2_.renderYawOffset * 0.017453292F));
    setSize(0.25F, 0.25F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityLlamaSpitOther(World worldIn, double x, double y, double z, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    setPosition(x, y, z);
    for (int i = 0; i < 20; i++) {
      double d0 = 0.4D + 0.1D * i;
      worldIn.spawnParticle(EnumParticleTypes.SPIT, x, y, z, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
    } 
    this.motionX = p_i47274_8_;
    this.motionY = p_i47274_10_;
    this.motionZ = p_i47274_12_;
  }
  
  public void onHit(RayTraceResult movingObject) {
    if (!this.world.isRemote && movingObject.entityHit != null)
      if (movingObject.entityHit != this.owner && movingObject.entityHit instanceof EntityLivingBase && (!false || (movingObject.entityHit instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase && false && this.owner.getFakeHealth() > 0.0F)) && movingObject.entityHit != null && this.owner != null && movingObject.entityHit != this.owner) {
        this.owner.inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was spat on by ", DamageSource.causeThrownDamage(this.owner, this), 1.0F);
        if (movingObject.entityHit.isNonBoss())
          ((EntityLivingBase)movingObject.entityHit).knockBack(this, 0.75F, MathHelper.sin(this.owner.rotationYawHead * 0.017453292F), -MathHelper.cos(this.owner.rotationYawHead * 0.017453292F));
        if (!this.world.isRemote)
          setDead(); 
      }  
    if (!this.world.isRemote && movingObject.entityHit == null)
      setDead(); 
  }
  
  protected void entityInit() {}
  
  public void onUpdate() {
    super.onUpdate();
    setSize(0.25F, 0.25F);
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.owner != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive() && !false)
          onHit(new RayTraceResult(entity1));
      }  
  }
  
  protected void readEntityFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("Owner", 10))
      this.ownerNbt = compound.getCompoundTag("Owner"); 
    setSize(0.25F, 0.25F);
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {
    if (this.owner != null) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      UUID uuid = this.owner.getUniqueID();
      nbttagcompound.setUniqueId("OwnerUUID", uuid);
      compound.setTag("Owner", nbttagcompound);
    } 
    setSize(0.25F, 0.25F);
  }
  
  private void restoreOwnerFromSave() {
    if (this.ownerNbt != null && this.ownerNbt.hasUniqueId("OwnerUUID")) {
      UUID uuid = this.ownerNbt.getUniqueId("OwnerUUID");
      for (EntityLlama entityllama : this.world.getEntitiesWithinAABB(EntityLlama.class, getEntityBoundingBox().grow(15.0D))) {
        if (entityllama.getUniqueID().equals(uuid)) {
          this.owner = entityllama;
          break;
        } 
      } 
    } 
    this.ownerNbt = null;
  }
}


