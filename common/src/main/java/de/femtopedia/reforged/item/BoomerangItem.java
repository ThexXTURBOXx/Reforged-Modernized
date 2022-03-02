package de.femtopedia.reforged.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.femtopedia.reforged.api.ReforgedProperties;
import de.femtopedia.reforged.entity.BoomerangEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BoomerangItem extends AxeItem implements Vanishable {

    private static final float BASE_DAMAGE = 3;
    private static final float BASE_SPEED = -3;

    private final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> modifiers;

    public BoomerangItem(ToolMaterial material) {
        super(material, BASE_DAMAGE, BASE_SPEED, new ReforgedProperties());

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
                "Weapon modifier", getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
                "Weapon modifier", BASE_SPEED, EntityAttributeModifier.Operation.ADDITION));
        modifiers = builder.build();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? modifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int j = getMaxUseTime(stack) - remainingUseTicks;
            float strength = j / 20f;
            strength = (strength * strength + strength * 2.0f) / 3f;
            if (strength < 0.1f) {
                return;
            }

            boolean critical = false;
            if (strength > 1.5f) {
                strength = 1.5f;
                critical = true;
            }
            strength *= 1.5f;

            world.playSoundFromEntity(null, player, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                    0.6f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.0f));
            if (!world.isClient) {
                BoomerangEntity boomerang = new BoomerangEntity(world, player, stack.copy());
                boomerang.setCritical(critical);
                boomerang.setVelocity(player, player.getPitch(), player.getYaw(), 0, strength, 5);
                boomerang.setPunch(EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, stack));
                if (EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                    boomerang.setOnFireFor(100);
                }
                world.spawnEntity(boomerang);
            }

            if (!player.isCreative()) {
                ItemStack newStack = stack.copy();
                newStack.decrement(1);
                player.getInventory().setStack(player.getInventory().selectedSlot, newStack);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

}
