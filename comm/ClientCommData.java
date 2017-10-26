package comm;

import modbus.ModbusCommand;
import protocol.IProtocol;
import protocol.IProtocolCodec;

public class ClientCommData extends CommDataBase {

    public ClientCommData(IProtocol protocol){
    	super(protocol);
    }

	public int Timeout = 1000;  //ms

	public int Retries = 3;



}