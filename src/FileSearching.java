import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class FileSearching {
    
    public static void main(String[] args) {
        BlockingQueue<File> blockqueue = new ArrayBlockingQueue<File>(5);

        new Thread(new PathFinder(blockqueue, mainPath)).start();

        for (int i = 0; i < 50; i++)
            new Thread(new WordFinder(blockqueue, wordToFind)).start();
    }
    final static private File mainPath = new File(System.getProperty("user.dir"));
    final static private String wordToFind = new String("źródło");
}

class PathFinder implements Runnable {
    BlockingQueue<File> queue;
    File mainPath;
    public PathFinder(BlockingQueue<File> queue, File mainPath) {
        this.queue = queue;
        this.mainPath = mainPath;
    }
    public void run() {
        try
        {
            findPath(mainPath);
            queue.put(new File("empty"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void findPath(File path) throws InterruptedException {
        File[] paths = path.listFiles();

        for (int i = 0; i < paths.length; i++)
            if (paths[i].isDirectory())
                findPath(paths[i]);
            else
                queue.put(paths[i]);
    }
}
class WordFinder implements Runnable {
    BlockingQueue<File> queue;
    String wordToFind;

    public WordFinder(BlockingQueue<File> queue, String wordToFind)
    {
        this.queue = queue;
        this.wordToFind= wordToFind;
    }
    public void run()
    {
        boolean finished = false;
        while(!finished)
        {
            try
            {
                File searhedFile = queue.take();
                if (searhedFile.equals(new File("empty")))
                {
                    queue.put(searhedFile);
                    finished = true;
                }
                else
                    searchWord(searhedFile);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public void searchWord(File searhedFile) throws FileNotFoundException
    {
        Scanner reader = new Scanner(new BufferedReader(new FileReader(searhedFile)));

        int numberOfLine = 0;

        while (reader.hasNextLine())
        {
            numberOfLine++;

            if (reader.nextLine().contains(wordToFind))
                System.out.println("Searched word is ine the File: "+searhedFile.getPath()+" in line "+numberOfLine);
        }

        reader.close();
    }
}

