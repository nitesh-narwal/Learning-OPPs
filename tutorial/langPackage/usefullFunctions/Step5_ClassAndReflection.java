package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

import java.lang.reflect.*;

/**
 * STEP 5: CLASS CLASS AND REFLECTION - ADVANCED
 * 
 * Class represents a class or interface in the JVM.
 * Reflection allows runtime inspection and manipulation of classes.
 * 
 * Key Concepts:
 * - Getting Class objects
 * - Class metadata (methods, fields, constructors)
 * - Creating instances at runtime
 * - Advanced metaprogramming
 */

public class Step5_ClassAndReflection {

    public static void main(String[] args) {
        System.out.println("===== STEP 5: CLASS AND REFLECTION - ADVANCED =====\n");

        // ============= 1. GETTING CLASS OBJECTS =============
        System.out.println("1️⃣  GETTING CLASS OBJECTS:\n");

        // Method 1: Using .class
        Class<?> stringClass1 = String.class;
        System.out.println("  String.class = " + stringClass1);

        // Method 2: Using getClass()
        String text = "Hello";
        Class<?> stringClass2 = text.getClass();
        System.out.println("  \"Hello\".getClass() = " + stringClass2);

        // Method 3: Using Class.forName()
        try {
            Class<?> stringClass3 = Class.forName("java.lang.String");
            System.out.println("  Class.forName(\"java.lang.String\") = " + stringClass3);
        } catch (ClassNotFoundException e) {
            System.out.println("  Error: Class not found");
        }

        System.out.println("  Purpose: Obtain Class object for runtime inspection");

        // ============= 2. CLASS METADATA =============
        System.out.println("\n2️⃣  CLASS METADATA:\n");

        Class<?> personClass = Person.class;

        System.out.println("  Class name: " + personClass.getName());
        System.out.println("  Simple name: " + personClass.getSimpleName());
        System.out.println("  Package: " + personClass.getPackage());
        System.out.println("  Is interface: " + personClass.isInterface());
        System.out.println("  Is primitive: " + personClass.isPrimitive());

        System.out.println("  Purpose: Examine class characteristics");

        // ============= 3. GETTING METHODS =============
        System.out.println("\n3️⃣  INSPECTING METHODS:\n");

        System.out.println("  Public methods in " + personClass.getSimpleName() + ":");
        Method[] methods = personClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("    - " + method.getName() + "()");
        }

        System.out.println("  Purpose: Get all methods of a class");

        // ============= 4. GETTING FIELDS =============
        System.out.println("\n4️⃣  INSPECTING FIELDS:\n");

