import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class GUI_Dialog extends JDialog {

	 final JPanel contentPanel = new JPanel();
	 JTextField circumTextField;
	 JTextField crownTextField;
	 JTextField heightTextField;
	 JTextField nameTextField;
	 
	 JButton cancelButton = new JButton("Cancel");
	 JButton submitButton = new JButton("Submit");
	 JTextField startingTextField, endingTextField;
	 
	 JComboBox countyBox;
	 JComboBox speciesBox;
	 
	JLabel currPointsLbl = new JLabel("");
	 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GUI_Dialog dialog = new GUI_Dialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GUI_Dialog() {
		
		
		setBounds(100, 100, 922, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblNewLabel = new JLabel("Enter New Tree Data");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 25));
				panel.add(lblNewLabel);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel countyLbl = new JLabel("County: ");
				countyLbl.setHorizontalAlignment(SwingConstants.LEFT);
				panel.add(countyLbl);
			}
			{
				countyBox = new JComboBox();
				panel.add(countyBox);
			}
			{
				JLabel treeNameLbl = new JLabel("Common Name:");
				panel.add(treeNameLbl);
			}
			{
				speciesBox = new JComboBox();
				panel.add(speciesBox);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel circumferenceLbl = new JLabel("Circumference (in):");
				panel.add(circumferenceLbl);
			}
			{
				circumTextField = new JTextField();
				panel.add(circumTextField);
				circumTextField.setColumns(10);
			}
			{
				JLabel crownSpreadLbl = new JLabel("Crown Spread (ft):");
				panel.add(crownSpreadLbl);
			}
			{
				crownTextField = new JTextField();
				panel.add(crownTextField);
				crownTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel heightLbl = new JLabel("Height (ft):");
				panel.add(heightLbl);
			}
			{
				heightTextField = new JTextField();
				panel.add(heightTextField);
				heightTextField.setColumns(10);
			}
			{
				JLabel contributorLbl = new JLabel("Contributor (Your Name): ");
				panel.add(contributorLbl);
			}
			{
				nameTextField = new JTextField();
				panel.add(nameTextField);
				nameTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel lblYearStarted = new JLabel("Year Started:");
				panel.add(lblYearStarted);
			}
			{
				startingTextField = new JTextField();
				panel.add(startingTextField);
				startingTextField.setColumns(10);
			}
			{
				JLabel lblYearEnded = new JLabel("Year Ended:");
				panel.add(lblYearEnded);
			}
			{
				endingTextField = new JTextField();
				panel.add(endingTextField);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				currPointsLbl.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 12));
				currPointsLbl.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(currPointsLbl);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				submitButton.setActionCommand("OK");
				buttonPane.add(submitButton);
				getRootPane().setDefaultButton(submitButton);
			}
			{
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
