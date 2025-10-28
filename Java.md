## 🔹 1. What is the Object class in Java?

Answer:

It is the root superclass for all classes in Java.

Every class in Java implicitly extends Object (if no other superclass is specified).

It defines a set of fundamental methods that all Java objects inherit, such as:

equals()

hashCode()

toString()

clone()

getClass()

wait(), notify(), notifyAll()

finalize() (deprecated since Java 9)

## 🔹 2. Explain equals() and hashCode() contract.

Answer:
The general contract between equals() and hashCode() is:

If two objects are equal according to equals(), they must have the same hashCode().

If two objects have different hash codes, they cannot be equal.

If two objects have same hash code, they may or may not be equal (hash collision possible).

Best practice:
Whenever you override equals(), you must also override hashCode().

Example:
```
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Employee)) return false;
    Employee e = (Employee) obj;
    return id == e.id && Objects.equals(name, e.name);
}

@Override
public int hashCode() {
    return Objects.hash(id, name);
}
```

## 🔹 3. Difference between == and equals()?
Comparison Type	              ==	                                  equals()
Purpose	                      Compares references (memory address)	Compares object content
Applicable To	                Primitives and references	            Only for objects
Can be overridden?	          ❌ No	                                ✅ Yes

## 🔹 4. What is the role of toString() method?

Answer:

Returns a string representation of the object.

Default implementation: getClass().getName() + '@' + Integer.toHexString(hashCode())

Should be overridden to return meaningful info.

Example:
```
@Override
public String toString() {
    return "Employee{id=" + id + ", name=" + name + "}";
}
```

## 🔹 5. What does clone() method do?

Answer:

Creates and returns a copy of the object.

The class must:

Implement Cloneable interface, otherwise CloneNotSupportedException is thrown.

Override clone() method and make it public.

Example:
```
@Override
public Object clone() throws CloneNotSupportedException {
    return super.clone();
}
```
👉 Deep Copy vs Shallow Copy:

super.clone() gives shallow copy (references shared).

Deep copy requires manually cloning mutable fields.

## 🔹 6. What is getClass() used for?

Answer:

Returns the runtime class object (instance of Class<?>) representing the type of the object.
```
Employee e = new Employee();
System.out.println(e.getClass().getName()); // prints "Employee"
```

## 🔹 7. Explain wait(), notify(), notifyAll().

These are thread communication methods defined in Object class (not Thread).

Method	Description
wait()	Causes current thread to wait until another thread calls notify() or notifyAll() on same object
notify()	Wakes up one waiting thread
notifyAll()	Wakes up all waiting threads

Important:

Must be called within a synchronized block.

Operate on the object monitor (not the thread).

Example:
```
synchronized(obj) {
   while(!condition) obj.wait();
   obj.notify();
}
```

## 🔹 8. What is the finalize() method? Why deprecated?

Answer:

Used to cleanup resources before GC.

Deprecated because:

Unpredictable (GC timing uncertain)

Could cause memory/resource leaks

Replaced by try-with-resources or Cleaner/PhantomReference

## 🔹 9. Why wait(), notify(), and notifyAll() are in Object class, not Thread?

Answer:
Because every object can be used as a monitor (lock). Threads wait and notify on object monitors, not on thread instances.

## 🔹 10. What happens if you call wait() outside synchronized block?

Answer:

IllegalMonitorStateException is thrown.

Because the current thread doesn’t hold the monitor lock on the object.

---

```
| Question                                                    | Key Point                                                     |
| ----------------------------------------------------------- | ------------------------------------------------------------- |
| What happens if `equals()` is symmetric but not transitive? | Violates contract; unpredictable behavior in collections.     |
| Can you override `hashCode()` to always return constant?    | Yes but inefficient — all objects go into same bucket.        |
| Why is `Object.clone()` protected?                          | To enforce that subclass explicitly decides cloning behavior. |
| How `hashCode()` affects `HashMap` performance?             | Poor hashCode → more collisions → slower retrieval.           |
```

---

🧵 PART 2 — java.lang.Thread Class

## 🔹 1. What is the Thread class in Java?

Answer:

Represents a single thread of execution in JVM.

Can be created by:

Extending Thread class and overriding run()

Implementing Runnable and passing it to a Thread constructor.

## 🔹 2. How do you create a thread in Java?

