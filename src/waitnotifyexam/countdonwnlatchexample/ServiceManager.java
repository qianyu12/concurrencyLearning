package waitnotifyexam.countdonwnlatchexample;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ServiceManager {
    private CountDownLatch countDownLatch;
    private Set<AbstractService> services;

    public void init(){
        countDownLatch = new CountDownLatch(3);
        services = new HashSet<>();
        services.add(new Service1(countDownLatch));
        services.add(new Service2(countDownLatch));
        services.add(new Service3(countDownLatch));
    }

    public void startAllServices(){
        init();
        for(AbstractService service:services){
            service.start();
        }
    }

    public boolean checkIfAllServicesStarted(){
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(AbstractService service:services){
            if(!service.isStarted()){
                System.out.println(service.getClass().getSimpleName()+"not started");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        ServiceManager serviceManager =  new ServiceManager();
        serviceManager.startAllServices();
        boolean allStated = serviceManager.checkIfAllServicesStarted();
        if(!allStated){
            System.out.println("not all started");
            System.exit(1);
        }
    }
}
