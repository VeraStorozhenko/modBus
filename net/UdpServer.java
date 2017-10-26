package net;

import java.net.Socket;

import byteArray.ByteArrayReader;
import comm.CommResponse;
import comm.ServerCommData;
import protocol.IProtocol;

public class UdpServer extends IpServer{

	public UdpServer(SocketClient port, IProtocol protocol){
        super (port, protocol);
    }

    protected  void Worker(){
        /*while (this._closing == false){
            int length = this.Port.getReceiveBufferSize();//look for incoming data
            if (length > 0){
                byte[] buffer = new byte[length];
                EndPoint remote = new IPEndPoint(IPAddress.Any, 0);

                this.Port.ReceiveFrom(buffer, remote);//read the data from the physical port
                ServerCommData data = new ServerCommData(this.Protocol);//try to decode the incoming data
                data.setIncomingData(new ByteArrayReader(buffer));
                CommResponse result = this.Protocol.getCodec().ServerDecode(data);

                if (result.status == CommResponse.ACK){ //the command is recognized, so call the host back
                    //this.OnServeCommand(data);
                    this.Protocol.getCodec().ServerEncode(data);//encode the host data
                    byte[] outgoing = data.getOutgoingData().ToByteArray();//return the resulting data to the remote caller//return the resulting data to the remote caller
                    this.Port.SendTo(outgoing, remote);
                }
            }
            Thread.sleep(0);
        }

        this.setIsRunning(false);//marks the server not running*/
    }

}
