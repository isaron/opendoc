package thread.objectlock;

import java.io.File;

public class TestObject {

	public void print() {
		
		System.out.println("start object print method...");
		
		for(int i = 0; i < 100000000; i++) {
			File f = new File("");
		}
		
		System.out.println("end object print method...");
	}
	
	public synchronized void synPrint() {
		
		System.out.println("start object synPrint method...");
		
		for(int i = 0; i < 100000000; i++) {
			File f = new File("");
		}
		
		System.out.println("end object synPrint method...");
	}
}
