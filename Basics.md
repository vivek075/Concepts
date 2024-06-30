# Shallow Copy
- It is fast as no new memory is allocated.
- Changes in one entity is reflected in other entity.
- The default version of the clone() method supports shallow copy.
- A shallow copy is less expensive.
- Cloned object and the original object are not disjoint.
# Deep Copy
- It is slow as new memory is allocated.
- Changes in one entity are not reflected in changes in another identity.
- In order to make the clone() method support the deep copy, one has to override the clone() method.
- Deep copy is highly expensive.
- Cloned object and the original object are disjoint.
# What is the order of execution of instance initialization blocks, static initialization blocks, and constructors?
The order of execution is:
- Static initialization blocks (when the class is loaded).
- Instance initialization blocks (when an instance is created).
- Constructors (after the instance initialization blocks).

```
class Example {
    static int a;
    int b;
    // Static initialization block
    static {
        a = 1;
        System.out.println("Static Initialization Block");
    }
    // Instance initialization block
    {
        b = 2;
        System.out.println("Instance Initialization Block");
    }
    Example() {
        System.out.println("Constructor");
    }
    public static void main(String[] args) {
        new Example();
    }
}
```
Output : 
Static Initialization Block

Instance Initialization Block

Constructor
# What happens if there is an exception in a static initialization block?
If an exception occurs in a static initialization block, it prevents the class from being loaded, resulting in a `ExceptionInInitializerError`. This error occurs the first time the class is accessed.

```
class Example {
    static {
        System.out.println("Static Block");
        if (true) {
            throw new RuntimeException("Exception in static block");
        }
    }
    
    public static void main(String[] args) {
        try {
            new Example();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
```
Output:

Static Block
java.lang.ExceptionInInitializerError
Caused by: java.lang.RuntimeException: Exception in static block
    at Example.<clinit>(Example.java:6)
# Can you access instance variables in a static initialization block?
No, instance variables cannot be accessed directly in a static initialization block because static blocks do not belong to an instance of the class. They are executed when the class is loaded and no instance exists at that point.
# Can you call a static method in an instance initialization block?
Yes, you can call a static method in an instance initialization block since static methods belong to the class and can be accessed without an instance.
```
class Example {
    static void staticMethod() {
        System.out.println("Static Method");
    }
    
    {
        staticMethod(); // Calling static method
        System.out.println("Instance Block");
    }
    
    Example() {
        System.out.println("Constructor");
    }
    
    public static void main(String[] args) {
        new Example();
    }
}
```
Output:

Static Method
Instance Block
Constructor
