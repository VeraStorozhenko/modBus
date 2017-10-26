package modbus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;
import comm.CommDataBase;

public class ModbusCodecBase{

	public static final ModbusCommandCodec[] CommandCodecs = new ModbusCommandCodec[24];

	static{
		CommandCodecs[ModbusCommand.FuncReadMultipleRegisters] = new ModbusCodecReadMultipleRegisters();
        CommandCodecs[ModbusCommand.FuncWriteMultipleRegisters] = new ModbusCodecWriteMultipleRegisters();
        CommandCodecs[ModbusCommand.FuncReadCoils] = new ModbusCodecReadMultipleDiscretes();
        CommandCodecs[ModbusCommand.FuncReadInputDiscretes] = new ModbusCodecReadMultipleDiscretes();
        CommandCodecs[ModbusCommand.FuncReadInputRegisters] = new ModbusCodecReadMultipleRegisters();
        CommandCodecs[ModbusCommand.FuncWriteCoil] = new ModbusCodecWriteSingleDiscrete();
        CommandCodecs[ModbusCommand.FuncWriteSingleRegister] = new ModbusCodecWriteSingleRegister();
        CommandCodecs[ModbusCommand.FuncForceMultipleCoils] = new ModbusCodecForceMultipleCoils();
    }


    static void PushRequestHeader(ModbusCommand command, ByteArrayWriter body) throws Exception{

    	body.WriteUInt16BE((short)command.getStartAddress());
        body.WriteInt16BE((short)command.getCount());

     }


     static boolean PopRequestHeader(ModbusCommand command, ByteArrayReader body){
    	 //System.out.println(body.CanRead(4));

    	 if (body.CanRead(4)) {
            command.setStartAddress(body.ReadUInt16BE());
            command.setCount(body.ReadInt16BE());
            command.QueryTotalLength += 4;
            return true;
          }else{
        	  return false;
          }
      }

      static void PushDiscretes(ModbusCommand command, ByteArrayWriter body) throws Exception{
    	  int count = command.getCount();
 		 try {
          body.WriteByte((byte) ((count + 7) / 8));
          int i = 0;
          int cell = 0;
          for (int k = 0; k < count; k++){
        	  if (command.getData()[k] != 0)
                  cell |= (1 << i);

                if (++i == 8){
                    body.WriteByte((byte)cell);
                    i = 0;
                    cell = 0;
                }
            }

            if (i > 0)
                body.WriteByte((byte)cell);
 		 }catch(IOException e){
 			e.printStackTrace();
 		 }
        }


        static boolean PopDiscretes(ModbusCommand command, ByteArrayReader body){
            boolean success = false;
            /*try{
                int byteCount = body.read();
                int count = command.getCount();
                command.setData(new int[count]);
                command.QueryTotalLength += (byteCount + 1);

                int k = 0;
                while (count > 0){
                    byteCount--;
                    int cell = body.read();
                    int n = count <= 8 ? count : 8;
                    count -= n;
                    for (int i = 0; i < n; i++)
                       command.getData()[k++] = (int)(cell & (1 << i));
                    }
                success = true;
            }catch(IOException e){
            	e.printStackTrace();
            }*/
            return success;
        }



}
