# What is an enum in Java, and how is it different from a class or interface?

An enum in Java is a special data type used to define a collection of constants.

Key Differences:

Class: A class can have fields, methods, and constructors; enums can too but with restrictions. For instance, enum constants are implicitly public static final.

Interface: Interfaces define behavior with methods; enums cannot implement multiple interfaces but can implement one.

Enum constants are type-safe, whereas int or String constants used in older languages are not. `enums` ensure correctness and safety in code compared to traditional constants (`int` or `String`).

Type Safety in Enums

Definition: Type safety means that a variable can only hold values of a specific type, and the compiler enforces this rule.

In Java, enums are a distinct type, so the compiler ensures that only valid values of the enum type can be assigned to a variable.

```
enum Day {
    MONDAY, TUESDAY, WEDNESDAY
}

Day today = Day.MONDAY;  // Correct: Type-safe assignment
today = "Monday";       // Compilation Error: Incompatible types
```

In this example:

The variable today can only accept values of the Day enum type.

Any attempt to assign a String or other types will result in a compile-time error, preventing bugs.

Lack of Type Safety in int or String Constants

Before enums, developers often used int or String constants to represent categories, states, or types. This approach lacks type safety because any int or String value could be mistakenly assigned.

Example Without Enums:

```
public static final int MONDAY = 1;
public static final int TUESDAY = 2;
public static final int WEDNESDAY = 3;

int today = MONDAY;        // Correct
today = 100;               // Incorrect, but compiles
```

In this example:

today is of type int, so the compiler does not restrict values to the intended constants like MONDAY, TUESDAY, etc.

The value 100 is invalid but is allowed by the compiler, potentially leading to bugs.

With Enums:
```
Day today = Day.MONDAY;    // Correct
today = 100;               // Compilation Error: Incompatible types
```

Advantages of Type Safety in Enums

- Error Prevention: Prevents assigning invalid values to variables at compile time.

- Code Clarity: Enums convey intent better than raw int or String constants.

- Readability: Using Day.MONDAY is more expressive than 1 or "Monday".

- Maintainability: Enums are easier to refactor or extend without introducing subtle bugs.

# How are enum constants internally represented in Java?

Enum constants are represented as static final instances of the enum type.

Internally, they are stored in a hidden array and initialized in the static block of the enum class.

Example:
```
enum Color {
    RED, GREEN, BLUE
}

// Internally:
final class Color {
    public static final Color RED = new Color();
    public static final Color GREEN = new Color();
    public static final Color BLUE = new Color();
    private Color() { }
}
```

When you define an enum like this:
```
enum Color {
    RED, GREEN, BLUE
}
```

Three instances of the Color enum are created.

- Instance Creation for Enum Constants:

Each enum constant (RED, GREEN, BLUE) is a static, final instance of the enum type Color.

During class loading, the JVM creates exactly one instance for each enum constant.

This is part of the enum's implementation in the JVM, ensuring that enum constants are immutable and singleton.

- Behind the Scenes: The above enum is equivalent to:
```
final class Color extends Enum<Color> {
    public static final Color RED = new Color("RED", 0);
    public static final Color GREEN = new Color("GREEN", 1);
    public static final Color BLUE = new Color("BLUE", 2);

    private Color(String name, int ordinal) {
        super(name, ordinal);
    }
}
```
Here:

The RED, GREEN, and BLUE constants are instances of the Color class.

The constructor for Color is called exactly three times.

- Enum Characteristics:

Each constant is created once during class initialization and stored in a static array.

The values() method in enums returns this array.
```
for (Color color : Color.values()) {
    System.out.println(color);
}
```

When you define an enum with three constants (RED, GREEN, BLUE), the JVM creates three unique instances of the enum, one for each constant. These instances are singleton and immutable, ensuring that no additional instances of the same enum constants are ever created.

# What is the default superclass of an enum in Java?

The default superclass for all enums is java.lang.Enum.

java.lang.Enum provides methods like name(), ordinal(), and compareTo().

# How does the values() method in an enum work, and where does it come from?

The values() method is automatically generated by the compiler.

The method is static and is unique to the enum class in which it is defined

It returns an array of all constants in the order they are declared.

