package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecWriteMultipleRegisters extends ModbusCommandCodec{

	public boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
		ModbusCodecBase.PushRequestHeader(command, body);
        short count = command.getCount();
        body.WriteByte((byte)(count * 2)); //need for SERIAL no need for tcp/ip!
        for (int i = 0; i < count; i++)
            body.WriteUInt16BE(command.getData()[i]);
         return true;
        }

	public boolean ClientTcpEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{

		short count = command.getCount();
		for (int i = 0; i < count; i++)
            body.WriteUInt16BE(command.getData()[i]);
         return true;
        }

        public boolean ClientDecode(ModbusCommand command, ByteArrayReader body) {
            return true;
        }

        public boolean ServerEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
            ModbusCodecBase.PushRequestHeader(command, body);
            return true;
        }


        public boolean ServerDecode(ModbusCommand command, ByteArrayReader body){
            //if (ModbusCodecBase.PopRequestHeader(command, body) == false)
            //    return false;

            boolean success = false;
            //if (body.CanRead(1))
           // {
                //byte count = body.ReadByte();
            	byte count = (byte) body.Length;

                if (body.CanRead(count)){
                    command.QueryTotalLength += (count + 1);
                    count /= 2;
                    command.setData(new short[count]) ;
                    short[] data = new short[count]; //registers

                    for (int i = 0; i < count; i++){
                        //command.getData()[i] = (byte) body.ReadUInt16BE();
                        data[i] = body.ReadUInt16LE();
                        System.out.println(data[i]);
                    }

                    command.setData(data);
                    success = true;
                }
            //}

            return success;
        }

}
