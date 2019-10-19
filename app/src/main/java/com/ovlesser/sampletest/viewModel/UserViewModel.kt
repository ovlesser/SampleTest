package com.ovlesser.sampletest.viewModel

import android.content.Context
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.model.User
import com.ovlesser.sampletest.view.AlbumFragment

class UserViewModel(val context: Context?, val user: User) : ViewModel() {
    fun equal(userViewModel: UserViewModel) : Boolean{
        return this.user == userViewModel.user
    }
    fun onClick(viewModel: UserViewModel) {
        (context as MainActivity).run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, AlbumFragment(viewModel.user))
                .addToBackStack("AlbumFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}