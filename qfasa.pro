-injars 'G:\development\source\JukeboxReloaded\build\1.1.1.1.2\JukeboxReloaded-universal-1.5.2-1.1.1.1.2-Forge-7.8.0.686.jar'
-outjars 'G:\development\source\JukeboxReloaded\build\1.1.1.1.2\JukeboxReloaded-universal-1.5.2-1.1.1.1.2-Forge-7.8.0.686.obf.jar'

-libraryjars 'C:\Program Files\Java\jre7\lib\rt.jar'
-libraryjars 'G:\development\source\JukeboxReloaded\forge\mcp\temp\bin\minecraft'
-libraryjars 'G:\development\source\JukeboxReloaded\forge\mcp\jars\bin\jinput.jar'
-libraryjars 'G:\development\source\JukeboxReloaded\forge\mcp\jars\bin\lwjgl.jar'
-libraryjars 'G:\development\source\JukeboxReloaded\forge\mcp\jars\bin\lwjgl_util.jar'
-libraryjars 'G:\development\source\JukeboxReloaded\forge\mcp\lib'
-libraryjars 'G:\development\source\JukeboxReloaded\minecraftforge.jar'

-dontskipnonpubliclibraryclassmembers
-target 1.6
-allowaccessmodification
-useuniqueclassmembernames
-dontusemixedcaseclassnames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
-dontwarn net.minecraft.entity.player.EntityPlayer
-dontwarn net.minecraft.client.audio.SoundPoolEntry


-keep class cazzar.mods.jukeboxreloaded.JukeboxReloaded {
    <fields>;
    <methods>;
}

-keep class cazzar.mods.jukeboxreloaded.proxy.ClientProxy {
    <fields>;
    <methods>;
}

-keepclassmembers class cazzar.mods.jukeboxreloaded.configuration.ConfigHelper {
    <fields>;
}

-keep class cazzar.mods.jukeboxreloaded.network.PacketHandler

-keep class cazzar.mods.jukeboxreloaded.blocks.* {
    <fields>;
    <methods>;
}

# Keep - Applications. Keep all application classes, along with their 'main'
# methods.
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Also keep - Database drivers. Keep all implementations of java.sql.Driver.
-keep class * extends java.sql.Driver

# Also keep - Swing UI L&F. Keep all extensions of javax.swing.plaf.ComponentUI,
# along with the special 'createUI' method.
-keep class * extends javax.swing.plaf.ComponentUI {
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}
