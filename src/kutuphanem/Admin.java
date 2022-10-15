
package kutuphanem;

import java.awt.Color;
import java.util.ArrayList;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;//arama importu
import java.sql.ResultSetMetaData;
import net.proteanit.sql.DbUtils;
        


public class Admin extends javax.swing.JFrame {

  DefaultTableModel model;//tablo modeli oluşturuldu.
  DefaultTableModel ktpyz;
  DefaultTableModel models;
  
    public Admin() {
       initComponents();
     uyeTable();
     yazarktpTable();
      duyuruTable();

   
     adminGet.setText(giris.k_ad);//Admin girişine göre sisteme giren kişinin Kullanıcı adı bilgisini getirir.
     }
    
    //uye tablosu
    public void uyeTable(){
           model = (DefaultTableModel)uyebilgi.getModel();//model üyebilgi tablosunun modelidir. 
           model.setRowCount(0);
     try {
         ArrayList<uyeler> uye = getUye();//veri çekilmiştir.
         for(uyeler uyeb : uye ){//her bir uye için bir obje oluşturuldu. ve her bir uye için tek sutun kullanılacak.
             Object[] row = { uyeb.getUye_id(),uyeb.getK_ad(),uyeb.getU_ad(),uyeb.getU_soyad(),uyeb.getYetki()}; 
             model.addRow(row);//her bir üyeyi model yani uyebilgini tablosunun içine eklenmiştir.
         }
     } catch (SQLException e) {}
    }
          
    
    //Duyuru Tablosundaki verileri getirir. 
    public void duyuruTable() {
    models = (DefaultTableModel)duyuruTablo.getModel();
    models.setRowCount(0);
     try {
         ArrayList<duyurular> duyuru = getDuyuru();
          for(duyurular duyur : duyuru ){
             Object[] row = {duyur.getDuyuruNo(),duyur.getDuyuru(),duyur.getBaslık() };     
             models.addRow(row);
         }  
     } catch (SQLException ex) {
         Logger.getLogger(KullanıcıMenu.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
    
    //yazar ve kitap tablosuna veri getirir.
    public void yazarktpTable(){
      ktpyz = (DefaultTableModel)ktpbilgi.getModel();
      ktpyz.setRowCount(0);
      try {
         ArrayList<okuduklarım> okuduk= getOkuduklarım();
         for(okuduklarım oku: okuduk){
             Object[] row = {oku.getKitapNo(),oku.getYazarAd(),oku.getYazarSoyad(),oku.getKitapAd(), oku.getKitapTur(),oku.getYayınEv() };     
             ktpyz.addRow(row);
         }
     } catch (SQLException e) {}
    }
    

    
    //* ÜYE İŞLEMLERİ*/
    public ArrayList<uyeler> getUye() throws SQLException{
    Connection dbcon=null;// dbcon adında connection oluşturdum.
    DBHelper dbHelper= new DBHelper();//dbhelper nesnesini olluşturdum connection oluşturması gerekiyor.
    Statement st=null;//sql server sorgularımı çalıştırabilmek için ise bir statementa ihtiyacım var ve bunu st adında oluşturdum.
    ResultSet sonuc;//veritabanımdan dönen verileri tutar.
    ArrayList<uyeler> uye=null;//uye adında bir liste oluşturuldu.
        try{//hata ayıklama işlemi.
          dbcon = dbHelper.connectionDB();//veritabanı baglantımız sağlandı.
           st = dbcon.createStatement();//sql cümlecigi oluşturdum.
            sonuc =st.executeQuery("select * from uye");// sql sorgusunu result set sonucda tutuldu.
            uye= new ArrayList<uyeler>();//sorgu sonucu arrayliste aktarıldı yukarıda oluşturdugum null degerdeki arraylistin referansıdır.
            while(sonuc.next()){   //while döngüsü ile sonuc degerini yukarıdan aşagıya dolaşmaktadır.
            uye.add(new uyeler(
                    sonuc.getInt("uye_id"),
                    sonuc.getString("u_ad"),
                    sonuc.getString("u_soyad"),
                    sonuc.getString("k_ad") ,
                    sonuc.getString("yetki") ) );
            }
        }catch(SQLException e){//hata olursa çalışacak kod
            System.out.println("hata kodu:"+e.getErrorCode());
        }finally{
        st.close();
       dbcon.close();
        }
        return uye;      
    }
    
    
    //* Kitap tablosu */
       public ArrayList<okuduklarım> getOkuduklarım () throws SQLException{
      Connection dbcon=null;
      DBHelper dbHelper= new DBHelper();
        Statement st=null;
        ResultSet sonuc;
    ArrayList<okuduklarım> okuduk=null;
        try{
          dbcon = dbHelper.connectionDB();
            st = dbcon.createStatement();
            sonuc =st.executeQuery("select  *  from kitap");
            okuduk= new ArrayList<okuduklarım>();
            while(sonuc.next()){
            okuduk.add(new okuduklarım(
                    sonuc.getInt("kitap_id"),
                    sonuc.getString("yazar_ad"),
                    sonuc.getString("yazar_soyad"),
                    sonuc.getString("kitap_ad"),
                    sonuc.getString("kitap_tur"),
                    sonuc.getString("yayin_evi") ) );
            }
             kitapTabloGuncelle();
        }catch(SQLException e){
            System.out.println("hata kodu:"+e.getErrorCode());
        }finally{
        st.close();
       dbcon.close();
        }
        return okuduk;// program okuduk degerini döndürecektir. 
    }
    
    
      //Duyuruları arrayliste ekler 
    public ArrayList<duyurular> getDuyuru () throws SQLException{
      Connection dbcon=null;
      DBHelper dbHelper= new DBHelper();
        Statement st=null;
        ResultSet sonuc;
    ArrayList<duyurular> duyuru=null;  
        try{
          dbcon = dbHelper.connectionDB();
            st = dbcon.createStatement();
            sonuc =st.executeQuery("select * from duyuru");
            duyuru= new ArrayList<duyurular>();
            while(sonuc.next()){
            duyuru.add(new duyurular(
                    sonuc.getInt("duyuru_id"),
                    sonuc.getString("duyuru"),
                    sonuc.getString("baslık") ) );
            }
        }catch(SQLException e){
            System.out.println("hata kodu:"+e.getErrorCode());
        }finally{
        st.close();
       dbcon.close();
        }
        return duyuru;
   
    } 
        
        
        
        
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tab1Admin = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tab2Admin = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tab3Admin = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tab4Admin = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        adminGet = new javax.swing.JLabel();
        tab5Admin = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        TabCıkıs = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jp1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ktpbilgi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        yazArama = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        kitapAd = new javax.swing.JTextField();
        KitapTur = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        yayınEv = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        yazarSoyad = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        yazarAd = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        KitapKayıt = new javax.swing.JButton();
        jp2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jp3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        uyebilgi = new javax.swing.JTable();
        txtAra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        u_ad1 = new javax.swing.JTextField();
        u_soyad1 = new javax.swing.JTextField();
        k_ad1 = new javax.swing.JTextField();
        pass1 = new javax.swing.JPasswordField();
        yetki1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        uyeGuncelle = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jp4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        u_ad = new javax.swing.JTextField();
        u_soyad = new javax.swing.JTextField();
        k_ad = new javax.swing.JTextField();
        yetkiVer = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        yetki = new javax.swing.JComboBox<>();
        pass = new javax.swing.JPasswordField();
        jPanel10 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jp5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        DuyuruEkle = new javax.swing.JButton();
        baslik = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        duyuru = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        duyuruTablo = new javax.swing.JTable();
        DuyuruSil = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("ÜYE İŞLEM");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(5, 135, 165));
        jLabel35.setText("KUTUPHANESİ");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("GELİŞİM ");

        jPanel11.setBackground(new java.awt.Color(74, 34, 61));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(5, 135, 165));
        jLabel46.setText("KUTUPHANESİ");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("GELİŞİM ");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("DUYURU");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel47)
                .addGap(38, 38, 38)
                .addComponent(jLabel46)
                .addGap(50, 50, 50)
                .addComponent(jLabel48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel47)
                    .addComponent(jLabel46))
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(74, 34, 61));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(5, 135, 165));
        jLabel55.setText("KUTUPHANESİ");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("GELİŞİM ");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("YETKİLENDİRME");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel56)
                .addGap(46, 46, 46)
                .addComponent(jLabel55)
                .addGap(31, 31, 31)
                .addComponent(jLabel75)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel56)
                    .addComponent(jLabel55))
                .addContainerGap())
        );

        jPanel22.setBackground(new java.awt.Color(74, 34, 61));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(5, 135, 165));
        jLabel79.setText("KUTUPHANESİ");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("GELİŞİM ");

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("DUYURU");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel80)
                .addGap(39, 39, 39)
                .addComponent(jLabel79)
                .addGap(34, 34, 34)
                .addComponent(jLabel81)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(jLabel80)
                    .addComponent(jLabel79))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(74, 34, 61));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/person-icon.png"))); // NOI18N

        tab1Admin.setBackground(new java.awt.Color(255, 204, 51));
        tab1Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1AdminMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Kitap Ekle");

        javax.swing.GroupLayout tab1AdminLayout = new javax.swing.GroupLayout(tab1Admin);
        tab1Admin.setLayout(tab1AdminLayout);
        tab1AdminLayout.setHorizontalGroup(
            tab1AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1AdminLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(106, 106, 106))
        );
        tab1AdminLayout.setVerticalGroup(
            tab1AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tab2Admin.setBackground(new java.awt.Color(255, 204, 51));
        tab2Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2AdminMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Kitap Onayla");

        javax.swing.GroupLayout tab2AdminLayout = new javax.swing.GroupLayout(tab2Admin);
        tab2Admin.setLayout(tab2AdminLayout);
        tab2AdminLayout.setHorizontalGroup(
            tab2AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2AdminLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(106, 106, 106))
        );
        tab2AdminLayout.setVerticalGroup(
            tab2AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        tab3Admin.setBackground(new java.awt.Color(255, 204, 51));
        tab3Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3AdminMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Üye İşlem");

        javax.swing.GroupLayout tab3AdminLayout = new javax.swing.GroupLayout(tab3Admin);
        tab3Admin.setLayout(tab3AdminLayout);
        tab3AdminLayout.setHorizontalGroup(
            tab3AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3AdminLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(105, 105, 105))
        );
        tab3AdminLayout.setVerticalGroup(
            tab3AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        tab4Admin.setBackground(new java.awt.Color(255, 204, 51));
        tab4Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4AdminMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Yetkilendir");

        javax.swing.GroupLayout tab4AdminLayout = new javax.swing.GroupLayout(tab4Admin);
        tab4Admin.setLayout(tab4AdminLayout);
        tab4AdminLayout.setHorizontalGroup(
            tab4AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4AdminLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(105, 105, 105))
        );
        tab4AdminLayout.setVerticalGroup(
            tab4AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        adminGet.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        adminGet.setForeground(new java.awt.Color(255, 255, 255));
        adminGet.setText("Admin");

        tab5Admin.setBackground(new java.awt.Color(255, 204, 51));
        tab5Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab5AdminMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Duyuru Ekle");

        javax.swing.GroupLayout tab5AdminLayout = new javax.swing.GroupLayout(tab5Admin);
        tab5Admin.setLayout(tab5AdminLayout);
        tab5AdminLayout.setHorizontalGroup(
            tab5AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab5AdminLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(105, 105, 105))
        );
        tab5AdminLayout.setVerticalGroup(
            tab5AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        TabCıkıs.setBackground(new java.awt.Color(255, 204, 51));
        TabCıkıs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabCıkısMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Çıkış");

        javax.swing.GroupLayout TabCıkısLayout = new javax.swing.GroupLayout(TabCıkıs);
        TabCıkıs.setLayout(TabCıkısLayout);
        TabCıkısLayout.setHorizontalGroup(
            TabCıkısLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabCıkısLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        TabCıkısLayout.setVerticalGroup(
            TabCıkısLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabCıkıs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tab2Admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tab3Admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tab4Admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tab5Admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tab1Admin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(adminGet, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(adminGet)
                .addGap(40, 40, 40)
                .addComponent(tab1Admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab2Admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab3Admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab4Admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab5Admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addComponent(TabCıkıs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jPanel2.setBackground(new java.awt.Color(186, 79, 84));

        jp1.setBackground(new java.awt.Color(186, 79, 84));

        ktpbilgi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "kitap_id", "yazar_ad", "yazar_soyad", "kitap_ad", "kitap_tur", "yayın evi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ktpbilgi.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ktpbilgi.setRowHeight(25);
        ktpbilgi.setSelectionBackground(new java.awt.Color(232, 57, 95));
        ktpbilgi.setShowVerticalLines(false);
        ktpbilgi.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(ktpbilgi);
        if (ktpbilgi.getColumnModel().getColumnCount() > 0) {
            ktpbilgi.getColumnModel().getColumn(0).setResizable(false);
            ktpbilgi.getColumnModel().getColumn(1).setResizable(false);
            ktpbilgi.getColumnModel().getColumn(2).setResizable(false);
            ktpbilgi.getColumnModel().getColumn(3).setResizable(false);
            ktpbilgi.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel4.setBackground(new java.awt.Color(186, 79, 84));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Yazar Adı:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Yazar Soyad:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Yazar Doğum.T:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4)
                    .addComponent(jTextField3)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBackground(new java.awt.Color(186, 79, 84));

        yazArama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                yazAramaKeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(245, 246, 250));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Ara");
        jButton1.setBorderPainted(false);

        jButton3.setBackground(new java.awt.Color(245, 246, 250));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Sil");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(245, 246, 250));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("Guncelle");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yazArama, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yazArama, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(186, 79, 84));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        jPanel16.setBackground(new java.awt.Color(74, 34, 61));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(5, 135, 165));
        jLabel60.setText("KUTUPHANESİ");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("GELİŞİM ");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("KİTAP İŞLEM");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel61)
                .addGap(34, 34, 34)
                .addComponent(jLabel60)
                .addGap(32, 32, 32)
                .addComponent(jLabel62)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jLabel60)
                    .addComponent(jLabel62))
                .addContainerGap())
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Kitap Adı");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Kitap Tur");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Yayın evi");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Yazar Soyad:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Yazar Ad:");

        KitapKayıt.setBackground(new java.awt.Color(245, 246, 250));
        KitapKayıt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        KitapKayıt.setText("Kaydet");
        KitapKayıt.setBorderPainted(false);
        KitapKayıt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KitapKayıtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp1Layout = new javax.swing.GroupLayout(jp1);
        jp1.setLayout(jp1Layout);
        jp1Layout.setHorizontalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp1Layout.createSequentialGroup()
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(35, 35, 35)
                                .addComponent(yazarAd, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(yazarSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(KitapTur, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp1Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kitapAd, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(KitapKayıt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(yayınEv, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jp1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jp1Layout.setVerticalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp1Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(kitapAd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(yazarAd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(KitapTur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yazarSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yayınEv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(KitapKayıt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jp2.setBackground(new java.awt.Color(186, 79, 84));

        jPanel15.setBackground(new java.awt.Color(74, 34, 61));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(5, 135, 165));
        jLabel58.setText("KUTUPHANESİ");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("GELİŞİM ");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("KİTAP ONAY");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel59)
                .addGap(34, 34, 34)
                .addComponent(jLabel58)
                .addGap(32, 32, 32)
                .addComponent(jLabel57)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jLabel58)
                    .addComponent(jLabel57))
                .addContainerGap())
        );

        javax.swing.GroupLayout jp2Layout = new javax.swing.GroupLayout(jp2);
        jp2.setLayout(jp2Layout);
        jp2Layout.setHorizontalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp2Layout.setVerticalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp2Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 652, Short.MAX_VALUE))
        );

        jp3.setBackground(new java.awt.Color(186, 79, 84));

        uyebilgi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Uye_id", "Kullanıcı_Ad", "Uye_Ad", "Uye_Soyad", "Yetki"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        uyebilgi.setFocusable(false);
        uyebilgi.setGridColor(new java.awt.Color(0, 102, 204));
        uyebilgi.setIntercellSpacing(new java.awt.Dimension(0, 0));
        uyebilgi.setRowHeight(25);
        uyebilgi.setSelectionBackground(new java.awt.Color(232, 57, 95));
        uyebilgi.setShowHorizontalLines(false);
        uyebilgi.setShowVerticalLines(false);
        uyebilgi.getTableHeader().setReorderingAllowed(false);
        uyebilgi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uyebilgiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(uyebilgi);
        if (uyebilgi.getColumnModel().getColumnCount() > 0) {
            uyebilgi.getColumnModel().getColumn(0).setResizable(false);
            uyebilgi.getColumnModel().getColumn(1).setResizable(false);
            uyebilgi.getColumnModel().getColumn(2).setResizable(false);
            uyebilgi.getColumnModel().getColumn(3).setResizable(false);
            uyebilgi.getColumnModel().getColumn(4).setResizable(false);
        }

        txtAra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAraKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Üye Ara");

        jButton2.setBackground(new java.awt.Color(245, 246, 250));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Üye Sil");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(186, 79, 84));

        u_ad1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                u_ad1FocusGained(evt);
            }
        });

        u_soyad1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                u_soyad1FocusGained(evt);
            }
        });

        k_ad1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                k_ad1FocusGained(evt);
            }
        });

        pass1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pass1FocusGained(evt);
            }
        });

        yetki1.setEditable(true);
        yetki1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yönetici", "kullanıcı" }));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("AD");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Soyad");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Kullanıcı Adı");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Sifre");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Yetki");

        uyeGuncelle.setBackground(new java.awt.Color(245, 246, 250));
        uyeGuncelle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        uyeGuncelle.setText("Üye Güncelle");
        uyeGuncelle.setBorderPainted(false);
        uyeGuncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uyeGuncelleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(u_ad1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(k_ad1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(yetki1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(u_soyad1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uyeGuncelle, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pass1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(k_ad1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(yetki1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uyeGuncelle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(u_soyad1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(u_ad1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        jPanel12.setBackground(new java.awt.Color(74, 34, 61));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(5, 135, 165));
        jLabel49.setText("KUTUPHANESİ");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("GELİŞİM ");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("ÜYE İŞLEM");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel50)
                .addGap(34, 34, 34)
                .addComponent(jLabel49)
                .addGap(36, 36, 36)
                .addComponent(jLabel51)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel50)
                    .addComponent(jLabel49))
                .addContainerGap())
        );

        javax.swing.GroupLayout jp3Layout = new javax.swing.GroupLayout(jp3);
        jp3.setLayout(jp3Layout);
        jp3Layout.setHorizontalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jp3Layout.createSequentialGroup()
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp3Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jp3Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAra, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jp3Layout.setVerticalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp3Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAra, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jp4.setBackground(new java.awt.Color(186, 79, 84));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Kullanıcı Adı");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("AD");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Soyad");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Sifre");

        u_ad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                u_adFocusGained(evt);
            }
        });

        u_soyad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                u_soyadFocusGained(evt);
            }
        });

        k_ad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                k_adFocusGained(evt);
            }
        });

        yetkiVer.setBackground(new java.awt.Color(245, 246, 250));
        yetkiVer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        yetkiVer.setText("Yetki Ver");
        yetkiVer.setBorderPainted(false);
        yetkiVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yetkiVerActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Yetki");

        yetki.setEditable(true);
        yetki.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yönetici", "kullanıcı" }));

        pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passFocusGained(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(74, 34, 61));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(5, 135, 165));
        jLabel43.setText("KUTUPHANESİ");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("GELİŞİM ");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("YETKİLENDİRME");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel44)
                .addGap(46, 46, 46)
                .addComponent(jLabel43)
                .addGap(31, 31, 31)
                .addComponent(jLabel45)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43))
                .addContainerGap())
        );

        javax.swing.GroupLayout jp4Layout = new javax.swing.GroupLayout(jp4);
        jp4.setLayout(jp4Layout);
        jp4Layout.setHorizontalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp4Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(yetkiVer, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp4Layout.createSequentialGroup()
                        .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(u_ad)
                            .addComponent(u_soyad)
                            .addComponent(k_ad)
                            .addComponent(yetki, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp4Layout.setVerticalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp4Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(u_ad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(u_soyad, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k_ad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yetki, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(yetkiVer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
        );

        jp5.setBackground(new java.awt.Color(186, 79, 84));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Başlık");

        DuyuruEkle.setBackground(new java.awt.Color(245, 246, 250));
        DuyuruEkle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DuyuruEkle.setText("Duyuru Ekle");
        DuyuruEkle.setBorderPainted(false);
        DuyuruEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DuyuruEkleActionPerformed(evt);
            }
        });

        baslik.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                baslikFocusGained(evt);
            }
        });

        duyuru.setColumns(20);
        duyuru.setRows(5);
        duyuru.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                duyuruFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(duyuru);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Duyuru");

        duyuruTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Duyuru id", "Baslık", "Duyuru"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        duyuruTablo.setFocusable(false);
        duyuruTablo.setGridColor(new java.awt.Color(204, 204, 204));
        duyuruTablo.setIntercellSpacing(new java.awt.Dimension(0, 0));
        duyuruTablo.setName(""); // NOI18N
        duyuruTablo.setRowHeight(25);
        duyuruTablo.setSelectionBackground(new java.awt.Color(232, 57, 95));
        duyuruTablo.setShowGrid(true);
        duyuruTablo.setShowVerticalLines(false);
        duyuruTablo.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(duyuruTablo);
        if (duyuruTablo.getColumnModel().getColumnCount() > 0) {
            duyuruTablo.getColumnModel().getColumn(0).setResizable(false);
            duyuruTablo.getColumnModel().getColumn(1).setResizable(false);
            duyuruTablo.getColumnModel().getColumn(2).setResizable(false);
        }

        DuyuruSil.setBackground(new java.awt.Color(245, 246, 250));
        DuyuruSil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DuyuruSil.setText("Duyuru Sil");
        DuyuruSil.setBorderPainted(false);
        DuyuruSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DuyuruSilActionPerformed(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(74, 34, 61));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(5, 135, 165));
        jLabel76.setText("KUTUPHANESİ");

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("GELİŞİM ");

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("DUYURU");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel77)
                .addGap(39, 39, 39)
                .addComponent(jLabel76)
                .addGap(34, 34, 34)
                .addComponent(jLabel78)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jLabel77)
                    .addComponent(jLabel76))
                .addContainerGap())
        );

        javax.swing.GroupLayout jp5Layout = new javax.swing.GroupLayout(jp5);
        jp5.setLayout(jp5Layout);
        jp5Layout.setHorizontalGroup(
            jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jp5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jp5Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(DuyuruEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(DuyuruSil, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(baslik, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jp5Layout.setVerticalGroup(
            jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp5Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(baslik, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DuyuruEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DuyuruSil, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jp5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tab1AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1AdminMouseClicked
        jp1.setVisible(true);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
         jp5.setVisible(false);
        tab1Admin.setBackground(new Color(220, 221, 225));
        tab2Admin.setBackground(new Color(255,204,51));
        tab3Admin.setBackground(new Color(255,204,51));
        tab4Admin.setBackground(new Color(255,204,51));
          tab5Admin.setBackground(new Color(255,204,51));
    }//GEN-LAST:event_tab1AdminMouseClicked

    private void tab2AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2AdminMouseClicked
        jp1.setVisible(false);
        jp2.setVisible(true);
        jp3.setVisible(false);
        jp4.setVisible(false);
         jp5.setVisible(false);
       tab2Admin.setBackground(new Color(220, 221, 225));
        tab1Admin.setBackground(new Color(255,204,51));
        tab3Admin.setBackground(new Color(255,204,51));
        tab4Admin.setBackground(new Color(255,204,51));
          tab5Admin.setBackground(new Color(255,204,51));
    }//GEN-LAST:event_tab2AdminMouseClicked

    private void tab3AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3AdminMouseClicked
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(true);
        jp4.setVisible(false);
         jp5.setVisible(false);
        tab3Admin.setBackground(new Color(220, 221, 225));
        tab1Admin.setBackground(new Color(255,204,51));
        tab2Admin.setBackground(new Color(255,204,51));
        tab4Admin.setBackground(new Color(255,204,51));
          tab5Admin.setBackground(new Color(255,204,51));
    }//GEN-LAST:event_tab3AdminMouseClicked

    private void tab4AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4AdminMouseClicked
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(true);
        jp5.setVisible(false);
       
        tab4Admin.setBackground(new Color(220, 221, 225));
        tab1Admin.setBackground(new Color(255,204,51));
       tab2Admin.setBackground(new Color(255,204,51));
        tab3Admin.setBackground(new Color(255,204,51));
         tab5Admin.setBackground(new Color(255,204,51));
    }//GEN-LAST:event_tab4AdminMouseClicked

    private void tab5AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5AdminMouseClicked
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);
        jp5.setVisible(true);
        tab5Admin.setBackground(new Color(220, 221, 225));
        tab1Admin.setBackground(new Color(255,204,51));
       tab2Admin.setBackground(new Color(255,204,51));
        tab3Admin.setBackground(new Color(255,204,51));
         tab4Admin.setBackground(new Color(255,204,51));  
    }//GEN-LAST:event_tab5AdminMouseClicked

    private void TabCıkısMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabCıkısMouseClicked
          try{
              int onay = JOptionPane.showConfirmDialog(null, "Çıkış yapmak istediginize Emin misiniz?", "Çıkış", JOptionPane.YES_NO_OPTION);        
              if (onay == 0) {    //yes'e basıldı ise sil
                  dispose();
        giris g = new giris();
        g.setTitle("Uye Giris");
        g.setVisible(true);
            } else {            //yes'e basılmadı ise hiç bir şey yapma
} }catch(Exception e){ }
       
        TabCıkıs.setBackground(new Color(220, 221, 225));
       tab1Admin.setBackground(new Color(255,204,51));
       tab2Admin.setBackground(new Color(255,204,51));
        tab3Admin.setBackground(new Color(255,204,51));
         tab4Admin.setBackground(new Color(255,204,51));
          tab5Admin.setBackground(new Color(255,204,51));
    }//GEN-LAST:event_TabCıkısMouseClicked

    
 
 
   //KİTAP TABLO GÜNCELLER
    public void kitapTabloGuncelle() throws SQLException{
    DBHelper dbHelper= new DBHelper();//dbhelper nesnesini olluşturdum connection oluşturması gerekiyor.
    Connection dbcon =null;
    dbcon = dbHelper.connectionDB();
    PreparedStatement st =null; //sql server sorgularımı çalıştırabilmek için ise bir statementa ihtiyacım var ve bunu st adında oluşturdum.
    ResultSet sonuc = null;//veritabanımdan dönen verileri tutar.
     String sql="select  *  from kitap";
    try{
       st = dbcon.prepareStatement(sql);
       sonuc = st.executeQuery();
       ktpbilgi.setModel(DbUtils.resultSetToTableModel(sonuc));
      //uyebilgi.setModel();
    }catch(Exception e){
   JOptionPane.showMessageDialog(null, e);
    }finally{
    st.close();
    sonuc.close();
    }
    }
    
    

    // ÜYE BİLGİSİ ARAMA 
    private void txtAraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAraKeyReleased
        //keyReleased oluşturuldu olaylardan yani bir events oluşturulmuş oldu.
        String AramaAnahtarı = txtAra.getText();//arama anahtarı textbox verisinden alınıp tutulmuştur.
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(model);//nesne oluşturuldu.
              uyebilgi.setRowSorter(tableRowSorter);
              tableRowSorter.setRowFilter(RowFilter.regexFilter(AramaAnahtarı) );//arama anahtarını filtreleyerek arama yapılmıştır.
    }//GEN-LAST:event_txtAraKeyReleased

    
