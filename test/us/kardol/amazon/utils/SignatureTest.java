package us.kardol.amazon.utils;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillermo Kardolus
 */
public class SignatureTest {
    /**
     * Test of generate method, of class Signature.
     */
    @Test
    public void testGenerate() {
        System.out.println("Testing Signature.generate");
        Signature instance = new Signature();
        String key = "secret", data = "Message";
        String expResult = "qnR8UCqJggD55PohusaBNviGoOJ67HC6Btry4qXLVZc=";
        String result = instance.generateHash(key, data);
        
        System.out.print("Test 1. ");
        assertEquals(expResult, result);
        System.out.println("... ok");
    }
    
    /**
     * Test of canonicalize method, of class Signature.
     */
    @Test
    public void testCanonicalize() {
        System.out.println("Testing Signature.canonicalize");
        Signature instance = new Signature();
        
        String expResult = "car=audi&jeans=levi&key=value";
        Map<String,String> input = new HashMap<String,String>();
        input.put("car", "audi");
        input.put("key", "value");
        input.put("jeans", "levi");
        
        String result = instance.canonicalize(input);
        
        System.out.print("Test 1. ");
        assertEquals(expResult, result);
        System.out.println("... ok");
        
        input.put("z jay", "bla");
        result = instance.canonicalize(input);
        expResult += "&z%20jay=bla";
        
        System.out.print("Test 2. ");
        assertEquals(expResult, result);
        System.out.println("... ok");
    }
    
}
