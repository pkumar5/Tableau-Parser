package TWB;

public class Data_source {
	

	private String caption;
	private String ID;
	private int cnt;
	
	// The constructor method
	public Data_source(int cnt, String caption, String ID) {
		
		this.caption = caption;
		this.ID = ID;	
		this.cnt = cnt;
	}
	
	public String ds_Details(){
		return(cnt+") ID = " + ID + "\t\t\tName = " +  caption + "\n");       
	}
	
}
