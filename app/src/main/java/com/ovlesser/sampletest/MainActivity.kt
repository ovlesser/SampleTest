package com.ovlesser.sampletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.ovlesser.sampletest.network.ContentApiService
import com.ovlesser.sampletest.view.UserFragment

class MainActivity : AppCompatActivity() {
    val repository = ContentApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, UserFragment())
            .addToBackStack("UserFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
