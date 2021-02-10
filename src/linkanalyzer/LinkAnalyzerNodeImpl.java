package linkanalyzer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("serial")
public class LinkAnalyzerNodeImpl implements Serializable, LinkAnalyzerNode{
	
	/**
	 * List of Link objects maintained by the LinkAnalyzerNode object.
	 */
	List<Link> links = new ArrayList<Link>();
	
	/**
	 * Constructor
	 * @param folder File object for the folder containing files to read
	 */
	public LinkAnalyzerNodeImpl(File folder){
		
		//Loop through all files in the folder
		for( File file : folder.listFiles() ){
			
			//Use scanner to read individual lines to parse
			if( file.toString().contains(".json")){
			
				System.out.println("Reading file: " + file);
				try{
					
					Scanner scanner = new Scanner(file);
					
					while( scanner.hasNextLine() ){
						
						String lineToParse = scanner.nextLine().trim();
						
						try{
							
							//Add a Link returned by static parse method to list
							links.add( Link.parse(lineToParse) );
							
						}catch(Exception e){
							System.out.println("Exception Parsing Line.");
						}
					}
					scanner.close();
								
				}catch (IOException e){
					e.printStackTrace();
				}
			
			}//end if
			
		}//end for
		
	}

	/**
     * Return the Links whose timestamp is between startTime and endTime, searching just the files managed by this node.
     * @param startTime Minimum timestamp to be returned.
     * @param endTime Maximum timestamp to be returned.
     * @return Links whose timestamp is between startTime and endTime.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByTime(long startTime, long endTime) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		
		for( Link link : links ){
			
			if( link.timestamp() > startTime && link.timestamp() < endTime )
				linksToReturn.add(link);
		}
		
		return linksToReturn;
	}

	/**
     * Return the Links with a given URL, searching just the files managed by this node.
     * @param url URL to search for.
     * @return Links with the given URL.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByURL(String url) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		
		for( Link link : links ){
			
			if( link.url().equals(url) )
				linksToReturn.add(link);
		}
		
		return linksToReturn;
	}

	/**
     * Return the Links containing all of the given tags, searching just the files managed by this node.
     * @param tags Set of tags of interest.
     * @return Links containing all of the given tags.
     * @throws RemoteException
     */
	@Override
	public Set<Link> linksByTag(String... tags) throws RemoteException{
		
		Set<Link> linksToReturn = new HashSet<Link>();
		List<String> tagsToCheck = new ArrayList<String>();
		
		for( String tag : tags )
			tagsToCheck.add(tag);
		
		for( Link link : links ){
			
			if( link.tags().containsAll(tagsToCheck) )
				linksToReturn.add(link);
		}
		
		return linksToReturn;
	}
	
	/**
	 * Main method
	 * Converts the provided folder filepath to a File object to be passed to the constructor.
	 * Locates the LinkAnalyzer from the RMI registry using a specified URL name and registers
	 * the new LinkAnalyzerNodeImpl with it.
	 * @param args filepath of folder containing files to read
	 */
	public static void main(String[] args){
				
		String filePath = args[0];
		
		File folder = new File(filePath);
		System.out.println("Folder to analyze: " + folder);
		
		LinkAnalyzerNodeImpl lan = new LinkAnalyzerNodeImpl(folder);
		System.out.println("New LinkAnalyzerNode Created, Folder analyzed.");
		
		try{
			
           LinkAnalyzer la = (LinkAnalyzer)Naming.lookup(LinkAnalyzer.URL); 
           System.out.println("Naming Lookup>");
           
           la.registerNode(lan);
           System.out.println("Node Registered.");
           
        }catch( RemoteException | MalformedURLException | NotBoundException e ){ 
           e.printStackTrace(); 
        } 

	}

}