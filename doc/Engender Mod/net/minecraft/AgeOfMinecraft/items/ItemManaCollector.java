package net.minecraft.AgeOfMinecraft.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityManaOrb;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemManaCollector extends ItemBEItem implements IBauble {
  private final int type;
  
  public ItemManaCollector(String name, int data) {
    super(name);
    this.type = data;
    func_77625_d(1);
    func_77627_a(true);
    if (data != 2)
      func_185043_a(new ResourceLocation("percent"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
              return ItemManaCollector.this.getState(stack);
            }
          }); 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_77636_d(ItemStack stack) {
    return true;
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.TRINKET;
  }
  
  public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    player.func_184185_a(SoundEvents.field_193781_bp, 0.75F, 1.9F);
  }
  
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    player.func_184185_a(SoundEvents.field_193781_bp, 0.75F, 2.0F);
  }
  
  public void onWornTick(ItemStack stack, EntityLivingBase entityIn) {
    World world = entityIn.field_70170_p;
    if (!world.field_72995_K && entityIn instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer)entityIn;
      if (!player.func_175149_v()) {
        List<Entity> list = world.func_72839_b((Entity)player, player.func_174813_aQ().func_186662_g(24.0D));
        if (list != null && !list.isEmpty())
          for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity instanceof EntityManaOrb) {
              EntityManaOrb orb = (EntityManaOrb)entity;
              if (this.type == 2) {
                orb.magnet = stack;
                orb.closestPlayer = player;
              } else if (orb.getEntropy()) {
                if (getEntropy(stack) < getMaxEntropy(stack)) {
                  orb.magnet = stack;
                  orb.closestPlayer = player;
                } 
              } else if (getMana(stack) < getMaxMana(stack)) {
                orb.magnet = stack;
                orb.closestPlayer = player;
              } 
              if (player.func_70032_d((Entity)orb) <= 2.0D)
                if (orb.getMana() > 0) {
                  if (orb.getEntropy() && getEntropy(stack) < getMaxEntropy(stack)) {
                    orb.field_70170_p.func_184133_a(null, entityIn.func_180425_c(), SoundEvents.field_193781_bp, entityIn.func_184176_by(), 2.0F, 2.0F);
                    if (orb.getMana() > getMaxEntropy(stack) - getEntropy(stack)) {
                      int oldamount = orb.getMana();
                      orb.setMana(oldamount - getMaxEntropy(stack) - getEntropy(stack));
                      increaseHolding(oldamount, stack, true);
                      orb.magnet = ItemStack.field_190927_a;
                      orb.closestPlayer = null;
                    } else {
                      increaseHolding(orb.getMana(), stack, true);
                      orb.func_70106_y();
                    } 
                  } else if (!orb.getEntropy() && getMana(stack) < getMaxMana(stack)) {
                    orb.field_70170_p.func_184133_a(null, entityIn.func_180425_c(), SoundEvents.field_187542_ac, entityIn.func_184176_by(), 2.0F, 2.0F);
                    if (orb.getMana() > getMaxMana(stack) - getMana(stack)) {
                      int oldamount = orb.getMana();
                      orb.setMana(oldamount - getMaxMana(stack) - getMana(stack));
                      increaseHolding(oldamount, stack, false);
                      orb.magnet = ItemStack.field_190927_a;
                      orb.closestPlayer = null;
                    } else {
                      increaseHolding(orb.getMana(), stack, false);
                      orb.func_70106_y();
                    } 
                  } 
                } else {
                  orb.func_70106_y();
                }  
            } 
          }  
      } 
    } 
  }
  
  public void func_77663_a(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
    if (entityIn instanceof EntityLivingBase)
      onWornTick(stack, (EntityLivingBase)entityIn); 
  }
  
  public float getState(ItemStack stack) {
    float currentHolding = (this.type == 1) ? getEntropy(stack) : getMana(stack);
    float maxHolding = (this.type == 1) ? getMaxEntropy(stack) : getMaxMana(stack);
    if (currentHolding >= maxHolding * 0.75F)
      return 1.0F; 
    if (currentHolding >= maxHolding * 0.5F)
      return 0.75F; 
    if (currentHolding >= maxHolding * 0.25F)
      return 0.5F; 
    if (currentHolding >= 1.0F)
      return 0.25F; 
    return 0.0F;
  }
  
  public void increaseHolding(int amount, ItemStack stack, boolean isEntropy) {
    if (isEntropy) {
      setEntropy((amount < 0) ? (getEntropy(stack) - amount) : (getEntropy(stack) + amount), stack);
    } else {
      setMana((amount < 0) ? (getMana(stack) - amount) : (getMana(stack) + amount), stack);
    } 
  }
  
  public void setMana(int amount, ItemStack stack) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    stack.func_77978_p().func_74768_a("mana", (amount > getMaxMana(stack)) ? getMaxMana(stack) : amount);
  }
  
  public void setEntropy(int amount, ItemStack stack) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    stack.func_77978_p().func_74768_a("entropy", (amount > getMaxEntropy(stack)) ? getMaxEntropy(stack) : amount);
  }
  
  public int getMana(ItemStack stack) {
    return (this.type == 1) ? 0 : ((stack.func_77942_o() && stack.func_77978_p().func_74764_b("mana")) ? stack.func_77978_p().func_74762_e("mana") : 0);
  }
  
  public int getEntropy(ItemStack stack) {
    return (this.type == 0) ? 0 : ((stack.func_77942_o() && stack.func_77978_p().func_74764_b("entropy")) ? stack.func_77978_p().func_74762_e("entropy") : 0);
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    if (!stack.func_77942_o()) {
      stack.func_77982_d(new NBTTagCompound());
      stack.func_77978_p().func_74768_a("mana", 0);
      stack.func_77978_p().func_74768_a("entropy", 0);
    } 
    return (this.type == 2) ? ESetup.UBEREPIC : EnumRarity.EPIC;
  }
  
  public int getMaxMana(ItemStack stack) {
    return (this.type == 2) ? Integer.MAX_VALUE : ((this.type == 0) ? getManaCrystalMana(stack) : 0);
  }
  
  public int getMaxEntropy(ItemStack stack) {
    return (this.type == 2) ? Integer.MAX_VALUE : ((this.type == 1) ? getEntropyCrystalMana(stack) : 0);
  }
  
  public int getManaCrystalMana(ItemStack stack) {
    switch (getMetadata(stack)) {
      case 0:
        return 10000;
      case 1:
        return 20000;
      case 2:
        return 40000;
      case 3:
        return 80000;
      case 4:
        return 160000;
      case 5:
        return 320000;
      case 6:
        return 640000;
      case 7:
        return 1280000;
      case 8:
        return 2560000;
    } 
    return 5120000;
  }
  
  public int getEntropyCrystalMana(ItemStack stack) {
    switch (getMetadata(stack)) {
      case 0:
        return 500;
      case 1:
        return 1000;
      case 2:
        return 2000;
      case 3:
        return 4000;
      case 4:
        return 8000;
      case 5:
        return 16000;
      case 6:
        return 32000;
      case 7:
        return 64000;
      case 8:
        return 128000;
    } 
    return 256000;
  }
  
  public void func_77624_a(ItemStack stack, World player, List<String> l, ITooltipFlag B) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    switch (this.type) {
      case 0:
        l.add("Allows you to collect Mana, then funnel it into the Fusion Crafter to make mobs.");
        if (getMana(stack) > 0)
          l.add(TextFormatting.AQUA + "Mana Count : " + getMana(stack) + "/" + getMaxMana(stack)); 
        break;
      case 1:
        l.add("Allows you to collect Entropy, then funnel it into the Fusion Crafter to make mobs.");
        if (getEntropy(stack) > 0)
          l.add(TextFormatting.DARK_RED + "Entropy Count : " + getEntropy(stack) + "/" + getMaxEntropy(stack)); 
        break;
      case 2:
        l.add("(" + TextFormatting.GOLD + "ARTIFACT" + TextFormatting.GRAY + ")");
        l.add("Allows you to collect INFINITE Mana and Entropy, then funnel it into the Fusion Crafter to make mobs.");
        if (getMana(stack) > 0)
          l.add(TextFormatting.AQUA + "Mana Count : " + getMana(stack) + "/Infinite"); 
        if (getEntropy(stack) > 0)
          l.add(TextFormatting.DARK_RED + "Entropy Count : " + getEntropy(stack) + "/Infinite"); 
        break;
    } 
  }
  
  public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
    ItemStack stack = new ItemStack(this, 1, (this.type == 2) ? 0 : 9);
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (this.type != 1)
      ((ItemManaCollector)stack.func_77973_b()).setMana(((ItemManaCollector)stack.func_77973_b()).getMaxMana(stack), stack); 
    if (this.type != 0)
      ((ItemManaCollector)stack.func_77973_b()).setEntropy(((ItemManaCollector)stack.func_77973_b()).getMaxEntropy(stack), stack); 
    if (func_194125_a(tab)) {
      items.add(new ItemStack(this, 1, (this.type == 2) ? 0 : 9));
      items.add(stack);
    } 
  }
}
