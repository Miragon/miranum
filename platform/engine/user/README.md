# Miranum User

```xml
<dependency>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>miranum-engine-user</artifactId>
    <version>${project.version}</version>
</dependency>
```

**API**

```java
public interface UserApi {

    List<User> searchUser(String query);

    User getUserByUserName(String username);
}
```
