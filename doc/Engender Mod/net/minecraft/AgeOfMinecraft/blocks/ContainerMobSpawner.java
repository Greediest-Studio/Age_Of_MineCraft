package net.minecraft.AgeOfMinecraft.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMobSpawner extends Container {
  private final IInventory tileFuser;
  
  private int currentItemBurnTime;
  
  private int fuseTime;
  
  private int totalSpawnMobTime;
  
  public int mana;
  
  public int entropy;
  
  public ContainerMobSpawner(InventoryPlayer playerInventory, IInventory tileBrewingStandIn) {
    this.tileFuser = tileBrewingStandIn;
    func_75146_a(new Ingredient(tileBrewingStandIn, 0, 79, 17));
    func_75146_a(new Fuel(tileBrewingStandIn, 1, 27, 4));
    func_75146_a(new Result(tileBrewingStandIn, 2, 79, 58));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++)
        func_75146_a(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)); 
    } 
    for (int k = 0; k < 9; k++)
      func_75146_a(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142)); 
  }
  
  public void func_75132_a(IContainerListener listener) {
    super.func_75132_a(listener);
    listener.func_175173_a(this, this.tileFuser);
  }
  
  public void func_75142_b() {
    super.func_75142_b();
    for (int i = 0; i < this.field_75149_d.size(); i++) {
      IContainerListener icontainerlistener = this.field_75149_d.get(i);
      if (this.currentItemBurnTime != this.tileFuser.func_174887_a_(0))
        icontainerlistener.func_71112_a(this, 0, this.tileFuser.func_174887_a_(0)); 
      if (this.fuseTime != this.tileFuser.func_174887_a_(1))
        icontainerlistener.func_71112_a(this, 1, this.tileFuser.func_174887_a_(1)); 
      if (this.totalSpawnMobTime != this.tileFuser.func_174887_a_(2))
        icontainerlistener.func_71112_a(this, 2, this.tileFuser.func_174887_a_(2)); 
    } 
    this.currentItemBurnTime = this.tileFuser.func_174887_a_(0);
    this.fuseTime = this.tileFuser.func_174887_a_(1);
    this.totalSpawnMobTime = this.tileFuser.func_174887_a_(2);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_75137_b(int id, int data) {
    this.tileFuser.func_174885_b(id, data);
  }
  
  public boolean func_75145_c(EntityPlayer playerIn) {
    return this.tileFuser.func_70300_a(playerIn);
  }
  
  public ItemStack func_82846_b(EntityPlayer playerIn, int index) {
    ItemStack itemstack = ItemStack.field_190927_a;
    Slot slot = this.field_75151_b.get(index);
    if (slot != null && slot.func_75216_d()) {
      ItemStack itemstack1 = slot.func_75211_c();
      itemstack = itemstack1.func_77946_l();
      if (index == 2) {
        if (!func_75135_a(itemstack1, 3, 39, true))
          return ItemStack.field_190927_a; 
        slot.func_75220_a(itemstack1, itemstack);
      } else if (index != 1 && index != 0) {
        if (itemstack1.func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemFusion) {
          if (!func_75135_a(itemstack1, 0, 1, false))
            return ItemStack.field_190927_a; 
        } else if (itemstack1.func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemManaCollector) {
          if (!func_75135_a(itemstack1, 1, 2, false))
            return ItemStack.field_190927_a; 
        } else if (index >= 3 && index < 30) {
          if (!func_75135_a(itemstack1, 30, 39, false))
            return ItemStack.field_190927_a; 
        } else if (index >= 30 && index < 39 && !func_75135_a(itemstack1, 3, 30, false)) {
          return ItemStack.field_190927_a;
        } 
      } else if (!func_75135_a(itemstack1, 3, 39, false)) {
        return ItemStack.field_190927_a;
      } 
      if (itemstack1.func_190926_b()) {
        slot.func_75215_d(ItemStack.field_190927_a);
      } else {
        slot.func_75218_e();
      } 
      if (itemstack1.func_190916_E() == itemstack.func_190916_E())
        return ItemStack.field_190927_a; 
      slot.func_190901_a(playerIn, itemstack1);
    } 
    return itemstack;
  }
  
  static class Fuel extends Slot {
    public Fuel(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
      super(iInventoryIn, index, xPosition, yPosition);
    }
    
    public boolean func_75214_a(ItemStack stack) {
      return canHoldItem(stack);
    }
    
    public static boolean canHoldItem(ItemStack stack) {
      return stack.func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemManaCollector;
    }
    
    public int func_75219_a() {
      return 1;
    }
  }
  
  static class Ingredient extends Slot {
    public Ingredient(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
      super(iInventoryIn, index, xPosition, yPosition);
    }
    
    public boolean func_75214_a(ItemStack stack) {
      return canHoldItem(stack);
    }
    
    public static boolean canHoldItem(ItemStack stack) {
      return stack.func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemFusion;
    }
    
    public int func_75219_a() {
      return 64;
    }
  }
  
  static class Result extends Slot {
    public Result(IInventory p_i47598_1_, int p_i47598_2_, int p_i47598_3_, int p_i47598_4_) {
      super(p_i47598_1_, p_i47598_2_, p_i47598_3_, p_i47598_4_);
    }
    
    public int func_75219_a() {
      return 64;
    }
    
    public ItemStack func_190901_a(EntityPlayer thePlayer, ItemStack stack) {
      if (thePlayer instanceof EntityPlayerMP)
        CriteriaTriggers.field_192125_e.func_192208_a((EntityPlayerMP)thePlayer, ((EntityPlayerMP)thePlayer).field_71071_by); 
      super.func_190901_a(thePlayer, stack);
      return stack;
    }
    
    public boolean func_75214_a(ItemStack stack) {
      return canHoldItem(stack);
    }
    
    public static boolean canHoldItem(ItemStack stack) {
      return stack.func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemTierItem;
    }
  }
}
