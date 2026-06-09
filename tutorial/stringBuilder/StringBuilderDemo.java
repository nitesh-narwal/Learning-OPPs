package me.niteshh.OPPs.tutorial.stringBuilder;

public class StringBuilderDemo {
    static void main(String[] args) {
        // Now see what's happening here.
        // We know that String is immutable, so we can't change the value of String.
        // so we add strings by creating new String object.
        String str = "Hello";
        str.concat(" World");
        String str1 = str.concat(" World");
        System.out.println(str);
        System.out.println(str1);


        // now see
        String result = " ";
        for (int i = 0; i < 1000; i++) {
            result += "Hello";
        }
        System.out.println(result);

        // This creates multiple String objects which consumes memory and is not performance efficient.
    }
}

class StringBuilderDemo1 {
    static void main(String[] args) throws InterruptedException {
        // String is immutable so we can't change the value of String.
        // but we can change the value of StringBuilder.
        String str = "Hello";
        StringBuilder sb = new StringBuilder(str);
        // It is mutable and
        // we can do method chaining
        // but it's not thread safe.
        sb.append(" World").append("!!!");
        System.out.println(sb);
        // we can also do reverse operation
        System.out.println(sb.reverse());
        // we can again convert it to string
        System.out.println(sb.toString()); // so it immutable now.

        // Internal workings of StringBuilder
        // 1. It creates a character array of size 16.
        // 2. It uses the character array to store the string. 16 is the default size. We can change it.
        // 3. It provides methods to manipulate the string.
        // 4. It automatically resizes the character array when needed.

        // StringBuilder is not thread safe.

        StringBuilder sb1 = new StringBuilder();

        Task t1 = new Task(sb1);
        Task t2 = new Task(sb1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("The final length of StringBuilder is: " + sb1.length()); // so builder is not thread safe.
    }
}

class StringBufferDemo{
    static void main(String[] args) throws InterruptedException {
        // StringBuffer is similar to StringBuilder but it is thread safe.
        // It is synchronized and can be used in multi-threaded environment.
        StringBuffer sbuf = new StringBuffer("Hello");
        sbuf.append(" World").append("!!!");
        System.out.println(sbuf);

        StringBuffer sb = new StringBuffer();
        Task1 t1 = new Task1(sb);
        Task1 t2 = new Task1(sb);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("The final length of StringBuffer is: " + sb.length()); // so buffer is thread safe.
    }
}

class Task extends Thread{
    StringBuilder sb ;

    public Task(StringBuilder sb) {
        this.sb = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }
    }
}

class Task1 extends Thread{
    StringBuffer sb ;

    public Task1(StringBuffer sb) {
        this.sb = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }
    }
}

