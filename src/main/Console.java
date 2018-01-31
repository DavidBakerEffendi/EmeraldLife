/**
 * 
 */
package main;

import java.util.Scanner;

/**
 * @author David Baker Effendi
 * @since 0.1
 */
public class Console {
    /*
     * Menu options:
     */
    public static final int DEFAULT_CODE		= -1;
    public static final int EXIT_CODE 			= 0;
    public static final int PREMIUM_RECONCILIATOR 	= 1;

    public static int generateMainMenu() {
	int option = DEFAULT_CODE;
	String sectionBreak = "";
	Scanner scanner = new Scanner(System.in);
	
	for (int i = 0; i < 100; i++) {
	    sectionBreak = sectionBreak + "=";
	}

	System.out.println(sectionBreak);
	System.out.println("Emerald Life Excel Processor");
	System.out.println(sectionBreak);
	System.out.println("Please select one of the following:\n");
	System.out.println("1. Premium Reconciliator");
	System.out.println("\n0. Exit\n");
	System.out.print("Enter your option: ");

	try {
	    if (scanner.hasNextInt())
		option = scanner.nextInt();
	    else
		scanner.next();
	    while (EXIT_CODE > option || option > PREMIUM_RECONCILIATOR){
		System.out.println("Invalid option, please try again...");
		if (scanner.hasNextInt())
			option = scanner.nextInt();
		    else
			scanner.next();
	    }
	} catch (Exception e) {
	    System.out.println("Error occured while obtaining selection:");
	    e.printStackTrace();
	} finally {
	    scanner.close();
	}

	return option;
    }
}
