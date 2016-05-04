import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;


public class Server 
{
	ArrayList<Article> articleSearchIndex = new ArrayList<Article>();
	Session[] sessions;
	File articleFolder = new File("src/articles");
	File[] listOfFiles = articleFolder.listFiles();
	ServerSocket serverSocket;
	Socket socket;
	int port = 80;
	int voidVar;
	
	ServerUI ui;
	
	
	public Server()
	{
		this.indexArticles();
		createUI();
	}
	
	public void createUI()
	{
		ui = new ServerUI();
		ui.yellow();
		try 
		{
			ui.setAdressLbl(InetAddress.getLocalHost(), this.port);
		} 
		catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void runServer() throws IOException, URISyntaxException
	{

		
		while (true)
		{
			try 
			{
				socket = serverSocket.accept();
				ui.green();
				ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
				String command = (String) objectInput.readObject();
				System.out.println("SERVER: tick...");
				this.resolveCommand(command);
			} 
			
			catch (IOException e) 
			{

				e.printStackTrace();
			}
			catch (ClassNotFoundException e) 
			{
			
				e.printStackTrace();
			}
			
			finally
			{
				socket.close();
				ui.yellow();
			}
		}
	}
	public void createServerSocket()
	{
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("SERVER: Socket bound @Port " + port + "/ localAdress: " + InetAddress.getLocalHost());
		}
		
		catch (BindException be)
		{
			port++;
			System.out.println("SERVER: Port already in use trying Port " + port);
			createServerSocket();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	
	}
	
	public void resolveCommand(String command) throws IOException
	{
		if (command.equals("crashMe"))
		{
			System.out.println("CLIENT: command = " + command);
			this.crashServer();
		}
		else if (command.equals("ping"))
		{
			System.out.println("CLIENT: command = " + command);
			System.out.println("pong");
		}
		else if (command.equals("hello"))
		{
			System.out.println("CLIENT: command = " + command);
			System.out.println("Client says hello!");
			System.out.println("SERVER: attempting to answer...");
			serializeString("Server says hello back!");
			System.out.println("SERVER: said hello back!");
			
		}
		else
		{
			System.out.println("CLIENT: command = " + command);
			ArrayList<URI> foundUri = new ArrayList<URI>(this.searchText(command));
			for (int n = 0; n < foundUri.size(); n++)
			{
				System.out.println("SERVER: searchtext: URI's: " + foundUri.get(n));
				serializeURI(foundUri.get(n));
				
			}
			
		}

	}
	
	public void serializeString(String string)
	{
		try 
		{
			System.out.println("SERVER: serializeString: serialzing: " + string);
			try
			{
			socket.getOutputStream().flush();
			}
			catch (Exception e)
			{
				System.out.println("SERVER: can't flush, Socket is closed");
			}
			ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectOut.writeObject(string);
			objectOut.flush();
			//objectOut.close();
			socket.getOutputStream().flush();
			socket.getOutputStream().close();
			socket.close();
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void serializeURI(URI uri)
	{
		try 
		{
			System.out.println("SERVER: serializeURI: serialzing: " + uri);
			try
			{
			socket.getOutputStream().flush();
			}
			catch (Exception e)
			{
				System.out.println("SERVER: can't flush, Socket is closed");
			}
			ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectOut.writeObject(uri);
			objectOut.flush();
			//objectOut.close();
			socket.getOutputStream().flush();
			socket.getOutputStream().close();
			socket.close();
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void indexArticles()
	{
		for (int n= 0; n < listOfFiles.length; n++)
		{
			System.out.println("indexArticles: loop: " + n);
			String fileExtension = FilenameUtils.getExtension(listOfFiles[n].getName());
			if (fileExtension.equals("html"))
			{
				System.out.println("SERVER_FILEREADER " + listOfFiles[n].getName());
				System.out.println("SERVER_FILEREADER " + listOfFiles[n].getPath());
				Article bufferedArticle = new Article(listOfFiles[n].getPath());
				articleSearchIndex.add(bufferedArticle);
				
			}
			else
			{
				System.out.println("SERVER_FILEREADER " + "skipping file");
			}

		}
		for (int n= 0; n < articleSearchIndex.size(); n++)
		{
			System.out.println("SERVER_FILEREADER: created " + articleSearchIndex.get(n));
		}
	}
	
	public void searchTitle(String keyWord)
	{
		for (int n = 0; n < articleSearchIndex.size(); n++)
		{
			if (articleSearchIndex.get(n).title.equals(keyWord))
			{
				System.out.println("SERVER: searchTest: found: " + articleSearchIndex.get(n).title + " in Article: " + articleSearchIndex.get(n).htmlDoc);
			}

			else
			{

			}
		}

	}
	
	public void crashServer()
	{
		System.out.println(this.voidVar);
		System.exit(0);
	}
	
	public void searchTag(String keyWord)
	{
		for (int n = 0; n < articleSearchIndex.size(); n++)
		{
			for (int m = 0; m < articleSearchIndex.get(n).tags.length; m++)
			if (articleSearchIndex.get(n).tags[m].equals(keyWord))
			{
				System.out.println("SERVER: searchTest: found: " + articleSearchIndex.get(n).tags[m] + " in Article: " + articleSearchIndex.get(n).htmlDoc);
			}

			else
			{

			}
		}
	}
	
	public ArrayList<URI> searchText(String keyWord)
	{
		ArrayList<URI> uri = new ArrayList<URI>();
		for (int n = 0; n < articleSearchIndex.size(); n++)
		{
			for (int m = 0; m < articleSearchIndex.get(n).chapters.size(); m++ )
			{
				for (int o = 0; o < articleSearchIndex.get(n).chapters.get(m).size(); o++ )
				{
					if (articleSearchIndex.get(n).chapters.get(m).get(o).contains(keyWord))
					{
						System.out.println("SERVER: searchText: found: " + articleSearchIndex.get(n).chapters.get(m).get(o) + " in Article: " + articleSearchIndex.get(n).htmlDoc);
						System.out.println("SERVER: searchText: found: in chapter " + m);
						if (uri.contains(articleSearchIndex.get(n).htmlDoc))
						{
							
						}
						else
						{
							uri.add(articleSearchIndex.get(n).htmlDoc);
						}
						
					}
		
					else
					{

					}
					
					
				}
			}
		}
		return uri;
	}
	
	public void serializeURIList(ArrayList<URI> list)
	{
		try 
		{
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(os);
			objectOut.writeObject(list);
			objectOut.flush();
			objectOut.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
