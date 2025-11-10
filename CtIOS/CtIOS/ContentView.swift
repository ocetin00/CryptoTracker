//
//  ContentView.swift
//  CtIOS
//
//  Created by Oguzhan Cetin on 10.11.2025.
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
