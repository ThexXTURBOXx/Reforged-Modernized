package de.femtopedia.reforged.client.renderers;

import de.femtopedia.reforged.api.MaterialApi;
import de.femtopedia.reforged.entity.BoomerangEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

@Environment(value = EnvType.CLIENT)
public class BoomerangEntityRenderer extends EntityRenderer<BoomerangEntity> {

    public static final Identifier TEXTURE = new Identifier("reforged", "textures/entity/boomerang.png");

    private static final float SQRT2 = MathHelper.SQUARE_ROOT_OF_TWO;

    public BoomerangEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BoomerangEntity boomerang, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(
                boomerang.prevPitch + (boomerang.getPitch() - boomerang.prevPitch) * g));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(
                (boomerang.prevYaw + (boomerang.getYaw() - boomerang.prevYaw) * g) - 90F));

        MaterialApi.MaterialData data = MaterialApi.getMaterialData(boomerang.getMaterial());
        boolean renderMaterial = data.material() != ToolMaterials.WOOD;
        MaterialApi.Color color = data.color();
        float ft0 = 0.0F;
        float ft1 = 0.5F;
        float ft2 = 1.0F;

        float fh = 0.08F;
        float f2 = 0.2F;
        float f3 = 0.9F;
        float f4 = 1F - f2;

        float ft3 = 0.5F;
        float ft4 = 0.65625F;

        matrixStack.translate(-0.5F, 0F, -0.5F);

        VertexConsumer vertexConsumer = // TODO Test cull
                vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(boomerang)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f positionMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 1, ft1, ft0, 0, 1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 1, ft0, ft0, 0, 1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 0, ft0, ft1, 0, 1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 0, ft1, ft1, 0, 1, 0, light);

        if (renderMaterial) {
            vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 1, ft2, ft0, color, 0, 1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 1, ft1, ft0, color, 0, 1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 0, ft1, ft1, color, 0, 1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 0, ft2, ft1, color, 0, 1, 0, light);
        }

        vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 0, ft0, ft1, 0, -1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 1, ft1, ft1, 0, -1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 1, ft1, ft0, 0, -1, 0, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 0, ft0, ft0, 0, -1, 0, light);

        if (renderMaterial) {
            vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 0, ft1, ft1, color,
                    0, -1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 1, 0, 1, ft2, ft1, color,
                    0, -1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 1, ft2, ft0, color,
                    0, -1, 0, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, 0, 0, 0, ft1, ft0, color,
                    0, -1, 0, light);
        }

        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f4, ft1, ft3, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f4, ft1, ft4, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f3, fh, f4, ft0, ft4, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f3, -fh, f4, ft0, ft3, -SQRT2, 0F, SQRT2, light);

        if (renderMaterial) {
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f4, ft2, ft3, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f4, ft2, ft4, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f3, fh, f4, ft1, ft4, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f3, -fh, f4, ft1, ft3, color,
                    -SQRT2, 0F, SQRT2, light);
        }

        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f4, ft1, ft3, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f4, ft1, ft4, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f2, ft0, ft4, -SQRT2, 0F, SQRT2, light);
        vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f2, ft0, ft3, -SQRT2, 0F, SQRT2, light);

        if (renderMaterial) {
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f4, ft2, ft3, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f4, ft2, ft4, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, fh, f2, ft1, ft4, color,
                    -SQRT2, 0F, SQRT2, light);
            vertex(positionMatrix, normalMatrix, vertexConsumer, f2, -fh, f2, ft1, ft3, color,
                    -SQRT2, 0F, SQRT2, light);
        }

        matrixStack.pop();
        super.render(boomerang, f, g, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(BoomerangEntity entity) {
        return TEXTURE;
    }

    public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer,
                       float x, float y, float z, float u, float v,
                       float normalX, float normalY, float normalZ, int light) {
        vertex(positionMatrix, normalMatrix, vertexConsumer, x, y, z, u, v, MaterialApi.Color.WHITE,
                normalX, normalY, normalZ, light);
    }

    public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer,
                       float x, float y, float z, float u, float v, MaterialApi.Color color,
                       float normalX, float normalY, float normalZ, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z)
                .color(color.red(), color.green(), color.blue(), color.alpha())
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, normalX, normalY, normalZ)
                .next();
    }

}
