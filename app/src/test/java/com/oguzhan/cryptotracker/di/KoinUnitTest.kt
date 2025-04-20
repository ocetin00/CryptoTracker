package com.oguzhan.cryptotracker.di

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.oguzhan.cryptotracker.data.remote.FirebaseAuthApi
import com.oguzhan.cryptotracker.data.remote.FirebaseFireStoreApi
import io.mockk.mockk
import org.junit.Test
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {


    @Test
    fun checkAllModules() {
        val testModule = module {
            single<FirebaseApp> {
                mockk(relaxed = true)
            }
            singleOf(::FirebaseAuth) {
                mockk(relaxed = true)
            }
            singleOf(::FirebaseAuthApi) {
                mockk(relaxed = true)
            }
            singleOf(::FirebaseFireStoreApi) {
                mockk(relaxed = true)
            }
        }
        checkModules {
            modules(appModule, testModule) // testModule ile Firebase'i override ediyoruz
        }
    }
}