```
enum Season { WINTER, SPRING, SUMMER, FALL }

for (Season s : Season.values()) {
    System.out.println(s);
}
// Output: WINTER SPRING SUMMER FALL
```
```
enum Color {
    RED, GREEN, BLUE;
}
```
The compiler generates the following equivalent code behind the scenes:
```
final class Color extends Enum<Color> {
    public static final Color RED = new Color("RED", 0);
    public static final Color GREEN = new Color("GREEN", 1);
    public static final Color BLUE = new Color("BLUE", 2);

    private Color(String name, int ordinal) {
        super(name, ordinal);
    }

    // values() method automatically generated by the compiler
    public static Color[] values() {
        return new Color[]{RED, GREEN, BLUE};
    }

    public static Color valueOf(String name) {
        return Enum.valueOf(Color.class, name);
    }
}
```

Method Signature:
```
public static T[] values();
```
Return Type: Returns an array of the enum type (T[]).

Modifiers: The method is public and static.

Stored Internally:

Enum constants are stored in a static array within the enum class.

The values() method directly returns a reference to this array.

How values() Works Internally

Efficient Storage:

When an enum is loaded, the JVM creates a static array of all the constants in declaration order.
This array is used internally by the values() method to retrieve the constants.

Immutable Copy:

The values() method returns a copy of the internal array, ensuring immutability.
This prevents accidental modification of the enum's constant array.

Example:
```
Color[] colors = Color.values();
colors[0] = null; // Will only affect this copy, not the original array
```

Why Is `values()` Not in java.lang.Enum?

The `values()` method is specific to each enum class, not the Enum class. If it were in java.lang.Enum, it would require generics or reflection to determine the correct enum type dynamically. Adding it per enum simplifies the implementation and allows type safety.

# How are enums compiled into bytecode? What does the .class file for an enum look like?

The compiler generates a class file for the enum. Each constant is represented as a public static final instance.

The enum class contains:

A static block for initializing constants.

Methods like values() and valueOf().

The enum constructor is private.

Example Bytecode: For:
```
enum Color { RED, GREEN }
```
Bytecode might include:
```
public final class Color extends java.lang.Enum<Color> {
    public static final Color RED;
    public static final Color GREEN;

    private static final Color[] VALUES;
    
    static {
        RED = new Color("RED", 0);
        GREEN = new Color("GREEN", 1);
        VALUES = new Color[] { RED, GREEN };
    }

    private Color(String name, int ordinal) { super(name, ordinal); }
}
```

# Explain the thread safety of enum instances in Java.
Enum instances are inherently thread-safe because:

They are singleton and created at class-loading time.

Class loading in Java is thread-safe, ensuring that the enum constants are initialized only once.

Example Use Case:
```
enum SingletonEnum {
    INSTANCE;

    public void performAction() {
        System.out.println("Thread-safe action performed!");
    }
}
```
```
enum Color {
    RED, GREEN, BLUE;
}
```
Only one instance of Color.RED, Color.GREEN, and Color.BLUE exists throughout the JVM lifecycle.

These instances are created once and remain immutable and shared across all threads.

**Immutability of Enum Constants**

Immutability ensures that once an enum constant is created, it cannot be changed.

Enums are implicitly final in Java, which means that the enum constants cannot be reassigned or modified after they are created. This immutability is a key reason why enum instances are thread-safe.

Since enum constants cannot change after initialization, multiple threads can safely access them without worrying about synchronization.

**Enum with Instance Fields**
In some cases, an enum can have instance fields and methods. However, even then, thread safety is ensured as long as the fields are immutable.

Consider this example:
```
enum Day {
    MONDAY("First day"), TUESDAY("Second day");

    private final String description;

    Day(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
```
The `description` field is `final` and assigned at the time of enum constant creation.

Once the enum constants are created, their **state cannot change**, so there are no concerns about thread-safety regarding the field values.

**Use of Enum in Singleton Pattern**

Enums are frequently used in singleton design patterns because they automatically ensure thread safety. Since the enum constants are guaranteed to be created only once and are globally accessible, enums provide a simple and effective way to implement a singleton pattern without needing additional synchronization.

Example:
```
enum Singleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("Singleton instance method.");
    }
}
```
In this case, `Singleton.INSTANCE` is a thread-safe singleton. The JVM ensures that `INSTANCE` is created only once, and all threads that access it will see the same instance.

