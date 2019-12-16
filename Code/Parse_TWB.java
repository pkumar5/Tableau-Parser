package TWB;

import java.awt.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;


public class Parse_TWB {    
	
    /**
     *
     * @param inputFile
     */
    public static void parseTWB(String inputFile) {
    	String DLine = "---------------------------------------------------------------"
    		        	+ "------------------------------------------------------------";                
        try {

                // Creating a File object that represents the disk file.
                PrintStream o = new PrintStream(new File(inputFile+"_Calculations.txt"));


                // Assign o to output stream to write output to Textfile.
                System.setOut(o);
                
                //Initiate XML document
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                System.out.println("\nTWB Parcer V-2.0 \n Created by: Praveen Kumar \n https://github.com/pkumar5"); 
                System.out.println();

                
                //get XML document subset for datasourse and coolumn
                NodeList datasourceList = doc.getElementsByTagName("datasource");
                NodeList columnList = doc.getElementsByTagName("column");   
                
                // Initiate Lists and Hashtables and global variables 
                int fl_counter = 1;
                int ds_counter = 1;                
                String calc_list = "";                
                ArrayList <Calculated_field> Calculation_list = new ArrayList();
                ArrayList <Data_source> Datasourse_list = new ArrayList();
                Hashtable <String, String> f_hashtable = new Hashtable <String, String>();
               

                
                //Parse XML for Calculated Fields                
                for (int counter = 0; counter < columnList.getLength(); counter++){                	
                    Node columnNode = columnList.item(counter);
                    Element columnElement = (Element) columnNode;
                    NodeList columnNodeList = columnNode.getChildNodes();                    
                                        
                    for(int i =0; i < columnNodeList.getLength(); i++){                    	
                        Node calcNode = columnNodeList.item(i);

		                if (calcNode.getNodeName()=="calculation"){		                	
		                    Element calculationElement = (Element) calcNode;
		                    
		                    if (calculationElement.getAttribute("formula").length()>0){		                    	
		                        String Name = columnElement.getAttribute("name");       
		                        
		                        if (!(calc_list.contains(Name))){
		                        	// Get Formula Data from XML
		                            calc_list = calc_list.concat(Name+" ");
		                            String caption = columnElement.getAttribute("caption");
		                            String role = columnElement.getAttribute("role");
		                            String ID = columnElement.getAttribute("name");
		                            String datatype = columnElement.getAttribute("datatype");
		                            String formula =  calculationElement.getAttribute("formula");
		                          //Save Calculated_field Objects
		                            Calculated_field calc = new Calculated_field(fl_counter, caption, role, ID, datatype, formula);
		                            fl_counter++;
		                            Calculation_list.add(calc);
		                            f_hashtable.put(ID,caption);		                            
		                        }                                       
		                    }
		                }
                    }
                }
                
                
                //Parse XML for DataSources
                for (int item = 0; item < datasourceList.getLength(); item++){
                    Node datasourceNode = datasourceList.item(item);
                    Element datasourceElement = (Element) datasourceNode;
                    if (datasourceElement.getAttribute("inline").length()>0 && datasourceElement.getAttribute("caption").length()>0){
 
                                                String caption = datasourceElement.getAttribute("caption");
                                                String ID = datasourceElement.getAttribute("name");
                                                //Save Datas_ource objects
                                                Data_source ds = new Data_source(ds_counter, caption, ID);
            		                            ds_counter++;
            		                            Datasourse_list.add(ds);
            		                            f_hashtable.put("["+ID+"]",caption);		

                    }
                }            
                
//                //Check Hashtable for content
//                Set s = f_hashtable.entrySet();
//        		Iterator it = s.iterator();
//        		while (it.hasNext()) {
//        			System.out.println(it.next());
//        		}

                
                String pattern  = "\\[Calculation_\\d*]";
                String ds_pattern = "\\[sqlproxy.\\d*.\\d*]";
                
                Pattern r = Pattern.compile(pattern);
                Pattern r2 = Pattern.compile(ds_pattern);
                
                String old_name = "" ;
                String new_name = "" ;
                
                
                // Prints Calculated fields
                System.out.print(Calculation_list.size());
                System.out.println(" Calculations Found:");
                System.out.println(DLine);                
                for (int i=0;i<Calculation_list.size();i++){
                            System.out.println(Calculation_list.get(i).displayDetails()); 
                            if(Calculation_list.get(i).displayFormula().length()>0) {  
                            	
                            	//temporary list of Tableau ID_Calculations                            	
                            	ArrayList tempList = new ArrayList();
                            	
                            	//Check for [Calculations] in formula string                           	
                            	Matcher m1 = r.matcher(Calculation_list.get(i).displayFormula());
                            	Matcher m2 = r2.matcher(Calculation_list.get(i).displayFormula());
                            	                 	
                            	while (m1.find()){
                            		if(!tempList.contains(m1.group(0))) {                            			
                            			tempList.add(m1.group(0));
//                            			System.out.println(m1.group(0));
                            		}                            		
                            	} 
                            	while (m2.find()){
                            		if(!tempList.contains(m2.group(0))) {                            			
                            			tempList.add(m2.group(0));
//                            			System.out.println(m2.group(0));
                            		}                            		
                            	} 
//                            	System.out.println(tempList.size());
                            	
                            	String New_calculation = (Calculation_list.get(i).displayFormula());
                            	for(int j=0; j<tempList.size();j++) {                            		
                            		
                            			old_name = (tempList.get(j).toString()); 
                            			if(f_hashtable.get(tempList.get(j)) != null){
                            				new_name = "["+(f_hashtable.get(tempList.get(j)).toString())+"]";                            				
                            			}  
                            			
                            		New_calculation = (New_calculation.replace(old_name, new_name)); 
                            		//System.out.println(tempList.get(j));
                            	}
                            	System.out.println();
                            	System.out.println(New_calculation);  
                            	//Original Calculater FIeld
//                            	System.out.println(Calculation_list.get(i).displayFormula());
                            }

                            System.out.println();                            
                            System.out.println(DLine);
                }
                
                // Prints Datasources
                System.out.println(DLine+"\n"); 
                System.out.print(Datasourse_list.size());
                System.out.println(" Datasources found");
                System.out.println(DLine); 
                
                for (int i=0;i<Datasourse_list.size();i++){   
                	System.out.println(Datasourse_list.get(i).ds_Details()); 
                }                
                
                
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        System.out.println(DLine);
    }
}
