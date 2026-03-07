package ru.tbank.education.school.lesson1


fun calculate(a: Double, b: Double, operation: OperationType = OperationType.ADD): Double? {
    return when (operation) {
        OperationType.ADD -> a + b
        OperationType.SUBTRACT -> a - b
        OperationType.MULTIPLY -> a * b
        OperationType.DIVIDE -> if (b != 0.0) a / b else null
    }
}

@Suppress("ReturnCount")
fun String.calculate(): Double? {
    val (str_a, op, str_b) = this.split(" ")
    val a = str_a.toDoubleOrNull()
    val b = str_b.toDoubleOrNull()
    if (a == null || b == null) return null
    return when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if (b != 0.0) a / b else null
        else -> null
    }
}
