package io.miragon.miranum.connect.adapter.in.c8.elementtemplates;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

@JsonAppend(attrs = @JsonAppend.Attr(value = "$schema"), prepend = true)
public class SchemaMixin {
    // Because the generated element template does not have a property to
    // set the schema, we add it manually on serialization
}
