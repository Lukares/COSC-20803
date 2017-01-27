import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class GUI {

	JFrame frame;
	JButton getMissingButton = new JButton("Get Missing Keys");
	JButton getPresentButton = new JButton("Get Present Keys");
	JButton getAscButton = new JButton("Get Ascending File");

	JLabel ascLbl = new JLabel("****<Filename>****");
	JLabel presentLbl = new JLabel("****<Filename>****");
	JLabel missingLbl = new JLabel("****<Filename>****");
	
	JRadioButton rdbtn100_0 = new JRadioButton("Search 100 / Skip 0");
	JRadioButton rdbtn150_100 = new JRadioButton("Search 150 / Skip 100");
	JRadioButton rdbtn200_50 = new JRadioButton("Search 200 / Skip 50");
	JRadioButton rdbtn500_0 = new JRadioButton("Search 500 / Skip 0");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	JButton loadButton = new JButton("Load In Data");
	JLabel numSearchLbl = new JLabel("Search Comparisons: ");
	private final JPanel panel_6 = new JPanel();
	private final JPanel panel_7 = new JPanel();
	final JButton searchButton = new JButton("Begin Searching");
	final JRadioButton seqRdBtn = new JRadioButton("Sequential Search");
	final JRadioButton binaryRdBtn = new JRadioButton("Binary Search");
	final JRadioButton interpolationRdBtn = new JRadioButton("Interpolation Search");
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
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

	public GUI() {
		initialize();
	}

	 void initialize() {
		 
		frame = new JFrame();
		frame.setBounds(100, 100, 770, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(3, 2, 0, 0));
		panel.add(getAscButton);
		ascLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(ascLbl);
		panel.add(getPresentButton);
		presentLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(presentLbl);
		panel.add(getMissingButton);		
		missingLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(missingLbl);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		buttonGroup.add(rdbtn100_0);
		
		panel_1.add(rdbtn100_0);
		buttonGroup.add(rdbtn150_100);
		panel_1.add(rdbtn150_100);
		buttonGroup.add(rdbtn200_50);
		panel_1.add(rdbtn200_50);
		buttonGroup.add(rdbtn500_0);
		panel_1.add(rdbtn500_0);
		
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_2.add(panel_3);
		loadButton.setEnabled(false);
		
		panel_3.add(loadButton);
		
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 1, 0, 0));
		
		panel_4.add(panel_6);
		searchButton.setEnabled(false);
		
		panel_6.add(searchButton);
		
		panel_4.add(panel_7);
		buttonGroup_1.add(seqRdBtn);
		seqRdBtn.setSelected(true);
		
		panel_7.add(seqRdBtn);
		buttonGroup_1.add(binaryRdBtn);
		
		panel_7.add(binaryRdBtn);
		buttonGroup_1.add(interpolationRdBtn);
		
		panel_7.add(interpolationRdBtn);
		
		panel_2.add(panel_5);
		numSearchLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_5.add(numSearchLbl);
	}

}
