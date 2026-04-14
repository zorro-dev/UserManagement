# Java Stream Documentation

## What Is A Stream?

A `Stream` in Java is used to process data from a collection like `List`, `Set`, etc.

A stream does not store data. It takes data from a source and processes it step by step.

Basic syntax:

```java
collection.stream()
        .method1()
        .method2()
        .terminalMethod();
```

Example:

```java
List<UserResponseDto> result = users.stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
```

In your Spring Boot project, streams are useful for converting entity objects into DTO objects.

Example:

```java
public List<UserResponseDto> getUsersByCity(String city) {
    return userRepository.findByCity(city)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
}
```

Step by step:

```text
List<User>
    -> Stream<User>
    -> Stream<UserResponseDto>
    -> List<UserResponseDto>
```

---

## 1. stream()

### Syntax

```java
collection.stream()
```

### Return Type

```java
Stream<T>
```

If the collection is:

```java
List<User>
```

Then:

```java
users.stream()
```

Returns:

```java
Stream<User>
```

### Usage

Used to start stream processing on a collection.

### Example

```java
List<String> names = List.of("John", "Sara", "Alex");

Stream<String> nameStream = names.stream();
```

### Where It Is Needed

Use `stream()` when you want to process a collection using methods like:

- `map()`
- `filter()`
- `sorted()`
- `count()`
- `collect()`

---

## 2. map()

### Syntax

```java
stream.map(element -> convertedValue)
```

or:

```java
stream.map(this::methodName)
```

### Return Type

```java
Stream<R>
```

`R` means the converted type.

### Usage

Used to convert each element into another value or object.

### Example

```java
List<String> names = List.of("john", "sara");

List<String> upperNames = names.stream()
        .map(name -> name.toUpperCase())
        .collect(Collectors.toList());
```

Output:

```java
["JOHN", "SARA"]
```

### Spring Boot Example

```java
List<UserResponseDto> dtoList = users.stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
```

Here:

```text
User -> UserResponseDto
```

### Where It Is Needed

Use `map()` when you want to transform data.

Common examples:

- `User` to `UserResponseDto`
- `String` to uppercase `String`
- `Product` to product name
- `Employee` to employee salary

---

## 3. filter()

### Syntax

```java
stream.filter(element -> condition)
```

### Return Type

```java
Stream<T>
```

### Usage

Used to keep only elements that match a condition.

### Example

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

List<Integer> evenNumbers = numbers.stream()
        .filter(num -> num % 2 == 0)
        .collect(Collectors.toList());
```

Output:

```java
[2, 4, 6]
```

### Spring Boot Example

```java
List<User> activeUsers = users.stream()
        .filter(user -> user.getStatus() == User.UserStatus.ACTIVE)
        .collect(Collectors.toList());
```

### Where It Is Needed

Use `filter()` when you want only selected data.

Examples:

- Only active users
- Only users from Chennai
- Only products above 1000
- Only employees with salary greater than 50000

---

## 4. collect()

### Syntax

```java
stream.collect(Collectors.toList())
```

### Return Type

Depends on the collector.

For:

```java
Collectors.toList()
```

Return type is:

```java
List<T>
```

### Usage

Used to convert a stream back into a collection.

### Example

```java
List<String> names = List.of("john", "sara");

List<String> result = names.stream()
        .collect(Collectors.toList());
```

### Spring Boot Example

```java
return userRepository.findByCity(city)
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
```

The final return type is:

```java
List<UserResponseDto>
```

### Where It Is Needed

Use `collect()` when you need the final result as:

- `List`
- `Set`
- `Map`
- joined `String`
- grouped data

Common collectors:

```java
Collectors.toList()
Collectors.toSet()
Collectors.toMap()
Collectors.joining()
Collectors.groupingBy()
```

---

## 5. toList()

### Syntax

```java
stream.toList()
```

### Return Type

```java
List<T>
```

### Usage

Used to convert a stream into a list. It is a shorter form available in newer Java versions.

Instead of:

```java
.collect(Collectors.toList())
```

You can write:

```java
.toList()
```

### Example

```java
List<String> upperNames = names.stream()
        .map(name -> name.toUpperCase())
        .toList();
```

### Where It Is Needed

Use `toList()` when your final output should be a list.

---

## 6. forEach()

### Syntax

```java
stream.forEach(element -> action)
```

### Return Type

```java
void
```

### Usage

Used to perform an action for every element.

### Example

```java
List<String> names = List.of("John", "Sara", "Alex");

names.stream()
        .forEach(name -> System.out.println(name));
```

Output:

```text
John
Sara
Alex
```

### Where It Is Needed

Use `forEach()` for actions like:

- Printing
- Logging
- Calling a method for each item
- Sending notifications

Do not use `forEach()` when you want to return a new list. Use `map()` and `collect()` for that.

---

## 7. sorted()

### Syntax

Natural sorting:

```java
stream.sorted()
```

Custom sorting:

```java
stream.sorted(Comparator.comparing(ClassName::getFieldName))
```

### Return Type

```java
Stream<T>
```

### Usage

Used to sort stream elements.

### Example

```java
List<String> names = List.of("Sara", "Alex", "John");

