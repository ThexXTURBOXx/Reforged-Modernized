package de.femtopedia.reforged.fabric;

import de.femtopedia.reforged.fabriclike.ReforgedModFabricLike;
import net.fabricmc.api.ModInitializer;

public class ReforgedModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ReforgedModFabricLike.init();
    }

}
