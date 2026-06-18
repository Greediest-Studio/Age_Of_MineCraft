package net.minecraft.AgeOfMinecraft.effects;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class PotionBleeding extends PotionEngender {
  public PotionBleeding() {
    super("bleeding", true, 16711680, 1, 0, -0.1D);
  }
  
  public double func_111183_a(int amplifier, AttributeModifier modifier) {
    return modifier.func_111164_d() * (amplifier + 1);
  }
  
  public void func_76394_a(EntityLivingBase mob, int amplifier) {
    if (!EngenderMod.doesntHaveTimeToBleed((Entity)mob)) {
      mob.func_70097_a((new DamageSource("bleed")).func_76348_h(), 1.0F);
      if (mob instanceof EntityPlayer && EngenderConfig.loreFriendlyMode)
        ((EntityPlayer)mob).func_71020_j(0.05F * (amplifier + 1)); 
      if (mob instanceof EntityTameBase && EngenderConfig.loreFriendlyMode)
        ((EntityTameBase)mob).setEnergy(((EntityTameBase)mob).getEnergy() - 0.01F * (amplifier + 1)); 
    } 
  }
  
  public boolean func_76397_a(int par1, int par2) {
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
