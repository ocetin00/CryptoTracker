<!-- README.md -->

<h1 align="center">Crypto Tracker - Android App</h1>
<p align="center"> <strong>Cryptocurrency Tracking App</strong> built with Jetpack Compose, WorkManager, Hilt, Room, and Firebase Firestore.</p>

 ![Demo GIF](https://github.com/ocetin00/temp/raw/main/Screen_recording_20250205_023848.gif)

---

## Features ✅ 

 <strong>Real-time Cryptocurrency Prices</strong> via API<br>
 <strong>Favorites List</strong> stored in <strong>Firebase Firestore</strong><br>
 <strong>Offline Data Storage</strong> using <strong>Room Database</strong><br>
 <strong>Search Functionality</strong> for quick access to coins<br>
 <strong>WorkManager Integration</strong> for <strong>background price updates</strong><br>
 <strong>Hilt Dependency Injection</strong> for modular architecture(for now just package)<br>
 <strong>State Management with Jetpack Compose</strong><br>
 <strong>Shimmer Loading Animation</strong> for better UX<br>
 <strong>Authentication Flow</strong> (Login, Logout)<br>

---

## 🛠️ Tech Stack & Libraries

<table>
  <tr>
    <th>Feature</th>
    <th>Library</th>
  </tr>
  <tr>
    <td>UI Framework</td>
    <td><a href="https://developer.android.com/jetpack/compose">Jetpack Compose</a></td>
  </tr>
  <tr>
    <td>Background Work</td>
    <td><a href="https://developer.android.com/topic/libraries/architecture/workmanager">WorkManager</a></td>
  </tr>
  <tr>
    <td>Dependency Injection</td>
    <td><a href="https://dagger.dev/hilt/">Hilt</a></td>
  </tr>
  <tr>
    <td>Local Database</td>
    <td><a href="https://developer.android.com/jetpack/androidx/releases/room">Room</a></td>
  </tr>
  <tr>
    <td>Networking</td>
    <td><a href="https://square.github.io/retrofit/">Retrofit</a> + <a href="https://square.github.io/okhttp/">OkHttp</a></td>
  </tr>
  <tr>
    <td>Asynchronous</td>
    <td><a href="https://developer.android.com/kotlin/coroutines">Coroutines</a> + <a href="https://developer.android.com/kotlin/flow">Flow</a></td>
  </tr>
  <tr>
    <td>Firebase</td>
    <td><a href="https://firebase.google.com/docs/firestore">Firestore</a> + <a href="https://firebase.google.com/docs/auth">Auth</a></td>
  </tr>
  <tr>
    <td>Image Loading</td>
    <td><a href="https://coil-kt.github.io/coil/">Coil</a></td>
  </tr>
  <tr>
    <td>Testing</td>
    <td><a href="https://junit.org/">JUnit</a> + <a href="https://mockk.io/">MockK</a></td>
  </tr>
</table>

---

## 🏗️ Project Architecture

The project follows **MVVM (Model-View-ViewModel) architecture** with **Unidirectional Data Flow**:

```bash
app/
│── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── CoinDao.kt
│   │   ├── CoinEntity.kt
│   ├── remote/
│   │   ├── CoinApi.kt
│   │   ├── CoinRemoteModel.kt
│   ├── repository/
│   │   ├── CoinRepositoryImpl.kt
│
│── domain/
│   ├── repository/
│   ├── model/
│   ├── usecase/
│
│── ui/
│   ├── screen/
│   │   ├── auth/
│   │   ├── coin/
│
│── di/
│   ├── AppModule.kt
│   ├── NetworkModule.kt
│   ├── RepositoryModule.kt
```

---

## ⚙️ Setup & Installation

Clone this repository:
```bash
git clone https://github.com/yourusername/CryptoTracker.git
cd CryptoTracker
```

### 🏗 Prerequisites
- **Android Studio LadyBug or newer**
- **Minimum SDK 28 (Android 9.0)**
- **Gradle version: 8.10.2, AGP 8.8.0**

### 🔧 Running the Project

2️⃣ **Build & Run**
```bash
./gradlew clean build
```
or directly run from **Android Studio**.  

---

##  Things I Planned But Couldn't Implement 

- **Notifications** for price alerts 
- **String Res for hardcode strings**
- **More UI/Unit test**
- **Modularization**

