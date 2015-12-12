package us.kardol.amazon.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Guillermo Kardolus
 */
public class Signature {
    /*
        You calculate a keyed-hash message authentication code (HMAC-SHA256) 
        signature with your secret access key.
    */
    public String generateHash(String key, String data){
        String result = null;
        
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            
            result = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Signature.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    public String canonicalize(Map<String, String> unsortedMap){
        StringBuilder builder = new StringBuilder();
        SortedMap<String, String> sortedMap = new TreeMap<>(unsortedMap);
        
        if (sortedMap.isEmpty()) return "";
        Iterator<Map.Entry<String, String>> iter =
                 sortedMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> kvpair = iter.next();
            builder.append(encodeRfc3986(kvpair.getKey()));
            builder.append("=");
            builder.append(encodeRfc3986(kvpair.getValue()));
            if (iter.hasNext()) builder.append("&");
        }
        return builder.toString();   
    }
    
    public String encodeRfc3986(String s) {
        String out = null;

        try {
            out = URLEncoder.encode(s, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Signature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

}
