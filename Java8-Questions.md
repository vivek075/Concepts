# Java 8 Streams Examples
1. Group the students by department names

`Map<String, List<Student>> collect = list.stream().collect(Collectors.groupingBy(Student::getDepartmantName));`

2. Find the count of students in each department

`Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(Student::getDepartmantName,Collectors.counting()));`

3. Find all departments names

`List<String> deptName = list.stream().map(t -> t.getDepartmantName()).distinct().collect(Collectors.toList());`

4. Find the list of students whose age is less than 20

`List<Student> collect = list.stream().filter(t -> t.getAge() < 20).collect(Collectors.toList());`

5. Find the max age of students

`OptionalInt max = list.stream().mapToInt(t -> t.getAge()).max();`

6. Find the average age of male and female students

`Map<String, Double> collect = list.stream().collect(Collectors.groupingBy(Student::getGender, Collectors.averagingInt(Student::getAge)));`

7. Find the youngest student in all departments

`int min = list.stream().mapToInt(Student::getAge)
          .min()
          .getAsInt();`

`Student student = list.stream()
                    .min(Comparator.comparing(Student::getAge))
                    .get();`

8. Find the senior female student in all departments

`int seniorStudent = list.stream()
                    .filter(t -> t.getGender().equals("Female"))
                    .mapToInt(Student::getAge)
                    .max()
                    .getAsInt();`

`Student student = list.stream()
                    .filter(t -> t.getGender().equals("Female"))
                    .max(Comparator.comparing(Student::getAge))
                    .get();`

9. Find the list of students whose rank is between 50 and 100

`List<Student> collect = list.stream()
                        .filter(t -> t.getRank() > 50 && t.getRank() < 100)
                        .collect(Collectors.toList());`

10. Find the department who is having maximum number of students

`Entry<String, Long> entry = list.stream()
                           .collect(Collectors.groupingBy(Student::getDepartmantName, Collectors.counting()))
                           .entrySet()
                           .stream()
                           .max(Map.Entry.comparingByValue())
                           .get();`

11. Find the Students who stays in Mumbai and sort them by their names

`List<Student> collect = list.stream()
                        .filter(t -> t.getCity().equals("Mumbai"))
                        .sorted(Comparator.comparing(Student::getFirstName))
                        .collect(Collectors.toList());`

12. Find the total count of students

`long count = list.stream().count();`

13. Find the average rank in all departments

`Map<String, Double> collect = list.stream()
                              .collect(Collectors.groupingBy(Student::getDepartmantName, Collectors.averagingInt(Student::getRank)));`

14. Find the highest rank in each department

`Map<String, Optional<Student>> collect = list.stream()
                                        .collect(Collectors.groupingBy(Student::getDepartmantName,Collectors.minBy(Comparator.comparing(Student::getRank))));`

15. Find the list of students , which are sorted by their rank

`List<Student> collect = list.stream()
                        .sorted(Comparator.comparing(Student::getRank))
                        .collect(Collectors.toList());`

16. Find the second highest rank student

`Student student = list.stream()
                  .sorted(Comparator.comparing(Student::getRank))
                  .skip(1).findFirst()
                  .get();`

17. Find the ranks of students in all department in ascending order

`Map<String, List<Student>> collect = list.stream()
                                    .collect(Collectors.groupingBy(Student::getDepartmantName,Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
                                    .sorted(Comparator.comparing(Student::getRank))
                                    .collect(Collectors.toList()))));`

18. Count the occurrences of each word in a Array of strings using streams

`Map<String, Long> collect = Arrays.asList(words).stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));`

19. Find the longest string in a list of strings using streams

`Optional<String> max = list.stream().max(Comparator.comparingInt(String::length));`

20. Given a list of integers, remove duplicates and keep them in the descending order using streams

`List<Integer> collect = numbers.stream().distinct().sorted(Comparator.comparingInt(Integer::intValue).reversed()).collect(Collectors.toList());`

21. Find the average of a list of doubles using streams

`OptionalDouble average = doubles.stream().mapToDouble(Double::doubleValue).average();`

22. Merge two lists of integers and remove duplicates using streams

`List<Integer> collect = Stream.concat(list1.stream(), list2.stream()).distinct().collect(Collectors.toList());`

23. Given a list of strings, concatenate them into a single string using streams

`String collect = list.stream().collect(Collectors.joining());`

24. find the first non-repeating character in a string using streams

`Optional<Character> firstNonRepeatingChar = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();`

25. Given a list of strings, remove all strings that contain a specific character using streams

`List<String> collect = list.stream().filter(s->!s.contains(String.valueOf(specificChar))).collect(Collectors.toList());`

26. Given a list of integers, partition them into two groups: odd and even, using streams

`Map<Boolean, List<Integer>> oddEvenPartition = numbers.stream()
                                     .collect(Collectors.partitioningBy(n -> n % 2 == 0));`

27. Given an array of integers, find the kth largest element

`Integer num = list.stream().sorted(Comparator.reverseOrder()).limit(k).skip(k - 1).findFirst().orElse(-1);`

28. Write a program to perform cube on list elements and filter numbers greater than 50

`integerList.stream()
                  .map(i -> i*i*i)
                  .filter(i -> i>50)
                  .forEach(System.out::println);`

29. Find the count of strings starting with a vowels

`long count = list.stream().filter(s -> "aeiouAEIOU".contains(String.valueOf(s.charAt(0)))).count();`

30. Given a list of strings, find the longest palindrome string

`String str = list.stream().filter(s -> new StringBuilder(s).reverse().toString().equalsIgnoreCase(s))
    .max(Comparator.comparingInt(String::length)).orElse("");`

31. Given a list of integers, find the product of all non-negative integers

`long longNumber = integerList.stream().filter(num -> num >= 0).mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b);`

32. How do you find frequency of each character in a string using Java 8 streams?

`inputString.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));`
