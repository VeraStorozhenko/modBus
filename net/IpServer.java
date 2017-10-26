package net;

import java.io.IOException;
import java.net.Socket;

import comm.ICommServer;
import comm.ServeCommandEventArgs;
import protocol.IProtocol;

public abstract class IpServer  implements ICommServer {//extends SocketClient

	protected IpServer(Socket socket, IProtocol protocol){
		this.Socket = socket;
		this.Protocol = protocol;
		Start();

    }

	public Socket Socket;
    public IProtocol Protocol;

    private Thread _thread;
    protected boolean _closing;
    protected boolean isRunning;

	public void setIsRunning(boolean isRunning){
		this.isRunning = isRunning;
	}

	public boolean getIsRunning(){
	   return this.isRunning;
	}

    public void Start(){

      this.setIsRunning(true) ;//marks the server running
      try {
    	  Worker();
      }catch (Exception e) {
    	  e.printStackTrace();
      }
      //this._thread = new Thread(this.Worker);
      //this._thread.Start();
    }

   protected abstract void Worker() throws IOException, Exception;


   public void Abort(){
      this._closing = true;
      if (this._thread != null && this._thread.isAlive()){
    	  try {
			this._thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
      }
   }

	@Override
	public void ServeCommandHandler(Object sender, ServeCommandEventArgs e) {
		// TODO Auto-generated method stub

	}

}
