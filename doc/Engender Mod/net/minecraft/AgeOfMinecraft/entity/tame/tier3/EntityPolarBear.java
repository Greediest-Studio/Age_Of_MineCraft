package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPolarBear extends EntityTameBase implements Light, Animal {
  private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.func_187226_a(EntityPolarBear.class, DataSerializers.field_187198_h);
  
  private float clientSideStandAnimation0;
  
  private float clientSideStandAnimation;
  
  private int warningSoundTicks;
  
  public EntityPolarBear(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    func_70105_a(1.3F, 1.4F);
    this.field_70728_aV = 5;
  }
  
  public String getDescName() {
    return "polar_bear";
  }
  
  protected void func_184651_r() {
    super.func_184651_r();
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new AIMeleeAttack());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 9.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPolarBear(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (this.clientSideStandAnimation > 0.0F)
      func_70105_a(1.3F, 1.4F + this.clientSideStandAnimation * 0.275F); 
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  protected SoundEvent func_184639_G() {
    return func_70631_g_() ? SoundEvents.field_190027_es : SoundEvents.field_190026_er;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_190029_eu;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_190028_et;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_190030_ev, 0.15F, 1.0F / getFittness());
  }
  
  protected void playWarningSound() {
    if (this.warningSoundTicks <= 0) {
      func_184185_a(SoundEvents.field_190031_ew, 1.0F, 1.0F);
      this.warningSoundTicks = 20;
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_POLAR_BEAR;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(IS_STANDING, Boolean.valueOf(false));
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getJukeboxToDanceTo() != null)
      if (this.field_70173_aa % 10 == 0 || this.field_70173_aa % 10 == 1 || this.field_70173_aa % 10 == 2 || this.field_70173_aa % 10 == 3 || this.field_70173_aa % 10 == 4) {
        setStanding(false);
      } else {
        setStanding(true);
      }  
    if (this.field_70170_p.field_72995_K) {
      this.clientSideStandAnimation0 = this.clientSideStandAnimation;
      if (isStanding()) {
        this.clientSideStandAnimation = MathHelper.func_76131_a(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
      } else {
        this.clientSideStandAnimation = MathHelper.func_76131_a(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
      } 
    } 
    if (this.warningSoundTicks > 0)
      this.warningSoundTicks--; 
    if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
      if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 20 == 0) {
        func_70652_k((Entity)func_70638_az());
        func_184609_a(EnumHand.MAIN_HAND);
        if (!func_184592_cb().func_190926_b())
          func_184609_a(EnumHand.OFF_HAND); 
      }  
  }
  
  public boolean func_70652_k(Entity entityIn) {
    boolean flag = super.func_70652_k(entityIn);
    if (flag) {
      func_174815_a((EntityLivingBase)this, entityIn);
      func_184185_a(SoundEvents.field_190031_ew, 1.0F, 1.25F);
    } 
    return flag;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      this.field_70138_W = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70759_as = entitylivingbase.field_70759_as;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      strafe = entitylivingbase.field_70702_br * (func_70090_H() ? 1.0F : 0.5F);
      forward = entitylivingbase.field_191988_bg * (func_70090_H() ? 1.0F : 0.5F);
      if (forward != 0.0F) {
        this.field_70177_z = this.field_70761_aq = this.field_70759_as;
        this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      } 
      if (func_70090_H() || func_180799_ab()) {
        this.field_70181_x += 0.025D;
        if (entitylivingbase.field_191988_bg == 0.0F) {
          this.field_70159_w *= 0.9D;
          this.field_70179_y *= 0.9D;
        } else {
          if (this.field_70159_w > 3.0D)
            this.field_70159_w = 3.0D; 
          if (this.field_70179_y > 3.0D)
            this.field_70179_y = 3.0D; 
          if (this.field_70159_w < -3.0D)
            this.field_70159_w = -3.0D; 
          if (this.field_70179_y < -3.0D)
            this.field_70179_y = -3.0D; 
          this.field_70159_w *= isHero() ? 1.5D : 1.125D;
          this.field_70179_y *= isHero() ? 1.5D : 1.125D;
        } 
      } 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 20.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (entitylivingbase.field_191988_bg > 0.0F && this.field_70173_aa % 7 == 0)
        func_180429_a(new BlockPos((Entity)this), this.field_70170_p.func_180495_p(new BlockPos((Entity)this)).func_177230_c()); 
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
  
  public boolean isStanding() {
    return (((Boolean)this.field_70180_af.func_187225_a(IS_STANDING)).booleanValue() || !func_70089_S());
  }
  
  public void setStanding(boolean standing) {
    this.field_70180_af.func_187227_b(IS_STANDING, Boolean.valueOf(standing));
  }
  
  @SideOnly(Side.CLIENT)
  public float getStandingAnimationScale(float p_189795_1_) {
    return (this.clientSideStandAnimation0 + (this.clientSideStandAnimation - this.clientSideStandAnimation0) * p_189795_1_) / 6.0F;
  }
  
  protected float func_189749_co() {
    return 0.99F;
  }
  
  class AIHurtByTarget extends EntityAIHurtByTarget {
    public AIHurtByTarget() {
      super((EntityCreature)EntityPolarBear.this, false, new Class[0]);
    }
    
    public void func_75249_e() {
      super.func_75249_e();
      if (EntityPolarBear.this.func_70631_g_()) {
        func_190105_f();
        func_75251_c();
      } 
    }
    
    protected void func_179446_a(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
      if (creatureIn instanceof EntityPolarBear && !((EntityPolarBear)creatureIn).func_70631_g_())
        super.func_179446_a(creatureIn, entityLivingBaseIn); 
    }
  }
  
  class AIMeleeAttack extends EntityAIFriendlyAttackMelee {
    public AIMeleeAttack() {
      super(EntityPolarBear.this, 1.25D, true);
    }
    
    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
      double d0 = getAttackReachSqr(p_190102_1_);
      if (p_190102_2_ <= d0 && this.attackTick <= 0.0D) {
        this.attackTick = 20.0D;
        EntityPolarBear.this.func_70652_k((Entity)p_190102_1_);
        EntityPolarBear.this.setStanding(false);
        List<EntityLivingBase> list1 = EntityPolarBear.this.field_70170_p.func_175647_a(EntityLivingBase.class, p_190102_1_.func_174813_aQ().func_186662_g(2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list1 != null && !list1.isEmpty())
          for (int i1 = 0; i1 < list1.size(); i1++) {
            EntityLivingBase entity1 = list1.get(i1);
            if (!EntityPolarBear.this.func_184191_r((Entity)entity1))
              EntityPolarBear.this.func_70652_k((Entity)entity1); 
          }  
      } else if (p_190102_2_ <= d0 * 2.0D) {
        if (this.attackTick <= 0.0D) {
          EntityPolarBear.this.setStanding(false);
          this.attackTick = 20.0D;
        } 
        if (this.attackTick <= 10.0D) {
          EntityPolarBear.this.setStanding(true);
          EntityPolarBear.this.playWarningSound();
        } 
      } else {
        this.attackTick = 20.0D;
        EntityPolarBear.this.setStanding(false);
      } 
    }
    
    public void func_75251_c() {
      EntityPolarBear.this.setStanding(false);
      super.func_75251_c();
    }
    
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
      return (this.attacker.field_70130_N * this.attacker.field_70130_N + attackTarget.field_70130_N * attackTarget.field_70130_N) + 9.0D;
    }
  }
  
  class AIPanic extends EntityAIPanic {
    public AIPanic() {
      super((EntityCreature)EntityPolarBear.this, 2.0D);
    }
    
    public boolean func_75250_a() {
      return (!EntityPolarBear.this.func_70631_g_() && !EntityPolarBear.this.func_70027_ad()) ? false : super.func_75250_a();
    }
  }
  
  static class GroupData implements IEntityLivingData {
    public boolean madeParent;
  }
}
