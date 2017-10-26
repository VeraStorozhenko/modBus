package comm;

public class CommResponse {

	public final static int 	UNKNOWN = 0;
    public final static int 	IGNORE = 1;
    public final static int 	CRITICAL = 2;
    public final static int 	ACK = 3;

    public final CommDataBase 	data;
    public final int 			status;
    public final String 		description;

    public CommResponse(CommDataBase data){
         this(data, ACK, null);
     }

    public CommResponse(CommDataBase data, int status, String description){
    	this.data = data;
        this.status = status;
        this.description = description;
      }


	public String ToString(){
    	 return String.format("{0}: sts={1}; descr={2}", this.getClass().getName(), this.status, this.description);
     }
}
