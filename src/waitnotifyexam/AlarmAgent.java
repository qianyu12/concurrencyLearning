package waitnotifyexam;

import java.util.Random;


public class AlarmAgent {
    //single-mode
    private static class AlarmAgentLoad{
        private final static AlarmAgent alarmAgent= new AlarmAgent();
    }
    //hear thread
     class  HeartThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                while (true){
                    if(checkConnection()){
                        connectToServer = true;
                    }else {
                        connectToServer = false;
                        System.out.println("连接已经断开，准备重新连接中....");
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        private boolean checkConnection(){
            boolean isConnect = true;
            Random random = new Random();
            int rd = random.nextInt(1000);
            if(rd<500){
                isConnect = false;
            }
            return isConnect;
        }
    }
    //whether connect server or not
    private volatile boolean connectToServer = false;
    //heart thread:monitor connect is normal or not

    private AlarmAgent(){}

    private final  HeartThread heartThread = new HeartThread();

    private void connectToServer(){
        //start a thread to connect the server;
        new Thread(){
            @Override
            public void run() {
                doConnection();
            }
        }.start();
    }

    public void doConnection(){
        //模拟连接时间：
        try {
            Thread.sleep(500);
            synchronized (this){
                this.connectToServer = true;
                System.out.println("连接成功");
                notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void sendAlarm(String alarmInfo){
        synchronized (this){
            try{
                while (!connectToServer){
                    System.out.println("无法发送信息，连接断开");
                    wait();
                }
                //do something
                System.out.println(alarmInfo);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void init(){
        connectToServer();
        heartThread.setDaemon(true);
        heartThread.start();
    }

    public static AlarmAgent getInstance(){
        return AlarmAgentLoad.alarmAgent;
    }
    public static void main(String[] args){
        AlarmAgent alarmAgent = AlarmAgent.getInstance();
        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        for(int i = 0;i<threads.length;i++){
            threads[i] = new runThread(alarmAgent,"i am " + (i+1));
        }
        for(Thread thread:threads){
            thread.start();
        }
        alarmAgent.init();
    }
    public static class runThread extends Thread{
        private AlarmAgent alarmAgent;
        private String info;
        public runThread(AlarmAgent alarmAgent,String info){
            this.alarmAgent = alarmAgent;
            this.info = info;
        }
        @Override
        public void run(){
            alarmAgent.sendAlarm(getName()+"send info"+info+"success");
        }
    }
}
