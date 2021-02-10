package linkanalyzer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.Naming; 
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class LinkAnalyzerImpl extends UnicastRemoteObject implements LinkAnalyzer{
	
	/**
	 * List of LinkAnalyzerNodes registered to the LinkAnalyzer object.
	 */
	private List<LinkAnalyzerNode> nodes = new ArrayList<LinkAnalyzerNode>();
	
	/**
	 * Constructor
	 * @throws RemoteException
	 */
	public LinkAnalyzerImpl() throws RemoteException{

		super();
	}

	/**
     * Return the Links whose timestamp is between startTime and endTime.
     * @param startTime Minimum timestamp to be returned.
     * @param endTime Maximum timestamp to be returned.
     * @return Links whose timestamp is between startTime and endTime.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByTime(long startTime, long endTime) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		
		for( LinkAnalyzerNode node : nodes ){
			
			Set<Link> setToAdd = node.linksByTime(startTime, endTime);
			
			for( Link link : setToAdd ){
				
				linksToReturn.add(link);
			}
		}
		
		return linksToReturn;
	}

	/**
     * Return the Links with a given URL.
     * @param url URL to search for.
     * @return Links with the given URL.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByURL(String url) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		
		for( LinkAnalyzerNode node : nodes ){
			
			Set<Link> setToAdd = node.linksByURL(url);
			
			for( Link link : setToAdd ){
				
				linksToReturn.add(link);
			}
		}
		
		return linksToReturn;
	}

	/**
     * Return the Links containing all of the given tags.
     * @param tags Set of tags of interest.
     * @return Links containing all of the given tags.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByTag(String... tags) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		
		for( LinkAnalyzerNode node : nodes ){
			
			Set<Link> setToAdd = node.linksByTag(tags);
			
			for( Link link : setToAdd ){
				
				linksToReturn.add(link);
			}
		}
		
		return linksToReturn;
	}

	/**
     * Register the given LinkAnalyzerNode as a node for use by this LinkAnalyzer.
     * @param node A node to be used by this LinkAnalyzer.
     * @throws RemoteException
     */
	@Override
	public void registerNode(LinkAnalyzerNode node) throws RemoteException{
		
		nodes.add(node);
	}
	
	/**
	 * Main Method
	 * Creates a LinkAnalyzerImpl object and registers it with the RMI registry with
	 * a specified URL name.
	 */
	public static void main(String[] args){
		
		try {
			
			LinkAnalyzerImpl la = new LinkAnalyzerImpl();
			
			Naming.rebind(LinkAnalyzer.URL, la);
			System.out.println("Naming rebind.");
			
		}catch( RemoteException | MalformedURLException e ){
			e.printStackTrace();
		}
		
	}

}