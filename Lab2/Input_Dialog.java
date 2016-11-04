import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

public class Input_Dialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	JButton readInButton = new JButton("Get File");
	JButton okButton = new JButton("OK");
	JButton cancelButton = new JButton("Cancel");
	JLabel fileLbl = new JLabel("<Filename>");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Input_Dialog dialog = new Input_Dialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Input_Dialog() {
		setBounds(100, 100, 272, 125);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				panel.add(readInButton);
			}
		}
		{
			
			fileLbl.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(fileLbl);
		}
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
	}

}
