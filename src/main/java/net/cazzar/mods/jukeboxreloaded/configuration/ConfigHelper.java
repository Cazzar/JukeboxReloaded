/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

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
        @ConfigurationComment("A portable Jukebox")
        @ItemID
        public int portableJukeboxId = 5007;
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
