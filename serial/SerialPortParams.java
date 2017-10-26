package serial;

//import application;
import comm.ICommClient;
import comm.ICommServer;
import jssc.SerialPort;  /*Èìïîðò êëàññîâ áèáëèîòåêè jssc*/
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import modbus.ModbusRtuCodec;
//import application.SerialPortParams;
import modbus.ModbusServer;
import protocol.IProtocol;

public class SerialPortParams implements ISerialPort{//

	public boolean eventHappen = false;
	public static SerialPort serialPort;
	public boolean isOpen = false;


	public SerialPortParams(String portName, int baudRate, Parity parity, int dataBits, StopBits stopBits, boolean rtsEnable){
		this.PortName = portName;
		this.BaudRate = baudRate;
        this.Parity = parity;
        this.DataBits = dataBits;
        this.StopBits = stopBits;
        this.RtsEnable = rtsEnable;

     }
        //public SerialPortParams() {}

        ModbusRtuCodec codec = new ModbusRtuCodec();
        public String 	PortName;
		public int 		BaudRate;
        public int 		DataBits;
        public Parity 	Parity;
        public StopBits StopBits;
        public boolean 	RtsEnable;
        int WriteTimeout;

        public String[] getPorts(){
        	String[] portNames = SerialPortList.getPortNames();
        	return portNames;
        }

        @Override
		public int getBaudRate() {
			return this.BaudRate;
		}
		@Override
		public Parity getParity() {
			return this.Parity;
		}
		@Override
		public int getDataBits() {
			return this.DataBits;
		}
		@Override
		public StopBits getStopBits() {
			return this.StopBits;
		}

		@Override
		public boolean getRtsEnable() {
			return this.RtsEnable;
		}

		@Override
		public boolean getIsOpen() {
			return this.isOpen;
		}

		//@Override
		public int getBytesToRead() {
			// TODO Auto-generated method stub
			return 0;
		}

		//@Override
		public void setBaudRate(int BaudRate) {
			// TODO Auto-generated method stub

		}
		//@Override
		public void setParity(Parity parity) {
			// TODO Auto-generated method stub

		}
		@Override
		public void setDataBits(int DataBits) {
			// TODO Auto-generated method stub

		}
		@Override
		public void setStopBits(StopBits StopBits) {
			// TODO Auto-generated method stub

		}
		@Override
		public void setRtsEnable(boolean rtsEnable) {
			// TODO Auto-generated method stub

		}
		@Override
		public void setIsOpen(boolean IsOpen) {
			// TODO Auto-generated method stub

		}
		@Override
		public void setBytesToRead(int BytesToRead) {
			// TODO Auto-generated method stub

		}


		public  void Open(){
			serialPort = new SerialPort (PortName);
			try {
				serialPort.openPort (); /*Ìåòîä îòêðûòèÿ ïîðòà*/
		        serialPort.setParams (SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); /*Çàäàåì îñíîâíûå ïàðàìåòðû ïðîòîêîëà UART*/
		        //serialPort.setEventsMask (SerialPort.MASK_RXCHAR); /*Óñòàíàâëèâàåì ìàñêó èëè ñïèñîê ñîáûòèÿ íà êîòîðûå áóäåò ïðîèñõîäèòü ðåàêöèÿ. Â äàííîì ñëó÷àå ýòî ïðèõîä äàííûõ â áóôôåð ïîðòà*/
		        //serialPort.addEventListener (new PortReader(), SerialPort.MASK_RXCHAR); /*Ïåðåäàåì ýêçåìïëÿð êëàññà EventListener ïîðòó, ãäå áóäåò îáðàáàòûâàòüñÿ ñîáûòèÿ. Íèæå îïèñàí êëàññ*/
		        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
		        serialPort.setEventsMask(mask);//Set mask
		        //serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
		        isOpen = true;

			}catch (SerialPortException ex) {
				System.out.println(ex);
			}
		}

		//public  void AddEventlistener() throws SerialPortException{
		//	serialPort.addEventListener (new  PortReader(), SerialPort.MASK_RXCHAR);
		//}

		@Override
		public byte[] Read(){

			byte[] buffer = new byte[64];

			for(int attempt = 0; attempt < 3; attempt++){
				try {
					buffer = serialPort.readBytes();

					if(buffer != null)
						return buffer;

					Thread.sleep( 500 );
						//serialPort.waitBytesWithTimeout("readBytes()", 8, 500);

				}catch (Exception ex){// | InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			return buffer;
		}

		@Override
		public void Close() {
			// TODO Auto-generated method stub
		}

		@Override
		public void DiscardInBuffer() {//clear file?
		}

		@Override
		public void DiscardOutBuffer() {
			// TODO Auto-generated method stub
		}


		@Override
		public boolean Write(byte[] buffer) throws SerialPortException { //write to serial port
		    byte address = 	(byte)0x10;		//0b0001_0000;
		    byte function = (byte)0x3;		//0b0000_0011;
		    byte start_h = 	(byte)0x10;		//0b0001_0000;
		    byte datah = 	(byte)0x0;		//0b0000_0000;
		    byte datal = 	(byte)0x0;		//0b0000_0010;
		    byte count = 	(byte)0x2;		//0b0000_0010;
		    byte crch = 	(byte)0xc3;		//0b1100_0011;//195
		    byte crcl = 	(byte)0x8a;		//0b1000_1010;//138

		    byte[] requests = {address, function, start_h, datah, datal, count, crch, crcl};
		    //serialPort.writeBytes(requests);
		    boolean success = serialPort.writeBytes(buffer);
		    return success;

		}

		@Override
		public void SetParams(SerialPortParams prm) {
			// TODO Auto-generated method stub

		}

        @Override
		public void setWriteTimeout(int timeout) {
			this.WriteTimeout = timeout;
        }

        @Override
		public int getWriteTimeout() {
			return this.WriteTimeout;
        }

    	public ICommClient GetClient(){
    		return new SerialPortClient(this);
    	}

    	class SerialPortReader implements SerialPortEventListener {

    	    public void serialEvent(SerialPortEvent event) {
    	        if(event.isRXCHAR()){
    	        	eventHappen = true;

    	        	//If data is available
    	            if(event.getEventValue() == 8){//Check bytes count in the input buffer
    	                //Read data, if 10 bytes available
    	                try {
    	                    byte buffer[] = serialPort.readBytes();
    	                    System.out.println("get it");
    	                }
    	                catch (SerialPortException ex) {
    	                    System.out.println(ex);
    	                }
    	            }
    	        }
    	        else if(event.isCTS()){//If CTS line has changed state
    	            if(event.getEventValue() == 1){//If line is ON
    	                //System.out.println("CTS - ON");
    	            }
    	            else {
    	                //System.out.println("CTS - OFF");
    	            }
    	        }
    	        else if(event.isDSR()){///If DSR line has changed state
    	            if(event.getEventValue() == 1){//If line is ON
    	                //System.out.println("DSR - ON");
    	            }
    	            else {
    	                //System.out.println("DSR - OFF");
    	            }
    	        }
    	    }

    	}

    	/*public static ICommServer GetServer(ISerialPort serialPort, IProtocol protocol) {
			return new SerialPortServer(serialPort, protocol);
		}*/


}






