package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 2: SIMPLE INTERFACE IMPLEMENTATION
 * ============================================================================
 * 
 * Syntax:
 * -------
 * public interface InterfaceName {
 *     returnType methodName(parameters);
 * }
 * 
 * To implement an interface:
 * -------
 * public class ClassName implements InterfaceName {
 *     // Must provide implementation for all abstract methods
 *     @Override
 *     public returnType methodName(parameters) {
 *         // implementation
 *     }
 * }
 * 
 * Key Points:
 * -----------
 * 1. Use "implements" keyword to implement interface (not "extends")
 * 2. Must implement ALL abstract methods from interface
 * 3. A class can implement multiple interfaces using comma-separated list
 *    Example: public class MyClass implements Interface1, Interface2, Interface3
 * 4. @Override annotation is not mandatory but recommended (best practice)
 * 5. Implementing methods must be public
 * ============================================================================
 */

// INTERFACE 1: Defines what a Printable object should do
interface Printable {
    /**
     * Abstract method that must be implemented
     * This method should print the object's details
     */
    void print();
}

// INTERFACE 2: Defines what a Saveable object should do
interface Saveable {
    /**
     * Abstract method that must be implemented
     * This method should save the object's data
     */
    void save();
}

/**
 * CLASS: Document
 * ===============
 * This class implements BOTH Printable and Saveable interfaces
 * This is called MULTIPLE INTERFACE IMPLEMENTATION
 * This is how we achieve multiple inheritance in Java!
 */
class Document implements Printable, Saveable {
    
    private String content;
    private String title;
    
    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    /**
     * Implementation of Printable interface method
     * This class MUST implement this method
     */
    @Override
    public void print() {
        System.out.println("========== PRINTING ==========");
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("=============================\n");
    }
    
    /**
     * Implementation of Saveable interface method
     * This class MUST implement this method
     */
    @Override
    public void save() {
        System.out.println("✓ Document '" + title + "' saved to database");
    }
    
    // Additional helper methods (not from interface)
    public String getTitle() {
        return title;
    }
}

/**
 * CLASS: Report
 * =============
 * Another class implementing the same interfaces
 * Shows that different classes can implement same interface differently
 */
class Report implements Printable, Saveable {
    
    private String reportName;
    private double totalAmount;
    
    public Report(String reportName, double totalAmount) {
        this.reportName = reportName;
        this.totalAmount = totalAmount;
    }
    
    /**
     * Different implementation from Document class
     * But both follow the Printable interface contract
     */
    @Override
    public void print() {
        System.out.println("========== REPORT ==========");
        System.out.println("Report Name: " + reportName);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("============================\n");
    }
    
    /**
     * Different implementation from Document class
     * But both follow the Saveable interface contract
     */
    @Override
    public void save() {
        System.out.println("✓ Report '" + reportName + "' saved successfully");
    }
}

/**
 * WHY THIS APPROACH?
 * 
 * Without Interface (TIGHTLY COUPLED):
 * Document doc = new Document(...);
 * Report rep = new Report(...);
 * doc.print();  // Can only work with Document
 * rep.print();  // Can only work with Report
 * // Need separate code for each type
 * 
 * With Interface (LOOSELY COUPLED):
 * Printable obj1 = new Document(...);
 * Printable obj2 = new Report(...);
 * obj1.print();  // Works with any class that implements Printable
 * obj2.print();  // Same method call works for different types!
 * // Single code works for multiple types
 * 
 * This is the POWER OF INTERFACES!
 */

