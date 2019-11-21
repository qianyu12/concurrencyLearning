package problems.deadlock.solve;

import problems.deadlock.AbstractPhilosopher;
import problems.deadlock.Chopstick;

public class GlobalLockBasedPhilosopher extends AbstractPhilosopher {
    private final static Object object = new Object();
    GlobalLockBasedPhilosopher(int id, Chopstick firstOne, Chopstick secondOne) {
     super(id,firstOne,secondOne);
    }
    @Override
    public void eat() {
        synchronized (object){
            firstOne.pickUp();
            secondOne.pickUp();
            firstOne.pickDown();
            secondOne.pickDown();
        }
    }
}
