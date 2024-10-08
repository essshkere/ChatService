import com.sun.org.apache.xml.internal.serializer.utils.Utils
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages

fun main() {
    val messageService = MessageService()
    val message = Message(0, 1, false, "text", false)
    messageService.create(message)
    println(message)
    val count = messageService.getUnreadChatsCount()
println (count)
}

data class Message(
    var id: Int = 0, // id сообщения
    val idChat: Int, // название собеседника
    var isDeleted: Boolean = false, //  удаленность сообщения
    var text: String = "0",//  само сообщение
    var readMessage: Boolean = false, // прочитано сообщение?
    val sent: Boolean = true // отправленное или полученное сообщение
)

data class Chat(
    var id: Int = 0, // id сообщения
    val title: String = "Name", // название собеседника
    var isDeleted: Boolean = false,//  удаленность чата
    val message: Message,

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
        val chatService = ChatService()
        chatService.create(Chat(message = message))

        return message
    }

    override fun read(id: Int): Message? { //+
        return messages.find { it.id == id }
    }

    fun update(message: Message): Message? { //+
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

    fun getUnreadChatsCount(): (List<Message>) -> Int {


        val predicate = fun(message: Message) = message.readMessage == false
        val unreadList = messages.filter(predicate)

        val uniqueId: (List<Message>) -> Int = { idChat -> idChat.distinct().size }
        return uniqueId

    }
//    public inline fun <Message> MutableList<Message>.filter (predicate : (Message) -> Boolean): List<Message> {
//
//        return filterTo(ArrayList<Message>(),predicate )
//
//    }
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

    fun getLastChatsCount(): Int {
        var count = 0
        return count
    }

    fun showId() {
        for (chat in chats) {
            println("ID: ${chat.id}, Текст: ${chat.title}")
        }
    }
}