**Thread-Safety and Enums with Mutable State**

If an enum contains mutable state (fields that can be changed after the enum is initialized), then it can break thread safety.

For example, **this is not thread-safe**:
```
enum UnsafeEnum {
    INSTANCE;

    private List<String> data = new ArrayList<>();

    public void addData(String value) {
        data.add(value);
    }

    public List<String> getData() {
        return data;
    }
}
```
Here:

The `data` field is mutable. Multiple threads can modify it concurrently, leading to potential issues like race conditions.

To make this thread-safe, you would need to **synchronize** access to the mutable field or use a thread-safe collection (e.g., `CopyOnWriteArrayList`).

# How can you associate fields and methods with enum constants? Provide an example.
You can associate fields and methods by defining instance variables and constructors in the enum. Each constant can have its own associated data.
```
enum Day {
    MONDAY("Start of the week"), 
    FRIDAY("End of the work week");

    private String description;

    // Constructor
    Day(String description) {
        this.description = description;
    }

    // Getter
    public String getDescription() {
        return description;
    }
}

System.out.println(Day.MONDAY.getDescription()); // Output: Start of the week
```

# What are the advantages of using a constructor in an enum?
Custom Fields: Allows associating data with each constant.

Initialization: Simplifies initialization of complex constants.

Type-Safe Behavior: Constants can encapsulate behavior or properties.

# How can you override methods for individual enum constants?
You can override methods by defining the method in the enum and providing constant-specific implementations.
```
enum Operation {
    ADD {
        @Override
        public int apply(int x, int y) {
            return x + y;
        }
    },
    MULTIPLY {
        @Override
        public int apply(int x, int y) {
            return x * y;
        }
    };

    public abstract int apply(int x, int y);
}
System.out.println(Operation.ADD.apply(2, 3)); // Output: 5
```

# Is it possible to have an abstract method in an enum? How would you implement it?
Yes, enums can have abstract methods, which must be implemented by each constant.
```
enum Shape {
    CIRCLE {
        @Override
        public double area(double radius) {
            return Math.PI * radius * radius;
        }
    },
    SQUARE {
        @Override
        public double area(double side) {
            return side * side;
        }
    };

    public abstract double area(double dimension);
}
```

# Can enums implement interfaces? If so, provide an example.
Yes, enums can implement interfaces, allowing them to conform to a common contract.
```
interface Drawable {
    void draw();
}

enum Shape implements Drawable {
    CIRCLE {
        public void draw() { System.out.println("Drawing Circle"); }
    },
    SQUARE {
        public void draw() { System.out.println("Drawing Square"); }
    }
}
```

# Can an enum extend another enum? Why or why not?
No, enums cannot extend other enums because they implicitly extend java.lang.Enum. Java does not allow multiple inheritance.

# How can you convert a string to an enum constant? What are the pitfalls of this approach?
Use Enum.valueOf(Class<T>, String). A pitfall is that it throws IllegalArgumentException if the string doesn’t match any constant.
```
Day day = Day.valueOf("MONDAY");
System.out.println(day); // Output: MONDAY
```

# Can you use enums in collections like HashMap or TreeSet? Why are they efficient?
Yes, enums can be used because they implement `Comparable` and `hashCode()`.

Efficiency comes from their immutable, unique nature and reduced hash collisions.

# How do enums ensure type safety compared to integer constants?
Enums are strongly typed and restrict valid values to predefined constants.

# Are enums stored in the heap or the method area of JVM memory? Explain.
Enums are stored in the method area, as they are constants initialized at class loading.

# How does the JVM optimize enums for memory and performance?
Uses a single instance per constant.

Backed by an array, avoiding hash calculations.

# What happens when you serialize an enum in Java?
Enum serialization only saves the **name** of the constant. During deserialization, the JVM reconstructs the instance using the `name`.

# How does enum deserialization work? Can it break the singleton property?
Enum deserialization uses the `readResolve()` method to ensure singleton instances.

Enums in Java are designed to be singleton instances by default. The Java Virtual Machine (JVM) ensures that there is exactly one instance of each enum constant, even in the case of deserialization.

**Default Serialization Mechanism for Enums**

- Enums implement the `java.io.Serializable` interface by default.

- When an enum is serialized, only the **name of the constant** is serialized (not its fields or state).

