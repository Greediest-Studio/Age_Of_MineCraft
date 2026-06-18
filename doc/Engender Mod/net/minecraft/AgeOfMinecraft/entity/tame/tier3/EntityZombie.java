package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityZombie extends EntityTameBase implements IRangedAttackMob, Undead {
  private static final UUID UUID1 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A1");
  
  private static final UUID UUID2 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A2");
  
  private static final UUID UUID3 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A3");
  
  private static final AttributeModifier attackDamageBoost1 = new AttributeModifier(UUID1, "First Boost", 1.0D, 0);
  
  private static final AttributeModifier attackDamageBoost2 = new AttributeModifier(UUID2, "Second Boost", 1.0D, 0);
  
  private static final AttributeModifier attackDamageBoost3 = new AttributeModifier(UUID3, "Third Boost", 1.0D, 0);
  
  protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (IAttribute)(new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).func_111117_a("Spawn Reinforcements Chance");
  
  private static final DataParameter<Integer> ZOMBIE_VARIANT = EntityDataManager.func_187226_a(EntityZombie.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> VILLAGER_TYPE = EntityDataManager.func_187226_a(EntityZombie.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> CONVERTING = EntityDataManager.func_187226_a(EntityZombie.class, DataSerializers.field_187198_h);
  
  private int conversionTime;
  
  private int helmetCount = 1;
  
  private final EntityAIAttackRangedBowAlly<EntityZombie> aiArrowAttack = new EntityAIAttackRangedBowAlly(this, 1.0D, 5, 15.0F);
  
  private final EntityAIAttackRangedAlly aiRangedAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  public EntityZombie(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    func_70105_a(0.5F, 1.95F);
    this.field_70728_aV = 5;
    if (worldIn != null && !worldIn.field_72995_K)
      setCombatTask(); 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(2.0D);
    func_110140_aT().func_111150_b(SPAWN_REINFORCEMENTS_CHANCE).func_111128_a(this.field_70146_Z.nextDouble() * 0.10000000149011612D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(ZOMBIE_VARIANT, Integer.valueOf(0));
    func_184212_Q().func_187214_a(VILLAGER_TYPE, Integer.valueOf(0));
    func_184212_Q().func_187214_a(CONVERTING, Boolean.valueOf(false));
  }
  
  public String getDescName() {
    if (func_70631_g_())
      return "zombie_baby"; 
    if (isVillager())
      return "zombie_villager"; 
    switch (getZombieType()) {
      case 1:
        return "zombie_husk";
      case 2:
        return "zombie_prison";
    } 
    return "zombie";
  }
  
  public int getNextLevelRequirement() {
    return (getTier() == EnumTier.TIER4) ? 100 : 25;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityZombie(this.field_70170_p);
  }
  
  public boolean canBeMatedWith() {
    return super.canBeMatedWith();
  }
  
  protected float func_70647_i() {
    return (EngenderConfig.mobs.useMobTalkerModels && (isVillager() || func_70631_g_())) ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      switch (getZombieType()) {
        case 1:
          return I18n.func_74838_a("entity.HuskHelpful.cmm.name");
        case 2:
          return I18n.func_74838_a("entity.PrisonZombieHelpful.cmm.name");
      } 
      return I18n.func_74838_a("entity." + str + ".cmm.name");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    switch (getZombieType()) {
      case 1:
        return I18n.func_74838_a("entity.HuskHelpful.name");
      case 2:
        return I18n.func_74838_a("entity.PrisonZombieHelpful.name");
    } 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return (getZombieType() != 0) ? EnumTier.TIER4 : EnumTier.TIER3;
  }
  
  public boolean isVillager() {
    return isAntiMob() ? false : ((((Integer)func_184212_Q().func_187225_a(VILLAGER_TYPE)).intValue() > 0));
  }
  
  public int getVillagerType() {
    return ((Integer)func_184212_Q().func_187225_a(VILLAGER_TYPE)).intValue() - 1;
  }
  
  public void setVillagerType(int villagerType) {
    func_184212_Q().func_187227_b(VILLAGER_TYPE, Integer.valueOf(isAntiMob() ? 0 : (villagerType + 1)));
  }
  
  public int getZombieType() {
    return ((Integer)func_184212_Q().func_187225_a(ZOMBIE_VARIANT)).intValue() - 1;
  }
  
  public void setZombieType(int villagerType) {
    func_184212_Q().func_187227_b(ZOMBIE_VARIANT, Integer.valueOf(villagerType + 1));
  }
  
  public void setToNotVillager() {
    func_184212_Q().func_187227_b(VILLAGER_TYPE, Integer.valueOf(0));
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    float f = this.field_70170_p.func_175649_E(new BlockPos((Entity)this)).func_180168_b();
    if (func_184582_a(EntityEquipmentSlot.MAINHAND) != null && (func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151033_d || func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151059_bz))
      entity.func_70015_d(12); 
    if (func_70027_ad() && this.field_70146_Z.nextFloat() < f * 0.5F)
      entity.func_70015_d(5 * (int)f); 
    if (getZombieType() == 1 && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).field_70181_x += 0.2D;
      inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.field_76438_s, 200 * (int)f, 0);
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
        inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.field_76437_t, 200, 0); 
    } 
    if (getZombieType() == 2 && entity instanceof EntityLivingBase) {
      entity.field_70159_w = 0.0D;
      entity.field_70181_x = 0.0D;
      entity.field_70179_y = 0.0D;
      inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, MobEffects.field_76421_d, 5, 1);
      if (entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
        ((EntityLiving)entity).func_70624_b(null); 
    } 
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(700);
  }
  
  protected boolean func_70610_aX() {
    return (getSpecialAttackTimer() > 600 || super.func_70610_aX());
  }
  
  public void createChild() {
    func_184185_a(isVillager() ? ESound.girlMoan : ESound.guyDeath, 1.0F, func_70647_i() + 0.1F);
    func_184185_a(func_184615_bR(), 1.0F, func_70647_i() + 0.15F);
    int i;
    for (i = 0; i < 10; i++)
      spawnHeartParticle(); 
    if (!this.field_70170_p.field_72995_K && isVillager())
      for (i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityZombie baby = new EntityZombie(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setGrowingAge(-48000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        baby.setVillagerType(getVillagerType());
        baby.setZombieType(getZombieType());
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  public void func_70636_d() {
    if (isVillager() && (getZombieType() == 1 || getZombieType() == 2 || getZombieType() == 3))
      setToNotVillager(); 
    IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111264_e);
    if (func_110143_aJ() < func_110138_aP() * 0.75D && !iattributeinstance.func_180374_a(attackDamageBoost1))
      iattributeinstance.func_111121_a(attackDamageBoost1); 
    if (func_110143_aJ() < func_110138_aP() * 0.5F && !iattributeinstance.func_180374_a(attackDamageBoost2))
      iattributeinstance.func_111121_a(attackDamageBoost2); 
    if (func_110143_aJ() < func_110138_aP() * 0.25F && !iattributeinstance.func_180374_a(attackDamageBoost3))
      iattributeinstance.func_111121_a(attackDamageBoost3); 
    if (func_110143_aJ() >= func_110138_aP() * 0.75D && iattributeinstance.func_180374_a(attackDamageBoost1))
      iattributeinstance.func_111124_b(attackDamageBoost1); 
    if (func_110143_aJ() >= func_110138_aP() * 0.5F && iattributeinstance.func_180374_a(attackDamageBoost2))
      iattributeinstance.func_111124_b(attackDamageBoost2); 
    if (func_110143_aJ() >= func_110138_aP() * 0.25F && iattributeinstance.func_180374_a(attackDamageBoost3))
      iattributeinstance.func_111124_b(attackDamageBoost3); 
    if (func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemAppleGold && isVillager() && func_70644_a(MobEffects.field_76437_t)) {
      if (this.field_70173_aa > 53)
        this.field_70173_aa = 20; 
      func_184609_a(EnumHand.MAIN_HAND);
      func_184598_c(EnumHand.MAIN_HAND);
      if (this.field_70173_aa % 2 == 0) {
        this.field_70125_A = 40.0F;
      } else {
        this.field_70125_A = 0.0F;
      } 
      if (this.field_70173_aa == 50) {
        for (int ai = 0; ai < ((ItemFood)func_184614_ca().func_77973_b()).func_150905_g(func_184614_ca()); ai++)
          spawnHeartParticle(); 
        func_70691_i(((ItemFood)func_184614_ca().func_77973_b()).func_150905_g(func_184614_ca()));
        func_184185_a(SoundEvents.field_187739_dZ, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
        startConversion(200);
      } 
    } 
    if (func_70089_S() && func_184592_cb().func_77973_b() instanceof net.minecraft.item.ItemAppleGold && isVillager() && func_70644_a(MobEffects.field_76437_t)) {
      if (this.field_70173_aa > 53)
        this.field_70173_aa = 20; 
      if (this.field_70173_aa % 2 == 0) {
        this.field_70125_A = 40.0F;
      } else {
        this.field_70125_A = 0.0F;
      } 
      func_184609_a(EnumHand.OFF_HAND);
      func_184598_c(EnumHand.OFF_HAND);
      if (this.field_70173_aa == 50) {
        spawnHeartParticle();
        func_70691_i(((ItemFood)func_184592_cb().func_77973_b()).func_150905_g(func_184592_cb()));
        func_184185_a(SoundEvents.field_187739_dZ, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
        startConversion(200);
      } 
    } 
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
    if (func_184613_cA())
      this.field_70761_aq = this.field_70177_z = -((float)MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y)) * 57.295776F; 
    if (!(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie)) {
      if (isHero() && getSpecialAttackTimer() > 600) {
        this.field_70159_w = 0.0D;
        this.field_70179_y = 0.0D;
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null)
              if (!func_184191_r((Entity)entity)) {
                entity.field_70172_ad = 0;
                inflictEngenderMobDamage(entity, " was yelled at to death by ", (new DamageSource("yell")).func_76348_h(), 0.05F);
              }  
          }  
      } 
      if (isHero() && getSpecialAttackTimer() > 600 && getSpecialAttackTimer() < 640)
        this.field_70125_A = -50.0F; 
      if (isHero() && getSpecialAttackTimer() == 600) {
        List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityTameBase entity = list.get(i1);
            if (entity != null)
              if (func_184191_r((Entity)entity)) {
                this.moralRaisedTimer = 600;
                entity.moralRaisedTimer = 600;
              }  
          }  
      } 
      if (isHero() && getSpecialAttackTimer() == 640) {
        if (func_70631_g_()) {
          func_184185_a(ESound.zombieSpecial, 10.0F, 1.5F);
        } else {
          func_184185_a(ESound.zombieSpecial, 10.0F, 1.0F);
        } 
        spawnZombieAlly();
      } 
      if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
    } 
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie || isAntiMob() || getZombieType() == 1 || func_70631_g_() || isHero()) && this.helmetCount != 0) {
      if (!this.field_70170_p.field_72995_K)
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
    if (func_70089_S() && this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && !func_70631_g_() && !func_70045_F() && !isAntiMob() && getZombieType() != 1 && !isHero()) {
      float f = func_70013_c();
      if (f > 0.5F && this.field_70173_aa % (!func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() ? 80 : 10) == 0 && this.field_70170_p.func_175678_i(new BlockPos(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v))) {
        boolean flag = true;
        ItemStack itemstack = func_184582_a(EntityEquipmentSlot.HEAD);
        if (!itemstack.func_190926_b()) {
          if (itemstack.func_77984_f()) {
            itemstack.func_77972_a(1, (EntityLivingBase)this);
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
  
  public void func_70071_h_() {
    ItemStack hats = (this.helmetCount > 0) ? new ItemStack((Item)Items.field_151024_Q, this.helmetCount) : ItemStack.field_190927_a;
    this.basicInventory.func_70299_a(7, hats);
    if (!this.field_70170_p.field_72995_K && isConverting()) {
      int i = getConversionTimeBoost();
      this.conversionTime -= i;
      if (this.conversionTime <= 0)
        convertToVillager(); 
    } 
    super.func_70071_h_();
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
  
  protected SoundEvent func_184639_G() {
    return (getZombieType() == 1) ? SoundEvents.field_190022_cI : (isVillager() ? SoundEvents.field_187940_hn : SoundEvents.field_187899_gZ);
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(isVillager() ? ESound.girlHurt : ESound.guyHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return (getZombieType() == 1) ? SoundEvents.field_190024_cK : (isVillager() ? SoundEvents.field_187944_hr : SoundEvents.field_187934_hh);
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(isVillager() ? ESound.girlDeath : ESound.guyDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return (getZombieType() == 1) ? SoundEvents.field_190023_cJ : (isVillager() ? SoundEvents.field_187943_hq : SoundEvents.field_187930_hd);
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie)
      func_184185_a(SoundEvents.field_187709_dP, 0.15F, 1.0F / getFittness()); 
    func_184185_a((getZombieType() == 1) ? SoundEvents.field_190025_cL : (isVillager() ? SoundEvents.field_187946_ht : SoundEvents.field_187939_hm), 0.15F, 1.0F / getFittness());
  }
  
  public void func_70645_a(DamageSource cause) {
    super.func_70645_a(cause);
    if (!this.field_70170_p.field_72995_K && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        func_145779_a((Item)Items.field_151024_Q, 1);
        this.helmetCount--;
      }  
    if (cause.func_76346_g() instanceof EntityCreeper && ((EntityCreeper)cause.func_76346_g()).getPowered())
      func_70099_a(new ItemStack(Items.field_151144_bL, 1, 2), 0.0F); 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    switch (getZombieType()) {
      case 1:
        return ELoot.ENTITIES_HUSK;
      case 2:
        return ELoot.ENTITIES_PRISON_ZOMBIE;
    } 
    if (isVillager())
      return ELoot.ENTITIES_ZOMBIE_VILLAGER; 
    return ELoot.ENTITIES_ZOMBIE;
  }
  
  protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
    super.func_70628_a(p_70628_1_, p_70628_2_);
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.1F : 0.05F)) {
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151040_l));
        if (func_70681_au().nextInt(3) == 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151040_l)); 
      } else {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151037_a));
        if (func_70681_au().nextInt(3) == 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151037_a)); 
      } 
    } 
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
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("ZombieType", getZombieType());
    if (isVillager()) {
      tagCompound.func_74757_a("IsVillager", true);
      tagCompound.func_74768_a("VillagerProfession", getVillagerType());
    } 
    tagCompound.func_74768_a("ConversionTime", isConverting() ? this.conversionTime : -1);
    tagCompound.func_74768_a("Helmets", this.helmetCount);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setZombieType(tagCompund.func_74762_e("ZombieType"));
    if (tagCompund.func_74767_n("IsVillager"))
      if (tagCompund.func_150297_b("VillagerProfession", 99)) {
        setVillagerType(tagCompund.func_74762_e("VillagerProfession"));
      } else {
        setVillagerType(this.field_70170_p.field_73012_v.nextInt(5));
      }  
    if (tagCompund.func_150297_b("ConversionTime", 99) && tagCompund.func_74762_e("ConversionTime") > -1)
      startConversion(tagCompund.func_74762_e("ConversionTime")); 
    if (tagCompund.func_150297_b("Helmets", 99))
      this.helmetCount = tagCompund.func_74762_e("Helmets"); 
    setCombatTask();
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    super.func_70074_a(entityLivingIn);
    if ((this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
      EntityVillager entityvillager = (EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
      entityzombie.field_70125_A = entityvillager.field_70125_A;
      entityzombie.field_70761_aq = entityzombie.field_70177_z = entityzombie.field_70759_as = entityvillager.field_70759_as;
      entityzombie.func_82149_j((Entity)entityLivingIn);
      this.field_70170_p.func_72900_e((Entity)entityLivingIn);
      entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), new GroupData(false, true));
      entityzombie.setVillagerType(entityvillager.func_70946_n());
      entityzombie.setChild(entityLivingIn.func_70631_g_());
      entityzombie.func_94061_f(entityvillager.func_175446_cd());
      if (!isWild())
        entityzombie.setOwnerId(func_184753_b()); 
      if (entityvillager.func_145818_k_())
        entityzombie.func_96094_a(entityvillager.func_95999_t()); 
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
    } 
    if (entityLivingIn instanceof EntityVillager) {
      EntityVillager entityvillager = (EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
      entityzombie.field_70125_A = entityvillager.field_70125_A;
      entityzombie.field_70761_aq = entityzombie.field_70177_z = entityzombie.field_70759_as = entityvillager.field_70759_as;
      entityzombie.func_82149_j((Entity)entityLivingIn);
      this.field_70170_p.func_72900_e((Entity)entityLivingIn);
      entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), new GroupData(false, true));
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setChild(entityLivingIn.func_70631_g_());
      entityzombie.func_94061_f(entityvillager.func_175446_cd());
      if (!isWild())
        entityzombie.setOwnerId(func_184753_b()); 
      if (entityvillager.func_145818_k_())
        entityzombie.func_96094_a(entityvillager.func_95999_t()); 
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
    } 
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((getZombieType() == 1) ? (this.field_70131_O * 0.9F) : (this.field_70131_O * 0.84F)) : ((getZombieType() == 1) ? (this.field_70131_O * 0.9F) : (this.field_70131_O * 0.87F));
  }
  
  protected boolean func_175448_a(ItemStack p_175448_1_) {
    return (p_175448_1_.func_77973_b() == Items.field_151110_aK && func_70631_g_() && func_184218_aH()) ? false : super.func_175448_a(p_175448_1_);
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    float f = difficulty.func_180170_c();
    if (livingdata == null)
      livingdata = new GroupData((this.field_70170_p.field_73012_v.nextFloat() < ForgeModContainer.zombieBabyChance), (this.field_70170_p.field_73012_v.nextFloat() < 0.05F)); 
    if (livingdata instanceof GroupData) {
      GroupData groupdata = (GroupData)livingdata;
      Biome biome = this.field_70170_p.func_180494_b(new BlockPos((Entity)this));
      if (biome instanceof net.minecraft.world.biome.BiomeDesert && this.field_70146_Z.nextInt(5) != 0) {
        setZombieType(1);
        setToNotVillager();
      } 
      if (groupdata.isVillager)
        setVillagerType(this.field_70146_Z.nextInt(5)); 
      if (groupdata.isChild) {
        setChild(true);
        setGrowingAge(-60000);
        if (this.field_70170_p.field_73012_v.nextFloat() < 0.05D) {
          List<EntityChicken> list = this.field_70170_p.func_175647_a(EntityChicken.class, func_174813_aQ().func_72314_b(5.0D, 3.0D, 5.0D), EntitySelectors.field_152785_b);
          if (!list.isEmpty()) {
            EntityChicken entitychicken = list.get(0);
            entitychicken.setChickenJockey(true);
            func_184220_m((Entity)entitychicken);
            entitychicken.setOwnerId(func_184753_b());
          } 
        } else if (this.field_70170_p.field_73012_v.nextFloat() < 0.05D) {
          EntityChicken entitychicken1 = new EntityChicken(this.field_70170_p);
          entitychicken1.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
          entitychicken1.func_180482_a(difficulty, (IEntityLivingData)null);
          entitychicken1.setChickenJockey(true);
          entitychicken1.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)entitychicken1);
          func_184220_m((Entity)entitychicken1);
        } 
      } 
    } 
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    if (func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
      Calendar calendar = this.field_70170_p.func_83015_S();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    double d0 = this.field_70146_Z.nextDouble() * 1.5D * f;
    if (d0 > 1.0D)
      func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random zombie-spawn bonus", d0, 2)); 
    if (this.field_70146_Z.nextFloat() < f * 0.05F) {
      func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 0.25D + 0.5D, 0));
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 3.0D + 1.0D, 2));
    } 
    return livingdata;
  }
  
  public void becomeAHero() {
    super.becomeAHero();
    func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 0.25D + 0.5D, 0));
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 3.0D + 1.0D, 2));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_184191_r((Entity)player) && func_70631_g_() && stack.func_190926_b() && player.func_70093_af() && func_184187_bx() == null) {
      List<EntityChicken> list = this.field_70170_p.func_175647_a(EntityChicken.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty() && !func_184207_aI())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityChicken entity = list.get(i1);
          if (entity != null && !entity.func_184207_aI() && func_184191_r((Entity)entity) && func_70631_g_() && !this.field_70170_p.field_72995_K) {
            entity.field_70173_aa = 0;
            func_184220_m((Entity)entity);
            func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
            break;
          } 
        }  
      return true;
    } 
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && !stack.func_190926_b() && stack.func_77973_b() == Items.field_151141_av && func_184187_bx() == null && (hasOwner(player) || player.func_184191_r((Entity)this))) {
      func_184185_a(SoundEvents.field_187706_dO, 0.5F, 1.0F);
      player.func_184220_m((Entity)this);
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
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.OFFHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || (stack.func_77973_b() instanceof ItemFood && (!(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold) || (stack.func_77973_b() == Items.field_151153_ao && stack.func_77960_j() == 0 && isVillager() && func_70644_a(MobEffects.field_76437_t)))) || stack.func_77973_b() == Items.field_185167_i || stack.func_77973_b().func_77661_b(stack) == EnumAction.BLOCK || stack.func_77973_b() == Items.field_190929_cY)) {
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
  
  protected void startConversion(int ticks) {
    this.conversionTime = ticks;
    func_184212_Q().func_187227_b(CONVERTING, Boolean.valueOf(true));
    func_184589_d(MobEffects.field_76437_t);
    func_70690_d(new PotionEffect(MobEffects.field_76420_g, ticks, Math.min(this.field_70170_p.func_175659_aa().func_151525_a() - 1, 0)));
    this.field_70170_p.func_72960_a((Entity)this, (byte)16);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 16) {
      if (!func_174814_R())
        this.field_70170_p.func_184134_a(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, SoundEvents.field_187942_hp, func_184176_by(), 1.0F + this.field_70146_Z.nextFloat(), this.field_70146_Z.nextFloat() * 0.7F + 0.3F, false); 
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (super.func_70097_a(source, amount)) {
      EntityLivingBase entitylivingbase = func_70638_az();
      if (entitylivingbase == null && source.func_76346_g() instanceof EntityLivingBase)
        entitylivingbase = (EntityLivingBase)source.func_76346_g(); 
      if (entitylivingbase != null && isHero())
        spawnZombieAlly(); 
      return true;
    } 
    return false;
  }
  
  public void spawnZombieAlly() {
    int i = MathHelper.func_76128_c(this.field_70165_t);
    int j = MathHelper.func_76128_c(this.field_70163_u);
    int k = MathHelper.func_76128_c(this.field_70161_v);
    if (this.field_70146_Z.nextFloat() < func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111126_e() && this.field_70170_p.func_82736_K().func_82766_b("doMobSpawning"))
      for (int l = 0; l < 50; l++) {
        int i1 = i + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int j1 = j + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int k1 = k + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        if (this.field_70170_p.func_180495_p(new BlockPos(i1, j1 - 1, k1)).isSideSolid((IBlockAccess)this.field_70170_p, new BlockPos(i1, j1 - 1, k1), EnumFacing.UP) && this.field_70170_p.func_175671_l(new BlockPos(i1, j1, k1)) < 10) {
          EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
          entityzombie.func_70107_b(i1, j1, k1);
          if (!this.field_70170_p.func_175636_b(i1, j1, k1, 7.0D) && this.field_70170_p.func_72917_a(entityzombie.func_174813_aQ(), (Entity)entityzombie) && this.field_70170_p.func_184144_a((Entity)entityzombie, entityzombie.func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(entityzombie.func_174813_aQ())) {
            this.field_70170_p.func_72838_d((Entity)entityzombie);
            entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
            entityzombie.setZombieType(getZombieType());
            entityzombie.setOwnerId(func_184753_b());
            entityzombie.setIsAntiMob(isAntiMob());
            entityzombie.setGrowingAge(getGrowingAge());
            if (isVillager() && this.field_70146_Z.nextFloat() < 0.25F)
              entityzombie.setVillagerType(this.field_70146_Z.nextInt(5)); 
            func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111128_a(func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111125_b() - 0.05D);
            entityzombie.func_110148_a(SPAWN_REINFORCEMENTS_CHANCE).func_111128_a(0.0D);
            break;
          } 
        } 
      }  
  }
  
  public boolean isConverting() {
    return ((Boolean)func_184212_Q().func_187225_a(CONVERTING)).booleanValue();
  }
  
  protected void convertToVillager() {
    EntityVillager entityvillager = new EntityVillager(this.field_70170_p);
    entityvillager.func_82149_j((Entity)this);
    entityvillager.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
    entityvillager.field_70761_aq = entityvillager.field_70177_z = entityvillager.field_70759_as = this.field_70759_as;
    entityvillager.field_70125_A = this.field_70125_A;
    entityvillager.func_94061_f(func_175446_cd());
    entityvillager.setProfession(getVillagerType());
    entityvillager.setOwnerId(func_184753_b());
    if (func_145818_k_())
      entityvillager.func_96094_a(func_95999_t()); 
    if (!this.field_70170_p.field_72995_K)
      if (this.helmetCount > 0)
        func_145779_a((Item)Items.field_151024_Q, this.helmetCount);  
    this.field_70170_p.func_72838_d((Entity)entityvillager);
    entityvillager.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0));
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1027, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
    this.field_70170_p.func_72900_e((Entity)this);
  }
  
  protected int getConversionTimeBoost() {
    int i = 1;
    if (this.field_70146_Z.nextFloat() < 0.01F) {
      int j = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      for (int k = (int)this.field_70165_t - 4; k < (int)this.field_70165_t + 4 && j < 14; k++) {
        for (int l = (int)this.field_70163_u - 4; l < (int)this.field_70163_u + 4 && j < 14; l++) {
          for (int i1 = (int)this.field_70161_v - 4; i1 < (int)this.field_70161_v + 4 && j < 14; i1++) {
            Block block = this.field_70170_p.func_180495_p((BlockPos)blockpos$mutableblockpos.func_181079_c(k, l, i1)).func_177230_c();
            if (block == Blocks.field_150411_aY || block == Blocks.field_150324_C) {
              if (this.field_70146_Z.nextFloat() < 0.3F)
                i++; 
              j++;
            } 
          } 
        } 
      } 
    } 
    return i;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (func_70658_aO() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      EntityMutantZombie mutant = new EntityMutantZombie(this.field_70170_p);
      mutant.setZombieType(getZombieType());
      return (EntityTameBase)mutant;
    } 
    return null;
  }
  
  public class GroupData implements IEntityLivingData {
    public boolean isChild;
    
    public boolean isVillager;
    
    public GroupData(boolean isBaby, boolean isVillagerZombie) {
      this.isChild = false;
      this.isVillager = false;
      this.isChild = isBaby;
      this.isVillager = isVillagerZombie;
    }
  }
  
  public void setCombatTask() {
    if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiRangedAttack);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      ItemStack itemstack = func_184614_ca();
      if ((itemstack != null && itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow) || itemstack.func_77973_b() == Items.field_151126_ay || itemstack.func_77973_b() == Items.field_151059_bz || itemstack.func_77973_b() == Items.field_151110_aK) {
        this.field_70714_bg.func_75776_a(4, (itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow && getIntelligence() >= 50.0F) ? (EntityAIBase)this.aiArrowAttack : (EntityAIBase)this.aiRangedAttack);
      } else {
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)this.aiAttackOnCollide);
      } 
    } 
  }
  
  public void func_184201_a(EntityEquipmentSlot slotIn, ItemStack stack) {
    super.func_184201_a(slotIn, stack);
    if (!this.field_70170_p.field_72995_K && slotIn == EntityEquipmentSlot.MAINHAND)
      setCombatTask(); 
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
      entityarrow.func_70186_c(d0, d1 + d3 * func_70032_d((Entity)target) * 0.013D, d2, 1.4F, (getIntelligence() < 50.0F) ? (29.0F - getIntelligence() / 5.0F) : (isHero() ? 1.0F : 9.0F));
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
      if (getZombieType() == 2)
        entityarrow.func_184558_a(new PotionEffect(MobEffects.field_76421_d, 600)); 
      if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, (EntityLivingBase)this) > 0)
        entityarrow.func_70015_d(100); 
      if (func_184592_cb() != null && func_184592_cb().func_77973_b() == Items.field_185167_i)
        entityarrow.func_184555_a(func_184592_cb()); 
      func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entityarrow);
    } 
  }
}
