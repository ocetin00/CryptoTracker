# ProGuard Configuration

## Overview
ProGuard/R8 configuration has been added to the project to optimize and obfuscate the release builds.

## Files Added

### 1. `shared/proguard-rules.pro`
Main ProGuard rules file containing:
- **Kotlin & Coroutines**: Keeps coroutines and Kotlin metadata
- **Kotlinx Serialization**: Preserves serialization annotations and serializers
- **Data Models**: Keeps all data and domain model classes
- **Firebase**: Keeps Firebase and Google Play Services classes
- **Ktor**: HTTP client configuration
- **Koin**: Dependency injection framework rules
- **WorkManager**: Keeps Worker classes including PriceUpdateWorker
- **ViewModels**: Preserves ViewModel classes
- **Room Database**: Database entity and DAO preservation
- **Coil**: Image loading library rules
- **Navigation Compose**: Navigation components
- **Logging**: Removes debug/verbose/info logs in release builds

### 2. `shared/consumer-rules.pro`
Consumer ProGuard rules for library usage:
- Keeps public APIs
- Preserves Kotlin metadata
- Keeps serializable classes and data models

## Build Configuration

### Release Build
```kotlin
buildTypes {
    getByName("release") {
        isMinifyEnabled = true  // Enables code shrinking and obfuscation
        isShrinkResources = false  // Set to true to remove unused resources
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### Debug Build
- Minification is disabled for faster builds and easier debugging

## Important Classes Preserved

1. **Worker Classes**: `PriceUpdateWorker` and all Worker subclasses
2. **ViewModels**: All ViewModel subclasses
3. **Data Models**: All classes in `com.oguzhan.shared.core.data.**`
4. **Domain Models**: All classes in `com.oguzhan.shared.core.domain.**`
5. **Room Database**: Entity and DAO classes
6. **Serializable Classes**: All classes annotated with `@Serializable`

## Benefits

- **Smaller APK**: Removes unused code and resources
- **Obfuscation**: Makes reverse engineering more difficult
- **Optimization**: Improves app performance
- **Security**: Removes debug logging in production

## Testing

After enabling ProGuard:
1. Build a release APK: `./gradlew assembleRelease`
2. Test all features thoroughly
3. Check for any crashes related to missing classes
4. Review the generated `mapping.txt` file in `build/outputs/mapping/release/`

## Troubleshooting

If you encounter issues:
1. Check crash logs for ClassNotFoundException or MethodNotFoundException
2. Add specific keep rules in `proguard-rules.pro`
3. Use `-keep class your.package.ClassName { *; }` to preserve specific classes
4. Test with `isMinifyEnabled = false` to isolate ProGuard issues

## Mapping File

The `mapping.txt` file is generated during release builds and maps obfuscated names to original names. Keep this file to:
- Deobfuscate stack traces from production crashes
- Upload to Firebase Crashlytics or Play Console for automatic deobfuscation

Location: `shared/build/outputs/mapping/release/mapping.txt`

