package net.cazzar.mods.jukeboxreloaded.configuration;

import net.cazzar.corelib.configuration.Config;
import net.cazzar.corelib.configuration.annotations.BlockID;
import net.cazzar.corelib.configuration.annotations.ConfigurationClass;
import net.cazzar.corelib.configuration.annotations.ConfigurationComment;
import net.cazzar.corelib.configuration.annotations.ItemID;
import net.minecraftforge.common.Configuration;

// @ConfigurationClass(category = "default")
public class ConfigHelper {
    @ConfigurationClass
    public class Blocks {
        @BlockID
        @ConfigurationComment("The item ID for the Jukebox")
        public int Jukebox = 3000;
    }

    @ConfigurationClass
    public class Items {
        @ConfigurationComment("Some special records")
        @ItemID
        public int record1 = 5001;
        @ItemID
        public int record2 = 5002;
        @ItemID
        public int record3 = 5003;
        @ItemID
        public int record4 = 5004;
        @ItemID
        public int record5 = 5005;
        @ItemID
        public int record6 = 5006;
    }

    @ConfigurationClass
    public class Main {
        public boolean enableUpdater = true;
    }

    public Main main;
    public Blocks blocks;
    public Items items;

    public ConfigHelper(Configuration config) {
        Config.parse(main = new Main(), config);
        Config.parse(blocks = new Blocks(), config);
        Config.parse(items = new Items(), config);
        if (config.hasChanged())
            config.save();
    }
}
