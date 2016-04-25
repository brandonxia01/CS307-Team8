import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTester {
	public static void main(String[] args) {
		sendGetReq(1, 2, "ayushVijayjayjay");
		sendGetReq(1, 3, "ayushVijayjayjay");
		sendGetReq(1, 4, "ayushVijayjayjay");
		sendGetReq(1, 5, "ayushVijayjayjay");
		sendGetReq(1, 6, "ayushVijayjayjay");
		sendGetReq(1, 7, "ayushVijayjayjay");
		sendGetReq(1, 8, "1");

		sendGetReq(2, 1, "{'username':'ayeshEatsEggs'}");
		sendGetReq(2, 2, "{'username':'ayeshEatsEggs', 'gamemode':1, 'score':159357}");
		sendGetReq(2, 4, "{'followingName':'ayushVijayjayjay', 'followerName':'ayeshEatsEggs'}");
	}
	
	public static void sendGetReq(int type, int reqType, String arg) {
		try {
			Socket echoSocket = new Socket("localhost", 9000);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			out.write("" + type);
			out.flush();
			out.write("0" + reqType);
			out.flush();
			out.write(arg);
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println(in.readLine());
			echoSocket.close();
		} catch (Exception e) {}
		System.out.println();
	}
}
