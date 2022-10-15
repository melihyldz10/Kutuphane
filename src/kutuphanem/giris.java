
package kutuphanem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public class giris extends javax.swing.JFrame {
 static String k_ad;
  static String sifre;
   
    public giris() {
        initComponents();     
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        kullanıcıAd = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        giris = new javax.swing.JButton();
        kayıtol = new javax.swing.JButton();
        usericon = new javax.swing.JLabel();
        lock = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sifreGoster = new javax.swing.JCheckBox();

        jPasswordField1.setText("jPasswordField1");

        jPasswordField2.setText("jPasswordField2");

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 102, 0));
        jLabel3.setText("Kullanıcı Adı");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(74, 34, 61));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(186, 79, 84));

        kullanıcıAd.setBackground(new java.awt.Color(186, 79, 84));
        kullanıcıAd.setForeground(new java.awt.Color(255, 255, 255));
        kullanıcıAd.setText("Kullanıcı Ad");
        kullanıcıAd.setBorder(null);
        kullanıcıAd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kullanıcıAdFocusGained(evt);
            }
        });

        pass.setBackground(new java.awt.Color(186, 79, 84));
        pass.setForeground(new java.awt.Color(255, 255, 255));
        pass.setText("sifre");
        pass.setBorder(null);
        pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passFocusGained(evt);
            }
        });

        giris.setBackground(new java.awt.Color(247, 241, 227));
        giris.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giris.setText("Giris");
        giris.setBorder(null);
        giris.setBorderPainted(false);
        giris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                girisActionPerformed(evt);
            }
        });

        kayıtol.setBackground(new java.awt.Color(0, 102, 102));
        kayıtol.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kayıtol.setForeground(new java.awt.Color(255, 255, 255));
        kayıtol.setText("Kayıt Ol");
        kayıtol.setBorderPainted(false);
        kayıtol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kayıtolActionPerformed(evt);
            }
        });

        usericon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        lock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lock-24.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Şifrenizi mi unuttunuz ?");

        sifreGoster.setBackground(new java.awt.Color(186, 79, 84));
        sifreGoster.setForeground(new java.awt.Color(255, 255, 255));
        sifreGoster.setText("Şifre Göster");
        sifreGoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sifreGosterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(81, 81, 81))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sifreGoster))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(usericon)
                            .addComponent(lock))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass)
                            .addComponent(kullanıcıAd)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(kayıtol, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(giris, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))))
                .addGap(43, 43, 43))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(143, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(kullanıcıAd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usericon, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lock, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(sifreGoster)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giris, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kayıtol, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void kullanıcıAdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kullanıcıAdFocusGained
        kullanıcıAd.setText("");
    }//GEN-LAST:event_kullanıcıAdFocusGained

    private void passFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passFocusGained
     pass.setText("");
    }//GEN-LAST:event_passFocusGained


    
    //button giris
    private void girisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_girisActionPerformed
      
         k_ad = kullanıcıAd.getText();
        sifre = String.valueOf(pass.getPassword());
       //eğer kullanıcı adı ve şifre girilmemiş  ise hata verir.
        if(k_ad.isEmpty() || sifre.isEmpty()){
        JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre Giriniz ", "Hata!!",JOptionPane.ERROR_MESSAGE);
        }else{
                KullanıcıGiris(k_ad , sifre);
        }
       
        
    }//GEN-LAST:event_girisActionPerformed

    //kullanıcıkayıt
    private void kayıtolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kayıtolActionPerformed
        dispose();
        KullanıcıKayıt k = new KullanıcıKayıt();
        k.setTitle("KullanıcıKayıt");
        k.setVisible(true);
    }//GEN-LAST:event_kayıtolActionPerformed

    private void sifreGosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sifreGosterActionPerformed
           // seçili oldugu zaman şifreyi ekranda gösterir
        if(sifreGoster.isSelected()){  
        pass.setEchoChar((char)0);
        }
        else{
        pass.setEchoChar(('*'));
        }
    }//GEN-LAST:event_sifreGosterActionPerformed

 
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
            java.util.logging.Logger.getLogger(giris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(giris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(giris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(giris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new giris().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton giris;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton kayıtol;
    private javax.swing.JTextField kullanıcıAd;
    private javax.swing.JLabel lock;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField pass;
    private javax.swing.JCheckBox sifreGoster;
    private javax.swing.JLabel usericon;
    // End of variables declaration//GEN-END:variables

    private void KullanıcıGiris(String k_ad, String sifre) {
       Connection dbcon = DBHelper.connectionDB();
       if(dbcon !=null){
        try {
            PreparedStatement st = (PreparedStatement)  dbcon.prepareStatement("Select  *  from  uye  WHERE k_ad = ? AND sifre = ?");
           st.setString(1, k_ad);
          st.setString(2, sifre);
          ResultSet res = st.executeQuery();
            if(res.next()){//giriş yaptıktan sonra gösterge tablosunu veya yeni sayfayı göster
            if (res.getString("yetki").equals("yönetici")) {
                   dispose();  
                   Admin a = new Admin();
                   a.setVisible(true);
                      a.setTitle("Admin");
                }  else {
                  dispose();  
                   KullanıcıMenu km = new KullanıcıMenu();
                    km.setVisible(true);
                       km.setTitle("Kullanıcı");
            }   
            }else{
               JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre bulunamadı ", "hata ",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {Logger.getLogger(giris.class.getName()).log(Level.SEVERE, null, e);}
    }else{ System.out.println(" baglantı hatası");}
    
    }

    
}
