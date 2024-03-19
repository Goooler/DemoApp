# This is a configuration file for ProGuard.
# https://www.guardsquare.com/manual/configuration/usage
-allowaccessmodification
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes Signature,InnerClasses,EnclosingMethod,*Annotation*
-verbose

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

# remove all logging from production apk
-assumenosideeffects class android.util.Log {
    public static *** getStackTraceString(...);
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
}

# Assume isInEditMode() always return false in release builds so they can be pruned
-assumevalues public class * extends android.view.View {
  boolean isInEditMode() return false;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

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

# remove some kotlin overhead
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkNotNull(java.lang.Object);
    static void checkNotNull(java.lang.Object, java.lang.String);
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void throwUninitializedPropertyAccessException(java.lang.String);
}

# Check that qualifier annotations have been discarded.
-checkdiscard @javax.inject.Qualifier class *

# ViewBinding
-keep,allowoptimization public class * extends androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
}
# Extra rules for R8 fullMode
-keep,allowobfuscation,allowshrinking class io.goooler.demoapp.common.base.binding.BaseBindingActivity
-keep,allowobfuscation,allowshrinking class * extends io.goooler.demoapp.common.base.binding.BaseBindingActivity
