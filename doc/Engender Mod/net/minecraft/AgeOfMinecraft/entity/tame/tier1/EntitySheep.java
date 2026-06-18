package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySheep extends EntityTameBase implements Light, Animal, IShearable, IJumpingMount {
  private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.func_187226_a(EntitySheep.class, DataSerializers.field_187191_a);
  
  private int shootTimer;
  
  private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container() {
        public boolean func_75145_c(EntityPlayer playerIn) {
          return false;
        }
      },  2, 1);
  
  private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
  
  private int sheepTimer;
  
  private EntityAIEatGrass entityAIEatGrass;
  
  protected float jumpPower;
  
  public static float[] getDyeRgb(EnumDyeColor dyeColor) {
    return DYE_TO_RGB.get(dyeColor);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySheep(this.field_70170_p);
  }
  
  public EntitySheep(World worldIn) {
    super(worldIn);
    func_70105_a(0.9F, 1.3F);
    this.inventoryCrafting.func_70299_a(0, new ItemStack(Items.field_151100_aR));
    this.inventoryCrafting.func_70299_a(1, new ItemStack(Items.field_151100_aR));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 16.0F, 4.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)(this.entityAIEatGrass = new EntityAIEatGrass((EntityLiving)this)));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 1;
  }
  
  public String getDescName() {
    return "jeb_".equals(func_70005_c_()) ? "jeb_sheep" : ((getFleeceColor() == EnumDyeColor.PINK) ? "pink_sheep" : "sheep");
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  protected void func_70619_bc() {
    this.sheepTimer = this.entityAIEatGrass.func_151499_f();
    super.func_70619_bc();
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  public void func_70636_d() {
    if (this.field_70170_p.field_72995_K)
      this.sheepTimer = Math.max(0, this.sheepTimer - 1); 
    if (func_70638_az() != null) {
      this.shootTimer++;
    } else if (this.shootTimer > 0) {
      this.shootTimer--;
    } 
    if (this.field_70173_aa > 20 && func_145818_k_() && "jeb_".equals(func_95999_t()) && func_70638_az() != null) {
      if (this.shootTimer > 0) {
        func_70661_as().func_75499_g();
        func_184185_a(SoundEvents.field_187659_cY, func_70599_aP(), 0.5F + (this.shootTimer / 20));
      } 
      if (this.shootTimer >= 60) {
        for (int i = 0; i < 256; i++) {
          double d9 = i / 255.0D;
          double d6 = this.field_70165_t + (this.field_70165_t - (func_70638_az()).field_70165_t) * -d9;
          double d7 = this.field_70163_u + func_70047_e() + (this.field_70163_u - (func_70638_az()).field_70163_u) * -d9;
          double d8 = this.field_70161_v + (this.field_70161_v - (func_70638_az()).field_70161_v) * -d9;
          this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, d6, d7, d8, 0.0D, 0.01D, 0.0D, new int[0]);
        } 
        func_184185_a(SoundEvents.field_187942_hp, func_70599_aP(), this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat() * 0.4F + 1.0F);
        func_70638_az().func_70097_a(DamageSource.func_188405_b((EntityLivingBase)this), 12.0F);
        this.shootTimer = -200;
      } 
    } 
    super.func_70636_d();
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(DYE_COLOR, Byte.valueOf((byte)0));
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    if (getSheared())
      return ELoot.ENTITIES_SHEEP; 
    switch (getFleeceColor()) {
      default:
        return ELoot.ENTITIES_SHEEP_WHITE;
      case ORANGE:
        return ELoot.ENTITIES_SHEEP_ORANGE;
      case MAGENTA:
        return ELoot.ENTITIES_SHEEP_MAGENTA;
      case LIGHT_BLUE:
        return ELoot.ENTITIES_SHEEP_LIGHT_BLUE;
      case YELLOW:
        return ELoot.ENTITIES_SHEEP_YELLOW;
      case LIME:
        return ELoot.ENTITIES_SHEEP_LIME;
      case PINK:
        return ELoot.ENTITIES_SHEEP_PINK;
      case GRAY:
        return ELoot.ENTITIES_SHEEP_GRAY;
      case SILVER:
        return ELoot.ENTITIES_SHEEP_SILVER;
      case CYAN:
        return ELoot.ENTITIES_SHEEP_CYAN;
      case PURPLE:
        return ELoot.ENTITIES_SHEEP_PURPLE;
      case BLUE:
        return ELoot.ENTITIES_SHEEP_BLUE;
      case BROWN:
        return ELoot.ENTITIES_SHEEP_BROWN;
      case GREEN:
        return ELoot.ENTITIES_SHEEP_GREEN;
      case RED:
        return ELoot.ENTITIES_SHEEP_RED;
      case BLACK:
        break;
    } 
    return ELoot.ENTITIES_SHEEP_BLACK;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 10) {
      this.sheepTimer = 40;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151100_aR && hasOwner(player)) {
      EnumDyeColor enumdyecolor = EnumDyeColor.func_176766_a(stack.func_77960_j());
      if (!getSheared() && getFleeceColor() != enumdyecolor) {
        func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i() - 0.2F);
        this.field_70170_p.func_175718_b(2001, func_180425_c(), Block.func_176210_f(Blocks.field_150404_cg.func_176223_P().func_177226_a((IProperty)BlockCarpet.field_176330_a, (Comparable)enumdyecolor)));
        setFleeceColor(enumdyecolor);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i());
      this.field_70170_p.func_175718_b(2001, func_180425_c(), Block.func_176210_f(Blocks.field_150404_cg.func_176223_P().func_177226_a((IProperty)BlockCarpet.field_176330_a, (Comparable)getFleeceColor())));
      return true;
    } 
    return false;
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      this.field_70138_W = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70759_as = entitylivingbase.field_70759_as;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (forward != 0.0F) {
        this.field_70177_z = this.field_70761_aq = this.field_70759_as;
        this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      } 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.7D * this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-0.4F * f * this.jumpPower);
          this.field_70179_y += (0.4F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d1 = this.field_70165_t - this.field_70169_q;
      double d0 = this.field_70161_v - this.field_70166_s;
      float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
      if (f2 > 1.0F)
        f2 = 1.0F; 
      this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.1F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadRotationPointY(float p_70894_1_) {
    return (this.sheepTimer <= 0) ? 0.0F : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0F : ((this.sheepTimer < 4) ? ((this.sheepTimer - p_70894_1_) / 4.0F) : (-((this.sheepTimer - 40) - p_70894_1_) / 4.0F)));
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadRotationAngleX(float p_70890_1_) {
    if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
      float f = ((this.sheepTimer - 4) - p_70890_1_) / 32.0F;
      return 0.62831855F + 0.2199115F * MathHelper.func_76126_a(f * 28.7F);
    } 
    return (this.sheepTimer > 0) ? 0.62831855F : (this.field_70125_A * 0.017453292F);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74757_a("Sheared", getSheared());
    tagCompound.func_74774_a("Color", (byte)getFleeceColor().func_176765_a());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setSheared(tagCompund.func_74767_n("Sheared"));
    setFleeceColor(EnumDyeColor.func_176764_b(tagCompund.func_74771_c("Color")));
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187757_eG;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187761_eI;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187759_eH;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187765_eK, 0.15F, 1.0F / getFittness());
  }
  
  public EnumDyeColor getFleeceColor() {
    return EnumDyeColor.func_176764_b(((Byte)this.field_70180_af.func_187225_a(DYE_COLOR)).byteValue() & 0xF);
  }
  
  public void setFleeceColor(EnumDyeColor color) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(DYE_COLOR)).byteValue();
    this.field_70180_af.func_187227_b(DYE_COLOR, Byte.valueOf((byte)(b0 & 0xF0 | color.func_176765_a() & 0xF)));
  }
  
  public boolean getSheared() {
    return ((((Byte)this.field_70180_af.func_187225_a(DYE_COLOR)).byteValue() & 0x10) != 0);
  }
  
  public void setSheared(boolean sheared) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(DYE_COLOR)).byteValue();
    if (sheared) {
      this.field_70180_af.func_187227_b(DYE_COLOR, Byte.valueOf((byte)(b0 | 0x10)));
    } else {
      this.field_70180_af.func_187227_b(DYE_COLOR, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
    } 
  }
  
  public static EnumDyeColor getRandomSheepColor(Random random) {
    int i = random.nextInt(100);
    return (random.nextInt(500) == 0) ? EnumDyeColor.PINK : ((i < 18) ? EnumDyeColor.BROWN : ((i < 15) ? EnumDyeColor.SILVER : ((i < 10) ? EnumDyeColor.GRAY : ((i < 5) ? EnumDyeColor.BLACK : EnumDyeColor.WHITE))));
  }
  
  public void func_70615_aA() {
    setSheared(false);
    func_70691_i(2.0F);
    func_145779_a(Items.field_151014_N, 2 + this.field_70146_Z.nextInt(3));
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    setFleeceColor(getRandomSheepColor(this.field_70170_p.field_73012_v));
    return livingdata;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.775D;
  }
  
  public float func_70047_e() {
    return 0.95F * this.field_70131_O;
  }
  
  static {
    DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] { 1.0F, 1.0F, 1.0F });
    DYE_TO_RGB.put(EnumDyeColor.ORANGE, new float[] { 0.85F, 0.5F, 0.2F });
    DYE_TO_RGB.put(EnumDyeColor.MAGENTA, new float[] { 0.7F, 0.3F, 0.85F });
    DYE_TO_RGB.put(EnumDyeColor.LIGHT_BLUE, new float[] { 0.4F, 0.6F, 0.85F });
    DYE_TO_RGB.put(EnumDyeColor.YELLOW, new float[] { 0.9F, 0.9F, 0.2F });
    DYE_TO_RGB.put(EnumDyeColor.LIME, new float[] { 0.5F, 0.8F, 0.1F });
    DYE_TO_RGB.put(EnumDyeColor.PINK, new float[] { 0.95F, 0.5F, 0.65F });
    DYE_TO_RGB.put(EnumDyeColor.GRAY, new float[] { 0.3F, 0.3F, 0.3F });
    DYE_TO_RGB.put(EnumDyeColor.SILVER, new float[] { 0.6F, 0.6F, 0.6F });
    DYE_TO_RGB.put(EnumDyeColor.CYAN, new float[] { 0.3F, 0.5F, 0.6F });
    DYE_TO_RGB.put(EnumDyeColor.PURPLE, new float[] { 0.5F, 0.25F, 0.7F });
    DYE_TO_RGB.put(EnumDyeColor.BLUE, new float[] { 0.2F, 0.3F, 0.7F });
    DYE_TO_RGB.put(EnumDyeColor.BROWN, new float[] { 0.4F, 0.3F, 0.2F });
    DYE_TO_RGB.put(EnumDyeColor.GREEN, new float[] { 0.4F, 0.5F, 0.2F });
    DYE_TO_RGB.put(EnumDyeColor.RED, new float[] { 0.6F, 0.2F, 0.2F });
    DYE_TO_RGB.put(EnumDyeColor.BLACK, new float[] { 0.1F, 0.1F, 0.1F });
  }
  
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return (!getSheared() && !func_70631_g_());
  }
  
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    setSheared(true);
    int i = 1 + this.field_70146_Z.nextInt(3);
    List<ItemStack> ret = new ArrayList<>();
    for (int j = 0; j < i; j++)
      ret.add(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, getFleeceColor().func_176765_a())); 
    func_184185_a(SoundEvents.field_187763_eJ, 1.0F, 1.0F);
    return ret;
  }
}
