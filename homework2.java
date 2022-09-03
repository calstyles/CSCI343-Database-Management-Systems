import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.collections.VStack;
import voldemort.versioning.Versioned;



public class homework2 {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("/Users/calebstyles/Downloads/species.csv"));
		String key = "cstyles2:root";
        String name = "";
        String bootstrapUrl = "tcp://dbclass.cs.unca.edu:6666";
		scan.useDelimiter("\n"); 
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));
        StoreClient<String, List<Map<String, String>>> directory = factory.getStoreClient("directory");
                
        while (scan.hasNext()){ 
            List<Map<String, String>> value = new ArrayList<Map<String, String>>();
            String str = scan.next();
            String[] families = str.split(" ");
            for(int i = 0; i < families.length; i++) {
                Map<String, String> entry = new HashMap<String, String>();
                if(families[i].charAt(families[i].length() - 1) == ',') {                	
                    families[i] = families[i].substring(0, families[i].length() - 1);
                }
                
                if(families[i].charAt(0) == '"') {
                    while(true) {
                        name += families[i];
//                        System.out.println(name);
                        if(families[i].charAt(families[i].length() - 1) == '"') {
                            break;
                        }
                        i++;
                    }
//                    System.out.println(name);
                    entry.put("name", name);
                    entry.put("key", null);
                    name = "";
                }else{
                    entry.put("name", families[i]);
                    entry.put("key", "cstyles2: " + families[i]);
                }
                System.out.println(entry);
                value.add(entry);
            }
            directory.put(key, value); 
        }try{
	        Scanner scanInput = new Scanner(System.in);
	        System.out.println("Put your key right here: ");
	        String userKey = scanInput.nextLine();      
	    	Versioned<List<Map<String, String>>> versioned = directory.get(userKey);
	    	System.out.println("Total number of keys: " + String.valueOf(versioned.getValue().size()));
        }catch(NullPointerException e) {
        	System.out.println("That directory does not exist! Please type in a different directory, such as cstyles2:root!");
        }
        
	}	
}