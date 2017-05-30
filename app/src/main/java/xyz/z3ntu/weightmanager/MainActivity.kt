package xyz.z3ntu.weightmanager

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import xyz.z3ntu.weightmanager.rest.WeightData
import xyz.z3ntu.weightmanager.rest.WeightDataSend
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DataFragment.OnListFragmentInteractionListener {

//    var urlbase = "http://10.91.52.89:8090"
    var urlbase = "http://192.168.0.12:8090"

    companion object {
        @JvmStatic lateinit var ITEMS: MutableList<DateWeightEntry>
    }

    init {
        ITEMS = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            val newFragment = EntryDialog()
            newFragment.show(fragmentManager, "entrydialog")
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        GetHttpRequestTask().execute()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onListFragmentInteraction(item: DateWeightEntry) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private inner class GetHttpRequestTask : AsyncTask<Void, Void, Array<WeightData>>() {
        override fun doInBackground(vararg params: Void): Array<WeightData> {
            println("WE ARE NOW DOING SOMETHING.")
            try {
                val url = "$urlbase/weightData/"
                val restTemplate = RestTemplate()
                restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
                val weightData = restTemplate.getForObject(url, Array<WeightData>::class.java)
                return weightData
            } catch (e: Exception) {
                Log.e("MainActivity", e.message, e)
            }

            return emptyArray()
        }

        override fun onPostExecute(weightDatas: Array<WeightData>) {
            println("HELLOOOOOO")
            println(weightDatas)
            weightDatas.forEach {
                println(it)
                DataFragment.adapter.addItem(DateWeightEntry(it.date, it.weight))
                DataFragment.adapter.notifyDataSetChanged()
            }
        }

    }

    inner class CreateHttpRequestTask : AsyncTask<WeightDataSend, Void, Boolean>() {
        override fun doInBackground(vararg p0: WeightDataSend): Boolean {
            if(p0.size != 1) {
                println("CreateHttpRequestTask: WRONG NUMBER OF ARGUMENTS!")
                return false
            }
            try {
                val url = "$urlbase/weightData/"
                val restTemplate = RestTemplate()
                restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
                val success = restTemplate.postForObject(url, p0.first(), WeightData::class.java)
                println(success)
                DataFragment.adapter.addItem(DateWeightEntry(success.date, success.weight))
                DataFragment.adapter.notifyDataSetChanged()
                return true
            } catch (e: Exception) {
                Log.e("MainActivity", e.message, e)
            }
            return false
        }

        override fun onPostExecute(result: Boolean) {
            println("POST SUCCESS: $result")
        }
    }

/*    private inner class GetBackgroundTask : AsyncTask<URL, Int, Array<WeightData>>() {
        override fun doInBackground(vararg urls: URL): Array<WeightData>? {
            // initiate http request
            return null
        }

        override fun onPostExecute(weightData: Array<WeightData>) {
            // Is called with the return value of doInBackground
            super.onPostExecute(weightData)
        }
    }*/
}
