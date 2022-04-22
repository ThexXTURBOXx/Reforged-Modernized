package de.femtopedia.reforged.quilt;

import de.femtopedia.reforged.fabriclike.ReforgedModFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class ReforgedModQuilt implements ModInitializer {

    @Override
    public void onInitialize(ModContainer mod) {
        ReforgedModFabricLike.init();
    }

}
