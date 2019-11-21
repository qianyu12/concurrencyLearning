package problems.deadlock;

public class DeadLockPhilosopher extends AbstractPhilosopher {
    DeadLockPhilosopher(int id, Chopstick firstOne, Chopstick secondOne) {
        super(id, firstOne, secondOne);
    }

    @Override
    public void eat() {
        synchronized (firstOne){
            firstOne.pickUp();
            System.out.println(Thread.currentThread().getName()+"拿起了筷子"+firstOne.ID);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondOne){
                secondOne.pickUp();
                System.out.println(Thread.currentThread().getName()+"拿起了筷子"+secondOne.ID);
                doEat();
                secondOne.pickDown();
                System.out.println(Thread.currentThread().getName()+"放下了筷子"+secondOne.ID);
            }
            firstOne.pickDown();
            System.out.println(Thread.currentThread().getName()+"放下了了筷子"+firstOne.ID);
        }
        System.out.println(Thread.currentThread().getName()+"吃完了饭");
    }


}
