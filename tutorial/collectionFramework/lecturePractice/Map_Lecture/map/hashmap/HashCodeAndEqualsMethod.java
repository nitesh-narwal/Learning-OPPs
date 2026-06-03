package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.map.hashmap;

import java.util.HashMap;
import java.util.Objects;

public class HashCodeAndEqualsMethod {
    static void main(String[] args) {

        /**
         * ========== PROBLEM DEMONSTRATION ==========
         * Without custom hashCode() and equals() methods:
         * - Even if two Person objects have SAME data (name="Alice", id=1),
         *   they are stored as DIFFERENT keys in HashMap because they have DIFFERENT memory addresses.
         * - This is because the default hashCode() uses the object's memory address.
         */
        HashMap<Person, String> map = new HashMap<>();
        // p1 and p2 have identical data but different memory addresses
        Person p1 = new Person("Alice", 1);  // p1 has different memory address than p2, so they generate different hashCodes
        Person p2 = new Person("Alice", 1);
        Person p3 = new Person("Bob", 2);
        Person p4 = new Person("Rahul", 5);

        // BEFORE custom hashCode/equals: Each person gets a separate index in HashMap
        map.put(p1, "Student");  // hashCode1 ---> Index1
        map.put(p2, "Teacher");  // hashCode2 ---> Index2 (Should be the same index as p1 after override)
        map.put(p3, "Student");  // hashCode3 ---> Index3
        map.put(p4, "Student");  // (hashCode4 ---> Index4),  After creating the custom hashCode() method, p1 and p2 will have the same hashCode,
                                                                // so they will be stored in the same index.
        
        /*
        * EXPLANATION OF DEFAULT BEHAVIOR (without custom equals & hashCode):
        * In the case of p1 and p2, they are stored at different address locations in memory.
        * So, they are considered as DIFFERENT objects with DIFFERENT hashCodes.
        * So, they are NOT stored in the same index.
        * Result: HashMap size = 4 (all 4 objects are stored as separate keys)
        *  */

        // ============== VISUALIZING HASHCODES ==============
        // Each object gets a unique hashcode based on its memory address (default behavior)
        System.out.println("\n========== HASHCODE DEMONSTRATION ==========\n");
        System.out.println("Object Details:");
        System.out.println("p1 Name: " + p1.getName() + ", ID: " + p1.getId() + " | HashCode: " + p1.hashCode());
        System.out.println("p2 Name: " + p2.getName() + ", ID: " + p2.getId() + " | HashCode: " + p2.hashCode());
        System.out.println("p3 Name: " + p3.getName() + ", ID: " + p3.getId() + " | HashCode: " + p3.hashCode());
        System.out.println("p4 Name: " + p4.getName() + ", ID: " + p4.getId() + " | HashCode: " + p4.hashCode());
        
        System.out.println("\n⚠️ Notice: p1 and p2 have SAME data (name='Alice', id=1)");
        System.out.println("   BUT different hashcodes because they are DIFFERENT objects in memory!");
        System.out.println("\nHashCode Comparison:");
        // BEFORE custom methods: This is FALSE because different memory addresses = different hashCodes
        System.out.println("p1.hashCode() == p2.hashCode() ? " + (p1.hashCode() == p2.hashCode()));
        // BEFORE custom methods: This is FALSE because default equals() compares memory addresses
        System.out.println("p1.equals(p2) ? " + p1.equals(p2) + " (using default equals from Object class)");
        
        System.out.println("\n========== MAP STORAGE ==========");
        System.out.println("Map Size: " + map.size());
        System.out.println("Map Contents: " + map);

        System.out.println("\n <--------------------------------->\n");
        System.out.println("Map Size: " + map.size());
        // Each get() call uses hashCode() and equals() to find the value
        System.out.println("Value of p1: " + map.get(p1));
        System.out.println("Value of p2: " + map.get(p2));
        System.out.println("Value of p3: " + map.get(p3));
        System.out.println("Value of p4: " + map.get(p4));
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);


