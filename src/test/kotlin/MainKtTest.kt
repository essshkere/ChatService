import org.junit.Test
import org.junit.Assert.assertEquals


class MainKtTest {

    @Test
    fun testMesCreate() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        val result = messageService.create(Message( idChat = 3, title = "Тимур")).id
        assertEquals(3, result)
    }

    @Test
    fun testChatCreate() {
        val chatService = ChatService()
        chatService.create(Chat( Message( idChat = 1, title = "Марат"), title = "Марат"))
        chatService.create(Chat( Message( idChat = 2, title = "Мурат"), title = "Мурат"))
        val result = chatService.create(Chat( Message( idChat = 3, title = "Тимур"), title = "Тимур")).id
        assertEquals(3, result)
    }

    @Test
    fun testMesReadTrue() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.read(2)?.title
            assertEquals("Мурат", result)
    }

    @Test
    fun testChatReadTrue () {
        val chatService = ChatService()
        chatService.create(Chat( Message( idChat = 1, title = "Марат"), title = "Марат"))
        chatService.create(Chat( Message( idChat = 2, title = "Мурат"), title = "Мурат"))
        chatService.create(Chat( Message( idChat = 3, title = "Тимур"), title = "Тимур"))
        val result = chatService.read(2)?.title

        assertEquals("Мурат", result)
    }

    @Test
    fun testMesReadNull() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.read(4)?.title
        assertEquals(null, result)
    }

    @Test
    fun testChatReadNull () {
        val chatService = ChatService()
        chatService.create(Chat( Message( idChat = 1, title = "Марат"), title = "Марат"))
        chatService.create(Chat( Message( idChat = 2, title = "Мурат"), title = "Мурат"))
        chatService.create(Chat( Message( idChat = 3, title = "Тимур"), title = "Тимур"))
        val result = chatService.read(4)?.title

        assertEquals(null, result)
    }

    @Test
    fun testMesUpdateTrue() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.update(Message(idChat = 2,title = "Борис не попадешь", id = 2))?.title
        assertEquals("Борис не попадешь", result)
    }

    @Test
    fun testMesUpdateFalse() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.update(Message(idChat = 5,title = "Борис не попадешь", id = 5))?.title
        assertEquals(null, result)
    }

    @Test
    fun testMesDeleteTrue() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.delete(1)
        assertEquals(true, result)
    }

    @Test
    fun testMesDeleteFalse() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        val result = messageService.delete(5)
        assertEquals(false, result)
    }

    @Test
    fun testChatDeleteTrue () {
        val chatService = ChatService()
        chatService.create(Chat( Message( idChat = 1, title = "Марат"), title = "Марат"))
        chatService.create(Chat( Message( idChat = 2, title = "Мурат"), title = "Мурат"))
        chatService.create(Chat( Message( idChat = 3, title = "Тимур"), title = "Тимур"))
        val result = chatService.delete(1)

        assertEquals(true, result)
    }

    @Test
    fun testChatDeleteFalse () {
        val chatService = ChatService()
        chatService.create(Chat( Message( idChat = 1, title = "Марат"), title = "Марат"))
        chatService.create(Chat( Message( idChat = 2, title = "Мурат"), title = "Мурат"))
        chatService.create(Chat( Message( idChat = 3, title = "Тимур"), title = "Тимур"))
        val result = chatService.delete(4)

        assertEquals(false, result)
    }
    @Test
    fun testMesReadStatusTrue() {
        val messageService = MessageService()
        val test = messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        messageService.readStatusChange(1)
        val result = test.readMessage
            assertEquals(true, result)
    }
    @Test
    fun testMesReadStatusFalse() {
        val messageService = MessageService()
        val test = messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        messageService.readStatusChange(1)
        messageService.readStatusChange(1)
        val result = test.readMessage
        assertEquals(false, result)
    }
    @Test
    fun testMesUnreadChatCount() {
        val messageService = MessageService()
        messageService.create(Message( idChat = 1, title = "Марат"))
        messageService.create(Message( idChat = 2, title = "Мурат"))
        messageService.create(Message( idChat = 3, title = "Тимур"))
        messageService.readStatusChange(1)
        val result = messageService.getUnreadChatsCount()
        assertEquals(2, result)
    }

}