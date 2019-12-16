package TWB;
import TWB.Parse_TWB;



import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadTWB extends JPanel implements ActionListener {
    
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
    public LoadTWB() {
        super(new BorderLayout());
//Create the log first, because the action listeners
//need to refer to it.
log = new JTextArea(20,40);
log.setMargin(new Insets(5,5,5,5));
log.setEditable(false);
JScrollPane logScrollPane = new JScrollPane(log);
log.append("TWB Parser V-2.0: July 2018"+newline+" Created by Praveen Kumar "+newline+ "https://github.com/pkumar5"+newline);
log.append("Extracts Calculations and Datasources from a Tableau TWB file");
log.append(newline+"Click Button: Get TWB file.."+newline);

//Create a file chooser
fc = new JFileChooser();
FileNameExtensionFilter filter = new FileNameExtensionFilter("twb", "twb");
fc.setFileFilter(filter);

openButton = new JButton("Get TWB File..");

openButton.addActionListener(this);

//Create the save button.  We use the image from the JLF
//Graphics Repository (but we extracted it from the jar).
saveButton = new JButton("Parse TWB File..");

saveButton.addActionListener(this);

//For layout purposes, put the buttons in a separate panel
JPanel buttonPanel = new JPanel(); //use FlowLayout
buttonPanel.add(openButton);
buttonPanel.add(saveButton);

//Add the buttons and the log to this panel.
add(buttonPanel, BorderLayout.PAGE_START);
add(logScrollPane, BorderLayout.CENTER);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(LoadTWB.this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                
                log.append(newline+"Selected " + file.getName() + " Located at: " + newline);
                log.append(file.getAbsolutePath() + "." + newline);
                log.append(newline+"Click 'Parse TWB' to parse File"+newline);
                
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
            
            //Handle save button action.
        } else if (e.getSource() == saveButton) {
            File file = fc.getSelectedFile();
            
            if (file != null){
                Parse_TWB.parseTWB(file.getAbsolutePath());
                log.append("Done!"+newline+ "Saved as : " + file.getName() + "_Calculations.txt" + newline);
            }
            else {
            log.append("No File Found!"+newline);
            }
                        
            log.setCaretPosition(log.getDocument().getLength());
            
        }
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LoadTWB.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Get TWB Calculations:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new LoadTWB());
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}

