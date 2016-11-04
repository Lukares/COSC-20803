import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/* Name: Luke Reddick
 * Class: COSC 20803 Data Structures
 * Professor: Dr. Comer
 * Date: 11/2016
 * 
 * This program loads a simple GUI that requires a user to input a text file of water tributary systems by location.
 * Afterward, the data will be initialized into a fully-threaded naturally corresponding binary tree. Right links are
 * sibling nodes, left links are child nodes. Thus every sibling node to a child node is a child of that child's parent node. 
 * This way tree traversal is much more intuitive and efficient. We traverse the tree by preorder successor in this program.
 * 
 * The user has options to insert, delete, and rename tributary nodes in the database. Along with these simple edits, the user
 * can query the database about a particular nodes immediate and total supplying tributaries as well as a trace to destination 
 * simulating the flow of water from tributary to tributary. Additionally the user may display the data in preorder, reverse preorder
 * and level order. 
 * 
 * destHeader is the only node loaded into a variable that holds the circular linked list of location nodes (destination nodes).
 * This variable is used as our starting point when attempting to search for a particular node. The S Stack allows us to initialized data
 * using a level order and node data input to build the tree. 
 * Other notable variables include:
 * 
 * wbr - the BufferedReader for the text file.
 * numDestinations - counter that keeps track of locations in the database
 * currDest - a temp variable for the Destination Node during initialization.
 * 
 * All other variables are generally temporary or place holders to visit and traverse data. 
 * 
 * There are currently some shortcomings of this implementation:
 * 
 * The delete method crashes when deleting nodes that are roots (Blue Mesa Lake // Gulf of Mexico)
 * The supplying tributaries query leaves a couple nodes out of its display. 
 * The level order display does not display in complete order, some of the output is not in order. 
 * If a user selects a tributary not located in a destination they selected in the corresponding combo box, the program will catch an infinite loop. 
 * 
 * These bugs will be patched in a later update as I try to continue working. 
 */

public class Lab2 extends GUI implements ActionListener {

	File waterFile;
	Input_Dialog fileDialog = new Input_Dialog();
	Edit_Dialog eDialog = new Edit_Dialog();
	BufferedReader wbr;
	int numDestinations = 0;
	int numTribs;
	DestinationNode destHeader;
	Stack<TempTribData> S = new Stack<TempTribData>();
	
	public static void main(String[] args) {
		Lab2 l2 = new Lab2();
		l2.initialize();
		l2.frame.setVisible(true);
	}
	
// Lab2 constructor that adds action listeners to all relevant components and informs the user about the empty combo boxes. 	
	
