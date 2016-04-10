import java.io.IOException;


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
		}

	}

}