- During deserialization, the JVM uses the name to look up the corresponding enum constant.

**Enum Deserialization and Singleton Property**
Enums have special handling during deserialization to preserve their singleton property:

`readResolve()` Implementation: Java's serialization mechanism ensures that the singleton property of enums is maintained by implementing a special method called `readResolve()` internally for enums.

Instead of creating a new instance during deserialization, the `readResolve()` method ensures that the existing enum instance is returned.

Example:
Consider this enum:
```
enum Color {
    RED, GREEN, BLUE;
}
```
When deserialized, the `readResolve()` method is called internally, and it ensures that the deserialized constant refers to the same instance as the one in memory.

**Why Deserialization Cannot Break Singleton for Enums**
Explanation of `readResolve()`:

The `readResolve()` method is defined in `java.lang.Enum` as:
```
protected final Object readResolve() {
    return Enum.valueOf(getDeclaringClass(), name());
}
```
This method:

Retrieves the enum type (via `getDeclaringClass()`).
Looks up the constant by name (via `Enum.valueOf()`).
Returns the existing instance of the enum constant.
Since the JVM intercepts the deserialization process and enforces the use of `readResolve()`, **new instances are never created during deserialization**, maintaining the singleton property of the enum.

**Can Enum Singleton Property Be Broken?**
Under normal circumstances, the singleton property of enums cannot be broken due to their strict handling by the JVM. However, it can theoretically be broken under special or unsupported scenarios:

Using Reflection
Reflection can bypass the singleton property of enums by accessing and invoking the private constructor of an enum, which is normally not allowed.
```
import java.lang.reflect.Constructor;

enum Color {
    RED, GREEN, BLUE;
}

public class EnumReflection {
    public static void main(String[] args) throws Exception {
        Constructor<Color> constructor = Color.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Color anotherRed = constructor.newInstance("RED", 0);

        System.out.println(Color.RED == anotherRed); // Prints false, singleton broken!
    }
}
```
Why This Breaks Singleton:

The enum's private constructor is invoked directly.

This bypasses the JVM's protection mechanisms.

Custom Serialization Code

If someone overrides the serialization process manually (using `writeObject()` or `readObject()`), they could potentially create new instances. However, this is generally discouraged and should not occur in standard usage.

Serialization with a Different ClassLoader

Enums rely on the same classloader for maintaining their singleton property. If deserialization happens with a different classloader (e.g., in distributed systems), it might break the singleton property.

Serialization and Deserialization of Enums
```
import java.io.*;

enum Color {
    RED, GREEN, BLUE;
}

public class EnumSerializationExample {
    public static void main(String[] args) throws Exception {
        // Serialize enum constant
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("color.ser"));
        oos.writeObject(Color.RED);
        oos.close();

        // Deserialize enum constant
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("color.ser"));
        Color deserializedColor = (Color) ois.readObject();
        ois.close();

        // Check if the deserialized instance is the same as the original
        System.out.println(Color.RED == deserializedColor); // Prints true
    }
}
```
**Why Enums Are Reliable for Singleton Implementation**
Using enums for singleton patterns is often preferred because:

The JVM guarantees that enum constants are immutable and singleton.

The `readResolve()` mechanism ensures that deserialization cannot create new instances.

Reflection-based attacks are restricted because enum constructors are private, and attempting to create an enum instance via reflection throws a java.lang.IllegalArgumentException.

# Explain how enums are used with annotations for input validation.
Enums combined with annotations provide a powerful mechanism to enforce constraints and validate inputs at runtime or during compile-time checks. This approach is especially useful when the set of valid inputs is fixed and predefined.

Here’s an in-depth exploration of how enums and annotations can be used together for input validation:

**Why Use Enums with Annotations for Validation?**

Enums Represent Fixed Sets: Enums are perfect for representing a fixed, predefined set of values (e.g., days of the week, payment methods, etc.). They ensure type safety and eliminate the risks of using plain strings or integers.

Annotations Simplify Validation: Annotations make it easy to declare and enforce validation rules directly in the code without writing boilerplate validation logic in multiple places.

Combining these two concepts:

The enum provides the set of valid values.
The annotation defines a validation rule that can be applied to fields or method parameters.

**How to Use Enums with Custom Annotations for Validation**

