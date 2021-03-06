package com.desertfox.couplelink

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

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
