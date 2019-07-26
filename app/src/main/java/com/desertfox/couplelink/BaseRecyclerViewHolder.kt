package com.desertfox.couplelink

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.extensions.LayoutContainer

abstract class BaseRecyclerViewHolder(
        final override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val disposables = CompositeDisposable()

    fun recycler() {
        disposables.clear()
    }

    fun bindData(data: Any?) {
        onDataBind(data)
    }

    protected open fun onDataBind(data: Any?) = Unit

    protected fun Disposable.bind() = apply {
        disposables.add(this)
    }
}