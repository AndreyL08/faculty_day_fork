data class Homework(val subject: String, val text: String)

class HomeworkManager {
    private val homeworks = mutableListOf<Homework>()
    fun addHomework(subject: String, text: String) = homeworks.add(Homework(subject, text))
    fun getHomeworks(): List<Homework> = homeworks.toList()
}

class HomeworkPrinter {
    fun printHomeworks(homeworks: List<Homework>) {
        println("Домашка:")
        for ((subject, text) in homeworks) {
            println("$subject: $text")
        }
    }
}

class HomeworkReminderSender {
    fun sendRemindersToParents(parentsPhones: List<String>) {
        // здесь как будто код для отправки SMS
        for (phone in parentsPhones) {
            println("Отправляю SMS на $phone: Не забудьте проверить домашку!")
        }
    }
}

class HomeworkFileSaver {
    fun saveToFile(homeworks: List<Homework>, filename: String) {
        // логика сохранения в файл
    }
}

