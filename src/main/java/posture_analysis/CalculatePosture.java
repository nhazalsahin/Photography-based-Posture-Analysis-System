/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posture_analysis;

import java.awt.Point;
import java.util.List;

public class CalculatePosture {
    
    public static double DikeyEksenSapmaHesapla(Point p1, Point p2) {
    double deltaX = p1.x - p2.x;
    double deltaY = p1.y - p2.y;

    // atan2(dx, dy) kullanarak dikey eksenle yapılan açıyı radyan cinsinden alırız
    double radyan = Math.atan2(Math.abs(deltaX), Math.abs(deltaY));
    
    return Math.toDegrees(radyan);
    }
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

    public static double UcNoktaAciHesapla(Point p1, Point p2, Point p3) {
     // Vektör 1 (Apeks'ten Tragus'a)
     double v1x = p1.x - p2.x;
     double v1y = p1.y - p2.y;

     // Vektör 2 (Apeks'ten C7'ye)
     double v2x = p3.x - p2.x;
     double v2y = p3.y - p2.y;

     // Dot product ve Magnitudes üzerinden açı hesaplama (Daha stabil bir yöntem)
     double dotProduct = (v1x * v2x) + (v1y * v2y);
     double mag1 = Math.sqrt(v1x * v1x + v1y * v1y);
     double mag2 = Math.sqrt(v2x * v2x + v2y * v2y);

     // Sıfıra bölme kontrolü
     if (mag1 == 0 || mag2 == 0) return 180;

     double cosTheta = dotProduct / (mag1 * mag2);

     // Hassasiyet hatalarını (1.0000000002 gibi) önlemek için clamping
     cosTheta = Math.max(-1.0, Math.min(1.0, cosTheta));

     return Math.toDegrees(Math.acos(cosTheta));
 }
    
    public static AnalizSonucu basPozisyonuYorumla(List<Point> noktalar){
        //c7 tragus vertikal aci
        double aci = VerticalAciHesapla(noktalar.get(2), noktalar.get(0));
        
        if(aci<=15){
            return new AnalizSonucu("Normal: Baş pozisyonu ideal sınırlar içerisinde.", 100);
        }
        else if(aci>15 && aci<=30){
            return new AnalizSonucu("Minimal : Baş hafif düzeyde öne tilt yapmış.", 75);
        }
        else if(aci<=45 && aci>30){
            return new AnalizSonucu("Orta: Baş belirgin şekilde öne doğru yer değiştirmiş", 50);
        }
        else{
            return new AnalizSonucu("Yüksek: Baş ileri derecede öne tilt yapmış.Lütfen hekime danışın", 0);
        }  
}

    public static AnalizSonucu servikalLordozYorumla(List<Point> noktalar) {

        //tragus,servikal lordoz apeksi,c7 üç nokta
        
    double icAci = UcNoktaAciHesapla(noktalar.get(0), noktalar.get(1), noktalar.get(2));
    double lordozAcisi = 180.0 - icAci;

    // Tıbbi literatüre göre eşik değerleri (Örn: Harrison, 1996 - Servikal Lordoz Standartları)
    if (lordozAcisi < 10) {
        return new AnalizSonucu("Kritik: Servikal lordozda ciddi azalma (Boyun Düzleşmesi). " +
                "Omurga fizyolojik eğrisini kaybetmiş, disk yükü maksimum seviyede.", 0);
    } 
    else if (lordozAcisi >= 10 && lordozAcisi < 20) {
        return new AnalizSonucu("Orta: Servikal lordozda belirgin azalma. " +
                "Postüral bozukluk ve kronik ağrı riski yüksektir.", 50);
    } 
    else if (lordozAcisi >= 20 && lordozAcisi < 30) {
        return new AnalizSonucu("Hafif: Minimal lordoz kaybı. " +
                "Genellikle kas spazmı veya başlangıç aşamasında postüral adaptasyon.", 75);
    } 
    else if (lordozAcisi >= 30 && lordozAcisi <= 45) {
        return new AnalizSonucu("Normal: Servikal kavis fizyolojik sınırlar içerisinde (%30-45°).", 100);
    } 
    else {
        return new AnalizSonucu("Artmış Lordoz (Hiperlordoz): Kavis normalden fazla. " +
                "Sırt bölgesindeki kompanse edici (kifoz) bir durum araştırılmalıdır.", 70);
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
        }else if(derece>=5 && derece<15){
            return new AnalizSonucu("Hafif: " + yonYorumu + " (Minimal Protraksiyon)", 75);
        }else if(derece>=15 && derece<25){
            return new AnalizSonucu("Orta: " + yonYorumu + " Belirgin yuvarlak omuz duruşu gözlemlendi.", 50);
        } else {
            return new AnalizSonucu("Yüksek: İleri derece omuz protraksiyonu. Pektoral germe ve sırt güçlendirme önerilir.", 0);
        }
    }
    
    
    public static AnalizSonucu kifozYorumla(List<Point> noktalar) {

        //c7, torokal kifoz apeksi , lomber lordoz apeksi
        
    double derece = UcNoktaAciHesapla(noktalar.get(2), noktalar.get(4), noktalar.get(5));

    
    if (derece >= 165) {
        return new AnalizSonucu("Düz Sırt: Torakal kifozda azalma (Flat Back) gözlendi. " +
               "Şok emilim kapasitesi azalmış olabilir.", 50);
    }

    else if (derece >= 150 && derece < 165) {
        return new AnalizSonucu("Normal: Torakal kavis (kifoz) fizyolojik sınırlar içerisinde.", 100);
    }

    else if (derece >=140 && derece < 150) {
        return new AnalizSonucu("Orta: Belirgin kifotik postür (Kamburluk eğilimi). " +
               "Omuz protraksiyonu ile kombine olabilir.", 50);
    }

    else {
        return new AnalizSonucu("Yüksek: İleri derece torakal kifoz. Postüral egzersizler ve " +
               "klinik takip gereklidir.", 0);
    }
}
    
