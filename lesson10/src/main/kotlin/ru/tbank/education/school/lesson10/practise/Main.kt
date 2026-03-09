import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

fun main() {
    task1()
    println()
    task2()
    println()
    task3()
}

/*
1) Строки + регулярные выражения
["Name: Ivan, score=17", ...]
Извлечь имя и score, собрать пары, вывести победителя.
*/
fun task1() {
    val lines = listOf(
        "Name: Ivan, score=17",
        "Name: Olga, score=23",
        "Name: Max, score=5"
    )

    val re = Regex("""^Name:\s*([A-Za-z]+)\s*,\s*score=(\d+)\s*$""")

    val pairs: List<Pair<String, Int>> = lines.mapNotNull { s ->
        val m = re.find(s) ?: return@mapNotNull null
        val name = m.groupValues[1]
        val score = m.groupValues[2].toInt()
        name to score
    }

    println("Task 1 pairs: $pairs")

    val best = pairs.maxByOrNull { it.second }
    if (best != null) {
        println("Task 1 best: ${best.first} (${best.second})")
    } else {
        println("Task 1: no valid lines")
    }
}

/*
2) Даты + коллекции
["2026-01-22", ...]
Преобразовать в даты, отсортировать, посчитать сколько в январе 2026.
*/
fun task2() {
    val dateStrings = listOf(
        "2026-01-22",
        "2026-02-01",
        "2025-12-31",
        "2026-01-05"
    )

    val fmt = DateTimeFormatter.ISO_LOCAL_DATE

    val dates = dateStrings.map { LocalDate.parse(it, fmt) }.sorted()

    println("Task 2 sorted dates: ${dates.joinToString { it.format(fmt) }}")

    val countJan2026 = dates.count { it.year == 2026 && it.month == Month.JANUARY }
    println("Task 2 count in Jan 2026: $countJan2026")
}

/*
3) Коллекции + строки
"apple orange apple banana orange apple"
Частоты слов, вывести слова с частотой > 1 по алфавиту.
*/
fun task3() {
    val text = "apple orange apple banana orange apple"

    val words = text.trim().split(Regex("""\s+""")).filter { it.isNotEmpty() }

    val freq = mutableMapOf<String, Int>()
    for (w in words) {
        freq[w] = (freq[w] ?: 0) + 1
    }

    println("Task 3 freq: $freq")

    val repeated = freq
        .filter { (_, c) -> c > 1 }
        .keys
        .sorted()

    println("Task 3 repeated words: ${repeated.joinToString(", ")}")
}

fun task4() {
    val data = listOf("A-123", "B-7", "AA-12", "C-001", "D-99x")
    val re = Regex("^[A-Z]-\\d{1,3}\$")
    val filtered = data.filter { re.matches(it) }
    println("Task 4 filtered: $filtered")
}

fun task5() {
    val data = listOf("  Hello   world  ", "A   B    C", "   one")
    val normalized = data.map { s -> s.trim().replace(Regex("\\s+"), " ") }
    println(normalized)
}

fun task6() {
    val pairs = listOf(
        Pair("2026-01-01", "2026-01-10"),
        Pair("2025-12-31", "2026-01-01"),
        Pair("2026-02-01", "2026-01-22")
    )
    val fmt = DateTimeFormatter.ISO_LOCAL_DATE
    val diffs = pairs.map { (a, b) ->
        val d1 = LocalDate.parse(a, fmt)
        val d2 = LocalDate.parse(b, fmt)
        ChronoUnit.DAYS.between(d1, d2)
    }
    println(diffs)
}

fun task7() {
    val data = listOf("math:Ivan", "bio:Olga", "math:Max", "bio:Ivan", "cs:Olga")
    val map = linkedMapOf<String, MutableList<String>>()
    for (s in data) {
        val parts = s.split(":", limit = 2)
        if (parts.size == 2) {
            val key = parts[0]
            val name = parts[1]
            map.computeIfAbsent(key) {mutableListOf()}.add(name)
        }
    }
    println(map)
}

fun task8() {
    val data = listOf("Start at 2026/01/22 09:14", "No time here", "End: 22-01-2026 18:05")
    val result = mutableListOf<String>()
    val re = Regex("""(?i).*?(?:(\d{4})/(\d{2})/(\d{2})\s+(\d{2}:\d{2})|(\d{2})-(\d{2})-(\d{4})\s+(\d{2}:\d{2})).*""")

    for (s in data) {
        val m = re.find(s) ?: continue
        val g = m.groupValues
        val normalized = if (g[1].isNotEmpty()) {
            val year = g[1]
            val month = g[2]
            val day = g[3]
            val time = g[4]
            "%s-%s-%s %s".format(year, month, day, time)
        } else {
            val day = g[5]
            val month = g[6]
            val year = g[7]
            val time = g[8]
            "%s-%s-%s %s".format(year, month, day, time)
        }
        result.add(normalized)
    }
    println(result)
}
