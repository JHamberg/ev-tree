package utility;

import evtree.EVNode;
import evtree.EVTree;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Simple EVTree traverser.
 * @author Jonatan Hamberg
 */
public class Traverser {
    private final EVTree tree;
    private final HashMap<String, Object> info;
    
    /**
     * Creates a traverser.
     * @param tree EVTree
     * @param info Device information
     */
    public Traverser(EVTree tree, HashMap<String, Object> info){
        this.tree = tree;
        this.info = info;
    }
    
    // This method is incomplete
    public void getSuggestions() {
        EVNode root = tree.getRoot();
        ArrayList<EVNode> children;
        while(!(children = root.getChildren()).isEmpty()){
            for(EVNode node : children){
                // System.out.println(node.getSplit());
                root = node;
            }
        }
    }
    
}
