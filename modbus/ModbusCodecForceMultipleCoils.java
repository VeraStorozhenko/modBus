package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecForceMultipleCoils extends ModbusCommandCodec{
    public boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
            ModbusCodecBase.PushRequestHeader(command,body);

            ModbusCodecBase.PushDiscretes( command, body);
            return true;
        }

        public boolean ClientDecode(ModbusCommand command, ByteArrayReader body){
            return ModbusCodecBase.PopRequestHeader(command, body);
        }

        public boolean ServerEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
            ModbusCodecBase.PushRequestHeader(command, body);
            return true;
        }

        public boolean ServerDecode(ModbusCommand command,ByteArrayReader body){
            return
                ModbusCodecBase.PopRequestHeader(command, body) &&
                ModbusCodecBase.PopDiscretes(command, body);
        }

}
