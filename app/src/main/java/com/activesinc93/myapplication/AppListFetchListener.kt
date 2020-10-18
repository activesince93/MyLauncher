package com.activesinc93.myapplication

/**
 * Created by Darshan Parikh on 18/10/20.
 */
interface AppListFetchListener {
    fun onAppListReceived(appsList : ArrayList<MyApp>)
}