package com.redcodetechnologies.mlm

import android.content.Intent
import android.os.Bundle

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.widget.ExpandableListView
import android.support.v4.widget.DrawerLayout
import android.widget.ExpandableListView.OnGroupExpandListener
import com.redcodetechnologies.mlm.adapter.ExpandListAdapter
import com.redcodetechnologies.mlm.ui.DashBoardFragment
import com.redcodetechnologies.mlm.ui.MakeTableFragment
import com.redcodetechnologies.mlm.ui.ReportFragment


class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listDataHeader: ArrayList<String>? = null
    var listDataChild: HashMap<String, List<String>>? = null
    var expListView: ExpandableListView? = null
    var nav_view: NavigationView? = null
    var lastExpandedPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        nav_view = findViewById(R.id.nav_view) as NavigationView
        nav_view!!.setNavigationItemSelectedListener(this)
        enableExpandableList();
        getSupportActionBar()!!.setTitle("Dashboard")
        supportFragmentManager.beginTransaction().add(R.id.main_layout, ReportFragment()).commit()

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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

        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_logout ->{
                startActivity(Intent(this@DrawerActivity,SignInActivity::class.java))
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
    //    drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun enableExpandableList() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()
        expListView = findViewById(R.id.left_drawer) as ExpandableListView

        prepareListData(listDataHeader!!, listDataChild!!)
        val listAdapter = ExpandListAdapter(this, listDataHeader, listDataChild)
        // setting list_group adapter
        expListView!!.setAdapter(listAdapter)


        expListView!!.setOnGroupExpandListener(OnGroupExpandListener { groupPosition ->

            if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                expListView!!.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = groupPosition
        })

        expListView!!.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener {

            override fun onGroupClick(parent: ExpandableListView, v: View,
                                      groupPosition: Int, id: Long): Boolean {

                if (id == 0L) {
                    // for non-child parents
                    drawer_layout.closeDrawer(GravityCompat.START)
                 supportFragmentManager.beginTransaction().replace(R.id.main_layout, DashBoardFragment()).commit()

                    return true
                } else if(id == 4L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, MakeTableFragment()).commit()
                    return true
                }
                 else   // for child parents
                    return false


            }
        })

        // Listview on child click listener
        expListView!!.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {

            override fun onChildClick(parent: ExpandableListView, v: View,
                                      groupPosition: Int, childPosition: Int, id: Long): Boolean {
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
        })
    }


    private fun prepareListData(listDataHeader: MutableList<String>, listDataChild: MutableMap<String, List<String>>) {


        // Adding child data
        listDataHeader.add("Dashboard")
        listDataHeader.add("Network")
        listDataHeader.add("E-Wallet")
        listDataHeader.add("Payments")
        listDataHeader.add("Settings")
        listDataHeader.add("Reports")
        listDataHeader.add("Sponsor")
        listDataHeader.add("IT Support")

        // Adding child data
        val dashboard = ArrayList<String>()


        val network = ArrayList<String>()
        network.add("All Members")
        network.add("Add New Member")
        network.add("Users Tree")
        network.add("Users Downliners")
        network.add("Sign-Up Users List")

        val ewallet = ArrayList<String>()
        ewallet.add("E-Wallet Summary")
        ewallet.add("Transactions")
        ewallet.add("E-Wallet Debits")
        ewallet.add("E-Wallet Credits")


        //   listDataChild[listDataHeader[0]] = null
        listDataChild[listDataHeader[0]] = dashboard // Header, Child data
        listDataChild[listDataHeader[1]] = network
        listDataChild[listDataHeader[2]] = ewallet


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_layout)
        fragment!!.onActivityResult(requestCode, resultCode, data)
    }
}
