package cazzar.mods.jukeboxreloaded.configuration;

import java.lang.reflect.Field;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class Config {
	
	/**
	 * Parse the passed class for annotated fields with the
	 * <i>@ConfigurationOption</i>
	 * 
	 * @author Cayde Dixon (cazzar)
	 * @param instance the instance of the configurations option if it is non
	 *            static
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	public static void Parse(Object instance, Configuration config) {
		final Class clazz = instance.getClass();
		final Field[] fields = clazz.getDeclaredFields();
		
		for (final Field field : fields) {
			final ConfigurationOption annotation = field
					.getAnnotation(ConfigurationOption.class);
			if (annotation == null) continue;
			final Class type = field.getType();
			
			try {
				if (type == boolean.class || type == Boolean.class) {
					final Boolean def = field.getBoolean(instance);
					final Boolean value = config.get(annotation.category(),
							annotation.key(), def, annotation.comment())
							.getBoolean(def);
					
					field.setBoolean(instance, value);
				}
				if (type == Double.class) {
					final Double def = field.getDouble(instance);
					final Double value = config.get(annotation.category(),
							annotation.key(), def, annotation.comment())
							.getDouble(def);
					
					field.setDouble(instance, value);
				}
				if (type == String.class) {
					final String def = (String) field.get(instance);
					final String value = config.get(annotation.category(),
							annotation.key(), def, annotation.comment())
							.getString();
					
					field.set(instance, value);
				}
				if (type == Integer.class || type == int.class) {
					final Integer def = field.getInt(instance);
					final Integer value = config.get(annotation.category(),
							annotation.key(), def, annotation.comment())
							.getInt(def);
					
					field.setInt(instance, value);
				}
				if (type == Property.class) {
					// Property def = (Property)field.get(instance);
					final Property value = config.get(annotation.category(),
							annotation.key(), 0, annotation.comment());
					
					field.set(instance, value);
				}
				
			}
			catch (final IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
