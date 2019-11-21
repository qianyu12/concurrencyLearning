package conandpro;

public class Consumer extends Thread{
    private Storage storage;
    private int number;
    Consumer(Storage storage, int number){
        this.storage = storage;
        this.number = number;
    }

    @Override
    public void run(){
        storage.consumer(number);
    }
}
