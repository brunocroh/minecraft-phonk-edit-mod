package com.brunocroh.phonkedit;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class SoundTestScreen extends Screen {
	public SoundTestScreen() {
		super(Text.literal("Sound Test Menu"));
	}

	@Override
	protected void init() {
		super.init();

		// Add a button in the center of the screen
		this.addDrawableChild(
			ButtonWidget.builder(Text.literal("Play Sound"), button -> {
				// Play the sound when button is clicked
				if (this.client != null && this.client.player != null) {
					this.client.player.playSound(CustomSounds.PASSO_BEM_SOLTO, 1.0f, 1.0f);
					System.out.println("Sound button clicked!");
				}
			})
			.dimensions(this.width / 2 - 100, this.height / 2 - 10, 200, 20)
			.build()
		);

		// Add a close button
		this.addDrawableChild(
			ButtonWidget.builder(Text.literal("Close"), button -> {
				if (this.client != null) {
					this.client.setScreen(null);
				}
			})
			.dimensions(this.width / 2 - 100, this.height / 2 + 20, 200, 20)
			.build()
		);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		// Draw title
		context.drawCenteredTextWithShadow(
			this.textRenderer,
			this.title,
			this.width / 2,
			20,
			0xFFFFFF
		);

		// Draw instructions
		context.drawCenteredTextWithShadow(
			this.textRenderer,
			Text.literal("Click the button to test the custom sound"),
			this.width / 2,
			this.height / 2 - 30,
			0xAAAAAA
		);
	}

	@Override
	public boolean shouldPause() {
		return false;
	}
}
