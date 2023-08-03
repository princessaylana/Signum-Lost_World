/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class SkyForgeScreen extends HandledScreen<SkyForgeScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/gui/skyforge_gui.png");
    public SkyForgeScreen(SkyForgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        // arrow
        if(handler.isCrafting()) {
            //where the textures are animated
            context.drawTexture(TEXTURE, x + 80, y + 34, 176, 14, handler.getScaledProgress(), 16);
        }
        // fire
        if(handler.hasFuel()) {
            context.drawTexture(TEXTURE, x + 56, y + 36 + 14 - handler.getScaledFuelProgress(), 176,
                    14 - handler.getScaledFuelProgress(), 14, handler.getScaledFuelProgress());
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

}
