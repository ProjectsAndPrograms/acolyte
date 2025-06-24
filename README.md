# 🔧 Acolyte — Spring Boot Code Generator CLI

Acolyte is a command-line tool designed to boost your productivity in Spring Boot projects by automating the creation of common boilerplate code. It helps maintain a clean and standardized package structure, making your development process faster, more consistent, and less error-prone.

It supports generating entities, services, repositories, controllers, views, configuration files, and more — all with a single command.

<div style="display: flex;flex-direction: column; grid-gap: 10px;">
   <div style="display: flex; grid-gap: 10px;">
        <img src="screenshots/oranbyte1.png" alt="screenshots" width="99%" style="border: 2px solid lightgreen"/>
    </div>
</div>

---

## 🚀 Available Commands

### 📦 Make Entity
Generates a JPA entity class with a basic structure.

```bash
java acolyte make:entity <Entity-Name>
# Example:
java acolyte make:entity User
```

---

### 👁️ Make View
Creates a Thymeleaf view file with the specified name.

```bash
java acolyte make:view <view-name>
# Example:
java acolyte make:view user-list
```

---

### 🎮 Make Controller
Generates a Spring controller class. You can optionally scaffold common REST methods using `--resource`.

```bash
java acolyte make:controller <Controller-Name> [--resource <methods>]

# Examples:
java acolyte make:controller UserController
java acolyte make:controller UserController --resource
java acolyte make:controller UserController --resource index save destroy
```

---

### 📄 Make DTO or POJO
Creates a Data Transfer Object or plain Java class.

```bash
java acolyte make:dto <Dto-Name>
# Example:
java acolyte make:dto UserDto
```

---

### 🛠️ Make Service
Generates a service class and optionally an interface following the service pattern.

```bash
java acolyte make:service <Service-Name>
# Example:
java acolyte make:service UserService
```

---

### 🗃️ Make Repository
Creates a JPA repository interface for a given entity.

```bash
java acolyte make:repository <Entity-Name>
# OR
java acolyte make:repo <Entity-Name>

# Example:
java acolyte make:repo User
```

---

### ⚙️ Make Configuration
Creates a configuration class annotated with `@Configuration`.

```bash
java acolyte make:configuration <Config-Class-Name>
# OR
java acolyte make:config <Config-Class-Name>

# Example:
java acolyte make:config WebConfig
```

---

### ✅ Make Validator
Creates a custom validator class and links it to a DTO for form validation.

```bash
java acolyte make:validator <Validator-Class-Name> --dto=<Dto-Class-Name>
# Example:
java acolyte make:validator UserValidator --dto=UserDto
```

---

### 🎧 Make Listener
Generates an entity listener for lifecycle callbacks.

```bash
java acolyte make:listener <Listener-Class-Name>
# Example:
java acolyte make:listener UserListener
```

**Usage in Entity:**

```java
@EntityListeners(UserListener.class)
public class User {
    // fields
}
```

---

### 🧩 Make Fragment / Component
Generates a reusable Thymeleaf HTML fragment/component.

```bash
java acolyte make:fragment <fragment-path>
# OR
java acolyte make:component <component-path>

# Example:
java acolyte make:fragment admin/layout/header
java acolyte make:component admin/layout/header
```

**Usage in Thymeleaf:**

```html
<th:block th:replace="~{/admin/layout/header :: header}"></th:block>
```

---

## 📚 Benefits of Using Acolyte

- ✅ Eliminate repetitive code writing
- ✅ Maintain consistent folder structure
- ✅ Focus on business logic, not scaffolding
- ✅ Easy integration with any Spring Boot project
- ✅ Supports modular architecture and reusability

---

## 🧪 Coming Soon

Here are the upcoming features planned for Acolyte CLI to make it even more powerful and developer-friendly:

- **make:model** – Generate Entity, DTO, Repository, Service, and Controller in one shot.
- **make:test** – Create test classes for services, controllers, or repositories using JUnit.
- **make:request** – Generate form request classes with built-in validation annotations.
- **make:enum** – Create a type-safe Enum with custom values.
- **make:interceptor** – Scaffold Spring's HTTP interceptor for custom middleware.
- **make:resource** – Generate a standardized API response wrapper class.
- **make:scheduler** – Create a class with a `@Scheduled` job using cron expression.
- **make:runner** – Create a class that runs logic on app startup via `CommandLineRunner`.
- **make:exceptionhandler** – Scaffold a global exception handler with `@ControllerAdvice`.
- **make:configprops** – Generate `@ConfigurationProperties` class for binding custom configs.
- **wizard** – Interactive CLI flow to build modules step-by-step.
- **custom templates** – Allow users to plug in their own code templates.
- **i18n support** – Generate language properties files for multi-language apps.
- **thymeleaf-page** – Scaffold Thymeleaf pages like forms or lists with mock data bindings.


---

Feel free to contribute or suggest features to make **Acolyte** even more powerful!
