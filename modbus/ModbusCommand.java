package modbus;

public class ModbusCommand{

	public final static byte FuncReadMultipleRegisters = 3;
    public final static byte FuncWriteMultipleRegisters = 16;

   /**
   * Class 1 functions
   **/
   static public final byte FuncReadCoils = 1;
   static public final byte FuncReadInputDiscretes = 2;
   static public final byte FuncReadInputRegisters = 4;
   static public final byte FuncWriteCoil = 5;
   static public final byte FuncWriteSingleRegister = 6;
   static public final byte FuncReadExceptionStatus = 7;

   /**
   * Class 2 functions
   **/
   static public final byte FuncForceMultipleCoils = 15;

   /**
   * Exceptions
   **/
   static public final byte ErrorIllegalFunction = 1;
   static public final byte ErrorIllegalDataAddress = 2;
   static public final byte ErrorIllegalDataValue = 3;
   static public final byte ErrorIllegalResponseLength = 4;
   static public final byte ErrorAcknowledge = 5;
   static public final byte ErrorSlaveDeviceBusy = 6;
   static public final byte ErrorNegativeAcknowledge = 7;
   static public final byte ErrorMemoryParity = 8;

   public ModbusCommand(byte fc){
	   this.FunctionCode = fc;
   }

   public final byte FunctionCode;
   int QueryTotalLength;
   protected int TransId;

   private short 		startAddress; //2bytes = 1 short
   private short 		count;
   private short[] 		data;
   private byte 		exceptionCode;


   public void setStartAddress(short startAddress){
	   this.startAddress = startAddress;
   }

   public short getStartAddress(){
	   return this.startAddress;
   }

   public void setCount(short count) {
	   this.count = count;
   }

   public short getCount(){
	   return this.count;
   }


   public short[] getData(){
	   return this.data;
   }

   public void setData(short[] data){
	   this.data = data;
   }

   public byte getExceptionCode(){
	   return this.exceptionCode;
   }

   public void setExceptionCode(byte exceptionCode){
	   this.exceptionCode = exceptionCode;
   }
}
