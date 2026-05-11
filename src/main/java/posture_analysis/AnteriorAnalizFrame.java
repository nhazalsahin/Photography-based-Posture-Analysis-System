/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package posture_analysis;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author nhaza
 */
public class AnteriorAnalizFrame extends javax.swing.JFrame {
    private String gelenYol;
    private java.util.List<java.awt.Point> yanNoktalar;
    private String yanYol;      
    private String onYol;    


   private String[] referansIsimleri = { 
    "1- Çene", 
    "2- Omuz Tepesi-1", 
    "3- Omuz Tepesi-2", 
    "4- Köprücük kemikleri birleşim noktası", 
    "5- SIAS - 1(Leğen kemiği önündeki sivri çıkıntı)",
    "6- SIAS - 2(Leğen kemiği önündeki sivri çıkıntı)",
    "7- Diz kapağı merkezi - 1 ",
    "8- Diz kapağı merkezi - 2 ",
    "9- Ayak bileği merkezi - 1 ",
    "10- Ayak bileği merkezi - 2 "      
    };
    private int mevcutNoktaIndex = 0;
    
    private java.util.List<java.awt.Point> noktalar = new java.util.ArrayList<>();
    public AnteriorAnalizFrame() {
        initComponents();
    }
    public AnteriorAnalizFrame(String yanYol, String onYol, java.util.List<java.awt.Point> yanNoktalar) {
        this.yanYol = yanYol;
        this.onYol = onYol;
        this.yanNoktalar = yanNoktalar;
        
        initComponents(); 

        // RESİM YÜKLEME: Bu sefer onYol değişkenini kullanıyoruz!
        try {
            java.io.File dosya = new java.io.File(this.onYol);
            if (dosya.exists()) {
                ImageIcon orijinalSimge = new ImageIcon(dosya.getAbsolutePath());
                Image ölçekliResim = orijinalSimge.getImage().getScaledInstance(750, 500, Image.SCALE_SMOOTH);
                jLabel_karsidan_foto.setIcon(new ImageIcon(ölçekliResim));
                jLabel_karsidan_foto.setText(""); 
                this.revalidate();
                this.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel_referans = new javax.swing.JLabel();
        jLabel_karsidan_foto = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (noktalar != null) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                    g2d.setColor(java.awt.Color.GREEN);
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    for (java.awt.Point p : noktalar) {
                        g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
                    }
                }
            }
        };
        jButton_ileri = new javax.swing.JButton();
        jButton_geri = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(10, 25, 47));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 214, 246));
        jLabel1.setText("ÖNDEN POSTÜR ANALİZİ İÇİN REFERANS NOKTALARINI İŞARETLEYİN");

        jLabel_referans.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_referans.setForeground(new java.awt.Color(100, 255, 218));
        jLabel_referans.setText("1-Çene");

        jLabel_karsidan_foto.setBackground(new java.awt.Color(0, 102, 102));
        jLabel_karsidan_foto.setText("Kullanıcı Yandan Foto");
        jLabel_karsidan_foto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_karsidan_fotoMouseClicked(evt);
            }
        });

        jButton_ileri.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_ileri.setIcon(new javax.swing.ImageIcon("C:\\Users\\nhaza\\OneDrive\\Documents\\NetBeansProjects\\Postur_Analizi\\src\\main\\java\\posture_analysis\\icons\\icons8-forward-50.png")); // NOI18N
        jButton_ileri.setText("İLERİ");
        jButton_ileri.setEnabled(false);
        jButton_ileri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ileriActionPerformed(evt);
            }
        });

        jButton_geri.setIcon(new javax.swing.ImageIcon("C:\\Users\\nhaza\\OneDrive\\Documents\\NetBeansProjects\\Postur_Analizi\\src\\main\\java\\posture_analysis\\icons\\turn-left (1).png")); // NOI18N
        jButton_geri.setText("GERİ");
        jButton_geri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_geriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel_referans)
                            .addComponent(jLabel_karsidan_foto))
                        .addContainerGap(489, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_geri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_ileri)
                        .addGap(15, 15, 15))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_ileri))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_referans)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_karsidan_foto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 499, Short.MAX_VALUE)
                        .addComponent(jButton_geri)))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_karsidan_fotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_karsidan_fotoMouseClicked
        if (mevcutNoktaIndex < 10) {
        // Sadece burada ekleme yapıyoruz
        noktalar.add(evt.getPoint());
        jLabel_karsidan_foto.repaint();
        
        System.out.println(referansIsimleri[mevcutNoktaIndex] + " İşaretlendi: " + evt.getPoint());

        mevcutNoktaIndex++;

        if (mevcutNoktaIndex < 10) {
            jLabel_referans.setText(referansIsimleri[mevcutNoktaIndex]);
        } else {
            jLabel_referans.setText("Tüm Noktalar İşaretlendi!");
            jButton_ileri.setEnabled(true);
        }
        
    }
    }//GEN-LAST:event_jLabel_karsidan_fotoMouseClicked

    private void jButton_ileriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ileriActionPerformed
       try {
        // Tüm yolları ve noktaları SonucFrame'e paslıyoruz
        SonucFrame sonuc = new SonucFrame(this.yanNoktalar, this.noktalar, this.yanYol, this.onYol);
        sonuc.setVisible(true);
        this.dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButton_ileriActionPerformed

    private void jButton_geriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_geriActionPerformed
        // TODO add your handling code here:
        YandanAnalizFrame yandan = new YandanAnalizFrame(this.yanYol, this.onYol);
        yandan.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton_geriActionPerformed

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
            java.util.logging.Logger.getLogger(AnteriorAnalizFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnteriorAnalizFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnteriorAnalizFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnteriorAnalizFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnteriorAnalizFrame().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_geri;
    private javax.swing.JButton jButton_ileri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_karsidan_foto;
    private javax.swing.JLabel jLabel_referans;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
