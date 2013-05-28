package cazzar.mods.jukeboxreloaded.configuration;

import java.lang.reflect.Field;
import java.util.logging.Level;

import cazzar.mods.jukeboxreloaded.lib.util.LogHelper;

import net.minecraftforge.common.Configuration;

public class ConfigurationParser {

	/**
	 * Parse the passed class for annotated fields with the
	 * <i>@ConfigurationOption</i>
	 * 
	 * @author Cayde Dixon (cazzar)
	 * @param instance
	 *            the instance of the configurations option if it is non static
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	public static void Parse(Object instance, Configuration config) {
		Class clazz = instance.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (!field.isAccessible()) field.setAccessible(true);

			ConfigurationOption annotation = field
					.getAnnotation(ConfigurationOption.class);
			if (annotation == null) {
				continue;
			}

			String category = annotation.category();
			String comment = annotation.comment();
			String key = annotation.key();

			try {
				parseField(field, instance, category, key, comment, config);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	private static void parseField(Field field, Object instance,
			String category, String key, String comment, Configuration config)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> type = field.getType();
		if (comment.isEmpty()) comment = null;

		if (type == boolean.class || type == Boolean.class) {
			Object def = field.get(instance);

			Object value;
			value = config.get(category, key,
					(Boolean) def, comment).getBoolean((Boolean) def);

			field.set(instance, value);
		} else if (type == double.class || type == Double.class) {
			Object def = field.get(instance);

			Object value;
			value = config.get(category, key,
					(Double) def, comment).getDouble((Double) def);


			field.set(instance, value);
		} else if (type == String.class) {
			Object def = field.get(instance);

			Object value;
			value = config.get(category, key,
					(String) def, comment).getString();
			
			field.set(instance, value);
		} else if (type == Integer.class || type == int.class) {
			if (field.isAnnotationPresent(BlockID.class)) {
				Object def = field.get(instance);

				Object value;
				value = config.getBlock(category, key,
						(Integer) def, comment).getInt();
				
				field.set(instance, value);
				return;
			}
			if (field.isAnnotationPresent(ItemID.class)) {
				Object def = field.get(instance);

				Object value;
				value = config.getItem(category, key,
						(Integer) def, comment).getInt();
				
				field.set(instance, value);
				return;
			}

			Object def = field.get(instance);

			Object value;
			value = config.get(category, key,
					(Integer) def, comment).getInt((Integer) def);
			
			field.set(instance, value);
		} else if (type == float.class || type == Float.class) {
			Object def = field.get(instance);
			String value;
			value = config.get(category, key, String.valueOf((Float)def), comment).getString();
			
			Float actual = Float.valueOf((String)value);
			field.set(instance, actual);
			//double def = field.getFloat(instance);
			//double value;
			//if (!comment.isEmpty()) value = config.get(category, key, def,
			//		comment).getDouble(def);
			//else value = config.get(category, key, def).getDouble(def);

			//float actualValue = Float.valueOf(String.valueOf(value));
			//field.setFloat(instance, actualValue);

		} else {
			LogHelper.log(Level.WARNING, "Type \"" + type.getName()
					+ "\" is not supportd with annotations");
		}
	}

	public static void ParseClass(Object instance, Configuration config) {
		Class<? extends Object> clazz = instance.getClass();

		ConfigurationClass annotation = (ConfigurationClass) clazz
				.getAnnotation(ConfigurationClass.class);
		if (annotation == null) { return; }
		String category = (annotation.category().isEmpty()) ? clazz
				.getSimpleName() : annotation.category();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (!field.isAccessible()) field.setAccessible(true);
			ConfigurationComment commentAnnotation = field
					.getAnnotation(ConfigurationComment.class);
			String comment = (commentAnnotation == null) ? ""
					: commentAnnotation.value();
			String key = field.getName();

			try {
				parseField(field, instance, category, key, comment, config);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}
