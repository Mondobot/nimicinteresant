package networking;

import java.nio.*;

public class MsgHandler {
	int type;
	String op;
	
	public MsgHandler(int type, String ops) {
		this.type = type;
		this.setOp(ops);
	}
	
	public int read(ByteBuffer into){
		return 0;
	};
	
	public void write(ByteBuffer from){
	};
	
	public void setOp(String ops) {
		if (ops.equals("w")) {
			this.op = "w";
			
		
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
