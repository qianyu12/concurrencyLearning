package waitnotifyexam.countdonwnlatchexample;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Service3 extends AbstractService {
    Service3(CountDownLatch countDownLatch){
        super(countDownLatch);
    }
    @Override
    public boolean doStart() {
        System.out.println(this.getClass().getSimpleName()+"is starting");
        Random random = new Random();
        int rd = random.nextInt(100);
        return rd>=40;
    }
}
