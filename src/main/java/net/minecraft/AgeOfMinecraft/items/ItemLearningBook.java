package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLearningBook extends ItemBEItem {
  public int tier;
  
  public boolean artifact;
  
  public float EXPERIENCE;
  
  public float STRENGTH;
  
  public float STAMINA;
  
  public float INTELEGENCE;
  
  public float DEXTERITY;
  
  public float AGILITY;
  
  public String tooltip;
  
  public ItemLearningBook(int tier, String name, String description, int durability, int experience, float strength, float stamina, float intelegence, float dexterity, float agility) {
    super(name);
    setMaxStackSize(1);
    setMaxDamage(durability);
    this.tier = tier;
    this.artifact = (tier >= 6);
    this.EXPERIENCE = experience;
    this.STRENGTH = strength;
    this.STAMINA = stamina;
    this.INTELEGENCE = intelegence;
    this.DEXTERITY = dexterity;
    this.AGILITY = agility;
    this.tooltip = description;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack) {
    return true;
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return this.artifact ? ESetup.UBEREPIC : super.getRarity(stack);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    TextFormatting tierF, STR, STA, INT, DEX, AGL;
    String tierS, STRS, STAS, INTS, DEXS, AGLS;
    if (this.tier == 0) {
      tierS = "Basic";
      tierF = TextFormatting.RESET;
    } else if (this.tier == 1) {
      tierS = "Modern";
      tierF = TextFormatting.BLUE;
    } else if (this.tier == 2) {
      tierS = "Advanced";
      tierF = TextFormatting.RED;
    } else if (this.tier == 3) {
      tierS = "Complex";
      tierF = TextFormatting.LIGHT_PURPLE;
    } else if (this.tier == 4) {
      tierS = "Expert";
      tierF = TextFormatting.DARK_PURPLE;
    } else if (this.tier == 5) {
      tierS = "Master";
      tierF = TextFormatting.YELLOW;
    } else {
      tierS = "ARTIFACT";
      tierF = TextFormatting.GOLD;
    } 
    tooltip.add("(" + tierF + tierS + TextFormatting.GRAY + ")");
    tooltip.add(TextFormatting.ITALIC + this.tooltip);
    if (this.STRENGTH < 0.0F) {
      STR = TextFormatting.RED;
      STRS = "";
    } else if (this.STRENGTH > 0.0F) {
      STR = TextFormatting.GREEN;
      STRS = "+";
    } else {
      STR = TextFormatting.GOLD;
      STRS = "";
    } 
    if (this.STAMINA < 0.0F) {
      STA = TextFormatting.RED;
      STAS = "";
    } else if (this.STAMINA > 0.0F) {
      STA = TextFormatting.GREEN;
      STAS = "+";
    } else {
      STA = TextFormatting.GOLD;
      STAS = "";
    } 
    if (this.INTELEGENCE < 0.0F) {
      INT = TextFormatting.RED;
      INTS = "";
    } else if (this.INTELEGENCE > 0.0F) {
      INT = TextFormatting.GREEN;
      INTS = "+";
    } else {
      INT = TextFormatting.GOLD;
      INTS = "";
    } 
    if (this.DEXTERITY < 0.0F) {
      DEX = TextFormatting.RED;
      DEXS = "";
    } else if (this.DEXTERITY > 0.0F) {
      DEX = TextFormatting.GREEN;
      DEXS = "+";
    } else {
      DEX = TextFormatting.GOLD;
      DEXS = "";
    } 
    if (this.AGILITY < 0.0F) {
      AGL = TextFormatting.RED;
      AGLS = "";
    } else if (this.AGILITY > 0.0F) {
      AGL = TextFormatting.GREEN;
      AGLS = "+";
    } else {
      AGL = TextFormatting.GOLD;
      AGLS = "";
    } 
    tooltip.add("");
    tooltip.add(STR + "STR: " + STRS + this.STRENGTH);
    tooltip.add(STA + "STA: " + STAS + this.STAMINA);
    tooltip.add(INT + "INT: " + INTS + this.INTELEGENCE);
    tooltip.add(DEX + "DEX: " + DEXS + this.DEXTERITY);
    tooltip.add(AGL + "AGL: " + AGLS + this.AGILITY);
  }
  
  public int getTier() {
    return this.tier;
  }
  
  public void setTier(int tier) {
    this.tier = tier;
  }
  
  public boolean isArtifact() {
    return this.artifact;
  }
}
