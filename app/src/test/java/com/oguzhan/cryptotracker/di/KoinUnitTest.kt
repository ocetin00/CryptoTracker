package com.oguzhan.cryptotracker.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.oguzhan.shared.core.data.firebase.FirebaseAuthApi
import com.oguzhan.shared.core.data.firebase.FirebaseFireStoreApi
import io.mockk.mockk
import org.junit.Test
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

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