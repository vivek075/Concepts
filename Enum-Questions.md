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
