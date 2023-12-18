package za.lana.signum.client.shaders;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.Signum;

import java.io.IOException;

// Example taken from io.github.cottonmc.cotton.gui.impl.client.LibGuiShaders
public final class SignumShaders extends ShaderProgram{

    private static @Nullable ShaderProgram lostsky;

    public SignumShaders(ResourceFactory factory, String name, VertexFormat format) throws IOException {
        super(factory, name, format);
        assertPresent(getLostsky());

    }

    public static void register() {
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            // Register our core shaders.
            context.register(new Identifier(Signum.MOD_ID,"lostsky"), VertexFormats.POSITION, program -> lostsky = program);
        });
        Signum.LOGGER.info("Registering Dimension Shaders for " + Signum.MOD_ID);
    }
    private static ShaderProgram assertPresent(ShaderProgram program) {
        if (program == null) {
            throw new NullPointerException("Shader signum:" + "lostsky" + " not initialised!");
        }
        return program;
    }
    public static ShaderProgram getLostsky() {
        return assertPresent(lostsky);
    }
}
