package net.superkat.brokenleadwarner.mixin;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.brokenleadwarner.BrokenLeadWarner;
import net.superkat.brokenleadwarner.LeadWarnerConfig;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@Shadow @Nullable private Entity holdingEntity;
	public abstract void sendMessage(Text message, boolean actionBar);
	@Inject(at = @At("HEAD"), method = "updateLeash")
	public void init(CallbackInfo ci) {
		MobEntity self = (MobEntity) (Object) this;
		Entity entity = self.getHoldingEntity();
		if (self.getHoldingEntity() != null) {
			//distanceFromLeashedEntity is the distance between the player who has the lead and the leaded entity
			//distanceFromLeashHolder is the distance between the mod user and the player who is/was holding the leaded entity
			float distanceFromLeashedEntity = self.distanceTo(entity);
			float distanceFromLeashHolder = getInstance().player.distanceTo(holdingEntity);
			if (!self.isAlive() || !self.getHoldingEntity().isAlive() || entity != null && entity.world == self.world) {
				//If the distance between a player and a leaded entity is greater than 10 blocks
				if (distanceFromLeashedEntity > 10.0F) {
					//NOTE: This really only works in an LAN environment, which is pretty useless in the long run. It doesn't work in servers.
					//If the distance between the mod user and the player who is holding a leaded entity is less than 0
					//This basically means both players have to be in the exact same position down to the decimal points
					//If it is off by even 0.0001, then it will not trigger the mod
					//If the mod user is leading an entity, then the distance between them and themselves is 0
					if (!(distanceFromLeashHolder > 0.0F)) {
						if (LeadWarnerConfig.enabled) {
							sendWarningMessage();
							playSoundEffect();
						} else if (!LeadWarnerConfig.enabled) {
							BrokenLeadWarner.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
						} else {
							BrokenLeadWarner.LOGGER.warn("Warning Message process abandoned. Unknown reason.");
						}
						//This method could be improved upon in the future if some serious bugs show up
						//This logs the distance between the leash holder and the leashed entity, and the distance between the mod user and the leash holder
						BrokenLeadWarner.LOGGER.info("distanceFromLeashedEntity = " + distanceFromLeashedEntity);
						BrokenLeadWarner.LOGGER.info("distanceFromLeashHolder = " + distanceFromLeashHolder);
					}
				}
			}
		}
	}

	//An older method for detecting if the player's lead broke. I'm keeping this here in case I need it later.
	//I decided to change it because I really wanted to add multiplayer support, but I couldn't figure it out.
//		PathAwareEntity self = (PathAwareEntity) (Object) this;
//		Entity entity = self.getHoldingEntity();
//		if (entity != null && entity.world == self.world) {
//			float f = self.distanceTo(entity);
////			if (self instanceof TameableEntity && ((TameableEntity)self).isInSittingPose()) {
//			//if the entity is more than 10 blocks(?) away from the player with the leash
//			if (f > 10.0F) {
//				if (DeleteLater.getInstance().enabled) {
//					sendWarningMessage();
//				} else if (!DeleteLater.getInstance().enabled) {
//					BrokenLeadWarner.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
//				} else {
//					BrokenLeadWarner.LOGGER.warn("Warning Message process abandoned. Unknown reason.");
//				}
//				//This method could be improved upon in the future if some serious bugs show up
//			}
////			}
//		}

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