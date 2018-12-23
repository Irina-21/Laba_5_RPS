
public class SmallInteger {
	private static final int MaxValue = 10000;
	private static final int MinValue = -10000;
	private int SIvalue;
	private String message = "";

	// public methods
	public SmallInteger() {
	}

	public SmallInteger(int value) {
		if (limit(value))
			SIvalue = value;
	}

	public SmallInteger(String value) {
		try {
			int val = Integer.parseInt(value);
			if (limit(val))
				SIvalue = val;
		} catch (NumberFormatException e) {
			setMessage(e.getMessage());
		}
	}

	public SmallInteger add(SmallInteger val) {
		return together(val, 1);
	}

	public SmallInteger opposite() {
		this.SIvalue = 0 - this.SIvalue;
		return this;
	}

	public SmallInteger subtract(SmallInteger val) {
		return together(val, -1);
	}

	public SmallInteger multiply(SmallInteger val) {
		return together(val, 0);
	}

	public SmallInteger divide(SmallInteger val) {
		return together(val, 2);
	}

	public SmallInteger pow(SmallInteger val) {
		return together(val, 3);
	}

	public int GetValueInt() {
		return SIvalue;
	}

	// private methods
	private void setMessage(String str) throws StackOverflowException{
		this.message = str;
	}
	private boolean limit(int val) {
		try {
		if (val >= MinValue && val <= MaxValue)
			return true;
		else
			throw new StackOverflowException("Limit");
			//return false;
		}
		catch (StackOverflowException e) {
			setMessage(e.getMessage());
			return false;
		}
	}

	private SmallInteger together(SmallInteger val, int r) {
		if (val.message == "") {
			if (this.message == "") {
				switch (r) {
				case -1:
					this.message = "";
					if (limit(this.SIvalue - val.SIvalue))
					this.SIvalue = this.SIvalue - val.SIvalue;
					break;
				case 0:
					this.message = "";
					if (limit(this.SIvalue * val.SIvalue))
					this.SIvalue = this.SIvalue * val.SIvalue;
					break;
				case 1:
					this.message = "";
					if (limit(this.SIvalue + val.SIvalue))
					this.SIvalue = this.SIvalue + val.SIvalue;
					break;
				case 2:
					try {
						this.SIvalue = (int) (this.SIvalue / val.SIvalue);
					} catch (ArithmeticException e) {
						setMessage(e.getMessage());
						//this.message = "Divide by 0 is not allowed";
					}
					break;
				case 3:
					if (val.SIvalue != 0) {
						int i;
						int x = this.SIvalue;
						for (i = 1; i < val.SIvalue; ++i) {
						if (limit(this.SIvalue + val.SIvalue))
							this.SIvalue = this.SIvalue * x;
						else break;
						}
						if (val.SIvalue < 0)
							this.SIvalue = 0;
					} else
						this.SIvalue = 1;
					break;
				default:
					break;
				}
			}
		}		
		return this;
	}

	public String toString() {
		if (this.message != "") {
			return this.message;
		} else {
			return String.valueOf(SIvalue);
		}
	}
}

class StackOverflowException extends ArithmeticException{
	public StackOverflowException(String message) {
		super(message);
	}
}
