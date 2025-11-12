//
//  ContentView.swift
//  CryptoTrackerIOS
//
//  Created by Oguzhan Cetin on 10.11.2025.
//

import UIKit
import SwiftUI
import sharedKit
import FirebaseCore

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}




struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}
