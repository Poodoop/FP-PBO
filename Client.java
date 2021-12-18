package chatApps;

public class Client 
{

    public static void main(String[] args) 
    {
        ChatClient client=new ChatClient("127.0.0.1");
        client.startRunning();
    }
}
