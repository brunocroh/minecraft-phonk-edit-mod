package com.brunocroh.phonkedit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.KeyBinding.Category;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.ActionResult;


import org.lwjgl.glfw.GLFW;

public class PhonkEditModClient implements ClientModInitializer {
    private static KeyBinding openTestMenuKey;
    private static boolean frozen = false;
    private static long freezeStartTime = 0;
	private static SoundInstance currentSound; 
    private static long lastPlacedTime = 0;
    private static final long COOLDOWN_MS = 5000; 
    private static boolean airborne = false;

    @Override
    public void onInitializeClient() {
		openTestMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			 "com.brunocroh.phonkedit",
			  GLFW.GLFW_KEY_K,
			   Category.MISC
		));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null) {
                if (client.player.isOnGround()) {
                    if(airborne) {
                        airborne = false;
                        freeze();
                    }
                } else {
                    airborne = true;
                }
            }

            while (openTestMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new SoundTestScreen());
                }
            }

			if (frozen && (System.currentTimeMillis() - freezeStartTime) > 2000) {
                frozen = false;
                client.execute(() -> {
                    client.setScreen(null); 
					System.out.println("currentSound: "+ currentSound);
                    stopCurrentSound(client);
                });
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return this.freeze();
        });
        
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            return this.freeze();
        });
    }

	  private void stopCurrentSound(MinecraftClient client) {
        if (currentSound != null) {
			client.getSoundManager().stopSounds(currentSound.getId(), currentSound.getCategory());
            currentSound = null;
        }
    }

    private ActionResult freeze() {

        if (lastPlacedTime == 0) {
            lastPlacedTime = System.currentTimeMillis() - 4000;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPlacedTime < COOLDOWN_MS) {
            return ActionResult.CONSUME;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (!frozen) {
            frozen = true;
            freezeStartTime = System.currentTimeMillis();

            client.execute(() -> {
                client.setScreen(new FrozenScreen());
            });
        }

        currentSound = PositionedSoundInstance.master(CustomSounds.PASSO_BEM_SOLTO, 1.0f, 3.0f);
        client.getSoundManager().play(currentSound);

        lastPlacedTime = currentTime;
        return ActionResult.CONSUME;

    }
}
