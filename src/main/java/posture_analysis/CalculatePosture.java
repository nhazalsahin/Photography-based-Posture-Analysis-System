/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posture_analysis;

import java.awt.Point;
import java.util.List;

public class CalculatePosture {
    //kifoz için kamburun en çıkıntılı noktası ve boyun lordozu için
    public static double HorizontalAciHesapla(Point p1, Point p2){
        Point sag = (p1.x < p2.x) ? p1 : p2;
        Point sol = (p1.x < p2.x) ? p2 : p1;
        return Math.round(Math.toDegrees(Math.atan2(sol.y - sag.y, sol.x - sag.x)));
    }

    public static double VerticalAciHesapla(Point p1, Point p2){
        Point ust = (p1.y < p2.y) ? p1 : p2; // Y koordinatı küçük olan üsttedir
        Point alt = (p1.y < p2.y) ? p2 : p1;
        return Math.round(Math.toDegrees(Math.atan2(ust.y - alt.y, ust.x - alt.x)));
    }

    public static double UcNoktaAciHesapla(Point p1, Point p2, Point p3){
        double aci1 = Math.atan2(p1.y - p2.y, p1.x - p2.x);
        double aci2 = Math.atan2(p3.y - p2.y, p3.x - p2.x);
        double sonuc = Math.abs(Math.toDegrees(aci1 - aci2));
        return (sonuc > 180) ? 360 - sonuc : sonuc;
    }
    
    public static AnalizSonucu basPozisyonuYorumla(List<Point> noktalar){
        //c7 tragus vertikal aci
        double aci = VerticalAciHesapla(noktalar.get(2), noktalar.get(0));
        double sapma = Math.abs(90 - Math.abs(aci));
        
        if(sapma<5){
            return new AnalizSonucu("Normal: Baş pozisyonu ideal sınırlar içerisinde.", 100);
        }
        else if(sapma>=5 && sapma<10){
            return new AnalizSonucu("Minimal : Baş hafif düzeyde öne tilt yapmış.", 75);
        }
        else if(sapma>=10 && sapma<15){
            return new AnalizSonucu("Orta: Baş belirgin şekilde öne doğru yer değiştirmiş", 50);
        }
        else{
            return new AnalizSonucu("Yüksek: Baş ileri derecede öne tilt yapmış.Lütfen hekime danışın", 0);
        }  
}

    public static AnalizSonucu servikalLordozYorumla(List<Point> noktalar) {

        //tragus,servikal lordoz apeksi,c7 üç nokta
        
    double sapma = UcNoktaAciHesapla(noktalar.get(0), noktalar.get(1), noktalar.get(2));

    if (sapma < 8) {
        return new AnalizSonucu("Risk: Servikal lordozda belirgin azalma saptandı (Boyun Düzleşmesi riski). " +
               "Disk aralıklarına binen yük artmış olabilir.",0);
    }

    else if (sapma >= 8 && sapma < 15) {
        return new AnalizSonucu("Hafif: Servikal lordozda minimal azalma gözlendi. " +
               "Kas spazmına bağlı geçici bir düzleşme eğilimi olabilir.",75);
    }

    else if (sapma >= 15 && sapma <= 25) {
        return new AnalizSonucu("Normal: Servikal lordoz (boyun kavisi) fizyolojik sınırlar içerisinde korunmuş.", 100);
    }

    else {
        return new AnalizSonucu("Artmış Lordoz: Servikal kavis normalden fazla görünüyor. " +
               "Sırt kifozuyla kompanse edilen bir duruş olabilir.", 75);
    }
    }
    
    public static AnalizSonucu boyunLateralTiltYorumla(List<Point> noktalar) {
    double mutlakAci = VerticalAciHesapla(noktalar.get(0), noktalar.get(3));

    if (mutlakAci < 3) {
        return new AnalizSonucu("Normal: Baş ve boyun dizilimi karşıdan bakışta merkezi (orta hatta).", 100);
    }

    String yon = (mutlakAci > 0) ? "sağa " : "sola ";

    if (mutlakAci >= 3 && mutlakAci < 7) {
        return new AnalizSonucu("Hafif: Başın " + yon + "doğru minimal lateral tilti (yana eğilme) izlendi.", 75);
    } else if (mutlakAci >= 7 && mutlakAci < 12) {
        return new AnalizSonucu("Orta: Belirgin boyun asimetrisi. Sternokleidomastoid (SKM) kas gerginliği açısından değerlendirilmelidir.", 50);
    } else {
        return new AnalizSonucu("Yüksek: İleri derece lateral tilt saptandı. Torticollis veya ileri derece skolyoz kompanzasyonu olabilir.", 0);
    }
}
    
