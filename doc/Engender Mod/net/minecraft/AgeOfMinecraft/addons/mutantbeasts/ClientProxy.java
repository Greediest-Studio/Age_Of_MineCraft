package net.minecraft.AgeOfMinecraft.addons.mutantbeasts;

import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityEndersoulFragment;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeletonArrow;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntitySpiderPig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityThrowingBlock;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderEndersoulFragment;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantCreeper;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantSkeleton;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantSkeletonArrow;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderMutantZombie;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderSpiderPig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render.RenderThrowingBlock;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
  public void preInit(FMLPreInitializationEvent e) {
    super.preInit(e);
    renderEntitiesMutantBeasts();
  }
  
  public void init(FMLInitializationEvent e) {
    super.init(e);
  }
  
  public void postInit(FMLPostInitializationEvent e) {
    super.postInit(e);
  }
  
  public void renderEntitiesMutantBeasts() {
    if (Loader.isModLoaded("mutantbeasts")) {
      RenderingRegistry.registerEntityRenderingHandler(EntitySpiderPig.class, manager -> new RenderSpiderPig(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantSnowGolem.class, manager -> new RenderMutantSnowGolem(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantZombie.class, manager -> new RenderMutantZombie(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantCreeper.class, manager -> new RenderMutantCreeper(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantSkeleton.class, manager -> new RenderMutantSkeleton(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantEnderman.class, manager -> new RenderMutantEnderman(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMutantSkeletonArrow.class, manager -> new RenderMutantSkeletonArrow(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityThrowingBlock.class, manager -> new RenderThrowingBlock(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityEndersoulFragment.class, manager -> new RenderEndersoulFragment(manager));
    } 
  }
}
