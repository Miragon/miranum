package io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Constraints {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean notEmpty;
}