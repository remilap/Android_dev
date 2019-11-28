package eu.remilapointe.chatsample.ui.model

class MessageItemUi(val content: String, val textColor: Int, val messageType: Int) {
    companion object {
        const val TYPE_MY_MESSAGE = 0
        const val TYPE_FRIEND_MESASGE = 1
    }
}
