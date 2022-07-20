package net.superkat.tutorialmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
		@Override
	public void onInitialize() {
			var enabled = LeadWarnerConfig.getInstance().enabled;
			var actionBar = LeadWarnerConfig.getInstance().actionBar;
			var playSound = LeadWarnerConfig.getInstance().playSound;
			System.out.println("TutorialMod.java loaded!");
		}
}
