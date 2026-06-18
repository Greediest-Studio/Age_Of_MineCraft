package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStorm extends EntityTameBase implements Massive, Armored, Flying, Undead {
  private static final DataParameter<Boolean> DOESNT_HAVE_COMMAND_BLOCK = EntityDataManager.func_187226_a(EntityWitherStorm.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Integer> SIZE = EntityDataManager.func_187226_a(EntityWitherStorm.class, DataSerializers.field_187192_b);
  
  public EntityWitherStormHead centerHead = new EntityWitherStormHead(this.field_70170_p);
  
  public EntityWitherStormHead rightHead = new EntityWitherStormHead(this.field_70170_p);
  
  public EntityWitherStormHead leftHead = new EntityWitherStormHead(this.field_70170_p);
  
  public EntityWitherStormTentacle tentacle1 = new EntityWitherStormTentacle(this.field_70170_p);
  
  public EntityWitherStormTentacle tentacle2 = new EntityWitherStormTentacle(this.field_70170_p);
  
  public EntityWitherStormTentacle tentacle3 = new EntityWitherStormTentacle(this.field_70170_p);
  
  public EntityWitherStormTentacle tentacle4 = new EntityWitherStormTentacle(this.field_70170_p);
  
  public EntityWitherStormTentacle tentacle5 = new EntityWitherStormTentacle(this.field_70170_p);
  
  public EntityWitherStormTentacleDevourer tentacledevourer1 = new EntityWitherStormTentacleDevourer(this.field_70170_p);
  
  public EntityWitherStormTentacleDevourer tentacledevourer2 = new EntityWitherStormTentacleDevourer(this.field_70170_p);
  
  public EntityWitherStorm(World worldIn) {
    super(worldIn);
    func_70105_a(9.0F, 32.0F);
    this.isOffensive = true;
    this.field_70178_ae = true;
    this.field_70145_X = true;
    ((PathNavigateGround)func_70661_as()).func_179693_d(true);
    this.field_70765_h = new WitherStormMoveHelper(this);
    this.field_70714_bg.func_75776_a(5, new AIRandomFly(this));
    this.field_70714_bg.func_75776_a(7, new AILookAround());
    this.field_70728_aV = 50000;
    setLevel(300);
    this.field_70158_ak = true;
    for (EntityPlayer entityplayer : worldIn.field_73010_i)
      worldIn.func_184133_a(null, entityplayer.func_180425_c(), ESound.witherStormFinish, func_184176_by(), Float.MAX_VALUE, 1.0F); 
    if (!worldIn.field_72995_K) {
      this.centerHead = new EntityWitherStormHead(worldIn);
      this.centerHead.residentWitherStorm = this;
      this.centerHead.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.centerHead);
      this.rightHead = new EntityWitherStormHead(worldIn);
      this.rightHead.residentWitherStorm = this;
      this.rightHead.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.rightHead);
      this.leftHead = new EntityWitherStormHead(worldIn);
      this.leftHead.residentWitherStorm = this;
      this.leftHead.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.leftHead);
      this.tentacle1 = new EntityWitherStormTentacle(worldIn);
      this.tentacle1.residentWitherStorm = this;
      this.tentacle1.func_82149_j((Entity)this);
      this.field_70170_p.func_72838_d((Entity)this.tentacle1);
      this.tentacle2 = new EntityWitherStormTentacle(worldIn);
      this.tentacle2.residentWitherStorm = this;
      this.tentacle2.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacle2);
      this.tentacle3 = new EntityWitherStormTentacle(worldIn);
      this.tentacle3.residentWitherStorm = this;
      this.tentacle3.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacle3);
      this.tentacle4 = new EntityWitherStormTentacle(worldIn);
      this.tentacle4.residentWitherStorm = this;
      this.tentacle4.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacle4);
      this.tentacle5 = new EntityWitherStormTentacle(worldIn);
      this.tentacle5.residentWitherStorm = this;
      this.tentacle5.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacle5);
      this.tentacledevourer1 = new EntityWitherStormTentacleDevourer(worldIn);
      this.tentacledevourer1.residentWitherStorm = this;
      this.tentacledevourer1.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacledevourer1);
      this.tentacledevourer2 = new EntityWitherStormTentacleDevourer(worldIn);
      this.tentacledevourer2.residentWitherStorm = this;
      this.tentacledevourer2.func_82149_j((Entity)this);
      worldIn.func_72838_d((Entity)this.tentacledevourer2);
    } 
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return doesntContainACommandBlock() ? "wither_storm_severed" : ((getSize() >= 250000) ? "wither_storm_thunderstorm" : ((getSize() < 250000 && getSize() >= 50000) ? "wither_storm_devourer" : "wither_storm_destroyer"));
  }
  
  public int playMusic() {
    return (getSize() >= 250000) ? 68 : ((getSize() < 250000 && getSize() >= 50000) ? 13 : 12);
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186745_a(BossInfo.Color.PURPLE);
    this.bossInfo.func_186741_a(true);
  }
  
  public int getSpawnTimer() {
    return 0;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.WITHER_STORM;
  }
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 32.0F + this.field_70146_Z.nextFloat() * 32.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 64.0F + this.field_70146_Z.nextFloat() * 24.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 16.0F + this.field_70146_Z.nextFloat() * 16.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public void func_174812_G() {
    super.func_174812_G();
    this.centerHead.func_70106_y();
    this.rightHead.func_70106_y();
    this.leftHead.func_70106_y();
    this.tentacle1.func_70106_y();
    this.tentacle2.func_70106_y();
    this.tentacle3.func_70106_y();
    this.tentacle4.func_70106_y();
    this.tentacle5.func_70106_y();
    this.tentacledevourer1.func_70106_y();
    this.tentacledevourer2.func_70106_y();
    this.centerHead = null;
    this.rightHead = null;
    this.leftHead = null;
    this.tentacle1 = null;
    this.tentacle2 = null;
    this.tentacle3 = null;
    this.tentacle4 = null;
    this.tentacle5 = null;
    this.tentacledevourer1 = null;
    this.tentacledevourer2 = null;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12500.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.8D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(128.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    return doesntContainACommandBlock() ? "Severed Wither Storm" : "The Wither Storm";
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(SIZE, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(DOESNT_HAVE_COMMAND_BLOCK, Boolean.valueOf(false));
  }
  
  public void func_70076_C() {}
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Growth", getSize());
    tagCompound.func_74757_a("NoCommandBlock", doesntContainACommandBlock());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    Grow(tagCompund.func_74762_e("Growth"));
    setNotContainingCommandBlock(tagCompund.func_74767_n("NoCommandBlock"));
  }
  
  protected SoundEvent func_184639_G() {
    return ESound.witherStormAmbient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return doesntContainACommandBlock() ? ESound.witherStormHurt : ESound.witherStormHurtCommandBlock;
  }
  
  protected SoundEvent func_184615_bR() {
    return doesntContainACommandBlock() ? ESound.witherStormHurt : ESound.witherStormDeath;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 1.0F : 100.0F;
  }
  
  protected float func_70647_i() {
    return 1.0F;
  }
  
  public float func_70047_e() {
    return 0.5F;
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  public EnumWitherStormPhase getPhase() {
    return (getSize() <= 250000 && getSize() > 50000) ? EnumWitherStormPhase.Devourer : ((getSize() > 250000) ? EnumWitherStormPhase.ThunderStorm : EnumWitherStormPhase.Destroyer);
  }
  
  public void func_70636_d() {
    float rot = this.field_70759_as * 0.017453292F;
    float oned = MathHelper.func_76126_a(rot);
    float twod = MathHelper.func_76134_b(rot);
    if (!isWild() && func_70068_e((Entity)getOwner()) >= 48400.0D)
      func_70012_b((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, this.field_70177_z, this.field_70125_A); 
    if (!doesntContainACommandBlock() && !this.field_70170_p.field_72995_K)
      if (func_70089_S()) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      }  
    func_70661_as().func_75499_g();
    this.field_70728_aV = getSize();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(doesntContainACommandBlock() ? 1000.0D : getSize());
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(doesntContainACommandBlock() ? 20.0D : 24.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(doesntContainACommandBlock() ? 10.0D : 20.0D);
    if (!doesntContainACommandBlock() && getSize() >= 300000 && !this.field_70170_p.field_72995_K && this.field_70173_aa % 1000 == 0) {
      EntityWitherStorm entityzombie = new EntityWitherStorm(this.field_70170_p);
      entityzombie.func_82149_j((Entity)this);
      entityzombie.func_94061_f(func_175446_cd());
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      entityzombie.setOwnerId(func_184753_b());
      entityzombie.setNotContainingCommandBlock(true);
      entityzombie.field_70159_w = this.field_70146_Z.nextDouble() - 0.5D;
      entityzombie.field_70179_y = this.field_70146_Z.nextDouble() - 0.5D;
      Grow(getSize() - 12500);
    } 
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
    if (!isWild())
      getOwner().func_184596_c(MobEffects.field_82731_v); 
    if (this.deathTicks <= 0)
      if (!this.field_70170_p.field_72995_K) {
        if (this.centerHead != null) {
          if (this.centerHead.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.centerHead.func_70012_b(this.field_70165_t + (oned * -7.0F), this.field_70163_u, this.field_70161_v - (twod * -7.0F), 0.0F, 0.0F);
              this.centerHead.field_70761_aq = this.centerHead.field_70177_z = this.field_70759_as;
            } else {
              this.centerHead.func_70012_b(this.field_70165_t + (oned * -7.0F), this.field_70163_u, this.field_70161_v - (twod * -7.0F), 0.0F, 0.0F);
              this.centerHead.field_70761_aq = this.centerHead.field_70177_z = this.field_70759_as;
            }  
          if (getJukeboxToDanceTo() != null)
            this.centerHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.centerHead.field_70128_L) {
            this.centerHead = null;
            func_70097_a(DamageSource.field_76377_j, 1000.0F);
          } 
        } else {
          this.centerHead = new EntityWitherStormHead(this.field_70170_p);
          this.centerHead.residentWitherStorm = this;
          this.centerHead.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.centerHead);
          this.centerHead.func_70071_h_();
        } 
        if (this.rightHead != null) {
          if (this.rightHead.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.rightHead.func_70012_b(this.field_70165_t + (twod * -7.0F) + (oned * -7.0F), this.field_70163_u, this.field_70161_v + (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F);
              this.rightHead.field_70761_aq = this.rightHead.field_70177_z = this.field_70759_as + 10.0F;
            } else {
              this.rightHead.func_70012_b(this.field_70165_t + (twod * -20.0F) + (oned * -4.0F), this.field_70163_u + 10.0D, this.field_70161_v + (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F);
              this.rightHead.field_70761_aq = this.rightHead.field_70177_z = this.field_70759_as + 30.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.rightHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.rightHead.field_70128_L) {
            this.rightHead = null;
            func_70097_a(DamageSource.field_76377_j, 1000.0F);
          } 
        } else {
          this.rightHead = new EntityWitherStormHead(this.field_70170_p);
          this.rightHead.residentWitherStorm = this;
          this.rightHead.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.rightHead);
          this.rightHead.func_70071_h_();
        } 
        if (this.leftHead != null) {
          if (this.leftHead.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.leftHead.func_70012_b(this.field_70165_t - (twod * -7.0F) + (oned * -7.0F), this.field_70163_u, this.field_70161_v - (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F);
              this.leftHead.field_70761_aq = this.leftHead.field_70177_z = this.field_70759_as - 10.0F;
            } else {
              this.leftHead.func_70012_b(this.field_70165_t - (twod * -20.0F) + (oned * -4.0F), this.field_70163_u + 10.0D, this.field_70161_v - (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F);
              this.leftHead.field_70761_aq = this.leftHead.field_70177_z = this.field_70759_as - 30.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.leftHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.leftHead.field_70128_L) {
            this.leftHead = null;
            func_70097_a(DamageSource.field_76377_j, 1000.0F);
          } 
        } else {
          this.leftHead = new EntityWitherStormHead(this.field_70170_p);
          this.leftHead.residentWitherStorm = this;
          this.leftHead.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.leftHead);
          this.leftHead.func_70071_h_();
        } 
        if (this.tentacle1 != null) {
          if (this.tentacle1.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacle1.func_70012_b(this.field_70165_t, this.field_70163_u - 2.0D, this.field_70161_v, 0.0F, 0.0F);
              this.tentacle1.field_70759_as = this.field_70759_as - 30.0F;
            } else {
              this.tentacle1.func_70012_b(this.field_70165_t, this.field_70163_u - 12.0D, this.field_70161_v, 0.0F, 0.0F);
              this.tentacle1.field_70759_as = this.field_70759_as - 60.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle1.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle1.field_70128_L) {
            this.tentacle1 = null;
            func_70097_a(DamageSource.field_76377_j, 100.0F);
          } 
        } else {
          this.tentacle1 = new EntityWitherStormTentacle(this.field_70170_p);
          this.tentacle1.residentWitherStorm = this;
          this.tentacle1.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacle1);
          this.tentacle1.func_70071_h_();
        } 
        if (this.tentacle2 != null) {
          if (this.tentacle2.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacle2.func_70012_b(this.field_70165_t + (twod * 4.0F), this.field_70163_u - 1.0D, this.field_70161_v + (oned * 4.0F), 0.0F, 0.0F);
              this.tentacle2.field_70759_as = this.field_70759_as + 90.0F;
            } else {
              this.tentacle2.func_70012_b(this.field_70165_t + (twod * 2.0F), this.field_70163_u - 8.0D, this.field_70161_v + (oned * 2.0F), 0.0F, 0.0F);
              this.tentacle2.field_70759_as = this.field_70759_as + 60.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle2.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle2.field_70128_L) {
            this.tentacle2 = null;
            func_70097_a(DamageSource.field_76377_j, 100.0F);
          } 
        } else {
          this.tentacle2 = new EntityWitherStormTentacle(this.field_70170_p);
          this.tentacle2.residentWitherStorm = this;
          this.tentacle2.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacle1);
          this.tentacle2.func_70071_h_();
        } 
        if (this.tentacle3 != null) {
          if (this.tentacle3.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacle3.func_70012_b(this.field_70165_t - (twod * -2.0F), this.field_70163_u + 4.0D, this.field_70161_v - (oned * -2.0F), 0.0F, 0.0F);
              this.tentacle3.field_70759_as = this.field_70759_as - 130.0F;
            } else {
              this.tentacle3.func_70012_b(this.field_70165_t - (twod * -8.0F), this.field_70163_u + 8.0D, this.field_70161_v - (oned * -8.0F), 0.0F, 0.0F);
              this.tentacle3.field_70759_as = this.field_70759_as - 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle3.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle3.field_70128_L) {
            this.tentacle3 = null;
            func_70097_a(DamageSource.field_76377_j, 100.0F);
          } 
        } else {
          this.tentacle3 = new EntityWitherStormTentacle(this.field_70170_p);
          this.tentacle3.residentWitherStorm = this;
          this.tentacle3.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacle3);
          this.tentacle3.func_70071_h_();
        } 
        if (this.tentacle4 != null) {
          if (this.tentacle4.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacle4.func_70012_b(this.field_70165_t + (twod * -5.0F), this.field_70163_u + 3.0D, this.field_70161_v + (oned * -5.0F), 0.0F, 0.0F);
              this.tentacle4.field_70759_as = this.field_70759_as + 120.0F;
            } else {
              this.tentacle4.func_70012_b(this.field_70165_t + (twod * -5.0F), this.field_70163_u + 4.0D, this.field_70161_v + (oned * -5.0F), 0.0F, 0.0F);
              this.tentacle4.field_70759_as = this.field_70759_as + 105.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle4.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle4.field_70128_L) {
            this.tentacle4 = null;
            func_70097_a(DamageSource.field_76377_j, 100.0F);
          } 
        } else {
          this.tentacle4 = new EntityWitherStormTentacle(this.field_70170_p);
          this.tentacle4.residentWitherStorm = this;
          this.tentacle4.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacle4);
          this.tentacle4.func_70071_h_();
        } 
        if (this.tentacle5 != null) {
          if (this.tentacle5.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacle5.func_70012_b(this.field_70165_t, this.field_70163_u + 2.0D, this.field_70161_v, 0.0F, 0.0F);
              this.tentacle5.field_70759_as = this.field_70759_as - 100.0F;
            } else {
              this.tentacle5.func_70012_b(this.field_70165_t, this.field_70163_u + 3.0D, this.field_70161_v, 0.0F, 0.0F);
              this.tentacle5.field_70759_as = this.field_70759_as - 180.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle5.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle5.field_70128_L) {
            this.tentacle5 = null;
            func_70097_a(DamageSource.field_76377_j, 100.0F);
          } 
        } else {
          this.tentacle5 = new EntityWitherStormTentacle(this.field_70170_p);
          this.tentacle5.residentWitherStorm = this;
          this.tentacle5.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacle5);
          this.tentacle5.func_70071_h_();
        } 
        if (this.tentacledevourer1 != null) {
          if (this.tentacledevourer1.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacledevourer1.func_70012_b(this.field_70165_t, this.field_70163_u + 10.0D, this.field_70161_v - oned, 0.0F, 0.0F);
              this.tentacledevourer1.field_70759_as = this.field_70759_as - 180.0F;
            } else {
              this.tentacledevourer1.func_70012_b(this.field_70165_t - (twod * -12.0F), this.field_70163_u + 10.0D, this.field_70161_v - (oned * -12.0F), 0.0F, 0.0F);
              this.tentacledevourer1.func_82142_c((getSize() < 50000));
              this.tentacledevourer1.field_70759_as = this.field_70759_as - 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacledevourer1.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacledevourer1.field_70128_L) {
            this.tentacledevourer1 = null;
            func_70097_a(DamageSource.field_76377_j, 200.0F);
          } 
        } else {
          this.tentacledevourer1 = new EntityWitherStormTentacleDevourer(this.field_70170_p);
          this.tentacledevourer1.residentWitherStorm = this;
          this.tentacledevourer1.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacledevourer1);
          this.tentacledevourer1.func_70071_h_();
        } 
        if (this.tentacledevourer2 != null) {
          if (this.tentacledevourer2.func_70089_S())
            if (doesntContainACommandBlock()) {
              this.tentacledevourer2.func_70012_b(this.field_70165_t, this.field_70163_u + 10.0D, this.field_70161_v + oned, 0.0F, 0.0F);
              this.tentacledevourer2.field_70759_as = this.field_70759_as + 180.0F;
            } else {
              this.tentacledevourer2.func_70012_b(this.field_70165_t + (twod * -12.0F), this.field_70163_u + 10.0D, this.field_70161_v + (oned * -12.0F), 0.0F, 0.0F);
              this.tentacledevourer2.func_82142_c((getSize() < 50000));
              this.tentacledevourer2.field_70759_as = this.field_70759_as + 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacledevourer2.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacledevourer2.field_70128_L) {
            this.tentacledevourer2 = null;
            func_70097_a(DamageSource.field_76377_j, 200.0F);
          } 
        } else {
          this.tentacledevourer2 = new EntityWitherStormTentacleDevourer(this.field_70170_p);
          this.tentacledevourer2.residentWitherStorm = this;
          this.tentacledevourer2.func_82149_j((Entity)this);
          this.field_70170_p.func_72838_d((Entity)this.tentacledevourer2);
          this.tentacledevourer2.func_70071_h_();
        } 
      }  
    if ((this.field_70173_aa + func_145782_y()) % 100 == 0)
      func_184185_a(ESound.commandBlockWitherHum, 0.25F, 1.0F); 
    if (doesntContainACommandBlock()) {
      func_70691_i(1.0F);
      if (this.field_70130_N != 12.0F && this.field_70131_O != 12.0F)
        func_70105_a(12.0F, 12.0F); 
    } else {
      func_70691_i(2.0F);
      if (this.field_70130_N != 9.0F && this.field_70131_O != 32.0F)
        func_70105_a(9.0F, 32.0F); 
    } 
    if (func_110143_aJ() <= 0.0F) {
      float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 12.0F;
      float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 36.0F;
      float f17 = (this.field_70146_Z.nextFloat() - 0.5F) * 12.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f13, this.field_70163_u - 4.0D + f15, this.field_70161_v + f17, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.field_70170_p.field_72995_K && getOwner() != null && this.field_70163_u <= (getOwner()).field_70163_u && this.field_70163_u >= (getOwner()).field_70163_u - 0.5D && !func_70093_af())
      createEngenderModExplosion((Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 8.0F, false, EngenderConfig.mobs.grief); 
    this.field_70122_E = false;
    this.field_70160_al = true;
    this.field_70145_X = true;
    if (getSize() < 12500)
      Grow(12500); 
    if (getSize() >= 250000) {
      WorldInfo worldinfo = this.field_70170_p.func_72912_H();
      worldinfo.func_176142_i(0);
      worldinfo.func_76080_g(200);
      worldinfo.func_76090_f(200);
      worldinfo.func_76084_b(true);
      worldinfo.func_76069_a(true);
    } 
    if (this.field_70181_x > 1.0D)
      this.field_70181_x = 0.0D; 
    this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    if (this.deathTicks <= 0) {
      if (this.field_70718_bc <= 50 && func_110143_aJ() <= 0.0F) {
        func_70606_j(1.0F);
      } else if (this.field_70718_bc > 50 && func_110143_aJ() <= 0.0F) {
        func_70606_j(0.0F);
      } 
      if (this.field_70173_aa % 20 == 0 && !func_175446_cd() && func_70089_S() && EngenderConfig.mobs.grief && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < getSize() / 100; i1++) {
          if (this.field_70146_Z.nextInt(4) == 0) {
            int l1 = MathHelper.func_76128_c(this.field_70165_t) + MathHelper.func_76136_a(this.field_70146_Z, 2, 128) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
            int i2 = MathHelper.func_76128_c(this.field_70161_v) + MathHelper.func_76136_a(this.field_70146_Z, 2, 128) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
            BlockPos blockpos = new BlockPos(l1, MathHelper.func_76128_c(this.field_70163_u), i2);
            blockpos = this.field_70170_p.func_175672_r(blockpos);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            if (this.field_70170_p.func_180495_p(blockpos.func_177984_a()).func_177230_c().isAir(this.field_70170_p.func_180495_p(blockpos.func_177984_a()), (IBlockAccess)this.field_70170_p, blockpos.func_177984_a()) && !block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175707_a(blockpos, blockpos) && block.func_176195_g(iblockstate, this.field_70170_p, new BlockPos(l1, blockpos.func_177956_o(), i2)) != -1.0F)
              if (block.func_149688_o(iblockstate).func_76224_d()) {
                this.field_70170_p.func_175698_g(new BlockPos(l1, blockpos.func_177956_o(), i2));
              } else {
                this.field_70170_p.func_72838_d((Entity)new EntityFallingBlock(this.field_70170_p, l1, blockpos.func_177956_o(), i2, block.func_176221_a(iblockstate, (IBlockAccess)this.field_70170_p, blockpos)));
              }  
          } 
        }  
      if (this.field_70173_aa % 60 == 0) {
        List<EntityCreature> list2 = this.field_70170_p.func_175647_a(EntityCreature.class, func_174813_aQ().func_186662_g(128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list2 != null && !list2.isEmpty())
          for (int i1 = 0; i1 < list2.size(); i1++) {
            EntityCreature entity = list2.get(i1);
            EntityAIAvoidEntitySPC ai = new EntityAIAvoidEntitySPC(entity, EntityWitherStorm.class, 128.0F, 1.5D, 1.5D);
            if (entity != null && entity.func_70089_S() && entity.func_184222_aU() && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity instanceof EntityTameBase) && !entity.field_70714_bg.field_75782_a.contains(ai)) {
              entity.field_70714_bg.func_75776_a(0, (EntityAIBase)ai);
            } else {
              list2.remove(entity);
            } 
          }  
      } 
      if (this.field_70173_aa > 20 && !doesntContainACommandBlock()) {
        List<Entity> list2 = this.field_70170_p.func_175647_a(Entity.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list2 != null && !list2.isEmpty() && func_70089_S())
          for (int i1 = 0; i1 < list2.size(); i1++) {
            Entity entity = list2.get(i1);
            if (!(entity instanceof EntityFallingBlock) && entity != null && entity.func_70047_e() == 0.0F && entity.field_70131_O == 0.98F && entity.field_70130_N == 0.98F) {
              entity.field_70145_X = (entity.field_70123_F || entity.field_70124_G);
              double d01 = this.field_70165_t - entity.field_70165_t;
              double d11 = this.field_70163_u + func_70047_e() - entity.field_70163_u;
              double d21 = this.field_70161_v - entity.field_70161_v;
              float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
              entity.field_70159_w = d01 / f2 * 0.5D * 0.5D + entity.field_70159_w * 0.5D;
              entity.field_70181_x = d11 / f2 * 0.5D * 0.5D + entity.field_70181_x * 0.5D;
              entity.field_70179_y = d21 / f2 * 0.5D * 0.5D + entity.field_70179_y * 0.5D;
            } 
          }  
      } 
      List<EntityFallingBlock> list = this.field_70170_p.func_175647_a(EntityFallingBlock.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list != null && !list.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityFallingBlock entity = list.get(i1);
          if (entity != null) {
            double d01 = this.field_70165_t - entity.field_70165_t;
            double d11 = this.field_70163_u + func_70047_e() - entity.field_70163_u;
            double d21 = this.field_70161_v - entity.field_70161_v;
            float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
            entity.field_70159_w = d01 / f2 * 0.5D * 0.5D + entity.field_70159_w * 0.5D;
            entity.field_70181_x = d11 / f2 * 0.5D * 0.5D + entity.field_70181_x * 0.5D;
            entity.field_70179_y = d21 / f2 * 0.5D * 0.5D + entity.field_70179_y * 0.5D;
            List<EntityLivingBase> sublist = this.field_70170_p.func_175647_a(EntityLivingBase.class, entity.func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
            if (func_70089_S() && sublist != null && !sublist.isEmpty())
              for (int i11 = 0; i11 < sublist.size(); i11++) {
                EntityLivingBase subentity = sublist.get(i11);
                if (subentity != null && !func_184191_r((Entity)subentity))
                  subentity.func_70097_a(DamageSource.field_76368_d, 5.0F); 
              }  
          } 
        }  
      List<EntityFallingBlock> list1 = this.field_70170_p.func_175647_a(EntityFallingBlock.class, func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list1 != null && !list1.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list1.size(); i1++) {
          EntityFallingBlock entity = list1.get(i1);
          if (entity != null) {
            entity.func_70106_y();
            Grow(getSize() + 3);
            func_70691_i(2.0F);
          } 
        }  
      List<EntityItem> list11 = this.field_70170_p.func_175647_a(EntityItem.class, func_174813_aQ().func_72314_b(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list11 != null && !list11.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list11.size(); i1++) {
          EntityItem entity = list11.get(i1);
          if (entity != null && entity.func_92059_d().func_77973_b() != EItem.witheredNetherStar) {
            double d01 = this.field_70165_t - entity.field_70165_t;
            double d11 = this.field_70163_u + 2.0D - entity.field_70163_u;
            double d21 = this.field_70161_v - entity.field_70161_v;
            float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
            entity.field_70159_w = d01 / f2 * 0.6D * 0.6D + entity.field_70159_w * 0.6D;
            entity.field_70181_x = d11 / f2 * 0.6D * 0.6D + entity.field_70181_x * 0.6D;
            entity.field_70179_y = d21 / f2 * 0.6D * 0.6D + entity.field_70179_y * 0.6D;
          } 
        }  
      List<EntityItem> list111 = this.field_70170_p.func_175647_a(EntityItem.class, func_174813_aQ().func_72314_b(4.0D, 4.0D, 4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list111 != null && !list111.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list111.size(); i1++) {
          EntityItem entity = list111.get(i1);
          if (entity != null && entity.func_92059_d().func_77973_b() != EItem.witheredNetherStar) {
            entity.func_70106_y();
            Grow(getSize() + 1 + entity.func_92059_d().func_190916_E());
            func_70691_i((1 + entity.func_92059_d().func_190916_E()));
          } 
        }  
      List<EntityArrow> list1111 = this.field_70170_p.func_175647_a(EntityArrow.class, func_174813_aQ().func_72314_b(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list1111 != null && !list1111.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list1111.size(); i1++) {
          EntityArrow entity = list1111.get(i1);
          if (entity != null && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther)) {
            double d01 = this.field_70165_t - entity.field_70165_t;
            double d11 = this.field_70163_u + 2.0D - entity.field_70163_u;
            double d21 = this.field_70161_v - entity.field_70161_v;
            float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
            entity.field_70159_w = d01 / f2 * 0.6D * 0.6D + entity.field_70159_w * 0.6D;
            entity.field_70181_x = d11 / f2 * 0.6D * 0.6D + entity.field_70181_x * 0.6D;
            entity.field_70179_y = d21 / f2 * 0.6D * 0.6D + entity.field_70179_y * 0.6D;
          } 
        }  
      List<EntityArrow> list11111 = this.field_70170_p.func_175647_a(EntityArrow.class, func_174813_aQ().func_72314_b(4.0D, 4.0D, 4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list11111 != null && !list11111.isEmpty() && func_70089_S() && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < list11111.size(); i1++) {
          EntityArrow entity = list11111.get(i1);
          if (entity != null && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther)) {
            entity.func_70106_y();
            Grow(getSize() + 1);
            func_70691_i(1.0F);
          } 
        }  
    } 
    this.field_70158_ak = true;
    super.func_70636_d();
  }
  
  public void func_70110_aj() {}
  
  protected void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    float f = doesntContainACommandBlock() ? 0.99F : ((getSize() > 250000) ? 0.85F : ((getSize() > 250000) ? 0.88F : 0.91F));
    float f1 = 0.16277136F / f * f * f;
    func_191958_b(strafe, vertical, forward, 0.02F);
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70159_w *= f;
    this.field_70181_x *= f;
    this.field_70179_y *= f;
    this.field_184618_aE = this.field_70721_aZ;
    double d1 = this.field_70165_t - this.field_70169_q;
    double d0 = this.field_70161_v - this.field_70166_s;
    float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
    if (f2 > 1.0F)
      f2 = 1.0F; 
    this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.4F;
    this.field_184619_aG += this.field_70721_aZ;
  }
  
  public boolean func_70617_f_() {
    return false;
  }
  
  private double func_82214_u(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.field_70165_t; 
    float f = (this.field_70761_aq + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76134_b(f);
    return this.field_70165_t + f1 * 1.25D;
  }
  
  private double func_82208_v(int p_82208_1_) {
    return (p_82208_1_ <= 0) ? (this.field_70163_u + 2.25D) : (this.field_70163_u + 2.25D);
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
  
  public void setPartAttackTarget(@Nullable EntityTameBase part, @Nullable EntityLivingBase entitylivingbaseIn) {
    if (part != null)
      part.func_70624_b(entitylivingbaseIn); 
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (getSize() > 250000 && amount < 20.0F)
      return false; 
    if (source.func_76355_l() == "chaosImplosion")
      amount *= 0.2F; 
    if (source.func_94541_c())
      amount *= 5.0F; 
    if (amount > 5000.0F && source.func_94541_c() && !doesntContainACommandBlock() && getSize() >= 50000 && !this.field_70170_p.field_72995_K) {
      EntityWitherStorm entityzombie = new EntityWitherStorm(this.field_70170_p);
      entityzombie.func_82149_j((Entity)this);
      entityzombie.func_94061_f(func_175446_cd());
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      entityzombie.setOwnerId(func_184753_b());
      entityzombie.setNotContainingCommandBlock(true);
      entityzombie.field_70159_w = this.field_70146_Z.nextDouble() - 0.5D;
      entityzombie.field_70179_y = this.field_70146_Z.nextDouble() - 0.5D;
      Grow(getSize() - 12500);
    } 
    if (func_180431_b(source))
      return false; 
    if (!source.func_76352_a() && !source.func_82725_o() && source != DamageSource.field_76371_c && source != DamageSource.field_76370_b && source != DamageSource.field_76372_a && source != DamageSource.field_76368_d && source != DamageSource.field_76379_h && source != DamageSource.field_76369_e) {
      if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityLivingBase) {
        func_70624_b((EntityLivingBase)source.func_76346_g());
        if (this.centerHead != null)
          this.centerHead.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.leftHead != null)
          this.leftHead.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.rightHead != null)
          this.rightHead.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacle1 != null)
          this.tentacle1.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacle2 != null)
          this.tentacle2.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacle3 != null)
          this.tentacle3.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacle4 != null)
          this.tentacle4.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacle5 != null)
          this.tentacle5.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacledevourer1 != null)
          this.tentacledevourer1.func_70624_b((EntityLivingBase)source.func_76346_g()); 
        if (this.tentacledevourer2 != null)
          this.tentacledevourer2.func_70624_b((EntityLivingBase)source.func_76346_g()); 
      } 
      return super.func_70097_a(source, amount);
    } 
    return false;
  }
  
  protected void func_70623_bb() {
    this.field_70708_bq = 0;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
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
  
  public int getSize() {
    return ((Integer)this.field_70180_af.func_187225_a(SIZE)).intValue();
  }
  
  public void Grow(int p_82215_1_) {
    if (!this.field_70170_p.field_72995_K) {
      this.field_70180_af.func_187227_b(SIZE, Integer.valueOf(doesntContainACommandBlock() ? 12500 : p_82215_1_));
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(doesntContainACommandBlock() ? 1000.0D : p_82215_1_);
      if (p_82215_1_ == 12500 && !isWild())
        for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i)
          entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation(doesntContainACommandBlock() ? "\u00A75 A Wither Storm has fissioned!" : ("\u00A75" + getOwner().func_70005_c_() + "'s Wither Storm has grown to Destroyer form!!"), new Object[0]), true);  
    } 
  }
  
  public boolean doesntContainACommandBlock() {
    return ((Boolean)this.field_70180_af.func_187225_a(DOESNT_HAVE_COMMAND_BLOCK)).booleanValue();
  }
  
  public void setNotContainingCommandBlock(boolean p_82215_1_) {
    if (p_82215_1_)
      this.field_110153_bc = Float.MAX_VALUE; 
    this.field_70180_af.func_187227_b(DOESNT_HAVE_COMMAND_BLOCK, Boolean.valueOf(p_82215_1_));
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return doesntContainACommandBlock() ? ELoot.ENTITIES_WITHER_STORM_MULAGEN : ELoot.ENTITIES_WITHER_STORM;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  protected void func_70609_aI() {
    BlockPos blockpos = func_180425_c();
    if (getGrowingAge() < 50000) {
      if (this.tentacledevourer1 != null)
        this.tentacledevourer1.func_70106_y(); 
      if (this.tentacledevourer2 != null)
        this.tentacledevourer2.func_70106_y(); 
    } 
    if (doesntContainACommandBlock()) {
      this.deathTicks++;
      super.func_70609_aI();
    } else {
      this.deathTicks++;
      if (getSize() > 12500 && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175707_a(blockpos, blockpos))
        for (int j = 0; j < ((getSize() > 50000) ? 20 : 10); j++) {
          this.field_70170_p.func_175656_a(func_180425_c().func_177981_b(j), Blocks.field_150343_Z.func_176223_P());
          EntityFallingBlock deathBlocks = new EntityFallingBlock(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D + j, this.field_70161_v, this.field_70170_p.func_180495_p(func_180425_c().func_177981_b(j)));
          deathBlocks.field_70159_w += this.field_70146_Z.nextDouble() * 4.0D - 2.0D;
          deathBlocks.field_70181_x -= this.field_70146_Z.nextDouble();
          deathBlocks.field_70179_y += this.field_70146_Z.nextDouble() * 4.0D - 2.0D;
          deathBlocks.func_145806_a(true);
          this.field_70170_p.func_72838_d((Entity)deathBlocks);
          Grow(getSize() - 3);
        }  
      if (this.deathTicks % 20 == 0)
        this.field_70725_aQ++; 
      if (func_110143_aJ() <= 0.0F) {
        func_70624_b((EntityLivingBase)null);
        float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 12.0F;
        float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 36.0F;
        float f17 = (this.field_70146_Z.nextFloat() - 0.5F) * 12.0F;
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f13, this.field_70163_u - 4.0D + f15, this.field_70161_v + f17, 0.0D, 0.0D, 0.0D, new int[0]);
      } 
      if (!this.field_70170_p.field_72995_K)
        if (this.deathTicks == 1)
          if (getOwner() != null) {
            for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
              this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
              entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s Wither Storm has been killed!!!", new Object[0]), true);
            } 
            ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Wither Storm has been destroyed!", new Object[0]));
          }   
      if (this.deathTicks == 80)
        if (this.tentacle1 != null && this.tentacle1.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacle1.field_70165_t, this.tentacle1.field_70163_u, this.tentacle1.field_70161_v, 6.0F, EngenderConfig.mobs.grief); 
          this.tentacle1.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacle1.field_70181_x = 0.800000011920929D;
          this.tentacle1.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 100)
        if (this.tentacle2 != null && this.tentacle2.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacle2.field_70165_t, this.tentacle2.field_70163_u, this.tentacle2.field_70161_v, 6.0F, EngenderConfig.mobs.grief); 
          this.tentacle2.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacle2.field_70181_x = 0.800000011920929D;
          this.tentacle2.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 110)
        if (this.tentacledevourer1 != null && this.tentacledevourer1.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacledevourer1.field_70165_t, this.tentacledevourer1.field_70163_u, this.tentacledevourer1.field_70161_v, 15.0F, EngenderConfig.mobs.grief); 
          this.tentacledevourer1.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacledevourer1.field_70181_x = 0.800000011920929D;
          this.tentacledevourer1.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 150) {
        if (this.tentacle4 != null && this.tentacle4.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacle4.field_70165_t, this.tentacle4.field_70163_u, this.tentacle4.field_70161_v, 6.0F, EngenderConfig.mobs.grief); 
          this.tentacle4.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacle4.field_70181_x = 0.800000011920929D;
          this.tentacle4.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        } 
        if (this.tentacle3 != null && this.tentacle3.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacle3.field_70165_t, this.tentacle3.field_70163_u, this.tentacle3.field_70161_v, 6.0F, EngenderConfig.mobs.grief); 
          this.tentacle3.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacle3.field_70181_x = 0.800000011920929D;
          this.tentacle3.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        } 
      } 
      if (this.deathTicks == 180) {
        if (this.tentacle5 != null && this.tentacle5.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacle5.field_70165_t, this.tentacle5.field_70163_u, this.tentacle5.field_70161_v, 6.0F, EngenderConfig.mobs.grief); 
          this.tentacle5.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacle5.field_70181_x = 0.800000011920929D;
          this.tentacle5.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        } 
        if (this.tentacledevourer2 != null && this.tentacledevourer2.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.tentacledevourer2.field_70165_t, this.tentacledevourer2.field_70163_u, this.tentacledevourer2.field_70161_v, 15.0F, EngenderConfig.mobs.grief); 
          this.tentacledevourer2.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.tentacledevourer2.field_70181_x = 0.800000011920929D;
          this.tentacledevourer2.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        } 
      } 
      if (this.deathTicks == 180)
        if (this.leftHead != null && this.leftHead.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.leftHead.field_70165_t, this.leftHead.field_70163_u, this.leftHead.field_70161_v, 9.0F, EngenderConfig.mobs.grief); 
          this.leftHead.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.leftHead.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 200)
        if (this.rightHead != null && this.rightHead.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          if (!this.field_70170_p.field_72995_K)
            createEngenderModExplosionFireless((Entity)this, this.rightHead.field_70165_t, this.rightHead.field_70163_u, this.rightHead.field_70161_v, 9.0F, EngenderConfig.mobs.grief); 
          this.rightHead.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
          this.rightHead.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks >= 300 && !this.field_70170_p.field_72995_K) {
        if (this.centerHead != null && this.centerHead.residentWitherStorm != null) {
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), 2.0F);
          createEngenderModExplosionFireless((Entity)this, this.centerHead.field_70165_t, this.centerHead.field_70163_u, this.centerHead.field_70161_v, 9.0F, EngenderConfig.mobs.grief);
          double d01 = this.centerHead.field_70165_t - this.field_70165_t;
          double d21 = this.centerHead.field_70161_v - this.field_70161_v;
          float f2 = MathHelper.func_76133_a(d01 * d01 + d21 * d21);
          this.centerHead.field_70159_w = d01 / f2 * 0.6D * 0.6D + this.centerHead.field_70159_w;
          this.centerHead.field_70179_y = d21 / f2 * 0.6D * 0.6D + this.centerHead.field_70179_y;
        } 
        List<EntityFallingBlock> list = this.field_70170_p.func_175647_a(EntityFallingBlock.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty() && this.field_70170_p.field_72995_K)
          for (int j = 0; j < list.size(); j++) {
            EntityFallingBlock entity = list.get(j);
            if (entity != null) {
              createEngenderModExplosionFireless((Entity)this, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, 2.0F, false);
              entity.func_70106_y();
            } 
          }  
        for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i)
          this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F); 
        int i = getSize();
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, j));
        } 
        EntityItem entityitem = func_70099_a(new ItemStack(EItem.witheredNetherStar), 0.0F);
        if (entityitem != null)
          entityitem.func_174873_u(); 
        if (!this.field_70170_p.field_72995_K)
          for (EntityWitherStorm allmulegans : this.field_70170_p.func_72872_a(EntityWitherStorm.class, func_174813_aQ().func_186662_g(256.0D))) {
            if (allmulegans.doesntContainACommandBlock())
              allmulegans.func_70606_j(0.0F); 
          }  
        for (int i1 = 0; i1 < getSize() / 10000; i1++)
          func_70099_a(new ItemStack(Blocks.field_150343_Z, 64), 0.0F); 
        func_70106_y();
      } 
    } 
  }
  
  class AILookAround extends EntityAIBase {
    private EntityWitherStorm witherStorm = EntityWitherStorm.this;
    
    public AILookAround() {
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      return true;
    }
    
    public void func_75246_d() {
      if (this.witherStorm.func_70638_az() != null) {
        this.witherStorm.func_70671_ap().func_75651_a((Entity)this.witherStorm.func_70638_az(), 3.0F, 0.0F);
      } else if (this.witherStorm.centerHead != null && this.witherStorm.centerHead.func_184207_aI()) {
        Vec3d vec3 = this.witherStorm.centerHead.func_184179_bs().func_70676_i(1.0F);
        this.witherStorm.func_70671_ap().func_75650_a((this.witherStorm.centerHead.func_184179_bs()).field_70165_t + vec3.field_72450_a * 8.0D, (this.witherStorm.centerHead.func_184179_bs()).field_70163_u + vec3.field_72448_b * 8.0D, (this.witherStorm.centerHead.func_184179_bs()).field_70161_v + vec3.field_72449_c * 8.0D, 180.0F, 0.0F);
      } else {
        this.witherStorm.func_70671_ap().func_75650_a(this.witherStorm.func_70605_aq().func_179917_d(), this.witherStorm.field_70163_u, this.witherStorm.func_70605_aq().func_179918_f(), 3.0F, 0.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityWitherStorm witherStorm;
    
    public AIRandomFly(EntityWitherStorm ghast) {
      this.witherStorm = ghast;
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      EntityMoveHelper entitymovehelper = this.witherStorm.func_70605_aq();
      if (!entitymovehelper.func_75640_a())
        return true; 
      double d0 = entitymovehelper.func_179917_d() - this.witherStorm.field_70165_t;
      double d1 = entitymovehelper.func_179919_e() - this.witherStorm.field_70163_u;
      double d2 = entitymovehelper.func_179918_f() - this.witherStorm.field_70161_v;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return (d3 < 1.0D || d3 > 3600.0D);
    }
    
    public boolean func_75253_b() {
      return false;
    }
    
    public void func_75249_e() {
      Random random = this.witherStorm.func_70681_au();
      if (this.witherStorm.getOwner() != null) {
        double d0 = (this.witherStorm.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 48.0F);
        double d1 = (this.witherStorm.getOwner()).field_70163_u + 40.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = (this.witherStorm.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 48.0F);
        if (this.witherStorm.func_70093_af()) {
          d0 = (this.witherStorm.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
          d1 = (this.witherStorm.getOwner()).field_70163_u - 60.0D - ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          d2 = (this.witherStorm.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
        } 
        if (this.witherStorm.centerHead.func_184207_aI()) {
          Vec3d vec3 = this.witherStorm.getOwner().func_70676_i(1.0F);
          d0 = this.witherStorm.field_70165_t + vec3.field_72450_a * 8.0D;
          d1 = this.witherStorm.field_70163_u + vec3.field_72448_b * 8.0D;
          d2 = this.witherStorm.field_70161_v + vec3.field_72449_c * 8.0D;
        } 
        this.witherStorm.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
      } else {
        EntityPlayer player = this.witherStorm.field_70170_p.func_72890_a((Entity)this.witherStorm, 256.0D);
        if (this.witherStorm.func_70638_az() != null) {
          double d0 = (this.witherStorm.func_70638_az()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = this.witherStorm.field_70170_p.func_175672_r(this.witherStorm.func_180425_c()).func_177956_o() + 48.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.witherStorm.func_70638_az()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.witherStorm.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else if (player != null && this.witherStorm.func_70068_e((Entity)player) > 2304.0D) {
          double d0 = player.field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = player.field_70163_u + 48.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = player.field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.witherStorm.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else {
          double d0 = this.witherStorm.field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = this.witherStorm.field_70163_u + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = this.witherStorm.field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          if (d1 < 32.0D)
            d1 = 32.0D; 
          this.witherStorm.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } 
      } 
    }
  }
  
  static class WitherStormMoveHelper extends EntityMoveHelper {
    private EntityWitherStorm witherStorm;
    
    private int courseChangeCooldown;
    
    public WitherStormMoveHelper(EntityWitherStorm ghast) {
      super((EntityLiving)ghast);
      this.witherStorm = ghast;
    }
    
    public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
        double d0 = this.field_75646_b - this.witherStorm.field_70165_t;
        double d1 = this.field_75647_c - this.witherStorm.field_70163_u;
        double d2 = this.field_75644_d - this.witherStorm.field_70161_v;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (this.courseChangeCooldown-- <= 0) {
          this.courseChangeCooldown += this.witherStorm.func_70681_au().nextInt(5) + 2;
          d3 = MathHelper.func_76133_a(d3);
          if (this.witherStorm.getOwner() != null && this.witherStorm.func_70068_e((Entity)this.witherStorm.getOwner()) > 5184.0D && this.witherStorm.getGuardBlock() == null) {
            this.witherStorm.field_70159_w += d0 / d3 * 0.2D;
            this.witherStorm.field_70181_x += d1 / d3 * 0.2D;
            this.witherStorm.field_70179_y += d2 / d3 * 0.2D;
          } else if (this.witherStorm.moralRaisedTimer > 200) {
            this.witherStorm.field_70159_w += d0 / d3 * 0.2D;
            this.witherStorm.field_70181_x += d1 / d3 * 0.2D;
            this.witherStorm.field_70179_y += d2 / d3 * 0.2D;
          } else {
            this.witherStorm.field_70159_w += d0 / d3 * 0.1D;
            this.witherStorm.field_70181_x += d1 / d3 * 0.1D;
            this.witherStorm.field_70179_y += d2 / d3 * 0.1D;
          } 
        } 
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      return true;
    }
  }
}
