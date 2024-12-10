# Semaphore

A semaphore controls access to a shared resource by using permits in java.

- If permits are greater than zero, then semaphore allow access to shared resource.

- If permits are zero or less than zero, then semaphore does not allow access to shared resource.

These permits are sort of counters, which allow access to the shared resource. Thus, to access the resource, a thread must be granted a permit from the semaphore.

**Semaphore has 2 constructors in java**

_Semaphore(int permits)_

permits is the initial number of permits available.

This value can be negative, in which case releases must occur before any acquires will be granted, permits is number of threads that can access shared resource at a time. 

If permits is 1, then only one threads that can access shared resource at a time.

_Semaphore(int permits, boolean fair)_

permits is the initial number of permits available.

This value can be negative, in which case releases must occur before any acquires will be granted.

By setting fair to true, we ensure that waiting threads are granted a permit in the order in which they requested access.

**Semaphore’s acquire( ) method has 2 forms  in java:**

_void acquire( ) throws InterruptedException_

Acquires a permit if one is available and decrements the number of available permits by 1.

If no permit is available then the current thread waits until one of the following things happen > 

- some other thread calls release() method on this semaphore or, 

- some other thread interrupts the current thread.

_void acquire(int permits) throws InterruptedException_

Acquires permits number of permits if available and decrements the number of available permits by permits.

If permits number of permits are not available then the current thread becomes dormant until  one of the following things happens -

- some other thread calls release() method on this semaphore and available permits become equal to permits or,

- some other thread interrupts the current thread.

**Semaphore’s release( ) method has 2 forms  in java:**

_void release( )_
  
  Releases a permit and increases the number of available permits by 1.
  For releasing lock by calling release() method it’s not mandatory that thread must have acquired permit by calling acquire() method.

_void release(int permits)_

  Releases permits number of permits and increases the number of available permits by permits.
  For releasing lock by calling release(int permits) method it’s not mandatory that thread must have acquired permit by calling acquire()/acquire(int permit) method.

```
import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphoreProducer=new Semaphore(1);
        Semaphore semaphoreConsumer=new Semaphore(0);
        System.out.println("semaphoreProducer permit=1 | semaphoreConsumer permit=0");

        SProducer producer=new SProducer(semaphoreProducer,semaphoreConsumer);
        SConsumer consumer=new SConsumer(semaphoreConsumer,semaphoreProducer);

        Thread producerThread = new Thread(producer, "ProducerThread");
        Thread consumerThread = new Thread(consumer, "ConsumerThread");

        producerThread.start();
        consumerThread.start();
    }
}
class SProducer implements Runnable {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    public SProducer(Semaphore semaphoreProducer,Semaphore semaphoreConsumer) {
        this.semaphoreProducer=semaphoreProducer;
        this.semaphoreConsumer=semaphoreConsumer;
    }
    @Override
    public void run() {
        for(int i=1;i<=5;i++){
            try {
                semaphoreProducer.acquire();
                System.out.println("Produced : "+i);
                semaphoreConsumer.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class SConsumer implements Runnable {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    public SConsumer(Semaphore semaphoreConsumer,Semaphore semaphoreProducer) {
        this.semaphoreProducer=semaphoreProducer;
        this.semaphoreConsumer=semaphoreConsumer;
    }
    @Override
    public void run() {
        for(int i=1;i<=5;i++){
            try {
                semaphoreConsumer.acquire();
                System.out.println("Consumed : "+i);
                semaphoreProducer.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
Output :
semaphoreProducer permit=1 | semaphoreConsumer permit=0
Produced : 1
Consumed : 1
Produced : 2
Consumed : 2
Produced : 3
Consumed : 3
Produced : 4
Consumed : 4
Produced : 5
Consumed : 5
```

---
# Java Program demonstrating deadlock
```
public class DeadlockExample {
    public static void main(String[] args) {
        final Object resource1 = "Resource1";
        final Object resource2 = "Resource2";
        // Thread 1 tries to lock resource1 then resource2
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked resource 1");
                try {
                    // Adding delay so that both threads can start trying to lock resources
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource2) {
                    System.out.println("Thread 1: locked resource 2");
                }
            }
        });
        // Thread 2 tries to lock resource2 then resource1
        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: locked resource 2");
                try {
                    // Adding delay so that both threads can start trying to lock resources
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource1) {
                    System.out.println("Thread 2: locked resource 1");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
```

