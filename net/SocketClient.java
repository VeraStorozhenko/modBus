package net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

import byteArray.ByteArrayWriter;
import comm.ClientCommData;
import comm.CommResponse;
import comm.ICommClient;

public class SocketClient extends Socket implements ICommClient{ //IDisposable

    protected int TIMEOUT_MILLISECONDS = 5000;	//Define a timeout in milliseconds for each asynchronous call. If a response is not received within this timeout period, the call is aborted.
    private int MaxBufferSize = 2048;			//The maximum size of the data buffer to use with the asynchronous socket methods
    private Socket _socket = null;				//Cached Socket object that will be used by each call for the lifetime of this class
    DataOutputStream outToServer;

    String hostName;
    int portNumber;
    int timeout;

    private int Latency;
    private String LastError;

    public void setLatency(int latency){
    	this.Latency = latency;
    }

    public int getLatency(){
    	return this.Latency;
    }

    public void setLastError(String lastError){
    	this.LastError = lastError;
    }

    public SocketClient(String hostName, int portNumber) throws UnknownHostException, IOException{
    	this.hostName = hostName;
    	this.portNumber = portNumber;
    	this.Connect(this.hostName, this.portNumber, TIMEOUT_MILLISECONDS);
    }

    public SocketClient(){}

    public CommResponse Query(ClientCommData data) throws Exception{

    	//Connect(this.hostName, this.portNumber, TIMEOUT_MILLISECONDS);

    	byte[] outgoing = data.getOutgoingData().ToByteArray(); //convert the request data as an ordinary byte array

        int totalTimeout = this.Latency + data.Timeout;

        for (int attempt = 0, retries = data.Retries; attempt < retries; attempt++){

        	//DataOutputStream dOut = new DataOutputStream(_socket.getOutputStream());
        	//dOut.writeInt(outgoing.length); // write length of the message
        	//dOut.write(outgoing);           // write the message

        	this.Send(outgoing);

            ByteArrayWriter incoming = new ByteArrayWriter();//create a writer for accumulate the incoming data

            incoming.WriteBytes(this.Receive(totalTimeout));

            data.setIncomingData(incoming.ToReader());
            CommResponse result = data.getOwnerProtocol().getCodec().ClientDecode(data);

            if (result.status == CommResponse.ACK){//exit whether any concrete result: either good or bad
                return result;
            }else if (result.status == CommResponse.CRITICAL){
                return result;
            }else if (result.status != CommResponse.UNKNOWN){
                break;
            }
        }

        return new CommResponse(data, CommResponse.CRITICAL, LastError);
    }

    public boolean Connect(String hostName, int portNumber, int timeout) throws UnknownHostException, IOException{
    	timeout = TIMEOUT_MILLISECONDS;

    	_socket = new Socket(hostName, portNumber);

    	if(_socket.isConnected()){
    		return true;
    	}else{
             return false;//result = clientSocket.		//e.SocketError.ToString();// Retrieve the result of this request
        }

    }

    public boolean Send(byte[] outgoing) throws IOException{

    	outToServer = new DataOutputStream(_socket.getOutputStream());
    	outToServer.writeInt(outgoing.length);				//write length of the message
    	outToServer.write(outgoing, 0, outgoing.length);	//write the message

    	return true;
    }


    public byte[] Receive(int timeout) throws IOException{

    	String response = "Operation Timeout";
        InputStream is = _socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] dat = new byte[16384];

        while ((nRead = is.read(dat, 0, dat.length)) != -1) {
          buffer.write(dat, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    public void Close() throws IOException{
        if (_socket != null){
            _socket.close();
        }
    }

    private boolean _disposed = false;



    public void Dispose() throws IOException{
        Dispose(true);
    }

    private void Dispose(boolean disposing) throws IOException{
        if (!this._disposed){
            if (disposing) {
                this.Close();
                this.Dispose();
            }
            this._disposed = true;
        }
    }





}
