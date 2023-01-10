# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-allowaccessmodification
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
  native <methods>;
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# Assume isInEditMode() always return false in release builds so they can be pruned
-assumevalues public class * extends android.view.View {
  boolean isInEditMode() return false;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

-keepattributes Signature,InnerClasses,EnclosingMethod,*Annotation*

# Okio
-dontwarn okio.**

# Kotlin
-dontwarn kotlin.**

# Some unsafe classfactory stuff
-keep class sun.misc.Unsafe { *; }

# Ensure the custom, fast service loader implementation is removed. R8 will fold these for us
-assumenosideeffects class kotlinx.coroutines.internal.MainDispatcherLoader {
  boolean FAST_SERVICE_LOADER_ENABLED return false;
}
-assumenosideeffects class kotlinx.coroutines.internal.FastServiceLoader {
  boolean ANDROID_DETECTED return true;
}
-checkdiscard class kotlinx.coroutines.internal.FastServiceLoader

# Check that qualifier annotations have been discarded.
-checkdiscard @javax.inject.Qualifier class *

# Coroutines debug agent bits
-dontwarn java.lang.instrument.ClassFileTransformer
-dontwarn sun.misc.SignalHandler

# ZoneRulesProvider _does_ exist!
-dontwarn java.time.zone.ZoneRulesProvider

# DataBinding
-keep,allowoptimization public class * extends androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
}

# Extra rules for R8 fullMode
-keep,allowobfuscation,allowshrinking class io.goooler.demoapp.common.base.binding.BaseBindingActivity
-keep,allowobfuscation,allowshrinking class * extends io.goooler.demoapp.common.base.binding.BaseBindingActivity
# TODO: Waiting for new retrofit release to remove these rules
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
