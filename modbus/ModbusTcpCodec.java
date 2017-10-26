package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;
import comm.CommDataBase;
import comm.CommResponse;

public class ModbusTcpCodec extends ModbusCodecBase implements protocol.IProtocolCodec {

    @Override
    public void ClientEncode(CommDataBase data) throws Exception{

    	ModbusClient client = (ModbusClient)data.getOwnerProtocol();
        ModbusCommand command = (ModbusCommand)data.getUserData();
        byte fncode = command.FunctionCode;

        ByteArrayWriter body = new ByteArrayWriter(); //encode the command body, if applies
        ModbusCommandCodec codec = CommandCodecs[fncode];
	    if (codec != null) codec.ClientTcpEncode(command, body);

	    int length = 2 + (command.getCount() * 2);

	    ByteArrayWriter writer = new ByteArrayWriter();//create a writer for the outgoing data

	    writer.WriteUInt16BE((short)command.TransId);//transaction-id (always zero)
	    writer.WriteInt16BE((short) 0);//protocol-identifier (always zero)
	    writer.WriteInt16BE((short)length);//message length
	    writer.WriteByte(client.getAddress());//unit identifier (address)
	    writer.WriteByte(fncode);//function code
	    writer.WriteBytes(body);//body data
	    data.setOutgoingData(writer.ToReader());
	 }


    @Override
    public CommResponse ClientDecode(CommDataBase data){
    	ModbusClient client = (ModbusClient)data.getOwnerProtocol();
    	ModbusCommand command = (ModbusCommand)data.getUserData();
    	ByteArrayReader incoming = data.getIncomingData();

	    if (incoming.Length >= 6 && incoming.ReadUInt16BE() == (short)command.TransId && incoming.ReadInt16BE() == 0 ){
	    	short length = incoming.ReadInt16BE();//message length
	    	if (incoming.Length >= (length + 6) && incoming.ReadByte() == client.getAddress()) {//validate address
		        byte fncode = incoming.ReadByte();//validate function code
		        if ((fncode & 0x7F) == command.FunctionCode){
			        if (fncode <= 0x7F) {
			        	ByteArrayReader body = new ByteArrayReader(incoming.ReadToEnd());
			        	ModbusCommandCodec codec = CommandCodecs[fncode];//encode the command body, if applies
			            if (codec != null && codec.ClientDecode(command, body)){
			            	return new CommResponse(data, CommResponse.ACK, null);
			            }
			       }else{
			         command.setExceptionCode(incoming.ReadByte());//exception
			         return new CommResponse(data, CommResponse.CRITICAL, null);
			       }
			    }
	         }
	    }
	   return new CommResponse(data, CommResponse.UNKNOWN, null);
    }

    @Override
    public void ServerEncode(CommDataBase data) throws Exception{//, short clientaddress
    	ModbusServer server = (ModbusServer)data.getOwnerProtocol();
    	ModbusCommand command = (ModbusCommand)data.getUserData();
    	byte fncode = command.FunctionCode;

    	ByteArrayWriter body = new ByteArrayWriter();//encode the command body, if applies
    	ModbusCommandCodec codec = CommandCodecs[fncode];
	    if (codec != null) codec.ServerEncode(command, body);

	    int length = (command.getExceptionCode() == 0) ? 2 + body.getLength(): 3;//calculate length field
	    ByteArrayWriter writer = new ByteArrayWriter();//create a writer for the outgoing data

	    writer.WriteUInt16BE((short)command.TransId);//transaction-id
	    writer.WriteInt16BE((short) 0);//protocol-identifier
	    writer.WriteInt16BE((short)length);//message length
	    writer.WriteByte((byte) 0x10);
	    //writer.WriteByte(server.Address);

	    if (command.getExceptionCode() == 0){
	    	writer.WriteByte(command.FunctionCode);//function code
	        writer.WriteBytes(body);//body data
	    }else{
	    	writer.WriteByte((byte)(command.FunctionCode | 0x80));//function code
	        writer.WriteByte(command.getExceptionCode());//exception code
	    }
	    data.setOutgoingData(writer.ToReader());
    }

    @Override
    public CommResponse ServerDecode(CommDataBase data){
    	ModbusServer server = (ModbusServer)data.getOwnerProtocol();
    	ByteArrayReader incoming = data.getIncomingData();
    	//

	   if (incoming.Length < 6) return new CommResponse(data, CommResponse.UNKNOWN, null);//validate header first
	   short transId = incoming.ReadUInt16BE();//transaction-id
	   short protId = incoming.ReadInt16BE();//protocol-identifier
	   if (protId != 0) return new CommResponse(data, CommResponse.IGNORE, null);;
	   short length = incoming.ReadInt16LE();//message length;


	   if (incoming.Length < (length + 6)) return new CommResponse(data, CommResponse.UNKNOWN, null);
	   byte address = incoming.ReadByte();//address
	   data.setAddress(address);

	   //if ( address == server.Address )
	   if (true){
	     byte fncode = incoming.ReadByte();//function code
	     ModbusCommand command = new ModbusCommand(fncode);//create a new command
	     data.setUserData(command);
	     command.TransId = transId;
	     ByteArrayReader body = new ByteArrayReader(incoming.ReadToEnd());
	     ModbusCommandCodec codec = CommandCodecs[fncode];//encode the command body, if applies
	     if (codec != null && codec.ServerDecode(command, body)){
	    	 System.out.println("Decode success");
	         return new CommResponse( data, CommResponse.ACK, null);
	     }
	 }
	return null;

}




}
