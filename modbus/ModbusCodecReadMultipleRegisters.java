package modbus;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusCodecReadMultipleRegisters extends ModbusCommandCodec{

	public  boolean ClientEncode(ModbusCommand command, ByteArrayWriter body) throws Exception{
		ModbusCodecBase.PushRequestHeader(command, body);
        return true;
   }


        public  boolean ClientDecode(ModbusCommand command, ByteArrayReader body){
            boolean success = false;
            if (body.CanRead(1)){
                //byte count = body.ReadByte();
            	byte count = (byte) command.getCount();

            	if (body.CanRead(count * 2)){
                	short[] data = new short[count]; //registers
                    //count /= 2;
                    //command.setData(new short[count]);
                    for (int i = 0; i < count; i++){
                    	//data[i] = body.ReadUInt16BE();
                    	data[i] = body.ReadUInt16LE();
                    }
                    command.setData(data);
                    success = true;
                }
            }

            return success;
        }

        /*
        public override bool ServerEncode(
            ModbusCommand command,
            ByteArrayWriter body)
        {
            var count = command.Count;
            body.WriteByte((byte)(count * 2));
            for (int i = 0; i < count; i++)
                body.WriteUInt16BE(command.Data[i]);

            return true;
        }*/


        public boolean ServerDecode(ModbusCommand command, ByteArrayReader body){

            if (ModbusCodecBase.PopRequestHeader(command, body)){
            	command.setData(command.getData()); //?????

                return true;
            }
            else
            {
                return false;
            }
        }





}
