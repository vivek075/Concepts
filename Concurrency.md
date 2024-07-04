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
