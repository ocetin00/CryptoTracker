//
//  CryptoTrackerIOSApp.swift
//  CryptoTrackerIOS
//
//  Created by Oguzhan Cetin on 10.11.2025.
//

import SwiftUI
import FirebaseCore
import FirebaseAppCheck

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {

        FirebaseApp.configure()
        let providerFactory = AppCheckDebugProviderFactory()
        AppCheck.setAppCheckProviderFactory(providerFactory)
        
        //dt 568C502C-C5F9-4919-A836-8A004B1E726E
        

        
        
        return true
    }
}


@main
struct CryptoTrackerIOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
