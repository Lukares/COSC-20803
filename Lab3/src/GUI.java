import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

public class GUI {

	JFrame frame = new JFrame();
	
	JSpinner spinner = new JSpinner();
	JButton fileButton = new JButton("Get File");
	JLabel fileLabel = new JLabel("*****<Filename>*****");
	JButton bubbleSortBtn = new JButton("Bubble Sort GO");
	JButton cocktailBtn = new JButton("Cocktail Shaker Sort GO");
	JButton shellBtn = new JButton("Shell Sort GO");
	JButton quickBtn = new JButton("Quick Sort GO");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame.setBounds(100, 100, 900, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel titleLabel = new JLabel("Sorting Algorithm Test");
		titleLabel.setFont(new Font("Goudy Stout", Font.PLAIN, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(titleLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel numKeysLabel = new JLabel("Number of Keys to Sort: ");
		numKeysLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numKeysLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		panel_1.add(numKeysLabel);
		
		spinner.setModel(new SpinnerNumberModel(100, 100, 5000, 50));
		panel_1.add(spinner);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		
		JLabel label = new JLabel("Select File to Sort: ");
		label.setFont(new Font("Proxy 1", Font.BOLD, 12));
		panel_4.add(label);
		
		panel_4.add(fileButton);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		
		fileLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		fileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(fileLabel);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(2, 4, 0, 0));
		
		JLabel bsLabel = new JLabel("Bubble Sort: ");
		bsLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		bsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(bsLabel);
		
		JLabel ctssLabel = new JLabel("Cocktail Shaker Sort:");
		ctssLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		ctssLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(ctssLabel);
		
		JLabel ssLabel = new JLabel("Shell Sort: ");
		ssLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		ssLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(ssLabel);
		
		JLabel qsLabel = new JLabel("Quick Sort: ");
		qsLabel.setFont(new Font("Proxy 1", Font.BOLD, 12));
		qsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(qsLabel);
		bubbleSortBtn.setEnabled(false);
		
		panel_6.add(bubbleSortBtn);
		cocktailBtn.setEnabled(false);
		
		panel_6.add(cocktailBtn);
		shellBtn.setEnabled(false);
		
		panel_6.add(shellBtn);
		quickBtn.setEnabled(false);
		
		panel_6.add(quickBtn);
	}

}
