

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainRun {

	public static void main(String[] args) {
		
		//** Initialize file paths
		File inputFile = null;
		File cleanedTweetsFile =null;
		File averageDegreeFile = null;
		String OS = System.getProperty("os.name");
		if (OS.startsWith("Windows"))
		{
			//System.out.println("Windows");
		File[] roots = File.listRoots();
		inputFile = new File(roots[0],"tweet_input/tweets.txt");
		File outputDir = new File(roots[0],"tweet_output");
		if(!outputDir.exists())
		{
			outputDir.mkdir();
		}
		cleanedTweetsFile = new File(outputDir,"ft1.txt");
		averageDegreeFile = new File(outputDir,"ft2.txt");
		}
		else
		{
			File root = (new File(System.getProperty("user.dir")));
			inputFile = new File(root,"tweet_input/tweets.txt");
			File outputDir = new File(root,"tweet_output");
			if(!outputDir.exists())
			{
				outputDir.mkdir();
			}
			cleanedTweetsFile = new File(outputDir,"ft1.txt");
			averageDegreeFile = new File(outputDir,"ft2.txt");
		}
		
		/*File inputFile = new File(roots[0],"tweet_input/tweets2.txt");		//Smaller test file
		File cleanedTweetsFile = new File(roots[0],"tweet_output/ft1_2.txt");
		File averageDegreeFile = new File(roots[0],"tweet_output/ft2_2.txt");
*/
		System.out.println("Processing input file  - " + inputFile.getAbsolutePath());
		System.out.println("Processing started");
		
		/**
		 * Initializing some variables
		 */
		int countTweetsWithNotAllowedUnicode=0;		//maintains count of tweets with unicode that is not Basic LATIN
		String line=null;
		TweetObj tweetObj = null;
		int lineCount =0;
		long time = System.currentTimeMillis();
		AverageDegree averageDegree = new AverageDegree();
		
		try {
			//**Initialize reader and writers
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writeCleanTweets = new BufferedWriter(new FileWriter(cleanedTweetsFile));
            BufferedWriter writeAverageDegree = new BufferedWriter(new FileWriter(averageDegreeFile));
            
			while((line = reader.readLine()) != null) {
            	lineCount++;
            		//System.out.println(line);
            	
            	/**
            	 * Run Feature 1: Clean tweet JSON
            	 */
                tweetObj = TweetsCleaned.cleanTweet(line);
                //** if JSON was invalid i.e. did not have the fields "created_at" or "text" then tweetObj is null 
                if(tweetObj==null)
                {
                    	//writeCleanTweets.append("Invalid tweet :: "+ line +"\r\n");
                    	//invalidTweet++;
                	continue;
                }
                //** keep count of tweets with invalid unicode
                if (tweetObj.hasInvalidUnicode()) 
                	countTweetsWithNotAllowedUnicode++;
               /**
                * Run Feature 2: Calculate Average degree for each valid tweet
                */
                String degree = averageDegree.getAverageDegree(tweetObj) ;
                //String degree = averageDegree.getAverageDegree(line) ;	//** Can also generate average degree 
                															//using the line that will parse JSON text 
                
                //**Write clean tweet and degree in respective files
                writeCleanTweets.append(tweetObj.toString()+"\r\n");
                writeAverageDegree.append(degree + "\r\n");
                //**Flush writers if 50 lines have been read
                if(lineCount%50==0)
                {
                	writeCleanTweets.flush();
                	writeAverageDegree.flush();
                }
            }   
			
			//** Append number of tweets with unicode count
			writeCleanTweets.append("\r\nNumber of tweets with unicode: "+ countTweetsWithNotAllowedUnicode);
				//System.out.println("Number of tweets with unicode: "+ countTweetsWithNotAllowedunicode);
				//System.out.println("Invalid tweets: "+ invalidTweet);

			//**  close files.
			writeCleanTweets.close();
			writeAverageDegree.close();
			reader.close(); 
			System.out.println("The tweet file has been processed sucessfully!");
			System.out.println("Time taken:" + (System.currentTimeMillis()-time));
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + inputFile + "'");
            System.out.println(ex.getMessage());
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" + inputFile + "'");   
            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }


	}
}
