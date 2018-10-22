package com.redcodetechnologies.mlm

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
import com.redcodetechnologies.mlm.ui.InboxFragment
import com.redcodetechnologies.mlm.ui.MakeTableFragment


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
        supportFragmentManager.beginTransaction().add(R.id.main_layout, DashBoardFragment()).commit()

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

            if (lastExpandedPosition !== -1 && groupPosition != lastExpandedPosition) {
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
        listDataHeader.add("Home")
        listDataHeader.add("Faqeer Parbirham")
        listDataHeader.add("Pithoro")
        listDataHeader.add("It Support")
        listDataHeader.add("Product5")

        // Adding child data
        val top = ArrayList<String>()
        top.add("Follower1")
        top.add("Follower2")
        top.add("Follower3")


        val mid = ArrayList<String>()
        mid.add("y1")
        mid.add("y2")
        mid.add("y3")

        val bottom = ArrayList<String>()
        bottom.add("Inbox")
        bottom.add("Sent Box")


        //   listDataChild[listDataHeader[0]] = null
        listDataChild[listDataHeader[1]] = top // Header, Child data
        listDataChild[listDataHeader[2]] = mid
        listDataChild[listDataHeader[3]] = bottom


    }

}
