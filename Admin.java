import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Admin extends User implements AdminInterface {
	//empty constructor
	Admin() {
		setUsername("Admin");
		setPassword("Admin001");
		setFname("N/A");
		setLname("N/A");
	}

	//constructor that takes username and password
	Admin(String username, String pwd)  {
		setUsername(username);
		setPassword(pwd);
		setFname("N/A");
		setLname("N/A");
	}
	
	//Method to create course
	@Override
	public void createCourse() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter course name: ");
		String name = scanner.nextLine();
		
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		System.out.println("Maximum number of student in course: ");
		int max = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Current number of student in course: ");
		int numOfStud = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Name of course instructor: ");
		String instructor = scanner.nextLine();
		
		System.out.println("Course section number: ");
		int section = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Course Location: ");
		String location = scanner.nextLine();
		
		Course newCourse = new Course(name, id, max, numOfStud,instructor, section, location );
		
		getCourses().add(newCourse);
		
		System.out.println("To register students into the new course, do so in the main menu. ");
		System.out.println(name + " has successfully been added to the university system.");
	}

	//Method to delete a course
	@Override
	public void deleteCourse() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(id) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(scanner.nextLine());
				
				while (!foundSect)  {
					if (findCourse(id,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(id, sect);
						System.out.println("Deleting course IN PROGRESS...");
						System.out.println("Deleting course from student's registered course list...");
						for (Student s: getStudents())  {
							s.withdraw(course);
						}
						getCourses().remove(course);
						System.out.println("SUCCESSFULLY deleted course from system.");
					} else  {
						System.out.println("Course section not found. Enter valid course section: ");
						sect = Integer.parseInt(scanner.nextLine());
					}
				}
			}  else {
				System.out.println("Course ID not found. Enter a valid course ID: ");
				id = scanner.nextLine();
			}
		}
	}

	//Method to edit course information
	@Override
	public void editCourse() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(id) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(scanner.nextLine());
				
				while (!foundSect)  {
					if (findCourse(id,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(id, sect);
						
						System.out.println("What would you like to edit?");
						System.out.println("1. Maximum number of students allowed to register");
						System.out.println("2. Current number of students registered");
						System.out.println("3. List of students registered in course ");
						System.out.println("4. Course instructor");
						System.out.println("5. Course section");
						System.out.println("6. Course location");
						int choice = Integer.parseInt(scanner.nextLine());
						
						while (choice < 1 || choice > 6)  {
							System.out.println("Invalid choice! Enter again.");
							System.out.println("1. Maximum number of students registered");
							System.out.println("2. Current number of students registered");
							System.out.println("3. List of students registered in course ");
							System.out.println("4. Course instructor");
							System.out.println("5. Course section");
							System.out.println("6. Course location");
							choice = Integer.parseInt(scanner.nextLine());	
						}
						
						switch(choice)  {
						case 1: 
							System.out.println("Enter new maximum number of students: ");
							int max = Integer.parseInt(scanner.nextLine());
							course.setMaxStudents(max);
							break;
						case 2:
							System.out.println("Enter new current number of students registered: ");
							int num = Integer.parseInt(scanner.nextLine());
							course.setNumOfStudents(num);
							break;
						case 3:
					
							System.out.println("Enter student first name: ");
							String f_name = scanner.nextLine();
							System.out.println("Enter student last name: ");
							String l_name = scanner.nextLine();
							
							Student s = new Student();
							
							boolean foundStudent = false;
							while (!foundStudent){
								if (findStudent(f_name, l_name) != null)  {
									foundStudent = true;
									s = findStudent(f_name, l_name);
								} else  {
									System.out.println("Student does not exist. Please enter the name of an already registered student");
									System.out.println("Enter student first name: ");
									f_name = scanner.nextLine();
									System.out.println("Enter student last name: ");
									l_name = scanner.nextLine();
								}
							}
							System.out.println(" ");
							System.out.println("Do you want to add or remove student from list of students? ");
							System.out.println(" 1. Add \n 2. Remove");
							int choice2 = Integer.parseInt(scanner.nextLine());	
							
							switch (choice2)  {
								case 1:
									registerStudent(s,course);
									break;
								case 2: 
									removeStudent(f_name,l_name,course);
									break;
							}
							break;
						case 4: 
							System.out.println("Enter new course instructor: ");
							String instructor = scanner.nextLine();
							course.setInstructor(instructor);
							break;
						case 5:
							System.out.println("Enter new course section: ");
							int section = Integer.parseInt(scanner.nextLine());
							course.setCourseSect(section);
							break;
						case 6:
							System.out.println("Enter new course location: ");
							String location = scanner.nextLine();
							course.setLocation(location);
						}
					} else  {
						System.out.println("Course section not found. Enter a valid course section: ");
						sect = Integer.parseInt(scanner.nextLine());
					}
				}
			}  else {
				System.out.println("Course ID not found. Enter a valid course ID: ");
				id = scanner.nextLine();
			}
		}
	}

	//Method to remove student, given first and last name, and a course object
	public void removeStudent(String f_name, String l_name, Course course)  {
		Student student = findStudent(f_name, l_name, course);
		if (student!= null)  {
			//remove student from the course's list of registered student 
			(course.getStudentsRegistered()).remove(student);
			//remove this course from the student's list of registered course
			(student.getRegisteredC()).remove(course);
		} else {
			System.out.println("Student is not registered in " + course.getCourseName() + " section " + course.getCourseSect());
		}
	}
	
	//overloading
	//method to find whether or not student is registered in a course
	public Student findStudent(String f_name, String l_name, Course course)  {
		for (Student s: course.getStudentsRegistered())  {
			if ((s.getF_name()).equals(f_name) && (s.getL_name()).equals(l_name))  
				return s;
		}
		return null;
	}
	
	//Method to find if student exists in the ArrayList of ALL students, given first and last name
	//overloading
	public Student findStudent(String f_name, String l_name)  {
		for (Student s: getStudents())  {
			if ((s.getF_name()).equals(f_name) && (s.getL_name()).equals(l_name))  
				return s;
		}
		return null;
	}
	
	//Method to display information of a specific course
	@Override
	public void displayCourse() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(id) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(scanner.nextLine());
				
				while (!foundSect)  {
					if (findCourse(id,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(id, sect);
						System.out.println("Course name: " +course.getCourseName());
						System.out.println("Course ID:" + course.getCourseID());
						System.out.println("Maximum number of Students registered: " + course.getMaxStudents());
						System.out.println("Current number of students: " + course.getNumOfStudents());
						System.out.println("List of students registed: " + course.getStudentsRegistered().toString());
						System.out.println("Course instructor: " + course.getInstructor());
						System.out.println("Course section: " + course.getCourseSect());
						System.out.println("Course location: " + course.getLocation());
						System.out.println(" ");
					} else  {
						System.out.println("Course section not found. Enter valid course section: ");
						sect = Integer.parseInt(scanner.nextLine());
					} 	
				}
			}  else {
				System.out.println("Course ID not found. Enter a valid course ID: ");
				id = scanner.nextLine();
			}
		}
	}
		
	//Method to register student to system, and prompts admin for necessary information
	@Override
	public void registerStudent() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter new student's first name: "); 
		String f_name = scanner.nextLine();
		System.out.println("Enter new student's last name: ");
		String l_name = scanner.nextLine();
		
		System.out.println("Enter a username for new student: ");
		String username = scanner.nextLine();
		
		System.out.println("Enter a password for new student: ");
		String pwd = scanner.nextLine();
		
		Student newStudent = new Student(username, pwd, f_name, l_name);
		
		System.out.println("Student successfully registered to system.");
		System.out.println("Do you want to assign the new student to a course? ");
		System.out.println(" 1. Yes \n 2. No");
		int choice = Integer.parseInt(scanner.nextLine());
		
		while (choice < 1 || choice > 2)  {
			System.out.println("Invalid choice! Enter again.");
			System.out.println("Do you want to assign the new student to a course? ");
			System.out.println(" 1. Yes \n 2. No");
			choice = Integer.parseInt(scanner.nextLine());	
		}
		
		switch (choice)  {
			case 1: 
				System.out.println("Enter course ID: ");
				String id = scanner.nextLine();
				
				boolean foundCourse = false;
				
				while (!foundCourse) {
					if (findCourse(id) != null)  {
						foundCourse = true;
						boolean foundSect = false;
						
						System.out.println("Enter course section: ");
						int sect = Integer.parseInt(scanner.nextLine());
						
						while (!foundSect)  {
							if (findCourse(id,sect)!= null)  {
								foundSect = true;
								Course course = findCourse(id, sect);
								registerStudent(newStudent, course);
							} else  {
								System.out.println("Course section not found. Enter valid course section: ");
								sect = Integer.parseInt(scanner.nextLine());
							}
						}
					}  else {
						System.out.println("Course ID not found. Enter a valid course ID: ");
						id = scanner.nextLine();
					}
				}
				break;
			case 2:
				System.out.println("Registering student to system.");
				registerStudent(newStudent);
				break;
		}
		
	}
	
	//Method to register student into system, without assigning to a specific course
	public void registerStudent(Student s)  {
		getStudents().add(s);
		System.out.println(s.getF_name() + s.getL_name() + " succesfully registered to system.");
	}
	
	//Method to register student to a specific course
	public void registerStudent(Student s, Course course)  {
		getStudents().add(s);
		s.registerCourse(course);
	}

	//Method to view all courses 
	//Overrides viewCourses() in User super class
	@Override
	public void viewCourses() {
		for (Course c: getCourses())  {
			System.out.println("Course name: " +c.getCourseName());
			System.out.println("Course ID:" + c.getCourseID());
			System.out.println("Maximum number of Students registered: " + c.getMaxStudents());
			System.out.println("Current number of students: " + c.getNumOfStudents());
			System.out.println("List of students registed: " + c.getStudentsRegistered().toString());
			System.out.println("Course instructor: " + c.getInstructor());
			System.out.println("Course section: " + c.getCourseSect());
			System.out.println("Course location: " + c.getLocation());
			System.out.println(" ");
			
		}
	}
	
	//Method to view all courses that are full 
	@Override
	public void viewFULL() {
		System.out.println("List of courses that are FULL: ");
		System.out.println(" ");
		for (Course c: getCourses())  {
			if(c.getNumOfStudents() >= c.getMaxStudents())  {
				System.out.println(c.toString());
				System.out.println("  ");
			}
		}
	}
	
	//Method to write to a file the list of courses that are full 
	public void writeToFile()  throws IOException  {
		PrintWriter pw = null;
        FileOutputStream fo = null;
        File file = null;
        try {
        	file = new File("FullCourses.txt");
        	pw = new PrintWriter(new FileOutputStream(file));
            fo = new FileOutputStream(file);
            pw.write("List of FULL courses: ");
            pw.write("\n");
            for (Course c: getCourses())  {
    			if(c.getNumOfStudents() >= c.getMaxStudents())  {
    				pw.write(c.toString());
    				pw.write("\n");
    				pw.write("\n");
    			}
    		}
		} finally { 
			pw.flush();
			pw.close();
			fo.close();
			System.out.println("FINISHED writing to file.");
			System.out.println(" ");
;		}
			
	}

	//Method to view all students registered in a specific course
	@Override
	public void studentsRegistered() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		boolean foundCourse = false;
		
		while (!foundCourse) {
			if (findCourse(id) != null)  {
				foundCourse = true;
				boolean foundSect = false;
				
				System.out.println("Enter course section: ");
				int sect = Integer.parseInt(scanner.nextLine());
				
				while (!foundSect)  {
					if (findCourse(id,sect)!= null)  {
						foundSect = true;
						Course course = findCourse(id, sect);
						System.out.println("List of students registered in " + course.getCourseName() + " section " + course.getCourseSect());
						System.out.println(course.getStudentsRegistered().toString());
					} else  {
						System.out.println("Course section not found. Enter valid course section: ");
						sect = Integer.parseInt(scanner.nextLine());
					}
				}
			}  else {
				System.out.println("Course ID not found. Enter a valid course ID: ");
				id = scanner.nextLine();
			}
		}
		
	}
	
	//Method to view a specific student's registered courses
	//Overrides viewStudentsCourses() in User super class
	@Override
	public void viewStudentsCourses()  {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter student first name: ");
		String f_name = scanner.nextLine();
		
		System.out.println("Enter student last name: ");
		String l_name = scanner.nextLine();
		
		boolean foundStudent = false;
		
		while(!foundStudent)  {
			if (findStudent(f_name, l_name) != null)  {
				foundStudent = true;
				Student s = findStudent(f_name, l_name);
				System.out.println("List of courses that " + s.getF_name() + " " + s.getL_name() + " is registered in: ");
				for (Course c: s.getRegisteredC())  {
					System.out.println(c.toString());
					System.out.println(" ");
				} 
			} else  {
				System.out.println("Student does not exist. Please enter the name of an already registered student");
				System.out.println("Enter student first name: ");
				f_name = scanner.nextLine();
				System.out.println("Enter student last name: ");
				l_name = scanner.nextLine();
			}
		}
	}
	
	//Method to sort courses by the current number of students registered
	public void sort()  {
		System.out.println("Sorting courses by the current nubmer of students registered...");
		Collections.sort(getCourses());
		System.out.println("Sorting complete.");
	}
}
