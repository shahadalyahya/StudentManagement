package app.studentManagmentProject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.studentManagmentProject.exception.StudentAlreadyExistsException;
import app.studentManagmentProject.model.Course;
import app.studentManagmentProject.model.Student;
import app.studentManagmentProject.service.impl.CourseService;
import app.studentManagmentProject.service.impl.StudentDBServiceImpl;
import app.studentManagmentProject.util.DBConnection;
import app.studentManagmentProject.main;

public class main {
static Scanner scanner = new Scanner(System.in);
	static StudentDBServiceImpl studentService = new StudentDBServiceImpl();
	static CourseService courseService = new CourseService();
	private static final Logger logger = LogManager.getLogger(main.class);
	
	public static void main(String[] args) {

		testConnection();
		
		int option = 0;

	    do {
	        showMenu();

	        try {
	            option = scanner.nextInt();

	            switch (option) {

	            case 1:
	                studentMenu();
	                break;

	            case 2:
	                courseMenu();
	                break;

	            case 3:
	                System.out.println("Thank you. Goodbye!");
	                logger.info("User selected Exit.");
	                break;

	            default:
	                System.out.println("Invalid option. Please try again.");
	                logger.warn("Invalid main menu option: {}", option);
	            }

	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number.");
	            logger.warn("User entered invalid input.");
	            scanner.nextLine();
	        }

	    } while (option != 3);

	    logger.info("Application closed.");
	    scanner.close();
	}

	/**
	 * Displays the main menu and asks the user to select an option.
	 */
	private static void showMenu() {
		System.out.println("----------------------------\n" 
		+ "MAIN MENU:\n"
		+ "1. Student Menu\n"
		+ "2. Course Menu\n"
		+ "3. Exit\n"
		+ "Enter the number of the option you want: ");

	}
	private static void showStudentMenu() {

	    System.out.println("----------------------------\n"
	    + "STUDENT MENU:\n"
	    + "1. Add Student\n"
	    + "2. Show Students\n"
	    + "3. Find Student\n"
	    + "4. Update Student\n"
	    + "5. Delete Student\n"
	    + "6. Back to Main Menu\n"
	    + "Enter the number of the option you want: ");

	}
	private static void showCourseMenu() {

	    System.out.println("----------------------------\n"
	    + "COURSE MENU:\n"
	    + "1. Add Course\n"
	    + "2. Show Courses\n"
	    + "3. Find Course\n"
	    + "4. Update Course\n"
	    + "5. Delete Course\n"
	    + "6. Back to Main Menu\n"
	    + "Enter the number of the option you want: ");

	}
	private static void studentMenu() {

		int option = 0;

		do {
			showStudentMenu();

			try {
				option = scanner.nextInt();

				switch (option) {
				case 1:
					logger.info("Starting Add Student operation.");
					addStudent();
					break;

				case 2:
					logger.info("Starting Show Students operation.");
					showStudents();
					break;

				case 3:
					logger.info("Starting Find Student operation.");
					searchStudent();
					break;

				case 4:
					logger.info("Starting Update Student operation.");
					updateStudent();
					break;

				case 5:
					logger.info("Starting Delete Student operation.");
					deleteStudent();
					break;

				case 6:
					logger.info("Returning to main menu.");
					System.out.println("Returning to main menu...");
					break;

				default:
					logger.warn("Invalid student menu option: {}", option);
					System.out.println("Invalid option. Please try again.");
				}

			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine();
			}

		} while (option != 6);
	}

	private static void courseMenu() {

		int option = 0;

		do {
			showCourseMenu();

			try {
				option = scanner.nextInt();

				switch (option) {
				case 1:
					logger.info("Starting Add Course operation.");
					addCourse();
					break;

				case 2:
					logger.info("Starting Show Courses operation.");
					showCourses();
					break;

				case 3:
					logger.info("Starting Find Course operation.");
					findCourse();
					break;

				case 4:
					logger.info("Starting Update Course operation.");
					updateCourse();
					break;

				case 5:
					logger.info("Starting Delete Course operation.");
					deleteCourse();
					break;

				case 6:
					logger.info("Returning to main menu.");
					System.out.println("Returning to main menu...");
					break;

				default:
					logger.warn("Invalid course menu option: {}", option);
					System.out.println("Invalid option. Please try again.");

				}

			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine();
			}

		} while (option != 6);
	}

