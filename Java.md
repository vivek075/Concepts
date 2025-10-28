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

```
| Question                                                    | Key Point                                                     |
| ----------------------------------------------------------- | ------------------------------------------------------------- |
| What happens if `equals()` is symmetric but not transitive? | Violates contract; unpredictable behavior in collections.     |
| Can you override `hashCode()` to always return constant?    | Yes but inefficient — all objects go into same bucket.        |
| Why is `Object.clone()` protected?                          | To enforce that subclass explicitly decides cloning behavior. |
| How `hashCode()` affects `HashMap` performance?             | Poor hashCode → more collisions → slower retrieval.           |
```
