package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.pathfinding.PathNodeType;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntityIcyEnderCreeper extends EntityTameBase implements IJumpingMount, Armored, Ender {
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.1D, 0)).func_111168_a(false);
  
  private int field_184720_bx = 0;
  
  private int field_184721_by = 0;
  
  protected float jumpPower;
  
  public EntityIcyEnderCreeper(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.5F, 2.75F);
    } else {
      func_70105_a(0.5F, 2.875F);
    } 
    this.field_70138_W = 1.0F;
    this.isOffensive = true;
    func_184644_a(PathNodeType.WATER, -1.0F);
    func_184644_a(PathNodeType.DANGER_FIRE, -1.0F);
    func_184644_a(PathNodeType.DANGER_CACTUS, -1.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.5D, 48.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.5D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 10;
  }
  
  public String getDescName() {
    return "icy_ender_creeper";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityIcyEnderCreeper(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(10.0D);
  }
  
  public int timesToConvert() {
    return 27;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 2.5F;
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
  
  public void func_70624_b(EntityLivingBase entitylivingbaseIn) {
    super.func_70624_b(entitylivingbaseIn);
    IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
    if (entitylivingbaseIn == null) {
      this.field_184721_by = 0;
      iattributeinstance.func_111124_b(attackingSpeedBoostModifier);
    } else {
      this.field_184721_by = this.field_70173_aa;
      if (!iattributeinstance.func_180374_a(attackingSpeedBoostModifier))
        iattributeinstance.func_111121_a(attackingSpeedBoostModifier); 
    } 
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.ENDER;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(10); i++) {
        EntityIcyEnderCreeper baby = new EntityIcyEnderCreeper(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
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
  
  public void func_184716_o() {
    if (this.field_70173_aa >= this.field_184720_bx + 400) {
      this.field_184720_bx = this.field_70173_aa;
      if (!func_174814_R())
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, SoundEvents.field_187533_aW, func_184176_by(), func_70093_af() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F, false); 
    } 
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (isArmsRaised() && this.field_70170_p.field_72995_K)
      func_184716_o(); 
    super.func_184206_a(key);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.88F) : (this.field_70131_O * 0.89F);
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity != null)
          if (!func_184191_r((Entity)entity)) {
            teleportToEntity((Entity)entity);
            func_70652_k((Entity)entity);
          }  
      }  
    setSpecialAttackTimer(1200);
  }
  
  public void func_70636_d() {
    if (func_70638_az() != null && func_70638_az().func_70089_S() && func_70068_e((Entity)func_70638_az()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (func_70026_G() && this.field_70172_ad <= 10)
      func_70097_a((new DamageSource("onFire")).func_76348_h().func_151518_m().func_76351_m(), 2.0F); 
    if (func_70638_az() != null) {
      if (this.field_70173_aa % 400 == 0)
        func_184185_a(SoundEvents.field_187533_aW, func_70093_af() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F); 
      if ((func_70638_az()).field_70131_O <= 2.5F && func_70638_az().func_184222_aU() && func_70638_az() instanceof EntityLiving && !(func_70638_az() instanceof EntityTameBase)) {
        ((EntityLiving)func_70638_az()).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 60, 9));
        ((EntityLiving)func_70638_az()).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 60, 0));
        ((EntityLiving)func_70638_az()).func_70671_ap().func_75650_a((func_70638_az()).field_70165_t + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, (func_70638_az()).field_70163_u + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, (func_70638_az()).field_70161_v + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, 180.0F, 180.0F);
        ((EntityLiving)func_70638_az()).func_70624_b((EntityLivingBase)null);
        (func_70638_az()).field_70761_aq = (func_70638_az()).field_70177_z = (func_70638_az()).field_70759_as;
      } 
    } 
    if (this.field_70170_p.field_72995_K) {
      this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
      this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, new int[0]);
      for (int i = 0; i < 2; i++) {
        double d0 = (float)this.field_70165_t - 0.5D + this.field_70146_Z.nextFloat();
        double d1 = ((float)this.field_70163_u + this.field_70146_Z.nextFloat());
        double d2 = (float)this.field_70161_v - 0.5D + this.field_70146_Z.nextFloat();
        double d3 = (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D;
        double d4 = -this.field_70146_Z.nextDouble();
        double d5 = (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D;
        d1 += this.field_70146_Z.nextDouble() * this.field_70131_O;
        this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
      } 
    } 
    if (!func_184207_aI() && getGuardBlock() == null && getOwner() != null && (func_70068_e((Entity)getOwner()) > 4096.0D || !func_70685_l((Entity)getOwner())) && !this.field_70170_p.field_72995_K) {
      func_70624_b((EntityLivingBase)null);
      func_70661_as().func_75499_g();
      teleportTo((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v);
    } 
    if (!func_184207_aI() && func_70643_av() != null && func_70681_au().nextInt(20) == 0) {
      if (func_70643_av().func_70068_e((Entity)this) < 2.0D && !this.field_70170_p.field_72995_K)
        teleportRandomly(); 
      if (func_70643_av().func_70068_e((Entity)this) > 128.0D && !this.field_70170_p.field_72995_K)
        teleportToEntity((Entity)func_70643_av()); 
    } 
    super.func_70636_d();
  }
  
  protected void func_70619_bc() {
    if (func_70026_G()) {
      func_70015_d(10);
      teleportRandomly();
    } 
    super.func_70619_bc();
  }
  
  protected boolean teleportRandomly() {
    if (this.field_70170_p.func_72935_r() || func_70026_G())
      func_70642_aH(); 
    double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    double d1 = this.field_70163_u + (this.field_70146_Z.nextInt(64) - 32);
    double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    Vec3d vec3d = new Vec3d(this.field_70165_t - p_70816_1_.field_70165_t, (func_174813_aQ()).field_72338_b + (this.field_70131_O / 2.0F) - p_70816_1_.field_70163_u + p_70816_1_.func_70047_e(), this.field_70161_v - p_70816_1_.field_70161_v);
    vec3d = vec3d.func_72432_b();
    double d0 = 16.0D;
    double d1 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72450_a * d0;
    double d2 = this.field_70163_u + (this.field_70146_Z.nextInt(16) - 8) - vec3d.field_72448_b * d0;
    double d3 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72449_c * d0;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    boolean flag = (func_184595_k(event.getTargetX(), event.getTargetY(), event.getTargetZ()) && !isInLove() && !func_184218_aH());
    if (flag) {
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextFloat() < 0.01F) {
        EntityEndermite entityendermite = new EntityEndermite(this.field_70170_p);
        entityendermite.setOwnerId(func_184753_b());
        entityendermite.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
        this.field_70170_p.func_72838_d((Entity)entityendermite);
      } 
    } 
    return flag;
  }
  
  public boolean func_184595_k(double x, double y, double z) {
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.field_70170_p;
    Random random = func_70681_au();
    if (world.func_175667_e(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.func_177956_o() > 0) {
        BlockPos blockpos1 = blockpos.func_177977_b();
        IBlockState iblockstate = world.func_180495_p(blockpos1);
        if (iblockstate.func_185904_a().func_76230_c()) {
          flag1 = true;
          continue;
        } 
        this.field_70163_u--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (func_184207_aI())
          func_184179_bs().func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v); 
        if (world.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !world.func_72953_d(func_174813_aQ()))
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70634_a(d0, d1, d2);
      return false;
    } 
    int i = 128;
    for (int j = 0; j < 128; j++) {
      double d6 = j / 127.0D;
      float f = (random.nextFloat() - 0.5F) * 0.2F;
      float f1 = (random.nextFloat() - 0.5F) * 0.2F;
      float f2 = (random.nextFloat() - 0.5F) * 0.2F;
      double d3 = d0 + (this.field_70165_t - d0) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      double d4 = d1 + (this.field_70163_u - d1) * d6 + random.nextDouble() * this.field_70131_O;
      double d5 = d2 + (this.field_70161_v - d2) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      world.func_175688_a(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
    } 
    if (this instanceof EntityCreature)
      func_70661_as().func_75499_g(); 
    return true;
  }
  
  protected SoundEvent func_184639_G() {
    return isArmsRaised() ? SoundEvents.field_187532_aV : SoundEvents.field_187529_aS;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187531_aU;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187530_aT;
  }
  
  protected Item func_146068_u() {
    return Items.field_151079_bi;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null && !isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K) {
      player.func_184220_m((Entity)this);
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151061_bv && (hasOwner(player) || player.func_184191_r((Entity)this))) {
      List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty() && !func_184207_aI())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null)
            if (func_184191_r((Entity)entity)) {
              this.field_70170_p.func_184133_a((EntityPlayer)null, entity.func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
              entity.func_184204_a(1);
            }  
        }  
      this.field_70170_p.func_184133_a((EntityPlayer)null, func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      this.field_70170_p.func_184133_a((EntityPlayer)null, player.func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      func_184204_a(1);
      player.func_184204_a(1);
      return true;
    } 
    return false;
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      double d8 = 0.5D;
      Vec3d vec3 = func_70676_i(1.0F);
      double dx = vec3.field_72450_a * d8;
      double dz = vec3.field_72449_c * d8;
      passenger.func_70107_b(this.field_70165_t + dx, this.field_70163_u + 0.25D, this.field_70161_v + dz);
    } 
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
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = this.field_70759_as = entitylivingbase.field_70177_z;
      this.field_70125_A = 0.0F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * 1.5F);
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      this.field_70747_aH = 0.02F;
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-0.4F * f * this.jumpPower);
          this.field_70179_y += (0.4F * f1 * this.jumpPower);
        } 
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
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ICY_ENDER_CREEPER;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    Entity entity = source.func_76346_g();
    setSitResting(false);
    if (func_70089_S() && (func_180431_b(source) || source.func_76346_g() instanceof net.minecraft.entity.monster.EntityCreeper || source instanceof net.minecraft.util.EntityDamageSourceIndirect || source.func_94541_c() || source.func_76352_a())) {
      if (teleportRandomly())
        return true; 
      return false;
    } 
    boolean flag = super.func_70097_a(source, amount);
    if (source.func_76363_c())
      teleportRandomly(); 
    return flag;
  }
}
