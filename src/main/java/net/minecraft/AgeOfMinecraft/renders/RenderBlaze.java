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
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    addLayer(new LayerCustomHeadEngender(regularmodel.blazeHead, cmmmodel.Head));
    addLayer(this.helditems);
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void preRenderCallback(EntityBlaze entitylivingbaseIn, float partialTickTime) {
    this.mainModel = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel);
    this.layerRenderers.remove(this.helditems);
    this.helditems.modelRenderer1 = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.RArm : regularmodel.blazeSticks[0];
    this.helditems.modelRenderer2 = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel.LArm : regularmodel.blazeSticks[3];
    addLayer(this.helditems);
    if (entitylivingbaseIn.getIllusionFormTime() > 0)
      GlStateManager.scale(0.4F, 0.4F, 0.4F); 
    if (EngenderConfig.mobs.useMobTalkerModels)
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild()) {
        GlStateManager.translate(0.0F, 0.6F, 0.25F);
      } else if (entitylivingbaseIn.isRiding()) {
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
      }  
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.ticksExisted <= 21 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected void applyRotations(EntityBlaze entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (EngenderConfig.mobs.useMobTalkerModels)
      if (entityLiving.isSitResting())
        GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.2F) * 0.1F, 0.0F);  
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -f * 0.125F);
      } else {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.25F, 0.0F, 0.0F);
      } 
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityBlaze entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/illager/vex.png") : (EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmTextures : cmmTextures) : (entity.isAntiMob() ? antiTextures : textures));
  }
  
  private ModelRenderer getHead() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)getMainModel()).Head : ((ModelBlaze)getMainModel()).blazeHead;
  }
  
  private ModelRenderer getRightArm() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)getMainModel()).RArm : ((ModelBlaze)getMainModel()).blazeSticks[0];
  }
  
  private ModelRenderer getLeftArm() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMBlaze)getMainModel()).LArm : ((ModelBlaze)getMainModel()).blazeSticks[3];
  }
  
  public void doRender(EntityBlaze entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.shadowOpaque = 0.0F;
    } else {
      this.shadowOpaque = 1.0F;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerHead implements LayerRenderer<EntityLivingBase> {
    private RenderLiving<?> renderer;
    
    public LayerHead(RenderLiving<?> p_i46120_1_) {
      this.renderer = p_i46120_1_;
    }
    
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (this.renderer != null && !entitylivingbaseIn.isChild()) {
        GlStateManager.pushMatrix();
        ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (!itemstack.isEmpty()) {
          Item item = itemstack.getItem();
          Minecraft minecraft = Minecraft.getMinecraft();
          boolean flag = (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof EntityZombie && ((EntityZombie)entitylivingbaseIn).isVillager()));
          if (entitylivingbaseIn.isChild() && !flag) {
            GlStateManager.translate(0.0F, 0.5F * scale, 0.0F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
          } 
          ((RenderBlaze)this.renderer).getHead().postRender(0.0625F);
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          if (item == Items.SKULL) {
            GlStateManager.scale(1.1875F, -1.1875F, -1.1875F);
            if (flag)
              GlStateManager.translate(0.0F, 0.0625F, 0.0F); 
            GameProfile gameprofile = null;
            if (itemstack.hasTagCompound()) {
              NBTTagCompound nbttagcompound = itemstack.getTagCompound();
              if (nbttagcompound.hasKey("SkullOwner", 10)) {
                gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
              } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
                String s = nbttagcompound.getString("SkullOwner");
                if (!StringUtils.isBlank(s)) {
                  gameprofile = TileEntitySkull.updateGameProfile(new GameProfile((UUID)null, s));
                  nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                } 
              } 
            } 
            TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1, limbSwing);
          } else if (!(item instanceof ItemArmor) || ((ItemArmor)item).getEquipmentSlot() != EntityEquipmentSlot.HEAD) {
            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            if (flag)
              GlStateManager.translate(0.0F, 0.1875F, 0.0F); 
            minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
          } 
        } 
        GlStateManager.popMatrix();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
