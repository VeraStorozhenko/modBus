package byteArray;

public class ByteArrayWriter {

	private final int ChunkSize = 64;

    public ByteArrayWriter(){
        this._buffer = new byte[ChunkSize];
        this._proxy = new byte[8];
    }

    public ByteArrayWriter(byte[] initial){
        this._length = initial.length;
        this._buffer = new byte[this._length];
        System.arraycopy(initial, 0, this._buffer, 0, this._length);
    }


    private byte[] _buffer;
    private int _length;
    private  byte[] _proxy;
    //public int Length;

    public void setLength(int length){
        this._length = length;
    }

    public int getLength(){
        return this._length;
    }

    public byte[] ToByteArray(){
        byte[] clone = new byte[this._length];
        System.arraycopy(this._buffer, 0, clone, 0, this._length);
        return clone;
    }

    public ByteArrayReader ToReader(){
        return new ByteArrayReader(this._buffer, 0, this._length);
    }

    public void Drop() throws Exception{
        this.CheckImmutable();
        this._length = 0;
    }

    public void Drop(int count) throws Exception{
        this.CheckImmutable();
        if (count < 0 || count > this._length){
            throw new Exception("count");
        }

        this._length -= count;
    }

    public void WriteByte(byte value) throws Exception{
        this.Allocate(1);
        this._buffer[this._length++] = value;
    }


    public void WriteBytes(byte[] values, int offset, int count) throws Exception{
        this.Allocate(count);
        System.arraycopy(values, offset, this._buffer, this._length, count);
        //System.arraycopy(values, offset, this._buffer, this._length, count);
        this._length += count;
    }

    public void WriteBytes(byte[] values) throws Exception{
        this.WriteBytes(values, 0, values.length);
    }


    public void WriteBytes(ByteArrayWriter writer) throws Exception {
        this.WriteBytes( writer._buffer, 0, writer._length);
    }

    public void WriteBytes(ByteArrayReader reader) throws Exception{
        this.WriteBytes(reader.ToByteArray());
    }

    public void WriteInt16LE(short value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteUInt16LE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 2);
    }


    public void WriteInt16BE(short value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteInt16BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 2);
    }



    public void WriteUInt16LE(short value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteUInt16LE(this._proxy, 0, value);
        this.WriteBytes( this._proxy, 0, 2);
    }


    public void WriteUInt16BE(short value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteUInt16BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 2);
    }

    public void WriteInt32LE(int value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteInt32LE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 4);
    }

    public void WriteInt32BE(int value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteInt32BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0,  4);
    }

    public void WriteInt64LE(int value) throws Exception {
        this.CheckImmutable();
        ByteArrayHelpers.WriteInt64LE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 8);
    }

    public void WriteInt64BE(long value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.WriteInt64BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 8);
    }

    public void Write87LE(float value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.Write87LE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 4);
    }

    public void Write87BE(float value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.Write87BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 4);
    }

    public void Write1912LE(float value) throws Exception {
        this.CheckImmutable();
        ByteArrayHelpers.Write1912LE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 4);
    }

    public void Write1912BE(float value) throws Exception{
        this.CheckImmutable();
        ByteArrayHelpers.Write1912BE(this._proxy, 0, value);
        this.WriteBytes(this._proxy, 0, 4);
    }

    private void Allocate(int count) throws Exception{
        this.CheckImmutable();
        int size = this._buffer.length;
        int newLen = this._length + count;
        if (newLen < size)
            return;
        do{
            size += ChunkSize;
        } while (size < newLen);

        byte[] temp = new byte[size];
        System.arraycopy(this._buffer, 0, temp, 0, this._buffer.length);
        this._buffer = temp;
    }


    private void CheckImmutable() throws Exception{
        if (this._proxy == null)
            throw new Exception();
    }
}
