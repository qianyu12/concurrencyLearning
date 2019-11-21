package atomic.solve;

/**
 * 打印结果有可能是192.168.1.2 ,2019，因为set不是原子操作
 */
public class AtomicSolve1 {
    volatile Host host = new Host("192.168.1.1",2019);
    public void updateHost(){
        Host newHost = new Host("192.168.1.2",2020);
        host = newHost;
    }
    public void connectHost(){
        System.out.println("connect to host:"+host.getIp()+"the port is:"+host.getPort());
    }

    public static void main(String[] args){
        final int  num = 5;
        AtomicSolve1 atomicExample = new AtomicSolve1();
        Thread[] theads = new Thread[num];
        theads[0] = new Thread(new Worker(atomicExample));
        for(int i = 1;i<num;i++){
            theads[i] = new Thread(new Consumer(atomicExample));
        }
        theads[1].start();
        theads[0].start();
        theads[2].start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        theads[3].start();
    }
}

class Worker implements Runnable{
    AtomicSolve1 atomicSolve1;
    public  Worker(AtomicSolve1 atomicExample){
        try{
            Thread.sleep(100);
            this.atomicSolve1 = atomicExample;
        }catch (Exception e){

        }
    }
    @Override
    public void run() {
        atomicSolve1.updateHost();
    }
}

class Consumer implements Runnable{
    AtomicSolve1 atomicSolve1;
    public  Consumer (AtomicSolve1 atomicExample){
        this.atomicSolve1 = atomicExample;
    }
    @Override
    public void run() {
        atomicSolve1.connectHost();
    }
}

final class Host{
    final private String ip;
    final private int port;

    public Host(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
