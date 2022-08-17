package net.superkat.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.tutorialmod.LeadWarnerConfig;
import net.superkat.tutorialmod.TutorialMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;

@Mixin(PathAwareEntity.class)
public abstract class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "updateLeash")
	public void init(CallbackInfo ci) {
		PathAwareEntity self = (PathAwareEntity) (Object) this;
		Entity entity = self.getHoldingEntity();
		if (entity != null && entity.world == self.world) {
			float f = self.distanceTo(entity);
//			if (self instanceof TameableEntity && ((TameableEntity)self).isInSittingPose()) {
			//if the entity is more than 10 blocks(?) away from the player with the leash
			if (f > 10.0F) {
				if (LeadWarnerConfig.getInstance().enabled) {
					sendWarningMessage();
				} else if (!LeadWarnerConfig.getInstance().enabled) {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
				} else {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Unknown reason.");
				}
				//This method could be improved upon in the future if some serious bugs show up
			}
//			}
		}
	}


	private void sendWarningMessage() {
		TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
		/*
		FIXME - Mod will amplify sound if multiple leads break
		I'm possibly going to be keeping this for now until I feel like it messes up the mod in more than 1 way.
		FIXME - Change the whole method on how the mod realizes that the lead is tied to a fence.
		*/
		playSoundEffect();
		if (LeadWarnerConfig.getInstance().actionBar) {
			//Activates if the action bar setting is true
			//Sends a hotbar message. (A small piece of text above the player's hotbar)
			getInstance().inGameHud.setOverlayMessage(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED), false);
		} else if (!LeadWarnerConfig.getInstance().actionBar) {
			//Sends a bigger message in the center of the player's screen
			//It will take 0.5 seconds for the title to fade in, stay on screen for 40 seconds, then fade out in 0.75 seconds
//			TODO - Add config for fadeInTicks/stayTicks/fadeOutTicks
			getInstance().inGameHud.setTitleTicks(10, 40, 15);
			getInstance().inGameHud.setTitle(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED));
		} else {
			TutorialMod.LOGGER.info("No warning message sent: none of the boolean conditions were met.");
		}
	}

	private void playSoundEffect() {
		if (LeadWarnerConfig.getInstance().playSound) {
			//Plays warning sound
			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(TutorialMod.MY_SOUND_EVENT, 1.0F, 1.0F));
			TutorialMod.LOGGER.info("Playing sound!");
		} else {
			TutorialMod.LOGGER.info("Playing Sound process abandoned. Option disabled.");
		}

	}

}
