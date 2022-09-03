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
	public static final Identifier MY_SOUND_ID = new Identifier("brokenleadwarner:my_sound");
	public static SoundEvent MY_SOUND_EVENT = new SoundEvent(MY_SOUND_ID);

	@Override
	public void onInitialize() {
		//Loads config booleans
		var enabled = LeadWarnerConfig.getInstance().enabled;
		var altWarningMethod = LeadWarnerConfig.getInstance().altWarningMethod;
		var playSound = LeadWarnerConfig.getInstance().playSound;

		Registry.register(Registry.SOUND_EVENT, BrokenLeadWarner.MY_SOUND_ID, MY_SOUND_EVENT);
		System.out.println("BrokenLeadWarner.java loaded!");
	}
}
