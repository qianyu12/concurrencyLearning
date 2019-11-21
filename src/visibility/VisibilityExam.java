package visibility;


public class VisibilityExam {
    public static void main(String[] args){
        TimeConsumerTask task = new TimeConsumerTask();
        Thread thread = new Thread(task);
        thread.start();
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){

        }finally {
            task.cancel();
            System.out.println("here");
        }
    }
}
class TimeConsumerTask implements Runnable{
    private boolean isCancel = false;
    @Override
    public void run() {
        while (!isCancel){

        }
        if(isCancel){
            System.out.println("is Canceled");
        }else {
            System.out.println("done");
        }
    }

    public void cancel(){
        this.isCancel = true;
    }
}
