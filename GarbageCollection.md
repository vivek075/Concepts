# 1. What is Garbage Collection in Java? Why is it needed?
Garbage Collection (GC) in Java is an automatic memory management process that reclaims memory used by objects that are no longer reachable in the program. It's needed to:
Prevent memory leaks and OutOfMemoryError
Simplify programming (no manual free() as in C++)
Improve application stability

# 2. Describe the Java Memory Model. Which parts are managed by the garbage collector?
The Java Memory Model (JMM) defines how the JVM manages memory:
Heap (GC managed): Eden, Survivor, Old (Tenured), and sometimes Metaspace.
Stack: Per-thread, holds method frames and local variables.
Metaspace (JDK 8+): Stores class metadata; partially GC-managed.
Code Cache: Holds JIT-compiled code.
GC manages mostly the Heap and Metaspace regions.

# 3. Explain the lifecycle of an object from creation to garbage collection.
Object created on the heap via new.
Lives in Eden.
If it survives minor GCs, moves to Survivor.
Eventually promoted to Old Generation if it continues surviving.
When unreachable and eligible for GC, it’s cleared during Minor or Major GC.

# 4. What are strong, weak, soft, and phantom references?
| Type        | GC Eligible?        | Use Case                             |
| ----------- | ------------------- | ------------------------------------ |
| **Strong**  | No (default)        | Regular objects                      |
| **Soft**    | Yes (low memory)    | Memory-sensitive caches              |
| **Weak**    | Yes (any GC)        | Maps where keys shouldn't prevent GC |
| **Phantom** | Yes (post-finalize) | Cleanup actions after GC             |

# 5. What is the difference between finalization and cleaner?
finalize(): Called before GC, deprecated in Java 9+, unpredictable.
Cleaner API: Java 9+, runs outside GC thread, safer for post-mortem cleanup.
