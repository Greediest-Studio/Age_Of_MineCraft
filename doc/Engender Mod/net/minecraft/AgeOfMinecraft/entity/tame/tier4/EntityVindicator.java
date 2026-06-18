package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityAbstractIllagers;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIVindicatorBreakDoor;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVindicator extends EntityAbstractIllagers {
  protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.func_187226_a(EntityVindicator.class, DataSerializers.field_187191_a);
  
  private boolean johnny;
  
  public EntityVindicator(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee((EntityTameBase)this, 1.0D, true));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D, 80));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader((EntityTameBase)this, 1.0D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 10;
  }
  
  public String getDescName() {
    return "vindicator";
  }
  
  @SideOnly(Side.CLIENT)
  public EntityAbstractIllagers.IllagerArmPose getArmPose() {
    return isAggressive() ? EntityAbstractIllagers.IllagerArmPose.ATTACKING : EntityAbstractIllagers.IllagerArmPose.CROSSED;
  }
  
  public int getNextLevelRequirement() {
    return 150;
  }
  
  public float getBonusVSLight() {
    return 0.75F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 1.5F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityVindicator(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3499999940395355D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(15.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(DATA_FLAGS_ID, Byte.valueOf((byte)0));
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.4F) : super.func_70647_i();
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
      for (int i = 0; i < 1; i++) {
        EntityVindicator baby = new EntityVindicator(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setGrowingAge(-60000);
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
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_VINDICATION_ILLAGER;
  }
  
  @SideOnly(Side.CLIENT)
  private boolean getVindicatorFlag(int p_190637_1_) {
    int i = ((Byte)this.field_70180_af.func_187225_a(DATA_FLAGS_ID)).byteValue();
    return ((i & p_190637_1_) != 0);
  }
  
  private void setVindicatorFlag(int p_190638_1_, boolean p_190638_2_) {
    int i = ((Byte)this.field_70180_af.func_187225_a(DATA_FLAGS_ID)).byteValue();
    if (p_190638_2_) {
      i |= p_190638_1_;
    } else {
      i &= p_190638_1_ ^ 0xFFFFFFFF;
    } 
    this.field_70180_af.func_187227_b(DATA_FLAGS_ID, Byte.valueOf((byte)(i & 0xFF)));
  }
  
  public void setAggressive(boolean p_190636_1_) {
    setVindicatorFlag(1, p_190636_1_);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAggressive() {
    return getVindicatorFlag(1);
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    if (this.johnny)
      compound.func_74757_a("Johnny", true); 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    if (compound.func_150297_b("Johnny", 99))
      this.johnny = compound.func_74767_n("Johnny"); 
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    IEntityLivingData ientitylivingdata = super.func_180482_a(difficulty, livingdata);
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    return ientitylivingdata;
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151036_c));
  }
  
  public void func_70636_d() {
    super.func_70636_d();
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (func_70638_az() != null)
      func_70031_b(true); 
    setAggressive(this.johnny ? true : ((func_70638_az() != null)));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && stack.func_77973_b() instanceof net.minecraft.item.ItemAxe) {
      func_70642_aH();
      func_184185_a(SoundEvents.field_187727_dV, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && stack.func_190926_b() && player.func_70093_af()) {
      dropEquipmentUndamaged();
      player.func_184609_a(hand);
      func_184185_a(SoundEvents.field_191268_hm, 1.0F, 1.0F);
      return true;
    } 
    return false;
  }
  
  public void func_96094_a(String name) {
    super.func_96094_a(name);
    if (!this.johnny && "Johnny".equals(name)) {
      this.field_70173_aa = 0;
      func_184185_a(ESound.heresJohnny, 2.0F, 1.0F);
      this.johnny = true;
      ((PathNavigateGround)func_70661_as()).func_179688_b(true);
      this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIVindicatorBreakDoor((EntityLiving)this));
    } else {
      this.johnny = false;
      ((PathNavigateGround)func_70661_as()).func_179688_b(false);
      this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIVindicatorBreakDoor((EntityLiving)this));
    } 
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_191268_hm;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() - 0.3F); 
    return SoundEvents.field_191269_hn;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() - 0.3F); 
    return SoundEvents.field_191270_ho;
  }
}
