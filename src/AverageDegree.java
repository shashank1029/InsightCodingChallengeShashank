

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author shashank
 *Class to calculate Average degree of connectivity of a graph of hash tags in given tweets
 */
public class AverageDegree {
	
	public static final String hashTagRegex = "\\S*#(?:\\[[^\\]]+\\]|\\S+)";
	public static final Pattern hashTagPattern = Pattern.compile(hashTagRegex);
	public static final long tweetsTimeInterval = 60*1000;
	
	private HashMap<String, HashSet<HashTag>> hashTagsGraph = new HashMap<>(); //**This is the graph
	private double degreeSum =0;
	private double finalAverageDegree = 0.00;
	private double numberOfTags=0;
	//private Date latestTweetDate = null;
	private Date oldestAllowableTweetDate = null;
	
	public AverageDegree() {
	}
	
	/**
	 * Parses JSON and cleans tweet and then calculates average degree
	 * @param tweetJSON
	 * @return Average degree in String format with 2 decimal places 
	 *   
	 */
	public String getAverageDegree(String tweetJSON){
		return getAverageDegree(TweetsCleaned.cleanTweet(tweetJSON));
	}
	
	/**
	 * Accepts tweet and  calculates average degree
	 * @param tweet - tweet object containing tweet text and timestamp
	 * @return Average degree in String format with 2 decimal places
	 *   
	 */
	public String getAverageDegree(TweetObj tweet)
	{
		HashSet<HashTag> hashTagsInTweet = new HashSet<>();
		//**Clean tweet text to remove unicode characters
		tweet = TweetsCleaned.cleanTweetText(tweet);	
		
		//**if the tweet is invalid i.e doesn't have any text or timestamp,return old average degree
		if(tweet.getText()==null || tweet.getTimeStamp()==null) 		
			return getFinalAverageDegree();				
		
		//**remove hashtag edges in graph which are older than a time limit from the latest valid tweet
		if(oldestAllowableTweetDate==null )	
		{
			oldestAllowableTweetDate = tweet.getTimeStamp();
			//latestTweetDate = tweet.getTimeStamp();
		}
		else if(/*tweet.getTimeStamp().after(latestTweetDate) && */(tweet.getTimeStamp().getTime() - oldestAllowableTweetDate.getTime() > tweetsTimeInterval))
		{
			//latestTweetDate = tweet.getTimeStamp();
			oldestAllowableTweetDate = new Date(tweet.getTimeStamp().getTime()-tweetsTimeInterval);
			removeOldTweetEdges(tweet.getTimeStamp());
		}
		
		//**Search for hashtags in given tweet
		Matcher matcher = hashTagPattern.matcher(tweet.getText());		
		while (matcher.find())
		{
				//HashTag newHashTag = null;
				//System.out.println(m.group(0));
				//newHashTag = new HashTag(matcher.group(0), tweet.getTimeStamp());
			hashTagsInTweet.add(new HashTag(matcher.group(0), tweet.getTimeStamp()));		
				//System.out.println(newHashTag.getText());
		}
		
		//**If atleast 2 hashtags were found in tweet, then add them to the graph
		if(hashTagsInTweet.size()>1)
		{
			//**add each hashtag pair to the graph
			for(HashTag keyHashTag: hashTagsInTweet)
			{
				HashSet<HashTag> linkedTags = null;
				if((linkedTags = hashTagsGraph.get(keyHashTag.getText()))==null)
				{
					linkedTags = new LinkedHashSet<>();
				}
				//** Add all other hashtags as edges for each hashtag
				for(HashTag hashTag:hashTagsInTweet)
				{
					if(hashTag!=keyHashTag)
					{
						if(linkedTags.add(hashTag))
						{
							degreeSum++;	//**Increment degree count as number of edges are added
								//System.out.println("Edge added::::"+", sum:"+degreeSum+" key:" + keyHashTag.getText()+ "::edge::"+hashTag.getText() + "  "+ hashTag.getTimeStamp());
						}
					}
				}
				//**Building graph
				hashTagsGraph.put(keyHashTag.getText(), linkedTags);
			}
		}
		
		//**Calculate average degree 
		numberOfTags= hashTagsGraph.size();
		finalAverageDegree = degreeSum/numberOfTags;		
			//System.out.println("Graph::"+ hashTagsGraph);
			//System.out.println("Degree sum: "+degreeSum);
			//System.out.println("Number of tags:"+numberOfTags);
			//System.out.println("Avg degree: "+finalAverageDegree);
		return getFinalAverageDegree();
	}
	
	/**
	 * Converts average degree from long to string; rounds it to 2 decimal places
	 * @return Average degree 
	 */
	public String getFinalAverageDegree()
	{
		if(finalAverageDegree>0)
			return String.format("%.2f", finalAverageDegree);
		else
			return "0.00";
	}
	
	/**
	 * Removes tweets from graph that are older than a certain tile limit from the latest graph
	 * @param latestTweetDate
	 */
	private void removeOldTweetEdges(Date latestTweetDate)
	{
		//**Create copy of graph entries
		Set<Entry<String,HashSet<HashTag>>> hashtTagEntries =  new HashSet<>();
		hashtTagEntries.addAll(hashTagsGraph.entrySet());
		boolean wereEdgesRemoved = false;
		
		//**Iterate each edge in graph
		for(Entry<String, HashSet<HashTag>> record: hashtTagEntries)
		{
			//**Make a copy of all edges of a tag
			HashSet<HashTag> edgeTags = new HashSet<>();
			edgeTags.addAll(record.getValue());
			//**Iterate all edges for a hashtag
			for (HashTag edgeTag:record.getValue())
			{
				//**If time difference between edge and latest time is greater than limit, remove from edge copy
				if(latestTweetDate.getTime() - edgeTag.getTimeStamp().getTime() > tweetsTimeInterval)
				{	wereEdgesRemoved = true;
					edgeTags.remove(edgeTag);
					degreeSum--;
						//System.out.println("Edge removed::::"+", sum:"+degreeSum+" key:" + record.getKey()+ "::edge::"+edgeTag.getText() + "  "+ edgeTag.getTimeStamp());
				}
			}
			if(wereEdgesRemoved){
				if(edgeTags.isEmpty())
					hashTagsGraph.remove(record.getKey());
				else if(wereEdgesRemoved){
					hashTagsGraph.put(record.getKey(), edgeTags);
					//System.out.println("Edge removed - " +hashTagsGraph);
				}
				wereEdgesRemoved=false;
			}
		}
		//hashTagsGraph=hashTagsGraphCopy;
	}

	public HashMap<String, HashSet<HashTag>> getHashTagsGraph() {
		return hashTagsGraph;
	}

	public double getDegreeSum() {
		return degreeSum;
	}

	public double getNumberOfTags() {
		return numberOfTags;
	}

	/*public Date getLatestTweetDate() {
		return latestTweetDate;
	}
	*/
}
