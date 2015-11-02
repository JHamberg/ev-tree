package main;

import evtree.EVTree;
import java.io.File;
import javax.swing.JFileChooser;
import utility.Parser;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author Jonatan Hamberg
 */
public class Main {
    
    public static void main(String[] args) throws FileNotFoundException {
        File treeFile = openFile();
        EVTree tree = Parser.parseTree(treeFile);
        tree.print(3); // depth 3
        
        HashMap<String, Object> info = getDeviceInfo();
        System.out.println("\nSuggestions:");
        for(String suggestion : tree.getSuggestions(info)){
            System.out.println(suggestion);
        }
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
    
    // Similar data will ultimately come from device
    private static HashMap<String, Object> getDeviceInfo() {
        return new HashMap<String, Object>() {{
            put("batteryTemperature", 20);
            put("batteryHealth", "good");
            put("wifiSignalStrength", "other");
            put("wifiStatus", "enabled");
            put("networkType", "mobile");
            put("mobileNetworkType", "HSPA");
            put("mobileDataActivity", "inout");
            put("mobileDataStatus", "connected");
            put("cpuUsage", "other");
            put("screenBrightness", 50);
            put("distanceTraveled", 20);
        }};
    }
}
