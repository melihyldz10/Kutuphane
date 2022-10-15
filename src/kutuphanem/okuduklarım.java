

package kutuphanem;


public class okuduklarım {
    
        int  kitapNo;
        String yazarAd;
        String yazarSoyad;
       String kitapAd;
       String kitapTur;
       String yayınEv;


  
   

        //constcracter
        public okuduklarım(int kitapNo,String yazarAd, String yazarSoyad, String kitapAd, String kitapTur, String yayınEv) {
            this.kitapNo = kitapNo;
            this.yazarAd = yazarAd;
            this.yazarSoyad = yazarSoyad;
            this.kitapAd = kitapAd;
            this.kitapTur = kitapTur;
            this.yayınEv = yayınEv;
        }

        public int getKitapNo() { return kitapNo;  }
        public void setKitapNo(int kitapNo) { this.kitapNo = kitapNo;}

         public String getYazarAd() { return yazarAd;}
         public void setYazarAd(String yazarAd) { this.yazarAd = yazarAd;}

            public String getYazarSoyad() { return yazarSoyad; }
          public void setYazarSoyad(String yazarSoyad) {   this.yazarSoyad = yazarSoyad; }
        
        public String getKitapAd() {  return kitapAd;}
        public void setKitapAd(String kitapAd) {    this.kitapAd = kitapAd;}

        public String getKitapTur() {return kitapTur;}
        public void setKitapTur(String kitapTur) { this.kitapTur = kitapTur;}
       
        public String getYayınEv() {  return yayınEv;}
        public void setYayınEv(String yayınEv) {   this.yayınEv = yayınEv;}
        
   
    }
    
    
    


