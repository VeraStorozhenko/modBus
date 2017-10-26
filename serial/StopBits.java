package serial;

public enum StopBits { NONE(0), ONE(1), TWO(2), ONEPOINTFIVE(3);

	 private final int stopBits;

	 StopBits(int stopBits) {
       this.stopBits = stopBits;
   }

	 public int getStopBits(){
		 return this.stopBits;
	 }
}
