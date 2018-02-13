/**
 * 
 */
package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author David Baker Effendi
 * @since 0.1
 */
public class GUI {
    private static int option = 0;
    private static boolean selected = false;

    /*
     * Menu options:
     */
    public static final int DEFAULT_CODE = -1;
    public static final int EXIT_CODE = 0;
    public static final int PREMIUM_RECONCILIATOR = 1;

    /*
     * Buttons:
     */
    private static JButton premReconButton = new JButton("Premium Reconciliator");
    private static JButton exitButton = new JButton("Exit");

    public GUI() {
	option = DEFAULT_CODE;
	// Create GUI here. Store option.
	JFrame hostFrame = new JFrame("Emerald Life");
	JPanel hostPanel = new JPanel();
	JPanel optionsPanel = new JPanel();
	JPanel exitPanel = new JPanel();

	JLabel headingLabel = new JLabel("Emerald Life Tools");
	headingLabel.setFont(new Font("ARIAL", Font.BOLD, 20));
	JLabel optionsLabel = new JLabel("Please select an option from below:");

	premReconButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		selected = true;
		option = PREMIUM_RECONCILIATOR;
	    }
	});
	exitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		selected = true;
		option = EXIT_CODE;
	    }
	});

	hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.PAGE_AXIS));
	optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
	exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));

	hostPanel.add(headingLabel);
	// hostPanel.add(Box.createRigidArea(new Dimension(0, 15)));

	optionsPanel.add(optionsLabel);
	optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	optionsPanel.add(premReconButton);

	exitPanel.add(Box.createRigidArea(new Dimension(10, 5)));
	exitPanel.add(exitButton);

	optionsPanel.add(Box.createVerticalGlue());

	hostPanel.add(optionsPanel);
	hostPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	hostPanel.add(exitPanel);
	hostPanel.add(Box.createRigidArea(new Dimension(0, 5)));

	hostFrame.add(hostPanel);
	hostFrame.setSize(300, 300);
	hostFrame.setLocationRelativeTo(null);
	hostFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	hostFrame.setVisible(true);
    }

    public int getOption() {
	return option;
    }

    public boolean hasClicked() {
	return selected;
    }

    public void resetOptions() {
	selected = false;
    }

    @Deprecated
    public int getConsoleOption() {
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
	    while (EXIT_CODE > option || option > PREMIUM_RECONCILIATOR) {
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
