/*  Dictionary
 *  Dictionary
 *  Natali Boniel, 201122140 */

package Q2;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.Serializable;

public class Dictionary implements Serializable{
	
	private HashMap<String,String> dictionaryList;
    
	public Dictionary()
	{
		dictionaryList = new HashMap<String,String>();  
	}

	public void cheackIfNeedAdd(String term) throws TermExistException
	{
	    if(dictionaryList.containsKey(term))
	        throw new TermExistException("WARNING: Term " + term + " is already exists");    
	}

	public void removeTerm(String term) throws TermNotExistException
	{
	     if(!dictionaryList.containsKey(term))
	        throw new TermNotExistException("WARNING: Term " + term + " is not exists");
	     dictionaryList.remove(term);
	}

	public void cheackIfNeedUpdate(String term) throws TermNotExistException
	{
	     if(!dictionaryList.containsKey(term))
	        throw new TermNotExistException("WARNING: Term " + term + " is not exists");
	}

	public void updateTerm(String term, String def)
	{
		dictionaryList.put(term, def);
	}

	public String searchTerm(String term)throws TermNotExistException
	{
	    if(!dictionaryList.containsKey(term))
	        throw new TermNotExistException("WARNING: Term " + term + " is not exists");
	     return dictionaryList.get(term);
	}

	public Set getKeys()
	{
	    return dictionaryList.keySet();
	}

	public String getDef(String key)
	{
	    return dictionaryList.get(key);
	}
}
