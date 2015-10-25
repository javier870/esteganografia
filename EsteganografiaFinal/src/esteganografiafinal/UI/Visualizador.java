/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package esteganografiafinal.UI;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Angel
 */
public final class Visualizador extends javax.swing.JFrame {
    
    JFileChooser fs = new JFileChooser();
    Image imagenCargada;
    int control;
    
    /**
     * Creates new form Visualizador
     */
    public Visualizador() {
        initComponents();
        inicializar();
    }
    
    public void inicializar(){
        panelScrol.setVisible(false);        
        panelBarra.setVisible(false);
        botonAceptar.setVisible(false);
        botonCifrar.setVisible(false);
        botonDescifrar.setVisible(false);
        botonCargar.setVisible(false);
        botonGuardar.setVisible(false);
        areaTexto.setText("");
    }
    
    private void muestraBotones(){
        botonCifrar.setVisible(true);
        botonDescifrar.setVisible(true);
        botonCargar.setVisible(true);
        botonGuardar.setVisible(true);
        botonAceptar.setVisible(false);
    }
    
    private BufferedImage buscador(Image imagen, String mensaje) throws IOException{
        String mensajeCompleto = "##/#/#"+mensaje+"//##/#";
        BufferedImage imagenNueva = (BufferedImage) imagen, nuevaddddd = new BufferedImage(imagenNueva.getWidth(), imagenNueva.getHeight(), BufferedImage.TYPE_INT_RGB);
        char[] charArreglo = mensajeCompleto.toCharArray();
        int charControl = 0, contador=0;
        ArrayList<String> nuevaImagen = new ArrayList<>(), almacenado = new ArrayList<>();
        barraProgreso.setMaximum(imagenNueva.getHeight());
        barraProgreso.setStringPainted(true);
                      
        for(int i=0;i<imagenNueva.getHeight();i++){ 
            for(int j=0;j<imagenNueva.getWidth();j++){
                if(charControl<charArreglo.length){
                    almacenado.add(String.format("#%06X", (0xFFFFFF & imagenNueva.getRGB(j, i))));
                    if(almacenado.size()==8){
                        nuevaImagen.addAll(convertidor(almacenado, charArreglo[charControl]));
                        charControl++;
                        almacenado.removeAll(almacenado);
                    }                    
                } else nuevaImagen.add(String.format("#%06X", (0xFFFFFF & imagenNueva.getRGB(j, i))));
                
            }
            barraProgreso.setValue(i+1);
            panelBarra.update(panelBarra.getGraphics());
        }
        
        for(int i=0;i<imagenNueva.getHeight();i++){ 
            for(int j=0;j<imagenNueva.getWidth();j++){
                try {
                    nuevaddddd.setRGB(j, i, Color.decode(nuevaImagen.get(contador)).getRGB());
                    contador++; 
                } catch (Exception e) {System.out.println(e);}                
            }
        }
        botonImagen.setIcon(new ImageIcon(nuevaddddd.getScaledInstance(610, 360, Image.SCALE_DEFAULT)));
        return nuevaddddd;
    }
    
