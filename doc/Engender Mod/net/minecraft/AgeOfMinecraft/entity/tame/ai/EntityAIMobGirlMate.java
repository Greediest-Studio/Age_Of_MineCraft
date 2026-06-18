package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIMobGirlMate extends EntityAIBase {
  private final EntityTameBase theAnimal;
  
  World world;
  
  int spawnBabyDelay;
  
  double moveSpeed;
  
  public EntityAIMobGirlMate(EntityTameBase animal, double speedIn) {
    this.theAnimal = animal;
    this.world = animal.field_70170_p;
    this.moveSpeed = speedIn;
    func_75248_a(3);
  }
  
  public boolean func_75250_a() {
    if (!this.theAnimal.isInLove())
      return false; 
    if (this.theAnimal.isWild())
      return false; 
    if (!EngenderConfig.mobs.useMobTalkerModels)
      return false; 
    if (!EngenderConfig.mobs.breeding)
      return false; 
    return (this.theAnimal.canBeMatedWith() && !this.theAnimal.func_70631_g_() && this.theAnimal.getOwner() != null);
  }
  
  public boolean func_75253_b() {
    return (this.theAnimal.getOwner().func_70089_S() && this.theAnimal.isInLove() && this.spawnBabyDelay < 200);
  }
  
  public void func_75251_c() {
    this.spawnBabyDelay = 0;
  }
  
  public void func_75246_d() {
    if (this.theAnimal.func_70068_e((Entity)this.theAnimal.getOwner()) < 2.0D) {
      if ((this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast || this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube) && !this.theAnimal.getOwner().func_70644_a(MobEffects.field_76426_n))
        this.theAnimal.getOwner().func_70015_d(10); 
      if (this.spawnBabyDelay % ((this.spawnBabyDelay >= 100) ? 5 : 10) == 0) {
        this.theAnimal.field_70172_ad = 0;
        this.theAnimal.func_70097_a(new DamageSource("outOfWorld"), 0.01F);
      } 
      Vec3d vec3 = this.theAnimal.getOwner().func_70676_i(1.0F);
      this.theAnimal.func_70107_b((this.theAnimal.getOwner()).field_70165_t + vec3.field_72450_a * 0.5D, (this.theAnimal.getOwner()).field_70163_u, (this.theAnimal.getOwner()).field_70161_v + vec3.field_72449_c * 0.5D);
      this.theAnimal.field_70760_ar = this.theAnimal.field_70761_aq = this.theAnimal.field_70126_B = this.theAnimal.field_70177_z = this.theAnimal.field_70758_at = this.theAnimal.field_70759_as = (this.theAnimal.getOwner()).field_70177_z;
      this.theAnimal.field_70127_C = this.theAnimal.field_70125_A = (this.theAnimal.getOwner()).field_70125_A;
      this.spawnBabyDelay++;
      if (this.spawnBabyDelay >= 200)
        spawnBaby(); 
    } else {
      this.theAnimal.func_70671_ap().func_75651_a((Entity)this.theAnimal.getOwner(), 10.0F, this.theAnimal.func_70646_bf());
      this.theAnimal.func_70661_as().func_75497_a((Entity)this.theAnimal.getOwner(), this.moveSpeed);
      this.theAnimal.func_70605_aq().func_75642_a((this.theAnimal.getOwner()).field_70165_t, (this.theAnimal.getOwner()).field_70163_u, (this.theAnimal.getOwner()).field_70161_v, this.moveSpeed);
    } 
  }
  
  private EntityPlayer getNearbyMate() {
    List<EntityPlayer> list = this.world.func_72872_a(EntityPlayer.class, this.theAnimal.func_174813_aQ().func_186662_g(8.0D));
    double d0 = Double.MAX_VALUE;
    EntityPlayer entityanimal = null;
    for (EntityPlayer entityanimal1 : list) {
      if (this.theAnimal.canBeMatedWith() && this.theAnimal.func_70068_e((Entity)entityanimal1) < d0 && this.theAnimal.func_184191_r((Entity)entityanimal1)) {
        entityanimal = entityanimal1;
        d0 = this.theAnimal.func_70068_e((Entity)entityanimal1);
      } 
    } 
    return entityanimal;
  }
  
  private void spawnBaby() {
    if (this.theAnimal.getOwner() != null) {
      EntityPlayer entityplayer = (EntityPlayer)this.theAnimal.getOwner();
      if (entityplayer == null)
        entityplayer = (EntityPlayer)this.theAnimal.getOwner(); 
      this.theAnimal.setGrowingAge(0);
      this.theAnimal.resetInLove();
      this.theAnimal.createChild();
      this.theAnimal.setSitResting(false);
      Random random = this.theAnimal.func_70681_au();
      for (int i = 0; i < 100; i++) {
        double d0 = random.nextGaussian() * 0.02D;
        double d1 = random.nextGaussian() * 0.02D;
        double d2 = random.nextGaussian() * 0.02D;
        double d3 = random.nextDouble() * this.theAnimal.field_70130_N * 2.0D - this.theAnimal.field_70130_N;
        double d4 = 0.5D + random.nextDouble() * this.theAnimal.field_70131_O;
        double d5 = random.nextDouble() * this.theAnimal.field_70130_N * 2.0D - this.theAnimal.field_70130_N;
        this.world.func_175688_a(EnumParticleTypes.HEART, this.theAnimal.field_70165_t + d3, this.theAnimal.field_70163_u + d4, this.theAnimal.field_70161_v + d5, d0, d1, d2, new int[0]);
      } 
      if (this.world.func_82736_K().func_82766_b("doMobLoot"))
        this.world.func_72838_d((Entity)new EntityXPOrb(this.world, this.theAnimal.field_70165_t, this.theAnimal.field_70163_u, this.theAnimal.field_70161_v, random.nextInt(15) + 5 + (int)this.theAnimal.field_70131_O + (int)this.theAnimal.field_70130_N + (int)this.theAnimal.func_70047_e())); 
    } 
  }
}
