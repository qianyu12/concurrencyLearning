package threadmanage;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 当线程因为异常退出的时候，新建一个线程去代替它
 * */
public class ThreadMonitor {
    volatile boolean isInited = false;
    private BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(100);

    public static void main(String[] args) throws InterruptedException {
        ThreadMonitor threadMonitor = new ThreadMonitor();
        threadMonitor.newThread();
        for(int i = 0;i<100;i++){
            threadMonitor.produce("product"+i);
        }
    }

    public void newThread(){
        if(isInited){
            return;
        }
        Thread thread = new WorkThread();
        thread.setName("workThread"+new Random().nextInt(12));
        thread.setUncaughtExceptionHandler(new Monitor());
        thread.start();
        isInited = true;
    }

    public void produce(String msg) throws InterruptedException {
        blockingQueue.put(msg);
        System.out.println("making product "+ msg);
    }
    class Monitor implements Thread.UncaughtExceptionHandler{
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName()+"happens error, it is "+e);
            //new a thread
            System.out.println("about to start thread");
            isInited = false;
            newThread();
        }
    }

    class WorkThread extends Thread{
        @Override
        public void run(){
            try {
                while (true){
                    String msg = blockingQueue.take();
                    doProcess(msg);
                    sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void doProcess(String msg){
            System.out.println(getName()+" takes the value "+msg);
            Random random = new Random();
            int rd=  random.nextInt(100);
            if(rd<20){
                throw new RuntimeException();
            }
        }
    }
}
