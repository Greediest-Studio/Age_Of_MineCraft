package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySacthoth extends EntityTameBase implements Armored, Undead {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(EntitySacthoth.class, DataSerializers.field_187191_a);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8.0D, 0);
  
  private int shadowFlameShootTimer;
  
  public EntitySacthoth(World par1World) {
    super(par1World);
    func_70105_a(1.1F, 3.8F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 12.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.field_70158_ak = true;
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 2;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186741_a(true);
    if (func_110143_aJ() > func_110138_aP() * 0.75D && this.bossInfo.func_186736_g() != BossInfo.Color.BLUE)
      this.bossInfo.func_186745_a(BossInfo.Color.BLUE); 
    if (func_110143_aJ() < func_110138_aP() * 0.75D && func_110143_aJ() > func_110138_aP() / 2.0F && this.bossInfo.func_186736_g() != BossInfo.Color.GREEN)
      this.bossInfo.func_186745_a(BossInfo.Color.GREEN); 
    if (func_110143_aJ() < func_110138_aP() / 2.0F && func_110143_aJ() > func_110138_aP() / 4.0F && this.bossInfo.func_186736_g() != BossInfo.Color.YELLOW)
      this.bossInfo.func_186745_a(BossInfo.Color.YELLOW); 
    if (func_110143_aJ() < func_110138_aP() / 4.0F && func_110143_aJ() > 0.0F && this.bossInfo.func_186736_g() != BossInfo.Color.RED)
      this.bossInfo.func_186745_a(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 0.75F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CLIMBING, new Byte((byte)0));
  }
  
  public String func_70005_c_() {
    return TextFormatting.DARK_RED + super.func_70005_c_() + TextFormatting.WHITE;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K)
      setBesideClimbableBlock(this.field_70123_F); 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.4D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(25.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(600.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(30.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(15.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (this.field_70173_aa % 20 == 0)
      func_70691_i(1.0F); 
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase) {
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 15));
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 60));
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 120));
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76419_f, 240));
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 4.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.3F;
  }
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if (!potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187594_A;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187603_D;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.sacthoth_death;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.5F : 5.0F;
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue();
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.field_70180_af.func_187227_b(CLIMBING, Byte.valueOf(b0));
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource == DamageSource.field_76368_d) {
      teleportRandomly();
      return false;
    } 
    if (par1DamageSource.func_94541_c()) {
      if (this.field_70170_p.field_72995_K && ACConfig.showBossDialogs)
        SpecialTextUtil.SacthothText(new String[] { I18n.func_74838_a("message.sacthoth.damage.explosion") }); 
      return false;
    } 
    if (par1DamageSource.func_76352_a()) {
      if (this.field_70170_p.field_72995_K && ACConfig.showBossDialogs)
        SpecialTextUtil.SacthothText(new String[] { I18n.func_74838_a("message.sacthoth.damage.projectile") }); 
      return false;
    } 
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  protected boolean teleportRandomly() {
    double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 64.0D;
    double d1 = this.field_70163_u + (this.field_70146_Z.nextInt(64) - 32);
    double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 64.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportTo(double par1, double par3, double par5) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, par1, par3, par5, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    double d3 = this.field_70165_t;
    double d4 = this.field_70163_u;
    double d5 = this.field_70161_v;
    this.field_70165_t = event.getTargetX();
    this.field_70163_u = event.getTargetY();
    this.field_70161_v = event.getTargetZ();
    boolean flag = false;
    BlockPos pos = new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    if (this.field_70170_p.func_175667_e(pos)) {
      boolean flag1 = false;
      while (!flag1 && pos.func_177956_o() > 0) {
        BlockPos pos1 = pos.func_177977_b();
        IBlockState block = this.field_70170_p.func_180495_p(pos1);
        if (block.func_185904_a().func_76230_c()) {
          flag1 = true;
          continue;
        } 
        this.field_70163_u--;
        pos = pos1;
      } 
      if (flag1) {
        func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ()))
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70107_b(d3, d4, d5);
      return false;
    } 
    short short1 = 128;
    for (int l = 0; l < short1; l++) {
      double d6 = l / (short1 - 1.0D);
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
      double d7 = d3 + (this.field_70165_t - d3) * d6 + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      double d8 = d4 + (this.field_70163_u - d4) * d6 + this.field_70146_Z.nextDouble() * this.field_70131_O;
      double d9 = d5 + (this.field_70161_v - d5) * d6 + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      if (ACConfig.particleEntity)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d7, d8, d9, f, f1, f2, new int[0]); 
    } 
    this.field_70170_p.func_184134_a(d3, d4, d5, SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F, false);
    func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
    return true;
  }
  
  protected void func_70609_aI() {
    this.deathTicks++;
    if (!this.field_70170_p.field_72995_K)
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s Sacthoth has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Sacthoth has been destroyed!", new Object[0]));
        }   
    if (this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity) {
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        if (this.deathTicks >= 190 && this.deathTicks <= 200)
          this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
      } 
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.deathTicks > 100 && this.deathTicks % 5 == 0) {
        int i = 500;
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
        } 
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.shadow_fragment, 4)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.shadow_shard, 2)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.shadow_gem)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.shard_of_oblivion)));
      } 
      if (this.deathTicks >= 150 && this.deathTicks <= 200) {
        if (this.deathTicks >= 150) {
          EntityShadowCreature shadowCreature = new EntityShadowCreature(this.field_70170_p);
          shadowCreature.func_82149_j((Entity)this);
          shadowCreature.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)shadowCreature);
        } 
        if (this.deathTicks >= 175) {
          EntityShadowMonster shadowMonster = new EntityShadowMonster(this.field_70170_p);
          shadowMonster.func_82149_j((Entity)this);
          shadowMonster.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)shadowMonster);
        } 
        if (this.deathTicks >= 195) {
          EntityShadowBeast shadowBeast = new EntityShadowBeast(this.field_70170_p);
          shadowBeast.func_82149_j((Entity)this);
          shadowBeast.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)shadowBeast);
        } 
      } 
      if (this.deathTicks == 200 && !this.field_70170_p.field_72995_K) {
        List<Entity> list = this.field_70170_p.field_72996_f;
        if (list != null)
          for (int k2 = 0; k2 < list.size(); k2++) {
            Entity entity = list.get(k2);
            if (entity instanceof EntityJzahar && entity.func_70089_S())
              SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { ((EntityJzahar)entity).func_184191_r((Entity)this) ? I18n.func_74838_a("message.jzaharhelpful.snidecomment.sacthoth") : I18n.func_74838_a("message.jzahar.snidecomment.sacthoth") }); 
          }  
        func_70106_y();
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(ACItems.sacthoths_soul_harvesting_blade)));
      } 
    } 
  }
  
  private int posneg(int num) {
    return this.field_70146_Z.nextBoolean() ? this.field_70146_Z.nextInt(num) : (-1 * this.field_70146_Z.nextInt(num));
  }
  
  protected void func_82167_n(Entity par1Entity) {
    if (this.deathTicks == 0)
      par1Entity.func_70108_f((Entity)this); 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 3);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      if (i == 2)
        passenger.func_70107_b(this.field_70165_t - (f4 * 0.75F), this.field_70163_u + (2.25F * getFittness()), this.field_70161_v - (f11 * 0.75F)); 
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t + (f4 * 0.75F), this.field_70163_u + (2.25F * getFittness()), this.field_70161_v + (f11 * 0.75F)); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t + (f11 * 0.25F), this.field_70163_u + (2.25F * getFittness()), this.field_70161_v - (f4 * 0.25F)); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 5.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
        setBesideClimbableBlock(this.field_70123_F);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
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
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.metalHitCrush;
  }
  
  public int func_70646_bf() {
    return 180;
  }
  
  public void func_70636_d() {
    if (!func_82150_aj())
      for (int i = 0; i < 3 && ACConfig.particleEntity && this.field_70170_p.field_73011_w.getDimension() != ACLib.dark_realm_id; i++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);  
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(30.0D, 30.0D, 30.0D));
    if (list != null)
      for (int k2 = 0; k2 < list.size(); k2++) {
        Entity entity = list.get(k2);
        if (entity instanceof EntityPlayer && !func_184191_r(entity) && !entity.field_70128_L && this.deathTicks == 0 && !((EntityPlayer)entity).field_71075_bZ.field_75098_d)
          ((EntityPlayer)entity).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 40)); 
      }  
    EntityPlayer player = this.field_70170_p.func_72890_a((Entity)this, 160.0D);
    if (player != null && !func_184191_r((Entity)player) && player.func_70032_d((Entity)this) >= 50.0D && !player.field_71075_bZ.field_75098_d) {
      if (player.field_70165_t - this.field_70165_t > 50.0D)
        teleportTo(player.field_70165_t + 30.0D, player.field_70163_u, player.field_70161_v); 
      if (player.field_70165_t - this.field_70165_t < -50.0D)
        teleportTo(player.field_70165_t - 30.0D, player.field_70163_u, player.field_70161_v); 
      if (player.field_70161_v - this.field_70161_v > 50.0D)
        teleportTo(player.field_70165_t, player.field_70163_u, player.field_70161_v - 30.0D); 
      if (player.field_70161_v - this.field_70161_v < -50.0D)
        teleportTo(player.field_70165_t, player.field_70163_u, player.field_70161_v + 30.0D); 
      if (player.field_70163_u - this.field_70163_u > 50.0D)
        teleportTo(player.field_70165_t, player.field_70163_u, player.field_70161_v); 
      if (player.field_70163_u - this.field_70163_u < -50.0D)
        teleportTo(player.field_70165_t, player.field_70163_u, player.field_70161_v); 
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) <= 100.0D && this.shadowFlameShootTimer <= (isHero() ? -100 : -300))
      this.shadowFlameShootTimer = 100; 
    if (func_70638_az() != null)
      func_70625_a((Entity)func_70638_az(), 10.0F, 180.0F); 
    if (this.shadowFlameShootTimer > 0) {
      this.field_70159_w *= 0.05D;
      this.field_70179_y *= 0.05D;
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
      if (this.field_70173_aa % 5 == 0)
        this.field_70170_p.func_184133_a(null, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + func_70047_e(), this.field_70161_v + 0.5D), SoundEvents.field_187557_bK, func_184176_by(), 1.5F + func_70681_au().nextFloat(), func_70681_au().nextFloat() * 0.5F + 0.3F); 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, target.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list1 != null && !list1.isEmpty())
          for (int i1 = 0; i1 < list1.size(); i1++) {
            EntityLivingBase entity = list1.get(i1);
            if (entity != null && !func_184191_r((Entity)entity) && this.field_70146_Z.nextInt(3) == 0)
              if (entity.func_70097_a((new DamageSource("shadow")).func_76348_h().func_151518_m().func_82726_p(), (float)(15.0D - func_70032_d((Entity)entity)) * 3.0F)) {
                entity.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 1600));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 1600));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 800, 1));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 800));
                entity.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 400, 1));
              } else {
                func_70652_k((Entity)entity);
                entity.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 400));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 400));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 200));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200));
                entity.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 100));
              }  
          }  
        if (target.func_70097_a(AbyssalCraftAPI.antimatter, (float)(15.0D - func_70032_d(target)) * 3.0F)) {
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 1600));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76439_r, 1600));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 800, 1));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 800));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 400, 1));
          } 
        } else {
          func_70652_k(target);
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 400));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76439_r, 400));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 200));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200));
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 100));
          } 
        } 
      } 
    } 
    this.shadowFlameShootTimer--;
    super.func_70636_d();
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    if (this.deathTicks > 0)
      par1NBTTagCompound.func_74768_a("DeathTicks", this.deathTicks); 
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    this.deathTicks = par1NBTTagCompound.func_74762_e("DeathTicks");
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    if (this.field_70170_p.func_72935_r())
      this.field_70170_p.func_72877_b(14000L); 
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 4.0D + this.field_70146_Z.nextDouble() * 16.0D;
    Vec3d srcVec = new Vec3d(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v);
    Vec3d lookVec = func_70676_i(1.0F);
    RayTraceResult raytrace = this.field_70170_p.func_72933_a(srcVec, srcVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range));
    BlockPos hitpos = (raytrace != null) ? raytrace.func_178782_a() : null;
    double rx = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70165_t - hitpos.func_177958_n()));
    double ry = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70163_u - hitpos.func_177956_o()));
    double rz = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70161_v - hitpos.func_177952_p()));
    Vec3d destVec = srcVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
    float var9 = 8.0F;
    List<Entity> possibleList = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72317_d(lookVec.field_72450_a * rx, lookVec.field_72448_b * ry, lookVec.field_72449_c * rz).func_72314_b(var9, var9, var9));
    double hitDist = 0.0D;
    for (Entity possibleEntity : possibleList) {
      if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !func_184191_r(possibleEntity)) {
        float borderSize = possibleEntity.func_70111_Y();
        AxisAlignedBB collisionBB = possibleEntity.func_174813_aQ().func_72314_b(borderSize, borderSize, borderSize);
        RayTraceResult interceptPos = collisionBB.func_72327_a(srcVec, destVec);
        if (collisionBB.func_72318_a(srcVec)) {
          if (0.0D < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = 0.0D;
          } 
          continue;
        } 
        if (interceptPos != null) {
          double possibleDist = srcVec.func_72438_d(interceptPos.field_72307_f);
          if (possibleDist < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = possibleDist;
          } 
        } 
      } 
    } 
    return pointedEntity;
  }
  
  protected void addMouthParticles() {
    if (this.field_70170_p.field_72995_K) {
      Vec3d vector = func_70040_Z();
      double px = this.field_70165_t;
      double py = this.field_70163_u + func_70047_e();
      double pz = this.field_70161_v;
      for (int i = 0; i < 45; i++) {
        double dx = vector.field_72450_a;
        double dy = vector.field_72448_b;
        double dz = vector.field_72449_c;
        double spread = 10.0D + func_70681_au().nextDouble() * 5.0D;
        double velocity = 1.0D + func_70681_au().nextDouble();
        dx += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dy += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dz += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[0]);
      } 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 23) {
      addMouthParticles();
    } else {
      super.func_70103_a(id);
    } 
  }
}
