package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDreadguard extends EntityTameBase implements Armored {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  private int flameShootTimer;
  
  public EntityDreadguard(World par1World) {
    super(par1World);
    func_70105_a(0.75F, 2.9F);
    this.field_70178_ae = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 12.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 1000;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDreadguard(this.field_70170_p);
  }
  
  protected void dropEquipmentUndamaged() {}
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(240.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(120.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.func_76347_k())
      return false; 
    if (par1DamageSource.func_76346_g() instanceof net.minecraft.entity.projectile.EntityArrow)
      return false; 
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.dreadguard_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ACSounds.dreadguard_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.dreadguard_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.25F, 0.9F);
    func_184185_a(SoundEvents.field_187605_cG, 0.5F, (func_70631_g_() ? 1.5F : 1.0F) / getFittness());
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_DREADGUARD;
  }
  
  protected Item func_146068_u() {
    return ACItems.dreaded_shard_of_abyssalnite;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ACItems.dreaded_abyssalnite_helmet));
    func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(ACItems.dreaded_abyssalnite_chestplate));
    func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(ACItems.dreaded_abyssalnite_leggings));
    func_184201_a(EntityEquipmentSlot.FEET, new ItemStack(ACItems.dreaded_abyssalnite_boots));
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
      attribute.func_111121_a(attackDamageBoost);
      if (this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    return par1EntityLivingData;
  }
  
  public void func_70636_d() {
    if (this.field_70173_aa == 1)
      func_184185_a(ESound.amalgamate, 1.0F, 1.0F); 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) <= 64.0D && this.flameShootTimer <= (isHero() ? -100 : -200))
      this.flameShootTimer = 60; 
    if (this.flameShootTimer > 0) {
      this.field_70159_w *= 0.0D;
      this.field_70179_y *= 0.0D;
      func_70659_e(0.0F);
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
      if (this.field_70173_aa % 5 == 0)
        this.field_70170_p.func_184133_a(null, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + func_70047_e(), this.field_70161_v + 0.5D), ACSounds.dreadguard_barf, func_184176_by(), 0.5F + func_70681_au().nextFloat(), func_70681_au().nextFloat() * 0.5F + 0.2F); 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, target.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null && !func_184191_r((Entity)entity) && this.field_70146_Z.nextInt(3) == 0)
              if (entity.func_70097_a(AbyssalCraftAPI.dread, (float)(15.0D - func_70032_d((Entity)entity)))) {
                entity.func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
                entity.func_70015_d((int)(15.0F - func_70032_d((Entity)entity)));
              } else {
                func_70652_k((Entity)entity);
                entity.func_70015_d((int)(15.0F - func_70032_d((Entity)entity)));
              }  
          }  
        if (target.func_70097_a(AbyssalCraftAPI.dread, (float)(15.0D - func_70032_d(target)))) {
          if (target instanceof EntityLivingBase)
            ((EntityLivingBase)target).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
          target.func_70015_d((int)(15.0F - func_70032_d(target)));
        } else {
          func_70652_k(target);
          target.func_70015_d((int)(15.0F - func_70032_d(target)));
        } 
      } 
    } 
    this.flameShootTimer--;
    super.func_70636_d();
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 4.0D + this.field_70146_Z.nextDouble() * 8.0D;
    Vec3d srcVec = new Vec3d(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v);
    Vec3d lookVec = func_70676_i(1.0F);
    RayTraceResult raytrace = this.field_70170_p.func_72933_a(srcVec, srcVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range));
    BlockPos hitpos = (raytrace != null) ? raytrace.func_178782_a() : null;
    double rx = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70165_t - hitpos.func_177958_n()));
    double ry = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70163_u - hitpos.func_177956_o()));
    double rz = (hitpos == null) ? range : Math.min(range, Math.abs(this.field_70161_v - hitpos.func_177952_p()));
    Vec3d destVec = srcVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
    float var9 = 4.0F;
    List<Entity> possibleList = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72317_d(lookVec.field_72450_a * rx, lookVec.field_72448_b * ry, lookVec.field_72449_c * rz).func_72314_b(var9, var9, var9));
    double hitDist = 0.0D;
    for (Entity possibleEntity : possibleList) {
      if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !func_184191_r(possibleEntity)) {
        float borderSize = possibleEntity.func_70111_Y();
        AxisAlignedBB collisionBB = possibleEntity.func_174813_aQ().func_72314_b(borderSize, borderSize, borderSize);
        RayTraceResult interceptPos = collisionBB.func_72327_a(srcVec, destVec);
        if (collisionBB.func_72318_a(srcVec)) {
          if (0.0D < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = 0.0D;
          } 
          continue;
        } 
        if (interceptPos != null) {
          double possibleDist = srcVec.func_72438_d(interceptPos.field_72307_f);
          if (possibleDist < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = possibleDist;
          } 
        } 
      } 
    } 
    return pointedEntity;
  }
  
  protected void addMouthParticles() {
    if (this.field_70170_p.field_72995_K) {
      Vec3d vector = func_70040_Z();
      double px = this.field_70165_t;
      double py = this.field_70163_u + func_70047_e();
      double pz = this.field_70161_v;
      for (int i = 0; i < 5; i++) {
        double dx = vector.field_72450_a;
        double dy = vector.field_72448_b;
        double dz = vector.field_72449_c;
        double spread = 5.0D + func_70681_au().nextDouble() * 2.5D;
        double velocity = 0.5D + func_70681_au().nextDouble() * 0.5D;
        dx += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dy += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dz += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.func_150891_b(ACItems.dreaded_shard_of_abyssalnite) });
        this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.func_150891_b(ACItems.dread_fragment) });
      } 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 23) {
      addMouthParticles();
    } else {
      super.func_70103_a(id);
    } 
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.metalHitCrush;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_70631_g_()) {
      if (hasOwner(player)) {
        player.func_184609_a(EnumHand.MAIN_HAND);
        if (func_184187_bx() == null) {
          func_184205_a((Entity)player, true);
        } else {
          func_184210_p();
        } 
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 2);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t - (f4 * 0.75F * getFittness()), this.field_70163_u + (1.7F * getFittness()), this.field_70161_v - (f11 * 0.75F * getFittness())); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t + (f4 * 0.75F * getFittness()), this.field_70163_u + (1.7F * getFittness()), this.field_70161_v + (f11 * 0.75F * getFittness())); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 5.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (entitylivingbase.field_191988_bg > 0.0F && this.field_70173_aa % 7 == 0)
        func_180429_a(new BlockPos((Entity)this), this.field_70170_p.func_180495_p(new BlockPos((Entity)this)).func_177230_c()); 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
}
