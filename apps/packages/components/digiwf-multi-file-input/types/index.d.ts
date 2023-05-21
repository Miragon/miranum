import Vue from "vue";

export class DwfMultiFileInput extends Vue {
}

export interface DocumentData {
    type: string;
    name: string;
    data: string;
    size: number;
}

export interface FormContext {
    id: string;
    type: string;
}


