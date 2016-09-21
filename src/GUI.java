import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;

public class GUI {

	JFrame frame = new JFrame();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField pointsTextField = new JTextField();
	
	JTextArea textArea = new JTextArea();
	
	JPanel panel = new JPanel();
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	JPanel panel_3 = new JPanel();
	
	JLabel titleLabel = new JLabel("Texas County Oak Wilt Database");
	JLabel lblPointsExceeding = new JLabel("Points Exceeding:");
	
	
	JButton readInButton = new JButton("Read Tree Data From File");
	JButton createButton = new JButton("Create New Tree Data");
	JButton cancelButton = new JButton("Cancel");
	JButton displayButton = new JButton("Display Tree Data");
	JButton deleteButton = new JButton("Delete Tree");
	
	JRadioButton countyRB = new JRadioButton("By County");
	JRadioButton nameRB = new JRadioButton("By Common Name");
	JRadioButton wiltRB = new JRadioButton("Trees with Oak Wilt");
	JRadioButton pointsRB = new JRadioButton("With points exceeding...");
	
	
	
	
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
		
		frame.setBounds(100, 100, 1097, 676);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Proxy 3", Font.BOLD, 26));
		panel.add(titleLabel);
		
		
		panel.add(panel_1);
		
		
		panel_1.add(readInButton);
		
		
		panel_1.add(createButton);
		
		
		panel_1.add(cancelButton);
		
		
		panel.add(panel_2);
		
		
		panel_2.add(displayButton);
		
		
		buttonGroup.add(countyRB);
		panel_2.add(countyRB);
		
		
		buttonGroup.add(nameRB);
		panel_2.add(nameRB);
		
		
		buttonGroup.add(wiltRB);
		panel_2.add(wiltRB);
		
		
		buttonGroup.add(pointsRB);
		panel_2.add(pointsRB);
		
		
		panel.add(panel_3);
		
		
		panel_3.add(deleteButton);
		
		
		panel_3.add(lblPointsExceeding);
		
		
		panel_3.add(pointsTextField);
		pointsTextField.setColumns(10);
		
		
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		textArea.setText(String.format("%-18s%-19s%-13s%-20s%-13s%-19s%-17s%-18s", "County Name" , "Species Name" , "Points" , "Circumference" , "Height" , "Crown Spread" , "Date Added" , "Contributor"));
		textArea.append("\n______________________________________________________________________________________________________________________________________________");
	}

}
