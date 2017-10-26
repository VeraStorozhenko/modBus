package serial;

import comm.ClientCommData;
import comm.CommResponse;
import comm.ICommClient;
import jssc.SerialPortException;
import byteArray.ByteArrayReader;

class SerialPortClient implements ICommClient{

	//public  ISerialPort Port;
    public  SerialPortParams Setting;
    public int Latency;


    SerialPortClient(SerialPortParams setting){
        this.Setting = setting;
    }

    void setLatency(int latency){
    	this.Latency = latency;
    }

    int getLatency(){
    	return this.Latency;
    }

    public CommResponse Query(ClientCommData data) throws InterruptedException{
            final int tempSize = 64;
            byte[] temp = new byte[tempSize];
            int t = 20;

    		byte[] outgoing = data.getOutgoingData().ToByteArray(); //convert the request data as an ordinary byte array

            try {
            	boolean success = Setting.Write(outgoing);
            	if(success){
            		Thread.sleep(500);
            		temp = Setting.Read();
            	}



            } catch (SerialPortException e) {
				e.printStackTrace();
			}

            if(temp == null){
            	System.out.println("Following function have no implementation or slave device(emulator doesnt set prperly!)");
            	//make result  status return unknown
            }

            ByteArrayReader incoming = new ByteArrayReader(temp);
            data.setIncomingData(incoming);

            CommResponse result = data.getOwnerProtocol().getCodec().ClientDecode(data);

                if (result.status == CommResponse.ACK){
                    return result;
                }
                else if (result.status == CommResponse.CRITICAL){
                    return result;
                }
                else if (result.status != CommResponse.UNKNOWN){
                    return result; //break!
                }

            return  new CommResponse(data);


            //byte[] incoming = data.getIncomingData();
            //int attempt = 0, retries = data.Retries; attempt < retries; attempt++
            /*for (int attempt = 0, retries = data.Retries; attempt < retries; attempt++){
                try{
                	Setting.DiscardInBuffer();
                	Setting.DiscardOutBuffer();
                	Setting.setWriteTimeout(500);
                	Setting.Write(outgoing, 0, outgoing.length);

                }catch (Exception ex1){
                    return new CommResponse(data,CommResponse.Critical,ex1.getMessage());}*/
               // incoming.drop();

               /* boolean timeoutExpired = false;
                int totalTimeout = this.Latency + data.Timeout;
                final Timer time = new Timer();

                time.schedule(new TimerTask() {
                	@Override
                	public void run(){
                        while (timeoutExpired == false){
                            int length = Setting.getBytesToRead();
                            if (length > 0){
                                if (length > tempSize) length = tempSize;
                                try{
                                	Setting.Read(temp, 0, length);
                                }catch (Exception ex2){
                                	new CommResponse(data,CommResponse.Critical,ex2.getMessage());
                                }

                                //incoming.read(data);
                                //incoming.Write(temp, 0, length);
                                //data.setIncomingData(incoming.ToReader());
                                //CommResponse result = data.getOwnerProtocol();

                            }
                		}
                       new CommResponse(data);
                	}
                }, 1000, 1000 );
            }
           return  new CommResponse(data);


                	//int i = 0;

                    //public void run() { //ÏÅÐÅÇÀÃÐÓÆÀÅÌ ÌÅÒÎÄ RUN Â ÊÎÒÎÐÎÌ ÄÅËÀÅÒÅ ÒÎ ×ÒÎ ÂÀÌ ÍÀÄÎ
                        //
                        /*while (timeoutExpired == false){
                            int length = s;

                            if (length > 0){
                                if (length > tempSize) length = tempSize;

                                try{
                                        Settings.Read(temp, 0, length);
                                }
                                catch (Exception ex){
                                    return new CommResponse(data,CommResponse.Critical, ex.Message);
                                }

                                incoming.WriteBytes(temp, 0, length);
                                data.IncomingData = incoming.ToReader();
                            }
                       }*/
                    //}}), 4000, 4000)






            //return new CommResponse(data,CommResponse.Critical, null);
    //}







  }

}