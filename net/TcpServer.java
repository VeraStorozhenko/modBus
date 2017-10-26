package net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import byteArray.ByteArrayWriter;
import comm.CommResponse;
import comm.ServerCommData;
import protocol.IProtocol;

public class TcpServer  extends IpServer {

    public TcpServer(Socket socket, IProtocol protocol){
       super(socket, protocol);
    }

    private int CacheSize = 300;
    private int IdleTimeout = 10;
    private int getLength = 0;
    private int counter = 10;

    protected  void Worker() throws Exception{
        try{
        	System.out.println("Worker!");
        	int counter = 1000;//start the local timer, which gets the session dying
            ByteArrayWriter writer = null;//create a writer for the incoming data
            byte[] buffer = new byte[CacheSize];

            while (counter-- > 0){//this._closing == false &&

               InputStream sin = this.Socket.getInputStream();
               OutputStream sout = this.Socket.getOutputStream();
               DataInputStream in = new DataInputStream(sin);
               DataOutputStream out = new DataOutputStream(sout);

               int len = in.readInt();
               byte[] data = new byte[len];
               		if (len > 0) {
            	    	System.out.println("Gotted someth!");
            	    	in.readFully(data); //Array with bytes we read

            	        ByteArrayOutputStream os = new ByteArrayOutputStream();

                        if (writer == null) writer = new ByteArrayWriter();
                        writer.WriteBytes(data, 0, data.length);

                        os.write(data, 0, data.length);
                        os.flush();
                        buffer = os.toByteArray();

                        ServerCommData incomingData = new ServerCommData(this.Protocol);//try to decode the incoming data
                        incomingData.setIncomingData(writer.ToReader()); //OK

                        CommResponse result = this.Protocol.getCodec().ServerDecode(incomingData);


 //DECODE SUCCES!!!! ^_^
 //NEED TO ENCODE NEW DATA!!!

                        if (result.status == CommResponse.ACK){
    	                    //this.OnServeCommand(data);//the command is recognized, so call the host back
    	                    this.Protocol.getCodec().ServerEncode(incomingData);//encode the host data
    	                    byte[] outgoing = incomingData.getOutgoingData().ToByteArray();//return the resulting data to the remote caller
    	                    //this.Port.Send(outgoing);
    	                    DataOutputStream dOut = new DataOutputStream(this.Socket.getOutputStream());
    	                    dOut.writeInt(outgoing.length); // write length of the message
    	                    dOut.write(outgoing);           // write the message

    	                    //counter = IdleTimeout;//reset the timer
    	                    writer = null;
                        }
                        else if (result.status == CommResponse.IGNORE){
                            writer = null;
                        }
                    }
                    Thread.sleep(0);
            	}

                }finally{
                	System.out.println("Finally");
                }


        }
    }
