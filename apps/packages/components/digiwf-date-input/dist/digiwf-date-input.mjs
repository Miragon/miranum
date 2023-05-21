import { VTextField as v, VTooltip as h, VBtn as m, VIcon as C } from "vuetify/lib";
import { defineComponent as V, ref as b } from "vue";
const y = V({
  props: [
    "schema",
    "on"
  ],
  setup({ schema: r, on: e }) {
    var l;
    const { title: i, readOnly: s, description: a, default: u } = r, { dense: d, outlined: f } = r["x-props"];
    let n = [];
    (l = r["x-rules"]) != null && l.includes("required") && n.push((o) => !!o || "Dieses Feld ist ein Pflichtfeld");
    const t = b(u);
    return {
      label: i,
      description: a,
      dense: d,
      outlined: f,
      readOnly: s,
      dateValue: t,
      rules: n,
      onChange: () => {
        e != null && e.input && e.input(t.value);
      }
    };
  }
});
function g(r, e, i, s, a, u, d, f) {
  var n = typeof r == "function" ? r.options : r;
  e && (n.render = e, n.staticRenderFns = i, n._compiled = !0), s && (n.functional = !0), u && (n._scopeId = "data-v-" + u);
  var t;
  if (d ? (t = function(o) {
    o = o || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext, !o && typeof __VUE_SSR_CONTEXT__ < "u" && (o = __VUE_SSR_CONTEXT__), a && a.call(this, o), o && o._registeredComponents && o._registeredComponents.add(d);
  }, n._ssrRegister = t) : a && (t = f ? function() {
    a.call(
      this,
      (n.functional ? this.parent : this).$root.$options.shadowRoot
    );
  } : a), t)
    if (n.functional) {
      n._injectStyles = t;
      var _ = n.render;
      n.render = function(p, c) {
        return t.call(c), _(p, c);
      };
    } else {
      var l = n.beforeCreate;
      n.beforeCreate = l ? [].concat(l, t) : [t];
    }
  return {
    exports: r,
    options: n
  };
}
var k = function() {
  var e = this, i = e._self._c;
  return e._self._setupProxy, i(v, { attrs: { type: "date", label: e.label, dense: e.dense, outlined: e.outlined, disabled: e.readOnly, rules: e.rules }, on: { change: e.onChange }, scopedSlots: e._u([{ key: "append-outer", fn: function() {
    return [e.description ? i(h, { attrs: { left: "", "open-on-hover": !1 }, scopedSlots: e._u([{ key: "activator", fn: function({ on: s }) {
      return [i(m, { attrs: { icon: "", "retain-focus-on-click": "" }, on: { click: s.click, blur: s.blur } }, [i(C, [e._v(" mdi-information")])], 1)];
    } }], null, !1, 4286154591) }, [i("div", { staticClass: "tooltip" }, [e._v(e._s(e.description))])]) : e._e()];
  }, proxy: !0 }]), model: { value: e.dateValue, callback: function(s) {
    e.dateValue = s;
  }, expression: "dateValue" } });
}, R = [], T = /* @__PURE__ */ g(
  y,
  k,
  R,
  !1,
  null,
  "ad1345cf",
  null,
  null
);
const F = T.exports;
export {
  F as DwfDateInput
};
