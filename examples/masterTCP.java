package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import comm.CommResponse;
import comm.ICommClient;
import comm.ICommServer;
import modbus.ModbusClient;
import modbus.ModbusCommand;
import modbus.ModbusTcpCodec;
import net.SocketClient;

public class client {

	static ICommClient 				medium;
	static ModbusClient 			driver;

	public static void main(String[] args) throws Exception {

		   short[] buf = {1, 2, 9};

	       medium = new SocketClient("localhost", 502);
	       driver = new ModbusClient(new ModbusTcpCodec());
	       driver.setAddress((byte) 0x1);
	       ModbusCommand d = new ModbusCommand(ModbusCommand.FuncWriteMultipleRegisters);
	       d.setStartAddress((short)0x10);
	       d.setCount((short) 3); //how many  registers 1 register = 2 bytes
	       d.setData(buf);

	       CommResponse t = driver.ExecuteGeneric(medium, d);
	       if (t.status != CommResponse.ACK){
	    	   throw new Exception("Ошибка записи параметров");
	       }
	}

}
