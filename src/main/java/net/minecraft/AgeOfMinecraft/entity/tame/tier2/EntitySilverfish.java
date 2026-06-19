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
      setSize(0.25F, 0.95F);
    } else {
      setSize(0.4F, 0.3F);
    } 
    this.isOffensive = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(1, this.summonSilverfish = new AISummonSilverfish(this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 24.0F, 6.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 1;
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
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.3F) : super.getSoundPitch();
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
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height / 3.0F);
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SILVERFISH_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SILVERFISH_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.playStepSound(pos, blockIn); 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_SILVERFISH;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isEntityInvulnerable(source))
      return false; 
    if ((source instanceof net.minecraft.util.EntityDamageSource || source == DamageSource.MAGIC) && this.summonSilverfish != null)
      this.summonSilverfish.notifyHurt(); 
    return super.attackEntityFrom(source, amount);
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == Blocks.STONE) ? 10.0F : super.getBlockPathWeight(pos);
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(400);
    playSound(ESound.bugSpecial, 10.0F, 1.0F);
    if (!this.world.isRemote)
      for (int i = 0; i < 2; i++) {
        EntitySilverfish mob = new EntitySilverfish(this.world);
        mob.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        mob.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        this.world.spawnEntity((Entity)mob);
        mob.setOwnerId(getOwnerId());
      }  
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D)
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead; 
    if (getAttackTarget() != null && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (getSpecialAttackTimer() >= 380 && isHero()) {
      getNavigator().clearPath();
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
      float f2 = this.renderYawOffset * 0.017453292F;
      float f19 = MathHelper.sin(f2);
      float f3 = MathHelper.cos(f2);
      for (int i = 0; i < 32; i++) {
        this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX, this.posY + this.rand.nextDouble() * this.height * 2.0D, this.posZ, (f3 * 0.15F), 0.01D, (f19 * 0.15F));
        this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX, this.posY + this.rand.nextDouble() * this.height * 2.0D, this.posZ, (f3 * -0.15F), 0.01D, (f19 * -0.15F));
      } 
    } 
  }
  
  static class AISummonSilverfish extends EntityAIBase {
    private EntitySilverfish silverfish;
    
    private int lookForFriends;
    
    public AISummonSilverfish(EntitySilverfish silverfishIn) {
      this.silverfish = silverfishIn;
    }
    
    public void notifyHurt() {
      if (this.lookForFriends == 0)
        this.lookForFriends = 20; 
    }
    
    public boolean shouldExecute() {
      return (this.lookForFriends > 0);
    }
    
    public void updateTask() {
      this.lookForFriends--;
      if (this.lookForFriends <= 0) {
        World world = this.silverfish.world;
        Random random = this.silverfish.getRNG();
        BlockPos blockpos = new BlockPos((Entity)this.silverfish);
        int i;
        for (i = 0; i <= 5 && i >= -5; i = (i <= 0) ? (1 - i) : (0 - i)) {
          int j;
          for (j = 0; j <= 10 && j >= -10; j = (j <= 0) ? (1 - j) : (0 - j)) {
            int k;
            for (k = 0; k <= 10 && k >= -10; k = (k <= 0) ? (1 - k) : (0 - k)) {
              BlockPos blockpos1 = blockpos.add(j, i, k);
              IBlockState iblockstate = world.getBlockState(blockpos1);
              if (iblockstate.getBlock() == Blocks.MONSTER_EGG) {
                if (EngenderConfig.mobs.grief) {
                  world.destroyBlock(blockpos1, true);
                } else {
                  world.setBlockState(blockpos1, ((BlockSilverfish.EnumType)iblockstate.getValue((IProperty)BlockSilverfish.VARIANT)).getModelBlock(), 3);
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
