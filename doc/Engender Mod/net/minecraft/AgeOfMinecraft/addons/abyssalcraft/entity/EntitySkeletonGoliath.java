package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
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
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySkeletonGoliath extends EntityTameBase implements Armored, Massive, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  public EntitySkeletonGoliath(World par1World) {
    super(par1World);
    func_70105_a(1.0F, 4.5F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 12.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isOffensive = true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySkeletonGoliath(this.field_70170_p);
  }
  
  public int getNextLevelRequirement() {
    return 1000;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 1.5F;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(40.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return super.func_70652_k(par1Entity);
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.3F;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187854_fc;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187864_fh;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187856_fd;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187868_fj, 0.15F, 1.0F);
  }
  
  protected Item func_146068_u() {
    return Items.field_151103_aS;
  }
  
  public ItemStack func_184586_b(EnumHand hand) {
    if (hand == EnumHand.MAIN_HAND)
      return new ItemStack(ACItems.cudgel); 
    return super.func_184586_b(hand);
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_SKELETON_GOLIATH;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void func_70636_d() {
    this.isOffensive = true;
    if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && !func_70631_g_() && !func_70045_F() && !isHero()) {
      float f = func_70013_c();
      if (f > 0.5F && this.field_70173_aa % (!func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() ? 80 : 10) == 0 && this.field_70170_p.func_175678_i(new BlockPos(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v))) {
        boolean flag = true;
        ItemStack itemstack = func_184582_a(EntityEquipmentSlot.HEAD);
        if (!itemstack.func_190926_b()) {
          if (itemstack.func_77984_f()) {
            itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
            if (itemstack.func_77952_i() >= itemstack.func_77958_k()) {
              func_70669_a(itemstack);
              func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
            } 
          } 
          flag = false;
        } 
        if (flag)
          func_70015_d(8); 
      } 
    } 
    super.func_70636_d();
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
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
        passenger.func_70107_b(this.field_70165_t - (f4 * 0.75F * getFittness()), this.field_70163_u + (2.7F * getFittness()), this.field_70161_v - (f11 * 0.75F * getFittness())); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t + (f4 * 0.75F * getFittness()), this.field_70163_u + (2.7F * getFittness()), this.field_70161_v + (f11 * 0.75F * getFittness())); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br / 3.0F;
      forward = entitylivingbase.field_191988_bg / 3.0F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(1.0D));
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity instanceof EntityLivingBase && !func_184191_r(entity) && !this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0)
          func_70652_k(entity); 
      } 
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
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    if (func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
      Calendar calendar1 = this.field_70170_p.func_83015_S();
      if (calendar1.get(2) + 1 == 10 && calendar1.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
}
