package com.ovlesser.sampletest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.databinding.FragmentAlbumBinding
import com.ovlesser.sampletest.model.User
import com.ovlesser.sampletest.viewModel.AlbumViewModel
import com.ovlesser.sampletest.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_album.*

class AlbumFragment(val user: User) : Fragment() {
    private lateinit var viewModel: AlbumViewModel
    private lateinit var dataBinding: FragmentAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = activity?.run {
//            ViewModelProviders.of(this).get(UserListViewModel::class.java)
//        } ?: throw Exception("Invalid Activity")
        (activity as MainActivity).run {
            viewModel = AlbumViewModel(this, user)
            viewModel.fetch()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel
        album?.run {
            adapter = PhotoAdaptor(viewModel)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return dataBinding.root
    }
}