    @SuppressWarnings("empty-statement")
    private ArrayList<String> convertidor(ArrayList<String> hexColor, Character caracter){
        ArrayList<String> convertido = new ArrayList<>();
        char[] arr = Integer.toBinaryString(Integer.parseInt(Integer.toHexString(caracter), 16)).toCharArray();
        int diferencia = 8-arr.length;
        
        int charControl = 0;
        for (String color : hexColor) {
            String b = Integer.toBinaryString(Integer.parseInt(color.substring(5,7), 16));            
            String integrador = Integer.valueOf(Integer.parseInt(color.substring(5,7), 16))<16? "0":"";
            
            if(diferencia==0){
                String codigo = '#'+color.substring(1,5)+integrador+Integer.toHexString(Integer.parseInt(b.substring(0,b.length()-1)+arr[charControl], 2));
                convertido.add(codigo);                
                charControl++;
            } else{
                String codigo = '#'+color.substring(1,5)+integrador+Integer.toHexString(Integer.parseInt(b.substring(0,b.length()-1)+'0', 2));
                convertido.add(codigo);
                diferencia--;
            }            
        }
              
        return convertido;        
    }
     private String descifrador(BufferedImage imagenNueva) {
         String mensaje = "";
         barraProgreso.setMaximum(imagenNueva.getHeight());
         barraProgreso.setStringPainted(true);        
         
         ArrayList<String> almacenado = new ArrayList<>();
         for(int i=0;i<imagenNueva.getHeight();i++){ 
            for(int j=0;j<imagenNueva.getWidth();j++){
                almacenado.add(String.format("#%06X", (0xFFFFFF & imagenNueva.getRGB(j, i))));
                if(almacenado.size()==8){
                    if(almacenado.size()==8){
                        String letra = "";
                        for (String string : almacenado) {
                            String string2 = Integer.toBinaryString(Integer.parseInt(string.substring(5,7), 16));
                            letra+=string2.substring(string2.length()-1, string2.length());
                        }
                        mensaje+=(char) Integer.parseInt(letra,2);
                        almacenado.removeAll(almacenado);
                    }                    
                }                
            }
            barraProgreso.setValue(i+1);
            panelBarra.update(panelBarra.getGraphics());
            
        }
        int buscador = 0;
        String salida = "//##/#";
        for (int i = 0; i < mensaje.length(); i++) {
            buscador = mensaje.charAt(i)==salida.charAt(buscador)? buscador+=1:0;            
            if (buscador==6) return mensaje.substring(6, i-5);            
        }
        return "No existe mensaje";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonGuardar = new javax.swing.JButton();
        panelBarra = new javax.swing.JPanel();
        barraProgreso = new javax.swing.JProgressBar();
        botonAceptar = new javax.swing.JButton();
        botonImagen = new javax.swing.JButton();
        botonDescifrar = new javax.swing.JButton();
        botonCargar = new javax.swing.JButton();
        botonCifrar = new javax.swing.JButton();
        panelScrol = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        labelInicio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(620, 523));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/rsz_1444859962_3floppy_unmount.png"))); // NOI18N
        botonGuardar.setText("Guardar Imagen");
        botonGuardar.setContentAreaFilled(false);
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setPreferredSize(new java.awt.Dimension(115, 30));
        botonGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonGuardarMousePressed(evt);
            }
        });
        getContentPane().add(botonGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 120, 120));

        panelBarra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barraProgreso.setPreferredSize(new java.awt.Dimension(146, 23));
        panelBarra.add(barraProgreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, -1));

        getContentPane().add(panelBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 610, -1));

        botonAceptar.setText("Aceptar");
        botonAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonAceptarMousePressed(evt);
            }
        });
        getContentPane().add(botonAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 500, -1, -1));

        botonImagen.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        botonImagen.setText("Ninguna imagen cargada");
        botonImagen.setFocusPainted(false);
        botonImagen.setFocusable(false);
        botonImagen.setRequestFocusEnabled(false);
        botonImagen.setRolloverEnabled(false);
        botonImagen.setVerifyInputWhenFocusTarget(false);
        botonImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonImagenMousePressed(evt);
            }
        });
        getContentPane().add(botonImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 360));

        botonDescifrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/1444858606_binary.png"))); // NOI18N
        botonDescifrar.setText("Descifrar Imagen");
        botonDescifrar.setContentAreaFilled(false);
        botonDescifrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDescifrar.setPreferredSize(new java.awt.Dimension(115, 30));
        botonDescifrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        botonDescifrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDescifrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonDescifrarMousePressed(evt);
            }
        });
        getContentPane().add(botonDescifrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 120, 120));

        botonCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/rsz_1444859495_iphoto.png"))); // NOI18N
        botonCargar.setText("Cargar Imagen");
        botonCargar.setContentAreaFilled(false);
        botonCargar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCargar.setPreferredSize(new java.awt.Dimension(115, 30));
        botonCargar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        botonCargar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCargar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonCargarMousePressed(evt);
            }
        });
        getContentPane().add(botonCargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 120, 120));

        botonCifrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gnome_mime_application_pgp_keys.png"))); // NOI18N
        botonCifrar.setText("Cifrar Imagen");
        botonCifrar.setContentAreaFilled(false);
        botonCifrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCifrar.setPreferredSize(new java.awt.Dimension(115, 30));
        botonCifrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        botonCifrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCifrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonCifrarMousePressed(evt);
            }
        });
        getContentPane().add(botonCifrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 120, 120));

        areaTexto.setColumns(20);
        areaTexto.setRows(5);
        panelScrol.setViewportView(areaTexto);

        getContentPane().add(panelScrol, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 610, 120));

        labelInicio.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        labelInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInicio.setText("Pulse el boton para cargar una imagen.");
        getContentPane().add(labelInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 610, 160));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCifrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCifrarMousePressed
        inicializar();
        control=1;
        panelScrol.setVisible(true);
        botonAceptar.setVisible(true);        
    }//GEN-LAST:event_botonCifrarMousePressed

    private void botonDescifrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonDescifrarMousePressed
        inicializar();
        control=2;
        panelScrol.setVisible(true);
        panelScrol.setVisible(true);
        areaTexto.setEditable(false);
        botonAceptar.setVisible(true);
        areaTexto.setText("Mensaje: "+descifrador((BufferedImage) imagenCargada));
    }//GEN-LAST:event_botonDescifrarMousePressed

    private void botonImagenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonImagenMousePressed
        fs.showOpenDialog(this);
        if (fs.getSelectedFile()==null) return;
        File file = fs.getSelectedFile();
        System.out.println(file);
        try {
            imagenCargada = ImageIO.read(file);
            botonImagen.setIcon(new ImageIcon(imagenCargada.getScaledInstance(610, 360, Image.SCALE_DEFAULT)));
            labelInicio.setVisible(false);
            botonImagen.setText("");
            muestraBotones();
        } catch (IOException ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonImagenMousePressed

    private void botonAceptarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAceptarMousePressed
        if (control==1) {
            try {
                imagenCargada = buscador(imagenCargada, areaTexto.getText());
                panelScrol.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else inicializar();
        
        muestraBotones();
        control=0;        
    }//GEN-LAST:event_botonAceptarMousePressed

    private void botonCargarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCargarMousePressed
        botonImagenMousePressed(evt);
    }//GEN-LAST:event_botonCargarMousePressed

    private void botonGuardarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonGuardarMousePressed
        fs.showSaveDialog(this);
        if (fs.getSelectedFile()==null) return;
        System.out.println(fs.getSelectedFile());
        try {
            ImageIO.write((RenderedImage) imagenCargada, "png", new File(fs.getSelectedFile()+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonGuardarMousePressed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualizador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCargar;
    private javax.swing.JButton botonCifrar;
    private javax.swing.JButton botonDescifrar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonImagen;
    private javax.swing.JLabel labelInicio;
    private javax.swing.JPanel panelBarra;
    private javax.swing.JScrollPane panelScrol;
    // End of variables declaration//GEN-END:variables

}
