package conandpro;

public class Producer extends Thread {
    private Storage storage;
    private int number;
    Producer(Storage storage, int number){
        this.storage = storage;
        this.number = number;
    }

    @Override
    public void run(){
        storage.produce(number);
    }
}
