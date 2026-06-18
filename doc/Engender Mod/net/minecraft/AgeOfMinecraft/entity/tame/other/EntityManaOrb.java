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
  
  public ItemStack magnet = ItemStack.field_190927_a;
  
  private int xpTargetColor;
  
  private static final DataParameter<Integer> amount = EntityDataManager.func_187226_a(EntityManaOrb.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> entropy = EntityDataManager.func_187226_a(EntityManaOrb.class, DataSerializers.field_187198_h);
  
  private int blinkTimer;
  
  public EntityManaOrb(World worldIn, double x, double y, double z, int expValue, boolean isEntropy) {
    this(worldIn);
    func_70107_b(x, y, z);
    setMana(expValue);
    setEntropy(isEntropy);
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public EntityManaOrb(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    func_189654_d(true);
    this.field_70177_z = (float)(Math.random() * 360.0D);
    this.field_70159_w = ((float)(Math.random() * 0.1D - 0.05D) * 2.0F);
    this.field_70181_x = ((float)(Math.random() * 0.1D) * 2.0F);
    this.field_70179_y = ((float)(Math.random() * 0.1D - 0.05D) * 2.0F);
  }
  
  protected void func_70088_a() {
    func_184212_Q().func_187214_a(amount, Integer.valueOf(0));
    func_184212_Q().func_187214_a(entropy, Boolean.valueOf(false));
  }
  
  public void setEntropy(boolean value) {
    func_184212_Q().func_187227_b(entropy, Boolean.valueOf(value));
  }
  
  public boolean getEntropy() {
    return ((Boolean)this.field_70180_af.func_187225_a(entropy)).booleanValue();
  }
  
  public void setMana(int mana) {
    func_184212_Q().func_187227_b(amount, Integer.valueOf(mana));
  }
  
  public int getMana() {
    return ((Integer)this.field_70180_af.func_187225_a(amount)).intValue();
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    float f = 0.5F;
    f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
    int i = super.func_70070_b();
    int j = i & 0xFF;
    int k = i >> 16 & 0xFF;
    j += (int)(f * 15.0F * 16.0F);
    if (j > 240)
      j = 240; 
    return j | k << 16;
  }
  
  protected int func_190531_bD() {
    return Integer.MAX_VALUE;
  }
  
  @SideOnly(Side.CLIENT)
  public static double getRenderDistanceWeight() {
    return 64.0D;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.magnet.func_190926_b() || (!this.magnet.func_190926_b() && !(this.magnet.func_77973_b() instanceof ItemManaCollector)) || this.closestPlayer == null || (this.closestPlayer != null && func_70032_d((Entity)this.closestPlayer) > 24.0D)) {
      this.magnet = ItemStack.field_190927_a;
      this.closestPlayer = null;
    } 
    if (this.blinkTimer > 0) {
      this.blinkTimer--;
    } else if (getEntropy()) {
      this.blinkTimer += 20 + this.field_70146_Z.nextInt(100);
    } else {
      this.blinkTimer += 20;
    } 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    func_145771_j(this.field_70165_t, ((func_174813_aQ()).field_72338_b + (func_174813_aQ()).field_72337_e) / 2.0D, this.field_70161_v);
    if (this.xpTargetColor < this.xpColor - 20 + func_145782_y() % 100)
      this.xpTargetColor = this.xpColor; 
    if (!this.field_70170_p.field_72995_K && this.field_70173_aa > 20 && !this.magnet.func_190926_b() && this.closestPlayer != null && func_70032_d((Entity)this.closestPlayer) <= 16.0D) {
      ItemStack stack = this.magnet;
      Item item = stack.func_77973_b();
      if (item instanceof ItemManaCollector && ((!getEntropy() && ((ItemManaCollector)item).getMana(stack) < ((ItemManaCollector)item).getMaxMana(stack)) || (getEntropy() && ((ItemManaCollector)item).getEntropy(stack) < ((ItemManaCollector)item).getMaxEntropy(stack)))) {
        double d1 = (this.closestPlayer.field_70165_t - this.field_70165_t) / 24.0D;
        double d2 = (this.closestPlayer.field_70163_u + this.closestPlayer.func_70047_e() - this.field_70163_u) / 24.0D;
        double d3 = (this.closestPlayer.field_70161_v - this.field_70161_v) / 24.0D;
        double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        double d5 = 1.0D - d4;
        if (d5 > 0.0D) {
          d5 *= d5;
          this.field_70159_w += d1 / d4 * d5 * 0.1D;
          this.field_70181_x += d2 / d4 * d5 * 0.1D;
          this.field_70179_y += d3 / d4 * d5 * 0.1D;
        } 
      } else {
        this.magnet = ItemStack.field_190927_a;
        this.closestPlayer = null;
      } 
    } 
    if (this.field_70173_aa == 40) {
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
    } 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    float f = 0.95F;
    if (this.field_70122_E) {
      BlockPos underPos = new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));
      IBlockState underState = this.field_70170_p.func_180495_p(underPos);
      f = underState.func_177230_c().getSlipperiness(underState, (IBlockAccess)this.field_70170_p, underPos, this) * 0.99F;
    } 
    this.field_70159_w *= f;
    this.field_70181_x *= 0.95D;
    this.field_70179_y *= f;
    if (this.field_70122_E)
      this.field_70181_x *= -0.8999999761581421D; 
    this.xpColor++;
    this.age++;
    if (this.age >= 6000 && !EngenderConfig.debugMode)
      func_70106_y(); 
  }
  
  public boolean func_70072_I() {
    return this.field_70170_p.func_72918_a(func_174813_aQ(), Material.field_151586_h, this);
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    compound.func_74777_a("Age", (short)this.age);
    compound.func_74768_a("Value", getMana());
    compound.func_74757_a("Entropy", getEntropy());
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    this.age = compound.func_74765_d("Age");
    setMana(compound.func_74762_e("Value"));
    setEntropy(compound.func_74767_n("Entropy"));
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
  
  public boolean func_70075_an() {
    return false;
  }
  
  private static int roundAverage(float value) {
    double floor = Math.floor(value);
    return (int)floor + ((Math.random() < value - floor) ? 1 : 0);
  }
}
