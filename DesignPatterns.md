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
---
# Bounded Contexts in Microservices

Bounded contexts are a core concept in Domain-Driven Design (DDD) and play a crucial role in the architecture of microservices. They help in defining clear boundaries within which a particular domain model is valid and consistent, ensuring that each microservice has a well-defined scope and responsibility.

A bounded context represents a specific responsibility and area of a larger domain. Within a bounded context, all models, logic, and data are consistent and follow the same rules. It helps to avoid ambiguity and conflicts that might arise from having multiple models and interpretations of the same concepts in different parts of the system.

**Benefits of Bounded Contexts in Microservices**

Clear Boundaries: Ensures that each microservice has a clear, well-defined responsibility.

Modularity: Promotes modularity and independence between services, allowing easier maintenance and scalability.

Consistency: Maintains consistency within each bounded context by avoiding the overlap of domain logic and data.

Decoupling: Encourages loose coupling between different parts of the system, making the overall architecture more flexible and resilient to changes.

**Bounded Contexts in a Banking Trading System**

In a banking trading system, the application can be divided into several bounded contexts, each representing a distinct area of functionality. Here are some examples of bounded contexts:

1. Trade Execution Context:

Responsibility: Handling the execution of trades.

Entities: Trade, Order, ExecutionReport.

Services: TradeExecutionService, OrderService.

2. Risk Management Context:

Responsibility: Managing risk associated with trades.

Entities: RiskAssessment, Position, Exposure.

Services: RiskAssessmentService, PositionService.

3. Settlement Context:

Responsibility: Settling trades after execution.

Entities: Settlement, Payment, Ledger.

Services: SettlementService, PaymentService.

4. Market Data Context:

Responsibility: Providing market data such as prices and rates.

Entities: MarketData, Price, Rate.

Services: MarketDataService, PriceService.

5. Compliance Context:

Responsibility: Ensuring trades comply with regulatory requirements.

Entities: ComplianceCheck, Report, AuditTrail.

Services: ComplianceService, ReportingService.

Interactions Between Bounded Contexts

Each bounded context operates independently but may need to interact with other contexts. This interaction is typically managed through well-defined APIs or asynchronous messaging (events).

Example Interaction:

Trade Execution and Risk Management:

When a trade is executed, the TradeExecutionService publishes a TradeExecutedEvent.

The RiskAssessmentService subscribes to this event to assess the risk of the new trade.

Trade Execution Context:
```
public class TradeExecutionService {
    private final TradeRepository tradeRepository;
    private final EventPublisher eventPublisher;

    public TradeExecutionService(TradeRepository tradeRepository, EventPublisher eventPublisher) {
        this.tradeRepository = tradeRepository;
        this.eventPublisher = eventPublisher;
    }

    public void executeTrade(Trade trade) {
        tradeRepository.save(trade);
        eventPublisher.publish(new TradeExecutedEvent(trade));
    }
}
```
Risk Management Context:
```
public class RiskAssessmentService {
    private final PositionRepository positionRepository;
    private final RiskCalculator riskCalculator;

    @EventListener
    public void handleTradeExecuted(TradeExecutedEvent event) {
        Trade trade = event.getTrade();
        Position position = positionRepository.findByTrade(trade);
        riskCalculator.calculateRisk(position);
    }
}
```
