import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


public class Article 
{
	URI htmlDoc;
	String title;
	String imgPath;
	ArrayList<ArrayList<String>> chapters = new ArrayList<ArrayList<String>>();
	ArrayList<String> text = new ArrayList<String>();
	String[] tags;
	BufferedReader reader;

	public Article(String filePath)
	{
		String chapterSplitBy ="<CHAPTERSEPERATOR>";
		String csvSplitBy = ",";
		String line = "";
		ArrayList<String> tagsList;
		int lineCounter = 0;
		int chapterCounter = 0;
		
		try 
		{
			reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null)
			{
				System.out.println("ARTICLE: LineCounter: " + lineCounter);
				if (lineCounter == 0)
				{
				System.out.println("ARTICLE: BufferedReader: " + line); //TODO TestMethode
				tags = line.split(csvSplitBy);
				lineCounter++;
				}
				
				else if (lineCounter == 1)
				{
					System.out.println("ARTICLE: BufferedReader: " + line);
					title = line;
					lineCounter++;
				}
				
				else if (lineCounter == 2)
				{
					System.out.println("ARTICLE: BufferedReader: " + line);
					imgPath = line;
					lineCounter++;
				}
				else if (lineCounter == 3)
				{
					System.out.println("ARTICLE: BufferedReader: " + line);
					htmlDoc = URI.create(line);
					lineCounter++;
				}
				
				else if (lineCounter > 3)
				{
					if (line.equals(chapterSplitBy))
					{
						lineCounter++;
						this.chapters.add(text);
						System.out.println("ARTICLE: Chapter: adding new chapter, size: " + chapters.size());
						this.text = new ArrayList<String>();
						System.out.println("ARTICLE: BufferedReader: found Chapterseperator");
					}
					System.out.println("ARTICLE: BufferedReader: " + line);
					this.text.add(line);
					lineCounter++;
				}
			}
			
			
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("ARTICLE: creating with path: " + filePath + " and Title: " + title);
		testFile(); //TODO TestMethode
		testChapters();
		
	}
	
	private void testFile()
	{
		System.out.println("=====TESTFILE=====");
		for (int n = 0; n < tags.length; n++)
		{
			System.out.println("ARTICLE: tags: " + tags[n]);
		}
		System.out.println("ARTICLE: title: " + title);
		System.out.println("ARTICLE: imgPath: " + imgPath);
		System.out.println("ARTICLE: URI: " + htmlDoc);

		for (int n = 0; n < text.size(); n++)
		{
			System.out.println("ARTICLE: text: " + text.get(n));
		}
		System.out.println("==================");
	}
	
	private void testChapters()
	{
		System.out.println("=====TESTCHAPTERS=====");
		for (int n = 0; n < chapters.size(); n++)
		{
			for (int m = 1; m < chapters.get(n).size(); m++)
			{
				System.out.println("ARTICLE: chapters: " + chapters.get(n).get(m));
			}
			System.out.println("----------------------");
		}
		System.out.println("==================");
	}
}
