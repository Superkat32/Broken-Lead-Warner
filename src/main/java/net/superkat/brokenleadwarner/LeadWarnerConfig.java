package net.superkat.brokenleadwarner;

import eu.midnightdust.lib.config.MidnightConfig;

public class LeadWarnerConfig extends MidnightConfig {

    @Entry public static boolean enabled = true;
//    @Entry public static boolean altWarningMethod = false;
    @Entry public static WarningMethodEnum warningMethodEnum = WarningMethodEnum.HOTBAR;
    public static enum WarningMethodEnum {
        HOTBAR, CHAT
    }
    @Entry public static boolean playSound = true;
    @Entry public static boolean showText = true;
    @Entry(min=0, max=100) public static int soundVolume = 100;
}
