package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityChagarothSpawn extends EntityTameBase implements Light {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(EntityChagarothSpawn.class, DataSerializers.field_187191_a);
  
  public EntityChagarothSpawn(World par1World) {
    super(par1World);
    func_70105_a(0.5F, 0.5F);
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityChagarothSpawn(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(16.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    } 
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof net.minecraft.entity.player.EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K)
      setBesideClimbableBlock(this.field_70123_F); 
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.1F;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.dread_spawn_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ACSounds.dread_spawn_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.dread_spawn_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.15F, 1.0F);
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue();
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.field_70180_af.func_187227_b(CLIMBING, Byte.valueOf(b0));
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_70636_d() {
    if (func_184187_bx() != null && func_184187_bx() instanceof EntityDreadSlugOther) {
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = (func_184187_bx()).field_70177_z + 180.0F;
      this.field_70125_A = -(func_184187_bx()).field_70125_A;
    } 
    if (this.field_70173_aa == 1)
      func_184185_a(ESound.amalgamate, 1.0F, 1.0F); 
    super.func_70636_d();
  }
  
  protected Item func_146068_u() {
    return ACItems.dread_fragment;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_SPAWN_OF_CHAGAROTH;
  }
  
  public boolean func_70662_br() {
    return true;
  }
}
