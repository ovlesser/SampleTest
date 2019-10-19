package com.ovlesser.sampletest.viewModel

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import com.ovlesser.sampletest.ActionStatus
import com.ovlesser.sampletest.MainActivity
import com.ovlesser.sampletest.model.User
import com.ovlesser.sampletest.network.ContentApiService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Handler

class UserListViewModel(val context: Context?) : ViewModel() {
    private var currentSearchObservable: Single<List<UserViewModel>> = Single.just(listOf())
    val viewModels: BehaviorSubject<List<UserViewModel>> = BehaviorSubject.create()
    val status: PublishSubject<ActionStatus> = PublishSubject.create()
    var repository = (context as? MainActivity)?.repository ?: ContentApiService.create()

    constructor(context: Context?, repository: ContentApiService) : this(context) {
        this.repository = repository
    }

    fun fetch(): Single<List<UserViewModel>>? {
        currentSearchObservable.unsubscribeOn(Schedulers.io())
        currentSearchObservable = repository.users()
            .map { it.map { UserViewModel(context, it) }}
            .doOnError {}
            .onErrorReturn { listOf() }

        val observable = currentSearchObservable.subscribeOn(Schedulers.io())
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