package de.femtopedia.reforged.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSourceAxe extends EntityDamageSource {

    public DamageSourceAxe(Entity source) {
        super("battle_axe", source);
        setBypassesArmor();
    }

}