Step 1: Define an Enum

Define an enum to represent the valid set of values.
```
public enum Color {
    RED, GREEN, BLUE;
}
```
Step 2 + 4: Create a Custom Annotation / Link Annotation with the Validator

Define a custom annotation for validation that refers to the enum. & Associate the annotation with the validator class using @Constraint.
```
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ColorValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidColor {
    String message() default "Invalid color. Allowed values are RED, GREEN, BLUE.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

Step 3: Implement the Validation Logic

To validate the annotated field or parameter, implement a validator class.

Use a library like Jakarta Bean Validation (JSR 380) with ConstraintValidator.

Or, write a custom validation framework.

Here’s an example using Bean Validation:
```
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ColorValidator implements ConstraintValidator<ValidColor, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Allow null values if required
        }

        try {
            Color.valueOf(value.toUpperCase());
            return true; // Valid if the value exists in the enum
        } catch (IllegalArgumentException e) {
            return false; // Invalid if the value is not in the enum
        }
    }
}
```

Step 5: Use the Annotation

Apply the annotation to fields or method parameters.
```
import jakarta.validation.constraints.NotNull;

public class Product {
    @NotNull
    @ValidColor
    private String color;

    public Product(String color) {
        this.color = color;
    }

    // Getter and Setter
}
```

Step 6: Validate Inputs

Use a validation framework like Hibernate Validator to validate the object.
```
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class ValidationExample {
    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Product product = new Product("YELLOW"); // Invalid color
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Product> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            System.out.println("Validation passed!");
        }
    }
}
```
Output
```
Invalid color. Allowed values are RED, GREEN, BLUE.
```

**Benefits of Using Enums with Annotations**

Type Safety: Enums eliminate risks of invalid inputs by restricting values to a predefined set.

Reusable Validation: Once written, the validation logic can be reused across multiple fields or parameters.

Readability: The combination of enums and annotations makes the code self-documenting.

Ease of Maintenance: Any changes to the valid set of values (enum) automatically propagate to all annotated fields.

Dependecies:
```
<dependencies>
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>7.0.1.Final</version>
        </dependency>
        <dependency>
            <groupId>jakarta.el</groupId>
            <artifactId>jakarta.el-api</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>jakarta.el</artifactId>
            <version>4.0.1</version>
        </dependency>
</dependencies>
```

# How would you ensure lazy initialization of a resource inside an enum constant?
Lazy initialization inside an enum constant is a useful technique to delay the creation of a resource until it is actually needed. This approach avoids unnecessary resource usage during the initialization of the enum constants.

Here’s how you can ensure lazy initialization of a resource inside an enum constant in Java:

Approach: Use a Lazy Holder or Supplier

The recommended way to implement lazy initialization is to use a nested static holder class or a java.util.function.Supplier.

**1. Using a Nested Static Holder Class**

A static nested class provides a clean, thread-safe way to lazily initialize a resource. Since nested static classes are loaded only when they are first accessed, this ensures the resource is created only when needed.
```
enum ResourceEnum {
    INSTANCE;

    // Nested static class for lazy initialization
    private static class ResourceHolder {
        private static final Resource RESOURCE = new Resource();
    }

    // Getter for the resource
    public Resource getResource() {
        return ResourceHolder.RESOURCE;
    }
}

class Resource {
    public Resource() {
        System.out.println("Resource initialized.");
    }
}
```
```
public class LazyInitializationExample {
    public static void main(String[] args) {
        System.out.println("Before accessing resource.");
        Resource resource = ResourceEnum.INSTANCE.getResource();
        System.out.println("Resource accessed: " + resource);
    }
}
```
Output:
```
Before accessing resource.
Resource initialized.
Resource accessed: com.example.Resource@<hash>
```

Why It Works:

The static `ResourceHolder` class is loaded only when `getResource()` is called for the first time. This ensures that `Resource` is initialized lazily and in a thread-safe manner.

**2. Using a Supplier for Lazy Initialization**

A Supplier is a functional interface that provides a lazy and thread-safe way to initialize resources.

```
import java.util.function.Supplier;

enum ResourceEnum {
    INSTANCE;

    // Lazy resource initialization using Supplier
    private final Supplier<Resource> resourceSupplier = this::createResource;

    private Resource createResource() {
        System.out.println("Resource initialized.");
        return new Resource();
    }

