package de.femtopedia.reforged.api;

import de.femtopedia.reforged.item.ItemBattleaxe;
import java.util.UUID;
import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.femtopedia.reforged.ReforgedMod.MOD_ID;

public class ReforgedRegistry {

    // =================================================== REGISTRIES ==================================================

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_KEY);

    // =================================================== ATTRIBUTES ==================================================

    public static final UUID IGNORE_ARMOR_DAMAGE_ID = UUID.fromString("e3fdc3f0-6fed-4b39-9488-7d958bf1ef68");

    public static final UUID WEAPON_KNOCKBACK_ID = UUID.fromString("3abca465-3764-4322-9973-bdac0f2832c2");

    public static final UUID RELOAD_TIME_ID = UUID.fromString("8b1761bb-f3b3-4c50-9b2b-107b3c888eec");

    public static final UUID WEAPON_REACH_ID = UUID.fromString("ff531328-e8c2-490f-bbba-c6e7d86744c7");

    public static final EntityAttribute IGNORE_ARMOR_DAMAGE =
            new ClampedEntityAttribute("attribute.reforged.ignore_armor", 0D, 0D, Double.MAX_VALUE);

    public static final EntityAttribute WEAPON_KNOCKBACK =
            new ClampedEntityAttribute("attribute.reforged.knockback", 0.4D, 0D, Double.MAX_VALUE);

    public static final EntityAttribute RELOAD_TIME =
            new ClampedEntityAttribute("attribute.reforged.reload_time", 0D, 0D, Double.MAX_VALUE);

    public static final EntityAttribute WEAPON_REACH =
            new ClampedEntityAttribute("attribute.reforged.reach", 0D, 0D, Double.MAX_VALUE);

    // ===================================================== ITEMS =====================================================

    public static final RegistrySupplier<Item> BATTLE_AXE_WOOD = ITEMS.register("battle_axe_wood",
            () -> new ItemBattleaxe(ToolMaterials.WOOD, 1));

    public static final RegistrySupplier<Item> BATTLE_AXE_STONE = ITEMS.register("battle_axe_stone",
            () -> new ItemBattleaxe(ToolMaterials.STONE, 1));

    public static final RegistrySupplier<Item> BATTLE_AXE_IRON = ITEMS.register("battle_axe_iron",
            () -> new ItemBattleaxe(ToolMaterials.IRON, 1));

    public static final RegistrySupplier<Item> BATTLE_AXE_DIAMOND = ITEMS.register("battle_axe_diamond",
            () -> new ItemBattleaxe(ToolMaterials.DIAMOND, 1));

    public static final RegistrySupplier<Item> BATTLE_AXE_GOLD = ITEMS.register("battle_axe_gold",
            () -> new ItemBattleaxe(ToolMaterials.GOLD, 1));

    public static final RegistrySupplier<Item> BATTLE_AXE_NETHERITE = ITEMS.register("battle_axe_netherite",
            () -> new ItemBattleaxe(ToolMaterials.NETHERITE, 1));

    // ================================================== CREATIVE TAB =================================================

    public static final ItemGroup REFORGED_ITEM_GROUP =
            CreativeTabs.create(new Identifier(MOD_ID, "items"), () -> new ItemStack(BATTLE_AXE_IRON.get()));

    public static void init() {
        ITEMS.register();
    }

}
