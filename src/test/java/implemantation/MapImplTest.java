package implemantation;

import static org.junit.Assert.*;

public class MapImplTest {

    @org.junit.Test
    public void put() {
        MapImpl<Integer, Long> map = new MapImpl<Integer, Long>();
        assertEquals("Method put() should return : true", true, map.put(1, 3333L));
    }

    @org.junit.Test
    public void get() {
        MapImpl<Integer, Long> map = new MapImpl<Integer, Long>();
        map.put(1, 123123123123L);
        map.put(2, 33L);
        map.put(3, 3332323L);
        assertEquals(33, 33);
    }

    @org.junit.Test
    public void size() {
        MapImpl<Integer, Long> map = new MapImpl<Integer, Long>();
        map.put(1, 123123123123L);
        map.put(2, 33L);
        map.put(3, 3332323L);
        assertEquals("Map size should be : 3", 3, map.size());
    }
}