package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRabbit extends EntityTameBase implements Light, Tiny, Animal {
  private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.func_187226_a(EntityRabbit.class, DataSerializers.field_187192_b);
  
  private int field_175540_bm = 0;
  
  private int field_175535_bn = 0;
  
  private boolean field_175537_bp = false;
  
  private int currentMoveTypeDuration = 0;
  
  private int carrotTicks = 0;
  
  public EntityRabbit(World worldIn) {
    super(worldIn);
    func_70105_a(0.4F, 0.5F);
    this.field_70767_i = new RabbitJumpHelper(this);
    this.field_70765_h = new RabbitMoveHelper(this);
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new AIRaidFarm(this));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D, 80));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.25D, 32.0F, 8.0F));
    setMovementSpeed(0.0D);
  }
  
  public String getDescName() {
    return (getRabbitType() == 99) ? "rabbit_murder" : "rabbit";
  }
  
  public int getNextLevelRequirement() {
    return (getRabbitType() == 99) ? 100 : 5;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityRabbit(this.field_70170_p);
  }
  
  public boolean canBeButchered() {
    return (getRabbitType() != 99);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  protected float func_175134_bD() {
    if (!this.field_70123_F && (!this.field_70765_h.func_75640_a() || this.field_70765_h.func_179919_e() <= this.field_70163_u + 0.5D)) {
      Path pathentity = this.field_70699_by.func_75505_d();
      if (pathentity != null && pathentity.func_75873_e() < pathentity.func_75874_d()) {
        Vec3d vec3d = pathentity.func_75878_a((Entity)this);
        if (vec3d.field_72448_b > this.field_70163_u)
          return 0.5F; 
      } 
      return (this.field_70765_h.func_75638_b() <= 0.6D) ? 0.2F : 0.3F;
    } 
    return 0.5F;
  }
  
  public EnumTier getTier() {
    return (getRabbitType() == 99) ? EnumTier.TIER4 : EnumTier.TIER1;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(RABBIT_TYPE, Integer.valueOf(0));
  }
  
  public void func_70619_bc() {
    super.func_70619_bc();
    if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && getRabbitType() == 99 && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 4.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
      func_70652_k((Entity)func_70638_az()); 
    if (this.currentMoveTypeDuration > 0)
      this.currentMoveTypeDuration--; 
    if (this.carrotTicks > 0) {
      this.carrotTicks -= this.field_70146_Z.nextInt(3);
      if (this.carrotTicks < 0)
        this.carrotTicks = 0; 
    } 
    if (this.field_70122_E) {
      if (!this.field_175537_bp) {
        func_70637_d(false);
        func_175517_cu();
      } 
      if (getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
        EntityLivingBase entitylivingbase = func_70638_az();
        if (entitylivingbase != null && func_70068_e((Entity)entitylivingbase) < 16.0D) {
          calculateRotationYaw(entitylivingbase.field_70165_t, entitylivingbase.field_70161_v);
          this.field_70765_h.func_75642_a(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v, this.field_70765_h.func_75638_b());
          func_184770_cZ();
          this.field_175537_bp = true;
        } 
      } 
      RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper)this.field_70767_i;
      if (!entityrabbit$rabbitjumphelper.getIsJumping()) {
        if (this.field_70765_h.func_75640_a() && this.currentMoveTypeDuration == 0) {
          Path pathentity = this.field_70699_by.func_75505_d();
          Vec3d vec3d = new Vec3d(this.field_70765_h.func_179917_d(), this.field_70765_h.func_179919_e(), this.field_70765_h.func_179918_f());
          if (pathentity != null && pathentity.func_75873_e() < pathentity.func_75874_d())
            vec3d = pathentity.func_75878_a((Entity)this); 
          calculateRotationYaw(vec3d.field_72450_a, vec3d.field_72449_c);
          func_184770_cZ();
        } 
      } else if (!entityrabbit$rabbitjumphelper.func_180065_d()) {
        func_175518_cr();
      } 
    } 
    this.field_175537_bp = this.field_70122_E;
  }
  
  public void func_184770_cZ() {
    func_70637_d(true);
    this.field_175535_bn = 10;
    this.field_175540_bm = 0;
  }
  
  public void func_174830_Y() {}
  
  private void calculateRotationYaw(double x, double z) {
    this.field_70177_z = (float)(MathHelper.func_181159_b(z - this.field_70161_v, x - this.field_70165_t) * 57.29577951308232D) - 90.0F;
  }
  
  private void func_175518_cr() {
    ((RabbitJumpHelper)this.field_70767_i).func_180066_a(true);
  }
  
  private void func_175520_cs() {
    ((RabbitJumpHelper)this.field_70767_i).func_180066_a(false);
  }
  
  private void updateMoveTypeDuration() {
    if (this.field_70765_h.func_75638_b() < 2.2D) {
      this.currentMoveTypeDuration = 10;
    } else {
      this.currentMoveTypeDuration = 1;
    } 
  }
  
  private void func_175517_cu() {
    updateMoveTypeDuration();
    func_175520_cs();
  }
  
  public void func_70636_d() {
    ItemStack charge = (getRabbitType() == 99) ? new ItemStack(Items.field_151144_bL) : ItemStack.field_190927_a;
    charge.func_151001_c("Killer Rabbit");
    this.basicInventory.func_70299_a(7, charge);
    super.func_70636_d();
    this.isOffensive = (getRabbitType() == 99);
    if (this.field_175540_bm != this.field_175535_bn) {
      if (this.field_175540_bm == 0 && !this.field_70170_p.field_72995_K)
        this.field_70170_p.func_72960_a((Entity)this, (byte)1); 
      this.field_175540_bm++;
    } else if (this.field_175535_bn != 0) {
      this.field_175540_bm = 0;
      this.field_175535_bn = 0;
    } 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("RabbitType", getRabbitType());
    tagCompound.func_74768_a("MoreCarrotTicks", this.carrotTicks);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setRabbitType(tagCompund.func_74762_e("RabbitType"));
    this.carrotTicks = tagCompund.func_74762_e("MoreCarrotTicks");
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (getRabbitType() == 99) {
      entityIn.field_70172_ad = 0;
      func_184185_a(SoundEvents.field_187818_ek, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
    } 
    return super.func_70652_k(entityIn);
  }
  
  protected SoundEvent getJumpSound() {
    return SoundEvents.field_187824_en;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187816_ej;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187822_em;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187820_el;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source) || (source.func_82725_o() && getRabbitType() == 99))
      return false; 
    return super.func_70097_a(source, amount);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_RABBIT;
  }
  
  public int getRabbitType() {
    return ((Integer)this.field_70180_af.func_187225_a(RABBIT_TYPE)).intValue();
  }
  
  public void setRabbitType(int rabbitTypeId) {
    if (rabbitTypeId == 99) {
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
      this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIEvilAttack(this));
      this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
      this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
      this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
      this.isOffensive = true;
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
      func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
      this.field_70728_aV = 50;
      if (!func_145818_k_())
        func_96094_a(I18n.func_74838_a("entity.KillerBunny.name")); 
    } else {
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
    } 
    this.field_70728_aV = 1;
    this.field_70180_af.func_187227_b(RABBIT_TYPE, Integer.valueOf(rabbitTypeId));
  }
  
  protected void func_70664_aZ() {
    super.func_70664_aZ();
    double d0 = this.field_70765_h.func_75638_b();
    if (d0 > 0.0D) {
      double d1 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
      if (d1 < 0.010000000000000002D)
        func_191958_b(0.0F, 0.0F, 1.0F, 0.1F); 
    } 
    if (!this.field_70170_p.field_72995_K)
      this.field_70170_p.func_72960_a((Entity)this, (byte)1); 
  }
  
  @SideOnly(Side.CLIENT)
  public float func_175521_o(float p_175521_1_) {
    return (this.field_175535_bn == 0) ? 0.0F : ((this.field_175540_bm + p_175521_1_) / this.field_175535_bn);
  }
  
  public void setMovementSpeed(double newSpeed) {
    func_70661_as().func_75489_a(newSpeed);
    this.field_70765_h.func_75642_a(this.field_70765_h.func_179917_d(), this.field_70765_h.func_179919_e(), this.field_70765_h.func_179918_f(), newSpeed);
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    int i = this.field_70146_Z.nextInt(6);
    boolean flag = false;
    if (livingdata instanceof RabbitTypeData) {
      i = ((RabbitTypeData)livingdata).typeData;
      flag = true;
    } else {
      livingdata = new RabbitTypeData(i);
    } 
    setRabbitType(i);
    if (this.field_70146_Z.nextInt(2500) == 0)
      setRabbitType(99); 
    return livingdata;
  }
  
  private boolean isCarrotEaten() {
    return (this.carrotTicks == 0);
  }
  
  protected void createEatingParticles() {
    BlockCarrot blockcarrot = (BlockCarrot)Blocks.field_150459_bM;
    IBlockState iblockstate = blockcarrot.func_185528_e(blockcarrot.func_185526_g());
    this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_DUST, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 0.5D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[] { Block.func_176210_f(iblockstate) });
    this.carrotTicks = 40;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 1) {
      func_174808_Z();
      this.field_175535_bn = 10;
      this.field_175540_bm = 0;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public void func_184206_a(DataParameter<?> key) {
    super.func_184206_a(key);
  }
  
  static class AIEvilAttack extends EntityAIFriendlyAttackMelee {
    public AIEvilAttack(EntityRabbit rabbit) {
      super(rabbit, 3.0D, true);
    }
  }
  
  static class AIPanic extends EntityAIPanic {
    private EntityRabbit theEntity;
    
    public AIPanic(EntityRabbit rabbit, double speedIn) {
      super((EntityCreature)rabbit, speedIn);
      this.theEntity = rabbit;
    }
    
    public void func_75246_d() {
      super.func_75246_d();
      this.theEntity.setMovementSpeed(this.field_75265_b);
    }
  }
  
  static class AIRaidFarm extends EntityAIMoveToBlock {
    private final EntityRabbit rabbit;
    
    private boolean field_179498_d;
    
    private boolean field_179499_e = false;
    
    public AIRaidFarm(EntityRabbit rabbitIn) {
      super((EntityCreature)rabbitIn, 0.75D, 16);
      this.rabbit = rabbitIn;
    }
    
    public boolean func_75250_a() {
      if (this.field_179496_a <= 0) {
        if (!EngenderConfig.mobs.grief)
          return false; 
        this.field_179499_e = false;
        this.field_179498_d = this.rabbit.isCarrotEaten();
        this.field_179498_d = true;
      } 
      return super.func_75250_a();
    }
    
    public boolean func_75253_b() {
      return (this.field_179499_e && super.func_75253_b());
    }
    
    public void func_75249_e() {
      super.func_75249_e();
    }
    
    public void func_75251_c() {
      super.func_75251_c();
    }
    
    public void func_75246_d() {
      super.func_75246_d();
      this.rabbit.func_70671_ap().func_75650_a(this.field_179494_b.func_177958_n() + 0.5D, (this.field_179494_b.func_177956_o() + 1), this.field_179494_b.func_177952_p() + 0.5D, 10.0F, this.rabbit.func_70646_bf());
      if (func_179487_f()) {
        World world = this.rabbit.field_70170_p;
        BlockPos blockpos = this.field_179494_b.func_177984_a();
        IBlockState iblockstate = world.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        if (this.field_179499_e && block instanceof BlockCarrot) {
          Integer integer = (Integer)iblockstate.func_177229_b((IProperty)BlockCarrot.field_176488_a);
          if (integer.intValue() == 0) {
            world.func_180501_a(blockpos, Blocks.field_150350_a.func_176223_P(), 2);
            world.func_175655_b(blockpos, true);
          } else {
            world.func_180501_a(blockpos, iblockstate.func_177226_a((IProperty)BlockCarrot.field_176488_a, Integer.valueOf(integer.intValue() - 1)), 2);
            world.func_175718_b(2001, blockpos, Block.func_176210_f(iblockstate));
          } 
          this.rabbit.createEatingParticles();
        } 
        this.field_179499_e = false;
        this.field_179496_a = 10;
      } 
    }
    
    protected boolean func_179488_a(World worldIn, BlockPos pos) {
      Block block = worldIn.func_180495_p(pos).func_177230_c();
      if (block == Blocks.field_150458_ak && this.field_179498_d && !this.field_179499_e) {
        pos = pos.func_177984_a();
        IBlockState iblockstate = worldIn.func_180495_p(pos);
        block = iblockstate.func_177230_c();
        if (block instanceof BlockCarrot && ((BlockCarrot)block).func_185525_y(iblockstate)) {
          this.field_179499_e = true;
          return true;
        } 
      } 
      return false;
    }
  }
  
  public class RabbitJumpHelper extends EntityJumpHelper {
    private EntityRabbit theEntity;
    
    private boolean field_180068_d = false;
    
    public RabbitJumpHelper(EntityRabbit rabbit) {
      super((EntityLiving)rabbit);
      this.theEntity = rabbit;
    }
    
    public boolean getIsJumping() {
      return this.field_75662_b;
    }
    
    public boolean func_180065_d() {
      return this.field_180068_d;
    }
    
    public void func_180066_a(boolean p_180066_1_) {
      this.field_180068_d = p_180066_1_;
    }
    
    public void func_75661_b() {
      if (this.field_75662_b) {
        this.theEntity.func_184770_cZ();
        this.field_75662_b = false;
      } 
    }
  }
  
  static class RabbitMoveHelper extends EntityMoveHelper {
    private EntityRabbit theEntity;
    
    private double field_188492_j;
    
    public RabbitMoveHelper(EntityRabbit rabbit) {
      super((EntityLiving)rabbit);
      this.theEntity = rabbit;
    }
    
    public void func_75641_c() {
      if (this.theEntity.field_70122_E && !this.theEntity.field_70703_bu && !((EntityRabbit.RabbitJumpHelper)this.theEntity.field_70767_i).getIsJumping()) {
        this.theEntity.setMovementSpeed(0.0D);
      } else if (func_75640_a()) {
        this.theEntity.setMovementSpeed(this.field_188492_j);
      } 
      super.func_75641_c();
    }
    
    public void func_75642_a(double x, double y, double z, double speedIn) {
      if (this.theEntity.func_70090_H())
        speedIn = 1.5D; 
      super.func_75642_a(x, y, z, speedIn);
      if (speedIn > 0.0D)
        this.field_188492_j = speedIn; 
    }
  }
  
  public static class RabbitTypeData implements IEntityLivingData {
    public int typeData;
    
    public RabbitTypeData(int type) {
      this.typeData = type;
    }
  }
}
