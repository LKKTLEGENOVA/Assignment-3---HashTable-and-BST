import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> table = new MyHashTable<>();
        Random random = new Random();

        // Add 10000 random elements to the hashtable
        for (int i = 0; i < 10000; i++) {
            MyTestingClass key = new MyTestingClass(random.nextInt(1000));
            table.put(key, "Value" + i);
        }

        // Print the number of elements in each bucket
        table.printBucketSizes();
    }
}
