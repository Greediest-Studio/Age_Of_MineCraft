package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconMobGirlBreedingRitual extends NecronomiconRitual {
  public NecronomiconMobGirlBreedingRitual() {
    super("mobGirlBreeding", 2, 10000.0F, Items.BED, Items.GOLDEN_APPLE, Items.BED, Items.GOLDEN_APPLE, Items.BED, Items.GOLDEN_APPLE, Items.BED, Items.GOLDEN_APPLE);
  }
  
  public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
    List<EntityTameBase> dreadSpawns = world.getEntitiesWithinAABB(EntityTameBase.class, (new AxisAlignedBB(pos)).grow(128.0D));
    return (!dreadSpawns.isEmpty() && EngenderConfig.mobs.useMobTalkerModels && EngenderConfig.mobs.breeding);
  }
  
  protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
    world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), (pos.getY() + 1), pos.getZ(), false));
    world.createExplosion(player, pos.getX(), (pos.getY() + 1), pos.getZ(), 40.0F, false);
    List<EntityTameBase> girlList = Lists.newArrayList();
    List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, (new AxisAlignedBB(pos)).grow(128.0D));
    for (Entity entity : entities) {
      if (entity instanceof EntityTameBase && ((EntityTameBase)entity).canBeMatedWith())
        girlList.add((EntityTameBase)entity); 
    } 
    if (!girlList.isEmpty())
      for (EntityTameBase girl : girlList) {
        if (false) {
          if (!girl.isChild())
            girl.setGrowingAge(5000); 
          girl.setInLove(player);
          girl.attackEntityFrom(DamageSource.GENERIC, 0.0F);
        } 
      }  
  }
  
  protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}
}