    public static AnalizSonucu omuzSimetriYorumla(List<Point> noktalar) {
        
    double mutlakAci =HorizontalAciHesapla(noktalar.get(1), noktalar.get(2));
    
    if (mutlakAci < 3) {
        return new AnalizSonucu("Simetrik: Omuz seviyeleri dengeli.", 100);
    }

    String yuksekTaraf = (mutlakAci > 0) ? "Sağ omuz daha yüksekte. " : "Sol omuz daha yüksekte. ";

    if (mutlakAci >= 3 && mutlakAci < 7) {
        return new AnalizSonucu("Hafif: " + yuksekTaraf + "Omuzlarda minimal seviye farkı izlendi.", 75);
    } else {
        return new AnalizSonucu("Belirgin: " + yuksekTaraf + "Omuz asimetrisi saptandı. Klinik değerlendirme ve skolyoz taraması önerilir.", 100);
    }
}
    
    public static AnalizSonucu omuzProtraksiyonYorumla(List<Point> noktalar){
        //akromion ve trochanter major vertikal açı
        String yonYorumu = "";
        double derece = VerticalAciHesapla(noktalar.get(3), noktalar.get(6));
        if(derece>3){
            yonYorumu = "Omuz, gövde hattına göre öne doğru yer değiştirmiş.";
        }
        if(derece<5){
            return new AnalizSonucu("Normal : Omuz pozisyonu gövde ve ağırlık merkezi ile uyumlu.", 100);
        }else if(derece>=5 && derece<10){
            return new AnalizSonucu("Hafif: " + yonYorumu + " (Minimal Protraksiyon)", 75);
        }else if(derece>=10 && derece<15){
            return new AnalizSonucu("Orta: " + yonYorumu + " Belirgin yuvarlak omuz duruşu gözlemlendi.", 50);
        } else {
            return new AnalizSonucu("Yüksek: İleri derece omuz protraksiyonu. Pektoral germe ve sırt güçlendirme önerilir.", 0);
        }
    }
    
    
    public static AnalizSonucu kifozYorumla(List<Point> noktalar) {

        //c7, torokal kifoz apeksi , lomber lordoz apeksi
        
    double sapma = UcNoktaAciHesapla(noktalar.get(2), noktalar.get(4), noktalar.get(5));

    
    if (sapma < 12) {
        return new AnalizSonucu("Düz Sırt: Torakal kifozda azalma (Flat Back) gözlendi. " +
               "Şok emilim kapasitesi azalmış olabilir.", 50);
    }

    else if (sapma >= 12 && sapma < 22) {
        return new AnalizSonucu("Normal: Torakal kavis (kifoz) fizyolojik sınırlar içerisinde.", 100);
    }

    else if (sapma >= 22 && sapma < 32) {
        return new AnalizSonucu("Orta: Belirgin kifotik postür (Kamburluk eğilimi). " +
               "Omuz protraksiyonu ile kombine olabilir.", 50);
    }

    else {
        return new AnalizSonucu("Yüksek: İleri derece torakal kifoz. Postüral egzersizler ve " +
               "klinik takip gereklidir.", 0);
    }
}
    
    public static AnalizSonucu pelvikTiltYorumla(List<Point> noktalar) {
        
        //uc nokta lumbal apex sias trochanter major
      
    double sapma = UcNoktaAciHesapla(noktalar.get(5), noktalar.get(7), noktalar.get(6));

    if (sapma < 7) {
        return new AnalizSonucu("Nötral: Pelvik dizilim fizyolojik sınırlar içerisinde.", 100);
    }

    else if (sapma >= 7 && sapma < 15) {
        return new AnalizSonucu("Hafif: Minimal pelvik tilt gözlendi. Bel bölgesindeki mekanik yükü etkileyebilir.", 75);
    }

    else if (sapma >= 15 && sapma <= 25) {
        return new AnalizSonucu("Orta: Belirgin pelvik tilt saptandı. Lumbal lordoz derinliği ile kombine değerlendirilmelidir.", 50);
    }

    else {
        return new AnalizSonucu("Yüksek: İleri derece pelvik tilt. Kalça ve bel biyomekaniği üzerinde ciddi risk oluşturabilir.", 0);
    }
}
    
