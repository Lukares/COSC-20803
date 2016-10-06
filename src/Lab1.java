import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.soap.Node;

public class Lab1 extends GUI implements ActionListener , ListSelectionListener{
	
	File countyFile, speciesFile, treeFile;
	GUI_Dialog dialog = new GUI_Dialog();
	GUI_FileDialog fDialog = new GUI_FileDialog();
	BufferedReader cbr, sbr, tbr;
	CountyNode header;
	TreeNode tN;

	public static void main(String args[])
	{
		Lab1 l1 = new Lab1();
		l1.initialize();
		l1.frame.setVisible(true);
	}
	public Lab1()
	{
		model.addElement(String.format("%-18s%-45s%-13s%-20s%-13s%-19s%-20s%-20s%-20s", "County", "Species Name", "Points", "Circumference", "Height", "Crown Spread", "Starting Year", "Ending Year", "Contributor"));
		model.addElement("________________________________________________________________________________________________________________________________________________________________________________________________________________");
		
		list.addListSelectionListener(this);
		readInButton.addActionListener(this);
		createButton.addActionListener(this);
		displayButton.addActionListener(this);
		cancelButton.addActionListener(this);
		deleteButton.addActionListener(this);
		btnDisplayAllTrees.addActionListener(this);
		dialog.cancelButton.addActionListener(this);
		dialog.submitButton.addActionListener(this);
		fDialog.countyFileButton.addActionListener(this);
		fDialog.speciesFileButton.addActionListener(this);
		fDialog.treeDataButton.addActionListener(this);
		fDialog.okButton.addActionListener(this);
		fDialog.cancelButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) throws NumberFormatException
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
				treeFile = getFile();
				fDialog.treeDataFileLabel.setText(treeFile.getName());
			}
			if(e.getSource() == fDialog.okButton)
			{
				try 
				{
					createOakWiltLL();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
			createButton.setEnabled(true);
			displayButton.setEnabled(true);
			btnDisplayAllTrees.setEnabled(true);
			countyRB.setEnabled(true);
			nameRB.setEnabled(true);
			wiltRB.setEnabled(true);
			pointsRB.setEnabled(true);
			comboBox.setEnabled(true);
			comboBox_1.setEnabled(true);
			pointsTextField.setEnabled(true);
			
			fDialog.dispose();
			}
			if(e.getSource() == fDialog.cancelButton)
			{
				countyFile = null;
				speciesFile = null;
				treeFile = null;
				fDialog.countyFileLabel.setText("<filename>");
				fDialog.speciesFileLabel.setText("<filename>");
				fDialog.treeDataFileLabel.setText("<filename>");
				fDialog.dispose();
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
		if(e.getSource() == dialog.submitButton)
			{
			int counterC = 0;
			int counterS = 1;
				TreeNode newTree = new TreeNode();
				CountyNode tempCCounter = header;
				
				while(!(tempCCounter.getCountyName().equals(dialog.countyBox.getSelectedItem())))
				{
					counterC++;
					tempCCounter = tempCCounter.getDown();
				}
				SpeciesNode tempSCounter = header.getDownX(counterC).getRight();
				while(!(tempSCounter.getRight().getCommonName().equals(dialog.speciesBox.getSelectedItem())))
				{
					counterS++;
					tempSCounter = tempSCounter.getRight();
				}
								
				Double newCircum = Double.parseDouble(dialog.circumTextField.getText().trim());
				Double newHeight = Double.parseDouble(dialog.heightTextField.getText().trim());
				Double newCrownSpread = Double.parseDouble(dialog.crownTextField.getText().trim());
				Double newPoints = (double) Math.round(newCircum + newHeight + (0.25 * newCrownSpread));
				String yearStart = dialog.startingTextField.getText().trim();
				String yearEnd = dialog.endingTextField.getText().trim();
				String newName = dialog.nameTextField.getText();
				
				newTree.setCircumferance(newCircum);
				newTree.setHeight(newHeight);
				newTree.setCrownSpread(newCrownSpread);
				newTree.setPoints(newPoints);
				newTree.setName(newName);
				newTree.setYrEnded(yearEnd);
				newTree.setYrStarted(yearStart);
				newTree.setDownPtr(null);
				if(header.getDownX(counterC).getRight().getRightX(counterS).numTrees > 0)
				{
					header.getDownX(counterC).getRight().getRightX(counterS).getLastPtr().setDownPtr(newTree);
					header.getDownX(counterC).getRight().getRightX(counterS).setLastPtr(newTree);
				} else {
					header.getDownX(counterC).getRight().getRightX(counterS).setDownPtr(newTree);
					header.getDownX(counterC).getRight().getRightX(counterS).setLastPtr(newTree);
				}
				
				header.getDownX(counterC).numCountyTrees++;
				header.getDownX(counterC).getRight().getRightX(counterS).numTrees++;
				dialog.dispose();
			}
		if(e.getSource() == cancelButton)
		{
			frame.dispose();
		}
		if(e.getSource() == displayButton)
		{
			displayTreesBy();
		}
		if(e.getSource() == btnDisplayAllTrees)
		{
			displayAllTrees();
		}
		if(e.getSource() == deleteButton)
		{
			deleteTree();
		}
		
	}
	public void valueChanged(ListSelectionEvent le)
	{
		if(le.getSource() == list)
		{
			deleteButton.setEnabled(true);
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
	public void createOakWiltLL() throws IOException
	{
		
// Create header nodes for the linked lists
		header = new CountyNode();
		header.setCountyName("Head");
		header.setNumTrees(0);
		header.setDown(header);	
		header.setRight(null);
	
// Open readers and input streams to initialize the text data into the linked lists.
		InputStream cs = new FileInputStream(countyFile);
		InputStreamReader csr = new InputStreamReader(cs);
		cbr = new BufferedReader(csr);
		
		InputStream ss = new FileInputStream(speciesFile);
		InputStreamReader ssr = new InputStreamReader(ss);
		sbr = new BufferedReader(ssr);
		
		InputStream ts = new FileInputStream(treeFile);
		InputStreamReader tsr = new InputStreamReader(ts);
		tbr = new BufferedReader(tsr);

// Build the county nodes into a county nodes linked list and point them to a header species node.
		CountyNode temp = header;
		String cityName = cbr.readLine();
		int numCounties = 0;
		while(cityName != null)
		{
			CountyNode curr = new CountyNode();
			curr.setCountyName(cityName);
			curr.setNumTrees(0);
			curr.setRight(new SpeciesNode());
			curr.setDown(header);
		temp.setDown(curr);
		temp = curr;
		comboBox.addItem(cityName);
		dialog.countyBox.addItem(cityName);
		numCounties++;
		cityName = cbr.readLine();
		}
		
		
// Build the species nodes into a species linked list and attach into the county nodes. 		
				
		for(int k = 0; k < numCounties; k++)
		{
			String speciesLine = sbr.readLine();
			SpeciesNode tmp = header.getDownX(k + 1).getRight();
			while(speciesLine != null)
			{	
				String[] r = speciesLine.split("/");
			
				SpeciesNode curr = new SpeciesNode();
				curr.setCommonName(r[0]);
				curr.setScientificName(r[1]);
				curr.setNumTrees(0);
				curr.setDownPtr(null);
				curr.setLastPtr(null);
				curr.setRight(header.getDownX(k).getRight());
				
				tmp.setRight(curr);
				tmp = curr;
				if(k < 1)
				{
				comboBox_1.addItem(curr.getCommonName());
				dialog.speciesBox.addItem(curr.getCommonName());
				}
				speciesLine = sbr.readLine();
			}
			ss = new FileInputStream(speciesFile);
			ssr = new InputStreamReader(ss);
			sbr = new BufferedReader(ssr);
		}
			
// Build the tree nodes into null-terminated linked lists and attach them accordingly
		CountyNode tempCN = header;
		while(!(tempCN.getDown().getCountyName().equals("Head")))
		{
			loadTreeData();	
			tempCN = tempCN.getDown();
		}
		
		displayAllTrees();
}
   public void displayAllTrees()
    {
	model.removeAllElements();
	model.addElement(String.format("%-18s%-45s%-13s%-20s%-13s%-19s%-20s%-20s%-20s", "County", "Species Name", "Points", "Circumference", "Height", "Crown Spread", "Starting Year", "Ending Year", "Contributor"));
	model.addElement("________________________________________________________________________________________________________________________________________________________________________________________________________________");
	
	for(int i = 1; i < 11; i++)
	{
		int j = 1;		
		String coName = header.getDownX(i).getCountyName();
		if(header.getDownX(i).getNumTrees() == 0) j = 23;
	
		for(j = 1; j < 23; j++)
		{	
		 String spName = header.getDownX(i).getRight().getRightX(j).getCommonName() + "/" + header.getDownX(i).getRight().getRightX(j).getScientificName();	
		 TreeNode tree = header.getDownX(i).getRight().getRightX(j).getDownPtr();
		 for(int k = 0; k < header.getDownX(i).getRight().getRightX(j).getNumTrees(); k++)
			{			
				Double points = tree.getPoints();
				Double circumference = tree.getCircumferance();
				Double height = tree.getHeight();
				Double crownSpread = tree.getCrownSpread();
				String yrStart = tree.getYrStarted();
				String yrEnd = tree.getYrEnded();
				String contributor = tree.getName();
				model.addElement(String.format("%-18s%-45s%-13s%-20s%-13s%-19s%-20s%-20s%-20s", coName, spName , points, circumference, height, crownSpread, yrStart, yrEnd, contributor ));			
				tree = tree.getDownPtr();
			}
		}
		}
	}
	   public void displayTreesBy()
	    {
		   model.removeAllElements();
		   model.addElement(String.format("%-18s%-45s%-13s%-20s%-13s%-19s%-20s%-20s%-20s", "County", "Species Name", "Points", "Circumference", "Height", "Crown Spread", "Starting Year", "Ending Year", "Contributor"));
		   model.addElement("________________________________________________________________________________________________________________________________________________________________________________________________________________");
		  
		   int x = 0;
		   int a = 0;
		   int y = 0;
		   int b = 0;
		   CountyNode tempC = header;
		   SpeciesNode tempS = header.getDown().getRight();
		   if(countyRB.isSelected() == true)
		   {
			   y = 1;
			   b = 23;
			   while(!(tempC.getCountyName().equals(comboBox.getSelectedItem())))
			   {
				   x++;
				   tempC = tempC.getDown();
			   }
			   a = x + 1;
		   }
		   if(nameRB.isSelected() == true)
		   {
			   x = 1;
			   a = 11;
			   while(!(tempS.getCommonName().equals(comboBox_1.getSelectedItem())))
			   {
				   y++;
				   tempS = tempS.getRight();
			   }
			   b = y + 1;
		   }
			for(int i = x; i < a; i++)
			{
				int j = 1;		
				String coName = header.getDownX(i).getCountyName();
				if(header.getDownX(i).getNumTrees() == 0) j = 23;
			
				for(j = y; j < b; j++)
				{	
				 String spName = header.getDownX(i).getRight().getRightX(j).getCommonName() + "/" + header.getDownX(i).getRight().getRightX(j).getScientificName();	
				 TreeNode tree = header.getDownX(i).getRight().getRightX(j).getDownPtr();
				 for(int k = 0; k < header.getDownX(i).getRight().getRightX(j).getNumTrees(); k++)
					{			
						Double points = tree.getPoints();
						Double circumference = tree.getCircumferance();
						Double height = tree.getHeight();
						Double crownSpread = tree.getCrownSpread();
						String yrStart = tree.getYrStarted();
						String yrEnd = tree.getYrEnded();
						String contributor = tree.getName();
						model.addElement(String.format("%-18s%-45s%-13s%-20s%-13s%-19s%-20s%-20s%-20s", coName, spName, points, circumference, height, crownSpread, yrStart, yrEnd,  contributor));			
						tree = tree.getDownPtr();
					}
				}
				}	
	    }
	public void loadTreeData() throws NumberFormatException, IOException
	{
			int countyCounter = 0;
			int speciesCounter = 0;
			SpeciesNode sNFinder;
			CountyNode cNN = new CountyNode();
			CountyNode tempC = header;
			String cName = tbr.readLine();
			String treeDataLine;
			
			while(!(tempC.getCountyName().equals(cName)))
			{		
					tempC = tempC.getDown();
					countyCounter++;
			}
			
			cNN = header.getDownX(countyCounter);		
			
			while(!(treeDataLine = tbr.readLine()).equals("$$$$$"))
			{	
				SpeciesNode tempS = header.getDownX(countyCounter).getRight();
				speciesCounter = 0;
				String tData[] = treeDataLine.split("/");
				String sName = tData[0];
				
				Double tPoints = Double.parseDouble(tData[1]);
				Double tCircumference = Double.parseDouble(tData[2]);
				Double tHeight = Double.parseDouble(tData[3]);
				Double tCrownSpread = Double.parseDouble(tData[4]);				
				
				while(!(tempS.getCommonName().equals(sName)))
				{			
						tempS = tempS.getRight();
						speciesCounter++;
				}
				
				sNFinder = 	cNN.getRight().getRightX(speciesCounter);
							
				tN = new TreeNode();
					tN.setPoints(tPoints);
					tN.setCircumferance(tCircumference);
					tN.setHeight(tHeight);
					tN.setCrownSpread(tCrownSpread);
					tN.setYrStarted(tData[5]);
					tN.setYrEnded(tData[6]);
					tN.setName(tData[7]);
					tN.setDownPtr(null);
													
				if(sNFinder.getDownPtr() != null)
					{
						sNFinder.getLastPtr().setDownPtr(tN);
						sNFinder.setLastPtr(tN);
						sNFinder.numTrees++;
					}
				else if(sNFinder.getDownPtr() == null)		
					{
						sNFinder.setDownPtr(tN);
						sNFinder.setLastPtr(tN);
						sNFinder.numTrees++;
					}
				cNN.numCountyTrees++;
				
				sNFinder.setRight(cNN.getRight().getRightX(speciesCounter + 1));
				cNN.getRight().getRightX(speciesCounter - 1).setRight(sNFinder);
			}		
			header.getDownX(countyCounter - 1).setDown(cNN);
			cNN.setDown(header.getDownX(countyCounter + 1));
	}
	public void deleteTree()
	{
		CountyNode tempFinder = header;
		SpeciesNode tmpFinder;
		TreeNode toDelete;
		int countyFinder = 0;
		int speciesFinder = 0;
		
		String dTreeData[] = list.getSelectedValue().toString().split("  +");
		String checkCounty = dTreeData[0];
		String[] checkSpecies = dTreeData[1].split("/");
		String checkCommon = checkSpecies[0];
		double checkPoints = Double.parseDouble(dTreeData[2]);
		double checkCircum = Double.parseDouble(dTreeData[3]);
		double checkHeight = Double.parseDouble(dTreeData[4]);
		double checkCrown = Double.parseDouble(dTreeData[5]);
		String checkYrSt = dTreeData[6];
		String checkYrEnd = dTreeData[7];
		String checkName = dTreeData[8];
		
		while(!(tempFinder.getCountyName().equals(checkCounty)))
		{
			countyFinder++;
			tempFinder = tempFinder.getDown();
		}
		System.out.println(checkCommon);
		tmpFinder = header.getDownX(countyFinder).getRight();
		while(!(tmpFinder.getCommonName().equals(checkCommon)))
		{
			tmpFinder = tmpFinder.getRight();
			speciesFinder++;
		}
		
		toDelete = header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr();
		for(int i = 0; i < header.getDownX(countyFinder).getRight().getRightX(speciesFinder).numTrees; i++)
		{
			if(toDelete.getPoints() == checkPoints && toDelete.getCircumferance() == checkCircum && 
					toDelete.getHeight() == checkHeight && toDelete.getYrEnded() == checkYrEnd && 
					toDelete.getYrStarted() == checkYrSt && toDelete.getName() == checkName)
			{
				if(header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().equals(toDelete) && header.getDownX(countyFinder).getRight().getRightX(speciesFinder).numTrees == 1)
				{
					header.getDownX(countyFinder).getRight().getRightX(speciesFinder).setDownPtr(null);
					header.getDownX(countyFinder).getRight().getRightX(speciesFinder).setLastPtr(null);
					toDelete = null;
				}
				if(header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().equals(toDelete) && header.getDownX(countyFinder).getRight().getRightX(speciesFinder).numTrees > 1)
				{
				header.getDownX(countyFinder).getRight().getRightX(speciesFinder).setDownPtr(toDelete.getDownPtr());
				toDelete = null;
				}
				if(header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getLastPtr().equals(toDelete) && header.getDownX(countyFinder).getRight().getRightX(speciesFinder).numTrees > 1)
				{				
					header.getDownX(countyFinder).getRight().getRightX(speciesFinder).setLastPtr(header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().getDownPtrX(i - 1));
					header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().getDownPtrX(i - 1).setDownPtr(null);
					toDelete = null;
				} else {
					header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().getDownPtrX(i - 1).setDownPtr(header.getDownX(countyFinder).getRight().getRightX(speciesFinder).getDownPtr().getDownPtrX(i + 1));
					toDelete = null;
				}				
			} 
			toDelete = toDelete.getDownPtr();
		}
		header.getDownX(countyFinder).getRight().getRightX(speciesFinder).numTrees--;
		model.remove(list.getSelectedIndex());
	}
	public interface CountyNamesNodeInterface
	{
	// ******************PUBLIC METHODS******************************************
	// public String getCountyName();
	// --> returns the countyName of the node being pointed to
	//
	// public void setCountyName(String cn);
	// --> sets the countyName of the node being pointed to to cn.
	//
	// public int getNumTrees();
	// --> returns the numTrees field of the CountyNode being pointed to
	//
	// public void setNumTrees(int nt);
	// --> sets the numTrees field of the CountyNode being pointed to to nt.
	//
	// public CountyNode getDown();
	// --> returns the value of the downPtr for the specified CountyNode
	//
	// public void setDown(CountyNode ptr);
	// --> sets the downPtr of the specified CountyNode to ptr
	//
	// public SpeciesNode getRight();
	// --> returns the value of the rightPtr for the specified CountyNode
	//
	// public void setRight(SpeciesNode ptr);
	// --> sets the rightPtr of the specified CountyNode to ptr
	 //**************************************************************************
	 
	/**
	* Protocol for CountyNameNodeInterface
	* @author James Comer
	*/
	//Method signatures
	 public String getCountyName();
	 public void setCountyName(String cn);
	 public int getNumTrees();
	 public void setNumTrees(int nt);
	 public CountyNode getDown();
	 public void setDown(CountyNode ptr);
	 public SpeciesNode getRight();
	 public void setRight(SpeciesNode ptr);
	}
public class CountyNode implements CountyNamesNodeInterface
{
	private String countyName = "";
	private int numCountyTrees = 0;
	private SpeciesNode speciesNodePtr;
	private CountyNode countyNodePtr;
	
	public void setCountyName(String s)
	{
		this.countyName = s;
	}
	public String getCountyName()
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
	public void setDown(CountyNode cN)
	{
		this.countyNodePtr = cN;
	}
	public CountyNode getDownX(int x)
	{
		CountyNode temp = this;
		int y = 0;
		while(y < x)
		{
			temp = temp.getDown();
			y++;
		}
		return temp;
	}
	public void setRight(SpeciesNode sN)
	{
		this.speciesNodePtr = sN;
	}
	public SpeciesNode getRight()
	{
		return this.speciesNodePtr;
	}
	public CountyNode getDown()
	{
		return this.countyNodePtr;
	}	
}
public interface SpeciesNodeInterface
{
// ******************PUBLIC METHODS******************************************
// public String getCommonName()
// --> returns the commonName field of the specified SpeciesNode
//
// public void setCommonName(String s)
// --> sets the commonName field of the specified SpeciesNode to s
//
// public String getScientificName()
// --> returns the scientificName field of the specified SpeciesNode
//
// public void setScientificName(String s)
// --> sets the scientificName field of the specified SpeciesNode to s
//
// public int getNumTrees()
// --> returns the numTrees field of the specified SpeciesNode
//
// public void setNumTrees(int n)
// --> sets the numTrees field of the specified SpeciesNode to n
//
// public TreeNode getDownPtr()
// --> returns the downPtr field of the specified SpeciesNode
//
// public void setDownPtr(TreeNode p)
// --> sets the downPtr field of the specified SpeciesNode to p
//
// public void setLastPtr(TreeNode p)
// --> sets the lastPtr field of the specified SpeciesNode to point to p
//
// public TreeNode getLastPtr);
// --> returns the lastPtr field of the specified SpeciesNode
//
//**************************************************************************** 10
/**
* Protocol for SpeciesNodeInterface
* @author James Comer
*/
//Method signatures
public String getCommonName();
public void setCommonName(String s);
public String getScientificName();
public void setScientificName(String s);
public int getNumTrees();
public void setNumTrees(int n);
public TreeNode getDownPtr();
public void setDownPtr(TreeNode p);
public TreeNode getLastPtr();
public void setLastPtr(TreeNode p);
}
public class SpeciesNode implements SpeciesNodeInterface
{
	private String commonName = "";
	private String scientificName = "";
	private int numTrees = 0;
	private TreeNode treeNodePtrNext;
	private TreeNode treeNodePtrLast;
	private SpeciesNode speciesNodePtr;
	
	public SpeciesNode()
	{
		this.commonName = "Head";
		this.scientificName = "Head";
		this.speciesNodePtr = this;
		this.numTrees = 0;
		this.treeNodePtrLast = null;
		this.treeNodePtrNext = null;
	}
	@Override
	public String getCommonName() {
		return this.commonName;
	}

	@Override
	public void setCommonName(String s) {
		this.commonName = s;		
	}

	@Override
	public String getScientificName() {
		return this.scientificName;
	}

	@Override
	public void setScientificName(String s) {
		this.scientificName = s;
	}

	@Override
	public int getNumTrees() {
		return this.numTrees;
	}

	@Override
	public void setNumTrees(int n) {
		this.numTrees = n;
	}

	@Override
	public TreeNode getDownPtr() {
		return this.treeNodePtrNext;
	}

	@Override
	public void setDownPtr(TreeNode p) {
		this.treeNodePtrNext = p;
	}

	@Override
	public TreeNode getLastPtr() {
		return this.treeNodePtrLast;
	}

	@Override
	public void setLastPtr(TreeNode p) {
		this.treeNodePtrLast = p;
	}
	public void setRight(SpeciesNode p)
	{
		this.speciesNodePtr = p;
	}
	public SpeciesNode getRight()
	{
		return this.speciesNodePtr;
	}
	public SpeciesNode getRightX(int x)
	{
		int y = 0;
		SpeciesNode temp = this;
		while(y < x)
		{
			temp = temp.getRight();			
			y++;
		}
		return temp;
	}
	
}

public interface TreeNodeInterface
{
// ******************PUBLIC METHODS******************************************
// public double getPoints()
// --> returns the points field of the specified TreeNode
//
// public void setPoints(double d)
// --> sets the points field of the specified TreeNode to d
//
// public double getCircumferance()
// --> returns the circumferance field of the specified TreeNode
//
// public void setCircumferance(double d)
// --> sets the circumferance field of the specified TreeNode to d
//
// public double getHeight()
// --> returns the height field of the specified TreeNode
//
// public void setHeight(double d)
// --> sets the height field of the specified TreeNode to d
//
// public double getCrownSpread()
// --> returns the crownSpread field of the specified TreeNode
//
// public void setCrownSpread(double d)
// --> sets the crownSpread field of the specified TreeNode to d
//
// public String getYrStarted()
// --> returns the yrStarted field of the specified TreeNode
//
// public void setYrStarted(String s)
// --> sets the yrStarted field of the specified TreeNode to s
//
// public String getYrEnded()
// --> returns the yrEnded field of the specified TreeNode 11
// public void setYrEnded(String s)
// --> sets the yrEnded field of the specified TreeNode to s
//
// public String getName()
// --> returns the personName field of the specified TreeNode
//
// public void setName(String s)
// --> sets the personName field of the specified TreeNode to s
//
// public TreeNode getDownPtr()
// --> returns the downPtr field of the specified TreeNode
//
// public void setDownPtr(TreeNode p);
// --> sets the downPtr field of the specified TreeNode to p
//
//****************************************************************************
/**
* Protocol for TreeNodeInterface
* @author James Comer
*/
//Method signatures
public double getPoints();
public void setPoints(double d);
public double getCircumferance();
public void setCircumferance(double d);
public double getHeight();
public void setHeight(double d);
public double getCrownSpread();
public void setCrownSpread(double d);
public String getYrStarted();
public void setYrStarted(String s);
public String getYrEnded();
public void setYrEnded(String s);
public String getName();
public void setName(String s);
public TreeNode getDownPtr();
public void setDownPtr(TreeNode p);
}
public class TreeNode implements TreeNodeInterface
{
	private double points = 0;
	private double circumference = 0;
	private double height = 0;
	private double crownSpread = 0;
	private String yrStarted = "";
	private String yrEnded = "";
	private String name = "";
	private TreeNode TreeNodePtr;

	@Override
	public double getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(double d) {
		this.points = d;
	}

	@Override
	public double getCircumferance() {
		return this.circumference;
	}

	@Override
	public void setCircumferance(double d) {
		this.circumference = d;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(double d) {
		this.height = d;
	}

	@Override
	public double getCrownSpread() {
		return this.crownSpread;
	}

	@Override
	public void setCrownSpread(double d) {
		this.crownSpread = d;
	}

	@Override
	public String getYrStarted() {
		return this.yrStarted;
	}

	@Override
	public void setYrStarted(String s) {
		this.yrStarted = s;
	}

	@Override
	public String getYrEnded() {
		return this.yrEnded;
	}

	@Override
	public void setYrEnded(String s) {
		this.yrEnded = s;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String s) {
		this.name = s;
	}

	@Override
	public TreeNode getDownPtr() {
		return this.TreeNodePtr;
	}

	@Override
	public void setDownPtr(TreeNode p) {
		this.TreeNodePtr = p;
	}
	public TreeNode getDownPtrX(int x)
	{
		int y = 0;
		TreeNode temp = this;
		while(y < x)
		{
			temp = temp.getDownPtr();			
			y++;
		}
		return temp;
	}
	
}
}
