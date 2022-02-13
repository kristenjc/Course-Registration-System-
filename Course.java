
import java.io.Serializable;
import java.util.ArrayList;

public class Course extends MainProgram implements Serializable, Comparable<Course>{
	private static final long serialVersionUID = 1L;
	
	private String courseName;
	private String courseID;
	private int maxStudents;
	private int numOfStudents;
	private ArrayList<Student> registeredStudents;
	private String instructor;
	private int section;
	private String location;
	
	//empty course constructor 
	Course()  {
		numOfStudents = 0;
	}
	
	//constructor
	Course(String name, String id, int maxStudents, int numOfStudents ,String instructor, int section, String location )  {
		this.courseName = name;
		this.courseID = id;
		this.maxStudents = maxStudents;
		this.numOfStudents = numOfStudents;
		this.registeredStudents = new ArrayList<Student>();
		this.instructor = instructor;
		this.section = section;
		this.location = location;
		
	}
	
	//Method to return an arraylist of courses that are full 
	public ArrayList<Course> maxCourses()  {
		ArrayList<Course> fullCourses = new ArrayList<Course>();
		for (Course c: getCourses())  {
			if (c.getNumOfStudents() == (c.getMaxStudents()))  {
				fullCourses.add(c);
			}
		}
		return fullCourses;
	}
	
	//Method to return an arraylist of courses that are not full 
	public ArrayList<Course> availableCourses()  {
		ArrayList<Course> availableCourses = new ArrayList<Course>();
		for (Course c: getCourses())  {
			if (c.getNumOfStudents() < (c.getMaxStudents()))  {
				availableCourses.add(c);
			}
		}
		return availableCourses;
	}
	
	//method to add student into arraylist of registered student
	public void addStudent(Student s)  {
		getStudentsRegistered().add(s);
		setNumOfStudents(getNumOfStudents() +1);
	}
	
	//method to remove student from arraylist of registered student
	public void removeStudent(Student s)  {
		getStudentsRegistered().remove(s);
		setNumOfStudents(getNumOfStudents() -1);
	}
	
	//getters and setters for course name
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	//getters and setters for course ID
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	//getters and setters for maximum number of students
	public int getMaxStudents() {
		return maxStudents;
	}
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	
	//getters and setters for number of students in course
	public int getNumOfStudents() {
		return numOfStudents;
	}
	public void setNumOfStudents(int numOfStudents) {
		this.numOfStudents = numOfStudents;
	}
	
	//getter for ArrayList of registered students
	public ArrayList<Student> getStudentsRegistered() {
		return registeredStudents;
	}
	
	//getters and setters for course instructor 
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	//getters and setters for course section
	public int getCourseSect() {
		return section;
	}
	public void setCourseSect(int section) {
		this.section = section;
	}
	
	//getters and setters for course location
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	//Method to compare classes based on the number of currently enrolled students 
	@Override
	public int compareTo(Course c) {
		return this.numOfStudents - c.getNumOfStudents();
	}
	
	//Method to convert Course object to string
	@Override 
	public String toString()  {
		return  " Course name: " + courseName + 
				"\n Course ID: " + courseID + 
				"\n Course Instructor: " + instructor +
				"\n Course section: " + section + 
				"\n Course location: " + location;
	}
		
}
