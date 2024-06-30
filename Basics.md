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
