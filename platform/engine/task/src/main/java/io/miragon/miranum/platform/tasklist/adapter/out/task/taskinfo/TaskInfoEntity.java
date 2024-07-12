package io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TaskInfo")
@Table(name = "MIRANUM_TASK_INFO")
public class TaskInfoEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "description_")
    private String description;

    @Column(name = "definitionname_", nullable = false)
    private String definitionName;

    @Column(name = "instanceid_", nullable = false)
    private String instanceId;

    @Column(name = "assignee_")
    private String assignee;

    @Column(name = "formkey_")
    private String formKey;

    @Builder.Default
    @OneToMany(mappedBy = "taskInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskAuthorityEntity> authorities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "taskInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskCustomFieldEntity> customFields = new ArrayList<>();

}
