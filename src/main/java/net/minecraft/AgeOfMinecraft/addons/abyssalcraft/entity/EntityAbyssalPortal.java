package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityAbyssalPortal extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Structure {
  private static final DataParameter<Integer> TOWER1_TARGET = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER2_TARGET = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER3_TARGET = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER4_TARGET = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer>[] TARGETS = new DataParameter[] { TOWER1_TARGET, TOWER2_TARGET, TOWER3_TARGET, TOWER4_TARGET };
  
  private static final DataParameter<Integer> LIGHTNINGTIMER = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> METADATA = EntityDataManager.createKey(EntityAbyssalPortal.class, DataSerializers.VARINT);
  
  private int[] towerUpdate = new int[4];
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
  public MultiPartEntityPart[] partArray;
  
  public MultiPartEntityPart portal = new MultiPartEntityPart(this, "portal", 4.0F, 1.0F);
  
  public MultiPartEntityPart tower1 = new MultiPartEntityPart(this, "tower1", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower2 = new MultiPartEntityPart(this, "tower2", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower3 = new MultiPartEntityPart(this, "tower3", 1.0F, 4.0F);
  
  public MultiPartEntityPart tower4 = new MultiPartEntityPart(this, "tower4", 1.0F, 4.0F);
  
  public MultiPartEntityPart side1 = new MultiPartEntityPart(this, "side1", 1.0F, 1.0F);
  
  public MultiPartEntityPart side2 = new MultiPartEntityPart(this, "side2", 1.0F, 1.0F);
  
  public MultiPartEntityPart side3 = new MultiPartEntityPart(this, "side3", 1.0F, 1.0F);
  
  public MultiPartEntityPart side4 = new MultiPartEntityPart(this, "side4", 1.0F, 1.0F);
  
  public EntityAbyssalPortal(World worldIn) {
    super(worldIn);
    this.partArray = new MultiPartEntityPart[] { this.portal, this.tower1, this.tower2, this.tower3, this.tower4, this.side1, this.side2, this.side3, this.side4 };
    setSize(4.0F, 1.0F);
    this.reachWidth = 6.0F;
    this.isImmuneToFire = true;
    playSound(ESound.portalMake, 100.0F, 1.0F);
    playSound(ESound.portalAmbient, 5.0F, 1.0F);
    this.experienceValue = 18000;
    setLevel(300);
    setLocationAndAngles((int)this.posX, (int)this.posY, (int)this.posZ, 0.0F, -90.0F);
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "portal_aby";
  }
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public int getMetaData() {
    return this.dataManager.get(METADATA);
  }
  
  public void setMetaData(int p_82215_1_) {
    this.dataManager.set(METADATA, p_82215_1_);
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.CONSTRUCT;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(METADATA, 0);
    this.dataManager.register(TOWER1_TARGET, 0);
    this.dataManager.register(TOWER2_TARGET, 0);
    this.dataManager.register(TOWER3_TARGET, 0);
    this.dataManager.register(TOWER4_TARGET, 0);
  }
  
  public int getWatchedTargetId(int p_82203_1_) {
    return this.dataManager.get(TARGETS[p_82203_1_]);
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.dataManager.set(TARGETS[targetOffset], newId);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("MetaData", getMetaData());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setMetaData(tagCompund.getInteger("MetaData"));
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 0.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 0.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 0.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    super.onKillEntity(entityLivingIn);
    EntityLightningBolt shot = new EntityLightningBolt(this.world, entityLivingIn.posX - 0.5D, entityLivingIn.posY, entityLivingIn.posZ - 0.5D, true);
    float f = -((float)(MathHelper.atan2(shot.posZ - this.posZ, shot.posZ - this.posZ) * 57.29577951308232D)) - 90.0F;
    shot.rotationYaw = f;
    this.world.addWeatherEffect(shot);
    entityLivingIn.onStruckByLightning(shot);
    entityLivingIn.motionY += 4.0D;
    if (entityLivingIn instanceof EntityCreeper || entityLivingIn instanceof net.minecraft.entity.monster.EntityZombie || entityLivingIn instanceof net.minecraft.entity.monster.AbstractSkeleton) {
      EntityCreeper creeper = new EntityCreeper(this.world);
      if (!this.world.isRemote)
        this.world.spawnEntity(creeper);
      creeper.copyLocationAndAnglesFrom(entityLivingIn);
      creeper.onStruckByLightning(shot);
      entityLivingIn.onDeath(DamageSource.causeMobDamage(creeper).setDamageBypassesArmor());
      creeper.setDead();
      entityLivingIn.motionX = 0.0D;
      entityLivingIn.motionZ = 0.0D;
      entityLivingIn.knockBack(shot, 2.0F, MathHelper.sin(shot.rotationYaw * 0.017453292F), -MathHelper.cos(shot.rotationYaw * 0.017453292F));
      entityLivingIn.motionY = 0.0D;
      if (entityLivingIn.isAirBorne) {
        entityLivingIn.motionY += this.rand.nextDouble() * 1.5D;
      } else {
        entityLivingIn.motionY += this.rand.nextDouble() * 3.0D;
      } 
    } 
    if (entityLivingIn.height < 2.0F && entityLivingIn.width < 2.0F && entityLivingIn.isNonBoss()) {
      entityLivingIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 2.0F);
      entityLivingIn.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 2.0F);
      entityLivingIn.playSound(SoundEvents.BLOCK_LAVA_POP, 2.0F, 2.0F);
      entityLivingIn.world.setEntityState(entityLivingIn, (byte)20);
      entityLivingIn.setDead();
    } 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    int i = MathHelper.ceil((distance - 6.0F) * damageMultiplier);
    if (i > 0) {
      playSound(ESound.golemSmash, 10.0F, 0.75F);
      playSound(ESound.golemSmash, 10.0F, 0.5F);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D + i, 2.0D, 4.0D + i), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity : list) {
              if (entity != null) {
                  if (!false) {
                      entity.motionY += 1.0D + i * 0.05D;
                      entity.attackEntityFrom(DamageSource.causeExplosionDamage((Explosion) null), 5.0F + i);
                      entity.isAirBorne = true;
                      float f = MathHelper.sqrt(MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.sin(this.rotationYaw * 0.017453292F) + -MathHelper.cos(this.rotationYaw * 0.017453292F) * -MathHelper.cos(this.rotationYaw * 0.017453292F));
                      entity.motionX /= 2.0D;
                      entity.motionZ /= 2.0D;
                      entity.motionX -= (MathHelper.sin(this.rotationYaw * 0.017453292F) / f) * 1.0D;
                      entity.motionZ -= (-MathHelper.cos(this.rotationYaw * 0.017453292F) / f) * 1.0D;
                  }
                  if (EngenderConfig.general.useMessage && !entity.isEntityAlive() && !isWild())
                      getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was blown up by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
              }
          }
    } 
  }
  
  private double getHeadX(int p_82214_1_) {
    switch (p_82214_1_) {
      case 0:
        return this.posX + 3.25D;
      case 1:
        return this.posX + 3.25D;
      case 2:
        return this.posX - 3.25D;
      case 3:
        return this.posX - 3.25D;
    } 
    return this.posX;
  }
  
  private double getHeadY(int p_82208_1_) {
    return this.posY + 3.5D;
  }
  
  private double getHeadZ(int p_82213_1_) {
    switch (p_82213_1_) {
      case 0:
        return this.posZ + 3.25D;
      case 1:
        return this.posZ - 3.25D;
      case 2:
        return this.posZ + 3.25D;
      case 3:
        return this.posZ - 3.25D;
    } 
    return this.posZ;
  }
  
  public void onLivingUpdate() {
    if (!this.world.isRemote && EngenderConfig.mobs.grief) {
      int x = MathHelper.floor(this.posX);
      int y = MathHelper.floor(this.posY);
      int z = MathHelper.floor(this.posZ);
      for (int x1 = -3; x1 <= 3; x1++) {
        for (int z1 = -3; z1 <= 3; z1++) {
          for (int y1 = 0; y1 <= 3; y1++) {
            BlockPos blockpos = new BlockPos(x + x1, y + y1, z + z1);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (!block.isAir(iblockstate, this.world, blockpos) && block.getBlockHardness(iblockstate, this.world, blockpos) >= 0.0F)
              this.world.destroyBlock(blockpos, true); 
          } 
        } 
      } 
    } 
    float f = (getJukeboxToDanceTo() != null) ? (MathHelper.cos(this.ticksExisted * 0.25F) * 0.25F) : (MathHelper.cos(this.ticksExisted * 0.05F) * 0.25F);
    this.portal.onUpdate();
    this.portal.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
    this.tower1.onUpdate();
    this.tower1.setLocationAndAngles(this.posX + 2.75D + f, this.posY + 0.325D, this.posZ + 2.75D + f, 0.0F, 0.0F);
    this.tower2.onUpdate();
    this.tower2.setLocationAndAngles(this.posX + 2.75D + f, this.posY + 0.325D, this.posZ - 2.75D - f, 0.0F, 0.0F);
    this.tower3.onUpdate();
    this.tower3.setLocationAndAngles(this.posX - 2.75D - f, this.posY + 0.325D, this.posZ - 2.75D - f, 0.0F, 0.0F);
    this.tower4.onUpdate();
    this.tower4.setLocationAndAngles(this.posX - 2.75D - f, this.posY + 0.325D, this.posZ + 2.75D + f, 0.0F, 0.0F);
    this.side1.onUpdate();
    this.side1.setLocationAndAngles(this.posX + 2.5D, this.posY + 0.5D, this.posZ, 0.0F, 0.0F);
    this.side1.setEntityBoundingBox(new AxisAlignedBB(this.side1.posX - 0.5D, this.side1.posY, this.side1.posZ - 2.0D, this.side1.posX + 0.5D, this.side1.posY + 1.0D, this.side1.posZ + 2.0D));
    this.side2.onUpdate();
    this.side2.setLocationAndAngles(this.posX - 2.5D, this.posY + 0.5D, this.posZ, 0.0F, 0.0F);
    this.side2.setEntityBoundingBox(new AxisAlignedBB(this.side2.posX - 0.5D, this.side2.posY, this.side2.posZ - 2.0D, this.side2.posX + 0.5D, this.side2.posY + 1.0D, this.side2.posZ + 2.0D));
    this.side3.onUpdate();
    this.side3.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ + 2.5D, 0.0F, 0.0F);
    this.side3.setEntityBoundingBox(new AxisAlignedBB(this.side3.posX - 2.0D, this.side3.posY, this.side3.posZ - 0.5D, this.side3.posX + 2.0D, this.side3.posY + 1.0D, this.side3.posZ + 0.5D));
    this.side4.onUpdate();
    this.side4.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ - 2.5D, 0.0F, 0.0F);
    this.side4.setEntityBoundingBox(new AxisAlignedBB(this.side4.posX - 2.0D, this.side4.posY, this.side4.posZ - 0.5D, this.side4.posX + 2.0D, this.side4.posY + 1.0D, this.side4.posZ + 0.5D));
    if (!this.world.isRemote)
      for (int i = 0; i < 4; i++) {
        if (getRevengeTarget() != null)
          updateWatchedTargetId(i, getRevengeTarget().getEntityId()); 
        if (this.ticksExisted > 80 && isEntityAlive() && this.ticksExisted >= this.towerUpdate[i]) {
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.world.getEntityByID(i1);
            if (entity != null && entity.isEntityAlive() && getDistance(entity) <= getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() && canEntityBeSeen(entity)) {
              double d1 = getHeadX(i);
              double d2 = getHeadY(i);
              double d3 = getHeadZ(i);
              fireLightning(entity, d1, d2, d3);
              if (this.moralRaisedTimer > 200) {
                this.towerUpdate[i] = this.ticksExisted + 1;
              } else if (getMetaData() > 1) {
                this.towerUpdate[i] = this.ticksExisted + 30;
              } else {
                this.towerUpdate[i] = this.ticksExisted + 60;
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.and(EntitySelectors.IS_ALIVE));
            for (int k1 = 0; k1 < 10 && !list1.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase = list1.get(this.rand.nextInt(list1.size()));
              if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen(entitylivingbase) && !false) {
                if (entitylivingbase instanceof EntityPlayer) {
                  if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
                    updateWatchedTargetId(i, entitylivingbase.getEntityId()); 
                } else {
                  updateWatchedTargetId(i, entitylivingbase.getEntityId());
                } 
              } else {
                list1.remove(entitylivingbase);
              } 
            } 
          } 
        } 
      }  
    this.experienceValue = 6000 * (1 + getMetaData());
    if (getMetaData() > 0) {
      addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2147483647, (getMetaData() > 2) ? 1 : 0));
      addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2147483647, (getMetaData() > 2) ? 1 : 0));
    } 
    List<Entity> list = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(1.0D, 4.0D, 1.0D).offset(0.0D, 2.0D, 0.0D), EntitySelectors.IS_ALIVE);
    if (!list.isEmpty())
      for (int l = 0; l < list.size(); l++) {
        Entity[] aentity = getParts();
        if (aentity != null)
          for (Entity part : aentity) {
            List<Entity> partlist = this.world.getEntitiesInAABBexcluding(this, part.getEntityBoundingBox(), EntitySelectors.IS_ALIVE);
            if (!partlist.isEmpty())
                for (Entity entity : partlist) {
                    if (entity instanceof EntityLivingBase && !entity.noClip && !(entity instanceof IEntityMultiPart)) {
                        if (entity.collided)
                            entity.attackEntityFrom(DamageSource.IN_WALL, 1.0F);
                        part.applyEntityCollision(entity);
                        entity.applyEntityCollision(part);
                        entity.motionY += 0.1D;
                    }
                }
          }  
      }  
    this.motionX = 0.0D;
    this.motionZ = 0.0D;
    if (this.motionY > 0.0D)
      this.motionY = 0.0D; 
    super.onLivingUpdate();
    this.prevRenderYawOffset = this.renderYawOffset = this.prevRotationYaw = this.rotationYaw = this.prevRotationYawHead = this.rotationYawHead = 0.0F;
    this.prevRotationPitch = this.rotationPitch = 90.0F;
    this.isAirBorne = false;
    this.onGround = true;
    setSprinting(false);
    if (this.world.isRemote)
      for (int i = 0; i < 3 && this.ticksExisted > 60; i++) {
        if (this.towerUpdate[i] > 20)
          this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, getHeadX(i), getHeadY(i), getHeadY(i), ((float)getHeadX(i) + this.rand.nextFloat()) - 0.5D, ((float)getHeadY(i) - this.rand.nextFloat() - 1.0F), ((float)getHeadY(i) + this.rand.nextFloat()) - 0.5D);
        if (getMetaData() > 0)
          this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.0D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.01D, 0.0D);
        if (getMetaData() > 1) {
          this.world.spawnParticle(EnumParticleTypes.CRIT, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
          this.world.spawnParticle(EnumParticleTypes.CRIT, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
          this.world.spawnParticle(EnumParticleTypes.CRIT, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
          this.world.spawnParticle(EnumParticleTypes.CRIT, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        } 
        if (getMetaData() > 2)
          this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 1.0D, 0.0D, 0.5D);
        if (getMetaData() > 3) {
          this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, -0.05D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D);
        } 
        this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.0D + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D + this.rand.nextDouble() * 0.1D - 0.05D, 0.0D + this.rand.nextDouble() * 0.2D, 0.0D + this.rand.nextDouble() * 0.1D - 0.05D);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.25D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.world, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.world, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.world, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
        AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.world, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D);
      }  
    if ((this.ticksExisted + getEntityId()) % ((getJukeboxToDanceTo() != null) ? 20 : 200) == 0)
      playSound(ESound.portalWhoosh, 5.0F, 1.0F); 
    if ((this.ticksExisted + getEntityId()) % 670 == 0)
      playSound(ESound.portalAmbient, 5.0F, 1.0F); 
    if ((this.ticksExisted + getEntityId()) % 400 == 0) {
      SoundEvent[] chants = { ACSounds.cthulhu_chant, ACSounds.yog_sothoth_chant_1, ACSounds.yog_sothoth_chant_2, ACSounds.hastur_chant_1, ACSounds.hastur_chant_2, ACSounds.sleeping_chant, ACSounds.cthugha_chant };
      playSound(chants[this.world.rand.nextInt(chants.length)], 2.0F, getSoundPitch());
      playSound(chants[this.world.rand.nextInt(chants.length)], 2.0F, getSoundPitch() - 0.025F);
      playSound(chants[this.world.rand.nextInt(chants.length)], 2.0F, getSoundPitch() - 0.05F);
    } 
    if (this.ticksExisted + getEntityId() > 60)
      if ((this.ticksExisted + getEntityId()) % ((getJukeboxToDanceTo() != null) ? 20 : ((getMetaData() > 2) ? 50 : 100)) == 0 && this.rand.nextInt((getJukeboxToDanceTo() != null) ? 5 : 10) == 0) {
        EntityAbygolem entityAbygolem;
        EntityAbyssalZombie entityAbyssalZombie;
        EntityDepthsGhoul entityDepthsGhoul;
        EntityDragonMinion entityDragonMinion;
        EntityGreaterDreadSpawn entityGreaterDreadSpawn;
        EntityLesserShoggoth entityLesserShoggoth;
        EntityDreadguard bat;
        EntityGatekeeperMinion chicken;
        EntityLesserDreadbeast cow;
        EntityCoraliumSquid entityCoraliumSquid;
        EntityChagarothSpawn entityChagarothSpawn;
        EntityOmotholGhoul entityOmotholGhoul;
        EntityRemnant entityRemnant;
        EntityShadowBeast mooshroom;
        EntitySkeletonGoliath ocelot;
        EntityChagarothFist entityChagarothFist;
        EntityDreadgolem rabbit;
        EntityDreadling entityDreadling;
        EntityDreadSpawn entityDreadSpawn;
        EntityShadowCreature sheep;
        EntityShadowMonster entityShadowMonster;
        EntityDragonBoss entityDragonBoss;
        EntitySacthoth pig;
        playSound(ESound.portalWhoosh, 10.0F, getSoundPitch() + 1.5F);
        int i = 0;
        if (this.rand.nextInt(4) == 0)
          i++; 
        if (this.rand.nextInt(8) == 0)
          i++; 
        switch (i) {
          case 0:
            switch (this.rand.nextInt(10)) {
              case 0:
                entityAbygolem = new EntityAbygolem(this.world);
                entityAbygolem.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityAbygolem.setOwnerId(getOwnerId()); 
                entityAbygolem.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityAbygolem)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityAbygolem);
                break;
              case 1:
                entityAbyssalZombie = new EntityAbyssalZombie(this.world);
                entityAbyssalZombie.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityAbyssalZombie.setOwnerId(getOwnerId()); 
                entityAbyssalZombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityAbyssalZombie)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityAbyssalZombie);
                break;
              case 2:
                entityDepthsGhoul = new EntityDepthsGhoul(this.world);
                entityDepthsGhoul.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDepthsGhoul.setOwnerId(getOwnerId()); 
                entityDepthsGhoul.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityDepthsGhoul)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityDepthsGhoul);
                break;
              case 3:
                entityCoraliumSquid = new EntityCoraliumSquid(this.world);
                entityCoraliumSquid.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityCoraliumSquid.setOwnerId(getOwnerId()); 
                entityCoraliumSquid.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityCoraliumSquid)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityCoraliumSquid);
                break;
              case 4:
                entityChagarothSpawn = new EntityChagarothSpawn(this.world);
                entityChagarothSpawn.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityChagarothSpawn.setOwnerId(getOwnerId()); 
                entityChagarothSpawn.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityChagarothSpawn)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityChagarothSpawn);
                break;
              case 5:
                entityChagarothFist = new EntityChagarothFist(this.world);
                entityChagarothFist.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityChagarothFist.setOwnerId(getOwnerId()); 
                entityChagarothFist.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityChagarothFist)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityChagarothFist);
                break;
              case 6:
                rabbit = new EntityDreadgolem(this.world);
                rabbit.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  rabbit.setOwnerId(getOwnerId()); 
                rabbit.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(rabbit)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(rabbit);
                break;
              case 7:
                entityDreadling = new EntityDreadling(this.world);
                entityDreadling.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDreadling.setOwnerId(getOwnerId()); 
                entityDreadling.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityDreadling)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityDreadling);
                break;
              case 8:
                entityDreadSpawn = new EntityDreadSpawn(this.world);
                entityDreadSpawn.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDreadSpawn.setOwnerId(getOwnerId()); 
                entityDreadSpawn.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityDreadSpawn)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityDreadSpawn);
                break;
              case 9:
                sheep = new EntityShadowCreature(this.world);
                sheep.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  sheep.setOwnerId(getOwnerId()); 
                sheep.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(sheep)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(sheep);
                break;
            } 
            break;
          case 1:
            switch (this.rand.nextInt(6)) {
              case 0:
                entityDragonMinion = new EntityDragonMinion(this.world);
                entityDragonMinion.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDragonMinion.setOwnerId(getOwnerId()); 
                entityDragonMinion.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityDragonMinion)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityDragonMinion);
                break;
              case 1:
                entityGreaterDreadSpawn = new EntityGreaterDreadSpawn(this.world);
                entityGreaterDreadSpawn.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityGreaterDreadSpawn.setOwnerId(getOwnerId()); 
                entityGreaterDreadSpawn.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityGreaterDreadSpawn)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityGreaterDreadSpawn);
                break;
              case 2:
                entityLesserShoggoth = new EntityLesserShoggoth(this.world);
                entityLesserShoggoth.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityLesserShoggoth.setOwnerId(getOwnerId()); 
                entityLesserShoggoth.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityLesserShoggoth)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityLesserShoggoth);
                break;
              case 3:
                entityOmotholGhoul = new EntityOmotholGhoul(this.world);
                entityOmotholGhoul.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityOmotholGhoul.setOwnerId(getOwnerId()); 
                entityOmotholGhoul.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityOmotholGhoul)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityOmotholGhoul);
                break;
              case 4:
                entityRemnant = new EntityRemnant(this.world);
                entityRemnant.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityRemnant.setOwnerId(getOwnerId()); 
                entityRemnant.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityRemnant)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityRemnant);
                break;
              case 5:
                entityShadowMonster = new EntityShadowMonster(this.world);
                entityShadowMonster.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityShadowMonster.setOwnerId(getOwnerId()); 
                entityShadowMonster.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityShadowMonster)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityShadowMonster);
                break;
            } 
            break;
          case 2:
            switch (this.rand.nextInt(8)) {
              case 0:
                bat = new EntityDreadguard(this.world);
                bat.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  bat.setOwnerId(getOwnerId()); 
                bat.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(bat)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(bat);
                break;
              case 1:
                chicken = new EntityGatekeeperMinion(this.world);
                chicken.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  chicken.setOwnerId(getOwnerId()); 
                chicken.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(chicken)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(chicken);
                break;
              case 2:
                cow = new EntityLesserDreadbeast(this.world);
                cow.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cow.setOwnerId(getOwnerId()); 
                cow.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(cow)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(cow);
                break;
              case 3:
                mooshroom = new EntityShadowBeast(this.world);
                mooshroom.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  mooshroom.setOwnerId(getOwnerId()); 
                mooshroom.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(mooshroom)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(mooshroom);
                break;
              case 4:
                ocelot = new EntitySkeletonGoliath(this.world);
                ocelot.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ocelot.setOwnerId(getOwnerId()); 
                ocelot.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(ocelot)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(ocelot);
                break;
              case 5:
                entityDragonBoss = new EntityDragonBoss(this.world);
                entityDragonBoss.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityDragonBoss.setOwnerId(getOwnerId()); 
                entityDragonBoss.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityDragonBoss)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(entityDragonBoss);
                break;
              case 6:
                pig = new EntitySacthoth(this.world);
                pig.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pig.setOwnerId(getOwnerId()); 
                pig.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(pig)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity(pig);
                break;
            } 
            break;
        } 
      }  
  }
  
  public void fireLightning(Entity entity, double x, double y, double z) {
    if (this.rand.nextInt(2) == 0) {
      if (entity != null && entity.isEntityAlive()) {
        double d6 = entity.posX - x;
        double d7 = entity.posY + this.rand.nextDouble() * 2.0D - y;
        double d8 = entity.posZ - z;
        this.world.playEvent(null, 1016, new BlockPos(this), 0);
        EntityMiniBlackHole entitydragonfireball = new EntityMiniBlackHole(this.world, this, d6, d7, d8);
        entitydragonfireball.posX = x;
        entitydragonfireball.posY = y;
        entitydragonfireball.posZ = z;
        this.world.spawnEntity(entitydragonfireball);
      } 
    } else {
      super.fireLightning(entity, x, y, z);
    } 
  }
  
  protected void onDeathUpdate() {
    this.deathTime++;
    if (this.deathTime == 1) {
      playSound(ESound.buildingDeath, 10.0F, 1.0F);
      if (!this.world.isRemote)
        entityDropItem(new ItemStack(EItem.abyssalPortalStaff, 1, getMetaData()), 1.0F); 
      for (int k = 0; k < 2500; k++) {
        double d2 = this.rand.nextGaussian() * 0.05D;
        double d0 = this.rand.nextGaussian() * 0.05D;
        double d1 = this.rand.nextGaussian() * 0.05D;
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, -0.25D, d1);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, -0.25D, d1);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, 0.5D, d1);
      } 
    } 
    if (!this.world.isRemote && (isPlayer() || (this.recentlyHit > 0 && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))) {
      int i = getExperiencePoints(this.attackingPlayer) / 60;
      i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
      } 
    } 
    if (this.deathTime == 60)
      setDead(); 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_PORTAL;
  }
  
  protected SoundEvent getDeathSound() {
    return null;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return null;
  }
  
  public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
    if (isEntityAlive() && this.ticksExisted > 80)
      super.setAttackTarget(entitylivingbaseIn); 
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
  
  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (this.ticksExisted > 40 && stack.isEmpty() && !isWild() && player == getOwner()) {
      if (player.isSneaking()) {
        List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(256.0D), Predicates.and(EntitySelectors.IS_ALIVE));
        if (list != null && !list.isEmpty() && !isBeingRidden())
            for (EntityTameBase entity : list) {
                if (entity != null)
                    if (false) {
                        playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
                        entity.changeDimension(ACLib.omothol_id);
                    }
            }
        playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
        player.changeDimension(ACLib.omothol_id);
        return true;
      } 
      player.world.playSound(player, new BlockPos(player), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 100.0F, 0.5F);
      setHealth(0.0F);
      return true;
    } 
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart portalPart, DamageSource source, float damage) {
    if (portalPart == this.side1 || portalPart == this.side2 || portalPart == this.side3 || portalPart == this.side4)
      damage = 0.0F; 
    return super.attackEntityFrom(source, damage);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return attackEntityFromPart(this.portal, source, amount);
  }
  
  public Entity[] getParts() {
    return this.partArray;
  }
  
  public AxisAlignedBB getCollisionBoundingBox() {
    return getEntityBoundingBox();
  }
  
  public void applyEntityCollision(Entity entity) {
    if (entity instanceof EntityLivingBase && !(entity instanceof IEntityMultiPart) && entity.posY + entity.getEyeHeight() <= this.posY + 1.0D)
      entity.attackEntityFrom(DamageSource.IN_WALL, 1.0F); 
  }
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}
}

