package ru.tbank.education.school.lesson2

import ru.tbank.education.school.lesson2.University.*

fun main() {
    val faculty = Faculty(name = "Факультет компьютерных наук")

    faculty.addCourse("CS101", "Анализ данных и машинное обучение", "БДиИП")
    faculty.addCourse("CS102", "Алгоритмы и структуры данных", "БДиИП")

    faculty.addTeacher(
        teacherId = "T-1",
        teacherName = "Иван Иванов",
        teacherEmail = "iivanov@edu.hse.ru",
        teacherDepartment = "БДиИП",
        teacherLimitOfCourses = 2,
    )

    faculty.addStudent(
        studentId = "S-1",
        studentName = "Пётр Петров",
        studentEmail = "ppetrov@edu.hse.ru",
        studentGroupId = "K-01",
        studentYearOfAdmission = 2025,
    )

    val teacher = faculty.findTeacherById("T-1")!!
    val student = faculty.findStudentById("S-1")!!
    val course = faculty.findCourseById("CS101")!!

    faculty.enrollTeacherToCourse(teacher, course)
    faculty.enrollStudentToCourse(student, course)

    println(teacher.getInfo())
    println(student.getInfo())

    println("Курсы студента ${student.name}:")
    student.getCourses().forEach { println("- ${it.name} (${it.department})") }
}