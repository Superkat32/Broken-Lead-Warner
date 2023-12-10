package net.superkat.brokenleadwarner.mixin;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.brokenleadwarner.BrokenLeadWarner;
import net.superkat.brokenleadwarner.LeadWarnerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@Shadow private int holdingEntityId;

	private int lastHoldingEntityId;
	private boolean isInteract;

	//Start of code provided by Enriath.

	// Keep track of if the lead was manually removed by hooking specifically when the game removes the lead.
	@ModifyArg(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;detachLeash(ZZ)V"), index = 0)
	private boolean onInteractDetach(boolean sendPacket) {
		this.isInteract = true;
		return sendPacket;
	}

	@Inject(method = "detachLeash(ZZ)V", at = @At("HEAD"))
	public void onDetach(boolean sendPacket, boolean dropItem, CallbackInfo ci)	{
		int playerId = getInstance().player.getId();
		// Only notify if:
		//  - The entity is no longer being led (holdingEntityId == 0)
		//  - The entity that was leading it was the client's player
		//  - The player didn't choose to remove the lead by right clicking
		if (!this.isInteract && this.holdingEntityId == 0 && this.lastHoldingEntityId == playerId) {
			if (LeadWarnerConfig.enabled) {
				sendWarningMessage();
				playSoundEffect();
			} else if (!LeadWarnerConfig.enabled) {
				BrokenLeadWarner.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
			} else {
				BrokenLeadWarner.LOGGER.warn("Warning Message process abandoned. Unknown reason.");
			}
		}
		// Store the last entity to interact with the lead, so we know the owner after it detaches
		this.lastHoldingEntityId = holdingEntityId;
		// A bit of a hacky way to make sure the mod knows an interact happened.
		// When the player right clicks to remove a lead, detachLeash gets called twice:
		// once to make the lead drop (in interact), and once to update the holdingEntityId.
		// The notable difference is the former sends a packet, while the latter doesn't, so by using the arg passed in,
		// we can know which call it was from, and only update it on the latter call.
		if (!sendPacket) {
			this.isInteract = false;
		}
	}
	//End of code provided by Enriath. Thanks!

	private void sendWarningMessage() {
		if (LeadWarnerConfig.showText) {
			//To add a new warning method, please follow the following steps, future self...
			//First, begin by adding the enum to LeadWarnerConfig.java
			//Second, add an else/if statement here
			//Afterwards, add the text to the language json file(s)
			//Debug if needed... Debug, because it will be needed
			//And ta-da! You now have a new warning method!
			if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.HOTBAR)) {
				//Sends a hotbar message. (A small piece of text above the player's hotbar)
				getInstance().player.sendMessage(Text.translatable("chat.brokenleadwarner.broken_lead").formatted(Formatting.BOLD, Formatting.RED),true);
				BrokenLeadWarner.LOGGER.info("Warning message has been sent!(Hotbar message)");
			} else if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.CHAT)) {
				assert getInstance().player != null;
				//Sends a chat message to the player. Unfortunately, it will have a grey mark next to it
				getInstance().player.sendMessage(Text.translatable("chat.brokenleadwarner.broken_lead").formatted(Formatting.BOLD, Formatting.RED),false);
				BrokenLeadWarner.LOGGER.info("Warning message has been sent!(Chat message)");
			} else {
				BrokenLeadWarner.LOGGER.warn("No warning message sent: none of the boolean conditions were met.");
			}
		} else if (!LeadWarnerConfig.showText) {
			BrokenLeadWarner.LOGGER.info("Warning Text was disabled!");
		} else {
			BrokenLeadWarner.LOGGER.warn("No warning text was sent! Something must have gone wrong...");
		}
	}

	private void playSoundEffect() {
		if (LeadWarnerConfig.playSound) {
			//Plays warning sound
			BrokenLeadWarner.LOGGER.info("Warning sound (seemingly) played!");
			float warningSoundVolume = LeadWarnerConfig.soundVolume / 100;
			getInstance().getSoundManager().play(PositionedSoundInstance.master(BrokenLeadWarner.WARNING_SOUND_EVENT, 1.0F, warningSoundVolume));
		}
	}
}