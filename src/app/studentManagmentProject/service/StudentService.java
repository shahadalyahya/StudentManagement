package app.studentManagmentProject.service;

import java.util.List;

import app.studentManagmentProject.model.Student;

public interface StudentService {

	/**
	 * Adds a new student to the system.
	 *
	 * @param student the student to be added
	 * @return true if the student was added successfully, otherwise false
	 * @throws Exception if an error occurs while adding the student
	 */
	public boolean addStudent(Student student) throws Exception;

	/**
	 * Retrieves all students from the system.
	 *
	 * @return a list containing all students
	 */
	public List<Student> showStudents();

	/**
	 * Searches for a student by name.
	 *
	 * @param studentName the name of the student to search for
	 * @return the student's information if found, otherwise a message indicating
	 *         that the student was not found
	 */
	public String searchStudent(String studentName);

	/**
	 * Updates a student's information using their national ID.
	 *
	 * @param nationalId the national ID of the student to update
	 * @param student    the updated student information
	 * @return true if the student was updated successfully, otherwise false
	 */
	public boolean updateStudent(String nationalId, Student student);

	/**
	 * Deletes a student using their national ID.
	 *
	 * @param nationalId the national ID of the student to delete
	 * @return true if the student was deleted successfully, otherwise false
	 */
	public boolean deleteStudent(String nationalId);
}

