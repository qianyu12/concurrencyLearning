package problems.lockout;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LockoutExample {
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);

    public void start(){
        new WorkThread().start();
    }

    public static void main(String args[]) throws InterruptedException {
        LockoutExample lockoutExample = new LockoutExample();
        lockoutExample.start();
        for(int i = 0;i<100000;i++){
            lockoutExample.put("fx");
            Thread.sleep(1000);
        }
    }
    private synchronized void put(String string) throws InterruptedException {
        blockingQueue.put(string);
    }

    private synchronized void delete() throws InterruptedException {
        String result = blockingQueue.take();
        System.out.println(result);
    }

    class  WorkThread extends Thread{
        @Override
        public void run() {
            try {
                while (true){
                    delete();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
