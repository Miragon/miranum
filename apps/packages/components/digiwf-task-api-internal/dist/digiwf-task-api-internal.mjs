function vr(t) {
  return t && t.__esModule && Object.prototype.hasOwnProperty.call(t, "default") ? t.default : t;
}
var Xe = { exports: {} }, me = { exports: {} }, Ke = function(e, r) {
  return function() {
    for (var s = new Array(arguments.length), n = 0; n < s.length; n++)
      s[n] = arguments[n];
    return e.apply(r, s);
  };
}, Tr = Ke, C = Object.prototype.toString;
function ve(t) {
  return Array.isArray(t);
}
function he(t) {
  return typeof t > "u";
}
function kr(t) {
  return t !== null && !he(t) && t.constructor !== null && !he(t.constructor) && typeof t.constructor.isBuffer == "function" && t.constructor.isBuffer(t);
}
function Ye(t) {
  return C.call(t) === "[object ArrayBuffer]";
}
function yr(t) {
  return C.call(t) === "[object FormData]";
}
function gr(t) {
  var e;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? e = ArrayBuffer.isView(t) : e = t && t.buffer && Ye(t.buffer), e;
}
function br(t) {
  return typeof t == "string";
}
function Or(t) {
  return typeof t == "number";
}
function Ze(t) {
  return t !== null && typeof t == "object";
}
function $(t) {
  if (C.call(t) !== "[object Object]")
    return !1;
  var e = Object.getPrototypeOf(t);
  return e === null || e === Object.prototype;
}
function Rr(t) {
  return C.call(t) === "[object Date]";
}
function Sr(t) {
  return C.call(t) === "[object File]";
}
function wr(t) {
  return C.call(t) === "[object Blob]";
}
function er(t) {
  return C.call(t) === "[object Function]";
}
function Ar(t) {
  return Ze(t) && er(t.pipe);
}
function Er(t) {
  return C.call(t) === "[object URLSearchParams]";
}
function Cr(t) {
  return t.trim ? t.trim() : t.replace(/^\s+|\s+$/g, "");
}
function Pr() {
  return typeof navigator < "u" && (navigator.product === "ReactNative" || navigator.product === "NativeScript" || navigator.product === "NS") ? !1 : typeof window < "u" && typeof document < "u";
}
function Te(t, e) {
  if (!(t === null || typeof t > "u"))
    if (typeof t != "object" && (t = [t]), ve(t))
      for (var r = 0, a = t.length; r < a; r++)
        e.call(null, t[r], r, t);
    else
      for (var s in t)
        Object.prototype.hasOwnProperty.call(t, s) && e.call(null, t[s], s, t);
}
function fe() {
  var t = {};
  function e(s, n) {
    $(t[n]) && $(s) ? t[n] = fe(t[n], s) : $(s) ? t[n] = fe({}, s) : ve(s) ? t[n] = s.slice() : t[n] = s;
  }
  for (var r = 0, a = arguments.length; r < a; r++)
    Te(arguments[r], e);
  return t;
}
function Ur(t, e, r) {
  return Te(e, function(s, n) {
    r && typeof s == "function" ? t[n] = Tr(s, r) : t[n] = s;
  }), t;
}
function Vr(t) {
  return t.charCodeAt(0) === 65279 && (t = t.slice(1)), t;
}
var R = {
  isArray: ve,
  isArrayBuffer: Ye,
  isBuffer: kr,
  isFormData: yr,
  isArrayBufferView: gr,
  isString: br,
  isNumber: Or,
  isObject: Ze,
  isPlainObject: $,
  isUndefined: he,
  isDate: Rr,
  isFile: Sr,
  isBlob: wr,
  isFunction: er,
  isStream: Ar,
  isURLSearchParams: Er,
  isStandardBrowserEnv: Pr,
  forEach: Te,
  merge: fe,
  extend: Ur,
  trim: Cr,
  stripBOM: Vr
}, I = R;
function we(t) {
  return encodeURIComponent(t).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
var rr = function(e, r, a) {
  if (!r)
    return e;
  var s;
  if (a)
    s = a(r);
  else if (I.isURLSearchParams(r))
    s = r.toString();
  else {
    var n = [];
    I.forEach(r, function(c, l) {
      c === null || typeof c > "u" || (I.isArray(c) ? l = l + "[]" : c = [c], I.forEach(c, function(u) {
        I.isDate(u) ? u = u.toISOString() : I.isObject(u) && (u = JSON.stringify(u)), n.push(we(l) + "=" + we(u));
      }));
    }), s = n.join("&");
  }
  if (s) {
    var o = e.indexOf("#");
    o !== -1 && (e = e.slice(0, o)), e += (e.indexOf("?") === -1 ? "?" : "&") + s;
  }
  return e;
}, Ir = R;
function M() {
  this.handlers = [];
}
M.prototype.use = function(e, r, a) {
  return this.handlers.push({
    fulfilled: e,
    rejected: r,
    synchronous: a ? a.synchronous : !1,
    runWhen: a ? a.runWhen : null
  }), this.handlers.length - 1;
};
M.prototype.eject = function(e) {
  this.handlers[e] && (this.handlers[e] = null);
};
M.prototype.forEach = function(e) {
  Ir.forEach(this.handlers, function(a) {
    a !== null && e(a);
  });
};
var xr = M, Br = R, qr = function(e, r) {
  Br.forEach(e, function(s, n) {
    n !== r && n.toUpperCase() === r.toUpperCase() && (e[r] = s, delete e[n]);
  });
}, tr = function(e, r, a, s, n) {
  return e.config = r, a && (e.code = a), e.request = s, e.response = n, e.isAxiosError = !0, e.toJSON = function() {
    return {
      message: this.message,
      name: this.name,
      description: this.description,
      number: this.number,
      fileName: this.fileName,
      lineNumber: this.lineNumber,
      columnNumber: this.columnNumber,
      stack: this.stack,
      config: this.config,
      code: this.code,
      status: this.response && this.response.status ? this.response.status : null
    };
  }, e;
}, sr = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, z, Ae;
function ar() {
  if (Ae)
    return z;
  Ae = 1;
  var t = tr;
  return z = function(r, a, s, n, o) {
    var i = new Error(r);
    return t(i, a, s, n, o);
  }, z;
}
var X, Ee;
function jr() {
  if (Ee)
    return X;
  Ee = 1;
  var t = ar();
  return X = function(r, a, s) {
    var n = s.config.validateStatus;
    !s.status || !n || n(s.status) ? r(s) : a(t(
      "Request failed with status code " + s.status,
      s.config,
      null,
      s.request,
      s
    ));
  }, X;
}
var K, Ce;
function Nr() {
  if (Ce)
    return K;
  Ce = 1;
  var t = R;
  return K = t.isStandardBrowserEnv() ? function() {
    return {
      write: function(a, s, n, o, i, c) {
        var l = [];
        l.push(a + "=" + encodeURIComponent(s)), t.isNumber(n) && l.push("expires=" + new Date(n).toGMTString()), t.isString(o) && l.push("path=" + o), t.isString(i) && l.push("domain=" + i), c === !0 && l.push("secure"), document.cookie = l.join("; ");
      },
      read: function(a) {
        var s = document.cookie.match(new RegExp("(^|;\\s*)(" + a + ")=([^;]*)"));
        return s ? decodeURIComponent(s[3]) : null;
      },
      remove: function(a) {
        this.write(a, "", Date.now() - 864e5);
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
  }(), K;
}
var Y, Pe;
function Lr() {
  return Pe || (Pe = 1, Y = function(e) {
    return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(e);
  }), Y;
}
var Z, Ue;
function Fr() {
  return Ue || (Ue = 1, Z = function(e, r) {
    return r ? e.replace(/\/+$/, "") + "/" + r.replace(/^\/+/, "") : e;
  }), Z;
}
var ee, Ve;
function $r() {
  if (Ve)
    return ee;
  Ve = 1;
  var t = Lr(), e = Fr();
  return ee = function(a, s) {
    return a && !t(s) ? e(a, s) : s;
  }, ee;
}
var re, Ie;
function Hr() {
  if (Ie)
    return re;
  Ie = 1;
  var t = R, e = [
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
  ];
  return re = function(a) {
    var s = {}, n, o, i;
    return a && t.forEach(a.split(`
`), function(l) {
      if (i = l.indexOf(":"), n = t.trim(l.substr(0, i)).toLowerCase(), o = t.trim(l.substr(i + 1)), n) {
        if (s[n] && e.indexOf(n) >= 0)
          return;
        n === "set-cookie" ? s[n] = (s[n] ? s[n] : []).concat([o]) : s[n] = s[n] ? s[n] + ", " + o : o;
      }
    }), s;
  }, re;
}
var te, xe;
function Dr() {
  if (xe)
    return te;
  xe = 1;
  var t = R;
  return te = t.isStandardBrowserEnv() ? function() {
    var r = /(msie|trident)/i.test(navigator.userAgent), a = document.createElement("a"), s;
    function n(o) {
      var i = o;
      return r && (a.setAttribute("href", i), i = a.href), a.setAttribute("href", i), {
        href: a.href,
        protocol: a.protocol ? a.protocol.replace(/:$/, "") : "",
        host: a.host,
        search: a.search ? a.search.replace(/^\?/, "") : "",
        hash: a.hash ? a.hash.replace(/^#/, "") : "",
        hostname: a.hostname,
        port: a.port,
        pathname: a.pathname.charAt(0) === "/" ? a.pathname : "/" + a.pathname
      };
    }
    return s = n(window.location.href), function(i) {
      var c = t.isString(i) ? n(i) : i;
      return c.protocol === s.protocol && c.host === s.host;
    };
  }() : function() {
    return function() {
      return !0;
    };
  }(), te;
}
var se, Be;
function G() {
  if (Be)
    return se;
  Be = 1;
  function t(e) {
    this.message = e;
  }
  return t.prototype.toString = function() {
    return "Cancel" + (this.message ? ": " + this.message : "");
  }, t.prototype.__CANCEL__ = !0, se = t, se;
}
var ae, qe;
function je() {
  if (qe)
    return ae;
  qe = 1;
  var t = R, e = jr(), r = Nr(), a = rr, s = $r(), n = Hr(), o = Dr(), i = ar(), c = sr, l = G();
  return ae = function(u) {
    return new Promise(function(m, B) {
      var q = u.data, j = u.headers, N = u.responseType, U;
      function be() {
        u.cancelToken && u.cancelToken.unsubscribe(U), u.signal && u.signal.removeEventListener("abort", U);
      }
      t.isFormData(q) && delete j["Content-Type"];
      var h = new XMLHttpRequest();
      if (u.auth) {
        var fr = u.auth.username || "", pr = u.auth.password ? unescape(encodeURIComponent(u.auth.password)) : "";
        j.Authorization = "Basic " + btoa(fr + ":" + pr);
      }
      var Oe = s(u.baseURL, u.url);
      h.open(u.method.toUpperCase(), a(Oe, u.params, u.paramsSerializer), !0), h.timeout = u.timeout;
      function Re() {
        if (!!h) {
          var w = "getAllResponseHeaders" in h ? n(h.getAllResponseHeaders()) : null, V = !N || N === "text" || N === "json" ? h.responseText : h.response, P = {
            data: V,
            status: h.status,
            statusText: h.statusText,
            headers: w,
            config: u,
            request: h
          };
          e(function(W) {
            m(W), be();
          }, function(W) {
            B(W), be();
          }, P), h = null;
        }
      }
      if ("onloadend" in h ? h.onloadend = Re : h.onreadystatechange = function() {
        !h || h.readyState !== 4 || h.status === 0 && !(h.responseURL && h.responseURL.indexOf("file:") === 0) || setTimeout(Re);
      }, h.onabort = function() {
        !h || (B(i("Request aborted", u, "ECONNABORTED", h)), h = null);
      }, h.onerror = function() {
        B(i("Network Error", u, null, h)), h = null;
      }, h.ontimeout = function() {
        var V = u.timeout ? "timeout of " + u.timeout + "ms exceeded" : "timeout exceeded", P = u.transitional || c;
        u.timeoutErrorMessage && (V = u.timeoutErrorMessage), B(i(
          V,
          u,
          P.clarifyTimeoutError ? "ETIMEDOUT" : "ECONNABORTED",
          h
        )), h = null;
      }, t.isStandardBrowserEnv()) {
        var Se = (u.withCredentials || o(Oe)) && u.xsrfCookieName ? r.read(u.xsrfCookieName) : void 0;
        Se && (j[u.xsrfHeaderName] = Se);
      }
      "setRequestHeader" in h && t.forEach(j, function(V, P) {
        typeof q > "u" && P.toLowerCase() === "content-type" ? delete j[P] : h.setRequestHeader(P, V);
      }), t.isUndefined(u.withCredentials) || (h.withCredentials = !!u.withCredentials), N && N !== "json" && (h.responseType = u.responseType), typeof u.onDownloadProgress == "function" && h.addEventListener("progress", u.onDownloadProgress), typeof u.onUploadProgress == "function" && h.upload && h.upload.addEventListener("progress", u.onUploadProgress), (u.cancelToken || u.signal) && (U = function(w) {
        !h || (B(!w || w && w.type ? new l("canceled") : w), h.abort(), h = null);
      }, u.cancelToken && u.cancelToken.subscribe(U), u.signal && (u.signal.aborted ? U() : u.signal.addEventListener("abort", U))), q || (q = null), h.send(q);
    });
  }, ae;
}
var p = R, Ne = qr, _r = tr, Mr = sr, Gr = {
  "Content-Type": "application/x-www-form-urlencoded"
};
function Le(t, e) {
  !p.isUndefined(t) && p.isUndefined(t["Content-Type"]) && (t["Content-Type"] = e);
}
function Jr() {
  var t;
  return (typeof XMLHttpRequest < "u" || typeof process < "u" && Object.prototype.toString.call(process) === "[object process]") && (t = je()), t;
}
function Qr(t, e, r) {
  if (p.isString(t))
    try {
      return (e || JSON.parse)(t), p.trim(t);
    } catch (a) {
      if (a.name !== "SyntaxError")
        throw a;
    }
  return (r || JSON.stringify)(t);
}
var J = {
  transitional: Mr,
  adapter: Jr(),
  transformRequest: [function(e, r) {
    return Ne(r, "Accept"), Ne(r, "Content-Type"), p.isFormData(e) || p.isArrayBuffer(e) || p.isBuffer(e) || p.isStream(e) || p.isFile(e) || p.isBlob(e) ? e : p.isArrayBufferView(e) ? e.buffer : p.isURLSearchParams(e) ? (Le(r, "application/x-www-form-urlencoded;charset=utf-8"), e.toString()) : p.isObject(e) || r && r["Content-Type"] === "application/json" ? (Le(r, "application/json"), Qr(e)) : e;
  }],
  transformResponse: [function(e) {
    var r = this.transitional || J.transitional, a = r && r.silentJSONParsing, s = r && r.forcedJSONParsing, n = !a && this.responseType === "json";
    if (n || s && p.isString(e) && e.length)
      try {
        return JSON.parse(e);
      } catch (o) {
        if (n)
          throw o.name === "SyntaxError" ? _r(o, this, "E_JSON_PARSE") : o;
      }
    return e;
  }],
  timeout: 0,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
  maxContentLength: -1,
  maxBodyLength: -1,
  validateStatus: function(e) {
    return e >= 200 && e < 300;
  },
  headers: {
    common: {
      Accept: "application/json, text/plain, */*"
    }
  }
};
p.forEach(["delete", "get", "head"], function(e) {
  J.headers[e] = {};
});
p.forEach(["post", "put", "patch"], function(e) {
  J.headers[e] = p.merge(Gr);
});
var ke = J, Wr = R, zr = ke, Xr = function(e, r, a) {
  var s = this || zr;
  return Wr.forEach(a, function(o) {
    e = o.call(s, e, r);
  }), e;
}, ne, Fe;
function nr() {
  return Fe || (Fe = 1, ne = function(e) {
    return !!(e && e.__CANCEL__);
  }), ne;
}
var $e = R, oe = Xr, Kr = nr(), Yr = ke, Zr = G();
function ie(t) {
  if (t.cancelToken && t.cancelToken.throwIfRequested(), t.signal && t.signal.aborted)
    throw new Zr("canceled");
}
var et = function(e) {
  ie(e), e.headers = e.headers || {}, e.data = oe.call(
    e,
    e.data,
    e.headers,
    e.transformRequest
  ), e.headers = $e.merge(
    e.headers.common || {},
    e.headers[e.method] || {},
    e.headers
  ), $e.forEach(
    ["delete", "get", "head", "post", "put", "patch", "common"],
    function(s) {
      delete e.headers[s];
    }
  );
  var r = e.adapter || Yr.adapter;
  return r(e).then(function(s) {
    return ie(e), s.data = oe.call(
      e,
      s.data,
      s.headers,
      e.transformResponse
    ), s;
  }, function(s) {
    return Kr(s) || (ie(e), s && s.response && (s.response.data = oe.call(
      e,
      s.response.data,
      s.response.headers,
      e.transformResponse
    ))), Promise.reject(s);
  });
}, S = R, or = function(e, r) {
  r = r || {};
  var a = {};
  function s(d, u) {
    return S.isPlainObject(d) && S.isPlainObject(u) ? S.merge(d, u) : S.isPlainObject(u) ? S.merge({}, u) : S.isArray(u) ? u.slice() : u;
  }
  function n(d) {
    if (S.isUndefined(r[d])) {
      if (!S.isUndefined(e[d]))
        return s(void 0, e[d]);
    } else
      return s(e[d], r[d]);
  }
  function o(d) {
    if (!S.isUndefined(r[d]))
      return s(void 0, r[d]);
  }
  function i(d) {
    if (S.isUndefined(r[d])) {
      if (!S.isUndefined(e[d]))
        return s(void 0, e[d]);
    } else
      return s(void 0, r[d]);
  }
  function c(d) {
    if (d in r)
      return s(e[d], r[d]);
    if (d in e)
      return s(void 0, e[d]);
  }
  var l = {
    url: o,
    method: o,
    data: o,
    baseURL: i,
    transformRequest: i,
    transformResponse: i,
    paramsSerializer: i,
    timeout: i,
    timeoutMessage: i,
    withCredentials: i,
    adapter: i,
    responseType: i,
    xsrfCookieName: i,
    xsrfHeaderName: i,
    onUploadProgress: i,
    onDownloadProgress: i,
    decompress: i,
    maxContentLength: i,
    maxBodyLength: i,
    transport: i,
    httpAgent: i,
    httpsAgent: i,
    cancelToken: i,
    socketPath: i,
    responseEncoding: i,
    validateStatus: c
  };
  return S.forEach(Object.keys(e).concat(Object.keys(r)), function(u) {
    var f = l[u] || n, m = f(u);
    S.isUndefined(m) && f !== c || (a[u] = m);
  }), a;
}, ue, He;
function ir() {
  return He || (He = 1, ue = {
    version: "0.26.1"
  }), ue;
}
var rt = ir().version, ye = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach(function(t, e) {
  ye[t] = function(a) {
    return typeof a === t || "a" + (e < 1 ? "n " : " ") + t;
  };
});
var De = {};
ye.transitional = function(e, r, a) {
  function s(n, o) {
    return "[Axios v" + rt + "] Transitional option '" + n + "'" + o + (a ? ". " + a : "");
  }
  return function(n, o, i) {
    if (e === !1)
      throw new Error(s(o, " has been removed" + (r ? " in " + r : "")));
    return r && !De[o] && (De[o] = !0, console.warn(
      s(
        o,
        " has been deprecated since v" + r + " and will be removed in the near future"
      )
    )), e ? e(n, o, i) : !0;
  };
};
function tt(t, e, r) {
  if (typeof t != "object")
    throw new TypeError("options must be an object");
  for (var a = Object.keys(t), s = a.length; s-- > 0; ) {
    var n = a[s], o = e[n];
    if (o) {
      var i = t[n], c = i === void 0 || o(i, n, t);
      if (c !== !0)
        throw new TypeError("option " + n + " must be " + c);
      continue;
    }
    if (r !== !0)
      throw Error("Unknown option " + n);
  }
}
var st = {
  assertOptions: tt,
  validators: ye
}, ur = R, at = rr, _e = xr, Me = et, Q = or, cr = st, x = cr.validators;
function L(t) {
  this.defaults = t, this.interceptors = {
    request: new _e(),
    response: new _e()
  };
}
L.prototype.request = function(e, r) {
  typeof e == "string" ? (r = r || {}, r.url = e) : r = e || {}, r = Q(this.defaults, r), r.method ? r.method = r.method.toLowerCase() : this.defaults.method ? r.method = this.defaults.method.toLowerCase() : r.method = "get";
  var a = r.transitional;
  a !== void 0 && cr.assertOptions(a, {
    silentJSONParsing: x.transitional(x.boolean),
    forcedJSONParsing: x.transitional(x.boolean),
    clarifyTimeoutError: x.transitional(x.boolean)
  }, !1);
  var s = [], n = !0;
  this.interceptors.request.forEach(function(m) {
    typeof m.runWhen == "function" && m.runWhen(r) === !1 || (n = n && m.synchronous, s.unshift(m.fulfilled, m.rejected));
  });
  var o = [];
  this.interceptors.response.forEach(function(m) {
    o.push(m.fulfilled, m.rejected);
  });
  var i;
  if (!n) {
    var c = [Me, void 0];
    for (Array.prototype.unshift.apply(c, s), c = c.concat(o), i = Promise.resolve(r); c.length; )
      i = i.then(c.shift(), c.shift());
    return i;
  }
  for (var l = r; s.length; ) {
    var d = s.shift(), u = s.shift();
    try {
      l = d(l);
    } catch (f) {
      u(f);
      break;
    }
  }
  try {
    i = Me(l);
  } catch (f) {
    return Promise.reject(f);
  }
  for (; o.length; )
    i = i.then(o.shift(), o.shift());
  return i;
};
L.prototype.getUri = function(e) {
  return e = Q(this.defaults, e), at(e.url, e.params, e.paramsSerializer).replace(/^\?/, "");
};
ur.forEach(["delete", "get", "head", "options"], function(e) {
  L.prototype[e] = function(r, a) {
    return this.request(Q(a || {}, {
      method: e,
      url: r,
      data: (a || {}).data
    }));
  };
});
ur.forEach(["post", "put", "patch"], function(e) {
  L.prototype[e] = function(r, a, s) {
    return this.request(Q(s || {}, {
      method: e,
      url: r,
      data: a
    }));
  };
});
var nt = L, ce, Ge;
function ot() {
  if (Ge)
    return ce;
  Ge = 1;
  var t = G();
  function e(r) {
    if (typeof r != "function")
      throw new TypeError("executor must be a function.");
    var a;
    this.promise = new Promise(function(o) {
      a = o;
    });
    var s = this;
    this.promise.then(function(n) {
      if (!!s._listeners) {
        var o, i = s._listeners.length;
        for (o = 0; o < i; o++)
          s._listeners[o](n);
        s._listeners = null;
      }
    }), this.promise.then = function(n) {
      var o, i = new Promise(function(c) {
        s.subscribe(c), o = c;
      }).then(n);
      return i.cancel = function() {
        s.unsubscribe(o);
      }, i;
    }, r(function(o) {
      s.reason || (s.reason = new t(o), a(s.reason));
    });
  }
  return e.prototype.throwIfRequested = function() {
    if (this.reason)
      throw this.reason;
  }, e.prototype.subscribe = function(a) {
    if (this.reason) {
      a(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(a) : this._listeners = [a];
  }, e.prototype.unsubscribe = function(a) {
    if (!!this._listeners) {
      var s = this._listeners.indexOf(a);
      s !== -1 && this._listeners.splice(s, 1);
    }
  }, e.source = function() {
    var a, s = new e(function(o) {
      a = o;
    });
    return {
      token: s,
      cancel: a
    };
  }, ce = e, ce;
}
var le, Je;
function it() {
  return Je || (Je = 1, le = function(e) {
    return function(a) {
      return e.apply(null, a);
    };
  }), le;
}
var de, Qe;
function ut() {
  if (Qe)
    return de;
  Qe = 1;
  var t = R;
  return de = function(r) {
    return t.isObject(r) && r.isAxiosError === !0;
  }, de;
}
var We = R, ct = Ke, H = nt, lt = or, dt = ke;
function lr(t) {
  var e = new H(t), r = ct(H.prototype.request, e);
  return We.extend(r, H.prototype, e), We.extend(r, e), r.create = function(s) {
    return lr(lt(t, s));
  }, r;
}
var E = lr(dt);
E.Axios = H;
E.Cancel = G();
E.CancelToken = ot();
E.isCancel = nr();
E.VERSION = ir().version;
E.all = function(e) {
  return Promise.all(e);
};
E.spread = it();
E.isAxiosError = ut();
me.exports = E;
me.exports.default = E;
(function(t) {
  t.exports = me.exports;
})(Xe);
const T = /* @__PURE__ */ vr(Xe.exports), k = "";
class ge {
  constructor(e, r = k, a = T) {
    this.basePath = r, this.axios = a, e && (this.configuration = e, this.basePath = e.basePath || this.basePath);
  }
}
class ht extends Error {
  constructor(e, r) {
    super(r), this.field = e, this.name = "RequiredError";
  }
}
const y = "https://example.com", v = function(t, e, r) {
  if (r == null)
    throw new ht(e, `Required parameter ${e} was null or undefined when calling ${t}.`);
};
function pe(t, e, r = "") {
  typeof e == "object" ? Array.isArray(e) ? e.forEach((a) => pe(t, a, r)) : Object.keys(e).forEach(
    (a) => pe(t, e[a], `${r}${r !== "" ? "." : ""}${a}`)
  ) : t.has(r) ? t.append(r, e) : t.set(r, e);
}
const g = function(t, ...e) {
  const r = new URLSearchParams(t.search);
  pe(r, e), t.search = r.toString();
}, F = function(t, e, r) {
  const a = typeof t != "string";
  return (a && r && r.isJsonMime ? r.isJsonMime(e.headers["Content-Type"]) : a) ? JSON.stringify(t !== void 0 ? t : {}) : t || "";
}, b = function(t) {
  return t.pathname + t.search + t.hash;
}, O = function(t, e, r, a) {
  return (s = e, n = r) => {
    const o = { ...t.options, url: ((a == null ? void 0 : a.basePath) || n) + t.url };
    return s.request(o);
  };
}, ft = function(t) {
  return {
    assignTask: async (e, r, a = {}) => {
      v("assignTask", "taskId", e), v("assignTask", "taskAssignment", r);
      const s = "/tasks/id/{taskId}/assign".replace("{taskId}", encodeURIComponent(String(e))), n = new URL(s, y);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "POST", ...o, ...a }, c = {}, l = {};
      c["Content-Type"] = "application/json", g(n, l);
      let d = o && o.headers ? o.headers : {};
      return i.headers = { ...c, ...d, ...a.headers }, i.data = F(r, i, t), {
        url: b(n),
        options: i
      };
    },
    completeTask: async (e, r, a = {}) => {
      v("completeTask", "taskId", e), v("completeTask", "requestBody", r);
      const s = "/tasks/id/{taskId}".replace("{taskId}", encodeURIComponent(String(e))), n = new URL(s, y);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "POST", ...o, ...a }, c = {}, l = {};
      c["Content-Type"] = "application/json", g(n, l);
      let d = o && o.headers ? o.headers : {};
      return i.headers = { ...c, ...d, ...a.headers }, i.data = F(r, i, t), {
        url: b(n),
        options: i
      };
    },
    deferTask: async (e, r, a = {}) => {
      v("deferTask", "taskId", e), v("deferTask", "taskDeferral", r);
      const s = "/tasks/id/{taskId}/defer".replace("{taskId}", encodeURIComponent(String(e))), n = new URL(s, y);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "POST", ...o, ...a }, c = {}, l = {};
      c["Content-Type"] = "application/json", g(n, l);
      let d = o && o.headers ? o.headers : {};
      return i.headers = { ...c, ...d, ...a.headers }, i.data = F(r, i, t), {
        url: b(n),
        options: i
      };
    },
    getSchema: async (e, r = {}) => {
      v("getSchema", "schemaId", e);
      const a = "/schema/id/{schemaId}/combined".replace("{schemaId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "GET", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    },
    getTaskByTaskId: async (e, r = {}) => {
      v("getTaskByTaskId", "taskId", e);
      const a = "/tasks/id/{taskId}".replace("{taskId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "GET", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    },
    getTaskSchema: async (e, r = {}) => {
      v("getTaskSchema", "taskId", e);
      const a = "/schema/task-id/{taskId}/combined".replace("{taskId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "GET", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    },
    getTaskWithSchemaByTaskId: async (e, r = {}) => {
      v("getTaskWithSchemaByTaskId", "taskId", e);
      const a = "/tasks/id/{taskId}/with-schema".replace("{taskId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "GET", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    },
    saveTaskVariables: async (e, r, a = {}) => {
      v("saveTaskVariables", "taskId", e), v("saveTaskVariables", "requestBody", r);
      const s = "/tasks/id/{taskId}/save".replace("{taskId}", encodeURIComponent(String(e))), n = new URL(s, y);
      let o;
      t && (o = t.baseOptions);
      const i = { method: "POST", ...o, ...a }, c = {}, l = {};
      c["Content-Type"] = "application/json", g(n, l);
      let d = o && o.headers ? o.headers : {};
      return i.headers = { ...c, ...d, ...a.headers }, i.data = F(r, i, t), {
        url: b(n),
        options: i
      };
    },
    unassignTask: async (e, r = {}) => {
      v("unassignTask", "taskId", e);
      const a = "/tasks/id/{taskId}/unassign".replace("{taskId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "POST", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    },
    undeferTask: async (e, r = {}) => {
      v("undeferTask", "taskId", e);
      const a = "/tasks/id/{taskId}/undefer".replace("{taskId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "POST", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    }
  };
}, A = function(t) {
  const e = ft(t);
  return {
    async assignTask(r, a, s) {
      const n = await e.assignTask(r, a, s);
      return O(n, T, k, t);
    },
    async completeTask(r, a, s) {
      const n = await e.completeTask(r, a, s);
      return O(n, T, k, t);
    },
    async deferTask(r, a, s) {
      const n = await e.deferTask(r, a, s);
      return O(n, T, k, t);
    },
    async getSchema(r, a) {
      const s = await e.getSchema(r, a);
      return O(s, T, k, t);
    },
    async getTaskByTaskId(r, a) {
      const s = await e.getTaskByTaskId(r, a);
      return O(s, T, k, t);
    },
    async getTaskSchema(r, a) {
      const s = await e.getTaskSchema(r, a);
      return O(s, T, k, t);
    },
    async getTaskWithSchemaByTaskId(r, a) {
      const s = await e.getTaskWithSchemaByTaskId(r, a);
      return O(s, T, k, t);
    },
    async saveTaskVariables(r, a, s) {
      const n = await e.saveTaskVariables(r, a, s);
      return O(n, T, k, t);
    },
    async unassignTask(r, a) {
      const s = await e.unassignTask(r, a);
      return O(s, T, k, t);
    },
    async undeferTask(r, a) {
      const s = await e.undeferTask(r, a);
      return O(s, T, k, t);
    }
  };
}, Tt = function(t, e, r) {
  const a = A(t);
  return {
    assignTask(s, n, o) {
      return a.assignTask(s, n, o).then((i) => i(r, e));
    },
    completeTask(s, n, o) {
      return a.completeTask(s, n, o).then((i) => i(r, e));
    },
    deferTask(s, n, o) {
      return a.deferTask(s, n, o).then((i) => i(r, e));
    },
    getSchema(s, n) {
      return a.getSchema(s, n).then((o) => o(r, e));
    },
    getTaskByTaskId(s, n) {
      return a.getTaskByTaskId(s, n).then((o) => o(r, e));
    },
    getTaskSchema(s, n) {
      return a.getTaskSchema(s, n).then((o) => o(r, e));
    },
    getTaskWithSchemaByTaskId(s, n) {
      return a.getTaskWithSchemaByTaskId(s, n).then((o) => o(r, e));
    },
    saveTaskVariables(s, n, o) {
      return a.saveTaskVariables(s, n, o).then((i) => i(r, e));
    },
    unassignTask(s, n) {
      return a.unassignTask(s, n).then((o) => o(r, e));
    },
    undeferTask(s, n) {
      return a.undeferTask(s, n).then((o) => o(r, e));
    }
  };
};
class kt extends ge {
  assignTask(e, r) {
    return A(this.configuration).assignTask(e.taskId, e.taskAssignment, r).then((a) => a(this.axios, this.basePath));
  }
  completeTask(e, r) {
    return A(this.configuration).completeTask(e.taskId, e.requestBody, r).then((a) => a(this.axios, this.basePath));
  }
  deferTask(e, r) {
    return A(this.configuration).deferTask(e.taskId, e.taskDeferral, r).then((a) => a(this.axios, this.basePath));
  }
  getSchema(e, r) {
    return A(this.configuration).getSchema(e.schemaId, r).then((a) => a(this.axios, this.basePath));
  }
  getTaskByTaskId(e, r) {
    return A(this.configuration).getTaskByTaskId(e.taskId, r).then((a) => a(this.axios, this.basePath));
  }
  getTaskSchema(e, r) {
    return A(this.configuration).getTaskSchema(e.taskId, r).then((a) => a(this.axios, this.basePath));
  }
  getTaskWithSchemaByTaskId(e, r) {
    return A(this.configuration).getTaskWithSchemaByTaskId(e.taskId, r).then((a) => a(this.axios, this.basePath));
  }
  saveTaskVariables(e, r) {
    return A(this.configuration).saveTaskVariables(e.taskId, e.requestBody, r).then((a) => a(this.axios, this.basePath));
  }
  unassignTask(e, r) {
    return A(this.configuration).unassignTask(e.taskId, r).then((a) => a(this.axios, this.basePath));
  }
  undeferTask(e, r) {
    return A(this.configuration).undeferTask(e.taskId, r).then((a) => a(this.axios, this.basePath));
  }
}
const pt = function(t) {
  return {
    getAssignedGroupTasks: async (e, r, a, s, n = {}) => {
      const o = "/tasks/group/assigned", i = new URL(o, y);
      let c;
      t && (c = t.baseOptions);
      const l = { method: "GET", ...c, ...n }, d = {}, u = {};
      e !== void 0 && (u.page = e), r !== void 0 && (u.size = r), a !== void 0 && (u.query = a), s !== void 0 && (u.sort = s), g(i, u);
      let f = c && c.headers ? c.headers : {};
      return l.headers = { ...d, ...f, ...n.headers }, {
        url: b(i),
        options: l
      };
    },
    getCurrentUserTasks: async (e, r, a, s, n, o = {}) => {
      const i = "/tasks/user", c = new URL(i, y);
      let l;
      t && (l = t.baseOptions);
      const d = { method: "GET", ...l, ...o }, u = {}, f = {};
      e !== void 0 && (f.page = e), r !== void 0 && (f.size = r), a !== void 0 && (f.query = a), s !== void 0 && (f.followUp = s instanceof Date ? s.toISOString().substr(0, 10) : s), n !== void 0 && (f.sort = n), g(c, f);
      let m = l && l.headers ? l.headers : {};
      return d.headers = { ...u, ...m, ...o.headers }, {
        url: b(c),
        options: d
      };
    },
    getUnassignedGroupTasks: async (e, r, a, s, n = {}) => {
      const o = "/tasks/group/unassigned", i = new URL(o, y);
      let c;
      t && (c = t.baseOptions);
      const l = { method: "GET", ...c, ...n }, d = {}, u = {};
      e !== void 0 && (u.page = e), r !== void 0 && (u.size = r), a !== void 0 && (u.query = a), s !== void 0 && (u.sort = s), g(i, u);
      let f = c && c.headers ? c.headers : {};
      return l.headers = { ...d, ...f, ...n.headers }, {
        url: b(i),
        options: l
      };
    }
  };
}, D = function(t) {
  const e = pt(t);
  return {
    async getAssignedGroupTasks(r, a, s, n, o) {
      const i = await e.getAssignedGroupTasks(r, a, s, n, o);
      return O(i, T, k, t);
    },
    async getCurrentUserTasks(r, a, s, n, o, i) {
      const c = await e.getCurrentUserTasks(r, a, s, n, o, i);
      return O(c, T, k, t);
    },
    async getUnassignedGroupTasks(r, a, s, n, o) {
      const i = await e.getUnassignedGroupTasks(r, a, s, n, o);
      return O(i, T, k, t);
    }
  };
}, yt = function(t, e, r) {
  const a = D(t);
  return {
    getAssignedGroupTasks(s, n, o, i, c) {
      return a.getAssignedGroupTasks(s, n, o, i, c).then((l) => l(r, e));
    },
    getCurrentUserTasks(s, n, o, i, c, l) {
      return a.getCurrentUserTasks(s, n, o, i, c, l).then((d) => d(r, e));
    },
    getUnassignedGroupTasks(s, n, o, i, c) {
      return a.getUnassignedGroupTasks(s, n, o, i, c).then((l) => l(r, e));
    }
  };
};
class gt extends ge {
  getAssignedGroupTasks(e = {}, r) {
    return D(this.configuration).getAssignedGroupTasks(e.page, e.size, e.query, e.sort, r).then((a) => a(this.axios, this.basePath));
  }
  getCurrentUserTasks(e = {}, r) {
    return D(this.configuration).getCurrentUserTasks(e.page, e.size, e.query, e.followUp, e.sort, r).then((a) => a(this.axios, this.basePath));
  }
  getUnassignedGroupTasks(e = {}, r) {
    return D(this.configuration).getUnassignedGroupTasks(e.page, e.size, e.query, e.sort, r).then((a) => a(this.axios, this.basePath));
  }
}
const mt = function(t) {
  return {
    resolveUser: async (e, r = {}) => {
      v("resolveUser", "userId", e);
      const a = "/user/id/{userId}".replace("{userId}", encodeURIComponent(String(e))), s = new URL(a, y);
      let n;
      t && (n = t.baseOptions);
      const o = { method: "GET", ...n, ...r }, i = {};
      g(s, {});
      let l = n && n.headers ? n.headers : {};
      return o.headers = { ...i, ...l, ...r.headers }, {
        url: b(s),
        options: o
      };
    }
  };
}, dr = function(t) {
  const e = mt(t);
  return {
    async resolveUser(r, a) {
      const s = await e.resolveUser(r, a);
      return O(s, T, k, t);
    }
  };
}, bt = function(t, e, r) {
  const a = dr(t);
  return {
    resolveUser(s, n) {
      return a.resolveUser(s, n).then((o) => o(r, e));
    }
  };
};
class Ot extends ge {
  resolveUser(e, r) {
    return dr(this.configuration).resolveUser(e.userId, r).then((a) => a(this.axios, this.basePath));
  }
}
class vt {
  constructor(e = {}) {
    this.apiKey = e.apiKey, this.username = e.username, this.password = e.password, this.accessToken = e.accessToken, this.basePath = e.basePath, this.baseOptions = e.baseOptions, this.formDataCtor = e.formDataCtor;
  }
  isJsonMime(e) {
    const r = new RegExp("^(application/json|[^;/ 	]+/[^;/ 	]+[+]json)[ 	]*(;.*)?$", "i");
    return e !== null && (r.test(e) || e.toLowerCase() === "application/json-patch+json");
  }
}
var hr = /* @__PURE__ */ ((t) => (t.INFO = "info", t.WARNING = "warning", t.ERROR = "error", t))(hr || {});
class ze extends Error {
  constructor({ level: e = "error", message: r = "Ein unbekannter Fehler ist aufgetreten, bitte den Administrator informieren." }) {
    super(r), this.stack = new Error().stack, this.level = e, this.message = r;
  }
}
class _ {
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
    const r = new vt();
    return r.baseOptions = e, r;
  }
  static getPOSTConfig(e) {
    return {
      method: "POST",
      body: e ? JSON.stringify(e) : void 0,
      headers: _.getHeaders(),
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getPUTConfig(e) {
    const r = _.getHeaders();
    return e.version && r.append("If-Match", e.version), {
      method: "PUT",
      body: e ? JSON.stringify(e) : void 0,
      headers: r,
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static defaultCatchHandler(e, r = "Es ist ein unbekannter Fehler aufgetreten.") {
    throw e instanceof ze ? e : new ze({
      level: hr.WARNING,
      message: r
    });
  }
  static getPATCHConfig(e) {
    const r = _.getHeaders();
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
  ze as ApiError,
  vt as Configuration,
  _ as FetchUtils,
  hr as Levels,
  kt as TaskApi,
  ft as TaskApiAxiosParamCreator,
  Tt as TaskApiFactory,
  A as TaskApiFp,
  gt as TasksApi,
  pt as TasksApiAxiosParamCreator,
  yt as TasksApiFactory,
  D as TasksApiFp,
  Ot as UserApi,
  mt as UserApiAxiosParamCreator,
  bt as UserApiFactory,
  dr as UserApiFp
};
