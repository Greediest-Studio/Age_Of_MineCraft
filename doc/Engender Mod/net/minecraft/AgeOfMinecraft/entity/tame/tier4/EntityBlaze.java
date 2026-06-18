package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlaze extends EntityTameBase implements IJumpingMount, Light, Flying, Elemental {
  private float heightOffset = 0.5F;
  
  private int heightOffsetUpdateTime;
  
  private static final DataParameter<Byte> ON_FIRE = EntityDataManager.func_187226_a(EntityBlaze.class, DataSerializers.field_187191_a);
  
  protected float jumpPower;
  
  public EntityBlaze(World worldIn) {
    super(worldIn);
    func_184644_a(PathNodeType.WATER, -1.0F);
    this.isOffensive = true;
    this.field_70178_ae = true;
    this.field_70728_aV = 10;
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(4, new AIFireballAttack(this));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIWanderAvoidWaterFlying((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70765_h = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.5F, 1.95F);
    } else {
      func_70105_a(0.6F, 1.8F);
    } 
  }
  
  public String getDescName() {
    return "blaze";
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying(this, (EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return (PathNavigate)pathnavigateflying;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
    func_110148_a(SharedMonsterAttributes.field_193334_e).func_111128_a(0.75D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(48.0D);
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityBlaze(this.field_70170_p);
  }
  
  public float getBonusVSLight() {
    return 0.8F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ON_FIRE, Byte.valueOf((byte)0));
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187594_A;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() - 0.1F); 
    return SoundEvents.field_187603_D;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() - 0.1F); 
    return SoundEvents.field_187600_C;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
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
        EntityBlaze baby = new EntityBlaze(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        baby.setGrowingAge(-72000);
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
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(800);
  }
  
  public void func_70636_d() {
    if (func_70026_G()) {
      func_184185_a(SoundEvents.field_187658_bx, 1.0F, 1.0F);
      func_70097_a((new DamageSource("cooler")).func_76348_h().func_151518_m().func_76351_m(), 4.0F);
      func_70605_aq().func_75642_a(this.field_70165_t, this.field_70163_u + 3.0D, this.field_70161_v, 1.0D);
    } 
    if (isHero() && getSpecialAttackTimer() > 790) {
      this.field_70159_w = 0.0D;
      this.field_70179_y = 0.0D;
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity)) {
              entity.func_70015_d(60);
              entity.field_70172_ad = 0;
              inflictEngenderMobDamage(entity, " was scorched to death by ", (new DamageSource("burn")).func_76348_h().func_76361_j(), 1.0F);
            }  
        }  
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 256.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!this.field_70122_E && this.field_70181_x < 0.0D && func_70089_S())
      this.field_70181_x *= 0.6D; 
    if (this.field_70170_p.field_72995_K) {
      if (isHero() && getSpecialAttackTimer() > 790)
        for (int i = 0; i < 3000; i++) {
          this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FLAME, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, new int[0]);
        }  
      if (!func_70026_G() && func_70089_S() && getIllusionFormTime() <= 0) {
        if (this.field_70146_Z.nextInt(24) == 0 && !func_174814_R())
          this.field_70170_p.func_184134_a(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, SoundEvents.field_187597_B, func_184176_by(), func_70599_aP(), this.field_70146_Z.nextFloat() * 0.7F + 0.3F, false); 
        for (int i = 0; i < 2; i++) {
          if (func_70093_af() || func_70631_g_()) {
            this.field_70170_p.func_175688_a(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          } else {
            this.field_70170_p.func_175688_a(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          } 
        } 
      } 
    } 
    super.func_70636_d();
  }
  
  protected void func_70619_bc() {
    this.heightOffsetUpdateTime--;
    if (this.heightOffsetUpdateTime <= 0) {
      this.heightOffsetUpdateTime = 100;
      this.heightOffset = this.field_70146_Z.nextFloat() * 6.0F;
    } 
    EntityLivingBase entitylivingbase = func_70638_az();
    if (entitylivingbase != null && this.field_70163_u + func_70047_e() - this.heightOffset < entitylivingbase.field_70163_u + entitylivingbase.func_70047_e()) {
      this.field_70181_x += (0.30000001192092896D - this.field_70181_x) * 0.30000001192092896D;
      this.field_70160_al = true;
    } 
    super.func_70619_bc();
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected boolean func_70041_e_() {
    return EngenderConfig.mobs.useMobTalkerModels;
  }
  
  public boolean func_70027_ad() {
    return func_70845_n();
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_BLAZE;
  }
  
  public boolean func_70845_n() {
    return ((((Byte)this.field_70180_af.func_187225_a(ON_FIRE)).byteValue() & 0x1) != 0);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && stack.func_77973_b() != Items.field_151063_bx && (func_184640_d(stack) == EntityEquipmentSlot.MAINHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || stack.func_77973_b() == Items.field_151031_f)) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.OFFHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || (stack.func_77973_b() instanceof net.minecraft.item.ItemFood && !(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold)) || stack.func_77973_b() == Items.field_185167_i || stack.func_77973_b() == Items.field_185159_cQ)) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.HEAD) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.LEGS) {
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
    if (func_184191_r((Entity)player) && stack.func_190926_b() && player.func_70093_af()) {
      dropEquipmentUndamaged();
      player.func_184609_a(hand);
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI() && func_82171_bF()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A - 5.0F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.03F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } 
      this.field_184618_aE = this.field_70721_aZ;
      if (((EntityLivingBase)func_184179_bs()).field_70702_br != 0.0F && this.field_70173_aa % 40 == 0 && !this.field_70170_p.field_72995_K) {
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1018, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
        Vec3d vec3 = func_70676_i(1.0F);
        EntitySmallFireballOther entitysmallfireball = new EntitySmallFireballOther(this.field_70170_p, (EntityLivingBase)this, this.field_70165_t + vec3.field_72450_a * 16.0D - this.field_70165_t + vec3.field_72450_a, this.field_70163_u + func_70047_e() + vec3.field_72448_b * 16.0D - this.field_70163_u + func_70047_e() + vec3.field_72448_b, this.field_70161_v + vec3.field_72449_c * 16.0D - this.field_70161_v + vec3.field_72449_c);
        entitysmallfireball.field_70163_u = this.field_70163_u + (this.field_70131_O / 2.0F) + 0.5D;
        this.field_70170_p.func_72838_d((Entity)entitysmallfireball);
        func_184609_a(EnumHand.MAIN_HAND);
        float dm = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
        entitysmallfireball.damage = dm * 0.6F;
      } 
      for (int i = 0; i < 64; i++) {
        int in = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70163_u - this.field_70146_Z.nextDouble() * (4.0D + this.heightOffset));
        int k = MathHelper.func_76128_c(this.field_70161_v);
        BlockPos blockpos = new BlockPos(in, j, k);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        if (block != Blocks.field_150350_a)
          func_70107_b(this.field_70165_t, this.field_70163_u + 0.005D, this.field_70161_v); 
      } 
      if (this.jumpPower > 0.0F) {
        this.field_70181_x += this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.jumpPower = 0.0F;
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      this.field_70747_aH = 0.02F;
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void setOnFire(boolean onFire) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(ON_FIRE)).byteValue();
    if (onFire && !func_70026_G()) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.field_70180_af.func_187227_b(ON_FIRE, Byte.valueOf(b0));
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (amount == 0.0F && source.func_76355_l() == "thrown" && source instanceof EntityDamageSourceIndirect && ((EntityDamageSourceIndirect)source).func_76346_g() instanceof net.minecraft.entity.projectile.EntitySnowball)
      amount = 3.0F; 
    return super.func_70097_a(source, amount);
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getRegularHurtSound() : ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getPierceHurtSound() : ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.metalHitCrush;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.15F) : super.func_70647_i();
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityBlaze blaze;
    
    private int attackStep;
    
    private int attackTime;
    
    public AIFireballAttack(EntityBlaze blazeIn) {
      this.blaze = blazeIn;
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.blaze.func_70638_az();
      return (entitylivingbase != null && entitylivingbase.func_70089_S());
    }
    
    public void func_75249_e() {
      this.attackStep = 0;
    }
    
    public void func_75251_c() {
      this.blaze.setOnFire(false);
    }
    
    public void func_75246_d() {
      this.attackTime--;
      EntityLivingBase entitylivingbase = this.blaze.func_70638_az();
      this.blaze.func_70671_ap().func_75651_a((Entity)entitylivingbase, this.blaze.func_184649_cE(), this.blaze.func_70646_bf());
      double d0 = this.blaze.func_70032_d((Entity)entitylivingbase);
      if (d0 < 3.0D) {
        if (this.attackTime <= 0) {
          this.attackTime = 20;
          this.blaze.func_70652_k((Entity)entitylivingbase);
        } 
        this.blaze.func_70605_aq().func_75642_a(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v, 1.0D);
      } else if (d0 < getFollowDistance()) {
        double d1 = entitylivingbase.field_70165_t - this.blaze.field_70165_t;
        double d2 = (entitylivingbase.func_174813_aQ()).field_72338_b + (entitylivingbase.field_70131_O / 2.0F) - this.blaze.field_70163_u + (this.blaze.field_70131_O / 2.0F);
        double d3 = entitylivingbase.field_70161_v - this.blaze.field_70161_v;
        if (this.attackTime <= 0) {
          this.attackStep++;
          if (this.attackStep == 1) {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 30 : 60;
            this.blaze.setOnFire(true);
          } else if (this.attackStep <= 4) {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 3 : 6;
          } else {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 50 : 100;
            this.attackStep = 0;
            this.blaze.setOnFire(false);
          } 
          if (this.attackStep > 1) {
            float f = MathHelper.func_76129_c(MathHelper.func_76133_a(d0)) * 0.5F;
            this.blaze.field_70170_p.func_180498_a((EntityPlayer)null, 1018, new BlockPos((int)this.blaze.field_70165_t, (int)this.blaze.field_70163_u, (int)this.blaze.field_70161_v), 0);
            for (int i = 0; i < 1; i++) {
              EntitySmallFireballOther entitysmallfireball = new EntitySmallFireballOther(this.blaze.field_70170_p, (EntityLivingBase)this.blaze, d1 + (this.blaze.isHero() ? 0.0D : (this.blaze.func_70681_au().nextGaussian() * f)), d2, d3 + (this.blaze.isHero() ? 0.0D : (this.blaze.func_70681_au().nextGaussian() * f)));
              entitysmallfireball.field_70163_u = this.blaze.field_70163_u + (this.blaze.field_70131_O / 2.0F) + 0.5D;
              this.blaze.field_70170_p.func_72838_d((Entity)entitysmallfireball);
            } 
          } 
        } 
      } else {
        this.blaze.func_70661_as().func_75499_g();
        this.blaze.func_70605_aq().func_75642_a(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v, 1.0D);
      } 
      super.func_75246_d();
    }
    
    private double getFollowDistance() {
      IAttributeInstance iattributeinstance = this.blaze.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return (iattributeinstance == null) ? 16.0D : iattributeinstance.func_111126_e();
    }
  }
}
