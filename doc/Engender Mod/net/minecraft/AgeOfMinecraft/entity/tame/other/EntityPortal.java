package net.minecraft.AgeOfMinecraft.entity.tame.other;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityMooshroom;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityParrot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySnowman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityPortal extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Structure {
  private static final DataParameter<Integer> TOWER1_TARGET = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER2_TARGET = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER3_TARGET = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER4_TARGET = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer>[] TARGETS = new DataParameter[] { TOWER1_TARGET, TOWER2_TARGET, TOWER3_TARGET, TOWER4_TARGET };
  
  private static final DataParameter<Integer> LIGHTNINGTIMER = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> METADATA = EntityDataManager.func_187226_a(EntityPortal.class, DataSerializers.field_187192_b);
  
  private int[] towerUpdate = new int[4];
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
  public MultiPartEntityPart[] partArray;
  
  public MultiPartEntityPart portal = new MultiPartEntityPart(this, "portal", 4.0F, 1.0F);
  
  public MultiPartEntityPart tower1 = new MultiPartEntityPart(this, "tower1", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower2 = new MultiPartEntityPart(this, "tower2", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower3 = new MultiPartEntityPart(this, "tower3", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower4 = new MultiPartEntityPart(this, "tower4", 1.0F, 4.0F);
  
  public MultiPartEntityPart side1 = new MultiPartEntityPart(this, "side1", 1.0F, 1.0F);
  
  public MultiPartEntityPart side2 = new MultiPartEntityPart(this, "side2", 1.0F, 1.0F);
  
  public MultiPartEntityPart side3 = new MultiPartEntityPart(this, "side3", 1.0F, 1.0F);
  
  public MultiPartEntityPart side4 = new MultiPartEntityPart(this, "side4", 1.0F, 1.0F);
  
  public EntityPortal(World worldIn) {
    super(worldIn);
    this.partArray = new MultiPartEntityPart[] { this.portal, this.tower1, this.tower2, this.tower3, this.tower4, this.side1, this.side2, this.side3, this.side4 };
    func_70105_a(4.0F, 1.0F);
    this.reachWidth = 6.0F;
    this.field_70178_ae = true;
    func_184185_a(ESound.portalMake, 100.0F, 1.0F);
    func_184185_a(ESound.portalAmbient, 5.0F, 1.0F);
    this.field_70728_aV = 18000;
    setLevel(300);
    func_70012_b((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0.0F, -90.0F);
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "portal";
  }
  
  public int getMetaData() {
    return ((Integer)this.field_70180_af.func_187225_a(METADATA)).intValue();
  }
  
  public void setMetaData(int p_82215_1_) {
    this.field_70180_af.func_187227_b(METADATA, Integer.valueOf(p_82215_1_));
  }
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.CONSTRUCT;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(METADATA, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TOWER1_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TOWER2_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TOWER3_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TOWER4_TARGET, Integer.valueOf(0));
  }
  
  public int getWatchedTargetId(int p_82203_1_) {
    return ((Integer)this.field_70180_af.func_187225_a(TARGETS[p_82203_1_])).intValue();
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.field_70180_af.func_187227_b(TARGETS[targetOffset], Integer.valueOf(newId));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(96.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("MetaData", getMetaData());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setMetaData(tagCompund.func_74762_e("MetaData"));
  }
  
  public boolean canUseGuardBlock() {
    return false;
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
    return 0.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 0.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 0.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
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
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    super.func_70074_a(entityLivingIn);
    EntityLightningBolt shot = new EntityLightningBolt(this.field_70170_p, entityLivingIn.field_70165_t - 0.5D, entityLivingIn.field_70163_u, entityLivingIn.field_70161_v - 0.5D, true);
    float f = -((float)(MathHelper.func_181159_b(shot.field_70161_v - this.field_70161_v, shot.field_70161_v - this.field_70161_v) * 57.29577951308232D)) - 90.0F;
    shot.field_70177_z = f;
    this.field_70170_p.func_72942_c((Entity)shot);
    entityLivingIn.func_70077_a(shot);
    entityLivingIn.field_70181_x += 4.0D;
    if (entityLivingIn instanceof EntityCreeper || entityLivingIn instanceof net.minecraft.entity.monster.EntityZombie || entityLivingIn instanceof net.minecraft.entity.monster.AbstractSkeleton) {
      EntityCreeper creeper = new EntityCreeper(this.field_70170_p);
      if (!this.field_70170_p.field_72995_K)
        this.field_70170_p.func_72838_d((Entity)creeper); 
      creeper.func_82149_j((Entity)entityLivingIn);
      creeper.func_70077_a(shot);
      entityLivingIn.func_70645_a(DamageSource.func_76358_a((EntityLivingBase)creeper).func_76348_h());
      creeper.func_70106_y();
      entityLivingIn.field_70159_w = 0.0D;
      entityLivingIn.field_70179_y = 0.0D;
      entityLivingIn.func_70653_a((Entity)shot, 2.0F, MathHelper.func_76126_a(shot.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(shot.field_70177_z * 0.017453292F));
      entityLivingIn.field_70181_x = 0.0D;
      if (entityLivingIn.field_70160_al) {
        entityLivingIn.field_70181_x += this.field_70146_Z.nextDouble() * 1.5D;
      } else {
        entityLivingIn.field_70181_x += this.field_70146_Z.nextDouble() * 3.0D;
      } 
    } 
    if (entityLivingIn.field_70131_O < 2.0F && entityLivingIn.field_70130_N < 2.0F && entityLivingIn.func_184222_aU()) {
      entityLivingIn.func_184185_a(SoundEvents.field_187539_bB, 2.0F, 2.0F);
      entityLivingIn.func_184185_a(SoundEvents.field_187659_cY, 2.0F, 2.0F);
      entityLivingIn.func_184185_a(SoundEvents.field_187662_cZ, 2.0F, 2.0F);
      entityLivingIn.field_70170_p.func_72960_a((Entity)entityLivingIn, (byte)20);
      entityLivingIn.func_70106_y();
    } 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    int i = MathHelper.func_76123_f((distance - 6.0F) * damageMultiplier);
    if (i > 0) {
      func_184185_a(ESound.golemSmash, 10.0F, 0.75F);
      func_184185_a(ESound.golemSmash, 10.0F, 0.5F);
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(4.0D + i, 2.0D, 4.0D + i), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null) {
            if (!func_184191_r((Entity)entity)) {
              entity.field_70181_x += 1.0D + i * 0.05D;
              entity.func_70097_a(DamageSource.func_94539_a((Explosion)null), 5.0F + i);
              entity.field_70160_al = true;
              float f = MathHelper.func_76129_c(MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) + -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
              entity.field_70159_w /= 2.0D;
              entity.field_70179_y /= 2.0D;
              entity.field_70159_w -= (MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) / f) * 1.0D;
              entity.field_70179_y -= (-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) / f) * 1.0D;
            } 
            if (EngenderConfig.general.useMessage && !entity.func_70089_S() && !isWild())
              getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was blown up by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
        }  
    } 
  }
  
  public boolean func_70652_k(Entity entityIn) {
    super.func_70652_k(entityIn);
    func_184185_a(ESound.lightningShot, 10.0F, 1.0F);
    if (entityIn instanceof EntityLivingBase) {
      entityIn.field_70181_x++;
      if (!entityIn.func_70089_S() && !entityIn.field_70128_L)
        func_70074_a((EntityLivingBase)entityIn); 
      ((EntityLivingBase)entityIn).func_70653_a((Entity)this, 1.0F, MathHelper.func_76126_a(entityIn.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(entityIn.field_70177_z * 0.017453292F));
      if (!(entityIn instanceof EntityTameBase))
        entityIn.field_70181_x += this.field_70146_Z.nextDouble(); 
    } 
    entityIn.func_70015_d(100);
    if (entityIn instanceof EntityTameBase && ((EntityTameBase)entityIn).getFakeHealth() > 0.0F) {
      entityIn.field_70181_x = 0.0D;
      entityIn.func_70066_B();
    } 
    if (entityIn instanceof EntityLivingBase && entityIn instanceof IEntityMultiPart) {
      ((EntityLivingBase)entityIn).field_70159_w = 0.0D;
      ((EntityLivingBase)entityIn).field_70181_x = 0.0D;
      ((EntityLivingBase)entityIn).field_70179_y = 0.0D;
    } 
    return true;
  }
  
  private double func_82214_u(int p_82214_1_) {
    switch (p_82214_1_) {
      case 0:
        return this.field_70165_t + 3.25D;
      case 1:
        return this.field_70165_t + 3.25D;
      case 2:
        return this.field_70165_t - 3.25D;
      case 3:
        return this.field_70165_t - 3.25D;
    } 
    return this.field_70165_t;
  }
  
  private double func_82208_v(int p_82208_1_) {
    return this.field_70163_u + 3.5D;
  }
  
  private double func_82213_w(int p_82213_1_) {
    switch (p_82213_1_) {
      case 0:
        return this.field_70161_v + 3.25D;
      case 1:
        return this.field_70161_v - 3.25D;
      case 2:
        return this.field_70161_v + 3.25D;
      case 3:
        return this.field_70161_v - 3.25D;
    } 
    return this.field_70161_v;
  }
  
  public void func_70636_d() {
    if (!this.field_70170_p.field_72995_K && EngenderConfig.mobs.grief) {
      int x = MathHelper.func_76128_c(this.field_70165_t);
      int y = MathHelper.func_76128_c(this.field_70163_u);
      int z = MathHelper.func_76128_c(this.field_70161_v);
      for (int x1 = -3; x1 <= 3; x1++) {
        for (int z1 = -3; z1 <= 3; z1++) {
          for (int y1 = 0; y1 <= 3; y1++) {
            BlockPos blockpos = new BlockPos(x + x1, y + y1, z + z1);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            if (!block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && block.func_176195_g(iblockstate, this.field_70170_p, blockpos) >= 0.0F)
              this.field_70170_p.func_175655_b(blockpos, true); 
          } 
        } 
      } 
    } 
    if (this.field_70170_p.field_72995_K || isWild() || !(getOwner() instanceof EntityPlayer) || getMetaData() >= 4);
    float f = (getJukeboxToDanceTo() != null) ? (MathHelper.func_76134_b(this.field_70173_aa * 0.25F) * 0.25F) : (MathHelper.func_76134_b(this.field_70173_aa * 0.05F) * 0.25F);
    this.portal.func_70071_h_();
    this.portal.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0F, 0.0F);
    this.tower1.func_70071_h_();
    this.tower1.func_70012_b(this.field_70165_t + 2.75D + f, this.field_70163_u + 0.325D, this.field_70161_v + 2.75D + f, 0.0F, 0.0F);
    this.tower2.func_70071_h_();
    this.tower2.func_70012_b(this.field_70165_t + 2.75D + f, this.field_70163_u + 0.325D, this.field_70161_v - 2.75D - f, 0.0F, 0.0F);
    this.tower3.func_70071_h_();
    this.tower3.func_70012_b(this.field_70165_t - 2.75D - f, this.field_70163_u + 0.325D, this.field_70161_v - 2.75D - f, 0.0F, 0.0F);
    this.tower4.func_70071_h_();
    this.tower4.func_70012_b(this.field_70165_t - 2.75D - f, this.field_70163_u + 0.325D, this.field_70161_v + 2.75D + f, 0.0F, 0.0F);
    this.side1.func_70071_h_();
    this.side1.func_70012_b(this.field_70165_t + 2.5D, this.field_70163_u + 0.5D, this.field_70161_v, 0.0F, 0.0F);
    this.side1.func_174826_a(new AxisAlignedBB(this.side1.field_70165_t - 0.5D, this.side1.field_70163_u, this.side1.field_70161_v - 2.0D, this.side1.field_70165_t + 0.5D, this.side1.field_70163_u + 1.0D, this.side1.field_70161_v + 2.0D));
    this.side2.func_70071_h_();
    this.side2.func_70012_b(this.field_70165_t - 2.5D, this.field_70163_u + 0.5D, this.field_70161_v, 0.0F, 0.0F);
    this.side2.func_174826_a(new AxisAlignedBB(this.side2.field_70165_t - 0.5D, this.side2.field_70163_u, this.side2.field_70161_v - 2.0D, this.side2.field_70165_t + 0.5D, this.side2.field_70163_u + 1.0D, this.side2.field_70161_v + 2.0D));
    this.side3.func_70071_h_();
    this.side3.func_70012_b(this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v + 2.5D, 0.0F, 0.0F);
    this.side3.func_174826_a(new AxisAlignedBB(this.side3.field_70165_t - 2.0D, this.side3.field_70163_u, this.side3.field_70161_v - 0.5D, this.side3.field_70165_t + 2.0D, this.side3.field_70163_u + 1.0D, this.side3.field_70161_v + 0.5D));
    this.side4.func_70071_h_();
    this.side4.func_70012_b(this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v - 2.5D, 0.0F, 0.0F);
    this.side4.func_174826_a(new AxisAlignedBB(this.side4.field_70165_t - 2.0D, this.side4.field_70163_u, this.side4.field_70161_v - 0.5D, this.side4.field_70165_t + 2.0D, this.side4.field_70163_u + 1.0D, this.side4.field_70161_v + 0.5D));
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 4; i++) {
        if (func_70643_av() != null)
          updateWatchedTargetId(i, func_70643_av().func_145782_y()); 
        if (this.field_70173_aa > 80 && func_70089_S() && this.field_70173_aa >= this.towerUpdate[i]) {
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.field_70170_p.func_73045_a(i1);
            if (entity != null && entity.func_70089_S() && func_70032_d(entity) <= func_110148_a(SharedMonsterAttributes.field_111265_b).func_111125_b() && func_70685_l(entity)) {
              if (func_184191_r(entity))
                updateWatchedTargetId(i, 0); 
              double d1 = func_82214_u(i);
              double d2 = func_82208_v(i);
              double d3 = func_82213_w(i);
              fireLightning(entity, d1, d2, d3);
              if (this.moralRaisedTimer > 200) {
                this.towerUpdate[i] = this.field_70173_aa + 1;
              } else if (getMetaData() > 1) {
                this.towerUpdate[i] = this.field_70173_aa + 30;
              } else {
                this.towerUpdate[i] = this.field_70173_aa + 60;
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e()), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
            for (int k1 = 0; k1 < 10 && !list1.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase = list1.get(this.field_70146_Z.nextInt(list1.size()));
              if (entitylivingbase != this && entitylivingbase.func_70089_S() && func_70685_l((Entity)entitylivingbase) && !func_184191_r((Entity)entitylivingbase)) {
                if (entitylivingbase instanceof EntityPlayer) {
                  if (!((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a)
                    updateWatchedTargetId(i, entitylivingbase.func_145782_y()); 
                } else {
                  updateWatchedTargetId(i, entitylivingbase.func_145782_y());
                } 
              } else {
                list1.remove(entitylivingbase);
              } 
            } 
          } 
        } 
      }  
    this.field_70728_aV = 6000 * (1 + getMetaData());
    if (getMetaData() > 0) {
      func_70690_d(new PotionEffect(MobEffects.field_76429_m, 2147483647, (getMetaData() > 2) ? 1 : 0));
      func_70690_d(new PotionEffect(MobEffects.field_76420_g, 2147483647, (getMetaData() > 2) ? 1 : 0));
    } 
    List<Entity> list = this.field_70170_p.func_175674_a((Entity)this, func_174813_aQ().func_72314_b(1.0D, 4.0D, 1.0D).func_72317_d(0.0D, 2.0D, 0.0D), EntitySelectors.field_94557_a);
    if (!list.isEmpty())
      for (int l = 0; l < list.size(); l++) {
        Entity[] aentity = func_70021_al();
        if (aentity != null)
          for (Entity part : aentity) {
            List<Entity> partlist = this.field_70170_p.func_175674_a((Entity)this, part.func_174813_aQ(), EntitySelectors.field_94557_a);
            if (!partlist.isEmpty())
              for (int l1 = 0; l1 < partlist.size(); l1++) {
                Entity entity = partlist.get(l1);
                if (entity instanceof EntityLivingBase && !entity.field_70145_X && !(entity instanceof IEntityMultiPart)) {
                  if (entity.field_70132_H)
                    entity.func_70097_a(DamageSource.field_76368_d, 1.0F); 
                  part.func_70108_f(entity);
                  entity.func_70108_f(part);
                  entity.field_70181_x += 0.1D;
                } 
              }  
          }  
      }  
    this.field_70159_w = 0.0D;
    this.field_70179_y = 0.0D;
    if (this.field_70181_x > 0.0D)
      this.field_70181_x = 0.0D; 
    super.func_70636_d();
    this.field_70760_ar = this.field_70761_aq = this.field_70126_B = this.field_70177_z = this.field_70758_at = this.field_70759_as = 0.0F;
    this.field_70127_C = this.field_70125_A = 90.0F;
    this.field_70160_al = false;
    this.field_70122_E = true;
    func_70031_b(false);
    if (this.field_70170_p.field_72995_K)
      if (func_70089_S())
        for (int i = 0; i < 3 && this.field_70173_aa > 60; i++) {
          if (this.towerUpdate[i] > 20)
            this.field_70170_p.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, func_82214_u(i), func_82208_v(i), func_82208_v(i), ((float)func_82214_u(i) + this.field_70146_Z.nextFloat()) - 0.5D, ((float)func_82208_v(i) - this.field_70146_Z.nextFloat() - 1.0F), ((float)func_82208_v(i) + this.field_70146_Z.nextFloat()) - 0.5D, new int[0]); 
          if (getMetaData() > 0)
            this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.0D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 1.0D, 0.0D, 0.0D, new int[0]); 
          if (getMetaData() > 1) {
            this.field_70170_p.func_175682_a(EnumParticleTypes.SPELL_INSTANT, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
            this.field_70170_p.func_175682_a(EnumParticleTypes.SPELL_INSTANT, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
            this.field_70170_p.func_175682_a(EnumParticleTypes.SPELL_INSTANT, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
            this.field_70170_p.func_175682_a(EnumParticleTypes.SPELL_INSTANT, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          } 
          if (getMetaData() > 2)
            this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 1.0D, 0.0D, 0.5D, new int[0]); 
          if (getMetaData() > 3) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
            this.field_70170_p.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
            this.field_70170_p.func_175688_a(EnumParticleTypes.FLAME, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
          } 
          this.field_70170_p.func_175688_a(EnumParticleTypes.TOWN_AURA, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.0D + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D + this.field_70146_Z.nextDouble() * 0.1D - 0.05D, 0.0D + this.field_70146_Z.nextDouble() * 0.2D, 0.0D + this.field_70146_Z.nextDouble() * 0.1D - 0.05D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.5D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.FIREWORKS_SPARK, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.FIREWORKS_SPARK, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.FIREWORKS_SPARK, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.FIREWORKS_SPARK, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        }   
    if ((this.field_70173_aa + func_145782_y()) % ((getJukeboxToDanceTo() != null) ? 20 : 200) == 0)
      func_184185_a(ESound.portalWhoosh, 5.0F, 1.0F); 
    if ((this.field_70173_aa + func_145782_y()) % 670 == 0)
      func_184185_a(ESound.portalAmbient, 5.0F, 1.0F); 
    if (this.field_70173_aa + func_145782_y() > 60)
      if ((this.field_70173_aa + func_145782_y()) % ((getJukeboxToDanceTo() != null) ? 20 : ((getMetaData() > 2) ? 50 : 100)) == 0 && this.field_70146_Z.nextInt((getJukeboxToDanceTo() != null) ? 5 : 10) == 0) {
        EntityBat bat;
        EntityEndermite endermite;
        EntityCreeper creeper;
        EntityBlaze blaze;
        EntityElderGuardian elderguardian;
        EntityChicken chicken;
        EntitySilverfish silverfish;
        EntityMagmaCube magmacube;
        EntityCaveSpider cavespider;
        EntityGiant giant;
        EntityCow cow;
        EntitySnowman snowgolem;
        EntitySkeleton skeleton;
        EntityEnderman enderman;
        EntityIronGolem irongolem;
        EntityMooshroom mooshroom;
        EntitySquid squid;
        EntitySlime slime;
        EntityGhast ghast;
        EntityWither wither;
        EntityOcelot ocelot;
        EntityVillager villager;
        EntitySpider spider;
        EntityGuardian guardian;
        EntityEvoker evoker;
        EntityPig pig;
        EntityWolf wolf;
        EntityZombie zombie;
        EntityPigZombie pigzombie;
        EntityIllusioner illusioner;
        EntityRabbit rabbit;
        EntityLlama llama;
        EntityVex vex;
        EntityRabbit killerrabbit;
        EntitySheep sheep;
        EntitySkeleton witherskeleton;
        EntityParrot parrot;
        EntityShulker shulker;
        EntityWitch witch;
        EntityZombie entityZombie1;
        EntitySkeleton stray;
        EntityVindicator vindicator;
        func_184185_a(ESound.portalWhoosh, 10.0F, func_70647_i() + 1.9F);
        int i = 0;
        if (this.field_70146_Z.nextInt(2) == 0)
          i++; 
        if (this.field_70146_Z.nextInt(3) == 0)
          i++; 
        if (this.field_70146_Z.nextInt(6) == 0)
          i++; 
        if (this.field_70146_Z.nextInt(12) == 0)
          i++; 
        switch (i) {
          case 0:
            switch (this.field_70146_Z.nextInt(9)) {
              case 0:
                bat = new EntityBat(this.field_70170_p);
                bat.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  bat.setOwnerId(func_184753_b()); 
                bat.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)bat)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)bat); 
                break;
              case 1:
                chicken = new EntityChicken(this.field_70170_p);
                chicken.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  chicken.setOwnerId(func_184753_b()); 
                chicken.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)chicken)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)chicken); 
                break;
              case 2:
                cow = new EntityCow(this.field_70170_p);
                cow.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cow.setOwnerId(func_184753_b()); 
                cow.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)cow)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)cow); 
                break;
              case 3:
                mooshroom = new EntityMooshroom(this.field_70170_p);
                mooshroom.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  mooshroom.setOwnerId(func_184753_b()); 
                mooshroom.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)mooshroom)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)mooshroom); 
                break;
              case 4:
                ocelot = new EntityOcelot(this.field_70170_p);
                ocelot.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ocelot.setOwnerId(func_184753_b()); 
                ocelot.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)ocelot)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)ocelot); 
                break;
              case 5:
                pig = new EntityPig(this.field_70170_p);
                pig.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pig.setOwnerId(func_184753_b()); 
                pig.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)pig)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)pig); 
                break;
              case 6:
                rabbit = new EntityRabbit(this.field_70170_p);
                rabbit.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  rabbit.setOwnerId(func_184753_b()); 
                rabbit.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)rabbit)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)rabbit); 
                break;
              case 7:
                sheep = new EntitySheep(this.field_70170_p);
                sheep.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  sheep.setOwnerId(func_184753_b()); 
                sheep.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)sheep)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)sheep); 
                break;
              case 8:
                parrot = new EntityParrot(this.field_70170_p);
                parrot.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  parrot.setOwnerId(func_184753_b()); 
                parrot.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)parrot)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)parrot); 
                break;
            } 
            break;
          case 1:
            switch (this.field_70146_Z.nextInt(7)) {
              case 0:
                endermite = new EntityEndermite(this.field_70170_p);
                endermite.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  endermite.setOwnerId(func_184753_b()); 
                endermite.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)endermite)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)endermite); 
                break;
              case 1:
                silverfish = new EntitySilverfish(this.field_70170_p);
                silverfish.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  silverfish.setOwnerId(func_184753_b()); 
                silverfish.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)silverfish)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)silverfish); 
                break;
              case 2:
                snowgolem = new EntitySnowman(this.field_70170_p);
                snowgolem.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  snowgolem.setOwnerId(func_184753_b()); 
                snowgolem.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)snowgolem)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)snowgolem); 
                break;
              case 3:
                squid = new EntitySquid(this.field_70170_p);
                squid.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  squid.setOwnerId(func_184753_b()); 
                squid.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)squid)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)squid); 
                break;
              case 4:
                villager = new EntityVillager(this.field_70170_p);
                villager.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  villager.setOwnerId(func_184753_b()); 
                villager.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)villager)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)villager); 
                break;
              case 5:
                wolf = new EntityWolf(this.field_70170_p);
                wolf.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  wolf.setOwnerId(func_184753_b()); 
                wolf.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)wolf)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)wolf); 
                break;
              case 6:
                llama = new EntityLlama(this.field_70170_p);
                llama.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  llama.setOwnerId(func_184753_b()); 
                llama.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)llama)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)llama); 
                break;
            } 
            break;
          case 2:
            switch (this.field_70146_Z.nextInt(7)) {
              case 0:
                creeper = new EntityCreeper(this.field_70170_p);
                creeper.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  creeper.setOwnerId(func_184753_b()); 
                creeper.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)creeper)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)creeper); 
                break;
              case 1:
                magmacube = new EntityMagmaCube(this.field_70170_p);
                magmacube.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  magmacube.setOwnerId(func_184753_b()); 
                magmacube.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)magmacube)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)magmacube); 
                break;
              case 2:
                skeleton = new EntitySkeleton(this.field_70170_p);
                skeleton.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  skeleton.setOwnerId(func_184753_b()); 
                skeleton.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)skeleton)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)skeleton); 
                break;
              case 3:
                slime = new EntitySlime(this.field_70170_p);
                slime.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  slime.setOwnerId(func_184753_b()); 
                slime.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)slime)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)slime); 
                break;
              case 4:
                spider = new EntitySpider(this.field_70170_p);
                spider.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  spider.setOwnerId(func_184753_b()); 
                spider.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)spider)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)spider); 
                break;
              case 5:
                zombie = new EntityZombie(this.field_70170_p);
                zombie.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  zombie.setOwnerId(func_184753_b()); 
                zombie.setZombieType(0);
                zombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)zombie)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)zombie); 
                break;
              case 6:
                vex = new EntityVex(this.field_70170_p);
                vex.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  vex.setOwnerId(func_184753_b()); 
                vex.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)vex)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)vex); 
                break;
            } 
            break;
          case 3:
            switch (this.field_70146_Z.nextInt(12)) {
              case 0:
                blaze = new EntityBlaze(this.field_70170_p);
                blaze.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  blaze.setOwnerId(func_184753_b()); 
                blaze.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)blaze)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)blaze); 
                break;
              case 1:
                cavespider = new EntityCaveSpider(this.field_70170_p);
                cavespider.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cavespider.setOwnerId(func_184753_b()); 
                cavespider.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)cavespider)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)cavespider); 
                break;
              case 2:
                enderman = new EntityEnderman(this.field_70170_p);
                enderman.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  enderman.setOwnerId(func_184753_b()); 
                enderman.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)enderman)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)enderman); 
                break;
              case 3:
                ghast = new EntityGhast(this.field_70170_p);
                ghast.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ghast.setOwnerId(func_184753_b()); 
                ghast.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)ghast)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)ghast); 
                break;
              case 4:
                guardian = new EntityGuardian(this.field_70170_p);
                guardian.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  guardian.setOwnerId(func_184753_b()); 
                guardian.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)guardian)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)guardian); 
                break;
              case 5:
                pigzombie = new EntityPigZombie(this.field_70170_p);
                pigzombie.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pigzombie.setOwnerId(func_184753_b()); 
                pigzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)pigzombie)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)pigzombie); 
                break;
              case 6:
                killerrabbit = new EntityRabbit(this.field_70170_p);
                killerrabbit.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  killerrabbit.setOwnerId(func_184753_b()); 
                killerrabbit.setRabbitType(99);
                killerrabbit.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)killerrabbit)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)killerrabbit); 
                break;
              case 7:
                witherskeleton = new EntitySkeleton(this.field_70170_p);
                witherskeleton.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  witherskeleton.setOwnerId(func_184753_b()); 
                witherskeleton.setSkeletonType(1);
                witherskeleton.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151052_q));
                if (func_70681_au().nextInt(2) > 0)
                  witherskeleton.func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151052_q)); 
                witherskeleton.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)witherskeleton)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)witherskeleton); 
                break;
              case 8:
                shulker = new EntityShulker(this.field_70170_p);
                shulker.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  shulker.setOwnerId(func_184753_b()); 
                shulker.func_70108_f((Entity)this);
                shulker.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)shulker)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)shulker); 
                break;
              case 9:
                witch = new EntityWitch(this.field_70170_p);
                witch.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  witch.setOwnerId(func_184753_b()); 
                witch.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)witch)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)witch); 
                break;
              case 10:
                entityZombie1 = new EntityZombie(this.field_70170_p);
                entityZombie1.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityZombie1.setOwnerId(func_184753_b()); 
                entityZombie1.setZombieType(1);
                entityZombie1.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityZombie1)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityZombie1); 
                break;
              case 11:
                stray = new EntitySkeleton(this.field_70170_p);
                stray.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  stray.setOwnerId(func_184753_b()); 
                stray.setSkeletonType(2);
                stray.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)stray)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)stray); 
                break;
              case 12:
                vindicator = new EntityVindicator(this.field_70170_p);
                vindicator.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  vindicator.setOwnerId(func_184753_b()); 
                vindicator.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)vindicator)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)vindicator); 
                break;
            } 
            break;
          case 4:
            switch (this.field_70146_Z.nextInt(6)) {
              case 0:
                elderguardian = new EntityElderGuardian(this.field_70170_p);
                elderguardian.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  elderguardian.setOwnerId(func_184753_b()); 
                elderguardian.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)elderguardian)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)elderguardian); 
                break;
              case 1:
                giant = new EntityGiant(this.field_70170_p);
                giant.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  giant.setOwnerId(func_184753_b()); 
                giant.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)giant)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)giant); 
                break;
              case 2:
                irongolem = new EntityIronGolem(this.field_70170_p);
                irongolem.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  irongolem.setOwnerId(func_184753_b()); 
                irongolem.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)irongolem)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)irongolem); 
                break;
              case 3:
                wither = new EntityWither(this.field_70170_p);
                wither.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  wither.setOwnerId(func_184753_b()); 
                wither.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)wither)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)wither); 
                break;
              case 4:
                evoker = new EntityEvoker(this.field_70170_p);
                evoker.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  evoker.setOwnerId(func_184753_b()); 
                evoker.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)evoker)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)evoker); 
                break;
              case 5:
                illusioner = new EntityIllusioner(this.field_70170_p);
                illusioner.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  illusioner.setOwnerId(func_184753_b()); 
                illusioner.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)illusioner)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)illusioner); 
                break;
            } 
            break;
        } 
      }  
  }
  
  protected void func_70609_aI() {
    this.field_70725_aQ++;
    if (this.field_70725_aQ == 1) {
      func_184185_a(ESound.buildingDeath, 10.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K)
        func_70099_a(new ItemStack(EItem.portalStaff, 1, getMetaData()), 1.0F); 
      for (int k = 0; k < 2500; k++) {
        double d2 = this.field_70146_Z.nextGaussian() * 0.05D;
        double d0 = this.field_70146_Z.nextGaussian() * 0.05D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.05D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 4.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, -0.25D, d1, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 4.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, -0.25D, d1, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 4.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, 0.5D, d1, new int[0]);
      } 
    } 
    if (!this.field_70170_p.field_72995_K && (func_70684_aJ() || (this.field_70718_bc > 0 && func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")))) {
      int i = func_70693_a(this.field_70717_bb) / 60;
      i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, i);
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
      } 
    } 
    if (this.field_70725_aQ == 60)
      func_70106_y(); 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_PORTAL;
  }
  
  protected SoundEvent func_184615_bR() {
    return null;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return null;
  }
  
  public void func_70624_b(EntityLivingBase entitylivingbaseIn) {
    if (func_70089_S() && this.field_70173_aa > 80)
      super.func_70624_b(entitylivingbaseIn); 
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
  
  public boolean func_184645_a(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (this.field_70173_aa > 40 && stack.func_190926_b() && !isWild() && player == getOwner()) {
      if (player.func_70093_af()) {
        List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty() && !func_184207_aI())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityTameBase entity = list.get(i1);
            if (entity != null)
              if (func_184191_r((Entity)entity)) {
                func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
                entity.func_184204_a(1);
              }  
          }  
        func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
        player.func_184204_a(1);
        return true;
      } 
      player.field_70170_p.func_184133_a(player, new BlockPos((Entity)player), SoundEvents.field_187698_i, SoundCategory.PLAYERS, 100.0F, 0.5F);
      func_70606_j(0.0F);
      return true;
    } 
    return false;
  }
  
  public boolean func_70067_L() {
    return true;
  }
  
  public World func_82194_d() {
    return this.field_70170_p;
  }
  
  public int getDamageCap() {
    return 100;
  }
  
  public boolean func_70965_a(MultiPartEntityPart portalPart, DamageSource source, float damage) {
    if (portalPart == this.side1 || portalPart == this.side2 || portalPart == this.side3 || portalPart == this.side4)
      damage = 0.0F; 
    return super.func_70097_a(source, damage);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_70965_a(this.portal, source, amount);
  }
  
  public Entity[] func_70021_al() {
    return (Entity[])this.partArray;
  }
  
  public AxisAlignedBB func_70046_E() {
    return func_174813_aQ();
  }
  
  public void func_70108_f(Entity entity) {
    if (entity instanceof EntityLivingBase && !(entity instanceof IEntityMultiPart) && entity.field_70163_u + entity.func_70047_e() <= this.field_70163_u + 1.0D)
      entity.func_70097_a(DamageSource.field_76368_d, 1.0F); 
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {}
}
