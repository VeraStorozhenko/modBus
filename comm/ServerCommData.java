package comm;

import byteArray.ByteArrayReader;
import protocol.IProtocol;

public class ServerCommData extends CommDataBase{

	ByteArrayReader incomingData;// = new ByteArrayReader();

    public ServerCommData(IProtocol protocol){ //ByteArrayReader incomingData){
    	super(protocol);
        super.setIncomingData(incomingData);
    }
}
