package com.desertfox.couplelink.banned

import android.view.View
import android.view.ViewGroup
import com.desertfox.couplelink.BaseRecyclerViewHolder
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.inflate

class BannedAddViewHolder private constructor(itemView: View) : BaseRecyclerViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_bannedterm_hide, false))

    override fun onDataBind(data: Any?) {

    }
}