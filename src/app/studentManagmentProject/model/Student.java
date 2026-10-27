package app.studentManagmentProject.model;

public class Student {
private int id;
private String name;
private int age;
private double grade;
private String nationalId;

//constructors 
public Student(String name, int age, double grade) {
	this.name = name;
	this.age= age;
	this.grade=grade;
}

public Student(String name, int age, double grade, String nationalId) {
	this.name= name;
	this.age= age;
	this.grade =grade;
	this.setNationalId(nationalId);
	
}

public Student(int id, String name, int age, double grade) {
	this.id= id;
	this.name= name;
	this.age= age;
	this.grade= grade;
}

public void setId(int id) {
	this.id= id;
}

public void setName(String name) {
	this.name= name;
	
}

public void setAge(int age) {
	this.age= age;
}

public void setGrade(double grade) {
	this.grade =grade;
}

public void setNationalId(String nationalId) {
	if(nationalId ==null || nationalId.isEmpty()) {
		throw new IllegalArgumentException("National ID is required");
	}
	if (nationalId.length()!= 10) {
		throw new IllegalArgumentException("National ID must contain 10 digit");
	}
	if (nationalId.charAt(0) != '1' && nationalId.charAt(0) != '2') {
		throw new IllegalArgumentException("National ID must start with 1 or 2");
	}
	this.nationalId = nationalId;
}

public int getId() {
	return id;
}

public String getName() {
	return name;
}

public int getAge() {
	return age;
}

public double getGrade() {
	return grade;
}

public String getNationalId() {
	return nationalId;
}

public String studentInfo() {
	return "Student National ID: " + this.nationalId
			+ ", Name: " + this.name
			+ ", Age: " + this.age
			+ ", Grade: "+ this.grade
			+ ", Grade Level: "+ getGradeLevel()
			+", Status: "+ getPassFailStatus();		
}
public String getPassFailStatus() {
	if (this.grade >= 60){
		return "Pass";
	}
	else {
		return "Failed";
	}
	
}

public String getGradeLevel() {
	if(this.grade >= 90) 
		return "Excellent";
	else if (this.grade >=80)
		return "Very Good";
	else if (this.grade >70) 
		return "Good";
	else if(this.grade >=60)
		return "Pass";
	else 
		return "Fail";		
}



}
