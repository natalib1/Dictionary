/*  Dictionary
 *  Panel class
 *  Natali Boniel, 201122140 */

package Q2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DictionaryPanel extends JPanel{

	private Dictionary dictionaryList;
	private JButton addTermButton, removeTermButton, updateTermButton, searchTermButton, saveButton, loadButton;
	private File file;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	   
	public DictionaryPanel()
	{
		file = new File("dictionary.txt");
		dictionaryList = new Dictionary();
		JPanel bottomPanel = new JPanel();
		addTermButton = new JButton("Add Term And Definition");
		removeTermButton = new JButton("Remove Term And Definition");
		updateTermButton = new JButton("Update Term");
		searchTermButton = new JButton("Search Term");
		saveButton = new JButton("Save Dictionary File");
		loadButton = new JButton("Load Dictionary File");
		
		bottomPanel.setLayout(new GridLayout(2,3,10,10));
		bottomPanel.add(addTermButton);
		bottomPanel.add(removeTermButton);
		bottomPanel.add(updateTermButton);
		bottomPanel.add(searchTermButton);
		bottomPanel.add(saveButton);
		bottomPanel.add(loadButton);
		bottomPanel.setLayout(new GridLayout(2,1,10,10));
		this.setLayout(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
		 
		addTermButton.addActionListener(new AddTermListener());
		removeTermButton.addActionListener(new RemoveTermListener());
		updateTermButton.addActionListener(new UpdateTermListener());
		searchTermButton.addActionListener(new SearchTermListener());
		saveButton.addActionListener(new SaveListener()); 
		loadButton.addActionListener(new LoadListener());
				 
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		add(new JScrollPane(list),BorderLayout.CENTER);
		loadDictionary();
	}
	    
	private void loadDictionary()
	{
		try 
        {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try
            {      
            	dictionaryList =(Dictionary)in.readObject();
            	ShowDictionary();
            } 
            catch(ClassNotFoundException ex)
            {
                JOptionPane.showMessageDialog(null, "The file doesn't contain dictionary");
            }
        } 
        catch(IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "Error while opening file");
        }
	}

	private class AddTermListener implements ActionListener
	{	    
	    public void actionPerformed(ActionEvent e)
	    {
	        String term = JOptionPane.showInputDialog("Enter a term to add"); 
	        if(term != null && term.matches("[ a-zA-Z]+"))
	        {    
	        	try
	        	{
					term = term.trim();
					term = term.toLowerCase();
					dictionaryList.cheackIfNeedAdd(term);
					String def = JOptionPane.showInputDialog("Enter definition for the term you want to add");
					def = def.trim();
					if(def != null && def.matches("[ a-zA-Z0-9,']+") && !def.matches("[,']+"))
					{
		                dictionaryList.updateTerm(term,def);
		                ShowDictionary();
	                }
					else
						JOptionPane.showMessageDialog(null, "WARNING: Definition doesn't enter");
				}
	        	catch(TermExistException ex)
	        	{
	        		JOptionPane.showMessageDialog(null, ex.getMessage());
	        	}
	        }
	        else
	            JOptionPane.showMessageDialog(null, "WARNING: English please :)");
	    }
	}

	private class RemoveTermListener implements ActionListener
	{	    
	    public void actionPerformed(ActionEvent e)
	    {
	        String term = JOptionPane.showInputDialog("Enter a term to remove");
	        if(term != null && term.matches("[ a-zA-Z]+"))
	        {
	        	try
	        	{
		            term = term.trim();
		            term = term.toLowerCase();
		            dictionaryList.removeTerm(term);
		            ShowDictionary();
		        }
	        	catch(TermNotExistException ex)
	        	{
	        		JOptionPane.showMessageDialog(null, ex.getMessage());
	        	}
	        }
	        else
	            JOptionPane.showMessageDialog(null, "WARNING: English please :)");
	    }
	}

	private class UpdateTermListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String term = JOptionPane.showInputDialog("Enter a term to update");
	        if (term !=null && term.matches("[ a-zA-Z]+"))
	        {
	        	try
	        	{
		            term = term.trim();
		            term = term.toLowerCase();
		            dictionaryList.cheackIfNeedUpdate(term);
		            String def = JOptionPane.showInputDialog("Enter new definition for the term you want to update");
		            def = def.trim();
		            if(def != null && def.matches("[ a-zA-Z0-9,]+") && !def.matches("[,']+"))
		            {
		                dictionaryList.updateTerm(term, def);
		                ShowDictionary();
	                }
		            else
		            	JOptionPane.showMessageDialog(null, "WARNING: Definition doesn't enter");
	        	}
	        	catch(TermNotExistException ex)
	        	{
	        		JOptionPane.showMessageDialog(null, ex.getMessage());
	        	}
	        }
	        else
	            JOptionPane.showMessageDialog(null, "WARNING: English please :)");
	     }
	}

	private class SearchTermListener implements ActionListener
	{  
	   public void actionPerformed(ActionEvent e)
	   { 
		   String term = JOptionPane.showInputDialog("Enter a term to search");
	       if(term != null && term.matches("[ a-zA-Z]+"))
	       {
	    	   try
	    	   {
			       term = term.trim();
			       term = term.toLowerCase();
			       String def = dictionaryList.searchTerm(term);
			       JOptionPane.showMessageDialog(null, "TERM: \t\t" + term + "\tDEFINITION: \t" + def);
	    	   }
	    	   catch(TermNotExistException ex)
	    	   {
	    	   	   JOptionPane.showMessageDialog(null, ex.getMessage());
	    	   }
	    
	       }
	       else
	    	   JOptionPane.showMessageDialog(null, "WARNING: English please :)");
	    }
	}
	
	private class LoadListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
	    {
	    	JFileChooser fc = new JFileChooser();
	        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        int result = fc.showOpenDialog(null);
	        
	        if(result != JFileChooser.CANCEL_OPTION)
	        {
		        file = fc.getSelectedFile();
		        loadDictionary();  
	        }
	    }
	}

	private class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{ 
			try
			{
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)); 
				out.writeObject(dictionaryList);
				out.close();
	        }
	        catch(IOException ex) 
	        {
	            JOptionPane.showMessageDialog(null, "WARNING: Can't open file)");
	        }
		}
	}

	public void ShowDictionary()
	{
	    listModel.clear();
	    listModel.addElement("TERM: \t DEFINITION: \t");
	    
	    Set<String> keys = dictionaryList.getKeys();
	    TreeSet<String> sortedKeys = new TreeSet<String>(keys);
	    for (Iterator<String> iteratorGlossary = sortedKeys.iterator(); iteratorGlossary.hasNext();) 
	    {
	    	String nextKey = iteratorGlossary.next();
	    	listModel.addElement(nextKey + "              " + dictionaryList.getDef(nextKey));
	    }
	}

}
