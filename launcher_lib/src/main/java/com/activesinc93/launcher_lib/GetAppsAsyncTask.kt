package com.activesinc93.launcher_lib

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.core.content.pm.PackageInfoCompat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Darshan Parikh on 18/10/20.
 */

class GetAppsAsyncTask(
    private val context: Context,
    private val listener: AppListFetchListener,
) : AsyncTask<Void, Void, ArrayList<MyApp>>() {

    override fun doInBackground(vararg params: Void?): ArrayList<MyApp> {
        val appsList = ArrayList<MyApp>()
        val packages = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        packages.forEach {
            if (it == null) return@forEach
            val packageName = it.packageName

            if (packageName.isNullOrEmpty()) return@forEach
            val packageManager = context.packageManager ?: return@forEach

            val appName = packageManager.getApplicationLabel(it).toString()
            val icon = packageManager.getApplicationIcon(packageName)

            val packageInfo = packageManager.getPackageInfo(packageName, 0) ?: return@forEach
            val versionName = packageInfo.versionName
            val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)

            val launchActivityIntent = packageManager.getLaunchIntentForPackage(packageName) ?: return@forEach
            val component = launchActivityIntent.component ?: return@forEach

            val launchActivity = component.className

            if ((it.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0)
                or !(it.flags and ApplicationInfo.FLAG_SYSTEM != 0)) {
                val myApp = MyApp(
                    appName,
                    icon,
                    packageName,
                    launchActivity,
                    versionCode,
                    versionName
                )
                appsList.add(myApp)
            }
        }

        Collections.sort(
            appsList,
            object : Comparator<MyApp> {
                override fun compare(o1: MyApp?, o2: MyApp?): Int {
                    if (o1 == null || o2 == null) return 0
                    return o1.name.compareTo(o2.name)
                }
            }
        )
        return appsList
    }

    override fun onPostExecute(installedApps: ArrayList<MyApp>) {
        super.onPostExecute(installedApps)
        listener.onAppListReceived(installedApps)
    }
}

