import com.sun.org.apache.xml.internal.serializer.utils.Utils
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages

fun main() {
    val messageService = MessageService()
    val chatService = ChatService()
    messageService.create(Message( idChat = 1, title = "Марат"))
    messageService.create(Message( idChat = 2, title = "Мурат"))
    messageService.create(Message( idChat = 3, title = "Тимур"))




}

data class Message(
    var id: Int = 0, // id сообщения
    val idChat: Int, // название собеседника
    var isDeleted: Boolean = false, //  удаленность сообщения
    var text: String = "0",//  само сообщение
    var readMessage: Boolean = false, // прочитано сообщение?
    val sent: Boolean = true, // отправленное или полученное сообщение
    val title: String
)

data class Chat(
    val message: Message,
    val title: String, // название собеседника
    var id: Int = 0, // id сообщения
    var isDeleted: Boolean = false,//  удаленность чата


)


interface Service<T> {
    fun create(item: T): T
    fun read(id: Int): T?
    fun delete(id: Int): Boolean
    fun show()


}

class MessageService(private val messages: MutableList<Message> = mutableListOf()) : Service<Message> {
    private var i = 1

    override fun create(message: Message): Message {//+
        message.id = i++
        messages.add(message)
        val chatService = ChatService()
        if (chatService.read(message.idChat) == null) {
            chatService.create(Chat(message, message.title))
        }

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

    override fun delete(id: Int): Boolean { //+
        val message = read(id)
        return if (message != null) {
            message.isDeleted = true
            true
        } else {
            false
        }
    }

    override fun show() { //+
        for (message in messages) {
            println("ID: ${message.id}, Текст: ${message.text}")
        }
    }

    fun readStatusChange(id: Int): Boolean { //+
        val message = read(id)
        return if (message != null) {
            message.readMessage = if (message.readMessage == true) false else true
            true
        } else {
            false
        }
    }

    fun getUnreadChatsCount(): Int {//+
        val predicate: (Message) -> Boolean = { message -> !message.readMessage }
        //  список непрочитанных сообщений
        val unreadMessages = messages.filter(predicate)
        // уникальные идентификаторы
        val uniqueChatIds = unreadMessages.map { it.idChat }.distinct()
        return uniqueChatIds.size
    }

    fun getLastMessages(): List<String> {
        val lastMessages = mutableListOf<String>()

        val groupedMessages = messages.groupBy { it.idChat }
        for ((idChat, messages) in groupedMessages) {
            val lastMessage = messages.lastOrNull { !it.isDeleted }
            if (lastMessage != null) {
                lastMessages.add("Чат ID: $idChat, Последнее сообщение: ${lastMessage.text}")
            } else {
                lastMessages.add("Чат ID: $idChat, нет сообщений.")
            }
        }

        return lastMessages
    }
    fun getMessagesFromChat(idChat: Int, count: Int): List<Message> {
        val messagesFromChat = messages.filter { it.idChat == idChat && !it.isDeleted }.takeLast(count)

        // Помечаем сообщения как прочитанные
        messagesFromChat.forEach { it.readMessage = true }

        return if (messagesFromChat.isNotEmpty()) {
            messagesFromChat
        } else {
            println("Нет сообщений в чате ID: $idChat.")
            emptyList()
        }
    }

}


public class ChatService(private val chats: MutableList<Chat> = mutableListOf()) : Service<Chat> {
    private var i = 1

    override fun create(chat: Chat): Chat { //когда отправляется сообщение
        chat.id = i++
        chats.add(chat)
        return chat
    }

    override fun read(id: Int): Chat? {//+
        return chats.find { it.id == id }
    }


    override fun delete(id: Int): Boolean {//+
        val chat = read(id)
        return if (chat != null) {
            chat.isDeleted = true
            true
        } else {
            false
        }
    }

    override fun show() {//+
        for (chat in chats) {
            println("ID: ${chat.id}, Текст: ${chat.title}")
        }
    }


    fun showIdChat(idChat: Int) {//+
        for (chat in chats) {
            if (chat.id == idChat)
                println("ID: ${chat.id}, Текст: ${chat.title}")
        }
    }
    fun chatExists(idChat: Int): Boolean {
        return chats.any { it.id == idChat }
    }
    fun getChats(): List<Chat> {
        return chats.filter { !it.isDeleted }
    }
}

