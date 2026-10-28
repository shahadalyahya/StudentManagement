package app.studentManagmentProject.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.studentManagmentProject.constants.StudentConstant;
import app.studentManagmentProject.dao.StudentDAO;
import app.studentManagmentProject.model.Student;
import app.studentManagmentProject.util.DBConnection;

public class StudentDBDAOImpl implements StudentDAO {

	private DBConnection dbConnection;
	private static final Logger logger = LogManager.getLogger(StudentDBDAOImpl.class);

	public StudentDBDAOImpl() {
		dbConnection = new DBConnection();
	}

	// Create
	@Override
	public boolean addStudent(Student student) throws SQLException {

		String sql = "INSERT INTO student (name, age, grade, national_id) VALUES (?,?,?,?)";

		logger.debug("Writing student to database.");
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			logger.debug("Database connection established.");

			preparedStatement.setString(StudentConstant.NAME_INDEX, student.getName());
			preparedStatement.setInt(StudentConstant.AGE_INDEX, student.getAge());
			preparedStatement.setDouble(StudentConstant.GRADE_INDEX, student.getGrade());
			preparedStatement.setString(StudentConstant.NATIONAL_ID_INDEX, student.getNationalId());

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				logger.info("Student added to database successfully");
				return true;
			}

			logger.warn("Student was not added to database.");
			return false;
		}
	}

	// Read
	@Override
	public List<Student> getAllStudent() throws SQLException {
		List<Student> students = new ArrayList<Student>();

		String sql = "SELECT name, age, grade, national_id FROM student";
		logger.debug("Reading students from database.");
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				String name = resultSet.getString(StudentConstant.NAME_INDEX);
				int age = resultSet.getInt(StudentConstant.AGE_INDEX);
				double grade = resultSet.getDouble(StudentConstant.GRADE_INDEX);
				String nationalId = resultSet.getString(StudentConstant.NATIONAL_ID_INDEX);

				Student student = new Student(name, age, grade, nationalId);

				students.add(student);
			}

			logger.info("Finished reading students from database.");

		}
		return students;

	}

	@Override
	public Student getStudentByName(String searchName) throws SQLException {

		String sql = "SELECT name, age, grade, national_id FROM student WHERE name = ?";
		logger.debug("Reading student from database.");

		Student student = null;

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, searchName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {

					String name = resultSet.getString(StudentConstant.NAME_INDEX);
					int age = resultSet.getInt(StudentConstant.AGE_INDEX);
					double grade = resultSet.getDouble(StudentConstant.GRADE_INDEX);
					String nationalId = resultSet.getString(StudentConstant.NATIONAL_ID_INDEX);

					student = new Student(name, age, grade, nationalId);

					logger.info("Student found: {}", searchName);
				} else {
					logger.info("Student not found: {}", searchName);
				}
			}
		}

		logger.info("Finished reading student from database.");

		return student;
	}

	// Update
	@Override
	public boolean updateStudent(String nationalId, Student student) throws SQLException {

		String sql = "UPDATE student SET name = ?, age = ?, grade = ? WHERE national_id = ?";

		logger.debug("Updating student in database: {}", nationalId);

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(StudentConstant.NAME_INDEX, student.getName());
			preparedStatement.setInt(StudentConstant.AGE_INDEX, student.getAge());
			preparedStatement.setDouble(StudentConstant.GRADE_INDEX, student.getGrade());
			preparedStatement.setString(StudentConstant.NATIONAL_ID_INDEX, nationalId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				logger.info("Student updated successfully: {}", nationalId);
				return true;
			}

			logger.warn("Student not found: {}", nationalId);
			return false;
		}
	}

	// Delete
	@Override
	public boolean deleteStudent(String nationalId) throws SQLException {
		String sql = "DELETE FROM student WHERE national_id = ?";

		logger.debug("Deleting student from database: {}", nationalId);

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, nationalId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				logger.info("Student deleted successfully: {}", nationalId);
				return true;
			}

			logger.warn("Student not found: {}", nationalId);
			return false;
		}
	}

	@Override
	public Student getStudentByNationalId(String nationalId) throws SQLException {
		String sql = "SELECT name, age, grade, national_id FROM student WHERE national_id = ?";
		logger.debug("Reading student from database.");

		Student student = null;

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, nationalId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {

					String name = resultSet.getString(StudentConstant.NAME_INDEX);
					int age = resultSet.getInt(StudentConstant.AGE_INDEX);
					double grade = resultSet.getDouble(StudentConstant.GRADE_INDEX);

					student = new Student(name, age, grade, nationalId);

					logger.info("Student found: {}", nationalId);
				} else {
					logger.info("Student not found: {}", nationalId);
				}
			}
		}

		logger.info("Finished reading student from database.");

		return student;
	}
}
