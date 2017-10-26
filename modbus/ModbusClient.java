package modbus;

import comm.ClientCommData;
import comm.CommResponse;
import comm.ICommClient;
import protocol.*;

public class ModbusClient implements IProtocol{

    private IProtocolCodec 	codec;
    private byte 			slaveAddress;

	public ModbusClient(IProtocolCodec codecs){
        this.codec = codecs;
    }

    public void setCodec(IProtocolCodec codec) {
    	this.codec = codec;
    }

    public IProtocolCodec getCodec() {
    	return this.codec;
    }

    public void setAddress(byte address) {
    	this.slaveAddress = address;
    }

    public byte getAddress(){
    	return this.slaveAddress;
    }

    public CommResponse ExecuteGeneric(ICommClient port, ModbusCommand command) throws Exception
    {
    	ClientCommData data = new ClientCommData(this);

    	data.setUserData(command);

        this.codec.ClientEncode(data);

        return port.Query(data);
    }
}
