/*  Dictionary
 *  Main Class 
 *  Natali Boniel, 201122140 */

package Q2;

import javax.swing.JFrame;

public class MainDictionary {
	
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DictionaryPanel d = new DictionaryPanel();
        frame.add(d);
        frame.setSize(620,620);
        frame.setVisible(true);
        
    }
}
