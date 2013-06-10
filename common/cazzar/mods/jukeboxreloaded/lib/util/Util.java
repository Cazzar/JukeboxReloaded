package cazzar.mods.jukeboxreloaded.lib.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Util {
	public static void saveUrl(String filename, URL url) throws MalformedURLException, IOException
	{
		new File(filename).getParentFile().mkdirs();
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try
		{
			in = new BufferedInputStream(url.openStream());
			fout = new FileOutputStream(filename);
			copyStream(in, fout);
		} finally {
			if (in != null) in.close();
			if (fout != null) fout.close();
		}
	}

	public static void copyStream(InputStream input, OutputStream output) {
		try {
			byte data[] = new byte[8192];
			int count;
			while ((count = input.read(data, 0, 8192)) != -1)
			{
				output.write(data, 0, count);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
