package com.ovlesser.sampletest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.viewModel.UserListViewModel
import com.ovlesser.sampletest.databinding.FragmentUsersBinding
import kotlinx.android.synthetic.main.fragment_users.*

class UserFragment : Fragment() {
    private lateinit var viewModel: UserListViewModel
    private lateinit var dataBinding: FragmentUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = activity?.run {
//            ViewModelProviders.of(this).get(UserListViewModel::class.java)
//        } ?: throw Exception("Invalid Activity")
        (activity as MainActivity).run {
            viewModel = UserListViewModel(this)
            viewModel.fetch()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_list?.run {
            adapter = UserAdapter(viewModel)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)
        return dataBinding.root
    }
}