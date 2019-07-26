package com.desertfox.couplelink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desertfox.couplelink.R
import com.desertfox.couplelink.chatting.MsgType
import com.desertfox.couplelink.model.responses.ChatModel
import com.desertfox.couplelink.model.responses.MsgModel
import com.desertfox.couplelink.util.StringFormatUtil
import kotlinx.android.synthetic.main.item_chat_date.view.*
import kotlinx.android.synthetic.main.item_chat_mine.view.*
import kotlinx.android.synthetic.main.item_chat_yours.view.*

class ChatRecyclerViewAdapter
    : RecyclerView.Adapter<ViewHolder<MsgModel>>() {
    private var items = mutableListOf<ChatModel>()
    private val mine = 0
    private val yours = 1
    private val date = 2

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder<MsgModel> = when (type) {
        mine -> {
            val viewGroup = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_mine, parent, false)
            MineChatItemViewHolder(viewGroup)
        }
        yours -> {
            val viewGroup = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_yours, parent, false)
            YoursChatItemViewHolder(viewGroup)
        }
        else -> {
            val viewGroup = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_date, parent, false)
            DateChatItemViewHolder(viewGroup)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (items[position].msgType) {
        MsgType.MINE -> mine
        MsgType.YOURS -> yours
        MsgType.DATE -> date
    }

    override fun onBindViewHolder(holder: ViewHolder<MsgModel>, position: Int) {
        holder.bind(items[position].msgModel)
    }

    fun addItem(msg: ChatModel) {
        items.add(msg)
        notifyItemChanged(items.size - 1)
    }

    fun setList(msg: List<ChatModel>) {
        items.clear()
        items.addAll(msg)
        notifyDataSetChanged()
    }

    inner class DateChatItemViewHolder(view: View) : ViewHolder<MsgModel>(view) {
        private val tvDate by lazy { itemView.tv_chat_date }

        override fun bind(item: MsgModel) {
            tvDate.text = StringFormatUtil.getDateString(item.createdAt)
        }
    }

    inner class MineChatItemViewHolder(view: View) : ViewHolder<MsgModel>(view) {
        private val tvMsg by lazy { itemView.tv_chat_mine }
        private val tvTime by lazy { itemView.tv_chat_mine_time }
        private val ivProfile by lazy { itemView.iv_profile_mine }

        override fun bind(item: MsgModel) {
            tvMsg.text = item.message
            tvTime.text = StringFormatUtil.getTimeString(
                StringFormatUtil.parseLocalDateTime(item.createdAt)
            )
            ivProfile.visibility = View.VISIBLE
        }
    }

    inner class YoursChatItemViewHolder(view: View) : ViewHolder<MsgModel>(view) {
        private val tvMsg by lazy { itemView.tv_chat_yours }
        private val tvTime by lazy { itemView.tv_chat_yours_time }
        private val ivProfile by lazy { itemView.iv_profile_yours }

        override fun bind(item: MsgModel) {
            tvMsg.text = item.message
            tvTime.text = StringFormatUtil.getTimeString(item.createdAt)
            ivProfile.visibility = View.VISIBLE
        }
    }
}