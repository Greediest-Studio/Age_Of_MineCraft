package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntitySpellcasterIllager;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityLargeFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntityEvoker extends EntitySpellcasterIllager implements IRangedAttackMob {
  public EntityEvoker(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 50;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIFollowLeader((EntityTameBase)this, 1.2D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntitySpellcasterIllager.AICastingSpell(this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIConvertingSpell());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIReinforcingSpell());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIPolymorphSpell());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIWololoSpell());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AISummonSpell());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AISummonMeteorStormSpell());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIDisintigrationRaySpell());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AILightningBoltSpell());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIPoisonSpraySpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AISmallFireballSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIFireballSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIFrostRaySpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIMagicMissileSpell());
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new AIAttackSpell());
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIFriendlyAttackMelee((EntityTameBase)this, 1.0D, false));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "evoker";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(5.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityEvoker(this.field_70170_p);
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1; i++) {
        EntityEvoker baby = new EntityEvoker(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setGrowingAge(-100000);
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
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.func_74838_a("entity." + str + ".cmm.name");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.4F) : super.func_70647_i();
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_EVOCATION_ILLAGER;
  }
  
  public void func_82196_d(EntityLivingBase target, float p_82196_2_) {
    double d1 = 1.25D;
    Vec3d vec3d = func_70676_i(1.0F);
    double d2 = target.field_70165_t - this.field_70165_t + vec3d.field_72450_a * d1;
    double d3 = (target.func_174813_aQ()).field_72338_b + (target.field_70131_O / 2.0F) - 0.5D + this.field_70163_u + (this.field_70131_O / 2.0F);
    double d4 = target.field_70161_v - this.field_70161_v + vec3d.field_72449_c * d1;
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
    EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
    entitylargefireball.field_92057_e = 4;
    entitylargefireball.field_70165_t = this.field_70165_t + vec3d.field_72450_a * d1;
    entitylargefireball.field_70163_u = this.field_70163_u + (this.field_70131_O / 2.0F) + 0.5D;
    entitylargefireball.field_70161_v = this.field_70161_v + vec3d.field_72449_c * d1;
    this.field_70170_p.func_72838_d((Entity)entitylargefireball);
    func_184609_a(EnumHand.MAIN_HAND);
  }
  
  public void attackEntityWithRangedAttack2(EntityLivingBase target, float p_82196_2_) {
    double d1 = 1.25D;
    Vec3d vec3d = func_70676_i(1.0F);
    double d2 = target.field_70165_t - this.field_70165_t + vec3d.field_72450_a * d1;
    double d3 = (target.func_174813_aQ()).field_72338_b + (target.field_70131_O / 2.0F) - 0.5D + this.field_70163_u + (this.field_70131_O / 2.0F);
    double d4 = target.field_70161_v - this.field_70161_v + vec3d.field_72449_c * d1;
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
    EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
    entitylargefireball.field_70165_t = this.field_70165_t + vec3d.field_72450_a * d1;
    entitylargefireball.field_70163_u = this.field_70163_u + (this.field_70131_O / 2.0F) + 0.5D;
    entitylargefireball.field_70161_v = this.field_70161_v + vec3d.field_72449_c * d1;
    this.field_70170_p.func_72838_d((Entity)entitylargefireball);
    func_184609_a(EnumHand.MAIN_HAND);
  }
  
  public void attackEntityWithSpray(EntityLivingBase target, float p_82196_2_) {
    double d = 1.25D;
    Vec3d vec3d = func_70676_i(1.0F);
    EntityPoisonSpray entitysnowball = new EntityPoisonSpray(this.field_70170_p, (EntityLivingBase)this);
    double d0 = target.field_70163_u + target.func_70047_e() - 1.100000023841858D;
    double d1 = target.field_70165_t - this.field_70165_t + vec3d.field_72450_a * d;
    double d2 = d0 - entitysnowball.field_70163_u;
    double d3 = target.field_70161_v - this.field_70161_v + vec3d.field_72449_c * d;
    float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.1F;
    entitysnowball.func_70186_c(d1, d2 + f, d3, 1.2F, 1.0F);
    func_184185_a(SoundEvents.field_187888_ft, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
    this.field_70170_p.func_72838_d((Entity)entitysnowball);
    func_184609_a(EnumHand.MAIN_HAND);
  }
  
  public void fireRay(Entity entity, double x, double y, double z) {
    if (entity != null && entity.func_70089_S()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.field_70165_t - d3;
      double d7 = entity.field_70163_u - d4;
      double d8 = entity.field_70161_v - d5;
      EntityDisintigrationRay entitywitherskull = new EntityDisintigrationRay(this.field_70170_p, entity, (EntityLivingBase)this, d6, d7, d8);
      entitywitherskull.field_70163_u = d4;
      entitywitherskull.field_70165_t = d3;
      entitywitherskull.field_70161_v = d5;
      entitywitherskull.field_70233_c = d4;
      entitywitherskull.field_70232_b = d3;
      entitywitherskull.field_70230_d = d5;
      entitywitherskull.targetEntity = entity;
      this.field_70170_p.func_72838_d((Entity)entitywitherskull);
    } 
  }
  
  public void fireCone(Entity entity, double x, double y, double z) {
    if (entity != null && entity.func_70089_S()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.field_70165_t - d3;
      double d7 = entity.field_70163_u - d4;
      double d8 = entity.field_70161_v - d5;
      EntityFrostRay entitywitherskull = new EntityFrostRay(this.field_70170_p, entity, (EntityLivingBase)this, d6, d7, d8);
      entitywitherskull.field_70163_u = d4;
      entitywitherskull.field_70165_t = d3;
      entitywitherskull.field_70161_v = d5;
      entitywitherskull.field_70233_c = d4;
      entitywitherskull.field_70232_b = d3;
      entitywitherskull.field_70230_d = d5;
      entitywitherskull.targetEntity = entity;
      this.field_70170_p.func_72838_d((Entity)entitywitherskull);
    } 
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(64.0D, 64.0D, 64.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity != null && !func_184191_r((Entity)entity))
          if (!func_184191_r((Entity)entity)) {
            try {
              ReflectionHelper.findField(entity.getClass(), new String[] { "shieldTime" }).setInt(entity, 0);
            } catch (Exception exception) {}
            double d1 = entity.field_70165_t + (this.field_70146_Z.nextFloat() * 16.0F - 8.0F);
            double d2 = entity.field_70163_u + 20.0D + (this.field_70146_Z.nextFloat() * 20.0F - 10.0F);
            double d3 = entity.field_70161_v + (this.field_70146_Z.nextFloat() * 16.0F - 8.0F);
            fireLightning((Entity)entity, d1, d2, d3);
            this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, true));
          }  
      }  
    setSpecialAttackTimer(1800);
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (getConvertingTarget() != null && func_70068_e((Entity)getConvertingTarget()) > 256.0D)
      func_70661_as().func_75497_a((Entity)getConvertingTarget(), 1.0D); 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) > 512.0D)
      func_70661_as().func_75497_a((Entity)func_70638_az(), 1.0D); 
    if (getAllyTarget() != null && !getAllyTarget().func_70089_S())
      setConvertingTarget((EntityTameBase)null); 
    if (getConvertingTarget() != null && !getConvertingTarget().func_70089_S())
      setConvertingTarget((EntityTameBase)null); 
    if (getWololoTarget() != null && !getWololoTarget().func_70089_S())
      setWololoTarget((EntitySheep)null); 
    if (func_70638_az() != null && !func_70638_az().func_70089_S())
      func_70624_b((EntityLivingBase)null); 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getJukeboxToDanceTo() != null) {
      if (this.field_70173_aa % 10 == 0)
        func_184185_a(SoundEvents.field_191248_br, func_70093_af() ? 0.1F : 1.0F, this.field_70146_Z.nextFloat() * 2.0F); 
      float f = this.field_70761_aq * 0.017453292F + MathHelper.func_76134_b(this.field_70173_aa * 0.6662F) * 0.5F;
      float f1 = MathHelper.func_76134_b(f);
      float f2 = MathHelper.func_76126_a(f);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + f1 * (func_70631_g_() ? 0.3D : 0.6D) * getFittness(), this.field_70163_u + (func_70631_g_() ? 0.9D : 1.8D) * getFittness(), this.field_70161_v + f2 * (func_70631_g_() ? 0.3D : 0.6D) * getFittness(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), new int[0]);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t - f1 * (func_70631_g_() ? 0.3D : 0.6D) * getFittness(), this.field_70163_u + (func_70631_g_() ? 0.9D : 1.8D) * getFittness(), this.field_70161_v - f2 * (func_70631_g_() ? 0.3D : 0.6D) * getFittness(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), new int[0]);
    } 
    if (!func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b()) {
      func_184201_a(EntityEquipmentSlot.OFFHAND, func_184582_a(EntityEquipmentSlot.HEAD));
      func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
    } 
    if (!func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b()) {
      func_184201_a(EntityEquipmentSlot.OFFHAND, func_184582_a(EntityEquipmentSlot.CHEST));
      func_184201_a(EntityEquipmentSlot.CHEST, ItemStack.field_190927_a);
    } 
    if (!func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b()) {
      func_184201_a(EntityEquipmentSlot.OFFHAND, func_184582_a(EntityEquipmentSlot.LEGS));
      func_184201_a(EntityEquipmentSlot.LEGS, ItemStack.field_190927_a);
    } 
    if (!func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b()) {
      func_184201_a(EntityEquipmentSlot.OFFHAND, func_184582_a(EntityEquipmentSlot.LEGS));
      func_184201_a(EntityEquipmentSlot.LEGS, ItemStack.field_190927_a);
    } 
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    return super.func_180482_a(difficulty, livingdata);
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(Items.field_190929_cY));
    func_184642_a(EntityEquipmentSlot.HEAD, 0.0F);
    func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(Items.field_190929_cY));
    func_184642_a(EntityEquipmentSlot.CHEST, 0.0F);
    func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(Items.field_190929_cY));
    func_184642_a(EntityEquipmentSlot.LEGS, 0.0F);
    func_184201_a(EntityEquipmentSlot.FEET, new ItemStack(Items.field_190929_cY));
    func_184642_a(EntityEquipmentSlot.FEET, 0.0F);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_191243_bm;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() - 0.3F); 
    return SoundEvents.field_191245_bo;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() - 0.3F); 
    return SoundEvents.field_191246_bp;
  }
  
  protected float func_70672_c(DamageSource p_70672_1_, float p_70672_2_) {
    p_70672_2_ = super.func_70672_c(p_70672_1_, p_70672_2_);
    if (p_70672_1_.func_76346_g() instanceof EntityLivingBase && func_184191_r(p_70672_1_.func_76346_g()))
      p_70672_2_ = 0.0F; 
    if (p_70672_1_.func_82725_o() && p_70672_1_.func_76355_l() != "antimatter")
      p_70672_2_ = (float)(p_70672_2_ * 0.05D); 
    return p_70672_2_;
  }
  
  protected SoundEvent getSpellSound() {
    return SoundEvents.field_191244_bn;
  }
  
  class AIAttackSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIAttackSpell() {
      super(EntityEvoker.this);
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      double d0 = EntityEvoker.this.field_70163_u;
      double d1 = EntityEvoker.this.field_70163_u;
      float f = (float)MathHelper.func_181159_b(entitylivingbase.field_70161_v - EntityEvoker.this.field_70161_v, entitylivingbase.field_70165_t - EntityEvoker.this.field_70165_t);
      if (EntityEvoker.this.func_70032_d((Entity)entitylivingbase) < 4.0D && entitylivingbase.field_70163_u <= d0 + 1.0D) {
        d0 = EntityEvoker.this.field_70163_u;
        d1 = EntityEvoker.this.field_70163_u;
        int i;
        for (i = 0; i < 5; i++) {
          float f1 = f + i * 3.1415927F * 0.4F;
          spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f1) * 1.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f1) * 1.0D, d0, d1, f1, 0);
        } 
        for (i = 0; i < 10; i++) {
          float f1 = f + i * 3.1415927F * 0.2F;
          spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f1) * 2.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f1) * 2.0D, d0, d1, f1, 10);
        } 
        if (EntityEvoker.this.getLevel() >= 5)
          for (i = 0; i < 15; i++) {
            float f1 = f + i * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f1) * 3.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f1) * 3.0D, d0, d1, f1, 20);
          }  
        if (EntityEvoker.this.getLevel() >= 10)
          for (int k = 0; k < 20; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f2) * 4.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f2) * 4.0D, d0, d1, f2, 30);
          }  
        if (EntityEvoker.this.getLevel() >= 15)
          for (int k = 0; k < 25; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f2) * 5.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f2) * 5.0D, d0, d1, f2, 40);
          }  
        if (EntityEvoker.this.getLevel() >= 20)
          for (int k = 0; k < 30; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f2) * 6.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f2) * 6.0D, d0, d1, f2, 50);
          }  
        if (EntityEvoker.this.getLevel() >= 25)
          for (int k = 0; k < 35; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f2) * 7.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f2) * 7.0D, d0, d1, f2, 60);
          }  
        if (EntityEvoker.this.getLevel() >= 30)
          for (int k = 0; k < 40; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.field_70165_t + MathHelper.func_76134_b(f2) * 8.0D, EntityEvoker.this.field_70161_v + MathHelper.func_76126_a(f2) * 8.0D, d0, d1, f2, 70);
          }  
      } else if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S()) {
        if (!EntityEvoker.this.field_70170_p.field_72995_K) {
          float j = EntityEvoker.this.field_70761_aq * 0.017453292F;
          float f1 = MathHelper.func_76134_b(j);
          float f2 = MathHelper.func_76126_a(j);
          EntityInvisibleFangsProjectile entitymagicmissiles = new EntityInvisibleFangsProjectile(EntityEvoker.this.field_70170_p, (Entity)entitylivingbase, (EntityLivingBase)EntityEvoker.this, EntityEvoker.this.field_70165_t, EntityEvoker.this.field_70163_u, EntityEvoker.this.field_70161_v);
          EntityEvoker.this.field_70170_p.func_72838_d((Entity)entitymagicmissiles);
        } 
      } 
    }
    
    private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
      BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
      boolean flag = true;
      double d0 = 0.0D;
      if (flag && !EntityEvoker.this.field_70170_p.field_72995_K) {
        EntityEvokerFangOther entityevokerfangs = new EntityEvokerFangOther(EntityEvoker.this.field_70170_p, p_190876_1_, blockpos.func_177956_o() + d0, p_190876_3_, p_190876_9_, p_190876_10_, (EntityLivingBase)EntityEvoker.this);
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityevokerfangs);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FANGS;
    }
  }
  
  class AIFireballSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIFireballSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 45.72D && EntityEvoker.this.func_70068_e((Entity)EntityEvoker.this.func_70638_az()) < 45.0D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 3);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 160;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S())
        EntityEvoker.this.func_82196_d(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FIREBALL;
    }
  }
  
  class AISmallFireballSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISmallFireballSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 36.576D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 1);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 80;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S())
        EntityEvoker.this.attackEntityWithRangedAttack2(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FIREBOLT;
    }
  }
  
  class AIPoisonSpraySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIPoisonSpraySpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 9.144D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 2);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 120;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S())
        EntityEvoker.this.attackEntityWithSpray(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.POISON_SPRAY;
    }
  }
  
  class AIMagicMissileSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIMagicMissileSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 36.576D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 5);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 160;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S())
        for (int i = 0; i < (EntityEvoker.this.isHero() ? 18 : 9); i++) {
          if (!EntityEvoker.this.field_70170_p.field_72995_K) {
            EntityMagicMissile entitymagicmissiles = new EntityMagicMissile(EntityEvoker.this.field_70170_p, (Entity)entitylivingbase, (EntityLivingBase)EntityEvoker.this, EntityEvoker.this.field_70165_t, EntityEvoker.this.field_70163_u + 2.0D, EntityEvoker.this.field_70161_v);
            entitymagicmissiles.field_70163_u = EntityEvoker.this.field_70163_u + 2.0D;
            Random random = new Random();
            entitymagicmissiles.field_70159_w += random.nextDouble() * 2.0D - 1.0D;
            entitymagicmissiles.field_70181_x += random.nextDouble() * 2.0D;
            entitymagicmissiles.field_70179_y += random.nextDouble() * 2.0D - 1.0D;
            EntityEvoker.this.field_70170_p.func_72838_d((Entity)entitymagicmissiles);
          } 
        }  
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.MAGIC_MISSLE;
    }
  }
  
  class AIDisintigrationRaySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIDisintigrationRaySpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 18.288D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 20);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 360;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        float f = EntityEvoker.this.field_70761_aq * 0.017453292F;
        float f1 = MathHelper.func_76134_b(f);
        float f2 = MathHelper.func_76126_a(f);
        double d1 = EntityEvoker.this.field_70165_t - (f2 * 0.4F);
        double d2 = EntityEvoker.this.field_70163_u + 1.25D;
        double d3 = EntityEvoker.this.field_70161_v + (f1 * 0.4F);
        EntityEvoker.this.fireRay((Entity)entitylivingbase, d1, d2, d3);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.DISINTIGRATION_RAY;
    }
  }
  
  class AIFrostRaySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIFrostRaySpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > 18.288D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 15);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 200;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        float f = EntityEvoker.this.field_70761_aq * 0.017453292F;
        float f1 = MathHelper.func_76134_b(f);
        float f2 = MathHelper.func_76126_a(f);
        double d1 = EntityEvoker.this.field_70165_t - (f2 * 0.4F);
        double d2 = EntityEvoker.this.field_70163_u + 1.25D;
        double d3 = EntityEvoker.this.field_70161_v + (f1 * 0.4F);
        EntityEvoker.this.fireCone((Entity)entitylivingbase, d1, d2, d3);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FROST_RAY;
    }
  }
  
  class AILightningBoltSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AILightningBoltSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityEvoker.this.func_70638_az() != null && EntityEvoker.this.func_70032_d((Entity)EntityEvoker.this.func_70638_az()) > ((EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? 64.0D : 30.48D))
        return false; 
      return (EntityEvoker.this.getLevel() >= 10);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? 1200 : 240;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.func_70638_az();
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase != null && entitylivingbase.func_70089_S()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        if (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) {
          EntityEvoker.this.performSpecialAttack();
        } else {
          float f = EntityEvoker.this.field_70761_aq * 0.017453292F;
          float f1 = MathHelper.func_76134_b(f);
          float f2 = MathHelper.func_76126_a(f);
          double d1 = EntityEvoker.this.field_70165_t - (f2 * 0.4F);
          double d2 = EntityEvoker.this.field_70163_u + 1.25D;
          double d3 = EntityEvoker.this.field_70161_v + (f1 * 0.4F);
          EntityEvoker.this.fireLightning((Entity)entitylivingbase, d1, d2, d3);
        } 
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? SoundEvents.field_187754_de : SoundEvents.field_191247_bq;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.LIGHTNING_BOLT;
    }
  }
  
  class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISummonSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      int count = 0;
      List<EntityTameBase> list = EntityEvoker.this.field_70170_p.func_175647_a(EntityTameBase.class, EntityEvoker.this.func_174813_aQ().func_186662_g(32.0D), EntitySelectors.field_94557_a);
      if (!list.isEmpty()) {
        EntityTameBase entity = list.get(EntityEvoker.this.field_70146_Z.nextInt(list.size()));
        if (!entity.hasLimitedLife()) {
          list.remove(entity);
        } else {
          count++;
          if (count > 10)
            return false; 
        } 
      } 
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 400;
    }
    
    protected void castSpell() {
      switch (EntityEvoker.this.field_70146_Z.nextInt(10)) {
        case 1:
          if (EntityEvoker.this.getLevel() >= 10) {
            for (int j = 0; j < (4 + EntityEvoker.this.field_70146_Z.nextInt(6)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntityZombie entityvex = new EntityZombie(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 2:
          if (EntityEvoker.this.getLevel() >= 20) {
            for (int j = 0; j < (2 + EntityEvoker.this.field_70146_Z.nextInt(6)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntitySkeleton entityvex = new EntitySkeleton(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 3:
          if (EntityEvoker.this.getLevel() >= 50) {
            for (int j = 0; j < (2 + EntityEvoker.this.field_70146_Z.nextInt(4)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntityBlaze entityvex = new EntityBlaze(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 4:
          if (EntityEvoker.this.getLevel() >= 100) {
            for (int j = 0; j < (2 + EntityEvoker.this.field_70146_Z.nextInt(2)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntityEnderman entityvex = new EntityEnderman(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 5:
          if (EntityEvoker.this.getLevel() >= 150) {
            for (int j = 0; j < 2 * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntityIceGolem entityvex = new EntityIceGolem(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 6:
          if (EntityEvoker.this.getLevel() >= 200) {
            for (int j = 0; j < 2 * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
              EntityMagmaGolem entityvex = new EntityMagmaGolem(EntityEvoker.this.field_70170_p);
              entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
              entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
              entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            } 
            return;
          } 
        case 7:
          if (EntityEvoker.this.getLevel() >= 300) {
            BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
            EntityWither entityvex = new EntityWither(EntityEvoker.this.field_70170_p);
            entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
            entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
            if (!EntityEvoker.this.isWild())
              entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
            entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
            entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
            entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
            EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
            return;
          } 
          break;
      } 
      for (int i = 0; i < (4 + EntityEvoker.this.field_70146_Z.nextInt(8)) * (EntityEvoker.this.isHero() ? 2 : 1); i++) {
        BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).func_177982_a(-3 + EntityEvoker.this.field_70146_Z.nextInt(6), EntityEvoker.this.field_70146_Z.nextInt(4), -3 + EntityEvoker.this.field_70146_Z.nextInt(6));
        EntityVex entityvex = new EntityVex(EntityEvoker.this.field_70170_p);
        entityvex.func_174828_a(blockpos, 0.0F, 0.0F);
        entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(blockpos), (IEntityLivingData)null);
        if (!EntityEvoker.this.isWild())
          entityvex.setOwnerId(EntityEvoker.this.func_184753_b()); 
        entityvex.setBoundOrigin(blockpos);
        entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * ((EntityEvoker.this.getLevel() <= 10) ? 10 : EntityEvoker.this.getLevel())));
        entityvex.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 4000));
        entityvex.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 4000));
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191248_br;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.SUMMON_VEX;
    }
  }
  
  class AISummonMeteorStormSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISummonMeteorStormSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      return (EntityEvoker.this.getLevel() >= 50);
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.func_70638_az() != null && super.func_75253_b());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 1200;
    }
    
    protected void castSpell() {
      List<Entity> list = EntityEvoker.this.field_70170_p.func_72839_b((Entity)EntityEvoker.this, EntityEvoker.this.func_174813_aQ().func_186662_g(64.0D));
      if (list != null && !list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Entity entity = list.get(i);
          if (entity != null && entity instanceof EntityLivingBase && !EntityEvoker.this.func_184191_r(entity)) {
            double d1 = entity.field_70165_t + EntityEvoker.this.field_70146_Z.nextDouble() * 50.0D - 25.0D;
            double d2 = entity.field_70163_u + 100.0D + EntityEvoker.this.field_70146_Z.nextDouble() * 50.0D - 25.0D;
            double d3 = entity.field_70161_v + EntityEvoker.this.field_70146_Z.nextDouble() * 50.0D - 25.0D;
            double d4 = entity.field_70165_t - d1;
            double d5 = entity.field_70163_u - d2;
            double d6 = entity.field_70161_v - d3;
            EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(EntityEvoker.this.field_70170_p, (EntityLivingBase)EntityEvoker.this, d4, d5, d6);
            EntityEvoker.this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)entitylargefireball), 0);
            entitylargefireball.field_92057_e = 8;
            entitylargefireball.field_70165_t = d1;
            entitylargefireball.field_70163_u = d2;
            entitylargefireball.field_70161_v = d3;
            if (!EntityEvoker.this.field_70170_p.field_72995_K)
              EntityEvoker.this.field_70170_p.func_72838_d((Entity)entitylargefireball); 
          } 
        }  
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191248_br;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.METEOR_STORM;
    }
  }
  
  public class AIWololoSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntitySheep> wololoSelector = new Predicate<EntitySheep>() {
        public boolean apply(EntitySheep p_apply_1_) {
          return (EntityEvoker.this.getOwner() != null) ? ((p_apply_1_.func_175509_cj() == EnumDyeColor.RED)) : ((p_apply_1_.func_175509_cj() == EnumDyeColor.BLUE));
        }
      };
    
    public AIWololoSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (EntityEvoker.this.func_70638_az() != null)
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.field_70173_aa < this.spellCooldown)
        return false; 
      List<EntitySheep> list = EntityEvoker.this.field_70170_p.func_175647_a(EntitySheep.class, EntityEvoker.this.func_174813_aQ().func_72314_b(16.0D, 4.0D, 16.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityEvoker.this.setWololoTarget(list.get(EntityEvoker.this.field_70146_Z.nextInt(list.size())));
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.getWololoTarget() != null && this.spellWarmup > 0);
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      EntityEvoker.this.setWololoTarget((EntitySheep)null);
    }
    
    protected void castSpell() {
      EntitySheep entitysheep = EntityEvoker.this.getWololoTarget();
      if (entitysheep != null && entitysheep.func_70089_S()) {
        EntityEvoker.this.func_184185_a(ESound.converted, 1.0F, 1.0F);
        if (EntityEvoker.this.isWild()) {
          entitysheep.func_175512_b(EnumDyeColor.RED);
        } else {
          entitysheep.func_175512_b(EnumDyeColor.BLUE);
        } 
      } 
    }
    
    protected int getCastWarmupTime() {
      return 40;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 40;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191249_bs;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.WOLOLO;
    }
  }
  
  public class AIConvertingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = new Predicate<EntityTameBase>() {
        public boolean apply(EntityTameBase p_apply_1_) {
          return (p_apply_1_.isWild() && p_apply_1_.func_70089_S() && !p_apply_1_.isABoss() && p_apply_1_.getTier() != EnumTier.TIER6);
        }
      };
    
    public AIConvertingSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (EntityEvoker.this.isWild())
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.field_70173_aa < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityEvoker.this.field_70170_p.func_175647_a(EntityTameBase.class, EntityEvoker.this.func_174813_aQ().func_186662_g(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityEvoker.this.field_70146_Z.nextInt(list.size()));
      if (EntityEvoker.this.func_184191_r((Entity)entity) || entity.isABoss() || entity.isHero() || entity.hasLimitedLife()) {
        list.remove(entity);
        return false;
      } 
      EntityEvoker.this.setConvertingTarget(entity);
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.getConvertingTarget() != null && EntityEvoker.this.getConvertingTarget().func_70089_S() && EntityEvoker.this.getConvertingTarget().isWild() && this.spellWarmup > 0);
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      if (EntityEvoker.this.getConvertingTarget() != null && !EntityEvoker.this.getConvertingTarget().isWild())
        EntityEvoker.this.setConvertingTarget((EntityTameBase)null); 
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityEvoker.this.getConvertingTarget();
      if (entitysheep != null && entitysheep.func_70089_S()) {
        entitysheep.func_70656_aK();
        entitysheep.func_70661_as().func_75497_a((Entity)EntityEvoker.this, 1.2D);
        entitysheep.incrementConversion((EntityPlayer)EntityEvoker.this.getOwner());
        for (int i1 = 0; i1 < EntityEvoker.this.getLevel() / 10 + 1; i1++)
          entitysheep.incrementConversion((EntityPlayer)EntityEvoker.this.getOwner()); 
      } 
    }
    
    protected int getCastWarmupTime() {
      return 10;
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 10;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191249_bs;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.CONVERT;
    }
  }
  
  public class AIReinforcingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = new Predicate<EntityTameBase>() {
        public boolean apply(EntityTameBase p_apply_1_) {
          return (!p_apply_1_.isWild() && p_apply_1_.func_70089_S());
        }
      };
    
    public AIReinforcingSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (EntityEvoker.this.isWild())
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.field_70173_aa < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityEvoker.this.field_70170_p.func_175647_a(EntityTameBase.class, EntityEvoker.this.func_174813_aQ().func_186662_g(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityEvoker.this.field_70146_Z.nextInt(list.size()));
      if ((!entity.func_70644_a(MobEffects.field_76437_t) && !entity.func_70644_a(MobEffects.field_76421_d) && !entity.func_70644_a(MobEffects.field_76431_k) && !entity.func_70644_a(MobEffects.field_188424_y) && !entity.func_70644_a(MobEffects.field_76440_q) && !entity.func_70644_a(MobEffects.field_76436_u) && !entity.func_70644_a(MobEffects.field_82731_v) && !entity.func_70027_ad()) || entity.hasLimitedLife()) {
        list.remove(entity);
        return false;
      } 
      EntityEvoker.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityEvoker.this.getAllyTarget() != null && EntityEvoker.this.getAllyTarget().func_70089_S() && EntityEvoker.this.func_184191_r((Entity)EntityEvoker.this.getAllyTarget()) && (EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_76431_k) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_188424_y) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_76437_t) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_76440_q) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_76421_d) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_82731_v) || EntityEvoker.this.getAllyTarget().func_70644_a(MobEffects.field_76436_u) || EntityEvoker.this.getAllyTarget().func_70027_ad()) && this.spellWarmup > 0);
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      EntityEvoker.this.setAllyTarget((EntityTameBase)null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityEvoker.this.getAllyTarget();
      if (entitysheep != null && entitysheep.func_70089_S() && EntityEvoker.this.func_184191_r((Entity)entitysheep))
        if (entitysheep.func_70027_ad() || entitysheep.func_70644_a(MobEffects.field_188424_y) || entitysheep.func_70644_a(MobEffects.field_76437_t) || entitysheep.func_70644_a(MobEffects.field_76440_q) || entitysheep.func_70644_a(MobEffects.field_188424_y) || entitysheep.func_70644_a(MobEffects.field_76431_k) || entitysheep.func_70644_a(MobEffects.field_76421_d) || entitysheep.func_70644_a(MobEffects.field_76436_u) || entitysheep.func_70644_a(MobEffects.field_82731_v)) {
          if (entitysheep instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep)
            entitysheep.func_96094_a("jeb_"); 
          entitysheep.func_70656_aK();
          entitysheep.func_70066_B();
          entitysheep.func_184596_c(MobEffects.field_76436_u);
          entitysheep.func_184596_c(MobEffects.field_82731_v);
          entitysheep.func_184596_c(MobEffects.field_76421_d);
          entitysheep.func_184596_c(MobEffects.field_76437_t);
          entitysheep.func_184596_c(MobEffects.field_76440_q);
          entitysheep.func_184596_c(MobEffects.field_76431_k);
          entitysheep.func_184596_c(MobEffects.field_188424_y);
          entitysheep.func_184596_c(MobEffects.field_76438_s);
          entitysheep.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 1000 * EntityEvoker.this.getLevel()));
          entitysheep.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 1000 * EntityEvoker.this.getLevel()));
          EntityEvoker.this.getOwner().func_70066_B();
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76436_u);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_82731_v);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76421_d);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76437_t);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76431_k);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76440_q);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_188424_y);
          EntityEvoker.this.getOwner().func_184596_c(MobEffects.field_76438_s);
          EntityEvoker.this.getOwner().func_70690_d(new PotionEffect(MobEffects.field_76429_m, 1000 * EntityEvoker.this.getLevel()));
          EntityEvoker.this.getOwner().func_70690_d(new PotionEffect(MobEffects.field_76428_l, 100, 1));
          EntityEvoker.this.getOwner().func_70690_d(new PotionEffect(MobEffects.field_76443_y, 20));
          EntityEvoker.this.getOwner().func_70690_d(new PotionEffect(MobEffects.field_76426_n, 1000 * EntityEvoker.this.getLevel()));
          if ((entitysheep instanceof EntityPig && !((EntityPig)entitysheep).getSaddled()) || (entitysheep instanceof EntityCreeper && !((EntityCreeper)entitysheep).getPowered())) {
            entitysheep.func_70077_a((EntityLightningBolt)null);
            entitysheep.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(entitysheep.field_70170_p, entitysheep.field_70165_t - 0.5D, entitysheep.field_70163_u, entitysheep.field_70161_v - 0.5D, true));
          } 
          if (entitysheep instanceof EntityRabbit && ((EntityRabbit)entitysheep).getRabbitType() != 99) {
            ((EntityRabbit)entitysheep).setRabbitType(99);
            entitysheep.field_70173_aa = 1;
            entitysheep.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(entitysheep.field_70170_p, entitysheep.field_70165_t - 0.5D, entitysheep.field_70163_u, entitysheep.field_70161_v - 0.5D, true));
          } 
          if (entitysheep instanceof EntitySlime && ((EntitySlime)entitysheep).getSlimeSize() <= 1) {
            ((EntitySlime)entitysheep).setSlimeSize((EntityEvoker.this.field_70146_Z.nextInt(4) == 0) ? 4 : 2);
            entitysheep.field_70173_aa = 1;
            entitysheep.func_184185_a(SoundEvents.field_187545_bE, 2.0F, 1.0F);
            entitysheep.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(entitysheep.field_70170_p, entitysheep.field_70165_t - 0.5D, entitysheep.field_70163_u, entitysheep.field_70161_v - 0.5D, true));
          } 
          func_75251_c();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 0;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_191248_br;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BUFFER_EVOKER;
    }
  }
  
  public class AIPolymorphSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIPolymorphSpell() {
      super(EntityEvoker.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      return (EntityEvoker.this.getPolymorphTime() <= 0 && EntityEvoker.this.getLevel() >= 50);
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 2400;
    }
    
    protected void castSpell() {
      if (EntityEvoker.this.getLevel() >= 300) {
        EntityEnderDragon entityvex = new EntityEnderDragon(EntityEvoker.this.field_70170_p);
        entityvex.func_82149_j((Entity)EntityEvoker.this);
        entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(EntityEvoker.this.func_180425_c()), (IEntityLivingData)null);
        entityvex.setOwnerId(EntityEvoker.this.func_184753_b());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.func_184185_a(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.func_184185_a(ESound.blast, 10.0F, 1.0F);
        entityvex.func_70656_aK();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.func_96094_a(EntityEvoker.this.func_70005_c_());
        entityvex.field_70761_aq = entityvex.field_70177_z = entityvex.field_70759_as + 180.0F;
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
        EntityEvoker.this.field_70170_p.func_72900_e((Entity)EntityEvoker.this);
      } else if (EntityEvoker.this.getLevel() < 300 && EntityEvoker.this.getLevel() >= 200) {
        EntityWither entityvex = new EntityWither(EntityEvoker.this.field_70170_p);
        entityvex.func_82149_j((Entity)EntityEvoker.this);
        entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(EntityEvoker.this.func_180425_c()), (IEntityLivingData)null);
        entityvex.setOwnerId(EntityEvoker.this.func_184753_b());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.func_184185_a(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.func_184185_a(ESound.blast, 10.0F, 1.0F);
        entityvex.func_70656_aK();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.func_96094_a(EntityEvoker.this.func_70005_c_());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
        EntityEvoker.this.field_70170_p.func_72900_e((Entity)EntityEvoker.this);
      } else if (EntityEvoker.this.getLevel() < 200 && EntityEvoker.this.getLevel() >= 100) {
        EntityGiant entityvex = new EntityGiant(EntityEvoker.this.field_70170_p);
        entityvex.func_82149_j((Entity)EntityEvoker.this);
        entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(EntityEvoker.this.func_180425_c()), (IEntityLivingData)null);
        entityvex.setOwnerId(EntityEvoker.this.func_184753_b());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.func_184185_a(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.func_184185_a(ESound.blast, 10.0F, 1.0F);
        entityvex.func_70656_aK();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.func_96094_a(EntityEvoker.this.func_70005_c_());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
        EntityEvoker.this.field_70170_p.func_72900_e((Entity)EntityEvoker.this);
      } else {
        EntityIronGolem entityvex = new EntityIronGolem(EntityEvoker.this.field_70170_p);
        entityvex.func_82149_j((Entity)EntityEvoker.this);
        entityvex.func_180482_a(EntityEvoker.this.field_70170_p.func_175649_E(EntityEvoker.this.func_180425_c()), (IEntityLivingData)null);
        entityvex.setOwnerId(EntityEvoker.this.func_184753_b());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.func_184185_a(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.func_184185_a(ESound.blast, 10.0F, 1.0F);
        entityvex.func_70656_aK();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.func_96094_a(EntityEvoker.this.func_70005_c_());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.field_70170_p.func_72838_d((Entity)entityvex);
        EntityEvoker.this.field_70170_p.func_72900_e((Entity)EntityEvoker.this);
      } 
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      EntityEvoker.this.func_184185_a(SoundEvents.field_191248_br, 1.0F, 1.0F);
      return SoundEvents.field_193790_di;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.POLYMORPH;
    }
  }
}
