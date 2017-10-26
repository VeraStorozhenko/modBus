package serial;

public enum Parity {
	NONE(0),
	ODD(1),
	EVEN(2),
	MARK(3),
	SPACE(4);

	 private final int parity;

   Parity(int parity) {
       this.parity = parity;
   }

   public int getParity() {
       return this.parity;
   }
}