    public Resource getResource() {
        return resourceSupplier.get();
    }
}

class Resource {
    public Resource() {
        System.out.println("Resource constructor called.");
    }
}
```
```
public class LazyInitializationExample {
    public static void main(String[] args) {
        System.out.println("Before accessing resource.");
        Resource resource = ResourceEnum.INSTANCE.getResource();
        System.out.println("Resource accessed: " + resource);
    }
}
```
Output:
```
Before accessing resource.
Resource initialized.
Resource accessed: com.example.Resource@<hash>
```
Thread Safety of These Approaches

Static Holder Class:

The JVM guarantees that a class is loaded and initialized in a thread-safe manner. Therefore, using a nested static holder ensures thread safety without requiring synchronization.

Supplier:

The supplier-based approach is thread-safe as long as the resource is immutable or the initialization logic does not depend on external mutable state.

# How can you make an enum extensible?
In Java, enums are inherently non-extensible because they are implicitly final. This design ensures that an enum type represents a fixed set of constants, maintaining their integrity and type safety. However, there are certain patterns and workarounds that simulate extensibility when it’s necessary to expand the behavior or values associated with an enum-like construct.

Here’s how you can achieve a semblance of extensibility for enums:

**1. Use a Combination of Enum and a Supporting Registry**

You can maintain a registry or a mapping to extend the set of values dynamically at runtime. The registry holds additional values that mimic enum constants.
```
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Status {
    SUCCESS("Operation was successful"),
    FAILURE("Operation failed");

    private final String description;

    private static final Map<String, Status> EXTENDED_STATUSES = new HashMap<>();

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Method to register new "enum-like" statuses
    public static void registerStatus(String name, String description) {
        EXTENDED_STATUSES.put(name, new Status(description) {});
    }

    // Method to retrieve all statuses
    public static Map<String, Status> getAllStatuses() {
        Map<String, Status> statuses = new HashMap<>();
        for (Status status : Status.values()) {
            statuses.put(status.name(), status);
        }
        statuses.putAll(EXTENDED_STATUSES);
        return Collections.unmodifiableMap(statuses);
    }
}
```
```
public class Main {
    public static void main(String[] args) {
        // Access built-in enums
        System.out.println(Status.SUCCESS.getDescription());

        // Register a new status
        Status.registerStatus("PENDING", "Operation is pending");

        // Access all statuses, including the registered one
        System.out.println(Status.getAllStatuses());
    }
}
```
**2. Use Interfaces for Extensible Behavior**

Instead of relying on enums for extensibility, use an interface to define common behavior and allow enums or other implementations to extend it.
```
public interface ErrorCode {
    String getCode();
    String getMessage();
}

public enum StandardErrorCode implements ErrorCode {
    NOT_FOUND("404", "Resource not found"),
    SERVER_ERROR("500", "Internal server error");

    private final String code;
    private final String message;

    StandardErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

// Custom implementation of ErrorCode
public class CustomErrorCode implements ErrorCode {
    private final String code;
    private final String message;

    public CustomErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
```
```
public class Main {
    public static void main(String[] args) {
        ErrorCode errorCode1 = StandardErrorCode.NOT_FOUND;
        System.out.println(errorCode1.getMessage());

        ErrorCode customCode = new CustomErrorCode("600", "Custom error");
        System.out.println(customCode.getMessage());
    }
}
```
**3. Use Abstract Classes with a Static Registry**

If more complex extensibility is required, you can use an abstract class with static methods for predefined constants and allow dynamic addition of new constants.
```
import java.util.HashMap;
import java.util.Map;

public abstract class ExtensibleEnum {
    private final String name;

    private static final Map<String, ExtensibleEnum> VALUES = new HashMap<>();

    protected ExtensibleEnum(String name) {
        this.name = name;
        VALUES.put(name, this);
    }

    public String getName() {
        return name;
    }

    public static ExtensibleEnum valueOf(String name) {
        return VALUES.get(name);
    }

    public static Map<String, ExtensibleEnum> values() {
        return new HashMap<>(VALUES);
    }
}

// Predefined constants
class Color extends ExtensibleEnum {
    public static final Color RED = new Color("RED");
    public static final Color BLUE = new Color("BLUE");