	public Lab2()
	{
		textArea.setText("Combo Boxes will be populated as tributaries are added to the database");
		
		inputButton.addActionListener(this);
		editButton.addActionListener(this);
		exitButton.addActionListener(this);
		discoverButton.addActionListener(this);
		displayDataButton.addActionListener(this);
		levelRdBtn.addActionListener(this);
		reverseRdBtn.addActionListener(this);
		normalRdBtn.addActionListener(this);
		rdbtnAddNew.addActionListener(this);
		rdbtnDelete.addActionListener(this);
		rdbtnRename.addActionListener(this);
		rdbtnSupplyingTributaries.addActionListener(this);
		rdbtnAllImmediateTributaries.addActionListener(this);
		flowRdBtn.addActionListener(this);
		fileDialog.readInButton.addActionListener(this);
		fileDialog.cancelButton.addActionListener(this);
		fileDialog.okButton.addActionListener(this);
		eDialog.okButton.addActionListener(this);
		eDialog.cancelButton.addActionListener(this);
	}
	
// actionPerformed handles every button and radio button selection in the program. Buttons require a radio button selection to be enabled. 
// The data will only be initialized after the selected text file has been submitted from the dialog ok button. And the rest of the GUI
// follows intuitively as button selections work to their specified functions. Editing the data will clear combo boxes and update them afterward. 
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == levelRdBtn || e.getSource() == reverseRdBtn || e.getSource() == normalRdBtn)
		{
			displayDataButton.setEnabled(true);
		}
		if(e.getSource() == rdbtnAddNew || e.getSource() == rdbtnDelete || e.getSource() == rdbtnRename)
		{
			editButton.setEnabled(true);
		}
		if(e.getSource() == rdbtnSupplyingTributaries || e.getSource() == rdbtnAllImmediateTributaries || e.getSource() == flowRdBtn)
		{
			discoverButton.setEnabled(true);
		}
		if(e.getSource() == inputButton)
		{
			fileDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			fileDialog.setVisible(true);
		}
			if(e.getSource() == fileDialog.readInButton)
			{
				waterFile = getFile();
				fileLbl.setText(waterFile.getName());
				fileDialog.fileLbl.setText(waterFile.getName());
			}
			if(e.getSource() == fileDialog.okButton)
			{
				rdbtnAddNew.setEnabled(true);
				rdbtnDelete.setEnabled(true);
				rdbtnRename.setEnabled(true);
				rdbtnSupplyingTributaries.setEnabled(true);
				rdbtnAllImmediateTributaries.setEnabled(true);
				flowRdBtn.setEnabled(true);
				levelRdBtn.setEnabled(true);
				reverseRdBtn.setEnabled(true);
				normalRdBtn.setEnabled(true);
				discoverCBB.setEnabled(true);
				locCBB.setEnabled(true);
				locationCBB.setEnabled(true);
				
				try {
					createTributaryTree();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				fileDialog.dispose();
			}
			if(e.getSource() == fileDialog.cancelButton)
			{
				fileDialog.dispose();
			}
		if(e.getSource() == exitButton)
		{
			frame.dispose();
		}
		if(e.getSource() == editButton)
		{
			if(rdbtnAddNew.isSelected())
			{
				setPanelEnabled(eDialog.renamePanel, false);
				setPanelEnabled(eDialog.deletePanel, false);
				setPanelEnabled(eDialog.addNewPanel, true);
			}
			else if(rdbtnDelete.isSelected())
			{
				setPanelEnabled(eDialog.renamePanel, false);
				setPanelEnabled(eDialog.deletePanel, true);
				setPanelEnabled(eDialog.addNewPanel, false);
			}
			else if(rdbtnRename.isSelected())
			{
				setPanelEnabled(eDialog.renamePanel, true);
				setPanelEnabled(eDialog.deletePanel, false);
				setPanelEnabled(eDialog.addNewPanel, false);
			}		
			
			eDialog.setVisible(true);
		}
			if(e.getSource() == eDialog.okButton)
			{
				if(rdbtnAddNew.isSelected())
				{
					addNode((String) eDialog.locationCBB.getSelectedItem(), (Integer) eDialog.spinner.getValue(), (String) eDialog.flowsCBB.getSelectedItem(), eDialog.newTribTextField.getText());
					discoverCBB.removeAllItems();
					eDialog.renameCBB.removeAllItems();
					eDialog.deleteCBB.removeAllItems();
					eDialog.flowsCBB.removeAllItems();
					for(int i = 1; i <= numDestinations; i++)
					{
						update(destHeader.getRightX(i).getLeft());
					}
				}
				else if(rdbtnDelete.isSelected())
				{
					delete((String) eDialog.destDelCBB.getSelectedItem(), (String) eDialog.deleteCBB.getSelectedItem());
					discoverCBB.removeAllItems();
					eDialog.renameCBB.removeAllItems();
					eDialog.deleteCBB.removeAllItems();
					eDialog.flowsCBB.removeAllItems();
					for(int i = 1; i <= numDestinations; i++)
					{
						update(destHeader.getRightX(i).getLeft());
					}
				}
				else if(rdbtnRename.isSelected())
				{
					rename((String) eDialog.renameDestCBB.getSelectedItem(), (String) eDialog.renameCBB.getSelectedItem(), eDialog.renameTF.getText().trim());
					discoverCBB.removeAllItems();
					eDialog.renameCBB.removeAllItems();
					eDialog.deleteCBB.removeAllItems();
					eDialog.flowsCBB.removeAllItems();
					for(int i = 1; i <= numDestinations; i++)
					{
						update(destHeader.getRightX(i).getLeft());
					}
				}
				eDialog.dispose();
			}
			if(e.getSource() == eDialog.cancelButton)
			{
				eDialog.dispose();
			}
		if(e.getSource() == discoverButton)
		{
			if(rdbtnSupplyingTributaries.isSelected())
			{
				supplyingTribs((String) locCBB.getSelectedItem(), (String) discoverCBB.getSelectedItem());
			}
			else if(rdbtnAllImmediateTributaries.isSelected())
			{
				immediateTribs((String) locCBB.getSelectedItem(), (String) discoverCBB.getSelectedItem());
			}
			else if(flowRdBtn.isSelected())
			{
				traceToDestination((String) locCBB.getSelectedItem(), (String) discoverCBB.getSelectedItem());
			}
		}
		if(e.getSource() == displayDataButton)
		{
			
			if(normalRdBtn.isSelected())
			{
				DestinationNode destNode = destHeader;
				while(destNode.getDestination() != (String) locationCBB.getSelectedItem())
				{
					destNode = destNode.getRight();
				}
				displayPreorder(destNode.getLeft());
			}
			else if(reverseRdBtn.isSelected())
			{
				DestinationNode destNode = destHeader;
				while(destNode.getDestination() != (String) locationCBB.getSelectedItem())
				{
					destNode = destNode.getRight();
				}
				displayReverse(destNode.getLeft());			}
			else if(levelRdBtn.isSelected())
			{
				displayLevelOrder((String) locationCBB.getSelectedItem());
			}
		}
	}
	
	public File getFile() throws NullPointerException
	{
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			return fc.getSelectedFile();
		}
		return null;
	}
	
