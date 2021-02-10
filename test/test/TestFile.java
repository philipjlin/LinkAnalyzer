package test;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import linkanalyzer.*;

import static org.junit.Assert.assertEquals;

public class TestFile
{
    @BeforeClass
    public static void setup() throws RemoteException, NotBoundException, MalformedURLException
    {
        linkAnalyzer = (LinkAnalyzer) Naming.lookup(LinkAnalyzer.URL);
    }

    @Test
    public void testLinksByTime() throws RemoteException
    {
        final long START_TIME = 1259034000L;
        final long END_TIME = 1259036000L;
        Set<Link> actual = linkAnalyzer.linksByTime(START_TIME, END_TIME);
        Set<Link> expected = new HashSet<Link>();
        expected.add(expectedLink("http://web-solutions-uk.blogspot.com/2009/11/bespoke-web-site-development.html", 1259034471));
        expected.add(expectedLink("http://www.johnlees.com.au/pages/see-john-lees-live.php", 1259035289));
        expected.add(expectedLink("http://www.opensocial.org/profile/BetfairTrading", 1259034430));
        expected.add(expectedLink("http://www.quiz-creator.com/articles/make-quizzes-for-your-website.html", 1259035364));
        expected.add(expectedLink("http://www.theaterseatstore.com/Manchester-Collection?sc=10&category=184238", 1259034595));
        expected.add(expectedLink("http://www.theaterseatstore.com/Monterey-Collection?sc=10&category=184238", 1259035488));
        assertEquals(expected, actual);
    }

    @Test
    public void testLinksByURL() throws RemoteException
    {
        final String URL = "http://libraryden.com/";
        Set<Link> actual = linkAnalyzer.linksByURL(URL);
        Set<Link> expected = new HashSet<Link>();
        expected.add(expectedLink(URL, 1257263441L));
        expected.add(expectedLink(URL, 1257351466L));
        expected.add(expectedLink(URL, 1257351467L));
        expected.add(expectedLink(URL, 1257430907L));
        expected.add(expectedLink(URL, 1257947177L));
        expected.add(expectedLink(URL, 1257953649L));
        expected.add(expectedLink(URL, 1258456400L));
        assertEquals(expected, actual);
    }

    @Test
    public void testLinksByTags() throws RemoteException
    {
        final String[] TAGS = new String[]{"canada", "tax"};
        Set<Link> actual = linkAnalyzer.linksByTag(TAGS);
        Set<Link> expected = new HashSet<Link>();
        expected.add(expectedLink("http://terrywygal.com/?p=281", 1257155695));
        expected.add(expectedLink("http://terrywygal.com/?p=308", 1257155695));
        expected.add(expectedLink("http://terrywygal.com/?p=304", 1257155695));
        expected.add(expectedLink("http://terrywygal.com/?p=278", 1257155695));
        expected.add(expectedLink("http://terrywygal.com/?p=295", 1257155695));
        expected.add(expectedLink("http://terrywygal.com/?p=292", 1257155695));
        assertEquals(expected, actual);
    }

    private Link expectedLink(String url, long timestamp)
    {
        return new Link(url, timestamp, Collections.<String>emptyList());
    }

    private static LinkAnalyzer linkAnalyzer;
}
