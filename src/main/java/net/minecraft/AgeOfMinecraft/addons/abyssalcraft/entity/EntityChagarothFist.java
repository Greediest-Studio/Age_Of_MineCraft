package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityChagarothFist extends EntityTameBase implements Armored {
  public EntityChagarothFist(World par1World) {
    super(par1World);
    setSize(0.4F, 1.5F);
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityChagarothFist(this.world);
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.5D);
    } 
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
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    if (ACConfig.hardcoreMode && par1Entity instanceof net.minecraft.entity.player.EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return super.attackEntityAsMob(par1Entity);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_FIST_OF_CHAGAROTH;
  }
  
  public void onLivingUpdate() {
    if (getRidingEntity() != null && getRidingEntity() instanceof EntityDreadSlugOther) {
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (getRidingEntity()).rotationYaw + 180.0F;
      this.rotationPitch = -(getRidingEntity()).rotationPitch;
    } 
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    super.onLivingUpdate();
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
  }
}
