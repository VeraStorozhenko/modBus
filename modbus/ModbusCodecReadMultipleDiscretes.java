package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecReadMultipleDiscretes extends ModbusCommandCodec{

	public boolean ClientEncode(ModbusCommand command,
        ByteArrayWriter body) throws Exception{
        ModbusCodecBase.PushRequestHeader(command, body);
        return true;
    }

    public boolean ClientDecode(ModbusCommand command, ByteArrayReader body){
            return ModbusCodecBase.PopDiscretes(command, body);
        }

    public boolean ServerEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
       ModbusCodecBase.PushDiscretes( command, body);
       return true;
   }

   public boolean ServerDecode( ModbusCommand command, ByteArrayReader body){
        if (ModbusCodecBase.PopRequestHeader(command, body)){
           command.setData(new short[command.getCount()]);
           return true;
         }else{
            return false;
         }
   }

}
