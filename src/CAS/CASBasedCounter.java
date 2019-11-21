package CAS;

public class CASBasedCounter {
    private volatile int count = 0;

     public int getCount(){
        return count;
    }

    public void increment(){
         int oldValue;
         int newValue;
         do {
             oldValue = count;
             newValue = oldValue+1;
         }while (compareAndSwap(oldValue,newValue));
    }

    private boolean compareAndSwap(int oldValue,int newValue){
         if(count==oldValue){
             count = newValue;
             return true;
         }else {
             return false;
         }
    }

    public static void main(String agrs[]){
         CASBasedCounter casBasedCounter = new CASBasedCounter();
         System.out.println(casBasedCounter.getCount());
         Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
         for(int i = 0;i<threads.length;i++){
             threads[i] = new WorkThread(casBasedCounter);
         }
         for(Thread thread:threads){
             thread.start();
         }

    }
    static class WorkThread extends Thread{
         CASBasedCounter casBasedCounter;
         WorkThread(CASBasedCounter casBasedCounter){
             this.casBasedCounter = casBasedCounter;
         }
        @Override
        public void run() {
            casBasedCounter.increment();
            System.out.println(currentThread().getName()+"----"+casBasedCounter.getCount());
        }
    }
}
