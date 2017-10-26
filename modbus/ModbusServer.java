package modbus;

import protocol.IProtocol;
import protocol.IProtocolCodec;

public class ModbusServer implements IProtocol{

	private IProtocolCodec Codec;
	private byte address;

     public ModbusServer(IProtocolCodec codec)
     {
         this.Codec = codec;
     }

     public void setAddress(byte address){
    	 this.address = address;
     }

     public byte getAddress(){
    	 return this.address;
     }

	@Override
	public IProtocolCodec getCodec() {
		return this.Codec;
	}

	private void setCodec(IProtocolCodec Codec){
		this.Codec = Codec;
	}
}