	static void addStudent() {

		System.out.println("Enter student national ID:");
		String nationalId = scanner.next();

		System.out.println("Enter student name:");
		scanner.nextLine();
		String name = scanner.nextLine();

		System.out.println("Enter student age:");
		int age = scanner.nextInt();

		System.out.println("Enter student grade:");
		double grade = scanner.nextDouble();

		try {
			Student student = new Student(name, age, grade, nationalId);
			
			boolean flag = studentService.addStudent(student);
			if (flag) {
				System.out.println("Student added successfully.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn("Invalid student data: {}", e.getMessage());

		} catch (StudentAlreadyExistsException e) {
			System.out.println(e.getMessage());
			logger.warn("Student already exists: {}", e.getMessage());

		} catch (Exception e) {
			System.out.println("An unexpected error occurred.");
			logger.error("Error while adding student.", e);
		}
	}

	static void showStudents() {

		List<Student> students = studentService.showStudents();

		for (Student student : students) {
			System.out.println(student.studentInfo());
		}
	}
	
	private static void searchStudent() {

		System.out.println("enter student name:");

		scanner.nextLine();

		String searchName = scanner.nextLine();

		logger.info("User Searching for student: {}", searchName);

		String searchResult = studentService.searchStudent(searchName);
		System.out.println(searchResult);

	}

	
	private static void updateStudent() {

		System.out.println("Enter student national ID:");
		String nationalId = scanner.next();

		System.out.println("Enter new student name:");
		scanner.nextLine();
		String name = scanner.nextLine();

		System.out.println("Enter new student age:");
		int age = scanner.nextInt();

		System.out.println("Enter new student grade:");
		double grade = scanner.nextDouble();

		try {
			Student student = new Student(name, age, grade, nationalId);

			boolean updated = studentService.updateStudent(nationalId, student);

			if (updated) {
				System.out.println("Student updated successfully.");
			} else {
				System.out.println("Student not found.");
			}

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn("Invalid student data: {}", e.getMessage());
		}
	}
	
	private static void deleteStudent() {

		System.out.println("Enter student national ID:");
		String nationalId = scanner.next();

		boolean deleted = studentService.deleteStudent(nationalId);

		if (deleted) {
			System.out.println("Student deleted successfully.");
			logger.info("Student deleted: {}", nationalId);
		} else {
			System.out.println("Student not found.");
			logger.warn("Student not found for deletion: {}", nationalId);
		}
	}
	
	private static void addCourse() {

	    System.out.println("Enter course code:");
	    String courseCode = scanner.next();

	    System.out.println("Enter course name:");
	    scanner.nextLine();
	    String name = scanner.nextLine();

	    System.out.println("Enter course description:");
	    String description = scanner.nextLine();

	    try {
		    Course course = new Course(name, description, courseCode);
		    
	        boolean added = courseService.addCourse(course);

	        if (added) {
	            System.out.println("Course added successfully.");
	        } else {
	            System.out.println("Course was not added.");
	        }

	    } catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn("Invalid course data: {}", e.getMessage());

		} catch (Exception e) {
	        System.out.println("An unexpected error occurred.");
	        logger.error("Error while adding course.", e);
	    }
	}

	private static void showCourses() {

	    try {
	        List<Course> courses = courseService.showCourses();

	        if (courses.isEmpty()) {
	            System.out.println("No courses found.");
	            return;
	        }

	        for (Course course : courses) {
	            System.out.println(course);
	        }

	    } catch (Exception e) {
	        System.out.println("An unexpected error occurred.");
	        logger.error("Error while retrieving courses.", e);
	    }
	}
	
	private static void findCourse() {

	    System.out.println("Enter course code:");
	    String courseCode = scanner.next();

	    try {
	        Course course = courseService.findCourses(courseCode);

	        if (course == null) {
	            System.out.println("Course not found.");
	        } else {
	            System.out.println(course);
	        }

	    } catch (Exception e) {
	        System.out.println("An unexpected error occurred.");
	        logger.error("Error while searching for course.", e);
	    }
	}
	
	
	private static void updateCourse() {

	    System.out.println("Enter course code:");
	    String courseCode = scanner.next();

	    System.out.println("Enter new course name:");
	    scanner.nextLine();
	    String name = scanner.nextLine();

	    System.out.println("Enter new course description:");
	    String description = scanner.nextLine();



	    try {
		    Course course = new Course(name, description, courseCode);
		    
	        boolean updated = courseService.updateCourse(courseCode, course);

	        if (updated) {
	            System.out.println("Course updated successfully.");
	            logger.info("Course updated: {}", courseCode);
	        } else {
	            System.out.println("Course not found.");
	            logger.warn("Course not found for update: {}", courseCode);
	        }

	    } catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn("Invalid course data: {}", e.getMessage());

		} catch (Exception e) {
	        System.out.println("An unexpected error occurred.");
	        logger.error("Error while updating course.", e);
	    }
	}
	
	private static void deleteCourse() {

	    System.out.println("Enter course code:");
	    String courseCode = scanner.next();

	    try {
	        boolean deleted = courseService.deleteCourse(courseCode);

	        if (deleted) {
	            System.out.println("Course deleted successfully.");
	            logger.info("Course deleted: {}", courseCode);
	        } else {
	            System.out.println("Course not found.");
	            logger.warn("Course not found for deletion: {}", courseCode);
	        }

	    } catch (Exception e) {
	        System.out.println("An unexpected error occurred.");
	        logger.error("Error while deleting course.", e);
	    }
	}
	
	
	public static void testConnection() {

		try {
			Connection connection = DBConnection.getConnection();
			logger.info("Database test connection started.");

			System.out.println("Connected to the database successfully.");

			connection.close();
			logger.info("Database test connection closed.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			logger.error("Database test connection failed.", e);
		}
	}

}