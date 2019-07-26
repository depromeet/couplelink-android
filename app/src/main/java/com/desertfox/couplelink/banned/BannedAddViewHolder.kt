package com.desertfox.couplelink.banned

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.desertfox.couplelink.BaseRecyclerViewHolder
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.inflate
import com.desertfox.couplelink.util.throttleClicks
import com.desertfox.couplelink.util.toast
import kotlinx.android.synthetic.main.item_bannedterm_add.*


class BannedAddViewHolder private constructor(itemView: View, private val callback: BannedAdapter.ContentCallback) :
    BaseRecyclerViewHolder(itemView) {
    constructor(
        parent: ViewGroup,
        callback: BannedAdapter.ContentCallback
    ) : this(parent.inflate(R.layout.item_bannedterm_add, false), callback)

    override fun onDataBind(data: Any?) {
        if (data !is BannedAdapter.Item) return
        item_bannedterm_add_plus.throttleClicks().subscribe {
            item_bannedterm_add_group.isVisible = true
            item_bannedterm_add_plus.isVisible = false
        }.bind()

        item_bannedterm_add_btn.throttleClicks().subscribe {
            val word = item_bannedterm_add_edit.text.toString()
            if (word.isEmpty()) {
                itemView.context.toast("")
                return@subscribe
            }
            callback.requestAddBanned(item_bannedterm_add_edit.text.toString())
            item_bannedterm_add_group.isVisible = false
            item_bannedterm_add_plus.isVisible = true
            item_bannedterm_add_edit.setText("")
            val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(item_bannedterm_add_edit.windowToken, 0)

        }.bind()
    }

}