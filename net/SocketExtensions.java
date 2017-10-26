package net;

import java.net.Socket;

import comm.ICommClient;
import comm.ICommServer;
import protocol.IProtocol;

public class SocketExtensions {

	public static ICommClient GetClient(SocketClient port){
            return new IpClient(port);
    }

   public static ICommServer GetTcpListener(SocketClient port,IProtocol protocol){
	   //SocketClient client = port.
	   return new TcpServer(port, protocol);
   }

   public static ICommServer GetUdpListener(SocketClient port, IProtocol protocol){
	   return new UdpServer(port, protocol);
    }
}
