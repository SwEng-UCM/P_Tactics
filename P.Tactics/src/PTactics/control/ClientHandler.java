package PTactics.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable { //done with the help of chatGPT

	Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BlockingQueue<GameMessage> messageQueue;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void sendMessage(String msg) {
        out.println(msg);
    }
    
	@Override
	public void run() {
		try {
            String input;
            while ((input = in.readLine()) != null) {
                messageQueue.put(new GameMessage(this, input));
            }
            messageQueue.put(new GameMessage(this, "disconnected"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	

}
