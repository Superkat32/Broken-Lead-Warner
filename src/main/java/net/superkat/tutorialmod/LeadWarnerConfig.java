package net.superkat.tutorialmod;

import net.fabricmc.loader.api.FabricLoader;

public interface LeadWarnerConfig {

    LeadWarnerConfig DEFAULT = FabricLoader.getInstance().isModLoaded("cloth-config") ? LeadWarnerConfigMenu.getInstance() : new LeadWarnerConfig() { };

    default boolean actionBar() {
        return false;
    }

    default boolean rainbowText() {
        return false;
    }

}
//    static class Builder {
//        Screen build(Screen parent) {
//            ConfigBuilder builder = ConfigBuilder.create()
//                    .setParentScreen(parent)
//                    .setTitle(Text.literal("title.tutorialmod.config"));
//            builder.setSavingRunnable(LeadWarnerConfigMenu::save);
//            ConfigCategory general = builder.getOrCreateCategory(Text.of("catagory.tutorialmod.general"));
//            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
//            general.addEntry(entryBuilder.startBooleanToggle(Text.of("tutorialmod.options.actionBar"), TutorialMod.config.actionBar)
//                    .setDefaultValue(false)
//                    .setSaveConsumer(val -> TutorialMod.config.actionBar = val).build());
//            general.addEntry(entryBuilder.startBooleanToggle(Text.of("tutorialmod.options.rainbowText"), TutorialMod.config.rainbowText)
//                    .setDefaultValue(false)
//                    .setSaveConsumer(val -> TutorialMod.config.rainbowText = val).build());
//            return builder.build();
//        }
//    }