

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author shashank
 *	Class to obtain clean tweet text and timestamp
 */
public class TweetsCleaned {
	
	/**
	 * @param testString
	 * @return True - if test string contains only ASCII Unicode characters in the "Basic Latin" category (ranging from 0x0000-0x007F) 
	 * 	otherwise return False
	 */
	public static boolean containsAllowedUnicode(String testString)
	{
		return testString.matches("\\p{ASCII}+");
	}
	
	/**
	 * Parse JSON to tweetObj. Clean text to remove unicode and replace line break and tab characters with spaces
	 * @param jsonTweet
	 * @return TweetObj containing timestamp and tweet 
	 */
	public static TweetObj cleanTweet(String jsonTweet)
	{ 
		JSONObject tweetJson  = new JSONObject(jsonTweet);		//TODO: Add dependency of org.json 20140107 in README
		String timestamp = null;
		String tweetText = null;
		//**Parse JSON to obtain timestamp and tweet text
		try 
		{
			timestamp = tweetJson.getString("created_at");
			tweetText = tweetJson.getString("text");
		} catch (JSONException e) {
			System.out.println("Error in parsing JSON: " + e.getMessage());
			return null;
		}
		TweetObj tweetObj = new TweetObj(tweetText, timestamp);
		//**Clean tweet text 
		tweetObj= cleanTweetText(tweetObj);
			//tweetOutput = tweetText + " (timestamp: "+ timestamp+ ")\n";
		return tweetObj;
		
	}
	
	/**
	 * Remove unicode characters; replace tab, line break characters with space
	 * @param tweetObj
	 * @return TweetObj with cleaned tweet text.  
	 * 
	 */
	public static TweetObj cleanTweetText(TweetObj tweetObj)
	{
		String cleanTweet = tweetObj.getText();
		if (!cleanTweet.matches("\\p{ASCII}+"))		//**find if there is a character in tweet text that is not a Basic Latin character
		{
				//System.out.println("match::::: " + tweetObj);
			//**Set flag if tweet contains an invalid unicode
			tweetObj.setHasInvalidUnicode(true);				
			cleanTweet = cleanTweet.replaceAll("[^\\p{ASCII}]", "");
		}
		cleanTweet = cleanTweet.replaceAll("\\t", " ");
		cleanTweet = cleanTweet.replaceAll("\\n", " ");
		cleanTweet = cleanTweet.replaceAll("\\r", " ");
			//System.out.println(cleanTweet);
		tweetObj.setText(cleanTweet);
		return tweetObj;
	}

}