---
# How do you handle concurrent modifications to a Collection in Java?

Concurrent modifications to a collection in Java can cause different issues, such as unexpected behavior, non-deterministic results, or even throwing a ConcurrentModificationException. To handle concurrent modifications to a collection in Java, you can use one of the following approaches:

_Use synchronized collections_: One way to handle concurrent modifications to a collection is to use a synchronized collection. A synchronized collection is a thread-safe collection that ensures only one thread can modify the collection at a time. We can create a synchronized collection by calling the Collections.synchronizedCollection() method, passing in the collection you want to synchronize. For example
```
List<String> list = new ArrayList<>();
List<String> synchronizedList = Collections.synchronizedList(list);
```

_Use concurrent collections_: Another way to handle concurrent modifications to a collection is to use a concurrent collection. A concurrent collection is a thread-safe collection that allows multiple threads to modify the collection concurrently without external synchronization. The (java.util.concurrent) package provides a range of concurrent collection classes, such as `ConcurrentHashMap`, `ConcurrentLinkedDeque`, and `ConcurrentSkipListSet`.

_Use explicit locking_: We can also handle concurrent modifications to a collection by using explicit locking. We can use the synchronized keyword or the (java.util.concurrent.locks) package to lock the collection when modifying it. For example
```
List<String> list = new ArrayList<>();
synchronized(list) {
 list.add(“test”);
}
```

`Use iterators`: When iterating over a collection, we should use the `Iterator` interface to avoid concurrent modifications. If you modify the collection while iterating over it using an iterator, we will get a ConcurrentModificationException. Instead, can use the `remove()` method of the iterator to remove elements from the collection while iterating over it. For example
```
List<String> list = new ArrayList<>();
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
 String element = iterator.next();
 if (someCondition) {
 iterator.remove(); // safe way to remove an element from the list
 }
}
```

# Explain the synchronized keyword in Java.
The synchronized keyword in Java is used to control access to a block of code or method by multiple threads, ensuring that only one thread can execute the synchronized block or method at a time.

# Callable
`Callable` is a functional interface in Java that is part of the `java.util.concurrent` package.

It allows you to define a task that:

1. Can be executed in a separate thread.

2. Returns a result (unlike Runnable).

3. Can throw a checked exception.

Goal:

Create a task to calculate the square of a number using `Callable`.

1. Create a Callable Task:
  - Use a Callable to define a task that calculates the square of a number.
2. Use an Executor Service:
  - Submit the Callable task to an ExecutorService to execute it.
3. Get the Result:
  - The result is returned as a Future, which you can query or block to get the result.

```
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) {
        // Step 1: Create a Callable task
        Callable<Integer> task = () -> {
            int number = 5; // Example number
            System.out.println("Calculating square of " + number);
            return number * number; // Return the square
        };

        // Step 2: Use an ExecutorService to run the task
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            // Submit the task and get a Future
            Future<Integer> future = executor.submit(task);

            // Step 3: Get the result from the Future
            Integer result = future.get(); // This blocks until the task completes
            System.out.println("Square is: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shut down the executor
            executor.shutdown();
        }
    }
}
```

Using Callable Without ExecutorService

Since `Callable` cannot run directly in a thread (unlike `Runnable`), you can wrap it with a `FutureTask`. A `FutureTask` can be executed by a `Thread`.
```
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableWithoutExecutor {
    public static void main(String[] args) {
        // Step 1: Create a Callable task
        Callable<String> task = () -> {
            Thread.sleep(1000); // Simulate some work
            return "Task executed without ExecutorService!";
        };

        // Step 2: Wrap the Callable in a FutureTask
        FutureTask<String> futureTask = new FutureTask<>(task);

        // Step 3: Execute the FutureTask in a Thread
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            // Step 4: Get the result
            String result = futureTask.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
