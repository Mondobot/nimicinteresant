package wstests;

import java.rmi.RemoteException;

import ops.MainStub;
import ops.MainStub.*;


public class Test{
	public static void main(String []args) {
		try {
			
			MainStub mainStub = new MainStub();
		
			Add add0 = new Add();			
			add0.setA(8);
			add0.setB(9);
		
			System.out.println(mainStub.add(add0).get_return());
			
			int result;
			GetResult getResult = new GetResult();			
			getResult.setX(0);
			
			int res = mainStub.getResult(getResult).get_return();
			
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}