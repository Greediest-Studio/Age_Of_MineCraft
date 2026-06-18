package net.minecraft.AgeOfMinecraft.renders;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.models.ModelBlaze;
import net.minecraft.AgeOfMinecraft.models.ModelCMMBlaze;
import net.minecraft.AgeOfMinecraft.models.ModelVex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

@SideOnly(Side.CLIENT)
public class RenderBlaze extends RenderLiving<EntityBlaze> {
  private static final ResourceLocation cmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/blaze.png");
  
  private static final ResourceLocation textures = new ResourceLocation("textures/entity/blaze.png");
  
  private static final ResourceLocation anticmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/blaze.png");
  
  private static final ResourceLocation antiTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/blaze.png");
  
  private static ModelCMMBlaze cmmmodel = new ModelCMMBlaze();
  
  private static ModelBlaze regularmodel = new ModelBlaze();
  
  private LayerHeldItemCMM helditems = new LayerHeldItemCMM(EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.RArm : regularmodel.blazeSticks[0], EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.LArm : regularmodel.blazeSticks[3]);
  
  private static ModelVex disguisemodel = new ModelVex();
  
  public RenderBlaze(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.blazeHead, cmmmodel.Head));
    func_177094_a(this.helditems);
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void preRenderCallback(EntityBlaze entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel);
    this.field_177097_h.remove(this.helditems);
    this.helditems.modelRenderer1 = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.RArm : regularmodel.blazeSticks[0];
    this.helditems.modelRenderer2 = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.LArm : regularmodel.blazeSticks[3];
    func_177094_a(this.helditems);
    if (entitylivingbaseIn.getIllusionFormTime() > 0)
      GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F); 
    if (EngenderConfig.mobs.useMobTalkerModels)
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_()) {
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F);
      } else if (entitylivingbaseIn.func_184218_aH()) {
        GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F);
      }  
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected void applyRotations(EntityBlaze entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (EngenderConfig.mobs.useMobTalkerModels)
      if (entityLiving.isSitResting())
        GlStateManager.func_179109_b(0.0F, MathHelper.func_76134_b(p_77043_2_ * 0.2F) * 0.1F, 0.0F);  
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179109_b(0.0F, 0.0F, -f * 0.125F);
      } else {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179109_b(f * 0.25F, 0.0F, 0.0F);
      } 
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityBlaze entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/illager/vex.png") : (EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmTextures : cmmTextures) : (entity.isAntiMob() ? antiTextures : textures));
  }
  
  private ModelRenderer getHead() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)func_177087_b()).Head : ((ModelBlaze)func_177087_b()).blazeHead;
  }
  
  private ModelRenderer getRightArm() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)func_177087_b()).RArm : ((ModelBlaze)func_177087_b()).blazeSticks[0];
  }
  
  private ModelRenderer getLeftArm() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)func_177087_b()).LArm : ((ModelBlaze)func_177087_b()).blazeSticks[3];
  }
  
  public void doRender(EntityBlaze entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerHead implements LayerRenderer<EntityLivingBase> {
    private RenderLiving<?> renderer;
    
    public LayerHead(RenderLiving<?> p_i46120_1_) {
      this.renderer = p_i46120_1_;
    }
    
    public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (this.renderer != null && !entitylivingbaseIn.func_70631_g_()) {
        GlStateManager.func_179094_E();
        ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.HEAD);
        if (!itemstack.func_190926_b()) {
          Item item = itemstack.func_77973_b();
          Minecraft minecraft = Minecraft.func_71410_x();
          boolean flag = (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof EntityZombie && ((EntityZombie)entitylivingbaseIn).isVillager()));
          if (entitylivingbaseIn.func_70631_g_() && !flag) {
            GlStateManager.func_179109_b(0.0F, 0.5F * scale, 0.0F);
            GlStateManager.func_179109_b(0.0F, 16.0F * scale, 0.0F);
          } 
          ((RenderBlaze)this.renderer).getHead().func_78794_c(0.0625F);
          GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
          if (item == Items.field_151144_bL) {
            GlStateManager.func_179152_a(1.1875F, -1.1875F, -1.1875F);
            if (flag)
              GlStateManager.func_179109_b(0.0F, 0.0625F, 0.0F); 
            GameProfile gameprofile = null;
            if (itemstack.func_77942_o()) {
              NBTTagCompound nbttagcompound = itemstack.func_77978_p();
              if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
                gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
              } else if (nbttagcompound.func_150297_b("SkullOwner", 8)) {
                String s = nbttagcompound.func_74779_i("SkullOwner");
                if (!StringUtils.isBlank(s)) {
                  gameprofile = TileEntitySkull.func_174884_b(new GameProfile((UUID)null, s));
                  nbttagcompound.func_74782_a("SkullOwner", (NBTBase)NBTUtil.func_180708_a(new NBTTagCompound(), gameprofile));
                } 
              } 
            } 
            TileEntitySkullRenderer.field_147536_b.func_188190_a(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.func_77960_j(), gameprofile, -1, limbSwing);
          } else if (!(item instanceof ItemArmor) || ((ItemArmor)item).func_185083_B_() != EntityEquipmentSlot.HEAD) {
            GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179152_a(0.625F, -0.625F, -0.625F);
            if (flag)
              GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F); 
            minecraft.func_175597_ag().func_178099_a(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
          } 
        } 
        GlStateManager.func_179121_F();
      } 
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
}
