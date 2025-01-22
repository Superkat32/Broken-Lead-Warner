package net.superkat.brokenleadwarner.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class LeadWarnerConfig extends MidnightConfig {

    @Entry public static boolean enabled = true;
    @Entry public static WarningMethodEnum warningMethodEnum = WarningMethodEnum.HOTBAR;
    @Entry public static boolean showMobName = true;
    @Entry public static boolean playSound = true;
    @Entry public static WarningSoundType warningSoundType = WarningSoundType.WARNING_PING;
    @Entry public static boolean showText = true;
    @Entry(name = "Warning Sound Volume", isSlider = true, min=0, max=100) public static float soundVolume = 100;

    public static enum WarningMethodEnum {
        HOTBAR, CHAT
    }
    public static enum WarningSoundType {
        VANILLA_SNAP, WARNING_PING
    }

}
