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
    if (!entitylivingbaseIn.func_82150_aj() && entitylivingbaseIn.getBookID() != 0) {
      GlStateManager.func_179091_B();
      GlStateManager.func_179108_z();
      GlStateManager.func_179094_E();
      float f = entitylivingbaseIn.field_70173_aa + partialTicks;
      GlStateManager.func_179109_b(0.0F, 0.1F + MathHelper.func_76126_a(f * 0.1F) * 0.01F + 0.25F, -0.725F);
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(210.0F, 0.0F, 0.0F, 1.0F);
      if (EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn.func_70631_g_() && (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIcyEnderCreeper)) {
        GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
        GlStateManager.func_179109_b(0.0625F, -1.125F, 0.0F);
      } 
      if (entitylivingbaseIn.func_70093_af())
        GlStateManager.func_179109_b(0.0F, -0.1F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIcyEnderCreeper)
        GlStateManager.func_179109_b(EngenderConfig.mobs.useMobTalkerModels ? 0.25F : -0.625F, EngenderConfig.mobs.useMobTalkerModels ? 0.125F : 0.675F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIceGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagmaGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityPrisonGolem)
        GlStateManager.func_179109_b((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem && EngenderConfig.mobs.useMobTalkerModels) ? -0.125F : -0.825F, (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem && EngenderConfig.mobs.useMobTalkerModels) ? 0.0F : 0.675F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.func_179109_b(-0.125F, 0.125F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite)
        GlStateManager.func_179109_b((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish && EngenderConfig.mobs.useMobTalkerModels) ? 0.25F : -0.25F, (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish && EngenderConfig.mobs.useMobTalkerModels) ? 0.0F : -0.825F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider && !EngenderConfig.mobs.useMobTalkerModels) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPrisonSpider)
        GlStateManager.func_179109_b(-0.725F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider && EngenderConfig.mobs.useMobTalkerModels && !(entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPrisonSpider))
        GlStateManager.func_179109_b(0.0625F, 0.0F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) && !EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.func_179109_b(0.25F, 0.125F, 0.0F); 
      if ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast && !EngenderConfig.mobs.useMobTalkerModels) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGhasther) {
        GlStateManager.func_179109_b(-0.675F, -1.5F, 0.1F);
        GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime && !EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179109_b(-0.1F, -0.95F, 0.0F);
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
        GlStateManager.func_179114_b(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid) {
        GlStateManager.func_179109_b(-0.125F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPolarBear)
        GlStateManager.func_179109_b(-0.85F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEversource)
        GlStateManager.func_179109_b(-0.25F, -0.325F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit)
        GlStateManager.func_179109_b(-0.125F, -0.875F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot)
        GlStateManager.func_179109_b(-0.5F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf)
        GlStateManager.func_179109_b(-0.5F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig)
        GlStateManager.func_179109_b(-0.675F, -0.5F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow)
        GlStateManager.func_179109_b(-0.5F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep)
        GlStateManager.func_179109_b(-0.5F, 0.0F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama)
        GlStateManager.func_179109_b(-0.45F, 0.75F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant)
        GlStateManager.func_179109_b(-0.125F, 0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySkeletonGoliath)
        GlStateManager.func_179109_b(0.125F, 0.725F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth)
        GlStateManager.func_179109_b(0.0F, 1.325F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothFist)
        GlStateManager.func_179109_b(0.5F, -0.125F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling)
        GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul)
        GlStateManager.func_179109_b(0.25F, 0.875F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGreaterDreadSpawn || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast)
        GlStateManager.func_179109_b(-0.125F, -0.75F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGatekeeperMinion)
        GlStateManager.func_179109_b(0.0F, 0.575F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth)
        GlStateManager.func_179109_b(-0.75F, 1.25F, 0.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion) {
        GlStateManager.func_179109_b(-7.0F, 2.5F, 0.0F);
        GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker) {
        GlStateManager.func_179109_b(-0.125F, -0.25F, 0.0F);
        GlStateManager.func_179114_b(-30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither)
        GlStateManager.func_179109_b(0.0F, 0.0F, MathHelper.func_76126_a(f * 0.1F) * 0.5F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowBeast) {
        GlStateManager.func_179109_b(-0.1F, 0.25F, 0.0F);
        GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth) {
        GlStateManager.func_179109_b(-0.25F, 1.75F, MathHelper.func_76126_a(f * 0.1F) * 1.5F);
        GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowCreature) {
        GlStateManager.func_179109_b(0.0F, -0.625F, -0.075F);
        GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster) {
        GlStateManager.func_179109_b(0.325F, -0.125F, 0.0F);
        GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn.func_70631_g_() && (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton)) {
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
        GlStateManager.func_179109_b(0.125F, -1.25F, 0.0F);
      } 
      switch (((ItemLearningBook)entitylivingbaseIn.getCurrentBook().func_77973_b()).getTier()) {
        case 1:
          GlStateManager.func_179124_c(0.5F, 0.75F, 1.0F);
          break;
        case 2:
          GlStateManager.func_179124_c(1.0F, 0.5F, 0.0F);
          break;
        case 3:
          GlStateManager.func_179124_c(0.8F, 0.2F, 1.0F);
          break;
        case 4:
          GlStateManager.func_179124_c(0.0F, 0.5F, 1.0F);
          break;
        case 5:
          GlStateManager.func_179124_c(0.8F, 0.9F, 0.0F);
          break;
        case 6:
          GlStateManager.func_179124_c(MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.5F + 1.0F + 0.5F, 0.9F, 0.0F);
          break;
        default:
          GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
          break;
      } 
      this.render.func_110776_a(TEXTURE_BOOK);
      float f3 = entitylivingbaseIn.pageFlipPrev + (entitylivingbaseIn.pageFlip - entitylivingbaseIn.pageFlipPrev) * partialTicks + 0.25F;
      float f4 = entitylivingbaseIn.pageFlipPrev + (entitylivingbaseIn.pageFlip - entitylivingbaseIn.pageFlipPrev) * partialTicks + 0.75F;
      f3 = (f3 - MathHelper.func_76140_b(f3)) * 1.6F - 0.3F;
      f4 = (f4 - MathHelper.func_76140_b(f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F)
        f3 = 0.0F; 
      if (f4 < 0.0F)
        f4 = 0.0F; 
      if (f3 > 1.0F)
        f3 = 1.0F; 
      if (f4 > 1.0F)
        f4 = 1.0F; 
      float f5 = entitylivingbaseIn.bookSpreadPrev + (entitylivingbaseIn.bookSpread - entitylivingbaseIn.bookSpreadPrev) * partialTicks;
      this.modelBook.func_78088_a((Entity)entitylivingbaseIn, f, f3, f4, f5, 0.0F, 0.0625F);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
      GlStateManager.func_179133_A();
      GlStateManager.func_179101_C();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
