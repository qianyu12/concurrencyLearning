package atomic;

/**
 * 打印结果有可能是192.168.1.2 ,2019，因为set不是原子操作
 */
public class AtomicExample {
    Host host = new Host("192.168.1.1",2019);
    public void updateHost(){
        host.updateHost("192.168.1.2",2020);
    }
    public void connectHost(){
        System.out.println("connect to host:"+host.getIp()+"the port is:"+host.getPort());
    }

    public static void main(String[] args){
        final int  num = 5;
        AtomicExample atomicExample = new AtomicExample();
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
    AtomicExample atomicExample;
    public  Worker(AtomicExample atomicExample){
        try{
            Thread.sleep(100);
            this.atomicExample = atomicExample;
        }catch (Exception e){

        }
    }
    @Override
    public void run() {
        atomicExample.updateHost();
    }
}

class Consumer implements Runnable{
    AtomicExample atomicExample;
    public  Consumer (AtomicExample atomicExample){
        this.atomicExample = atomicExample;
    }
    @Override
    public void run() {
        atomicExample.connectHost();
    }
}

class Host{
    private String ip;
    private int port;

    public Host(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void updateHost(String ip, int port){
        this.setIp(ip);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setPort(port);
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
