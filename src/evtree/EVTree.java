package evtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import utility.Range;

/**
 * Simple EVTree implementation.
 * @author Jonatan Hamberg
 */
public class EVTree {

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
            String suggestion;
            String split = node.getSplit();
            Object value = node.getValue();
            
            // Detect value type
            if(value instanceof Range){
                Range range = (Range) value;
                int min = (int) range.getMin();
                int max = (int) range.getMax();
                suggestion = "["+min+" ,"+max+"]";
            } else {
                suggestion = (String) value;
            }
            results.add("-Change "+split+ " to "+suggestion);
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
            EVNode old = node;
            String currentSplit = children.first().getSplit();
            EVNode best = children.last();
            Object deviceValue = info.get(currentSplit); 
            
            // Todo, replace with children.get(deviceValue);
            for(EVNode child : children){
               Object nodeValue = child.getValue();
               
               // Device value matches the node value
               if((nodeValue instanceof Range 
                       && deviceValue instanceof Integer
                       && ((Range) nodeValue).contains(((Integer) deviceValue)))
                       || (nodeValue instanceof String 
                       && deviceValue instanceof String 
                       && nodeValue.equals(deviceValue))){
                   
                   // Compare matching node to best
                   if(best.getEv() < child.getEv()){
                       suggestions.add(best);
                   }
                   // Traverse down
                   node = child;
                   break;
               } 
            }
            // No matching child found
            if(node == old) break;
        }
        
        return suggestions;
    }
    
    // Recursively prints the tree as a list
    private void print(EVNode root, String prefix, int depth){
        if(root == null || depth < 0) return;
        String splitValue;
        Object value = root.getValue();
        if(value instanceof Range){
            Range range = (Range) value;
            splitValue = "["+range.getMin()+","+range.getMax()+"]";
        } else {
            splitValue = (String) value;
        }
        System.out.println(prefix 
                + "+- " + root.getSplit() 
                + ": " + splitValue
                + " (" + root.getEv() + ")");
        for(EVNode child : root.getChildren()){
            print(child, prefix+"|  ", depth-1);
        }
    }
}
