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
  private NonNullList<ItemStack> fuserItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
  
  private float currentItemBurnTime;
  
  public float fuseTime;
  
  public float totalSpawnMobTime;
  
  public double mana;
  
  public double entropy;
  
  private String fuserCustomName;
  
  public int getSizeInventory() {
    return 3;
  }
  
  public boolean isEmpty() {
    for (ItemStack itemstack : this.fuserItemStacks) {
      if (!itemstack.isEmpty())
        return false; 
    } 
    return true;
  }
  
  public ItemStack getStackInSlot(int index) {
    return (ItemStack)this.fuserItemStacks.get(index);
  }
  
  public ItemStack decrStackSize(int index, int count) {
    return ItemStackHelper.getAndSplit((List)this.fuserItemStacks, index, count);
  }
  
  public ItemStack removeStackFromSlot(int index) {
    return ItemStackHelper.getAndRemove((List)this.fuserItemStacks, index);
  }
  
  public void setInventorySlotContents(int index, ItemStack stack) {
    ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(index);
    boolean flag = (!stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack));
    this.fuserItemStacks.set(index, stack);
    if (stack.getCount() > getInventoryStackLimit())
      stack.setCount(getInventoryStackLimit()); 
    if (index == 0 && !flag) {
      this.totalSpawnMobTime = timeToSpawnmob(stack);
      this.fuseTime = 0.0F;
      markDirty();
    } 
  }
  
  public String getName() {
    return hasCustomName() ? this.fuserCustomName : "Fusion Crafter";
  }
  
  public ITextComponent getDisplayName() {
    return new TextComponentString(getName());
  }
  
  public boolean hasCustomName() {
    return (this.fuserCustomName != null && !this.fuserCustomName.isEmpty());
  }
  
  public void setCustomInventoryName(String p_145951_1_) {
    this.fuserCustomName = p_145951_1_;
  }
  
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    this.fuserItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
    ItemStackHelper.loadAllItems(compound, this.fuserItemStacks);
    this.fuseTime = compound.getShort("CookTime");
    this.totalSpawnMobTime = compound.getShort("CookTimeTotal");
    this.mana = compound.getDouble("Mana");
    this.entropy = compound.getDouble("Entropy");
    this.currentItemBurnTime = getItemBurnTime((ItemStack)this.fuserItemStacks.get(1));
    if (compound.hasKey("CustomName", 8))
      this.fuserCustomName = compound.getString("CustomName"); 
  }
  
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    compound.setShort("CookTime", (short)(int)this.fuseTime);
    compound.setShort("CookTimeTotal", (short)(int)this.totalSpawnMobTime);
    compound.setDouble("Mana", getMana());
    compound.setDouble("Entropy", getEntropy());
    ItemStackHelper.saveAllItems(compound, this.fuserItemStacks);
    if (hasCustomName())
      compound.setString("CustomName", this.fuserCustomName); 
    return compound;
  }
  
  @SideOnly(Side.CLIENT)
  public static boolean isBurning(IInventory p_174903_0_) {
    return (p_174903_0_.getField(0) > 0);
  }
  
  public void update() {
    Random rand = (getWorld()).rand;
    double d3 = (this.pos.getX() + rand.nextFloat());
    double d4 = (this.pos.getY() + rand.nextFloat());
    double d5 = (this.pos.getZ() + rand.nextFloat());
    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D);
    if (this.fuseTime > 0.0F)
      this.world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, 0.0D, 0.0D, 0.0D);
    if (rand.nextInt(500) == 0)
      switch (rand.nextInt(40)) {
        case 0:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 1:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 2:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_COW_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 3:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_CAT_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 4:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 5:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_RABBIT_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 6:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 7:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ENDERMITE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 8:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SILVERFISH_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 9:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SNOWMAN_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 10:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_LLAMA_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 11:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SQUID_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 12:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 13:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 14:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 15:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 16:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_POLAR_BEAR_WARNING, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 17:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SKELETON_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 18:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 19:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_STRAY_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 20:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 21:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SPIDER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 22:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ZOMBIE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 23:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 24:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_HUSK_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 25:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_VEX_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 26:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.VINDICATION_ILLAGER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 27:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 28:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ENDERMEN_SCREAM, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 29:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_GHAST_HURT, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 30:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_GUARDIAN_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 31:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
          break;
        case 32:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_SHULKER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 33:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 34:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
        case 35:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ZOMBIE_AMBIENT, SoundCategory.BLOCKS, 5.0F, 0.5F, false);
          break;
        case 36:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 37:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
          break;
        case 38:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
          break;
        case 39:
          this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), ESound.witherStormRoar, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
          break;
      }  
    boolean flag1 = false;
    if (this.mana > getMaxMana())
      this.mana = getMaxMana(); 
    if (this.entropy > getMaxEntropy())
      this.entropy = getMaxEntropy(); 
    ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(1);
    if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemManaCollector && ((ItemManaCollector)itemstack.getItem()).getMana(itemstack) > 0 && getMana() < getMaxMana()) {
      if (((ItemManaCollector)itemstack.getItem()).getMana(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.getItem()).setMana(((ItemManaCollector)itemstack.getItem()).getMana(itemstack) - 500, itemstack);
        this.mana += 500.0D;
      } else if (((ItemManaCollector)itemstack.getItem()).getMana(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.getItem()).setMana(((ItemManaCollector)itemstack.getItem()).getMana(itemstack) - 50, itemstack);
        this.mana += 50.0D;
      } else {
        ((ItemManaCollector)itemstack.getItem()).setMana(((ItemManaCollector)itemstack.getItem()).getMana(itemstack) - 1, itemstack);
        this.mana++;
      } 
      if (((ItemManaCollector)itemstack.getItem()).getMana(itemstack) == 1)
        this.world.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.5F); 
    } 
    if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemManaCollector && ((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) > 0 && getEntropy() < getMaxEntropy()) {
      if (((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) >= 500) {
        ((ItemManaCollector)itemstack.getItem()).setEntropy(((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) - 500, itemstack);
        this.entropy += 500.0D;
      } else if (((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) >= 50) {
        ((ItemManaCollector)itemstack.getItem()).setEntropy(((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) - 50, itemstack);
        this.entropy += 50.0D;
      } else {
        ((ItemManaCollector)itemstack.getItem()).setEntropy(((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) - 1, itemstack);
        this.entropy++;
      } 
      if (((ItemManaCollector)itemstack.getItem()).getEntropy(itemstack) == 1)
        this.world.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 0.1F, 1.0F); 
    } 
    if (!((ItemStack)this.fuserItemStacks.get(0)).isEmpty()) {
      if (canSmelt() && this.mana >= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getManaCost() && this.entropy >= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getEntropyCost()) {
        this.totalSpawnMobTime = timeToSpawnmob((ItemStack)this.fuserItemStacks.get(0));
        this.currentItemBurnTime = this.fuseTime;
        this.fuseTime++;
        if (this.fuseTime >= this.totalSpawnMobTime) {
          this.fuseTime = 0.0F;
          this.world.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1.0F, 2.0F);
          this.mana -= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getManaCost();
          this.entropy -= ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getEntropyCost();
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
      markDirty(); 
  }
  
  public int timeToSpawnmob(ItemStack p_174904_1_) {
    if (p_174904_1_.getItem() instanceof ItemFusion)
      return ((ItemFusion)p_174904_1_.getItem()).getItemToFuse().getTimeToSpawnMob() * fuseTimeMultiplyer(); 
    return 0;
  }
  
  public int fuseTimeMultiplyer() {
    return 20;
  }
  
  private boolean canSmelt() {
    if (((ItemStack)this.fuserItemStacks.get(0)).isEmpty())
      return false; 
    ItemStack itemstack = MobSpawnerRecipes.instance().getSmeltingResult((ItemStack)this.fuserItemStacks.get(0));
    if (itemstack.isEmpty())
      return false; 
    ItemStack itemstack1 = (ItemStack)this.fuserItemStacks.get(2);
    if (itemstack1.isEmpty())
      return true; 
    if (!itemstack1.isItemEqual(itemstack))
      return false; 
    if (this.mana < ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getManaCost())
      return false; 
    if (this.entropy < ((ItemFusion)((ItemStack)this.fuserItemStacks.get(0)).getItem()).getEntropyCost())
      return false; 
    int result = itemstack1.getCount() + itemstack.getCount();
    return (result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize());
  }
  
  public void smeltItem() {
    if (canSmelt()) {
      ItemStack itemstack = (ItemStack)this.fuserItemStacks.get(0);
      ItemStack itemstack1 = MobSpawnerRecipes.instance().getSmeltingResult(itemstack);
      ItemStack itemstack2 = (ItemStack)this.fuserItemStacks.get(2);
      if (itemstack2.isEmpty()) {
        this.fuserItemStacks.set(2, itemstack1.copy());
      } else if (itemstack2.getItem() == itemstack1.getItem()) {
        itemstack2.grow(itemstack1.getCount());
      } 
      itemstack.shrink(1);
    } 
  }
  
  public static int getItemBurnTime(ItemStack stack) {
    return stack.isEmpty() ? 0 : 40;
  }
  
  public boolean isUsableByPlayer(EntityPlayer player) {
    if (this.world.getTileEntity(this.pos) != this)
      return false; 
    return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
  }
  
  public void openInventory(EntityPlayer player) {}
  
  public void closeInventory(EntityPlayer player) {}
  
  public boolean isItemValidForSlot(int index, ItemStack stack) {
    if (index == 2)
      return false; 
    if (index == 0)
      return stack.getItem() instanceof ItemFusion; 
    return stack.getItem() instanceof ItemManaCollector;
  }
  
  public int[] getSlotsForFace(EnumFacing side) {
    if (side == EnumFacing.UP)
      return new int[] { 0, 1 }; 
    return new int[] { 2 };
  }
  
  public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
    return isItemValidForSlot(index, itemStackIn);
  }
  
  public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
    return true;
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
    return new ContainerMobSpawner(playerInventory, this);
  }
  
  public int getField(int id) {
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
  
  public void setField(int id, int value) {
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
  
  public int getFieldCount() {
    return 3;
  }
  
  public SPacketUpdateTileEntity getUpdatePacket() {
    return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
  }
  
  public NBTTagCompound getUpdateTag() {
    return writeToNBT(new NBTTagCompound());
  }
  
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    readFromNBT(packet.getNbtCompound());
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
  
  public void clear() {
    this.fuserItemStacks.clear();
  }
  
  public int getInventoryStackLimit() {
    return 64;
  }
}
