package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLockExample {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private int value = 0;
    public void read(){
        try{
            readLock.lock();
            System.out.println(Thread.currentThread().getName()+"is reading and the value is:"+value);
        }
        finally {
            readLock.unlock();
        }
    }

    public void write(){
        try{
            writeLock.lock();
            System.out.println(Thread.currentThread().getName()+" isWriting");
            value++;
        }
        finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args){
        int numberOfProcess = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numberOfProcess];
        ReadAndWriteLockExample readAndWriteLockExample = new ReadAndWriteLockExample();
        for(int i = 0;i<numberOfProcess-1;i++){
            threads[i] =  new ReadThread("reade"+i,readAndWriteLockExample);
        }
        threads[numberOfProcess-1] = new WriteThread("writer",readAndWriteLockExample);
        for(Thread thread:threads){
            thread.start();
        }
    }
}
class ReadThread extends Thread{
    ReadAndWriteLockExample readAndWriteLockExample;
    ReadThread(String name,ReadAndWriteLockExample readAndWriteLockExample){
        super(name);
        this.readAndWriteLockExample = readAndWriteLockExample;
    }

    @Override
    public void run(){
        while (true){
            readAndWriteLockExample.read();
        }
    }
}

class WriteThread extends Thread{
    ReadAndWriteLockExample readAndWriteLockExample;
    WriteThread(String name, ReadAndWriteLockExample readAndWriteLockExample){
        super(name);
        this.readAndWriteLockExample = readAndWriteLockExample;
    }
    @Override
    public void run(){
        while (true){
            readAndWriteLockExample.write();
        }
    }
}
