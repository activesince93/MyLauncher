package com.activesinc93.launcher_lib

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 * Created by Darshan Parikh on 18/10/20.
 */

object LauncherUtils {

    private fun getDefaultLauncher(context: Context): String? {
        val packageManager = context.packageManager
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.HOME")

        val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) ?: return null
        return resolveInfo.activityInfo.packageName
    }

    fun isMyLauncherDefault(context: Context): Boolean {
        return getDefaultLauncher(context).equals(context.packageName)
    }

    fun resetPreferredLauncherAndOpenChooser(context: Context, activity: Activity) {
        val packageManager = context.packageManager
        val componentName = ComponentName(context, activity.javaClass)
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        val selector = Intent(Intent.ACTION_MAIN)
        selector.addCategory(Intent.CATEGORY_HOME)
        context.startActivity(selector)

        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.DONT_KILL_APP
        )
    }
}