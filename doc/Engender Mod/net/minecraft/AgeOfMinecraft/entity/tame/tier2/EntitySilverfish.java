package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class EntitySilverfish extends EntityTameBase implements Light, Tiny {
  private AISummonSilverfish summonSilverfish;
  
  public EntitySilverfish(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.25F, 0.95F);
    } else {
      func_70105_a(0.4F, 0.3F);
    } 
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(1, this.summonSilverfish = new AISummonSilverfish(this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 24.0F, 6.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 1;
  }
  
  public String getDescName() {
    return "silverfish";
  }
  
  public int getNextLevelRequirement() {
    return 10;
  }
  
  public float getBonusVSArmored() {
    return 0.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.25F;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.3F) : super.func_70647_i();
  }
  
  public int timesToConvert() {
    return 3;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O / 3.0F);
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
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187793_eY;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187850_fa;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187795_eZ;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187852_fb, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.func_180429_a(pos, blockIn); 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_SILVERFISH;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source))
      return false; 
    if ((source instanceof net.minecraft.util.EntityDamageSource || source == DamageSource.field_76376_m) && this.summonSilverfish != null)
      this.summonSilverfish.func_179462_f(); 
    return super.func_70097_a(source, amount);
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150348_b) ? 10.0F : super.func_180484_a(pos);
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(400);
    func_184185_a(ESound.bugSpecial, 10.0F, 1.0F);
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 2; i++) {
        EntitySilverfish mob = new EntitySilverfish(this.field_70170_p);
        mob.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
        mob.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        this.field_70170_p.func_72838_d((Entity)mob);
        mob.setOwnerId(func_184753_b());
      }  
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y != 0.0D)
      this.field_70761_aq = this.field_70177_z = this.field_70759_as; 
    if (func_70638_az() != null && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (getSpecialAttackTimer() >= 380 && isHero()) {
      func_70661_as().func_75499_g();
      this.field_70159_w = 0.0D;
      this.field_70179_y = 0.0D;
      float f2 = this.field_70761_aq * 0.017453292F;
      float f19 = MathHelper.func_76126_a(f2);
      float f3 = MathHelper.func_76134_b(f2);
      for (int i = 0; i < 32; i++) {
        this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O * 2.0D, this.field_70161_v, (f3 * 0.15F), 0.01D, (f19 * 0.15F), new int[0]);
        this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O * 2.0D, this.field_70161_v, (f3 * -0.15F), 0.01D, (f19 * -0.15F), new int[0]);
      } 
    } 
  }
  
  static class AISummonSilverfish extends EntityAIBase {
    private EntitySilverfish silverfish;
    
    private int field_179463_b;
    
    public AISummonSilverfish(EntitySilverfish silverfishIn) {
      this.silverfish = silverfishIn;
    }
    
    public void func_179462_f() {
      if (this.field_179463_b == 0)
        this.field_179463_b = 20; 
    }
    
    public boolean func_75250_a() {
      return (this.field_179463_b > 0);
    }
    
    public void func_75246_d() {
      this.field_179463_b--;
      if (this.field_179463_b <= 0) {
        World world = this.silverfish.field_70170_p;
        Random random = this.silverfish.func_70681_au();
        BlockPos blockpos = new BlockPos((Entity)this.silverfish);
        int i;
        for (i = 0; i <= 5 && i >= -5; i = (i <= 0) ? (1 - i) : (0 - i)) {
          int j;
          for (j = 0; j <= 10 && j >= -10; j = (j <= 0) ? (1 - j) : (0 - j)) {
            int k;
            for (k = 0; k <= 10 && k >= -10; k = (k <= 0) ? (1 - k) : (0 - k)) {
              BlockPos blockpos1 = blockpos.func_177982_a(j, i, k);
              IBlockState iblockstate = world.func_180495_p(blockpos1);
              if (iblockstate.func_177230_c() == Blocks.field_150418_aU) {
                if (EngenderConfig.mobs.grief) {
                  world.func_175655_b(blockpos1, true);
                } else {
                  world.func_180501_a(blockpos1, ((BlockSilverfish.EnumType)iblockstate.func_177229_b((IProperty)BlockSilverfish.field_176378_a)).func_176883_d(), 3);
                } 
                if (random.nextBoolean())
                  return; 
              } 
            } 
          } 
        } 
      } 
    }
  }
}
