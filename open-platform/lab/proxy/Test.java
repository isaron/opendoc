package proxy;

import java.lang.reflect.Proxy;

public class Test {

	public static void main(String[] args) {
		
		// bussiness implement service
		IBussinessService bussinessService = new BussinessService();
		
		// bussiness aop invocation handler
		BussinessInvocation aopHandler = new BussinessInvocation(bussinessService);
		
		// build proxy
		IBussinessService proxy = (IBussinessService) Proxy.newProxyInstance(
				bussinessService.getClass().getClassLoader(), bussinessService
						.getClass().getInterfaces(), aopHandler);
		
		// invoke method
		proxy.save();
		
		System.out.println("=========================");
		
		proxy.otherMethod();
	}
}
