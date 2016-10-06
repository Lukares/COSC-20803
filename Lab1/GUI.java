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
import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.CompoundBorder;
import javax.swing.JComboBox;

public class GUI {

	JFrame frame = new JFrame();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JTextField pointsTextField = new JTextField();
	
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
	
	JScrollPane jsp = new JScrollPane();
	DefaultListModel model = new DefaultListModel();
	final JList list = new JList(model);
	final JComboBox comboBox = new JComboBox();
	final JComboBox comboBox_1 = new JComboBox();
	final JButton btnDisplayAllTrees = new JButton("Display All Trees");
			
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
		
		frame.setBounds(100, 100, 1265, 716);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Proxy 3", Font.BOLD, 26));
		panel.add(titleLabel);
		
		
		panel.add(panel_1);
		
		
		panel_1.add(readInButton);
		createButton.setEnabled(false);
		
		
		panel_1.add(createButton);
		
		
		panel_1.add(cancelButton);
		
		
		panel.add(panel_2);
		btnDisplayAllTrees.setEnabled(false);
		
		panel_2.add(btnDisplayAllTrees);
		displayButton.setEnabled(false);
				
		panel_2.add(displayButton);				
		buttonGroup.add(countyRB);
		countyRB.setEnabled(false);
		panel_2.add(countyRB);		
		comboBox.setEnabled(false);
		
		panel_2.add(comboBox);
		buttonGroup.add(nameRB);
		nameRB.setEnabled(false);
		panel_2.add(nameRB);	
		comboBox_1.setEnabled(false);
		
		panel_2.add(comboBox_1);
		buttonGroup.add(wiltRB);
		wiltRB.setEnabled(false);
		panel_2.add(wiltRB);		
		buttonGroup.add(pointsRB);
		pointsRB.setEnabled(false);
		panel_2.add(pointsRB);		
		panel.add(panel_3);		
		deleteButton.setEnabled(false);
		panel_3.add(deleteButton);				
		panel_3.add(lblPointsExceeding);	
		pointsTextField.setEnabled(false);
		panel_3.add(pointsTextField);
		pointsTextField.setColumns(10);
		frame.getContentPane().add(jsp, BorderLayout.CENTER);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		jsp.setViewportView(list);
	}

}
