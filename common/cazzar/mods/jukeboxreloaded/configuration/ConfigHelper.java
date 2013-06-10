package cazzar.mods.jukeboxreloaded.configuration;

import net.minecraftforge.common.Configuration;

// @ConfigurationClass(category = "default")
public class ConfigHelper {
	@ConfigurationClass
	public class Blocks {
		@BlockID
		@ConfigurationComment("The item ID for the Jukebox")
		public int	Jukebox	= 3000;
	}
	
	@ConfigurationClass
	public class Items {
		@ConfigurationComment("Some special records")
		@ItemID
		public int	record1	= 3001;
		@ItemID
		public int	record2	= 3002;
		@ItemID
		public int	record3	= 3003;
		@ItemID
		public int	record4	= 3004;
		@ItemID
		public int	record5	= 3005;
		@ItemID
		public int	record6	= 3006;
	}
	
	@ConfigurationClass
	public class Main {
		public boolean	enableUpdater	= true;
	}
	
	public Main		main;
	public Blocks	blocks;
	public Items	items;
	
	public ConfigHelper(Configuration config) {
		Config.ParseClass(main = new Main(), config);
		Config.ParseClass(blocks = new Blocks(), config);
		Config.ParseClass(items = new Items(), config);
		config.save();
	}
}
