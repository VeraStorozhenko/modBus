package net;

import java.io.IOException;
import java.net.Socket;

import byteArray.ByteArrayWriter;
import comm.ClientCommData;
import comm.CommResponse;
import comm.ICommClient;

public class IpClient extends SocketClient implements ICommClient{

	public IpClient(SocketClient port){
        this.Port = port;
    }

    public SocketClient Port;

    public CommResponse Query(ClientCommData data) throws Exception{

    	byte[] outgoing = data.getOutgoingData().ToByteArray(); //convert the request data as an ordinary byte array
    	ByteArrayWriter incoming = new ByteArrayWriter();		//create a writer for accumulate the incoming data

        int tempSize = 64;
        byte[] temp = new byte[tempSize];

       for (int attempt = 0, retries = data.Retries; attempt < retries; attempt++){
    	   this.Port.Send(outgoing);
    	   incoming.Drop();

           int length = this.Port.getReceiveBufferSize();

          if (length > 0){
        	  if (length > tempSize)length = tempSize;
        	  		temp = this.Port.Receive(1000);
        	  		incoming.WriteBytes(temp, 0,length);//append data to the writer
        	  		data.setIncomingData(incoming.ToReader());//try to decode the stream
        	  		CommResponse result = data.getOwnerProtocol().getCodec().ClientDecode(data);
              if (result.status == CommResponse.ACK){
            	  return result;
              }else if (result.status == CommResponse.CRITICAL){
            	  return result;
              }
              else if (result.status != CommResponse.UNKNOWN){
            	  break;
              }
          }
          Thread.sleep(0);
        }
       return new CommResponse(data, CommResponse.CRITICAL, null);
  }
}


