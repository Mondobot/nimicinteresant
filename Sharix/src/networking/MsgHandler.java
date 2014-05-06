package networking;

import java.nio.*;

public class MsgHandler {
	int type;
	String op;

	public final static int GETNAME = 0;
	public final static int RESNAME = 1;
	public final static int GETFILE = 2;
	public final static int ENDFILE = 3;
	public final static int ACKFILE = 4;
	public final static int NACKFILE = 5;
	public final static int DATAPACK = 6;
	
	public MsgHandler(int type, String ops) {
		this.type = type;
		this.setOp(ops);
	}
	
	public int read(ByteBuffer into){
		return 0;
	};
	
	public int write(ByteBuffer from){
		return 0;
	};
	
	public void setOp(String ops) {
		if (ops.equals("rw")) {
			this.op = "rw";
			
		
		} else if (ops.equals("r")) {
			this.op = "r";
		}
	}
	
	public int getType() {
		return this.type;
	}

	public String getOp() {
		return this.op;
	}
}