        /**
         * ========== EXAMPLE WITH STRING KEYS ==========
         * String class ALREADY overrides hashCode() and equals() properly.
         * So duplicate String keys WILL overwrite previous values.
         * This demonstrates the CORRECT behavior we want with custom objects.
         */
        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("Subham", 90); // hashCode1 ---> Index1
        map2.put("Rohit", 93);  // hashCode2 ---> Index2
        map2.put("Rahul", 95);  // hashCode3 ---> Index3
        map2.put("Rahul", 97);  // hashCode3 ----> Index3   .Duplicate key, will overwrite previous value (95 becomes 97)
        map2.put("Subham", 99);  // hashCode1 ---> Index1 ---> equals() ---> replace (90 becomes 99)
        // Result: map2.size() = 3 (duplicate keys overwrote previous values)


        /**
         *  ========== HASHMAP TIME COMPLEXITY TABLE ==========
         *  
         *  ┌─────────────────────────┬──────────────┬──────────────┬─────────────────────────────────────────────────┐
         *  │ METHOD                  │ AVERAGE CASE │ WORST CASE   │ EXPLANATION                                     │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ hashCode()              │     O(1)     │     O(1)     │ Fixed operations on fields, no iteration        │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ equals(Object obj)      │     O(1)     │     O(1)     │ Field-by-field comparison, fixed number of ops  │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.get(key)        │     O(1)     │     O(logN)  │ AVG: Direct bucket access                       │
         *  │                         │              │              │ WORST: Hash collision, check all n keys         │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.put(key, value) │     O(1)     │     O(logN)  │ AVG: Hash + find bucket + insert                │
         *  │                         │              │              │ WORST: Hash collision, check all n keys         │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.remove(key)     │     O(1)     │     O(n)     │ AVG: Hash + find and delete                     │
         *  │                         │              │              │ WORST: Hash collision, scan all n keys          │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.containsKey()   │     O(1)     │     O(logN)  │ Same as get() - checks if key exists            │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.containsValue() │     O(n)     │     O(n)     │ Must iterate through ALL n values to find match │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.values()        │     O(n)     │     O(n)     │ Creates collection of all n values              │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.keySet()        │     O(n)     │     O(n)     │ Creates set of all n keys                       │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.entrySet()      │     O(n)     │     O(n)     │ Creates set of all n key-value pairs            │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.size()          │     O(1)     │     O(1)     │ Returns stored size variable                    │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.clear()         │     O(n)     │     O(n)     │ Must delete all n entries from map              │
         *  ├─────────────────────────┼──────────────┼──────────────┼─────────────────────────────────────────────────┤
         *  │ HashMap.isEmpty()       │     O(1)     │     O(1)     │ Checks if size is 0                             │
         *  └─────────────────────────┴──────────────┴──────────────┴─────────────────────────────────────────────────┘
         *  
         *  ========== KEY INSIGHTS ==========
         *  
         *  ✓ KEY-BASED OPERATIONS (get, put, remove, containsKey):
         *    - Use hashCode() to find bucket in O(1)
         *    - Then use equals() to find exact key
         *    - Average O(1) with good hash distribution
         *    - Worst O(n) when all keys hash to same bucket (hash collision)
         *  
         *  ✓ VALUE-BASED OPERATIONS (containsValue):
         *    - No hash function available for values
         *    - Must LINEAR SCAN through all n entries
         *    - Always O(n) regardless of distribution
         *  
         *  ✓ COLLECTION OPERATIONS (keySet, values, entrySet):
         *    - Creates new collection containing all n entries
         *    - Must iterate through entire HashMap
         *    - Always O(n)
         *  
         *  ✓ HASH COLLISION SCENARIO (Worst Case):
         *    - Occurs when multiple different keys generate same hashCode
         *    - Java 8+: Uses Red-Black Tree when collision chain grows (threshold: 8)
         *    - Still degrades to O(n) in pathological cases
         *   
         * */
    }
}

