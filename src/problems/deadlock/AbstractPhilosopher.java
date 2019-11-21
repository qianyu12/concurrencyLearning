package problems.deadlock;

import java.util.Random;

public abstract class AbstractPhilosopher extends Thread {
    protected final int id;
    protected final Chopstick firstOne;
    protected final Chopstick secondOne;

    public AbstractPhilosopher(int id,Chopstick firstOne,Chopstick secondOne){
        super("Philosopher----"+id);
        this.id = id;
        this.firstOne = firstOne;
        this.secondOne = secondOne;
    }

    @Override
    public void run(){
        while (true){
            think();
            eat();
        }
    }

    public void think(){
        System.out.println(Thread.currentThread().getName()+"is Thinking");
        try {
            sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void eat();

    public void doEat(){
        System.out.println(Thread.currentThread().getName()+"is Eating");
        try {
            sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
