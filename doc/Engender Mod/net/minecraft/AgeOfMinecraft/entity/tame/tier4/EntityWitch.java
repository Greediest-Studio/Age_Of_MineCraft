package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.init.MiscHandler;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitch extends EntityTameBase implements IRangedAttackMob, Light {
  private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
  
  private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).func_111168_a(false);
  
  private static final DataParameter<Boolean> IS_AGGRESSIVE = EntityDataManager.func_187226_a(EntityWitch.class, DataSerializers.field_187198_h);
  
  private int witchAttackTimer;
  
  public EntityWitch(World worldIn) {
    super(worldIn);
    func_70105_a(0.5F, 1.95F);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 8.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.0D, 40, 10.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 10;
  }
  
  public String getDescName() {
    return "witch";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityWitch(this.field_70170_p);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(IS_AGGRESSIVE, Boolean.valueOf(false));
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187920_gt;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187923_gw;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187921_gu;
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
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityWitch baby = new EntityWitch(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        baby.setGrowingAge(-32000);
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
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  public void setAggressive(boolean aggressive) {
    func_184212_Q().func_187227_b(IS_AGGRESSIVE, Boolean.valueOf(aggressive));
  }
  
  public boolean func_184730_o() {
    return ((Boolean)func_184212_Q().func_187225_a(IS_AGGRESSIVE)).booleanValue();
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(26.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
  }
  
  public void performSpecialAttack() {
    func_184185_a(ESound.witchSpecial, 10.0F, 1.0F);
    setSpecialAttackTimer(1200);
  }
  
  public void func_70636_d() {
    if (isHero() && getSpecialAttackTimer() == 1180) {
      List<EntityLiving> list = this.field_70170_p.func_175647_a(EntityLiving.class, func_174813_aQ().func_72314_b(24.0D, 24.0D, 24.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLiving entity = list.get(i1);
          if (entity != null && entity.func_184222_aU() && !(entity instanceof EntityTameBase) && !func_184191_r((Entity)entity)) {
            EntityPig entityzombie = new EntityPig(this.field_70170_p);
            entityzombie.func_82149_j((Entity)entity);
            this.field_70170_p.func_72900_e((Entity)entity);
            entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), null);
            entityzombie.func_70656_aK();
            if (!this.field_70170_p.field_72995_K)
              this.field_70170_p.func_72838_d((Entity)entityzombie); 
          } 
        }  
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 256.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!this.field_70170_p.field_72995_K) {
      if (this.witchAttackTimer <= 28 && this.witchAttackTimer > 0 && this.field_70173_aa % 4 == 0 && func_70089_S())
        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187664_bz, func_184176_by(), func_70599_aP(), func_70647_i() - 0.2F); 
      if (func_184730_o() && func_110143_aJ() > 0.0F && this.field_70173_aa > 20) {
        if (this.witchAttackTimer-- <= 0) {
          this.field_70761_aq = this.field_70177_z = this.field_70759_as;
          setAggressive(false);
          ItemStack itemstack = func_184614_ca();
          func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
          if (itemstack.func_77973_b() == Items.field_151068_bn) {
            this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187739_dZ, func_184176_by(), func_70599_aP(), func_70647_i());
            List<PotionEffect> list = PotionUtils.func_185189_a(itemstack);
            if (list != null)
              for (PotionEffect potioneffect : list)
                func_70690_d(new PotionEffect(potioneffect));  
          } 
          func_110148_a(SharedMonsterAttributes.field_111263_d).func_111124_b(MODIFIER);
        } 
      } else {
        PotionType potiontype = null;
        if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76427_o)) {
          potiontype = PotionTypes.field_185249_u;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76426_n)) {
          potiontype = PotionTypes.field_185242_n;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76424_c)) {
          potiontype = PotionTypes.field_185245_q;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76428_l)) {
          potiontype = PotionTypes.field_185221_D;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76439_r)) {
          potiontype = PotionTypes.field_185235_g;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76430_j)) {
          potiontype = PotionTypes.field_185240_l;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && !func_70644_a(MobEffects.field_76420_g) && func_70638_az() != null) {
          potiontype = PotionTypes.field_185225_H;
        } else if (this.field_70146_Z.nextFloat() < 0.1F && func_110143_aJ() < func_110138_aP()) {
          potiontype = PotionTypes.field_185251_w;
        } 
        if (potiontype != null && func_110143_aJ() > 0.0F && this.field_70173_aa > 20 && !func_70631_g_()) {
          func_184201_a(EntityEquipmentSlot.MAINHAND, PotionUtils.func_185188_a(new ItemStack((Item)Items.field_151068_bn), potiontype));
          this.witchAttackTimer = func_184614_ca().func_77988_m();
          setAggressive(true);
          this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187922_gv, func_184176_by(), func_70599_aP(), func_70647_i());
          IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
          iattributeinstance.func_111124_b(MODIFIER);
          iattributeinstance.func_111121_a(MODIFIER);
        } 
      } 
      if (!this.field_70170_p.field_72995_K && !func_70631_g_() && getOwner() != null && this.field_70170_p.func_72890_a((Entity)this, 16.0D) != null && this.field_70170_p.func_72890_a((Entity)this, 16.0D) == getOwner() && func_70032_d((Entity)getOwner()) < 16.0D && func_70685_l((Entity)getOwner()) && this.field_70146_Z.nextInt(200) == 0) {
        func_82196_d(getOwner(), 0.0F);
        func_70671_ap().func_75651_a((Entity)getOwner(), 180.0F, 30.0F);
      } 
      if (this.field_70146_Z.nextFloat() < 0.01F)
        this.field_70170_p.func_72960_a((Entity)this, (byte)15); 
    } 
    super.func_70636_d();
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 15) {
      for (int i = 0; i < 50; i++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_WITCH, this.field_70165_t + this.field_70146_Z.nextGaussian() * 0.12999999523162842D, (func_174813_aQ()).field_72337_e + 0.5D + this.field_70146_Z.nextGaussian() * 0.12999999523162842D, this.field_70161_v + this.field_70146_Z.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]); 
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    return (potioneffectIn.func_188419_a() == MobEffects.field_76436_u) ? false : super.func_70687_e(potioneffectIn);
  }
  
  protected float func_70672_c(DamageSource source, float damage) {
    damage = super.func_70672_c(source, damage);
    if (source.func_76346_g() == this)
      damage = 0.0F; 
    if (source.func_82725_o())
      damage = (float)(damage * 0.15D); 
    return damage;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_WITCH;
  }
  
  public void func_82196_d(EntityLivingBase target, float p_82196_2_) {
    for (int i = 0; i < (isHero() ? 3 : 1); i++) {
      if (!func_184730_o() && func_70685_l((Entity)target)) {
        double d0 = target.field_70163_u;
        double d1 = target.field_70165_t + target.field_70159_w - this.field_70165_t;
        double d2 = d0 - this.field_70163_u;
        double d3 = target.field_70161_v + target.field_70179_y - this.field_70161_v;
        float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3);
        PotionType potiontype = PotionTypes.field_185253_y;
        if (func_184191_r((Entity)target))
          switch (this.field_70146_Z.nextInt(8)) {
            case 0:
              potiontype = PotionTypes.field_185251_w;
              break;
            case 1:
              potiontype = PotionTypes.field_185242_n;
              break;
            case 2:
              potiontype = PotionTypes.field_185235_g;
              break;
            case 3:
              potiontype = PotionTypes.field_185249_u;
              break;
            case 4:
              potiontype = PotionTypes.field_185222_E;
              break;
            case 5:
              potiontype = PotionTypes.field_185245_q;
              break;
            case 6:
              potiontype = PotionTypes.field_185225_H;
              break;
            case 7:
              potiontype = PotionTypes.field_185240_l;
              break;
          }  
        if ((target instanceof EntityWitch || target instanceof net.minecraft.entity.monster.EntityWitch) && !func_184191_r((Entity)target)) {
          target.func_70097_a(DamageSource.field_76368_d, 5.0F);
          potiontype = PotionTypes.field_185233_e;
          target.field_70172_ad = 0;
          target.func_70690_d(new PotionEffect(MobEffects.field_188424_y, 100, 4));
        } else if (target.func_70662_br() && !func_184191_r((Entity)target)) {
          potiontype = PotionTypes.field_185251_w;
        } else if (target.func_184222_aU() && !target.func_70644_a(MobEffects.field_76421_d) && target.func_70687_e(new PotionEffect(MobEffects.field_76421_d)) && target != getOwner()) {
          potiontype = PotionTypes.field_185247_s;
        } else if (target.func_184222_aU() && target.func_110143_aJ() >= 2.0F && !target.func_70662_br() && target.func_70687_e(new PotionEffect(MobEffects.field_76436_u)) && !(target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider) && !(target instanceof net.minecraft.entity.monster.EntitySpider) && !target.func_70644_a(MobEffects.field_76436_u) && !func_184191_r((Entity)target)) {
          potiontype = PotionTypes.field_185219_B;
        } else if (target.func_184222_aU() && !target.func_70644_a(MobEffects.field_76437_t) && target.func_70687_e(new PotionEffect(MobEffects.field_76437_t)) && !func_184191_r((Entity)target)) {
          potiontype = PotionTypes.field_185227_J;
        } else if (target.func_184222_aU() && Loader.isModLoaded("abyssalcraft") && target.func_70687_e(new PotionEffect(AbyssalCraftAPI.antimatter_potion)) && !EntityUtil.isEntityAnti(target) && isAntiMob() && !target.func_70644_a(AbyssalCraftAPI.antimatter_potion) && !func_184191_r((Entity)target)) {
          potiontype = MiscHandler.antiMatter_long;
        } 
        EntityPotionOther entitypotion = new EntityPotionOther(this.field_70170_p, (EntityLivingBase)this, PotionUtils.func_185188_a((getIntelligence() > 40.0F) ? new ItemStack((Item)Items.field_185156_bI) : new ItemStack((Item)Items.field_185155_bH), potiontype));
        entitypotion.field_70125_A -= -20.0F;
        entitypotion.func_70186_c(d1, d2, d3, 1.0F * func_70032_d((Entity)target) / func_70032_d((Entity)target), 9.0F);
        func_70642_aH();
        func_184609_a(EnumHand.MAIN_HAND);
        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187924_gx, func_184176_by(), 1.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.4F);
        this.field_70170_p.func_72838_d((Entity)entitypotion);
      } 
    } 
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.84F;
  }
}
