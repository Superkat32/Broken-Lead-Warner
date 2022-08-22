package net.superkat.brokenleadwarner;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("TutorialModClient.java is loaded!");
    }
}
