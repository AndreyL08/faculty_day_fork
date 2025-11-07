package ru.tbank.education.school.lesson1

/**
 * Метод для вычисления простых арифметических операций.
 */
fun calculate(a: Double, b: Double, operation: OperationType): Double? {
    when (operation) {
        OperationType.ADD -> return a + b
        OperationType.SUBTRACT -> return a - b
        OperationType.MULTIPLY -> return a * b
        OperationType.DIVIDE -> if (b != 0.0) return a/b else return null
    }
}

/**
 * Функция вычисления выражения, представленного строкой
 * @return результат вычисления строки или null, если вычисление невозможно
 * @sample "5 * 2".calculate()
 */
@Suppress("ReturnCount")
fun String.calculate(): Double? {
    val lst = this.split(" ")
    val a = lst[0].toDoubleOrNull() ?: return null
    val b = lst[2].toDoubleOrNull() ?: return null
    val oper = lst[1]
    val op: OperationType = when (oper) {
        "+" -> OperationType.ADD
        "-" -> OperationType.SUBTRACT
        "*" -> OperationType.MULTIPLY
        "/" -> OperationType.DIVIDE
        else -> return null
    }
    return calculate(a, b, op)
}
