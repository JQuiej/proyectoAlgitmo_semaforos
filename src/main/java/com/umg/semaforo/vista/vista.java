
package com.umg.semaforo.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;



public class vista extends javax.swing.JFrame {

private final Queue<JLabel> clientes = new LinkedList<>();
    private static final int capacidad = 3;
    private JLabel[] Puerta = new JLabel[3];
    private JLabel In = new JLabel();
    private JLabel[] clientesLabels = new JLabel[3];
    private Timer timer;
    private int clientesEnFila = 0;
    private volatile boolean running = true;

    public vista() {
        initComponents();
        this.setTitle("SEMAFORO UMG");
        this.setLocationRelativeTo(null);
        INICIAR.addActionListener(e -> iniciarAnimacion());
        PARAR.addActionListener(e -> detenerAnimacion() );


        Puerta[0] = puer;
        Puerta[1] = puer2;
        Puerta[2] = puer3;
        In = IN;
        clientesLabels[0] = client1;
        clientesLabels[1] = client2;
        clientesLabels[2] = client3;
    }

    private void iniciarAnimacion() {
        new Thread(() -> {
            try {
                producir();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                consumir();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    private void detenerAnimacion() {
    running = false; 
    }
        
    public static int RandomNumber(int minRange1, int maxRange1, int minRange2, int maxRange2) {
        Random random = new Random();
        
        // Decidir aleatoriamente si usar el primer rango o el segundo rango
        if (random.nextBoolean()) {
            // Generar número aleatorio en el primer rango
            return random.nextInt((maxRange1 - minRange1) + 1) + minRange1;
        } else {
            // Generar número aleatorio en el segundo rango
            return random.nextInt((maxRange2 - minRange2) + 1) + minRange2;
        }
    }

    public synchronized void producir() throws InterruptedException {
        while (running) {
            while (clientesEnFila == capacidad) {
                wait();  // Espera si la fila está llena
            }

            int index = clientesEnFila;
            JLabel clienteLabel = clientesLabels[index];
            clientes.add(clienteLabel);
            clientesEnFila++;
            System.out.println("movemos a cola "  );
            moverCliente(clienteLabel, Puerta[index]);

            notify();  // Notifica al consumidor que hay un cliente disponible
            Thread.sleep(RandomNumber(2000,3000,2250,2750));
        }
    }

    public synchronized void consumir() throws InterruptedException {
        while (running) {
            while (clientesEnFila == 0) {
                wait();  // Espera si no hay clientes en la fila
            }

            JLabel atendido = clientes.poll();
            clientesEnFila--;

            // Espera un tiempo mientras el cliente es atendido
            Thread.sleep(RandomNumber(1000,2000,1225,1750));

            // Después de ser atendido, regresa el cliente a su posición inicial
            resetCliente(atendido);
            notify();  // Notifica al productor que hay espacio disponible en la fila
            Thread.sleep(RandomNumber(250,1000,500,750));
        }
    }

    private void moverCliente(JLabel cliente, JLabel destino) {
        int startX = cliente.getX();
        int startY = cliente.getY();
        
        System.out.println("x " +startX +" y " + startY );

        int endX = destino.getX()+10;
        int endY = destino.getY()+90;

        int delay = 10;
        int step = 5;

    timer = new Timer(delay, new ActionListener() {
        int x = startX;
        int y = startY;
        int tolerance = 1; 

        @Override
        public void actionPerformed(ActionEvent e) {
            if (x < endX) x += step;
            if (y < endY) y += step;

            if (x > endX) x -= step;  // Ajusta si pasa el destino
            if (y > endY) y -= step;

            cliente.setLocation(x, y);

            if (Math.abs(x - endX) <= tolerance && Math.abs(y - endY) <= tolerance) {
               
                cliente.setLocation(endX, endY);  // Asegura que el JLabel llegue exactamente a la posición final
                timer.stop();
            }
        }
    });

    timer.start();
}

    private void resetCliente(JLabel cliente) {
        int originalX = 960; // Posición X original (puedes ajustar según tu diseño)
        int originalY = 530; // Posición Y original (puedes ajustar según tu diseño)

        cliente.setLocation(originalX, originalY);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new vista().setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PARAR = new javax.swing.JButton();
        INICIAR = new javax.swing.JButton();
        client1 = new javax.swing.JLabel();
        client2 = new javax.swing.JLabel();
        client3 = new javax.swing.JLabel();
        client4 = new javax.swing.JLabel();
        client5 = new javax.swing.JLabel();
        IN = new javax.swing.JLabel();
        puer3 = new javax.swing.JLabel();
        puer = new javax.swing.JLabel();
        puer2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 245, 220));
        setName("Barberia"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1160, 720));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PARAR.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        PARAR.setText("PARAR");
        getContentPane().add(PARAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 150, 60));

        INICIAR.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        INICIAR.setText("INICIAR");
        getContentPane().add(INICIAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 150, 60));

        client1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N
        getContentPane().add(client1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 530, -1, -1));

        client2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N
        getContentPane().add(client2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 530, -1, -1));

        client3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N
        getContentPane().add(client3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 530, -1, -1));

        client4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N
        getContentPane().add(client4, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 530, -1, -1));

        client5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N
        getContentPane().add(client5, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 530, -1, -1));

        IN.setText("Inicio");
        getContentPane().add(IN, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 660, -1, -1));

        puer3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puerta}.jpg"))); // NOI18N
        getContentPane().add(puer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, -1, -1));

        puer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puerta}.jpg"))); // NOI18N
        getContentPane().add(puer, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, -1, -1));

        puer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puerta}.jpg"))); // NOI18N
        getContentPane().add(puer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, -1, -1));

        jLabel4.setText("Salida 2");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, -1, -1));

        jLabel5.setText("Salida 3");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 330, -1, -1));

        jLabel6.setText("Salida 1");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/diseno-interior-habitacion-minima-vacia-suelo-espina-pescado.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IN;
    private javax.swing.JButton INICIAR;
    private javax.swing.JButton PARAR;
    private javax.swing.JLabel client1;
    private javax.swing.JLabel client2;
    private javax.swing.JLabel client3;
    private javax.swing.JLabel client4;
    private javax.swing.JLabel client5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel puer;
    private javax.swing.JLabel puer2;
    private javax.swing.JLabel puer3;
    // End of variables declaration//GEN-END:variables
}
