package com.desertfox.couplelink

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    /**
     * onDestroy 때 자동 dispose
     * 항상 subscribe 뒤에 호출
     */
    protected fun Disposable.bind() = apply {
        disposables.add(this)
    }

}
