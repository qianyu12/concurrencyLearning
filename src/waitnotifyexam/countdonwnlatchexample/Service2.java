package waitnotifyexam.countdonwnlatchexample;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Service2 extends AbstractService{

    Service2(CountDownLatch countDownLatch){
        super(countDownLatch);
    }
    @Override
    public boolean doStart() {
        System.out.println(this.getClass().getSimpleName()+"is starting");
        Random random = new Random();
        int rd = random.nextInt(100);
        return rd>=30;
    }

}
