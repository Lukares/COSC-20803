import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.xml.soap.Node;

public class Lab1 extends GUI implements ActionListener{
	
	File countyFile, speciesFile, treeDataFile;
	GUI_Dialog dialog = new GUI_Dialog();
	GUI_FileDialog fDialog = new GUI_FileDialog();

	public static void main(String args[])
	{
		Lab1 l1 = new Lab1();
		l1.initialize();
		l1.frame.setVisible(true);
	}
	public Lab1()
	{
		readInButton.addActionListener(this);
		createButton.addActionListener(this);
		cancelButton.addActionListener(this);
		dialog.cancelButton.addActionListener(this);
		dialog.submitButton.addActionListener(this);
		fDialog.countyFileButton.addActionListener(this);
		fDialog.speciesFileButton.addActionListener(this);
		fDialog.treeDataButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == readInButton)
		{
			fDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			fDialog.setVisible(true);
		}
		if(e.getSource() == fDialog.countyFileButton)
		{
			countyFile = getFile();
			fDialog.countyFileLabel.setText(countyFile.getName());
		}
		if(e.getSource() == fDialog.speciesFileButton)
		{
			speciesFile = getFile();
			fDialog.speciesFileLabel.setText(speciesFile.getName());
		}
		if(e.getSource() == fDialog.treeDataButton)
		{
			treeDataFile = getFile();
			fDialog.treeDataFileLabel.setText(treeDataFile.getName());
		}
		if(e.getSource() == createButton)
		{			
			try 
			{
				
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			} 
		}
		if(e.getSource() == dialog.cancelButton)
		{
			dialog.dispose();
		}
		if(e.getSource() == cancelButton)
		{
			frame.dispose();
		}
	}
	public File getFile()
	{
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			return fc.getSelectedFile();
		}
		return null;
	}
	
public class countyNode
{
	private String countyName = "";
	private int numCountyTrees = 0;
	private speciesNode speciesNodePtr;
	private countyNode countyNodePtr;
	
	public void setName(String s)
	{
		this.countyName = s;
	}
	public String getName()
	{
		return this.countyName;
	}
	public void setNumTrees(int x)
	{
		this.numCountyTrees = x;
	}
	public int getNumTrees()
	{
		return this.numCountyTrees;
	}
	public void setNext(countyNode cN)
	{
		this.countyNodePtr = cN;
	}
	public countyNode getNext()
	{
		return this.countyNodePtr;
	}
	public void setRight(speciesNode sN)
	{
		this.speciesNodePtr = sN;
	}
	public speciesNode getRight()
	{
		return this.speciesNodePtr;
	}	
}
public class speciesNode
{
	
}
}
