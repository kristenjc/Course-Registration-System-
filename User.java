package CRS_System;

public class User extends Course {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String f_name;
	private String l_name;
	
	//empty constructor 
	User()  {
		setUsername("username");
		setPassword("password");
		setFname("first name");
		setLname("last name");
	}

	//get username
	public String getUsername() {
		return username;
	}

	//get password
	public String getPassword() {
		return password;
	}

	//get first name
	public String getF_name() {
		return f_name;
	}

	//get last name
	public String getL_name() {
		return l_name;
	}
	
	//set username
	public void setUsername(String username)  {
		this.username = username;
	}

	//set password
	public void setPassword(String password)  {
		this.password = password;
	}
	
	//Set first name
	public void setFname(String f_name)  {
		this.f_name = f_name;
	}
	
	//Set last name
	public void setLname(String l_name)  {
		this.l_name = l_name;
	}
	
	//Method to view all courses
	public void viewCourses()  {
		System.out.println("View courses.");
		for (Course c: getCourses())  {
			System.out.println("Course name: " +c.getCourseName());
			System.out.println("Course ID:" + c.getCourseID());
			System.out.println("Course instructor: " + c.getInstructor());
			System.out.println("Course section: " + c.getCourseSect());
			System.out.println("Course location: " + c.getLocation());
			System.out.println(" ");
		}
	}
	
	//Method to view a student's list of courses
	public void viewStudentsCourses()  {
		//view student's courses
		System.out.println("View list of student's courses. ");
	}
	
	
	//Method to exit program
	public void exit()  {
		System.out.println("  ");
		System.out.println("Exiting Program...");
		System.out.println(" ");
		serialize();
		System.out.println("Succesfully exit program.");
		System.exit(0);
	}
	
	//Method to find existence of a course when course id is provided 
	public Course findCourse(String courseID)  {
		for (Course c: getCourses())  {
			if (c.getCourseID().equals(courseID))  {
				return c;
			}
		}
		return null;
	}
	
	//Method to check existence of course when course ID and course Section is provided
	public Course findCourse(String courseID, int sect)  {
		for (Course c: getCourses())  {
			if (c.getCourseID().equals(courseID) && c.getCourseSect() == sect)  {
				return c;
			}
		}
		return null;
	}
	
}
