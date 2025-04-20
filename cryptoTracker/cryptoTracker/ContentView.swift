//
//  ContentView.swift
//  cryptoTracker
//
//  Created by Oguzhan Cetin on 20.04.2025.
//

import SwiftUI
import sharedKit

struct ContentView: View {
    var body: some View {
        Text(Greeting().greet())
            .padding()
    }
}

#Preview {
    ContentView()
}
