fun main() {
}

data class Message(
    var id: Int = 0, // id сообщения
    val idChat: Int = 0, // название собеседника
    var isDeleted: Boolean = false, //  удаленность сообщения
    var text: String = "0",//  само сообщение
    var readMessage: Boolean = false // прочитано сообщение?
)

data class Chat(
    var id: Int = 0, // id сообщения
    val title: String = "Name", // название собеседника
    var isDeleted: Boolean = false,//  удаленность чата
    val message:Message = Message()

)


interface Service<T> {
    fun create(item: T): T
    fun read(id: Int): T?

    fun delete(id: Int): Boolean
    fun show()

}

class MessageService(private val messages: MutableList<Message> = mutableListOf()) : Service<Message> {
    private var i = 1

    override fun create(message: Message): Message {
        message.id = i++
        messages.add(message)
        val chatService  = ChatService()
        chatService.create(Chat(message = message ))

        return message
    }

    override fun read(id: Int): Message? {
        return messages.find { it.id == id }
    }

    fun update(message: Message): Message? {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1) {
            messages[index] = message
            return message
        }
        return null
    }

    override fun delete(id: Int): Boolean {
        val message = read(id)
        return if (message != null) {
            message.isDeleted = true
            true
        } else {
            false
        }
    }

    override fun show() {
        for (message in messages) {
            println("ID: ${message.id}, Текст: ${message.text}")
        }
    }

    fun readStatusChange(id: Int): Boolean {
        val message = read(id)
        return if (message != null) {
            message.readMessage = true
            true
        } else {
            false
        }
    }
}


class ChatService(private val chats: MutableList<Chat> = mutableListOf()) : Service<Chat> {
    private var i = 1

    override fun create(chat: Chat): Chat { //когда  отправляется сообщение
        chat.id = i++
        chats.add(chat)
        return chat
    }

    override fun read(id: Int): Chat? {
        return chats.find { it.id == id }
    }

   fun  getUnreadChatsCount () : Int? {
       var count = 0
       return count
   }

    override fun delete(id: Int): Boolean {
        val chat = read(id)
        return if (chat != null) {
            chat.isDeleted = true
            true
        } else {
            false
        }
    }

    override fun show() {
        for (chat in chats) {
            println("ID: ${chat.id}, Текст: ${chat.title}")
        }
    }
    fun  getLastChatsCount () : Int? {
        var count = 0
        return count
    }
     fun showId() {
        for (chat in chats) {
            println("ID: ${chat.id}, Текст: ${chat.title}")
        }
    }
}