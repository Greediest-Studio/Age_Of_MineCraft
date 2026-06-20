package net.minecraft.AgeOfMinecraft.entity;

import net.minecraft.AgeOfMinecraft.util.AttributeCompat;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import java.util.Random;

public class EntityBase extends EntityCreature {
  public World world;
  public Random rand;
  public double posX;
  public double posY;
  public double posZ;
  public double motionX;
  public double motionY;
  public double motionZ;
  public float width;
  public float height;
  public float rotationYaw;
  public float rotationPitch;
  public float rotationYawHead;
  public float renderYawOffset;
  public float prevRotationYaw;
  public float prevRotationPitch;
  public float prevRenderYawOffset;
  public float prevRotationYawHead;
  public float fallDistance;
  public boolean onGround;
  public boolean noClip;
  public boolean isAirBorne;
  public int hurtResistantTime;
  public int deathTime;
  public int idleTime;
  public int experienceValue;
  public float[] inventoryArmorDropChances;
  public float[] inventoryHandsDropChances;
  public int ticksExisted;

  private double lastSyncedCompatPosX;
  private double lastSyncedCompatPosY;
  private double lastSyncedCompatPosZ;
  private double lastSyncedCompatMotionX;
  private double lastSyncedCompatMotionY;
  private double lastSyncedCompatMotionZ;
  private float lastSyncedCompatRotationYaw;
  private float lastSyncedCompatRotationPitch;
  private float lastSyncedCompatRotationYawHead;
  private float lastSyncedCompatRenderYawOffset;
  private float lastSyncedCompatFallDistance;
  private boolean lastSyncedCompatOnGround;
  private boolean lastSyncedCompatNoClip;
  private boolean lastSyncedCompatIsAirBorne;
  private int lastSyncedCompatTicks;

  protected final EntityAITasks tasks;
  
  protected final EntityAITasks targetTasks;
  
  public EntityBase(World worldIn) {
    super(worldIn);
    this.world = worldIn;
    syncCompatEntityFields();
    this.tasks = EntityAICompat.tasks(this);
    this.targetTasks = EntityAICompat.targetTasks(this);
  }

  protected EntityAITasks compatTasks() {
    return EntityAICompat.tasks(this);
  }

  protected EntityAITasks compatTargetTasks() {
    return EntityAICompat.targetTasks(this);
  }

  protected Random compatRandom() {
    Random random = EntityCompat.getRandom(this, this.rand, "entity.rand", "rand", "field_70146_Z", "X");
    return random == null ? new Random() : random;
  }

  public void onUpdate() {
    syncCompatEntityFields();
    syncCompatTicks();
    super.onUpdate();
    syncCompatEntityFields();
    syncCompatTicks();
  }

  public void onLivingUpdate() {
    syncCompatEntityFields();
    syncCompatTicks();
    super.onLivingUpdate();
    syncCompatEntityFields();
    syncCompatTicks();
  }

