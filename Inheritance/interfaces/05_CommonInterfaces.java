package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 5: COMMON INTERFACES IN REAL APPLICATIONS
 * ============================================================================
 * 
 * These interfaces are used in almost every Java application
 * Understanding them helps you write better code
 * ============================================================================
 */

// INTERFACE 1: Comparable - for sorting objects
interface ComparableEmployee extends Comparable<ComparableEmployee> {
    String getName();
    double getSalary();
}

/**
 * CLASS: Employee implementing Comparable
 * This allows employees to be sorted by salary
 */
class Employee implements ComparableEmployee {
    
    private int id;
    private String name;
    private double salary;
    
    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public double getSalary() {
        return salary;
    }
    
    /**
     * compareTo method from Comparable interface
     * Returns:
     * - Negative number: this object < other object
     * - Zero: this object = other object
     * - Positive number: this object > other object
     */
    @Override
    public int compareTo(ComparableEmployee other) {
        // Sort by salary (ascending)
        return Double.compare(this.salary, other.getSalary());
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

// INTERFACE 2: Cloneable - for creating deep copies
interface Cloneable {
    // This is a marker interface (no methods, just signals intent)
    // Used by Object.clone() method
}

/**
 * CLASS: Person with cloning capability
 * Implements Cloneable to allow object copying
 */
class Person implements Cloneable {
    
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    /**
     * clone method for creating copy of object
     * This avoids reference sharing
     */
    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Person(this.name, this.age);
        }
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

// INTERFACE 3: Serializable - for saving objects to files/network
/**
 * Marker interface (no methods)
 * Indicates object can be converted to byte stream
 * Used for:
 * - Saving to files
 * - Sending over network
 * - Storing in database
 */
interface Serializable {
}

class SerializableStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String rollNo;
    private String name;
    private double gpa;
    
    public SerializableStudent(String rollNo, String name, double gpa) {
        this.rollNo = rollNo;
        this.name = name;
        this.gpa = gpa;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "rollNo='" + rollNo + '\'' +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}

// INTERFACE 4: Iterable - for using enhanced for loop
/**
 * Allows object to be used in for-each loop
 */
interface IterableList<T> extends Iterable<T> {
    void add(T element);
    T get(int index);
    int size();
}

/**
 * CLASS: CustomList implementing Iterable
 * Allows for-each loop: for (String item : list) { }
 */
class CustomList<T> implements IterableList<T> {
    
    private java.util.ArrayList<T> elements = new java.util.ArrayList<>();
    
    @Override
    public void add(T element) {
        elements.add(element);
    }
    
    @Override
    public T get(int index) {
        return elements.get(index);
    }
    
    @Override
    public int size() {
        return elements.size();
    }
    
    /**
     * iterator() from Iterable interface
     * Required for for-each loop support
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return elements.iterator();
    }
}

/**
 * INTERFACE 5: Runnable - for multi-threading
 */
interface RunnableTask extends Runnable {
    void execute();
}

/**
 * CLASS: Task implementing Runnable
 * Can be executed in separate thread
 */
class Task implements RunnableTask {
    
    private String taskName;
    
    public Task(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public void execute() {
        System.out.println("Executing: " + taskName);
    }
    
    /**
     * run() from Runnable interface
     * This method runs when thread starts
     */
    @Override
    public void run() {
        System.out.println("[Thread] Starting: " + taskName);
        execute();
        System.out.println("[Thread] Completed: " + taskName);
    }
}

/**
 * ============================================================================
 * SUMMARY OF COMMON INTERFACE TYPES
 * ============================================================================
 * 
 * 1. REGULAR INTERFACE (has abstract methods)
 *    Example: DataStorage, Drawable, Employee
 *    Purpose: Define contract for implementing classes
 * 
 * 2. MARKER INTERFACE (no methods, just marks intent)
 *    Example: Cloneable, Serializable, Runnable
 *    Purpose: Signal to JVM that object has special capability
 *    
 *    When to use Marker Interface:
 *    - When you want to mark a class without changing its structure
 *    - Example: Make class Serializable without adding methods
 * 
 * 3. FUNCTIONAL INTERFACE (single abstract method)
 *    Example: Runnable, Callable, ActionListener
 *    Purpose: Can be implemented using Lambda expressions (Java 8+)
 *    
 *    Syntax: interface MyInterface {
 *        void doSomething();  // Only one method
 *    }
 *    
 *    Can use with lambda:
 *    MyInterface obj = () -> System.out.println("Done");
 * 
 * WHY THESE INTERFACES?
 * =====================
 * Java provides these standard interfaces because:
 * 1. Consistency - All classes follow same pattern
 * 2. Framework Integration - Tools expect these interfaces
 * 3. Code Reusability - Collections, streams, etc. expect Comparable, Iterable
 * 4. Type Safety - Compiler ensures implementations are correct
 * 5. Polymorphism - Different implementations work same way
 */

