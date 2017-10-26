package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecWriteSingleRegister extends ModbusCommandCodec{
    public boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{

    		body.WriteUInt16BE((short)command.getStartAddress());
            body.WriteUInt16BE(command.getData()[0]);
            return true;
        }

        public boolean ClientDecode( ModbusCommand command, ByteArrayReader body){
            return true;
        }



        public boolean ServerEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
            ModbusCodecBase.PushRequestHeader(command, body);
            return true;
        }


        public boolean ServerDecode( ModbusCommand command, ByteArrayReader body) {
            if (body.CanRead(4)){
                command.setStartAddress(body.ReadUInt16BE());
                command.setCount((short) 1);
                command.QueryTotalLength += 4;
                short dataWrite[] = new short[1];
                dataWrite[0] = body.ReadUInt16BE();
                command.setData(dataWrite);
                return true;
            }
            else{
                return false;
            }


    }
}
