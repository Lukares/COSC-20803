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

public class GUI_Dialog extends JDialog {

	 final JPanel contentPanel = new JPanel();
	 JTextField textField;
	 JTextField textField_1;
	 JTextField textField_2;
	 JTextField textField_3;
	 JTextField textField_4;
	 JTextField textField_5;
	 
	 JButton cancelButton = new JButton("Cancel");
	 JButton submitButton = new JButton("Submit");
	 

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
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(10);
			}
			{
				JLabel treeNameLbl = new JLabel("Common Name:");
				panel.add(treeNameLbl);
			}
			{
				textField_1 = new JTextField();
				panel.add(textField_1);
				textField_1.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel circumferenceLbl = new JLabel("Circumference:");
				panel.add(circumferenceLbl);
			}
			{
				textField_2 = new JTextField();
				panel.add(textField_2);
				textField_2.setColumns(10);
			}
			{
				JLabel crownSpreadLbl = new JLabel("Crown Spread: ");
				panel.add(crownSpreadLbl);
			}
			{
				textField_3 = new JTextField();
				panel.add(textField_3);
				textField_3.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 4, 10, 0));
			{
				JLabel heightLbl = new JLabel("Height: ");
				panel.add(heightLbl);
			}
			{
				textField_4 = new JTextField();
				panel.add(textField_4);
				textField_4.setColumns(10);
			}
			{
				JLabel contributorLbl = new JLabel("Contributor (Your Name): ");
				panel.add(contributorLbl);
			}
			{
				textField_5 = new JTextField();
				panel.add(textField_5);
				textField_5.setColumns(10);
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
