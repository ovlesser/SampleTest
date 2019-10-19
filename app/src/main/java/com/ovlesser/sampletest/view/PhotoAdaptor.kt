package com.ovlesser.sampletest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.ovlesser.sampletest.R
import com.ovlesser.sampletest.databinding.FragmentAlbumItemBinding
import com.ovlesser.sampletest.viewModel.AlbumViewModel
import com.ovlesser.sampletest.viewModel.PhotoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_album_item.view.*

class PhotoAdaptor(val viewModel: AlbumViewModel)
    : RecyclerView.Adapter<PhotoAdaptor.ViewHolder>() {
    private var viewModels: MutableList<PhotoViewModel> = arrayListOf()
    private val disposes = CompositeDisposable()

    init {
        viewModel.viewModels.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // clear the RecyclerView if the result of new query is coming
                if (it.isEmpty()) {
                    viewModels.clear()
                }
                viewModels.addAll(it)
                notifyDataSetChanged()
            }
            .addTo(disposes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding : FragmentAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_album_item,
            parent,
            false
        )
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = viewModels.get(position)
        photo.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = viewModels.size

    inner class ViewHolder(val dataBinding: FragmentAlbumItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(viewModel: PhotoViewModel) {
            dataBinding.viewModel = viewModel
            (dataBinding.root.image_thumbnail as SimpleDraweeView).setImageURI(viewModel.photo.thumbnailUrl)
        }
    }
}