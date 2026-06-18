package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;

public class EntitySkeleton extends EntityTameBase implements IRangedAttackMob, Undead {
  private static final DataParameter<Integer> SKELETON_VARIANT = EntityDataManager.func_187226_a(EntitySkeleton.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.func_187226_a(EntitySkeleton.class, DataSerializers.field_187198_h);
  
  private final EntityAIAttackRangedBowAlly<EntitySkeleton> aiArrowAttack = new EntityAIAttackRangedBowAlly(this, 1.0D, 5, 15.0F);
  
  private final EntityAIAttackRangedAlly aiRangedAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  private int helmetCount = 1;
  
  public EntitySkeleton(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIRestrictSun((EntityCreature)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    if (getSkeletonType() == 1) {
      func_70105_a(0.6F, 2.39F);
    } else {
      func_70105_a(0.5F, 1.95F);
    } 
    if (worldIn != null && !worldIn.field_72995_K)
      setCombatTask(); 
    this.field_70728_aV = 4;
  }
  
  public String getDescName() {
    switch (getSkeletonType()) {
      case 1:
        return "wither_skeleton";
      case 2:
        return "stray";
    } 
    return "skeleton";
  }
  
  public int getNextLevelRequirement() {
    return (getTier() == EnumTier.TIER4) ? 100 : 25;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySkeleton(this.field_70170_p);
  }
  
  public int timesToConvert() {
    return (getSkeletonType() != 0) ? 27 : 13;
  }
  
  public EnumTier getTier() {
    return (getSkeletonType() != 0) ? EnumTier.TIER4 : EnumTier.TIER3;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (func_184187_bx() != null && func_184187_bx() instanceof EntitySkeletonHorse) {
        switch (getSkeletonType()) {
          case 1:
            return I18n.func_74838_a("entity.WitherSkeletonHorsemanHelpful.cmm.name");
          case 2:
            return I18n.func_74838_a("entity.StrayHorsemanHelpful.cmm.name");
        } 
        return I18n.func_74838_a("entity.SkeletonHorsemanHelpful.cmm.name");
      } 
      switch (getSkeletonType()) {
        case 1:
          return I18n.func_74838_a("entity.WitherSkeletonHelpful.cmm.name");
        case 2:
          return I18n.func_74838_a("entity.StrayHelpful.cmm.name");
      } 
      return I18n.func_74838_a("entity.SkeletonHelpful.cmm.name");
    } 
    if (func_184187_bx() != null && func_184187_bx() instanceof EntitySkeletonHorse) {
      switch (getSkeletonType()) {
        case 1:
          return I18n.func_74838_a("entity.WitherSkeletonHorsemanHelpful.name");
        case 2:
          return I18n.func_74838_a("entity.StrayHorsemanHelpful.name");
      } 
      return I18n.func_74838_a("entity.SkeletonHorsemanHelpful.name");
    } 
    switch (getSkeletonType()) {
      case 1:
        return I18n.func_74838_a("entity.WitherSkeletonHelpful.name");
      case 2:
        return I18n.func_74838_a("entity.StrayHelpful.name");
    } 
    return I18n.func_74838_a("entity.SkeletonHelpful.name");
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(SKELETON_VARIANT, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(ATTACKING, Boolean.valueOf(false));
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntitySkeleton baby = new EntitySkeleton(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setGrowingAge(-48000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        baby.setSkeletonType(getSkeletonType());
        baby.func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
        baby.func_184201_a(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a);
        baby.func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
        baby.func_184201_a(EntityEquipmentSlot.CHEST, ItemStack.field_190927_a);
        baby.func_184201_a(EntityEquipmentSlot.LEGS, ItemStack.field_190927_a);
        baby.func_184201_a(EntityEquipmentSlot.FEET, ItemStack.field_190927_a);
        if (isMarried())
          for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
            baby.levelUp();  
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantSkeleton(this.field_70170_p); 
    return null;
  }
  
  protected SoundEvent func_184639_G() {
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.field_190036_ha;
      case 2:
        return SoundEvents.field_190032_gu;
    } 
    return SoundEvents.field_187854_fc;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.05F); 
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.field_190038_hc;
      case 2:
        return SoundEvents.field_190034_gw;
    } 
    return SoundEvents.field_187864_fh;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.05F); 
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.field_190037_hb;
      case 2:
        return SoundEvents.field_190033_gv;
    } 
    return SoundEvents.field_187856_fd;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    switch (getSkeletonType()) {
      case 1:
        func_184185_a(SoundEvents.field_190039_hd, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
      case 2:
        func_184185_a(SoundEvents.field_190035_gx, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
        break;
    } 
    func_184185_a(SoundEvents.field_187868_fj, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.func_180429_a(pos, blockIn); 
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.15F) : super.func_70647_i();
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      if (getSkeletonType() == 1 && p_70652_1_ instanceof EntityLivingBase) {
        int i = 5;
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
          i = 10;
        } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
          i = 20;
        } 
        if (i > 0)
          ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_82731_v, i * 20, 0 + this.field_70170_p.func_175659_aa().func_151525_a())); 
      } 
      if (getSkeletonType() == 2 && p_70652_1_ instanceof EntityLivingBase) {
        int i = 10;
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
          i = 20;
        } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
          i = 40;
        } 
        if (i > 0)
          ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76421_d, i * 20, 0 + this.field_70170_p.func_175659_aa().func_151525_a())); 
      } 
      if (func_184582_a(EntityEquipmentSlot.MAINHAND) != null && func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof net.minecraft.item.ItemBow) {
        func_184609_a(EnumHand.OFF_HAND);
        func_184185_a(SoundEvents.field_187721_dT, func_70599_aP(), 1.0F);
        func_70653_a((Entity)this, 0.5F, MathHelper.func_76126_a(this.field_70759_as * 0.017453292F), -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F));
        ((EntityLivingBase)p_70652_1_).func_70653_a((Entity)this, 1.0F, MathHelper.func_76126_a(this.field_70759_as * 0.017453292F), -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F));
      } 
      if (func_184582_a(EntityEquipmentSlot.OFFHAND) != null && func_184582_a(EntityEquipmentSlot.OFFHAND).func_77973_b() == Items.field_185159_cQ) {
        func_184185_a(SoundEvents.field_187767_eL, func_70599_aP(), 1.0F);
        func_70653_a((Entity)this, 0.5F, -MathHelper.func_76126_a(this.field_70759_as * 0.017453292F), MathHelper.func_76134_b(this.field_70759_as * 0.017453292F));
        ((EntityLivingBase)p_70652_1_).func_70653_a((Entity)this, 1.0F, MathHelper.func_76126_a(this.field_70759_as * 0.017453292F), -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F));
      } 
      if (func_184582_a(EntityEquipmentSlot.MAINHAND) != null && (func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151033_d || func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151059_bz))
        p_70652_1_.func_70015_d(12); 
      return true;
    } 
    return false;
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    return (potioneffectIn.func_188419_a() == MobEffects.field_82731_v && getSkeletonType() == 1) ? false : super.func_70687_e(potioneffectIn);
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void performSpecialAttack() {
    if (func_184586_b(EnumHand.MAIN_HAND) != null && func_184586_b(EnumHand.MAIN_HAND).func_77973_b() instanceof net.minecraft.item.ItemBow) {
      setSpecialAttackTimer(500);
      func_184185_a(ESound.skeletonSpecial, 10.0F, 1.0F);
    } else {
      func_70638_az().func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * 5.0F);
      setSpecialAttackTimer(500);
    } 
  }
  
  public void func_70636_d() {
    ItemStack hats = (this.helmetCount > 0) ? new ItemStack((Item)Items.field_151024_Q, this.helmetCount) : ItemStack.field_190927_a;
    this.basicInventory.func_70299_a(7, hats);
    if (func_184218_aH() && func_184187_bx() instanceof EntitySkeletonHorse)
      func_184188_bt().clear(); 
    if (getOwner() != null)
      if (func_70068_e((Entity)getOwner()) >= 2304.0D && func_184613_cA()) {
        double d01 = (getOwner()).field_70165_t - this.field_70165_t;
        double d11 = (getOwner()).field_70163_u - this.field_70163_u;
        double d21 = (getOwner()).field_70161_v - this.field_70161_v;
        float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
        this.field_70159_w = d01 / f2 * 0.5D * 0.5D + this.field_70159_w * 0.5D;
        this.field_70181_x = d11 / f2 * 0.5D * 0.5D + this.field_70179_y * 0.5D;
        this.field_70179_y = d21 / f2 * 0.5D * 0.5D + this.field_70179_y * 0.5D;
        func_70625_a((Entity)getOwner(), 180.0F, 180.0F);
      }  
    if (this.field_70170_p.func_175678_i(func_180425_c()) && ((func_70638_az() != null && this.field_70122_E) || (!isWild() && (getOwner()).field_70163_u > this.field_70163_u && getOwner().func_184613_cA())) && func_184582_a(EntityEquipmentSlot.CHEST) != null && func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR) {
      this.field_70181_x = 1.0D;
      func_70052_a(7, true);
    } 
    if (!this.field_70122_E && func_184582_a(EntityEquipmentSlot.CHEST) != null && func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR && !func_184613_cA())
      func_70052_a(7, true); 
    if (this.field_70122_E && func_184613_cA())
      func_70052_a(7, false); 
    if (func_184613_cA())
      this.field_70761_aq = this.field_70177_z = -((float)MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y)) * 57.295776F; 
    ItemStack item = func_184586_b(EnumHand.MAIN_HAND);
    ItemStack secitem = func_184586_b(EnumHand.OFF_HAND);
    if (!item.func_190926_b() && !secitem.func_190926_b() && item.func_77973_b() instanceof net.minecraft.item.ItemBow && !(secitem.func_77973_b() instanceof net.minecraft.item.ItemBow) && secitem.func_77973_b() != Items.field_185167_i && func_70638_az() != null && func_70068_e((Entity)func_70638_az()) <= 128.0D && (func_70638_az()).field_70163_u <= this.field_70163_u + 3.0D) {
      func_184201_a(EntityEquipmentSlot.MAINHAND, secitem);
      func_184201_a(EntityEquipmentSlot.OFFHAND, item);
    } 
    if (!item.func_190926_b() && !secitem.func_190926_b() && secitem.func_77973_b() instanceof net.minecraft.item.ItemBow && !(item.func_77973_b() instanceof net.minecraft.item.ItemBow) && item.func_77973_b() != Items.field_185167_i && func_70638_az() != null && (func_70068_e((Entity)func_70638_az()) > 128.0D || (func_70638_az()).field_70163_u > this.field_70163_u + 3.0D)) {
      func_184201_a(EntityEquipmentSlot.MAINHAND, secitem);
      func_184201_a(EntityEquipmentSlot.OFFHAND, item);
    } 
    if (!item.func_190926_b() && item.func_77973_b() instanceof net.minecraft.item.ItemBow) {
      if (func_70638_az() != null && func_70638_az().func_70089_S() && func_70068_e((Entity)func_70638_az()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
      if (func_70638_az() != null && isHero() && getSpecialAttackTimer() < 490 && getSpecialAttackTimer() > 470)
        func_82196_d(func_70638_az(), 2.0F); 
    } else if (func_70638_az() != null && func_70638_az().func_70089_S() && func_70068_e((Entity)func_70638_az()) < 32.0D && getSpecialAttackTimer() <= 0 && isHero()) {
      performSpecialAttack();
    } 
    if (func_184187_bx() != null && func_184187_bx() instanceof EntitySkeletonHorse) {
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
      if (func_70093_af()) {
        func_184187_bx().func_70095_a(true);
      } else {
        func_184187_bx().func_70095_a(false);
      } 
      if (func_70051_ag()) {
        func_184187_bx().func_70031_b(true);
      } else {
        func_184187_bx().func_70031_b(false);
      } 
    } 
    if (getSkeletonType() == 1 && func_184187_bx() == null)
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(10.0D); 
    if (this.field_70170_p.field_72995_K && getSkeletonType() == 1)
      this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]); 
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((getSkeletonType() == 1 || isAntiMob() || func_70631_g_() || isHero()) && this.helmetCount != 0)
      if (!this.field_70170_p.field_72995_K)
        for (int i = 0; i < this.helmetCount; i++) {
          func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
          func_145779_a((Item)Items.field_151024_Q, 1);
          this.helmetCount--;
        }   
    ItemStack head = func_184582_a(EntityEquipmentSlot.HEAD);
    if (!head.func_190926_b() && head.func_77973_b() == Items.field_151024_Q && func_70089_S() && !this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K) {
      func_184609_a(EnumHand.MAIN_HAND);
      this.helmetCount++;
      func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
      func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
    } 
    if (func_70089_S() && this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && !func_70631_g_() && getSkeletonType() != 1 && !isAntiMob() && !isHero() && !func_70045_F()) {
      float f = func_70013_c();
      if (f > 0.5F && this.field_70173_aa % (!func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() ? 80 : 10) == 0 && this.field_70170_p.func_175678_i(new BlockPos(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v))) {
        boolean flag = true;
        ItemStack itemstack = func_184582_a(EntityEquipmentSlot.HEAD);
        if (!itemstack.func_190926_b()) {
          if (itemstack.func_77984_f()) {
            itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
            if (itemstack.func_77952_i() >= itemstack.func_77958_k()) {
              func_70669_a(itemstack);
              func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
            } 
          } 
          flag = false;
        } 
        if (flag)
          if (this.helmetCount > 0) {
            func_184609_a(EnumHand.MAIN_HAND);
            func_184609_a(EnumHand.OFF_HAND);
            this.helmetCount--;
            func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((Item)Items.field_151024_Q));
            func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
          } else {
            func_70015_d(8);
          }  
      } 
    } 
    super.func_70636_d();
  }
  
  public void func_70098_U() {
    super.func_70098_U();
    if (func_184187_bx() instanceof EntityCreature) {
      EntityCreature entitycreature = (EntityCreature)func_184187_bx();
      this.field_70761_aq = entitycreature.field_70761_aq;
      entitycreature.field_70125_A = this.field_70125_A;
      entitycreature.field_70759_as = this.field_70759_as;
      if (func_70638_az() != null)
        entitycreature.func_70624_b(func_70638_az()); 
      if (this.field_70173_aa % 40 == 0) {
        this.field_70761_aq = this.field_70177_z = this.field_70759_as;
        if (func_70638_az() == null) {
          entitycreature.func_70691_i(5.0F);
        } else {
          entitycreature.func_70691_i(1.0F);
        } 
      } 
    } 
  }
  
  public void func_70645_a(DamageSource cause) {
    super.func_70645_a(cause);
    if (!this.field_70170_p.field_72995_K && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        func_145779_a((Item)Items.field_151024_Q, 1);
        this.helmetCount--;
      }  
    if (cause.func_76346_g() instanceof EntityCreeper && ((EntityCreeper)cause.func_76346_g()).getPowered())
      func_70099_a(new ItemStack(Items.field_151144_bL, 1, (getSkeletonType() == 1) ? 1 : 0), 0.0F); 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    switch (getSkeletonType()) {
      case 1:
        return ELoot.ENTITIES_WITHER_SKELETON;
      case 2:
        return ELoot.ENTITIES_STRAY;
    } 
    return ELoot.ENTITIES_SKELETON;
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    if (this.field_70146_Z.nextFloat() < 0.25F * difficulty.func_180170_c()) {
      int i = this.field_70146_Z.nextInt(2);
      float f = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.325F : 0.25F;
      if (this.field_70146_Z.nextFloat() < 0.1F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.15F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.2F)
        i++; 
      boolean flag = true;
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
          ItemStack itemstack = func_184582_a(entityequipmentslot);
          if (!flag && this.field_70146_Z.nextFloat() < f)
            break; 
          flag = false;
          if (itemstack.func_190926_b()) {
            Item item = func_184636_a(entityequipmentslot, i);
            if (item != null)
              func_184201_a(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
    if (getSkeletonType() == 1) {
      func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151052_q));
      if (func_70681_au().nextInt(3) == 0) {
        int i = this.field_70146_Z.nextInt(3);
        if (i == 0) {
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_185159_cQ));
        } else {
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151052_q));
        } 
      } 
    } else {
      func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.field_151031_f));
      if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
        int i = this.field_70146_Z.nextInt(3);
        ItemStack stack = new ItemStack(Items.field_185167_i);
        if (i == 0) {
          PotionUtils.func_185188_a(stack, PotionTypes.field_185219_B);
          func_184201_a(EntityEquipmentSlot.OFFHAND, stack);
        } else {
          PotionUtils.func_185188_a(stack, PotionTypes.field_185227_J);
          func_184201_a(EntityEquipmentSlot.OFFHAND, stack);
        } 
      } else if (func_70681_au().nextInt(20) == 0) {
        func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151041_m));
      } 
    } 
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    Biome biome = this.field_70170_p.func_180494_b(new BlockPos((Entity)this));
    if (this.field_70170_p.field_73011_w instanceof net.minecraft.world.WorldProviderHell && this.field_70146_Z.nextInt(5) != 0) {
      setSkeletonType(1);
    } else if (biome instanceof net.minecraft.world.biome.BiomeSnow && this.field_70146_Z.nextInt(5) != 0) {
      setSkeletonType(2);
    } 
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    if (func_184582_a(EntityEquipmentSlot.HEAD) == null) {
      Calendar calendar = this.field_70170_p.func_83015_S();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    return livingdata;
  }
  
  public void setCombatTask() {
    if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiRangedAttack);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      ItemStack itemstack = func_184614_ca();
      if ((itemstack != null && itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow) || itemstack.func_77973_b() == Items.field_151126_ay || itemstack.func_77973_b() == Items.field_151059_bz || itemstack.func_77973_b() == Items.field_151110_aK) {
        this.field_70714_bg.func_75776_a(4, (itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow && getIntelligence() >= 10.0F) ? (EntityAIBase)this.aiArrowAttack : (EntityAIBase)this.aiRangedAttack);
      } else {
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)this.aiAttackOnCollide);
      } 
    } 
  }
  
  public void func_82196_d(EntityLivingBase target, float p_82196_2_) {
    if (func_184614_ca().func_77973_b() instanceof ItemPEGun) {
      if (((ItemPEGun)func_184614_ca().func_77973_b()).getContainedEnergy(func_184614_ca()) > 0.0F) {
        float f = MathHelper.func_76134_b((this.field_70759_as + (180 * ((func_184638_cS() ? 1 : 2) - 1))) * 0.017453292F);
        float f1 = MathHelper.func_76126_a((this.field_70759_as + (180 * ((func_184638_cS() ? 1 : 2) - 1))) * 0.017453292F);
        ((ItemPEGun)func_184614_ca().func_77973_b()).consumeEnergy(func_184614_ca(), 1.0F);
        double d22 = 64.0D;
        Vec3d vec3 = func_70676_i(1.0F);
        double d2 = target.field_70165_t - this.field_70165_t + f * 0.35D + vec3.field_72450_a;
        double d3 = target.field_70163_u + target.func_70047_e() - 0.1D - this.field_70163_u + func_70047_e() - 0.10000000149011612D + vec3.field_72448_b;
        double d4 = target.field_70161_v - this.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
        EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
        entitywitherskull.field_70165_t = this.field_70165_t + f * 0.35D + vec3.field_72450_a;
        entitywitherskull.field_70163_u = this.field_70163_u + func_70047_e() - 0.10000000149011612D + vec3.field_72448_b;
        entitywitherskull.field_70161_v = this.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
        entitywitherskull.damage = ((ItemPEGun)func_184614_ca().func_77973_b()).getProjectileDamage(func_184614_ca());
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitywitherskull); 
        func_184185_a(SoundEvents.field_187539_bB, 0.5F, 0.5F + func_70681_au().nextFloat() * 0.25F);
        func_184185_a(ESound.pegunfire, 0.5F, 0.6F + func_70681_au().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
      } else {
        func_184185_a(ESound.pegunjam, 0.5F, 1.0F);
        func_70099_a(func_184614_ca(), 1.4F);
        func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
      } 
    } else if (func_184614_ca().func_77973_b() == Items.field_151126_ay) {
      EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.field_70170_p, (EntityLivingBase)this);
      double d0 = target.field_70163_u + target.func_70047_e() - 1.15D;
      double d1 = target.field_70165_t - this.field_70165_t;
      double d2 = d0 - entitysnowball.field_70163_u;
      double d3 = target.field_70161_v - this.field_70161_v;
      float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.func_70186_c(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entitysnowball);
      func_184609_a(EnumHand.MAIN_HAND);
      entitysnowball.damage = (target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || target instanceof net.minecraft.entity.monster.EntityBlaze) ? 3.0F : ((this.field_70146_Z.nextInt(3) == 0 || !(target instanceof EntityTameBase)) ? 1.0F : 0.0F);
    } else if (func_184614_ca().func_77973_b() == Items.field_151110_aK) {
      EntityEgg entitysnowball = new EntityEgg(this.field_70170_p, (EntityLivingBase)this);
      double d0 = target.field_70163_u + target.func_70047_e() - 1.15D;
      double d1 = target.field_70165_t - this.field_70165_t;
      double d2 = d0 - entitysnowball.field_70163_u;
      double d3 = target.field_70161_v - this.field_70161_v;
      float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.func_70186_c(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entitysnowball);
      func_184609_a(EnumHand.MAIN_HAND);
    } else if (func_184614_ca().func_77973_b() == Items.field_151059_bz) {
      double d1 = func_70631_g_() ? 0.25D : 0.5D;
      Vec3d vec3 = func_70676_i(1.0F);
      double d2 = target.field_70165_t - this.field_70165_t + vec3.field_72450_a * d1;
      double d3 = target.field_70163_u + ((target.field_70131_O > 8.0F) ? 7.0D : (target.field_70131_O * 0.5D)) - this.field_70163_u + 1.0D;
      double d4 = target.field_70161_v - this.field_70161_v + vec3.field_72449_c * d1;
      EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
      entitylargefireball.field_70165_t = this.field_70165_t + vec3.field_72450_a * d1;
      entitylargefireball.field_70163_u = this.field_70163_u + 1.0D;
      entitylargefireball.field_70161_v = this.field_70161_v + vec3.field_72449_c * d1;
      float dm = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      entitylargefireball.damage = dm;
      func_184185_a(SoundEvents.field_187557_bK, 1.0F, 1.5F);
      this.field_70170_p.func_72838_d((Entity)entitylargefireball);
      func_184609_a(EnumHand.MAIN_HAND);
    } else {
      EntityTippedArrowOther entityarrow = new EntityTippedArrowOther(this.field_70170_p, (EntityLivingBase)this);
      double d0 = target.field_70165_t - this.field_70165_t;
      double d1 = target.field_70163_u + target.func_70047_e() - this.field_70163_u + func_70047_e() - 0.10000000149011612D;
      double d2 = target.field_70161_v - this.field_70161_v;
      double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
      entityarrow.func_70186_c(d0, d1 + d3 * func_70032_d((Entity)target) * 0.013D, d2, 1.4F, isHero() ? 1.0F : 9.0F);
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_185309_u, (EntityLivingBase)this);
      int j = EnchantmentHelper.func_185284_a(Enchantments.field_185310_v, (EntityLivingBase)this);
      if (func_184187_bx() != null) {
        entityarrow.func_70239_b((p_82196_2_ * 3.0F) + this.field_70146_Z.nextGaussian() * 0.25D + 0.5D);
      } else {
        entityarrow.func_70239_b((p_82196_2_ * 1.5F) + this.field_70146_Z.nextGaussian() * 0.25D + 0.5D);
      } 
      if (target instanceof net.minecraft.entity.boss.EntityDragon && this.field_70163_u < target.field_70163_u - 10.0D)
        entityarrow.field_70181_x++; 
      if (i > 0)
        entityarrow.func_70239_b(entityarrow.func_70242_d() + i * 0.5D + 0.5D); 
      if (isHero())
        entityarrow.func_70239_b(entityarrow.func_70242_d() * 2.0D); 
      if (this.moralRaisedTimer > 200)
        entityarrow.func_70239_b(entityarrow.func_70242_d() * 1.5D); 
      if (func_184187_bx() != null) {
        j += 2;
        entityarrow.func_70243_d(true);
      } 
      if (j > 0)
        entityarrow.func_70240_a(j); 
      if (getSkeletonType() == 2)
        entityarrow.func_184558_a(new PotionEffect(MobEffects.field_76421_d, 600)); 
      if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, (EntityLivingBase)this) > 0 || getSkeletonType() == 1)
        entityarrow.func_70015_d(100); 
      if (func_184592_cb() != null && func_184592_cb().func_77973_b() == Items.field_185167_i)
        entityarrow.func_184555_a(func_184592_cb()); 
      func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entityarrow);
    } 
  }
  
  public boolean func_70027_ad() {
    return (getSkeletonType() == 1) ? false : super.func_70027_ad();
  }
  
  public int getSkeletonType() {
    return ((Integer)this.field_70180_af.func_187225_a(SKELETON_VARIANT)).intValue();
  }
  
  public void setSkeletonType(int p_82201_1_) {
    this.field_70180_af.func_187227_b(SKELETON_VARIANT, Integer.valueOf(p_82201_1_));
    this.field_70178_ae = (p_82201_1_ == 1);
    if (p_82201_1_ == 1)
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(10.0D); 
    if (p_82201_1_ == 1) {
      func_70105_a(0.6F, 2.39F);
    } else {
      func_70105_a(0.5F, 1.95F);
    } 
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    if (tagCompund.func_150297_b("SkeletonType", 99)) {
      int i = tagCompund.func_74771_c("SkeletonType");
      setSkeletonType(i);
    } 
    if (tagCompund.func_150297_b("Helmets", 99))
      this.helmetCount = tagCompund.func_74762_e("Helmets"); 
    setCombatTask();
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74774_a("SkeletonType", (byte)getSkeletonType());
    tagCompound.func_74768_a("Helmets", this.helmetCount);
  }
  
  public void func_184201_a(EntityEquipmentSlot slotIn, ItemStack stack) {
    super.func_184201_a(slotIn, stack);
    if (!this.field_70170_p.field_72995_K && slotIn == EntityEquipmentSlot.MAINHAND)
      setCombatTask(); 
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.82F) : (this.field_70131_O * 0.84F);
  }
  
  public double func_70033_W() {
    return (getSkeletonType() == 1) ? (super.func_70033_W() - 0.54D) : (super.func_70033_W() - 0.45D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_184191_r((Entity)player) && stack.func_190926_b() && player.func_70093_af() && func_184187_bx() == null) {
      List<EntitySkeletonHorse> list = this.field_70170_p.func_175647_a(EntitySkeletonHorse.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty() && !func_184207_aI())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntitySkeletonHorse entity = list.get(i1);
          if (entity != null && !entity.func_184207_aI() && !this.field_70170_p.field_72995_K) {
            entity.func_110234_j(true);
            entity.func_110219_q(true);
            entity.field_70173_aa = 0;
            func_184220_m((Entity)entity);
            func_184185_a(SoundEvents.field_187702_cm, 1.0F, 1.0F);
            break;
          } 
        }  
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151024_Q) {
      this.helmetCount++;
      func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K)
        stack.func_190918_g(1); 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.HEAD || stack.func_77973_b() == Items.field_151103_aS || stack.func_77973_b() == Item.func_150898_a(Blocks.field_185764_cQ) || stack.func_77973_b() == Items.field_151008_G) && stack.func_77973_b() != Items.field_151024_Q) {
      func_184201_a(EntityEquipmentSlot.HEAD, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.HEAD, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
      func_184201_a(EntityEquipmentSlot.CHEST, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.CHEST, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.LEGS) {
      func_184201_a(EntityEquipmentSlot.LEGS, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.LEGS, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.FEET).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.FEET) {
      func_184201_a(EntityEquipmentSlot.FEET, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.FEET, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && stack.func_77973_b() != Items.field_151057_cb && (func_184640_d(stack) == EntityEquipmentSlot.MAINHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || stack.func_77973_b() == Items.field_151031_f)) {
      func_184185_a(SoundEvents.field_187727_dV, 1.0F, 2.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.OFFHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || (stack.func_77973_b() instanceof net.minecraft.item.ItemFood && !(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold)) || stack.func_77973_b() == Items.field_185167_i || stack.func_77973_b().func_77661_b(stack) == EnumAction.BLOCK || stack.func_77973_b() == Items.field_190929_cY)) {
      func_184185_a(SoundEvents.field_187727_dV, 1.0F, 2.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.OFFHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    return false;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (func_70658_aO() > 10) ? ESound.metalHit : (EngenderConfig.mobs.useMobTalkerModels ? super.getRegularHurtSound() : ESound.woodHit);
  }
  
  protected SoundEvent getPierceHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getPierceHurtSound() : ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.woodHitCrush;
  }
}
