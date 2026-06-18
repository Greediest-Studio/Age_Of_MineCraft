package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityChagaroth extends EntityTameBase implements Armored, Massive, Structure {
  private static final DataParameter<Integer> FLAMETIMER = EntityDataManager.func_187226_a(EntityChagaroth.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.func_187226_a(EntityChagaroth.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.func_187226_a(EntityChagaroth.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.func_187226_a(EntityChagaroth.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
  
  private final float[] xRotationHeads = new float[2];
  
  private final float[] yRotationHeads = new float[2];
  
  private final float[] xRotOHeads = new float[2];
  
  private final float[] yRotOHeads = new float[2];
  
  private final int[] nextHeadUpdate = new int[2];
  
  private final int[] idleHeadUpdates = new int[2];
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8.0D, 0);
  
  public EntityChagaroth(World par1World) {
    super(par1World);
    func_70105_a(2.25F, 4.5F);
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 0.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70158_ak = true;
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 5;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186746_a(BossInfo.Overlay.NOTCHED_20);
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
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public String func_70005_c_() {
    return TextFormatting.DARK_RED + super.func_70005_c_() + TextFormatting.WHITE;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(FIRST_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(SECOND_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(THIRD_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(FLAMETIMER, Integer.valueOf(0));
  }
  
  public int getFlameTime() {
    return ((Integer)this.field_70180_af.func_187225_a(FLAMETIMER)).intValue();
  }
  
  public void setFlameTime(int time) {
    this.field_70180_af.func_187227_b(FLAMETIMER, Integer.valueOf(time));
  }
  
  public AxisAlignedBB func_70046_E() {
    return func_174813_aQ();
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 400, 1)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 4.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(2000.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(30.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(15.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.dreadguard_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ACSounds.dreadguard_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.dreadguard_death;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.5F : 5.0F;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    func_70661_as().func_75499_g();
    if (this.field_70173_aa % 10 == 0)
      func_70691_i(1.0F); 
    for (int i = 1; i < 3; i++) {
      if (this.field_70173_aa >= this.nextHeadUpdate[i - 1]) {
        this.nextHeadUpdate[i - 1] = this.field_70173_aa + 10 + this.field_70146_Z.nextInt(10);
        int k1 = getWatchedTargetId(i);
        if (k1 > 0) {
          Entity entity = this.field_70170_p.func_73045_a(k1);
          if (entity != null && entity.func_70089_S() && func_70068_e(entity) <= 2304.0D && func_70685_l(entity)) {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75102_a) {
              updateWatchedTargetId(i, 0);
            } else {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } else {
            updateWatchedTargetId(i, 0);
          } 
        } else {
          List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(48.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
          for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
            EntityLivingBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
            if (entitylivingbase != this && !func_184191_r((Entity)entitylivingbase) && entitylivingbase.func_70089_S() && entitylivingbase.func_110143_aJ() > 0.0F && func_70685_l((Entity)entitylivingbase)) {
              if (entitylivingbase instanceof EntityPlayer) {
                if (!((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a)
                  updateWatchedTargetId(i, entitylivingbase.func_145782_y()); 
                break;
              } 
              updateWatchedTargetId(i, entitylivingbase.func_145782_y());
              break;
            } 
            list.remove(entitylivingbase);
          } 
        } 
      } 
    } 
    if (func_70638_az() != null) {
      updateWatchedTargetId(0, func_70638_az().func_145782_y());
    } else {
      updateWatchedTargetId(0, 0);
    } 
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.field_70165_t, p_82216_2_.field_70163_u + 0.5D, p_82216_2_.field_70161_v, true);
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
    EntityDreadSlugOther entitydreadslug1, entitydreadslug11;
    EntityDreadSpawn mob;
    EntityDreadSlugOther entitydreadslug111;
    EntityChagarothSpawn spawn;
    EntityDreadSlugOther entitydreadslug1111;
    EntityChagarothFist fist;
    EntityDreadedChargeOther entitydragonfireball;
    double d0 = getHeadX(p_82209_1_);
    double d1 = getHeadY(p_82209_1_);
    double d2 = getHeadZ(p_82209_1_);
    double d3 = x - d0;
    double d4 = y - d1;
    double d5 = z - d2;
    float f1 = MathHelper.func_76133_a(d3 * d3 + d5 * d5) * 0.2F;
    float f2 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
    EntityDreadSlugOther entitydreadslug = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
    entitydreadslug.field_70163_u = d1;
    entitydreadslug.field_70165_t = d0;
    entitydreadslug.field_70161_v = d2;
    entitydreadslug.func_70186_c(d3, d4 + f1, d5, 1.75F, 1.0F);
    func_184185_a(ESound.amalgamate, 2.0F, 1.25F);
    if (!this.field_70170_p.field_72995_K)
      this.field_70170_p.func_72838_d((Entity)entitydreadslug); 
    entitydreadslug.field_70159_w = d3 / f2 * 0.8D * 0.8D + entitydreadslug.field_70159_w;
    entitydreadslug.field_70181_x = d4 / f2 * 0.8D * 0.8D + entitydreadslug.field_70181_x;
    entitydreadslug.field_70179_y = d5 / f2 * 0.8D * 0.8D + entitydreadslug.field_70179_y;
    switch (this.field_70146_Z.nextInt(5)) {
      case 0:
        entitydreadslug1 = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
        entitydreadslug1.field_70163_u = d1;
        entitydreadslug1.field_70165_t = d0;
        entitydreadslug1.field_70161_v = d2;
        entitydreadslug1.func_70186_c(d3, d4 + f1, d5, 1.75F, 1.0F);
        func_184185_a(ESound.amalgamate, 2.0F, 1.25F);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitydreadslug1); 
        entitydreadslug1.field_70159_w = d3 / f2 * 0.8D * 0.8D + entitydreadslug1.field_70159_w;
        entitydreadslug1.field_70181_x = d4 / f2 * 0.8D * 0.8D + entitydreadslug1.field_70181_x;
        entitydreadslug1.field_70179_y = d5 / f2 * 0.8D * 0.8D + entitydreadslug1.field_70179_y;
        this.nextHeadUpdate[p_82209_1_ - 2] = this.field_70173_aa + 10;
        break;
      case 1:
        entitydreadslug11 = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
        entitydreadslug11.field_70163_u = d1;
        entitydreadslug11.field_70165_t = d0;
        entitydreadslug11.field_70161_v = d2;
        mob = new EntityDreadSpawn(this.field_70170_p);
        mob.func_82149_j((Entity)entitydreadslug11);
        entitydreadslug11.func_70186_c(d3, d4 + f1 + this.field_70146_Z.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        func_184185_a(ESound.amalgamate, 2.0F, 1.25F);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitydreadslug11); 
        mob.setOwnerId(func_184753_b());
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)mob); 
        mob.func_70097_a(DamageSource.field_188406_j, 2.0F);
        mob.func_184220_m((Entity)entitydreadslug11);
        mob.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.field_70173_aa + 20;
        break;
      case 2:
        entitydreadslug111 = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
        entitydreadslug111.field_70163_u = d1;
        entitydreadslug111.field_70165_t = d0;
        entitydreadslug111.field_70161_v = d2;
        spawn = new EntityChagarothSpawn(this.field_70170_p);
        spawn.func_82149_j((Entity)entitydreadslug111);
        entitydreadslug111.func_70186_c(d3, d4 + f1 + this.field_70146_Z.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        func_184185_a(ESound.amalgamate, 2.0F, 1.25F);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitydreadslug111); 
        spawn.setOwnerId(func_184753_b());
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)spawn); 
        spawn.func_70097_a(DamageSource.field_188406_j, 2.0F);
        spawn.func_184220_m((Entity)entitydreadslug111);
        spawn.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.field_70173_aa + 20;
        break;
      case 3:
        entitydreadslug1111 = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
        entitydreadslug1111.field_70163_u = d1;
        entitydreadslug1111.field_70165_t = d0;
        entitydreadslug1111.field_70161_v = d2;
        fist = new EntityChagarothFist(this.field_70170_p);
        fist.func_82149_j((Entity)entitydreadslug1111);
        entitydreadslug1111.func_70186_c(d3, d4 + f1 + this.field_70146_Z.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        func_184185_a(ESound.amalgamate, 2.0F, 1.25F);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitydreadslug1111); 
        fist.setOwnerId(func_184753_b());
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)fist); 
        fist.func_70097_a(DamageSource.field_188406_j, 2.0F);
        fist.func_184220_m((Entity)entitydreadslug1111);
        fist.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.field_70173_aa + 20;
        break;
      case 4:
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        d4 = y + 0.5D - d1;
        entitydragonfireball = new EntityDreadedChargeOther(this.field_70170_p, (EntityLivingBase)this, d3, d4, d5);
        entitydragonfireball.field_70165_t = d0;
        entitydragonfireball.field_70163_u = d1;
        entitydragonfireball.field_70161_v = d2;
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitydragonfireball); 
        this.nextHeadUpdate[p_82209_1_ - 2] = this.field_70173_aa + 100;
        break;
    } 
  }
  
  private double getHeadX(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.field_70165_t; 
    float f = (this.field_70761_aq + (180 * (p_82214_1_ - 1))) * 0.017453292F;
    float f1 = MathHelper.func_76134_b(f);
    return this.field_70165_t + f1 * 1.5D;
  }
  
  private double getHeadY(int p_82208_1_) {
    return this.field_70163_u + func_70047_e() * 0.85D;
  }
  
  private double getHeadZ(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.field_70161_v; 
    float f = (this.field_70761_aq + (180 * (p_82213_1_ - 1))) * 0.017453292F;
    float f1 = MathHelper.func_76126_a(f);
    return this.field_70161_v + f1 * 1.5D;
  }
  
  private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f = MathHelper.func_76142_g(p_82204_2_ - p_82204_1_);
    if (f > p_82204_3_)
      f = p_82204_3_; 
    if (f < -p_82204_3_)
      f = -p_82204_3_; 
    return p_82204_1_ + f;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadYRotation(int p_82207_1_) {
    return this.yRotationHeads[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadXRotation(int p_82210_1_) {
    return this.xRotationHeads[p_82210_1_];
  }
  
  public void func_70636_d() {
    if (this.field_70173_aa % 40 == 0 && !this.field_70170_p.field_72995_K)
      for (int x = func_180425_c().func_177958_n() - 3; x <= func_180425_c().func_177958_n() + 3; x++) {
        for (int z = func_180425_c().func_177952_p() - 3; z <= func_180425_c().func_177952_p() + 3; z++) {
          if (!(this.field_70170_p.func_180494_b(new BlockPos(x, 0, z)) instanceof com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome) && this.field_70170_p
            .func_180494_b(new BlockPos(x, 0, z)) != ACBiomes.purged) {
            func_184185_a(ESound.amalgamate, 1.0F, 1.0F);
            Biome b = ACBiomes.dreadlands;
            Chunk c = this.field_70170_p.func_175726_f(func_180425_c());
            c.func_76605_m()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.func_185362_a(b);
            c.func_177427_f(true);
            PacketDispatcher.sendToDimension((IMessage)new CleansingRitualMessage(x, z, Biome.func_185362_a(b)), this.field_70170_p.field_73011_w.getDimension());
          } 
        } 
      }  
    for (int a = 0; a < 10; a++)
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + this.field_70146_Z.nextGaussian() * 1.25D, this.field_70163_u + 1.0D + this.field_70146_Z.nextGaussian(), this.field_70161_v + this.field_70146_Z.nextGaussian() * 1.25D, 0.375D + this.field_70146_Z.nextDouble() * 0.15D, 0.0D, 0.0D, new int[0]); 
    for (int l = 0; l < 3; l++) {
      double d10 = getHeadX(l);
      double d2 = getHeadY(l);
      double d4 = getHeadZ(l);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, d10 + this.field_70146_Z.nextGaussian() * 0.2D, d2 + this.field_70146_Z.nextGaussian() * 0.4D + 0.2D, d4 + this.field_70146_Z.nextGaussian() * 0.2D, 0.375D + this.field_70146_Z.nextDouble() * 0.15D, 0.0D, 0.0D, new int[0]);
    } 
    func_70031_b(false);
    this.field_70159_w = 0.0D;
    this.field_70179_y = 0.0D;
    if (this.field_70181_x > 0.0D)
      this.field_70181_x = 0.0D; 
    this.field_70160_al = false;
    this.field_70122_E = true;
    func_70031_b(false);
    for (int i = 0; i < 2; i++) {
      this.yRotOHeads[i] = this.yRotationHeads[i];
      this.xRotOHeads[i] = this.xRotationHeads[i];
    } 
    for (int j = 0; j < 2; j++) {
      int k = getWatchedTargetId(j + 1);
      Entity entity1 = null;
      if (k > 0)
        entity1 = this.field_70170_p.func_73045_a(k); 
      if (entity1 != null) {
        double d11 = getHeadX(j + 1);
        double d12 = getHeadY(j + 1);
        double d13 = getHeadZ(j + 1);
        double d6 = entity1.field_70165_t - d11;
        double d7 = entity1.field_70163_u + entity1.func_70047_e() - d12;
        double d8 = entity1.field_70161_v - d13;
        double d9 = MathHelper.func_76133_a(d6 * d6 + d8 * d8);
        float f = (float)(MathHelper.func_181159_b(d8, d6) * 57.29577951308232D) - 90.0F;
        float f1 = (float)-(MathHelper.func_181159_b(d7, d9) * 57.29577951308232D);
        this.xRotationHeads[j] = rotlerp(this.xRotationHeads[j], f1, 40.0F);
        this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], f, 10.0F);
      } else {
        this.xRotationHeads[j] = rotlerp(this.xRotationHeads[j], this.field_70125_A, 40.0F);
        this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], this.field_70759_as, 10.0F);
      } 
    } 
    List<EntityLivingBase> flamedetector = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(12.0D, 12.0D, 12.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a, EntitySelectors.field_188444_d }));
    if (flamedetector != null && !flamedetector.isEmpty())
      for (int i1 = 0; i1 < flamedetector.size(); i1++) {
        EntityLivingBase entity = flamedetector.get(i1);
        if (entity != null && !func_184191_r((Entity)entity) && func_70068_e((Entity)entity) <= 256.0D)
          if (getFlameTime() <= (isHero() ? -100 : -200)) {
            setFlameTime(150);
            if (entity != func_70638_az()) {
              func_70624_b(entity);
              func_70625_a((Entity)entity, 180.0F, 40.0F);
            } 
          }  
      }  
    if (hasOpenMouth()) {
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
      if (this.field_70173_aa % 3 == 0) {
        this.field_70170_p.func_184133_a(null, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + func_70047_e(), this.field_70161_v + 0.5D), ACSounds.dreadguard_barf, func_184176_by(), 2.0F + func_70681_au().nextFloat(), func_70681_au().nextFloat() * 0.6F + 0.2F);
        this.field_70170_p.func_184133_a(null, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + func_70047_e(), this.field_70161_v + 0.5D), ACSounds.dreadguard_barf, func_184176_by(), 2.0F + func_70681_au().nextFloat(), func_70681_au().nextFloat() * 0.5F + 0.2F);
        this.field_70170_p.func_184133_a(null, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + func_70047_e(), this.field_70161_v + 0.5D), ACSounds.dreadguard_barf, func_184176_by(), 2.0F + func_70681_au().nextFloat(), func_70681_au().nextFloat() * 0.4F + 0.2F);
      } 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, target.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null && !func_184191_r((Entity)entity) && this.field_70146_Z.nextInt(3) == 0)
              if (entity.func_70097_a(AbyssalCraftAPI.dread, (float)(15.0D - func_70032_d((Entity)entity)) * 5.0F)) {
                entity.func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1));
                entity.func_70015_d((int)(30.0F - func_70032_d((Entity)entity)));
              } else {
                func_70652_k((Entity)entity);
                entity.func_70015_d((int)(30.0F - func_70032_d((Entity)entity)));
              }  
          }  
        if (target.func_70097_a(AbyssalCraftAPI.dread, (float)(15.0D - func_70032_d(target)) * 5.0F)) {
          if (target instanceof EntityLivingBase)
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1)); 
          target.func_70015_d((int)(30.0F - func_70032_d(target)));
        } else {
          func_70652_k(target);
          target.func_70015_d((int)(30.0F - func_70032_d(target)));
        } 
      } 
    } 
    if (func_70638_az() != null)
      func_70625_a((Entity)func_70638_az(), 10.0F, 180.0F); 
    setFlameTime(getFlameTime() - 1);
    if (!this.field_70170_p.field_72995_K && func_70089_S()) {
      if (this.field_70146_Z.nextInt(isHero() ? 200 : 400) == 0) {
        EntityDreadSpawn mob = new EntityDreadSpawn(this.field_70170_p);
        mob.func_82149_j((Entity)this);
        mob.setOwnerId(func_184753_b());
        mob.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)mob);
        mob.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        EntityChagarothSpawn spawn = new EntityChagarothSpawn(this.field_70170_p);
        spawn.func_82149_j((Entity)this);
        spawn.setOwnerId(func_184753_b());
        spawn.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)spawn);
        spawn.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
      } 
      if (this.field_70146_Z.nextInt(isHero() ? 400 : 800) == 0) {
        EntityChagarothFist fist = new EntityChagarothFist(this.field_70170_p);
        fist.func_82149_j((Entity)this);
        fist.setOwnerId(func_184753_b());
        fist.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)fist);
        fist.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
      } 
      if (this.field_70146_Z.nextInt(isHero() ? 800 : 1600) == 0) {
        EntityDreadguard dreadGuard = new EntityDreadguard(this.field_70170_p);
        dreadGuard.func_82149_j((Entity)this);
        dreadGuard.setOwnerId(func_184753_b());
        dreadGuard.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)dreadGuard);
        dreadGuard.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
      } 
      if (this.field_70146_Z.nextInt(isHero() ? 1600 : 3200) == 0) {
        EntityGreaterDreadSpawn dreadGuard = new EntityGreaterDreadSpawn(this.field_70170_p);
        dreadGuard.func_82149_j((Entity)this);
        dreadGuard.setOwnerId(func_184753_b());
        dreadGuard.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)dreadGuard);
        dreadGuard.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
      } 
      if (this.field_70146_Z.nextInt(isHero() ? 3200 : 6400) == 0) {
        EntityLesserDreadbeast dreadGuard = new EntityLesserDreadbeast(this.field_70170_p);
        dreadGuard.func_82149_j((Entity)this);
        dreadGuard.setOwnerId(func_184753_b());
        dreadGuard.field_70159_w++;
        this.field_70170_p.func_72838_d((Entity)dreadGuard);
        dreadGuard.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
      } 
    } 
    super.func_70636_d();
  }
  
  public boolean hasOpenMouth() {
    return (getFlameTime() > 0);
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 8.0D + this.field_70146_Z.nextDouble() * 20.0D;
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
      double px = this.field_70165_t + vector.field_72450_a * 0.25D;
      double py = this.field_70163_u + (this.field_70131_O * 0.75F);
      double pz = this.field_70161_v + vector.field_72449_c * 0.25D;
      for (int i = 0; i < 30; i++) {
        double dx = vector.field_72450_a;
        double dy = vector.field_72448_b;
        double dz = vector.field_72449_c;
        double spread = 15.0D + func_70681_au().nextDouble() * 5.0D;
        double velocity = 0.5D + func_70681_au().nextDouble();
        dx += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dy += func_70681_au().nextGaussian() * 0.007499999832361937D;
        dz += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.func_150891_b(ACItems.dreaded_shard_of_abyssalnite) });
        this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.func_150891_b(ACItems.dread_fragment) });
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
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    par1NBTTagCompound.func_74768_a("FlameTime", getFlameTime());
    if (this.deathTicks > 0)
      par1NBTTagCompound.func_74768_a("DeathTicks", this.deathTicks); 
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    setFlameTime(par1NBTTagCompound.func_74762_e("FlameTime"));
    this.deathTicks = par1NBTTagCompound.func_74762_e("DeathTicks");
  }
  
  public int getWatchedTargetId(int head) {
    return ((Integer)this.field_70180_af.func_187225_a(HEAD_TARGETS[head])).intValue();
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.field_70180_af.func_187227_b(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
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
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if (!potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public void func_70108_f(Entity entityIn) {
    if (!func_184223_x(entityIn))
      if (!entityIn.field_70145_X && !this.field_70145_X) {
        double d0 = entityIn.field_70165_t - this.field_70165_t;
        double d1 = entityIn.field_70161_v - this.field_70161_v;
        double d2 = MathHelper.func_76132_a(d0, d1);
        if (d2 >= 0.01D) {
          d2 = MathHelper.func_76133_a(d2);
          d0 /= d2;
          d1 /= d2;
          double d3 = 1.0D / d2;
          if (d3 > 1.0D)
            d3 = 1.0D; 
          d0 *= d3;
          d1 *= d3;
          d0 *= 0.2D;
          d1 *= 0.2D;
          entityIn.func_70024_g(d0, 0.0D, d1);
          d0 *= 0.1D;
          d1 *= 0.1D;
          func_70024_g(d0, 0.0D, d1);
        } 
      }  
  }
  
  public EnumPushReaction func_184192_z() {
    return EnumPushReaction.IGNORE;
  }
  
  protected void func_70609_aI() {
    this.deathTicks++;
    if (!this.field_70170_p.field_72995_K)
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s Cha'garoth has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Cha'garoth has been destroyed!", new Object[0]));
        }   
    if (this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity) {
        this.field_70170_p.func_175688_a(EnumParticleTypes.FLAME, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.LAVA, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        if (this.deathTicks >= 190 && this.deathTicks <= 200)
          this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
      } 
    } 
    if (!this.field_70170_p.field_72995_K && 
      this.deathTicks > 180 && this.deathTicks % 2 == 0) {
      int i = 1000;
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.dread_fragment, 4)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.dreaded_chunk_of_abyssalnite, 2)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.dreaded_shard_of_abyssalnite)));
        this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.dreadium_ingot)));
      } 
    } 
    if (this.deathTicks == 100 && !this.field_70170_p.field_72995_K)
      SpecialTextUtil.ChagarothGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.chagaroth.death.1") }); 
    if (this.deathTicks == 200 && !this.field_70170_p.field_72995_K) {
      List<Entity> list = this.field_70170_p.field_72996_f;
      if (list != null)
        for (int k2 = 0; k2 < list.size(); k2++) {
          Entity entity = list.get(k2);
          if (entity instanceof EntityJzahar && entity.func_70089_S())
            SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { ((EntityJzahar)entity).func_184191_r((Entity)this) ? I18n.func_74838_a("message.jzaharhelpful.snidecomment.chagaroth") : I18n.func_74838_a("message.jzahar.snidecomment.chagaroth") }); 
        }  
      func_70106_y();
      this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(ACItems.dread_plagued_gateway_key)));
    } 
  }
  
  private int posneg(int num) {
    return this.field_70146_Z.nextBoolean() ? this.field_70146_Z.nextInt(num) : (-1 * this.field_70146_Z.nextInt(num));
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack itemstack = player.func_184586_b(hand);
    if (itemstack.func_190926_b()) {
      if (hasOwner(player)) {
        player.func_184609_a(EnumHand.MAIN_HAND);
        if (func_184187_bx() == null) {
          func_184205_a((Entity)player, true);
        } else {
          func_184210_p();
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public double func_70033_W() {
    return 0.25D;
  }
  
  public void func_70098_U() {
    super.func_70098_U();
    if (func_184187_bx() instanceof EntityLivingBase) {
      EntityLivingBase entitycreature = (EntityLivingBase)func_184187_bx();
      entitycreature.func_70066_B();
      entitycreature.func_184596_c(AbyssalCraftAPI.dread_plague);
      this.field_70761_aq = entitycreature.field_70761_aq;
      if (entitycreature.field_70122_E) {
        entitycreature.field_70159_w *= 0.125D;
        entitycreature.field_70179_y *= 0.125D;
      } else {
        entitycreature.field_70159_w *= 0.75D;
        entitycreature.field_70179_y *= 0.75D;
      } 
      if (entitycreature.field_70181_x > 0.0D)
        entitycreature.field_70181_x *= 0.9D; 
      if (entitycreature.field_70181_x < 0.0D)
        entitycreature.field_70181_x *= 1.1D; 
    } 
  }
}