//YETKİLENDİRME
    private void yetkiVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yetkiVerActionPerformed
        String ad= u_ad.getText();
        String soyad=   u_soyad.getText();
        String kullanıcıAd= k_ad.getText();
        String sifre=  pass.getText();
        String yetkiv =(String) yetki.getSelectedItem();   
      Connection dbcon = DBHelper.connectionDB(); 
       if(dbcon !=null){
        try {
            PreparedStatement st = (PreparedStatement)  dbcon.prepareStatement("INSERT  INTO   uye(u_ad,u_soyad,k_ad,sifre,yetki)  VALUES(?,?,?,?,?)");
           st.setString(1, ad);
          st.setString(2, soyad);
          st.setString(3, kullanıcıAd);
          st.setString(4, sifre);
           st.setString(5, yetkiv);     
          int res = st.executeUpdate();  
               JOptionPane.showMessageDialog(this, "Kullanıcıya Yetki Verilmiştir.", "Başarılı ",JOptionPane.INFORMATION_MESSAGE);
               uyeTable();//üye işlemlerindeki tabloyu günceller.
        } catch (SQLException e) {
            Logger.getLogger(giris.class.getName()).log(Level.SEVERE, null, e);
        }
    }else{ System.out.println(" baglantı hatası");}     
    }//GEN-LAST:event_yetkiVerActionPerformed

    
    
