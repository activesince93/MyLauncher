package com.activesinc93.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.activesinc93.launcher_lib.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AppListFetchListener, AppClickListener,
    View.OnClickListener {

    private lateinit var context: Context
    private lateinit var appListProvider: AppListProvider
    private var appsList = ArrayList<MyApp>()
    private var filteredAppList = ArrayList<MyApp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        appListProvider = AppListProvider.getInstance(context, this)

        initViews()
        getAppsList()
    }

    private fun getAppsList() {
        appListProvider.getAppList()
    }

    private fun initViews() {
        btnSetDefaultLauncher.setOnClickListener(this)
        rvAppsList.layoutManager = GridLayoutManager(context, 4)

        if(!LauncherUtils.isMyLauncherDefault(context)) {
            btnSetDefaultLauncher.visibility = View.VISIBLE
        } else {
            btnSetDefaultLauncher.visibility = View.GONE
        }

        etAppSearch.addTextChangedListener {
            filteredAppList.clear()
            if(it == null) return@addTextChangedListener

            val query = it.toString().trim()
            if(query.isEmpty()) {
                filteredAppList.addAll(appsList)
            } else {
                appsList.forEach { app ->
                    if(app.name.toLowerCase().startsWith(query.toLowerCase())) {
                        filteredAppList.add(app)
                    }
                }
            }

            setRecyclerViewAdapter(filteredAppList)
        }
    }

    override fun onAppListReceived(appsList: ArrayList<MyApp>) {
        this.appsList = appsList

        this.filteredAppList.clear()
        this.filteredAppList.addAll(appsList)

        setRecyclerViewAdapter(appsList)
    }

    private fun setRecyclerViewAdapter(appsList: ArrayList<MyApp>) {
        val adapter = AppListAdapter(context, appsList, this)
        rvAppsList.adapter = adapter
    }

    override fun onAppClicked(app: MyApp) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.component = ComponentName(app.packageName, app.mainActivityClass)
        startActivity(intent)
    }

    override fun onBackPressed() {}
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnSetDefaultLauncher -> {
                if(!LauncherUtils.isMyLauncherDefault(context)) {
                    LauncherUtils.resetPreferredLauncherAndOpenChooser(context, this)
                } else {
                    btnSetDefaultLauncher.visibility = View.GONE
                }
            }
        }
    }
}