Option 1: Extend Thread
```
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}
new MyThread().start();
```

Option 2: Implement Runnable
```
Runnable task = () -> System.out.println("Thread running");
new Thread(task).start();
```

## 🔹 3. Difference between start() and run()?
Method	Behavior
start()	Creates a new thread and calls run() in that new thread
run()	Executes in current thread (no new thread created)

## 🔹 4. Thread Lifecycle

States:
NEW → RUNNABLE → RUNNING → WAITING / TIMED_WAITING / BLOCKED → TERMINATED

## 🔹 5. What happens if you call start() twice on same thread?

Answer:

IllegalThreadStateException

A thread can only be started once.

## 🔹 6. Difference between sleep() and wait()
```
| Feature                         | `sleep()`                   | `wait()`            |
| ------------------------------- | --------------------------- | ------------------- |
| Belongs To                      | Thread class                | Object class        |
| Lock release                    | ❌ Doesn’t release lock      | ✅ Releases lock     |
| Usage                           | Temporarily pause execution | Thread coordination |
| Call inside synchronized block? | Optional                    | Mandatory           |
```

## 🔹 7. Explain join() method

Answer:

Used for thread synchronization — makes one thread wait until another finishes.
```
Thread t = new Thread(task);
t.start();
t.join(); // waits until t finishes
```

## 🔹 8. Explain yield() method

Answer:

Suggests that the current thread is willing to pause to let others run.

Scheduler may ignore the hint.

## 🔹 9. What is thread priority?

Threads have a priority (1–10).

Higher priority doesn’t guarantee execution order — it’s a hint to scheduler.

## 🔹 10. Daemon vs User Threads
Type	Description
User Thread	Keeps JVM alive
Daemon Thread	Runs in background (e.g., GC) — JVM exits when only daemons remain

Example:
```
Thread t = new Thread(task);
t.setDaemon(true);
t.start();
```

## 🔹 11. How do you stop a thread safely?

Answer:

Never use deprecated stop(), suspend(), resume().

Use a volatile boolean flag or interruption mechanism.

Example:
```
volatile boolean running = true;

public void run() {
    while (running && !Thread.currentThread().isInterrupted()) {
        // work
    }
}
public void stopThread() { running = false; }
```

## 🔹 12. How does interrupt() work?

Answer:

Sets the interrupt flag of the thread.

If thread is blocked (e.g., in sleep() or wait()), it throws InterruptedException.

You can check using:
```
Thread.currentThread().isInterrupted();
```

## 🔹 13. Explain ThreadLocal usage

Answer:
Provides thread-local variables — each thread has its own independent copy.

Example:
```
private static ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
```
Used in frameworks like Spring, Hibernate to store context per thread (e.g., DB sessions, user context).

## 🔹 14. Difference between Thread and Runnable
```
| Aspect      | `Thread`                   | `Runnable`                  |
| ----------- | -------------------------- | --------------------------- |
| Inheritance | Extends Thread             | Interface                   |
| Reusability | Can’t extend other classes | Can                         |
| Recommended | ❌                          | ✅ Preferred (more flexible) |
```

## 🔹 15. Can we start a thread twice?

Answer:
No. Will throw IllegalThreadStateException.

## 🔹 16. How to create multiple threads efficiently?

Answer:
Use ExecutorService / ThreadPoolExecutor instead of manually creating threads.

Example:
```
ExecutorService pool = Executors.newFixedThreadPool(10);
pool.submit(() -> System.out.println("Task executed"));
pool.shutdown();
```

---

```
| Question                                                       | Explanation                                                                 |
| -------------------------------------------------------------- | --------------------------------------------------------------------------- |
| Why `Thread` implements `Runnable`?                            | To separate **task (Runnable)** from **thread control (Thread)**.           |
| What is the difference between `Thread.yield()` and `sleep()`? | `yield()` doesn’t guarantee pause time; `sleep()` guarantees a fixed pause. |
| What is thread starvation?                                     | When low-priority threads never get CPU because higher ones keep running.   |
| What is a race condition?                                      | When multiple threads access shared data without synchronization.           |
| How to prevent race condition?                                 | Synchronization, locks, atomic variables.                                   |
| What’s difference between `synchronized` and `Lock` API?       | `Lock` gives more fine-grained control (tryLock, fairness, reentrancy).     |

```
