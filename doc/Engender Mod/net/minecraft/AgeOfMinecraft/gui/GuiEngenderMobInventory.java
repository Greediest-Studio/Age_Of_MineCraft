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
  
  private ItemStack draggedStack = ItemStack.field_190927_a;
  
  private Slot clickedSlot;
  
  private boolean isRightMouseClick;
  
  private int dragSplittingLimit;
  
  private int dragSplittingRemnant;
  
  public GuiEngenderMobInventory(EntityPlayer player, EntityTameBase entity) {
    super(player.field_71069_bz);
    this.field_146291_p = true;
    this.mob = entity;
  }
  
  public void func_73866_w_() {
    this.field_146292_n.clear();
    super.func_73866_w_();
  }
  
  protected void func_146979_b(int mouseX, int mouseY) {
    int tier = (this.mob.getTier() == EnumTier.TIER6) ? 6 : ((this.mob.getTier() == EnumTier.TIER5) ? 5 : ((this.mob.getTier() == EnumTier.TIER4) ? 4 : ((this.mob.getTier() == EnumTier.TIER3) ? 3 : ((this.mob.getTier() == EnumTier.TIER2) ? 2 : 1))));
    this.field_146289_q.func_78276_b(this.mob.func_70005_c_() + " (Tier " + tier + ")", 0, -10, 16777215);
    if (this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans) {
      this.field_146289_q.func_78276_b("EXP: worth it all", 70, 100, 4210752);
      this.field_146289_q.func_78276_b("total EXP: ?", 70, 110, 4210752);
      this.field_146289_q.func_78276_b("LV 1", 80, 82, 4210752);
      this.field_146289_q.func_78276_b("str (1)", 8, 80, 4210752);
      this.field_146289_q.func_78276_b("sta (lazy)", 8, 90, 4210752);
      this.field_146289_q.func_78276_b("int (?)", 8, 100, 4210752);
      this.field_146289_q.func_78276_b("dex (nah)", 8, 110, 4210752);
      this.field_146289_q.func_78276_b("agi (zzz)", 8, 120, 4210752);
    } else {
      this.field_146289_q.func_78276_b("EXP: " + (int)this.mob.getEXP() + "/" + (this.mob.getNextLevelRequirement() * this.mob.getLevel()), 70, 100, 4210752);
      this.field_146289_q.func_78276_b("Total EXP: " + ((this.mob.getTotalEXP() >= 2.1474836E9F) ? "N/A" : (String)Integer.valueOf((int)this.mob.getTotalEXP())), 70, 110, 4210752);
      this.field_146289_q.func_78276_b("Level " + this.mob.getLevel(), 80, 82, 4210752);
      this.field_146289_q.func_78276_b("STR (" + TextFormatting.RED + (int)this.mob.getStrength() + TextFormatting.RESET + ")", 8, 80, 4210752);
      this.field_146289_q.func_78276_b("STA (" + TextFormatting.DARK_GREEN + (int)this.mob.getStamina() + TextFormatting.RESET + ")", 8, 90, 4210752);
      this.field_146289_q.func_78276_b("INT (" + TextFormatting.BLUE + (int)this.mob.getIntelligence() + TextFormatting.RESET + ")", 8, 100, 4210752);
      this.field_146289_q.func_78276_b("DEX (" + TextFormatting.LIGHT_PURPLE + (int)this.mob.getDexterity() + TextFormatting.RESET + ")", 8, 110, 4210752);
      this.field_146289_q.func_78276_b("AGI (" + TextFormatting.DARK_AQUA + (int)this.mob.getAgility() + TextFormatting.RESET + ")", 8, 120, 4210752);
    } 
    TextFormatting render = this.mob.getTextFormat();
    GlStateManager.func_179094_E();
    GlStateManager.func_179152_a(0.75F, 0.75F, 1.0F);
    this.field_146289_q.func_78276_b(this.mob.isWild() ? (TextFormatting.LIGHT_PURPLE + "Mother Nature") : (TextFormatting.GOLD + "Clan Leader " + this.mob.getOwner().func_70005_c_()), 0, -25, 16777215);
    this.field_146289_q.func_78276_b("HP " + (int)this.mob.func_110143_aJ() + "/" + (int)this.mob.func_110138_aP(), 110, 11, 0);
    this.field_146289_q.func_78276_b("EN " + (int)this.mob.getEnergy() + "/" + 'd', 110, 33, 0);
    this.field_146289_q.func_78276_b("Armor " + this.mob.func_70658_aO() + "/" + MathHelper.func_76128_c(this.mob.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e()), 110, 56, 0);
    GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
    GlStateManager.func_179152_a(0.675F, 0.675F, 1.0F);
    this.field_146289_q.func_78276_b(render + I18n.func_74838_a("descriptionline1." + this.mob.getDescName()), 8, 290, 4210752);
    this.field_146289_q.func_78276_b(render + I18n.func_74838_a("descriptionline2." + this.mob.getDescName()), 8, 300, 4210752);
    GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
    GlStateManager.func_179121_F();
  }
  
  public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
    this.oldMouseX = mouseX;
    this.oldMouseY = mouseY;
    func_146276_q_();
    int i = this.field_147003_i;
    int j = this.field_147009_r;
    func_146976_a(partialTicks, mouseX, mouseY);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(i, j, 0.0F);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    for (int k = 0; k < this.field_146292_n.size(); k++)
      ((GuiButton)this.field_146292_n.get(k)).func_191745_a(this.field_146297_k, mouseX, mouseY, partialTicks); 
    for (int j1 = 0; j1 < this.field_146293_o.size(); j1++)
      ((GuiLabel)this.field_146293_o.get(j1)).func_146159_a(this.field_146297_k, mouseX, mouseY); 
    func_146979_b(mouseX, mouseY);
    for (int i1 = 0; i1 < this.mob.basicInventory.func_70302_i_(); i1++) {
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
      if (i1 == 7 && !this.mob.basicInventory.func_70301_a(7).func_190926_b())
        slot = new Slot((IInventory)this.mob.basicInventory, i1, 113, 62); 
      if (slot.func_111238_b()) {
        drawSlot(slot, i1);
        if (isMouseOverSlot(slot, mouseX, mouseY)) {
          this.theSlot = slot;
          GlStateManager.func_179140_f();
          GlStateManager.func_179097_i();
          int m = slot.field_75223_e;
          int k1 = slot.field_75221_f;
          GlStateManager.func_179135_a(true, true, true, false);
          func_73733_a(m, k1, m + 16, k1 + 16, -2130706433, -2130706433);
          GlStateManager.func_179135_a(true, true, true, true);
          GlStateManager.func_179145_e();
          GlStateManager.func_179126_j();
        } 
      } 
    } 
    GlStateManager.func_179121_F();
    if (this.theSlot != null && this.theSlot.func_75216_d() && isMouseOverSlot(this.theSlot, mouseX, mouseY)) {
      ItemStack itemstack1 = this.theSlot.func_75211_c();
      func_146285_a(itemstack1, mouseX, mouseY);
    } 
  }
  
  private void drawSlot(Slot slotIn, int index) {
    int i = slotIn.field_75223_e;
    int j = slotIn.field_75221_f;
    ItemStack itemstack = slotIn.func_75211_c();
    boolean flag = false;
    boolean flag1 = (slotIn == this.clickedSlot && !this.draggedStack.func_190926_b() && !this.isRightMouseClick);
    ItemStack itemstack1 = this.mob.basicInventory.func_70301_a(index);
    String s = null;
    if (slotIn == this.clickedSlot && !this.draggedStack.func_190926_b() && this.isRightMouseClick && !itemstack.func_190926_b()) {
      itemstack = itemstack.func_77946_l();
      itemstack.func_190920_e(itemstack.func_190916_E() / 2);
    } else if (this.field_147007_t && this.field_147008_s.contains(slotIn) && !itemstack1.func_190926_b()) {
      if (this.field_147008_s.size() == 1)
        return; 
      if (Container.func_94527_a(slotIn, itemstack1, true) && this.field_147002_h.func_94531_b(slotIn)) {
        itemstack = itemstack1.func_77946_l();
        flag = true;
        Container.func_94525_a(this.field_147008_s, this.dragSplittingLimit, itemstack, slotIn.func_75211_c().func_190926_b() ? 0 : slotIn.func_75211_c().func_190916_E());
        int k = Math.min(itemstack.func_77976_d(), slotIn.func_178170_b(itemstack));
        if (itemstack.func_190916_E() > k) {
          s = TextFormatting.YELLOW.toString() + k;
          itemstack.func_190920_e(k);
        } 
      } else {
        this.field_147008_s.remove(slotIn);
        updateDragSplitting();
      } 
    } 
    this.field_73735_i = 100.0F;
    this.field_146296_j.field_77023_b = 100.0F;
    if (itemstack.func_190926_b() && slotIn.func_111238_b()) {
      TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();
      if (textureatlassprite != null) {
        GlStateManager.func_179140_f();
        this.field_146297_k.func_110434_K().func_110577_a(slotIn.getBackgroundLocation());
        func_175175_a(i, j, textureatlassprite, 16, 16);
        GlStateManager.func_179145_e();
        flag1 = true;
      } 
    } 
    if (!flag1) {
      if (flag)
        func_73734_a(i, j, i + 16, j + 16, -2130706433); 
      GlStateManager.func_179126_j();
      this.field_146296_j.func_184391_a((EntityLivingBase)this.field_146297_k.field_71439_g, itemstack, i, j);
      this.field_146296_j.func_180453_a(this.field_146289_q, itemstack, i, j, s);
    } 
    this.field_146296_j.field_77023_b = 0.0F;
    this.field_73735_i = 0.0F;
  }
  
  private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
    return func_146978_c(slotIn.field_75223_e, slotIn.field_75221_f, 16, 16, mouseX, mouseY);
  }
  
  private void updateDragSplitting() {
    ItemStack itemstack = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
    if (!itemstack.func_190926_b() && this.field_147007_t)
      if (this.dragSplittingLimit == 2) {
        this.dragSplittingRemnant = itemstack.func_77976_d();
      } else {
        this.dragSplittingRemnant = itemstack.func_190916_E();
        for (Slot slot : this.field_147008_s) {
          ItemStack itemstack1 = itemstack.func_77946_l();
          ItemStack itemstack2 = slot.func_75211_c();
          int i = itemstack2.func_190926_b() ? 0 : itemstack2.func_190916_E();
          Container.func_94525_a(this.field_147008_s, this.dragSplittingLimit, itemstack1, i);
          int j = Math.min(itemstack1.func_77976_d(), slot.func_178170_b(itemstack1));
          if (itemstack1.func_190916_E() > j)
            itemstack1.func_190920_e(j); 
          this.dragSplittingRemnant -= itemstack1.func_190916_E() - i;
        } 
      }  
  }
  
  protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.field_146297_k.func_110434_K().func_110577_a(INVENTORY_BACKGROUND);
    int i = this.field_147003_i;
    int j = this.field_147009_r;
    func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
    if (this.mob.basicInventory.func_70301_a(5).func_190926_b())
      func_73729_b(i + 95, j + 62, 80, 201, 16, 16); 
    if (this.mob.basicInventory.func_70301_a(7).func_190926_b())
      func_73729_b(i + 113, j + 62, 96, 201, 16, 16); 
    if (this.mob.basicInventory.func_70301_a(6).func_190926_b())
      func_73729_b(i + 131, j + 62, 112, 201, 16, 16); 
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
      func_73729_b(i + 77, j + 15, 0, 166, health, 5); 
    int energy = (int)(this.mob.getEnergyPercent() * 94.0F);
    if (energy > 0)
      func_73729_b(i + 77, j + 31, 0, 171, energy, 5); 
    int exp = (int)(this.mob.getEXPPercent() * 101.0F);
    if (exp > 0)
      func_73729_b(i + 70, j + 92, 0, 176, exp, 5); 
    int armor = (int)(this.mob.func_70658_aO() * 2.0F);
    if (armor > 0)
      func_73729_b(i + 77, j + 48, 95, 166, armor + 1, 5); 
    int armort = (int)(MathHelper.func_76128_c(this.mob.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e()) * 2.0F);
    if (armort > 0)
      func_73729_b(i + 77, j + 48, 95, 171, armort + 1, 5); 
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
    int i = this.field_147003_i;
    int j = this.field_147009_r;
    func_175174_a((i + posX), j + posY, spikeyborder ? 100 : 80, 181, 20, 20);
    func_175174_a((i + posX + 5), j + posY + 5.0F, typeX, typeY, 10, 10);
  }
  
  public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
    GlStateManager.func_179142_g();
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(posX, posY, 50.0F);
    GlStateManager.func_179152_a(-scale, scale, scale);
    GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
    float f = ent.field_70761_aq;
    float f1 = ent.field_70177_z;
    float f2 = ent.field_70125_A;
    float f3 = ent.field_70758_at;
    float f4 = ent.field_70759_as;
    GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
    RenderHelper.func_74519_b();
    GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
    ent.field_70761_aq = 0.0F;
    ent.field_70177_z = 0.0F;
    ent.field_70125_A = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
    ent.field_70759_as = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
    ent.field_70758_at = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
    ent.field_70125_A = 0.0F;
    ent.field_70759_as = 0.0F;
    ent.field_70758_at = 0.0F;
    ent.func_174805_g(false);
    ent.func_82142_c(false);
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
    RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
    rendermanager.func_178631_a(180.0F);
    rendermanager.func_178633_a(false);
    float fi = 1.0F;
    float fi1 = Math.max(ent.field_70130_N, ent.field_70131_O + 0.25F);
    if (ent.field_70130_N >= ent.field_70131_O || ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal) {
      fi = 1.0F;
    } else {
      fi = 2.0F;
    } 
    if (fi1 > 1.0D)
      fi /= fi1; 
    GlStateManager.func_179152_a(fi, fi, fi);
    if (ent.field_70130_N >= ent.field_70131_O || ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal) {
      fi = 0.5F;
    } else {
      fi = 0.0F;
    } 
    GlStateManager.func_179109_b(0.0F, fi, 0.0F);
    if (ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) {
      GlStateManager.func_179109_b(0.0F, 18.0F, 0.0F);
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
    } 
    if (ent instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || ent instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || ent instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) {
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
      ent.field_70127_C = ent.field_70125_A = ent.field_70760_ar = ent.field_70126_B = ent.field_70758_at = ent.field_70761_aq = ent.field_70177_z = ent.field_70759_as = 0.0F;
    } 
    rendermanager.func_188391_a((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
    if (ent.func_184207_aI())
      for (Entity entity : ent.func_184188_bt()) {
        if (entity instanceof EntityLivingBase) {
          EntityLivingBase rider = (EntityLivingBase)entity;
          rider.field_70761_aq = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
          rider.field_70177_z = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
          rider.field_70125_A = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
          rider.field_70759_as = ent.field_70177_z;
          rider.field_70758_at = ent.field_70177_z;
          rider.func_174805_g(false);
          rider.func_82142_c(false);
          rendermanager.func_188391_a((Entity)rider, 0.0D, rider.field_70163_u - ent.field_70163_u, 0.0D, 0.0F, 1.0F, false);
        } 
      }  
    if (ent.func_184218_aH() && ent.func_184187_bx() instanceof EntityLivingBase) {
      EntityLivingBase rider = (EntityLivingBase)ent.func_184187_bx();
      rider.field_70761_aq = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
      rider.field_70177_z = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
      rider.field_70125_A = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
      rider.field_70759_as = ent.field_70177_z;
      rider.field_70758_at = ent.field_70177_z;
      rider.func_174805_g(false);
      rider.func_82142_c(false);
      rendermanager.func_188391_a((Entity)rider, 0.0D, rider.field_70163_u - ent.field_70163_u, 0.0D, 0.0F, 1.0F, false);
    } 
    rendermanager.func_178633_a(true);
    ent.field_70761_aq = f;
    ent.field_70177_z = f1;
    ent.field_70125_A = f2;
    ent.field_70758_at = f3;
    ent.field_70759_as = f4;
    GlStateManager.func_179121_F();
    RenderHelper.func_74518_a();
    GlStateManager.func_179101_C();
    GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
    GlStateManager.func_179090_x();
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
  }
}
