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
      RenderingRegistry.registerEntityRenderingHandler(EntitySquads.class, manager -> new RenderSnowball<>(manager, Items.WRITABLE_BOOK, Minecraft.getMinecraft().getRenderItem()));
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlugOther.class, manager -> new RenderSnowball<>(manager, ACItems.dread_fragment, Minecraft.getMinecraft().getRenderItem()));
      RenderingRegistry.registerEntityRenderingHandler(EntityInkProjectileOther.class, manager -> new RenderSnowball<>(manager, Items.DYE, Minecraft.getMinecraft().getRenderItem()));
      RenderingRegistry.registerEntityRenderingHandler(EntityOmotholChargeOther.class, RenderOmotholCharge::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumChargeOther.class, RenderCoraliumCharge::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadedChargeOther.class, RenderDreadedCharge::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityPEGunPellet.class, RenderPEGunPellet::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, RenderBlackHole::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityMiniBlackHole.class, RenderMiniBlackHole::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityImplosion.class, RenderImplosion::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, RenderAbyssalniteGolem::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, RenderDreadAbyssalniteGolem::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumSquid.class, RenderCoraliumSquid::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, RenderAbyssalZombie::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, RenderDepthsGhoul::new);
      RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, RenderSkeletonGoliath::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, RenderDragonMinion::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, RenderDragonBoss::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, RenderDreadSpawn::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, RenderChagarothSpawn::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, RenderDreadling::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, RenderChagarothFist::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, RenderGreaterDreadSpawn::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, RenderDreadguard::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, RenderLesserDreadbeast::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, RenderChagaroth::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, RenderOmotholGhoul::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, RenderLesserShoggoth::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, RenderShadowCreature::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, RenderShadowMonster::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, RenderShadowBeast::new);
      RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, RenderSacthoth::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, RenderRemnant::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, RenderGatekeeperMinion::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, RenderJzahar::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalPortal.class, RenderAbyssalPortal::new);
    } 
  }
}
