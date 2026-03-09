package ru.tbank.education.school.lesson8.homework.payments

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.Int

class PaymentProcessorTest {
    private lateinit var processor: PaymentProcessor

    @BeforeEach
    fun setUp() {
        processor = PaymentProcessor()
    }

    @ParameterizedTest
    @ValueSource(ints = [
        0, -13, -10000,
    ])
    fun `amount must be positive` (amount: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            processor.processPayment(
                amount = amount,
                cardNumber = "5100123456789012",
                expiryMonth = 5,
                expiryYear = 2032,
                currency = "rub",
                customerId = "C-8123",
            )
        }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "5100-1234-5678-9012",
        "5100 1234 5678 9012",
        "5100123456",
        "51001234565100123456",
        "",
        "51OO123456789O12",
    ])
    fun `card number format must be validated`(cardNumber: String) {
        assertThrows(IllegalArgumentException::class.java) {
            processor.processPayment(
                amount = 12,
                cardNumber = cardNumber,
                expiryMonth = 5,
                expiryYear = 2032,
                currency = "rub",
                customerId = "C-8123",
            )
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0, 2026",
        "13, 2026",
        "-1, 2027",
        "5, 2024",
        "10, 2025",
    )
    fun `expiry must be validated`(month: Int, year: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            processor.processPayment(
                amount = 12,
                cardNumber = "5100123456789012",
                expiryMonth = month,
                expiryYear = year,
                currency = "rub",
                customerId = "C-8123",
            )
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "   "])
    fun `currency must not be blank`(currency: String) {
        assertThrows(IllegalArgumentException::class.java) {
            processor.processPayment(
                amount = 12,
                cardNumber = "5100123456789012",
                expiryMonth = 11,
                expiryYear = 2025,
                currency = currency,
                customerId = "C-8123",
            )
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "   "])
    fun `customer id must not be blank`(customerId: String) {
        assertThrows(IllegalArgumentException::class.java) {
            processor.processPayment(
                amount = 12,
                cardNumber = "5100123456789012",
                expiryMonth = 11,
                expiryYear = 2025,
                currency = "USD",
                customerId = customerId,
            )
        }
    }

    @Test
    fun `suspicious card must be rejected`() {
        val result = processor.processPayment(
            amount = 12,
            cardNumber = "4444111122223333",
            expiryMonth = 11,
            expiryYear = 2025,
            currency = "USD",
            customerId = "C-1",
        )

        assertEquals("REJECTED", result.status)
        assertTrue(result.message.contains("fraud", ignoreCase = true))
    }

    @Test
    fun `card with invalid luhn must be rejected as suspicious`() {
        val result = processor.processPayment(
            amount = 12,
            cardNumber = "4111111111111112",
            expiryMonth = 11,
            expiryYear = 2025,
            currency = "USD",
            customerId = "C-10",
        )

        assertEquals("REJECTED", result.status)
        assertTrue(result.message.contains("fraud", ignoreCase = true))
    }

    @Test
    fun `successful payment should return success result`() {
        val result = processor.processPayment(
            amount = 10,
            cardNumber = "4111111111111111",
            expiryMonth = 12,
            expiryYear = 2026,
            currency = "usd",
            customerId = "C-2",
        )

        assertEquals("SUCCESS", result.status)
        assertEquals("Payment completed", result.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["USD", "EUR", "GBP", "JPY", "RUB", "CHF"])
    fun `currency conversion branches should be executed`(currency: String) {
        val result = processor.processPayment(
            amount = 10,
            cardNumber = "4111111111111111",
            expiryMonth = 12,
            expiryYear = 2026,
            currency = currency,
            customerId = "C-3",
        )

        assertEquals("SUCCESS", result.status)
    }

    @Test
    fun `gateway should fail with insufficient funds`() {
        val result = processor.processPayment(
            amount = 10,
            cardNumber = "5500000000000004",
            expiryMonth = 12,
            expiryYear = 2026,
            currency = "USD",
            customerId = "C-4",
        )

        assertEquals("FAILED", result.status)
        assertEquals("Insufficient funds", result.message)
    }

    @Test
    fun `gateway should fail when transaction limit exceeded`() {
        val result = processor.processPayment(
            amount = 100_001,
            cardNumber = "4111111111111111",
            expiryMonth = 12,
            expiryYear = 2026,
            currency = "USD",
            customerId = "C-5",
        )

        assertEquals("FAILED", result.status)
        assertEquals("Transaction limit exceeded", result.message)
    }

    @Test
    fun `gateway should fail with timeout`() {
        val result = processor.processPayment(
            amount = 34,
            cardNumber = "4111111111111111",
            expiryMonth = 12,
            expiryYear = 2026,
            currency = "USD",
            customerId = "C-6",
        )

        assertEquals("FAILED", result.status)
        assertEquals("Gateway timeout", result.message)
    }

    @Test
    fun `loyalty discount must have positive base amount`() {
        assertThrows(IllegalArgumentException::class.java) {
            processor.calculateLoyaltyDiscount(points = 1000, baseAmount = 0)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "12000, 30000, 5000",
        "8000, 20000, 3000",
        "2500, 10000, 1000",
        "700, 4000, 200",
        "100, 4000, 0",
    )
    fun `loyalty discount should be calculated correctly`(
        points: Int,
        baseAmount: Int,
        expectedDiscount: Int,
    ) {
        val discount = processor.calculateLoyaltyDiscount(points, baseAmount)
        assertEquals(expectedDiscount, discount)
    }

    @Test
    fun `bulk process should handle empty list`() {
        val results = processor.bulkProcess(emptyList())
        assertTrue(results.isEmpty())
    }

    @Test
    fun `bulk process should handle mixed valid and invalid payments`() {
        val payments = listOf(
            PaymentData(
                amount = 10,
                cardNumber = "4111111111111111",
                expiryMonth = 12,
                expiryYear = 2026,
                currency = "USD",
                customerId = "C-1",
            ),
            PaymentData(
                amount = 0,
                cardNumber = "4111111111111111",
                expiryMonth = 12,
                expiryYear = 2026,
                currency = "USD",
                customerId = "C-2",
            ),
        )

        val results = processor.bulkProcess(payments)

        assertEquals(2, results.size)
        assertEquals("SUCCESS", results[0].status)
        assertEquals("REJECTED", results[1].status)
    }
}