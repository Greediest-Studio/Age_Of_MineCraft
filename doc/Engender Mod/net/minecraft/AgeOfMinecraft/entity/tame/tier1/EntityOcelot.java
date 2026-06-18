package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameBase implements Light, Animal {
  public EntityOcelot(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.33D, 15.0F, 4.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAICustomLeapAttack((EntityLiving)this, 0.3F, 0.6F, 0.8F, 0.5F, 3.0D, 20.0D, 6));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.33D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70728_aV = 1;
    func_70105_a(0.6F, 0.7F);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityOcelot(this.field_70170_p);
  }
  
  public String getDescName() {
    return "cat";
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    List<EntityCreeper> list = this.field_70170_p.func_175647_a(EntityCreeper.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list != null && !list.isEmpty() && this.field_70173_aa % 40 == 0)
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityCreeper entity = list.get(i1);
        if (entity != null)
          entity.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)entity, EntityOcelot.class, 6.0F, 1.75D, 1.25D)); 
      }  
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public void func_70619_bc() {
    super.func_70619_bc();
    if (func_70605_aq().func_75640_a()) {
      double d0 = func_70605_aq().func_75638_b();
      if (d0 <= 0.6D) {
        func_70095_a(true);
        func_70031_b(false);
      } else if (d0 >= 1.33D) {
        func_70095_a(false);
        func_70031_b(true);
      } else {
        func_70095_a(false);
        func_70031_b(false);
      } 
    } else {
      func_70095_a(false);
      func_70031_b(false);
    } 
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187642_Q;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187639_P;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_OCELOT;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  public boolean func_70058_J() {
    if (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ())) {
      BlockPos blockpos = new BlockPos(this.field_70165_t, (func_174813_aQ()).field_72338_b, this.field_70161_v);
      if (blockpos.func_177956_o() < this.field_70170_p.func_181545_F())
        return false; 
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos.func_177977_b());
      Block block = iblockstate.func_177230_c();
      if (block == Blocks.field_150349_c || block.isLeaves(iblockstate, (IBlockAccess)this.field_70170_p, blockpos.func_177977_b()))
        return true; 
    } 
    return false;
  }
}
