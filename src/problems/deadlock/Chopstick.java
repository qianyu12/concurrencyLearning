package problems.deadlock;

public class Chopstick {
    public final int ID;
    private boolean pickUp;

    Chopstick(int id){
        this.ID = id;
        pickUp = false;
    }

    public void pickUp(){
        pickUp = true;
    }

    public void pickDown(){
        pickUp = false;
    }

    public boolean isPickUp(){
        return pickUp;
    }
}