    public static AnalizSonucu pelvikTiltYorumla(List<Point> noktalar) {
       
        //vertikal sias trochanter major
      
    double aci = Math.abs(DikeyEksenSapmaHesapla(noktalar.get(7), noktalar.get(6)));

    // Tıbbi Referans (ASIS-PSIS varsayımıyla veya dikey sapma toleransıyla)
    if (aci > 15) {
        return new AnalizSonucu("Anterior Pelvik Tilt: Pelvis öne doğru aşırı eğilmiş. " +
                "Bel çukuru (lordoz) artmış ve bel ağrısı riski oluşmuş olabilir.", 50);
    } 
    else if (aci >= 7 && aci <= 15) {
        return new AnalizSonucu("Normal: Pelvis nötr ve fizyolojik pozisyonda.", 100);
    } 
    else if (aci < 7) {
        // Açı 0'a yaklaştıkça pelvis "Flat" (Düz) pozisyona geçer
        return new AnalizSonucu("Posterior Pelvik Tilt: Pelvis arkaya eğilmiş (Tuck-under). " +
                "Bel düzleşmesi ve hamstring gerginliği eşlik edebilir.", 50);
    } 
    else {
        return new AnalizSonucu("Sınıflandırılamayan Değer: Ölçüm pozisyonunu kontrol edin.", 0);
    }
}
    
