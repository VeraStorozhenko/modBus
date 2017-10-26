package serial;


import jssc.SerialPortException;

//interface for all params and methods
public interface ISerialPort {

	/*String PortName;
    int BaudRate;
    int DataBits;
    Parity Parity;
    StopBits StopBits;
    boolean RtsEnable;
    boolean IsOpen;
    int BytesToRead;
    int WriteTimeout;*/

	//replace with getters/setters
	public int getBaudRate();
	public Parity getParity();
	public int getDataBits();
	public StopBits getStopBits();
	public boolean getRtsEnable();
    boolean getIsOpen();
    int getBytesToRead();
    int getWriteTimeout();

	public void setBaudRate(int BaudRate);
	public void setParity(Parity parity);
	public void setDataBits(int DataBits);
	public void setStopBits(StopBits StopBits);
	public void setRtsEnable(boolean rtsEnable);
	void setIsOpen(boolean IsOpen);
	void setBytesToRead(int BytesToRead);
	void setWriteTimeout(int WriteTimeout);

	void Open();
    void Close();
    void DiscardInBuffer();
    void DiscardOutBuffer();
    byte[] Read();
    boolean Write(byte[] buffer) throws SerialPortException;
    void SetParams(SerialPortParams prm);

    public boolean eventHappen = false;

}
