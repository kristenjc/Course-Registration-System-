package CRS_System;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MainProgram {
	private static ArrayList<Student> students = new ArrayList<>();
	private static ArrayList<Course> courses = new ArrayList<>();
	private static User user = new User();

	public static void main(String[] args) throws FileNotFoundException, NumberFormatException, IOException {
		File file1 = new File("Students.ser");
		File file2 = new File("Courses.ser");

		//Check to see if program has been run before
		//If it has, then deserialize content of files, to get course and student data
		//If not, read data from MyUniversityCourse.csv
		if (!file1.exists() || !file2.exists()) {
			readCSV();
			clearAllStudents();
		} else {
			deserialize();
		}

		//call login method
		Login();
	}

	// Login Method
	// Prompts login window, and ensures user input correct username and password
	// Determines whether to run system for an Admin or a Student
	public static void Login() throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		//Prompt welcome message, and determine whether to run ADMIN or STUDENT options
		System.out.println("Welcome to the Univeristy Course Registration System!");
		System.out.println("Are you an ADMIN or STUDENT? \n Enter 1 for Admin \n Enter 2 for Student \n Enter 3 to EXIT");
		int choice = Integer.parseInt(scanner.nextLine());

		while (choice < 1 || choice > 3) {
			System.out.println("Please enter '1' for Admin, '2' for Student or '3' to EXIT program");
			choice = Integer.parseInt(scanner.nextLine());
		}

		switch (choice) {
		case 1:
			//Admin Login Window
			user = new Admin();

			System.out.println("Admin Username: ");
			String username = scanner.nextLine();

			boolean c = false;
			c = user.getUsername().equals(username);

			while (!c) {
				System.out.println("Incorrect Admin username. Please enter username again: ");
				username = scanner.nextLine();
				c = user.getUsername().equals(username);
			}

			System.out.println("Admin Password: ");
			String pwd = scanner.nextLine();

			boolean d = false;
			d = (user.getPassword()).equals(pwd);
			while (!d) {
				System.out.println("Incorrect Admin password. Enter admin password again: ");
				pwd = scanner.nextLine();
				d = (user.getPassword()).equals(pwd);
			}
			
			System.out.println("  ");
			System.out.println("SUCCESSFULLY signed in as an ADMIN!");
			System.out.println("  ");
			AdminActions();
			break;
		case 2:
			//Student Login Window
			//If student already has a student account, make sure they enter valid username and password
			//If not, register a student account
			System.out.println("Alreay have a student account? Enter '1'. ");
			System.out.println("If you are new. Enter '2'. ");
			int choice2 = Integer.parseInt(scanner.nextLine());

			while (choice2 < 1 || choice2 > 2) {
				System.out.println("Invalid entry.");
				System.out.println(" Enter 1 if you already have an account \n Enter 2 to register an account");
				choice2 = Integer.parseInt(scanner.nextLine());
			}
			switch (choice2) {
			case 1: //Running login window for students with an existing account
				System.out.println("Student Username: ");
				String susername1 = scanner.nextLine();
				Boolean e = false;

				//check to make sure valid username is entered
				while (!e) {
					for (Student s : getStudents()) {
						if (s.getUsername().equals(susername1)) {
							user = s;
							e = true;
							break;
						}
					}
					if (!e) {
						System.out.println("Invalid username. ");
						System.out.println("Enter Student username: ");
						susername1 = scanner.nextLine();
					}
				}

				System.out.println("Student password: ");
				String spwd1 = scanner.nextLine();
				Boolean f = false;

				//check to make sure correct password is entered
				while (!f) {
					if (user.getPassword().equals(spwd1)) {
						f = true;
						break;
					}
					System.out.println("Invalid password.");
					System.out.println("Enter Student password: ");
					spwd1 = scanner.nextLine();
				}
				break;
				
			case 2:
				//Login window for students registering a new student account
				
				//Prompt student to set up account
				System.out.println("First name: ");
				String fname = scanner.nextLine();

				System.out.println("Last name: ");
				String lname = scanner.nextLine();

				System.out.println("Student Username: ");
				String susername2 = scanner.nextLine();

				System.out.println("Password: ");
				String pwd2 = scanner.nextLine();

				Student student = new Student(susername2, pwd2, fname, lname);

				//Add student into the ArrayList of all students
				students.add(student);

				user = student;
				break;
			}
		default: //Run Student Actions 
			System.out.println(" ");
			StudentActions();
			break;
			
		case 3:
			//Exit program
			user.exit();
		}

	}

	//Method to determine what type of Admin Actions to run 
	public static void AdminActions() throws IOException {
		//Prompt Admin for their choice of action 
		Scanner scanner = new Scanner(System.in);
		System.out.println("What do you want to do? (Enter option number)");
		System.out.println(" 1. Course Management \n 2. Reports \n 3. Exit");
		int choice = Integer.parseInt(scanner.nextLine());

		while (choice < 1 || choice > 3) {
			System.out.println("Invalid choice! Enter again.");
			System.out.println("1. Course Management \n 2. Reports \n 3. Exit");
			choice = Integer.parseInt(scanner.nextLine());
		}

		switch (choice) {
		case 1:
			//Call CCourse Management method
			CCourseManagement();
			break;
		case 2:
			//Call CReports method
			CReports();
			break;
		case 3:
			//Exit program
			user.exit();
		}
		System.out.println(" ");
		AdminActions();
	}

	//Method to run any action under Course Management for Admins
	public static void CCourseManagement() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What do you want to do? (Enter option number)");
		System.out.println("1. Create a new course");
		System.out.println("2. Delete a course");
		System.out.println("3. Edit a course");
		System.out.println("4. Display information for a given course");
		System.out.println("5. Register a student");
		System.out.println("6. Exit");
		int choice = Integer.parseInt(scanner.nextLine());

		while (choice < 1 || choice > 6) {
			System.out.println("Invalid choice! Enter again.");
			System.out.println("What do you want to do? (Enter option number)");
			System.out.println("1. Create a new course");
			System.out.println("2. Delete a course");
			System.out.println("3. Edit a course");
			System.out.println("4. Display information for a given course");
			System.out.println("5. Register a student");
			System.out.println("6. Exit");
			choice = Integer.parseInt(scanner.nextLine());
		}

		switch (choice) {
		case 1:
			//Call method that allows admin to create course 
			((Admin) user).createCourse();
			break;
		case 2:
			//Call method that allows admin to delete course
			((Admin) user).deleteCourse();
			break;
		case 3:
			//Call method that allows admin to edit course
			((Admin) user).editCourse();
			break;
		case 4:
			//call method that allows admin to display information of a specific course
			((Admin) user).displayCourse();
			break;
		case 5:
			//call method that allows admin to register student
			((Admin) user).registerStudent();
			break;
		case 6:
			//call method to exit program
			user.exit();
		}
	}

	//Method to run any action under Reports for Admins
	public static void CReports() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What do you want to do? (Enter option number)");
		System.out.println("1. View all courses");
		System.out.println("2. View all FULL courses");
		System.out.println("3. Write to a file courses that are FULL");
		System.out.println("4. View students registered in a specific course");
		System.out.println("5. View courses a given student is registered in");
		System.out.println("6. Sort courses (based on current number of registered students");
		System.out.println("7. EXIT");
		int choice = Integer.parseInt(scanner.nextLine());

		while (choice < 1 || choice > 7) {
			System.out.println("Invalid choice! Enter again.");
			System.out.println(" ");
			System.out.println("What do you want to do? (Enter option number)");
			System.out.println("1. View all courses");
			System.out.println("2. View all FULL courses");
			System.out.println("3. Write to a file courses that are FULL");
			System.out.println("4. View students registered in a specific course");
			System.out.println("5. View courses a given student is registered in");
			System.out.println("6. Sort courses (based on current number of registered students");
			System.out.println("7. EXIT");
			choice = Integer.parseInt(scanner.nextLine());
		}

		switch (choice) {
		case 1:
			//calls method that allows admin to view all courses
			user.viewCourses();
			break;
		case 2:
			//calls method that allows admin to view FULL courses
			((Admin) user).viewFULL();
			break;
		case 3:
			//calls method that allows admin to write to a file the list of courses that are full
			((Admin) user).writeToFile();
			break;
		case 4:
			((Admin) user).studentsRegistered();
			break;
		case 5:
			//view enrolled courses of a specific student
			user.viewStudentsCourses();
			break;
		case 6:
			//sort courses based on the current number of students registered
			((Admin) user).sort();
			break;
		case 7:
			((Admin) user).exit();
		default:
			AdminActions();
		}
	}

	//Method prompting window of student action options 
	public static void StudentActions() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What do you want to do? (Enter option number)");
		System.out.println("1. View all courses");
		System.out.println("2. View all available courses");
		System.out.println("3. Register on a course");
		System.out.println("4. Withdraw from a course");
		System.out.println("5. View all registered courses");
		System.out.println("6. Exit");
		int choice = Integer.parseInt(scanner.nextLine());

		while (choice < 1 || choice > 6) {
			System.out.println("Invalid choice! Enter again.");
			System.out.println("What do you want to do? (Enter option number)");
			System.out.println("1. View all courses");
			System.out.println("2. View all available courses");
			System.out.println("3. Register on a course");
			System.out.println("4. Withdraw from a course");
			System.out.println("5. View all registered courses");
			System.out.println("6. Exit");
			choice = Integer.parseInt(scanner.nextLine());
		}

		switch (choice) {
		case 1:
			//call method allowing students to view all courses
			((Student) user).viewCourses();
			break;
		case 2:
			//call method allowing students to view all courses that are not full
			((Student) user).viewNFull();
			break;
		case 3:
			//call method allowing student to register in a course
			((Student) user).registerCourse();
			System.out.println("Done registering course!");
			System.out.println("");
			break;
		case 4:
			//call method allowing student to withdraw from course
			((Student) user).withdraw();
			break;
		case 5:
			//Call method allowing student to view all of their registered courses
			((Student)user).viewStudentsCourses();
			System.out.println("");
			break;
		case 6:
			//Exit program
			user.exit();
		default: 
			break;
		}
		StudentActions();
	}

	//Method to get ArrayList of students
	public static ArrayList<Student> getStudents() {
		return students;
	}

	//Method to clear all students in the ArrayList of students
	public static ArrayList<Student> clearAllStudents() {
		for (Student s : getStudents()) {
			getStudents().remove(s);
		}
		return students;
	}

	//Method to get ArrayList of courses
	public static ArrayList<Course> getCourses() {
		return courses;
	}


	//Method to read CSV file containing university courses information
	public static void readCSV() throws FileNotFoundException {
		String path = "MyUniversityCourses.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				Course newCourse = new Course(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
						data[5], Integer.parseInt(data[6]), data[7]);
				courses.add(newCourse);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Method to deserialize both ArrayList of courses and ArrayList of students
	public static void deserialize() {
		try {
			FileInputStream fisC = new FileInputStream("Courses.ser");
			FileInputStream fisS = new FileInputStream("Students.ser");

			ObjectInputStream oisC = new ObjectInputStream(fisC);
			ObjectInputStream oisS = new ObjectInputStream(fisS);

			courses = (ArrayList<Course>) oisC.readObject();
			students = (ArrayList<Student>) oisS.readObject();

			oisC.close();
			oisS.close();

			fisC.close();
			fisS.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Method to serialize both ArrayList of Courses and ArrayList of Students
	public static void serialize() {
		try {
			System.out.println("SAVING CHANGES...");
			FileOutputStream fosC = new FileOutputStream("Courses.ser");
			FileOutputStream fosS = new FileOutputStream("Students.ser");

			ObjectOutputStream oosC = new ObjectOutputStream(fosC);
			ObjectOutputStream oosS = new ObjectOutputStream(fosS);

			oosC.writeObject(courses);
			oosS.writeObject(students);

			oosC.close();
			oosS.close();

			fosC.close();
			fosS.close();

			System.out.println("SUCCESFULLY saved changes!");
			System.out.println(" ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}