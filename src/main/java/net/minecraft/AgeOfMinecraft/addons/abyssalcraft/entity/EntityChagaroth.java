package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityChagaroth extends EntityTameBase implements Armored, Massive, Structure {
  private static final DataParameter<Integer> FLAMETIMER = EntityDataManager.createKey(EntityChagaroth.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.createKey(EntityChagaroth.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.createKey(EntityChagaroth.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.createKey(EntityChagaroth.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
  
  private final float[] xRotationHeads = new float[2];
  
  private final float[] yRotationHeads = new float[2];
  
  private final float[] xRotOHeads = new float[2];
  
  private final float[] yRotOHeads = new float[2];
  
  private final int[] nextHeadUpdate = new int[2];
  
  private final int[] idleHeadUpdates = new int[2];
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8.0D, 0);
  
  public EntityChagaroth(World par1World) {
    super(par1World);
    setSize(2.25F, 4.5F);
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 0.0D, true));
    this.tasks.addTask(3, new EntityAILookIdle(this));
    this.ignoreFrustumCheck = true;
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 5;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setOverlay(BossInfo.Overlay.NOTCHED_20);
    if (getHealth() > getMaxHealth() * 0.75D && this.bossInfo.getColor() != BossInfo.Color.BLUE)
      this.bossInfo.setColor(BossInfo.Color.BLUE); 
    if (getHealth() < getMaxHealth() * 0.75D && getHealth() > getMaxHealth() / 2.0F && this.bossInfo.getColor() != BossInfo.Color.GREEN)
      this.bossInfo.setColor(BossInfo.Color.GREEN); 
    if (getHealth() < getMaxHealth() / 2.0F && getHealth() > getMaxHealth() / 4.0F && this.bossInfo.getColor() != BossInfo.Color.YELLOW)
      this.bossInfo.setColor(BossInfo.Color.YELLOW); 
    if (getHealth() < getMaxHealth() / 4.0F && getHealth() > 0.0F && this.bossInfo.getColor() != BossInfo.Color.RED)
      this.bossInfo.setColor(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public String getName() {
    return TextFormatting.DARK_RED + super.getName() + TextFormatting.WHITE;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(FIRST_HEAD_TARGET, 0);
    this.getDataManager().register(SECOND_HEAD_TARGET, 0);
    this.getDataManager().register(THIRD_HEAD_TARGET, 0);
    this.getDataManager().register(FLAMETIMER, 0);
  }
  
  public int getFlameTime() {
    return this.getDataManager().get(FLAMETIMER);
  }
  
  public void setFlameTime(int time) {
    this.getDataManager().set(FLAMETIMER, time);
  }
  
  public AxisAlignedBB getCollisionBoundingBox() {
    return getEntityBoundingBox();
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 400, 1)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.dreadguard_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.dreadguard_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.dreadguard_death;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.5F : 5.0F;
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    getNavigator().clearPath();
    if (this.ticksExisted % 10 == 0)
      heal(1.0F); 
    for (int i = 1; i < 3; i++) {
      if (this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
        this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
        int k1 = getWatchedTargetId(i);
        if (k1 > 0) {
          Entity entity = this.world.getEntityByID(k1);
          if (entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= 2304.0D && canEntityBeSeen(entity)) {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage) {
              updateWatchedTargetId(i, 0);
            } else {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } else {
            updateWatchedTargetId(i, 0);
          } 
        } else {
          List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(48.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
          for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
            EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
            if (entitylivingbase != this && !false && entitylivingbase.isEntityAlive() && entitylivingbase.getHealth() > 0.0F && canEntityBeSeen(entitylivingbase)) {
              if (entitylivingbase instanceof EntityPlayer) {
                if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
                  updateWatchedTargetId(i, entitylivingbase.getEntityId()); 
                break;
              } 
              updateWatchedTargetId(i, entitylivingbase.getEntityId());
              break;
            } 
            list.remove(entitylivingbase);
          } 
        } 
      } 
    } 
    if (getAttackTarget() != null) {
      updateWatchedTargetId(0, getAttackTarget().getEntityId());
    } else {
      updateWatchedTargetId(0, 0);
    } 
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + 0.5D, p_82216_2_.posZ, true);
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
    EntityDreadSlugOther entitydreadslug1, entitydreadslug11;
    EntityDreadSpawn mob;
    EntityDreadSlugOther entitydreadslug111;
    EntityChagarothSpawn spawn;
    EntityDreadSlugOther entitydreadslug1111;
    EntityChagarothFist fist;
    EntityDreadedChargeOther entitydragonfireball;
    double d0 = getHeadX(p_82209_1_);
    double d1 = getHeadY(p_82209_1_);
    double d2 = getHeadZ(p_82209_1_);
    double d3 = x - d0;
    double d4 = y - d1;
    double d5 = z - d2;
    float f1 = MathHelper.sqrt(d3 * d3 + d5 * d5) * 0.2F;
    float f2 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    EntityDreadSlugOther entitydreadslug = new EntityDreadSlugOther(this.world, this);
    entitydreadslug.posY = d1;
    entitydreadslug.posX = d0;
    entitydreadslug.posZ = d2;
    entitydreadslug.shoot(d3, d4 + f1, d5, 1.75F, 1.0F);
    playSound(ESound.amalgamate, 2.0F, 1.25F);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      this.world.spawnEntity(entitydreadslug);
    entitydreadslug.motionX = d3 / f2 * 0.8D * 0.8D + entitydreadslug.motionX;
    entitydreadslug.motionY = d4 / f2 * 0.8D * 0.8D + entitydreadslug.motionY;
    entitydreadslug.motionZ = d5 / f2 * 0.8D * 0.8D + entitydreadslug.motionZ;
    switch (this.rand.nextInt(5)) {
      case 0:
        entitydreadslug1 = new EntityDreadSlugOther(this.world, this);
        entitydreadslug1.posY = d1;
        entitydreadslug1.posX = d0;
        entitydreadslug1.posZ = d2;
        entitydreadslug1.shoot(d3, d4 + f1, d5, 1.75F, 1.0F);
        playSound(ESound.amalgamate, 2.0F, 1.25F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitydreadslug1);
        entitydreadslug1.motionX = d3 / f2 * 0.8D * 0.8D + entitydreadslug1.motionX;
        entitydreadslug1.motionY = d4 / f2 * 0.8D * 0.8D + entitydreadslug1.motionY;
        entitydreadslug1.motionZ = d5 / f2 * 0.8D * 0.8D + entitydreadslug1.motionZ;
        this.nextHeadUpdate[p_82209_1_ - 2] = this.ticksExisted + 10;
        break;
      case 1:
        entitydreadslug11 = new EntityDreadSlugOther(this.world, this);
        entitydreadslug11.posY = d1;
        entitydreadslug11.posX = d0;
        entitydreadslug11.posZ = d2;
        mob = new EntityDreadSpawn(this.world);
        mob.copyLocationAndAnglesFrom(entitydreadslug11);
        entitydreadslug11.shoot(d3, d4 + f1 + this.rand.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        playSound(ESound.amalgamate, 2.0F, 1.25F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitydreadslug11);
        mob.setOwnerId(getOwnerId());
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(mob);
        mob.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2.0F);
        mob.startRiding(entitydreadslug11);
        mob.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.ticksExisted + 20;
        break;
      case 2:
        entitydreadslug111 = new EntityDreadSlugOther(this.world, this);
        entitydreadslug111.posY = d1;
        entitydreadslug111.posX = d0;
        entitydreadslug111.posZ = d2;
        spawn = new EntityChagarothSpawn(this.world);
        spawn.copyLocationAndAnglesFrom(entitydreadslug111);
        entitydreadslug111.shoot(d3, d4 + f1 + this.rand.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        playSound(ESound.amalgamate, 2.0F, 1.25F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitydreadslug111);
        spawn.setOwnerId(getOwnerId());
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(spawn);
        spawn.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2.0F);
        spawn.startRiding(entitydreadslug111);
        spawn.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.ticksExisted + 20;
        break;
      case 3:
        entitydreadslug1111 = new EntityDreadSlugOther(this.world, this);
        entitydreadslug1111.posY = d1;
        entitydreadslug1111.posX = d0;
        entitydreadslug1111.posZ = d2;
        fist = new EntityChagarothFist(this.world);
        fist.copyLocationAndAnglesFrom(entitydreadslug1111);
        entitydreadslug1111.shoot(d3, d4 + f1 + this.rand.nextDouble() * 150.0D, d5, 1.3F, 1.0F);
        playSound(ESound.amalgamate, 2.0F, 1.25F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitydreadslug1111);
        fist.setOwnerId(getOwnerId());
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(fist);
        fist.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2.0F);
        fist.startRiding(entitydreadslug1111);
        fist.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.ticksExisted + 20;
        break;
      case 4:
        this.world.playEvent(null, 1016, new BlockPos(this), 0);
        d4 = y + 0.5D - d1;
        entitydragonfireball = new EntityDreadedChargeOther(this.world, this, d3, d4, d5);
        entitydragonfireball.posX = d0;
        entitydragonfireball.posY = d1;
        entitydragonfireball.posZ = d2;
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitydragonfireball);
        this.nextHeadUpdate[p_82209_1_ - 2] = this.ticksExisted + 100;
        break;
    } 
  }
  
  private double getHeadX(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.posX; 
    float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) * 0.017453292F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.5D;
  }
  
  private double getHeadY(int p_82208_1_) {
    return this.posY + getEyeHeight() * 0.85D;
  }
  
  private double getHeadZ(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.posZ; 
    float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) * 0.017453292F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.5D;
  }
  
  private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);
    if (f > p_82204_3_)
      f = p_82204_3_; 
    if (f < -p_82204_3_)
      f = -p_82204_3_; 
    return p_82204_1_ + f;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadYRotation(int p_82207_1_) {
    return this.yRotationHeads[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadXRotation(int p_82210_1_) {
    return this.xRotationHeads[p_82210_1_];
  }
  
  public void onLivingUpdate() {
    if (this.ticksExisted % 40 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int x = getPosition().getX() - 3; x <= getPosition().getX() + 3; x++) {
        for (int z = getPosition().getZ() - 3; z <= getPosition().getZ() + 3; z++) {
          if (!(this.world.getBiome(new BlockPos(x, 0, z)) instanceof com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome) && this.world
            .getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged) {
            playSound(ESound.amalgamate, 1.0F, 1.0F);
            Biome b = ACBiomes.dreadlands;
            Chunk c = this.world.getChunk(getPosition());
            c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(b);
            c.setModified(true);
            PacketDispatcher.sendToDimension(new CleansingRitualMessage(x, z, Biome.getIdForBiome(b)), this.world.provider.getDimension());
          } 
        } 
      }  
    for (int a = 0; a < 10; a++)
      this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.25D, this.posY + 1.0D + this.rand.nextGaussian(), this.posZ + this.rand.nextGaussian() * 1.25D, 0.375D + this.rand.nextDouble() * 0.15D, 0.0D, 0.0D);
    for (int l = 0; l < 3; l++) {
      double d10 = getHeadX(l);
      double d2 = getHeadY(l);
      double d4 = getHeadZ(l);
      this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.2D, d2 + this.rand.nextGaussian() * 0.4D + 0.2D, d4 + this.rand.nextGaussian() * 0.2D, 0.375D + this.rand.nextDouble() * 0.15D, 0.0D, 0.0D);
    } 
    setSprinting(false);
    this.motionX = 0.0D;
    this.motionZ = 0.0D;
    if (this.motionY > 0.0D)
      this.motionY = 0.0D; 
    this.isAirBorne = false;
    this.onGround = true;
    setSprinting(false);
    for (int i = 0; i < 2; i++) {
      this.yRotOHeads[i] = this.yRotationHeads[i];
      this.xRotOHeads[i] = this.xRotationHeads[i];
    } 
    for (int j = 0; j < 2; j++) {
      int k = getWatchedTargetId(j + 1);
      Entity entity1 = null;
      if (k > 0)
        entity1 = this.world.getEntityByID(k); 
      if (entity1 != null) {
        double d11 = getHeadX(j + 1);
        double d12 = getHeadY(j + 1);
        double d13 = getHeadZ(j + 1);
        double d6 = entity1.posX - d11;
        double d7 = entity1.posY + entity1.getEyeHeight() - d12;
        double d8 = entity1.posZ - d13;
        double d9 = MathHelper.sqrt(d6 * d6 + d8 * d8);
        float f = (float)(MathHelper.atan2(d8, d6) * 57.29577951308232D) - 90.0F;
        float f1 = (float)-(MathHelper.atan2(d7, d9) * 57.29577951308232D);
        this.xRotationHeads[j] = rotlerp(this.xRotationHeads[j], f1, 40.0F);
        this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], f, 10.0F);
      } else {
        this.xRotationHeads[j] = rotlerp(this.xRotationHeads[j], this.rotationPitch, 40.0F);
        this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], this.rotationYawHead, 10.0F);
      } 
    } 
    List<EntityLivingBase> flamedetector = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(12.0D, 12.0D, 12.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE, EntitySelectors.CAN_AI_TARGET }));
    if (flamedetector != null && !flamedetector.isEmpty())
        for (EntityLivingBase entity : flamedetector) {
            if (entity != null && !false && getDistanceSq(entity) <= 256.0D)
                if (getFlameTime() <= (isHero() ? -100 : -200)) {
                    setFlameTime(150);
                    if (entity != getAttackTarget()) {
                        setAttackTarget(entity);
                        faceEntity(entity, 180.0F, 40.0F);
                    }
                }
        }
    if (hasOpenMouth()) {
      this.world.setEntityState(this, (byte)23);
      if (this.ticksExisted % 3 == 0) {
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 2.0F + getRNG().nextFloat(), getRNG().nextFloat() * 0.6F + 0.2F);
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 2.0F + getRNG().nextFloat(), getRNG().nextFloat() * 0.5F + 0.2F);
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 2.0F + getRNG().nextFloat(), getRNG().nextFloat() * 0.4F + 0.2F);
      } 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D), Predicates.and(EntitySelectors.IS_ALIVE));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity : list) {
                if (entity != null && !false && this.rand.nextInt(3) == 0)
                    if (entity.attackEntityFrom(AbyssalCraftAPI.dread, (float) (15.0D - getDistance(entity)) * 5.0F)) {
                        entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1));
                        entity.setFire((int) (30.0F - getDistance(entity)));
                    } else {
                        attackEntityAsMob(entity);
                        entity.setFire((int) (30.0F - getDistance(entity)));
                    }
            }
        if (target.attackEntityFrom(AbyssalCraftAPI.dread, (float)(15.0D - getDistance(target)) * 5.0F)) {
          if (target instanceof EntityLivingBase)
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1)); 
          target.setFire((int)(30.0F - getDistance(target)));
        } else {
          attackEntityAsMob(target);
          target.setFire((int)(30.0F - getDistance(target)));
        } 
      } 
    } 
    if (getAttackTarget() != null)
      faceEntity(getAttackTarget(), 10.0F, 180.0F);
    setFlameTime(getFlameTime() - 1);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive()) {
      if (this.rand.nextInt(isHero() ? 200 : 400) == 0) {
        EntityDreadSpawn mob = new EntityDreadSpawn(this.world);
        mob.copyLocationAndAnglesFrom(this);
        mob.setOwnerId(getOwnerId());
        mob.motionX++;
        this.world.spawnEntity(mob);
        mob.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        EntityChagarothSpawn spawn = new EntityChagarothSpawn(this.world);
        spawn.copyLocationAndAnglesFrom(this);
        spawn.setOwnerId(getOwnerId());
        spawn.motionX++;
        this.world.spawnEntity(spawn);
        spawn.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      } 
      if (this.rand.nextInt(isHero() ? 400 : 800) == 0) {
        EntityChagarothFist fist = new EntityChagarothFist(this.world);
        fist.copyLocationAndAnglesFrom(this);
        fist.setOwnerId(getOwnerId());
        fist.motionX++;
        this.world.spawnEntity(fist);
        fist.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      } 
      if (this.rand.nextInt(isHero() ? 800 : 1600) == 0) {
        EntityDreadguard dreadGuard = new EntityDreadguard(this.world);
        dreadGuard.copyLocationAndAnglesFrom(this);
        dreadGuard.setOwnerId(getOwnerId());
        dreadGuard.motionX++;
        this.world.spawnEntity(dreadGuard);
        dreadGuard.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      } 
      if (this.rand.nextInt(isHero() ? 1600 : 3200) == 0) {
        EntityGreaterDreadSpawn dreadGuard = new EntityGreaterDreadSpawn(this.world);
        dreadGuard.copyLocationAndAnglesFrom(this);
        dreadGuard.setOwnerId(getOwnerId());
        dreadGuard.motionX++;
        this.world.spawnEntity(dreadGuard);
        dreadGuard.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      } 
      if (this.rand.nextInt(isHero() ? 3200 : 6400) == 0) {
        EntityLesserDreadbeast dreadGuard = new EntityLesserDreadbeast(this.world);
        dreadGuard.copyLocationAndAnglesFrom(this);
        dreadGuard.setOwnerId(getOwnerId());
        dreadGuard.motionX++;
        this.world.spawnEntity(dreadGuard);
        dreadGuard.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      } 
    } 
    super.onLivingUpdate();
  }
  
  public boolean hasOpenMouth() {
    return (getFlameTime() > 0);
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 8.0D + this.rand.nextDouble() * 20.0D;
    Vec3d srcVec = new Vec3d(this.posX, this.posY + getEyeHeight(), this.posZ);
    Vec3d lookVec = getLook(1.0F);
    RayTraceResult raytrace = this.world.rayTraceBlocks(srcVec, srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range));
    BlockPos hitpos = (raytrace != null) ? raytrace.getBlockPos() : null;
    double rx = (hitpos == null) ? range : Math.min(range, Math.abs(this.posX - hitpos.getX()));
    double ry = (hitpos == null) ? range : Math.min(range, Math.abs(this.posY - hitpos.getY()));
    double rz = (hitpos == null) ? range : Math.min(range, Math.abs(this.posZ - hitpos.getZ()));
    Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);
    float var9 = 8.0F;
    List<Entity> possibleList = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().offset(lookVec.x * rx, lookVec.y * ry, lookVec.z * rz).grow(var9, var9, var9));
    double hitDist = 0.0D;
    for (Entity possibleEntity : possibleList) {
      if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !false) {
        float borderSize = possibleEntity.getCollisionBorderSize();
        AxisAlignedBB collisionBB = possibleEntity.getEntityBoundingBox().grow(borderSize, borderSize, borderSize);
        RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);
        if (collisionBB.contains(srcVec)) {
          if (0.0D < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = 0.0D;
          } 
          continue;
        } 
        if (interceptPos != null) {
          double possibleDist = srcVec.distanceTo(interceptPos.hitVec);
          if (possibleDist < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = possibleDist;
          } 
        } 
      } 
    } 
    return pointedEntity;
  }
  
  protected void addMouthParticles() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      Vec3d vector = getLookVec();
      double px = this.posX + vector.x * 0.25D;
      double py = this.posY + (this.height * 0.75F);
      double pz = this.posZ + vector.z * 0.25D;
      for (int i = 0; i < 30; i++) {
        double dx = vector.x;
        double dy = vector.y;
        double dz = vector.z;
        double spread = 15.0D + getRNG().nextDouble() * 5.0D;
        double velocity = 0.5D + getRNG().nextDouble();
        dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dy += getRNG().nextGaussian() * 0.007499999832361937D;
        dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, Item.getIdFromItem(ACItems.dreaded_shard_of_abyssalnite));
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, Item.getIdFromItem(ACItems.dread_fragment));
      } 
    } else {
      this.world.setEntityState(this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 23) {
      addMouthParticles();
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("FlameTime", getFlameTime());
    if (this.deathTicks > 0)
      par1NBTTagCompound.setInteger("DeathTicks", this.deathTicks); 
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setFlameTime(par1NBTTagCompound.getInteger("FlameTime"));
    this.deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
  }
  
  public int getWatchedTargetId(int head) {
    return this.getDataManager().get(HEAD_TARGETS[head]);
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.getDataManager().set(HEAD_TARGETS[targetOffset], newId);
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
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if (!potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public void applyEntityCollision(Entity entityIn) {
    if (!isRidingSameEntity(entityIn))
      if (!entityIn.noClip && !this.noClip) {
        double d0 = entityIn.posX - this.posX;
        double d1 = entityIn.posZ - this.posZ;
        double d2 = MathHelper.absMax(d0, d1);
        if (d2 >= 0.01D) {
          d2 = MathHelper.sqrt(d2);
          d0 /= d2;
          d1 /= d2;
          double d3 = 1.0D / d2;
          if (d3 > 1.0D)
            d3 = 1.0D; 
          d0 *= d3;
          d1 *= d3;
          d0 *= 0.2D;
          d1 *= 0.2D;
          entityIn.addVelocity(d0, 0.0D, d1);
          d0 *= 0.1D;
          d1 *= 0.1D;
          addVelocity(d0, 0.0D, d1);
        } 
      }  
  }
  
  public EnumPushReaction getPushReaction() {
    return EnumPushReaction.IGNORE;
  }
  
  protected void onDeathUpdate() {
    this.deathTicks++;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world)) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s Cha'garoth has been killed!!!", new Object[0]), true);
          } 
          getOwner().sendMessage(new TextComponentTranslation("Your Cha'garoth has been destroyed!", new Object[0]));
        }   
    if (this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity) {
        this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
        if (this.deathTicks >= 190 && this.deathTicks <= 200)
          this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
      } 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && 
      this.deathTicks > 180 && this.deathTicks % 2 == 0) {
      int i = 1000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
        this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.dread_fragment, 4)));
        this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.dreaded_chunk_of_abyssalnite, 2)));
        this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.dreaded_shard_of_abyssalnite)));
        this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.dreadium_ingot)));
      } 
    } 
    if (this.deathTicks == 100 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      SpecialTextUtil.ChagarothGroup(this.world, I18n.translateToLocal("message.chagaroth.death.1"));
    if (this.deathTicks == 200 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      List<Entity> list = net.minecraft.AgeOfMinecraft.util.EntityCompat.loadedEntityList(this.world);
      if (list != null)
          for (Entity entity : list) {
              if (entity instanceof EntityJzahar && entity.isEntityAlive())
                  SpecialTextUtil.JzaharGroup(this.world, false ? I18n.translateToLocal("message.jzaharhelpful.snidecomment.chagaroth") : I18n.translateToLocal("message.jzahar.snidecomment.chagaroth"));
          }
      setDead();
      this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ACItems.dread_plagued_gateway_key)));
    } 
  }
  
  private int posneg(int num) {
    return this.rand.nextBoolean() ? this.rand.nextInt(num) : (-1 * this.rand.nextInt(num));
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack itemstack = player.getHeldItem(hand);
    if (itemstack.isEmpty()) {
      if (hasOwner(player)) {
        player.swingArm(EnumHand.MAIN_HAND);
        if (getRidingEntity() == null) {
          startRiding(player, true);
        } else {
          dismountRidingEntity();
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public double getYOffset() {
    return 0.25D;
  }
  
  public void updateRidden() {
    super.updateRidden();
    if (getRidingEntity() instanceof EntityLivingBase) {
      EntityLivingBase entitycreature = (EntityLivingBase)getRidingEntity();
      entitycreature.extinguish();
      entitycreature.removeActivePotionEffect(AbyssalCraftAPI.dread_plague);
      this.renderYawOffset = entitycreature.renderYawOffset;
      if (entitycreature.onGround) {
        entitycreature.motionX *= 0.125D;
        entitycreature.motionZ *= 0.125D;
      } else {
        entitycreature.motionX *= 0.75D;
        entitycreature.motionZ *= 0.75D;
      } 
      if (entitycreature.motionY > 0.0D)
        entitycreature.motionY *= 0.9D; 
      if (entitycreature.motionY < 0.0D)
        entitycreature.motionY *= 1.1D; 
    } 
  }
}


