package net.superkat.brokenleadwarner;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.brokenleadwarner.config.LeadWarnerConfig;

public class BrokenLeadHandler {

    //To add a new warning method, please follow the following steps, future self...
    //First, begin by adding the enum to LeadWarnerConfig.java
    //Second, add an else/if statement here
    //Afterwards, add the text to the language json file(s)
    //Debug if needed... Debug, because it will be needed
    //And ta-da! You now have a new warning method!

    public static void onBrokenLead() {
        sendWarningMessage();
        playSoundEffect();
    }

    public static void sendWarningMessage() {
        if (!LeadWarnerConfig.showText) return;

        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        Text message = Text.translatable("chat.brokenleadwarner.broken_lead").formatted(Formatting.BOLD, Formatting.RED);

        if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.HOTBAR)) {
            //Sends a hotbar message. (A small piece of text above the player's hotbar)
            client.player.sendMessage(message,true);
        } else if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.CHAT)) {
            //Sends a chat message to the player. Unfortunately, it will have a grey mark next to it
            client.player.sendMessage(message,false);
        }
    }

    public static void playSoundEffect() {
        if (LeadWarnerConfig.playSound) {
            //Plays warning sound
            float warningSoundVolume = LeadWarnerConfig.soundVolume / 100;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(BrokenLeadWarner.WARNING_SOUND_EVENT, 1.0F, warningSoundVolume));
        }
    }

}
