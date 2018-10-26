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
import android.widget.ExpandableListView.OnGroupExpandListener
import com.redcodetechnologies.mlm.adapter.ExpandListAdapter
import com.redcodetechnologies.mlm.ui.*
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
                } else if (id == 4L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, NoficationListFragment()).commit()
                    return true
                } else   // for child parents
                    return false
            }
        })

        // Listview on child click listener
                expListView!!.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {

                override fun onChildClick(parent: ExpandableListView, v: View,
                          groupPosition: Int, childPosition: Int, id: Long): Boolean {
                        drawer_layout.closeDrawer(GravityCompat.START)

                    var args: Bundle = Bundle();

                    if (groupPosition == 1) {
                        var gt: NetworkFragment = NetworkFragment()
                        if (childPosition == 0) {
                        args.putString("Fragment", "MakeTable")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        } else if (childPosition == 1) {
                        args.putString("Fragment", "DownlineMembers")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        } else if (childPosition == 2) {
                        args.putString("Fragment", "ReferredMembers")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                         }
                         } else if (groupPosition == 2) {

                    var gt: GeneologyTableFragment = GeneologyTableFragment()
                        if (childPosition == 0) {
                        args.putString("Fragment", "MyPackageCommisionList")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "MyDirectCommisionList")
                        gt!!.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                     } else if (childPosition == 2) {
                        args.putString("Fragment", "MyTableCommisionList")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    }
                     } else if (groupPosition == 3) {
                          if (childPosition == 0 || childPosition == 1 || childPosition == 2 || childPosition == 3) {
                             var gt: WalletFragment = WalletFragment()
                            if (childPosition == 0) {
                            args.putString("Fragment", "E-Wallet Summary")
                            gt!!.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                              }else if (childPosition == 1) {
                            args.putString("Fragment", "Transactions")
                            gt!!.arguments = args

                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        } else if (childPosition == 2) {
                            args.putString("Fragment", "EWalletCredit")
                            gt!!.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        } else if (childPosition == 3) {
                            args.putString("Fragment", "EWalletDebits")
                            gt!!.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        }
                    } else {
                        if (childPosition == 0)
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, EWalletSummaryFragment()).commit()
                        else if (childPosition == 4)
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, WithdrawalFundFragment()).commit()
                        else if (childPosition == 5)
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, MyWithdrawalRequestFragment()).commit()
                    }
                } else if (groupPosition == 5) {
                    var gt: ReportFragment = ReportFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "ActivePayout")
                        gt!!.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "PayoutHistory")
                        gt!!.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "PayoutWithdrawalinProcess")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    }
                     } else if (groupPosition == 6  || groupPosition== 7) {
                      var gt: InboxFragment = InboxFragment()
                    var gta: SentFragment = SentFragment()

                    if (childPosition == 0 && groupPosition == 6) {
                        args.putString("Inbox", "Sponser")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    } else if (childPosition == 0 && groupPosition == 7) {
                        args.putString("Inbox", "IT")
                        gt!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                    } else if (childPosition == 1 && groupPosition == 6) {
                        args.putString("Sent", "Sponser")
                        gta!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gta!!).commit()
                    } else if (childPosition == 1 && groupPosition == 7) {
                        args.putString("Sent", "IT")
                        gta!!.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gta!!).commit()
                    }
                    } else if (groupPosition == 8) {
                        var gt: GetHelp = GetHelp()
                        if (childPosition == 1 ) {
                            args.putString("Fragment", "Get Help")
                            gt!!.arguments = args
                          supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt!!).commit()
                        }
                    }

                return true
            }
        })
    }


    private fun prepareListData(listDataHeader: MutableList<String>, listDataChild: MutableMap<String, List<String>>) {


        // Adding child data
        listDataHeader.add("Dashboard")
        listDataHeader.add("Network")
        listDataHeader.add("Genealogy Table")
        listDataHeader.add("E-Wallet")
        listDataHeader.add("Notication List")
        listDataHeader.add("Reports")
        listDataHeader.add("Sponsor Support")
        listDataHeader.add("IT Support")
        listDataHeader.add("General Help")

        // Adding child data


        val network = ArrayList<String>()
        network.add("Make Table")
        network.add("Down-line Members")
        network.add("Referred Members")

        val gtable = ArrayList<String>()
        gtable.add("My Package Commision List")
        gtable.add("My Direct Commision List")
        gtable.add("My Table Commision List")

        val ewallet = ArrayList<String>()
        ewallet.add("E-Wallet Summary")
        ewallet.add("Transactions")
        ewallet.add("E-Wallet Credits")
        ewallet.add("E-Wallet Debits")
        ewallet.add("Withdrawal Fund")

        ewallet.add("My Withdrawal Request")
        val reports = ArrayList<String>()
        reports.add("Active Payout")
        reports.add("Payout History")
        reports.add("Payout/Withdrawal in Process ")

        val support = ArrayList<String>()
        support.add("Inbox")
        support.add("Sent")


        val genralhelp = ArrayList<String>()
        genralhelp.add("My Help Request")
        genralhelp.add("Get Help")
        //   listDataChild[listDataHeader[0]] = null
        // Header, Child data
        listDataChild[listDataHeader[1]] = network
        listDataChild[listDataHeader[2]] = gtable
        listDataChild[listDataHeader[3]] = ewallet
        listDataChild[listDataHeader[5]] = reports
        listDataChild[listDataHeader[6]] = support
        listDataChild[listDataHeader[7]] = support
        listDataChild[listDataHeader[8]] = genralhelp
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_layout)
        fragment!!.onActivityResult(requestCode, resultCode, data)
    }
}
