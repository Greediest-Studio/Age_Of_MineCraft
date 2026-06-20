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
    ItemCompat.setMaxStackSize(this, 1);
    ItemCompat.setHasSubtypes(this, true);
    if (data != 2)
      addPropertyOverride(new ResourceLocation("percent"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
              return ItemManaCollector.this.getState(stack);
            }
          }); 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack) {
    return true;
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.TRINKET;
  }
  
  public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    player.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 0.75F, 1.9F);
  }
  
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    player.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 0.75F, 2.0F);
  }
  
  public void onWornTick(ItemStack stack, EntityLivingBase entityIn) {
    World world = net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entityIn);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(world) && entityIn instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer)entityIn;
      if (!player.isSpectator()) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(24.0D));
        if (list != null && !list.isEmpty())
            for (Entity entity : list) {
                if (entity instanceof EntityManaOrb) {
                    EntityManaOrb orb = (EntityManaOrb) entity;
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
                    if (player.getDistance(orb) <= 2.0D)
                        if (orb.getMana() > 0) {
                            if (orb.getEntropy() && getEntropy(stack) < getMaxEntropy(stack)) {
                                orb.world.playSound(null, entityIn.getPosition(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, entityIn.getSoundCategory(), 2.0F, 2.0F);
                                if (orb.getMana() > getMaxEntropy(stack) - getEntropy(stack)) {
                                    int oldamount = orb.getMana();
                                    orb.setMana(oldamount - getMaxEntropy(stack) - getEntropy(stack));
                                    increaseHolding(oldamount, stack, true);
                                    orb.magnet = ItemStack.EMPTY;
                                    orb.closestPlayer = null;
                                } else {
                                    increaseHolding(orb.getMana(), stack, true);
                                    orb.setDead();
                                }
                            } else if (!orb.getEntropy() && getMana(stack) < getMaxMana(stack)) {
                                orb.world.playSound(null, entityIn.getPosition(), SoundEvents.BLOCK_CHORUS_FLOWER_GROW, entityIn.getSoundCategory(), 2.0F, 2.0F);
                                if (orb.getMana() > getMaxMana(stack) - getMana(stack)) {
                                    int oldamount = orb.getMana();
                                    orb.setMana(oldamount - getMaxMana(stack) - getMana(stack));
                                    increaseHolding(oldamount, stack, false);
                                    orb.magnet = ItemStack.EMPTY;
                                    orb.closestPlayer = null;
                                } else {
                                    increaseHolding(orb.getMana(), stack, false);
                                    orb.setDead();
                                }
                            }
                        } else {
                            orb.setDead();
                        }
                }
            }
      } 
    } 
  }
  
  public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
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
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    stack.getTagCompound().setInteger("mana", Math.min(amount, getMaxMana(stack)));
  }
  
  public void setEntropy(int amount, ItemStack stack) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    stack.getTagCompound().setInteger("entropy", Math.min(amount, getMaxEntropy(stack)));
  }
  
  public int getMana(ItemStack stack) {
    return (this.type == 1) ? 0 : ((stack.hasTagCompound() && stack.getTagCompound().hasKey("mana")) ? stack.getTagCompound().getInteger("mana") : 0);
  }
  
  public int getEntropy(ItemStack stack) {
    return (this.type == 0) ? 0 : ((stack.hasTagCompound() && stack.getTagCompound().hasKey("entropy")) ? stack.getTagCompound().getInteger("entropy") : 0);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    if (!stack.hasTagCompound()) {
      stack.setTagCompound(new NBTTagCompound());
      stack.getTagCompound().setInteger("mana", 0);
      stack.getTagCompound().setInteger("entropy", 0);
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
  
  public void addInformation(ItemStack stack, World player, List<String> l, ITooltipFlag B) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
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
  
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    ItemStack stack = new ItemStack(this, 1, (this.type == 2) ? 0 : 9);
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    if (this.type != 1)
      ((ItemManaCollector)stack.getItem()).setMana(((ItemManaCollector)stack.getItem()).getMaxMana(stack), stack); 
    if (this.type != 0)
      ((ItemManaCollector)stack.getItem()).setEntropy(((ItemManaCollector)stack.getItem()).getMaxEntropy(stack), stack); 
    if (isInCreativeTab(tab)) {
      items.add(new ItemStack(this, 1, (this.type == 2) ? 0 : 9));
      items.add(stack);
    } 
  }
}
