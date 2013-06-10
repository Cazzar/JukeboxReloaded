package cazzar.mods.jukeboxreloaded.configuration;

import net.minecraftforge.common.Configuration;

// @ConfigurationClass(category = "default")
public class ConfigHelper {
	public Main		main;
	public Blocks	blocks;
	public Items	items;
	
	public ConfigHelper(Configuration config) {
		Config.Parse(main = new Main(), config);
		Config.Parse(blocks = new Blocks(), config);
		Config.Parse(items = new Items(), config);
	}
	
	@ConfigurationClass
	public class Main {
		public boolean	enableUpdater	= true;
	}
	
	@ConfigurationClass
	public class Blocks {
		@BlockID
		public int	Jukebox	= 3000;
	}
	
	@ConfigurationClass
	public class Items {
		@ConfigurationComment("Some special records")
		public int	record1	= 3001;
		public int	record2	= 3002;
		public int	record3	= 3003;
		public int	record4	= 3004;
		public int	record5	= 3005;
		public int	record6	= 3006;
	}
}