//*Duyuru
    private void DuyuruEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DuyuruEkleActionPerformed
        String baslikk = baslik.getText();        
        String duyurular = duyuru.getText();
          Connection dbcon = DBHelper.connectionDB();
            if(dbcon !=null){
         try {
               PreparedStatement st = (PreparedStatement)  dbcon.prepareStatement("INSERT  INTO   duyuru(duyuru,baslık)  VALUES(?,?)");
             st.setString(1, baslikk);
             st.setString(2, duyurular);
                int res = st.executeUpdate();  
                 JOptionPane.showMessageDialog(this, "Duyuru Yayınlanmıştır.", "Duyuru İşlemi ",JOptionPane.INFORMATION_MESSAGE);
                 duyuruTable(); 
         } catch (Exception e) {
               JOptionPane.showMessageDialog(this, "Lütfen Tekrar Deneyiniz", "Duyuru || Hata",JOptionPane.ERROR_MESSAGE);
        }      
      }  else{ System.out.println(" baglantı hatası");}  
             
    }//GEN-LAST:event_DuyuruEkleActionPerformed

 
//kitap bilgi Bilgisi Arama
    private void yazAramaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yazAramaKeyReleased
            String AramaAnahtarı = yazArama.getText();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(ktpyz);
              ktpbilgi.setRowSorter(tableRowSorter);
              tableRowSorter.setRowFilter(RowFilter.regexFilter(AramaAnahtarı) );        
    }//GEN-LAST:event_yazAramaKeyReleased

