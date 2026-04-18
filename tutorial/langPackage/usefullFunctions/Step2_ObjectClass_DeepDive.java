package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * STEP 2: OBJECT CLASS - THE ROOT OF EVERYTHING
 * 
 * Object is the parent class of ALL classes in Java.
 * Understanding Object class methods is crucial for any Java developer.
 * 
 * Key Concepts:
 * - Every class inherits from Object
 * - Object methods can be overridden
 * - toString(), equals(), hashCode() are most important
 */

public class Step2_ObjectClass_DeepDive {

    public static void main(String[] args) {
        System.out.println("===== STEP 2: OBJECT CLASS - THE ROOT OF EVERYTHING =====\n");

        // ============= 1. toString() METHOD =============
        System.out.println("1️⃣  toString() - String Representation:\n");

        Person person1 = new Person("Alice", 25);
        Person person2 = new Person("Bob", 30);

        // Default toString() (not overridden)
        System.out.println("  Default toString(): " + person1.toString());
        System.out.println("  Purpose: Returns class name and object reference");

        // When we print an object, toString() is called automatically
        System.out.println("  System.out.println(person1): " + person1);
        System.out.println("  Purpose: Implicit toString() call");

        // ============= 2. equals() METHOD =============
        System.out.println("\n2️⃣  equals() - Object Comparison:\n");

        Person alice1 = new Person("Alice", 25);
        Person alice2 = new Person("Alice", 25);
        Person bob = new Person("Bob", 30);

        // Default equals() (compares reference)
        System.out.println("  alice1 == alice2: " + (alice1 == alice2) + " (reference comparison)");
        System.out.println("  Purpose: Check if they are the same object in memory");

        // Overridden equals() (compares content)
        System.out.println("\n  alice1.equals(alice2): " + alice1.equals(alice2) + " (after override)");
        System.out.println("  alice1.equals(bob): " + alice1.equals(bob));
        System.out.println("  Purpose: Check if objects have same content/value");

        // ============= 3. hashCode() METHOD =============
        System.out.println("\n3️⃣  hashCode() - Hash Code Generation:\n");

        System.out.println("  alice1.hashCode(): " + alice1.hashCode());
        System.out.println("  alice2.hashCode(): " + alice2.hashCode());
        System.out.println("  bob.hashCode(): " + bob.hashCode());
        System.out.println("  Purpose: Generate unique code for object");

        System.out.println("\n  🔑 hashCode() Usage:");
        System.out.println("  - Used in HashMap, HashSet, etc.");
        System.out.println("  - Objects that are equal must have same hashCode");

        // ============= 4. getClass() METHOD =============
        System.out.println("\n4️⃣  getClass() - Runtime Type Information:\n");

        Person person = new Person("Charlie", 28);
        String name = "Java";
        int number = 42;

        System.out.println("  person.getClass(): " + person.getClass());
        System.out.println("  name.getClass(): " + name.getClass());
        System.out.println("  Purpose: Get runtime type of object");

        System.out.println("\n  person.getClass().getName(): " + person.getClass().getName());
        System.out.println("  person.getClass().getSimpleName(): " + person.getClass().getSimpleName());

        // ============= 5. clone() METHOD =============
        System.out.println("\n5️⃣  clone() - Object Copying:\n");

        CloneablePerson original = new CloneablePerson("David", 35);
        System.out.println("  Original: " + original);

        try {
            CloneablePerson cloned = (CloneablePerson) original.clone();
            System.out.println("  Cloned: " + cloned);
            System.out.println("  original == cloned: " + (original == cloned) + " (different objects)");
            System.out.println("  original.equals(cloned): " + original.equals(cloned) + " (same content)");
            System.out.println("  Purpose: Create independent copy of object");
        } catch (CloneNotSupportedException e) {
            System.out.println("  Error: Object not cloneable");
        }

        // ============= 6. finalize() METHOD (DEPRECATED) =============
        System.out.println("\n6️⃣  finalize() - Cleanup (DEPRECATED):\n");

        System.out.println("  ⚠️  finalize() is called before garbage collection");
        System.out.println("  ⚠️  DEPRECATED since Java 9 - use try-with-resources instead");
        System.out.println("  Purpose: Resource cleanup (old approach)");

        // ============= 7. PRACTICAL EXAMPLE: HashMap Usage =============
        System.out.println("\n7️⃣  PRACTICAL EXAMPLE: HashMap with Objects:\n");

        java.util.HashMap<Person, String> map = new java.util.HashMap<>();
        Person key1 = new Person("Eve", 28);
        Person key2 = new Person("Eve", 28);  // Same content as key1

        map.put(key1, "Developer");

        System.out.println("  map.put(key1, \"Developer\")");
        System.out.println("  key1: " + key1);
        System.out.println("  key1.hashCode(): " + key1.hashCode());

        System.out.println("\n  key2: " + key2);
        System.out.println("  key2.hashCode(): " + key2.hashCode());
        System.out.println("  key1.equals(key2): " + key1.equals(key2));

        System.out.println("\n  map.get(key2): " + map.get(key2));
        System.out.println("  Purpose: HashMap found key2 using equals() and hashCode()");

        // ============= 8. PRACTICAL EXAMPLE: HashSet Usage =============
        System.out.println("\n8️⃣  PRACTICAL EXAMPLE: HashSet with Objects:\n");

        java.util.HashSet<Person> set = new java.util.HashSet<>();
        set.add(new Person("Frank", 32));
        set.add(new Person("Frank", 32));  // Same person
        set.add(new Person("Grace", 29));

        System.out.println("  Added two \"Frank\" objects");
        System.out.println("  Set size: " + set.size() + " (should be 2, duplicates removed)");
        System.out.println("  Set contents: " + set);

        // ============= 9. COMPARISON OF IDENTITY VS EQUALITY =============
        System.out.println("\n9️⃣  IDENTITY vs EQUALITY:\n");

        String str1 = "Java";
        String str2 = "Java";
        String str3 = new String("Java");

        System.out.println("  str1 = \"Java\" (literal)");
        System.out.println("  str2 = \"Java\" (literal)");
        System.out.println("  str3 = new String(\"Java\") (new object)");

        System.out.println("\n  str1 == str2: " + (str1 == str2) + " (same reference)");
        System.out.println("  str1 == str3: " + (str1 == str3) + " (different reference)");
        System.out.println("  str1.equals(str3): " + str1.equals(str3) + " (same content)");

        System.out.println("\n  🔑 KEY INSIGHT:");
        System.out.println("  - == checks identity (reference)");
        System.out.println("  - equals() checks equality (content)");

        // ============= 10. SUMMARY TABLE =============
        System.out.println("\n🔟  OBJECT CLASS METHODS SUMMARY:\n");

        System.out.println("  ┌─────────────────┬──────────────────────────────────┐");
        System.out.println("  │ Method          │ Purpose                          │");
        System.out.println("  ├─────────────────┼──────────────────────────────────┤");
        System.out.println("  │ toString()      │ String representation            │");
        System.out.println("  │ equals()        │ Content comparison               │");
        System.out.println("  │ hashCode()      │ Hash code for collections        │");
        System.out.println("  │ getClass()      │ Runtime type information         │");
        System.out.println("  │ clone()         │ Create copy of object            │");
        System.out.println("  │ finalize()      │ Cleanup (DEPRECATED)             │");
        System.out.println("  └─────────────────┴──────────────────────────────────┘");

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Object is the parent of all classes");
        System.out.println("✓ toString() should be overridden for better output");
        System.out.println("✓ equals() and hashCode() should be overridden together");
        System.out.println("✓ HashMap and HashSet rely on equals() and hashCode()");
        System.out.println("✓ Use == for reference comparison, equals() for content");
    }

    // ============= HELPER CLASSES =============

    /**
     * Simple Person class
     */
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Person person = (Person) obj;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, age);
        }
    }

    /**
     * Cloneable Person class
     */
    static class CloneablePerson extends Person implements Cloneable {
        CloneablePerson(String name, int age) {
            super(name, age);
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}

