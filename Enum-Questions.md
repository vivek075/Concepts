What is an enum in Java, and how is it different from a class or interface?

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
