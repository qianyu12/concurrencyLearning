package race;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class ErrorRequestIDGenerator {
    private final static ErrorRequestIDGenerator INSTANCE = new ErrorRequestIDGenerator();
    private final static short UPPER =  999;
    private int sequence = -1;
    private int nextSequence(){
        if (sequence>=UPPER){
            sequence = 0;
        }else {
            sequence++;//no atomic
        }
        return sequence;
    }


    public String nextID(){
        DecimalFormat decimalFormat = new DecimalFormat("000");
        int sequence = nextSequence();
        return "0012"+decimalFormat.format(sequence);
    }

    private ErrorRequestIDGenerator(){

    }

    public static ErrorRequestIDGenerator getInstance(){
        return  INSTANCE;
    }

    public static void main(String args[]){
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numberOfThreads];
        for(int i = 0;i<numberOfThreads;i++){
            threads[i] = new WorkThread2(i,10);
        }

        for(Thread thread:threads){
            thread.start();
        }

    }

}
class WorkThread2 extends Thread{
    private int requestNumbers;
    public WorkThread2(int id, int requestNumbers){
        super("worker-"+id);
        this.requestNumbers = requestNumbers;
    }

    @Override
    public void run(){
        ErrorRequestIDGenerator requestIDGenerator = ErrorRequestIDGenerator.getInstance();
        while (requestNumbers-->0){
            String requestID = requestIDGenerator.nextID();
            try {
                Thread.sleep(100);
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
