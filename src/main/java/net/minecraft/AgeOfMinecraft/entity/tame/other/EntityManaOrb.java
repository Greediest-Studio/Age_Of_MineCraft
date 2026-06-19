package net.minecraft.AgeOfMinecraft.entity.tame.other;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.items.ItemManaCollector;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityManaOrb extends Entity {
  public int xpColor;
  
  public int age;
  
  public EntityPlayer closestPlayer = null;
  
  public ItemStack magnet = ItemStack.EMPTY;
  
  private int xpTargetColor;
  
  private static final DataParameter<Integer> amount = EntityDataManager.createKey(EntityManaOrb.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> entropy = EntityDataManager.createKey(EntityManaOrb.class, DataSerializers.BOOLEAN);
  
  private int blinkTimer;
  
  public EntityManaOrb(World worldIn, double x, double y, double z, int expValue, boolean isEntropy) {
    this(worldIn);
    setPosition(x, y, z);
    setMana(expValue);
    setEntropy(isEntropy);
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public EntityManaOrb(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    setNoGravity(true);
    this.rotationYaw = (float)(Math.random() * 360.0D);
    this.motionX = ((float)(Math.random() * 0.1D - 0.05D) * 2.0F);
    this.motionY = ((float)(Math.random() * 0.1D) * 2.0F);
    this.motionZ = ((float)(Math.random() * 0.1D - 0.05D) * 2.0F);
  }
  
  protected void entityInit() {
    getDataManager().register(amount, 0);
    getDataManager().register(entropy, Boolean.FALSE);
  }
  
  public void setEntropy(boolean value) {
    getDataManager().set(entropy, value);
  }
  
  public boolean getEntropy() {
    return (Boolean) this.dataManager.get(entropy);
  }
  
  public void setMana(int mana) {
    getDataManager().set(amount, mana);
  }
  
  public int getMana() {
    return (Integer) this.dataManager.get(amount);
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    float f = 0.5F;
    f = MathHelper.clamp(f, 0.0F, 1.0F);
    int i = super.getBrightnessForRender();
    int j = i & 0xFF;
    int k = i >> 16 & 0xFF;
    j += (int)(f * 15.0F * 16.0F);
    if (j > 240)
      j = 240; 
    return j | k << 16;
  }
  
  protected int getFireImmuneTicks() {
    return Integer.MAX_VALUE;
  }
  
  @SideOnly(Side.CLIENT)
  public static double getRenderDistanceWeight() {
    return 64.0D;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.magnet.isEmpty() || (!this.magnet.isEmpty() && !(this.magnet.getItem() instanceof ItemManaCollector)) || this.closestPlayer == null || (this.closestPlayer != null && getDistance((Entity)this.closestPlayer) > 24.0D)) {
      this.magnet = ItemStack.EMPTY;
      this.closestPlayer = null;
    } 
    if (this.blinkTimer > 0) {
      this.blinkTimer--;
    } else if (getEntropy()) {
      this.blinkTimer += 20 + this.rand.nextInt(100);
    } else {
      this.blinkTimer += 20;
    } 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ);
    if (this.xpTargetColor < this.xpColor - 20 + getEntityId() % 100)
      this.xpTargetColor = this.xpColor; 
    if (!this.world.isRemote && this.ticksExisted > 20 && !this.magnet.isEmpty() && this.closestPlayer != null && getDistance((Entity)this.closestPlayer) <= 16.0D) {
      ItemStack stack = this.magnet;
      Item item = stack.getItem();
      if (item instanceof ItemManaCollector && ((!getEntropy() && ((ItemManaCollector)item).getMana(stack) < ((ItemManaCollector)item).getMaxMana(stack)) || (getEntropy() && ((ItemManaCollector)item).getEntropy(stack) < ((ItemManaCollector)item).getMaxEntropy(stack)))) {
        double d1 = (this.closestPlayer.posX - this.posX) / 24.0D;
        double d2 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / 24.0D;
        double d3 = (this.closestPlayer.posZ - this.posZ) / 24.0D;
        double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        double d5 = 1.0D - d4;
        if (d5 > 0.0D) {
          d5 *= d5;
          this.motionX += d1 / d4 * d5 * 0.1D;
          this.motionY += d2 / d4 * d5 * 0.1D;
          this.motionZ += d3 / d4 * d5 * 0.1D;
        } 
      } else {
        this.magnet = ItemStack.EMPTY;
        this.closestPlayer = null;
      } 
    } 
    if (this.ticksExisted == 40) {
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
    } 
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    float f = 0.95F;
    if (this.onGround) {
      BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor((getEntityBoundingBox()).minY) - 1, MathHelper.floor(this.posZ));
      IBlockState underState = this.world.getBlockState(underPos);
      f = underState.getBlock().getSlipperiness(underState, (IBlockAccess)this.world, underPos, this) * 0.99F;
    } 
    this.motionX *= f;
    this.motionY *= 0.95D;
    this.motionZ *= f;
    if (this.onGround)
      this.motionY *= -0.8999999761581421D; 
    this.xpColor++;
    this.age++;
    if (this.age >= 6000 && !EngenderConfig.debugMode)
      setDead(); 
  }
  
  public boolean handleWaterMovement() {
    return this.world.handleMaterialAcceleration(getEntityBoundingBox(), Material.WATER, this);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    compound.setShort("Age", (short)this.age);
    compound.setInteger("Value", getMana());
    compound.setBoolean("Entropy", getEntropy());
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    this.age = compound.getShort("Age");
    setMana(compound.getInteger("Value"));
    setEntropy(compound.getBoolean("Entropy"));
  }
  
  public float getTextureByXP() {
    if (getEntropy()) {
      if (getMana() >= 30) {
        if (this.blinkTimer > 20)
          return 1.992F; 
        if (this.blinkTimer > 10)
          return 2.496F; 
        return 3.0F;
      } 
      if (getMana() >= 20) {
        if (this.blinkTimer > 20)
          return 2.496F; 
        if (this.blinkTimer > 10)
          return 3.0F; 
        return 3.504F;
      } 
      if (getMana() >= 10) {
        if (this.blinkTimer > 20)
          return 3.0F; 
        if (this.blinkTimer > 10)
          return 3.504F; 
        return 1.992F;
      } 
      if (getMana() >= 5)
        return 2.496F; 
      return 1.992F;
    } 
    if (getMana() >= 128) {
      if (this.blinkTimer > 13)
        return 0.0F; 
      if (this.blinkTimer > 7)
        return 0.504F; 
      return 1.008F;
    } 
    if (getMana() >= 24) {
      if (this.blinkTimer > 13)
        return 0.504F; 
      if (this.blinkTimer > 7)
        return 1.008F; 
      return 1.512F;
    } 
    if (getMana() >= 8) {
      if (this.blinkTimer > 10)
        return 1.512F; 
      return 0.0F;
    } 
    if (getMana() >= 4)
      return 1.008F; 
    if (getMana() >= 2)
      return 0.5F; 
    return 0.0F;
  }
  
  public float getTextureY() {
    if (getEntropy()) {
      if (getMana() >= 30)
        return 8.0F; 
      if (getMana() >= 20)
        return 4.0F; 
      if (getMana() >= 10) {
        if (this.blinkTimer > 10)
          return 0.0F; 
        return 4.0F;
      } 
      if (getMana() >= 5)
        return 0.0F; 
      return 0.0F;
    } 
    if (getMana() >= 512)
      return 8.0F; 
    if (getMana() >= 256)
      return 8.0F; 
    if (getMana() >= 128)
      return 8.0F; 
    if (getMana() >= 72)
      return 4.0F; 
    if (getMana() >= 48)
      return 4.0F; 
    if (getMana() >= 24)
      return 4.0F; 
    if (getMana() >= 8) {
      if (this.blinkTimer > 10)
        return 0.0F; 
      return 4.0F;
    } 
    if (getMana() >= 4)
      return 0.0F; 
    return 0.0F;
  }
  
  public static int getXPSplit(int expValue) {
    if (expValue >= 1024)
      return 1024; 
    if (expValue >= 512)
      return 512; 
    if (expValue >= 256)
      return 256; 
    if (expValue >= 128)
      return 128; 
    if (expValue >= 64)
      return 64; 
    if (expValue >= 32)
      return 32; 
    if (expValue >= 16)
      return 16; 
    if (expValue >= 8)
      return 8; 
    if (expValue >= 4)
      return 4; 
    return (expValue >= 2) ? 2 : 1;
  }
  
  public boolean canBeAttackedWithItem() {
    return false;
  }
  
  private static int roundAverage(float value) {
    double floor = Math.floor(value);
    return (int)floor + ((Math.random() < value - floor) ? 1 : 0);
  }
}
