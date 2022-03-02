package de.femtopedia.reforged.api;

import de.femtopedia.reforged.client.renderers.BoomerangEntityRenderer;
import de.femtopedia.reforged.entity.BoomerangEntity;
import de.femtopedia.reforged.item.BattleaxeItem;
import de.femtopedia.reforged.item.BoomerangItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.femtopedia.reforged.ReforgedMod.MOD_ID;

public class ReforgedRegistry {

    // =================================================== REGISTRIES ==================================================

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_KEY);

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(MOD_ID, Registry.ENTITY_TYPE_KEY);

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

    // ==================================================== ENTITIES ===================================================

    public static final RegistrySupplier<EntityType<BoomerangEntity>> BOOMERANG_ENTITY =
            ENTITIES.register("boomerang_entity", () -> EntityType.Builder.create(
                    BoomerangEntity::create, SpawnGroup.MISC).build("boomerang_entity"));

    // ===================================================== ITEMS =====================================================

    public static final Map<ToolMaterial, RegistrySupplier<BattleaxeItem>> BATTLE_AXES = new HashMap<>();

    public static final Map<ToolMaterial, RegistrySupplier<BoomerangItem>> BOOMERANGS = new HashMap<>();

    // ================================================== CREATIVE TAB =================================================

    public static final ItemGroup REFORGED_ITEM_GROUP = CreativeTabRegistry.create(new Identifier(MOD_ID, "items"),
            () -> new ItemStack(BATTLE_AXES.get(ToolMaterials.IRON).get()));

    public static void registerBattleAxe(MaterialApi.MaterialData data) {
        BATTLE_AXES.put(data.material(), ITEMS.register("battle_axe_" + data.key(),
                () -> new BattleaxeItem(data.material(), 1)));
    }

    public static void registerBoomerang(MaterialApi.MaterialData data) {
        BOOMERANGS.put(data.material(), ITEMS.register("boomerang_" + data.key(),
                () -> new BoomerangItem(data.material())));
    }

    private static void registerItems() {
        for (MaterialApi.MaterialData data : MaterialApi.getRegisteredMaterials()) {
            registerBattleAxe(data);
        }
        for (MaterialApi.MaterialData data : MaterialApi.getRegisteredMaterials()) {
            registerBoomerang(data);
        }
    }

    private static void finalizeRegistries() {
        ITEMS.register();
        ENTITIES.register();
    }

    private static void registerRenderers() {
        EntityRendererRegistry.register(BOOMERANG_ENTITY::get, BoomerangEntityRenderer::new);
    }

    public static void init() {
        registerItems();
        finalizeRegistries();
        registerRenderers();
    }

}
