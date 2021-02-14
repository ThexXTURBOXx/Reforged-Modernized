package de.femtopedia.reforged.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.femtopedia.reforged.api.ReforgedProperties;
import de.femtopedia.reforged.api.ReforgedRegistry;
import de.femtopedia.reforged.damage.DamageSourceAxe;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;

import static de.femtopedia.reforged.api.ReforgedRegistry.IGNORE_ARMOR_DAMAGE_ID;

public class ItemBattleaxe extends AxeItem implements Vanishable {

    private static final float BASE_DAMAGE = 3;
    private static final float BASE_SPEED = -3;

    private final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> modifiers;

    private final float ignoreArmor;

    public ItemBattleaxe(ToolMaterial material, float ignoreArmor) {
        super(material, BASE_DAMAGE, BASE_SPEED, new ReforgedProperties());

        this.ignoreArmor = ignoreArmor;

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
                "Weapon modifier", getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
                "Weapon modifier", BASE_SPEED, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReforgedRegistry.IGNORE_ARMOR_DAMAGE, new EntityAttributeModifier(IGNORE_ARMOR_DAMAGE_ID,
                "Weapon modifier", ignoreArmor, EntityAttributeModifier.Operation.ADDITION));
        modifiers = builder.build();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.damage(new DamageSourceAxe(attacker), ignoreArmor);
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? modifiers : super.getAttributeModifiers(slot);
    }

}
