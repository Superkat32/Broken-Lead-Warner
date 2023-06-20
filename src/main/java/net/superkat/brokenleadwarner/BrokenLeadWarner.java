package net.superkat.brokenleadwarner;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokenLeadWarner implements ModInitializer {
	public static final String MOD_ID = "brokenleadwarner";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier WARNING_SOUND_ID = new Identifier("brokenleadwarner:warning_sound");
	public static SoundEvent WARNING_SOUND_EVENT = SoundEvent.of(WARNING_SOUND_ID);
	//gosh dangit branches

	@Override
	public void onInitialize() {
		//Loads config
		MidnightConfig.init("brokenleadwarner", LeadWarnerConfig.class);

		//Registers broken lead notification sound
		Registry.register(Registries.SOUND_EVENT, BrokenLeadWarner.WARNING_SOUND_ID, WARNING_SOUND_EVENT);
		System.out.println("BrokenLeadWarner.java loaded!");
	}
}
