# Spring Component Scanning – Bean Creation (No DB, No JPA)

> **Focus:** How Spring Boot discovers classes via component scanning and creates beans automatically.

---

## 1. What Component Scanning Actually Does

When a Spring Boot application starts:

1. `@SpringBootApplication` is read
2. It implicitly enables `@ComponentScan`
3. Spring scans the **base package and all sub-packages**
4. Any class annotated with:
   - `@Component`
   - `@Service`
   - `@Repository` 
   - `@Controller / @RestController`
5. Spring **instantiates these classes as beans** and stores them in the **ApplicationContext**

> No annotation = no bean = no dependency injection.

---

## 2. Application Entry Point (Scan Starts Here)

```java
@SpringBootApplication
public class ComponentScanDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentScanDemoApplication.class, args);
    }
}
```

**Important:**
- This class should be placed at the **root package**
- Spring scans this package **and everything below it**

---

## 3. Model Class (NOT a Spring Bean)

> Model classes are plain Java objects. They are **not** managed by Spring.

```java
public class User {

    private Long id;
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
```

✔ No annotation
✔ No Spring lifecycle
✔ No shared singleton state

---

## 4. Repository Layer – Bean via Component Scanning

```java
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Long, User> store = new HashMap<>();

    public UserRepository() {
        System.out.println("UserRepository bean created");
    }

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public User findById(Long id) {
        return store.get(id);
    }
}
```

**Key Point:**
- `@Repository` is a specialization of `@Component`
- Spring auto-detects and creates this bean

---

## 5. Service Layer – Dependency Injection via Constructor

```java
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("UserService bean created");
    }

    public User createUser(Long id, String name) {
        User user = new User(id, name);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }
}
```

**What Spring Does:**
- Finds `UserRepository` bean
- Finds `UserService` bean
- Injects repository automatically

---

## 6. Controller Layer – Final Link

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("UserController bean created");
    }

    @PostMapping("/{id}/{name}")
    public User createUser(@PathVariable Long id,
                           @PathVariable String name) {
        return userService.createUser(id, name);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
```

**Dependency Chain:**

```
UserController
      ↓
UserService
      ↓
UserRepository
```

---

## 7. Startup Output (Proof of Bean Creation)

```
UserRepository bean created
UserService bean created
UserController bean created
```

This confirms that **component scanning created all beans automatically**.

---

## 8. Stereotype Annotations (Reality Check)

| Annotation | Purpose |
|---------|---------|
| `@Component` | Generic Spring bean |
| `@Service` | Business logic layer |
| `@Repository` | Data access layer |
| `@RestController` | Web / API layer |

> All are detected by component scanning. Differences are semantic.

---


---

## Key Point:

> **Component scanning discovers beans. Dependency injection wires them.**

---