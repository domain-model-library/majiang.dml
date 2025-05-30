/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dml.majiang.simulator.base.ui;

import dml.majiang.simulator.base.controller.Controller;

/**
 * @author DELL
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        Controller.setMainFrame(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerPanel = new javax.swing.JPanel();
        commandPanel = new CommandPanel();
        panPanel = new PanPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        containerPanel.setLayout(new java.awt.BorderLayout());
        containerPanel.add(commandPanel, java.awt.BorderLayout.PAGE_START);
        containerPanel.add(panPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(containerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private CommandPanel commandPanel;
    private javax.swing.JPanel containerPanel;
    private PanPanel panPanel;
    // End of variables declaration//GEN-END:variables
}
