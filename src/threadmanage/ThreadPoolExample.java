package threadmanage;

import java.util.concurrent.*;

public class ThreadPoolExample {
    final static int NProceses = Runtime.getRuntime().availableProcessors();
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(NProceses,NProceses*2,4, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100),new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args){
        ThreadPoolExample threadPoolExample = new ThreadPoolExample();
        Future<String> result =threadPoolExample.recognizeImage("");
        try {
            threadPoolExample.dosomething();
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Future<String> recognizeImage(String path){
       return threadPoolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return doRecognize(path);
            }
        });
    }

    public void dosomething() throws InterruptedException {
        Thread.sleep(200);
        System.out.println("do other thing");
    }
    public String doRecognize(String path) throws InterruptedException {
        System.out.println("i'm doing recognize");
        Thread.sleep(1000);
        System.out.println("recognize finished");
        return "川 7912";
    }

}
