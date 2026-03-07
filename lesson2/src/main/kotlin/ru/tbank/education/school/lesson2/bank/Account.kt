package ru.tbank.education.school.lesson2.bank

abstract class Account(
    val id: String,
    var balance: Double,
    val customerId: String
) {
    open fun withdraw(amount: Double): Boolean = false
    open fun deposit(amount: Double): Boolean {
        if (amount <= 0.0) return false
        balance += amount
        return true
    }
}

class CheckingAccount(
    id: String,
    balance: Double,
    customerId: String,
) : Account (
    id,
    balance,
    customerId,
) {
    override fun withdraw(amount: Double): Boolean {
        if (balance >= amount) {
            balance -= amount
            return true
        }
        return false
    }
}

class CreditAccount(
    id: String,
    balance: Double,
    customerId: String,
    val creditLimit: Double,
    val interestRate: Double,
) : Account(
    id,
    balance,
    customerId,
) {

    override fun withdraw(amount: Double): Boolean {
      if (creditLimit + balance >= amount) {
          balance -= amount
          return true
      }
        return false
    }

    fun applyDebtInterest(): Boolean {
        if (balance >= 0.0) return false
        balance -= balance * interestRate
        return true
    }
}

class SavingAccount(
    id: String,
    balance: Double,
    customerId: String,
    val interestRate: Double,
) : Account(
    id,
    balance,
    customerId,
) {
    override fun withdraw(amount: Double): Boolean {
        if (balance >= amount) {
            balance -= amount
            return true
        }
        return false
    }

    fun applyInterest(): Boolean {
        if (balance == 0.0) return false
        balance += balance * interestRate
        return true;
    }
}
