package conandpro;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args){
        Storage storage = new MyStorage();
        LinkedList<Thread> threads = new LinkedList<>();
        for(int i = 1;i<10;i++){
            Thread thread = new Producer(storage,i*i-2*i<10?10:i*i-2*i);
            threads.add(thread);
        }
        for(int i = 1;i<5;i++){
            Thread thread = new Consumer(storage,i*i);
            threads.add(thread);
        }
        for(Thread thread:threads){
            thread.start();
        }
    }
}
