package TWB;

public class Calculated_field {	
   String caption;
   String role;
   String ID;
   String datatype;
   String formula;
   int counter;

	// The constructor method
	public Calculated_field(int cnt, String cf_caption, String cf_role, String cf_ID, String cf_datatype, String cf_formula){
		counter = cnt;
		caption = cf_caption;
		role = cf_caption;
		ID = cf_ID;
		datatype = cf_datatype;
		formula = cf_formula;
	}
	
	public String getID() {
		return(ID);		
	}
	
	public String getName() {
		return(caption);		
	}
	public String displayDetails(){
		return(counter+") Name = " + caption + "  |  " + "Role = " + role + "  |  " + "ID = " + ID + "  |  " +"Datatype = " + datatype);       
        
	}
	public String displayFormula(){
		return(formula);       
    
	}

}
