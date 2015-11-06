

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

public class TestCases {

	@Test
	public void testGraphCreation() {
		TweetObj o1 = new TweetObj("abc", "Thu Oct 29 18:10:49 +0000 2015", null);
		TweetObj o2 = new TweetObj("abc", "Thu Oct 29 18:10:49 +0000 2015", null);
		TweetObj o3 = new TweetObj("abc", "Thu Oct 29 18:10:39 +0000 2015", null);
		HashMap<TweetObj, String> m = new HashMap<>();
		m.put(o1, "1");
		m.put(o2, "2");
		m.put(o3, "3");
		System.out.println(m);
		
		HashSet<TweetObj> s = new HashSet<>();
		s.add(o2);
		s.add(o1);
		s.add(o3);
		System.out.println(s);
		
	}
	
	@Test
	public void testAverageDegree(){
			TweetObj o1 = new TweetObj("#Spark Summit East this week! #Spark #Apache", "Thu Oct 29 17:51:01 +0000 2015");
			TweetObj o2 = new TweetObj("Just saw a great post on Insight Data Engineering #Apache #Hadoop #Storm", "Thu Oct 29 17:51:30 +0000 2015");
			TweetObj o3 = new TweetObj("Doing great work #Apache", "Thu Oct 29 17:51:55 +0000 2015");
			TweetObj o4 = new TweetObj("Excellent post on #Flink and #Spark", "Thu Oct 29 17:51:56 +0000 2015");
			TweetObj o5 = new TweetObj("New and improved #HBase connector for #Spark", "Thu Oct 29 17:51:59 +0000 2015");
			TweetObj o6 = new TweetObj("New 2.7.1 version update for #Hadoop #apaChe", "Thu Oct 29 17:52:05 +0000 2015");
			TweetObj o7 = new TweetObj("New 2.7.1 version update for #phsy #apaChe", "Thu Oct 29 17:53:05 +0000 2015");
			AverageDegree averageDegree= new AverageDegree();
			averageDegree.getAverageDegree(o1);
			averageDegree.getAverageDegree(o2);
			averageDegree.getAverageDegree(o3);
			averageDegree.getAverageDegree(o4);
			averageDegree.getAverageDegree(o5);
			averageDegree.getAverageDegree(o6);
			averageDegree.getAverageDegree(o7);
			System.out.println("Graph::"+ averageDegree.getHashTagsGraph());
			System.out.println("Degree sum: "+averageDegree.getDegreeSum());
			System.out.println("Number of tags:"+averageDegree.getNumberOfTags());
			System.out.println( "Average degree" + averageDegree.getFinalAverageDegree());
	}

}
