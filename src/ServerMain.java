import java.io.IOException;
import java.net.URISyntaxException;


public class ServerMain {

	public static void main(String[] args) 
	{
		Server server = new Server();
		//server.searchTitle("TestTitle");
		//server.searchTag("story");
		//server.searchText("Titan");
		server.createServerSocket();
		try {
			server.runServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
