package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Structure;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShulker extends EntityTameBase implements Armored, Structure, Ender {
  private static final UUID COVERED_ARMOR_BONUS_ID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
  
  private static final AttributeModifier COVERED_ARMOR_BONUS_MODIFIER = (new AttributeModifier(COVERED_ARMOR_BONUS_ID, "Covered armor bonus", 20.0D, 0)).func_111168_a(false);
  
  protected static final DataParameter<EnumFacing> ATTACHED_FACE = EntityDataManager.func_187226_a(EntityShulker.class, DataSerializers.field_187202_l);
  
  protected static final DataParameter<Optional<BlockPos>> ATTACHED_BLOCK_POS = EntityDataManager.func_187226_a(EntityShulker.class, DataSerializers.field_187201_k);
  
  protected static final DataParameter<Byte> PEEK_TICK = EntityDataManager.func_187226_a(EntityShulker.class, DataSerializers.field_187191_a);
  
  protected static final DataParameter<Byte> COLOR = EntityDataManager.func_187226_a(EntityShulker.class, DataSerializers.field_187191_a);
  
  public static final EnumDyeColor DEFAULT_COLOR = EnumDyeColor.PURPLE;
  
  private float prevPeekAmount;
  
  private float peekAmount;
  
  private BlockPos currentAttachmentPosition;
  
  private int clientSideTeleportInterpolation;
  
  public EntityShulker(World p_i46779_1_) {
    super(p_i46779_1_);
    func_70105_a(1.0F, 1.0F);
    this.field_70760_ar = 180.0F;
    this.field_70761_aq = 180.0F;
    this.isOffensive = true;
    this.field_70178_ae = true;
    this.currentAttachmentPosition = null;
    this.field_70728_aV = 10;
    this.field_70714_bg.func_75776_a(0, new AIAttack());
    this.field_70714_bg.func_75776_a(7, new AIPeek());
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    if (getAttachmentPos() == null)
      setAttachmentPos(func_180425_c().func_177977_b()); 
  }
  
  public String getDescName() {
    return "shulker";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 1.1F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public float getDefaultAgilityStat() {
    return 1.0F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityShulker(this.field_70170_p);
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    this.field_70761_aq = 180.0F;
    this.field_70760_ar = 180.0F;
    this.field_70177_z = 180.0F;
    this.field_70126_B = 180.0F;
    this.field_70759_as = 180.0F;
    this.field_70758_at = 180.0F;
    setColor(DEFAULT_COLOR);
    return super.func_180482_a(difficulty, livingdata);
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.ENDER;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187773_eO;
  }
  
  public void func_70642_aH() {
    if (!isClosed())
      super.func_70642_aH(); 
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187781_eS;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return isClosed() ? SoundEvents.field_187785_eU : SoundEvents.field_187783_eT;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ATTACHED_FACE, EnumFacing.DOWN);
    this.field_70180_af.func_187214_a(ATTACHED_BLOCK_POS, Optional.absent());
    this.field_70180_af.func_187214_a(PEEK_TICK, Byte.valueOf((byte)0));
    this.field_70180_af.func_187214_a(COLOR, Byte.valueOf((byte)DEFAULT_COLOR.func_176765_a()));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
  }
  
  protected EntityBodyHelper func_184650_s() {
    return new BodyHelper((EntityLivingBase)this);
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    this.field_70180_af.func_187227_b(ATTACHED_FACE, EnumFacing.func_82600_a(compound.func_74771_c("AttachFace")));
    this.field_70180_af.func_187227_b(PEEK_TICK, Byte.valueOf(compound.func_74771_c("Peek")));
    this.field_70180_af.func_187227_b(COLOR, Byte.valueOf(compound.func_74771_c("Color")));
    if (compound.func_74764_b("APX")) {
      int i = compound.func_74762_e("APX");
      int j = compound.func_74762_e("APY");
      int k = compound.func_74762_e("APZ");
      this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
    } else {
      this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.absent());
    } 
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74774_a("AttachFace", (byte)((EnumFacing)this.field_70180_af.func_187225_a(ATTACHED_FACE)).func_176745_a());
    compound.func_74774_a("Peek", ((Byte)this.field_70180_af.func_187225_a(PEEK_TICK)).byteValue());
    compound.func_74774_a("Color", ((Byte)this.field_70180_af.func_187225_a(COLOR)).byteValue());
    BlockPos blockpos = getAttachmentPos();
    if (blockpos != null) {
      compound.func_74768_a("APX", blockpos.func_177958_n());
      compound.func_74768_a("APY", blockpos.func_177956_o());
      compound.func_74768_a("APZ", blockpos.func_177952_p());
    } 
  }
  
  public EnumDyeColor getColor() {
    return EnumDyeColor.func_176764_b(((Byte)this.field_70180_af.func_187225_a(COLOR)).byteValue());
  }
  
  public void setColor(EnumDyeColor color) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(COLOR)).byteValue();
    this.field_70180_af.func_187227_b(COLOR, Byte.valueOf((byte)(b0 & 0xF0 | color.func_176765_a() & 0xF)));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (stack != null && stack.func_77973_b() == Items.field_151100_aR && hasOwner(player)) {
      EnumDyeColor enumdyecolor = EnumDyeColor.func_176766_a(stack.func_77960_j());
      if (getColor() != enumdyecolor) {
        func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i());
        player.func_184609_a(hand);
        if (!this.field_70170_p.field_72995_K) {
          func_70099_a(new ItemStack(Items.field_151100_aR, 1, getColor().func_176767_b()), 1.0F);
          setColor(enumdyecolor);
          stack.func_190918_g(1);
        } 
      } 
      return true;
    } 
    if (stack != null && stack.func_77973_b() == Items.field_151152_bP && hasOwner(player)) {
      func_184185_a(SoundEvents.field_187649_bu, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        if (!func_184614_ca().func_190926_b())
          func_70099_a(func_184614_ca(), 1.0F); 
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    return false;
  }
  
  public void func_70071_h_() {
    ItemStack charge = new ItemStack(Items.field_151100_aR, 1, getColor().func_176767_b());
    charge.func_151001_c("Color: " + getColor().func_176610_l());
    this.basicInventory.func_70299_a(7, charge);
    super.func_70071_h_();
    if (this.field_70725_aQ > 0)
      this.field_70181_x = -0.6D; 
    if (this.field_70725_aQ == 1) {
      func_184185_a(ESound.buildingDeath, 2.0F, 1.0F);
      for (int k = 0; k < 200; k++) {
        double d2 = this.field_70146_Z.nextGaussian() * 0.1D;
        double d0 = this.field_70146_Z.nextGaussian() * 0.1D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.1D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 2.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, -0.25D, d1, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 2.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, -0.25D, d1, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O * 2.0F), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, 0.25D, d1, new int[0]);
      } 
    } 
    BlockPos blockpos = (BlockPos)((Optional)this.field_70180_af.func_187225_a(ATTACHED_BLOCK_POS)).orNull();
    if (blockpos == null && !this.field_70170_p.field_72995_K) {
      blockpos = new BlockPos((Entity)this);
      this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.of(blockpos));
    } 
    if (!isWild() && func_70068_e((Entity)getOwner()) >= 576.0D)
      teleportTo((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v); 
    if (func_184218_aH()) {
      blockpos = null;
      float f = (func_184187_bx()).field_70177_z;
      this.field_70177_z = f;
      this.field_70761_aq = f;
      this.field_70760_ar = f;
      this.clientSideTeleportInterpolation = 0;
    } else if (!this.field_70170_p.field_72995_K) {
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
      if (iblockstate.func_185904_a() != Material.field_151579_a)
        if (iblockstate.func_177230_c() == Blocks.field_180384_M) {
          EnumFacing enumfacing = (EnumFacing)iblockstate.func_177229_b((IProperty)BlockPistonBase.field_176387_N);
          if (this.field_70170_p.func_175623_d(blockpos.func_177972_a(enumfacing))) {
            blockpos = blockpos.func_177972_a(enumfacing);
            this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.of(blockpos));
          } else {
            teleportShulkerToBlock();
          } 
        } else if (iblockstate.func_177230_c() == Blocks.field_150332_K) {
          EnumFacing enumfacing3 = (EnumFacing)iblockstate.func_177229_b((IProperty)BlockPistonExtension.field_176387_N);
          if (this.field_70170_p.func_175623_d(blockpos.func_177972_a(enumfacing3))) {
            blockpos = blockpos.func_177972_a(enumfacing3);
            this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.of(blockpos));
          } else {
            teleportShulkerToBlock();
          } 
        } else {
          teleportShulkerToBlock();
        }  
      BlockPos blockpos1 = blockpos.func_177972_a(getAttachmentFacing());
      if (!this.field_70170_p.func_175677_d(blockpos1, false)) {
        boolean flag = false;
        for (EnumFacing enumfacing1 : EnumFacing.values()) {
          blockpos1 = blockpos.func_177972_a(enumfacing1);
          if (this.field_70170_p.func_175677_d(blockpos1, false)) {
            this.field_70180_af.func_187227_b(ATTACHED_FACE, enumfacing1);
            flag = true;
            break;
          } 
        } 
        if (!flag)
          teleportShulkerToBlock(); 
      } 
      BlockPos blockpos2 = blockpos.func_177972_a(getAttachmentFacing().func_176734_d());
      if (this.field_70170_p.func_175677_d(blockpos2, false))
        teleportShulkerToBlock(); 
    } 
    float f1 = getPeekTick() * 0.01F;
    this.prevPeekAmount = this.peekAmount;
    if (this.peekAmount > f1) {
      this.peekAmount = MathHelper.func_76131_a(this.peekAmount - 0.05F, f1, 1.0F);
    } else if (this.peekAmount < f1) {
      this.peekAmount = MathHelper.func_76131_a(this.peekAmount + 0.05F, 0.0F, f1);
    } 
    if (blockpos != null && this.field_70170_p.func_180495_p(blockpos) != Blocks.field_150350_a) {
      if (this.field_70170_p.field_72995_K)
        if (this.clientSideTeleportInterpolation > 0 && this.currentAttachmentPosition != null) {
          this.clientSideTeleportInterpolation--;
        } else {
          this.currentAttachmentPosition = blockpos;
        }  
      if (func_70089_S() && this.field_70170_p.func_180495_p(blockpos) != Blocks.field_150350_a) {
        this.field_70142_S = this.field_70169_q = this.field_70165_t = blockpos.func_177958_n() + 0.5D;
        this.field_70137_T = this.field_70167_r = this.field_70163_u = blockpos.func_177956_o();
        this.field_70136_U = this.field_70166_s = this.field_70161_v = blockpos.func_177952_p() + 0.5D;
      } 
      double d3 = 0.5D - MathHelper.func_76126_a((0.5F + this.peekAmount) * 3.1415927F) * 0.5D;
      double d4 = 0.5D - MathHelper.func_76126_a((0.5F + this.prevPeekAmount) * 3.1415927F) * 0.5D;
      double d5 = d3 - d4;
      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      if (d5 > 0.0D) {
        List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ());
        if (!list.isEmpty())
          for (Entity entity : list) {
            if (!(entity instanceof EntityShulker) && !entity.field_70145_X)
              entity.func_70091_d(MoverType.SHULKER, d0, d1, d2); 
          }  
      } 
      if (func_70631_g_()) {
        float f = 0.0F;
        if (f > f1) {
          f = MathHelper.func_76131_a(f - 0.05F, f1, 1.0F);
        } else if (f < f1) {
          f = MathHelper.func_76131_a(f + 0.05F, 0.0F, f1);
        } 
        d3 = 0.25D - MathHelper.func_76126_a((0.5F + f) * 3.1415927F) * 0.25D;
        EnumFacing enumfacing2 = getAttachmentFacing();
        switch (enumfacing2) {
          default:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.25D, this.field_70163_u, this.field_70161_v - 0.25D, this.field_70165_t + 0.25D, this.field_70163_u + 0.5D + d3, this.field_70161_v + 0.25D));
            return;
          case ORANGE:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.25D, this.field_70163_u - d3 + 0.5D, this.field_70161_v - 0.25D, this.field_70165_t + 0.25D, this.field_70163_u + 1.0D, this.field_70161_v + 0.25D));
            return;
          case MAGENTA:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.25D, this.field_70163_u + 0.25D, this.field_70161_v - 0.5D, this.field_70165_t + 0.25D, this.field_70163_u + 0.75D, this.field_70161_v + 0.0D + d3));
            return;
          case LIGHT_BLUE:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.25D, this.field_70163_u + 0.25D, this.field_70161_v - 0.0D - d3, this.field_70165_t + 0.25D, this.field_70163_u + 0.75D, this.field_70161_v + 0.5D));
            return;
          case YELLOW:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u + 0.25D, this.field_70161_v - 0.25D, this.field_70165_t + 0.0D + d3, this.field_70163_u + 0.75D, this.field_70161_v + 0.25D));
            return;
          case LIME:
            break;
        } 
        func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.0D - d3, this.field_70163_u + 0.25D, this.field_70161_v - 0.25D, this.field_70165_t + 0.5D, this.field_70163_u + 0.75D, this.field_70161_v + 0.25D));
      } else {
        EnumFacing enumfacing2 = getAttachmentFacing();
        switch (enumfacing2) {
          case WHITE:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D + d3, this.field_70161_v + 0.5D));
            d1 = d5;
            break;
          case ORANGE:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u - d3, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d1 = -d5;
            break;
          case MAGENTA:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D + d3));
            d2 = d5;
            break;
          case LIGHT_BLUE:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D - d3, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d2 = -d5;
            break;
          case YELLOW:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D + d3, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d0 = d5;
            break;
          case LIME:
            func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D - d3, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d0 = -d5;
            break;
        } 
        if (d5 > 0.0D) {
          List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ());
          if (!list.isEmpty())
            for (Entity entity : list) {
              if (!(entity instanceof EntityShulker) && !entity.field_70145_X)
                entity.func_70091_d(MoverType.SHULKER, d0, d1, d2); 
            }  
        } 
      } 
    } 
  }
  
  public void func_70107_b(double x, double y, double z) {
    super.func_70107_b(x, y, z);
    if (this.field_70180_af != null && func_70089_S()) {
      Optional<BlockPos> optional = (Optional<BlockPos>)this.field_70180_af.func_187225_a(ATTACHED_BLOCK_POS);
      Optional<BlockPos> optional1 = Optional.of(new BlockPos(x, y, z));
      if (!optional1.equals(optional)) {
        this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, optional1);
        this.field_70180_af.func_187227_b(PEEK_TICK, Byte.valueOf((byte)0));
        this.field_70160_al = true;
      } 
    } 
  }
  
  public boolean teleportTo(double x, double y, double z) {
    this.currentAttachmentPosition = null;
    this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187791_eX, func_184176_by(), func_70599_aP(), 0.95F);
    func_184185_a(SoundEvents.field_187791_eX, func_70599_aP(), 0.95F);
    return true;
  }
  
  protected boolean teleportShulkerToBlock() {
    if (!func_175446_cd() && func_70089_S() && !func_184218_aH()) {
      BlockPos blockpos = new BlockPos((Entity)this);
      for (int i = 0; i < 5; i++) {
        BlockPos blockpos1 = blockpos.func_177982_a(16 - this.field_70146_Z.nextInt(32), 16 - this.field_70146_Z.nextInt(32), 16 - this.field_70146_Z.nextInt(32));
        if (blockpos1.func_177956_o() > 0 && this.field_70170_p.func_175623_d(blockpos1) && this.field_70170_p.func_191503_g((Entity)this) && this.field_70170_p.func_184144_a((Entity)this, new AxisAlignedBB(blockpos1)).isEmpty()) {
          boolean flag = false;
          for (EnumFacing enumfacing : EnumFacing.values()) {
            if (this.field_70170_p.func_175677_d(blockpos1.func_177972_a(enumfacing), false)) {
              this.field_70180_af.func_187227_b(ATTACHED_FACE, enumfacing);
              flag = true;
              break;
            } 
          } 
          if (flag) {
            func_184185_a(SoundEvents.field_187791_eX, func_70599_aP(), 0.95F);
            this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.of(blockpos1));
            this.field_70180_af.func_187227_b(PEEK_TICK, Byte.valueOf((byte)0));
            func_70624_b((EntityLivingBase)null);
            return true;
          } 
        } 
      } 
      return false;
    } 
    return true;
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(48.0D, 48.0D, 48.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity != null)
          if (!func_184191_r((Entity)entity)) {
            EntityShulkerBulletFriendly entityshulkerbullet = new EntityShulkerBulletFriendly(this.field_70170_p, this, (Entity)entity, getAttachmentFacing().func_176740_k());
            this.field_70170_p.func_72838_d((Entity)entityshulkerbullet);
            func_184185_a(SoundEvents.field_187789_eW, func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 0.7F);
          }  
      }  
    setSpecialAttackTimer(800);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAttachedToBlock() {
    return (this.currentAttachmentPosition != null && getAttachmentPos() != null);
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (!getCurrentBook().func_190926_b())
      updateArmorModifier(100); 
    if (func_70089_S() && this.field_70170_p.func_180495_p(getAttachmentPos()) != Blocks.field_150350_a) {
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
    } 
    if (this.field_70170_p.func_180495_p(getAttachmentPos()) == Blocks.field_150350_a && !func_184218_aH())
      teleportShulkerToBlock(); 
    if (!isWild())
      if (func_70068_e((Entity)getOwner()) >= 2304.0D) {
        this.field_70173_aa = 0;
        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, func_184176_by(), func_70599_aP(), 2.0F / this.field_70131_O);
        func_184185_a(SoundEvents.field_187534_aX, func_70599_aP(), 2.0F / this.field_70131_O);
        func_70661_as().func_75499_g();
        func_70012_b((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, this.field_70177_z, this.field_70125_A);
      }  
    this.field_70760_ar = this.field_70761_aq = this.field_70177_z = 180.0F;
    this.field_70160_al = false;
    this.field_70122_E = true;
    func_70031_b(false);
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty() && this.field_70173_aa % 60 == 0 && isPositiveShulker() && !func_175446_cd())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (!this.field_70170_p.field_72995_K && entity != null && func_184191_r((Entity)entity) && this.field_70146_Z.nextInt(60) == 0 && !(entity instanceof net.minecraft.entity.monster.IMob)) {
          EntityShulkerBulletFriendly entityshulkerbullet = new EntityShulkerBulletFriendly(this.field_70170_p, this, (Entity)entity, getAttachmentFacing().func_176740_k());
          this.field_70170_p.func_72838_d((Entity)entityshulkerbullet);
          func_184185_a(SoundEvents.field_187789_eW, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
          break;
        } 
      }  
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (ATTACHED_BLOCK_POS.equals(key) && this.field_70170_p.field_72995_K && !func_184218_aH()) {
      BlockPos blockpos = getAttachmentPos();
      if (blockpos != null) {
        if (this.currentAttachmentPosition == null) {
          this.currentAttachmentPosition = blockpos;
        } else {
          this.clientSideTeleportInterpolation = 6;
        } 
        if (func_70089_S() && this.field_70170_p.func_180495_p(getAttachmentPos()) != Blocks.field_150350_a) {
          this.field_70142_S = this.field_70169_q = this.field_70165_t = blockpos.func_177958_n() + 0.5D;
          this.field_70137_T = this.field_70167_r = this.field_70163_u = blockpos.func_177956_o();
          this.field_70136_U = this.field_70166_s = this.field_70161_v = blockpos.func_177952_p() + 0.5D;
        } 
      } 
    } 
    super.func_184206_a(key);
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
    this.field_70716_bi = 0;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (isClosed()) {
      Entity entity = source.func_76346_g();
      if (entity instanceof net.minecraft.entity.projectile.EntityArrow)
        return false; 
    } 
    if (super.func_70097_a(source, amount)) {
      if (this.field_70146_Z.nextInt(3) == 0)
        updateArmorModifier(0); 
      if (func_110143_aJ() < func_110138_aP() * 0.5D)
        teleportShulkerToBlock(); 
      return true;
    } 
    return false;
  }
  
  private boolean isClosed() {
    return (getPeekTick() == 0);
  }
  
  public AxisAlignedBB func_70046_E() {
    return func_70089_S() ? func_174813_aQ() : null;
  }
  
  public EnumFacing getAttachmentFacing() {
    return (EnumFacing)this.field_70180_af.func_187225_a(ATTACHED_FACE);
  }
  
  @Nullable
  public BlockPos getAttachmentPos() {
    return (BlockPos)((Optional)this.field_70180_af.func_187225_a(ATTACHED_BLOCK_POS)).orNull();
  }
  
  public void setAttachmentPos(@Nullable BlockPos pos) {
    this.field_70180_af.func_187227_b(ATTACHED_BLOCK_POS, Optional.fromNullable(pos));
  }
  
  public int getPeekTick() {
    return ((Byte)this.field_70180_af.func_187225_a(PEEK_TICK)).byteValue();
  }
  
  public void updateArmorModifier(int p_184691_1_) {
    if (!this.field_70170_p.field_72995_K) {
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111124_b(COVERED_ARMOR_BONUS_MODIFIER);
      if (p_184691_1_ == 0) {
        func_110148_a(SharedMonsterAttributes.field_188791_g).func_111121_a(COVERED_ARMOR_BONUS_MODIFIER);
        if (getCurrentBook().func_190926_b())
          func_184185_a(SoundEvents.field_187779_eR, 1.0F, 1.0F); 
      } else if (getCurrentBook().func_190926_b()) {
        func_184185_a(SoundEvents.field_187787_eV, 1.0F, 1.0F);
      } 
    } 
    this.field_70180_af.func_187227_b(PEEK_TICK, Byte.valueOf((byte)p_184691_1_));
  }
  
  @SideOnly(Side.CLIENT)
  public float getClientPeekAmount(float p_184688_1_) {
    return this.prevPeekAmount + (this.peekAmount - this.prevPeekAmount) * p_184688_1_;
  }
  
  @SideOnly(Side.CLIENT)
  public int getClientTeleportInterp() {
    return this.clientSideTeleportInterpolation;
  }
  
  @SideOnly(Side.CLIENT)
  public BlockPos getOldAttachPos() {
    return this.currentAttachmentPosition;
  }
  
  public float func_70047_e() {
    return func_70631_g_() ? ((getAttachmentFacing() == EnumFacing.UP) ? 0.75F : ((getAttachmentFacing() == EnumFacing.DOWN) ? 0.25F : 0.5F)) : 0.5F;
  }
  
  public int func_70646_bf() {
    return 180;
  }
  
  public void func_70108_f(Entity entityIn) {
    teleportShulkerToBlock();
  }
  
  public float func_70111_Y() {
    return 0.0F;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    switch (getColor()) {
      default:
        return ELoot.ENTITIES_SHULKER_WHITE;
      case ORANGE:
        return ELoot.ENTITIES_SHULKER_ORANGE;
      case MAGENTA:
        return ELoot.ENTITIES_SHULKER_MAGENTA;
      case LIGHT_BLUE:
        return ELoot.ENTITIES_SHULKER_LIGHT_BLUE;
      case YELLOW:
        return ELoot.ENTITIES_SHULKER_YELLOW;
      case LIME:
        return ELoot.ENTITIES_SHULKER_LIME;
      case PINK:
        return ELoot.ENTITIES_SHULKER_PINK;
      case GRAY:
        return ELoot.ENTITIES_SHULKER_GRAY;
      case SILVER:
        return ELoot.ENTITIES_SHULKER_SILVER;
      case CYAN:
        return ELoot.ENTITIES_SHULKER_CYAN;
      case PURPLE:
        return ELoot.ENTITIES_SHULKER_PURPLE;
      case BLUE:
        return ELoot.ENTITIES_SHULKER_BLUE;
      case BROWN:
        return ELoot.ENTITIES_SHULKER_BROWN;
      case GREEN:
        return ELoot.ENTITIES_SHULKER_GREEN;
      case RED:
        return ELoot.ENTITIES_SHULKER_RED;
      case BLACK:
        break;
    } 
    return ELoot.ENTITIES_SHULKER_BLACK;
  }
  
  public void inflictShulkerEffects(EntityLivingBase entity) {
    if (!this.field_70170_p.field_72995_K) {
      switch (getColor()) {
        case WHITE:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76441_p, 2400, 0);
          return;
        case ORANGE:
          entity.func_70015_d(15);
          return;
        case MAGENTA:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76431_k, 400, 0);
          if (entity.func_70662_br()) {
            inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76432_h, 1, 0);
          } else {
            inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76433_i, 1, 1);
          } 
          return;
        case LIGHT_BLUE:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_188425_z, 2400, 0);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76420_g, 1200, 1);
          return;
        case YELLOW:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76426_n, 2400, 0);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76422_e, 1200, 0);
          return;
        case LIME:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76430_j, 2400, 1);
          return;
        case PINK:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76429_m, 2400, 0);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76428_l, 600, 1);
          return;
        case GRAY:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76440_q, 400, 0);
          return;
        case SILVER:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76421_d, 200, 1);
          return;
        case CYAN:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76439_r, 2400, 0);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76424_c, 2400, 1);
          return;
        case BLUE:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76427_o, 2400, 0);
          return;
        case BROWN:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76419_f, 200, 1);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76437_t, 200, 0);
          return;
        case GREEN:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76436_u, 200, 1);
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76438_s, 200, 0);
          return;
        case RED:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76443_y, 1, 1);
          if (entity.func_70662_br()) {
            inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76433_i, 1, 1);
          } else {
            inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_76432_h, 1, 1);
          } 
          return;
        case BLACK:
          inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_82731_v, 200, 1);
          return;
      } 
      inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, entity, MobEffects.field_188424_y, 200, 0);
    } 
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    inflictShulkerEffects((EntityLivingBase)p_70652_1_);
    return func_184191_r(p_70652_1_) ? true : super.func_70652_k(p_70652_1_);
  }
  
  public boolean isPositiveShulker() {
    switch (getColor()) {
      case WHITE:
      case LIGHT_BLUE:
      case YELLOW:
      case LIME:
      case PINK:
      case CYAN:
      case BLUE:
      case RED:
        return true;
    } 
    return false;
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
  
  class AIAttack extends EntityAIBase {
    private int field_188520_b;
    
    public AIAttack() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = EntityShulker.this.func_70638_az();
      return (!EntityShulker.this.isPositiveShulker() && entitylivingbase != null && entitylivingbase.func_70089_S());
    }
    
    public void func_75249_e() {
      EntityShulker.this.updateArmorModifier(100);
      this.field_188520_b = 60;
    }
    
    public void func_75251_c() {
      EntityShulker.this.updateArmorModifier(0);
      EntityLivingBase entitylivingbase = EntityShulker.this.func_70638_az();
      if (entitylivingbase != null && !entitylivingbase.func_70089_S())
        EntityShulker.this.func_70624_b(null); 
    }
    
    public void func_75246_d() {
      this.field_188520_b--;
      EntityLivingBase entitylivingbase = EntityShulker.this.func_70638_az();
      EntityShulker.this.func_70671_ap().func_75651_a((Entity)entitylivingbase, 180.0F, 180.0F);
      double d0 = EntityShulker.this.func_70068_e((Entity)entitylivingbase);
      if (d0 < 2000.0D) {
        if (this.field_188520_b <= 0 && EntityShulker.this.func_70685_l((Entity)entitylivingbase)) {
          if (EntityShulker.this.moralRaisedTimer > 200) {
            this.field_188520_b = 5;
          } else {
            this.field_188520_b = 20 + EntityShulker.this.field_70146_Z.nextInt(10) * 20 / 2;
          } 
          if (d0 < 16.0D) {
            EntityShulker.this.func_184185_a(SoundEvents.field_187789_eW, 1.0F, (EntityShulker.this.field_70146_Z.nextFloat() - EntityShulker.this.field_70146_Z.nextFloat()) * 0.2F + 1.25F);
            EntityShulker.this.func_70652_k((Entity)entitylivingbase);
            if (d0 < 12.0D)
              EntityShulker.this.teleportShulkerToBlock(); 
          } else {
            EntityShulkerBulletFriendly entityshulkerbullet = new EntityShulkerBulletFriendly(EntityShulker.this.field_70170_p, EntityShulker.this, (Entity)entitylivingbase, EntityShulker.this.getAttachmentFacing().func_176740_k());
            EntityShulker.this.field_70170_p.func_72838_d((Entity)entityshulkerbullet);
            EntityShulker.this.func_184185_a(SoundEvents.field_187789_eW, 1.0F, (EntityShulker.this.field_70146_Z.nextFloat() - EntityShulker.this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
          } 
        } 
      } else {
        EntityShulker.this.func_70624_b((EntityLivingBase)null);
        func_75251_c();
      } 
      super.func_75246_d();
    }
  }
  
  class AIPeek extends EntityAIBase {
    private int field_188522_b;
    
    private AIPeek() {}
    
    public boolean func_75250_a() {
      return ((EntityShulker.this.func_70638_az() == null && EntityShulker.this.field_70146_Z.nextInt(20) == 0) || EntityShulker.this.func_70093_af());
    }
    
    public boolean func_75253_b() {
      return (EntityShulker.this.func_70638_az() == null && this.field_188522_b > 0);
    }
    
    public void func_75249_e() {
      if (EntityShulker.this.func_70093_af()) {
        this.field_188522_b = 100;
      } else {
        this.field_188522_b = 40 * (1 + EntityShulker.this.field_70146_Z.nextInt(3));
      } 
      EntityShulker.this.updateArmorModifier(30);
    }
    
    public void func_75251_c() {
      if (EntityShulker.this.func_70638_az() == null) {
        this.field_188522_b = 0;
        EntityShulker.this.updateArmorModifier(0);
      } 
    }
    
    public void func_75246_d() {
      this.field_188522_b--;
    }
  }
  
  class BodyHelper extends EntityBodyHelper {
    public BodyHelper(EntityLivingBase p_i47062_2_) {
      super(p_i47062_2_);
    }
    
    public void func_75664_a() {}
  }
}
