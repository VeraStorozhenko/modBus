package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comm.CommResponse;
import comm.ICommServer;
import modbus.ModbusClient;
import modbus.ModbusServer;
import modbus.ModbusTcpCodec;
import net.IpServer;
import net.SocketClient;
import net.TcpServer;
import protocol.IProtocol;

public class sever {

	static ICommServer 			medium;
	static IProtocol			protocol;
	static ModbusServer			driver;

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(502);
		Socket socket = server.accept(); //wait until client is connected
		System.out.println("Start listening.." + socket.getPort());

		driver = new ModbusServer(new ModbusTcpCodec());
		driver.setAddress((byte) 0x10);

		medium = new TcpServer(socket, driver);





