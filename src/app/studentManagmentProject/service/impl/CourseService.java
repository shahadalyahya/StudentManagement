package app.studentManagmentProject.service.impl;


import java.sql.SQLException;
import java.util.List;

import app.studentManagmentProject.dao.impl.CourseDAO;
import app.studentManagmentProject.model.Course;

public class CourseService {

	private CourseDAO courseDAO;

	public CourseService() {
		courseDAO = new CourseDAO();
	}

	// Create
	public boolean addCourse(Course course) throws SQLException {
		boolean flag = courseDAO.addCourse(course);
		return flag;
	}

	// Read
	public List<Course> showCourses() throws SQLException {
		List<Course> courses = courseDAO.getAllCourses();
		return courses;
	}

	public Course findCourses(String courseCode) throws SQLException {
		Course course = courseDAO.getCourse(courseCode);
		return course;
	}

	// Update
	public boolean updateCourse(String courseCode, Course course) throws SQLException {
		boolean flag = courseDAO.updateCourse(courseCode, course);
		return flag;
	}

	// Delete
	public boolean deleteCourse(String courseCode) throws SQLException {
		boolean flag = courseDAO.deleteCourse(courseCode);
		return flag;
	}
}

