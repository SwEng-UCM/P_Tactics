package P.Tactics.Multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable { //done with the help of chatGPT

    private BufferedReader in;
    private PrintWriter out;
    private BlockingQueue<GameMessage> messageQueue;

    public ClientHandler(BufferedReader in, PrintWriter out, BlockingQueue<GameMessage> queue) throws IOException {
        this.in = in;
        this.out = out;
        messageQueue = queue;
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
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            try {
				messageQueue.put(new GameMessage(this, "disconnected")); 
			} catch (InterruptedException e1) {//goofy ah
				e1.printStackTrace();
			}
        }
		
	}
	public void disconnect() {
		try {
			messageQueue.put(new GameMessage(this, "disconnected"));
			in.close();
			out.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
