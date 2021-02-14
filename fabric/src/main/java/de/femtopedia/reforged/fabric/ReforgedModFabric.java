package de.femtopedia.reforged.fabric;

import de.femtopedia.reforged.ReforgedMod;
import net.fabricmc.api.ModInitializer;

public class ReforgedModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ReforgedMod.init();
    }

}
