package br.com.schumaker.jchip8.ui;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_HEIGHT;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_WIDTH;
import br.com.schumaker.jchip8.core.Chip8;
import br.com.schumaker.jchip8.core.Chip8Registers;
import br.com.schumaker.jchip8.io.MainLoop;
import br.com.schumaker.jchip8.io.Chip8FileSystem;
import javax.swing.JFileChooser;

/**
 *
 * @author hudson schumaker
 */
public class FrMain extends javax.swing.JFrame {

    private final Chip8 chip8 = new Chip8();
    private MainLoop loop = new MainLoop(chip8);
    private Chip8FileSystem fileSystem = new Chip8FileSystem();

    public FrMain() {
        // setResizable(false);
        initComponents();
        setAndLoad();
    }

    private void setAndLoad() {
        this.setIgnoreRepaint(true);
        this.setLocationRelativeTo(null);
        this.add(loop);
        this.loop.start();

        //requestFocus();
        //draw.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        miLoadROM = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        mTools = new javax.swing.JMenu();
        miMemoryDump = new javax.swing.JMenuItem();
        imRegistersDump = new javax.swing.JMenuItem();
        miScreenDump = new javax.swing.JMenuItem();
        imStackDump = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JChip8 - Emulator");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        mFile.setText("File");

        miLoadROM.setText("Load ROM");
        miLoadROM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLoadROMActionPerformed(evt);
            }
        });
        mFile.add(miLoadROM);

        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        mFile.add(miExit);

        menuBar.add(mFile);

        mTools.setText("Tools");

        miMemoryDump.setText("Memory Dump");
        miMemoryDump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMemoryDumpActionPerformed(evt);
            }
        });
        mTools.add(miMemoryDump);

        imRegistersDump.setText("Registers Dump");
        imRegistersDump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imRegistersDumpActionPerformed(evt);
            }
        });
        mTools.add(imRegistersDump);

        miScreenDump.setText("Screen Dump");
        miScreenDump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miScreenDumpActionPerformed(evt);
            }
        });
        mTools.add(miScreenDump);

        imStackDump.setText("Stack Dump");
        imStackDump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imStackDumpActionPerformed(evt);
            }
        });
        mTools.add(imStackDump);

        menuBar.add(mTools);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        char charKey = evt.getKeyChar();
        int vkey = chip8.getKeyboard().map(charKey);

        if (chip8.isWaitingForKey()) {
            System.out.println("CPU is wainting");
            if (vkey != -1) {
                chip8.setBuffer(charKey);
            } else {
                chip8.setBuffer((char) -1);
            }
        }

        if (vkey != -1) {
            chip8.getKeyboard().setDown(vkey);
        }

        System.out.println("Charkey: " + charKey);
        System.out.println("Vkey: " + vkey);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        char charKey = evt.getKeyChar();
        int vkey = chip8.getKeyboard().map(charKey);
        if (vkey != -1) {
            chip8.getKeyboard().setUp(vkey);
        }
    }//GEN-LAST:event_formKeyReleased

    private void miLoadROMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLoadROMActionPerformed

        //  JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        JFileChooser chooser = new JFileChooser("C:\\Project\\8-bit\\Chip8\\roms");
//        FileFilter filter = new FileNameExtensionFilter("rom", "rom");
//        chooser.setFileFilter(filter);

        chooser.setAcceptAllFileFilterUsed(true);//support all files
        chooser.setDialogTitle("Abrir arquivo");
        chooser.setApproveButtonText("Abrir");
        int sf = chooser.showOpenDialog(null);
        boolean res = false;
        if (sf == JFileChooser.APPROVE_OPTION) {
            res = chip8.getMemory().loadRomToRam(
                    fileSystem.readFromDisk(
                            chooser.getSelectedFile().getAbsolutePath()));
        }

        if (res) {
            loop.setIsRunning(res);
        }
    }//GEN-LAST:event_miLoadROMActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_miExitActionPerformed

    private void miMemoryDumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMemoryDumpActionPerformed
        System.out.println("Memory dump:");
        //4036 because is reading 40 at a time
        for (int k = 0; k < 4036; k = k + 40) { //
            for (int i = k; i < k + 40; i++) {
                System.out.print(String.format("0x%08X", i));
                //System.out.print(chip8.getMemory().getMemory(i));
                System.out.print(", ");
            }
            System.out.println("");
        }
    }//GEN-LAST:event_miMemoryDumpActionPerformed

    private void imRegistersDumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imRegistersDumpActionPerformed
        System.out.println("Registers dump:");
        System.out.print("V= ");
        for (int k = 0; k < Chip8Registers.getInstance().getV().length; k++) {
            System.out.print(Chip8Registers.getInstance().getV()[k]);
            System.out.print(", ");
        }
        System.out.println("");
        System.out.println("I= " + Chip8Registers.getInstance().getI());
        System.out.println("DT= " + Chip8Registers.getInstance().getDT());
        System.out.println("ST= " + Chip8Registers.getInstance().getST());
        System.out.println("PC= " + Chip8Registers.getInstance().getPC());
        System.out.println("SP= " + Chip8Registers.getInstance().getSP());
    }//GEN-LAST:event_imRegistersDumpActionPerformed

    private void miScreenDumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miScreenDumpActionPerformed
        System.out.println("Screen dump:");
        for (int x = 0; x < CHIP8_HEIGHT; x++) {
            for (int y = 0; y < CHIP8_WIDTH; y++) {
                boolean d = chip8.getScreen().getPixels()[x][y];
                System.out.print(d);
                System.out.print(", ");
            }
            System.out.println("");
        }
    }//GEN-LAST:event_miScreenDumpActionPerformed

    private void imStackDumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imStackDumpActionPerformed
        System.out.println("Stack dump:");
        for (int k = 0; k < chip8.getStack().getStack().length; k++) {
            System.out.print("" + k + ": ");
            System.out.println("" + chip8.getStack().getStack()[k]);
        }
    }//GEN-LAST:event_imStackDumpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem imRegistersDump;
    private javax.swing.JMenuItem imStackDump;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mTools;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miLoadROM;
    private javax.swing.JMenuItem miMemoryDump;
    private javax.swing.JMenuItem miScreenDump;
    // End of variables declaration//GEN-END:variables
}
