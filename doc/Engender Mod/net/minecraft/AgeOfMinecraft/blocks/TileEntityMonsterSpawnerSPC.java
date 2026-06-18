package net.minecraft.AgeOfMinecraft.blocks;

import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.items.ItemFusion;
import net.minecraft.AgeOfMinecraft.items.ItemManaCollector;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMonsterSpawnerSPC extends TileEntity implements ITickable, IInventory {
  private NonNullList<ItemStack> fuserItemStacks = NonNullList.func_191197_a(3, ItemStack.field_190927_a);
  
  private float currentItemBurnTime;
  
  public float fuseTime;
  
  public float totalSpawnMobTime;
  
  public double mana;
  
  public double entropy;
  
  private String fuserCustomName;
  
  public int func_70302_i_() {
    return 3;
  }
  
  public boolean func_191420_l() {
    for (ItemStack itemstack : this.fuserItemStacks) {
      if (!itemstack.func_190926_b())
        return false; 
    } 
    return true;
  }
  
  public ItemStack func_70301_a(int index) {
    return (ItemStack)this.fuserItemStacks.get(index);
  }
  
  public ItemStack func_70298_a(int index, int count) {
    return ItemStackHelper.func_188382_a((List)this.fuserItemStacks, index, count);
  }
  
  public ItemStack func_70304_b(int index) {
    return ItemStackHelper.func_188383_a((List)this.fuserItemStacks, index);
  }
  
  public void func_70299_a(int index, ItemStack stack) {
    ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(index);
    boolean flag = (!stack.func_190926_b() && stack.func_77969_a(itemstack) && ItemStack.func_77970_a(stack, itemstack));
    this.fuserItemStacks.set(index, stack);
    if (stack.func_190916_E() > func_70297_j_())
      stack.func_190920_e(func_70297_j_()); 
    if (index == 0 && !flag) {
      this.totalSpawnMobTime = timeToSpawnmob(stack);
      this.fuseTime = 0.0F;
      func_70296_d();
    } 
  }
  
  public String func_70005_c_() {
    return func_145818_k_() ? this.fuserCustomName : "Fusion Crafter";
  }
  
  public ITextComponent func_145748_c_() {
    return (ITextComponent)new TextComponentString(func_70005_c_());
  }
  
  public boolean func_145818_k_() {
    return (this.fuserCustomName != null && this.fuserCustomName.length() > 0);
  }
  
  public void setCustomInventoryName(String p_145951_1_) {
    this.fuserCustomName = p_145951_1_;
  }
  
  public void func_145839_a(NBTTagCompound compound) {
    super.func_145839_a(compound);
    this.fuserItemStacks = NonNullList.func_191197_a(func_70302_i_(), ItemStack.field_190927_a);
    ItemStackHelper.func_191283_b(compound, this.fuserItemStacks);
    this.fuseTime = compound.func_74765_d("CookTime");
    this.totalSpawnMobTime = compound.func_74765_d("CookTimeTotal");
    this.mana = compound.func_74769_h("Mana");
    this.entropy = compound.func_74769_h("Entropy");
    this.currentItemBurnTime = getItemBurnTime((ItemStack)this.fuserItemStacks.get(1));
    if (compound.func_150297_b("CustomName", 8))
      this.fuserCustomName = compound.func_74779_i("CustomName"); 
  }
  
  public NBTTagCompound func_189515_b(NBTTagCompound compound) {
    super.func_189515_b(compound);
    compound.func_74777_a("CookTime", (short)(int)this.fuseTime);
    compound.func_74777_a("CookTimeTotal", (short)(int)this.totalSpawnMobTime);
    compound.func_74780_a("Mana", getMana());
    compound.func_74780_a("Entropy", getEntropy());
    ItemStackHelper.func_191282_a(compound, this.fuserItemStacks);
    if (func_145818_k_())
      compound.func_74778_a("CustomName", this.fuserCustomName); 
    return compound;
  }
  
  @SideOnly(Side.CLIENT)
  public static boolean isBurning(IInventory p_174903_0_) {
    return (p_174903_0_.func_174887_a_(0) > 0);
  }
  
  public void func_73660_a() {
    Random rand = (func_145831_w()).field_73012_v;
    double d3 = (this.field_174879_c.func_177958_n() + rand.nextFloat());
    double d4 = (this.field_174879_c.func_177956_o() + rand.nextFloat());
    double d5 = (this.field_174879_c.func_177952_p() + rand.nextFloat());
    this.field_145850_b.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
    if (this.fuseTime > 0.0F)
      this.field_145850_b.func_175688_a(EnumParticleTypes.PORTAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]); 
    if (rand.nextInt(500) == 0)
      switch (rand.nextInt(40)) {
        case 0:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187740_w, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 1:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187660_W, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 2:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187558_ak, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 3:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187636_O, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 4:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187697_dL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 5:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187816_ej, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 6:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187757_eG, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 7:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187535_aY, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 8:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187793_eY, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 9:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187799_fB, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 10:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_191260_dz, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 11:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187829_fQ, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 12:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187913_gm, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 13:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187861_gG, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 14:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187570_aq, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 15:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187762_di, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 16:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_190031_ew, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 17:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187854_fc, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 18:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_190036_ha, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 19:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_190032_gu, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 20:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187882_fq, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 21:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187817_fK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 22:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187899_gZ, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 23:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187940_hn, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 24:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_190022_cI, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 25:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_191265_hd, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 26:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_191268_hm, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 27:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187594_A, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 28:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187532_aV, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 29:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187555_bJ, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 30:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187670_cb, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 31:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187512_aB, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
          break;
        case 32:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187773_eO, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 33:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187936_hj, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 34:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187525_aO, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 35:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187899_gZ, SoundCategory.BLOCKS, 5.0F, 0.5F, false);
          break;
        case 36:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_191243_bm, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 37:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187602_cF, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 38:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187925_gy, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
          break;
        case 39:
          this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), ESound.witherStormRoar, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
      }  
    boolean flag1 = false;
    if (this.mana > getMaxMana())
      this.mana = getMaxMana(); 
    if (this.entropy > getMaxEntropy())
      this.entropy = getMaxEntropy(); 
    ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(1);
    if (!itemstack.func_190926_b() && itemstack.func_77973_b() instanceof ItemManaCollector && ((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) > 0 && getMana() < getMaxMana()) {
      if (((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.func_77973_b()).setMana(((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) - 500, itemstack);
        this.mana += 500.0D;
      } else if (((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.func_77973_b()).setMana(((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) - 50, itemstack);
        this.mana += 50.0D;
      } else {
        ((ItemManaCollector)itemstack.func_77973_b()).setMana(((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) - 1, itemstack);
        this.mana++;
      } 
      if (((ItemManaCollector)itemstack.func_77973_b()).getMana(itemstack) == 1)
        this.field_145850_b.func_184148_a(null, this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 1.0F, 0.5F); 
    } 
    if (!itemstack.func_190926_b() && itemstack.func_77973_b() instanceof ItemManaCollector && ((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) > 0 && getEntropy() < getMaxEntropy()) {
      if (((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.func_77973_b()).setEntropy(((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) - 500, itemstack);
        this.entropy += 500.0D;
      } else if (((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) >= 50) {
        ((ItemManaCollector)itemstack.func_77973_b()).setEntropy(((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) - 50, itemstack);
        this.entropy += 50.0D;
      } else {
        ((ItemManaCollector)itemstack.func_77973_b()).setEntropy(((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) - 1, itemstack);
        this.entropy++;
      } 
      if (((ItemManaCollector)itemstack.func_77973_b()).getEntropy(itemstack) == 1)
        this.field_145850_b.func_184148_a(null, this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_193781_bp, SoundCategory.BLOCKS, 0.1F, 1.0F); 
    } 
    if (!((ItemStack)this.fuserItemStacks.get(0)).func_190926_b()) {
      if (canSmelt() && this.mana >= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getManaCost() && this.entropy >= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getEntropyCost()) {
        this.totalSpawnMobTime = timeToSpawnmob((ItemStack)this.fuserItemStacks.get(0));
        this.currentItemBurnTime = this.fuseTime;
        this.fuseTime++;
        if (this.fuseTime >= this.totalSpawnMobTime) {
          this.fuseTime = 0.0F;
          this.field_145850_b.func_184148_a(null, this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), SoundEvents.field_193782_bq, SoundCategory.BLOCKS, 1.0F, 2.0F);
          this.mana -= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getManaCost();
          this.entropy -= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getEntropyCost();
          smeltItem();
          flag1 = true;
        } 
      } else {
        this.fuseTime--;
      } 
    } else if (this.fuseTime > 0.0F) {
      this.fuseTime--;
    } 
    if (flag1)
      func_70296_d(); 
  }
  
  public int timeToSpawnmob(ItemStack p_174904_1_) {
    if (p_174904_1_.func_77973_b() instanceof ItemFusion)
      return ((ItemFusion)p_174904_1_.func_77973_b()).getItemToFuse().getTimeToSpawnMob() * fuseTimeMultiplyer(); 
    return 0;
  }
  
  public int fuseTimeMultiplyer() {
    return 20;
  }
  
  private boolean canSmelt() {
    if (((ItemStack)this.fuserItemStacks.get(0)).func_190926_b())
      return false; 
    ItemStack itemstack = MobSpawnerRecipes.instance().getSmeltingResult((ItemStack)this.fuserItemStacks.get(0));
    if (itemstack.func_190926_b())
      return false; 
    ItemStack itemstack1 = (ItemStack)this.fuserItemStacks.get(2);
    if (itemstack1.func_190926_b())
      return true; 
    if (!itemstack1.func_77969_a(itemstack))
      return false; 
    if (this.mana < ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getManaCost())
      return false; 
    if (this.entropy < ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).func_77973_b()).getEntropyCost())
      return false; 
    int result = itemstack1.func_190916_E() + itemstack.func_190916_E();
    return (result <= func_70297_j_() && result <= itemstack1.func_77976_d());
  }
  
  public void smeltItem() {
    if (canSmelt()) {
      ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(0);
      ItemStack itemstack1 = MobSpawnerRecipes.instance().getSmeltingResult(itemstack);
      ItemStack itemstack2 = (ItemStack)this.fuserItemStacks.get(2);
      if (itemstack2.func_190926_b()) {
        this.fuserItemStacks.set(2, itemstack1.func_77946_l());
      } else if (itemstack2.func_77973_b() == itemstack1.func_77973_b()) {
        itemstack2.func_190917_f(itemstack1.func_190916_E());
      } 
      itemstack.func_190918_g(1);
    } 
  }
  
  public static int getItemBurnTime(ItemStack stack) {
    return stack.func_190926_b() ? 0 : 40;
  }
  
  public boolean func_70300_a(EntityPlayer player) {
    if (this.field_145850_b.func_175625_s(this.field_174879_c) != this)
      return false; 
    return (player.func_70092_e(this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o() + 0.5D, this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D);
  }
  
  public void func_174889_b(EntityPlayer player) {}
  
  public void func_174886_c(EntityPlayer player) {}
  
  public boolean func_94041_b(int index, ItemStack stack) {
    if (index == 2)
      return false; 
    if (index == 0)
      return stack.func_77973_b() instanceof ItemFusion; 
    return stack.func_77973_b() instanceof ItemManaCollector;
  }
  
  public int[] getSlotsForFace(EnumFacing side) {
    if (side == EnumFacing.UP)
      return new int[] { 0, 1 }; 
    return new int[] { 2 };
  }
  
  public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
    return func_94041_b(index, itemStackIn);
  }
  
  public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
    return true;
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
    return new ContainerMobSpawner(playerInventory, this);
  }
  
  public int func_174887_a_(int id) {
    switch (id) {
      case 0:
        return (int)this.currentItemBurnTime;
      case 1:
        return (int)this.fuseTime;
      case 2:
        return (int)this.totalSpawnMobTime;
    } 
    return 0;
  }
  
  public void func_174885_b(int id, int value) {
    switch (id) {
      case 0:
        this.currentItemBurnTime = value;
        break;
      case 1:
        this.fuseTime = value;
        break;
      case 2:
        this.totalSpawnMobTime = value;
        break;
    } 
  }
  
  public int func_174890_g() {
    return 3;
  }
  
  public SPacketUpdateTileEntity func_189518_D_() {
    return new SPacketUpdateTileEntity(this.field_174879_c, 1, func_189517_E_());
  }
  
  public NBTTagCompound func_189517_E_() {
    return func_189515_b(new NBTTagCompound());
  }
  
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    func_145839_a(packet.func_148857_g());
  }
  
  public double getMana() {
    return this.mana;
  }
  
  public double getEntropy() {
    return this.entropy;
  }
  
  public double getMaxMana() {
    return 5000000.0D;
  }
  
  public double getMaxEntropy() {
    return 500000.0D;
  }
  
  public void func_174888_l() {
    this.fuserItemStacks.clear();
  }
  
  public int func_70297_j_() {
    return 64;
  }
}
