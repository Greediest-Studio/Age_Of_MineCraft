package net.minecraft.AgeOfMinecraft.effects;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class PotionBleeding extends PotionEngender {
  public PotionBleeding() {
    super("bleeding", true, 16711680, 1, 0, -0.1D);
  }
  
  public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
    return modifier.getAmount() * (amplifier + 1);
  }
  
  public void performEffect(EntityLivingBase mob, int amplifier) {
  }
  
  public boolean isReady(int par1, int par2) {
    int k = 400 >> par2;
    if (k > 0)
      return (par1 % k == 0); 
    return true;
  }
  
  public List<ItemStack> getCurativeItems() {
    List<ItemStack> list = new ArrayList<>();
    return list;
  }
}
