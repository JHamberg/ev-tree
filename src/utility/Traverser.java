package utility;

import evtree.EVNode;
import evtree.EVTree;
import java.util.HashMap;
import java.util.TreeSet;

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
        TreeSet<EVNode> children;
        while(!(children = root.getChildren()).isEmpty()){
            for(EVNode node : children){
                // System.out.println(node.getSplit());
                root = node;
            }
        }
    }
    
}
