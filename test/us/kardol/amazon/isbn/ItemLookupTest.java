package us.kardol.amazon.isbn;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillermo Kardolus
 */
public class ItemLookupTest {
    /**
     * Test of lookup method, of class ItemLookup.
     */
    @Test
    public void testLookup() {
        System.out.println("Testing ItemLookUp.lookup");
        ItemLookup instance = new ItemLookup();
        instance.setAccessKey("your-key");
        instance.setAssociateTag("your-tag");
        instance.setSecretKey("your-secret");
        
        String isbn = "076243631X";
        String result = instance.lookup(isbn);
        
        System.out.println("ISBN: " + isbn);
        System.out.println("Result: " + result);
    }
    
}
