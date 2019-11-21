package waitnotifyexam;

import java.util.Random;

public class TimeOutWaitEample {
    private static final Object object = new Object();
    private static volatile boolean ready = false;
    private static Random random = new Random();

    public static void main(String[] args){
        Thread t = new Thread(){
            int i = 0;
            @Override
            public void run() {
                while (i<10){
                    synchronized (object){
                        System.out.println("notify"+i);
                        ready = random.nextInt(100)<20?true:false;
                        if(ready){
                            i++;
                            object.notify();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        waitAMoment(1000);
    }

    public static void waitAMoment(long waitTime){
        long startTime = System.currentTimeMillis();
        long throughTime;
        synchronized (object){
            while (!ready){
                try {
                    long nowTime =System.currentTimeMillis();
                    throughTime = nowTime - startTime;
                    System.out.println("througnTiem is "+throughTime);
                    if (throughTime>waitTime){
                        break;
                    }
                    object.wait(waitTime);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(ready){
                //do something
                System.out.println("wait end");
            }else {
                System.out.println("time out");
            }
        }
    }
}
