import java.util.Scanner;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.collections.VStack;
import voldemort.versioning.Versioned;

public class homework1{

	public static void main(String[] args) {
        stringStoreExample();

    }

    public static void stringStoreExample() {
    
        int i = 0;
        int keyIndex = 0;
        
        System.out.println("==============String store example=================");
        String bootstrapUrl = "tcp://dbclass.cs.unca.edu:6666";
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));
        StoreClient<String, String> client = factory.getStoreClient("test");
        
        while(true) {
            Versioned<String> versioned = client.get("cstyles2-" + i);
            if(versioned == null) {
                keyIndex = i;
                break;
            }
            i++;
        }
        
        System.out.println("Input your string here: ");
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        System.out.println("Putting an initial value");
        client.put("cstyles2-" + keyIndex, str);
        
        for(int j = 0; j < keyIndex + 1; j++) {
            Versioned<String> versioned = client.get("cstyles2-" + j);
            System.out.println(String.valueOf(versioned.getValue()));
        }
    }
}