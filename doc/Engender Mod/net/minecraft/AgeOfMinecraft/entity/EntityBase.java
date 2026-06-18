package net.minecraft.AgeOfMinecraft.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityBase extends EntityCreature {
  public EntityBase(World worldIn) {
    super(worldIn);
  }
  
  public int playMusic() {
    return 0;
  }
}
