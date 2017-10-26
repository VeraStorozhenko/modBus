package byteArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteArrayReader {

    public ByteArrayReader(byte[] source){
         this(source, 0, source.length);
    }

    public ByteArrayReader(byte[] source, int offset, int count){
        this.Length = count;
        this._buffer = new byte[count];

        this._buffer = arrayCopy(source, offset, count);
        this.Reset();
   }

    public static byte[] arrayCopy(byte[] source, int offset, int count){
    	byte[] target = new byte[count];
        //Buffer.BlockCopy(source, offset, target, 0, count);
        System.arraycopy(source, offset, target, 0, count);
        return target;
    }



    private byte[] _buffer;
    public int Position;
    public int Length;

    public int getPosition(){
    	return this.Position;
    }

    public void setPosition(int position){
    	this.Position = position;
    }

    public int getLength(){
    	return this.Length;
    }

    public void setLength(int length){
    	this.Length = length;
    }

    public byte[] ToByteArray(){
        byte[] clone = new byte[this.Length];
        System.arraycopy(this._buffer, 0, clone, 0, this.Length);
        return clone;
    }

    public void Reset(){
        this.Position = -1;
    }

    public boolean EndOfBuffer;
    public boolean isEndOfBuffer(){
    	return this.Position >= this.Length - 1;
    }

    public boolean CanRead(int count){
        int len = this.Length - this.Position - 1;
        return (count <= len);
    }

    public byte Peek(){ //Read the current pointed byte, but without moving the pointer
        return this._buffer[this.Position];
    }

    public byte ReadByte(){// Read the current byte and move the pointer accordingly
        return this._buffer[++this.Position];
    }

    public boolean TryReadByte(byte value){
        if (this.CanRead(1)){
            value = this.ReadByte();
            return true;
        }else{
            value = 0;
            return false;
        }
    }

    public byte[] ReadBytes(int count){
        //byte[] target = ByteArrayHelpers.ArrayCopy(this._buffer, this.Position + 1, count);
        byte[] target = new byte[count];
        System.arraycopy( this._buffer, this.Position + 1, target, 0, count);
        this.Position += count;
        return target;
    }

    public short[] readBytesToShort(int count){

    	short[] shorts = new short[count/2];
    	byte[] bytes = new byte[count];

        System.arraycopy( this._buffer, this.Position + 1, bytes, 0, count);

        /*if(count == 1){
        	shorts[0] = this._buffer[this.Position];
        }else{*/
        	this.Position += count;
        	ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(shorts);// to turn bytes to shorts as either big endian or little endian.
        //}
        return shorts;
    }

	public short bytesToShort(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}

	public byte[] shortToBytes(short value) {
	    return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array();
	}





    public byte[] ReadToEnd(){
        return this.ReadBytes(this.Length - (this.Position + 1));
    }

    public short ReadInt16LE(){ //Little Endian
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.ReadInt16LE(this._buffer, ptr);
    }

    public short ReadInt16BE(){ //Big Endian
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.ReadInt16BE(this._buffer, ptr);
    }


    public short ReadUInt16LE(){
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.ReadInt16LE(this._buffer, ptr);
    }

    public short ReadUInt16BE(){
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.ReadInt16BE(this._buffer, ptr);
    }

    public int ReadInt32LE()
    {
        int ptr = this.Position + 1;
        this.Position += 4;
        return ByteArrayHelpers.ReadInt32LE( this._buffer, ptr);
    }


    public int ReadInt32BE(){
        int ptr = this.Position + 1;
        this.Position += 4;
        return ByteArrayHelpers.ReadInt32BE(this._buffer, ptr);
    }

    public long ReadInt64LE(){
        int ptr = this.Position + 1;
        this.Position += 8;
        return ByteArrayHelpers.ReadInt64LE(this._buffer, ptr);
    }

    public long ReadInt64BE(){
        int ptr = this.Position + 1;
        this.Position += 8;
        return ByteArrayHelpers.ReadInt64BE(this._buffer, ptr);
    }

    public float Read87LE(){
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.Read87LE(this._buffer, ptr);
    }

    public float Read87BE(){
        int ptr = this.Position + 1;
        this.Position += 2;
        return ByteArrayHelpers.Read87BE(this._buffer, ptr);
    }

    public float Read1912LE(){
        int ptr = this.Position + 1;
        this.Position += 4;
        return ByteArrayHelpers.Read1912LE(this._buffer, ptr);
    }

    public float Read1912BE(){
        int ptr = this.Position + 1;
        this.Position += 4;
        return ByteArrayHelpers.Read1912BE(this._buffer, ptr);
    }
}

