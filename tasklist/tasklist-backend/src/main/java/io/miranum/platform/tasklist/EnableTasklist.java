package io.miranum.platform.tasklist;

import kotlin.annotation.MustBeDocumented;
import org.springframework.context.annotation.Import;

@MustBeDocumented
@Import(TasklistConfiguration.class)
public @interface EnableTasklist {
}
