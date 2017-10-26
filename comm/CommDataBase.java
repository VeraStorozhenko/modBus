package comm;

import protocol.IProtocol;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import byteArray.ByteArrayReader;

public class CommDataBase
{
	private boolean 			rejectRequest;
	private IProtocol 			ownerProtocol;
	private Object 				userData;
	private short 				address;
	private ByteArrayReader  	outgoingData;
	private ByteArrayReader 	incomingData;

	protected CommDataBase(IProtocol protocol){
        this.ownerProtocol = protocol;
        rejectRequest = false;
    }

    public void setOwnerProtocol(IProtocol protocol) {
    	this.ownerProtocol = protocol;
    }

    public IProtocol getOwnerProtocol(){
    	return this.ownerProtocol;
    }

    public Object getUserData() {
    	return this.userData;
    }


    public void setBuffer(byte registerCount) {
    	short[] dataBuffer = new short[registerCount];

    }

    public void setUserData(Object userdata){
    	this.userData = userdata;
    }

    public short getAddress() {
    	return this.address;
    }

    public void setAddress(short address){
    	this.address = address;
    }

    public boolean getRejectRequest() {
    	return this.rejectRequest;
    }

    public void setRejectRequest(boolean rejectRequest){
    	this.rejectRequest = rejectRequest;
    }

    public ByteArrayReader getOutgoingData(){
    	return this.outgoingData;
    }

    public void setOutgoingData(ByteArrayReader outgoingData){
    	this.outgoingData = outgoingData;
    }

    public void setIncomingData(ByteArrayReader incomingData){
    	this.incomingData = incomingData;
    }

    public ByteArrayReader getIncomingData(){
    	return this.incomingData;
    }

}