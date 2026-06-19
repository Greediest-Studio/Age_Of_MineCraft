package net.minecraft.AgeOfMinecraft.gui;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEngenderMobInventory extends GuiContainer {
  public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("ageofminecraft", "textures/engender_inventory_new.png");
  
  private float oldMouseX;
  
  private float oldMouseY;
  
  private final EntityTameBase mob;
  
  private Slot theSlot;
  
  private ItemStack draggedStack = ItemStack.EMPTY;
  
  private Slot clickedSlot;
  
  private boolean isRightMouseClick;
  
  private int dragSplittingLimit;
  
  private int dragSplittingRemnant;
  
  public GuiEngenderMobInventory(EntityPlayer player, EntityTameBase entity) {
    super(player.inventoryContainer);
    this.allowUserInput = true;
    this.mob = entity;
  }
  
  public void initGui() {
    this.buttonList.clear();
    super.initGui();
  }
  
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    int tier = (this.mob.getTier() == EnumTier.TIER6) ? 6 : ((this.mob.getTier() == EnumTier.TIER5) ? 5 : ((this.mob.getTier() == EnumTier.TIER4) ? 4 : ((this.mob.getTier() == EnumTier.TIER3) ? 3 : ((this.mob.getTier() == EnumTier.TIER2) ? 2 : 1))));
    this.fontRenderer.drawString(this.mob.getName() + " (Tier " + tier + ")", 0, -10, 16777215);
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans) {
      this.fontRenderer.drawString("EXP: worth it all", 70, 100, 4210752);
      this.fontRenderer.drawString("total EXP: ?", 70, 110, 4210752);
      this.fontRenderer.drawString("LV 1", 80, 82, 4210752);
      this.fontRenderer.drawString("str (1)", 8, 80, 4210752);
      this.fontRenderer.drawString("sta (lazy)", 8, 90, 4210752);
      this.fontRenderer.drawString("int (?)", 8, 100, 4210752);
      this.fontRenderer.drawString("dex (nah)", 8, 110, 4210752);
      this.fontRenderer.drawString("agi (zzz)", 8, 120, 4210752);
    } else {
      this.fontRenderer.drawString("EXP: " + (int)this.mob.getEXP() + "/" + (this.mob.getNextLevelRequirement() * this.mob.getLevel()), 70, 100, 4210752);
      this.fontRenderer.drawString("Total EXP: " + ((this.mob.getTotalEXP() >= 2.1474836E9F) ? "N/A" : Integer.toString((int)this.mob.getTotalEXP())), 70, 110, 4210752);
      this.fontRenderer.drawString("Level " + this.mob.getLevel(), 80, 82, 4210752);
      this.fontRenderer.drawString("STR (" + TextFormatting.RED + (int)this.mob.getStrength() + TextFormatting.RESET + ")", 8, 80, 4210752);
      this.fontRenderer.drawString("STA (" + TextFormatting.DARK_GREEN + (int)this.mob.getStamina() + TextFormatting.RESET + ")", 8, 90, 4210752);
      this.fontRenderer.drawString("INT (" + TextFormatting.BLUE + (int)this.mob.getIntelligence() + TextFormatting.RESET + ")", 8, 100, 4210752);
      this.fontRenderer.drawString("DEX (" + TextFormatting.LIGHT_PURPLE + (int)this.mob.getDexterity() + TextFormatting.RESET + ")", 8, 110, 4210752);
      this.fontRenderer.drawString("AGI (" + TextFormatting.DARK_AQUA + (int)this.mob.getAgility() + TextFormatting.RESET + ")", 8, 120, 4210752);
    } 
    TextFormatting render = this.mob.getTextFormat();
    GlStateManager.pushMatrix();
    GlStateManager.scale(0.75F, 0.75F, 1.0F);
    this.fontRenderer.drawString(this.mob.isWild() ? (TextFormatting.LIGHT_PURPLE + "Mother Nature") : (TextFormatting.GOLD + "Clan Leader " + this.mob.getOwner().getName()), 0, -25, 16777215);
    this.fontRenderer.drawString("HP " + (int)this.mob.getHealth() + "/" + (int)this.mob.getMaxHealth(), 110, 11, 0);
    this.fontRenderer.drawString("EN " + (int)this.mob.getEnergy() + "/" + 'd', 110, 33, 0);
    this.fontRenderer.drawString("Armor " + this.mob.getTotalArmorValue() + "/" + MathHelper.floor(this.mob.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()), 110, 56, 0);
    GlStateManager.scale(1.0F, 1.0F, 1.0F);
    GlStateManager.scale(0.675F, 0.675F, 1.0F);
    this.fontRenderer.drawString(render + I18n.translateToLocal("descriptionline1." + this.mob.getDescName()), 8, 290, 4210752);
    this.fontRenderer.drawString(render + I18n.translateToLocal("descriptionline2." + this.mob.getDescName()), 8, 300, 4210752);
    GlStateManager.scale(1.0F, 1.0F, 1.0F);
    GlStateManager.popMatrix();
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.oldMouseX = mouseX;
    this.oldMouseY = mouseY;
    drawDefaultBackground();
    int i = this.guiLeft;
    int j = this.guiTop;
    drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    GlStateManager.pushMatrix();
    GlStateManager.translate(i, j, 0.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      for (GuiButton guiButton : this.buttonList)
          ((GuiButton) guiButton).drawButton(this.mc, mouseX, mouseY, partialTicks);
      for (GuiLabel guiLabel : this.labelList) ((GuiLabel) guiLabel).drawLabel(this.mc, mouseX, mouseY);
    drawGuiContainerForegroundLayer(mouseX, mouseY);
    for (int i1 = 0; i1 < this.mob.basicInventory.getSizeInventory(); i1++) {
      Slot slot = new Slot((IInventory)this.mob.basicInventory, i1, 8, 8);
      if (i1 == 1)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 8, 26); 
      if (i1 == 2)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 8, 44); 
      if (i1 == 3)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 8, 62); 
      if (i1 == 4)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 77, 62); 
      if (i1 == 5)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 95, 62); 
      if (i1 == 6)
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 131, 62); 
      if (i1 == 7 && !this.mob.basicInventory.getStackInSlot(7).isEmpty())
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 113, 62); 
      if (slot.isEnabled()) {
        drawSlot(slot, i1);
        if (isMouseOverSlot(slot, mouseX, mouseY)) {
          this.theSlot = slot;
          GlStateManager.disableLighting();
          GlStateManager.disableDepth();
          int m = slot.xPos;
          int k1 = slot.yPos;
          GlStateManager.colorMask(true, true, true, false);
          drawGradientRect(m, k1, m + 16, k1 + 16, -2130706433, -2130706433);
          GlStateManager.colorMask(true, true, true, true);
          GlStateManager.enableLighting();
          GlStateManager.enableDepth();
        } 
      } 
    } 
    GlStateManager.popMatrix();
    if (this.theSlot != null && this.theSlot.getHasStack() && isMouseOverSlot(this.theSlot, mouseX, mouseY)) {
      ItemStack itemstack1 = this.theSlot.getStack();
      renderToolTip(itemstack1, mouseX, mouseY);
    } 
  }
  
  private void drawSlot(Slot slotIn, int index) {
    int i = slotIn.xPos;
    int j = slotIn.yPos;
    ItemStack itemstack = slotIn.getStack();
    boolean flag = false;
    boolean flag1 = (slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick);
    ItemStack itemstack1 = this.mob.basicInventory.getStackInSlot(index);
    String s = null;
    if (slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty()) {
      itemstack = itemstack.copy();
      itemstack.setCount(itemstack.getCount() / 2);
    } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && !itemstack1.isEmpty()) {
      if (this.dragSplittingSlots.size() == 1)
        return; 
      if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
        itemstack = itemstack1.copy();
        flag = true;
        Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack().isEmpty() ? 0 : slotIn.getStack().getCount());
        int k = Math.min(itemstack.getMaxStackSize(), slotIn.getItemStackLimit(itemstack));
        if (itemstack.getCount() > k) {
          s = TextFormatting.YELLOW.toString() + k;
          itemstack.setCount(k);
        } 
      } else {
        this.dragSplittingSlots.remove(slotIn);
        updateDragSplitting();
      } 
    } 
    this.zLevel = 100.0F;
    this.itemRender.zLevel = 100.0F;
    if (itemstack.isEmpty() && slotIn.isEnabled()) {
      TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();
      if (textureatlassprite != null) {
        GlStateManager.disableLighting();
        this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
        drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
        GlStateManager.enableLighting();
        flag1 = true;
      } 
    } 
    if (!flag1) {
      if (flag)
        drawRect(i, j, i + 16, j + 16, -2130706433); 
      GlStateManager.enableDepth();
      this.itemRender.renderItemAndEffectIntoGUI((EntityLivingBase)this.mc.player, itemstack, i, j);
      this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, i, j, s);
    } 
    this.itemRender.zLevel = 0.0F;
    this.zLevel = 0.0F;
  }
  
  public boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
    return isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
  }
  
  public void updateDragSplitting() {
    ItemStack itemstack = this.mc.player.inventory.getItemStack();
    if (!itemstack.isEmpty() && this.dragSplitting)
      if (this.dragSplittingLimit == 2) {
        this.dragSplittingRemnant = itemstack.getMaxStackSize();
      } else {
        this.dragSplittingRemnant = itemstack.getCount();
        for (Slot slot : this.dragSplittingSlots) {
          ItemStack itemstack1 = itemstack.copy();
          ItemStack itemstack2 = slot.getStack();
          int i = itemstack2.isEmpty() ? 0 : itemstack2.getCount();
          Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
          int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
          if (itemstack1.getCount() > j)
            itemstack1.setCount(j); 
          this.dragSplittingRemnant -= itemstack1.getCount() - i;
        } 
      }  
  }
  
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
    int i = this.guiLeft;
    int j = this.guiTop;
    drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    if (this.mob.basicInventory.getStackInSlot(5).isEmpty())
      drawTexturedModalRect(i + 95, j + 62, 80, 201, 16, 16); 
    if (this.mob.basicInventory.getStackInSlot(7).isEmpty())
      drawTexturedModalRect(i + 113, j + 62, 96, 201, 16, 16); 
    if (this.mob.basicInventory.getStackInSlot(6).isEmpty())
      drawTexturedModalRect(i + 131, j + 62, 112, 201, 16, 16); 
    if (this.mob.getLevel() > 299)
      drawTypeBox(1, 0, 16, true); 
    if (this.mob.isABoss())
      drawTypeBox(1, 1, 19, true); 
    if (this.mob.isHero())
      drawTypeBox(1, 2, 17, true); 
    if (this.mob.hasLastChance())
      drawTypeBox(1, 3, 14, true); 
    if (EngenderConfig.mobs.useMobTalkerModels && this.mob.canBeMarried() && this.mob.isMarried())
      drawTypeBox(1, 4, 15, true); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal)
      drawTypeBox(0, 0, 7, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Armored)
      drawTypeBox(0, 1, 0, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Elemental)
      drawTypeBox(0, 2, 3, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Ender)
      drawTypeBox(0, 3, 4, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Massive)
      drawTypeBox(0, 4, 28, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Structure)
      drawTypeBox(0, 5, 18, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Tiny)
      drawTypeBox(0, 6, 6, false); 
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.Undead)
      drawTypeBox(0, 7, 1, false); 
    int health = (int)(this.mob.getHealthPercent() * 94.0F);
    if (health > 0)
      drawTexturedModalRect(i + 77, j + 15, 0, 166, health, 5); 
    int energy = (int)(this.mob.getEnergyPercent() * 94.0F);
    if (energy > 0)
      drawTexturedModalRect(i + 77, j + 31, 0, 171, energy, 5); 
    int exp = (int)(this.mob.getEXPPercent() * 101.0F);
    if (exp > 0)
      drawTexturedModalRect(i + 70, j + 92, 0, 176, exp, 5); 
    int armor = (int)(this.mob.getTotalArmorValue() * 2.0F);
    if (armor > 0)
      drawTexturedModalRect(i + 77, j + 48, 95, 166, armor + 1, 5); 
    int armort = (int)(MathHelper.floor(this.mob.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()) * 2.0F);
    if (armort > 0)
      drawTexturedModalRect(i + 77, j + 48, 95, 171, armort + 1, 5); 
    drawEntityOnScreen(i + 50, j + 72, 32, (i + 51) - this.oldMouseX, (j + 75 - 50) - this.oldMouseY, (EntityLivingBase)this.mob);
  }
  
  public void drawTypeBox(int slotX, int slotY, int type, boolean spikeyborder) {
    int typeX, typeY, posX = -20 * (slotX + 1);
    float posY = 20.0F * slotY;
    int typeZ = 0;
    int typeZ2 = 0;
    int typePP = type;
    for (typePP = type; typePP > 7; typePP -= 8) {
      typeZ += 10;
      typeZ2 += 8;
    } 
    if (type == 0) {
      typeX = 0 - typeZ;
      typeY = 181 + typeZ;
    } else if (type == 1) {
      typeX = 10 - typeZ;
      typeY = 181 + typeZ;
    } else {
      typeX = 10 * type - 10 * typeZ2;
      typeY = 181 + typeZ;
    } 
    int i = this.guiLeft;
    int j = this.guiTop;
    drawTexturedModalRect((i + posX), j + posY, spikeyborder ? 100 : 80, 181, 20, 20);
    drawTexturedModalRect((i + posX + 5), j + posY + 5.0F, typeX, typeY, 10, 10);
  }
  
  public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
    GlStateManager.enableColorMaterial();
    GlStateManager.pushMatrix();
    GlStateManager.translate(posX, posY, 50.0F);
    GlStateManager.scale(-scale, scale, scale);
    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    float f = ent.renderYawOffset;
    float f1 = ent.rotationYaw;
    float f2 = ent.rotationPitch;
    float f3 = ent.prevRotationYawHead;
    float f4 = ent.rotationYawHead;
    GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
    ent.renderYawOffset = 0.0F;
    ent.rotationYaw = 0.0F;
    ent.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
    ent.rotationYawHead = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
    ent.prevRotationYawHead = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
    ent.rotationPitch = 0.0F;
    ent.rotationYawHead = 0.0F;
    ent.prevRotationYawHead = 0.0F;
    ent.setAlwaysRenderNameTag(false);
    ent.setInvisible(false);
    GlStateManager.translate(0.0F, 0.0F, 0.0F);
    RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
    rendermanager.setPlayerViewY(180.0F);
    rendermanager.setRenderShadow(false);
    float fi = 1.0F;
    float fi1 = Math.max(ent.width, ent.height + 0.25F);
    if (ent.width >= ent.height || ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal) {
      fi = 1.0F;
    } else {
      fi = 2.0F;
    } 
    if (fi1 > 1.0D)
      fi /= fi1; 
    GlStateManager.scale(fi, fi, fi);
    if (ent.width >= ent.height || ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal) {
      fi = 0.5F;
    } else {
      fi = 0.0F;
    } 
    GlStateManager.translate(0.0F, fi, 0.0F);
    if (ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) {
      GlStateManager.translate(0.0F, 18.0F, 0.0F);
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
    } 
    if (ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || ent instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || ent instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) {
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.translate(0.0F, 0.5F, 0.0F);
      ent.prevRotationPitch = ent.rotationPitch = ent.prevRenderYawOffset = ent.prevRotationYaw = ent.prevRotationYawHead = ent.renderYawOffset = ent.rotationYaw = ent.rotationYawHead = 0.0F;
    } 
    rendermanager.renderEntity((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
    if (ent.isBeingRidden())
      for (Entity entity : ent.getPassengers()) {
        if (entity instanceof EntityLivingBase) {
          EntityLivingBase rider = (EntityLivingBase)entity;
          rider.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
          rider.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
          rider.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
          rider.rotationYawHead = ent.rotationYaw;
          rider.prevRotationYawHead = ent.rotationYaw;
          rider.setAlwaysRenderNameTag(false);
          rider.setInvisible(false);
          rendermanager.renderEntity((Entity)rider, 0.0D, rider.posY - ent.posY, 0.0D, 0.0F, 1.0F, false);
        } 
      }  
    if (ent.isRiding() && ent.getRidingEntity() instanceof EntityLivingBase) {
      EntityLivingBase rider = (EntityLivingBase)ent.getRidingEntity();
      rider.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
      rider.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
      rider.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
      rider.rotationYawHead = ent.rotationYaw;
      rider.prevRotationYawHead = ent.rotationYaw;
      rider.setAlwaysRenderNameTag(false);
      rider.setInvisible(false);
      rendermanager.renderEntity((Entity)rider, 0.0D, rider.posY - ent.posY, 0.0D, 0.0F, 1.0F, false);
    } 
    rendermanager.setRenderShadow(true);
    ent.renderYawOffset = f;
    ent.rotationYaw = f1;
    ent.rotationPitch = f2;
    ent.prevRotationYawHead = f3;
    ent.rotationYawHead = f4;
    GlStateManager.popMatrix();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableRescaleNormal();
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.disableTexture2D();
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
}
