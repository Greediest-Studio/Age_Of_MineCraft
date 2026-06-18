package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityMooshroom;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityParrot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySnowman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
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

public class EntityDraconicPortal extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Structure {
  private static final DataParameter<Integer> TOWER1_TARGET = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER2_TARGET = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER3_TARGET = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TOWER4_TARGET = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer>[] TARGETS = new DataParameter[] { TOWER1_TARGET, TOWER2_TARGET, TOWER3_TARGET, TOWER4_TARGET };
  
  private static final DataParameter<Integer> LIGHTNINGTIMER = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> METADATA = EntityDataManager.createKey(EntityDraconicPortal.class, DataSerializers.VARINT);
  
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
  
  public EntityDraconicPortal(World worldIn) {
    super(worldIn);
    this.partArray = new MultiPartEntityPart[] { this.portal, this.tower1, this.tower2, this.tower3, this.tower4, this.side1, this.side2, this.side3, this.side4 };
    setSize(4.0F, 1.0F);
    this.reachWidth = 6.0F;
    this.isImmuneToFire = true;
    playSound(ESound.portalMake, 100.0F, 0.9F);
    playSound(ESound.portalAmbient, 5.0F, 0.75F);
    this.experienceValue = 18000;
    setLevel(300);
    setLocationAndAngles((int)this.posX, (int)this.posY, (int)this.posZ, 0.0F, -90.0F);
  }
  
  public int getMetaData() {
    return ((Integer)this.dataManager.get(METADATA)).intValue();
  }
  
