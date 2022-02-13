package CRS_System;

import java.io.IOException;

public interface StudentInterface {
	//Method to view all courses that are NOT FULL
	public void viewNFull();
	
	//Method to register into a course as a student
	public void registerCourse() throws IOException;
	
	//Method to withdraw from a course 
	public void withdraw();
}
