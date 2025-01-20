package net.superkat.brokenleadwarner.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.superkat.brokenleadwarner.BrokenLeadHandler;
import net.superkat.brokenleadwarner.config.LeadWarnerConfig;
import net.superkat.brokenleadwarner.duck.BreakableLeashableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Leashable.class)
public interface LeashableMixin {

	//Start of code provided by Enriath(Edited slightly by Superkat32 for 1.21+ update, main idea still remains)

	@Inject(method = "detachLeash(Lnet/minecraft/entity/Entity;ZZ)V", at = @At("HEAD"))
	private static <E extends Entity & Leashable> void brokenleadwarner$onDetach(E entity, boolean sendPacket, boolean dropItem, CallbackInfo ci) {
		if(entity.getLeashData() == null) return;
		int playerId = MinecraftClient.getInstance().player.getId();
		int holdingEntityId = entity.getLeashData().unresolvedLeashHolderId;
		// Only notify if:
		//  - The entity is no longer being led (holdingEntityId == 0)
		//  - The entity that was leading it was the client's player
		//  - The player didn't choose to remove the lead by right clicking

		BreakableLeashableEntity leashable = (BreakableLeashableEntity) entity;
		if(!leashable.isInteract() && holdingEntityId == 0 && leashable.lastHoldingEntityId() == playerId) {
			if(LeadWarnerConfig.enabled) {
				BrokenLeadHandler.onBrokenLead();
			}
		}

		// Store the last entity to interact with the lead, so we know the owner after it detaches
		leashable.setLastHoldingEntityId(holdingEntityId);

		// A bit of a hacky way to make sure the mod knows an interact happened.
		// When the player right clicks to remove a lead, detachLeash gets called twice:
		// once to make the lead drop (in interact), and once to update the holdingEntityId.
		// The notable difference is the former sends a packet, while the latter doesn't, so by using the arg passed in,
		// we can know which call it was from, and only update it on the latter call.
		if(!sendPacket) {
			leashable.setIsInteract(false);
		}

	}

	//End of code provided by Enriath. Thanks!
}