package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
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

public class EntityAbyssalPortal extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Structure {
  private static final DataParameter<Integer> TOWER1_TARGET = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER2_TARGET = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER3_TARGET = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TOWER4_TARGET = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer>[] TARGETS = new DataParameter[] { TOWER1_TARGET, TOWER2_TARGET, TOWER3_TARGET, TOWER4_TARGET };
  
  private static final DataParameter<Integer> LIGHTNINGTIMER = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> METADATA = EntityDataManager.func_187226_a(EntityAbyssalPortal.class, DataSerializers.field_187192_b);
  
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
  
  public EntityAbyssalPortal(World worldIn) {
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
    return "portal_aby";
  }
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public int getMetaData() {
    return ((Integer)this.field_70180_af.func_187225_a(METADATA)).intValue();
  }
  
  public void setMetaData(int p_82215_1_) {
    this.field_70180_af.func_187227_b(METADATA, Integer.valueOf(p_82215_1_));
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
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(128.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
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
      for (int i = 0; i < 3 && this.field_70173_aa > 60; i++) {
        if (this.towerUpdate[i] > 20)
          this.field_70170_p.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, func_82214_u(i), func_82208_v(i), func_82208_v(i), ((float)func_82214_u(i) + this.field_70146_Z.nextFloat()) - 0.5D, ((float)func_82208_v(i) - this.field_70146_Z.nextFloat() - 1.0F), ((float)func_82208_v(i) + this.field_70146_Z.nextFloat()) - 0.5D, new int[0]); 
        if (getMetaData() > 0)
          this.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.0D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.01D, 0.0D, new int[0]); 
        if (getMetaData() > 1) {
          this.field_70170_p.func_175682_a(EnumParticleTypes.CRIT, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.CRIT, true, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.CRIT, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.field_70170_p.func_175682_a(EnumParticleTypes.CRIT, true, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        } 
        if (getMetaData() > 2)
          this.field_70170_p.func_175688_a(EnumParticleTypes.CRIT_MAGIC, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 1.0D, 0.0D, 0.5D, new int[0]); 
        if (getMetaData() > 3) {
          this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, -0.05D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FLAME, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
        } 
        this.field_70170_p.func_175688_a(EnumParticleTypes.TOWN_AURA, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 1.0D + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D + this.field_70146_Z.nextDouble() * 0.1D - 0.05D, 0.0D + this.field_70146_Z.nextDouble() * 0.2D, 0.0D + this.field_70146_Z.nextDouble() * 0.1D - 0.05D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.25D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_INSTANT, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_INSTANT, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + 0.8D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.05D, 0.0D, new int[0]);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.field_70170_p, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.field_70170_p, this.field_70165_t + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.field_70170_p, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.field_70170_p, this.field_70165_t - 3.25D - f + this.field_70146_Z.nextDouble() - 0.5D, this.field_70163_u + 4.0D + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + 3.25D + f + this.field_70146_Z.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
      }  
    if ((this.field_70173_aa + func_145782_y()) % ((getJukeboxToDanceTo() != null) ? 20 : 200) == 0)
      func_184185_a(ESound.portalWhoosh, 5.0F, 1.0F); 
    if ((this.field_70173_aa + func_145782_y()) % 670 == 0)
      func_184185_a(ESound.portalAmbient, 5.0F, 1.0F); 
    if ((this.field_70173_aa + func_145782_y()) % 400 == 0) {
      SoundEvent[] chants = { ACSounds.cthulhu_chant, ACSounds.yog_sothoth_chant_1, ACSounds.yog_sothoth_chant_2, ACSounds.hastur_chant_1, ACSounds.hastur_chant_2, ACSounds.sleeping_chant, ACSounds.cthugha_chant };
      func_184185_a(chants[this.field_70170_p.field_73012_v.nextInt(chants.length)], 2.0F, func_70647_i());
      func_184185_a(chants[this.field_70170_p.field_73012_v.nextInt(chants.length)], 2.0F, func_70647_i() - 0.025F);
      func_184185_a(chants[this.field_70170_p.field_73012_v.nextInt(chants.length)], 2.0F, func_70647_i() - 0.05F);
    } 
    if (this.field_70173_aa + func_145782_y() > 60)
      if ((this.field_70173_aa + func_145782_y()) % ((getJukeboxToDanceTo() != null) ? 20 : ((getMetaData() > 2) ? 50 : 100)) == 0 && this.field_70146_Z.nextInt((getJukeboxToDanceTo() != null) ? 5 : 10) == 0) {
        EntityAbygolem entityAbygolem;
        EntityAbyssalZombie entityAbyssalZombie;
        EntityDepthsGhoul entityDepthsGhoul;
        EntityDragonMinion entityDragonMinion;
        EntityGreaterDreadSpawn entityGreaterDreadSpawn;
        EntityLesserShoggoth entityLesserShoggoth;
        EntityDreadguard bat;
        EntityGatekeeperMinion chicken;
        EntityLesserDreadbeast cow;
        EntityCoraliumSquid entityCoraliumSquid;
        EntityChagarothSpawn entityChagarothSpawn;
        EntityOmotholGhoul entityOmotholGhoul;
        EntityRemnant entityRemnant;
        EntityShadowBeast mooshroom;
        EntitySkeletonGoliath ocelot;
        EntityChagarothFist entityChagarothFist;
        EntityDreadgolem rabbit;
        EntityDreadling entityDreadling;
        EntityDreadSpawn entityDreadSpawn;
        EntityShadowCreature sheep;
        EntityShadowMonster entityShadowMonster;
        EntityDragonBoss entityDragonBoss;
        EntitySacthoth pig;
        func_184185_a(ESound.portalWhoosh, 10.0F, func_70647_i() + 1.5F);
        int i = 0;
        if (this.field_70146_Z.nextInt(4) == 0)
          i++; 
        if (this.field_70146_Z.nextInt(8) == 0)
          i++; 
        switch (i) {
          case 0:
            switch (this.field_70146_Z.nextInt(10)) {
              case 0:
                entityAbygolem = new EntityAbygolem(this.field_70170_p);
                entityAbygolem.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityAbygolem.setOwnerId(func_184753_b()); 
                entityAbygolem.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityAbygolem)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityAbygolem); 
                break;
              case 1:
                entityAbyssalZombie = new EntityAbyssalZombie(this.field_70170_p);
                entityAbyssalZombie.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityAbyssalZombie.setOwnerId(func_184753_b()); 
                entityAbyssalZombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityAbyssalZombie)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityAbyssalZombie); 
                break;
              case 2:
                entityDepthsGhoul = new EntityDepthsGhoul(this.field_70170_p);
                entityDepthsGhoul.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDepthsGhoul.setOwnerId(func_184753_b()); 
                entityDepthsGhoul.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityDepthsGhoul)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityDepthsGhoul); 
                break;
              case 3:
                entityCoraliumSquid = new EntityCoraliumSquid(this.field_70170_p);
                entityCoraliumSquid.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityCoraliumSquid.setOwnerId(func_184753_b()); 
                entityCoraliumSquid.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityCoraliumSquid)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityCoraliumSquid); 
                break;
              case 4:
                entityChagarothSpawn = new EntityChagarothSpawn(this.field_70170_p);
                entityChagarothSpawn.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityChagarothSpawn.setOwnerId(func_184753_b()); 
                entityChagarothSpawn.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityChagarothSpawn)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityChagarothSpawn); 
                break;
              case 5:
                entityChagarothFist = new EntityChagarothFist(this.field_70170_p);
                entityChagarothFist.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityChagarothFist.setOwnerId(func_184753_b()); 
                entityChagarothFist.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityChagarothFist)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityChagarothFist); 
                break;
              case 6:
                rabbit = new EntityDreadgolem(this.field_70170_p);
                rabbit.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  rabbit.setOwnerId(func_184753_b()); 
                rabbit.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)rabbit)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)rabbit); 
                break;
              case 7:
                entityDreadling = new EntityDreadling(this.field_70170_p);
                entityDreadling.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDreadling.setOwnerId(func_184753_b()); 
                entityDreadling.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityDreadling)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityDreadling); 
                break;
              case 8:
                entityDreadSpawn = new EntityDreadSpawn(this.field_70170_p);
                entityDreadSpawn.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDreadSpawn.setOwnerId(func_184753_b()); 
                entityDreadSpawn.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityDreadSpawn)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityDreadSpawn); 
                break;
              case 9:
                sheep = new EntityShadowCreature(this.field_70170_p);
                sheep.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  sheep.setOwnerId(func_184753_b()); 
                sheep.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)sheep)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)sheep); 
                break;
            } 
            break;
          case 1:
            switch (this.field_70146_Z.nextInt(6)) {
              case 0:
                entityDragonMinion = new EntityDragonMinion(this.field_70170_p);
                entityDragonMinion.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDragonMinion.setOwnerId(func_184753_b()); 
                entityDragonMinion.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityDragonMinion)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityDragonMinion); 
                break;
              case 1:
                entityGreaterDreadSpawn = new EntityGreaterDreadSpawn(this.field_70170_p);
                entityGreaterDreadSpawn.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityGreaterDreadSpawn.setOwnerId(func_184753_b()); 
                entityGreaterDreadSpawn.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityGreaterDreadSpawn)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityGreaterDreadSpawn); 
                break;
              case 2:
                entityLesserShoggoth = new EntityLesserShoggoth(this.field_70170_p);
                entityLesserShoggoth.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityLesserShoggoth.setOwnerId(func_184753_b()); 
                entityLesserShoggoth.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityLesserShoggoth)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityLesserShoggoth); 
                break;
              case 3:
                entityOmotholGhoul = new EntityOmotholGhoul(this.field_70170_p);
                entityOmotholGhoul.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityOmotholGhoul.setOwnerId(func_184753_b()); 
                entityOmotholGhoul.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityOmotholGhoul)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityOmotholGhoul); 
                break;
              case 4:
                entityRemnant = new EntityRemnant(this.field_70170_p);
                entityRemnant.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityRemnant.setOwnerId(func_184753_b()); 
                entityRemnant.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityRemnant)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityRemnant); 
                break;
              case 5:
                entityShadowMonster = new EntityShadowMonster(this.field_70170_p);
                entityShadowMonster.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityShadowMonster.setOwnerId(func_184753_b()); 
                entityShadowMonster.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityShadowMonster)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityShadowMonster); 
                break;
            } 
            break;
          case 2:
            switch (this.field_70146_Z.nextInt(8)) {
              case 0:
                bat = new EntityDreadguard(this.field_70170_p);
                bat.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  bat.setOwnerId(func_184753_b()); 
                bat.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)bat)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)bat); 
                break;
              case 1:
                chicken = new EntityGatekeeperMinion(this.field_70170_p);
                chicken.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  chicken.setOwnerId(func_184753_b()); 
                chicken.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)chicken)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)chicken); 
                break;
              case 2:
                cow = new EntityLesserDreadbeast(this.field_70170_p);
                cow.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cow.setOwnerId(func_184753_b()); 
                cow.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)cow)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)cow); 
                break;
              case 3:
                mooshroom = new EntityShadowBeast(this.field_70170_p);
                mooshroom.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  mooshroom.setOwnerId(func_184753_b()); 
                mooshroom.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)mooshroom)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)mooshroom); 
                break;
              case 4:
                ocelot = new EntitySkeletonGoliath(this.field_70170_p);
                ocelot.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ocelot.setOwnerId(func_184753_b()); 
                ocelot.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)ocelot)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)ocelot); 
                break;
              case 5:
                entityDragonBoss = new EntityDragonBoss(this.field_70170_p);
                entityDragonBoss.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDragonBoss.setOwnerId(func_184753_b()); 
                entityDragonBoss.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityDragonBoss)), null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)entityDragonBoss); 
                break;
              case 6:
                pig = new EntitySacthoth(this.field_70170_p);
                pig.func_70012_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), this.field_70163_u + 1.5D, this.field_70161_v + (this.field_70146_Z.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pig.setOwnerId(func_184753_b()); 
                pig.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)pig)), (IEntityLivingData)null);
                if (!this.field_70170_p.field_72995_K)
                  this.field_70170_p.func_72838_d((Entity)pig); 
                break;
            } 
            break;
        } 
      }  
  }
  
  public void fireLightning(Entity entity, double x, double y, double z) {
    if (this.field_70146_Z.nextInt(2) == 0) {
      if (entity != null && entity.func_70089_S()) {
        double d6 = entity.field_70165_t - x;
        double d7 = entity.field_70163_u + this.field_70146_Z.nextDouble() * 2.0D - y;
        double d8 = entity.field_70161_v - z;
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        EntityMiniBlackHole entitydragonfireball = new EntityMiniBlackHole(this.field_70170_p, (EntityLivingBase)this, d6, d7, d8);
        entitydragonfireball.field_70165_t = x;
        entitydragonfireball.field_70163_u = y;
        entitydragonfireball.field_70161_v = z;
        this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
      } 
    } else {
      super.fireLightning(entity, x, y, z);
    } 
  }
  
  protected void func_70609_aI() {
    this.field_70725_aQ++;
    if (this.field_70725_aQ == 1) {
      func_184185_a(ESound.buildingDeath, 10.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K)
        func_70099_a(new ItemStack(EItem.abyssalPortalStaff, 1, getMetaData()), 1.0F); 
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
                entity.func_184204_a(ACLib.omothol_id);
              }  
          }  
        func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
        player.func_184204_a(ACLib.omothol_id);
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
    return 50;
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
