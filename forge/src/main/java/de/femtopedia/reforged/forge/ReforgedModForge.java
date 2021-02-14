package de.femtopedia.reforged.forge;

import de.femtopedia.reforged.ReforgedMod;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ReforgedMod.MOD_ID)
public class ReforgedModForge {

    public ReforgedModForge() {
        EventBuses.registerModEventBus(ReforgedMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ReforgedMod.init();
    }

}
