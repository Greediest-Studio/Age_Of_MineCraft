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
    super("mobGirlBreeding", 2, 10000.0F, new Object[] { Items.field_151104_aV, Items.field_151153_ao, Items.field_151104_aV, Items.field_151153_ao, Items.field_151104_aV, Items.field_151153_ao, Items.field_151104_aV, Items.field_151153_ao });
  }
  
  public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
    List<EntityTameBase> dreadSpawns = world.func_72872_a(EntityTameBase.class, (new AxisAlignedBB(pos)).func_186662_g(128.0D));
    return (!dreadSpawns.isEmpty() && EngenderConfig.mobs.useMobTalkerModels && EngenderConfig.mobs.breeding);
  }
  
  protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
    world.func_72942_c((Entity)new EntityLightningBolt(world, pos.func_177958_n(), (pos.func_177956_o() + 1), pos.func_177952_p(), false));
    world.func_72876_a((Entity)player, pos.func_177958_n(), (pos.func_177956_o() + 1), pos.func_177952_p(), 40.0F, false);
    List<EntityTameBase> girlList = Lists.newArrayList();
    List<Entity> entities = world.func_72839_b((Entity)player, (new AxisAlignedBB(pos)).func_186662_g(128.0D));
    for (Entity entity : entities) {
      if (entity instanceof EntityTameBase && ((EntityTameBase)entity).canBeMatedWith())
        girlList.add((EntityTameBase)entity); 
    } 
    if (!girlList.isEmpty())
      for (EntityTameBase girl : girlList) {
        if (girl.func_184191_r((Entity)player)) {
          if (!girl.func_70631_g_())
            girl.setGrowingAge(5000); 
          girl.setInLove(player);
          girl.func_70097_a(DamageSource.field_76377_j, 0.0F);
        } 
      }  
  }
  
  protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}
}
