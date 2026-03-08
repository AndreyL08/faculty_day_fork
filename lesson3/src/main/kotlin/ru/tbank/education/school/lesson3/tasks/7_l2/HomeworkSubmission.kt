open class HomeworkSubmission(
    val studentName: String,
    var content: String
) {
    open val canResubmit: Boolean = true

    open fun resubmit(newContent: String) {
        content = newContent
        println("Ученик $studentName отправил новую версию работы")
    }
}

class FinalExamSubmission(
    studentName: String,
    content: String
) : HomeworkSubmission(studentName, content) {

    override val canResubmit: Boolean = false

    override fun resubmit(newContent: String) {
        // no-op
    }
}

fun allowFixes(submission: HomeworkSubmission) {
    if (submission.canResubmit) {
        submission.resubmit(submission.content + "\n// исправлено по замечаниям")
    }
}

fun main() {
    val draft = HomeworkSubmission("Аня", "Решение задачи 1")
    val final = FinalExamSubmission("Боря", "Итоговая контрольная работа")

    allowFixes(draft)
    allowFixes(final)
}
