package eu.remilapointe.chatsample.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.remilapointe.laser.adapter.ChatAdapter
import com.remilapointe.laser.ui.viewmodel.ChatViewModel

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel

    private var chatAdapter = ChatAdapter(mutableListOf())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        chatAdapter.data = viewModel.messages
        subscribeOnAddMessage()
        */
    }

    /*
    private fun subscribeOnAddMessage() {
        viewModel.notifyNewMessageInsertedLiveData.observe(this, Observer {
            chatAdapter.notifyItemInserted(it)
        })
    }

    override fun initView() {
        bot_conversation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                .apply {
                    stackFromEnd = true
                    isSmoothScrollbarEnabled = true
                }
            adaapter = chatAdapter
        }
        input_layout.onSendClicked = {
            viewModel.sendMessage(it)
            hideKeyboard(activity)
        }
    }
    */

}
