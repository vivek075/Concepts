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

# 6. Types of Garbage Collectors in JVM
Serial GC: Single-threaded, suitable for small apps.

Parallel GC: Multi-threaded, throughput-oriented.

CMS: Concurrent Mark-Sweep (deprecated in Java 14).

G1 GC: Region-based, low-pause collector, default in Java 9+.

ZGC: Sub-millisecond pauses, scales to TBs of heap (JDK 11+).

Shenandoah: Similar to ZGC but from RedHat (JDK 12+).

# 7. How does G1 GC work?
Divides heap into regions (not contiguous generations).

Performs concurrent marking to track live data.

Performs incremental compaction, avoiding full stop-the-world.

Targets predictable low-pause times (configurable via MaxGCPauseMillis).

# 8. Difference: Stop-the-World, Concurrent, Incremental
Stop-the-World (STW): JVM pauses all threads during GC.

Concurrent: GC runs alongside application threads.

Incremental: GC work is divided into small tasks (e.g., G1, ZGC).

# 9. How does JVM determine that an object is unreachable?
Using graph traversal algorithms:

Root Set: Local variables, static fields, JNI references.

If object not reachable via reference chains from GC roots → eligible for GC.

# 10. Explain generational GC
Young Generation: Eden + 2 Survivor spaces (Minor GC happens here).

Old Generation: Long-lived objects (Major GC here).

Permanent Generation: (before Java 8) — class metadata.

Objects promoted through generations after surviving multiple GCs.

# 11. Common JVM options for GC tuning
-Xms512m -Xmx4g
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xlog:gc*    # Java 9+

# 12. Choosing GC Algorithm
| App Type                    | GC Choice      | Why?                              |
| --------------------------- | -------------- | --------------------------------- |
| **Low-latency (trading)**   | ZGC/Shenandoah | Pause time minimization           |
| **High-throughput (batch)** | Parallel GC    | Max CPU usage, large heap support |
| **Mixed**                   | G1 GC          | Balanced performance              |


# 13. How to analyze GC logs? Tools?
Look for frequency of Full GCs, long pause times, promotion failures.

Tools:

GCViewer

GCEasy.io

jstat -gcutil

VisualVM / JFR

# 14. What is GC overhead error?
Occurs when:
GC takes >98% of time but recovers <2% heap

Error: java.lang.OutOfMemoryError: GC overhead limit exceeded

Fix: Tune heap size, reduce object allocation rate, optimize code.
