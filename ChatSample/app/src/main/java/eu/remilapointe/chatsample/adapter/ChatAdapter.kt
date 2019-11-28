package com.remilapointe.laser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.chatsample.R
import eu.remilapointe.chatsample.ui.model.MessageItemUi
import eu.remilapointe.chatsample.ui.model.MessageItemUi.Companion.TYPE_FRIEND_MESASGE
import eu.remilapointe.chatsample.ui.model.MessageItemUi.Companion.TYPE_MY_MESSAGE
import eu.remilapointe.chatsample.ui.viewholder.FriendMessageViewHolder
import eu.remilapointe.chatsample.ui.viewholder.MessageViewHolder
import eu.remilapointe.chatsample.ui.viewholder.MyMessageViewHolder

class ChatAdapter(var data: MutableList<MessageItemUi>) : RecyclerView.Adapter<MessageViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MY_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false)
                MyMessageViewHolder(view)
            }
            TYPE_FRIEND_MESASGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_message_item, parent, false)
                FriendMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val item = data[position]
        Log.d("adapter View", position.toString() + item.content)
        when (holder) {
            is MyMessageViewHolder -> holder.bind(item)
            is FriendMessageViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].messageType

}
