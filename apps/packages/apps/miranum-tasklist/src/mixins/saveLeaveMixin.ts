import {Component, Vue} from "vue-property-decorator";

/**
 * Mit dem SaveLeave Mixin kann ein Datenverlust durch ungewolltest Navigieren verhindert werden.
 *
 * Das Mixin lässt sich über die Mixins Funktion vom `vue-property-decorator` einbinden.
 *
 * Anschließend kann mittels des Überschreibens der `isDirty()` Methode festgelegt werden,
 * ob sicher navigiert werden kann oder eine Rückfrage an den Nutzer stattfinden soll.
 * Diese Rückfrage kann z.B. über einen Dialog gelöst werden. Hierzu bietet das SaveLeaveMixin
 * ein `saveLeaveDialog` Flag an. Für genererische Dialoge bietet das SaveLeaveMixin bereits Titel und
 * Text mit an.
 *
 * Mit dem Aufruf von `leave()` oder `cancel()` kann die Entscheidung des Nutzers ausgeführt werden.
 */
Component.registerHooks([
    'beforeRouteLeave',
]);
@Component
export default class SaveLeaveMixin extends Vue {
    name = "saveLeaveMixin";


    saveLeaveDialogTitle = 'Ungespeicherte Änderungen';
    saveLeaveDialogText = 'Es sind ungespeicherte Änderungen vorhanden. Wollen Sie die Seite verlassen?';
    saveLeaveDialog = false;
    isSave = false;
    // eslint-disable-next-line
    next: any = null;

    // eslint-disable-next-line
    beforeRouteLeave(to: any, from: any, next: any): void {
        if (this.isDirty() && !this.isSave) {
            this.saveLeaveDialog = true;
            this.next = next;
        } else {
            this.saveLeaveDialog = false;
            next();
        }
    }

    cancel(): void {
        this.saveLeaveDialog = false;
        this.next(false);
    }

    leave(): void {
        this.next();
    }

    /**
     * Methode sollte überschrieben werden.
     * In dieser Methode wird geprüft, ob der Nutzer ungespeicherte Änderungen eingegeben hat.
     */
    isDirty(): boolean {
        return true;
    }

}