//yazar ve kitap bilgisi silme
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
          Connection dbcon =null;
    DBHelper dbHelper= new DBHelper();
            dbcon = dbHelper.connectionDB();
    PreparedStatement st =null; 
        int row=   ktpbilgi.getSelectedRow();
        String silme =   ktpbilgi.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE FROM kitap  WHERE kitap_id="+silme; 
    try{
    st = dbcon.prepareStatement(sql);
   st.execute();
JOptionPane.showMessageDialog(null,"İşlem Başarılı bir şekilde gerçekleşmiştir.");
kitapTabloGuncelle();
    }catch(Exception e){
    JOptionPane.showMessageDialog(null, e);
    }      
    }//GEN-LAST:event_jButton3ActionPerformed

  
    
//  kitap güncelle
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      try {
          kitapTabloGuncelle();
      } catch (SQLException ex) {
          Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_jButton4ActionPerformed

    




// DUYURU SİLME İŞLEMİ
    private void DuyuruSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DuyuruSilActionPerformed
    Connection dbcon =null;
    DBHelper dbHelper= new DBHelper();
    dbcon = dbHelper.connectionDB();
    PreparedStatement st =null; 
        int row=   duyuruTablo.getSelectedRow();
        String silme =   duyuruTablo.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE  FROM duyuru where duyuru_id="+silme; 
    try{
    st = dbcon.prepareStatement(sql);
   st.execute();
JOptionPane.showMessageDialog(null,"İşlem Başarılı bir şekilde gerçekleşmiştir.");
  duyuruTable();//tabloyu günceller
    }catch(Exception e){
    JOptionPane.showMessageDialog(null, e);
    } 
        
    }//GEN-LAST:event_DuyuruSilActionPerformed

