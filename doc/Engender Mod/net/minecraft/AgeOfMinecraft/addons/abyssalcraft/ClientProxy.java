package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import com.shinoow.abyssalcraft.api.item.ACItems;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbygolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalPortal;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothFist;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSlugOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadedChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadgolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadguard;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGatekeeperMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGreaterDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityImplosion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityInkProjectileOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityMiniBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowBeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowCreature;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySkeletonGoliath;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySquads;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderAbyssalPortal;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderAbyssalniteGolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderChagarothFist;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderChagarothSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderCoraliumCharge;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderCoraliumSquid;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDreadAbyssalniteGolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDreadedCharge;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDreadguard;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDreadling;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderGatekeeperMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderGreaterDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderImplosion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderLesserDreadbeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderLesserShoggoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderMiniBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderOmotholCharge;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderOmotholGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderRemnant;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderSacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderShadowBeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderShadowCreature;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderShadowMonster;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderSkeletonGoliath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
  public void preInit(FMLPreInitializationEvent e) {
    super.preInit(e);
  }
  
  public void init(FMLInitializationEvent e) {
    super.init(e);
  }
  
  public void postInit(FMLPostInitializationEvent e) {
    super.postInit(e);
  }
  
  public void renderEntitiesAbyssalcraft() {
    if (Loader.isModLoaded("abyssalcraft")) {
      RenderingRegistry.registerEntityRenderingHandler(EntitySquads.class, manager -> new RenderSnowball(manager, Items.field_151099_bA, Minecraft.func_71410_x().func_175599_af()));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlugOther.class, manager -> new RenderSnowball(manager, ACItems.dread_fragment, Minecraft.func_71410_x().func_175599_af()));
      RenderingRegistry.registerEntityRenderingHandler(EntityInkProjectileOther.class, manager -> new RenderSnowball(manager, Items.field_151100_aR, Minecraft.func_71410_x().func_175599_af()));
      RenderingRegistry.registerEntityRenderingHandler(EntityOmotholChargeOther.class, manager -> new RenderOmotholCharge(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumChargeOther.class, manager -> new RenderCoraliumCharge(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadedChargeOther.class, manager -> new RenderDreadedCharge(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityPEGunPellet.class, manager -> new RenderPEGunPellet(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, manager -> new RenderBlackHole(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityMiniBlackHole.class, manager -> new RenderMiniBlackHole(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityImplosion.class, manager -> new RenderImplosion(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, manager -> new RenderAbyssalniteGolem(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, manager -> new RenderDreadAbyssalniteGolem(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumSquid.class, manager -> new RenderCoraliumSquid(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, manager -> new RenderAbyssalZombie(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, manager -> new RenderDepthsGhoul(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, manager -> new RenderSkeletonGoliath(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, manager -> new RenderDragonMinion(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, manager -> new RenderDragonBoss(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, manager -> new RenderDreadSpawn(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, manager -> new RenderChagarothSpawn(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, manager -> new RenderDreadling(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, manager -> new RenderChagarothFist(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, manager -> new RenderGreaterDreadSpawn(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, manager -> new RenderDreadguard(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, manager -> new RenderLesserDreadbeast(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, manager -> new RenderChagaroth(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, manager -> new RenderOmotholGhoul(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, manager -> new RenderLesserShoggoth(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, manager -> new RenderShadowCreature(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, manager -> new RenderShadowMonster(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, manager -> new RenderShadowBeast(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, manager -> new RenderSacthoth(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, manager -> new RenderRemnant(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, manager -> new RenderGatekeeperMinion(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, manager -> new RenderJzahar(manager));
      RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalPortal.class, manager -> new RenderAbyssalPortal(manager));
    } 
  }
}
