package app.studentManagmentProject.model;

public class Course {

	private int id;
	private String name;
	private String description;
	private String courseCode;

	// Constructors
	public Course(String courseName, String courseDescription) {
		this.name = courseName;
		this.description = courseDescription;
	}

	public Course(String name, String description, String courseCode) {
		this.name = name;
		this.description = description;
		this.setCourseCode(courseCode);
	}

	public Course(int id, String courseName, String courseDescription) {
		this.id = id;
		this.name = courseName;
		this.description = courseDescription;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCourseCode(String courseCode) {
		if(courseCode == null || !courseCode.matches("[a-zA-Z]{2}\\d{3}")) {
			throw new IllegalArgumentException("Course Code must contain 2 letters followed b 3 digit,  e.g. CS100");
		}
		this.courseCode = courseCode;
	}
	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

	@Override
	public String toString() {
		return "Course Name: " + name + 
				", description: " + description + 
				", courseCode: " + courseCode;
	}
}