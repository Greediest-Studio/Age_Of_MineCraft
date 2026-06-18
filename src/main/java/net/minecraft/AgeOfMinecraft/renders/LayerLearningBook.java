package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.items.ItemLearningBook;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerLearningBook implements LayerRenderer<EntityTameBase> {
  private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
  
  private final ModelBook modelBook = new ModelBook();
  
  private final RenderLiving<?> render;
  
  public LayerLearningBook(RenderLiving<?> p_i46105_1_) {
    this.render = p_i46105_1_;
  }
  
  public void doRenderLayer(EntityTameBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.getBookID() != 0) {
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableNormalize();
      GlStateManager.pushMatrix();
      float f = entitylivingbaseIn.ticksExisted + partialTicks;
      GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F + 0.25F, -0.725F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(210.0F, 0.0F, 0.0F, 1.0F);
      if (EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn.isChild() && (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIcyEnderCreeper)) {
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.translate(0.0625F, -1.125F, 0.0F);
      } 
      if (entitylivingbaseIn.isSneaking())
        GlStateManager.translate(0.0F, -0.1F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIcyEnderCreeper)
        GlStateManager.translate(EngenderConfig.mobs.useMobTalkerModels ? 0.25F : -0.625F, EngenderConfig.mobs.useMobTalkerModels ? 0.125F : 0.675F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIceGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagmaGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityPrisonGolem)
        GlStateManager.translate((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem && EngenderConfig.mobs.useMobTalkerModels) ? -0.125F : -0.825F, (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem && EngenderConfig.mobs.useMobTalkerModels) ? 0.0F : 0.675F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.translate(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.translate(-0.125F, 0.125F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite)
        GlStateManager.translate((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish && EngenderConfig.mobs.useMobTalkerModels) ? 0.25F : -0.25F, (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish && EngenderConfig.mobs.useMobTalkerModels) ? 0.0F : -0.825F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider && !EngenderConfig.mobs.useMobTalkerModels) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPrisonSpider)
        GlStateManager.translate(-0.725F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider && EngenderConfig.mobs.useMobTalkerModels && !(entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPrisonSpider))
        GlStateManager.translate(0.0625F, 0.0F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.translate(0.25F, 0.125F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast && !EngenderConfig.mobs.useMobTalkerModels) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGhasther) {
        GlStateManager.translate(-0.675F, -1.5F, 0.1F);
        GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime && !EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.translate(-0.1F, -0.95F, 0.0F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid) {
        GlStateManager.translate(-0.125F, 0.0F, 0.0F);
        GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPolarBear)
        GlStateManager.translate(-0.85F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEversource)
        GlStateManager.translate(-0.25F, -0.325F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit)
        GlStateManager.translate(-0.125F, -0.875F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot)
        GlStateManager.translate(-0.5F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf)
        GlStateManager.translate(-0.5F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig)
        GlStateManager.translate(-0.675F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow)
        GlStateManager.translate(-0.5F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep)
        GlStateManager.translate(-0.5F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama)
        GlStateManager.translate(-0.45F, 0.75F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant)
        GlStateManager.translate(-0.125F, 0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySkeletonGoliath)
        GlStateManager.translate(0.125F, 0.725F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth)
        GlStateManager.translate(0.0F, 1.325F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothFist)
        GlStateManager.translate(0.5F, -0.125F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling)
        GlStateManager.translate(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul)
        GlStateManager.translate(0.25F, 0.875F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGreaterDreadSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast)
        GlStateManager.translate(-0.125F, -0.75F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGatekeeperMinion)
        GlStateManager.translate(0.0F, 0.575F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth)
        GlStateManager.translate(-0.75F, 1.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion) {
        GlStateManager.translate(-7.0F, 2.5F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker) {
        GlStateManager.translate(-0.125F, -0.25F, 0.0F);
        GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither)
        GlStateManager.translate(0.0F, 0.0F, MathHelper.sin(f * 0.1F) * 0.5F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowBeast) {
        GlStateManager.translate(-0.1F, 0.25F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth) {
        GlStateManager.translate(-0.25F, 1.75F, MathHelper.sin(f * 0.1F) * 1.5F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowCreature) {
        GlStateManager.translate(0.0F, -0.625F, -0.075F);
        GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster) {
        GlStateManager.translate(0.325F, -0.125F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn.isChild() && (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton)) {
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(0.125F, -1.25F, 0.0F);
      } 
      switch (((ItemLearningBook)entitylivingbaseIn.getCurrentBook().getItem()).getTier()) {
        case 1:
          GlStateManager.color(0.5F, 0.75F, 1.0F);
          break;
        case 2:
          GlStateManager.color(1.0F, 0.5F, 0.0F);
          break;
        case 3:
          GlStateManager.color(0.8F, 0.2F, 1.0F);
          break;
        case 4:
          GlStateManager.color(0.0F, 0.5F, 1.0F);
          break;
        case 5:
          GlStateManager.color(0.8F, 0.9F, 0.0F);
          break;
        case 6:
          GlStateManager.color(MathHelper.cos(ageInTicks * 0.1F) * 0.5F + 1.0F + 0.5F, 0.9F, 0.0F);
          break;
        default:
          GlStateManager.color(1.0F, 1.0F, 1.0F);
          break;
      } 
      this.render.bindTexture(TEXTURE_BOOK);
      float f3 = entitylivingbaseIn.pageFlipPrev + (entitylivingbaseIn.pageFlip - entitylivingbaseIn.pageFlipPrev) * partialTicks + 0.25F;
      float f4 = entitylivingbaseIn.pageFlipPrev + (entitylivingbaseIn.pageFlip - entitylivingbaseIn.pageFlipPrev) * partialTicks + 0.75F;
      f3 = (f3 - MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
      f4 = (f4 - MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F)
        f3 = 0.0F; 
      if (f4 < 0.0F)
        f4 = 0.0F; 
      if (f3 > 1.0F)
        f3 = 1.0F; 
      if (f4 > 1.0F)
        f4 = 1.0F; 
      float f5 = entitylivingbaseIn.bookSpreadPrev + (entitylivingbaseIn.bookSpread - entitylivingbaseIn.bookSpreadPrev) * partialTicks;
      this.modelBook.render((Entity)entitylivingbaseIn, f, f3, f4, f5, 0.0F, 0.0625F);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      GlStateManager.disableNormalize();
      GlStateManager.disableRescaleNormal();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
