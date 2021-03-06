package async;

import java.util.concurrent.TimeUnit;

public class Volatile {
	//如果变量被volatile修饰
	// get: 能保证在引用该变量时取回的值始终来自主内存
	// set: 能保证该变量的值被修改时即时同步回主内存中
	//1. 不会取回初始化到一半的值
	//2. 保证在引用变量前的操作完整执行，保证在引用变量后的操作没有执行
	//3. 保证引用变量的逻辑不会被编译器优化
	private static boolean stopRequested;

	public static void request() {
		while (!getFlag()) {
		}
	}

	public static void setFlag(boolean flag) {
		stopRequested = flag;
	}

	//如果方法被synchronized修饰
	// 执行方法前会将主内存中该方法所需要访问的变量重新同步一次到工作内存中(可能取回初始化到一半的值)
	// 执行方法后会将工作内存中该方法修改过的变量重新同步一次回主内存中
	public static boolean getFlag() {
		return stopRequested;
	}

	public static void stopRequest() {
		setFlag(true);
	}

	public static void main(String[] args) throws Exception{
		new Thread(new Runnable() {
			public void run() {
				request();
			}
		}).start();
		
		TimeUnit.SECONDS.sleep(1);
		stopRequest();
	}
}
