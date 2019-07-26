package com.desertfox.couplelink.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.desertfox.couplelink.R
import com.desertfox.couplelink.chatting.MsgType
import com.desertfox.couplelink.data.UserData
import com.desertfox.couplelink.model.responses.ChatModel
import com.desertfox.couplelink.model.responses.MsgModel
import com.desertfox.couplelink.util.StringFormatUtil
import kotlinx.android.synthetic.main.item_chat_date.view.*
import kotlinx.android.synthetic.main.item_chat_mine.view.*
import kotlinx.android.synthetic.main.item_chat_yours.view.*


class ChatRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder<MsgModel>>() {
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
        private val imgPath = UserData.myMemberModel.profileImageUrl

        override fun bind(item: MsgModel) {
            tvMsg.text = item.message
            tvTime.text = StringFormatUtil.getTimeString(item.createdAt)
            ivProfile.visibility = View.VISIBLE
            if (imgPath != "")
                Glide.with(context).load(imgPath).apply(RequestOptions.circleCropTransform()).into(ivProfile)
        }
    }

    inner class YoursChatItemViewHolder(view: View) : ViewHolder<MsgModel>(view) {
        private val tvMsg by lazy { itemView.tv_chat_yours }
        private val tvTime by lazy { itemView.tv_chat_yours_time }
        private val ivProfile by lazy { itemView.iv_profile_yours }
        private val imgPath = UserData.partnerMemberModel.profileImageUrl

        override fun bind(item: MsgModel) {
            if (item.bannedIndexRange.isEmpty()) {
                tvMsg.text = item.message
            } else {
                tvMsg.text = item.message.let { content ->
                    val spannableString = SpannableString(content)
                    item.bannedIndexRange.forEach {
                        if (it != null) changeBannedWord(spannableString, it.startIndex, it.endIndex)
                    }
                    spannableString
                }
            }
            tvTime.text = StringFormatUtil.getTimeString(item.createdAt)
            ivProfile.visibility = View.VISIBLE
            if (imgPath != "")
                Glide.with(context).load(imgPath).apply(RequestOptions.circleCropTransform()).into(ivProfile)
        }
    }

    private fun changeBannedWord(spannableString: SpannableString, start: Int, end: Int) {
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}