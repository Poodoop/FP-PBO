package chatApps;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ChatClient extends JFrame {

	private static final long serialVersionUID = 1L;
    
	private ObjectInputStream in;
    private ObjectOutputStream out;
    private String message="";
    private String serverIP;
    private Socket connection;
    private int port = 1011;
    
 public ChatClient(String s) {
        
        initComponents();
        
        this.setTitle("ClientChat");
        this.setVisible(true);
        jlStatus.setVisible(true);
        serverIP = s;
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jTextField1 = new JTextField();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        chatArea = new JTextArea();
        jLabel2 = new JLabel();
        jLabel1 = new JLabel();
        jlStatus = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new Color(0, 0, 51));
        jPanel1.setForeground(new Color(204, 204, 204));
        jPanel1.setLayout(null);

        jTextField1.setToolTipText("text\tType your message here...");
        jTextField1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 370, 410, 40);

        jButton1.setBackground(new Color(204, 204, 255));
        jButton1.setFont(new Font("Tahoma", 1, 11));
        jButton1.setText("Send");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(420, 370, 80, 40);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 80, 490, 280);

        jLabel2.setFont(new Font("Myriad Pro", 1, 48)); 
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("Client");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(140, 20, 180, 40);

        jlStatus.setForeground(new Color(255, 255, 255));
        jlStatus.setText("...");
        jPanel1.add(jlStatus);
        jlStatus.setBounds(10, 50, 300, 40);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 400);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 508, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
        );

        setSize(new Dimension(508, 441));
        setLocationRelativeTo(null);
    }

    private void jTextField1ActionPerformed(ActionEvent evt) {

        sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {

       sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }

    
    public void startRunning()
    {
       try
       {
            jlStatus.setText("Attempting Connection ...");
            try
            {
                connection = new Socket(InetAddress.getByName(serverIP),port);
            }catch(IOException ioEception)
            {
                    JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
            }
            jlStatus.setText("Connected to: " + connection.getInetAddress().getHostName());


            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            whileChatting();
       }
       catch(IOException ioException)
       {
            ioException.printStackTrace();
       }
    }
    
    private void whileChatting() throws IOException
    {
      jTextField1.setEditable(true);
      do{
              try
              {
                      message = (String) in.readObject();
                      chatArea.append("\n" + message);
              }
              catch(ClassNotFoundException classNotFoundException)
              {
              }
      }while(!message.equals("Client - END"));
    }
  
    
    private void sendMessage(String message)
    {
        try
        {
            
            chatArea.append("\nME(Client) - " + message);
            out.writeObject(                                                               message);
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
