import { defineComponent as O, computed as b } from "vue";
var v = function(r) {
  return S(r) && !j(r);
};
function S(e) {
  return !!e && typeof e == "object";
}
function j(e) {
  var r = Object.prototype.toString.call(e);
  return r === "[object RegExp]" || r === "[object Date]" || w(e);
}
var M = typeof Symbol == "function" && Symbol.for, E = M ? Symbol.for("react.element") : 60103;
function w(e) {
  return e.$$typeof === E;
}
function T(e) {
  return Array.isArray(e) ? [] : {};
}
function l(e, r) {
  return r.clone !== !1 && r.isMergeableObject(e) ? a(T(e), e, r) : e;
}
function A(e, r, n) {
  return e.concat(r).map(function(o) {
    return l(o, n);
  });
}
function C(e, r) {
  if (!r.customMerge)
    return a;
  var n = r.customMerge(e);
  return typeof n == "function" ? n : a;
}
function P(e) {
  return Object.getOwnPropertySymbols ? Object.getOwnPropertySymbols(e).filter(function(r) {
    return e.propertyIsEnumerable(r);
  }) : [];
}
function h(e) {
  return Object.keys(e).concat(P(e));
}
function y(e, r) {
  try {
    return r in e;
  } catch {
    return !1;
  }
}
function I(e, r) {
  return y(e, r) && !(Object.hasOwnProperty.call(e, r) && Object.propertyIsEnumerable.call(e, r));
}
function R(e, r, n) {
  var o = {};
  return n.isMergeableObject(e) && h(e).forEach(function(t) {
    o[t] = l(e[t], n);
  }), h(r).forEach(function(t) {
    I(e, t) || (y(e, t) && n.isMergeableObject(r[t]) ? o[t] = C(t, n)(e[t], r[t], n) : o[t] = l(r[t], n));
  }), o;
}
function a(e, r, n) {
  n = n || {}, n.arrayMerge = n.arrayMerge || A, n.isMergeableObject = n.isMergeableObject || v, n.cloneUnlessOtherwiseSpecified = l;
  var o = Array.isArray(r), t = Array.isArray(e), u = o === t;
  return u ? o ? n.arrayMerge(e, r, n) : R(e, r, n) : l(r, n);
}
a.all = function(r, n) {
  if (!Array.isArray(r))
    throw new Error("first argument should be an array");
  return r.reduce(function(o, t) {
    return a(o, t, n);
  }, {});
};
var D = a, $ = D;
const F = O({
  props: ["options", "buttonText", "value", "schema"],
  emits: ["input"],
  setup(e, { emit: r }) {
    const n = {
      editMode: "inline",
      disableSorting: !0,
      sectionsClass: "pl-0 col-12 pb-0 pt-0 pr-0",
      objectContainerClass: "pl-0 pb-0 pt-0 pr-0",
      dialogProps: { maxWidth: 1e3 },
      timePickerProps: {
        format: "24hr"
      },
      messages: {
        required: "Dieses Feld ist ein Pflichtfeld",
        preview: "Vorschau",
        mdeGuide: "Dokumentation",
        continue: "weiter",
        minItems: "Mindestens {minItems} Eintr\xE4ge",
        maxItems: "Nicht mehr als {maxItems} Eintr\xE4ge"
      }
    }, o = {
      time: function(i, c) {
        const m = new Date("".concat(new Date().toISOString().split("T")[0], "T").concat(i));
        return new Date(m.getTime() + 6e4 * m.getTimezoneOffset()).toLocaleTimeString(c);
      }
    }, t = {
      required: function(i) {
        return !!i && i !== "" || i === 0 || "Dieses Feld ist ein Pflichtfeld";
      },
      requiredObject: function(i) {
        return !!i && (i.amount >= 1 || i.length >= 1) || "Dieses Feld ist ein Pflichtfeld";
      }
    }, u = b(() => e.options && e.options.readOnly ? {
      ...e.schema,
      readOnly: !0
    } : e.schema), f = b(() => ({
      rules: t,
      formats: o,
      ...$(n, e.options)
    }));
    return {
      currentSchema: u,
      input: (i) => {
        r("input", i);
      },
      currentOptions: f
    };
  }
});
function N(e, r, n, o, t, u, f, d) {
  var i = typeof e == "function" ? e.options : e;
  r && (i.render = r, i.staticRenderFns = n, i._compiled = !0), o && (i.functional = !0), u && (i._scopeId = "data-v-" + u);
  var c;
  if (f ? (c = function(s) {
    s = s || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext, !s && typeof __VUE_SSR_CONTEXT__ < "u" && (s = __VUE_SSR_CONTEXT__), t && t.call(this, s), s && s._registeredComponents && s._registeredComponents.add(f);
  }, i._ssrRegister = c) : t && (c = d ? function() {
    t.call(
      this,
      (i.functional ? this.parent : this).$root.$options.shadowRoot
    );
  } : t), c)
    if (i.functional) {
      i._injectStyles = c;
      var m = i.render;
      i.render = function(g, _) {
        return c.call(_), m(g, _);
      };
    } else {
      var p = i.beforeCreate;
      i.beforeCreate = p ? [].concat(p, c) : [c];
    }
  return {
    exports: e,
    options: i
  };
}
var U = function() {
  var r = this, n = r._self._c;
  return r._self._setupProxy, n("div", [n("Jsf", { attrs: { value: r.value, schema: r.currentSchema, options: r.currentOptions }, on: { input: r.input }, scopedSlots: r._u([r._l(r.$scopedSlots, function(o, t) {
    return { key: t, fn: function(u) {
      return [r._t(t, null, null, u)];
    } };
  })], null, !0) })], 1);
}, x = [], V = /* @__PURE__ */ N(
  F,
  U,
  x,
  !1,
  null,
  null,
  null,
  null
);
const z = V.exports;
export {
  z as DwfFormRenderer
};
