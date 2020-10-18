package com.activesinc93.launcher_lib

import android.content.Context

/**
 * Created by Darshan Parikh on 18/10/20.
 */

class AppListProvider {

    private var context: Context
    private var listFetchListener: AppListFetchListener

    private constructor(context: Context, listFetchListener: AppListFetchListener) {
        this.context = context
        this.listFetchListener = listFetchListener
    }

    fun getAppList() {
        GetAppsAsyncTask(context, listFetchListener).execute()
    }

    companion object {
        private var provider: AppListProvider? = null

        fun getInstance(context: Context, listFetchListener: AppListFetchListener): AppListProvider {
            if(provider == null) {
                provider = AppListProvider(context, listFetchListener)
            }
            return provider!!
        }
    }
}