package net.minecraft.AgeOfMinecraft.gui;

import net.minecraft.AgeOfMinecraft.blocks.ContainerMobSpawner;
import net.minecraft.AgeOfMinecraft.blocks.TileEntityMonsterSpawnerSPC;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEngenderFusionCrafter extends GuiContainer {
  private static final ResourceLocation BREWING_STAND_GUI_TEXTURES = new ResourceLocation("ageofminecraft", "textures/fusion_crafter.png");
  
  private final InventoryPlayer playerInventory;
  
  private final IInventory tileBrewingStand;
  
  public GuiEngenderFusionCrafter(InventoryPlayer playerInv, IInventory p_i45506_2_) {
    super((Container)new ContainerMobSpawner(playerInv, p_i45506_2_));
    this.playerInventory = playerInv;
    this.tileBrewingStand = p_i45506_2_;
  }
  
  public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
    func_146276_q_();
    super.func_73863_a(mouseX, mouseY, partialTicks);
    func_191948_b(mouseX, mouseY);
  }
  
  protected void func_146979_b(int mouseX, int mouseY) {
    String s = this.tileBrewingStand.func_145748_c_().func_150260_c();
    this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 3, 4210752);
    this.field_146289_q.func_78276_b(this.playerInventory.func_145748_c_().func_150260_c(), 8, this.field_147000_g - 96 + 2, 4210752);
  }
  
  protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.field_146297_k.func_110434_K().func_110577_a(BREWING_STAND_GUI_TEXTURES);
    int i = (this.field_146294_l - this.field_146999_f) / 2;
    int j = (this.field_146295_m - this.field_147000_g) / 2;
    func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
    float i1 = ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).fuseTime / ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).totalSpawnMobTime;
    int progress = (int)(i1 * 24.0F);
    if (progress > 0)
      func_73729_b(i + 85, j + 34, 177, 0, 4, progress); 
    double i2 = ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMana() / ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMaxMana();
    int mana = (int)(i2 * 80.0D);
    if (mana > 0)
      func_73729_b(i + 47, j + 10, 0, 166, mana, 5); 
    double i3 = ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getEntropy() / ((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMaxEntropy();
    int entropy = (int)(i3 * 28.0D);
    if (entropy > 0)
      func_73729_b(i + 47, j + 21, 0, 171, entropy, 5); 
    GlStateManager.func_179094_E();
    GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
    i = this.field_146294_l - this.field_146999_f;
    j = this.field_146295_m - this.field_147000_g;
    func_146279_a(TextFormatting.AQUA + "Mana: " + (int)((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMana() + "/" + (int)((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMaxMana(), i + 340, j + 48);
    func_146279_a(TextFormatting.DARK_RED + "Entropy: " + (int)((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getEntropy() + "/" + (int)((TileEntityMonsterSpawnerSPC)this.tileBrewingStand).getMaxEntropy(), i + 156, j + 72);
    GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
    GlStateManager.func_179121_F();
  }
}
