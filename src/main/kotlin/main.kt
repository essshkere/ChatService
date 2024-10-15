import com.sun.org.apache.xml.internal.serializer.utils.Utils
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages

fun main() {
    val messageService = MessageService()

    messageService.create(Message(idChat = 1, title = "Марат"))
    messageService.create(Message(idChat = 2, title = "Мурат"))
    messageService.create(Message(idChat = 3, title = "Тимур"))
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
        return message
    }


    override fun read(id: Int): Message? {
        return messages.find { it.id == id && !it.isDeleted }
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
        return messages.groupBy { it.idChat }
            .map { (idChat, msgs) ->
                msgs.lastOrNull { !it.isDeleted }?.let { lastMessage ->
                    "Чат ID: $idChat, Последнее сообщение: ${lastMessage.text}"
                } ?: "Чат ID: $idChat, нет сообщений."
            }
    }

    fun getMessagesFromChat(idChat: Int, count: Int): List<Message> {
        // Фильтруем сообщения по idChat и исключаем удалённые, оставляем последние count:
        val filteredMessages = messages.filter { it.idChat == idChat && !it.isDeleted }

        // Если список пуст, выводим сообщение в консоль и возвращаем пустой список:
        if (filteredMessages.isEmpty()) {
            println("Нет сообщений в чате ID: $idChat.")
            return emptyList()
        }

        // Помечаем сообщения как прочитанные и возвращаем последние count:
        return filteredMessages.takeLast(count).onEach { message -> message.readMessage = true }
    }
}




