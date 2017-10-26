package modbus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCommandCodec {

    public boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{//True when the function operated successfully
        return false;
    }

    public boolean ClientDecode(ModbusCommand command, ByteArrayReader body){//True when the function operated successfully
        return false;
    }


    public boolean ServerEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{//True when the function operated successfully
        return false;
    }

    public boolean ServerDecode(ModbusCommand command, ByteArrayReader body){//True when the function operated successfully
        return false;
    }

	public boolean ClientTcpEncode(ModbusCommand command, ByteArrayWriter body) throws Exception {
		return false;

	}

}
