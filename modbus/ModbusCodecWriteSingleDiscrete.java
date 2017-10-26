package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecWriteSingleDiscrete extends ModbusCommandCodec{

	public boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
		body.WriteUInt16BE((short)command.getStartAddress());
        short value = (short) (command.getData()[0] != 0 ? 0xFF : 0);
        body.WriteInt16BE((short)value);
        return true;
    }

   public boolean ClientDecode(ModbusCommand command, ByteArrayReader body){
       return true;
   }

   public  boolean ServerEncode( ModbusCommand command, ByteArrayWriter body) throws Exception{
      ModbusCodecBase.PushRequestHeader(command,body);
            return true;
        }

        public boolean ServerDecode(ModbusCommand command, ByteArrayReader body) {
            if (body.CanRead(4)){
                command.setStartAddress(body.ReadUInt16BE());
                command.setCount((short) 1);
                command.QueryTotalLength += 4;

                short[] data = new short[1];
                data[0] =  body.ReadUInt16BE();
                command.getData()[0] = data[0] != 0 ? (short)0xFFFF : (short)0;

                return true;
            }
            else{
                return false;
            }
    }
}
