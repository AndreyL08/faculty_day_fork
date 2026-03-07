package ru.tbank.education.school.lesson2.bank


class Bank {
    private var accountSeq = 1
    private var clientSeq = 1

    private val clients: MutableList<Client> = mutableListOf()
    private val accounts: MutableList<Account> = mutableListOf()

    fun addClient(clientFullName: String) {
        val newClient = Client("C-${clientSeq++}", clientFullName)
        clients.add(newClient)
    }

    fun addCheckingAccount(clientId: String) {
        val newAccount = CheckingAccount(
            id = "A-${accountSeq++}",
            balance = 0.0,
            customerId = clientId
        )
        accounts.add(newAccount)
    }

    fun addCreditAccount(clientId: String, creditLimit: Double, interestRate: Double) {
        val newAccount = CreditAccount(
            id = "A-${accountSeq++}",
            balance = 0.0,
            customerId = clientId,
            creditLimit = creditLimit,
            interestRate = interestRate

        )
        accounts.add(newAccount)
    }

    fun addSavingAccount(clientId: String, interestRate: Double) {
        val newAccount = SavingAccount(
            id = "A-${accountSeq++}",
            balance = 0.0,
            customerId = clientId,
            interestRate = interestRate
        )
        accounts.add(newAccount)
    }

    fun transfer(fromAccountId: String, toAccountId: String, amount: Double) {
        val fromAccount = accounts.find { it.id == fromAccountId }!!
        val toAccount = accounts.find { it.id == toAccountId }!!

        if (fromAccount.withdraw(amount)) toAccount.deposit(amount)
    }

}