// setPanelEnabled is in charge of enabling/disabling an entire panel of swing components on the Edit Dialog GUI frame. 
// Pending on the users radio button selection to rename, add or delete, the corresponding panel will be enabled to use. 
	
	public void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for(int i = 0; i < components.length; i++) {
	        if(components[i].getClass().getName() == "javax.swing.JPanel") {
	            setPanelEnabled((JPanel) components[i], isEnabled);
	        }

	        components[i].setEnabled(isEnabled);
	    }
	}
	
// createTributaryTree is the main initialization method that takes the text file input by the user and builds the 
// fully threaded binary tree. 
	
	public void createTributaryTree() throws IOException
	{
		
		destHeader = new DestinationNode();
		
		InputStream ws = new FileInputStream(waterFile);
		InputStreamReader wsr = new InputStreamReader(ws);
		wbr = new BufferedReader(wsr);
		
		String tributaryLn;
		String tribData[];
		String destName;
		
		int level, prevLevel;
		TributaryNode prevNode = null;
				
		while((destName = wbr.readLine()) != null)
		{	
			numTribs = 0;
			DestinationNode currDest = new DestinationNode();
				currDest.setDestination(destName);
				destHeader.getRightX(numDestinations).setRight(currDest);
				currDest.setRight(destHeader);
				currDest.setLeft(new TributaryNode());
				currDest.getLeft().setLeft(currDest.getLeft());
				currDest.getLeft().setRight(currDest.getLeft());
				currDest.getLeft().setLeftThread(true);
				currDest.getLeft().setRightThread(false);
				numDestinations++;
				
			eDialog.locationCBB.addItem(destName);		
			locCBB.addItem(destName);
			locationCBB.addItem(destName);
			eDialog.destDelCBB.addItem(destName);
			eDialog.renameDestCBB.addItem(destName);
			
			S.push(new TempTribData(0, currDest.getLeft()));
				
			while(!(tributaryLn = wbr.readLine()).equals("$$$$$"))
			{
				tributaryLn.trim();
				tributaryLn = tributaryLn.replace("\t", "");
				tribData = tributaryLn.split(" ");
				
				TributaryNode currTrib = new TributaryNode();
					currTrib.setTribName(tribData[1]);
				level = Integer.parseInt(tribData[0]);
					currTrib.setLevel(level);
					
				prevLevel = S.peek().getLevelNumber();				
				prevNode = S.peek().getTributaryNode();				
				
				discoverCBB.addItem(currTrib.getTribName());
				eDialog.renameCBB.addItem(currTrib.getTribName());
				eDialog.deleteCBB.addItem(currTrib.getTribName());
				eDialog.flowsCBB.addItem(currTrib.getTribName());
				
					if(level > prevLevel)
					{	

						currTrib.setRight(prevNode);
						currTrib.setRightThread(true);
						currTrib.setLeft(prevNode.getLeft());
						currTrib.setLeftThread(prevNode.isLeftThread());
						prevNode.setLeft(currTrib);
						prevNode.setLeftThread(false);
											
						S.push(new TempTribData(level, currTrib));
					}
					else
					{
						while(prevLevel > level)
						{
							TempTribData temp = S.pop();
							prevLevel = temp.getLevelNumber();
							prevNode = temp.getTributaryNode();
						}
						if(prevLevel < level)
						{
							System.out.println("Error");
							return;
						}
						else
						{
							currTrib.setLeft(prevNode);
							currTrib.setLeftThread(true);
							currTrib.setRight(prevNode.getRight());
							currTrib.setRightThread(prevNode.isRightThread());
							prevNode.setRight(currTrib);
							prevNode.setRightThread(false);							
						
					    	S.push(new TempTribData(level, currTrib));
						}
					}			
				numTribs++;	
			}
			while(!S.empty())
			{
				S = new Stack<TempTribData>();
			}
		}
	}

