package net.minecraft.AgeOfMinecraft.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBeaconSPC extends TileEntityLockable implements ITickable, ISidedInventory {
  public static final Potion[][] EFFECTS_LIST = new Potion[][] { { MobEffects.field_76424_c, MobEffects.field_76422_e }, { MobEffects.field_76429_m, MobEffects.field_76430_j }, { MobEffects.field_76420_g }, { MobEffects.field_76432_h } };
  
  private static final Set<Potion> VALID_EFFECTS = Sets.newHashSet();
  
  private final List<BeamSegment> beamSegments = Lists.newArrayList();
  
  @SideOnly(Side.CLIENT)
  private long beamRenderCounter;
  
  @SideOnly(Side.CLIENT)
  private float beamRenderScale;
  
  private boolean isComplete;
  
  private int levels = -1;
  
  @Nullable
  private Potion primaryEffect;
  
  @Nullable
  private Potion secondaryEffect;
  
  private ItemStack payment = ItemStack.field_190927_a;
  
  private String customName;
  
  public void func_73660_a() {
    if (this.field_145850_b.func_82737_E() % 80L == 0L)
      updateBeacon(); 
  }
  
  public void updateBeacon() {
    if (this.field_145850_b != null) {
      updateSegmentColors();
      addEffectsToPlayers();
    } 
  }
  
  private void addEffectsToPlayers() {
    if (this.isComplete && this.levels > 0 && !this.field_145850_b.field_72995_K && this.primaryEffect != null) {
      double d0 = (this.levels * 10 + 10);
      int i = 0;
      if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect)
        i = 1; 
      int j = (9 + this.levels * 2) * 20;
      int k = this.field_174879_c.func_177958_n();
      int l = this.field_174879_c.func_177956_o();
      int i1 = this.field_174879_c.func_177952_p();
      AxisAlignedBB axisalignedbb = (new AxisAlignedBB(k, l, i1, (k + 1), (l + 1), (i1 + 1))).func_186662_g(d0).func_72321_a(0.0D, this.field_145850_b.func_72800_K(), 0.0D);
      List<EntityPlayer> list = this.field_145850_b.func_72872_a(EntityPlayer.class, axisalignedbb);
      for (EntityPlayer entityplayer : list)
        entityplayer.func_70690_d(new PotionEffect(this.primaryEffect, j, i, true, true)); 
      if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null)
        for (EntityPlayer entityplayer1 : list)
          entityplayer1.func_70690_d(new PotionEffect(this.secondaryEffect, j, 0, true, true));  
    } 
  }
  
  private void updateSegmentColors() {
    int i = this.field_174879_c.func_177958_n();
    int j = this.field_174879_c.func_177956_o();
    int k = this.field_174879_c.func_177952_p();
    int l = this.levels;
    this.levels = 0;
    this.beamSegments.clear();
    this.isComplete = true;
    BeamSegment tileentitybeacon$beamsegment = new BeamSegment(EnumDyeColor.WHITE.func_193349_f());
    this.beamSegments.add(tileentitybeacon$beamsegment);
    boolean flag = true;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
    int i1 = j + 1;
    while (true) {
      if (i1 < 256) {
        float[] afloat;
        IBlockState iblockstate = this.field_145850_b.func_180495_p((BlockPos)blockpos$mutableblockpos.func_181079_c(i, i1, k));
        if (iblockstate.func_177230_c() == Blocks.field_150399_cn) {
          afloat = ((EnumDyeColor)iblockstate.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)).func_193349_f();
        } else if (iblockstate.func_177230_c() != Blocks.field_150397_co) {
          if (iblockstate.getLightOpacity((IBlockAccess)this.field_145850_b, (BlockPos)blockpos$mutableblockpos) >= 15 && iblockstate.func_177230_c() != Blocks.field_150357_h) {
            this.isComplete = false;
            this.beamSegments.clear();
            break;
          } 
          float[] customColor = iblockstate.func_177230_c().getBeaconColorMultiplier(iblockstate, this.field_145850_b, (BlockPos)blockpos$mutableblockpos, func_174877_v());
          if (customColor != null) {
            afloat = customColor;
          } else {
            tileentitybeacon$beamsegment.incrementHeight();
            i1++;
          } 
        } else {
          afloat = ((EnumDyeColor)iblockstate.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a)).func_193349_f();
        } 
        if (!flag)
          afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F }; 
        if (Arrays.equals(afloat, tileentitybeacon$beamsegment.getColors())) {
          tileentitybeacon$beamsegment.incrementHeight();
        } else {
          tileentitybeacon$beamsegment = new BeamSegment(afloat);
          this.beamSegments.add(tileentitybeacon$beamsegment);
        } 
        flag = false;
      } else {
        break;
      } 
      i1++;
    } 
    if (this.isComplete) {
      for (int l1 = 1; l1 <= 4; this.levels = l1++) {
        int i2 = j - l1;
        if (i2 < 0)
          break; 
        boolean flag1 = true;
        for (int j1 = i - l1; j1 <= i + l1 && flag1; j1++) {
          for (int k1 = k - l1; k1 <= k + l1; k1++) {
            Block block = this.field_145850_b.func_180495_p(new BlockPos(j1, i2, k1)).func_177230_c();
            if (!block.isBeaconBase((IBlockAccess)this.field_145850_b, new BlockPos(j1, i2, k1), func_174877_v())) {
              flag1 = false;
              break;
            } 
          } 
        } 
        if (!flag1)
          break; 
      } 
      if (this.levels == 0)
        this.isComplete = false; 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public List<BeamSegment> getBeamSegments() {
    return this.beamSegments;
  }
  
  @SideOnly(Side.CLIENT)
  public float shouldBeamRender() {
    if (!this.isComplete)
      return 0.0F; 
    int i = (int)(this.field_145850_b.func_82737_E() - this.beamRenderCounter);
    this.beamRenderCounter = this.field_145850_b.func_82737_E();
    if (i > 1) {
      this.beamRenderScale -= i / 40.0F;
      if (this.beamRenderScale < 0.0F)
        this.beamRenderScale = 0.0F; 
    } 
    this.beamRenderScale += 0.025F;
    if (this.beamRenderScale > 1.0F)
      this.beamRenderScale = 1.0F; 
    return this.beamRenderScale;
  }
  
  public int getLevels() {
    return this.levels;
  }
  
  @Nullable
  public SPacketUpdateTileEntity func_189518_D_() {
    return new SPacketUpdateTileEntity(this.field_174879_c, 3, func_189517_E_());
  }
  
  public NBTTagCompound func_189517_E_() {
    return func_189515_b(new NBTTagCompound());
  }
  
  @SideOnly(Side.CLIENT)
  public double func_145833_n() {
    return 65536.0D;
  }
  
  @Nullable
  private static Potion isBeaconEffect(int p_184279_0_) {
    Potion potion = Potion.func_188412_a(p_184279_0_);
    return VALID_EFFECTS.contains(potion) ? potion : null;
  }
  
  public void func_145839_a(NBTTagCompound compound) {
    super.func_145839_a(compound);
    this.primaryEffect = isBeaconEffect(compound.func_74762_e("Primary"));
    this.secondaryEffect = isBeaconEffect(compound.func_74762_e("Secondary"));
    this.levels = compound.func_74762_e("Levels");
  }
  
  public NBTTagCompound func_189515_b(NBTTagCompound compound) {
    super.func_189515_b(compound);
    compound.func_74768_a("Primary", Potion.func_188409_a(this.primaryEffect));
    compound.func_74768_a("Secondary", Potion.func_188409_a(this.secondaryEffect));
    compound.func_74768_a("Levels", this.levels);
    return compound;
  }
  
  public int func_70302_i_() {
    return 1;
  }
  
  public boolean func_191420_l() {
    return this.payment.func_190926_b();
  }
  
  public ItemStack func_70301_a(int index) {
    return (index == 0) ? this.payment : ItemStack.field_190927_a;
  }
  
  public ItemStack func_70298_a(int index, int count) {
    if (index == 0 && !this.payment.func_190926_b()) {
      if (count >= this.payment.func_190916_E()) {
        ItemStack itemstack = this.payment;
        this.payment = ItemStack.field_190927_a;
        return itemstack;
      } 
      return this.payment.func_77979_a(count);
    } 
    return ItemStack.field_190927_a;
  }
  
  public ItemStack func_70304_b(int index) {
    if (index == 0) {
      ItemStack itemstack = this.payment;
      this.payment = ItemStack.field_190927_a;
      return itemstack;
    } 
    return ItemStack.field_190927_a;
  }
  
  public void func_70299_a(int index, ItemStack stack) {
    if (index == 0)
      this.payment = stack; 
  }
  
  public String func_70005_c_() {
    return func_145818_k_() ? this.customName : "container.beacon";
  }
  
  public boolean func_145818_k_() {
    return (this.customName != null && !this.customName.isEmpty());
  }
  
  public void setName(String name) {
    this.customName = name;
  }
  
  public int func_70297_j_() {
    return 1;
  }
  
  public boolean func_70300_a(EntityPlayer player) {
    if (this.field_145850_b.func_175625_s(this.field_174879_c) != this)
      return false; 
    return (player.func_70092_e(this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o() + 0.5D, this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D);
  }
  
  public void func_174889_b(EntityPlayer player) {}
  
  public void func_174886_c(EntityPlayer player) {}
  
  public boolean func_94041_b(int index, ItemStack stack) {
    return (stack.func_77973_b() != null && stack.func_77973_b().isBeaconPayment(stack));
  }
  
  public String func_174875_k() {
    return "minecraft:beacon";
  }
  
  public Container func_174876_a(InventoryPlayer playerInventory, EntityPlayer playerIn) {
    return (Container)new ContainerBeacon((IInventory)playerInventory, (IInventory)this);
  }
  
  public int func_174887_a_(int id) {
    switch (id) {
      case 0:
        return this.levels;
      case 1:
        return Potion.func_188409_a(this.primaryEffect);
      case 2:
        return Potion.func_188409_a(this.secondaryEffect);
    } 
    return 0;
  }
  
  public void func_174885_b(int id, int value) {
    switch (id) {
      case 0:
        this.levels = value;
        break;
      case 1:
        this.primaryEffect = isBeaconEffect(value);
        break;
      case 2:
        this.secondaryEffect = isBeaconEffect(value);
        break;
    } 
  }
  
  public int func_174890_g() {
    return 3;
  }
  
  public void func_174888_l() {
    this.payment = ItemStack.field_190927_a;
  }
  
  public boolean func_145842_c(int id, int type) {
    if (id == 1) {
      updateBeacon();
      return true;
    } 
    return super.func_145842_c(id, type);
  }
  
  public int[] func_180463_a(EnumFacing side) {
    return new int[0];
  }
  
  public boolean func_180462_a(int index, ItemStack itemStackIn, EnumFacing direction) {
    return false;
  }
  
  public boolean func_180461_b(int index, ItemStack stack, EnumFacing direction) {
    return false;
  }
  
  static {
    for (Potion[] apotion : EFFECTS_LIST)
      Collections.addAll(VALID_EFFECTS, apotion); 
  }
  
  public static class BeamSegment {
    private final float[] colors;
    
    private int height;
    
    public BeamSegment(float[] colorsIn) {
      this.colors = colorsIn;
      this.height = 1;
    }
    
    protected void incrementHeight() {
      this.height++;
    }
    
    public float[] getColors() {
      return this.colors;
    }
    
    @SideOnly(Side.CLIENT)
    public int getHeight() {
      return this.height;
    }
  }
}
