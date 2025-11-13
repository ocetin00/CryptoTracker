# Platform Module Comparison

## Overview

Bu dokümanda Android ve iOS için platform-specific Koin modüllerinin karşılaştırması ve kullanımı açıklanmaktadır.

## Android Platform Module

### 📍 Konum
`shared/src/androidMain/kotlin/com/oguzhan/shared/di/PlatformModule.android.kt`

### 📦 İçerik
```kotlin
fun androidPlatformModule(context: Context): Module = module {
    single<Context> { context }
    viewModelOf(::MainViewModel)
    workerOf(::PriceUpdateWorker)
}
```

### ✨ Özellikler

1. **Context**
   - Android Context'i dependency olarak sağlar
   - WorkManager, SharedPreferences vb. için gerekli

2. **MainViewModel**
   - Splash screen ve authentication state management
   - `by viewModel()` ile MainActivity'de kullanılır

3. **PriceUpdateWorker**
   - Background price updates
   - 15 dakikada bir otomatik güncelleme
   - WorkManager integration

### 🚀 Kullanım

```kotlin
// CTApp.kt - Global initialization
startKoin {
    androidContext(this@CTApp)
    androidLogger()
    workManagerFactory()
    modules(
        androidPlatformModule(this@CTApp),
        appModule
    )
}
```

### 📱 Dependencies

- ✅ `Context` - Android system context
- ✅ `MainViewModel` - Activity-level state
- ✅ `PriceUpdateWorker` - Background tasks

---

## iOS Platform Module

### 📍 Konum
`shared/src/iosMain/kotlin/com/oguzhan/shared/di/PlatformModule.ios.kt`

### 📦 İçerik
```kotlin
actual fun platformModule(): Module = module {
    // Platform-specific iOS dependencies
    // Note: iOS doesn't need Context like Android
    // Background tasks handled differently via BGTaskScheduler
}
```

### ✨ Özellikler

1. **No Context Needed**
   - iOS doesn't have Android-like Context
   - Platform services accessed differently

2. **No MainViewModel**
   - iOS directly uses `MainViewController()`
   - State managed in composables

3. **Background Tasks**
   - Requires native Swift implementation
   - Uses `BGTaskScheduler` API
   - See `IOSBackgroundTaskHelper.kt`

### 🚀 Kullanım

```kotlin
// App.ios.kt - Per-screen initialization
@Composable
actual fun PlatformApp(startDestination: Any) {
    KoinApplication(
        application = {
            modules(
                platformModule(),
                appModule
            )
        },
        content = {
            val navController = rememberNavController()
            CtNavHost(navController, startDestination)
        }
    )
}
```

### 📱 Dependencies

- ✅ Currently empty (iOS-specific dependencies can be added as needed)
- ⚠️ Background tasks require native Swift code

---

## Platform Comparison Table

| Feature | Android | iOS |
|---------|---------|-----|
| **Context** | ✅ Required | ❌ Not applicable |
| **ViewModel Injection** | ✅ Global (`by viewModel()`) | ✅ Per-screen (`koinViewModel()`) |
| **Background Tasks** | ✅ WorkManager (Kotlin) | ⚠️ BGTaskScheduler (Swift) |
| **Koin Initialization** | Global (Application class) | Per-screen (KoinApplication) |
| **Module Function** | `androidPlatformModule(context)` | `platformModule()` |
| **Platform Services** | Via Context | Direct platform APIs |

---

## Common Module (Both Platforms)

### 📍 Konum
`shared/src/commonMain/kotlin/com/oguzhan/shared/di/AppModuleKoin.kt`

### 📦 Shared Dependencies

```kotlin
val appModule = module {
    // HTTP Client (Ktor)
    single { HttpClient { ... } }
    
    // Repositories
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::CoinRepositoryImpl) { bind<CoinRepository>() }
    
    // APIs
    single<CoinApi> { CoinKtorApi(get()) }
    single { FirebaseAuthApi(Firebase.auth) }
    single { FirebaseFireStoreApi(Firebase.firestore, get()) }
    
    // ViewModels (Common)
    viewModelOf(::AuthViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::CryptoListScreenViewModel)
    viewModelOf(::CryptoListDetailViewModel)
    
    // Use Cases
    singleOf(::GetCoinByIdUseCase)
    singleOf(::GetCoinListUseCases)
    singleOf(::GetFavoriteCoinListUseCase)
    singleOf(::SearchCoinListUseCases)
    singleOf(::SetFavoriteCoinUseCase)
    
    // Database
    single<AppDatabase> { getDatabaseBuilder() }
    single<CoinDao> { get<AppDatabase>().coinDao() }
}
```

---

## Best Practices

### ✅ Do

1. **Keep platform-specific code in platform modules**
   ```kotlin
   // Android only
   fun androidPlatformModule() = module {
       workerOf(::PriceUpdateWorker) // Android-specific
   }
   ```

2. **Use common module for shared logic**
   ```kotlin
   // Common for both platforms
   val appModule = module {
       viewModelOf(::AuthViewModel) // Works on both
   }
   ```

3. **Initialize Koin appropriately for each platform**
   - Android: Global in Application class
   - iOS: Per-screen in KoinApplication composable

### ❌ Don't

1. **Don't put platform-specific code in common module**
   ```kotlin
   // ❌ Wrong - Worker is Android-only
   val appModule = module {
       workerOf(::PriceUpdateWorker)
   }
   ```

2. **Don't use KoinApplication on Android if Koin is already started globally**
   ```kotlin
   // ❌ Wrong - Koin already started in CTApp
   @Composable
   fun AndroidApp() {
       KoinApplication { ... } // Creates duplicate context
   }
   ```

3. **Don't expect identical platform behavior**
   - Android WorkManager ≠ iOS BGTaskScheduler
   - Android Context ≠ iOS has no equivalent
   - Plan for platform differences

---

## Adding New Platform Dependencies

### Android Example

```kotlin
fun androidPlatformModule(context: Context): Module = module {
    single<Context> { context }
    
    // Add new Android-specific dependency
    single<SharedPreferences> {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    
    // Add new Worker
    workerOf(::MyNewWorker)
}
```

### iOS Example

```kotlin
actual fun platformModule(): Module = module {
    // Add iOS-specific dependency
    single<NSUserDefaults> { 
        NSUserDefaults.standardUserDefaults 
    }
}
```

---

## Summary

- **Android**: Rich platform module with Context, ViewModels, and Workers
- **iOS**: Minimal platform module, native code for background tasks
- **Common**: All business logic, repositories, and shared ViewModels
- **Strategy**: Keep platform code separate, maximize shared code

## Related Documentation

- [KOIN_SETUP.md](./KOIN_SETUP.md) - Detailed Koin configuration
- [IOS_BACKGROUND_TASKS.md](./IOS_BACKGROUND_TASKS.md) - iOS background task setup
- [PROGUARD_SETUP.md](./PROGUARD_SETUP.md) - Android ProGuard configuration

