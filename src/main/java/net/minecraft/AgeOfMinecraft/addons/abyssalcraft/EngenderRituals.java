package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.ACLib;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class EngenderRituals {
  public static void registerRituals() {
    Object[] engenderAsorah = { ACItems.transmutation_gem, new ItemStack(ACBlocks.ingot_block, 1, 1), ACItems.coralium_pearl, new ItemStack(ACBlocks.ingot_block, 1, 1), ACItems.eye_of_the_abyss, new ItemStack(ACBlocks.ingot_block, 1, 1), ACItems.coralium_pearl, new ItemStack(ACBlocks.ingot_block, 1, 1) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("engenderAsorah", 2, ACLib.abyssal_wasteland_id, 20000.0F, false, new ItemStack((Item)EItem.fusionItemAsorah), new ItemStack(ACItems.dreaded_gateway_key), engenderAsorah)).setNBTSensitive());
    Object[] engenderChagaroth = { ACItems.dread_plagued_gateway_key, new ItemStack(ACBlocks.ingot_block, 1, 2), ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACBlocks.ingot_block, 1, 2), ACItems.dreadium_katana, new ItemStack(ACBlocks.ingot_block, 1, 2), ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACBlocks.ingot_block, 1, 2) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("engenderChagaroth", 3, ACLib.dreadlands_id, 40000.0F, false, new ItemStack((Item)EItem.fusionItemChagaroth), new ItemStack((Item)EItem.fusionItemLesserDreadbeast), engenderChagaroth)).setNBTSensitive());
    Object[] engenderSacthoth = { ACBlocks.oblivion_deathbomb, ACItems.shard_of_oblivion, ACItems.liquid_antimatter_bucket_stack, Blocks.OBSIDIAN, ACItems.oblivion_catalyst, Blocks.OBSIDIAN, ACItems.liquid_coralium_bucket_stack, ACItems.shard_of_oblivion };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("engenderSacthoth", 3, ACLib.dark_realm_id, 40000.0F, false, new ItemStack((Item)EItem.fusionItemSacthoth), new ItemStack(ACItems.sacthoths_soul_harvesting_blade), engenderSacthoth)).setNBTSensitive());
    Object[] engenderJzahar = { ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.eldritch_scale, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.staff_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.eldritch_scale, new ItemStack(ACBlocks.ingot_block, 1, 3) };
    ItemStack abynom = new ItemStack(ACItems.abyssalnomicon);
    abynom.setTagCompound(new NBTTagCompound());
    abynom.getTagCompound().setFloat("PotEnergy", 100000.0F);
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("engenderJzahar", 4, ACLib.omothol_id, 100000.0F, false, new ItemStack((Item)EItem.fusionItemJzahar), abynom, engenderJzahar)).setNBTSensitiveSacrifice());
    RitualRegistry.instance().registerRitual(new NecronomiconMobGirlBreedingRitual());
    Object[] offerings = { new ItemStack(EItem.convertingStaff, 1, 0), Blocks.IRON_BLOCK, new ItemStack(EItem.convertingStaff, 1, 0), Blocks.GOLD_BLOCK, new ItemStack(EItem.convertingStaff, 1, 0), Blocks.DIAMOND_BLOCK, new ItemStack(EItem.convertingStaff, 1, 0), Blocks.EMERALD_BLOCK };
    ItemStack gun1 = new ItemStack(EItem.peGunLevel1);
    gun1.setTagCompound(new NBTTagCompound());
    gun1.getTagCompound().setFloat("PotEnergy", 500.0F);
    ItemStack gun12 = new ItemStack(EItem.peGunLevel2);
    gun12.setTagCompound(new NBTTagCompound());
    gun12.getTagCompound().setFloat("PotEnergy", 500.0F);
    ItemStack gun2 = new ItemStack(EItem.peGunLevel2);
    gun2.setTagCompound(new NBTTagCompound());
    gun2.getTagCompound().setFloat("PotEnergy", 1000.0F);
    ItemStack gun22 = new ItemStack(EItem.peGunLevel3);
    gun22.setTagCompound(new NBTTagCompound());
    gun22.getTagCompound().setFloat("PotEnergy", 1000.0F);
    ItemStack gun3 = new ItemStack(EItem.peGunLevel3);
    gun3.setTagCompound(new NBTTagCompound());
    gun3.getTagCompound().setFloat("PotEnergy", 1500.0F);
    ItemStack gun32 = new ItemStack(EItem.peGunLevel4);
    gun32.setTagCompound(new NBTTagCompound());
    gun32.getTagCompound().setFloat("PotEnergy", 1500.0F);
    ItemStack gun4 = new ItemStack(EItem.peGunLevel4);
    gun4.setTagCompound(new NBTTagCompound());
    gun4.getTagCompound().setFloat("PotEnergy", 2000.0F);
    ItemStack gun42 = new ItemStack(EItem.peGunLevel5);
    gun42.setTagCompound(new NBTTagCompound());
    gun42.getTagCompound().setFloat("PotEnergy", 2000.0F);
    Object[] pegunlevel2 = { ACItems.shadow_shard, new ItemStack(ACBlocks.stone, 1, 1), ACItems.coralium_gem, new ItemStack(ACBlocks.stone, 1, 1), ACItems.coralium_gem, new ItemStack(ACBlocks.stone, 1, 1), ACItems.coralium_gem, new ItemStack(ACBlocks.stone, 1, 1) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("gunUpgrade1", 1, ACLib.abyssal_wasteland_id, 2500.0F, gun12, gun1, pegunlevel2)).setTags("PotEnergy"));
    Object[] pegunlevel3 = { ACItems.shadow_gem, new ItemStack(ACBlocks.stone, 1, 3), ACItems.dread_cloth, new ItemStack(ACBlocks.stone, 1, 2), ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACBlocks.stone, 1, 3), ACItems.dread_cloth, new ItemStack(ACBlocks.stone, 1, 2) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("gunUpgrade2", 2, ACLib.dreadlands_id, 5000.0F, gun22, gun2, pegunlevel3)).setTags("PotEnergy"));
    Object[] pegunlevel4 = { ACItems.shard_of_oblivion, new ItemStack(ACBlocks.stone, 1, 6), ACItems.omothol_flesh, new ItemStack(ACBlocks.stone, 1, 6), ACItems.ethaxium_brick, new ItemStack(ACBlocks.stone, 1, 6), ACItems.omothol_flesh, new ItemStack(ACBlocks.stone, 1, 6) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("gunUpgrade3", 3, ACLib.omothol_id, 7500.0F, gun32, gun3, pegunlevel4)).setTags("PotEnergy"));
    Object[] pegunlevel5 = { ACItems.oblivion_catalyst, ACBlocks.ethaxium_brick, ACItems.eldritch_scale, ACBlocks.dark_ethaxium_brick, ACItems.liquid_antimatter_bucket_stack, ACBlocks.dark_ethaxium_brick, ACItems.eldritch_scale, ACBlocks.ethaxium_brick };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("gunUpgrade4", 4, ACLib.omothol_id, 10000.0F, gun42, gun4, pegunlevel5)).setTags("PotEnergy"));
    Object[] staff = { ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3), ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, 3) };
    RitualRegistry.instance().registerRitual((new NecronomiconInfusionRitual("abyportalStaffLevel1", 4, ACLib.omothol_id, 100000.0F, false, new ItemStack(EItem.abyssalPortalStaff), new ItemStack(EItem.portalStaff, 1, 4), staff)).setNBTSensitiveSacrifice());
  }
}
