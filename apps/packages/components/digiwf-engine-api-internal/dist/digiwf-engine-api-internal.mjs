function Be(t, e) {
  return function() {
    return t.apply(e, arguments);
  };
}
const { toString: _e } = Object.prototype, { getPrototypeOf: Pe } = Object, we = ((t) => (e) => {
  const r = _e.call(e);
  return t[r] || (t[r] = r.slice(8, -1).toLowerCase());
})(/* @__PURE__ */ Object.create(null)), N = (t) => (t = t.toLowerCase(), (e) => we(e) === t), oe = (t) => (e) => typeof e === t, { isArray: L } = Array, Q = oe("undefined");
function ct(t) {
  return t !== null && !Q(t) && t.constructor !== null && !Q(t.constructor) && j(t.constructor.isBuffer) && t.constructor.isBuffer(t);
}
const Le = N("ArrayBuffer");
function dt(t) {
  let e;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? e = ArrayBuffer.isView(t) : e = t && t.buffer && Le(t.buffer), e;
}
const ut = oe("string"), j = oe("function"), He = oe("number"), Ue = (t) => t !== null && typeof t == "object", ht = (t) => t === !0 || t === !1, X = (t) => {
  if (we(t) !== "object")
    return !1;
  const e = Pe(t);
  return (e === null || e === Object.prototype || Object.getPrototypeOf(e) === null) && !(Symbol.toStringTag in t) && !(Symbol.iterator in t);
}, pt = N("Date"), ft = N("File"), mt = N("Blob"), gt = N("FileList"), Ft = (t) => Ue(t) && j(t.pipe), Ot = (t) => {
  const e = "[object FormData]";
  return t && (typeof FormData == "function" && t instanceof FormData || _e.call(t) === e || j(t.toString) && t.toString() === e);
}, yt = N("URLSearchParams"), Pt = (t) => t.trim ? t.trim() : t.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "");
function G(t, e, { allOwnKeys: r = !1 } = {}) {
  if (t === null || typeof t > "u")
    return;
  let s, n;
  if (typeof t != "object" && (t = [t]), L(t))
    for (s = 0, n = t.length; s < n; s++)
      e.call(null, t[s], s, t);
  else {
    const a = r ? Object.getOwnPropertyNames(t) : Object.keys(t), o = a.length;
    let i;
    for (s = 0; s < o; s++)
      i = a[s], e.call(null, t[i], i, t);
  }
}
function ve(t, e) {
  e = e.toLowerCase();
  const r = Object.keys(t);
  let s = r.length, n;
  for (; s-- > 0; )
    if (n = r[s], e === n.toLowerCase())
      return n;
  return null;
}
const Me = (() => typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : typeof window < "u" ? window : global)(), qe = (t) => !Q(t) && t !== Me;
function he() {
  const { caseless: t } = qe(this) && this || {}, e = {}, r = (s, n) => {
    const a = t && ve(e, n) || n;
    X(e[a]) && X(s) ? e[a] = he(e[a], s) : X(s) ? e[a] = he({}, s) : L(s) ? e[a] = s.slice() : e[a] = s;
  };
  for (let s = 0, n = arguments.length; s < n; s++)
    arguments[s] && G(arguments[s], r);
  return e;
}
const wt = (t, e, r, { allOwnKeys: s } = {}) => (G(e, (n, a) => {
  r && j(n) ? t[a] = Be(n, r) : t[a] = n;
}, { allOwnKeys: s }), t), Ut = (t) => (t.charCodeAt(0) === 65279 && (t = t.slice(1)), t), At = (t, e, r, s) => {
  t.prototype = Object.create(e.prototype, s), t.prototype.constructor = t, Object.defineProperty(t, "super", {
    value: e.prototype
  }), r && Object.assign(t.prototype, r);
}, Rt = (t, e, r, s) => {
  let n, a, o;
  const i = {};
  if (e = e || {}, t == null)
    return e;
  do {
    for (n = Object.getOwnPropertyNames(t), a = n.length; a-- > 0; )
      o = n[a], (!s || s(o, t, e)) && !i[o] && (e[o] = t[o], i[o] = !0);
    t = r !== !1 && Pe(t);
  } while (t && (!r || r(t, e)) && t !== Object.prototype);
  return e;
}, Vt = (t, e, r) => {
  t = String(t), (r === void 0 || r > t.length) && (r = t.length), r -= e.length;
  const s = t.indexOf(e, r);
  return s !== -1 && s === r;
}, bt = (t) => {
  if (!t)
    return null;
  if (L(t))
    return t;
  let e = t.length;
  if (!He(e))
    return null;
  const r = new Array(e);
  for (; e-- > 0; )
    r[e] = t[e];
  return r;
}, Tt = ((t) => (e) => t && e instanceof t)(typeof Uint8Array < "u" && Pe(Uint8Array)), St = (t, e) => {
  const s = (t && t[Symbol.iterator]).call(t);
  let n;
  for (; (n = s.next()) && !n.done; ) {
    const a = n.value;
    e.call(t, a[0], a[1]);
  }
}, Ct = (t, e) => {
  let r;
  const s = [];
  for (; (r = t.exec(e)) !== null; )
    s.push(r);
  return s;
}, Et = N("HTMLFormElement"), xt = (t) => t.toLowerCase().replace(
  /[_-\s]([a-z\d])(\w*)/g,
  function(r, s, n) {
    return s.toUpperCase() + n;
  }
), Te = (({ hasOwnProperty: t }) => (e, r) => t.call(e, r))(Object.prototype), Dt = N("RegExp"), $e = (t, e) => {
  const r = Object.getOwnPropertyDescriptors(t), s = {};
  G(r, (n, a) => {
    e(n, a, t) !== !1 && (s[a] = n);
  }), Object.defineProperties(t, s);
}, kt = (t) => {
  $e(t, (e, r) => {
    if (j(t) && ["arguments", "caller", "callee"].indexOf(r) !== -1)
      return !1;
    const s = t[r];
    if (!!j(s)) {
      if (e.enumerable = !1, "writable" in e) {
        e.writable = !1;
        return;
      }
      e.set || (e.set = () => {
        throw Error("Can not rewrite read-only method '" + r + "'");
      });
    }
  });
}, Nt = (t, e) => {
  const r = {}, s = (n) => {
    n.forEach((a) => {
      r[a] = !0;
    });
  };
  return L(t) ? s(t) : s(String(t).split(e)), r;
}, It = () => {
}, jt = (t, e) => (t = +t, Number.isFinite(t) ? t : e), Bt = (t) => {
  const e = new Array(10), r = (s, n) => {
    if (Ue(s)) {
      if (e.indexOf(s) >= 0)
        return;
      if (!("toJSON" in s)) {
        e[n] = s;
        const a = L(s) ? [] : {};
        return G(s, (o, i) => {
          const l = r(o, n + 1);
          !Q(l) && (a[i] = l);
        }), e[n] = void 0, a;
      }
    }
    return s;
  };
  return r(t, 0);
}, d = {
  isArray: L,
  isArrayBuffer: Le,
  isBuffer: ct,
  isFormData: Ot,
  isArrayBufferView: dt,
  isString: ut,
  isNumber: He,
  isBoolean: ht,
  isObject: Ue,
  isPlainObject: X,
  isUndefined: Q,
  isDate: pt,
  isFile: ft,
  isBlob: mt,
  isRegExp: Dt,
  isFunction: j,
  isStream: Ft,
  isURLSearchParams: yt,
  isTypedArray: Tt,
  isFileList: gt,
  forEach: G,
  merge: he,
  extend: wt,
  trim: Pt,
  stripBOM: Ut,
  inherits: At,
  toFlatObject: Rt,
  kindOf: we,
  kindOfTest: N,
  endsWith: Vt,
  toArray: bt,
  forEachEntry: St,
  matchAll: Ct,
  isHTMLForm: Et,
  hasOwnProperty: Te,
  hasOwnProp: Te,
  reduceDescriptors: $e,
  freezeMethods: kt,
  toObjectSet: Nt,
  toCamelCase: xt,
  noop: It,
  toFiniteNumber: jt,
  findKey: ve,
  global: Me,
  isContextDefined: qe,
  toJSONObject: Bt
};
function A(t, e, r, s, n) {
  Error.call(this), Error.captureStackTrace ? Error.captureStackTrace(this, this.constructor) : this.stack = new Error().stack, this.message = t, this.name = "AxiosError", e && (this.code = e), r && (this.config = r), s && (this.request = s), n && (this.response = n);
}
d.inherits(A, Error, {
  toJSON: function() {
    return {
      message: this.message,
      name: this.name,
      description: this.description,
      number: this.number,
      fileName: this.fileName,
      lineNumber: this.lineNumber,
      columnNumber: this.columnNumber,
      stack: this.stack,
      config: d.toJSONObject(this.config),
      code: this.code,
      status: this.response && this.response.status ? this.response.status : null
    };
  }
});
const Je = A.prototype, Qe = {};
[
  "ERR_BAD_OPTION_VALUE",
  "ERR_BAD_OPTION",
  "ECONNABORTED",
  "ETIMEDOUT",
  "ERR_NETWORK",
  "ERR_FR_TOO_MANY_REDIRECTS",
  "ERR_DEPRECATED",
  "ERR_BAD_RESPONSE",
  "ERR_BAD_REQUEST",
  "ERR_CANCELED",
  "ERR_NOT_SUPPORT",
  "ERR_INVALID_URL"
].forEach((t) => {
  Qe[t] = { value: t };
});
Object.defineProperties(A, Qe);
Object.defineProperty(Je, "isAxiosError", { value: !0 });
A.from = (t, e, r, s, n, a) => {
  const o = Object.create(Je);
  return d.toFlatObject(t, o, function(l) {
    return l !== Error.prototype;
  }, (i) => i !== "isAxiosError"), A.call(o, t.message, e, r, s, n), o.cause = t, o.name = t.name, a && Object.assign(o, a), o;
};
var _t = typeof self == "object" ? self.FormData : window.FormData;
const Lt = _t;
function pe(t) {
  return d.isPlainObject(t) || d.isArray(t);
}
function Ge(t) {
  return d.endsWith(t, "[]") ? t.slice(0, -2) : t;
}
function Se(t, e, r) {
  return t ? t.concat(e).map(function(n, a) {
    return n = Ge(n), !r && a ? "[" + n + "]" : n;
  }).join(r ? "." : "") : e;
}
function Ht(t) {
  return d.isArray(t) && !t.some(pe);
}
const vt = d.toFlatObject(d, {}, null, function(e) {
  return /^is[A-Z]/.test(e);
});
function Mt(t) {
  return t && d.isFunction(t.append) && t[Symbol.toStringTag] === "FormData" && t[Symbol.iterator];
}
function ie(t, e, r) {
  if (!d.isObject(t))
    throw new TypeError("target must be an object");
  e = e || new (Lt || FormData)(), r = d.toFlatObject(r, {
    metaTokens: !0,
    dots: !1,
    indexes: !1
  }, !1, function(U, D) {
    return !d.isUndefined(D[U]);
  });
  const s = r.metaTokens, n = r.visitor || u, a = r.dots, o = r.indexes, l = (r.Blob || typeof Blob < "u" && Blob) && Mt(e);
  if (!d.isFunction(n))
    throw new TypeError("visitor must be a function");
  function c(f) {
    if (f === null)
      return "";
    if (d.isDate(f))
      return f.toISOString();
    if (!l && d.isBlob(f))
      throw new A("Blob is not supported. Use a Buffer instead.");
    return d.isArrayBuffer(f) || d.isTypedArray(f) ? l && typeof Blob == "function" ? new Blob([f]) : Buffer.from(f) : f;
  }
  function u(f, U, D) {
    let C = f;
    if (f && !D && typeof f == "object") {
      if (d.endsWith(U, "{}"))
        U = s ? U : U.slice(0, -2), f = JSON.stringify(f);
      else if (d.isArray(f) && Ht(f) || d.isFileList(f) || d.endsWith(U, "[]") && (C = d.toArray(f)))
        return U = Ge(U), C.forEach(function(K, lt) {
          !(d.isUndefined(K) || K === null) && e.append(
            o === !0 ? Se([U], lt, a) : o === null ? U : U + "[]",
            c(K)
          );
        }), !1;
    }
    return pe(f) ? !0 : (e.append(Se(D, U, a), c(f)), !1);
  }
  const h = [], V = Object.assign(vt, {
    defaultVisitor: u,
    convertValue: c,
    isVisitable: pe
  });
  function R(f, U) {
    if (!d.isUndefined(f)) {
      if (h.indexOf(f) !== -1)
        throw Error("Circular reference detected in " + U.join("."));
      h.push(f), d.forEach(f, function(C, B) {
        (!(d.isUndefined(C) || C === null) && n.call(
          e,
          C,
          d.isString(B) ? B.trim() : B,
          U,
          V
        )) === !0 && R(C, U ? U.concat(B) : [B]);
      }), h.pop();
    }
  }
  if (!d.isObject(t))
    throw new TypeError("data must be an object");
  return R(t), e;
}
function Ce(t) {
  const e = {
    "!": "%21",
    "'": "%27",
    "(": "%28",
    ")": "%29",
    "~": "%7E",
    "%20": "+",
    "%00": "\0"
  };
  return encodeURIComponent(t).replace(/[!'()~]|%20|%00/g, function(s) {
    return e[s];
  });
}
function Ae(t, e) {
  this._pairs = [], t && ie(t, this, e);
}
const ze = Ae.prototype;
ze.append = function(e, r) {
  this._pairs.push([e, r]);
};
ze.toString = function(e) {
  const r = e ? function(s) {
    return e.call(this, s, Ce);
  } : Ce;
  return this._pairs.map(function(n) {
    return r(n[0]) + "=" + r(n[1]);
  }, "").join("&");
};
function qt(t) {
  return encodeURIComponent(t).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
function Ke(t, e, r) {
  if (!e)
    return t;
  const s = r && r.encode || qt, n = r && r.serialize;
  let a;
  if (n ? a = n(e, r) : a = d.isURLSearchParams(e) ? e.toString() : new Ae(e, r).toString(s), a) {
    const o = t.indexOf("#");
    o !== -1 && (t = t.slice(0, o)), t += (t.indexOf("?") === -1 ? "?" : "&") + a;
  }
  return t;
}
class $t {
  constructor() {
    this.handlers = [];
  }
  use(e, r, s) {
    return this.handlers.push({
      fulfilled: e,
      rejected: r,
      synchronous: s ? s.synchronous : !1,
      runWhen: s ? s.runWhen : null
    }), this.handlers.length - 1;
  }
  eject(e) {
    this.handlers[e] && (this.handlers[e] = null);
  }
  clear() {
    this.handlers && (this.handlers = []);
  }
  forEach(e) {
    d.forEach(this.handlers, function(s) {
      s !== null && e(s);
    });
  }
}
const Ee = $t, We = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, Jt = typeof URLSearchParams < "u" ? URLSearchParams : Ae, Qt = FormData, Gt = (() => {
  let t;
  return typeof navigator < "u" && ((t = navigator.product) === "ReactNative" || t === "NativeScript" || t === "NS") ? !1 : typeof window < "u" && typeof document < "u";
})(), zt = (() => typeof WorkerGlobalScope < "u" && self instanceof WorkerGlobalScope && typeof self.importScripts == "function")(), x = {
  isBrowser: !0,
  classes: {
    URLSearchParams: Jt,
    FormData: Qt,
    Blob
  },
  isStandardBrowserEnv: Gt,
  isStandardBrowserWebWorkerEnv: zt,
  protocols: ["http", "https", "file", "blob", "url", "data"]
};
function Kt(t, e) {
  return ie(t, new x.classes.URLSearchParams(), Object.assign({
    visitor: function(r, s, n, a) {
      return x.isNode && d.isBuffer(r) ? (this.append(s, r.toString("base64")), !1) : a.defaultVisitor.apply(this, arguments);
    }
  }, e));
}
function Wt(t) {
  return d.matchAll(/\w+|\[(\w*)]/g, t).map((e) => e[0] === "[]" ? "" : e[1] || e[0]);
}
function Xt(t) {
  const e = {}, r = Object.keys(t);
  let s;
  const n = r.length;
  let a;
  for (s = 0; s < n; s++)
    a = r[s], e[a] = t[a];
  return e;
}
function Xe(t) {
  function e(r, s, n, a) {
    let o = r[a++];
    const i = Number.isFinite(+o), l = a >= r.length;
    return o = !o && d.isArray(n) ? n.length : o, l ? (d.hasOwnProp(n, o) ? n[o] = [n[o], s] : n[o] = s, !i) : ((!n[o] || !d.isObject(n[o])) && (n[o] = []), e(r, s, n[o], a) && d.isArray(n[o]) && (n[o] = Xt(n[o])), !i);
  }
  if (d.isFormData(t) && d.isFunction(t.entries)) {
    const r = {};
    return d.forEachEntry(t, (s, n) => {
      e(Wt(s), n, r, 0);
    }), r;
  }
  return null;
}
const Yt = {
  "Content-Type": void 0
};
function Zt(t, e, r) {
  if (d.isString(t))
    try {
      return (e || JSON.parse)(t), d.trim(t);
    } catch (s) {
      if (s.name !== "SyntaxError")
        throw s;
    }
  return (r || JSON.stringify)(t);
}
const le = {
  transitional: We,
  adapter: ["xhr", "http"],
  transformRequest: [function(e, r) {
    const s = r.getContentType() || "", n = s.indexOf("application/json") > -1, a = d.isObject(e);
    if (a && d.isHTMLForm(e) && (e = new FormData(e)), d.isFormData(e))
      return n && n ? JSON.stringify(Xe(e)) : e;
    if (d.isArrayBuffer(e) || d.isBuffer(e) || d.isStream(e) || d.isFile(e) || d.isBlob(e))
      return e;
    if (d.isArrayBufferView(e))
      return e.buffer;
    if (d.isURLSearchParams(e))
      return r.setContentType("application/x-www-form-urlencoded;charset=utf-8", !1), e.toString();
    let i;
    if (a) {
      if (s.indexOf("application/x-www-form-urlencoded") > -1)
        return Kt(e, this.formSerializer).toString();
      if ((i = d.isFileList(e)) || s.indexOf("multipart/form-data") > -1) {
        const l = this.env && this.env.FormData;
        return ie(
          i ? { "files[]": e } : e,
          l && new l(),
          this.formSerializer
        );
      }
    }
    return a || n ? (r.setContentType("application/json", !1), Zt(e)) : e;
  }],
  transformResponse: [function(e) {
    const r = this.transitional || le.transitional, s = r && r.forcedJSONParsing, n = this.responseType === "json";
    if (e && d.isString(e) && (s && !this.responseType || n)) {
      const o = !(r && r.silentJSONParsing) && n;
      try {
        return JSON.parse(e);
      } catch (i) {
        if (o)
          throw i.name === "SyntaxError" ? A.from(i, A.ERR_BAD_RESPONSE, this, null, this.response) : i;
      }
    }
    return e;
  }],
  timeout: 0,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
  maxContentLength: -1,
  maxBodyLength: -1,
  env: {
    FormData: x.classes.FormData,
    Blob: x.classes.Blob
  },
  validateStatus: function(e) {
    return e >= 200 && e < 300;
  },
  headers: {
    common: {
      Accept: "application/json, text/plain, */*"
    }
  }
};
d.forEach(["delete", "get", "head"], function(e) {
  le.headers[e] = {};
});
d.forEach(["post", "put", "patch"], function(e) {
  le.headers[e] = d.merge(Yt);
});
const Re = le, er = d.toObjectSet([
  "age",
  "authorization",
  "content-length",
  "content-type",
  "etag",
  "expires",
  "from",
  "host",
  "if-modified-since",
  "if-unmodified-since",
  "last-modified",
  "location",
  "max-forwards",
  "proxy-authorization",
  "referer",
  "retry-after",
  "user-agent"
]), tr = (t) => {
  const e = {};
  let r, s, n;
  return t && t.split(`
`).forEach(function(o) {
    n = o.indexOf(":"), r = o.substring(0, n).trim().toLowerCase(), s = o.substring(n + 1).trim(), !(!r || e[r] && er[r]) && (r === "set-cookie" ? e[r] ? e[r].push(s) : e[r] = [s] : e[r] = e[r] ? e[r] + ", " + s : s);
  }), e;
}, xe = Symbol("internals");
function H(t) {
  return t && String(t).trim().toLowerCase();
}
function Y(t) {
  return t === !1 || t == null ? t : d.isArray(t) ? t.map(Y) : String(t);
}
function rr(t) {
  const e = /* @__PURE__ */ Object.create(null), r = /([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g;
  let s;
  for (; s = r.exec(t); )
    e[s[1]] = s[2];
  return e;
}
function sr(t) {
  return /^[-_a-zA-Z]+$/.test(t.trim());
}
function De(t, e, r, s) {
  if (d.isFunction(s))
    return s.call(this, e, r);
  if (!!d.isString(e)) {
    if (d.isString(s))
      return e.indexOf(s) !== -1;
    if (d.isRegExp(s))
      return s.test(e);
  }
}
function nr(t) {
  return t.trim().toLowerCase().replace(/([a-z\d])(\w*)/g, (e, r, s) => r.toUpperCase() + s);
}
function ar(t, e) {
  const r = d.toCamelCase(" " + e);
  ["get", "set", "has"].forEach((s) => {
    Object.defineProperty(t, s + r, {
      value: function(n, a, o) {
        return this[s].call(this, e, n, a, o);
      },
      configurable: !0
    });
  });
}
class ce {
  constructor(e) {
    e && this.set(e);
  }
  set(e, r, s) {
    const n = this;
    function a(i, l, c) {
      const u = H(l);
      if (!u)
        throw new Error("header name must be a non-empty string");
      const h = d.findKey(n, u);
      (!h || n[h] === void 0 || c === !0 || c === void 0 && n[h] !== !1) && (n[h || l] = Y(i));
    }
    const o = (i, l) => d.forEach(i, (c, u) => a(c, u, l));
    return d.isPlainObject(e) || e instanceof this.constructor ? o(e, r) : d.isString(e) && (e = e.trim()) && !sr(e) ? o(tr(e), r) : e != null && a(r, e, s), this;
  }
  get(e, r) {
    if (e = H(e), e) {
      const s = d.findKey(this, e);
      if (s) {
        const n = this[s];
        if (!r)
          return n;
        if (r === !0)
          return rr(n);
        if (d.isFunction(r))
          return r.call(this, n, s);
        if (d.isRegExp(r))
          return r.exec(n);
        throw new TypeError("parser must be boolean|regexp|function");
      }
    }
  }
  has(e, r) {
    if (e = H(e), e) {
      const s = d.findKey(this, e);
      return !!(s && (!r || De(this, this[s], s, r)));
    }
    return !1;
  }
  delete(e, r) {
    const s = this;
    let n = !1;
    function a(o) {
      if (o = H(o), o) {
        const i = d.findKey(s, o);
        i && (!r || De(s, s[i], i, r)) && (delete s[i], n = !0);
      }
    }
    return d.isArray(e) ? e.forEach(a) : a(e), n;
  }
  clear() {
    return Object.keys(this).forEach(this.delete.bind(this));
  }
  normalize(e) {
    const r = this, s = {};
    return d.forEach(this, (n, a) => {
      const o = d.findKey(s, a);
      if (o) {
        r[o] = Y(n), delete r[a];
        return;
      }
      const i = e ? nr(a) : String(a).trim();
      i !== a && delete r[a], r[i] = Y(n), s[i] = !0;
    }), this;
  }
  concat(...e) {
    return this.constructor.concat(this, ...e);
  }
  toJSON(e) {
    const r = /* @__PURE__ */ Object.create(null);
    return d.forEach(this, (s, n) => {
      s != null && s !== !1 && (r[n] = e && d.isArray(s) ? s.join(", ") : s);
    }), r;
  }
  [Symbol.iterator]() {
    return Object.entries(this.toJSON())[Symbol.iterator]();
  }
  toString() {
    return Object.entries(this.toJSON()).map(([e, r]) => e + ": " + r).join(`
`);
  }
  get [Symbol.toStringTag]() {
    return "AxiosHeaders";
  }
  static from(e) {
    return e instanceof this ? e : new this(e);
  }
  static concat(e, ...r) {
    const s = new this(e);
    return r.forEach((n) => s.set(n)), s;
  }
  static accessor(e) {
    const s = (this[xe] = this[xe] = {
      accessors: {}
    }).accessors, n = this.prototype;
    function a(o) {
      const i = H(o);
      s[i] || (ar(n, o), s[i] = !0);
    }
    return d.isArray(e) ? e.forEach(a) : a(e), this;
  }
}
ce.accessor(["Content-Type", "Content-Length", "Accept", "Accept-Encoding", "User-Agent"]);
d.freezeMethods(ce.prototype);
d.freezeMethods(ce);
const k = ce;
function de(t, e) {
  const r = this || Re, s = e || r, n = k.from(s.headers);
  let a = s.data;
  return d.forEach(t, function(i) {
    a = i.call(r, a, n.normalize(), e ? e.status : void 0);
  }), n.normalize(), a;
}
function Ye(t) {
  return !!(t && t.__CANCEL__);
}
function z(t, e, r) {
  A.call(this, t ?? "canceled", A.ERR_CANCELED, e, r), this.name = "CanceledError";
}
d.inherits(z, A, {
  __CANCEL__: !0
});
const or = null;
function ir(t, e, r) {
  const s = r.config.validateStatus;
  !r.status || !s || s(r.status) ? t(r) : e(new A(
    "Request failed with status code " + r.status,
    [A.ERR_BAD_REQUEST, A.ERR_BAD_RESPONSE][Math.floor(r.status / 100) - 4],
    r.config,
    r.request,
    r
  ));
}
const lr = x.isStandardBrowserEnv ? function() {
  return {
    write: function(r, s, n, a, o, i) {
      const l = [];
      l.push(r + "=" + encodeURIComponent(s)), d.isNumber(n) && l.push("expires=" + new Date(n).toGMTString()), d.isString(a) && l.push("path=" + a), d.isString(o) && l.push("domain=" + o), i === !0 && l.push("secure"), document.cookie = l.join("; ");
    },
    read: function(r) {
      const s = document.cookie.match(new RegExp("(^|;\\s*)(" + r + ")=([^;]*)"));
      return s ? decodeURIComponent(s[3]) : null;
    },
    remove: function(r) {
      this.write(r, "", Date.now() - 864e5);
    }
  };
}() : function() {
  return {
    write: function() {
    },
    read: function() {
      return null;
    },
    remove: function() {
    }
  };
}();
function cr(t) {
  return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(t);
}
function dr(t, e) {
  return e ? t.replace(/\/+$/, "") + "/" + e.replace(/^\/+/, "") : t;
}
function Ze(t, e) {
  return t && !cr(e) ? dr(t, e) : e;
}
const ur = x.isStandardBrowserEnv ? function() {
  const e = /(msie|trident)/i.test(navigator.userAgent), r = document.createElement("a");
  let s;
  function n(a) {
    let o = a;
    return e && (r.setAttribute("href", o), o = r.href), r.setAttribute("href", o), {
      href: r.href,
      protocol: r.protocol ? r.protocol.replace(/:$/, "") : "",
      host: r.host,
      search: r.search ? r.search.replace(/^\?/, "") : "",
      hash: r.hash ? r.hash.replace(/^#/, "") : "",
      hostname: r.hostname,
      port: r.port,
      pathname: r.pathname.charAt(0) === "/" ? r.pathname : "/" + r.pathname
    };
  }
  return s = n(window.location.href), function(o) {
    const i = d.isString(o) ? n(o) : o;
    return i.protocol === s.protocol && i.host === s.host;
  };
}() : function() {
  return function() {
    return !0;
  };
}();
function hr(t) {
  const e = /^([-+\w]{1,25})(:?\/\/|:)/.exec(t);
  return e && e[1] || "";
}
function pr(t, e) {
  t = t || 10;
  const r = new Array(t), s = new Array(t);
  let n = 0, a = 0, o;
  return e = e !== void 0 ? e : 1e3, function(l) {
    const c = Date.now(), u = s[a];
    o || (o = c), r[n] = l, s[n] = c;
    let h = a, V = 0;
    for (; h !== n; )
      V += r[h++], h = h % t;
    if (n = (n + 1) % t, n === a && (a = (a + 1) % t), c - o < e)
      return;
    const R = u && c - u;
    return R ? Math.round(V * 1e3 / R) : void 0;
  };
}
function ke(t, e) {
  let r = 0;
  const s = pr(50, 250);
  return (n) => {
    const a = n.loaded, o = n.lengthComputable ? n.total : void 0, i = a - r, l = s(i), c = a <= o;
    r = a;
    const u = {
      loaded: a,
      total: o,
      progress: o ? a / o : void 0,
      bytes: i,
      rate: l || void 0,
      estimated: l && o && c ? (o - a) / l : void 0,
      event: n
    };
    u[e ? "download" : "upload"] = !0, t(u);
  };
}
const fr = typeof XMLHttpRequest < "u", mr = fr && function(t) {
  return new Promise(function(r, s) {
    let n = t.data;
    const a = k.from(t.headers).normalize(), o = t.responseType;
    let i;
    function l() {
      t.cancelToken && t.cancelToken.unsubscribe(i), t.signal && t.signal.removeEventListener("abort", i);
    }
    d.isFormData(n) && (x.isStandardBrowserEnv || x.isStandardBrowserWebWorkerEnv) && a.setContentType(!1);
    let c = new XMLHttpRequest();
    if (t.auth) {
      const R = t.auth.username || "", f = t.auth.password ? unescape(encodeURIComponent(t.auth.password)) : "";
      a.set("Authorization", "Basic " + btoa(R + ":" + f));
    }
    const u = Ze(t.baseURL, t.url);
    c.open(t.method.toUpperCase(), Ke(u, t.params, t.paramsSerializer), !0), c.timeout = t.timeout;
    function h() {
      if (!c)
        return;
      const R = k.from(
        "getAllResponseHeaders" in c && c.getAllResponseHeaders()
      ), U = {
        data: !o || o === "text" || o === "json" ? c.responseText : c.response,
        status: c.status,
        statusText: c.statusText,
        headers: R,
        config: t,
        request: c
      };
      ir(function(C) {
        r(C), l();
      }, function(C) {
        s(C), l();
      }, U), c = null;
    }
    if ("onloadend" in c ? c.onloadend = h : c.onreadystatechange = function() {
      !c || c.readyState !== 4 || c.status === 0 && !(c.responseURL && c.responseURL.indexOf("file:") === 0) || setTimeout(h);
    }, c.onabort = function() {
      !c || (s(new A("Request aborted", A.ECONNABORTED, t, c)), c = null);
    }, c.onerror = function() {
      s(new A("Network Error", A.ERR_NETWORK, t, c)), c = null;
    }, c.ontimeout = function() {
      let f = t.timeout ? "timeout of " + t.timeout + "ms exceeded" : "timeout exceeded";
      const U = t.transitional || We;
      t.timeoutErrorMessage && (f = t.timeoutErrorMessage), s(new A(
        f,
        U.clarifyTimeoutError ? A.ETIMEDOUT : A.ECONNABORTED,
        t,
        c
      )), c = null;
    }, x.isStandardBrowserEnv) {
      const R = (t.withCredentials || ur(u)) && t.xsrfCookieName && lr.read(t.xsrfCookieName);
      R && a.set(t.xsrfHeaderName, R);
    }
    n === void 0 && a.setContentType(null), "setRequestHeader" in c && d.forEach(a.toJSON(), function(f, U) {
      c.setRequestHeader(U, f);
    }), d.isUndefined(t.withCredentials) || (c.withCredentials = !!t.withCredentials), o && o !== "json" && (c.responseType = t.responseType), typeof t.onDownloadProgress == "function" && c.addEventListener("progress", ke(t.onDownloadProgress, !0)), typeof t.onUploadProgress == "function" && c.upload && c.upload.addEventListener("progress", ke(t.onUploadProgress)), (t.cancelToken || t.signal) && (i = (R) => {
      !c || (s(!R || R.type ? new z(null, t, c) : R), c.abort(), c = null);
    }, t.cancelToken && t.cancelToken.subscribe(i), t.signal && (t.signal.aborted ? i() : t.signal.addEventListener("abort", i)));
    const V = hr(u);
    if (V && x.protocols.indexOf(V) === -1) {
      s(new A("Unsupported protocol " + V + ":", A.ERR_BAD_REQUEST, t));
      return;
    }
    c.send(n || null);
  });
}, Z = {
  http: or,
  xhr: mr
};
d.forEach(Z, (t, e) => {
  if (t) {
    try {
      Object.defineProperty(t, "name", { value: e });
    } catch {
    }
    Object.defineProperty(t, "adapterName", { value: e });
  }
});
const gr = {
  getAdapter: (t) => {
    t = d.isArray(t) ? t : [t];
    const { length: e } = t;
    let r, s;
    for (let n = 0; n < e && (r = t[n], !(s = d.isString(r) ? Z[r.toLowerCase()] : r)); n++)
      ;
    if (!s)
      throw s === !1 ? new A(
        `Adapter ${r} is not supported by the environment`,
        "ERR_NOT_SUPPORT"
      ) : new Error(
        d.hasOwnProp(Z, r) ? `Adapter '${r}' is not available in the build` : `Unknown adapter '${r}'`
      );
    if (!d.isFunction(s))
      throw new TypeError("adapter is not a function");
    return s;
  },
  adapters: Z
};
function ue(t) {
  if (t.cancelToken && t.cancelToken.throwIfRequested(), t.signal && t.signal.aborted)
    throw new z(null, t);
}
function Ne(t) {
  return ue(t), t.headers = k.from(t.headers), t.data = de.call(
    t,
    t.transformRequest
  ), ["post", "put", "patch"].indexOf(t.method) !== -1 && t.headers.setContentType("application/x-www-form-urlencoded", !1), gr.getAdapter(t.adapter || Re.adapter)(t).then(function(s) {
    return ue(t), s.data = de.call(
      t,
      t.transformResponse,
      s
    ), s.headers = k.from(s.headers), s;
  }, function(s) {
    return Ye(s) || (ue(t), s && s.response && (s.response.data = de.call(
      t,
      t.transformResponse,
      s.response
    ), s.response.headers = k.from(s.response.headers))), Promise.reject(s);
  });
}
const Ie = (t) => t instanceof k ? t.toJSON() : t;
function _(t, e) {
  e = e || {};
  const r = {};
  function s(c, u, h) {
    return d.isPlainObject(c) && d.isPlainObject(u) ? d.merge.call({ caseless: h }, c, u) : d.isPlainObject(u) ? d.merge({}, u) : d.isArray(u) ? u.slice() : u;
  }
  function n(c, u, h) {
    if (d.isUndefined(u)) {
      if (!d.isUndefined(c))
        return s(void 0, c, h);
    } else
      return s(c, u, h);
  }
  function a(c, u) {
    if (!d.isUndefined(u))
      return s(void 0, u);
  }
  function o(c, u) {
    if (d.isUndefined(u)) {
      if (!d.isUndefined(c))
        return s(void 0, c);
    } else
      return s(void 0, u);
  }
  function i(c, u, h) {
    if (h in e)
      return s(c, u);
    if (h in t)
      return s(void 0, c);
  }
  const l = {
    url: a,
    method: a,
    data: a,
    baseURL: o,
    transformRequest: o,
    transformResponse: o,
    paramsSerializer: o,
    timeout: o,
    timeoutMessage: o,
    withCredentials: o,
    adapter: o,
    responseType: o,
    xsrfCookieName: o,
    xsrfHeaderName: o,
    onUploadProgress: o,
    onDownloadProgress: o,
    decompress: o,
    maxContentLength: o,
    maxBodyLength: o,
    beforeRedirect: o,
    transport: o,
    httpAgent: o,
    httpsAgent: o,
    cancelToken: o,
    socketPath: o,
    responseEncoding: o,
    validateStatus: i,
    headers: (c, u) => n(Ie(c), Ie(u), !0)
  };
  return d.forEach(Object.keys(t).concat(Object.keys(e)), function(u) {
    const h = l[u] || n, V = h(t[u], e[u], u);
    d.isUndefined(V) && h !== i || (r[u] = V);
  }), r;
}
const et = "1.2.2", Ve = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach((t, e) => {
  Ve[t] = function(s) {
    return typeof s === t || "a" + (e < 1 ? "n " : " ") + t;
  };
});
const je = {};
Ve.transitional = function(e, r, s) {
  function n(a, o) {
    return "[Axios v" + et + "] Transitional option '" + a + "'" + o + (s ? ". " + s : "");
  }
  return (a, o, i) => {
    if (e === !1)
      throw new A(
        n(o, " has been removed" + (r ? " in " + r : "")),
        A.ERR_DEPRECATED
      );
    return r && !je[o] && (je[o] = !0, console.warn(
      n(
        o,
        " has been deprecated since v" + r + " and will be removed in the near future"
      )
    )), e ? e(a, o, i) : !0;
  };
};
function Fr(t, e, r) {
  if (typeof t != "object")
    throw new A("options must be an object", A.ERR_BAD_OPTION_VALUE);
  const s = Object.keys(t);
  let n = s.length;
  for (; n-- > 0; ) {
    const a = s[n], o = e[a];
    if (o) {
      const i = t[a], l = i === void 0 || o(i, a, t);
      if (l !== !0)
        throw new A("option " + a + " must be " + l, A.ERR_BAD_OPTION_VALUE);
      continue;
    }
    if (r !== !0)
      throw new A("Unknown option " + a, A.ERR_BAD_OPTION);
  }
}
const fe = {
  assertOptions: Fr,
  validators: Ve
}, I = fe.validators;
class ae {
  constructor(e) {
    this.defaults = e, this.interceptors = {
      request: new Ee(),
      response: new Ee()
    };
  }
  request(e, r) {
    typeof e == "string" ? (r = r || {}, r.url = e) : r = e || {}, r = _(this.defaults, r);
    const { transitional: s, paramsSerializer: n, headers: a } = r;
    s !== void 0 && fe.assertOptions(s, {
      silentJSONParsing: I.transitional(I.boolean),
      forcedJSONParsing: I.transitional(I.boolean),
      clarifyTimeoutError: I.transitional(I.boolean)
    }, !1), n !== void 0 && fe.assertOptions(n, {
      encode: I.function,
      serialize: I.function
    }, !0), r.method = (r.method || this.defaults.method || "get").toLowerCase();
    let o;
    o = a && d.merge(
      a.common,
      a[r.method]
    ), o && d.forEach(
      ["delete", "get", "head", "post", "put", "patch", "common"],
      (f) => {
        delete a[f];
      }
    ), r.headers = k.concat(o, a);
    const i = [];
    let l = !0;
    this.interceptors.request.forEach(function(U) {
      typeof U.runWhen == "function" && U.runWhen(r) === !1 || (l = l && U.synchronous, i.unshift(U.fulfilled, U.rejected));
    });
    const c = [];
    this.interceptors.response.forEach(function(U) {
      c.push(U.fulfilled, U.rejected);
    });
    let u, h = 0, V;
    if (!l) {
      const f = [Ne.bind(this), void 0];
      for (f.unshift.apply(f, i), f.push.apply(f, c), V = f.length, u = Promise.resolve(r); h < V; )
        u = u.then(f[h++], f[h++]);
      return u;
    }
    V = i.length;
    let R = r;
    for (h = 0; h < V; ) {
      const f = i[h++], U = i[h++];
      try {
        R = f(R);
      } catch (D) {
        U.call(this, D);
        break;
      }
    }
    try {
      u = Ne.call(this, R);
    } catch (f) {
      return Promise.reject(f);
    }
    for (h = 0, V = c.length; h < V; )
      u = u.then(c[h++], c[h++]);
    return u;
  }
  getUri(e) {
    e = _(this.defaults, e);
    const r = Ze(e.baseURL, e.url);
    return Ke(r, e.params, e.paramsSerializer);
  }
}
d.forEach(["delete", "get", "head", "options"], function(e) {
  ae.prototype[e] = function(r, s) {
    return this.request(_(s || {}, {
      method: e,
      url: r,
      data: (s || {}).data
    }));
  };
});
d.forEach(["post", "put", "patch"], function(e) {
  function r(s) {
    return function(a, o, i) {
      return this.request(_(i || {}, {
        method: e,
        headers: s ? {
          "Content-Type": "multipart/form-data"
        } : {},
        url: a,
        data: o
      }));
    };
  }
  ae.prototype[e] = r(), ae.prototype[e + "Form"] = r(!0);
});
const ee = ae;
class be {
  constructor(e) {
    if (typeof e != "function")
      throw new TypeError("executor must be a function.");
    let r;
    this.promise = new Promise(function(a) {
      r = a;
    });
    const s = this;
    this.promise.then((n) => {
      if (!s._listeners)
        return;
      let a = s._listeners.length;
      for (; a-- > 0; )
        s._listeners[a](n);
      s._listeners = null;
    }), this.promise.then = (n) => {
      let a;
      const o = new Promise((i) => {
        s.subscribe(i), a = i;
      }).then(n);
      return o.cancel = function() {
        s.unsubscribe(a);
      }, o;
    }, e(function(a, o, i) {
      s.reason || (s.reason = new z(a, o, i), r(s.reason));
    });
  }
  throwIfRequested() {
    if (this.reason)
      throw this.reason;
  }
  subscribe(e) {
    if (this.reason) {
      e(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(e) : this._listeners = [e];
  }
  unsubscribe(e) {
    if (!this._listeners)
      return;
    const r = this._listeners.indexOf(e);
    r !== -1 && this._listeners.splice(r, 1);
  }
  static source() {
    let e;
    return {
      token: new be(function(n) {
        e = n;
      }),
      cancel: e
    };
  }
}
const Or = be;
function yr(t) {
  return function(r) {
    return t.apply(null, r);
  };
}
function Pr(t) {
  return d.isObject(t) && t.isAxiosError === !0;
}
const me = {
  Continue: 100,
  SwitchingProtocols: 101,
  Processing: 102,
  EarlyHints: 103,
  Ok: 200,
  Created: 201,
  Accepted: 202,
  NonAuthoritativeInformation: 203,
  NoContent: 204,
  ResetContent: 205,
  PartialContent: 206,
  MultiStatus: 207,
  AlreadyReported: 208,
  ImUsed: 226,
  MultipleChoices: 300,
  MovedPermanently: 301,
  Found: 302,
  SeeOther: 303,
  NotModified: 304,
  UseProxy: 305,
  Unused: 306,
  TemporaryRedirect: 307,
  PermanentRedirect: 308,
  BadRequest: 400,
  Unauthorized: 401,
  PaymentRequired: 402,
  Forbidden: 403,
  NotFound: 404,
  MethodNotAllowed: 405,
  NotAcceptable: 406,
  ProxyAuthenticationRequired: 407,
  RequestTimeout: 408,
  Conflict: 409,
  Gone: 410,
  LengthRequired: 411,
  PreconditionFailed: 412,
  PayloadTooLarge: 413,
  UriTooLong: 414,
  UnsupportedMediaType: 415,
  RangeNotSatisfiable: 416,
  ExpectationFailed: 417,
  ImATeapot: 418,
  MisdirectedRequest: 421,
  UnprocessableEntity: 422,
  Locked: 423,
  FailedDependency: 424,
  TooEarly: 425,
  UpgradeRequired: 426,
  PreconditionRequired: 428,
  TooManyRequests: 429,
  RequestHeaderFieldsTooLarge: 431,
  UnavailableForLegalReasons: 451,
  InternalServerError: 500,
  NotImplemented: 501,
  BadGateway: 502,
  ServiceUnavailable: 503,
  GatewayTimeout: 504,
  HttpVersionNotSupported: 505,
  VariantAlsoNegotiates: 506,
  InsufficientStorage: 507,
  LoopDetected: 508,
  NotExtended: 510,
  NetworkAuthenticationRequired: 511
};
Object.entries(me).forEach(([t, e]) => {
  me[e] = t;
});
const wr = me;
function tt(t) {
  const e = new ee(t), r = Be(ee.prototype.request, e);
  return d.extend(r, ee.prototype, e, { allOwnKeys: !0 }), d.extend(r, e, null, { allOwnKeys: !0 }), r.create = function(n) {
    return tt(_(t, n));
  }, r;
}
const b = tt(Re);
b.Axios = ee;
b.CanceledError = z;
b.CancelToken = Or;
b.isCancel = Ye;
b.VERSION = et;
b.toFormData = ie;
b.AxiosError = A;
b.Cancel = b.CanceledError;
b.all = function(e) {
  return Promise.all(e);
};
b.spread = yr;
b.isAxiosError = Pr;
b.mergeConfig = _;
b.AxiosHeaders = k;
b.formToJSON = (t) => Xe(d.isHTMLForm(t) ? new FormData(t) : t);
b.HttpStatusCode = wr;
b.default = b;
const m = b, g = "";
class T {
  constructor(e, r = g, s = m) {
    this.basePath = r, this.axios = s, e && (this.configuration = e, this.basePath = e.basePath || this.basePath);
  }
}
class Ur extends Error {
  constructor(e, r) {
    super(r), this.field = e, this.name = "RequiredError";
  }
}
const F = "https://example.com", p = function(t, e, r) {
  if (r == null)
    throw new Ur(e, `Required parameter ${e} was null or undefined when calling ${t}.`);
}, O = async function(t, e, r, s) {
  if (s && s.accessToken) {
    const n = typeof s.accessToken == "function" ? await s.accessToken(e, r) : await s.accessToken;
    t.Authorization = "Bearer " + n;
  }
};
function ge(t, e, r = "") {
  typeof e == "object" ? Array.isArray(e) ? e.forEach((s) => ge(t, s, r)) : Object.keys(e).forEach(
    (s) => ge(t, e[s], `${r}${r !== "" ? "." : ""}${s}`)
  ) : t.has(r) ? t.append(r, e) : t.set(r, e);
}
const y = function(t, ...e) {
  const r = new URLSearchParams(t.search);
  ge(r, e), t.search = r.toString();
}, S = function(t, e, r) {
  const s = typeof t != "string";
  return (s && r && r.isJsonMime ? r.isJsonMime(e.headers["Content-Type"]) : s) ? JSON.stringify(t !== void 0 ? t : {}) : t || "";
}, P = function(t) {
  return t.pathname + t.search + t.hash;
}, w = function(t, e, r, s) {
  return (n = e, a = r) => {
    const o = { ...t.options, url: (s?.basePath || a) + t.url };
    return n.request(o);
  };
}, Ar = function(t) {
  return {
    getMetadata: async (e, r = {}) => {
      p("getMetadata", "getAlwMetadataTO", e);
      const s = "/rest/alwdms/metadata", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, rt = function(t) {
  const e = Ar(t);
  return {
    async getMetadata(r, s) {
      const n = await e.getMetadata(r, s);
      return w(n, m, g, t);
    }
  };
}, vr = function(t, e, r) {
  const s = rt(t);
  return {
    getMetadata(n, a) {
      return s.getMetadata(n, a).then((o) => o(r, e));
    }
  };
};
class Mr extends T {
  getMetadata(e, r) {
    return rt(this.configuration).getMetadata(e.getAlwMetadataTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const Rr = function(t) {
  return {
    deployArtifacts: async (e, r = {}) => {
      p("deployArtifacts", "deploymentDto", e);
      const s = "/rest/deployment", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, st = function(t) {
  const e = Rr(t);
  return {
    async deployArtifacts(r, s) {
      const n = await e.deployArtifacts(r, s);
      return w(n, m, g, t);
    }
  };
}, qr = function(t, e, r) {
  const s = st(t);
  return {
    deployArtifacts(n, a) {
      return s.deployArtifacts(n, a).then((o) => o(r, e));
    }
  };
};
class $r extends T {
  deployArtifacts(e, r) {
    return st(this.configuration).deployArtifacts(e.deploymentDto, r).then((s) => s(this.axios, this.basePath));
  }
}
const Vr = function(t) {
  return {
    getMetaData: async (e, r = {}) => {
      p("getMetaData", "getMetadataTO", e);
      const s = "/rest/dms/metadata", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, nt = function(t) {
  const e = Vr(t);
  return {
    async getMetaData(r, s) {
      const n = await e.getMetaData(r, s);
      return w(n, m, g, t);
    }
  };
}, Jr = function(t, e, r) {
  const s = nt(t);
  return {
    getMetaData(n, a) {
      return s.getMetaData(n, a).then((o) => o(r, e));
    }
  };
};
class Qr extends T {
  getMetaData(e, r) {
    return nt(this.configuration).getMetaData(e.getMetadataTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const br = function(t) {
  return {
    getStatusDokumentForTask: async (e, r = {}) => {
      p("getStatusDokumentForTask", "id", e);
      const s = "/rest/document/task/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    }
  };
}, at = function(t) {
  const e = br(t);
  return {
    async getStatusDokumentForTask(r, s) {
      const n = await e.getStatusDokumentForTask(r, s);
      return w(n, m, g, t);
    }
  };
}, Gr = function(t, e, r) {
  const s = at(t);
  return {
    getStatusDokumentForTask(n, a) {
      return s.getStatusDokumentForTask(n, a).then((o) => o(r, e));
    }
  };
};
class zr extends T {
  getStatusDokumentForTask(e, r) {
    return at(this.configuration).getStatusDokumentForTask(e.id, r).then((s) => s(this.axios, this.basePath));
  }
}
const Tr = function(t) {
  return {
    _delete: async (e, r = {}) => {
      p("_delete", "id", e);
      const s = "/rest/filter/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "DELETE", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getFilters: async (e = {}) => {
      const r = "/rest/filter", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    },
    saveFilter: async (e, r = {}) => {
      p("saveFilter", "saveFilterTO", e);
      const s = "/rest/filter", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "PUT", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, te = function(t) {
  const e = Tr(t);
  return {
    async _delete(r, s) {
      const n = await e._delete(r, s);
      return w(n, m, g, t);
    },
    async getFilters(r) {
      const s = await e.getFilters(r);
      return w(s, m, g, t);
    },
    async saveFilter(r, s) {
      const n = await e.saveFilter(r, s);
      return w(n, m, g, t);
    }
  };
}, Kr = function(t, e, r) {
  const s = te(t);
  return {
    _delete(n, a) {
      return s._delete(n, a).then((o) => o(r, e));
    },
    getFilters(n) {
      return s.getFilters(n).then((a) => a(r, e));
    },
    saveFilter(n, a) {
      return s.saveFilter(n, a).then((o) => o(r, e));
    }
  };
};
class Wr extends T {
  _delete(e, r) {
    return te(this.configuration)._delete(e.id, r).then((s) => s(this.axios, this.basePath));
  }
  getFilters(e) {
    return te(this.configuration).getFilters(e).then((r) => r(this.axios, this.basePath));
  }
  saveFilter(e, r) {
    return te(this.configuration).saveFilter(e.saveFilterTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const Sr = function(t) {
  return {
    createForm: async (e, r = {}) => {
      p("createForm", "formTO", e);
      const s = "/rest/form", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    getForm: async (e, r = {}) => {
      p("getForm", "key", e);
      const s = "/rest/form/{key}".replace("{key}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getForms: async (e = {}) => {
      const r = "/rest/form", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    },
    updateForm: async (e, r, s = {}) => {
      p("updateForm", "key", e), p("updateForm", "formTO", r);
      const n = "/rest/form/{key}".replace("{key}", encodeURIComponent(String(e))), a = new URL(n, F);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "PUT", ...o, ...s }, l = {}, c = {};
      await O(l, "spring_oauth", [], t), l["Content-Type"] = "application/json", y(a, c);
      let u = o && o.headers ? o.headers : {};
      return i.headers = { ...l, ...u, ...s.headers }, i.data = S(r, i, t), {
        url: P(a),
        options: i
      };
    }
  };
}, v = function(t) {
  const e = Sr(t);
  return {
    async createForm(r, s) {
      const n = await e.createForm(r, s);
      return w(n, m, g, t);
    },
    async getForm(r, s) {
      const n = await e.getForm(r, s);
      return w(n, m, g, t);
    },
    async getForms(r) {
      const s = await e.getForms(r);
      return w(s, m, g, t);
    },
    async updateForm(r, s, n) {
      const a = await e.updateForm(r, s, n);
      return w(a, m, g, t);
    }
  };
}, Xr = function(t, e, r) {
  const s = v(t);
  return {
    createForm(n, a) {
      return s.createForm(n, a).then((o) => o(r, e));
    },
    getForm(n, a) {
      return s.getForm(n, a).then((o) => o(r, e));
    },
    getForms(n) {
      return s.getForms(n).then((a) => a(r, e));
    },
    updateForm(n, a, o) {
      return s.updateForm(n, a, o).then((i) => i(r, e));
    }
  };
};
class Yr extends T {
  createForm(e, r) {
    return v(this.configuration).createForm(e.formTO, r).then((s) => s(this.axios, this.basePath));
  }
  getForm(e, r) {
    return v(this.configuration).getForm(e.key, r).then((s) => s(this.axios, this.basePath));
  }
  getForms(e) {
    return v(this.configuration).getForms(e).then((r) => r(this.axios, this.basePath));
  }
  updateForm(e, r) {
    return v(this.configuration).updateForm(e.key, e.formTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const Cr = function(t) {
  return {
    getFileNames: async (e, r, s = {}) => {
      p("getFileNames", "taskId", e), p("getFileNames", "filePath", r);
      const n = "/rest/task/file/{taskId}".replace("{taskId}", encodeURIComponent(String(e))), a = new URL(n, F);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "GET", ...o, ...s }, l = {}, c = {};
      await O(l, "spring_oauth", [], t), r !== void 0 && (c.filePath = r), y(a, c);
      let u = o && o.headers ? o.headers : {};
      return i.headers = { ...l, ...u, ...s.headers }, {
        url: P(a),
        options: i
      };
    },
    getPresignedUrlForFileDeletion: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDeletion", "taskId", e), p("getPresignedUrlForFileDeletion", "filename", r), p("getPresignedUrlForFileDeletion", "filePath", s);
      const a = "/rest/task/file/{taskId}/{filename}".replace("{taskId}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "DELETE", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileDownload: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDownload", "taskId", e), p("getPresignedUrlForFileDownload", "fileName", r), p("getPresignedUrlForFileDownload", "filePath", s);
      const a = "/rest/task/file/{taskId}/{fileName}".replace("{taskId}", encodeURIComponent(String(e))).replace("{fileName}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "GET", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileUpload: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileUpload", "taskId", e), p("getPresignedUrlForFileUpload", "filename", r), p("getPresignedUrlForFileUpload", "filePath", s);
      const a = "/rest/task/file/{taskId}/{filename}".replace("{taskId}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "POST", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    }
  };
}, M = function(t) {
  const e = Cr(t);
  return {
    async getFileNames(r, s, n) {
      const a = await e.getFileNames(r, s, n);
      return w(a, m, g, t);
    },
    async getPresignedUrlForFileDeletion(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDeletion(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileDownload(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDownload(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileUpload(r, s, n, a) {
      const o = await e.getPresignedUrlForFileUpload(r, s, n, a);
      return w(o, m, g, t);
    }
  };
}, Zr = function(t, e, r) {
  const s = M(t);
  return {
    getFileNames(n, a, o) {
      return s.getFileNames(n, a, o).then((i) => i(r, e));
    },
    getPresignedUrlForFileDeletion(n, a, o, i) {
      return s.getPresignedUrlForFileDeletion(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileDownload(n, a, o, i) {
      return s.getPresignedUrlForFileDownload(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileUpload(n, a, o, i) {
      return s.getPresignedUrlForFileUpload(n, a, o, i).then((l) => l(r, e));
    }
  };
};
class es extends T {
  getFileNames(e, r) {
    return M(this.configuration).getFileNames(e.taskId, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDeletion(e, r) {
    return M(this.configuration).getPresignedUrlForFileDeletion(e.taskId, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDownload(e, r) {
    return M(this.configuration).getPresignedUrlForFileDownload(e.taskId, e.fileName, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileUpload(e, r) {
    return M(this.configuration).getPresignedUrlForFileUpload(e.taskId, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
}
const Er = function(t) {
  return {
    assignTask: async (e, r = {}) => {
      p("assignTask", "id", e);
      const s = "/rest/task/assign/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    cancelTask: async (e, r = {}) => {
      p("cancelTask", "id", e);
      const s = "/rest/task/cancel/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    completeTask: async (e, r = {}) => {
      p("completeTask", "completeTO", e);
      const s = "/rest/task", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    followUpTask: async (e, r = {}) => {
      p("followUpTask", "followUpTO", e);
      const s = "/rest/task/followup", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    getAssignedGroupTasks: async (e, r, s, n = {}) => {
      const a = "/rest/task/group/assigned", o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "GET", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), e !== void 0 && (u.page = e), r !== void 0 && (u.size = r), s !== void 0 && (u.query = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getOpenGroupTasks: async (e, r, s, n = {}) => {
      const a = "/rest/task/group/open", o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "GET", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), e !== void 0 && (u.page = e), r !== void 0 && (u.size = r), s !== void 0 && (u.query = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getTaskDetail: async (e, r = {}) => {
      p("getTaskDetail", "id", e);
      const s = "/rest/task/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getTasks: async (e, r, s, n, a = {}) => {
      const o = "/rest/task", i = new URL(o, F);
      let l;
      t && (l = t.baseOptions);
      const c = { method: "GET", ...l, ...a }, u = {}, h = {};
      await O(u, "spring_oauth", [], t), e !== void 0 && (h.page = e), r !== void 0 && (h.size = r), s !== void 0 && (h.query = s), n !== void 0 && (h.followUp = n), y(i, h);
      let V = l && l.headers ? l.headers : {};
      return c.headers = { ...u, ...V, ...a.headers }, {
        url: P(i),
        options: c
      };
    },
    saveTask: async (e, r = {}) => {
      p("saveTask", "saveTO", e);
      const s = "/rest/task", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "PUT", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, E = function(t) {
  const e = Er(t);
  return {
    async assignTask(r, s) {
      const n = await e.assignTask(r, s);
      return w(n, m, g, t);
    },
    async cancelTask(r, s) {
      const n = await e.cancelTask(r, s);
      return w(n, m, g, t);
    },
    async completeTask(r, s) {
      const n = await e.completeTask(r, s);
      return w(n, m, g, t);
    },
    async followUpTask(r, s) {
      const n = await e.followUpTask(r, s);
      return w(n, m, g, t);
    },
    async getAssignedGroupTasks(r, s, n, a) {
      const o = await e.getAssignedGroupTasks(r, s, n, a);
      return w(o, m, g, t);
    },
    async getOpenGroupTasks(r, s, n, a) {
      const o = await e.getOpenGroupTasks(r, s, n, a);
      return w(o, m, g, t);
    },
    async getTaskDetail(r, s) {
      const n = await e.getTaskDetail(r, s);
      return w(n, m, g, t);
    },
    async getTasks(r, s, n, a, o) {
      const i = await e.getTasks(r, s, n, a, o);
      return w(i, m, g, t);
    },
    async saveTask(r, s) {
      const n = await e.saveTask(r, s);
      return w(n, m, g, t);
    }
  };
}, ts = function(t, e, r) {
  const s = E(t);
  return {
    assignTask(n, a) {
      return s.assignTask(n, a).then((o) => o(r, e));
    },
    cancelTask(n, a) {
      return s.cancelTask(n, a).then((o) => o(r, e));
    },
    completeTask(n, a) {
      return s.completeTask(n, a).then((o) => o(r, e));
    },
    followUpTask(n, a) {
      return s.followUpTask(n, a).then((o) => o(r, e));
    },
    getAssignedGroupTasks(n, a, o, i) {
      return s.getAssignedGroupTasks(n, a, o, i).then((l) => l(r, e));
    },
    getOpenGroupTasks(n, a, o, i) {
      return s.getOpenGroupTasks(n, a, o, i).then((l) => l(r, e));
    },
    getTaskDetail(n, a) {
      return s.getTaskDetail(n, a).then((o) => o(r, e));
    },
    getTasks(n, a, o, i, l) {
      return s.getTasks(n, a, o, i, l).then((c) => c(r, e));
    },
    saveTask(n, a) {
      return s.saveTask(n, a).then((o) => o(r, e));
    }
  };
};
class rs extends T {
  assignTask(e, r) {
    return E(this.configuration).assignTask(e.id, r).then((s) => s(this.axios, this.basePath));
  }
  cancelTask(e, r) {
    return E(this.configuration).cancelTask(e.id, r).then((s) => s(this.axios, this.basePath));
  }
  completeTask(e, r) {
    return E(this.configuration).completeTask(e.completeTO, r).then((s) => s(this.axios, this.basePath));
  }
  followUpTask(e, r) {
    return E(this.configuration).followUpTask(e.followUpTO, r).then((s) => s(this.axios, this.basePath));
  }
  getAssignedGroupTasks(e = {}, r) {
    return E(this.configuration).getAssignedGroupTasks(e.page, e.size, e.query, r).then((s) => s(this.axios, this.basePath));
  }
  getOpenGroupTasks(e = {}, r) {
    return E(this.configuration).getOpenGroupTasks(e.page, e.size, e.query, r).then((s) => s(this.axios, this.basePath));
  }
  getTaskDetail(e, r) {
    return E(this.configuration).getTaskDetail(e.id, r).then((s) => s(this.axios, this.basePath));
  }
  getTasks(e = {}, r) {
    return E(this.configuration).getTasks(e.page, e.size, e.query, e.followUp, r).then((s) => s(this.axios, this.basePath));
  }
  saveTask(e, r) {
    return E(this.configuration).saveTask(e.saveTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const xr = function(t) {
  return {
    getInfo: async (e = {}) => {
      const r = "/rest/info", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    }
  };
}, ot = function(t) {
  const e = xr(t);
  return {
    async getInfo(r) {
      const s = await e.getInfo(r);
      return w(s, m, g, t);
    }
  };
}, ss = function(t, e, r) {
  const s = ot(t);
  return {
    getInfo(n) {
      return s.getInfo(n).then((a) => a(r, e));
    }
  };
};
class ns extends T {
  getInfo(e) {
    return ot(this.configuration).getInfo(e).then((r) => r(this.axios, this.basePath));
  }
}
const Dr = function(t) {
  return {
    sendMessage: async (e, r = {}) => {
      p("sendMessage", "sendMessageTO", e);
      const s = "/rest/input/message/send/message", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, it = function(t) {
  const e = Dr(t);
  return {
    async sendMessage(r, s) {
      const n = await e.sendMessage(r, s);
      return w(n, m, g, t);
    }
  };
}, as = function(t, e, r) {
  const s = it(t);
  return {
    sendMessage(n, a) {
      return s.sendMessage(n, a).then((o) => o(r, e));
    }
  };
};
class os extends T {
  sendMessage(e, r) {
    return it(this.configuration).sendMessage(e.sendMessageTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const kr = function(t) {
  return {
    createConfig: async (e, r = {}) => {
      p("createConfig", "processConfigTO", e);
      const s = "/rest/processconfig", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    getConfig: async (e, r = {}) => {
      p("getConfig", "key", e);
      const s = "/rest/processconfig/{key}".replace("{key}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    }
  };
}, Fe = function(t) {
  const e = kr(t);
  return {
    async createConfig(r, s) {
      const n = await e.createConfig(r, s);
      return w(n, m, g, t);
    },
    async getConfig(r, s) {
      const n = await e.getConfig(r, s);
      return w(n, m, g, t);
    }
  };
}, is = function(t, e, r) {
  const s = Fe(t);
  return {
    createConfig(n, a) {
      return s.createConfig(n, a).then((o) => o(r, e));
    },
    getConfig(n, a) {
      return s.getConfig(n, a).then((o) => o(r, e));
    }
  };
};
class ls extends T {
  createConfig(e, r) {
    return Fe(this.configuration).createConfig(e.processConfigTO, r).then((s) => s(this.axios, this.basePath));
  }
  getConfig(e, r) {
    return Fe(this.configuration).getConfig(e.key, r).then((s) => s(this.axios, this.basePath));
  }
}
const Nr = function(t) {
  return {
    createJsonSchema: async (e, r = {}) => {
      p("createJsonSchema", "jsonSchemaTO", e);
      const s = "/rest/jsonschema", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    getJsonSchema: async (e, r = {}) => {
      p("getJsonSchema", "key", e);
      const s = "/rest/jsonschema/{key}".replace("{key}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    }
  };
}, Oe = function(t) {
  const e = Nr(t);
  return {
    async createJsonSchema(r, s) {
      const n = await e.createJsonSchema(r, s);
      return w(n, m, g, t);
    },
    async getJsonSchema(r, s) {
      const n = await e.getJsonSchema(r, s);
      return w(n, m, g, t);
    }
  };
}, cs = function(t, e, r) {
  const s = Oe(t);
  return {
    createJsonSchema(n, a) {
      return s.createJsonSchema(n, a).then((o) => o(r, e));
    },
    getJsonSchema(n, a) {
      return s.getJsonSchema(n, a).then((o) => o(r, e));
    }
  };
};
class ds extends T {
  createJsonSchema(e, r) {
    return Oe(this.configuration).createJsonSchema(e.jsonSchemaTO, r).then((s) => s(this.axios, this.basePath));
  }
  getJsonSchema(e, r) {
    return Oe(this.configuration).getJsonSchema(e.key, r).then((s) => s(this.axios, this.basePath));
  }
}
const Ir = function(t) {
  return {
    getServiceDefinition: async (e, r = {}) => {
      p("getServiceDefinition", "key", e);
      const s = "/rest/service/definition/{key}".replace("{key}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getServiceDefinitions: async (e = {}) => {
      const r = "/rest/service/definition", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    },
    startInstance: async (e, r = {}) => {
      p("startInstance", "startInstanceTO", e);
      const s = "/rest/service/definition", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    }
  };
}, re = function(t) {
  const e = Ir(t);
  return {
    async getServiceDefinition(r, s) {
      const n = await e.getServiceDefinition(r, s);
      return w(n, m, g, t);
    },
    async getServiceDefinitions(r) {
      const s = await e.getServiceDefinitions(r);
      return w(s, m, g, t);
    },
    async startInstance(r, s) {
      const n = await e.startInstance(r, s);
      return w(n, m, g, t);
    }
  };
}, us = function(t, e, r) {
  const s = re(t);
  return {
    getServiceDefinition(n, a) {
      return s.getServiceDefinition(n, a).then((o) => o(r, e));
    },
    getServiceDefinitions(n) {
      return s.getServiceDefinitions(n).then((a) => a(r, e));
    },
    startInstance(n, a) {
      return s.startInstance(n, a).then((o) => o(r, e));
    }
  };
};
class hs extends T {
  getServiceDefinition(e, r) {
    return re(this.configuration).getServiceDefinition(e.key, r).then((s) => s(this.axios, this.basePath));
  }
  getServiceDefinitions(e) {
    return re(this.configuration).getServiceDefinitions(e).then((r) => r(this.axios, this.basePath));
  }
  startInstance(e, r) {
    return re(this.configuration).startInstance(e.startInstanceTO, r).then((s) => s(this.axios, this.basePath));
  }
}
const jr = function(t) {
  return {
    getAssignedInstances: async (e = {}) => {
      const r = "/rest/service/instance", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    },
    getProcessInstanceDetail: async (e, r = {}) => {
      p("getProcessInstanceDetail", "id", e);
      const s = "/rest/service/instance/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    }
  };
}, ye = function(t) {
  const e = jr(t);
  return {
    async getAssignedInstances(r) {
      const s = await e.getAssignedInstances(r);
      return w(s, m, g, t);
    },
    async getProcessInstanceDetail(r, s) {
      const n = await e.getProcessInstanceDetail(r, s);
      return w(n, m, g, t);
    }
  };
}, ps = function(t, e, r) {
  const s = ye(t);
  return {
    getAssignedInstances(n) {
      return s.getAssignedInstances(n).then((a) => a(r, e));
    },
    getProcessInstanceDetail(n, a) {
      return s.getProcessInstanceDetail(n, a).then((o) => o(r, e));
    }
  };
};
class fs extends T {
  getAssignedInstances(e) {
    return ye(this.configuration).getAssignedInstances(e).then((r) => r(this.axios, this.basePath));
  }
  getProcessInstanceDetail(e, r) {
    return ye(this.configuration).getProcessInstanceDetail(e.id, r).then((s) => s(this.axios, this.basePath));
  }
}
const Br = function(t) {
  return {
    getFileNames2: async (e, r, s = {}) => {
      p("getFileNames2", "instanceId", e), p("getFileNames2", "filePath", r);
      const n = "/rest/service/instance/file/{instanceId}".replace("{instanceId}", encodeURIComponent(String(e))), a = new URL(n, F);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "GET", ...o, ...s }, l = {}, c = {};
      await O(l, "spring_oauth", [], t), r !== void 0 && (c.filePath = r), y(a, c);
      let u = o && o.headers ? o.headers : {};
      return i.headers = { ...l, ...u, ...s.headers }, {
        url: P(a),
        options: i
      };
    },
    getPresignedUrlForFileDeletion2: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDeletion2", "instanceId", e), p("getPresignedUrlForFileDeletion2", "filename", r), p("getPresignedUrlForFileDeletion2", "filePath", s);
      const a = "/rest/service/instance/file/{instanceId}/{filename}".replace("{instanceId}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "DELETE", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileDownload2: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDownload2", "instanceId", e), p("getPresignedUrlForFileDownload2", "fileName", r), p("getPresignedUrlForFileDownload2", "filePath", s);
      const a = "/rest/service/instance/file/{instanceId}/{fileName}".replace("{instanceId}", encodeURIComponent(String(e))).replace("{fileName}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "GET", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileUpload2: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileUpload2", "instanceId", e), p("getPresignedUrlForFileUpload2", "filename", r), p("getPresignedUrlForFileUpload2", "filePath", s);
      const a = "/rest/service/instance/file/{instanceId}/{filename}".replace("{instanceId}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "POST", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    }
  };
}, q = function(t) {
  const e = Br(t);
  return {
    async getFileNames2(r, s, n) {
      const a = await e.getFileNames2(r, s, n);
      return w(a, m, g, t);
    },
    async getPresignedUrlForFileDeletion2(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDeletion2(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileDownload2(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDownload2(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileUpload2(r, s, n, a) {
      const o = await e.getPresignedUrlForFileUpload2(r, s, n, a);
      return w(o, m, g, t);
    }
  };
}, ms = function(t, e, r) {
  const s = q(t);
  return {
    getFileNames2(n, a, o) {
      return s.getFileNames2(n, a, o).then((i) => i(r, e));
    },
    getPresignedUrlForFileDeletion2(n, a, o, i) {
      return s.getPresignedUrlForFileDeletion2(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileDownload2(n, a, o, i) {
      return s.getPresignedUrlForFileDownload2(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileUpload2(n, a, o, i) {
      return s.getPresignedUrlForFileUpload2(n, a, o, i).then((l) => l(r, e));
    }
  };
};
class gs extends T {
  getFileNames2(e, r) {
    return q(this.configuration).getFileNames2(e.instanceId, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDeletion2(e, r) {
    return q(this.configuration).getPresignedUrlForFileDeletion2(e.instanceId, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDownload2(e, r) {
    return q(this.configuration).getPresignedUrlForFileDownload2(e.instanceId, e.fileName, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileUpload2(e, r) {
    return q(this.configuration).getPresignedUrlForFileUpload2(e.instanceId, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
}
const _r = function(t) {
  return {
    getFileNames1: async (e, r, s = {}) => {
      p("getFileNames1", "definitionKey", e), p("getFileNames1", "filePath", r);
      const n = "/rest/service/start/file/{definitionKey}".replace("{definitionKey}", encodeURIComponent(String(e))), a = new URL(n, F);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "GET", ...o, ...s }, l = {}, c = {};
      await O(l, "spring_oauth", [], t), r !== void 0 && (c.filePath = r), y(a, c);
      let u = o && o.headers ? o.headers : {};
      return i.headers = { ...l, ...u, ...s.headers }, {
        url: P(a),
        options: i
      };
    },
    getPresignedUrlForFileDeletion1: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDeletion1", "definitionKey", e), p("getPresignedUrlForFileDeletion1", "filename", r), p("getPresignedUrlForFileDeletion1", "filePath", s);
      const a = "/rest/service/start/file/{definitionKey}/{filename}".replace("{definitionKey}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "DELETE", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileDownload1: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileDownload1", "definitionKey", e), p("getPresignedUrlForFileDownload1", "fileName", r), p("getPresignedUrlForFileDownload1", "filePath", s);
      const a = "/rest/service/start/file/{definitionKey}/{fileName}".replace("{definitionKey}", encodeURIComponent(String(e))).replace("{fileName}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "GET", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    },
    getPresignedUrlForFileUpload1: async (e, r, s, n = {}) => {
      p("getPresignedUrlForFileUpload1", "definitionKey", e), p("getPresignedUrlForFileUpload1", "filename", r), p("getPresignedUrlForFileUpload1", "filePath", s);
      const a = "/rest/service/start/file/{definitionKey}/{filename}".replace("{definitionKey}", encodeURIComponent(String(e))).replace("{filename}", encodeURIComponent(String(r))), o = new URL(a, F);
      let i;
      t && (i = t.baseOptions);
      const l = { method: "POST", ...i, ...n }, c = {}, u = {};
      await O(c, "spring_oauth", [], t), s !== void 0 && (u.filePath = s), y(o, u);
      let h = i && i.headers ? i.headers : {};
      return l.headers = { ...c, ...h, ...n.headers }, {
        url: P(o),
        options: l
      };
    }
  };
}, $ = function(t) {
  const e = _r(t);
  return {
    async getFileNames1(r, s, n) {
      const a = await e.getFileNames1(r, s, n);
      return w(a, m, g, t);
    },
    async getPresignedUrlForFileDeletion1(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDeletion1(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileDownload1(r, s, n, a) {
      const o = await e.getPresignedUrlForFileDownload1(r, s, n, a);
      return w(o, m, g, t);
    },
    async getPresignedUrlForFileUpload1(r, s, n, a) {
      const o = await e.getPresignedUrlForFileUpload1(r, s, n, a);
      return w(o, m, g, t);
    }
  };
}, Fs = function(t, e, r) {
  const s = $(t);
  return {
    getFileNames1(n, a, o) {
      return s.getFileNames1(n, a, o).then((i) => i(r, e));
    },
    getPresignedUrlForFileDeletion1(n, a, o, i) {
      return s.getPresignedUrlForFileDeletion1(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileDownload1(n, a, o, i) {
      return s.getPresignedUrlForFileDownload1(n, a, o, i).then((l) => l(r, e));
    },
    getPresignedUrlForFileUpload1(n, a, o, i) {
      return s.getPresignedUrlForFileUpload1(n, a, o, i).then((l) => l(r, e));
    }
  };
};
class Os extends T {
  getFileNames1(e, r) {
    return $(this.configuration).getFileNames1(e.definitionKey, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDeletion1(e, r) {
    return $(this.configuration).getPresignedUrlForFileDeletion1(e.definitionKey, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileDownload1(e, r) {
    return $(this.configuration).getPresignedUrlForFileDownload1(e.definitionKey, e.fileName, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
  getPresignedUrlForFileUpload1(e, r) {
    return $(this.configuration).getPresignedUrlForFileUpload1(e.definitionKey, e.filename, e.filePath, r).then((s) => s(this.axios, this.basePath));
  }
}
const Lr = function(t) {
  return {
    getUser: async (e, r = {}) => {
      p("getUser", "id", e);
      const s = "/rest/user/{id}".replace("{id}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getUserByUsername: async (e, r = {}) => {
      p("getUserByUsername", "username", e);
      const s = "/rest/user/uid/{username}".replace("{username}", encodeURIComponent(String(e))), n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "GET", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, {
        url: P(n),
        options: o
      };
    },
    getUsers: async (e, r = {}) => {
      p("getUsers", "searchUserTO", e);
      const s = "/rest/user/search", n = new URL(s, F);
      let a;
      t && (a = t.baseOptions);
      const o = { method: "POST", ...a, ...r }, i = {}, l = {};
      await O(i, "spring_oauth", [], t), i["Content-Type"] = "application/json", y(n, l);
      let c = a && a.headers ? a.headers : {};
      return o.headers = { ...i, ...c, ...r.headers }, o.data = S(e, o, t), {
        url: P(n),
        options: o
      };
    },
    userinfo: async (e = {}) => {
      const r = "/rest/user/info", s = new URL(r, F);
      let n;
      t && (n = t.baseOptions);
      const a = { method: "GET", ...n, ...e }, o = {}, i = {};
      await O(o, "spring_oauth", [], t), y(s, i);
      let l = n && n.headers ? n.headers : {};
      return a.headers = { ...o, ...l, ...e.headers }, {
        url: P(s),
        options: a
      };
    }
  };
}, J = function(t) {
  const e = Lr(t);
  return {
    async getUser(r, s) {
      const n = await e.getUser(r, s);
      return w(n, m, g, t);
    },
    async getUserByUsername(r, s) {
      const n = await e.getUserByUsername(r, s);
      return w(n, m, g, t);
    },
    async getUsers(r, s) {
      const n = await e.getUsers(r, s);
      return w(n, m, g, t);
    },
    async userinfo(r) {
      const s = await e.userinfo(r);
      return w(s, m, g, t);
    }
  };
}, ys = function(t, e, r) {
  const s = J(t);
  return {
    getUser(n, a) {
      return s.getUser(n, a).then((o) => o(r, e));
    },
    getUserByUsername(n, a) {
      return s.getUserByUsername(n, a).then((o) => o(r, e));
    },
    getUsers(n, a) {
      return s.getUsers(n, a).then((o) => o(r, e));
    },
    userinfo(n) {
      return s.userinfo(n).then((a) => a(r, e));
    }
  };
};
class Ps extends T {
  getUser(e, r) {
    return J(this.configuration).getUser(e.id, r).then((s) => s(this.axios, this.basePath));
  }
  getUserByUsername(e, r) {
    return J(this.configuration).getUserByUsername(e.username, r).then((s) => s(this.axios, this.basePath));
  }
  getUsers(e, r) {
    return J(this.configuration).getUsers(e.searchUserTO, r).then((s) => s(this.axios, this.basePath));
  }
  userinfo(e) {
    return J(this.configuration).userinfo(e).then((r) => r(this.axios, this.basePath));
  }
}
class Hr {
  constructor(e = {}) {
    this.apiKey = e.apiKey, this.username = e.username, this.password = e.password, this.accessToken = e.accessToken, this.basePath = e.basePath, this.baseOptions = e.baseOptions, this.formDataCtor = e.formDataCtor;
  }
  isJsonMime(e) {
    const r = new RegExp("^(application/json|[^;/ 	]+/[^;/ 	]+[+]json)[ 	]*(;.*)?$", "i");
    return e !== null && (r.test(e) || e.toLowerCase() === "application/json-patch+json");
  }
}
var se = /* @__PURE__ */ ((t) => (t.INFO = "info", t.WARNING = "warning", t.ERROR = "error", t))(se || {});
class W extends Error {
  constructor({ level: e = "error", message: r = "Ein unbekannter Fehler ist aufgetreten, bitte den Administrator informieren." }) {
    super(r), this.stack = new Error().stack, this.level = e, this.message = r;
  }
}
class ne {
  static getGETConfig() {
    return {
      headers: this.getHeaders(),
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getDELETEConfig() {
    return {
      method: "DELETE",
      headers: this.getHeaders(),
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getAxiosConfig(e) {
    const r = new Hr();
    return r.baseOptions = e, r;
  }
  static getPOSTConfig(e) {
    return {
      method: "POST",
      body: e ? JSON.stringify(e) : void 0,
      headers: ne.getHeaders(),
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getPUTConfig(e) {
    const r = ne.getHeaders();
    return e.version && r.append("If-Match", e.version), {
      method: "PUT",
      body: e ? JSON.stringify(e) : void 0,
      headers: r,
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static defaultResponseHandler(e, r = "Es ist ein unbekannter Fehler aufgetreten.", s) {
    if (!e.ok)
      throw s && s[e.status] && s[e.status](e), e.status === 403 ? new W({
        level: se.ERROR,
        message: "Sie haben nicht die n\xF6tigen Rechte um diese Aktion durchzuf\xFChren."
      }) : (e.type === "opaqueredirect" && location.reload(), new W({
        level: se.WARNING,
        message: r
      }));
  }
  static defaultCatchHandler(e, r = "Es ist ein unbekannter Fehler aufgetreten.") {
    throw e instanceof W ? e : new W({
      level: se.WARNING,
      message: r
    });
  }
  static getPATCHConfig(e) {
    const r = ne.getHeaders();
    return e.version !== void 0 && r.append("If-Match", e.version), {
      method: "PATCH",
      body: e ? JSON.stringify(e) : void 0,
      headers: r,
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getHeaders() {
    const e = new Headers({
      "Content-Type": "application/json"
    }), r = this._getXSRFToken();
    return r !== "" && e.append("X-XSRF-TOKEN", r), e;
  }
  static _getXSRFToken() {
    const e = document.cookie.match("(^|;)\\s*XSRF-TOKEN\\s*=\\s*([^;]+)");
    return e ? e.pop() : "";
  }
}
export {
  Mr as AlwDmsRestControllerApi,
  Ar as AlwDmsRestControllerApiAxiosParamCreator,
  vr as AlwDmsRestControllerApiFactory,
  rt as AlwDmsRestControllerApiFp,
  W as ApiError,
  Hr as Configuration,
  $r as DeploymentControllerApi,
  Rr as DeploymentControllerApiAxiosParamCreator,
  qr as DeploymentControllerApiFactory,
  st as DeploymentControllerApiFp,
  Qr as DmsRestControllerApi,
  Vr as DmsRestControllerApiAxiosParamCreator,
  Jr as DmsRestControllerApiFactory,
  nt as DmsRestControllerApiFp,
  zr as DocumentRestControllerApi,
  br as DocumentRestControllerApiAxiosParamCreator,
  Gr as DocumentRestControllerApiFactory,
  at as DocumentRestControllerApiFp,
  ne as FetchUtils,
  Wr as FilterRestControllerApi,
  Tr as FilterRestControllerApiAxiosParamCreator,
  Kr as FilterRestControllerApiFactory,
  te as FilterRestControllerApiFp,
  Yr as FormRestControllerApi,
  Sr as FormRestControllerApiAxiosParamCreator,
  Xr as FormRestControllerApiFactory,
  v as FormRestControllerApiFp,
  es as HumanTaskFileRestControllerApi,
  Cr as HumanTaskFileRestControllerApiAxiosParamCreator,
  Zr as HumanTaskFileRestControllerApiFactory,
  M as HumanTaskFileRestControllerApiFp,
  rs as HumanTaskRestControllerApi,
  Er as HumanTaskRestControllerApiAxiosParamCreator,
  ts as HumanTaskRestControllerApiFactory,
  E as HumanTaskRestControllerApiFp,
  ns as InfoRestControllerApi,
  xr as InfoRestControllerApiAxiosParamCreator,
  ss as InfoRestControllerApiFactory,
  ot as InfoRestControllerApiFp,
  se as Levels,
  os as MessageControllerApi,
  Dr as MessageControllerApiAxiosParamCreator,
  as as MessageControllerApiFactory,
  it as MessageControllerApiFp,
  ls as ProcessConfigurationControllerApi,
  kr as ProcessConfigurationControllerApiAxiosParamCreator,
  is as ProcessConfigurationControllerApiFactory,
  Fe as ProcessConfigurationControllerApiFp,
  ds as SchemaRestControllerApi,
  Nr as SchemaRestControllerApiAxiosParamCreator,
  cs as SchemaRestControllerApiFactory,
  Oe as SchemaRestControllerApiFp,
  hs as ServiceDefinitionControllerApi,
  Ir as ServiceDefinitionControllerApiAxiosParamCreator,
  us as ServiceDefinitionControllerApiFactory,
  re as ServiceDefinitionControllerApiFp,
  fs as ServiceInstanceControllerApi,
  jr as ServiceInstanceControllerApiAxiosParamCreator,
  ps as ServiceInstanceControllerApiFactory,
  ye as ServiceInstanceControllerApiFp,
  gs as ServiceInstanceFileRestControllerApi,
  Br as ServiceInstanceFileRestControllerApiAxiosParamCreator,
  ms as ServiceInstanceFileRestControllerApiFactory,
  q as ServiceInstanceFileRestControllerApiFp,
  Os as ServiceStartFileRestControllerApi,
  _r as ServiceStartFileRestControllerApiAxiosParamCreator,
  Fs as ServiceStartFileRestControllerApiFactory,
  $ as ServiceStartFileRestControllerApiFp,
  Ps as UserRestControllerApi,
  Lr as UserRestControllerApiAxiosParamCreator,
  ys as UserRestControllerApiFactory,
  J as UserRestControllerApiFp
};
