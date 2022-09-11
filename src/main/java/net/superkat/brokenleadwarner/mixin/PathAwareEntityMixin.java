package net.superkat.brokenleadwarner.mixin;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.brokenleadwarner.BrokenLeadWarner;
import net.superkat.brokenleadwarner.LeadWarnerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;

@Mixin(PathAwareEntity.class)
public abstract class PathAwareEntityMixin {
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
					BrokenLeadWarner.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
				} else {
					BrokenLeadWarner.LOGGER.warn("Warning Message process abandoned. Unknown reason.");
				}
				//This method could be improved upon in the future if some serious bugs show up
			}
//			}
		}
	}

	private void sendWarningMessage() {
		playSoundEffect();
		if (LeadWarnerConfig.getInstance().showText) {
			if (LeadWarnerConfig.getInstance().altWarningMethod) {
				assert getInstance().player != null;
				//Sends a chat message to the player. Unfortunately, it will have a grey mark next to it
				getInstance().player.sendMessage(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED),false);
			} else if (!LeadWarnerConfig.getInstance().altWarningMethod) {
				//Sends a hotbar message. (A small piece of text above the player's hotbar)
				getInstance().inGameHud.setOverlayMessage(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED), false);
		/*			This old system worked fine until a random point in time
		//			Fabric API would occasionally throw an error with something seemingly related to this bit of code here, but I couldn't figure out how to fix it
		//			Besides, I think that chat is more ideal anyway
		//			I'm just leaving it here in case I want to try again later

				//Sends a bigger message in the center of the player's screen
				//It will take 0.5 seconds for the title to fade in, stay on screen for 2 seconds, then fade out in 0.75 seconds
		//			getInstance().inGameHud.setTitleTicks(10, 40, 15);
		//			getInstance().inGameHud.setTitle(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED));
		*/		} else {
				BrokenLeadWarner.LOGGER.info("No warning message sent: none of the boolean conditions were met.");
			}
		} else if (!LeadWarnerConfig.getInstance().showText) {
			BrokenLeadWarner.LOGGER.info("Warning Text was disabled!");
		} else {
			BrokenLeadWarner.LOGGER.warn("No warning text was sent! Something must have gone wrong...");
		}
	}

	private void playSoundEffect() {
		if (LeadWarnerConfig.getInstance().playSound) {
			//Plays warning sound
			//Dear future self...
			//I've placed the sound in ambient for now because I couldn't figure out how to move it outside of master other than ambient
			//If I figure out how to move it over to noteblocks then I'll do that, but until then, it'll stay in ambient
			//Good mourning future self... I have figured out how to play it in the noteblocks catagory, however...
			//It seems to cause some weird issues with the subtitles
			//Because of that, I will not be adding it
			getInstance().getSoundManager().play(PositionedSoundInstance.ambient(BrokenLeadWarner.MY_SOUND_EVENT, 1.0F, 1.0F));
		}
	}
}
