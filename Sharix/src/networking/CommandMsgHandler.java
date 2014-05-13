package networking;

import java.nio.ByteBuffer;

public class CommandMsgHandler extends MsgHandler {
	String msg;
	boolean sent;
	
	public CommandMsgHandler(int type, String ops, String msg) {
		super(type, ops);
		this.msg = msg;
		this.sent = false;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int read(ByteBuffer into) {
		if (!this.op.equals("r"))
			return -1;
	    
		into.clear();
		int bytesRead = 0;		
		if (!this.sent && into.remaining() < this.msg.length()) {
			System.out.println("Command msg len is < than buffer!");
			return -1;
			
		} else if (!this.sent) {
			bytesRead = this.msg.length();
			
			int pos = 0;
			while (pos < this.msg.length()) {
				into.putChar(this.msg.charAt(pos));
				++pos;
			}
			
			this.sent = true;
		}
	
		into.flip();	
		return bytesRead;
	}
	
	public void close() {
	}
}
