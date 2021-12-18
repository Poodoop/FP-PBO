package chatApps;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ChatServer extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
    private ObjectInputStream in; 
    private ObjectOutputStream out; 
    private Socket connection; 
    private ServerSocket server;
    private int clients = 100;
    private int port = 1011;
    
 public ChatServer() {
        
        initComponents();
        this.setTitle("Server");
        this.setVisible(true);
        jlStatus.setVisible(true);
    }
 
 public void startRunning()
 {
     try
     {
         server = new ServerSocket(port, clients);
         while(true)
         {
             try
             {
                 jlStatus.setText(" Waiting for Someone to Connect...");
                 connection = server.accept();
                 jlStatus.setText(" Now Connected to "+connection.getInetAddress().getHostName());


                 out = new ObjectOutputStream(connection.getOutputStream());
                 out.flush();
                 in = new ObjectInputStream(connection.getInputStream());

                 whileChatting();

             }catch(EOFException eofException)
             {
		     
             }
         }
     }
     catch(IOException ioException)
     {
             ioException.printStackTrace();
     }
 }
 
 private void whileChatting() throws IOException
 {
      String message="";    
      jTextField1.setEditable(true);
      do{
              try
              {
                      message = (String) in.readObject();
                      chatArea.append("\n"+message);
              }catch(ClassNotFoundException classNotFoundException)
              {
                      
              }
      }while(!message.equals("Client - END"));
 }
 
 private void initComponents() {

     jPanel1 = new JPanel();
     jScrollPane1 = new JScrollPane();
     chatArea = new JTextArea();
     jTextField1 = new JTextField();
     jButton1 = new JButton();
     jlStatus = new JLabel();
     jLabel2 = new JLabel();
     jLabel1 = new JLabel();

     setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     setBackground(new Color(51, 255, 204));
     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     setForeground(new Color(102, 255, 204));

     jPanel1.setBackground(new Color(154, 205, 49));
     jPanel1.setLayout(null);

     chatArea.setColumns(20);
     chatArea.setRows(5);
     jScrollPane1.setViewportView(chatArea);

     jPanel1.add(jScrollPane1);
     jScrollPane1.setBounds(10, 90, 480, 250);

     jTextField1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
             jTextField1ActionPerformed(evt);
         }
     });
     jPanel1.add(jTextField1);
     jTextField1.setBounds(10, 350, 400, 40);

     jButton1.setBackground(new Color(216, 191, 216));
     jButton1.setFont(new Font("Arial Black", 1, 12));
     jButton1.setText("Send");
     jButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
     jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
             jButton1ActionPerformed(evt);
         }
     });
     jPanel1.add(jButton1);
     jButton1.setBounds(410, 350, 80, 40);

     jlStatus.setForeground(new Color(255, 255, 255));
     jlStatus.setText("...");
     jPanel1.add(jlStatus);
     jlStatus.setBounds(10, 60, 300, 40);

     jLabel2.setFont(new Font("Myriad Pro", 1, 48)); 
     jLabel2.setForeground(new Color(51, 0, 51));
     jLabel2.setText("Server");
     jLabel2.setToolTipText("");
     jLabel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
     jPanel1.add(jLabel2);
     jLabel2.setBounds(80, 10, 190, 60);

     jLabel1.setBackground(new Color(153, 255, 204));
     jPanel1.add(jLabel1);
     jLabel1.setBounds(0, 35, 460, 410);

     GroupLayout layout = new GroupLayout(getContentPane());
     getContentPane().setLayout(layout);
     layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
     );
     layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
             .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
             .addGap(0, 0, Short.MAX_VALUE))
     );

     setSize(new Dimension(500, 427));
     setLocationRelativeTo(null);
 }
 
 private void jButton1ActionPerformed(ActionEvent evt) {
     
     sendMessage(jTextField1.getText());
	jTextField1.setText("");
 }
 private void jTextField1ActionPerformed(ActionEvent evt) {
	 
     sendMessage(jTextField1.getText());
	jTextField1.setText("");
 }
 
	private void sendMessage(String message)
    {
        try
        {
            
            chatArea.append("\nME : "+ message);            
            out.writeObject("(server) : " + message);
            out.flush();
        }
        catch(IOException ioException)
        {
            chatArea.append("\n Unable to Send Message");
        }
    }
    private JTextArea chatArea;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextField jTextField1;
    private JLabel jlStatus;	
}
