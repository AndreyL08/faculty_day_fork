data class StudentProfile(
    var name: String,
    var birthYear: Int,
    var className: String
) {
    fun calculateAge(currentYear: Int): Int = currentYear - birthYear
}

class StudentInfoPrinter {
    fun printStudentInfo(profile: StudentProfile, currentYear: Int) {
        println("Имя: ${profile.name}")
        println("Класс: ${profile.className}")
        println("Возраст: ${profile.calculateAge(currentYear)}")
    }
}

class StudentProfileSaver {
    fun saveToFile(profile: StudentProfile, filename: String) {
        // сохраняем данные в файл
    }
}