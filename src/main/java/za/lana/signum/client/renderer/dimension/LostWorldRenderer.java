
package za.lana.signum.client.renderer.dimension;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import za.lana.signum.effect.ModEffects;

@Environment(EnvType.CLIENT)
public class LostWorldRenderer  {
    public float tickDelta;
	private static float red1;
	private static float green1;
	private static float blue1;
	private static float alpha1;
	private static float f;
    private static MinecraftClient client;

    private static final Identifier SUN = new Identifier("signum:textures/environment/sun_glow.png");
    private static final Identifier LOST_WORLD_SKY = new Identifier("signum:textures/environment/rainbow_sky.png");

	public static void render(WorldRenderContext context) {

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		//renderTexture(context);
		renderStatusEffects(context);
		//renderVanillaSky(context);
		//
		RenderSystem.setShader(GameRenderer::getPositionProgram);
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
	}
	public static void renderStatusEffects(WorldRenderContext context) {
		Camera camera = context.camera();
		//CameraSubmersionType cameraSubmersionType = context.camera().getSubmersionType();
		Entity entity = camera.getFocusedEntity();
		//
		//RenderSystem.enableBlend();
		//RenderSystem.defaultBlendFunc();
		//RenderSystem.depthMask(false);
		//RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
		//-------------///
		if (entity instanceof ClientPlayerEntity) {
			if (((ClientPlayerEntity) entity).hasStatusEffect(ModEffects.BURN_EFFECT)){
				//everything is red
				RenderSystem.setShaderColor(0.7F, 0.3F, 0.1F, 0.25F);
			}
			if (((ClientPlayerEntity) entity).hasStatusEffect(ModEffects.TIBERIUM_POISON)){
				//everything is green
				RenderSystem.setShaderColor(0.1F, 0.7F, 0.3F, 0.25F);
			}
			if (((ClientPlayerEntity) entity).hasStatusEffect(ModEffects.FREEZE_EFFECT)){
				//everything is blue
				RenderSystem.setShaderColor(0.1F, 0.3F, 0.7F, 0.25F);
			}
			//RenderSystem.depthMask(true);
			//RenderSystem.disableBlend();
		}
	}
	// Vanilla Sky - WIP
	public static BufferBuilder.BuiltBuffer renderVanillaSky(WorldRenderContext context){
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		float g = Math.signum(f) * 512.0F;
		float h = 512.0F;
		//
		bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION);
		bufferBuilder.vertex(0.0, f, 0.0).next();
		for(int i = -180; i <= 180; i += 45) {
			bufferBuilder.vertex(g * MathHelper.cos((float)i * 0.017453292F), f, 512.0F * MathHelper.sin((float)i * 0.017453292F)).next();
		}
		RenderSystem.setShader(GameRenderer::getPositionProgram);
		RenderSystem.setShaderFogStart(1.0F);

		return bufferBuilder.end();
	}
	// Adapted from Fabric Rendering v1/ EndSky
	// gl texture instead?
	public static BufferBuilder.BuiltBuffer renderTexture(WorldRenderContext context){
		RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
		//
		RenderSystem.setShaderTexture(0, LOST_WORLD_SKY);
		RenderSystem.setShaderFogStart(1.0F);
		//
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		//
		Matrix4f matrix4f = context.matrixStack().peek().getPositionMatrix();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		//
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		//
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -99.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -99.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		//
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		//
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 101.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		//
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		//
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
		bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
		tessellator.draw();
		//
		return bufferBuilder.end();
	}

	/**
	 * 		// Messing with world color
	 * 		float red1 = (float) cos(0.10F)+(context.tickDelta());
	 * 		float green1 = (float) cos(0.03F)+(context.tickDelta());
	 * 		float blue1 = (float) cos(0.05F)+(context.tickDelta());
	 * 		float alpha1 = (float) cos(0.03F)+(context.tickDelta());
	 * 		RenderSystem.setShaderFogColor(red1, green1, blue1, alpha1);
	 */
}
