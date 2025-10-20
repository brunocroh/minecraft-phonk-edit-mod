package com.brunocroh.phonkedit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CustomSounds {
	private CustomSounds() {
		// private empty constructor to avoid accidental instantiation
	}

	public static final SoundEvent PASSO_BEM_SOLTO = registerSound("passo-bem-solto");

	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.of(PhonkEditMod.MOD_ID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	public static void initialize() {
		PhonkEditMod.LOGGER.info("Registering " + PhonkEditMod.MOD_ID + " Sounds");
	}
}