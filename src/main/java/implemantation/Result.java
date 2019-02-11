package implemantation;

public class Result {
    public static void main(String[] args) {
        MapImpl<Integer, Long> map = new MapImpl<Integer, Long>();
        map.put(1, 3333L);
        map.put(2, 1111L);
        map.put(3, 332323L);
        map.put(4, 333232332L);
        System.out.println(map.size());
        System.out.println(map.get(2));
    }
}
