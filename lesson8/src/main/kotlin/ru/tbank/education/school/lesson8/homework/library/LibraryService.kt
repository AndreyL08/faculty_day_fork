package ru.tbank.education.school.lesson8.homework.library

import kotlin.math.max

class LibraryService {
    private val books = mutableMapOf<String, Book>()
    private val borrowedBooks = mutableMapOf<String, String>()
    private val borrowerFines = mutableMapOf<String, Int>()

    fun addBook(book: Book) { books[book.isbn] = book }

    fun borrowBook(isbn: String, borrower: String) {
        if (isBorrowed(isbn) || !isAvailable(isbn)) throw IllegalArgumentException("Book $isbn is not available")
        if (hasFines(borrower) || borrower in borrowedBooks.values) throw IllegalArgumentException("The borrower $borrower has a borrowed book or an outstanding debt")
        borrowedBooks[isbn] = borrower
    }

    fun returnBook(isbn: String) {
        if (!isExist(isbn)) throw IllegalArgumentException("Book $isbn is not exist in the library")
        if (!isBorrowed(isbn)) throw IllegalArgumentException("Book $isbn is not borrowed")
        borrowedBooks.remove(isbn)
    }

    fun isAvailable(isbn: String): Boolean = !isBorrowed(isbn) && isExist(isbn)

    fun calculateOverdueFine(isbn: String, daysOverdue: Int): Int {
        if (!isBorrowed(isbn)) return 0
        return max(daysOverdue-10, 0) * 60
    }

    private fun isBorrowed(isbn: String): Boolean = borrowedBooks.containsKey(isbn)

    private fun isExist (isbn: String): Boolean = books.containsKey(isbn)

    private fun hasFines(borrower: String): Boolean = (borrowerFines[borrower] ?: 0) > 0
}