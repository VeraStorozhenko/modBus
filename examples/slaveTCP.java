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


//start listen
//get message

		/*
		byte[] buffer = new byte[1234];
	    InputStream in = socket.getInputStream();
	    DataInputStream dis = new DataInputStream(in);
	    int len = dis.readInt();
	    byte[] data = new byte[len];
	    if (len > 0) {
	        dis.readFully(data); //Array with bytes we read
	        for(int i = 0; i < data.length; i++){
	        	System.out.println(data[i]);
	        }
	    }*/

		/*InputStream stream = socket.getInputStream();
		byte[] data = new byte[100];
		int count = stream.read(data);
		System.out.println("The dumb client just sent me this line : ");*/

	    //String line = null;
	    //while(true() {
		    /*line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.

		    System.out.println("I'm sending it back...");
		    out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
		    out.flush(); // заставляем поток закончить передачу данных.
		    System.out.println("Waiting for the next line...");
		    System.out.println();*/
	    //}
	}


		/*int port = 502;
		ServerSocket ss = new ServerSocket(port);
		Socket socket = ss.accept();
		System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");

		medium = new TcpServer(socket, driver);*/



	       /*try {
	         ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
	         System.out.println("Waiting for a client...");

	         Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
	         System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
	         System.out.println();

	         //Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
	         InputStream sin = socket.getInputStream();
	         OutputStream sout = socket.getOutputStream();

	         //Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
	         DataInputStream in = new DataInputStream(sin);
	         DataOutputStream out = new DataOutputStream(sout);

	         String line = null;
	         while(true) {
	           line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
	           System.out.println("The dumb client just sent me this line : " + line);
	           System.out.println("I'm sending it back...");
	           out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
	           out.flush(); // заставляем поток закончить передачу данных.
	           System.out.println("Waiting for the next line...");
	           System.out.println();
	         }
	      } catch(Exception x) { x.printStackTrace(); }*/
	   }




