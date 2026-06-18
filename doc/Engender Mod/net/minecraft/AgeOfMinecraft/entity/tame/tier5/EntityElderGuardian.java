package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityElderGuardian extends EntityGuardian implements Massive {
  public EntityElderGuardian(World worldIn) {
    super(worldIn);
    func_110163_bv();
    this.field_70728_aV = 50;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.6F, 2.35F);
    } else {
      func_70105_a(2.35F, 2.35F);
    } 
    if (this.wander != null)
      this.wander.func_179479_b(400); 
  }
  
  public String getDescName() {
    return "elder_guardian";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ELDER_GUARDIAN;
  }
  
  public int func_175464_ck() {
    return 60;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187512_aB;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187517_aG;
  }
  
  protected SoundEvent func_184615_bR() {
    func_70624_b((EntityLivingBase)null);
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    if (!func_70090_H())
      func_184185_a(SoundEvents.field_187516_aF, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187515_aE;
  }
  
  public double func_70042_X() {
    return (!EngenderConfig.mobs.useMobTalkerModels && this.field_70122_E && !func_70090_H()) ? (this.field_70131_O * 1.1D) : (this.field_70131_O * 0.85D);
  }
  
  protected void func_70664_aZ() {
    this.field_70737_aN = 0;
    if (EngenderConfig.mobs.useMobTalkerModels && !func_70631_g_()) {
      func_184185_a(SoundEvents.field_191244_bn, 1.0F, 1.75F + this.field_70146_Z.nextFloat() * 0.25F);
    } else {
      func_184185_a(SoundEvents.field_187684_cg, 1.0F, func_70631_g_() ? 1.5F : 1.0F);
    } 
    this.field_70181_x += (func_70631_g_() ? 0.5D : 0.6D) - this.field_70181_x;
    this.field_70122_E = false;
    this.field_70160_al = true;
  }
  
  public float func_70603_bj() {
    return EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 2.35F;
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.9F + ((func_184218_aH() || func_70090_H() || func_180799_ab() || isSitResting() || !func_70631_g_()) ? (MathHelper.func_76134_b(this.field_70173_aa * 0.2F) * 0.1F) : 0.0F)) : ((this.field_70122_E && !func_70093_af() && !func_70090_H()) ? (this.field_70131_O * 0.75F) : (this.field_70131_O * 0.5F));
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityGuardian baby = new EntityGuardian(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        if (this.field_70146_Z.nextFloat() < 0.25F) {
          baby = new EntityElderGuardian(this.field_70170_p);
          baby.setGrowingAge(-100000);
          baby.setElder();
        } else {
          baby.setGrowingAge(-24000);
        } 
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        if (isMarried())
          for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
            baby.levelUp();  
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.1F) : super.func_70647_i();
  }
  
  public int func_70646_bf() {
    return EngenderConfig.mobs.useMobTalkerModels ? 40 : ((this.field_70122_E && func_70638_az() == null) ? 5 : 180);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    this.field_70143_R = 0.0F;
    if (EngenderConfig.mobs.useMobTalkerModels && !func_70631_g_() && this.field_70122_E) {
      this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 0.75D + (MathHelper.func_76134_b(this.field_70173_aa * 0.2F) * 0.1F), this.field_70161_v + this.field_70146_Z.nextDouble() - 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
      if (this.field_70173_aa % 30 == 0)
        func_184185_a(SoundEvents.field_191244_bn, 0.1F, 0.75F); 
    } 
  }
  
  public boolean isElder() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityElderGuardian(this.field_70170_p);
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if ((this.field_70173_aa + func_145782_y()) % 200 == 0) {
      List<EntityLivingBase> list = this.field_70170_p.func_175644_a(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
              return (EntityElderGuardian.this.func_70068_e((Entity)p_apply_1_) < 2500.0D && EntityElderGuardian.this.func_70681_au().nextInt(20) == 0);
            }
          });
      int j = 1;
      int k = 6000;
      int l = 1200;
      for (EntityLivingBase entityplayermp : list) {
        Potion potion;
        if (!func_184191_r((Entity)entityplayermp)) {
          potion = MobEffects.field_76421_d;
        } else {
          potion = MobEffects.field_76424_c;
        } 
        if (!entityplayermp.func_70644_a(potion) || entityplayermp.func_70660_b(potion).func_76458_c() < j || entityplayermp.func_70660_b(potion).func_76459_b() < l) {
          this.field_70170_p.func_184148_a(null, entityplayermp.field_70165_t, entityplayermp.field_70163_u, entityplayermp.field_70161_v, SoundEvents.field_187514_aD, func_184176_by(), func_70599_aP(), func_70631_g_() ? 1.5F : 1.0F);
          entityplayermp.func_70690_d(new PotionEffect(potion, k, j, true, false));
          entityplayermp.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0, true, false));
        } 
      } 
    } 
    if (this.field_70173_aa % 20 == 0) {
      List<EntityPlayerMP> list = this.field_70170_p.func_175661_b(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
            public boolean apply(@Nullable EntityPlayerMP p_apply_1_) {
              return (EntityElderGuardian.this.func_70068_e((Entity)p_apply_1_) < 2500.0D);
            }
          });
      for (EntityPlayerMP entityplayermp : list) {
        Potion potion;
        if (func_184191_r((Entity)entityplayermp)) {
          potion = MobEffects.field_76422_e;
        } else {
          potion = MobEffects.field_76419_f;
        } 
        if (this.field_70173_aa == 40 || !entityplayermp.func_70644_a(potion) || entityplayermp.func_70660_b(potion).func_76458_c() < 2 || entityplayermp.func_70660_b(potion).func_76459_b() < 1200) {
          entityplayermp.field_71135_a.func_147359_a((Packet)new SPacketChangeGameState(10, 0.0F));
          entityplayermp.func_70690_d(new PotionEffect(potion, 6000, 2, true, false));
          entityplayermp.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 100, 0, true, false));
          func_70625_a((Entity)entityplayermp, 180.0F, func_70646_bf());
        } 
      } 
    } 
  }
}
