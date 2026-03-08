class Benefits {
    private val benefits: MutableMap<String, Double> = mutableMapOf(
        "отличник" to 0.5,
        "льготник" to 0.7,
        "обычный" to 1.0
    )

    fun getBenefit(type: String): Double? = benefits[type]

    fun addBenefit(type: String, factor: Double) {
        if (factor in 0.0..1.0) benefits[type] = factor
    }
}

class DiscountCalculator {
    fun calculateDiscount(studentType: String, price: Int, benefits: Benefits): Int {
        val factor = benefits.getBenefit(studentType) ?: 1.0
        return (price * factor).toInt()
    }
}
