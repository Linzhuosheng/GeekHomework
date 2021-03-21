package homework;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

/**
 * 自定义ClassLoader
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/19 10:11
 */
public class MyClassLoader extends ClassLoader{

    private static final String FILEPATH = "src/homework/Hello.xlass";

    public static void main(String[] args) throws Exception {
        Class<?> classObject = new MyClassLoader().findClass("Hello");
        Object helloObject = classObject.newInstance();
        Method method = classObject.getMethod("hello");
        method.invoke(helloObject);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadFileToByteArray(FILEPATH);
        return defineClass(name,bytes,0,bytes.length);
    }

    /**
     * 处理 Hello.xlass 文件
     * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
     * @param  filePath 文件路径
     * @return byte[] 转换后新的字节数组
     */
    private byte[] loadFileToByteArray(String filePath){
        File file = new File(filePath);
        try {
            byte[] bytesArray = Files.readAllBytes(file.toPath());
            byte[] classBytesArray = new byte[bytesArray.length];
            for(int i=0;i<bytesArray.length;i++){
                classBytesArray[i] = (byte) (255 - bytesArray[i]);
            }
            return classBytesArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
