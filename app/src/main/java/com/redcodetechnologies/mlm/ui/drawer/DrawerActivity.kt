package com.redcodetechnologies.mlm.ui.drawer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.ExpandableListView.OnGroupExpandListener
import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Messages
import com.redcodetechnologies.mlm.models.MyNotification
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.adapter.ExpandListAdapter
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.dashboard.DashBoardFragment
import com.redcodetechnologies.mlm.ui.dashboard.SleepingDashboardFragment
import com.redcodetechnologies.mlm.ui.geologytable.GeneologyTableFragment
import com.redcodetechnologies.mlm.ui.network.NetworkFragment
import com.redcodetechnologies.mlm.ui.network.DirectMemberFragment
import com.redcodetechnologies.mlm.ui.network.downliners.DownlinerStatusFragment
import com.redcodetechnologies.mlm.ui.network.UpgradePackageFragment
import com.redcodetechnologies.mlm.ui.notification.NoficationListFragment
import com.redcodetechnologies.mlm.ui.support.InboxFragment
import com.redcodetechnologies.mlm.ui.support.SentFragment
import com.redcodetechnologies.mlm.ui.profile.ProfileActivity
import com.redcodetechnologies.mlm.ui.support.ReportFragment
import com.redcodetechnologies.mlm.ui.videos.tutorials.VideosListFragment
import com.redcodetechnologies.mlm.ui.videos.vediopack.VideoCategoryFragment
import com.redcodetechnologies.mlm.ui.wallet.EWalletSummaryFragment
import com.redcodetechnologies.mlm.ui.wallet.TransactionFragment
import com.redcodetechnologies.mlm.ui.wallet.WithdrawalFundFragment
import com.redcodetechnologies.mlm.ui.wallet.MyWithdrawalRequestFragment
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listDataHeader: ArrayList<String>? = null
    var listDataChild: HashMap<String, List<String>>? = null
    var expListView: ExpandableListView? = null
    var expListViewright: ExpandableListView? = null
    var nav_view: NavigationView? = null
    var lastExpandedPosition = -1
    var category: String = "Sales"
    var type: String? = null
    var notification: String = ""
    var message: String = ""
    var mPref: SharedPrefs? = null
    lateinit var headerView: View

    val PRFILE_UPDATE_REQ: Int = 44

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPref = SharedPrefs.getInstance()
        setSupportActionBar(toolbar)
        initView()

    }

    fun initView() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        nav_view = findViewById(R.id.nav_view) as NavigationView
        nav_view!!.setNavigationItemSelectedListener(this)

        if (intent.getStringExtra("type") != null) {
            message = intent.getStringExtra("notification")
            showMessageNotificationDialog();

        } else if (intent.getStringExtra("Category") != null) {
            category = intent.getStringExtra("Category");
            category = intent.getStringExtra("Category");
            if (intent.getStringExtra("notification") != null) {
                notification = intent.getStringExtra("notification")
                showNotificationDialog();
            }
        }
        headerView = nav_view!!.getHeaderView(0)

        if (category == "Sales") {
            enableExpandableList()
            supportFragmentManager.beginTransaction().add(R.id.main_layout, DashBoardFragment()).commit()
        } else if (category == "Sleeping") {
            enableExpandableListSleeping()
            supportFragmentManager.beginTransaction().add(R.id.main_layout, SleepingDashboardFragment()).commit()
        }
        getSupportActionBar()!!.setTitle("Dashboard")
        makeView()
        getUserObject()
        askPermission(Manifest.permission.CAMERA, 1)
        askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4)

        /*
        swipeRefreshLayout.setOnRefreshListener {
            object : SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {

                }
            }
        }*/
    }

    fun makeView() {

        val tv_status = headerView.findViewById<TextView>(R.id.tv_status)

        val obj = mPref!!.getUser(this@DrawerActivity);
        if (obj.username != null)
            headerView.findViewById<TextView>(R.id.tv_username).setText(obj.username);
        if (obj.email != null){
            headerView.findViewById<TextView>(R.id.tv_email).setText(obj.email);

        }
        if (obj.userDesignation != null)
            headerView.findViewById<TextView>(R.id.tv_designation).setText(obj.userDesignation.toString());
        if (obj.phone != null)
            headerView.findViewById<TextView>(R.id.tv_package_type).setText(obj.phone.toString());
        if (obj.profileImage != null) {
            val img = obj.profileImage
            if (img != "" && img != "null")
                headerView.findViewById<CircleImageView>(R.id.profile_image_citizen).setImageBitmap(stringtoImage(img.toString()))
        }

        if (obj.isVerify!!) {
            val img = this@DrawerActivity.getResources().getDrawable(R.drawable.ic_verified_users)
            tv_status.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null)
            tv_status.setText("Verified")
        } else {
            val img = this@DrawerActivity.getResources().getDrawable(R.drawable.ic_pending)
            tv_status.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null)

            if (!obj.isUserActive!! && !obj.isNewRequest!!) {
                showWarningDialog()
                tv_status.setText("Rejected")
            } else if (obj.isUserActive!!) {
                tv_status.setText("Not Verified ")
            } else {
                //showWarningDialog()
                tv_status.setText("Pending")
            }
        }
    }

    private fun showNotificationDialog() {

        val view: View = LayoutInflater.from(this@DrawerActivity).inflate(R.layout.dialog_notification, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(this@DrawerActivity)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        val tvtitle: TextView = view.findViewById(R.id.tv_title)
        val tvdescription: TextView = view.findViewById(R.id.tv_des)
        var obj = Gson().fromJson<MyNotification>(notification, MyNotification::class.java)
        tvtitle.setText(obj.NotificationName)
        tvdescription.setText(obj.NotificationDescription)
        val btn_dismiss: Button = view.findViewById(R.id.btn_dismiss)
        val btn_save: Button = view.findViewById(R.id.btn_save)

        btn_save.setOnClickListener {
            //save.
            saveNotification(obj, object : ServiceListener<String> {
                override fun success(obj: String) {
                    Apputils.showMsg(this@DrawerActivity, "Notication saved")
                    dialog.dismiss()
                }

                override fun fail(error: ServiceError) {
                    dialog.dismiss()
                }
            })
        }
        btn_dismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showMessageNotificationDialog() {

        val view: View = LayoutInflater.from(this@DrawerActivity).inflate(R.layout.dialog_notification_msg, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(this@DrawerActivity)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        val tvtitle: TextView = view.findViewById(R.id.tv_title)
        val tvdescription: TextView = view.findViewById(R.id.tv_des)

        val obj = Gson().fromJson<Messages>(message, Messages::class.java)

        tvtitle.setText(obj.Sender_Name)
        tvdescription.setText(obj.Message)
        val btn_dismiss: Button = view.findViewById(R.id.btn_dismiss)

        btn_dismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun showWarningDialog() {
        val view: View = LayoutInflater.from(this@DrawerActivity).inflate(R.layout.dialog_warning, null)
        val alertBox = AlertDialog.Builder(this@DrawerActivity)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        val obj = mPref!!.getUser(this@DrawerActivity);

        val btn_ok: Button = view.findViewById(R.id.btn_ok)
        val tv_warn: TextView = view.findViewById(R.id.tv_warn)
        if (!obj.isUserActive!! && !obj.isNewRequest!!)
            tv_warn.setText("Your account has been rejected!!")

        btn_ok.setOnClickListener {
            dialog.dismiss()
            mPref!!.clearToken(this@DrawerActivity)
            mPref!!.clearUser(this@DrawerActivity)
            startActivity(Intent(this@DrawerActivity, SignInActivity::class.java))
            finish()
        }
        dialog.show()

    }

    //<editor-fold desc="Menu control">
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_logout -> {

                mPref!!.clearToken(this@DrawerActivity)
                mPref!!.clearUser(this@DrawerActivity)
                startActivity(Intent(this@DrawerActivity, SignInActivity::class.java))
                finish()
                return true
            }
            R.id.action_myprofile -> {

                var intent = Intent(this@DrawerActivity, ProfileActivity::class.java)
                startActivityForResult(intent, PRFILE_UPDATE_REQ)

                return true
            }
            R.id.action_password -> {

                showChangePasswordDialog()

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
    //</editor-fold>

    //<editor-fold desc="Expendable list">
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


                //swipeRefreshLayout.setRefreshing(false);
                if (id == 0L) {
                    // for non-child parents
                    drawer_layout.closeDrawer(GravityCompat.START)
                    getUserObject()
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, DashBoardFragment()).commit()


                    return true
                } else if (id == 4L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, NoficationListFragment()).commit()
                    return true
                } else if (id == 8L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, VideoCategoryFragment()).commit()
                    return true
                } else if (id == 9L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, VideosListFragment()).commit()
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
                //swipeRefreshLayout.setRefreshing(false);
                var args: Bundle = Bundle();

                if (groupPosition == 1) {
                    val gt: NetworkFragment = NetworkFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "MakeTable")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "DownlineMembers")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "ReferredMembers") //Direct Members
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, DirectMemberFragment()).commit()
                    } else if (childPosition == 3) {
                        args.putString("Fragment", "Paid-unPaid Downliners")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, DownlinerStatusFragment()).commit()
                    }
                } else if (groupPosition == 2) {

                    val gt: GeneologyTableFragment = GeneologyTableFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "MyPackageCommisionList")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "MyDirectCommisionList")
                        gt.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "MyTableCommisionList")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    }
                } else if (groupPosition == 3) {
                    val gt: TransactionFragment = TransactionFragment()
                    when (childPosition) {
                        0 -> {
                            args.putString("Fragment", "E-Wallet Summary")
                            gt.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, EWalletSummaryFragment()).commit()
                        }
                        1 -> {
                            args.putString("Fragment", "wallet_transactions")
                            gt.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                        }
                        2 -> {
                            args.putString("Fragment", "wallet_credits")
                            gt.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                        }
                        3 -> {
                            args.putString("Fragment", "wallet_debits")
                            gt.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                        }
                        4 -> {
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, WithdrawalFundFragment()).commit()
                        }
                        5 -> {
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, MyWithdrawalRequestFragment()).commit()
                        }
                        6->{
                            args.putString("Fragment", "upgrade ")
                            gt.arguments = args
                            supportFragmentManager.beginTransaction().replace(R.id.main_layout, UpgradePackageFragment()).commit()
                        }
                    }
                } else if (groupPosition == 5) {
                    val gt: ReportFragment = ReportFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "ActivePayout")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "PayoutHistory")
                        gt.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "PayoutWithdrawalinProcess")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    }

                } else if (groupPosition == 6 || groupPosition == 7) {
                    val gt: InboxFragment = InboxFragment()
                    val gta: SentFragment = SentFragment()

                    if (childPosition == 0 && groupPosition == 6) {
                        args.putString("Fragment", "Sponser_Inbox")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 0 && groupPosition == 7) {
                        args.putString("Fragment", "IT_Inbox")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1 && groupPosition == 6) {
                        args.putString("Fragment", "Sponser_Sent")
                        gta.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gta).commit()
                    } else if (childPosition == 1 && groupPosition == 7) {
                        args.putString("Fragment", "IT_Sent")
                        gta.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gta).commit()
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
        listDataHeader.add("Commision Table")
        listDataHeader.add("E-Wallet")
        listDataHeader.add("Notication List")
        listDataHeader.add("Reports")
        listDataHeader.add("Sponsor Support")
        listDataHeader.add("IT Support")
        listDataHeader.add("Video Packs")
        listDataHeader.add("Training Videos")

        // Adding child data

        val network = ArrayList<String>()
        network.add("Add new Member")
        network.add("Down-line Members")
        network.add("Direct Members")
        network.add("Paid-unPaid Downliners")


        val gtable = ArrayList<String>()
        gtable.add("Package Commision List")
        gtable.add("Sale Commission List")
        gtable.add("Table Commision List")

        val ewallet = ArrayList<String>()
        ewallet.add("E-Wallet Summary")
        ewallet.add("Transactions")
        ewallet.add("E-Wallet Credits")
        ewallet.add("E-Wallet Debits")
        ewallet.add("Withdrawal Fund")
        ewallet.add("My Withdrawal Request")
        ewallet.add("Upgrage Package")

        val reports = ArrayList<String>()
        reports.add("Active Payout")
        reports.add("Payout History")
        reports.add("Payout/Withdrawal in Process ")

        val support = ArrayList<String>()
        support.add("Inbox")
        support.add("Sent")


        //   listDataChild[listDataHeader[0]] = null
        // Header, Child data
        listDataChild[listDataHeader[1]] = network
        listDataChild[listDataHeader[2]] = gtable
        listDataChild[listDataHeader[3]] = ewallet
        listDataChild[listDataHeader[5]] = reports
        listDataChild[listDataHeader[6]] = support
        listDataChild[listDataHeader[7]] = support

    }

    private fun enableExpandableListSleeping() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()
        expListView = findViewById(R.id.left_drawer) as ExpandableListView
        expListViewright = findViewById(R.id.right_drawer) as ExpandableListView
        expListViewright!!.visibility = View.VISIBLE
        prepareListDataSleeping(listDataHeader!!, listDataChild!!)

        val listAdapter = ExpandListAdapter(this, listDataHeader, listDataChild)
        // setting list_group adapter
        expListViewright!!.setAdapter(listAdapter)


        expListViewright!!.setOnGroupExpandListener(OnGroupExpandListener { groupPosition ->

            if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                expListViewright!!.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = groupPosition
        })

        expListViewright!!.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener {

            override fun onGroupClick(parent: ExpandableListView, v: View,
                                      groupPosition: Int, id: Long): Boolean {

                if (id == 0L) {
                    // for non-child parents
                    drawer_layout.closeDrawer(GravityCompat.START)
                    getUserObject()

                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, SleepingDashboardFragment()).commit()

                    return true
                } else if (id == 3L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, NoficationListFragment()).commit()
                    return true
                } else if (id == 6L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, VideoCategoryFragment()).commit()
                    return true
                } else if (id == 7L) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, VideosListFragment()).commit()
                    return true
                } else   // for child parents
                    return false
            }
        })

        // Listview on child click listener
        expListViewright!!.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {

            override fun onChildClick(parent: ExpandableListView, v: View,
                                      groupPosition: Int, childPosition: Int, id: Long): Boolean {
                drawer_layout.closeDrawer(GravityCompat.START)

                var args: Bundle = Bundle();

                if (groupPosition == 1) {
                    val gt: GeneologyTableFragment = GeneologyTableFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "MyPackageCommisionList")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    }
                } else if (groupPosition == 2) {
                    val gt = TransactionFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "E-Wallet Summary")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, EWalletSummaryFragment()).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "wallet_transactions")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "wallet_credits")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 3) {
                        args.putString("Fragment", "wallet_debits")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 4) {
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, WithdrawalFundFragment()).commit()
                    } else if (childPosition == 5) {
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, MyWithdrawalRequestFragment()).commit()
                        //supportFragmentManager.beginTransaction().replace(R.id.main_layout, WithdrawalFundFragment()).commit()
                    }
                } else if (groupPosition == 4) {
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, WithdrawalFundFragment()).commit()
                    val gt: ReportFragment = ReportFragment()
                    if (childPosition == 0) {
                        args.putString("Fragment", "ActivePayout")
                        gt.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1) {
                        args.putString("Fragment", "PayoutHistory")
                        gt.arguments = args

                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 2) {
                        args.putString("Fragment", "PayoutWithdrawalinProcess")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    }
                } else if (groupPosition == 5) {

                    val gt = InboxFragment()
                    val gta = SentFragment()

                    if (childPosition == 0) {
                        args.putString("Inbox", "IT")
                        gt.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gt).commit()
                    } else if (childPosition == 1) {
                        args.putString("Sent", "IT")
                        gta.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.main_layout, gta).commit()
                    }
                }

                return true
            }
        })
    }

    private fun prepareListDataSleeping(listDataHeader: MutableList<String>, listDataChild: MutableMap<String, List<String>>) {
        // Adding child data
        listDataHeader.add("Dashboard")
        listDataHeader.add("Commision Table")
        listDataHeader.add("E-Wallet")
        listDataHeader.add("Notication List")
        listDataHeader.add("Reports")
        listDataHeader.add("IT Support")
        listDataHeader.add("Video Packs")
        listDataHeader.add("Training Videos")


        // Adding child data
        val gtable = ArrayList<String>()
        gtable.add("Package Commision List")
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

        //   listDataChild[listDataHeader[0]] = null
        // Header, Child data

        listDataChild[listDataHeader[1]] = gtable
        listDataChild[listDataHeader[2]] = ewallet
        listDataChild[listDataHeader[4]] = reports
        listDataChild[listDataHeader[5]] = support

    }
    //</editor-fold>

    //<editor-fold desc="password change">
    private fun showChangePasswordDialog() {
        val view: View = LayoutInflater.from(this@DrawerActivity).inflate(R.layout.dilalog_new_pass, null)
        val alertBox = AlertDialog.Builder(this@DrawerActivity)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        var mPassword = mPref!!.getUser(this@DrawerActivity).password
        val uid = mPref!!.getUser(this@DrawerActivity).userId

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        val ed_old_pass: EditText = view.findViewById(R.id.ed_old_pass)
        val ed_new_pass: EditText = view.findViewById(R.id.ed_new_pass)
        val ed_confirm_pass: EditText = view.findViewById(R.id.ed_confirm_pass)
        val btn_verify: Button = view.findViewById(R.id.btn_verify)
        btn_verify.setOnClickListener {
            if (ed_old_pass.text.toString() == mPassword && ed_old_pass.text.toString().trim() != "") {
                if (ed_new_pass.text.toString().trim() != "" && ed_confirm_pass.text.toString().trim() != "") {
                    if (ed_new_pass.text.toString().trim() == ed_confirm_pass.text.toString().trim()) {
                        if (ed_new_pass.text.toString().trim { it <= ' ' }.length < 8) {
                            Apputils.showMsg(this@DrawerActivity, "Password should be greater than 8")
                        } else {
                            mPassword = ed_confirm_pass.text.toString()

                            ApiClint.getInstance()?.getService()?.updatePassword(mPassword!!, uid!!)
                                    ?.enqueue(object : Callback<Response> {
                                        override fun onFailure(call: Call<Response>?, t: Throwable?) {
                                            println("error")
                                        }

                                        override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                                            print("object success ")
                                            val code: Int = response!!.code()
                                            if (code == 200) {
                                                Apputils.showMsg(this@DrawerActivity, "Password  updated")

                                            } else {
                                                Apputils.showMsg(this@DrawerActivity, "Failed")

                                            }

                                        }
                                    })

                            dialog.dismiss()

                        }
                    } else {
                        Apputils.showMsg(this@DrawerActivity, "Password not matched")

                    }

                } else {
                    Apputils.showMsg(this@DrawerActivity, "Fill all fields")
                }

            } else {
                Apputils.showMsg(this@DrawerActivity, "Old Password is wrong")
            }
        }
        dialog.show()
    }
    //</editor-fold>

    fun saveNotification(obj: MyNotification, service: ServiceListener<String>) {

        if (!Apputils.isNetworkAvailable(this@DrawerActivity)) {
            service.success("network error")
            return
        }
        val id = SharedPrefs.getInstance()!!.getUser(this@DrawerActivity).userId
        ApiClint.getInstance()?.getService()?.saveNotification(id!!, obj)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        service.success("failed")
                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        val msg = response!!.message()
                        service.success(msg)
                    }
                })

    }

    fun stringtoImage(encodedString: String): Bitmap? {
        try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size);
            return bitmap;

        } catch (e: Exception) {
            return null
        }
    }

    fun askPermission(permission: String, requestcode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestcode)
        }
    }

    private fun getUserObject() {
        val token = SharedPrefs.getInstance()!!.getToken(this@DrawerActivity).accessToken
        val username = SharedPrefs.getInstance()!!.getUser(this@DrawerActivity).username
        if (!Apputils.isNetworkAvailable(this@DrawerActivity)) {
            Toast.makeText(this@DrawerActivity, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClint.getInstance()?.getService()?.getNewRegistoredUser("bearer " + token!!, username!!)
                ?.enqueue(object : Callback<NewUserRegistration> {
                    override fun onFailure(call: Call<NewUserRegistration>?, t: Throwable?) {
                        println("error")
                     //   swipeRefreshLayout.setRefreshing(false);

                    }

                    override fun onResponse(call: Call<NewUserRegistration>?, response: retrofit2.Response<NewUserRegistration>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            print("success")
                            try {
                                var obj: NewUserRegistration = response.body()!!
                                SharedPrefs.getInstance()!!.setUser(this@DrawerActivity, obj)
                                makeView()
                            } catch (e: Exception) {
                                tokenExpire()
                            }

                        } else {
                            print("error")
                        }
                       // swipeRefreshLayout.setRefreshing(false);
                    }
                })
    }

    fun tokenExpire() {
        Apputils.showMsg(this@DrawerActivity, "Session Expired")
        mPref!!.clearToken(this@DrawerActivity)
        mPref!!.clearUser(this@DrawerActivity)
        startActivity(Intent(this@DrawerActivity, SignInActivity::class.java))
        this@DrawerActivity.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_layout)
        fragment!!.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PRFILE_UPDATE_REQ && resultCode == Activity.RESULT_OK) {
            makeView()
        }
    }

    override fun onBackPressed() {
        val fragmentCurrent = supportFragmentManager.findFragmentById(R.id.main_layout)

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            if (category == "Sales") {
                if (fragmentCurrent !is DashBoardFragment) {
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, DashBoardFragment()).addToBackStack(null).commit();
                } else {
                    finish()
                }
            } else if (category == "Sleeping") {
                if (fragmentCurrent !is SleepingDashboardFragment) {
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout, SleepingDashboardFragment()).addToBackStack(null).commit();
                } else {
                    finish()
                }
            }


        }
    }



}
