package com.oguzhan.shared

actual fun getPlatform(): Platform {
   return object :Platform {
        override val name: String = "IOS.SDK_INT}"
    }
}