// delete is a method that will locate a specified tributary node from the parameters and remove it from the tree. 
// There are different cases for the deletion and each one changes how threads post-deletion are handled. 
	
    public  void delete(String location, String deleteName) {
    	
        DestinationNode nextDest = destHeader;
        while (nextDest != null) {

            if (nextDest.getDestination().equals(location)) {

                TributaryNode ptr = nextDest.getLeft();
                TributaryNode temp = nextDest.getLeft();
                TributaryNode prev = new TributaryNode();
                prev.setLeft(null);
                prev.setRight(null);

                do {
                    temp = preorderSuccessor(temp);
                    if (temp != null) 
                    {
                        if (temp != ptr) 
                        {
                            if (temp.getTribName().equals(deleteName)) 
                            {
// First child:
                                if (temp == prev.getLeft()) 
                                {
                                    if (temp.isRightThread()) 
                                    {
                                        prev.setLeft(temp.getLeft());
                                        prev.setLeftThread(true);
                                    } 
                                    else
                                    {
                                        prev.setLeft(temp.getRight()); 
                                    }
                                }
// Last child node: 
                                else if (temp.isRightThread()) 
                                {
                                    if (prev != null) 
                                    {
                                        prev.setRight(temp.getRight()); 
                                        prev.setRightThread(true);
                                    }
                                }
// Middle child node:
                                else 
                                { 
                                        prev.setRight(temp.getRight());
                                        temp.getRight().setLeft(prev);
                                        temp.getRight().setLeftThread(true);
                                }
// Has child nodes:
                                if (!temp.isLeftThread()) 
                                {
                                    TributaryNode next = temp.getLeft();

                                    while(next.getRight() != temp) 
                                    {
                                        next = next.getRight();
                                    }
                                    next.setRight(prev);
                                }
// Next has child nodes: 
                                if (temp.getRight().isLeftThread()) 
                                {
                                    temp.getRight().setLeft(temp.getLeft());

                                } 
                                else 
                                {
                                    TributaryNode next = temp.getRight().getLeft(); 
                                    while(!next.isLeftThread()) 
                                    {
                                        next = next.getLeft();
                                    }
                                    next.setLeft(temp.getLeft());
                                }
                                temp.setLeftThread(false);
                                temp.setLeft(null);
                                temp.setRightThread(false);
                                temp.setRight(temp);
                                break;
                            }
                        }
                    }
                    prev = temp;
                } while (temp != ptr);
                break;
            }
            nextDest = nextDest.getRight();
        }
    }
    
// addNode is a method that takes specifications of a new Tributary Node, where its located, what descendant it is of its parent node "next" and a name for it.   
// There are different if statements depending on where the insert is, threads are reattached appropriately. 
    
	public void addNode(String dest, int kDesc, String next, String newName)
	{
        DestinationNode nextLoc = destHeader;

        while (nextLoc != null) {

            if (nextLoc.getDestination().equals(dest)) {

                TributaryNode ptr = nextLoc.getLeft();
                TributaryNode temp = nextLoc.getLeft();

                do {
                    temp = preorderSuccessor(temp);
                    if (temp != null) {
                        if (temp != ptr) {

                            TributaryNode trib = new TributaryNode();
                            trib.setTribName(newName);
                            trib.setLevel(kDesc);

                            if (temp.getTribName().equals(next)) {
                                if (kDesc > temp.getLevel()) {

                                    trib.setRight(temp);

                                    trib.setRightThread(true);

                                    trib.setLeft(temp.getLeft());
                                    trib.setLeftThread(temp.isLeftThread()); 

                                    temp.setLeft(trib);
                                    temp.setLeftThread(false);

                                } else {

                                    if (trib.getLevel() < kDesc) {
                                        System.out.println("error");
                                        return;
                                    } else {

                                        trib.setLeft(temp);

                                        trib.setLeftThread(true);

                                        trib.setRight(temp.getRight());
                                        trib.setRightThread(temp.isRightThread());

                                        temp.setRight(trib);
                                        temp.setRightThread(false);
                                    }
                                }
                            }
                        }
                    }

                } while (temp != ptr);

                break;
            }
            nextLoc = nextLoc.getRight();
        }
	}
	
