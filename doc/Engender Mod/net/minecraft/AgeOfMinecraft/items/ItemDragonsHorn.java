package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseListAsorah;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemDragonsHorn extends ItemBEItem {
  public ItemDragonsHorn() {
    super("enderdragonshorn");
    func_77625_d(1);
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Raise moral with this horn, and call back your dragons");
    tooltip.add(TextFormatting.RED + "All hostile mobs aggro to you");
    tooltip.add(TextFormatting.GREEN + "Damages all nearby mobs");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Moral");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Attack");
    tooltip.add(TextFormatting.GREEN + "+Speed");
    tooltip.add(TextFormatting.GREEN + "+Strength");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
  }
  
  public int func_77626_a(ItemStack stack) {
    return 80;
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.func_184586_b(hand);
    boolean flag = (func_185060_a(playerIn) != null);
    ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
    if (ret != null)
      return ret; 
    if (!playerIn.field_71075_bZ.field_75098_d && !flag)
      return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn); 
    playerIn.func_184598_c(hand);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack func_185060_a(EntityPlayer player) {
    return player.func_184586_b(EnumHand.MAIN_HAND);
  }
  
  public boolean func_77636_d(ItemStack stack) {
    return true;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.EPIC;
  }
  
  public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    playerIn.field_70125_A = -75.0F;
    playerIn.field_70170_p.func_184134_a(playerIn.field_70165_t, playerIn.field_70163_u + playerIn.getDefaultEyeHeight(), playerIn.field_70161_v, getHornSound(), SoundCategory.PLAYERS, 1.0E9F, 1.0F, false);
    List<EntityLivingBase> list = playerIn.field_70170_p.func_175647_a(EntityLivingBase.class, playerIn.func_174813_aQ().func_186662_g(512.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity instanceof EntityEnderDragon && entity != null && entity.func_70089_S() && ((EntityEnderDragon)entity).func_184753_b() == playerIn.func_110124_au()) {
          ((EntityEnderDragon)entity).getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
          ((EntityEnderDragon)entity).func_70642_aH();
        } 
        if (entity instanceof EntityDragonBoss && entity != null && entity.func_70089_S() && ((EntityDragonBoss)entity).func_184753_b() == playerIn.func_110124_au()) {
          ((EntityDragonBoss)entity).getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH);
          ((EntityDragonBoss)entity).func_70642_aH();
        } 
        if (entity instanceof EntityChaosGuardian && entity != null && entity.func_70089_S() && ((EntityChaosGuardian)entity).func_184753_b() == playerIn.func_110124_au()) {
          ((EntityChaosGuardian)entity).setToGuard();
          ((EntityChaosGuardian)entity).func_70642_aH();
        } 
        if (entity instanceof EntityDragonMinion && entity != null && entity.func_70089_S() && ((EntityDragonMinion)entity).func_184753_b() == playerIn.func_110124_au()) {
          ((EntityDragonMinion)entity).target = (Entity)playerIn;
          ((EntityDragonMinion)entity).func_70642_aH();
        } 
        if (entity instanceof EntityMob) {
          ((EntityMob)entity).func_70624_b((EntityLivingBase)playerIn);
          ((EntityMob)entity).func_70604_c((EntityLivingBase)playerIn);
          ((EntityMob)entity).func_70605_aq().func_75642_a(playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70163_u, 1.2D);
          if (playerIn.func_70068_e((Entity)entity) <= 64.0D)
            ((EntityMob)entity).func_70097_a(DamageSource.field_76377_j, 50.0F); 
        } 
      }  
    List<EntityLivingBase> list1 = playerIn.field_70170_p.func_175647_a(EntityLivingBase.class, playerIn.func_174813_aQ().func_72314_b(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list1 != null && !list1.isEmpty())
      for (int i1 = 0; i1 < list1.size(); i1++) {
        EntityLivingBase entity = list1.get(i1);
        if (entity instanceof EntityTameBase && entity != null && entity.func_70089_S() && ((EntityTameBase)entity).getOwner() == playerIn)
          ((EntityTameBase)entity).moralRaisedTimer = 600; 
      }  
    playerIn.func_184185_a(ESound.battlecry, 10.0F, 1.0F);
    playerIn.func_70690_d(new PotionEffect(MobEffects.field_76420_g, 600, 0));
    playerIn.func_70690_d(new PotionEffect(MobEffects.field_76424_c, 600, 1));
    playerIn.func_71020_j(2.0F);
    return stack;
  }
  
  public SoundEvent getHornSound() {
    return ESound.dragonHornBlow;
  }
}
