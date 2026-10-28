package app.studentManagmentProject.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.studentManagmentProject.dao.StudentDAO;
import app.studentManagmentProject.dao.impl.StudentDBDAOImpl;
import app.studentManagmentProject.exception.StudentAlreadyExistsException;
import app.studentManagmentProject.model.Student;
import app.studentManagmentProject.service.StudentService;

public class StudentDBServiceImpl implements StudentService {

	private StudentDAO studentDAO;
	private static final Logger logger = LogManager.getLogger(StudentDBServiceImpl.class);

	public StudentDBServiceImpl() {
		studentDAO = new StudentDBDAOImpl();
	}

	// Create
	@Override
	public boolean addStudent(Student student) throws StudentAlreadyExistsException {

		try {

			if (studentDAO.getStudentByNationalId(student.getNationalId()) != null) {
				throw new StudentAlreadyExistsException("Student " + student.getNationalId() + " already exists");
			}

			return studentDAO.addStudent(student);

		} catch (StudentAlreadyExistsException e) {
			throw e;

		} catch (Exception e) {
			logger.warn("Failed to add student to database.", e);
			return false;

		}
	}

	// Read
	@Override
	public List<Student> showStudents() {
		List<Student> students = new ArrayList<Student>();
		
		try {
			students = studentDAO.getAllStudent();
		} catch (Exception e) {
			logger.warn("Failed to retrieve students from database.", e);
		}

		return students;
	}

	@Override
	public String searchStudent(String studentName) {
		try {
			Student student = studentDAO.getStudentByName(studentName);

			if (student == null) {
				return "Student not found";
			} else {
				return student.studentInfo();
			}
		} catch (Exception e) {
			logger.error("Failed to retrieve student from database. ", e);
			return "Error retrieving student";
		}

	}

	// Update
	@Override
	public boolean updateStudent(String nationalId, Student student) {
		try {
			return studentDAO.updateStudent(nationalId, student);

		} catch (Exception e) {

			logger.warn("Failed to update student in database. " + "National ID: " + nationalId, e);
			return false;
		}
	}

	// Delete
	@Override
	public boolean deleteStudent(String nationalId) {
		try {

			return studentDAO.deleteStudent(nationalId);

		} catch (Exception e) {

			logger.warn("Failed to delete student from database." + "National ID: " + nationalId,e);
			return false;
		}
	}
}
