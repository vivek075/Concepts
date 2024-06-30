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
