package evtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import utility.Range;

/**
 * Simple EVTree implementation.
 * @author Jonatan Hamberg
 */
public class EVTree implements Serializable{

    EVNode root;

    /**
     * Initializes the tree.
     * @param root Root node
     */
    public EVTree(EVNode root) {
        this.root = root;
    }

    /**
     * @return Root node
     */
    public EVNode getRoot() {
        return root;
    }
    
    /**
     * Prints a tree of full depth.
     */
    public void print(){
        this.print(Integer.MAX_VALUE);
    }
    
    /**
     * Prints a tree of custom depth.
     * @param depth Depth of traversal
     */
    public void print(int depth) {
        print(root, "", depth);
    }
    
    /**
     * Gets a list of suggestions based on device information.
     * @param info Device information
     * @return List of suggestions
     */
    public ArrayList<String> getSuggestions(HashMap<String, Object> info){
        ArrayList<String> results = new ArrayList<>();
        
        // Loop through suggested nodes
        for(EVNode node : this.getSuggestedNodes(info)){
            String split = node.getSplit();
            String value = node.getValueString();
            if(value == null){
                value = node.getValueRange().toString();
            }
            results.add("-Change "+split+ " to "+value);
        }
        return results;
    }
        
    // Gets a list of suggested nodes
    private ArrayList<EVNode> getSuggestedNodes(HashMap<String, Object> info) {
        ArrayList<EVNode> suggestions = new ArrayList<>();
        EVNode node = root;
        TreeSet<EVNode> children;
        
        // Get children for current node
        while(!(children = node.getChildren()).isEmpty()){
            EVNode initial = node;
            String split = children.first().getSplit();
            EVNode bestNode = children.last();
            Object deviceValue = info.get(split); 
            
            for(EVNode child : children){
                String value = child.getValueString();
                Range range = child.getValueRange();
                
                // Check if value is in range or matching
                if((deviceValue instanceof Integer 
                        && range != null 
                        && range.contains(deviceValue))
                        || (deviceValue instanceof String
                        && value != null
                        && value.equalsIgnoreCase((String) deviceValue))){
                    
                     // Compare matching node to best
                   if(bestNode.getEv() < child.getEv()){
                       suggestions.add(bestNode);
                   }
                   // Traverse down
                   node = child;
                   break;
                }
            }
            // No matching child found
            if(node == initial) break;
        }
        
        return suggestions;
    }
    
    // Recursively prints the tree as a list
    private void print(EVNode root, String prefix, int depth){
        if(root == null || depth < 0) return;
        String value = root.getValueString();
        if(value == null){
            value = root.getValueRange().toString();
        }
        System.out.println(prefix 
                + "+- " + root.getSplit() 
                + ": " + value
                + " (" + root.getEv() + ")");
        for(EVNode child : root.getChildren()){
            print(child, prefix+"|  ", depth-1);
        }
    }
}
