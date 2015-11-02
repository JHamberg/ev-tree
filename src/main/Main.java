package main;

import evtree.EVTree;
import java.io.File;
import javax.swing.JFileChooser;
import utility.Parser;
import java.io.FileNotFoundException;
import java.util.HashMap;
import utility.Traverser;

/**
 * @author Jonatan Hamberg
 */
public class Main {
    
    public static void main(String[] args) throws FileNotFoundException {
        File treeFile = openFile();
        EVTree tree = Parser.parseTree(treeFile);
        tree.print(3);
        
        // TODO: Finish traverser
        HashMap<String, Object> deviceInfo = new HashMap<>();
        Traverser t = new Traverser(tree, deviceInfo);
        t.getSuggestions();
    }

    private static File openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            System.out.println("No file selected.");
            System.exit(0);
        }
        return null;
    }
}
