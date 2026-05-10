
package posture_analysis;

public class Puanla {
    
   public static double boyunPuaniHesapla(AnalizSonucu basTilt,AnalizSonucu servikalLordoz, AnalizSonucu boyunTilt){
        double wBasTilt = 1.2;
        double wLordoz = 1.0;
        double wLateral = 0.8;

        return agirlikliOrtalama(
            new double[]{basTilt.puan, servikalLordoz.puan, boyunTilt.puan},
            new double[]{wBasTilt, wLordoz, wLateral}
        );
   }
   public static double SirtPuaniHesapla(AnalizSonucu kifoz){
       return kifoz.puan;
   }
   
   public static double omuzSirtPuaniHesapla(AnalizSonucu omuzProtraksiyon, AnalizSonucu omuzSimetri) {
        double wProtraksiyon = 1.0;
        double wSimetri = 0.7;

        return agirlikliOrtalama(
            new double[]{omuzProtraksiyon.puan, omuzSimetri.puan},
            new double[]{wProtraksiyon, wSimetri}
        );
    }
   
   public static double belPuaniHesapla(AnalizSonucu lumbalLordoz)  {

        return lumbalLordoz.puan;
   }
   public static double pelvisPuaniHesapla(AnalizSonucu pelvikTilt, AnalizSonucu pelvikObliquity){
        double wTilt = 1.2;
        double wObliquity = 1.0;

        return agirlikliOrtalama(
            new double[]{pelvikTilt.puan, pelvikObliquity.puan},
            new double[]{wTilt, wObliquity}
        );
   }
   
   public static double altEkstremitePuaniHesapla(AnalizSonucu dizYan, AnalizSonucu dizOn) {
        double wYan = 1.0;
        double wOn = 1.0;

        return agirlikliOrtalama(
            new double[]{dizYan.puan, dizOn.puan},
            new double[]{wYan, wOn}
        );
    }
   
   private static double agirlikliOrtalama(double[] puanlar, double[] agirliklar) {
        double toplamAgirlikliPuan = 0;
        double toplamAgirlik = 0;

        for (int i = 0; i < puanlar.length; i++) {
            toplamAgirlikliPuan += (puanlar[i] * agirliklar[i]);
            toplamAgirlik += agirliklar[i];
        }

        if (toplamAgirlik == 0) return 0;
        
        return Math.round(toplamAgirlikliPuan / toplamAgirlik);
    }
    
    
}
