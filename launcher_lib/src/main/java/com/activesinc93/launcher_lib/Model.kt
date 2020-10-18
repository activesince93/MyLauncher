package com.activesinc93.launcher_lib

import android.graphics.drawable.Drawable

/**
 * Created by Darshan Parikh on 18/10/20.
 */

data class MyApp(
    val name: String,
    val icon: Drawable,
    val packageName: String,
    val mainActivityClass: String,
    val versionCode: Long,
    val versionName: String,
)