/*
 * ==================================================================================
 * COMPREHENSIVE COMPARISON: String vs StringBuilder vs StringBuffer
 * ==================================================================================
 * 
 * 1. IMMUTABILITY
 *    ┌─────────────┬──────────────┬──────────────┬──────────────┐
 *    │ Aspect      │ String       │ StringBuilder│ StringBuffer │
 *    ├─────────────┼──────────────┼──────────────┼──────────────┤
 *    │ Immutable   │ ✅ YES       │ ❌ NO        │ ❌ NO        │
 *    │ Mutable     │ ❌ NO        │ ✅ YES       │ ✅ YES       │
 *    └─────────────┴──────────────┴──────────────┴──────────────┘
 *    
 *    String is immutable - once created, cannot be changed.
 *    StringBuilder and StringBuffer are mutable - can be modified.
 * 
 * ==================================================================================
 * 
 * 2. THREAD SAFETY
 *    ┌─────────────┬──────────────┬──────────────┬──────────────┐
 *    │ Aspect      │ String       │ StringBuilder│ StringBuffer │
 *    ├─────────────┼──────────────┼──────────────┼──────────────┤
 *    │ Thread-Safe │ ✅ YES        │ ❌ NO         │ ✅ YES        │
 *    │ Synchronized│ N/A          │ ❌ NO         │ ✅ YES        │
 *    │ Multi-thread│ ✅ SAFE      │ ⚠️ UNSAFE    │ ✅ SAFE       │
 *    └─────────────┴──────────────┴──────────────┴──────────────┘
 *    
 *    String: Thread-safe (immutable objects are inherently thread-safe)
 *    StringBuilder: NOT thread-safe (no synchronization)
 *    StringBuffer: Thread-safe (all methods are synchronized)
 * 
 * ==================================================================================
 * 
 * 3. PERFORMANCE & SPEED
 *    ┌─────────────┬──────────────┬──────────────┬──────────────┐
 *    │ Aspect      │ String       │ StringBuilder│ StringBuffer │
 *    ├─────────────┼──────────────┼──────────────┼──────────────┤
 *    │ Speed       │ ⚠️ SLOW      │ ✅ FAST      │ 🟡 MEDIUM     │
 *    │ Concat Op   │ ❌ Creates    │ ✅ Modifies  │ ✅ Modifies   │
 *    │             │    new obj   │    in-place  │    in-place  │
 *    │ Memory      │ ❌ HIGH       │ ✅ LOW       │ ✅ LOW        │
 *    │ Allocation  │    overhead  │              │              │
 *    └─────────────┴──────────────┴──────────────┴──────────────┘
 *    
 *    String: Slow because creates new object each time
 *    StringBuilder: Fast (no synchronization overhead)
 *    StringBuffer: Slower than StringBuilder due to synchronization
 * 
 * ==================================================================================
 * 
 * 4. INTERNAL STORAGE
 *    ┌─────────────┬──────────────┬──────────────┬──────────────┐
 *    │ Aspect      │ String       │ StringBuilder│ StringBuffer │
 *    ├─────────────┼──────────────┼──────────────┼──────────────┤
 *    │ Storage     │ String pool  │ Char array   │ Char array   │
 *    │ Default Cap │ N/A          │ 16 chars     │ 16 chars     │
 *    │ Resizing    │ N/A          │ ✅ Auto       │ ✅ Auto      │
 *    │ Factor      │ N/A          │ 1.5x growth  │ 1.5x growth  │
 *    └─────────────┴──────────────┴──────────────┴──────────────┘
 *    
 *    String: Stored in String pool (memory optimization)
 *    StringBuilder: Internal char array (16 default, auto-resizes)
 *    StringBuffer: Internal char array (16 default, auto-resizes)
 * 
 * ==================================================================================
 * 
 * 5. METHOD AVAILABILITY
 *    ┌─────────────┬──────────────┬──────────────┬──────────────┐
 *    │ Method      │ String       │ StringBuilder│ StringBuffer │
 *    ├─────────────┼──────────────┼──────────────┼──────────────┤
 *    │ append()    │ ❌ NO         │ ✅ YES       │ ✅ YES        │
 *    │ reverse()   │ ❌ NO         │ ✅ YES       │ ✅ YES        │
 *    │ insert()    │ ❌ NO         │ ✅ YES       │ ✅ YES        │
 *    │ delete()    │ ❌ NO         │ ✅ YES       │ ✅ YES        │
 *    │ replace()   │ ❌ NO         │ ✅ YES       │ ✅ YES        │
 *    │ substring() │ ✅ YES        │ ✅ YES       │ ✅ YES        │
 *    │ length()    │ ✅ YES        │ ✅ YES       │ ✅ YES        │
 *    │ concat()    │ ✅ YES        │ ✅ YES       │ ✅ YES        │
 *    └─────────────┴──────────────┴──────────────┴──────────────┘
 * 
 * ==================================================================================
 * 
 * 6. USE CASES & WHEN TO USE
 *    
 *    STRING:
 *    ✅ When you need fixed, immutable text
 *    ✅ When you don't need frequent modifications
 *    ✅ When using as HashMap/TreeMap keys (immutable guarantee)
 *    ✅ When you want thread-safe strings by default
 *    Example: String name = "John";
 *    
 *    STRINGBUILDER:
 *    ✅ When you need frequent string concatenations (single-threaded)
 *    ✅ When performance is critical in non-concurrent code
 *    ✅ When building dynamic strings in loops
 *    ✅ Inside methods where only one thread accesses it
 *    ❌ NOT for multi-threaded environments
 *    Example: Looping 1000 times: result += "Hello"
 *    
 *    STRINGBUFFER:
 *    ✅ When you need frequent modifications in multi-threaded code
 *    ✅ When multiple threads access and modify the same string
 *    ✅ In shared resources that need concurrent access
 *    ❌ Use StringBuilder if performance is more critical (no threading needed)
 *    Example: Shared logging buffer in multi-threaded server
 * 
 * ==================================================================================
 * 
 * 7. MEMORY & TIME COMPLEXITY
 *    
 *    STRING CONCATENATION in loop (n iterations):
 *    ┌──────────────┬─────────────┬────────────────┐
 *    │ Operation    │ Time        │ Objects Created│
 *    ├──────────────┼─────────────┼────────────────┤
 *    │ String +=    │ O(n²)       │ n objects      │
 *    │ StringBuilder│ O(n)        │ 1 object       │
 *    │ StringBuffer │ O(n)        │ 1 object       │
 *    └──────────────┴─────────────┴────────────────┘
 *    
 *    Example: Concatenating 1000 strings
 *    - String: Creates 1000 intermediate objects (VERY SLOW & WASTEFUL)
 *    - StringBuilder: Creates 1 object (FAST)
 *    - StringBuffer: Creates 1 object (FAST but slightly slower due to sync)
 * 
 * ==================================================================================
 * 
 * 8. EXAMPLE SCENARIOS
 *    
 *    Scenario 1: Simple print statement
 *    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *    String greeting = "Hello " + "World";           // FINE - uses compiler optimization
 *    
 *    Scenario 2: Loop building string (1000 times)
 *    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *    ❌ BAD:    String result = ""; 
 *               for(int i=0; i<1000; i++) result += "a";  // Creates 1000 objects!
 *    
 *    ✅ GOOD:   StringBuilder sb = new StringBuilder();
 *               for(int i=0; i<1000; i++) sb.append("a"); // Creates 1 object
 *    
 *    Scenario 3: Multi-threaded logging
 *    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *    ✅ BEST:   StringBuffer log = new StringBuffer();
 *               Thread t1 = new Thread(() -> log.append("log1"));
 *               Thread t2 = new Thread(() -> log.append("log2"));
 *               // Thread-safe, no data corruption
 * 
 * ==================================================================================
 * 
 * 9. QUICK DECISION GUIDE
 *    
 *    Are you concatenating strings in a loop?
 *    ├─ YES, Single-threaded    → Use StringBuilder ✅
 *    ├─ YES, Multi-threaded     → Use StringBuffer ✅
 *    └─ NO, few times           → Use String ✅
 *    
 *    Is thread-safety critical?
 *    ├─ YES                      → Use StringBuffer ✅
 *    └─ NO                       → Use StringBuilder (faster) ✅
 *    
 *    Do you need to modify the string frequently?
 *    ├─ YES                      → Use StringBuilder/StringBuffer ✅
 *    └─ NO                       → Use String ✅
 * 
 * ==================================================================================
 * 
 * 10. PERFORMANCE METRICS (Approximate)
 *     
 *     Concatenating 10,000 strings:
 *     ┌──────────────┬──────────────┬──────────┐
 *     │ Method       │ Time (ms)    │ Memory   │
 *     ├──────────────┼──────────────┼──────────┤
 *     │ String +=    │ ~5000 ms ❌   │ Very High│
 *     │ StringBuilder│ ~5 ms ✅      │ Low      │
 *     │ StringBuffer │ ~10 ms ✅     │ Low      │
 *     └──────────────┴──────────────┴──────────┘
 *     
 *     StringBuilder is ~1000x FASTER than String concatenation!
 * 
 * ==================================================================================
 * 
 * 11. IMPORTANT NOTES
 *     
 *     ⚠️ String Compiler Optimization:
 *        String result = "Hello" + "World";  // Compiler combines to "HelloWorld" at compile time
 *        This is NOT concatenation at runtime, so it's efficient.
 *     
 *     ⚠️ Method Chaining:
 *        StringBuilder only: sb.append("a").append("b").reverse();  // ✅ Method chaining
 *        StringBuffer also supports this.
 *     
 *     ⚠️ StringBuffer in Modern Java:
 *        Rarely used anymore - only use if you explicitly need thread-safety.
 *        Most code is single-threaded or uses proper concurrency patterns.
 * 
 * ==================================================================================
 */