    private Color(String name) {
        super(name);
    }

    public static void addColor(String name) {
        new Color(name);
    }
}
```
```
public class Main {
    public static void main(String[] args) {
        // Access predefined constants
        System.out.println(Color.RED.getName());

        // Add a custom constant
        Color.addColor("GREEN");

        // Access all constants
        System.out.println(Color.values());
    }
}
```
**4. Use Enum with Polymorphic Behavior**

You can use polymorphism within enums to provide extensible behavior, but this works only for predefined constants. For runtime extensions, consider combining with one of the above methods.
```
public enum Operation {
    ADD {
        @Override
        public int apply(int x, int y) {
            return x + y;
        }
    },
    MULTIPLY {
        @Override
        public int apply(int x, int y) {
            return x * y;
        }
    };

    public abstract int apply(int x, int y);
}
```
```
public class Main {
    public static void main(String[] args) {
        int result = Operation.ADD.apply(3, 4);
        System.out.println("Result: " + result);
    }
}
```
# Can enums have cyclic dependencies?
Enums in Java can have cyclic dependencies, but these cycles must be resolved indirectly through references to shared data structures or external methods. Direct cyclic dependencies, where one enum constant directly refers to another during initialization, can lead to initialization order issues or NullPointerException at runtime due to incomplete initialization.

Understanding Cyclic Dependencies in Enums

**1. Direct Reference Cycle (Not Allowed During Initialization)**

When an enum constant references another enum constant directly in its constructor or initialization, it can cause problems due to the order of initialization. During enum constant initialization, all enum constants are created in a defined order. If one constant tries to reference another before it is fully initialized, it will result in runtime issues.

Example (Problematic):
```
enum Node {
    A(B),  // Error: B is not initialized yet
    B(A);

    private final Node linkedNode;

    Node(Node linkedNode) {
        this.linkedNode = linkedNode;
    }

    public Node getLinkedNode() {
        return linkedNode;
    }
}
```
Why It Fails:

A tries to initialize with B, but B is not fully initialized yet.
This leads to a NullPointerException.

**2. Resolving Cyclic Dependencies Using Post-Initialization**

You can resolve cyclic dependencies by deferring the initialization of dependent references until after the enum constants have been created. Use a separate method to set up the relationships.

Example (Correct Approach):
```
enum Node {
    A, B;

    private Node linkedNode;

    public void setLinkedNode(Node linkedNode) {
        this.linkedNode = linkedNode;
    }

    public Node getLinkedNode() {
        return linkedNode;
    }

    // Static block to establish relationships after initialization
    static {
        A.setLinkedNode(B);
        B.setLinkedNode(A);
    }
}
```
```
public class CyclicEnumExample {
    public static void main(String[] args) {
        System.out.println("A's linked node: " + Node.A.getLinkedNode());
        System.out.println("B's linked node: " + Node.B.getLinkedNode());
    }
}
```
```
A's linked node: B
B's linked node: A
```

**3. Using External Structures for Cyclic Dependencies**

Instead of embedding dependencies within the enum constants, you can use an external data structure like a Map to define relationships.
```
import java.util.EnumMap;

enum Node {
    A, B, C;

    // Define relationships externally
    private static final EnumMap<Node, Node> linkedNodes = new EnumMap<>(Node.class);

    static {
        linkedNodes.put(A, B);
        linkedNodes.put(B, C);
        linkedNodes.put(C, A); // Cycle: A -> B -> C -> A
    }

    public Node getLinkedNode() {
        return linkedNodes.get(this);
    }
}
```
```
public class ExternalDependencyExample {
    public static void main(String[] args) {
        System.out.println("A's linked node: " + Node.A.getLinkedNode());
        System.out.println("B's linked node: " + Node.B.getLinkedNode());
        System.out.println("C's linked node: " + Node.C.getLinkedNode());
    }
}
```
```
A's linked node: B
B's linked node: C
C's linked node: A
```
**Key Considerations**

Initialization Order:

Enum constants are initialized in the order they are declared.
Referencing another constant before it is fully initialized will cause issues.

Thread Safety:

If cyclic dependencies are resolved using post-initialization, ensure the setup is thread-safe if accessed concurrently.

Design Simplicity:

Use external structures like EnumMap to manage relationships for cleaner code and better maintainability.