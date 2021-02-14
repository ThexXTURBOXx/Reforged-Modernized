package de.femtopedia.reforged.api;

import net.minecraft.item.Item.Settings;

public class ReforgedProperties extends Settings {

    public ReforgedProperties() {
        super();
        group(ReforgedRegistry.REFORGED_ITEM_GROUP);
    }

}
