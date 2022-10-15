

package kutuphanem;



public class uyeler {

         int uye_id;
        String u_ad;
        String u_soyad;
        String k_ad;
        String yetki;

    public uyeler(int uye_id,String u_ad, String u_soyad, String k_ad, String yetki) {
        
        this.uye_id=uye_id;
        this.u_ad = u_ad;
        this.u_soyad = u_soyad;
        this.k_ad = k_ad;
        this.yetki = yetki;
    }

    public int getUye_id() {return uye_id; }
    public void setUye_id(int uye_id) {this.uye_id = uye_id; }
    
    public String getU_ad() { return u_ad; }
    public void setU_ad(String u_ad) { this.u_ad = u_ad;}

    public String getU_soyad() {return u_soyad; }
   public void setU_soyad(String u_soyad) {  this.u_soyad = u_soyad;}

    public String getK_ad() {return k_ad;}
    public void setK_ad(String k_ad) { this.k_ad = k_ad;}

    public String getYetki() {return yetki;}
    public void setYetki(String yetki) {this.yetki = yetki;}
    
    
}
