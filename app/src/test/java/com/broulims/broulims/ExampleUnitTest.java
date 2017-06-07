package com.broulims.broulims;

import org.junit.Test;

import static com.broulims.broulims.R.string.search;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    //test the price
    @Test
    public void valid_search() throws Exception {
        Item item = new Item("Apple", "FAZWFW123", "back", 200);

        Item_Validator check= new Item_Validator();
        assertThat(check.valid(item), is(true));
    }




/*
    @Test
    public void valid_item() throws Exception {
        assertNotNull(Item.name);
    }
*/


}