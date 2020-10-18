package com.activesinc93.launcher_lib

/**
 * Created by Darshan Parikh on 18/10/20.
 */
interface AppListFetchListener {
    fun onAppListReceived(appsList : ArrayList<MyApp>)
}