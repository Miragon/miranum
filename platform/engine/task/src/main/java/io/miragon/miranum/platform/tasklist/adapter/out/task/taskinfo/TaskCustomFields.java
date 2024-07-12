package io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TaskCustomFields")
@Table(name = "MIRANUM_TASK_CUSTOM_FIELDS")
public class TaskCustomFields {

    /**
     * The id of the authority
     */
    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * The name of the custom field
     */
    @Column(name = "key_", nullable = false)
    private String key;

    /**
     * The value of the custom field
     */
    @Column(name = "value_", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskinfo_id")
    private TaskInfoEntity taskInfo;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

}