class Person{
    private String name;
    private int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * ========== CUSTOM hashCode() METHOD ==========
     * PURPOSE: Generate the SAME hashCode for objects with SAME data (name, id).
     * 
     * DEFAULT BEHAVIOR (super.hashCode()):
     *   - Returns a hashCode based on the object's memory address.
     *   - Different objects = Different hashCodes, even if data is identical.
     *   - This is why p1 and p2 get different hashCodes without custom method.
     * 
     * CUSTOM BEHAVIOR (Objects.hash(name, id)):
     *   - Returns a hashCode based on the VALUES of name and id fields.
     *   - Same data = Same hashCode (p1 and p2 will have identical hashCodes).
     *   - Different data = Different hashCodes.
     * 
     * IMPORTANT: hashCode() is called FIRST by HashMap to find the bucket/index.
     *            If two objects have the same hashCode, HashMap then calls equals() to check if they're the same key.
     */
    @Override
    public int hashCode() {
       // return super.hashCode();  // OLD: Uses memory address (not what we want)
        return Objects.hash(name, id);  // NEW: Same data = Same hashCode ✓
    }

    /**
     * ========== CUSTOM equals() METHOD ==========
     * PURPOSE: Determine if two Person objects are LOGICALLY equivalent (same data).
     * 
     * DEFAULT BEHAVIOR (super.equals(obj)):
     *   - Compares memory addresses using reference equality (this == obj).
     *   - Different objects in memory = Not equal, even if data is identical.
     *   - This is why p1.equals(p2) is false without custom method.
     * 
     * CUSTOM BEHAVIOR:
     *   - Step 1: Check reference equality (this == obj) → if same object, return true
     *   - Step 2: Check null safety (obj == null) → prevent NullPointerException
     *   - Step 3: Check class type (getClass() != obj.getClass()) → ensure comparing same type
     *   - Step 4: Compare actual field values (id and name) → check logical equality
     * 
     * EXECUTION FLOW in HashMap:
     *   1. HashMap calculates hashCode of the key
     *   2. If two keys have the SAME hashCode, HashMap calls equals() to verify they're actually the same key
     *   3. If equals() returns true, the value gets OVERWRITTEN
     *   4. If equals() returns false, objects are stored in same bucket but different linked list nodes (collision handling)
     * 
     * RESULT: Now p1 and p2 are treated as the SAME key because both hashCode() AND equals() return true.
     */
    @Override
    public boolean equals(Object obj) {
       // return super.equals(obj);  // OLD: Compares memory addresses (not what we want)

        // ===== SAFETY CHECKS =====
        // Step 1: Quick check - are we comparing the exact same object in memory?
        if(this == obj){
            return true;  // Same reference = definitely equal
        }
        
        // Step 2: Null safety check - prevent NullPointerException
        if(obj == null){
            return false;  // null is never equal to a Person object
        }
        
        // Step 3: Type safety check - can we safely cast to Person?
        if(getClass() != obj.getClass()){
            return false;  // Different class type = not equal
        }
        
        // ===== ACTUAL COMPARISON =====
        // Step 4: Now safely cast and compare field values
        Person p = (Person) obj;
        // Two Person objects are equal if they have the SAME id AND name
        return id == p.id && Objects.equals(name, p.getName());
    }

    /**
     * ========== toString() METHOD ==========
     * PURPOSE: Provide a readable String representation of the Person object.
     * Includes the hashCode to visualize the custom hashCode behavior.
     */
    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + " (hashCode: " + hashCode() + ")";
    }
}

// ========== COMPLETE SOLUTION REFERENCE ==========
// If you want p1 and p2 to be treated as the SAME key in HashMap, you need to override equals() and hashCode() in the Person class:
   /* @Override
      public int hashCode() {
        return Objects.hash(name, id);  // Same data = Same hashCode
       }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id == person.id && Objects.equals(name, person.name);
       }
*/