package waitnotifyexam;

public class JoinExam {
    public static void main(String[] args){
        Thread thread = new JoinThread();
        thread.start();
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("finished");
    }

    static class JoinThread extends Thread{
        @Override
        public void run(){
           for(int i = 0;i<100000;i++){
               System.out.println(i);
           }
        }
    }
}
