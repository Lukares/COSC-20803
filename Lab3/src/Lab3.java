import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

public class Lab3 extends GUI implements ActionListener
{
	File fileToSort;
	BufferedReader sbr;
	InputStream ss = null;
	InputStreamReader ssr;
	int comparisons, moves, sortInt;
	String[] alphaNums;
	
	public static void main(String[] args) {
		Lab3 l3 = new Lab3();
		l3.initialize();
		l3.frame.setVisible(true);
	}
	public Lab3()
	{
		fileButton.addActionListener(this);
		bubbleSortBtn.addActionListener(this);
		quickBtn.addActionListener(this);
		shellBtn.addActionListener(this);
		cocktailBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == fileButton)
		{
			fileToSort = getFile();
			fileLabel.setText("*****< " + fileToSort.getName() + " >*****");
		}
		
		if(e.getSource() == bubbleSortBtn)
		{
			
			   loadData(fileToSort);
		
				try {
					bubbleSort(alphaNums , sortInt);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for (int k = 0; k < sortInt; k++)
				{
					System.out.println( (k + 1) + " " + alphaNums[k]);
				}
				
			System.out.println("Bubble Sort Comparisons: " + comparisons + " moves: " + moves);
		}
		
		if(e.getSource() == quickBtn)
		{
			loadData(fileToSort);

			quickSort(alphaNums, 0, sortInt - 1);
			
			for (int k = 0; k < sortInt; k++)
			{
				System.out.println( (k + 1) + " " + alphaNums[k]);
			}
			System.out.println("QuickSort Comparisons: " + comparisons + " moves: " + moves);
		}
		
		if(e.getSource() == shellBtn)
		{
				
			loadData(fileToSort);

			int tempSort = sortInt;
			int keysCounter = 1;
			Stack<Integer> keyStack = new Stack<Integer>();	
			keyStack.push(tempSort / 2);
			
			while(keyStack.peek() != 1)
			{	
				tempSort = keyStack.peek();
				keyStack.push(tempSort / 2);
				keysCounter++;
			}
			
			keyStack.push(0);
			keysCounter++;
			
			int[] keys = new int[keysCounter];
			int z = 0;
			
			while(!keyStack.isEmpty())
			{
				keys[z] = keyStack.pop();
				z++;
			}

			shellSort(alphaNums, keys,  sortInt);
			
			for (int k = 0; k < sortInt; k++)
			{
				System.out.println( (k + 1) + " " + alphaNums[k]);
			}
			
			System.out.println("Shell Sort Comparisons: " + comparisons + " moves: " + moves);
		}
		
		if(e.getSource() == cocktailBtn)
		{
			loadData(fileToSort);
			
			cocktailShakerSort(alphaNums, sortInt);
			
			for (int k = 0; k < sortInt; k++)
			{
				System.out.println( (k + 1) + " " + alphaNums[k]);
			}
			
			System.out.println("Cocktail Shaker Comparisons: " + comparisons + " moves: " + moves);
		}
	}
	public void loadData(File f)
	{	
		try {
			ss = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}	
		ssr = new InputStreamReader(ss);
	
		comparisons = 0;
		moves = 0;
	
		sbr = new BufferedReader(ssr);
		sortInt = (int) spinner.getValue();
		alphaNums = new String[sortInt];
	
		for (int i = 0; i < sortInt; i++)
		{
			try {
				alphaNums[i] = sbr.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public File getFile() throws NullPointerException
	{
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			bubbleSortBtn.setEnabled(true);
			quickBtn.setEnabled(true);
			shellBtn.setEnabled(true);
			cocktailBtn.setEnabled(true);
			
			return fc.getSelectedFile();
		}
		return null;
	}
	
	public void bubbleSort(String[] t, int n) throws IOException
	{
		boolean valuesWereInterchanged = true;
		int passCount = 1;
		
			while ((passCount <= (n-1)) && (valuesWereInterchanged))
				{
				valuesWereInterchanged = false;

				for (int j = 0; j <= (n - 1) - passCount; j++)
					{
					comparisons++;
					if (t[j].compareTo(t[j+1]) >= 0)
					{
						valuesWereInterchanged = true;

						String temp = t[j];
						t[j] = t[j+1];
						t[j+1] = temp;
						
						moves += 3;
					}
				}
			passCount++;
		}
	}
	
	public void quickSort(String[] t, int left, int right)
	{
		
		String temp = "";

		int i = left;
		int j = right + 1;
		
		if (left < right)
		{
			String pivot = t[i];
			
		while (j > i)
		{
			
			i++;
			j--;
			

			while ((i < right) && comparisons++ > 0 && (t[i].compareTo(pivot) <= 0) && (j > i)) 
			{
				i++;
			}	
			
			while ((j > left) && comparisons++ > 0 && (t[j].compareTo(pivot) >= 0) && (j >= i))
			{
				j--;
			}

			if (j > i)
			{
				temp = t[i];
				t[i] = t[j];
				t[j] = temp;	
				
				moves += 3;
			}
		}
		
		// move the pivot element into its proper position
		temp = t[j];
		moves++;
		t[j] = t[left];
		moves++;
		t[left] = temp;
		moves++;
		
		quickSort(t, left, j-1); 
		quickSort(t, j+1, right);
		
		}		
	}
	
	public void cocktailShakerSort(String[] t, int n)
	{
		boolean swapped;
		 do
		 {
			 swapped = false;
			 for (int i = 0; i <= (n - 2); i++)
			 {
				 if (comparisons++ > 0 && t[i].compareTo(t[i+1]) >= 0)
				 {
					 String temp = t[i];
					 t[i] = t[i + 1];
					 t[i + 1] = temp;
					 
					 moves += 3;
					 
					 swapped = true;
				 }
			 } 
			 
			 if (!swapped)
			 {
				 break;
			 }
			 swapped = false;
		 
			 for (int i = (n - 2); i >= 0; i--)
			 {
				 if (comparisons++ > 0 && t[i].compareTo(t[i + 1]) >= 0)
				 {
					 String temp = t[i];
					 t[i] = t[i + 1];
					 t[i + 1] = temp;
					 
					 moves += 3;
					 
					swapped = true;
				 }
			 }
		  }
		 while (swapped);
	} 
	
	public void shellSort(String[] t, int[] h, int n) 
	{
		int inc = h[h.length - 1];
		int tracker = 1;
		boolean didWeWhile = false;
		
		while (inc >= 1)
			{
			tracker++;
				for(int i = inc; i <= (n - 1); i++)
				{				
					int j = i;
					String X = t[i];
							
					while (j >= inc && comparisons++ > 0 && t[j - inc].compareTo(X) >= 0)
					{
						didWeWhile = true;
						t[j] = t[j - inc];
						moves++;
						j = j - inc;
					}
					
					t[j] = X;
					
					if(didWeWhile)
					{
						moves += 2;
					}
				}
				
				if(h.length - tracker >= 0)
				{
					inc = h[h.length - tracker];
				}
			}
	}
}