  public void setMetaData(int p_82215_1_) {
    this.dataManager.set(METADATA, Integer.valueOf(p_82215_1_));
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "portal_drac";
  }
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.CONSTRUCT;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(METADATA, Integer.valueOf(0));
    this.dataManager.register(TOWER1_TARGET, Integer.valueOf(0));
    this.dataManager.register(TOWER2_TARGET, Integer.valueOf(0));
    this.dataManager.register(TOWER3_TARGET, Integer.valueOf(0));
    this.dataManager.register(TOWER4_TARGET, Integer.valueOf(0));
  }
  
  public int getWatchedTargetId(int p_82203_1_) {
    return ((Integer)this.dataManager.get(TARGETS[p_82203_1_])).intValue();
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.dataManager.set(TARGETS[targetOffset], Integer.valueOf(newId));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(96.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(40.0D);
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
    this.world.addWeatherEffect((Entity)shot);
    entityLivingIn.onStruckByLightning(shot);
    entityLivingIn.motionY += 4.0D;
    if (entityLivingIn instanceof net.minecraft.entity.monster.EntityCreeper || entityLivingIn instanceof net.minecraft.entity.monster.EntityZombie || entityLivingIn instanceof net.minecraft.entity.monster.AbstractSkeleton) {
      EntityCreeper creeper = new EntityCreeper(this.world);
      if (!this.world.isRemote)
        this.world.spawnEntity((Entity)creeper); 
      creeper.copyLocationAndAnglesFrom((Entity)entityLivingIn);
      creeper.onStruckByLightning(shot);
      entityLivingIn.onDeath(DamageSource.causeMobDamage((EntityLivingBase)creeper).setDamageBypassesArmor());
      creeper.setDead();
      entityLivingIn.motionX = 0.0D;
      entityLivingIn.motionZ = 0.0D;
      entityLivingIn.knockBack((Entity)shot, 2.0F, MathHelper.sin(shot.rotationYaw * 0.017453292F), -MathHelper.cos(shot.rotationYaw * 0.017453292F));
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
      entityLivingIn.world.setEntityState((Entity)entityLivingIn, (byte)20);
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
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D + i, 2.0D, 4.0D + i), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null) {
            if (!false) {
              entity.motionY += 1.0D + i * 0.05D;
              entity.attackEntityFrom(DamageSource.causeExplosionDamage((Explosion)null), 5.0F + i);
              entity.isAirBorne = true;
              float f = MathHelper.sqrt(MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.sin(this.rotationYaw * 0.017453292F) + -MathHelper.cos(this.rotationYaw * 0.017453292F) * -MathHelper.cos(this.rotationYaw * 0.017453292F));
              entity.motionX /= 2.0D;
              entity.motionZ /= 2.0D;
              entity.motionX -= (MathHelper.sin(this.rotationYaw * 0.017453292F) / f) * 1.0D;
              entity.motionZ -= (-MathHelper.cos(this.rotationYaw * 0.017453292F) / f) * 1.0D;
            } 
            if (EngenderConfig.general.useMessage && !entity.isEntityAlive() && !isWild())
              getOwner().sendMessage((ITextComponent)new TextComponentTranslation(entity.getName() + " was blown up by " + getName() + " (" + getOwner().getName() + ")", new Object[0])); 
          } 
        }  
    } 
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    super.attackEntityAsMob(entityIn);
    entityIn.motionY++;
    playSound(ESound.lightningShot, 10.0F, 1.0F);
    if (entityIn instanceof EntityLivingBase) {
      if (!entityIn.isEntityAlive() && !entityIn.isDead)
        onKillEntity((EntityLivingBase)entityIn); 
      ((EntityLivingBase)entityIn).knockBack((Entity)this, 1.0F, MathHelper.sin(entityIn.rotationYaw * 0.017453292F), -MathHelper.cos(entityIn.rotationYaw * 0.017453292F));
      if (!(entityIn instanceof EntityTameBase))
        entityIn.motionY += this.rand.nextDouble(); 
    } 
    entityIn.setFire(100);
    if (entityIn instanceof EntityLivingBase && entityIn instanceof IEntityMultiPart) {
      ((EntityLivingBase)entityIn).motionX = 0.0D;
      ((EntityLivingBase)entityIn).motionZ = 0.0D;
    } 
    return true;
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
            if (!block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && block.getBlockHardness(iblockstate, this.world, blockpos) >= 0.0F)
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
            if (entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() * getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() && canEntityBeSeen(entity)) {
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
            List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
            for (int k1 = 0; k1 < 10 && !list1.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase = list1.get(this.rand.nextInt(list1.size()));
              if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase) && !false) {
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
    List<Entity> list = this.world.getEntitiesInAABBexcluding((Entity)this, getEntityBoundingBox().grow(1.0D, 4.0D, 1.0D).offset(0.0D, 2.0D, 0.0D), EntitySelectors.IS_ALIVE);
    if (!list.isEmpty())
      for (int l = 0; l < list.size(); l++) {
        Entity[] aentity = getParts();
        if (aentity != null)
          for (Entity part : aentity) {
            List<Entity> partlist = this.world.getEntitiesInAABBexcluding((Entity)this, part.getEntityBoundingBox(), EntitySelectors.IS_ALIVE);
            if (!partlist.isEmpty())
              for (int l1 = 0; l1 < partlist.size(); l1++) {
                Entity entity = partlist.get(l1);
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
          this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, getHeadX(i), getHeadY(i), getHeadY(i), ((float)getHeadX(i) + this.rand.nextFloat()) - 0.5D, ((float)getHeadY(i) - this.rand.nextFloat() - 1.0F), ((float)getHeadY(i) + this.rand.nextFloat()) - 0.5D, new int[0]); 
        if (getMetaData() > 0)
          this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.0D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 1.0D, 0.0D, 0.0D, new int[0]); 
        if (getMetaData() > 1) {
          this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
          this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        } 
        if (getMetaData() > 2)
          this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 1.0D, 0.0D, 0.5D, new int[0]); 
        if (getMetaData() > 3) {
          this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D, new int[0]);
          this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D, new int[0]);
          this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D, new int[0]);
        } 
        this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 1.0D + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D + this.rand.nextDouble() * 0.1D - 0.05D, 0.0D + this.rand.nextDouble() * 0.2D, 0.0D + this.rand.nextDouble() * 0.1D - 0.05D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.25D, 0.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + 0.8D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.05D, 0.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.REDSTONE, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.REDSTONE, true, this.posX + 3.25D + f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.REDSTONE, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ - 3.25D - f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.REDSTONE, true, this.posX - 3.25D - f + this.rand.nextDouble() - 0.5D, this.posY + 4.0D + this.rand.nextDouble() - 0.5D, this.posZ + 3.25D + f + this.rand.nextDouble() - 0.5D, f * 0.1D, 0.02D, f * 0.1D, new int[0]);
      }  
    if ((this.ticksExisted + getEntityId()) % ((getJukeboxToDanceTo() != null) ? 20 : 200) == 0)
      playSound(ESound.portalWhoosh, 5.0F, 1.0F); 
    if ((this.ticksExisted + getEntityId()) % 894 == 0)
      playSound(ESound.portalAmbient, 5.0F, 0.75F); 
    if (this.ticksExisted + getEntityId() > 60)
      if ((this.ticksExisted + getEntityId()) % ((getJukeboxToDanceTo() != null) ? 10 : ((getMetaData() > 2) ? 25 : 50)) == 0 && this.rand.nextInt((getJukeboxToDanceTo() != null) ? 5 : 10) == 0) {
        EntityBat bat;
        EntityEndermite endermite;
        EntityCreeper creeper;
        EntityBlaze blaze;
        EntityElderGuardian elderguardian;
        EntityChicken chicken;
        EntitySilverfish silverfish;
        EntityMagmaCube magmacube;
        EntityCaveSpider cavespider;
        EntityGiant giant;
        EntityCow cow;
        EntitySnowman snowgolem;
        EntitySkeleton skeleton;
        EntityEnderman enderman;
        EntityIronGolem irongolem;
        EntityMooshroom mooshroom;
        EntitySquid squid;
        EntitySlime slime;
        EntityGhast ghast;
        EntityWither wither;
        EntityOcelot ocelot;
        EntityVillager villager;
        EntitySpider spider;
        EntityGuardian guardian;
        EntityEvoker evoker;
        EntityPig pig;
        EntityWolf wolf;
        EntityZombie zombie;
        EntityPigZombie pigzombie;
        EntityEnderDragon enderdragon;
        EntityRabbit rabbit;
        EntityLlama llama;
        EntityVex vex;
        EntityRabbit killerrabbit;
        EntityIllusioner illusioner;
        EntitySheep sheep;
        EntitySkeleton witherskeleton;
        EntityParrot parrot;
        EntityShulker shulker;
        EntityWitch witch;
        EntityZombie entityZombie1;
        EntitySkeleton stray;
        EntityVindicator vindicator;
        float pitch = getSoundPitch();
        playSound(ESound.portalWhoosh, 10.0F, pitch + 1.5F);
        playSound(ESound.portalWhoosh, 10.0F, pitch + 1.6F);
        playSound(ESound.portalWhoosh, 10.0F, pitch + 1.7F);
        playSound(ESound.portalWhoosh, 10.0F, pitch + 1.8F);
        playSound(ESound.portalWhoosh, 10.0F, pitch + 1.9F);
        int i = 0;
        if (this.rand.nextInt(2) == 0)
          i++; 
        if (this.rand.nextInt(2) == 0)
          i++; 
        if (this.rand.nextInt(4) == 0)
          i++; 
        if (this.rand.nextInt(6) == 0)
          i++; 
        switch (i) {
          case 0:
            switch (this.rand.nextInt(9)) {
              case 0:
                bat = new EntityBat(this.world);
                bat.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  bat.setOwnerId(getOwnerId()); 
                bat.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)bat)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)bat); 
                break;
              case 1:
                chicken = new EntityChicken(this.world);
                chicken.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  chicken.setOwnerId(getOwnerId()); 
                chicken.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)chicken)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)chicken); 
                break;
              case 2:
                cow = new EntityCow(this.world);
                cow.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cow.setOwnerId(getOwnerId()); 
                cow.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)cow)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)cow); 
                break;
              case 3:
                mooshroom = new EntityMooshroom(this.world);
                mooshroom.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  mooshroom.setOwnerId(getOwnerId()); 
                mooshroom.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)mooshroom)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)mooshroom); 
                break;
              case 4:
                ocelot = new EntityOcelot(this.world);
                ocelot.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ocelot.setOwnerId(getOwnerId()); 
                ocelot.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)ocelot)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)ocelot); 
                break;
              case 5:
                pig = new EntityPig(this.world);
                pig.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pig.setOwnerId(getOwnerId()); 
                pig.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)pig)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)pig); 
                break;
              case 6:
                rabbit = new EntityRabbit(this.world);
                rabbit.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  rabbit.setOwnerId(getOwnerId()); 
                rabbit.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)rabbit)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)rabbit); 
                break;
              case 7:
                sheep = new EntitySheep(this.world);
                sheep.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  sheep.setOwnerId(getOwnerId()); 
                sheep.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)sheep)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)sheep); 
                break;
              case 8:
                parrot = new EntityParrot(this.world);
                parrot.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  parrot.setOwnerId(getOwnerId()); 
                parrot.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)parrot)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)parrot); 
                break;
            } 
            break;
          case 1:
            switch (this.rand.nextInt(7)) {
              case 0:
                endermite = new EntityEndermite(this.world);
                endermite.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  endermite.setOwnerId(getOwnerId()); 
                endermite.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)endermite)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)endermite); 
                break;
              case 1:
                silverfish = new EntitySilverfish(this.world);
                silverfish.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  silverfish.setOwnerId(getOwnerId()); 
                silverfish.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)silverfish)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)silverfish); 
                break;
              case 2:
                snowgolem = new EntitySnowman(this.world);
                snowgolem.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  snowgolem.setOwnerId(getOwnerId()); 
                snowgolem.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)snowgolem)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)snowgolem); 
                break;
              case 3:
                squid = new EntitySquid(this.world);
                squid.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  squid.setOwnerId(getOwnerId()); 
                squid.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)squid)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)squid); 
                break;
              case 4:
                villager = new EntityVillager(this.world);
                villager.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  villager.setOwnerId(getOwnerId()); 
                villager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)villager)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)villager); 
                break;
              case 5:
                wolf = new EntityWolf(this.world);
                wolf.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  wolf.setOwnerId(getOwnerId()); 
                wolf.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)wolf)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)wolf); 
                break;
              case 6:
                llama = new EntityLlama(this.world);
                llama.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  llama.setOwnerId(getOwnerId()); 
                llama.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)llama)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)llama); 
                break;
            } 
            break;
          case 2:
            switch (this.rand.nextInt(7)) {
              case 0:
                creeper = new EntityCreeper(this.world);
                creeper.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  creeper.setOwnerId(getOwnerId()); 
                creeper.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)creeper)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)creeper); 
                break;
              case 1:
                magmacube = new EntityMagmaCube(this.world);
                magmacube.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  magmacube.setOwnerId(getOwnerId()); 
                magmacube.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)magmacube)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)magmacube); 
                break;
              case 2:
                skeleton = new EntitySkeleton(this.world);
                skeleton.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  skeleton.setOwnerId(getOwnerId()); 
                skeleton.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)skeleton)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)skeleton); 
                break;
              case 3:
                slime = new EntitySlime(this.world);
                slime.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  slime.setOwnerId(getOwnerId()); 
                slime.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)slime)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)slime); 
                break;
              case 4:
                spider = new EntitySpider(this.world);
                spider.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  spider.setOwnerId(getOwnerId()); 
                spider.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)spider)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)spider); 
                break;
              case 5:
                zombie = new EntityZombie(this.world);
                zombie.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  zombie.setOwnerId(getOwnerId()); 
                zombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)zombie)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)zombie); 
                break;
              case 6:
                vex = new EntityVex(this.world);
                vex.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  vex.setOwnerId(getOwnerId()); 
                vex.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)vex)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)vex); 
                break;
            } 
            break;
          case 3:
            switch (this.rand.nextInt(12)) {
              case 0:
                blaze = new EntityBlaze(this.world);
                blaze.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  blaze.setOwnerId(getOwnerId()); 
                blaze.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)blaze)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)blaze); 
                break;
              case 1:
                cavespider = new EntityCaveSpider(this.world);
                cavespider.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  cavespider.setOwnerId(getOwnerId()); 
                cavespider.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)cavespider)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)cavespider); 
                break;
              case 2:
                enderman = new EntityEnderman(this.world);
                enderman.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  enderman.setOwnerId(getOwnerId()); 
                enderman.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)enderman)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)enderman); 
                break;
              case 3:
                ghast = new EntityGhast(this.world);
                ghast.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  ghast.setOwnerId(getOwnerId()); 
                ghast.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)ghast)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)ghast); 
                break;
              case 4:
                guardian = new EntityGuardian(this.world);
                guardian.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  guardian.setOwnerId(getOwnerId()); 
                guardian.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)guardian)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)guardian); 
                break;
              case 5:
                pigzombie = new EntityPigZombie(this.world);
                pigzombie.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  pigzombie.setOwnerId(getOwnerId()); 
                pigzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)pigzombie)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)pigzombie); 
                break;
              case 6:
                killerrabbit = new EntityRabbit(this.world);
                killerrabbit.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  killerrabbit.setOwnerId(getOwnerId()); 
                killerrabbit.setRabbitType(99);
                killerrabbit.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)killerrabbit)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)killerrabbit); 
                break;
              case 7:
                witherskeleton = new EntitySkeleton(this.world);
                witherskeleton.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  witherskeleton.setOwnerId(getOwnerId()); 
                witherskeleton.setSkeletonType(1);
                witherskeleton.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                if (getRNG().nextInt(2) > 0)
                  witherskeleton.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_SWORD)); 
                witherskeleton.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)witherskeleton)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)witherskeleton); 
                break;
              case 8:
                shulker = new EntityShulker(this.world);
                shulker.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  shulker.setOwnerId(getOwnerId()); 
                shulker.applyEntityCollision((Entity)this);
                shulker.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)shulker)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)shulker); 
                break;
              case 9:
                witch = new EntityWitch(this.world);
                witch.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  witch.setOwnerId(getOwnerId()); 
                witch.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)witch)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)witch); 
                break;
              case 10:
                entityZombie1 = new EntityZombie(this.world);
                entityZombie1.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  entityZombie1.setOwnerId(getOwnerId()); 
                entityZombie1.setZombieType(1);
                entityZombie1.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityZombie1)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)entityZombie1); 
                break;
              case 11:
                stray = new EntitySkeleton(this.world);
                stray.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  stray.setOwnerId(getOwnerId()); 
                stray.setSkeletonType(2);
                stray.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)stray)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)stray); 
                break;
              case 12:
                vindicator = new EntityVindicator(this.world);
                vindicator.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  vindicator.setOwnerId(getOwnerId()); 
                vindicator.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)vindicator)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)vindicator); 
                break;
            } 
            break;
          case 4:
            switch (this.rand.nextInt(7)) {
              case 0:
                elderguardian = new EntityElderGuardian(this.world);
                elderguardian.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  elderguardian.setOwnerId(getOwnerId()); 
                elderguardian.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)elderguardian)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)elderguardian); 
                break;
              case 1:
                giant = new EntityGiant(this.world);
                giant.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  giant.setOwnerId(getOwnerId()); 
                giant.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)giant)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)giant); 
                break;
              case 2:
                irongolem = new EntityIronGolem(this.world);
                irongolem.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  irongolem.setOwnerId(getOwnerId()); 
                irongolem.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)irongolem)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)irongolem); 
                break;
              case 3:
                wither = new EntityWither(this.world);
                wither.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  wither.setOwnerId(getOwnerId()); 
                wither.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)wither)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)wither); 
                break;
              case 4:
                evoker = new EntityEvoker(this.world);
                evoker.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  evoker.setOwnerId(getOwnerId()); 
                evoker.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)evoker)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)evoker); 
                break;
              case 5:
                enderdragon = new EntityEnderDragon(this.world);
                enderdragon.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  enderdragon.setOwnerId(getOwnerId()); 
                enderdragon.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)enderdragon)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)enderdragon); 
                break;
              case 6:
                illusioner = new EntityIllusioner(this.world);
                illusioner.setLocationAndAngles(this.posX + (this.rand.nextFloat() * 4.0F - 2.0F), this.posY + 1.5D, this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F), 0.0F, 0.0F);
                if (!isWild())
                  illusioner.setOwnerId(getOwnerId()); 
                illusioner.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)illusioner)), null);
                if (!this.world.isRemote)
                  this.world.spawnEntity((Entity)illusioner); 
                break;
            } 
            break;
        } 
      }  
  }
  
  public void fireLightning(Entity entity, double x, double y, double z) {
    double d6 = entity.posX - x;
    double d7 = entity.posY + 1.0D - y;
    double d8 = entity.posZ - z;
    double d9 = d6 * d6 + d7 * d7 + d8 * d8;
    if (this.rand.nextBoolean()) {
      if (entity != null && entity.isEntityAlive()) {
        EntityGuardianProjectile entityGuardianProjectile1;
        int i;
        this.world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        switch (this.rand.nextInt(7)) {
          case 0:
          case 1:
            entityGuardianProjectile1 = new EntityGuardianProjectile(this.world, 1, (EntityLivingBase)entity, 5.0F + this.rand.nextFloat() * 8.0F, (Entity)this);
            entityGuardianProjectile1.copyLocationAndAnglesFrom((Entity)this);
            entityGuardianProjectile1.posX = x;
            entityGuardianProjectile1.posY = y;
            entityGuardianProjectile1.posZ = z;
            entityGuardianProjectile1.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
            entityGuardianProjectile1.shooter = (Entity)this;
            entityGuardianProjectile1.target = (EntityLivingBase)entity;
            entityGuardianProjectile1.motionX = d6 / d9 * 5.0D;
            entityGuardianProjectile1.motionY = d7 / d9 * 5.0D;
            entityGuardianProjectile1.motionZ = d8 / d9 * 5.0D;
            this.world.spawnEntity(entityGuardianProjectile1);
            return;
          case 2:
            entityGuardianProjectile1 = new EntityGuardianProjectile(this.world, 3, (EntityLivingBase)entity, 5.0F + this.rand.nextFloat() * 2.0F, (Entity)this);
            entityGuardianProjectile1.copyLocationAndAnglesFrom((Entity)this);
            entityGuardianProjectile1.posX = x;
            entityGuardianProjectile1.posY = y;
            entityGuardianProjectile1.posZ = z;
            entityGuardianProjectile1.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
            entityGuardianProjectile1.shooter = (Entity)this;
            entityGuardianProjectile1.target = (EntityLivingBase)entity;
            entityGuardianProjectile1.motionX = d6 / d9 * 5.0D;
            entityGuardianProjectile1.motionY = d7 / d9 * 5.0D;
            entityGuardianProjectile1.motionZ = d8 / d9 * 5.0D;
            this.world.spawnEntity(entityGuardianProjectile1);
            return;
          case 3:
            entityGuardianProjectile1 = new EntityGuardianProjectile(this.world, 4, (EntityLivingBase)entity, 5.0F + this.rand.nextFloat() * 10.0F, (Entity)this);
            entityGuardianProjectile1.copyLocationAndAnglesFrom((Entity)this);
            entityGuardianProjectile1.posX = x;
            entityGuardianProjectile1.posY = y;
            entityGuardianProjectile1.posZ = z;
            entityGuardianProjectile1.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
            entityGuardianProjectile1.shooter = (Entity)this;
            entityGuardianProjectile1.target = (EntityLivingBase)entity;
            entityGuardianProjectile1.motionX = d6 / d9 * 5.0D;
            entityGuardianProjectile1.motionY = d7 / d9 * 5.0D;
            entityGuardianProjectile1.motionZ = d8 / d9 * 5.0D;
            this.world.spawnEntity(entityGuardianProjectile1);
            return;
          case 4:
            entityGuardianProjectile1 = new EntityGuardianProjectile(this.world, 5, (EntityLivingBase)entity, 5.0F + this.rand.nextFloat() * 10.0F, (Entity)this);
            entityGuardianProjectile1.copyLocationAndAnglesFrom((Entity)this);
            entityGuardianProjectile1.posX = x;
            entityGuardianProjectile1.posY = y;
            entityGuardianProjectile1.posZ = z;
            entityGuardianProjectile1.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
            entityGuardianProjectile1.shooter = (Entity)this;
            entityGuardianProjectile1.target = (EntityLivingBase)entity;
            entityGuardianProjectile1.motionX = d6 / d9 * 5.0D;
            entityGuardianProjectile1.motionY = d7 / d9 * 5.0D;
            entityGuardianProjectile1.motionZ = d8 / d9 * 5.0D;
            this.world.spawnEntity(entityGuardianProjectile1);
            return;
          case 5:
            for (i = 10; i > 0; i--) {
              EntityGuardianProjectile entityGuardianProjectile = new EntityGuardianProjectile(this.world, 6, (EntityLivingBase)entity, (5.0F + this.rand.nextFloat() * 10.0F) / 2.0F, (Entity)this);
              entityGuardianProjectile.copyLocationAndAnglesFrom((Entity)this);
              entityGuardianProjectile.motionY = 0.0D;
              int randDir = this.rand.nextInt();
              double speed = 1.0D + this.rand.nextDouble() * 5.0D;
              entityGuardianProjectile.motionX = Math.sin(randDir) * speed;
              entityGuardianProjectile.motionZ = Math.cos(randDir) * speed;
              entityGuardianProjectile.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
              entityGuardianProjectile.shooter = (Entity)this;
              entityGuardianProjectile.target = (EntityLivingBase)entity;
              this.world.spawnEntity(entityGuardianProjectile);
            } 
            return;
        } 
        EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 6, (EntityLivingBase)entity, (5.0F + this.rand.nextFloat() * 10.0F) / 2.0F, (Entity)this);
        projectile.copyLocationAndAnglesFrom((Entity)this);
        projectile.posX = x;
        projectile.posY = y;
        projectile.posZ = z;
        projectile.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        projectile.shooter = (Entity)this;
        projectile.target = (EntityLivingBase)entity;
        projectile.motionX = d6 / d9 * 5.0D;
        projectile.motionY = d7 / d9 * 5.0D;
        projectile.motionZ = d8 / d9 * 5.0D;
        this.world.spawnEntity(projectile);
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
        entityDropItem(new ItemStack(EItem.draconicPortalStaff, 1, getMetaData()), 1.0F); 
      for (int k = 0; k < 2500; k++) {
        double d2 = this.rand.nextGaussian() * 0.05D;
        double d1 = this.rand.nextGaussian() * 0.05D;
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, -0.25D, d1, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, -0.25D, d1, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height * 4.0F), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, 0.5D, d1, new int[0]);
      } 
    } 
    if (!this.world.isRemote && (isPlayer() || (this.recentlyHit > 0 && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))) {
      int i = getExperiencePoints(this.attackingPlayer) / 60;
      i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.attackingPlayer, i);
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
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
        List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(256.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
        if (list != null && !list.isEmpty() && !isBeingRidden())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityTameBase entity = list.get(i1);
            if (entity != null)
              if (false) {
                playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
                entity.changeDimension(1);
              }  
          }  
        playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
        player.changeDimension(1);
        return true;
      } 
      player.world.playSound(player, new BlockPos((Entity)player), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 100.0F, 0.5F);
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
    return (Entity[])this.partArray;
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

