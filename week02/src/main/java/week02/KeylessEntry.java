package week02;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存泄露导致OOM的demo
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/22 16:28
 */
public class KeylessEntry {

    static class Key {
        Integer id ;

        Key(Integer i){
            id = i ;
        }

        @Override
        public int hashCode() {
            //return super.hashCode();
            return id.hashCode();
        }

        /*@Override
        public boolean equals(Object obj) {
            //return super.equals(obj);
            boolean result = false;
            if(obj instanceof Key){
                result = ((Key) obj).id.equals(this.id);
            }
            return result;
        }*/
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        while (true) {
            for (int i = 0; i < 1000; i++) {
                if(! map.containsKey(new Key(i))){
                    map.put(new Key(i),"Number: "+i);
                }
            }
            System.out.println("map size = "+ map.size());
        }

    }
}
