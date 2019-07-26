package com.desertfox.couplelink.banned

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.desertfox.couplelink.BaseRecyclerViewHolder
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.inflate
import com.desertfox.couplelink.util.throttleClicks
import kotlinx.android.synthetic.main.item_bannedterm.*

class BannedViewHolder private constructor(itemView: View,private val callback: BannedAdapter.ContentCallback) : BaseRecyclerViewHolder(itemView) {
    constructor(parent: ViewGroup, callback: BannedAdapter.ContentCallback) : this(parent.inflate(R.layout.item_bannedterm, false), callback)

    override fun onDataBind(data: Any?) {
        if (data !is BannedAdapter.Item) return
        data.model?.apply {
            item_bannedterm_count.text = this.count.toString()
            item_bannedterm_txt.text = this.name
            item_bannedterm_trash_btn.isVisible = data.viewType == BannedAdapter.ViewType.MY
            item_bannedterm_trash_btn.throttleClicks().subscribe {
                callback.requestDeleteBanned(id)
            }.bind()
        }
    }
}