// rename finds the specified tributary node from the combo box selections and changes the tribName variable to what the user put in the text field. 
	
	public void rename(String location, String toRename, String newName)
	{
		DestinationNode tempDest = destHeader;
		tempDest = tempDest.getRight();
		
		if(tempDest != null)
		{
			TributaryNode temp = tempDest.getLeft();
			TributaryNode stopper= tempDest.getLeft();
			
			if(tempDest.getDestination().equals(location))
			{
				do
				{
				temp = preorderSuccessor(temp);
				if(temp != null)
				{
					if(temp.getTribName().equals(toRename))
					{
						temp.setTribName(newName);
					}
				}
				} while (temp != stopper);				
			}
			tempDest = tempDest.getRight();
		}
	}
	
// displayPreorder simply uses the preorderSuccessor to traverse the tree and outputs at each visited node, thus displaying in preorder.	
	
	public void displayPreorder(TributaryNode potn)
	{
		TributaryNode temp = potn;
		textArea.setText("");
		do
		{
			temp = preorderSuccessor(temp);
			if(temp != null)
			{
				if(temp != potn)
				{
					textArea.append(temp.getTribName() + "\n");
				}
			}
		} while(temp != potn);
	}
	
// preorderSuccessor is a breat and butter method in this program that is in charge of traversing each node
// in the fully threaded naturally corresponding binary tree one time and in preorder. Whenever we want to iterate 
// through the data structure we call this method in a loop.
	
	public TributaryNode preorderSuccessor(TributaryNode t)
	{
		if (t == null)
		{
			return null;
		}
		if(!(t.isLeftThread()))
		{
			return t.getLeft();
		}
		else
		{
			TributaryNode p = t;
			while(p.isRightThread())
			{
				p = p.getRight();
			}
			return p.getRight();
		}
	}
	
// displayPreorder traverses the stack using preorderSuccessor then uses a Stack to push the preorder and then
// pops the preorder into output to see reverse preorder. 
	
	public void displayReverse(TributaryNode potn)
	{
		Stack<String> stack = new Stack<String>();
		TributaryNode temp = potn;
		textArea.setText("");
		do
		{
			temp = preorderSuccessor(temp);
			if(temp != null)
			{
				if(temp != potn)
				{
					stack.push(temp.getTribName());
				}
			}
		} while(temp != potn);
		while(!stack.isEmpty())
		{
			textArea.append(stack.pop() + "\n");
		}
	}
	
// update is a method that will keep all combo boxes up to date after insertions, deletions, and renaming so that a 
// user cannot select a combo box item that no longer exists in the database. 
	
	public void update(TributaryNode potn)
	{
		TributaryNode temp = potn;
		textArea.setText("");
		do
		{
			temp = preorderSuccessor(temp);
			if(temp != null)
			{
				if(temp != potn)
				{
					discoverCBB.addItem(temp.getTribName());
					eDialog.renameCBB.addItem(temp.getTribName());
					eDialog.deleteCBB.addItem(temp.getTribName());
					eDialog.flowsCBB.addItem(temp.getTribName());
				}
			}
		} while(temp != potn);
	}
	
	
// immediateTribs locates the tributary node given location and tributary name parameters then asks for a left link.
// If no left link exists, then there are no child nodes and consequently no immediate tributaries. If a left link does
// exist then start there, and traverse all its right links to visit sibling nodes and output. 
	
	public void immediateTribs(String location, String trib)
	{
		textArea.setText("The immediate Tributaries for " + trib + " are: " + "\n" + "\n");
		
		DestinationNode tempDest = destHeader;
		tempDest = tempDest.getRight();
		
		while(!tempDest.getDestination().equals(location))
		{
			tempDest = tempDest.getRight();
		}
		
		TributaryNode tempTrib = tempDest.getLeft();
		TributaryNode iterateTrib = new TributaryNode();
		
		do
		{
			tempTrib = preorderSuccessor(tempTrib);
			
		} while (!tempTrib.getTribName().equals(trib));
		
		if(tempTrib.isLeftThread())
		{
			textArea.append("None. This waterway has no tributaries."); 
		}
		if(!tempTrib.isLeftThread())
		{
			iterateTrib = tempTrib.getLeft();
			textArea.append(iterateTrib.getTribName());
			while(!iterateTrib.isRightThread())
			{
				iterateTrib = iterateTrib.getRight();
				textArea.append("\n" + iterateTrib.getTribName());
			}
		}
	}
	
