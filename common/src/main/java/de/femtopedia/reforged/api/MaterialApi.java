package de.femtopedia.reforged.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;

public final class MaterialApi {

    public static final ToolMaterial FALLBACK = ToolMaterials.WOOD;

    private static final List<MaterialData> DATA = new ArrayList<>();

    private static final Map<ToolMaterial, MaterialData> MAT_TO_DATA = new HashMap<>();

    static {
        initVanilla();
    }

    private MaterialApi() {
        throw new UnsupportedOperationException();
    }

    private static void initVanilla() {
        registerToolMaterial(new MaterialData(ToolMaterials.WOOD, "wood", 1,
                new Color(0.6f, 0.4f, 0.1f, 1.0f)));
        registerToolMaterial(new MaterialData(ToolMaterials.STONE, "stone", 1,
                new Color(0.5f, 0.5f, 0.5f, 1.0f)));
        registerToolMaterial(new MaterialData(ToolMaterials.IRON, "iron", 1,
                new Color(1.0f, 1.0f, 1.0f, 1.0f)));
        registerToolMaterial(new MaterialData(ToolMaterials.DIAMOND, "diamond", 1,
                new Color(0.0f, 0.8f, 0.7f, 1.0f)));
        registerToolMaterial(new MaterialData(ToolMaterials.GOLD, "gold", 1,
                new Color(1.0f, 0.9f, 0.0f, 1.0f)));
        registerToolMaterial(new MaterialData(ToolMaterials.NETHERITE, "netherite", 1,
                new Color(0.2f, 0.2f, 0.2f, 1.0f)));
    }

    public static void registerToolMaterial(MaterialData data) {
        DATA.add(data);
        MAT_TO_DATA.put(data.material(), data);
    }

    public static List<MaterialData> getRegisteredMaterials() {
        return new ArrayList<>(DATA);
    }

    public static MaterialData getMaterialData(ToolMaterial material) {
        return MAT_TO_DATA.get(material);
    }

    public record MaterialData(ToolMaterial material, String key, int ignoreArmor, Color color) {
    }

    public record Color(float red, float green, float blue, float alpha) {

        public static final Color WHITE = new Color(1, 1, 1, 1);

        public float[] asArray() {
            return new float[]{red, green, blue, alpha};
        }

    }

}
