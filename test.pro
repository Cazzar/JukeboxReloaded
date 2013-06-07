-injars build\1.0.6a\JukeboxReloaded-universal-1.5.2-1.0.6a-Forge-7.8.0.686.jar
-outjars build\1.0.6a\JukeboxReloaded-universal-1.5.2-1.0.6a-Forge-7.8.0.686.obf.jar

-libraryjars 'C:\Program Files\Java\jre7\lib\rt.jar'
-libraryjars forge\mcp\temp\bin\minecraft
-libraryjars forge\mcp\jars\bin\jinput.jar
-libraryjars forge\mcp\jars\bin\lwjgl.jar
-libraryjars forge\mcp\jars\bin\lwjgl_util.jar
-libraryjars forge\mcp\lib
-libraryjars minecraftforge.jar

-allowaccessmodification
-useuniqueclassmembernames
-dontusemixedcaseclassnames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
-dontnote net.minecraft.entity.player.EntityPlayer
-dontwarn net.minecraft.entity.player.EntityPlayer


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