List<String> sortedNames = names.stream()
        .sorted()
        .collect(Collectors.toList());
```

Output:

```java
["Alex", "John", "Sara"]
```

### Spring Boot Example

```java
List<User> sortedUsers = users.stream()
        .sorted(Comparator.comparing(User::getFirstName))
        .collect(Collectors.toList());
```

Descending order:

```java
List<User> sortedUsers = users.stream()
        .sorted(Comparator.comparing(User::getFirstName).reversed())
        .collect(Collectors.toList());
```

### Where It Is Needed

Use `sorted()` when you want ordered data.

Examples:

- Users sorted by name
- Products sorted by price
- Orders sorted by date
- Students sorted by marks

---

## 8. findFirst()

### Syntax

```java
stream.findFirst()
```

### Return Type

```java
Optional<T>
```

### Usage

Used to get the first element from a stream.

### Example

```java
Optional<String> firstName = names.stream()
        .findFirst();
```

Because the list may be empty, Java returns `Optional<T>`.

### Example With orElseThrow()

```java
String firstName = names.stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No name found"));
```

### Where It Is Needed

Use `findFirst()` when you need only the first matching value.

```java
User firstActiveUser = users.stream()
        .filter(user -> user.getStatus() == User.UserStatus.ACTIVE)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No active user found"));
```

---

## 9. findAny()

### Syntax

```java
stream.findAny()
```

### Return Type

```java
Optional<T>
```

### Usage

Used to get any one element from the stream.

### Example

```java
Optional<User> anyUser = users.stream()
        .findAny();
```

### Where It Is Needed

Use `findAny()` when you do not care which item is returned.

It is more useful in parallel streams.

---

## 10. anyMatch()

### Syntax

```java
stream.anyMatch(element -> condition)
```

### Return Type

```java
boolean
```

### Usage

Checks whether at least one element matches the condition.

### Example

```java
boolean hasActiveUser = users.stream()
        .anyMatch(user -> user.getStatus() == User.UserStatus.ACTIVE);
```

### Where It Is Needed

Use `anyMatch()` for yes or no checks.

Examples:

- Does any user have ACTIVE status?
- Does any product cost more than 10000?
- Does any name start with A?

---

## 11. allMatch()

### Syntax

```java
stream.allMatch(element -> condition)
```

### Return Type

```java
boolean
```

### Usage

Checks whether all elements match the condition.

### Example

```java
boolean allActive = users.stream()
        .allMatch(user -> user.getStatus() == User.UserStatus.ACTIVE);
```

### Where It Is Needed

Use `allMatch()` when every item must satisfy a rule.

Examples:

- Are all users active?
- Are all marks above pass mark?
- Are all products in stock?

---

## 12. noneMatch()

### Syntax

```java
stream.noneMatch(element -> condition)
```

### Return Type

```java
boolean
```

### Usage

Checks whether no element matches the condition.

### Example

```java
boolean noSuspendedUsers = users.stream()
        .noneMatch(user -> user.getStatus() == User.UserStatus.SUSPENDED);
```

### Where It Is Needed

Use `noneMatch()` when something should not exist.

Examples:

- No suspended users
- No empty names
- No negative prices

---

## 13. count()

### Syntax

```java
stream.count()
```

### Return Type

```java
long
```

### Usage

Counts the number of elements in a stream.

### Example

```java
long totalUsers = users.stream()
        .count();
```

With filter:

```java
long activeUsers = users.stream()
        .filter(user -> user.getStatus() == User.UserStatus.ACTIVE)
        .count();
```

### Where It Is Needed

Use `count()` when you need the total number of items.

Examples:

- Total users
- Total active users
- Total products above a price

---

## 14. limit()

### Syntax

```java
stream.limit(number)
```

### Return Type

```java
Stream<T>
```

### Usage

Takes only the first given number of elements.

### Example

```java
List<User> firstFiveUsers = users.stream()
        .limit(5)
        .collect(Collectors.toList());
```

### Where It Is Needed

Use `limit()` when you need only a fixed number of results.

Examples:

- First 5 users
- Top 10 products
- Latest 3 orders

---

## 15. skip()

### Syntax

```java
stream.skip(number)
```

### Return Type

```java
Stream<T>
```

### Usage

Skips the first given number of elements.

### Example

```java
List<User> afterFirstFive = users.stream()
        .skip(5)
        .collect(Collectors.toList());
```

Pagination-style example:

```java
List<User> pageTwoUsers = users.stream()
        .skip(10)
        .limit(10)
        .collect(Collectors.toList());
```

This means:

```text
Skip first 10 users, then take next 10 users.
```

### Where It Is Needed

Use `skip()` when you want to ignore some starting records.

---

## 16. distinct()

### Syntax

```java
stream.distinct()
```

### Return Type

```java
Stream<T>
```

### Usage

Removes duplicate values.

### Example

```java
List<Integer> numbers = List.of(1, 2, 2, 3, 3, 4);

