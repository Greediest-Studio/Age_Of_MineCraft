package net.minecraft.AgeOfMinecraft.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionEngender extends Potion {
  protected final double bonusPerLevel;
  
  public PotionEngender(String name, boolean isBadPotion, int color, int IconIndexX, int IconIndexY, double bonusPerLevelIn) {
    super(isBadPotion, color);
    func_76390_b("effect." + name);
    func_76399_b(IconIndexX, IconIndexY);
    setRegistryName(new ResourceLocation("ageofminecraft:" + name));
    this.bonusPerLevel = bonusPerLevelIn;
  }
  
  public double func_111183_a(int amplifier, AttributeModifier modifier) {
    return this.bonusPerLevel * (amplifier + 1);
  }
  
  public Potion func_76399_b(int par1, int par2) {
    super.func_76399_b(par1, par2);
    return this;
  }
  
  public boolean func_76400_d() {
    Minecraft.func_71410_x().func_110434_K().func_110577_a(new ResourceLocation("ageofminecraft:textures/effects.png"));
    return true;
  }
}
