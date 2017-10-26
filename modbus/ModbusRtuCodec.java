package modbus;

import comm.CommDataBase;
import comm.CommResponse;
import hashing.HashCrc16;
import protocol.IProtocolCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import byteArray.ByteArrayReader;
import byteArray.ByteArrayWriter;

public class ModbusRtuCodec extends ModbusCodecBase implements protocol.IProtocolCodec
{
        private static HashCrc16 _crc16 = null;{
        	if (_crc16 == null)
                   _crc16 = new HashCrc16();
        }

        @Override
        public void ClientEncode(CommDataBase data) throws Exception{

        	ModbusClient client = (ModbusClient)data.getOwnerProtocol();
            ModbusCommand command = (ModbusCommand)data.getUserData();

            byte fncode = command.FunctionCode;
            byte[] crcCalc;

            ByteArrayWriter body = new ByteArrayWriter(); //encode the command body, if applies

            ModbusCommandCodec codec = CommandCodecs[fncode];
            if (codec != null) codec.ClientEncode(command, body);

            ByteArrayWriter writer = new ByteArrayWriter(); //outgoing data
            try {
				writer.WriteByte(client.getAddress());
				writer.WriteByte(fncode);
				writer.WriteBytes(body);
				crcCalc = ModbusRtuCodec._crc16.getCRC(writer.ToByteArray(), writer.ToByteArray().length);
				writer.WriteByte(crcCalc[0]);
				writer.WriteByte(crcCalc[1]);
            } catch (Exception e1) {
				e1.printStackTrace();
			}
            data.setOutgoingData(writer.ToReader());
        }


        @Override
        public CommResponse ClientDecode(CommDataBase data){

        	ModbusClient client = (ModbusClient)data.getOwnerProtocol();
        	ModbusCommand command = (ModbusCommand)data.getUserData();//???
        	ByteArrayReader incoming = (ByteArrayReader) data.getIncomingData();
            byte bodyLen = (byte)(incoming.Length - 4);

            if (bodyLen >= 0 && incoming.ReadByte() == client.getAddress()){
            	int fncode = incoming.ReadByte();
            	byte[] crcCalc = new byte[2];
            	crcCalc = ModbusRtuCodec._crc16.getCRC(data.getIncomingData().ToByteArray(), data.getIncomingData().Length-2);

            	//StringBuilder str = new StringBuilder("");
            	//byte byteCounts = incoming.ReadByte();//not everywhere the next byte show data bytes length
            	//short body[] = new short[bodyLen];

            	ByteArrayReader body = new ByteArrayReader(incoming.ReadBytes(bodyLen));
            	//command.setData(body.readBytesToShort(bodyLen));


            	byte crcHi = incoming.ReadByte();
            	byte crcLo = incoming.ReadByte();

            	if(crcCalc[0] == crcHi & crcCalc[1] == crcLo){ //do not read all bytes before crc calc, u can move pointer
            		//by calculating how many bytes u were reqested//
            		//System.out.println(fncode);

                     if (((fncode & 0x7F) == command.FunctionCode) && (fncode > 0)){//fncode & 0x7F) == command.FunctionCode)

                    	if ((fncode) <= 0x7F){//get rid of minus

                    		ModbusCommandCodec codec = CommandCodecs[fncode];

                    		if (codec != null && codec.ClientDecode(command, body)){
                            	return new CommResponse(data, CommResponse.ACK, null);
                            }


                         }
                        }else {
                            command.setExceptionCode((byte) body.ReadByte());//íó õç..
                            	return new CommResponse(data, CommResponse.CRITICAL, null);
                        }
                    }
            	}


            return new CommResponse(data, CommResponse.UNKNOWN, null);
        }

        @Override
		public void ServerEncode(CommDataBase data) throws Exception{

        	ByteArrayWriter body = new ByteArrayWriter();
        	ModbusServer server = (ModbusServer)data.getOwnerProtocol();
        	ModbusCommand command = (ModbusCommand)data.getUserData();
        	byte fncode = command.FunctionCode;
        	ModbusCommandCodec codec = CommandCodecs[fncode];
            if (codec != null) codec.ServerEncode(command, body);
            int length = (command.getExceptionCode() == 0) ? 2 + ((ByteArrayWriter) body).getLength() : 3;
            ByteArrayWriter writer = new ByteArrayWriter();
            writer.WriteByte(server.getAddress());

            if (command.getExceptionCode() == 0){
            	writer.WriteByte(fncode);
            	try {
					writer.WriteBytes(body);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }else{
                try {
					writer.WriteByte((byte)(command.FunctionCode | 0x80));
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
					writer.WriteByte(command.getExceptionCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
            }

            writer.WriteByte((byte) 1);
            writer.WriteByte((byte) 2);
            writer.WriteByte((byte) 3);
            writer.WriteByte((byte) 4);

            //ModbusCommand command = (ModbusCommand)data.getUserData();

			byte[] crcCalc = ModbusRtuCodec._crc16.getCRC(writer.ToByteArray(), writer.ToByteArray().length);
			writer.WriteByte(crcCalc[0]);
			writer.WriteByte(crcCalc[1]);
            data.setOutgoingData(writer.ToReader());
        }

        @Override
        public CommResponse ServerDecode(CommDataBase data){

        	ModbusServer server = (ModbusServer)data.getOwnerProtocol();
        	ByteArrayReader incoming = (ByteArrayReader) data.getIncomingData();
        	byte length = 0;
			length = (byte)(incoming.Length);

            if (length < 4) return new CommResponse(data, CommResponse.UNKNOWN, null);

            int address = 0;
			address = incoming.ReadByte();

            if (address == server.getAddress())
            {

            	byte fncode = 0;
				fncode = (byte) incoming.ReadByte();
                if (fncode < CommandCodecs.length)
                {

                	ModbusCommand command = new ModbusCommand(fncode);
                    data.setUserData(command);
                    command.QueryTotalLength = 2;
                    ModbusCommandCodec codec = CommandCodecs[fncode];
                    byte[] numToRead = new byte[length - 4];

                    ByteArrayReader body = new ByteArrayReader(incoming.ReadBytes((length - 4)));

                    if (codec.ServerDecode(command, body)){
                        byte[] crcCalc = new byte[2];
                        {
                        	byte crcHi = incoming.ReadByte();
                        	byte crcLo = incoming.ReadByte();

                        	crcCalc = ModbusRtuCodec._crc16.getCRC(data.getIncomingData().ToByteArray(), data.getIncomingData().Length-2);

                        	if(crcCalc[0] == crcHi & crcCalc[1] == crcLo){
                        		System.out.println("Okey");
                                return new CommResponse(data, CommResponse.ACK, null);

                        	}

                        }
                    }
                }
            }

            //exception
            return new CommResponse(data, CommResponse.IGNORE, null);
        }
}

