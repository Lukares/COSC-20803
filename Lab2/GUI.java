import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class GUI {

	JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
	JRadioButton levelRdBtn = new JRadioButton("In Level-Order");
	JRadioButton reverseRdBtn = new JRadioButton("In \"Reverse Preorder\"");
	JRadioButton normalRdBtn = new JRadioButton("In Normal Preorder");
	JButton displayDataButton = new JButton("Display Tributary Data");
	JRadioButton rdbtnSupplyingTributaries = new JRadioButton("Supplying Tributaries");
	JRadioButton rdbtnAllImmediateTributaries = new JRadioButton("All Immediate Tributaries");
	JRadioButton flowRdBtn = new JRadioButton("Trace to Destination");
	JButton discoverButton = new JButton("Discover Data");
	JRadioButton rdbtnRename = new JRadioButton("Rename");
	JRadioButton rdbtnDelete = new JRadioButton("Delete ");
	JRadioButton rdbtnAddNew = new JRadioButton("Add New");
	JButton editButton = new JButton("Edit Tributary");
	JButton inputButton = new JButton("Input Tributary System");
	JLabel lblNewLabel = new JLabel("National Water Tributary Systems Database");
	
	JTextArea textArea = new JTextArea();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	final JLabel fileLbl = new JLabel("<Filename>");
	final JButton exitButton = new JButton("Exit Water Database");
	final JComboBox discoverCBB = new JComboBox();
	final JComboBox locationCBB = new JComboBox();
	final JComboBox locCBB = new JComboBox();

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
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 10));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(5, 0, 0, 0));
		

		lblNewLabel.setFont(new Font("Txt_IV50", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_1);
				
		panel_1.add(inputButton);
		
		panel_1.add(fileLbl);
		
		panel_1.add(exitButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2);
		
		editButton.setEnabled(false);
		panel_2.add(editButton);	

		buttonGroup_1.add(rdbtnAddNew);
		rdbtnAddNew.setEnabled(false);
		panel_2.add(rdbtnAddNew);	

		buttonGroup_1.add(rdbtnDelete);
		rdbtnDelete.setEnabled(false);
		panel_2.add(rdbtnDelete);
		
		buttonGroup_1.add(rdbtnRename);
		rdbtnRename.setEnabled(false);
		panel_2.add(rdbtnRename);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(panel_3);
		
		discoverButton.setEnabled(false);
		panel_3.add(discoverButton);	
		locCBB.setEnabled(false);
		
		panel_3.add(locCBB);
		discoverCBB.setEnabled(false);
		
		panel_3.add(discoverCBB);

		buttonGroup.add(flowRdBtn);
		flowRdBtn.setEnabled(false);
		panel_3.add(flowRdBtn);
		
		buttonGroup.add(rdbtnAllImmediateTributaries);
		rdbtnAllImmediateTributaries.setEnabled(false);
		panel_3.add(rdbtnAllImmediateTributaries);
		
		buttonGroup.add(rdbtnSupplyingTributaries);
		rdbtnSupplyingTributaries.setEnabled(false);
		panel_3.add(rdbtnSupplyingTributaries);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(panel_4);
		
		displayDataButton.setEnabled(false);
		panel_4.add(displayDataButton);
		locationCBB.setEnabled(false);
		
		panel_4.add(locationCBB);
		buttonGroup_2.add(normalRdBtn);
		normalRdBtn.setEnabled(false);
		panel_4.add(normalRdBtn);
		buttonGroup_2.add(reverseRdBtn);
		reverseRdBtn.setEnabled(false);
		panel_4.add(reverseRdBtn);	
		buttonGroup_2.add(levelRdBtn);
		levelRdBtn.setEnabled(false);
		panel_4.add(levelRdBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
	}

}
