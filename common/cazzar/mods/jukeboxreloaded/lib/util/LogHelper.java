package cazzar.mods.jukeboxreloaded.lib.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cazzar.mods.jukeboxreloaded.lib.Reference;

public class LogHelper {
	static Logger	logger;
	
	public static void config(String string, Object... args) {
		log(Level.CONFIG, string, args);
	}
	
	public static void fine(String string, Object... args) {
		log(Level.FINE, string, args);
	}
	
	public static void finer(String string, Object... args) {
		log(Level.FINER, string, args);
	}
	
	public static void finest(String string, Object... args) {
		log(Level.FINEST, string, args);
	}
	
	public static void info(String string, Object... args) {
		log(Level.INFO, string, args);
	}
	
	public static void init() {
		logger = Logger.getLogger(Reference.MOD_ID);
	}
	
	public static void log(Level level, String message, Object... args) {
		logger.log(level, String.format(message, args));
	}
	
	public static void severe(String string, Object... args) {
		log(Level.SEVERE, string, args);
	}
	
	public static void warning(String string, Object... args) {
		log(Level.WARNING, string, args);
	}
}
