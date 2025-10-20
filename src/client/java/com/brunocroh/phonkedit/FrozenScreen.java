package com.brunocroh.phonkedit;

import java.util.Random;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FrozenScreen extends Screen {
    private static final Identifier[] TEXTURES = {
        Identifier.of("phonkedit", "textures/shh.png"),
        Identifier.of("phonkedit", "textures/blueemoji.png"),
    };
    private static final int TEXTURE_WIDTH = 64; 
    private static final int TEXTURE_HEIGHT = 64; 
    private final Identifier selectedTexture;

    protected FrozenScreen() {
        super(Text.literal("Frozen"));
        Random random = new Random();
        selectedTexture = TEXTURES[random.nextInt(TEXTURES.length)];
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

      @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xAA808080);
        int padding = 5;
        int x = (this.width - TEXTURE_WIDTH) / 2;
        int y = (this.height + padding) - 39 - TEXTURE_HEIGHT;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, selectedTexture, x, y, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        super.render(context, mouseX, mouseY, delta);
    }
}
