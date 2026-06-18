package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import java.util.List;
import net.minecraft.AgeOfMinecraft.events.EngenderGeneralEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconChangeRitual extends NecronomiconRitual {
  public NecronomiconChangeRitual(String unlocalizedName, int bookType, float requiredEnergy, Object... offerings) {
    super(unlocalizedName, bookType, requiredEnergy, offerings);
  }
  
  public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
    List<EntityLiving> dreadSpawns = world.func_72872_a(EntityLiving.class, (new AxisAlignedBB(pos)).func_186662_g(24.0D));
    return !dreadSpawns.isEmpty();
  }
  
  protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
    List<EntityLiving> list = Lists.newArrayList();
    List<Entity> entities = world.func_72839_b((Entity)player, (new AxisAlignedBB(pos)).func_186662_g(128.0D));
    for (Entity entity : entities) {
      if (entity instanceof EntityLiving)
        list.add((EntityLiving)entity); 
    } 
    world.func_175669_a(1023, pos, 0);
    if (!list.isEmpty())
      for (EntityLiving mob : list)
        EngenderGeneralEvent.changeMob(world, pos, (EntityLivingBase)mob);  
  }
  
  protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}
}
