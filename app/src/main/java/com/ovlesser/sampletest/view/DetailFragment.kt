package com.ovlesser.sampletest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.databinding.FragmentDetailBinding
import com.ovlesser.sampletest.model.Photo
import com.ovlesser.sampletest.viewModel.DetailViewModel
import com.ovlesser.sampletest.viewModel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment(val photo: Photo) : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).run {
            viewModel = DetailViewModel(this, photo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.image.setImageURI(viewModel.photo.url)
//        view.tv_title.text = viewModel.photo.title
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dataBinding : FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }
}