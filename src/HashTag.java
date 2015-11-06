

import java.util.Date;
/**
 * 
 * @author shashank
 *	Object to store hash tag with its timestamp.
 *	Two HashTags are equal if the text is same
 */
public class HashTag extends TweetObj{

	
	public HashTag(String hashTag, String timeStamp) {
		setText(hashTag);
		setTimeStamp(timeStamp);
	}
	
	public HashTag(String hashTag, Date timeStamp) {
		setText(hashTag);
		setTimeStamp(timeStamp);
	}
	
	public void setText(String text) {
		this.text = text.trim().toLowerCase();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		
		if(obj==null || obj.getClass()!=this.getClass())
			return false;
		
		final TweetObj tObj = (TweetObj) obj;
		if ((tObj.getText()== null && this.getText()!=null) || ((tObj.getText()!= null && this.getText()==null)))
			return false;
		if(this.getText().equalsIgnoreCase(tObj.getText()) )
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
}
