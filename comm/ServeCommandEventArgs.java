package comm;

public class ServeCommandEventArgs {

	public ServerCommData Data;

	public ServeCommandEventArgs(ServerCommData data){
        this.Data = data;
    }

	ServerCommData getData(){
		return this.Data;
	}

	void setData(ServerCommData data){
		this.Data = data;
	}
}
