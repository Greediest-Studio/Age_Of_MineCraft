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
    func_70105_a(1.0F, 1.0F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityDreadedChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityDreadedChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.FLAME;
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    if (this.field_70173_aa > 10) {
      if (!this.field_70170_p.field_72995_K)
        for (int x = func_180425_c().func_177958_n() - 4; x < func_180425_c().func_177958_n() + 4; x++) {
          for (int z = func_180425_c().func_177952_p() - 4; z < func_180425_c().func_177952_p() + 4; z++) {
            if (!(this.field_70170_p.func_180494_b(new BlockPos(x, 0, z)) instanceof com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome) && this.field_70170_p
              .func_180494_b(new BlockPos(x, 0, z)) != ACBiomes.purged) {
              Biome b = ACBiomes.dreadlands;
              Chunk c = this.field_70170_p.func_175726_f(func_180425_c());
              c.func_76605_m()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.func_185362_a(b);
              c.func_177427_f(true);
              PacketDispatcher.sendToDimension((IMessage)new CleansingRitualMessage(x, z, Biome.func_185362_a(b)), this.field_70170_p.field_73011_w.getDimension());
            } 
          } 
        }  
      if (movingObject.field_72308_g != null) {
        if (this.field_70235_a != null && movingObject.field_72308_g instanceof EntityLivingBase && this.field_70235_a instanceof EntityTameBase && !((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) {
          this.field_70235_a.func_70652_k(movingObject.field_72308_g);
          EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 7.0F, false, false);
        } 
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) {
          ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76428_l, 400, 3));
          ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76429_m, 6000, 0));
          ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76426_n, 6000, 0));
          ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76444_x, 2400, 3));
        } 
      } 
      List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.FLAME);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 400));
      entityareaeffectcloud.setRadius(2.0F);
      entityareaeffectcloud.setDuration(200 + this.field_70146_Z.nextInt(200));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          double d0 = func_70068_e((Entity)entitylivingbase);
          if (this.field_70235_a != null && d0 < 64.0D)
            entityareaeffectcloud.func_70107_b(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v); 
        }  
      this.field_70170_p.func_184133_a((EntityPlayer)null, func_180425_c(), SoundEvents.field_187523_aM, SoundCategory.MASTER, 1.0F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
      if (!this.field_70170_p.field_72995_K)
        this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud); 
      if (this.field_70235_a != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 7.0F, false, false); 
      func_70106_y();
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S())
          func_70227_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
