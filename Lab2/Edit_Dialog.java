import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Edit_Dialog extends JDialog {
	
	JTextField newTribTextField;
	JTextField renameTF;
	
	JComboBox renameCBB = new JComboBox();
	JComboBox deleteCBB = new JComboBox();
	JComboBox locationCBB = new JComboBox();
	JComboBox flowsCBB = new JComboBox();
	
	JButton okButton = new JButton("OK");
	JButton cancelButton = new JButton("Cancel");
	
	JPanel addNewPanel = new JPanel();
	JPanel renamePanel = new JPanel();
	JPanel deletePanel = new JPanel();
	
	JSpinner spinner = new JSpinner();
	
	JComboBox destDelCBB = new JComboBox();
	JComboBox renameDestCBB = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Edit_Dialog dialog = new Edit_Dialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Edit_Dialog() {
		setBounds(100, 100, 925, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			getContentPane().add(addNewPanel, BorderLayout.NORTH);
			addNewPanel.setLayout(new GridLayout(4, 1, 0, 0));
			{
				JLabel lblNewLabel = new JLabel("Add New Tributary");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Proxy 6", Font.PLAIN, 14));
				addNewPanel.add(lblNewLabel);
			}
			{
				JPanel panel_1 = new JPanel();
				addNewPanel.add(panel_1);
				{
					JLabel lblNewLabel_1 = new JLabel("Name of Tributary: ");
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(lblNewLabel_1);
				}
				{
					newTribTextField = new JTextField();
					newTribTextField.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(newTribTextField);
					newTribTextField.setColumns(10);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				addNewPanel.add(panel_1);
				{
					JLabel lblNewLabel_2 = new JLabel("Location:");
					panel_1.add(lblNewLabel_2);
				}
				{

					panel_1.add(locationCBB);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				addNewPanel.add(panel_1);
				{
					JLabel lblNewLabel_3 = new JLabel("Flows To:");
					panel_1.add(lblNewLabel_3);
				}
				{
					panel_1.add(flowsCBB);
				}
				{
					JLabel lblDescendenceLevel = new JLabel("Descendence Level:");
					panel_1.add(lblDescendenceLevel);
				}
				{
					spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
					panel_1.add(spinner);
				}
			}
		}
		{
			getContentPane().add(renamePanel, BorderLayout.WEST);
			renamePanel.setLayout(new GridLayout(3, 0, 0, 0));
			{
				JLabel lblNewLabel_4 = new JLabel("Rename Tributary");
				lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_4.setFont(new Font("Proxy 5", Font.PLAIN, 14));
				renamePanel.add(lblNewLabel_4);
			}
			{
				JPanel panel = new JPanel();
				renamePanel.add(panel);
				{
					JLabel renameDestLbl = new JLabel("Location: ");
					panel.add(renameDestLbl);
				}
				{
					
					panel.add(renameDestCBB);
				}
				{
					JLabel lblNewLabel_5 = new JLabel("Tributary to Rename:");
					panel.add(lblNewLabel_5);
				}
				{

					panel.add(renameCBB);
				}
			}
			{
				JPanel panel = new JPanel();
				renamePanel.add(panel);
				{
					JLabel lblNewLabel_6 = new JLabel("Rename To:");
					panel.add(lblNewLabel_6);
				}
				{
					renameTF = new JTextField();
					panel.add(renameTF);
					renameTF.setColumns(10);
				}
			}
		}
		{
			getContentPane().add(deletePanel, BorderLayout.EAST);
			deletePanel.setLayout(new GridLayout(3, 0, 0, 0));
			{
				JLabel lblDeleteTributary = new JLabel("Delete Tributary");
				lblDeleteTributary.setHorizontalAlignment(SwingConstants.CENTER);
				lblDeleteTributary.setFont(new Font("Proxy 1", Font.PLAIN, 14));
				deletePanel.add(lblDeleteTributary);
			}
			{
				JPanel panel = new JPanel();
				deletePanel.add(panel);
				{
					JLabel lblLocation = new JLabel("Location: ");
					panel.add(lblLocation);
				}
				{
					panel.add(destDelCBB);
				}
			}
			{
				JPanel panel = new JPanel();
				deletePanel.add(panel);
				{
					JLabel lblNewLabel_7 = new JLabel("Tributary to Delete:");
					panel.add(lblNewLabel_7);
				}
				{
					panel.add(deleteCBB);
				}
			}
		}
	}

}
