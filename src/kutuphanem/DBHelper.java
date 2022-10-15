

package kutuphanem;
//kütüphaneler import edilmiştir. import java.sql.*; olarak da kullanılabilirdi.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
//connector jar yüklenmiştir kütüphane klasörüne
    //veritabanı giris bilgileri burada tanımlanmıştır ve hangi veritabanına baglanılacagıda belirtilmiştir.
         static String Db_url="jdbc:sqlserver://MelihYıldız;databaseName=kutuphane";//kutuphane veritabanına baglan.
       static  String kullanıcı="kutuphane";
      static  String sifre="123456";
 
      //mysql workbench baglantısı
    /*static final String Db_url="jdbc:mysql://localhost/kutuphane?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    static final String kullanıcı="root";
    static final String sifre="";*/
    
      //connection baglantısı oluşturuyorum.
    public static  Connection connectionDB(){
           Connection db = null;
    try{
    //connection baglantısı oluşturuldu 
    db = DriverManager.getConnection(Db_url,kullanıcı,sifre);   
            return db;
    }catch(SQLException e){
        System.out.println("Veritabanına bağlanılamamıştır. "+e.getMessage());//hata mesajını ekrana verir.
         System.out.println("Hata kodu "+e.getErrorCode());//hata kodunu verecektir hata durumunda ve bu bizim hataya daha hızlı ulaşmamızı sağlayacaktır.
        return null;
    }
    
    }
    
    
}
