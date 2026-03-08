interface CodeWriter {
    fun writeCode()
}

interface ProductionDeployer {
    fun deployToProduction()
}

interface ClientSupport {
    fun answerClientCall()
}

interface LoanProcessor {
    fun processLoanRequest()
}

class Developer(private val name: String) : CodeWriter, ProductionDeployer {
    override fun writeCode() = println("$name пишет код")
    override fun deployToProduction() = println("$name деплоит в прод")
}

class SupportOperator(private val name: String) : ClientSupport {
    override fun answerClientCall() = println("$name отвечает на звонок клиента")
}

class LoanManager(private val name: String) : LoanProcessor {
    override fun processLoanRequest() = println("$name обрабатывает заявку на кредит")
}

fun main() {
    val dev = Developer("Алексей")
    val support = SupportOperator("Мария")
    val loanManager = LoanManager("Игорь")

    dev.writeCode()
    dev.deployToProduction()

    support.answerClientCall()

    loanManager.processLoanRequest()
}