    public static AnalizSonucu lumbalLordozYorumla(List<Point> noktalar) {
    //torokal apeks lumbal apeks trochanter
    double aci = UcNoktaAciHesapla(noktalar.get(4), noktalar.get(5), noktalar.get(7));

    if (aci >= 165) {
        return new AnalizSonucu("Düz Bel: Lumbal lordozda belirgin azalma (Flat Back) saptandı. " +
               "Amortisör etkisi azaldığı için bel fıtığı riski artabilir.", 50);
    }

    else if (aci >= 145 && aci < 165) {
        return new AnalizSonucu("Normal: Lumbal lordoz (bel kavisi) fizyolojik sınırlar içerisinde.", 100);
    }

    else if (aci >= 130 && aci < 145) {
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
    
    public static AnalizSonucu SagDizDizilimiAnteriorYorumla(List<Point> noktalar) {
        
        Point sias = noktalar.get(4);
        Point diz = noktalar.get(6);
        Point bilek = noktalar.get(8);

        double aci = UcNoktaAciHesapla(sias, diz, bilek);
        double sapma = Math.abs(180 - aci);

        // 1. Kısım: Normal Aralık Kontrolü
        if (sapma < 5) {
            return new AnalizSonucu("Normal: Sağ alt ekstremite dizilimi fizyolojik sınırlarda.", 100);
        }

        // 2. Kısım: Yön Tayini (Sağ bacak için X ekseni kontrolü)
        // Sağ bacakta dizin X değeri SIAS'tan büyükse diz içe kaymıştır (Valgum).
        boolean isValgum = diz.x > sias.x;

        if (isValgum) {
            if (sapma < 10) {
                return new AnalizSonucu("Hafif Genu Valgum: Sağ dizde hafif içe yönelim (X bacak).", 75);
            } else {
                return new AnalizSonucu("Belirgin Genu Valgum: Belirgin X bacak dizilimi. Pes planus eşlik edebilir.", 0);
            }
        } else {
            // Varum Durumu
            if (sapma < 10) {
                return new AnalizSonucu("Hafif Genu Varum: Sağ dizde hafif dışa yönelim (O bacak).", 75);
            } else {
                return new AnalizSonucu("Belirgin Genu Varum: Belirgin parantez bacak dizilimi. Menisküs riski.", 0);
            }
        }
    }
    
    public static AnalizSonucu SolDizDizilimiAnteriorYorumla(List<Point> noktalar) {
    // Sol Bacak: SIAS(6), Diz(8), Ayak Bileği(10)
    Point sias = noktalar.get(5);
    Point diz = noktalar.get(7);
    Point bilek = noktalar.get(9);

    double aci = UcNoktaAciHesapla(sias, diz, bilek);
    double sapma = Math.abs(180 - aci);

    // 1. Kısım: Normal Aralık Kontrolü
    if (sapma < 5) {
        return new AnalizSonucu("Normal: Sol alt ekstremite dizilimi fizyolojik sınırlarda.", 100);
    }

    // 2. Kısım: Yön Tayini (Sol bacak için X ekseni kontrolü)
    // Sol bacakta dizin X değeri SIAS'tan küçükse diz içeri (merkeze) kaymıştır (Valgum).
    // Not: Bu mantık kameranın hastayı karşıdan çektiği varsayımıyla geçerlidir.
    boolean isValgum = diz.x < sias.x;

    if (isValgum) {
        if (sapma < 10) {
            return new AnalizSonucu("Hafif Genu Valgum: Sol dizde hafif içe yönelim (X bacak).", 75);
        } else {
            return new AnalizSonucu("Belirgin Genu Valgum: Belirgin X bacak dizilimi. Pes planus eşlik edebilir.", 0);
        }
    } else {
        // Varum Durumu (Diz dışarı kaçmışsa X değeri SIAS'tan büyük olur)
        if (sapma < 10) {
            return new AnalizSonucu("Hafif Genu Varum: Sol dizde hafif dışa yönelim (O bacak).", 75);
        } else {
            return new AnalizSonucu("Belirgin Genu Varum: Belirgin parantez bacak dizilimi. Menisküs riski.", 0);
        }
    }
}
    public static AnalizSonucu dizDizilimiAnteriorYorumla(List<Point>noktalar){
        String yorum = SolDizDizilimiAnteriorYorumla(noktalar).yorum+SagDizDizilimiAnteriorYorumla(noktalar).yorum;
        int puan = (SolDizDizilimiAnteriorYorumla(noktalar).puan+SagDizDizilimiAnteriorYorumla(noktalar).puan)/2;
        
        return new AnalizSonucu(yorum,puan);
    }
    
    public static AnalizSonucu dizPozisyonuYorumla(List<Point> noktalar) {

        //trochanter diz malleol
        double icAci = UcNoktaAciHesapla(noktalar.get(6), noktalar.get(8), noktalar.get(9));
        double sapma = Math.abs(180.0 - icAci);

        // 0-5 derece arası tıbben tam düz veya doğal kilitlenme kabul edilir
        if (sapma <= 5) {
            return new AnalizSonucu("Normal: Diz eklem dizilimi fizyolojik sınırlar içerisinde.", 100);
        }

        // YÖN KONTROLÜ: Diz trochanter-malleol hattının önünde mi arkasında mı?
        // Bu basit x kontrolü kişinin SAĞA baktığı senaryoda çalışır. 
        // Daha garantisi için Cross Product (çapraz çarpım) kullanılabilir.
        boolean dizOnde = (noktalar.get(8).x > noktalar.get(6).x); 

        if (dizOnde) {
            // DİZ FLEKSİYONU (Bükülü Diz)
            if (sapma > 5 && sapma < 12) {
                return new AnalizSonucu("Hafif Fleksiyon: Dizlerde minimal bükülme. Kuadriseps tonusu ve hamstring kısalığı kontrol edilmeli.", 75);
            } else {
                return new AnalizSonucu("Belirgin Fleksiyon: Dizler bükülü pozisyonda (Flexed Knee). " +
                        "Yürüyüş biyomekaniği ve enerji tüketimi etkilenebilir.", 0);
            }
        } else {
            // GENU REKURVATUM (Dizin arkaya kaçması)
            if (sapma > 5 && sapma < 10) {
                return new AnalizSonucu("Hafif Rekurvatum: Dizlerde arkaya doğru minimal kaçış başlangıcı.", 75);
            } else {
                return new AnalizSonucu("Genu Rekurvatum: Belirgin diz hiperektansiyonu saptandı. " + 
                        "Eklem kapsülü laksitesi ve quadriceps zayıflığı araştırılmalıdır.", 0);
            }
        }
        }

}