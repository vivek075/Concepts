# SOLID Principles

The SOLID principles are a set of five design principles in object-oriented programming that aim to make software designs more understandable, flexible, and maintainable.

1. Single Responsibility Principle (SRP)
A class should have only one reason to change, meaning it should have only one job or responsibility.

```
public class Invoice {
    private InvoiceData invoiceData;
    
    public void calculateTotal() {
        // Calculation logic
    }
}

public class InvoicePrinter {
    public void printInvoice(InvoiceData invoiceData) {
        // Printing logic
    }
}

public class InvoiceRepository {
    public void saveInvoice(InvoiceData invoiceData) {
        // Database saving logic
    }
}
Explanation: In this example, Invoice is responsible for invoice calculations, InvoicePrinter handles printing, and InvoiceRepository manages database operations. Each class has a single responsibility.
```
2. Open/Closed Principle (OCP)
Software entities (classes, modules, functions, etc.) should be open for extension but closed for modification.
```
public abstract class Shape {
    public abstract double area();
}

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

public class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}

public class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.area();
    }
}
Explanation: New shapes can be added by extending the Shape class without modifying existing code. This adheres to the OCP.
```
3. Liskov Substitution Principle (LSP)
Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.
```
public class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

public class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly");
    }
}
Explanation: This example violates LSP because an Ostrich cannot fly, and thus cannot be substituted for a Bird. A better design would avoid such a hierarchy.
```
4. Interface Segregation Principle (ISP)
Clients should not be forced to depend on methods they do not use. It is better to have multiple specific interfaces than a single general-purpose interface.
```
public interface Printer {
    void print(Document document);
}

public interface Scanner {
    void scan(Document document);
}

public class MultiFunctionPrinter implements Printer, Scanner {
    public void print(Document document) {
        // Print logic
    }

    public void scan(Document document) {
        // Scan logic
    }
}

public class SimplePrinter implements Printer {
    public void print(Document document) {
        // Print logic
    }
}
Explanation: Instead of one large interface, separate Printer and Scanner interfaces are created. MultiFunctionPrinter implements both, while SimplePrinter implements only Printer.
```
5. Dependency Inversion Principle (DIP)
High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions.
```
public interface PaymentProcessor {
    void processPayment(double amount);
}

public class CreditCardProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        // Process credit card payment
    }
}

public class PayPalProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        // Process PayPal payment
    }
}

public class PaymentService {
    private PaymentProcessor paymentProcessor;

    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void pay(double amount) {
        paymentProcessor.processPayment(amount);
    }
}
Explanation: PaymentService depends on the PaymentProcessor interface rather than a concrete class, making it easier to switch between different payment processors.
```
---
# Creational Design Patterns
This design pattern is all about class instantiation. How to effectively create the objects and reuse it and also explains how to control the object creation.

**_Singleton_**

Singleton is a **creational design pattern**, which ensures that only one object of its kind/type exists and provides a single point of access to it for any other code.

Singleton examples in Java core libraries:

`java.lang.Runtime#getRuntime()`

`java.awt.Desktop#getDesktop()`

`java.lang.System#getSecurityManager()`

**Naïve Singleton (single-threaded)**

```
public final class Singleton {
    private static Singleton instance;
    public String value;

    private Singleton(String value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        if (instance == null) {
            instance = new Singleton(value);
        }
        return instance;
    }
}
```
Client Code

```
public class DemoSingleThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
```

**Naïve Singleton (multithreaded)**

```
public class DemoMultiThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
```
**Thread-safe Singleton with lazy loading**

To fix the problem, we have to synchronize threads during first creation of the Singleton object.
```
public final class Singleton {
    // The field must be declared volatile so that double check lock would work correctly.
    private static volatile Singleton instance;

    public String value;

    private Singleton(String value) {
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        // Approach : double-checked locking (DCL). It exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate instances as a result.
        
        Singleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Singleton.class) {
            if (instance == null) {
                instance = new Singleton(value);
            }
            return instance;
        }
    }
}

public class DemoMultiThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
```

**_Factory Design Pattern_**

In factory pattern, we create object without exposing the creation logic to the client and refer to newly created object using a common interface.

In simple words, if we have a super class and sub-classes, based on data provided, we have to return the object of one of the sub-classes, we use a factory pattern.

The basic principle behind this pattern is that at run time, we get an object of similar type based on the parameter we pass.

If object creation code is spread in whole application, and if we need to change the process of object creation then you need to go in each and every place to make necessary changes.

**_Object Pool Pattern_**

Object Pool pattern offer a mechanism to reuse objects and share objects that are expensive to create.

Improve the performance

**_Abstract Factory Pattern_**

Abstract Factory pattern is a super-factory which creates other factories. This factory is also called as Factory of factories.
