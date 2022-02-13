
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements StudentInterface, Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Course> enrolledCourses = new ArrayList<>();

	//empty student constructor
	public Student()  {
		
	}
	
	//constructor to set username, password, first name, last name
	public Student(String username, String password, String f_name, String l_name)  {
		setUsername(username);
		setPassword(password);
		setFname(f_name);
		setLname(l_name);
	}
	
	//constructor to set first name and last name
	public Student(String f_name, String l_name)  {
		setFname(f_name);
		setLname(l_name);
	}
	
	//Method to view all courses
	//Overrides viewCourses() in User
	@Override
	public void viewCourses() {
		for (Course c: getCourses())  {
			System.out.println("Course name: " +c.getCourseName());
			System.out.println("Course ID:" + c.getCourseID());
			System.out.println("Course instructor: " + c.getInstructor());
			System.out.println("Course section: " + c.getCourseSect());
			System.out.println("Course location: " + c.getLocation());
			System.out.println(" ");
		}
	}

	//Method to view all available courses
	//Overrides viewNFull() in Student Interface
	@Override
	public void viewNFull() {
		for (Course c: getCourses())  {
			if(c.getNumOfStudents() < c.getMaxStudents())  {
				System.out.println("Course name: " +c.getCourseName());
				System.out.println("Course ID:" + c.getCourseID());
				System.out.println("Course instructor: " + c.getInstructor());
				System.out.println("Course section: " + c.getCourseSect());
				System.out.println("Course location: " + c.getLocation());
				System.out.println(" ");
			}
		}
	}

	//Method to register course when course is known 
	public void registerCourse(Course course) {
		if (course.getMaxStudents() == course.getNumOfStudents())  {
			System.out.println("FAILED to register, course is already FULL.");
		}  else  {
			System.out.println("Registering to " + course.getCourseName() + " Section " + course.getCourseSect());
			getRegisteredC().add(course);
			course.addStudent(this);
			System.out.println("SUCCESSFULLY registered to " + course.getCourseName() + " Section " + course.getCourseSect());
		}
	}
	
	//Method to registering course, and prompts student for necessary course or student info 
	//Overrides registerCourse() in Student Interface
	@Override
	public void registerCourse()  throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter course name: ");
		String name = reader.readLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(name) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(reader.readLine());
				
				while (!foundSect)  {
					if (findCourse(name,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(name, sect);
						registerCourse(course);
						System.out.println("  ");
					} else  {
						System.out.println("Course section not found. Enter valid course section: ");
						sect = Integer.parseInt(reader.readLine());
					} 	
				}
			}  else {
				System.out.println("Course name not found. Enter a valid course name: ");
				name = reader.readLine();
			}
		}
	}

	
	//Method to withdraw from course, when course info is known 
	//overloading withdraw method
	public void withdraw(Course course) {
		if (getRegisteredC().contains(course))  {
			//remove course from student's enrolled course list
			getRegisteredC().remove(course);
			//remove student from course's list of enrolled student
			course.getStudentsRegistered().remove(this);
			course.setNumOfStudents(getNumOfStudents()-1);
			System.out.println("SUCCESFULLY withdrawn from " + course.getCourseName() + " Section " + course.getCourseSect());
			System.out.println(" ");
		} else  {
			System.out.println("Student not registered in " + course.getCourseName() + " Section " + course.getCourseSect());
			System.out.println("FAILED to withdraw from " + course.getCourseName() + " Section " + course.getCourseSect());
			System.out.println(" ");
		}
	}
	
	@Override
	//Method to withdraw from course, and prompts student for necessary course info 
	//Overrides withdraw() in Student Interface
	public void withdraw()  {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter first name: ");
		String f_name = scanner.nextLine();
		System.out.println("Enter last name: ");
		String l_name = scanner.nextLine();
		
		System.out.println("Enter course name: ");
		String name = scanner.nextLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(name) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(scanner.nextLine());
				
				while (!foundSect)  {
					if (findCourse(name,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(name, sect);
						withdraw(course);
					} else  {
						System.out.println("Course section not found. Enter valid course section: ");
						sect = Integer.parseInt(scanner.nextLine());
					} 	
				}
			}  else {
				System.out.println("Course name not found. Enter a valid course name: ");
				name = scanner.nextLine();
			}
		}
	}

	//Method to get ArrayList of student's registered courses
	public ArrayList<Course> getRegisteredC() {
		return enrolledCourses;
	}
	
	//Method to view student's registerd courses
	//Overrides viewStudentsCourses() from User super class
	@Override
	public void viewStudentsCourses()  {
		System.out.println("List of Registered courses: ");
		for (Course c: getRegisteredC())  {
			System.out.print(c.toString());
			System.out.println("\n");
		}
	}
	
	//Method to check existence of course, given the course name
	//Overrides findCourse(String courseID) from User super class
	@Override
	public Course findCourse(String courseName)  {
		for (Course c: getCourses())  {
			if (c.getCourseName().equals(courseName))  {
				return c;
			}
		}
		return null;
	}
	
	//Method to check existence of course, given the course name and couse section 
	//Overrides findCourse(String courseID, int sect) from User super class
	@Override
	public Course findCourse(String courseName, int sect)  {
		for (Course c: getCourses())  {
			if (c.getCourseName().equals(courseName) && c.getCourseSect() == sect)  {
				return c;
			}
		}
		return null;
	}
	
	//Method converting student object to string
	public String toString()  {
		return this.getF_name() + " " + this.getL_name();
	}
	
	//Method to exit program
	public void exit()  {
		super.exit();
	}
	

}
