package byteArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

public class ByteArrayHelpers {

	public static float ReadSingle(short[] data, int offset){
		byte[] asByte = new byte[] {
			(byte)(data[offset + 1] % 256),
            (byte)(data[offset + 1] / 256),
            (byte)(data[offset] % 256),
            (byte)(data[offset] / 256) };
		//String s1 = Arrays.toString(asByte);
		float f = ByteBuffer.wrap(asByte).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		return f;
    }

	public static void WriteSingle(short[] data, int offset, float value) {
		int i = Float.floatToIntBits(value);
        data[offset+1] = (short)i;
        data[offset] = (short)(i >> 16);
	}

	public static byte[] ArrayCopy( byte[] source, int offset, int count){ //Array src, int srcOffset, Array dst, int dstOffset, int count
		//count = 80;
		byte[] target = new byte[count];
        //System.arraycopy(source, offset, target, offset, source.length);
        return target;
    }

	public static short ReadInt16LE(byte[] buffer, int offset){
        //return (short)(buffer[offset] | (buffer[offset + 1] << 8));
		//return (short)(buffer[offset] | (buffer[offset + 1] >> 8));
		return (short)((buffer[offset + 1]) | (buffer[offset] << 8));
     }

	public static short ReadInt16BE(byte[] buffer, int offset) {
		//short read = (short)((buffer[offset + 1]) | (buffer[offset] << 8));
		//System.out.println("READADDRESS" + read);
		short read =  (short) (((buffer[offset + 1]) & 0xFF) << 8 | ((buffer[offset]) & 0xFF));
		return read;

	}

    public static int ReadInt32LE( byte[] buffer, int offset){
    	return (int)(buffer[offset] | (buffer[offset + 1] << 8) | (buffer[offset + 2] << 16) | (buffer[offset + 3] << 24));
    }

   public static void WriteInt32LE(byte[] buffer, int offset, int value){
	   buffer[offset] = (byte)value;
       buffer[offset + 1] = (byte)(value >> 8);
       buffer[offset + 2] = (byte)(value >> 16);
       buffer[offset + 3] = (byte)(value >> 24);
   }

   public static int ReadInt32BE(byte[] buffer, int offset){
	   return (int)(buffer[offset + 3] | (buffer[offset + 2] << 8) | (buffer[offset + 1] << 16) | (buffer[offset] << 24));
    }

   public static void WriteInt32BE(byte[] buffer, int offset, int value){
	   buffer[offset] = (byte)(value >> 24);
       buffer[offset + 1] = (byte)(value >> 16);
       buffer[offset + 2] = (byte)(value >> 8);
       buffer[offset + 3] = (byte)value;
   }

   public static long ReadInt64LE(byte[] buffer, int offset){
	   return (int)ReadInt32LE(buffer, offset) | ((long)ReadInt32LE(buffer, offset + 4) << 32);
    }

    public static void WriteInt64LE(byte[] buffer, int offset, long value){
        buffer[offset] = (byte)value;
        buffer[offset + 1] = (byte)(value >> 8);
        buffer[offset + 2] = (byte)(value >> 16);
        buffer[offset + 3] = (byte)(value >> 24);
        buffer[offset + 4] = (byte)(value >> 32);
        buffer[offset + 5] = (byte)(value >> 40);
        buffer[offset + 6] = (byte)(value >> 48);
        buffer[offset + 7] = (byte)(value >> 56);
     }

     public static long ReadInt64BE(byte[] buffer, int offset){
            return (int)ReadInt32BE(buffer, offset + 4) |  ((long)ReadInt32BE(buffer, offset) << 32);
        }

     public static void WriteInt64BE(byte[] buffer, int offset, long value){
            buffer[offset] = (byte)(value >> 56);
            buffer[offset + 1] = (byte)(value >> 48);
            buffer[offset + 2] = (byte)(value >> 40);
            buffer[offset + 3] = (byte)(value >> 32);
            buffer[offset + 4] = (byte)(value >> 24);
            buffer[offset + 5] = (byte)(value >> 16);
            buffer[offset + 6] = (byte)(value >> 8);
            buffer[offset + 7] = (byte)value;
        }

     public static float Read87LE( byte[] buffer, int offset){
    	 byte temp = (byte) (buffer[offset] | (buffer[offset + 1] << 8));
             return (float)(temp / 128f);
     }

    public static void Write87LE(byte[] buffer, int offset, float value){
    	int ivalue = (int)(value * 128f);
        buffer[offset] = (byte)ivalue;
        buffer[offset + 1] = (byte)(ivalue >> 8);
    }

    public static float Read87BE( byte[] buffer, int offset){
    	int temp =  (buffer[offset + 1] | (buffer[offset] << 8));
    	return (float)(temp / 128f);
    }

    public static float ReadIEEE754(short[] buffer, byte offset){

    	byte arr[] = new byte[4];
    	byte el0 =  (byte)(buffer[0] & 0xff);
        byte el1 = (byte)(buffer[0 >> 8] & 0xff);
    	byte el2 =  (byte)(buffer[1] & 0xff);
        byte el3 = (byte)(buffer[1 >> 8] & 0xff);

    	arr[0] = el0;
    	arr[1] = el1;
    	arr[2] = el2;
    	arr[3] = el3;

    	float f = ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).getFloat();

    	return f;
    }

     public static void Write87BE(byte[] buffer, int offset, float value){
    	 int ivalue = (int)(value * 128f);
    	 buffer[offset] = (byte)(ivalue >> 8);
         buffer[offset + 1] = (byte)ivalue;
     }

     public static float Read1912LE( byte[] buffer, int offset){
    	 int temp =  (buffer[offset] | (buffer[offset + 1] << 8) | (buffer[offset + 2] << 16) | (buffer[offset + 3] << 24));
         return (float)(temp / 4096f);
     }

      public static void Write1912LE( byte[] buffer, int offset, float value){
          int ivalue = (int)(value * 4096f);
          buffer[offset] = (byte)ivalue;
          buffer[offset + 1] = (byte)(ivalue >> 8);
          buffer[offset + 2] = (byte)(ivalue >> 16);
          buffer[offset + 3] = (byte)(ivalue >> 24);
      }

     public static float Read1912BE( byte[] buffer, int offset) {
        int temp = buffer[offset + 3] | (buffer[offset + 2] << 8) | (buffer[offset + 1] << 16) | (buffer[offset] << 24);
        return (float)(temp / 4096f);
     }

     public static void Write1912BE(byte[] buffer, int offset, float value){
         int ivalue = (int)(value * 4096f);
         buffer[offset] = (byte)(ivalue >> 24);
         buffer[offset + 1] = (byte)(ivalue >> 16);
         buffer[offset + 2] = (byte)(ivalue >> 8);
         buffer[offset + 3] = (byte)ivalue;
     }

     public static void WriteInt16BE(byte[] buffer, int offset, short value){
         buffer[offset] = (byte)(value >> 8);
         buffer[offset + 1] = (byte)value;
   }

     public static void WriteUInt16LE(byte[] buffer, int offset, short value){
        buffer[offset] = (byte)value;
        buffer[offset + 1] = (byte)(value >> 8);
    }

     public static void WriteUInt16BE(byte[] buffer, int offset, short value){
    	 buffer[offset] = (byte)(value >> 8);
         buffer[offset + 1] = (byte)value;
    }
}


