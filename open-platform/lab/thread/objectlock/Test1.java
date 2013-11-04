package thread.objectlock;

/**
 * 实验：类对象调用（非多线程）同步锁是否有效
 * 
 * 结果：顺序调用不会有同步问题存在
 * 
 * 思考：但如果是WEB请求，对于每个请求WEB服务器都是启动一个线程来处理，固继续实验
 * 
 * @author Administrator
 *
 */
public class Test1 {

	public static void main(String[] args) {

		System.out.println("obj1 run...");
		TestObject obj1 = new TestObject();
		obj1.print();
		
		System.out.println("obj2 run...");
		TestObject obj2 = new TestObject();
		obj2.print();
	}
}
