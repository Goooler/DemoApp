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

# Retrofit
# This is to keep parameters on retrofit2.http-annotated methods while still allowing removal of unused ones
-keep,allowobfuscation @interface retrofit2.http.**
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.** <methods>;
}

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

# From OkHttp but gated appropriately
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# ZoneRulesProvider _does_ exist!
-dontwarn java.time.zone.ZoneRulesProvider

# Arouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# DataBinding
-keep public class * extends androidx.databinding.ViewDataBinding {*;}
