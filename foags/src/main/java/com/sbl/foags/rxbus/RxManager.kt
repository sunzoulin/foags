package com.sbl.foags.rxbus

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by baixiaokang on 16/4/28.
 */
class RxManager {

    private val mRxBus = RxBus.getInstance()
    private val mObservables = HashMap<String, Observable<*>>()// 管理观察源
    private val mCompositeSubscription = CompositeDisposable()// 管理订阅者者

    /**
     * 订阅事件
     *
     * @param eventName
     * @param action1
     */
    fun <T> on(eventName: String, action1: Consumer<T>) {
        val mObservable = mRxBus.register<T>(eventName)
        mObservables[eventName] = mObservable
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, Consumer { e -> e.printStackTrace() }))
    }

    fun add(m: Disposable) {
        mCompositeSubscription.add(m)
    }

    fun clear() {
        if (mCompositeSubscription.size() == 0) {
            return
        }
        mCompositeSubscription.dispose()// 取消订阅
        for ((key, value) in mObservables)
            mRxBus.unregister(key, value)// 移除观察
    }

    fun post(tag: Any, content: Any) {
        mRxBus.post(tag, content)
    }
}
