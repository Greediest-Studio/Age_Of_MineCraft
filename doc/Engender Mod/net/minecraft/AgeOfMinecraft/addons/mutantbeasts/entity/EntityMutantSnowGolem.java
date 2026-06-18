package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantSnowGolem extends EntityTameBase implements IRangedAttackMob, IShearable, IJumpingMount, Armored {
  private static final DataParameter<Boolean> PUMPKIN_EQUIPPED = EntityDataManager.func_187226_a(EntityMutantSnowGolem.class, DataSerializers.field_187198_h);
  
  private boolean isThrowing;
  
  private int throwingTick;
  
  protected float jumpPower;
  
  public EntityMutantSnowGolem(World worldIn) {
    super(worldIn);
    func_70105_a(0.9F, 2.2F);
    this.field_70714_bg.func_75776_a(0, new SwimJumpGoal());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRanged(this, 1.2D, 30, 24.0F));
    this.field_70714_bg.func_75776_a(4, new ThrowIceGoal());
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.75D));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "mutant_snowman";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantSnowGolem(this.field_70170_p);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.26D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(10.0D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
      func_184185_a(SoundEvents.field_187721_dT, 1.0F, 2.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    return false;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(PUMPKIN_EQUIPPED, Boolean.valueOf(true));
  }
  
  public boolean isPumpkinEquipped() {
    return ((Boolean)this.field_70180_af.func_187225_a(PUMPKIN_EQUIPPED)).booleanValue();
  }
  
  private void setPumpkinEquipped(boolean pumpkinEquipped) {
    this.field_70180_af.func_187227_b(PUMPKIN_EQUIPPED, Boolean.valueOf(pumpkinEquipped));
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
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)(new MBGroundPathNavigator((EntityLiving)this, worldIn)).setAvoidRain(true);
  }
  
  public float func_70047_e() {
    return 2.0F;
  }
  
  protected void func_184594_b(BlockPos pos) {}
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {}
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br / 3.0F;
      forward = entitylivingbase.field_191988_bg / 3.0F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 2.0D * this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-1.6F * f * this.jumpPower);
          this.field_70179_y += (1.6F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    float biomeTemp = this.field_70170_p.func_180494_b(func_180425_c()).func_180626_a(func_180425_c());
    if (this.isThrowing)
      this.throwingTick++; 
    if (func_70026_G() && this.field_70173_aa % 20 == 0)
      func_70097_a(DamageSource.field_76369_e, 1.0F); 
    if (biomeTemp > 1.2F && !func_70644_a(MobEffects.field_76426_n)) {
      if (this.field_70146_Z.nextFloat() > Math.min(80.0F, func_110143_aJ()) * 0.01F)
        this.field_70170_p.func_72960_a((Entity)this, (byte)4); 
      if (this.field_70173_aa % 60 == 0)
        func_70097_a(DamageSource.field_76370_b, 1.0F); 
    } 
    if (func_110143_aJ() > 0.0F && biomeTemp < 0.5F && this.field_70173_aa % 200 == 0)
      func_70691_i(1.0F); 
    if (!this.field_70170_p.field_72995_K && ForgeEventFactory.getMobGriefingEvent(this.field_70170_p, (Entity)this)) {
      int x = MathHelper.func_76128_c(this.field_70165_t);
      int y = MathHelper.func_76128_c((func_174813_aQ()).field_72338_b);
      int z = MathHelper.func_76128_c(this.field_70161_v);
      for (int i = -2; i <= 2; i++) {
        for (int j = -2; j <= 2; j++) {
          if (Math.abs(i) != 2 || Math.abs(j) != 2) {
            BlockPos blockpos = new BlockPos(x + i, y, z + j);
            BlockPos blockpos1 = new BlockPos(x + i, y - 1, z + j);
            BlockPos blockpos2 = new BlockPos(x + i, y + 1, z + j);
            boolean placeSnow = (biomeTemp < 0.95F && this.field_70170_p.func_175623_d(blockpos) && Blocks.field_150431_aC.func_176196_c(this.field_70170_p, blockpos));
            boolean placeIce = (this.field_70170_p.func_180495_p(blockpos1).func_185904_a() == Material.field_151586_h);
            if (this.field_70170_p.func_180495_p(blockpos).func_185904_a() == Material.field_151586_h)
              this.field_70170_p.func_175656_a(blockpos, Blocks.field_150432_aD.func_176223_P()); 
            if (this.field_70170_p.func_180495_p(blockpos2).func_185904_a() == Material.field_151586_h)
              this.field_70170_p.func_175656_a(blockpos2, Blocks.field_150432_aD.func_176223_P()); 
            if (!placeSnow || (((Math.abs(i) != 2 && Math.abs(j) != 2) || this.field_70146_Z.nextInt(20) == 0) && ((Math.abs(i) != 1 && Math.abs(j) != 1) || this.field_70146_Z.nextInt(10) == 0)))
              if (!placeIce || (((Math.abs(i) != 2 && Math.abs(j) != 2) || this.field_70146_Z.nextInt(14) == 0) && ((Math.abs(i) != 1 && Math.abs(j) != 1) || this.field_70146_Z.nextInt(6) == 0))) {
                if (placeSnow)
                  this.field_70170_p.func_175656_a(blockpos, Blocks.field_150431_aC.func_176223_P()); 
                if (placeIce)
                  this.field_70170_p.func_175656_a(blockpos1, Blocks.field_150432_aD.func_176223_P()); 
              }  
          } 
          continue;
        } 
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isThrowing() {
    return this.isThrowing;
  }
  
  @SideOnly(Side.CLIENT)
  public int getThrowingTick() {
    return this.throwingTick;
  }
  
  private void setThrowing(boolean isThrowing) {
    this.isThrowing = isThrowing;
    this.throwingTick = 0;
    this.field_70170_p.func_72960_a((Entity)this, (byte)(isThrowing ? 1 : 0));
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 0 || id == 1) {
      this.isThrowing = (id == 1);
      this.throwingTick = 0;
    } else if (id == 4) {
      this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_DROP, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 1.5F) - this.field_70130_N, this.field_70163_u - 0.15D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 1.5F) - this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
    } else if (id == 5 || id == 6 || id == 7) {
      int i = 0;
      while (true) {
        if (i < ((id == 5) ? 10 : ((id == 6) ? 30 : 1))) {
          double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
          if (id == 7) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.HEART, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 0.5D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d0, d1, d2, new int[0]);
          } else {
            this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 0.5D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d0, d1, d2, new int[] { Block.func_176210_f(Blocks.field_150431_aC.func_176223_P()) });
          } 
          i++;
          continue;
        } 
        break;
      } 
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (source.func_76364_f() instanceof net.minecraft.entity.projectile.EntitySnowball) {
      if (func_110143_aJ() < func_110138_aP()) {
        func_70691_i(1.0F);
        this.field_70170_p.func_72960_a((Entity)this, (byte)7);
        this.field_70170_p.func_72960_a((Entity)this, (byte)5);
      } 
      return false;
    } 
    boolean flag = super.func_70097_a(source, amount);
    if (flag && amount > 0.0F)
      this.field_70170_p.func_72960_a((Entity)this, (byte)6); 
    return flag;
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    if (!this.isThrowing)
      setThrowing(true); 
  }
  
  public void func_184724_a(boolean swingingArms) {}
  
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return isPumpkinEquipped();
  }
  
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    setPumpkinEquipped(false);
    return new ArrayList<>();
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74757_a("Pumpkin", isPumpkinEquipped());
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    if (compound.func_74764_b("Pumpkin"))
      setPumpkinEquipped(compound.func_74767_n("Pumpkin")); 
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_SNOW_GOLEM_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return MBSoundEvents.ENTITY_MUTANT_SNOW_GOLEM_DEATH;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187815_fJ, 0.15F, 1.0F);
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_snow_golem");
  }
  
  class SwimJumpGoal extends EntityAIBase {
    private int jumpTick;
    
    private boolean waterReplaced;
    
    private BlockPos prevPos;
    
    public SwimJumpGoal() {
      func_75248_a(4);
      ((MBGroundPathNavigator)EntityMutantSnowGolem.this.field_70699_by).func_179693_d(true);
    }
    
    public boolean func_75250_a() {
      return EntityMutantSnowGolem.this.func_70090_H();
    }
    
    public void func_75249_e() {
      this.prevPos = new BlockPos(MathHelper.func_76128_c(EntityMutantSnowGolem.this.field_70165_t), MathHelper.func_76128_c((EntityMutantSnowGolem.this.func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(EntityMutantSnowGolem.this.field_70161_v));
      EntityMutantSnowGolem.this.field_70159_w = ((EntityMutantSnowGolem.this.field_70146_Z.nextFloat() - EntityMutantSnowGolem.this.field_70146_Z.nextFloat()) * 0.9F);
      EntityMutantSnowGolem.this.field_70181_x = 1.5D;
      EntityMutantSnowGolem.this.field_70179_y = ((EntityMutantSnowGolem.this.field_70146_Z.nextFloat() - EntityMutantSnowGolem.this.field_70146_Z.nextFloat()) * 0.9F);
      EntityMutantSnowGolem.this.func_70097_a(DamageSource.field_76369_e, 16.0F);
      EntityMutantSnowGolem.this.field_70170_p.func_72960_a((Entity)EntityMutantSnowGolem.this, (byte)6);
    }
    
    public boolean func_75253_b() {
      return (this.jumpTick > 0);
    }
    
    public void func_75246_d() {
      this.jumpTick--;
      if (!this.waterReplaced && !EntityMutantSnowGolem.this.func_70090_H() && this.jumpTick < 17 && ForgeEventFactory.getMobGriefingEvent(EntityMutantSnowGolem.this.field_70170_p, (Entity)EntityMutantSnowGolem.this)) {
        int i = this.prevPos.func_177956_o();
        i = getWaterSurfaceHeight(EntityMutantSnowGolem.this.field_70170_p, this.prevPos);
        if (i > EntityMutantSnowGolem.this.field_70163_u)
          return; 
        for (int x = -2; x <= 2; x++) {
          for (int y = -1; y <= 1; y++) {
            for (int z = -2; z <= 2; z++) {
              if (y == 0 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
                int posX = this.prevPos.func_177958_n() + x;
                int posY = this.prevPos.func_177956_o() + y;
                int posZ = this.prevPos.func_177952_p() + z;
                Block block = EntityMutantSnowGolem.this.field_70170_p.func_180495_p(new BlockPos(posX, posY, posZ)).func_177230_c();
                if ((block == Blocks.field_150350_a || block == Blocks.field_150355_j) && ((y != 0) ? ((
                  
                  Math.abs(x) == 1 || Math.abs(z) == 1) && EntityMutantSnowGolem.this.field_70146_Z.nextInt(4) == 0) : ((
                  
                  Math.abs(x) == 2 || Math.abs(z) == 2) && EntityMutantSnowGolem.this.field_70146_Z.nextInt(3) == 0)))
                  EntityMutantSnowGolem.this.field_70170_p.func_175656_a(new BlockPos(posX, posY, posZ), Blocks.field_150432_aD.func_176223_P()); 
              } 
            } 
          } 
        } 
        Block topBlock = EntityMutantSnowGolem.this.field_70170_p.func_180495_p(this.prevPos.func_177981_b(2)).func_177230_c();
        if (topBlock == Blocks.field_150350_a)
          EntityMutantSnowGolem.this.field_70170_p.func_175656_a(this.prevPos.func_177981_b(2), Blocks.field_150432_aD.func_176223_P()); 
        this.waterReplaced = true;
      } 
    }
    
    public void func_75251_c() {
      this.jumpTick = 20;
      this.waterReplaced = false;
    }
    
    int getWaterSurfaceHeight(World world, BlockPos coord) {
      int y = coord.func_177956_o();
      while (true) {
        Block block = world.func_180495_p(coord.func_177984_a()).func_177230_c();
        if (block == Blocks.field_150355_j) {
          y++;
          continue;
        } 
        break;
      } 
      return y;
    }
  }
  
  class ThrowIceGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantSnowGolem.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantSnowGolem.this.isThrowing);
    }
    
    public void func_75249_e() {
      EntityMutantSnowGolem.this.field_70699_by.func_75499_g();
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSnowGolem.this.isThrowing && EntityMutantSnowGolem.this.throwingTick < 20);
    }
    
    public void func_75246_d() {
      EntityMutantSnowGolem.this.field_70761_aq = EntityMutantSnowGolem.this.field_70177_z;
      if (EntityMutantSnowGolem.this.throwingTick == 7) {
        EntityThrowingBlock block = new EntityThrowingBlock(EntityMutantSnowGolem.this.field_70170_p, EntityMutantSnowGolem.this);
        block.field_70163_u++;
        double x = this.attackTarget.field_70165_t + this.attackTarget.field_70159_w - block.field_70165_t;
        double y = this.attackTarget.field_70163_u - block.field_70163_u;
        double z = this.attackTarget.field_70161_v + this.attackTarget.field_70179_y - block.field_70161_v;
        double xz = Math.sqrt(x * x + z * z);
        block.func_70186_c(x, y + xz * 0.5D, z, 1.2F, 1.0F);
        block.setBlockState((!EntityMutantSnowGolem.this.func_184614_ca().func_190926_b() && EntityMutantSnowGolem.this.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBlock) ? Block.func_149634_a(EntityMutantSnowGolem.this.func_184614_ca().func_77973_b()).func_176223_P() : Blocks.field_150432_aD.func_176223_P());
        EntityMutantSnowGolem.this.field_70170_p.func_72838_d((Entity)block);
        EntityMutantSnowGolem.this.func_184185_a(SoundEvents.field_187721_dT, EntityMutantSnowGolem.this.func_70599_aP(), EntityMutantSnowGolem.this.func_70647_i());
      } 
    }
    
    public void func_75251_c() {
      EntityMutantSnowGolem.this.setThrowing(false);
    }
  }
}
