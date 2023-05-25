import Vue from "vue";

export class DwfFormRenderer extends Vue {
    options: any;
    buttonText: string;
    value: any;
    schema: any;
}


export interface Form {
    key: string;
    type: string;
    "x-display": string;
    allOf: Section[];
}

export interface Section {
    key: string;
    type: string;
    title: string;
    description?: string;
    allOf: (Container | FormFieldContainer | OptionalContainer)[];
    "x-options"?: {
        "sectionsTitlesClasses": string [];
    }
}

export interface Container {
    key: string;
    type: string;
    containerType: string;
    title: string;
    description?: string;
    "x-options"?: {
        "childrenClass": string
    };
}

export interface OptionalContainer extends Container {
    oneOf: SelectableSubSchema [];
}

export interface SelectableSubSchema {
    title: string;
    properties: Record<string, (FormEntryConst | FormField)>;
    "x-options": {
        "childrenClass": string
    };
}

export interface FormFieldContainer extends Container {
    key: string;
    properties: Record<string, (FormEntryConst | FormField)>;
}

export interface FormEntry {
    type: string;
}

export interface FormEntryConst extends FormEntry {
    const: string;
}

export interface FieldColProps {
    "cols"?: number;
    "sm"?: number;
}

export interface XProps {
    outlined: boolean;
    dense: boolean;
}

export interface FormField extends FormEntry {
    title: string;
    fieldType: string;
    description?: string;
    items?: any;
    properties?: Record<string, (FormEntryConst | FormField)>;
    format?: string;
    "x-display"?: string;
    "x-options"?: {
        fieldColProps: FieldColProps;
    };
    "x-props"?: XProps;
    "x-if"?: string;
    "x-rules"?: string [];
}


