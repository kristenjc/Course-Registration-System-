package CRS_System;

import java.io.IOException;

public interface AdminInterface {
	//Course Mangement Actions
	//Method to create new course
	public void createCourse();
	
	//Method to delete course
	public void deleteCourse();
	
	//Method to edit course
	public void editCourse();
	
	//Method to display course 
	public void displayCourse();
	
	//Method to register student 
	public void registerStudent();
	
	//Report Actions
	//Method to view all full courses
	public void viewFULL();
	
	//Method to view students registered in a course
	public void studentsRegistered();
	
	//Method to write to file the list of courses that are full
	public void writeToFile()  throws IOException;
	
}
