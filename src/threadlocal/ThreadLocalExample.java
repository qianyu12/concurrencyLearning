package threadlocal;

import java.security.SecureRandom;

public class ThreadLocalExample {
    static class ThreadLocalExamHolder{
        private static ThreadLocalExample INSTANCE = new ThreadLocalExample();
    }

    ThreadLocal<SecureRandom> secureRandomThreadLocal = new ThreadLocal<>(){
        @Override
        protected SecureRandom initialValue() {
            SecureRandom secureRandom  = new SecureRandom();
            secureRandom.setSeed(new byte[20]);
            return secureRandom;
        }
    };
    public ThreadLocalExample getInstance(){
        return ThreadLocalExamHolder.INSTANCE;
    }

    public int nextInt(int upperBound){
        return secureRandomThreadLocal.get().nextInt(upperBound);
    }
}
