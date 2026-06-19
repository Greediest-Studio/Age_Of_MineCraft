package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICreeperSwell;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCreeper extends EntityTameBase implements Light {
  private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 3;
  
  public EntityCreeper(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.tasks.addTask(0, new EntityAIAvoidEntitySPC(this, EntityOcelot.class, (Predicate<EntityOcelot>) p_apply_1_ -> (p_apply_1_ != null && p_apply_1_.isEntityAlive() && EntityCreeper.this.getIntelligence() < 10.0F),  6.0F, 1.0D, 1.2D));
    this.tasks.addTask(0, new EntityAIAvoidEntitySPC(this, EntityOcelot.class, (Predicate<EntityOcelot>) p_apply_1_ -> (p_apply_1_ != null && p_apply_1_.isEntityAlive() && EntityCreeper.this.getIntelligence() < 10.0F),  6.0F, 1.0D, 1.2D));
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(0, new EntityAICreeperSwell(this));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.0D, 32.0F, 6.0F));
    this.tasks.addTask(3, new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.45F, 1.75F);
    } else {
      setSize(0.5F, 1.625F);
    } 
    this.experienceValue = 5;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityCreeper(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
  }
  
  public String getDescName() {
    return getPowered() ? "charged_creeper" : "creeper";
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + (getPowered() ? 0.1F : 0.2F)) : super.getSoundPitch();
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (getPowered() ? (this.height * 0.94F) : (this.height * 0.83F)) : super.getEyeHeight();
  }
  
  public int timesToConvert() {
    return 13;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityCreeper baby = new EntityCreeper(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-36000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (getPowered())
          this.world.addWeatherEffect(new EntityLightningBolt(this.world, baby.posX - 0.5D, baby.posY + 1.0D, baby.posZ - 0.5D, false));
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity(baby);
      }  
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return new EntityMutantCreeper(this.world);
    return null;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return getPowered() ? I18n.translateToLocal("entity.ChargedCreeperHelpful.cmm.name") : I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return getPowered() ? I18n.translateToLocal("entity.ChargedCreeperHelpful.name") : I18n.translateToLocal("entity." + s + ".name");
  }
  
  public int getMaxFallHeight() {
    return (getAttackTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
  }
  
  public void fall(float distance, float damageMultiplier) {
    super.fall(distance, damageMultiplier);
    this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);
    if (this.timeSinceIgnited > this.fuseTime - 5)
      this.timeSinceIgnited = this.fuseTime - 5; 
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(STATE, -1);
    this.dataManager.register(POWERED, Boolean.FALSE);
    this.dataManager.register(IGNITED, Boolean.FALSE);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    if (this.dataManager.get(POWERED))
      tagCompound.setBoolean("powered", true); 
    tagCompound.setShort("Fuse", (short)this.fuseTime);
    tagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    tagCompound.setBoolean("ignited", hasIgnited());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    this.dataManager.set(POWERED, tagCompund.getBoolean("powered"));
    if (tagCompund.hasKey("Fuse", 99))
      this.fuseTime = tagCompund.getShort("Fuse"); 
    if (tagCompund.hasKey("ExplosionRadius", 99))
      this.explosionRadius = tagCompund.getByte("ExplosionRadius"); 
    if (tagCompund.getBoolean("ignited"))
      ignite(); 
  }
  
  public void onUpdate() {
    ItemStack charge = getPowered() ? new ItemStack(Items.NETHER_STAR) : ItemStack.EMPTY;
    charge.setStackDisplayName("Is Charged");
    this.basicInventory.setInventorySlotContents(7, charge);
    if (isEntityAlive()) {
      this.lastActiveTime = this.timeSinceIgnited;
      if (hasIgnited())
        setCreeperState(1); 
      int i = getCreeperState();
      if (i > 0 && this.timeSinceIgnited == 0)
        playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F); 
      this.timeSinceIgnited += i;
      if (this.timeSinceIgnited < 0)
        this.timeSinceIgnited = 0; 
      if (this.timeSinceIgnited >= this.fuseTime) {
        this.timeSinceIgnited = this.fuseTime;
        explode();
      } 
    } 
    if (this.deathTime == 1)
      if (EngenderConfig.mobs.useMobTalkerModels && getPowered()) {
        playSound(getDeathSound(), getSoundVolume(), getSoundPitch() + 0.2F);
        playSound(getDeathSound(), getSoundVolume(), getSoundPitch() - 0.2F);
      }  
    super.onUpdate();
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_CREEPER_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_CREEPER_DEATH;
  }
  
  public void onDeath(DamageSource cause) {
    super.onDeath(cause);
    if (this.world.getGameRules().getBoolean("doMobLoot"))
      if (cause.getTrueSource() instanceof net.minecraft.entity.monster.AbstractSkeleton) {
        int i = Item.getIdFromItem(Items.RECORD_13);
        int j = Item.getIdFromItem(Items.RECORD_WAIT);
        int k = i + this.rand.nextInt(j - i + 1);
        dropItem(Item.getItemById(k), 1);
      } else if (cause.getTrueSource() instanceof EntityCreeper && cause.getTrueSource() != this && ((EntityCreeper)cause.getTrueSource()).getPowered()) {
        entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
      }  
  }
  
  public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    return blockStateIn.getBlock().getExplosionResistance(worldIn, pos, this, explosionIn) * 0.5F;
  }
  
  public void inflictEngenderMobDamage(EntityLivingBase entity, String killmessage, DamageSource attacktype, float damage) {
    if (getPowered()) {
      this.world.addWeatherEffect(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
      entity.onStruckByLightning(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
    } 
    super.inflictEngenderMobDamage(entity, killmessage, attacktype, damage);
  }
  
  public boolean attackEntityAsMob(Entity entity) {
    if (super.attackEntityAsMob(entity)) {
      if (getPowered()) {
        this.world.addWeatherEffect(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
        entity.onStruckByLightning(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
      } 
      return true;
    } 
    return false;
  }
  
  public boolean getPowered() {
    return this.dataManager.get(POWERED);
  }
  
  public void setPowered(boolean powered) {
    if (powered)
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D); 
    this.dataManager.set(POWERED, powered);
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float p_70831_1_) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_CREEPER;
  }
  
  public int getCreeperState() {
    return this.dataManager.get(STATE);
  }
  
  public void setCreeperState(int state) {
    this.dataManager.set(STATE, state);
  }
  
  public void onStruckByLightning(EntityLightningBolt lightningBolt) {
    setPowered(true);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (!stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL) {
      this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        if (hasOwner(player)) {
          if (player.isSneaking())
            ignite(); 
          if (!getPowered()) {
            if (this.world.canBlockSeeSky(getPosition())) {
              this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX - 0.5D, this.posY + 1.625D, this.posZ - 0.5D, false));
            } else {
              spawnExplosionParticle();
            } 
          } else {
            heal(1.0F);
          } 
        } else {
          ignite();
        } 
        stack.damageItem(1, player);
      } 
      return true;
    } 
    return false;
  }
  
  public void performSpecialAttack() {
    ignite();
    playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 10.0F, 1.0F);
  }
  
  private void explode() {
    if (!this.world.isRemote) {
      boolean flag = EngenderConfig.mobs.grief;
      float f = getPowered() ? 2.0F : 1.0F;
      if (getSpecialAttackTimer() <= 0 && isHero()) {
        createEngenderModExplosionFireless(this, this.posX, this.posY + 1.0D, this.posZ, 20.0F * f, flag);
        setSpecialAttackTimer(800);
        this.motionY = 0.0D;
      } else if (getHealth() > getMaxHealth() * getIntelligence() * 0.01F || isWild()) {
        createEngenderModExplosionFireless(this, this.posX, this.posY + 1.0D, this.posZ, this.explosionRadius * f, flag);
        setDead();
        Collection<PotionEffect> collection = getActivePotionEffects();
        if (!collection.isEmpty()) {
          EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
          entityareaeffectcloud.setRadius(2.5F);
          entityareaeffectcloud.setRadiusOnUse(-0.5F);
          entityareaeffectcloud.setWaitTime(10);
          entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
          entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
          for (PotionEffect potioneffect : collection)
            entityareaeffectcloud.addEffect(new PotionEffect(potioneffect)); 
          this.world.spawnEntity(entityareaeffectcloud);
        } 
        if (!this.world.isRemote && EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof net.minecraft.entity.player.EntityPlayerMP)
          getOwner().sendMessage(new TextComponentTranslation("death.attack.self_destruct", new Object[] { getDisplayName() }));
      } 
      setCreeperState(-1);
      this.timeSinceIgnited = 0;
      this.dataManager.set(IGNITED, Boolean.FALSE);
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (getPowered())
      extinguish(); 
    if (getAttackTarget() != null && !hasIgnited() && getDistanceSq(getAttackTarget()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
  }
  
  public boolean hasIgnited() {
    return this.dataManager.get(IGNITED);
  }
  
  public void ignite() {
    this.dataManager.set(IGNITED, Boolean.TRUE);
  }
}
