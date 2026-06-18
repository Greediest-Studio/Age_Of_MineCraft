package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDreadedChargeOther extends EntityFireball {
  public EntityDreadedChargeOther(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityDreadedChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public EntityDreadedChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.FLAME;
  }
  
  protected boolean isFireballFiery() {
    return false;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (this.ticksExisted > 10) {
      if (!this.world.isRemote)
        for (int x = getPosition().getX() - 4; x < getPosition().getX() + 4; x++) {
          for (int z = getPosition().getZ() - 4; z < getPosition().getZ() + 4; z++) {
            if (!(this.world.getBiome(new BlockPos(x, 0, z)) instanceof com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome) && this.world
              .getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged) {
              Biome b = ACBiomes.dreadlands;
              Chunk c = this.world.getChunk(getPosition());
              c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(b);
              c.setModified(true);
              PacketDispatcher.sendToDimension((IMessage)new CleansingRitualMessage(x, z, Biome.getIdForBiome(b)), this.world.provider.getDimension());
            } 
          } 
        }  
      if (movingObject.entityHit != null) {
        if (this.shootingEntity != null && movingObject.entityHit instanceof EntityLivingBase && this.shootingEntity instanceof EntityTameBase && !false) {
          this.shootingEntity.attackEntityAsMob(movingObject.entityHit);
          EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false);
        } 
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase && false) {
          ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 3));
          ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
          ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
          ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
        } 
      } 
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.FLAME);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 400));
      entityareaeffectcloud.setRadius(2.0F);
      entityareaeffectcloud.setDuration(200 + this.rand.nextInt(200));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          double d0 = getDistanceSq((Entity)entitylivingbase);
          if (this.shootingEntity != null && d0 < 64.0D)
            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ); 
        }  
      this.world.playSound((EntityPlayer)null, getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.MASTER, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
      if (!this.world.isRemote)
        this.world.spawnEntity((Entity)entityareaeffectcloud); 
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false); 
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult((Entity)entity1)); 
      }  
  }
}


