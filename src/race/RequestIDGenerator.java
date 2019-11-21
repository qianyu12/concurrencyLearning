package race;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestIDGenerator {
    private final static RequestIDGenerator INSTANCE = new RequestIDGenerator();
    private final static short UPPER =  999;
    private AtomicInteger sequence = new AtomicInteger(-1);
    private int nextSequence(){
        if (sequence.get()>=UPPER){
            sequence.set(0);
        }else {
            sequence.incrementAndGet();
        }
        return sequence.get();
    }


    public String nextID(){
        DecimalFormat decimalFormat = new DecimalFormat("000");
        int sequence = nextSequence();
        return "0012"+decimalFormat.format(sequence);
    }

    private RequestIDGenerator(){

    }

    public static RequestIDGenerator getInstance(){
        return  INSTANCE;
    }

    public static void main(String args[]){
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numberOfThreads];
        for(int i = 0;i<numberOfThreads;i++){
            threads[i] = new WorkThread(i,10);
        }

        for(Thread thread:threads){
            thread.start();
        }

    }

}
class WorkThread extends Thread{
    private int requestNumbers;
    public WorkThread(int id, int requestNumbers){
        super("worker-"+id);
        this.requestNumbers = requestNumbers;
    }

    @Override
    public void run(){
        RequestIDGenerator requestIDGenerator = RequestIDGenerator.getInstance();
        while (requestNumbers-->0){
            String requestID = requestIDGenerator.nextID();
            try {
                Thread.sleep(50);
                System.out.println("Thread"+Thread.currentThread().getName()+"get sequenceID"+requestID);
                if(requestID.endsWith("79")){
                    System.out.println("no conflict");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