// supplyingTribs will locate the tributary node in question given the input parameters of a location and tributary name. Once located, 
// we will run the recursive method to traverse the nodes' left subtree and output each node visited since these are all suppliers. 
	
	public void supplyingTribs(String location, String trib)
	{
		Stack<TributaryNode> nodeStack = new Stack<TributaryNode>();
		textArea.setText("The supplying Tributaries for " + trib + " are: " + "\n");
		
		DestinationNode tempDest = destHeader;
		tempDest = tempDest.getRight();
		
		while(!tempDest.getDestination().equals(location))
		{
			tempDest = tempDest.getRight();
		}
		
		TributaryNode tempTrib = tempDest.getLeft();
		TributaryNode iterateTrib = new TributaryNode();
		
		do
		{
			tempTrib = preorderSuccessor(tempTrib);
			
		} while (!tempTrib.getTribName().equals(trib));
		if(tempTrib.isLeftThread())
		{
			textArea.append("None. This waterway has no tributaries.");
		}
		else
		{
			recSubTree(tempTrib);
		}
	}
	
	
// recSubTree short for Recursive SubTree, recursively traverses the left subtree of a given node since every node
// in the left subtree is a supplying tributary as everything in the left subtree is a child of the given tributary node.
// If the input node has no left child (its threaded) then we exit the method, if it does, we want to go left and visit
// all right link nodes (siblings) and also check their left links (child nodes of the sibling nodes) as these all supply the 
// input node. 
	
	public void recSubTree(TributaryNode t)
	{
		TributaryNode temp;
		
		if(t.isLeftThread())
		{
			return;
		}
		else
		{
			temp = t.getLeft();
			while(!temp.isRightThread())
			{
				textArea.append("\n" + temp.getTribName());
				recSubTree(temp);
				temp = temp.getRight();
			}
			textArea.append("\n" + temp.getTribName());
		}
	}
	
// traceToDestination is a method that will, given String parameters detailing a particular node, find that node
// and iterate to its farthest right sibling and climb the tree using right threads to locate each parent node it
// passes until it reaches the Header node. It prints as it goes, thus outputting the water flow and final destination. 
	
public void traceToDestination(String location, String trib)
	 {
		textArea.setText("");
		textArea.append("Water from this tributary will flow to these water systems respectively: " + "\n" + "\n");
		String traceStr = "";

		DestinationNode tempDest = destHeader;
		tempDest = tempDest.getRight();
		
		while(!tempDest.getDestination().equals(location))
		{
			tempDest = tempDest.getRight();
		}		
		TributaryNode temp = tempDest.getLeft();		
		do
		{
			temp = preorderSuccessor(temp);	
		} while (!temp.getTribName().equals(trib));

		while (temp.getTribName().compareTo("Header") != 0)
		{ 
			if(temp.isRightThread())
			{
				temp = temp.getRight();
				if(temp.getTribName().equals("Header"))
				{
					// Don't do it, don't add Header to the display please no. 
				}
				else
				{
					traceStr = traceStr + temp.getTribName() + "\n";
				}
			}
			else
			{
				while(!temp.isRightThread())
				{
					temp = temp.getRight();
				}
			}
		}
		textArea.append(traceStr);
	}

// displayLevelOrder is a method that will start at the root (non-header) tributary node of the threaded tree
// in a specified location. It will start with the root and iterate down the left and right subtrees adding new
// nodes onto the stack and popping the old ones until it has gone through everything. It will display level numbers
// and Tributary names in the order it traverses; which should, in theory, be top to bottom in level orders. 

public void displayLevelOrder(String location)
{
	textArea.setText("");
	DestinationNode tempDest = destHeader;
	while(!tempDest.getDestination().equals(location))
	{
		tempDest = tempDest.getRight();
	}
		
	TributaryNode root = tempDest.getLeft().getLeft();	
	
    Stack<TributaryNode> myStack = new Stack<TributaryNode>();
    myStack.clear();
    myStack.push(root);
    String builder = "";
    
   while(!myStack.isEmpty())
   {
	   TributaryNode tempNode = (TributaryNode) myStack.pop(); 
	   builder = builder + tempNode.getLevel() + " " + tempNode.getTribName() + "\n";
	   if(!tempNode.isLeftThread())
	   { 
		   myStack.add(tempNode.getLeft());
	   }
	   if(!tempNode.isRightThread())
	   { 
		   myStack.add(tempNode.getRight());
	   }
   	}
   	textArea.append(builder);
}

