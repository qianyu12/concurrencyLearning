package problems.deadlock;

public class PhilosopherTest {
    final static int NUMBERS = 2;
    public static void main(String[] args){
        Chopstick[] chopsticks = new Chopstick[NUMBERS];
        for(int i = 0;i<NUMBERS;i++){
            chopsticks[i] = new Chopstick(i);
        }
        Thread thread = new DeadLockPhilosopher(1000,chopsticks[0],chopsticks[1]);
        Thread thread1 = new DeadLockPhilosopher(1111,chopsticks[1],chopsticks[0]);
        thread.start();
        thread1.start();
    }
}
