
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyHashTable<K, V> {

    public void printBucketSizes() {
    }

    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        chainArray = new HashNode[M];
    }

    public MyHashTable(int M) {
        chainArray = new HashNode[M];
        this.M = M;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % M;
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        int keyIndex = hash(key);

        if (chainArray[keyIndex] == null) {
            chainArray[keyIndex] = new HashNode<>(key, value);
        } else {
            HashNode<K, V> node = chainArray[keyIndex];

            while (node.next != null) {
                node = node.next;
            }

            node.next = new HashNode<>(key, value);
        }

        size++;
    }

    public V get(K key) {
        int keyIndex = hash(key);

        if (chainArray[keyIndex] == null) {
            return null;
        }

        HashNode<K, V> temp = chainArray[keyIndex];

        while (!temp.key.equals(key) && temp.next != null) temp = temp.next;

        return temp.value;

    }

    public V remove(K key) {
        int keyIndex = hash(key);

        if (chainArray[keyIndex] == null) { return null; }

        if (chainArray[keyIndex].key.equals(key)) {
            HashNode<K, V> temp = chainArray[keyIndex];
            chainArray[keyIndex] = chainArray[keyIndex].next;
            size--;
            return temp.value;
        } else {
            HashNode<K, V> previous = chainArray[keyIndex];
            HashNode<K, V> node = previous.next;

            while (node != null && !node.key.equals(key)) {
                node = node.next;
                previous = node;
            }

            if (node != null) {
                previous.next = node.next;
                size--;
            }

            return null;
        }

    }

    public boolean contains(V value) {
        for (int i = chainArray.length - 1; i > 0; i--)
            for (HashNode<K, V> node = chainArray[i]; node != null; node = node.next)
                if (node.value.equals(value)) return true;

        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < chainArray.length; i++) {
            HashNode<K, V> node = chainArray[i];
            if (node.value.equals(value)) {
                return node.key;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        MyHashTable<Integer, Integer> hashTable = new MyHashTable<>();

        hashTable.put(5, 1);
        hashTable.put(2, 4);
        hashTable.put(3, 5);
        hashTable.put(4, 8);
        hashTable.put(7, 6);

        System.out.println(hashTable.getKey(3));
        System.out.println(hashTable.get(7));
        System.out.println(hashTable.contains(4));
    }
}