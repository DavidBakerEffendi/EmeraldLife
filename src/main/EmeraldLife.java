/**
 * 
 */
package main;

import java.io.File;

import javax.swing.JFileChooser;

import premiumReconciliator.PremiumReconciliator;

/**
 * @author David Baker Effendi
 * @since 0.1
 */
public class EmeraldLife {

    private static String SYSTEM_OS	= System.getProperty("os.name");
    private static String LINUX_OS 	= "Linux";
    private static String WINDOWS_OS	= "Windows";
    /**
     * @param args
     */
    public static void main(String[] args) {
	int option = Console.generateMainMenu();

	switch (option) {
	case (Console.DEFAULT_CODE):
	    System.out.println("No valid option collected, exiting...");
	    System.exit(0);
	    break;
	case (Console.EXIT_CODE):
	    System.out.println("Exiting...");
	    System.exit(0);
	    break;
	case (Console.PREMIUM_RECONCILIATOR):
	    File template = null;
	    File target = null;
	    JFileChooser fileChooser = new JFileChooser();
	    
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	    if (SYSTEM_OS.contains(LINUX_OS))
		fileChooser.setCurrentDirectory(new java.io.File(System.getenv("HOME")));
	    else if (SYSTEM_OS.contains(WINDOWS_OS))
		fileChooser.setCurrentDirectory(new java.io.File("C:\\"));

	    fileChooser.setDialogTitle("Select template file");

	    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		template = fileChooser.getSelectedFile();
	    } else {
		System.out.println("Operation cancelled by user, exiting...");
		System.exit(0);
	    }
	    
	    fileChooser.setDialogTitle("Select file to reconcile");

	    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		target = fileChooser.getSelectedFile();
	    } else {
		System.out.println("Operation cancelled by user, exiting...");
		System.exit(0);
	    }

	    PremiumReconciliator.reconcile(template, target);
	    break;
	}
	
	System.exit(0);
    }

}
