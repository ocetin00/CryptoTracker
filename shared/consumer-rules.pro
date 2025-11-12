# Consumer ProGuard rules for the shared module
# These rules will be applied to consumers of this module

# Keep all public APIs
-keep public class com.oguzhan.shared.** { public *; }

# Keep Kotlin metadata for reflection
-keepattributes *Annotation*

# Keep Kotlinx Serialization classes
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    <fields>;
}

# Keep data models
-keep class com.oguzhan.shared.core.data.remote.model.** { *; }
-keep class com.oguzhan.shared.core.domain.model.** { *; }

