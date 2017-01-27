import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

public class Lab4 extends GUI implements ActionListener
{
	File ascFile, missingFile, presentFile;
	
	InputStream is = null;
	InputStreamReader isr;
	BufferedReader br;
	
	String[] sortedArray;
	String[] missingKeys;
	String[] presentKeys;
	
	int skip;
	int search;
	int comparisons;
	
	
	public static void main(String[] args) 
	{
		Lab4 l4 = new Lab4();
		l4.initialize();
		l4.frame.setVisible(true);
	}

	public Lab4()
	{
		getMissingButton.addActionListener(this);
		getPresentButton.addActionListener(this);
		getAscButton.addActionListener(this);
		loadButton.addActionListener(this);
		searchButton.addActionListener(this);
		rdbtn100_0.addActionListener(this);
		rdbtn200_50.addActionListener(this);
		rdbtn150_100.addActionListener(this);
		rdbtn500_0.addActionListener(this);
	}


	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == getAscButton)
		{
			ascFile = getFile();
			ascLbl.setText(ascFile.getName());
		}
		if(e.getSource() == getMissingButton)
		{
			missingFile = getFile();
			missingLbl.setText(missingFile.getName());
		}
		if(e.getSource() == getPresentButton)
		{
			presentFile = getFile();
			presentLbl.setText(presentFile.getName());
		}
		if(e.getSource() == rdbtn100_0 || e.getSource() == rdbtn150_100 || e.getSource() == rdbtn200_50 || e.getSource() == rdbtn500_0)
		{
			if(ascFile != null && missingFile != null && presentFile != null)
			{
				loadButton.setEnabled(true);
			}
		}
		if(e.getSource() == loadButton)
		{
			try 
			{
				loadData(ascFile);
				loadData(missingFile);
				loadData(presentFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			searchButton.setEnabled(true);
		}
		if(e.getSource() == searchButton)
		{
			if(seqRdBtn.isSelected())
			{
				int compTotalPres = 0;
				int compTotalMiss = 0;
				int compAvgPres = 0;
				int compAvgMiss =0;
				for(int i = 0; i < presentKeys.length; i++)
				{
					System.out.println(sequentialSearch(sortedArray, presentKeys[i], 5000) + " Comparisons: " + comparisons);
					compTotalPres += comparisons;
				}
				for(int i = 0; i < missingKeys.length; i++)
				{
					System.out.println(sequentialSearch(sortedArray, missingKeys[i], 5000) + " Comparisons: " + comparisons);
					compTotalMiss += comparisons;
				}
				
				System.out.println("Comparisons for Present Keys: " + compTotalPres + " //  Comparisons for Missing Keys: " + compTotalMiss);
				compAvgPres = compTotalPres / presentKeys.length;
				compAvgMiss = compTotalMiss / missingKeys.length;
				System.out.println("Average comparisons for Present Keys: " + compAvgPres + " //  Average comparisons for Missing Keys: " + compAvgMiss);
			}
			if(binaryRdBtn.isSelected())
			{
				int compTotalPres = 0;
				int compTotalMiss = 0;
				int compAvgPres = 0;
				int compAvgMiss =0;
				for(int i = 0; i < presentKeys.length; i++)
				{
					System.out.println(i + " " + binarySearch(sortedArray, 0, 5001, presentKeys[i]) + " Comparisons: " + comparisons);
					compTotalPres += comparisons;
				}
				for(int i = 0; i < missingKeys.length; i++)
				{
					System.out.println(i + " " + (binarySearch(sortedArray, 0, 5001, missingKeys[i]) + " Comparisons: " + comparisons));
					compTotalMiss += comparisons;
				}
				
				System.out.println("Comparisons for Present Keys: " + compTotalPres + " //  Comparisons for Missing Keys: " + compTotalMiss);
				compAvgPres = compTotalPres / presentKeys.length;
				compAvgMiss = compTotalMiss / missingKeys.length;
				System.out.println("Average comparisons for Present Keys: " + compAvgPres + " //  Average comparisons for Missing Keys: " + compAvgMiss);
			}
			if(interpolationRdBtn.isSelected())
			{
				int compTotalPres = 0;
				int compTotalMiss = 0;
				int compAvgPres = 0;
				int compAvgMiss =0;
				
				for(int i = 0; i < presentKeys.length; i++)
				{
					System.out.println(i + " " + interpolationSearch(sortedArray, 5000, presentKeys[i]) + " Comparisons: " + comparisons);
					compTotalPres += comparisons;
				}
				for(int i = 0; i < missingKeys.length; i++)
				{
					System.out.println(i + " " + (interpolationSearch(sortedArray, 5000, missingKeys[i]) + " Comparisons: " + comparisons));
					compTotalMiss += comparisons;
				}
			
				System.out.println("Comparisons for Present Keys: " + compTotalPres + " //  Comparisons for Missing Keys: " + compTotalMiss);
				compAvgPres = compTotalPres / presentKeys.length;
				compAvgMiss = compTotalMiss / missingKeys.length;
				System.out.println("Average comparisons for Present Keys: " + compAvgPres + " //  Average comparisons for Missing Keys: " + compAvgMiss);
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
	
	public void loadData(File f) throws IOException
	{	
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}	
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);

		if(f == ascFile)
		{
			sortedArray = new String[5001];
			for (int i = 0; i < 5000; i++)
			{
				try {
					sortedArray[i] = br.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(f == missingFile)
		{
			if(rdbtn100_0.isSelected())
			{
				search = 100;
				skip = 0;
				missingKeys = new String[100];
			}
			else if(rdbtn150_100.isSelected())
			{
				search = 150;
				skip =100;
				missingKeys = new String[150];
			}
			else if(rdbtn200_50.isSelected())
			{
				search = 200;
				skip = 50;
				missingKeys = new String[200];
			}
			else if(rdbtn500_0.isSelected())
			{
				search = 500;
				skip = 0;
				missingKeys = new String[500];
			}
			for(int i = 0; i < skip; i++)
			{
				br.readLine();
			}
			for(int j = 0; j < search; j++)
			{
				missingKeys[j] = br.readLine();
			}
		}
		else if(f == presentFile)
		{
			if(rdbtn100_0.isSelected())
			{
				search = 100;
				skip = 0;
				presentKeys = new String[100];
			}
			else if(rdbtn150_100.isSelected())
			{
				search = 150;
				skip =100;
				presentKeys = new String[150];
			}
			else if(rdbtn200_50.isSelected())
			{
				search = 200;
				skip = 50;
				presentKeys = new String[200];
			}
			else if(rdbtn500_0.isSelected())
			{
				search = 500;
				skip = 0;
				presentKeys = new String[500];
			}
			for(int i = 0; i < skip; i++)
			{
				br.readLine();
			}
			for(int j = 0; j < search;j++)
			{
				presentKeys[j] = br.readLine();
			}
		}
		else 
		{
			return;
		}
	}
	public int sequentialSearch(String[] t, String k, int n)
	{
		comparisons = 0;
		
		t[n] = k;
		int i =0;
		while(comparisons++ >= 0 && !t[i].equals(k))
		{
			i++;
		}
		if(i < n) return i;
		else return -1;
	}
	public int binarySearch(String[ ] array, int lowerbound, int upperbound, String key)
	{
	int position;
	int n = upperbound;
	comparisons = 0; 

	position = ( lowerbound + upperbound) / 2;

	     while(comparisons++ >= 0 && (!array[position].equals(key)) && (lowerbound <= upperbound))
	     {
	        comparisons++;
	        
	        if (array[position].compareTo(key) > 0)             
	        {                                             
	              upperbound = position - 1;   
	        }                                                             
	              else                                                   
	        {                                                        
	              lowerbound = position + 1;    
	        }

	       position = (lowerbound + upperbound) / 2;

	     }
	     if (lowerbound <= upperbound)
	     {
	         return position;
	     }
	     else
	    	 return -1;
	  }
	public int interpolationSearch(String[] t, int n, String k)
	{
		comparisons = 0;
		
		int[] sortedIntArray = new int[5002];
		sortedIntArray[0] = -1;
		for(int i = 0; i < t.length - 1; i++)
		{
			char[] charKeys = sortedArray[i].toCharArray();
			sortedIntArray[i+1] = horners(charKeys[0], charKeys[1], charKeys [2], charKeys[3]);
		}
		sortedIntArray[5001] = 999999999;
				
		char[] keyChars = k.toCharArray();
		int keyInt = horners(keyChars[0], keyChars[1], keyChars[2], keyChars[3]);
		
		int left = 0;
		int right = n - 1;
		int middle;
		
		while(left <= right && keyInt >= sortedIntArray[left] && keyInt <= sortedIntArray[right])
		{
				int p = left + (((right -left) / (sortedIntArray[right] - sortedIntArray[left]) * (keyInt - sortedIntArray[left])));
				// middle = (int) (Math.floor(p * (right - left + 1)) + left);
			
				if (comparisons++ > 0 && keyInt == sortedIntArray[p])
				{
					return p;
				}
				else if (comparisons++ > 0 && keyInt > sortedIntArray[p])
				{
					right = p - 1;
				}
			else left = p + 1;
		}
		return -1;
	}
	public int horners(char c1, char c2, char c3, char c4)
	{
		return (int)((((((int)c1<<8)+(int)c2)<<8)+(int)c3)<<8)+(int)c4;
	}

}

