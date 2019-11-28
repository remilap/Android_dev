package eu.remilapointe.chatsample.ui.viewholder

import android.view.View
import android.widget.TextView
import com.remilapointe.laser.R
import eu.remilapointe.chatsample.ui.model.MessageItemUi

class MyMessageViewHolder(val view: View) : MessageViewHolder<MessageItemUi>(view) {
    private val messageContent = view.findViewById<TextView>(R.id.message)

    override fun bind(item: MessageItemUi) {
        messageContent.text = item.content
        messageContent.setTextColor(item.textColor)
    }
}
