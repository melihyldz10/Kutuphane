

package kutuphanem;




public class duyurular {
  int duyuruNo;
  String duyuru;
  String baslık;

    public duyurular(int duyuruNo,String duyuru, String baslık) {
          this.duyuruNo = duyuruNo;
        this.duyuru = duyuru;
        this.baslık = baslık;
    }

    public int getDuyuruNo() { return duyuruNo;}
    public void setDuyuruNo(int duyuruNo) {this.duyuruNo = duyuruNo;}

    public String getDuyuru() {  return duyuru;}
    public void setDuyuru(String duyuru) {  this.duyuru = duyuru; }

    public String getBaslık() {return baslık; }
    public void setBaslık(String baslık) { this.baslık = baslık;}
    
    
    
    
}
