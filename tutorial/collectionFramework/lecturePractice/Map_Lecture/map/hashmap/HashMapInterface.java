package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.map.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapInterface {

        /*
        * HashMap is a part of the java.util package and is one of the most commonly used implementations of the Map interface in Java.
        * It is based on the hash table data structure and provides constant-time performance for basic operations like get and put,
        *  assuming the hash function disperses the elements properly.
        *
        * KEY FEATURES OF HASHMAP:
        * 1. Key-Value Pairs: HashMap stores data in key-value pairs, where each key is unique and maps to a specific value.
        * 2. Hashing Mechanism: HashMap uses a hashing mechanism to compute an index for storing key-value pairs.
        *                        This allows for fast retrieval of values based on their keys.
        * 3. Null Keys and Values: HashMap allows one null key and multiple null values.
        *                            However, it does not allow duplicate keys.
        * 4. Unordered: HashMap does not maintain any order of its elements.
        *                The order of the key-value pairs may change over time as elements are added or removed.
        * 5. Not Synchronized: HashMap is not synchronized, which means it is not thread-safe.
        *                        If multiple threads access a HashMap concurrently, it must be synchronized externally.
        *
        *
        * METHODS IN HASHMAP:
        * 1. put(K key, V value) : Associates the specified value with the specified key in this map.
        * 2. get(Object key) : Returns the value to which the specified key is mapped,
        *                        or null if this map contains no mapping for the key.
        * 3. remove(Object key) : Removes the mapping for a key from this map if it is present.
        * 4. containsKey(Object key) : Returns true if this map contains a mapping for the specified key.
        * 5. containsValue(Object value) : Returns true if this map maps one or more keys to the specified value.
        * 6. size() : Returns the number of key-value mappings in this map.
        * 7. isEmpty() : Returns true if this map contains no key-value mappings.
        * 8. clear() : Removes all of the mappings from this map.
        * 9. keySet() : Returns a Set view of the keys contained in this map.
        * 10. values() : Returns a Collection view of the values contained in this map.
        * 11. entrySet() : Returns a Set view of the mappings contained in this map.
        * 12. putAll(Map<? extends K,? extends V> m) : Copies all of the mappings from the specified map to this map.
        * 13. clone() : Returns a shallow copy of this HashMap instance: the keys and values themselves are not cloned.
        * 14. hashCode() : Returns the hash code value for this map.
        * 15. equals(Object o) : Indicates whether some other object is "equal to" this one.
         *
         */

    static void main(String[] args) {
        // HashMap does not maintain any order of its elements.
        // The order of the key-value pairs may change over time as elements are added or removed.
        // The capacity of HashMap increases automatically with a rate of 100% when the current capacity is exceeded.
        // The default load factor of HashMap is 0.75,
        // which means that when the number of entries in the HashMap exceeds 75% of the current capacity,
        // the HashMap will be resized to accommodate more entries.
        // The time complexity of get and put operations in HashMap is O(1) on average,
        // but it can degrade to O(n) in the worst case if there are many hash collisions (i.e., multiple keys hashing to the same index).

        HashMap<Integer, String> hashMap = new HashMap<>();
//        HashMap<Integer, String> hashMap = new HashMap<>(19, 0.5f); // we can provide initial capacity and load factor to the HashMap constructor. HashMap(initialCapacity, loadFactor).
        hashMap.put(101, "Ankit"); // The key 101 is already present in the HashMap, so the value is updated.
        hashMap.put(102, "Nitesh");
        hashMap.put(103, "Rahul");
        hashMap.put(104, "Rajesh");
        hashMap.put(105, "Sahil");
        hashMap.put(106, "Komal");
        hashMap.put(107, "Kunal");
        hashMap.put(108, "Sunil");
        hashMap.put(null, "Susila");
        hashMap.put(null, "Ranu"); // Duplicate keys are not allowed in HashMap. 2 null keys are allowed.
                                        // Here the value associated with the first null key is updated to "Rajesh".
        System.out.println("The elements in the HashMap are: " + hashMap);
        System.out.println("The value associated with key 102 is: " + hashMap.get(102)); // get() method returns the value associated with the specified key.
        hashMap.remove(103);
        boolean rajesh = hashMap.remove(104, "Rohini"); // remove(Object key, Object value) method removes the entry for the specified key only if it is currently mapped to the specified value.
                                        // Here the entry for key 104 is not removed because the value associated with key 104 is "Rajesh" and not "Rohini".
        System.out.println("Was the entry for key 104 removed? : " + rajesh);
        System.out.println("The elements in the HashMap after removing key 103 are: " + hashMap);
        System.out.println("Does the HashMap contain key 104? " + hashMap.containsKey(104));
        System.out.println("Does the HashMap contain value 'Sahil'? " + hashMap.containsValue("Sahil"));
        System.out.println("The number of key-value mappings in the HashMap is: " + hashMap.size());
        System.out.println("Is the HashMap empty? " + hashMap.isEmpty());
        //  hashMap.clear();  // clear() method removes all of the mappings from this map.
        System.out.println("The elements in the HashMap after clearing it are: " + hashMap);


        Set<Integer> integers = hashMap.keySet(); // keySet() method returns a Set view of the keys contained in this map.
        for (Integer integer : integers) {
            System.out.println("Key: " + integer);
        }

        //System.out.println("The keys in the HashMap are: " + integers);

        Set<Map.Entry<Integer, String>> entries = hashMap.entrySet(); // entrySet() method returns a Set view of the mappings contained in this map.
        for (Map.Entry<Integer, String> entry : entries) {
         //  System.out.println("Entry: " + entry); // entrySet() method returns a Set view of the mappings contained in this map.
                                                    // Each element in the set is a Map.Entry object,
                                                    // which represents a key-value pair in the map.
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue()); // getKey() method returns the key corresponding to this entry.
                                                                                        // getValue() method returns the value corresponding to this entry.

            entry.setValue(entry.getValue().toUpperCase()); // setValue() method replaces the value corresponding to this entry with the specified value.
        }
        System.out.println("The elements in the HashMap after updating the values are: " + entries);

/*
        hashMap.keySet().forEach(key -> System.out.println("Key: " + key)); // forEach() method iterates over the elements of the specified collection,
        hashMap.values().forEach(value -> System.out.println("Value: " + value)); // and performs the given action for each element.
        hashMap.entrySet().forEach(entry -> System.out.println("Entry: " + entry)); // forEach() method iterates over the elements of the specified collection,
        // and performs the given action for each element.
*/

        // INTERNAL STRUCTURE OF HASHMAP:
            /**
             * 4 BASIC CONCEPTS OF HASHMAP:
             *   - Key : The identifier used to retrieve the value from the HashMap.
             *   - Value : The data stored in the HashMap. Data associated with a key is called the value.
             *   - Bucker : A place where the key-value pairs are stored. Think bucket as a cells in the list(array).
             *   - Hash Funtion : Converts the key into an index( bucket location) in the storage.
             *
             *   A Hash function is an Algorithm that takes an input (or 'key') and
             *   returns a fixed-size string of bytes, typically a numerical value.
             *   The output is known as a hash code, hash value or simply hash.
             *     The primary purpose of a hash function is to map data of arbitrary size to data of fixed size.
             *
             *     CHARACTERISTICS:
             *     1. Deterministic: The same input will always produce the same output.
             *     2. Fixed Output Size: Regardless of the input size, the hash code has a consistent size( eg: 32 bit or 64 bit ).
             *     3. Efficient Computation: The Hash function should compute quickly.
             *
             *
             *  HOW DATA IS STORED IN HASHMAP:
             *  Step 1: Hashing the key
             *          - First, the key is passed through a hash function to generate a unique hash code( an integer number).
             *          This hash code helps determine where the key-value pair will be stored in the array ( called a " Bucket array").
             *
             *  Step 2: Index Calculation
             *          - The hash code is then used to calculate the index in the array( bucket location) using.
             *             bucketIndex = hashcode % bucketArray.length;
             *          - This index decides which bucket will holds this key-value pair.
             *          For eg: if the hash code is 12,345 and the bucket array length is 16, then the bucket index will be 12,345 % 16 = 5.
             *
             *  Step 3: Storing in the Bucket
             *          - The key-value pair is then stored in the bucket at the calculated index.
             *          Each bucket can hold multiple key-value pairs.
             *          - If multiple keys hash to the same index (called a collision),
             *          the key-value pairs are stored in a linked list or a balanced tree within that bucket.
             *
             *
             * HOW HASHMAP RETRIEVES DATA:
             *  - When we call the get() method with a key, the HashMap performs the following steps:
             *    Step 1: Hashing the key : Similarly to insertion, the key is passed through a hash function to generate a unique hash code.
             *    Step 2: Finding the index : The hash code is then used to find the index of the bucket where the key-value pair is stored.
             *    Step 3: Searching the bucket : Once the correct bucket is found, it checks for the key in the bucket.
             *                                   If it find the key, then it returns the corresponding value.
             *
             *
             *   -> How data is stored in Hash in Table
             *       - The hash table is an array of buckets, each of which is a linked list of key-value pairs.
             *         Class Node<K, V> {
             *              final int hash;    // hash code for the key
             *              final K key;       // the key itself
             *              V value;        // the value associated with the key
             *              Node<K, V> next;    // reference to the next node in the bucket (for handling collisions)
             *         }
             *
             *
             *  HANDLING COLLISIONS:
             *  - Since multiple keys can generate the same index(called a collision), HashMap uses a technique to handle this situation.
             *    Java's HashMap uses a linked list( or balanced trees after java 8) for this.
             *  - If multiple key-value pairs map to the same bucket, they are stored in a linked list in the same bucket.
             *  - When retrieving a value, the HashMap will search through the linked list or
             *      traverse in the bucket to find the correct key and return its associated value.
             *
             *
             * */
        // HashMap uses a hash table to store the key-value pairs.
        // The hash table is an array of buckets, each of which is a linked list of key-value pairs.
        // The hash function is used to compute an index into the hash table array.
        // The hash table array is resized as needed to accommodate additional key-value pairs.
        // The hash table array is initially created with a default capacity of 16.
        // The load factor is used to determine when the hash table array should be resized.
        // The default load factor is 0.75, which means that when the number of entries in the hash table exceeds 75% of the current capacity,rajesh
        // the hash table array will be resized to accommodate more entries.
        // The time complexity of get and put operations in HashMap is O(1) on average,


        /* HASHMAP RESIZING( Rehashing...) :
         *  - Hashmap has an internal array size, which by default is 16.
         *    When the number of key-value pairs in the HashMap exceeds a certain threshold ( determined by the load factor, default is 0.75),
         *    the HashMap will resize itself to accommodate more entries.
         *  - The default size of the array is 16, so when more then 12 elements( 16*0.75) are inserted into the HashMap, it will trigger a resize operation.
         *  - During resizing, the HashMap creates a new array with a larger capacity (usually double the current capacity)
         *    and rehashes all the existing key-value pairs into the new array.
         *  - Rehashing involves recalculating the hash code for each key and determining
         *    the new index in the resized array based on the new capacity.
         *  - This process can be computationally expensive, especially if there are many key-value pairs to rehash,
         *    which is why it's important to choose an appropriate initial capacity and load factor
         *    when creating a HashMap to minimize the need for resizing.
         */


        /*
        *  HOW HASHMAP FIND THE CORRECT KEY IN CASE OF COLLISION:
        *    1. It used map.get(key) to find the value associated with the key. And this map.get(key) method performs "equals" operation on the key.
        *    2.
        * */
    }
}
