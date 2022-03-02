package de.femtopedia.reforged.entity;

import de.femtopedia.reforged.api.MaterialApi;
import de.femtopedia.reforged.api.ReforgedProjectile;
import de.femtopedia.reforged.api.ReforgedRegistry;
import de.femtopedia.reforged.item.BoomerangItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoomerangEntity extends ReforgedProjectile {

    public static final TrackedData<ItemStack> STACK = DataTracker.registerData(BoomerangEntity.class,
            TrackedDataHandlerRegistry.ITEM_STACK);

    public static final TrackedData<Float> STRENGTH = DataTracker.registerData(BoomerangEntity.class,
            TrackedDataHandlerRegistry.FLOAT);

    public static final double RETURN_STRENGTH = 0.05d;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;

    private boolean beenInGround = false;
    private float soundTimer = 0;
    private PickupPermission pickupPermission;

    public BoomerangEntity(EntityType<BoomerangEntity> type, World world) {
        super(type, world);
    }

    public BoomerangEntity(World world, double x, double y, double z) {
        super(ReforgedRegistry.BOOMERANG_ENTITY.get(), x, y, z, world);
    }

    public BoomerangEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ReforgedRegistry.BOOMERANG_ENTITY.get(), owner, world);
        setOwner(owner);
        setItemStack(stack);
        /*updatePositionAndAngles(owner.getX() - MathHelper.cos((getYaw() / 180f) * MathHelper.PI) * 0.16f,
                owner.getEyeY() - 0.1,
                owner.getZ() - MathHelper.sin((getYaw() / 180f) * MathHelper.PI) * 0.16f,
                owner.getYaw(), owner.getPitch());
        setVelocity(-MathHelper.sin((getYaw() / 180f) * MathHelper.PI)
                        * MathHelper.cos((getPitch() / 180F) * MathHelper.PI),
                MathHelper.cos((getYaw() / 180f) * MathHelper.PI)
                        * MathHelper.cos((getPitch() / 180F) * MathHelper.PI),
                -MathHelper.sin((getPitch() / 180f) * MathHelper.PI), strength, 5);
        setStrength(Math.min(1.5f, strength));*/
    }

    @Override
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        super.setVelocity(shooter, pitch, yaw, roll, speed, divergence);
        setStrength(Math.min(1.5f, speed));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(STACK, ItemStack.EMPTY);
        dataTracker.startTracking(STRENGTH, 0f);
    }

    @Override
    public void tick() {
        super.tick();

        if (inGround) {
            return;
        }

        float strength = dataTracker.get(STRENGTH) * 0.994f;
        if (strength < MIN_FLOAT_STRENGTH) {
            if (isCritical()) {
                setCritical(false);
            }
            strength = 0f;
        }

        if (!beenInGround) {
            setYaw(getYaw() + 20 * strength);
        }

        Entity owner = getOwner();
        if (!beenInGround && owner != null && strength > 0) {
            setVelocity(getVelocity().subtract(
                    getPos().subtract(getOwner().getEyePos())
                            .normalize()
                            .multiply(RETURN_STRENGTH)));

            float limitedStrength = Math.min(1, strength);
            soundTimer += limitedStrength;
            if (soundTimer > 3) {
                world.playSoundFromEntity(null, this, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                        0.6f, 1.0f / (random.nextFloat() * 0.2f + 2.2f - limitedStrength));
                soundTimer %= 3;
            }
        }

        dataTracker.set(STRENGTH, strength);
    }

    @Override
    protected void age() {
        // Don't age
    }

    @Override
    public boolean isNoClip() {
        return true;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return switch (getPickupPermission()) {
            case ALLOWED -> player.getInventory().insertStack(asItemStack());
            case CREATIVE_ONLY -> player.isCreative();
            case OWNER_ONLY -> player == getOwner(); // TODO Untested
            default -> false;
        };
    }

    @Override
    protected ItemStack asItemStack() {
        ItemStack stack = dataTracker.get(STACK);
        if (stack == null) {
            return ItemStack.EMPTY;
        }
        return stack;
    }

    public void setItemStack(ItemStack stack) {
        dataTracker.set(STACK, stack);
    }

    public ToolMaterial getMaterial() {
        ItemStack stack = asItemStack();
        if (stack != null && stack.getItem() instanceof BoomerangItem boomerang) {
            return boomerang.getMaterial();
        }
        return MaterialApi.FALLBACK;
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
        this.setPickupPermissionFromEntity(entity);
    }

    public void setStrength(float strength) {
        strength = Math.min(1.5F, strength);
        dataTracker.set(STRENGTH, strength);
    }

    public float getStrength() {
        return dataTracker.get(STRENGTH);
    }

    public void setPickupPermissionFromEntity(Entity entity) {
        setPickupPermission(PickupPermission.fromEntity(entity));
    }

    public void setPickupPermission(PickupPermission pickupPermission) {
        this.pickupPermission = pickupPermission;
    }

    public PickupPermission getPickupPermission() {
        return pickupPermission == null ? PickupPermission.DISALLOWED : pickupPermission;
    }

    public enum PickupPermission {

        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY,
        OWNER_ONLY;

        public static final PickupPermission[] VALUES = PickupPermission.values();

        public static PickupPermission fromEntity(Entity entity) {
            if (entity instanceof PlayerEntity player) {
                if (player.isCreative()) {
                    return PickupPermission.CREATIVE_ONLY;
                } else {
                    return PickupPermission.ALLOWED;
                    /*TODO BalkonsWeaponMod.instance.modConfig.allCanPickup
                            ? PickupPermission.ALLOWED
                            : PickupPermission.OWNER_ONLY;*/
                }
            }
            return PickupPermission.DISALLOWED;
        }

        public static PickupPermission fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > VALUES.length) {
                ordinal = 0;
            }
            return VALUES[ordinal];
        }

    }

    public static BoomerangEntity create(EntityType<BoomerangEntity> type, World world) {
        return new BoomerangEntity(type, world);
    }

}
