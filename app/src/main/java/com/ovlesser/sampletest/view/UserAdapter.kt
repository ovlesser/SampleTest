package com.ovlesser.sampletest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.viewModel.UserListViewModel
import com.ovlesser.sampletest.viewModel.UserViewModel
import com.ovlesser.sampletest.databinding.FragmentUsersItemBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class UserAdapter(val viewModel: UserListViewModel)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var viewModels: MutableList<UserViewModel> = arrayListOf()
    private val disposes = CompositeDisposable()

    init {
        viewModel.viewModels.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // clear the RecyclerView if the result of new query is coming
                if (it.isEmpty()) {
                    viewModels.clear()
                }
                viewModels.addAll(it)
                Log.d("TAG", "viewModels.addAll(it)")
                notifyDataSetChanged()
            }
            .addTo(disposes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding : FragmentUsersItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_users_item, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModels.get(position))
    }

    override fun getItemCount(): Int = viewModels.size

    inner class ViewHolder(val dataBinding: FragmentUsersItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(viewModel: UserViewModel) {
            dataBinding.viewModel = viewModel
        }
    }
}