//üye silme
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Connection dbcon =null;
        DBHelper dbHelper= new DBHelper();
        dbcon = dbHelper.connectionDB();
        PreparedStatement st =null;
        int row= uyebilgi.getSelectedRow();
        String silme = uyebilgi.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE FROM uye where uye_id="+silme;
        try{
            st = dbcon.prepareStatement(sql);
            st.execute();
            JOptionPane.showMessageDialog(null,"Uye Silinmiştir.");
            uyeTable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

//Üye Güncelleme
    private void uyeGuncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uyeGuncelleActionPerformed
      String ad= u_ad1.getText();
        String soyad=   u_soyad1.getText();
        String kullanıcıAd= k_ad1.getText();
        String sifre=  pass1.getText();
        String yetkiv =(String) yetki1.getSelectedItem();    
      Connection dbcon = DBHelper.connectionDB(); 
        int row= uyebilgi.getSelectedRow();
        String guncel = uyebilgi.getModel().getValueAt(row, 0).toString();
         String sql = "UPDATE  uye set u_ad=?,u_soyad=?,k_ad=?,sifre=?,yetki=? where uye_id="+guncel;
       if(dbcon !=null){
        try {
            PreparedStatement st = (PreparedStatement)  dbcon.prepareStatement(sql);
           st.setString(1, ad);
          st.setString(2, soyad);
          st.setString(3, kullanıcıAd);
          st.setString(4, sifre);
           st.setString(5, yetkiv);     
          int res = st.executeUpdate();  
               JOptionPane.showMessageDialog(this, "Kullanıcı başarılı bir şekilde güncellenmiştir.", " İşlem Başarılı ",JOptionPane.INFORMATION_MESSAGE);
                  uyeTable();
        } catch (SQLException e) {
            Logger.getLogger(giris.class.getName()).log(Level.SEVERE, null, e);
        }
    }else{ System.out.println("Bağlantı Hatası");}    
    }//GEN-LAST:event_uyeGuncelleActionPerformed


    /// üye işlem kısmına textboxlara tablodan id ye göre veri çekiyor.
    private void uyebilgiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uyebilgiMouseClicked
   int selectedRow = uyebilgi.getSelectedRow();
        model = (DefaultTableModel)uyebilgi.getModel();
        k_ad1.setText(model.getValueAt(selectedRow, 1).toString());
        u_ad1.setText(model.getValueAt(selectedRow, 2).toString());
         u_soyad1.setText(model.getValueAt(selectedRow, 3).toString());
          pass1.setText(model.getValueAt(selectedRow, 4).toString());    

    }//GEN-LAST:event_uyebilgiMouseClicked

    // kitap kayıt işlemi 
    private void KitapKayıtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KitapKayıtActionPerformed
        String yad= yazarAd.getText();
        String soyad=   yazarSoyad.getText();
        String kad=kitapAd.getText();
        String tur= KitapTur.getText();
        String yayın =yayınEv.getText();
      Connection dbcon = DBHelper.connectionDB(); 
       if(dbcon !=null){
        try {
            PreparedStatement st = (PreparedStatement)  dbcon.prepareStatement("INSERT  INTO   kitap(yazar_ad,yazar_soyad,kitap_ad,kitap_tur,yayin_evi)  VALUES(?,?,?,?,?)");
           st.setString(1, yad);
          st.setString(2, soyad);
          st.setString(3, kad);
          st.setString(4, tur);
           st.setString(5, yayın);     
          int res = st.executeUpdate();  
               JOptionPane.showMessageDialog(this, "Kitap Başarılı bir şekilde eklenmiştir..", "Başarılı ",JOptionPane.INFORMATION_MESSAGE);
             yazarktpTable();//üye işlemlerindeki tabloyu günceller.
        } catch (SQLException e) {
            Logger.getLogger(giris.class.getName()).log(Level.SEVERE, null, e);
        }
    }else{ System.out.println(" baglantı hatası");}     
        
        
    }//GEN-LAST:event_KitapKayıtActionPerformed

    
    
 // ÜYE İŞLEM TABLOSU FOCUSGAİNED   
    private void u_adFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_u_adFocusGained
        u_ad.setText("");
    }//GEN-LAST:event_u_adFocusGained

    private void u_soyadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_u_soyadFocusGained
       u_soyad.setText("");
    }//GEN-LAST:event_u_soyadFocusGained

    private void k_adFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_k_adFocusGained
        k_ad.setText("");
    }//GEN-LAST:event_k_adFocusGained

    private void passFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passFocusGained
        pass.setText("");
    }//GEN-LAST:event_passFocusGained

    
    // DUYURU İŞLEM TABLOSU focusGained
    private void baslikFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_baslikFocusGained
      baslik.setText("");
    }//GEN-LAST:event_baslikFocusGained

    private void duyuruFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_duyuruFocusGained
       duyuru.setText("");
    }//GEN-LAST:event_duyuruFocusGained

    //üye güncelle işlem tablosu focusGained
    private void u_ad1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_u_ad1FocusGained
  u_ad1.setText("");
    }//GEN-LAST:event_u_ad1FocusGained

    private void k_ad1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_k_ad1FocusGained
        k_ad1.setText("");
    }//GEN-LAST:event_k_ad1FocusGained

    private void u_soyad1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_u_soyad1FocusGained
      u_soyad1.setText("");
    }//GEN-LAST:event_u_soyad1FocusGained

    private void pass1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pass1FocusGained
       pass1.setText("");
    }//GEN-LAST:event_pass1FocusGained


    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DuyuruEkle;
    private javax.swing.JButton DuyuruSil;
    private javax.swing.JButton KitapKayıt;
    private javax.swing.JTextField KitapTur;
    private javax.swing.JPanel TabCıkıs;
    private javax.swing.JLabel adminGet;
    private javax.swing.JTextField baslik;
    private javax.swing.JTextArea duyuru;
    private javax.swing.JTable duyuruTablo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel jp1;
    private javax.swing.JPanel jp2;
    private javax.swing.JPanel jp3;
    private javax.swing.JPanel jp4;
    private javax.swing.JPanel jp5;
    private javax.swing.JTextField k_ad;
    private javax.swing.JTextField k_ad1;
    private javax.swing.JTextField kitapAd;
    private javax.swing.JTable ktpbilgi;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField pass1;
    private javax.swing.JPanel tab1Admin;
    private javax.swing.JPanel tab2Admin;
    private javax.swing.JPanel tab3Admin;
    private javax.swing.JPanel tab4Admin;
    private javax.swing.JPanel tab5Admin;
    private javax.swing.JTextField txtAra;
    private javax.swing.JTextField u_ad;
    private javax.swing.JTextField u_ad1;
    private javax.swing.JTextField u_soyad;
    private javax.swing.JTextField u_soyad1;
    private javax.swing.JButton uyeGuncelle;
    private javax.swing.JTable uyebilgi;
    private javax.swing.JTextField yayınEv;
    private javax.swing.JTextField yazArama;
    private javax.swing.JTextField yazarAd;
    private javax.swing.JTextField yazarSoyad;
    private javax.swing.JComboBox<String> yetki;
    private javax.swing.JComboBox<String> yetki1;
    private javax.swing.JButton yetkiVer;
    // End of variables declaration//GEN-END:variables
}
