package waitnotifyexam.countdonwnlatchexample;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractService extends Thread {
    private CountDownLatch countDownLatch;
    private boolean isStarted;

    public boolean isStarted(){
        return isStarted;
    }
    public abstract boolean doStart();
    AbstractService(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            isStarted = doStart();
            try {
                sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isStarted){
                System.out.println(getClass().getSimpleName()+"started successfully");
            }else {
                System.out.println(getClass().getSimpleName()+"started unsuccessfully");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(getClass().getSimpleName()+"started error");
        }finally {
            countDownLatch.countDown();
        }
    }
}
