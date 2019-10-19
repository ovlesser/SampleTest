package com.ovlesser.sampletest.viewModel

import android.content.Context
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.model.Photo
import com.ovlesser.sampletest.view.DetailFragment

class PhotoViewModel(val context: Context?, val photo: Photo) : ViewModel() {
    fun equal(photoViewModel: PhotoViewModel) : Boolean{
        return this.photo == photoViewModel.photo
    }

    fun onClick(viewModel: PhotoViewModel) {
        (context as MainActivity).run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, DetailFragment(viewModel.photo))
                .addToBackStack("DetailFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}