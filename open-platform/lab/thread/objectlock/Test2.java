package thread.objectlock;

/**
 * 实验：在多线程中（模拟WEB请求）调用类对象方法
 * 
 * 结果：WEB应用中隐含了多线程事实，调用业务方法经常存在同步问题
 * 
 * @author Administrator
 *
 */
public class Test2 {

	public static void main(String[] args) {

		TestObject obj = new TestObject();
		
		// WEB请求到来，分配一个新线程
		Thread t1 = new Thread(new RequestHandler(obj));
		t1.start();
		
		Thread t2 = new Thread(new RequestHandler(obj));
		t2.start();
	}
}

class RequestHandler implements Runnable {
	
	TestObject obj;
	
	/**
	 * 每个线程公用同一个对象，模拟Spring单例Bean
	 */
	public RequestHandler(TestObject obj) {
		this.obj = obj;
	}
	
	public void run() {
		
		// 调用类对象方法（业务处理）
		
		// obj.print();
		
		// obj.synPrint();
		
		TestObject everyTimeObj = new TestObject();
		everyTimeObj.print();
	}
}
