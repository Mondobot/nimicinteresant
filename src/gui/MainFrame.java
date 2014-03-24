/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Mar 13, 2014, 8:58:37 PM
 */
package gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import observer.IFileListener;
import observer.ITransferListener;
import observer.IUserListener;
import mediator.Mediator;
import model.File;
import model.Model;
import model.Transfer;
import model.User;

class ProgressRenderer extends DefaultTableCellRenderer {

    private final JProgressBar b = new JProgressBar(0, 100);

    public ProgressRenderer() {
        super();
        setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Integer i = (Integer) value;
        String text = "Completed";
        if (i < 0) {
            text = "Error";
        } else if (i < 100) {
            b.setValue(i);
            return b;
        }
        super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
        return this;
    }
}

public class MainFrame extends javax.swing.JFrame {
	private Controller controller;
	
    DefaultListModel<Object> usersModel = new DefaultListModel<>();
    DefaultListModel<Object> filesModel = new DefaultListModel<>();
    DefaultTableModel transfersModel = new DefaultTableModel();
    
    public MainFrame() {
        initComponents();
        designView();
        
        controller = new Controller(new Mediator());
        controller.registerMyUser("test");
        
        addListeners();    
        getListsFromServer();
    }
    
    private void getListsFromServer() {
    	controller.updateUsers();
    	controller.updateFiles();
    	controller.updateTransfers();
    }
    
    private void designView(){
    	setLocationRelativeTo(null);
    	setResizable(false);
    	setTitle("Tema 1");
        Toolkit tk = Toolkit.getDefaultToolkit();  
        int xSize = ((int) tk.getScreenSize().getWidth());  
        int ySize = ((int) tk.getScreenSize().getHeight());  
        setSize(xSize * 2 / 3, ySize * 2 / 3);  
        setLocationRelativeTo(null);
        
        jTable1.setPreferredScrollableViewportSize(jTable1.getPreferredSize());
        jTable1.setFillsViewportHeight(true);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new ProgressRenderer());
    }
    
    private void addListeners(){
    	Model.getInstance().addUserListener(new IUserListener() {
			
			@Override
			public void usersUpdated(List<User> users) {
				updateUsersList(users);				
			}
		});
        
        Model.getInstance().addFileListener(new IFileListener() {
			
			@Override
			public void filesUpdated(List<File> files) {
				updateFilesList(files);				
			}
		});
        
        Model.getInstance().addTransferListener(new ITransferListener() {
			
			@Override
			public void transfersUpdated(List<Transfer> transfers) {
				updateTransfersList(transfers);				
			}
		});
    }
    
    private void updateUsersList(List<User> users) {
    	usersModel.clear();
    	for (User user: users)
    		usersModel.addElement(user);    	
    }

    private void updateFilesList(List<File> files) {
    	filesModel.clear();
    	for (File file : files)
    		filesModel.addElement(file);
    }
    
    private void updateTransfersList(List<Transfer> transfers) {
    	transfersModel.setRowCount(0);
    	for (Transfer transfer : transfers) {
    		Object []row = new Object[5];
    		row[0] = transfer.getSource();
    		row[1] = transfer.getDest();
    		row[2] = transfer.getCargo();
    		row[3] = transfer.getProgress();
    		if (transfer.getProgress() < 100) {
    			if (transfer.getSource() == Model.getInstance().getMyUser())
    				row[4] = "Sending...";
    			else if (transfer.getDest() == Model.getInstance().getMyUser())
    				row[4] = "Receiving...";
    		}
    		else if (transfer.getProgress() == 100) 
    			row[4] = "Completed";    		
    		
    		transfersModel.addRow(row);
    	}
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane3.setBackground(new java.awt.Color(-1,true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Source", "Destination", "File name", "Progress", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(-1,true));
        jTable1.setName(""); // NOI18N
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane3.setViewportView(jTable1);

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>


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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}
