package cazzar.mods.jukeboxreloaded.configuration;

import java.lang.reflect.Field;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class Config {

	/**
	 * Parse the passed class for annotated fields with the <i>@ConfigurationOption</i>
	 * @author Cayde Dixon (cazzar)
	 * @param instance the instance of the configurations option if it is non static
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	public static void Parse(Object instance, Configuration config)
	{
		Class clazz = instance.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields)
		{
			ConfigurationOption annotation = (ConfigurationOption)field.getAnnotation(ConfigurationOption.class);
			if (annotation == null)
				continue;
			Class type = field.getType();
			
			try {
				if (type == boolean.class || type == Boolean.class){
					Boolean def = field.getBoolean(instance);		
					Boolean value = config.get(annotation.category(), annotation.key(), def, annotation.comment()).getBoolean(def);
					
					field.setBoolean(instance, value);
				}
				if (type == Double.class){
					Double def = field.getDouble(instance);
					Double value = config.get(annotation.category(), annotation.key(), def, annotation.comment()).getDouble(def);
					
					field.setDouble(instance, value);
				}
				if (type == String.class){
					String def = (String)field.get(instance);
					String value = config.get(annotation.category(), annotation.key(), def, annotation.comment()).getString();
					
					field.set(instance, value);
				}
				if (type == Integer.class || type == int.class){
					Integer def = field.getInt(instance);
					Integer value = config.get(annotation.category(), annotation.key(), def, annotation.comment()).getInt(def);
					
					field.setInt(instance, value);					
				}
				if (type == Property.class){
					//Property def = (Property)field.get(instance);
					Property value = config.get(annotation.category(), annotation.key(), 0, annotation.comment());
					
					field.set(instance, value);
				}
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
