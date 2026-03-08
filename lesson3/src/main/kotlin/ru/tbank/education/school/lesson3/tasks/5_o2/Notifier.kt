enum class Channel { SMS, EMAIL, TELEGRAM }

interface MessageSender {
    fun send(to: String, text: String)
}

class SmsSender : MessageSender {
    override fun send(to: String, text: String) {
        println("Отправляю SMS на $to: $text")
    }
}

class EmailSender : MessageSender {
    override fun send(to: String, text: String) {
        println("Отправляю Email на $to: $text")
    }
}

class TelegramSender : MessageSender {
    override fun send(to: String, text: String) {
        println("Отправляю сообщение в Telegram пользователю $to: $text")
    }
}

class ParentNotifier(
    private val senders: Map<Channel, MessageSender>
) {
    fun notifyParents(parents: List<String>, text: String, channel: Channel) {
        val sender = senders[channel] ?: return
        for (parent in parents) {
            sender.send(parent, text)
        }
    }
}

