package conandpro;

import java.util.LinkedList;

public class MyStorage implements Storage {
    private final int MAX_TASKS = 99;
    private final LinkedList tasks = new LinkedList();
    @Override
    public void produce(int numbers) {
        synchronized (tasks){
            while(tasks.size()+numbers>MAX_TASKS){
                System.out.printf("新任务的数量是%d，当前任务数量是%d,粮仓的最大数量是%d.\n",numbers,tasks.size(),MAX_TASKS);
                try {
                    tasks.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            for(int i = 0;i<numbers;i++){
                tasks.add(new Object());
            }
            System.out.printf("添加%d个新的任务,现在任务总数是%d\n",numbers,tasks.size());
            tasks.notifyAll();
        }
    }

    @Override
    public void consumer(int numbers) {
        synchronized (tasks){
            while(numbers>tasks.size()){
                System.out.printf("消费的任务的数量是%d，当前任务数量是%d，不足消费\n",numbers,tasks.size());
                try {
                    tasks.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            for(int i = 0;i<numbers;i++){
                tasks.remove(0);
            }
            System.out.printf("消费%d新的任务,现在任务总数是%d\n",numbers,tasks.size());
            tasks.notifyAll();
        }
    }


}
