package cn.core.framework.utils.rediscache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Create on : 2017年2月4日 下午12:28:24  <br>
 * Description :  序列化工具类<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class SerializeUtil {
    public static byte[] serialize(Object object) {
         ObjectOutputStream oos = null;
          ByteArrayOutputStream baos = null;
          try {
               // 序列化
              baos = new ByteArrayOutputStream();
              oos = new ObjectOutputStream(baos);
              oos.writeObject(object);
               byte[] bytes = baos.toByteArray();
               return bytes;
         } catch (Exception e) {

         }
          return null;
   }
    public static Object unserialize( byte[] bytes) {
         ByteArrayInputStream bais = null;
          try {
               // 反序列化
              bais = new ByteArrayInputStream(bytes);
              ObjectInputStream ois = new ObjectInputStream(bais);
               return ois.readObject();
         } catch (Exception e) {

         }
          return null;
   }

}
