package net.superkat.brokenleadwarner;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokenLeadWarner implements ModInitializer {
	public static final String MOD_ID = "brokenleadwarner";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier WARNING_SOUND_ID = new Identifier("brokenleadwarner:warning_sound");
	public static SoundEvent WARNING_SOUND_EVENT = new SoundEvent(WARNING_SOUND_ID);

	@Override
	public void onInitialize() {
		//Loads config booleans
		var enabled = LeadWarnerConfig.getInstance().enabled;
		var altWarningMethod = LeadWarnerConfig.getInstance().altWarningMethod;
		var playSound = LeadWarnerConfig.getInstance().playSound;
        var showText = LeadWarnerConfig.getInstance().showText;

		//Registers broken lead notification sound
		Registry.register(Registry.SOUND_EVENT, BrokenLeadWarner.WARNING_SOUND_ID, WARNING_SOUND_EVENT);
		System.out.println("BrokenLeadWarner.java loaded!");
	}
}