    public static AnalizSonucu lumbalLordozYorumla(List<Point> noktalar) {
    //torokal apeks lumbal apeks sias
    double sapma = UcNoktaAciHesapla(noktalar.get(4), noktalar.get(5), noktalar.get(7));

    if (sapma < 10) {
        return new AnalizSonucu("Düz Bel: Lumbal lordozda belirgin azalma (Flat Back) saptandı. " +
               "Amortisör etkisi azaldığı için bel fıtığı riski artabilir.", 50);
    }

    else if (sapma >= 10 && sapma < 22) {
        return new AnalizSonucu("Normal: Lumbal lordoz (bel kavisi) fizyolojik sınırlar içerisinde.", 100);
    }

    else if (sapma >= 22 && sapma < 32) {
        return new AnalizSonucu("Orta: Artmış bel çukuru (Hiperlordoz) gözlendi. " +
               "Bel eklemlerine binen yük artmış durumdadır.", 50);
    }

    else {
        return new AnalizSonucu("Yüksek: İleri derece lumbal hiperlordoz. " +
               "Klinik değerlendirme ve spesifik egzersiz programı önerilir.", 0);
    }
}
    
    public static AnalizSonucu pelvikObliquityYorumla(List<Point> noktalar) {
    double mutlakAci = HorizontalAciHesapla(noktalar.get(4), noktalar.get(5));

    if (mutlakAci < 2.5) {
        return new AnalizSonucu("Simetrik: Pelvik seviyeler (SIAS) dengeli görünüyor.", 100);
    }

    String yuksekTaraf = (mutlakAci > 0) ? "Sağ " : "Sol ";

    if (mutlakAci >= 2.5 && mutlakAci < 6) {
        return new AnalizSonucu("Hafif: " + yuksekTaraf + "tarafta pelvik yükselme (obliquity) izlendi. " +
               "Fonksiyonel bacak boyu farkı açısından değerlendirilmelidir.", 75);
    } else {
        return new AnalizSonucu("Belirgin: " + yuksekTaraf + "tarafta belirgin pelvik asimetri saptandı. " +
               "Yapısal bacak boyu kısalığı veya skolyoz eşlik ediyor olabilir.", 0);
    }
}
    
    public static AnalizSonucu dizDizilimiAnteriorYorumla(List<Point> noktalar) {
        
    double aci = UcNoktaAciHesapla(noktalar.get(4), noktalar.get(6), noktalar.get(8));
    double sapma = Math.abs(180 - aci);

   
    if (sapma < 4) {
        return new AnalizSonucu("Normal: Alt ekstremite dizilimi (kalça-diz-ayak bileği) fizyolojik sınırlarda.", 100);
    }

    if (sapma < 176) { 
        
        if (sapma >= 4 && sapma < 10) {
            return new AnalizSonucu("Hafif Genu Valgum: Dizlerde minimal içe doğru yönelim (X bacak eğilimi).", 75);
        } else {
            return new AnalizSonucu("Belirgin Genu Valgum: Belirgin X bacak dizilimi. Pes planus (düz tabanlık) eşlik ediyor olabilir.", 0);
        }
    } else {

        if (sapma >= 4 && sapma < 10) {
            return new AnalizSonucu("Hafif Genu Varum: Dizlerde minimal dışa doğru yönelim (Parantez bacak eğilimi).", 75);
        } else {
            return new AnalizSonucu("Belirgin Genu Varum: Belirgin parantez bacak dizilimi. Menisküs yüklenmeleri açısından riskli olabilir.", 0);
        }
    }
}
    
    public static AnalizSonucu dizPozisyonuYorumla(List<Point> noktalar) {

        //trochanter diz malleol
        
    double sapma = UcNoktaAciHesapla(noktalar.get(6), noktalar.get(8), noktalar.get(9));

    if (sapma < 5) {
        return new AnalizSonucu("Normal: Diz eklem dizilimi fizyolojik sınırlar içerisinde.", 100);
    }

    boolean dizOnde = (noktalar.get(8).x > noktalar.get(6).x);

    if (dizOnde) {

        if (sapma >= 5 && sapma < 12) {
            return new AnalizSonucu("Hafif Fleksiyon: Dizlerde minimal bükülme gözlendi. Kuadriseps tonusu kontrol edilmeli.", 75);
        } else {
            return new AnalizSonucu("Belirgin Fleksiyon: Dizler bükülü pozisyonda (Flexed Knee). Hamstring gerginliği olabilir.", 0);
        }
    } else {

        if (sapma >= 5 && sapma < 10) {
            return new AnalizSonucu("Hafif Rekurvatum: Dizlerde arkaya doğru minimal kaçış başlangıcı.", 75);
        } else {
            return new AnalizSonucu("Genu Rekurvatum: Belirgin diz hiperektansiyonu saptandı. " + 
                   "Gastrocnemius gerginliği ve bağ laksitesi değerlendirilmelidir.", 0);
        }
    }
}

}