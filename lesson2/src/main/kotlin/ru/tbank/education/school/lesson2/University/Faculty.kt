package ru.tbank.education.school.lesson2.University

class Faculty (
    val name: String,
    private val teachers: MutableList<Teacher> = mutableListOf(),
    private val students: MutableList<Student> = mutableListOf(),
    private val courses: MutableList<Course> = mutableListOf(),
) {
    fun addStudent(
        studentId: String,
        studentName: String,
        studentEmail: String,
        studentGroupId: String,
        studentYearOfAdmission: Int
    ): Boolean {
        val newStudent = Student(
            id = studentId,
            name = studentName,
            email = studentEmail,
            groupId = studentGroupId,
            yearOfAdmission = studentYearOfAdmission,
            listOfCourses = mutableListOf(),
            faculty = name,
            )
        if (students.contains(newStudent)) return false
        students.add(newStudent)
        return true
    }

    fun addCourse(
        courseId: String,
        courseName: String,
        courseDepartment: String
    ): Boolean {
        val newCourse = Course(
            id = courseId,
            name = courseName,
            department = courseDepartment,
        )
        if (courses.contains(newCourse)) return false
        courses.add(newCourse)
        return true
    }

    fun addTeacher(
        teacherId: String,
        teacherName: String,
        teacherEmail: String,
        teacherDepartment: String,
        teacherLimitOfCourses: Int,
    ): Boolean {
        val newTeacher = Teacher(
            id = teacherId,
            name = teacherName,
            email = teacherEmail,
            department = teacherDepartment,
            limitOfCourses = teacherLimitOfCourses,
            listOfCourses = mutableListOf(),
        )
        if (teachers.contains(newTeacher)) return false
        teachers.add(newTeacher)
        return true
    }

    fun enrollStudentToCourse(student: Student, course: Course): Boolean {
        if (!students.contains(student) || !courses.contains(course)) return false
        return student.enroll(course)
    }

    fun enrollTeacherToCourse(teacher: Teacher, course: Course): Boolean {
        if (!teachers.contains(teacher) || !courses.contains(course)) return false
        return teacher.addCourse(course)
    }

    fun findStudentById(id: String): Student? = students.find { it.id == id }

    fun findTeacherById(id: String): Teacher? = teachers.find { it.id == id }

    fun findCourseById(id: String): Course? = courses.find { it.id == id }

}