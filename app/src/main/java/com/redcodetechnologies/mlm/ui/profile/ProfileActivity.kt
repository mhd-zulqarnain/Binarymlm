package com.redcodetechnologies.mlm.ui.profile
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.profile.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileActivity : AppCompatActivity() {
    var adapter :ViewPagerAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val strip = findViewById<PagerTabStrip>(R.id.pager_header)
        strip.setPadding(-400, 0, 0, 0)
        adapter = ViewPagerAdapter(this@ProfileActivity.supportFragmentManager)
        viewPager.adapter = adapter
        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
