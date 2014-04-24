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

package net.cazzar.mods.jukeboxreloaded.lib.util;

import java.io.*;
import java.net.URL;

public class Util {
    public static void copyStream(InputStream input, OutputStream output) {
        try {
            final byte data[] = new byte[8192];
            int count;
            while ((count = input.read(data, 0, 8192)) != -1)
                output.write(data, 0, count);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveUrl(String filename, URL url)
            throws IOException {
        //noinspection ResultOfMethodCallIgnored
        new File(filename).getParentFile().mkdirs();
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(filename);
            copyStream(in, fout);
        } finally {
            if (in != null) in.close();
            if (fout != null) fout.close();
        }
    }
}