        System.out.println("  Fields in " + personClass.getSimpleName() + ":");
        Field[] fields = personClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("    - " + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("  Purpose: Get all fields of a class");

        // ============= 5. GETTING CONSTRUCTORS =============
        System.out.println("\n5️⃣  INSPECTING CONSTRUCTORS:\n");

        System.out.println("  Constructors in " + personClass.getSimpleName() + ":");
        Constructor<?>[] constructors = personClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.print("    - Person(");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.print(paramTypes[i].getSimpleName());
                if (i < paramTypes.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }

        System.out.println("  Purpose: Examine constructors");

        // ============= 6. CREATING INSTANCES AT RUNTIME =============
        System.out.println("\n6️⃣  CREATING INSTANCES AT RUNTIME:\n");

        try {
            // Get constructor
            Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
            
            // Create instance
            Object instance = constructor.newInstance("Alice", 25);
            System.out.println("  Created instance: " + instance);

        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println("  Purpose: Instantiate classes dynamically");

        // ============= 7. CALLING METHODS AT RUNTIME =============
        System.out.println("\n7️⃣  CALLING METHODS AT RUNTIME:\n");

        try {
            Person person = new Person("Bob", 30);
            
            // Get method
            Method getNameMethod = personClass.getMethod("getName");
            
            // Call method
            Object result = getNameMethod.invoke(person);
            System.out.println("  Invoked getName(): " + result);

            Method getAgeMethod = personClass.getMethod("getAge");
            result = getAgeMethod.invoke(person);
            System.out.println("  Invoked getAge(): " + result);

        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println("  Purpose: Call methods using reflection");

        // ============= 8. MODIFYING FIELDS AT RUNTIME =============
        System.out.println("\n8️⃣  MODIFYING FIELDS AT RUNTIME:\n");

        try {
            Person person = new Person("Charlie", 28);
            System.out.println("  Before: " + person);

            // Get field
            Field nameField = personClass.getDeclaredField("name");
            nameField.setAccessible(true);  // Allow access to private field
            
            // Modify field
            nameField.set(person, "Charles");
            System.out.println("  After modification: " + person);

        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println("  Purpose: Modify object state using reflection");

        // ============= 9. TYPE HIERARCHY =============
        System.out.println("\n9️⃣  TYPE HIERARCHY:\n");

        Class<?> current = personClass;
        System.out.println("  Inheritance chain for " + personClass.getSimpleName() + ":");
        
        while (current != null) {
            System.out.println("    - " + current.getSimpleName());
            current = current.getSuperclass();
        }

        System.out.println("  Purpose: Understand class hierarchy");

        // ============= 10. INTERFACES IMPLEMENTED =============
        System.out.println("\n🔟  INTERFACES:\n");

        Class<?>[] interfaces = personClass.getInterfaces();
        System.out.println("  Interfaces implemented by " + personClass.getSimpleName() + ":");
        
        if (interfaces.length == 0) {
            System.out.println("    (none)");
        } else {
            for (Class<?> iface : interfaces) {
                System.out.println("    - " + iface.getSimpleName());
            }
        }

        // ============= 11. PRACTICAL EXAMPLE: GENERIC CLASS INSPECTOR =============
        System.out.println("\n1️⃣1️⃣  PRACTICAL EXAMPLE: Class Inspector:\n");

        inspectClass(personClass);

        // ============= 12. ANNOTATIONS AND ADVANCED =============
        System.out.println("\n1️⃣2️⃣  ADVANCED: ACCESSING ANNOTATIONS:\n");

        Deprecated[] deprecations = personClass.getAnnotationsByType(Deprecated.class);
        System.out.println("  Deprecated annotations: " + deprecations.length);

        // ============= SUMMARY =====
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Use Class.forName() to dynamically load classes");
        System.out.println("✓ Reflection enables runtime class inspection");
        System.out.println("✓ Create instances dynamically using constructors");
        System.out.println("✓ Call methods dynamically using invoke()");
        System.out.println("✓ Reflection is powerful but slower than direct calls");
    }

    // ============= HELPER METHODS =============

    /**
     * Generic class inspector
     */
    static void inspectClass(Class<?> clazz) {
        System.out.println("  📋 CLASS INSPECTION REPORT:\n");
        System.out.println("  Class: " + clazz.getSimpleName());
        System.out.println("  Package: " + clazz.getPackage());
        
        System.out.println("\n  FIELDS:");
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            System.out.println("    (no fields)");
        } else {
            for (Field field : fields) {
                System.out.println("    - " + field.getType().getSimpleName() + " " + field.getName());
            }
        }

        System.out.println("\n  METHODS:");
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            System.out.println("    (no methods)");
        } else {
            for (Method method : methods) {
                System.out.println("    - " + method.getReturnType().getSimpleName() + " " + method.getName() + "()");
            }
        }

        System.out.println("\n  CONSTRUCTORS:");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.print("    - " + clazz.getSimpleName() + "(");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.print(paramTypes[i].getSimpleName());
                if (i < paramTypes.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }
    }

    // ============= SAMPLE CLASS FOR REFLECTION =============

    /**
     * Simple Person class for reflection examples
     */
    static class Person {
        private String name;
        private int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }

        void setName(String name) {
            this.name = name;
        }

        void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}

