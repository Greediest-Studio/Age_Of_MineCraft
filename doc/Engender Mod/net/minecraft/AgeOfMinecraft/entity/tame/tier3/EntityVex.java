package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVex extends EntityTameBase implements Light, Flying, Elemental {
  protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.func_187226_a(EntityVex.class, DataSerializers.field_187191_a);
  
  @Nullable
  private BlockPos boundOrigin;
  
  public EntityVex(World worldIn) {
    super(worldIn);
    this.field_70178_ae = true;
    this.field_70765_h = new AIMoveControl(this);
    func_70105_a(0.4F, 0.8F);
    this.field_70728_aV = 3;
  }
  
  public String getDescName() {
    return "vex";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 20;
  }
  
  public void func_70091_d(MoverType type, double x, double y, double z) {
    super.func_70091_d(type, x, y, z);
    func_145775_I();
  }
  
  protected void dropEquipmentUndamaged() {}
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSArmored() {
    return 0.9F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityVex(this.field_70170_p);
  }
  
  public void func_70071_h_() {
    this.field_70145_X = true;
    super.func_70071_h_();
    this.field_70145_X = false;
    if (func_70089_S()) {
      func_189654_d(true);
    } else {
      func_189654_d(false);
    } 
    func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151040_l));
    func_184642_a(EntityEquipmentSlot.MAINHAND, 0.0F);
    if (!isWild())
      if (func_70638_az() != null) {
        setBoundOrigin(func_70638_az().func_180425_c());
      } else {
        setBoundOrigin((getJukeboxToDanceTo() != null) ? getJukeboxToDanceTo().func_177981_b(2) : ((getGuardBlock() != null) ? new BlockPos(this.randPosX, this.randPosY, this.randPosZ) : getOwner().func_180425_c().func_177981_b((int)getOwner().func_70047_e())));
      }  
    if (!this.field_70170_p.field_72995_K && func_70089_S() && func_70638_az() == null && getSpecialAttackTimer() > 600) {
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(24.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
        EntityLivingBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
        if (entitylivingbase != this && !func_184191_r((Entity)entitylivingbase) && entitylivingbase.func_70089_S()) {
          func_70624_b(entitylivingbase);
          Vec3d vec3d = entitylivingbase.func_174824_e(1.0F);
          this.field_70765_h.func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
          break;
        } 
      } 
    } 
  }
  
  protected void func_184651_r() {
    super.func_184651_r();
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(4, new AIChargeAttack());
    this.field_70714_bg.func_75776_a(8, new AIMoveRandom());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 24.0F, 6.0F));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIFriendlyHurtByTarget((EntityCreature)this, true, new Class[0]));
    this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
    this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(14.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(VEX_FLAGS, Byte.valueOf((byte)0));
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    if (compound.func_74764_b("BoundX"))
      this.boundOrigin = new BlockPos(compound.func_74762_e("BoundX"), compound.func_74762_e("BoundY"), compound.func_74762_e("BoundZ")); 
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    if (this.boundOrigin != null) {
      compound.func_74768_a("BoundX", this.boundOrigin.func_177958_n());
      compound.func_74768_a("BoundY", this.boundOrigin.func_177956_o());
      compound.func_74768_a("BoundZ", this.boundOrigin.func_177952_p());
    } 
  }
  
  @Nullable
  public BlockPos getBoundOrigin() {
    return this.boundOrigin;
  }
  
  public void setBoundOrigin(@Nullable BlockPos boundOriginIn) {
    this.boundOrigin = boundOriginIn;
  }
  
  private boolean getVexFlag(int p_190656_1_) {
    int i = ((Byte)this.field_70180_af.func_187225_a(VEX_FLAGS)).byteValue();
    return ((i & p_190656_1_) != 0);
  }
  
  private void setVexFlag(int p_190660_1_, boolean p_190660_2_) {
    int i = ((Byte)this.field_70180_af.func_187225_a(VEX_FLAGS)).byteValue();
    if (p_190660_2_) {
      i |= p_190660_1_;
    } else {
      i &= p_190660_1_ ^ 0xFFFFFFFF;
    } 
    this.field_70180_af.func_187227_b(VEX_FLAGS, Byte.valueOf((byte)(i & 0xFF)));
  }
  
  public boolean isCharging() {
    return (getVexFlag(1) || getJukeboxToDanceTo() != null);
  }
  
  public void setIsCharging(boolean p_190648_1_) {
    setVexFlag(1, p_190648_1_);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_191264_hc;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_191266_he;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_191267_hf;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    return super.func_180482_a(difficulty, livingdata);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_VEX;
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151040_l));
    func_184642_a(EntityEquipmentSlot.MAINHAND, 0.0F);
  }
  
  class AIChargeAttack extends EntityAIBase {
    public AIChargeAttack() {
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      return (EntityVex.this.func_70638_az() != null && EntityVex.this.getSpecialAttackTimer() > 600) ? true : ((EntityVex.this.func_70638_az() != null && !EntityVex.this.func_70605_aq().func_75640_a() && EntityVex.this.field_70146_Z.nextInt(7) == 0) ? ((EntityVex.this.func_70068_e((Entity)EntityVex.this.func_70638_az()) > 4.0D)) : false);
    }
    
    public boolean func_75253_b() {
      return (EntityVex.this.func_70638_az() != null && EntityVex.this.getSpecialAttackTimer() > 600) ? true : ((EntityVex.this.func_70605_aq().func_75640_a() && EntityVex.this.isCharging() && EntityVex.this.func_70638_az() != null && EntityVex.this.func_70638_az().func_70089_S()));
    }
    
    public void func_75249_e() {
      EntityLivingBase entitylivingbase = EntityVex.this.func_70638_az();
      Vec3d vec3d = entitylivingbase.func_174824_e(1.0F);
      EntityVex.this.field_70765_h.func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
      EntityVex.this.setIsCharging(true);
      EntityVex.this.func_184185_a(SoundEvents.field_191265_hd, 1.0F, 1.0F);
      if (EntityVex.this.func_70638_az() != null && EntityVex.this.getSpecialAttackTimer() <= 0 && EntityVex.this.isHero()) {
        EntityVex.this.setSpecialAttackTimer(900);
        EntityVex.this.func_184185_a(SoundEvents.field_191265_hd, 10.0F, 0.75F);
      } 
    }
    
    public void func_75251_c() {
      EntityVex.this.setIsCharging(false);
    }
    
    public void func_75246_d() {
      EntityLivingBase entitylivingbase = EntityVex.this.func_70638_az();
      if (entitylivingbase != null && entitylivingbase.func_70089_S())
        if (EntityVex.this.func_70068_e((Entity)entitylivingbase) <= (EntityVex.this.field_70130_N * EntityVex.this.field_70130_N + entitylivingbase.field_70130_N * entitylivingbase.field_70130_N) + 9.0D) {
          EntityVex.this.func_70652_k((Entity)entitylivingbase);
        } else {
          double d0 = EntityVex.this.func_70068_e((Entity)entitylivingbase);
          if (d0 < 9.0D) {
            Vec3d vec3d = entitylivingbase.func_174824_e(1.0F);
            EntityVex.this.field_70765_h.func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
          } 
        }  
    }
  }
  
  class AIMoveControl extends EntityMoveHelper {
    public AIMoveControl(EntityVex vex) {
      super((EntityLiving)vex);
    }
    
    public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
        double d0 = this.field_75646_b - EntityVex.this.field_70165_t;
        double d1 = this.field_75647_c - EntityVex.this.field_70163_u;
        double d2 = this.field_75644_d - EntityVex.this.field_70161_v;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.func_76133_a(d3);
        if (d3 < EntityVex.this.func_174813_aQ().func_72320_b()) {
          this.field_188491_h = EntityMoveHelper.Action.WAIT;
          EntityVex.this.field_70159_w *= 0.5D;
          EntityVex.this.field_70181_x *= 0.5D;
          EntityVex.this.field_70179_y *= 0.5D;
        } else {
          EntityVex.this.field_70159_w += d0 / d3 * 0.2D * this.field_75645_e;
          EntityVex.this.field_70181_x += d1 / d3 * 0.05D * this.field_75645_e;
          EntityVex.this.field_70179_y += d2 / d3 * 0.2D * this.field_75645_e;
          if (EntityVex.this.func_70638_az() == null) {
            EntityVex.this.field_70177_z = -((float)MathHelper.func_181159_b(EntityVex.this.field_70159_w, EntityVex.this.field_70179_y)) * 57.295776F;
            EntityVex.this.field_70761_aq = EntityVex.this.field_70177_z;
          } else {
            double d4 = (EntityVex.this.func_70638_az()).field_70165_t - EntityVex.this.field_70165_t;
            double d5 = (EntityVex.this.func_70638_az()).field_70161_v - EntityVex.this.field_70161_v;
            EntityVex.this.field_70177_z = -((float)MathHelper.func_181159_b(d4, d5)) * 57.295776F;
            EntityVex.this.field_70761_aq = EntityVex.this.field_70177_z;
            EntityVex.this.func_70625_a((Entity)EntityVex.this.func_70638_az(), 10.0F, 40.0F);
          } 
        } 
      } 
    }
  }
  
  class AIMoveRandom extends EntityAIBase {
    public AIMoveRandom() {
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      return (!EntityVex.this.func_70605_aq().func_75640_a() && (EntityVex.this.field_70146_Z.nextInt(7) == 0 || EntityVex.this.getJukeboxToDanceTo() != null));
    }
    
    public boolean func_75253_b() {
      return false;
    }
    
    public void func_75246_d() {
      BlockPos blockpos = EntityVex.this.getBoundOrigin();
      if (blockpos == null)
        blockpos = new BlockPos((Entity)EntityVex.this); 
      for (int i = 0; i < 3; i++) {
        BlockPos blockpos1 = blockpos.func_177982_a(EntityVex.this.field_70146_Z.nextInt(15) - 7, EntityVex.this.field_70146_Z.nextInt(11) - 5, EntityVex.this.field_70146_Z.nextInt(15) - 7);
        if (EntityVex.this.field_70170_p.func_175623_d(blockpos1)) {
          EntityVex.this.field_70765_h.func_75642_a(blockpos1.func_177958_n() + 0.5D, blockpos1.func_177956_o() + 0.5D, blockpos1.func_177952_p() + 0.5D, 0.25D);
          if (EntityVex.this.func_70638_az() == null) {
            if (EntityVex.this.getJukeboxToDanceTo() != null) {
              EntityVex.this.func_70671_ap().func_75650_a(EntityVex.this.getJukeboxToDanceTo().func_177958_n() + 0.5D, EntityVex.this.getJukeboxToDanceTo().func_177956_o() + 0.5D, EntityVex.this.getJukeboxToDanceTo().func_177952_p() + 0.5D, 180.0F, 0.0F);
              break;
            } 
            EntityVex.this.func_70671_ap().func_75650_a(blockpos1.func_177958_n() + 0.5D, blockpos1.func_177956_o() + 0.5D, blockpos1.func_177952_p() + 0.5D, 180.0F, 20.0F);
          } 
          break;
        } 
      } 
    }
  }
}
