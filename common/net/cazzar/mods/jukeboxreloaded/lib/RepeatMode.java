package net.cazzar.mods.jukeboxreloaded.lib;

public enum RepeatMode {
    OFF, ALL, ONE;

    private static RepeatMode[] valid = {OFF, ALL, ONE};

    public static RepeatMode get(int mode) {
        return valid[mode];
    }
}
