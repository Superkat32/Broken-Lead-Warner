package net.superkat.tutorialmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static LeadWarnerConfig getConfig() {
		return LeadWarnerConfig.DEFAULT;
	}

	@Override
	public void onInitialize() {

		System.out.println("TutorialMod.java loaded!");
	}
}