// This class acts as a replacement for a tuple in java, that way I can stack this class 
// containing a Level Order and Tributary Node when building the initial threaded tree. 

	public class TempTribData
	{
		private int levelNumber;
		private DestinationNode destNode = null;
		private TributaryNode tribNode = null;
		
		public TempTribData(int x, DestinationNode dN)
		{
			this.levelNumber = x;
			this.destNode = dN;
		}
		public TempTribData(int x, TributaryNode tN)
		{
			this.levelNumber = x;
			this.tribNode = tN;
		}
		public TempTribData()
		{
			
		}
		public void setLevelNumber(int x)
		{
			this.levelNumber = x;
		}
		public int getLevelNumber()
		{
			return this.levelNumber;
		}
		public void setDestNode(DestinationNode dN)
		{
			this.destNode = dN;
		}
		public DestinationNode getDestinationNode()
		{
			return this.destNode;
		}
		public void setTribNode(TributaryNode tN)
		{
			this.tribNode = tN;
		}
		public TributaryNode getTributaryNode()
		{
			return this.tribNode;
		}
	}

// Destination Node class containing the left/right links, a name, and the appropriate get and set methods.
// I created a getRightX method that will iterate through the getRight method to help locate destinations. 
	
	public class DestinationNode
	{
		private int levelNum = 0;
		private DestinationNode rightLink;
		private TributaryNode leftLink;
		private String destination;
		private int numTributaries = 0;
		
		public DestinationNode()
		{
			this.rightLink = null;
			this.leftLink = null;
			this.destination = "Header";
			this.numTributaries = 0;
			this.levelNum = 0;
		}
		public int getLevelNum()
		{
			return this.levelNum;
		}
		public void setRight(DestinationNode dN)
		{
			this.rightLink = dN;
		}
		public DestinationNode getRight()
		{
			return this.rightLink;
		}
		public DestinationNode getRightX(int x)
		{
			int y = 0;
			DestinationNode temp = this;
			while(y < x)
			{
				temp = temp.getRight();			
				y++;
			}
			return temp;
		}			
		public void setLeft(TributaryNode tN)
		{
			this.leftLink = tN;
		}
		public TributaryNode getLeft()
		{
			return this.leftLink;
		}
		public void setDestination(String d)
		{
			this.destination = d;
		}
		public String getDestination()
		{
			return this.destination;
		}
		public void setNumTributaries(int x)
		{
			this.numTributaries = x;
		}
		public int getNumTributaries()
		{
			return this.numTributaries;
		}
	}
	
// This is the Tributary Node class containing left/right links, booleans indicating if the link is a thread, 
// data for the tributary name and level number. Appropriate get/set methods included and the constructor creates a header node. 
	
	public class TributaryNode 
	{
		private boolean leftThread = false;
		private boolean rightThread = false;
		private TributaryNode rightLink;
		private TributaryNode leftLink;
		private String tribName;
		private int levelNum;
		
		public TributaryNode()
		{
			leftThread = false;
			rightThread = false;
			rightLink = null;
			leftLink = null;
			tribName = "Header";
		}	
		public void setLevel(int x)
		{
			this.levelNum = x;
		}
		public int getLevel()
		{
			return this.levelNum;
		}
		public void setRightThread(boolean b)
		{
			this.rightThread = b;
		}
		public boolean isRightThread()
		{
			return this.rightThread;
		}
		public void setLeftThread(boolean b)
		{
			this.leftThread = b;
		}
		public boolean isLeftThread()
		{
			return this.leftThread;
		}
		public void setRight(TributaryNode tN)
		{
			this.rightLink = tN;
		}
		public TributaryNode getRight()
		{
			return this.rightLink;
		}
		public void setLeft(TributaryNode tN)
		{
			this.leftLink = tN;
		}
		public TributaryNode getLeft()
		{
			return this.leftLink;
		}
		public void setTribName(String name)
		{
			this.tribName = name;
		}
		public String getTribName()
		{
			return this.tribName;
		}
	}
}

