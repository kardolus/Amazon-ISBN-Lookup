package us.kardol.amazon.isbn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import us.kardol.amazon.utils.Signature;
import us.kardol.amazon.utils.TimeStamp;

/**
 * @author Guillermo Kardolus
 * 
 * Sample request
 *  http://webservices.amazon.com/onca/xml?
 *  Service=AWSECommerceService
 *  &Operation=ItemLookup
 *  &ResponseGroup=Large
 *  &SearchIndex=All
 *  &IdType=ISBN
 *  &ItemId=076243631X
 *  &AWSAccessKeyId=[Your_AWSAccessKeyID]
 *  &AssociateTag=[Your_AssociateTag]
 *  &Timestamp=[YYYY-MM-DDThh:mm:ssZ]
 *  &Signature=[Request_Signature]
 */

public class ItemLookup {
    private String awsAccessKeyId = null; 
    private String associateTag = null;
    private String secretKey = null;
    
    public ItemLookup(){
        
    }
    public ItemLookup(String key, String tag, String secret){
        this.associateTag = tag;
        this.awsAccessKeyId = key;
        this.secretKey = secret;
    }
    
    public String lookup(String isbn){
        String response = null, signData = null, stringUrl = null;
        String requestMethod = "GET", endPoint = "webservices.amazon.com",
               requestUri = "/onca/xml", queryString = "", signHash = "";
        Signature sign = new Signature();
        TimeStamp time = new TimeStamp();
        Map<String,String> params = new HashMap<>();
       
        // populate paramenters
        params.put("Timestamp", time.convert(new Date())); // now
        params.put("AWSAccessKeyId", this.awsAccessKeyId);
        params.put("AssociateTag", this.associateTag);
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");                                
        params.put("ItemId", isbn);
        params.put("ResponseGroup", "Small");
        params.put("AssociateTag", this.associateTag);
        params.put("IdType", "ISBN");
        params.put("SearchIndex", "All");
        
        queryString = sign.canonicalize(params);
        signData =  requestMethod + "\n" + 
                    endPoint + "\n" + 
                    requestUri + "\n" + 
                    queryString;
        System.out.println("Before: " + signData);
        
        signHash = sign.generateHash(this.secretKey, signData);
        System.out.println("After: " + signHash);
        
        stringUrl = "http://" + endPoint + requestUri + "?" + queryString + "&Signature=" + signHash;
        System.out.println("URL: " + stringUrl);
        
        // Have the good URL.. time to make the URL connection!
        URL url;
        try {
            url = new URL(stringUrl);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String chunk = null;
            while ((chunk = in.readLine()) != null) response += chunk;
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemLookup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemLookup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return response;
    }
    
    public void setAccessKey(String key){
        this.awsAccessKeyId = key;
    }
    public void setAssociateTag(String tag){
        this.associateTag = tag;
    }
    public void setSecretKey(String secret){
        this.secretKey = secret;
    }
    public String getAccessKey(){
        return this.awsAccessKeyId;
    }
    public String getAssociateTag(){
        return this.associateTag;
    }
    public String getSecretKey(){
        return this.secretKey;
    }
}