  protected void syncCompatEntityFields() {
    this.world = EntityCompat.world(this);
    this.rand = EntityCompat.getRandom(this, this.rand, "entity.rand", "rand", "field_70146_Z", "X");
    double realPosX = EntityCompat.posX(this);
    double realPosY = EntityCompat.posY(this);
    double realPosZ = EntityCompat.posZ(this);
    double realMotionX = EntityCompat.getDouble(this, this.motionX, "entity.motionX", "motionX", "field_70159_w", "v");
    double realMotionY = EntityCompat.getDouble(this, this.motionY, "entity.motionY", "motionY", "field_70181_x", "x");
    double realMotionZ = EntityCompat.getDouble(this, this.motionZ, "entity.motionZ", "motionZ", "field_70179_y", "w");
    float realRotationYaw = EntityCompat.getFloat(this, this.rotationYaw, "entity.rotationYaw", "rotationYaw", "field_70177_z", "y");
    float realRotationPitch = EntityCompat.getFloat(this, this.rotationPitch, "entity.rotationPitch", "rotationPitch", "field_70125_A", "z");
    float realRotationYawHead = EntityCompat.getFloat(this, this.rotationYawHead, "living.rotationYawHead", "rotationYawHead", "field_70759_as", "aK");
    float realRenderYawOffset = EntityCompat.getFloat(this, this.renderYawOffset, "living.renderYawOffset", "renderYawOffset", "field_70761_aq", "aM");
    float realFallDistance = EntityCompat.getFloat(this, this.fallDistance, "entity.fallDistance", "fallDistance", "field_70143_R", "M");
    boolean realOnGround = EntityCompat.getBoolean(this, this.onGround, "entity.onGround", "onGround", "field_70122_E", "C");
    boolean realNoClip = EntityCompat.getBoolean(this, this.noClip, "entity.noClip", "noClip", "field_70145_X", "U");
    boolean realIsAirBorne = EntityCompat.getBoolean(this, this.isAirBorne, "entity.isAirBorne", "isAirBorne", "field_70132_H", "G");
    if (this.posX != this.lastSyncedCompatPosX) {
      EntityCompat.setPosX(this, this.posX);
      realPosX = this.posX;
    }
    if (this.posY != this.lastSyncedCompatPosY) {
      EntityCompat.setPosY(this, this.posY);
      realPosY = this.posY;
    }
    if (this.posZ != this.lastSyncedCompatPosZ) {
      EntityCompat.setPosZ(this, this.posZ);
      realPosZ = this.posZ;
    }
    if (this.motionX != this.lastSyncedCompatMotionX) {
      EntityCompat.setDouble(this, this.motionX, "entity.motionX", "motionX", "field_70159_w", "v");
      realMotionX = this.motionX;
    }
    if (this.motionY != this.lastSyncedCompatMotionY) {
      EntityCompat.setDouble(this, this.motionY, "entity.motionY", "motionY", "field_70181_x", "x");
      realMotionY = this.motionY;
    }
    if (this.motionZ != this.lastSyncedCompatMotionZ) {
      EntityCompat.setDouble(this, this.motionZ, "entity.motionZ", "motionZ", "field_70179_y", "w");
      realMotionZ = this.motionZ;
    }
    if (this.rotationYaw != this.lastSyncedCompatRotationYaw) {
      EntityCompat.setFloat(this, this.rotationYaw, "entity.rotationYaw", "rotationYaw", "field_70177_z", "y");
      realRotationYaw = this.rotationYaw;
    }
    if (this.rotationPitch != this.lastSyncedCompatRotationPitch) {
      EntityCompat.setFloat(this, this.rotationPitch, "entity.rotationPitch", "rotationPitch", "field_70125_A", "z");
      realRotationPitch = this.rotationPitch;
    }
    if (this.rotationYawHead != this.lastSyncedCompatRotationYawHead) {
      EntityCompat.setFloat(this, this.rotationYawHead, "living.rotationYawHead", "rotationYawHead", "field_70759_as", "aK");
      realRotationYawHead = this.rotationYawHead;
    }
    if (this.renderYawOffset != this.lastSyncedCompatRenderYawOffset) {
      EntityCompat.setFloat(this, this.renderYawOffset, "living.renderYawOffset", "renderYawOffset", "field_70761_aq", "aM");
      realRenderYawOffset = this.renderYawOffset;
    }
    if (this.fallDistance != this.lastSyncedCompatFallDistance) {
      EntityCompat.setFloat(this, this.fallDistance, "entity.fallDistance", "fallDistance", "field_70143_R", "M");
      realFallDistance = this.fallDistance;
    }
    if (this.onGround != this.lastSyncedCompatOnGround) {
      EntityCompat.setBoolean(this, this.onGround, "entity.onGround", "onGround", "field_70122_E", "C");
      realOnGround = this.onGround;
    }
    if (this.noClip != this.lastSyncedCompatNoClip) {
      EntityCompat.setBoolean(this, this.noClip, "entity.noClip", "noClip", "field_70145_X", "U");
      realNoClip = this.noClip;
    }
    if (this.isAirBorne != this.lastSyncedCompatIsAirBorne) {
      EntityCompat.setBoolean(this, this.isAirBorne, "entity.isAirBorne", "isAirBorne", "field_70132_H", "G");
      realIsAirBorne = this.isAirBorne;
    }
    this.posX = realPosX;
    this.posY = realPosY;
    this.posZ = realPosZ;
    this.motionX = realMotionX;
    this.motionY = realMotionY;
    this.motionZ = realMotionZ;
    this.width = EntityCompat.getFloat(this, this.width, "entity.width", "width", "field_70130_N", "J");
    this.height = EntityCompat.getFloat(this, this.height, "entity.height", "height", "field_70131_O", "K");
    this.rotationYaw = realRotationYaw;
    this.rotationPitch = realRotationPitch;
    this.rotationYawHead = realRotationYawHead;
    this.renderYawOffset = realRenderYawOffset;
    this.prevRotationYaw = EntityCompat.getFloat(this, this.prevRotationYaw, "entity.prevRotationYaw", "prevRotationYaw", "field_70126_B", "A");
    this.prevRotationPitch = EntityCompat.getFloat(this, this.prevRotationPitch, "entity.prevRotationPitch", "prevRotationPitch", "field_70127_C", "B");
    this.prevRenderYawOffset = EntityCompat.getFloat(this, this.prevRenderYawOffset, "living.prevRenderYawOffset", "prevRenderYawOffset", "field_70760_ar", "aN");
    this.prevRotationYawHead = EntityCompat.getFloat(this, this.prevRotationYawHead, "living.prevRotationYawHead", "prevRotationYawHead", "field_70758_at", "aL");
    this.fallDistance = realFallDistance;
    this.onGround = realOnGround;
    this.noClip = realNoClip;
    this.isAirBorne = realIsAirBorne;
    this.hurtResistantTime = EntityCompat.getInt(this, this.hurtResistantTime, "entity.hurtResistantTime", "hurtResistantTime", "field_70172_ad", "ad");
    this.deathTime = EntityCompat.getInt(this, this.deathTime, "living.deathTime", "deathTime", "field_70725_aQ", "aS");
    this.idleTime = EntityCompat.getInt(this, this.idleTime, "living.idleTime", "idleTime", "field_70708_bq", "bp");
    this.experienceValue = EntityCompat.getInt(this, this.experienceValue, "living.experienceValue", "experienceValue", "field_70728_aV", "aV");
    this.inventoryArmorDropChances = EntityCompat.getFloatArray(this, this.inventoryArmorDropChances, "living.inventoryArmorDropChances", "inventoryArmorDropChances", "field_184655_bs", "bF");
    this.inventoryHandsDropChances = EntityCompat.getFloatArray(this, this.inventoryHandsDropChances, "living.inventoryHandsDropChances", "inventoryHandsDropChances", "field_184654_br", "bE");
    if (this.inventoryArmorDropChances == null)
      this.inventoryArmorDropChances = new float[4];
    if (this.inventoryHandsDropChances == null)
      this.inventoryHandsDropChances = new float[2];
    this.lastSyncedCompatPosX = realPosX;
    this.lastSyncedCompatPosY = realPosY;
    this.lastSyncedCompatPosZ = realPosZ;
    this.lastSyncedCompatMotionX = realMotionX;
    this.lastSyncedCompatMotionY = realMotionY;
    this.lastSyncedCompatMotionZ = realMotionZ;
    this.lastSyncedCompatRotationYaw = realRotationYaw;
    this.lastSyncedCompatRotationPitch = realRotationPitch;
    this.lastSyncedCompatRotationYawHead = realRotationYawHead;
    this.lastSyncedCompatRenderYawOffset = realRenderYawOffset;
    this.lastSyncedCompatFallDistance = realFallDistance;
    this.lastSyncedCompatOnGround = realOnGround;
    this.lastSyncedCompatNoClip = realNoClip;
    this.lastSyncedCompatIsAirBorne = realIsAirBorne;
  }

  protected void syncCompatTicks() {
    int realTicks = EntityCompat.ticksExisted(this);
    if (this.ticksExisted != this.lastSyncedCompatTicks) {
      EntityCompat.setTicksExisted(this, this.ticksExisted);
      realTicks = this.ticksExisted;
    }
    this.ticksExisted = realTicks;
    this.lastSyncedCompatTicks = realTicks;
  }

  public IAttributeInstance getEntityAttribute(IAttribute attribute) {
    IAttributeInstance instance = super.getEntityAttribute(attribute);
    return instance == null ? AttributeCompat.getEntityAttribute(this, attribute) : instance;
  }

  public AbstractAttributeMap getAttributeMap() {
    AbstractAttributeMap attributeMap = super.getAttributeMap();
    return attributeMap == null ? AttributeCompat.getAttributeMap(this) : attributeMap;
  }

  public EntityDataManager getDataManager() {
    EntityDataManager dataManager = super.getDataManager();
    return dataManager == null ? EntityCompat.getDataManager(this) : dataManager;
  }
  
  public int playMusic() {
    return 0;
  }
}
