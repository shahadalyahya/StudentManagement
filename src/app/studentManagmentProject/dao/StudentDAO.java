package app.studentManagmentProject.dao;

import java.util.List;

import app.studentManagmentProject.model.Student;

public interface StudentDAO {
	/**
	 * Adds a new student to the data source.
	 *
	 * @param student the student to be added
	 * @return true if the student was added successfully, otherwise false
	 * @throws Exception if an error occurs while adding the student
	 */
	public boolean addStudent(Student student)throws Exception;
	/**
	 * Retrieves all students from the data source.
	 *
	 * @return a list containing all students
	 * @throws Exception if an error occurs while retrieving students
	 */
	public List<Student> getAllStudent() throws Exception;
	/**
	 * Searches for a student by name.
	 *
	 * @param searchName the name of the student to search for
	 * @return the student if found, otherwise null
	 * @throws Exception if an error occurs while searching
	 */
	public Student getStudentByName(String searchName) throws Exception;
	/**
	 * Updates a student's information using their national ID.
	 *
	 * @param nationalId the national ID of the student to update
	 * @param student    the new student information
	 * @return true if the student was updated successfully, otherwise false
	 */
	public boolean updateStudent(String nationalId, Student student) throws Exception;
	/**
	 * Deletes a student using their national ID.
	 *
	 * @param nationalId the national ID of the student to delete
	 * @return true if the student was deleted successfully, otherwise false
	 */
	public boolean deleteStudent(String nationalId)throws Exception;
	/**
	 * Retrieves a student using their national ID.
	 *
	 * @param nationalId the national ID of the student
	 * @return the student if found, otherwise null
	 * @throws Exception if an error occurs while retrieving the student
	 */
	public Student getStudentByNationalId(String nationalId)throws Exception;
}