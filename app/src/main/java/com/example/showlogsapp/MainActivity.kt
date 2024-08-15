package com.example.showlogsapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.showlogsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!Requester.isInternetConnect(this)) {
            val builder = AlertDialog.Builder(this)
                .setMessage(R.string.noInternetText).setCancelable(false).create().show()
        }

        setFragment(SetCountLogs.newInstance(), binding.frameLayout.id)
        setOnNavigationViewItemSelected()
    }

    fun openMenu(view: View) {
        binding.main.openDrawer(GravityCompat.START)
    }

    fun setFragment(fragment: Fragment, layout: Int) {
        supportFragmentManager.beginTransaction().replace(layout, fragment).commit()
    }

    private fun setOnNavigationViewItemSelected() {
        binding.navigationView.setNavigationItemSelectedListener { it ->
            when(it.itemId) {
                R.id.logsFragment -> setFragment(LogsList.newInstance(), binding.frameLayout.id)
                R.id.setLogsCountFragment -> setFragment(SetCountLogs.newInstance(), binding.frameLayout.id)
            }
            binding.main.closeDrawer(GravityCompat.START)
            true
        }
    }

}