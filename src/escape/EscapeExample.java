package escape;

import java.util.HashMap;
import java.util.Map;

public class EscapeExample {
    private final Map<String,String> map = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        EscapeExample escapeExample = new EscapeExample();
        Thread.sleep(200);
        System.out.println(escapeExample.getValue("key"));
    }
    public EscapeExample(){
        new  Thread(){
            @Override
            public void run() {
                map.put("key","value");
            }
        }.start();
    }

    public String getValue(String key){
        return map.get(key);
    }
}