List<Integer> uniqueNumbers = numbers.stream()
        .distinct()
        .collect(Collectors.toList());
```

Output:

```java
[1, 2, 3, 4]
```

### Where It Is Needed

Use `distinct()` when duplicate values should be removed.

Examples:

- Unique cities
- Unique countries
- Unique product categories

---

## 17. min()

### Syntax

```java
stream.min(comparator)
```

### Return Type

```java
Optional<T>
```

### Usage

Finds the smallest element based on a comparator.

### Example

```java
Optional<Integer> minNumber = numbers.stream()
        .min(Integer::compareTo);
```

### Spring Boot Example

```java
Optional<User> earliestCreatedUser = users.stream()
        .min(Comparator.comparing(User::getCreatedAt));
```

### Where It Is Needed

Use `min()` when you need the smallest value.

Examples:

- Lowest price
- Smallest age
- Earliest date

---

## 18. max()

### Syntax

```java
stream.max(comparator)
```

### Return Type

```java
Optional<T>
```

### Usage

Finds the largest element based on a comparator.

### Example

```java
Optional<Integer> maxNumber = numbers.stream()
        .max(Integer::compareTo);
```

### Spring Boot Example

```java
Optional<User> latestCreatedUser = users.stream()
        .max(Comparator.comparing(User::getCreatedAt));
```

### Where It Is Needed

Use `max()` when you need the largest value.

Examples:

- Highest salary
- Highest price
- Latest created user
- Maximum marks

---

## 19. reduce()

### Syntax

```java
stream.reduce(initialValue, (a, b) -> result)
```

Another syntax:

```java
stream.reduce((a, b) -> result)
```

### Return Type

With initial value:

```java
T
```

Without initial value:

```java
Optional<T>
```

### Usage

Used to combine all elements into one final value.

### Example

```java
List<Integer> numbers = List.of(1, 2, 3, 4);

int sum = numbers.stream()
        .reduce(0, (a, b) -> a + b);
```

Output:

```java
10
```

Shorter:

```java
int sum = numbers.stream()
        .reduce(0, Integer::sum);
```

### Where It Is Needed

Use `reduce()` when many values should become one value.

Examples:

- Sum of numbers
- Total price
- Combined string
- Total salary

---

## 20. peek()

### Syntax

```java
stream.peek(element -> action)
```

### Return Type

```java
Stream<T>
```

### Usage

Used mainly for debugging inside a stream pipeline.

### Example

```java
List<String> result = names.stream()
        .peek(name -> System.out.println("Before map: " + name))
        .map(name -> name.toUpperCase())
        .peek(name -> System.out.println("After map: " + name))
        .collect(Collectors.toList());
```

### Where It Is Needed

Use `peek()` mostly for debugging.

For normal actions, prefer `forEach()`.

---

## Intermediate Methods

Intermediate methods return another stream.

Examples:

```java
map()
filter()
sorted()
limit()
skip()
distinct()
peek()
```

Example:

```java
users.stream()
        .filter(user -> user.getCity().equals("Chennai"))
        .map(this::mapToDto)
```

This is still a stream because there is no terminal method yet.

---

## Terminal Methods

Terminal methods end the stream and produce the final result.

Examples:

```java
collect()
toList()
forEach()
count()
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
min()
max()
reduce()
```

Example:

```java
List<UserResponseDto> result = users.stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
```

Here `collect()` ends the stream.

---

## Your Code Explained

Your method:

```java
public List<UserResponseDto> getUsersByCity(String city) {
    return userRepository.findByCity(city)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
}
```

Step by step:

### Step 1

```java
userRepository.findByCity(city)
```

Return type:

```java
List<User>
```

It gets all users from the given city.

### Step 2

```java
.stream()
```

Return type:

```java
Stream<User>
```

It starts stream processing.

### Step 3

```java
.map(this::mapToDto)
```

Return type:

```java
Stream<UserResponseDto>
```

It converts every `User` into `UserResponseDto`.

### Step 4

```java
.collect(Collectors.toList())
```

Return type:

```java
List<UserResponseDto>
```

It converts the stream back into a list.

---

## Same Code Without Stream

```java
public List<UserResponseDto> getUsersByCity(String city) {
    List<User> users = userRepository.findByCity(city);

    List<UserResponseDto> responseList = new ArrayList<>();

    for (User user : users) {
        UserResponseDto dto = mapToDto(user);
        responseList.add(dto);
    }

    return responseList;
}
```

## Same Code With Stream

```java
public List<UserResponseDto> getUsersByCity(String city) {
    return userRepository.findByCity(city)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
}
```

The stream version is shorter and cleaner.

---

## Most Common Stream Pattern In Spring Boot

```java
return repository.findSomething()
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
```

Meaning:

```text
Get entities from database
Convert each entity to DTO
Return list of DTOs
```

This is common in Spring Boot because APIs usually return DTOs instead of entity objects.
