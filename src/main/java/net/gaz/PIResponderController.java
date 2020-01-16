package net.gaz;



public class PIResponderController {
	
	public static void main(String[] args) {
		
		String dateString = "1999-1-3";
		
		if (dateString.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
			System.out.println("yes matches");
		} else {
			System.out.println("does not match");
		}
	}
}
