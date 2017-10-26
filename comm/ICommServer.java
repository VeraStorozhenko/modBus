package comm;

import jssc.SerialPortException;

public interface ICommServer {

        void Start() throws SerialPortException, Exception;
        void Abort();
        //ServeCommandHandler ServeCommand;
        public  void ServeCommandHandler(Object sender, ServeCommandEventArgs e);
		boolean getIsRunning();
		void setIsRunning(boolean isRunning);

}

