package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCommandBlockWither extends EntityTameBase implements IRangedAttackMob, Massive, Armored, Flying, Undead {
  private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.func_187226_a(EntityCommandBlockWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> SIZE = EntityDataManager.func_187226_a(EntityCommandBlockWither.class, DataSerializers.field_187192_b);
  
  private float[] field_82220_d = new float[2];
  
  private float[] field_82221_e = new float[2];
  
  private float[] field_82217_f = new float[2];
  
  private float[] field_82218_g = new float[2];
  
  private int[] field_82223_h = new int[2];
  
  private int[] field_82224_i = new int[2];
  
  private int blockBreakCounter;
  
  public EntityCommandBlockWither(World worldIn) {
    super(worldIn);
    func_70606_j(func_110138_aP());
    func_70105_a(1.0F, 3.3F);
    this.isOffensive = true;
    this.field_70178_ae = true;
    ((PathNavigateGround)func_70661_as()).func_179693_d(true);
    this.field_70714_bg.func_75776_a(0, new AIDoNothing());
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 3.0D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.0D, 60, 32.0F));
    this.field_70728_aV = 500;
    setLevel(300);
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "wither_notstorm";
  }
  
  public int playMusic() {
    return 11;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    if (getSize() >= 12250 && this.field_70173_aa % 2 == 0) {
      this.bossInfo.func_186745_a(BossInfo.Color.PURPLE);
    } else {
      this.bossInfo.func_186745_a(BossInfo.Color.RED);
    } 
    this.bossInfo.func_186741_a(true);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.WITHER_STORM;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    return "Wither (Wither Storm)";
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 24.0F + this.field_70146_Z.nextFloat() * 32.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 48.0F + this.field_70146_Z.nextFloat() * 32.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 48.0F + this.field_70146_Z.nextFloat() * 32.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(INVULNERABILITY_TIME, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(SIZE, Integer.valueOf(0));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Invul", getInvulTime());
    tagCompound.func_74768_a("Growth", getSize());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setInvulTime(tagCompund.func_74762_e("Invul"));
    Grow(tagCompund.func_74762_e("Growth"));
  }
  
  public int getSize() {
    return ((Integer)this.field_70180_af.func_187225_a(SIZE)).intValue();
  }
  
  public void Grow(int p_82215_1_) {
    this.field_70180_af.func_187227_b(SIZE, Integer.valueOf(p_82215_1_));
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return (getSize() >= 5000) ? ESound.commandBlockWitherIdle : SoundEvents.field_187925_gy;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187851_gB;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187849_gA;
  }
  
  public float func_70047_e() {
    return 2.75F;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.945D;
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  protected void func_70609_aI() {
    super.func_70609_aI();
    if (!this.field_70170_p.field_72995_K)
      if (this.field_70725_aQ == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.1F);
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 0.9F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s Wither Storm has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Wither Storm has been destroyed!", new Object[0]));
        }   
  }
  
  public void func_70636_d() {
    this.field_70728_aV = getSize();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((getSize() < 300) ? 300.0D : getSize());
    if (this.field_70163_u < 0.0D)
      this.field_70181_x += (0.5D - this.field_70181_x) * 0.6000000238418579D; 
    if (this.field_70159_w > 1.0D)
      this.field_70159_w = 1.0D; 
    if (this.field_70179_y < -1.0D)
      this.field_70179_y = -1.0D; 
    if (this.field_70181_x > 1.0D)
      this.field_70181_x = 1.0D; 
    if (this.field_70181_x < -1.0D)
      this.field_70181_x = -1.0D; 
    if (this.field_70159_w > 1.0D)
      this.field_70159_w = 1.0D; 
    if (this.field_70179_y < -1.0D)
      this.field_70179_y = -1.0D; 
    if ((this.field_70173_aa + func_145782_y()) % 100 == 0)
      func_184185_a(ESound.commandBlockWitherHum, (getSize() >= 5250) ? 0.25F : 5.0F, 1.0F); 
    if (getSize() >= 12500 && !this.field_70170_p.field_72995_K) {
      EntityWitherStorm witherstorm = new EntityWitherStorm(this.field_70170_p);
      witherstorm.func_82149_j((Entity)this);
      witherstorm.func_94061_f(func_175446_cd());
      this.field_70170_p.func_72838_d((Entity)witherstorm);
      witherstorm.setOwnerId(func_184753_b());
      if (func_145818_k_())
        witherstorm.func_96094_a(func_95999_t()); 
      witherstorm.Grow(12500);
      Grow(0);
      func_174812_G();
    } 
    if (this.field_70173_aa % 160 == 0)
      func_184185_a(ESound.commandBlockWitherGrow, 2.0F, 1.0F); 
    if (this.field_70173_aa % 30 == 0 && getSize() > 5000)
      func_70642_aH(); 
    if (this.field_70173_aa % 60 == 0 && getSize() > 12000)
      func_184185_a(ESound.witherStormFirstRoar, 100.0F, 1.0F); 
    for (int i = 0; i < 16; i++) {
      int in = MathHelper.func_76128_c(this.field_70165_t - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
      int m = MathHelper.func_76128_c(this.field_70163_u + 3.0D - this.field_70146_Z.nextDouble() * 9.0D);
      int n = MathHelper.func_76128_c(this.field_70161_v - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
      BlockPos blockpos = new BlockPos(in, m, n);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      if (!this.field_70170_p.field_72995_K && (block != Blocks.field_150350_a || m < 0))
        this.field_70181_x = 0.05000000074505806D; 
    } 
    for (int i1 = 0; i1 < 1 + getSize() && i1 <= 1500; i1++) {
      if (func_70089_S() && this.field_70146_Z.nextInt(50) == 0) {
        int i11 = MathHelper.func_76128_c(this.field_70163_u + 1.0D) + MathHelper.func_76136_a(this.field_70146_Z, 2, 24) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int l1 = MathHelper.func_76128_c(this.field_70165_t) + MathHelper.func_76136_a(this.field_70146_Z, 2, 24) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int i2 = MathHelper.func_76128_c(this.field_70161_v) + MathHelper.func_76136_a(this.field_70146_Z, 2, 24) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        BlockPos blockpos = new BlockPos(l1, i11, i2);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        if (this.field_70170_p.func_175710_j(blockpos) && !block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175707_a(blockpos, blockpos) && block.func_176195_g(iblockstate, this.field_70170_p, new BlockPos(l1, i11, i2)) != -1.0F)
          if (EngenderConfig.mobs.grief) {
            Grow(getSize() + 3);
            func_70691_i(3.0F);
            if (block.func_149688_o(iblockstate).func_76224_d()) {
              this.field_70170_p.func_175698_g(new BlockPos(l1, i11, i2));
            } else {
              this.field_70170_p.func_72838_d((Entity)new EntityFallingBlock(this.field_70170_p, l1, i11, i2, block.func_176221_a(iblockstate, (IBlockAccess)this.field_70170_p, blockpos)));
            } 
          }  
      } 
    } 
    List<EntityFallingBlock> list = this.field_70170_p.func_175647_a(EntityFallingBlock.class, func_174813_aQ().func_72314_b(64.0D, 64.0D, 64.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (func_70089_S() && list != null && !list.isEmpty())
      for (int m = 0; m < list.size(); m++) {
        EntityFallingBlock entityFallingBlock = list.get(m);
        if (entityFallingBlock != null) {
          entityFallingBlock.field_70145_X = true;
          double d01 = this.field_70165_t - entityFallingBlock.field_70165_t;
          double d11 = this.field_70163_u + 1.5D - entityFallingBlock.field_70163_u;
          double d21 = this.field_70161_v - entityFallingBlock.field_70161_v;
          float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
          entityFallingBlock.field_70159_w = d01 / f2 * 0.5D * 0.5D + entityFallingBlock.field_70159_w * 0.5D;
          entityFallingBlock.field_70181_x = d11 / f2 * 0.5D * 0.5D + entityFallingBlock.field_70181_x * 0.5D;
          entityFallingBlock.field_70179_y = d21 / f2 * 0.5D * 0.5D + entityFallingBlock.field_70179_y * 0.5D;
          List<EntityLivingBase> sublist = this.field_70170_p.func_175647_a(EntityLivingBase.class, entityFallingBlock.func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
          if (func_70089_S() && sublist != null && !sublist.isEmpty())
            for (int i11 = 0; i11 < sublist.size(); i11++) {
              EntityLivingBase subentity = sublist.get(i11);
              if (subentity != null && !func_184191_r((Entity)subentity))
                subentity.func_70097_a(DamageSource.field_76368_d, 1.0F); 
            }  
        } 
      }  
    List<EntityFallingBlock> list1 = this.field_70170_p.func_175647_a(EntityFallingBlock.class, func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (func_70089_S() && list1 != null && !list1.isEmpty())
      for (int m = 0; m < list1.size(); m++) {
        EntityFallingBlock entityFallingBlock = list1.get(m);
        if (entityFallingBlock != null) {
          entityFallingBlock.func_70106_y();
          Grow(getSize() + 3);
          func_70691_i(3.0F);
        } 
      }  
    List<EntityItem> list11 = this.field_70170_p.func_175647_a(EntityItem.class, func_174813_aQ().func_72314_b(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (func_70089_S() && list11 != null && !list11.isEmpty())
      for (int m = 0; m < list11.size(); m++) {
        EntityItem entityItem = list11.get(m);
        if (entityItem != null) {
          double d01 = this.field_70165_t - entityItem.field_70165_t;
          double d11 = this.field_70163_u + 1.5D - entityItem.field_70163_u;
          double d21 = this.field_70161_v - entityItem.field_70161_v;
          float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
          entityItem.field_70159_w = d01 / f2 * 0.6D * 0.6D + entityItem.field_70159_w * 0.6D;
          entityItem.field_70181_x = d11 / f2 * 0.6D * 0.6D + entityItem.field_70181_x * 0.6D;
          entityItem.field_70179_y = d21 / f2 * 0.6D * 0.6D + entityItem.field_70179_y * 0.6D;
        } 
      }  
    List<EntityItem> list111 = this.field_70170_p.func_175647_a(EntityItem.class, func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (func_70089_S() && list111 != null && !list111.isEmpty())
      for (int m = 0; m < list111.size(); m++) {
        EntityItem entityItem = list111.get(m);
        if (entityItem != null) {
          entityItem.func_70106_y();
          Grow(getSize() + 1 + entityItem.func_92059_d().func_190916_E());
          func_70691_i(1.0F + entityItem.func_92059_d().func_190916_E());
        } 
      }  
    EntityLivingBase entity = func_70638_az();
    if (entity != null && getSize() >= 6000 && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && !(entity instanceof EntityWitherStorm) && !(entity instanceof EntityWitherStormHead) && !(entity instanceof EntityWitherStormTentacle) && !(entity instanceof EntityWitherStormTentacleDevourer)) {
      if (!this.field_70170_p.field_72995_K)
        entity.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 2147483647, 1)); 
      double d01 = this.field_70165_t - entity.field_70165_t;
      double d11 = this.field_70163_u + func_70047_e() - entity.field_70163_u;
      double d21 = this.field_70161_v - entity.field_70161_v;
      float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
      if (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entity instanceof net.minecraft.entity.monster.EntityEnderman) {
        ((EntityLiving)entity).func_70624_b((EntityLivingBase)this);
      } else if (entity instanceof net.minecraft.entity.boss.EntityDragon && entity.func_110143_aJ() <= 1.0F) {
        ((EntityLiving)entity).func_70656_aK();
      } else if (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || !((EntityTameBase)entity).func_184222_aU() || ((EntityTameBase)entity).isHero())) {
        ((EntityLiving)entity).func_70624_b((EntityLivingBase)this);
      } else {
        if (entity instanceof EntityLiving && !entity.func_184222_aU())
          ((EntityLiving)entity).func_70624_b((EntityLivingBase)this); 
        entity.field_70159_w = d01 / f2 * 0.5D * 0.5D + entity.field_70159_w * 0.5D;
        entity.field_70181_x = d11 / f2 * 0.5D * 0.5D + entity.field_70181_x * 0.5D;
        entity.field_70179_y = d21 / f2 * 0.5D * 0.5D + entity.field_70179_y * 0.5D;
      } 
      List<EntityLiving> list11111 = this.field_70170_p.func_175647_a(EntityLiving.class, func_174813_aQ().func_186662_g(1.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list11111 != null && !list11111.isEmpty() && getSize() >= 6000)
        for (int m = 0; m < list11111.size(); m++) {
          EntityLiving entity1 = list11111.get(m);
          if (entity1 != null && entity1.func_70089_S() && (!func_184191_r((Entity)entity1) || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman) && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
            if (!isWild() && EngenderConfig.general.useMessage)
              getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity1.func_70005_c_() + " was eaten by The Wither Storm (" + getOwner().func_70005_c_() + ")", new Object[0])); 
            this.field_70170_p.func_72960_a((Entity)entity1, (byte)3);
            if (!entity1.func_184222_aU()) {
              entity1.func_70606_j(0.0F);
            } else {
              entity1.func_70106_y();
            } 
            Grow(getSize() + 1 + (int)entity1.field_70131_O * (int)entity1.field_70131_O + (int)entity1.field_70130_N * (int)entity1.field_70130_N);
            func_70691_i(1.0F + entity1.func_110138_aP() + ((int)entity1.field_70131_O * (int)entity1.field_70131_O) + ((int)entity1.field_70130_N * (int)entity1.field_70130_N));
          } 
        }  
    } 
    if (getSize() >= 1000 && this.field_70131_O != 4.0F) {
      func_70105_a(2.0F, 4.0F);
    } else if (this.field_70131_O != 3.3F) {
      func_70105_a(0.9F, 3.3F);
    } 
    if (this.field_70181_x < 0.0D)
      this.field_70181_x *= 0.6D; 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null)
      if (entity != null) {
        if (this.field_70163_u < entity.field_70163_u || (!isArmored() && this.field_70163_u < entity.field_70163_u + 4.0D + entity.func_70047_e())) {
          if (this.field_70181_x < 0.0D)
            this.field_70181_x = 0.0D; 
          this.field_70181_x += (0.35D - this.field_70181_x) * 0.6D;
        } 
        double d0 = entity.field_70165_t - this.field_70165_t;
        double d1 = entity.field_70161_v - this.field_70161_v;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 64.0D) {
          if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y != 0.0D)
            this.field_70761_aq = this.field_70177_z = (float)Math.atan2(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F; 
          double d5 = MathHelper.func_76133_a(d3);
          if (this.moralRaisedTimer > 200) {
            this.field_70159_w += (d0 / d5 * 0.35D - this.field_70159_w) * 0.5D;
            this.field_70179_y += (d1 / d5 * 0.35D - this.field_70179_y) * 0.5D;
          } else {
            this.field_70159_w += (d0 / d5 * 0.35D - this.field_70159_w) * 0.6D;
            this.field_70179_y += (d1 / d5 * 0.35D - this.field_70179_y) * 0.6D;
          } 
        } 
      }  
    super.func_70636_d();
    int k;
    for (k = 0; k < 2; k++) {
      this.field_82218_g[k] = this.field_82221_e[k];
      this.field_82217_f[k] = this.field_82220_d[k];
    } 
    for (k = 0; k < 2; k++) {
      this.field_82220_d[k] = func_82204_b(this.field_82220_d[k], this.field_70125_A, 40.0F);
      this.field_82221_e[k] = func_82204_b(this.field_82221_e[k], this.field_70761_aq, 10.0F);
    } 
    if (this.field_70170_p.field_72995_K)
      for (k = 0; k < 2; k++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);  
    int j;
    for (j = 0; j < 3; j++) {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d10 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d2 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d4 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (getInvulTime() > 0)
      for (j = 0; j < 3; j++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + this.field_70146_Z.nextGaussian() * 1.0D, this.field_70163_u + (this.field_70146_Z.nextFloat() * 3.3F), this.field_70161_v + this.field_70146_Z.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);  
  }
  
  protected void func_70619_bc() {
    if (getInvulTime() > 0) {
      int i = getInvulTime() - 1;
      if (i <= 0) {
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, 7.0F, false);
        func_184185_a(ESound.commandBlockWitherSpawn, Float.MAX_VALUE, 1.0F);
        for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
          this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), ESound.commandBlockWitherSpawn, func_184176_by(), Float.MAX_VALUE, 1.0F);
          entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A75A Wither Storm has been summoned in " + this.field_70170_p.field_73011_w.func_186058_p().func_186065_b() + "!", new Object[0]), true);
        } 
      } 
      setInvulTime(i);
      if (this.field_70173_aa % 1 == 0)
        func_70691_i(1.0F); 
    } else {
      super.func_70619_bc();
      if (this.blockBreakCounter > 0) {
        this.blockBreakCounter--;
        if (this.blockBreakCounter == 0 && EngenderConfig.mobs.grief) {
          int i11 = MathHelper.func_76128_c(this.field_70163_u);
          int l1 = MathHelper.func_76128_c(this.field_70165_t);
          int i2 = MathHelper.func_76128_c(this.field_70161_v);
          boolean flag = false;
          for (int k2 = -1; k2 <= 1; k2++) {
            for (int l2 = -1; l2 <= 1; l2++) {
              for (int j = 0; j <= 3; j++) {
                int i3 = l1 + k2;
                int k = i11 + j;
                int l = i2 + l2;
                BlockPos blockpos = new BlockPos(i3, k, l);
                IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                Block block = iblockstate.func_177230_c();
                if (!block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && block.canEntityDestroy(iblockstate, (IBlockAccess)this.field_70170_p, blockpos, (Entity)this))
                  flag = (this.field_70170_p.func_175655_b(blockpos, true) || flag); 
              } 
            } 
          } 
          if (flag)
            this.field_70170_p.func_180498_a((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
        } 
      } 
      if (this.field_70173_aa % 20 == 0)
        func_70691_i(1.0F); 
    } 
  }
  
  public static boolean canDestroyBlock(Block p_181033_0_) {
    return (p_181033_0_ != Blocks.field_150357_h && p_181033_0_ != Blocks.field_150384_bq && p_181033_0_ != Blocks.field_150378_br && p_181033_0_ != Blocks.field_150483_bI && p_181033_0_ != Blocks.field_185776_dc && p_181033_0_ != Blocks.field_185777_dd && p_181033_0_ != Blocks.field_180401_cv);
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public void func_82206_m() {
    setInvulTime(220);
    func_70606_j(func_110138_aP() / 3.0F);
  }
  
  public void func_70110_aj() {}
  
  private double func_82214_u(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.field_70165_t; 
    float f = (this.field_70761_aq + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76134_b(f);
    return this.field_70165_t + f1 * 1.25D;
  }
  
  private double func_82208_v(int p_82208_1_) {
    return (p_82208_1_ <= 0) ? (this.field_70163_u + 1.75D) : (this.field_70163_u + 2.25D);
  }
  
  private double func_82213_w(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.field_70161_v; 
    float f = (this.field_70761_aq + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76126_a(f);
    return this.field_70161_v + f1 * 1.25D;
  }
  
  private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f3 = MathHelper.func_76142_g(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_)
      f3 = p_82204_3_; 
    if (f3 < -p_82204_3_)
      f3 = -p_82204_3_; 
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.field_70165_t, p_82216_2_.field_70163_u + p_82216_2_.field_70131_O * 0.75D, p_82216_2_.field_70161_v, (p_82216_1_ == 0 && this.field_70146_Z.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_) {
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1024, new BlockPos((Entity)this), 0);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherStormSkull entitywitherskull = new EntityWitherStormSkull(this.field_70170_p, (EntityLivingBase)this, d6, d7, d8);
    if (p_82209_8_)
      entitywitherskull.setInvulnerable(true); 
    float f = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
    entitywitherskull.damage = f;
    entitywitherskull.field_70163_u = d4;
    entitywitherskull.field_70165_t = d3;
    entitywitherskull.field_70161_v = d5;
    this.field_70170_p.func_72838_d((Entity)entitywitherskull);
  }
  
  public void func_82196_d(EntityLivingBase p_82196_1_, float p_82196_2_) {
    if (!func_184191_r((Entity)p_82196_1_))
      launchWitherSkullToEntity(0, p_82196_1_); 
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source))
      return false; 
    if (!source.func_94541_c() && source != DamageSource.field_76369_e) {
      if (getInvulTime() > 0 && source != DamageSource.field_76380_i)
        return false; 
      if (getSize() >= 1000)
        if (source.func_76352_a() || source.func_76346_g() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton) {
          func_85034_r(0);
          return false;
        }  
      if (getSize() >= 5250)
        if (source.func_82725_o())
          return false;  
      if (this.blockBreakCounter <= 0)
        this.blockBreakCounter = 20; 
      for (int i = 0; i < this.field_82224_i.length; i++)
        this.field_82224_i[i] = this.field_82224_i[i] + 10; 
      if (source.func_82725_o() && getSize() < 5000)
        amount *= 100.0F; 
      return super.func_70097_a(source, amount);
    } 
    return false;
  }
  
  protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
    EntityItem entityitem = func_70099_a(new ItemStack(Blocks.field_150483_bI, 1, 0), 0.0F);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_COMMAND_BLOCK_WITHER;
  }
  
  protected void func_70623_bb() {
    this.field_70708_bq = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
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
  
  @SideOnly(Side.CLIENT)
  public float func_82207_a(int p_82207_1_) {
    return this.field_82221_e[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float func_82210_r(int p_82210_1_) {
    return this.field_82220_d[p_82210_1_];
  }
  
  public int getInvulTime() {
    return ((Integer)this.field_70180_af.func_187225_a(INVULNERABILITY_TIME)).intValue();
  }
  
  public void setInvulTime(int p_82215_1_) {
    this.field_70180_af.func_187227_b(INVULNERABILITY_TIME, Integer.valueOf(p_82215_1_));
  }
  
  public boolean isArmored() {
    return (getSize() >= 3000);
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  class AIDoNothing extends EntityAIBase {
    public AIDoNothing() {
      func_75248_a(7);
    }
    
    public boolean func_75250_a() {
      return false;
    }
  }
}
