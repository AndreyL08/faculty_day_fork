package ru.tbank.education.school.lesson2.University

abstract class Person (
    val id: String,
    val name: String,
    val email: String,
) {
    open fun getInfo(): String = "Имя: $name, email: $email"
}

class Student(
    id: String,
    name: String,
    email: String,
    val groupId: String,
    val yearOfAdmission: Int,
    private val listOfCourses: MutableList<Course> = mutableListOf(),
    val faculty: String,
) : Person(id, name, email) {

    constructor (
        id: String,
        name: String,
        email: String,
        groupId: String,
        faculty: String,
    ) : this(
        id,
        name,
        email,
        groupId,
        yearOfAdmission = 2026,
        listOfCourses = mutableListOf(),
        faculty = faculty,
    )

    override fun getInfo(): String =
        super.getInfo() + ", статус: Студент, группа: $groupId, факультет: $faculty, год поступления: $yearOfAdmission"

    fun enroll(course: Course) = listOfCourses.add(course)

    fun leave(course: Course): Boolean {
        if (!listOfCourses.contains(course)) return false
        listOfCourses.remove(course)
        return true
    }

    fun getCourses(): List<Course> = listOfCourses
}

open class Teacher(
    id: String,
    name: String,
    email: String,
    val department: String,
    protected val listOfCourses: MutableList<Course> = mutableListOf(),
    val limitOfCourses: Int,
) : Person(id, name, email) {

    override fun getInfo(): String =
        super.getInfo() + ", статус: Преподаватель, кафедра: $department,"

    fun addCourse(course: Course): Boolean {
        if (listOfCourses.contains(course) ||
            course.department != department ||
            listOfCourses.size >= limitOfCourses
            ) return false
        listOfCourses.add(course)
        return true
    }

    fun deleteCourse(course: Course): Boolean {
        if (listOfCourses.isEmpty() || !listOfCourses.contains(course)) return false
        listOfCourses.remove(course)
        return true
    }

    fun getCourses(): List<Course> = listOfCourses
}