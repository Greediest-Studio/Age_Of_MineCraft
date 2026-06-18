package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGatekeeperMinion extends EntityTameBase implements Armored, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  public EntityGatekeeperMinion(World par1World) {
    super(par1World);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIFireballAttack(this));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    func_70105_a(0.9F, 2.7F);
    this.isOffensive = true;
    this.field_70178_ae = true;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGatekeeperMinion(this.field_70170_p);
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
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
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(500.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(36.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(250.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(18.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    func_184609_a(EnumHand.MAIN_HAND);
    func_184609_a(EnumHand.OFF_HAND);
    boolean flag = super.func_70652_k(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.shadow_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    EntityLivingBase enemy = null;
    if (par1DamageSource.func_76346_g() instanceof EntityLivingBase && !func_184191_r(par1DamageSource.func_76346_g()))
      enemy = (EntityLivingBase)par1DamageSource.func_76346_g(); 
    if (this.field_70146_Z.nextInt(10) == 0) {
      List<EntityRemnant> remnants = this.field_70170_p.func_72872_a(EntityRemnant.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D));
      if (remnants != null && 
        enemy != null) {
        Iterator<EntityRemnant> iter = remnants.iterator();
        while (iter.hasNext())
          ((EntityRemnant)iter.next()).enrage(false, enemy); 
      } 
      func_184185_a(ACSounds.remnant_scream, 3.0F, 1.0F);
    } 
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  protected void func_70628_a(boolean par1, int par2) {
    ItemStack item = new ItemStack(ACItems.eldritch_scale);
    if (this.field_70146_Z.nextInt(10) == 0)
      item = new ItemStack(ACItems.ethaxium_ingot); 
    if (item != null) {
      int i = this.field_70146_Z.nextInt(3);
      if (par2 > 0)
        i += this.field_70146_Z.nextInt(par2 + 1); 
      for (int j = 0; j < i; j++)
        func_70099_a(item, 0.0F); 
    } 
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_MINION_OF_THE_GATEKEEPER;
  }
  
  public boolean func_70662_br() {
    return true;
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
  
  static class AIFireballAttack extends EntityAIFriendlyAttackMelee {
    private final EntityGatekeeperMinion blaze;
    
    private int attackStep;
    
    private int attackTime;
    
    public AIFireballAttack(EntityGatekeeperMinion blazeIn) {
      super(blazeIn, 1.0D, true);
      this.blaze = blazeIn;
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.blaze.func_70638_az();
      return (entitylivingbase != null && entitylivingbase.func_70089_S());
    }
    
    public void func_75249_e() {
      this.attackStep = 0;
    }
    
    public void func_75251_c() {
      super.func_75251_c();
    }
    
    public void func_75246_d() {
      this.attackTime--;
      EntityLivingBase entitylivingbase = this.blaze.func_70638_az();
      if (entitylivingbase != null) {
        double d0 = this.blaze.func_70068_e((Entity)entitylivingbase);
        if (d0 < getAttackReachSqr(entitylivingbase)) {
          if (this.attackTime <= 0) {
            this.attackTime = 20;
            this.blaze.func_70652_k((Entity)entitylivingbase);
          } 
        } else if (d0 < func_191523_f() * func_191523_f()) {
          double d1 = entitylivingbase.field_70165_t - this.blaze.field_70165_t;
          double d2 = entitylivingbase.field_70163_u + 0.5D - this.blaze.field_70163_u + (this.blaze.field_70131_O / 2.0F) + 0.5D;
          double d3 = entitylivingbase.field_70161_v - this.blaze.field_70161_v;
          if (this.attackTime <= 0) {
            this.attackStep++;
            if (this.attackStep == 1) {
              this.attackTime = 40;
            } else if (this.attackStep <= 4) {
              this.attackTime = 20;
            } else {
              this.attackTime = 60;
              this.attackStep = 0;
            } 
            if (this.attackStep > 1) {
              float f = MathHelper.func_76129_c(MathHelper.func_76133_a(d0)) * 0.5F;
              this.blaze.field_70170_p.func_180498_a((EntityPlayer)null, 1018, new BlockPos((int)this.blaze.field_70165_t, (int)this.blaze.field_70163_u, (int)this.blaze.field_70161_v), 0);
              this.blaze.func_184609_a(EnumHand.OFF_HAND);
              for (int i = 0; i < 1; i++) {
                EntityOmotholChargeOther entitysmallfireball = new EntityOmotholChargeOther(this.blaze.field_70170_p, (EntityLivingBase)this.blaze, d1, d2, d3);
                entitysmallfireball.field_70163_u = this.blaze.field_70163_u + (this.blaze.field_70131_O / 2.0F) + 0.5D;
                entitysmallfireball.func_184185_a(ACSounds.remnant_scream, 1.0F, 1.0F);
                this.blaze.field_70170_p.func_72838_d((Entity)entitysmallfireball);
              } 
            } 
          } 
          this.blaze.func_70671_ap().func_75651_a((Entity)entitylivingbase, this.blaze.func_184649_cE(), this.blaze.func_70646_bf());
        } 
      } 
      super.func_75246_d();
    }
    
    private double func_191523_f() {
      IAttributeInstance iattributeinstance = this.blaze.func_110148_a(SharedMonsterAttributes.field_111265_b);
      return (iattributeinstance == null) ? 16.0D : iattributeinstance.func_111126_e();
    }
  }
}
