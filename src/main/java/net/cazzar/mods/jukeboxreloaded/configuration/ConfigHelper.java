/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.configuration;

import net.cazzar.corelib.configuration.Config;
import net.cazzar.corelib.configuration.annotations.ConfigurationClass;
import net.minecraftforge.common.config.Configuration;


@ConfigurationClass(category = "default")
public class ConfigHelper {
    @Config.Exclude
    public final Config config;
    public Main main = new Main();

    public ConfigHelper(Configuration config) {
        this.config = new Config(config).setInstance(this);
        try {
            this.config.build();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (config.hasChanged())
            config.save();
    }

    public class Main {
        @SuppressWarnings("UnusedDeclaration")
        public boolean enableUpdater = true;
    }
}
