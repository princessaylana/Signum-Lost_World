package za.lana.signum.client.renderer.dimension;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import za.lana.signum.client.shaders.SignumShaders;

@Environment(EnvType.CLIENT)
public class LostWorldRenderer  {
    public float tickDelta;
    private static MinecraftClient client;
    //private static final Identifier END_SKY = new Identifier("textures/block/dirt.png");
    private static final Identifier SUN = new Identifier("signum:textures/environment/sun_glow.png");
    private static final Identifier LOST_WORLD_SKY = new Identifier("signum:textures/environment/rainbow_sky.png");



	public static void render(WorldRenderContext context) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
		//
		/**
		RenderSystem.setShaderTexture(0, LOST_WORLD_SKY);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Matrix4f matrix4f = context.matrixStack().peek().getPositionMatrix();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -99.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -99.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 101.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		tessellator.draw();
		//
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
		//
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		 **/
		//
		RenderSystem.setShader(SignumShaders::getLostsky);
		//
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
	}

}
