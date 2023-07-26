package io.miranum.platform.tasklist.domain.legacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Form object.
 * Used for backend validation of the completed forms.
 *
 * @author externer.dl.horn
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Form {

    /**
     * Sections of the form including all form fields.
     */
    private final List<Group> groups = new ArrayList<>();
    /**
     * Key of the form.
     */
    @NotBlank
    private String key;
    /**
     * description of the form.
     */
    private String description;
    /**
     * authorized groups.
     */
    private String authorizedGroups;
    /**
     * Buttons of the form.
     */
    private Map<String, Object> buttons;

    public Map<String, FormField> getFormFieldMap() {
        return this.groups.stream()
                .map(Group::getSchema)
                .flatMap(Collection::stream)
                .filter(field -> !StringUtils.isBlank(field.getKey()))
                .collect(Collectors.toMap(FormField::getKey, f -> f));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Group {

        /**
         * Label of the group.
         */
        private String label;

        /**
         * Schema of the group.
         * Includes form fields.
         */
        private List<FormField> schema = new ArrayList<>();

        public List<String> getFormFieldKeys() {
            return this.schema.stream()
                    .map(FormField::getKey)
                    .collect(Collectors.toList());
        }

    }


    /**
     * Form field obejct.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class FormField {

        /**
         * Type of the form field.
         */
        private String type;

        /**
         * Key of the form field.
         */
        private String key;

        /**
         * Default value that is used if no value is present.
         */
        private String defaultValue;

        /**
         * Default value field is used to fill a value from the data.
         */
        private String defaultValueField;

        /**
         * Label of the field.
         */
        private String label;

        /**
         * Prepend icon for the input field.
         */
        private String prependIcon;

        /**
         * Tooltip of the field.
         */
        private String tooltip;

        /**
         * Specifies the exact type of the input field.
         * Relevant for text fields
         */
        private String ext;

        /**
         * Indicates whether it is a multiple selection.
         * Relevant for select fields
         */
        private boolean multiple;

        /**
         * Description of the field.
         */
        private String description;

        /**
         * Ldap groups are relevant for the ldap-input.
         * Restrict the field to the specified groups.
         */
        private String ldapOus;

        /**
         * Height of the image.
         * Relevant for the image field.
         */
        private String imageHeight;

        /**
         * Width of the image.
         * Relevant for the image field.
         */
        private String imageWidth;

        /**
         * Indicates if the field is readonly.
         * Readonly fields are filtered when a form is completed.
         */
        private boolean readonly;

        /**
         * Rows of the textarea.
         */
        private Integer rows;

        /**
         * Width of the field.
         * Between 1 and 12.
         */
        private Integer col = 12;

        /**
         * label for select fields
         */
        private String itemText = "name";

        /**
         * value for select fields
         */
        private String itemValue = "value";

        /**
         * for object fields
         */
        private Boolean returnObject = false;

        /**
         * Items of the select field.
         */
        private List<Map<String, Object>> items = new ArrayList<>();

        /**
         * Rules of the field.
         * Used for validation in the frontend.
         */
        private List<Map<String, Object>> rules = new ArrayList<>();

    }
}
