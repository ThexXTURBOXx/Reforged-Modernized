package de.femtopedia.reforged.api;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

public abstract class ReforgedProjectile extends PersistentProjectileEntity {

    protected ReforgedProjectile(EntityType<? extends ReforgedProjectile> entityType, World world) {
        super(entityType, world);
    }

    protected ReforgedProjectile(EntityType<? extends ReforgedProjectile> entityType, double x, double y, double z,
                                 World world) {
        super(entityType, x, y, z, world);
    }

    protected ReforgedProjectile(EntityType<? extends ReforgedProjectile> entityType, LivingEntity livingEntity,
                                 World world) {
        super(entityType, livingEntity, world);
    }

}
