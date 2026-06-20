package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.init.MiscHandler;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitch extends EntityTameBase implements IRangedAttackMob, Light {
  private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
  
  private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).setSaved(false);
  
  private static final DataParameter<Boolean> IS_AGGRESSIVE = EntityDataManager.createKey(EntityWitch.class, DataSerializers.BOOLEAN);
  
  private int witchAttackTimer;
  
  public EntityWitch(World worldIn) {
    super(worldIn);
    setSize(0.5F, 1.95F);
    this.isOffensive = true;
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.2D, 32.0F, 8.0F));
    this.tasks.addTask(3, new EntityAIAttackRangedAlly(this, 1.0D, 40, 10.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.experienceValue = 10;
  }
  
  public String getDescName() {
    return "witch";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityWitch(this.world);
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(IS_AGGRESSIVE, Boolean.FALSE);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_WITCH_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_WITCH_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_WITCH_DEATH;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityWitch baby = new EntityWitch(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-32000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity(baby);
      }  
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch();
  }
  
  public void setAggressive(boolean aggressive) {
    getDataManager().set(IS_AGGRESSIVE, aggressive);
  }
  
  public boolean isDrinkingPotion() {
    return getDataManager().get(IS_AGGRESSIVE);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
  }
  
  public void performSpecialAttack() {
    playSound(ESound.witchSpecial, 10.0F, 1.0F);
    setSpecialAttackTimer(1200);
  }
  
  public void onLivingUpdate() {
    if (isHero() && getSpecialAttackTimer() == 1180) {
      List<EntityLiving> list = this.world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(24.0D, 24.0D, 24.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLiving entity : list) {
              if (entity != null && entity.isNonBoss() && !(entity instanceof EntityTameBase) && !false) {
                  EntityPig entityzombie = new EntityPig(this.world);
                  entityzombie.copyLocationAndAnglesFrom(entity);
                  this.world.removeEntity(entity);
                  entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), null);
                  entityzombie.spawnExplosionParticle();
                  if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
                      this.world.spawnEntity(entityzombie);
              }
          }
    } 
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 256.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.witchAttackTimer <= 28 && this.witchAttackTimer > 0 && this.ticksExisted % 4 == 0 && isEntityAlive())
        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_DRINK, getSoundCategory(), getSoundVolume(), getSoundPitch() - 0.2F);
      if (isDrinkingPotion() && getHealth() > 0.0F && this.ticksExisted > 20) {
        if (this.witchAttackTimer-- <= 0) {
          this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
          setAggressive(false);
          ItemStack itemstack = getHeldItemMainhand();
          setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
          if (itemstack.getItem() == Items.POTIONITEM) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_BURP, getSoundCategory(), getSoundVolume(), getSoundPitch());
            List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemstack);
            if (list != null)
              for (PotionEffect potioneffect : list)
                addPotionEffect(new PotionEffect(potioneffect));  
          } 
          getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
        } 
      } else {
        PotionType potiontype = null;
        if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.WATER_BREATHING)) {
          potiontype = PotionTypes.LONG_WATER_BREATHING;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.FIRE_RESISTANCE)) {
          potiontype = PotionTypes.LONG_FIRE_RESISTANCE;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.SPEED)) {
          potiontype = PotionTypes.STRONG_SWIFTNESS;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.REGENERATION)) {
          potiontype = PotionTypes.LONG_REGENERATION;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.NIGHT_VISION)) {
          potiontype = PotionTypes.LONG_NIGHT_VISION;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.JUMP_BOOST)) {
          potiontype = PotionTypes.STRONG_LEAPING;
        } else if (this.rand.nextFloat() < 0.1F && !isPotionActive(MobEffects.STRENGTH) && getAttackTarget() != null) {
          potiontype = PotionTypes.STRONG_STRENGTH;
        } else if (this.rand.nextFloat() < 0.1F && getHealth() < getMaxHealth()) {
          potiontype = PotionTypes.STRONG_HEALING;
        } 
        if (potiontype != null && getHealth() > 0.0F && this.ticksExisted > 20 && !isChild()) {
          setItemStackToSlot(EntityEquipmentSlot.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), potiontype));
          this.witchAttackTimer = getHeldItemMainhand().getMaxItemUseDuration();
          setAggressive(true);
          this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, getSoundCategory(), getSoundVolume(), getSoundPitch());
          IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
          iattributeinstance.removeModifier(MODIFIER);
          iattributeinstance.applyModifier(MODIFIER);
        } 
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !isChild() && getOwner() != null && this.world.getClosestPlayerToEntity(this, 16.0D) != null && this.world.getClosestPlayerToEntity(this, 16.0D) == getOwner() && getDistance(getOwner()) < 16.0D && canEntityBeSeen(getOwner()) && this.rand.nextInt(200) == 0) {
        attackEntityWithRangedAttack(getOwner(), 0.0F);
        getLookHelper().setLookPositionWithEntity(getOwner(), 180.0F, 30.0F);
      } 
      if (this.rand.nextFloat() < 0.01F)
        this.world.setEntityState(this, (byte)15);
    } 
    super.onLivingUpdate();
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 15) {
      for (int i = 0; i < 50; i++)
        this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842D, (getEntityBoundingBox()).maxY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D);
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return (potioneffectIn.getPotion() == MobEffects.POISON) ? false : super.isPotionApplicable(potioneffectIn);
  }
  
  protected float applyPotionDamageCalculations(DamageSource source, float damage) {
    damage = super.applyPotionDamageCalculations(source, damage);
    if (source.getTrueSource() == this)
      damage = 0.0F; 
    if (source.isMagicDamage())
      damage = (float)(damage * 0.15D); 
    return damage;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_WITCH;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
    for (int i = 0; i < (isHero() ? 3 : 1); i++) {
      if (!isDrinkingPotion() && canEntityBeSeen(target)) {
        double d0 = target.posY;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
        PotionType potiontype = PotionTypes.STRONG_HARMING;
        if (false)
          switch (this.rand.nextInt(8)) {
            case 0:
              potiontype = PotionTypes.STRONG_HEALING;
              break;
            case 1:
              potiontype = PotionTypes.LONG_FIRE_RESISTANCE;
              break;
            case 2:
              potiontype = PotionTypes.LONG_NIGHT_VISION;
              break;
            case 3:
              potiontype = PotionTypes.LONG_WATER_BREATHING;
              break;
            case 4:
              potiontype = PotionTypes.STRONG_REGENERATION;
              break;
            case 5:
              potiontype = PotionTypes.STRONG_SWIFTNESS;
              break;
            case 6:
              potiontype = PotionTypes.STRONG_STRENGTH;
              break;
            case 7:
              potiontype = PotionTypes.STRONG_LEAPING;
              break;
          }  
        if ((target instanceof EntityWitch || target instanceof net.minecraft.entity.monster.EntityWitch) && !false) {
          target.attackEntityFrom(DamageSource.IN_WALL, 5.0F);
          potiontype = PotionTypes.AWKWARD;
          target.hurtResistantTime = 0;
          target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 4));
        } else if (target.isEntityUndead() && !false) {
          potiontype = PotionTypes.STRONG_HEALING;
        } else if (target.isNonBoss() && !target.isPotionActive(MobEffects.SLOWNESS) && target.isPotionApplicable(new PotionEffect(MobEffects.SLOWNESS)) && target != getOwner()) {
          potiontype = PotionTypes.LONG_SLOWNESS;
        } else if (target.isNonBoss() && target.getHealth() >= 2.0F && !target.isEntityUndead() && target.isPotionApplicable(new PotionEffect(MobEffects.POISON)) && !(target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider) && !(target instanceof net.minecraft.entity.monster.EntitySpider) && !target.isPotionActive(MobEffects.POISON) && !false) {
          potiontype = PotionTypes.STRONG_POISON;
        } else if (target.isNonBoss() && !target.isPotionActive(MobEffects.WEAKNESS) && target.isPotionApplicable(new PotionEffect(MobEffects.WEAKNESS)) && !false) {
          potiontype = PotionTypes.LONG_WEAKNESS;
        } else if (target.isNonBoss() && Loader.isModLoaded("abyssalcraft") && target.isPotionApplicable(new PotionEffect(AbyssalCraftAPI.antimatter_potion)) && !EntityUtil.isEntityAnti(target) && isAntiMob() && !target.isPotionActive(AbyssalCraftAPI.antimatter_potion) && !false) {
          potiontype = MiscHandler.antiMatter_long;
        } 
        EntityPotionOther entitypotion = new EntityPotionOther(this.world, this, PotionUtils.addPotionToItemStack((getIntelligence() > 40.0F) ? new ItemStack(Items.LINGERING_POTION) : new ItemStack(Items.SPLASH_POTION), potiontype));
        entitypotion.rotationPitch -= -20.0F;
        entitypotion.shoot(d1, d2, d3, 1.0F * getDistance(target) / getDistance(target), 9.0F);
        playLivingSound();
        swingArm(EnumHand.MAIN_HAND);
        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.spawnEntity(entitypotion);
      } 
    } 
  }
  
  public float getEyeHeight() {
    return this.height * 0.84F;
  }
}

