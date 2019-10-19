package com.ovlesser.sampletest.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ovlesser.sampletest.ActionStatus
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.model.User
import com.ovlesser.sampletest.network.ContentApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class AlbumViewModel(val context: Context?, val user: User?) : ViewModel(){
    private var currentObservable: Single<List<PhotoViewModel>> = Single.just(listOf())
    val viewModels: BehaviorSubject<List<PhotoViewModel>> = BehaviorSubject.create()
    val status: PublishSubject<ActionStatus> = PublishSubject.create()
    var repository = (context as? MainActivity)?.repository ?: ContentApiService.create()

    constructor(context: Context?, user: User?, repository: ContentApiService) : this(context, user) {
        this.repository = repository
    }


    fun fetch(): Single<List<PhotoViewModel>>? {
        currentObservable.unsubscribeOn(Schedulers.io())
        currentObservable = repository.photos(user?.id)
            .map { it.map { PhotoViewModel(context, it) }}
            .doOnError {}
            .onErrorReturn { listOf() }

        val observable = currentObservable.subscribeOn(Schedulers.io())
        observable?.also {
            it.doOnSubscribe { status.onNext(ActionStatus.Start()) }
                .subscribe({
                    status.onNext(ActionStatus.Completed())
                    viewModels.onNext(it)
                }, {
                    it.printStackTrace()
                    status.onNext(ActionStatus.Failed(it))
                })
        }
        return observable
    }
}