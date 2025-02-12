# Miranum Task

```xml
<dependency>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>miranum-engine-core</artifactId>
    <version>${project.version}</version>
</dependency>
<dependency>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>miranum-engine-task</artifactId>
    <version>${project.version}</version>
</dependency>
```

## API

**Task Notifications**

To notify users or groups about tasks (create, assignment, complete, delete, ... events),
you can implement the `TaskNotificationOutPort` interface and enable tasklist notifications `miranum.tasklist.notificationsEnabled: true`.
This interface provides methods to notify assignees, candidate users, and candidate groups.

```java
@Slf4j
@Component
public class UsertaskNotificationAdapter implements TaskNotificationOutPort {

    @Override
    public void notifyAssignee(final String assignee, final String eventName, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify assignee: {} for task: {} and event {}", assignee, task.getName(), eventName);
    }

    @Override
    public void notifyCandidateUsers(final List<String> candidateUsers, final String eventName, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate users: {} for task: {} and event {}", candidateUsers, task.getName(), eventName);
    }

    @Override
    public void notifyCandidateGroups(final List<String> candidateGroups, final String eventName, final DelegateTask delegateTask) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate groups: {} for task: {} and event {}", candidateGroups, delegateTask.getName(), eventName);
    }

}
```

## Configuration

```yaml
miranum:
  tasklist:
    # if you want to disable the tasklist, set this to false
    enabled: true
    # if you want to enable tasklist notifications, set this to true
    notificationsEnabled: true
    # if you want a custom prefix for the tasklist custom fields, set this to your desired prefix
    customFieldsPrefix: "miranum_task_"
```
