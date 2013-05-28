package cazzar.mods.jukeboxreloaded.lib.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cazzar.mods.jukeboxreloaded.lib.Reference;

public class LogHelper {
	static Logger logger;
	public static void init() {
		logger = Logger.getLogger(Reference.MOD_ID);
	}
	
	public static void log(Level level, String message) { 
		logger.log(level, message);
	}

}
