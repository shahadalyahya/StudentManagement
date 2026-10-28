package app.studentManagmentProject.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.studentManagmentProject.model.Course;
import app.studentManagmentProject.util.DBConnection;

public class CourseDAO {
	private static final Logger logger = LogManager.getLogger(CourseDAO.class); 
	
	// Create
	public boolean addCourse(Course course)  throws SQLException {

		String sql = "INSERT INTO course (name, description, course_code) VALUES (?, ?, ?)";

		logger.debug("Writing course to database.");

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			logger.debug("Database connection established.");

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setString(3, course.getCourseCode());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				logger.info("Course added to database successfully.");
				return true;
			}

			logger.warn("Course was not added to database.");
			return false;
		}
	}
	
	// Read
	public List<Course> getAllCourses() throws SQLException {

		List<Course> courses = new ArrayList<Course>();

		String sql = "SELECT name, description, course_code FROM course";

		logger.debug("Reading courses from database.");

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String courseCode = resultSet.getString("course_code");

				Course course = new Course(name, description, courseCode);

				courses.add(course);
			}

			logger.info("Finished reading courses from database.");
		}

		return courses;
	}

	public Course getCourse(String courseCode) throws SQLException {

		String sql = "SELECT name, description, course_code FROM course WHERE course_code = ?";

		Course course = null;

		logger.debug("Reading course from database: {}", courseCode);

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, courseCode);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {

					String name = resultSet.getString("name");
					String description = resultSet.getString("description");
					String foundCourseCode = resultSet.getString("course_code");

					course = new Course(name, description, foundCourseCode);

					logger.info("Course found: {}", courseCode);

				} else {
					logger.info("Course not found: {}", courseCode);
				}
			}
		}

		return course;
	}

	// Update
	public boolean updateCourse(String courseCode, Course course) throws SQLException {

		String sql = "UPDATE course SET name = ?, description = ? WHERE course_code = ?";

		logger.debug("Updating course in database: {}", courseCode);

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setString(3, courseCode);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				logger.info("Course updated successfully: {}", courseCode);
				return true;
			}

			logger.warn("Course not found: {}", courseCode);
			return false;
		}
	}

	// Delete
	public boolean deleteCourse(String courseCode) throws SQLException {

		String sql = "DELETE FROM course WHERE course_code = ?";

		logger.debug("Deleting course from database: {}", courseCode);

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, courseCode);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				logger.info("Course deleted successfully: {}", courseCode);
				return true;
			}

			logger.warn("Course not found: {}", courseCode);
			return false;
		}
	}
}
