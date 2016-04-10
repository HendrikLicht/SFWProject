import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.apache.commons.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;


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
	
	
	public Server()
	{
		this.indexArticles();
	}
	
	public void runServer() throws IOException
	{

		
		while (true)
		{
			try 
			{
				socket = serverSocket.accept();
				ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
				//System.out.println(socket.getInputStream().read());
				String command = (String) objectInput.readObject();
				//System.out.println(objectInput.readObject());
				System.out.println("SERVER: tick...");
				this.resolveCommand(command);
				//serverSocket.close(); //TODO diese u nd folgende zeile entfernen dafür servercommand auflösungsmethode einfügen
				//createServerSocket(); //
			} 
			
			catch (IOException e) 
			{

				e.printStackTrace();
			}
			catch (ClassNotFoundException e) 
			{
			
				e.printStackTrace();
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
	
	public void resolveCommand(String command)
	{
		if (command.equals("crashMe"))
		{
			this.crashServer();
		}
		else if (command.equals("ping"))
		{
			System.out.println("pong");
		}
		else
		{
			this.searchText(command);
		}
	}
	
	public void indexArticles()
	{
		for (int n= 0; n < listOfFiles.length; n++)
		{
			System.out.println("indexArticles: loop: " + n);
			String fileExtension = FilenameUtils.getExtension(listOfFiles[n].getName());
			//System.out.println(fileExtension);	//TODO TestMethode
			if (fileExtension.equals("html"))
			{
				System.out.println("SERVER_FILEREADER " + listOfFiles[n].getName());
				System.out.println("SERVER_FILEREADER " + listOfFiles[n].getPath());
				Article bufferedArticle = new Article(listOfFiles[n].getPath()); //TODO change test for relative ArticleNAme
				articleSearchIndex.add(bufferedArticle);
				//System.out.println(articleSearchIndex);	//TODO TestMethode
				
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
				//System.out.println("SERVER: searchTest: found nothing");
			}
		}

	}
	
	public void crashServer()
	{
		System.out.println(this.voidVar);
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
				//System.out.println("SERVER: searchTest: found nothing");
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
						uri.add(articleSearchIndex.get(n).htmlDoc);//TODO
					}
		
					else
					{
						//System.out.println("SERVER: searchTest: found: nothing");
					}
					
					
				}
			}
		}
		return uri;
	}

}
