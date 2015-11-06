

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author shashank
 *	Object to store tweet text, timestamp, flag to mark if unicode was present in tweet text and date format for timestamp
 *	Two tweet objects are equal if there text and timestamps match 
 */
public class TweetObj  {
	
	protected String text = null;
	protected Date timeStamp = null;
	private boolean hasInvalidUnicode = false;
	//Example date: Thu Oct 29 18:10:49 +0000 2015
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss +SSSS yyyy"); 
	
	/**
	 * Empty constructor
	 */
	public TweetObj() {
		
	}

	
	/**
	 * 
	 * @param text
	 * @param timeStamp
	 * @param dateFormat - Specify date format for the timestamp, 
	 * by default it is set too EEE MMM dd HH:mm:ss +SSSS yyyy  - e.g.Thu Oct 29 18:10:49 +0000 2015 
	 * 
	 */
	public TweetObj(String tweetText, String timeStamp, SimpleDateFormat dateFormat) {
		setText(tweetText);
		setTimeStamp(timeStamp);
		if (dateFormat!=null)
			setDateFormat(dateFormat); 
	}
	
	/**
	 * 
	 * @param tweetText
	 * @param timeStamp
	 */
	public TweetObj(String tweetText, String timeStamp) {
		setText(tweetText);
		setTimeStamp(timeStamp);
	}
	
	/**
	 * 
	 * @param tweetText
	 * @param timeStamp
	 */
	public TweetObj(String tweetText, Date timeStamp) {
		setText(tweetText);
		setTimeStamp(timeStamp);
	}

	@Override
	public String toString() {
		return text + " (timestamp: "+ timeStamp+ ")" ;
	}

	/**
	 * parse String timeStamp using SimpleDateFormat dateFormat
	 * @param dateFormat
	 * @param timeStamp
	 * @return Date
	 */
	public static Date generateDate(SimpleDateFormat dateFormat, String timeStamp){
		Date date = null;
		try {
			if(timeStamp==null || timeStamp.length()==0)
				System.out.println("Blank timestamp recieved");
			date = dateFormat.parse(timeStamp);
		} catch (ParseException e) {
			System.out.println("Error in parsing date:"+ timeStamp+ "\n Error message: \n" +e.getMessage());
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		final TweetObj tObj = (TweetObj) obj;
		if ((tObj.getText()== null && this.getText()!=null) || ((tObj.getText()!= null && this.getText()==null)))
			return false;
		if((this.getTimeStamp().compareTo(tObj.getTimeStamp())==0) && this.getText().equalsIgnoreCase(tObj.getText()) )
			return true;
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		 final int prime = 31;
	        int result = 1;
	        result = prime * result
	                + ((getText()== null) ? 0 : getText().hashCode());
	        return result;
	}
	
	public void setTimeStamp(String timestamp) {
		this.timeStamp = generateDate(this.dateFormat, timestamp);
	}
	
	public void setText(String tweetText) {
		this.text = tweetText;
	}
	
	public String getText() {
		return text;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean hasInvalidUnicode() {
		return hasInvalidUnicode;
	}

	public void setHasInvalidUnicode(boolean hasInvalidUnicode) {
		this.hasInvalidUnicode = hasInvalidUnicode;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
