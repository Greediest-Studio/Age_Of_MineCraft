package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityParrot extends EntityTameBase implements EntityFlying, Light, Animal, Flying {
  private static final DataParameter<Integer> VARIANT = EntityDataManager.func_187226_a(EntityParrot.class, DataSerializers.field_187192_b);
  
  private static final Predicate<EntityLiving> CAN_MIMIC = new Predicate<EntityLiving>() {
      public boolean apply(@Nullable EntityLiving p_apply_1_) {
        return (p_apply_1_ != null && EntityParrot.MIMIC_SOUNDS.containsKey(p_apply_1_.getClass()));
      }
    };
  
  private static final Map<Class<? extends Entity>, SoundEvent> MIMIC_SOUNDS = Maps.newHashMapWithExpectedSize(32);
  
  public float flap;
  
  public float flapSpeed;
  
  public float oFlapSpeed;
  
  public float oFlap;
  
  public float flapping = 1.0F;
  
  public EntityParrot(World worldIn) {
    super(worldIn);
    func_70105_a(0.3F, 0.7F);
    this.field_70765_h = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
  }
  
  public String getDescName() {
    return "parrot";
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    setVariant(this.field_70146_Z.nextInt(5));
    return super.func_180482_a(difficulty, livingdata);
  }
  
  protected void func_184651_r() {
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.33D, 15.0F, 4.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIWanderAvoidWaterFlying((EntityCreature)this, 1.0D));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(6.0D);
    func_110148_a(SharedMonsterAttributes.field_193334_e).func_111128_a(0.75D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying(this, (EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return (PathNavigate)pathnavigateflying;
  }
  
  public float func_70047_e() {
    return 0.6F;
  }
  
  public void func_70636_d() {
    playMimicSound(this.field_70170_p, (Entity)this);
    super.func_70636_d();
    calculateFlapping();
  }
  
  private void calculateFlapping() {
    this.oFlap = this.flap;
    this.oFlapSpeed = this.flapSpeed;
    this.flapSpeed = (float)(this.flapSpeed + (this.field_70122_E ? -1 : 4) * 0.3D);
    this.flapSpeed = MathHelper.func_76131_a(this.flapSpeed, 0.0F, 1.0F);
    if (!this.field_70122_E && this.flapping < 1.0F)
      this.flapping = 1.0F; 
    this.flapping = (float)(this.flapping * 0.9D);
    if (!this.field_70122_E && this.field_70181_x < 0.0D && func_70089_S())
      this.field_70181_x *= 0.6D; 
    this.flap += this.flapping * 2.0F;
  }
  
  private static boolean playMimicSound(World worldIn, Entity p_192006_1_) {
    if (!p_192006_1_.func_174814_R() && worldIn.field_73012_v.nextInt(50) == 0) {
      List<EntityLiving> list = worldIn.func_175647_a(EntityLiving.class, p_192006_1_.func_174813_aQ().func_186662_g(20.0D), CAN_MIMIC);
      if (!list.isEmpty()) {
        EntityLiving entityliving = list.get(worldIn.field_73012_v.nextInt(list.size()));
        if (!entityliving.func_174814_R()) {
          SoundEvent soundevent = MIMIC_SOUNDS.get(entityliving.getClass());
          worldIn.func_184148_a((EntityPlayer)null, p_192006_1_.field_70165_t, p_192006_1_.field_70163_u, p_192006_1_.field_70161_v, soundevent, p_192006_1_.func_184176_by(), 0.7F, getPitch(worldIn.field_73012_v));
          return true;
        } 
      } 
      return false;
    } 
    return false;
  }
  
  public boolean isBreedingItem(ItemStack stack) {
    return false;
  }
  
  public boolean func_70601_bi() {
    int i = MathHelper.func_76128_c(this.field_70165_t);
    int j = MathHelper.func_76128_c((func_174813_aQ()).field_72338_b);
    int k = MathHelper.func_76128_c(this.field_70161_v);
    BlockPos blockpos = new BlockPos(i, j, k);
    Block block = this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c();
    return (block instanceof net.minecraft.block.BlockLeaves || block == Blocks.field_150349_c || block instanceof net.minecraft.block.BlockLog || (block == Blocks.field_150350_a && this.field_70170_p.func_175699_k(blockpos) > 8 && super.func_70601_bi()));
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {}
  
  protected void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public static void playAmbientSound(World worldIn, Entity p_192005_1_) {
    if (!p_192005_1_.func_174814_R() && !playMimicSound(worldIn, p_192005_1_) && worldIn.field_73012_v.nextInt(200) == 0)
      worldIn.func_184148_a((EntityPlayer)null, p_192005_1_.field_70165_t, p_192005_1_.field_70163_u, p_192005_1_.field_70161_v, getAmbientSound(worldIn.field_73012_v), p_192005_1_.func_184176_by(), 1.0F, getPitch(worldIn.field_73012_v)); 
  }
  
  @Nullable
  public SoundEvent func_184639_G() {
    return getAmbientSound(this.field_70146_Z);
  }
  
  private static SoundEvent getAmbientSound(Random random) {
    if (random.nextInt(1000) == 0) {
      List<SoundEvent> list = new ArrayList<>(MIMIC_SOUNDS.values());
      SoundEvent ret = list.get(random.nextInt(list.size()));
      return (ret == null) ? SoundEvents.field_192792_ep : ret;
    } 
    return SoundEvents.field_192792_ep;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return SoundEvents.field_192794_er;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_192793_eq;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_192795_es, 0.15F, 1.0F);
  }
  
  protected float func_191954_d(float p_191954_1_) {
    func_184185_a(SoundEvents.field_192796_et, 0.15F, 1.0F);
    return p_191954_1_ + this.flapSpeed / 2.0F;
  }
  
  protected boolean func_191957_ae() {
    return true;
  }
  
  protected float func_70647_i() {
    return getPitch(this.field_70146_Z);
  }
  
  private static float getPitch(Random random) {
    return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
  }
  
  public SoundCategory func_184176_by() {
    return SoundCategory.NEUTRAL;
  }
  
  public boolean func_70104_M() {
    return true;
  }
  
  protected void func_82167_n(Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer))
      super.func_82167_n(entityIn); 
  }
  
  public int getVariant() {
    return MathHelper.func_76125_a(((Integer)this.field_70180_af.func_187225_a(VARIANT)).intValue(), 0, 4);
  }
  
  public void setVariant(int p_191997_1_) {
    this.field_70180_af.func_187227_b(VARIANT, Integer.valueOf(p_191997_1_));
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(VARIANT, Integer.valueOf(0));
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74768_a("Variant", getVariant());
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    setVariant(compound.func_74762_e("Variant"));
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return LootTableList.field_192561_ax;
  }
  
  public boolean isFlying() {
    return !this.field_70122_E;
  }
  
  static {
    registerMimicSound((Class)EntityBlaze.class, SoundEvents.field_193791_eM);
    registerMimicSound((Class)EntityCaveSpider.class, SoundEvents.field_193813_fc);
    registerMimicSound((Class)EntityCreeper.class, SoundEvents.field_193792_eN);
    registerMimicSound((Class)EntityElderGuardian.class, SoundEvents.field_193793_eO);
    registerMimicSound((Class)EntityDragon.class, SoundEvents.field_193794_eP);
    registerMimicSound((Class)EntityEnderman.class, SoundEvents.field_193795_eQ);
    registerMimicSound((Class)EntityEndermite.class, SoundEvents.field_193796_eR);
    registerMimicSound((Class)EntityEvoker.class, SoundEvents.field_193797_eS);
    registerMimicSound((Class)EntityGhast.class, SoundEvents.field_193798_eT);
    registerMimicSound((Class)EntityHusk.class, SoundEvents.field_193799_eU);
    registerMimicSound((Class)EntityIllusionIllager.class, SoundEvents.field_193800_eV);
    registerMimicSound((Class)EntityMagmaCube.class, SoundEvents.field_193801_eW);
    registerMimicSound((Class)EntityPigZombie.class, SoundEvents.field_193822_fl);
    registerMimicSound((Class)EntityPolarBear.class, SoundEvents.field_193802_eX);
    registerMimicSound((Class)EntityShulker.class, SoundEvents.field_193803_eY);
    registerMimicSound((Class)EntitySilverfish.class, SoundEvents.field_193804_eZ);
    registerMimicSound((Class)EntitySkeleton.class, SoundEvents.field_193811_fa);
    registerMimicSound((Class)EntitySlime.class, SoundEvents.field_193812_fb);
    registerMimicSound((Class)EntitySpider.class, SoundEvents.field_193813_fc);
    registerMimicSound((Class)EntityStray.class, SoundEvents.field_193814_fd);
    registerMimicSound((Class)EntityVex.class, SoundEvents.field_193815_fe);
    registerMimicSound((Class)EntityVindicator.class, SoundEvents.field_193816_ff);
    registerMimicSound((Class)EntityWitch.class, SoundEvents.field_193817_fg);
    registerMimicSound((Class)EntityWither.class, SoundEvents.field_193818_fh);
    registerMimicSound((Class)EntityWitherSkeleton.class, SoundEvents.field_193819_fi);
    registerMimicSound((Class)EntityWolf.class, SoundEvents.field_193820_fj);
    registerMimicSound((Class)EntityZombie.class, SoundEvents.field_193821_fk);
    registerMimicSound((Class)EntityZombieVillager.class, SoundEvents.field_193823_fm);
  }
  
  public static void registerMimicSound(Class<? extends Entity> cls, SoundEvent sound) {
    MIMIC_SOUNDS.put(cls, sound);
  }
}
