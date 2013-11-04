package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BussinessInvocation implements InvocationHandler {
	
	private Object bussinessService;
	
	public BussinessInvocation(Object bussinessService) {
		this.bussinessService = bussinessService;
	}

	public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
		
		System.out.println("deal with before save...");
		
		System.out.println("method name: " + method.getName());
		method.invoke(bussinessService, params);
		
		System.out.println("deal with after save...");
		
		return null;
	}
}
