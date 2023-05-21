import { VCard as Jn, VCardTitle as Kn, VIcon as rt, VCardText as Wn, VImg as Gn, VBtn as pn, VFileInput as Xn, VTooltip as Zn } from "vuetify/lib";
import { defineComponent as cn, computed as ee, ref as fe, inject as Ut, onMounted as Qn } from "vue";
const Yn = {
  "image/jpeg": "mdi-image",
  "image/png": "mdi-image",
  "image/gif": "mdi-image",
  "image/bmp": "mdi-image",
  "application/pdf": "mdi-file-pdf",
  "application/msword": "mdi-file-word",
  "application/vnd.openxmlformats-officedocument.wordprocessingml.document": "mdi-file-word",
  "application/vnd.oasis.opendocument.text": "mdi-file-word",
  "application/vnd.ms-powerpoint": "mdi-file-powerpoint",
  "application/vnd.openxmlformats-officedocument.presentationml.presentation": "mdi-file-powerpoint",
  "application/vnd.oasis.opendocument.presentation": "mdi-file-powerpoint",
  "application/vnd.ms-excel": "mdi-file-excel",
  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": "mdi-file-excel",
  "application/vnd.oasis.opendocument.spreadsheet": "mdi-file-excel",
  "application/xml": "mdi-file-code",
  "application/json": "mdi-file-code",
  "application/xhtml+xml": "mdi-file-code",
  "text/html": "mdi-file-code",
  "text/xml": "mdi-file-code"
}, ei = cn({
  props: ["document", "readonly"],
  emits: ["remove-document"],
  setup(e, { emit: t }) {
    const n = ee(() => atob(e.document.data.substr(`data:${e.document.type};base64,`.length))), i = ee(() => {
      var p;
      return (p = Yn[e.document.type]) != null ? p : "mdi-file";
    }), a = ee(() => e.document.type.toLowerCase() === "image/jpeg" || e.document.type.toLowerCase() === "image/png"), o = () => {
      const p = n.value, d = [];
      for (let h = 0; h < p.length; h += 1024) {
        const m = p.slice(h, h + 1024), v = new Array(m.length);
        for (let k = 0; k < m.length; k++)
          v[k] = m.charCodeAt(k);
        const E = new Uint8Array(v);
        d.push(E);
      }
      const c = new Blob(d, { type: e.document.type });
      return URL.createObjectURL(c);
    };
    return {
      calcByteCharacters: n,
      icon: i,
      isImage: a,
      openInTab: () => {
        const p = o(), d = document.createElement("a");
        d.href = p, d.setAttribute("download", e.document.name), document.body.appendChild(d), d.click();
      },
      formatBytes: (p = 2) => {
        const c = p < 0 ? 0 : p, h = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"], m = Math.floor(Math.log(e.document.size) / Math.log(1024));
        return parseFloat((e.document.size / Math.pow(1024, m)).toFixed(c)) + " " + h[m];
      },
      removeDocument: () => {
        t("remove-document", e.document);
      }
    };
  }
});
function dn(e, t, n, i, a, o, r, s) {
  var l = typeof e == "function" ? e.options : e;
  t && (l.render = t, l.staticRenderFns = n, l._compiled = !0), i && (l.functional = !0), o && (l._scopeId = "data-v-" + o);
  var p;
  if (r ? (p = function(h) {
    h = h || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext, !h && typeof __VUE_SSR_CONTEXT__ < "u" && (h = __VUE_SSR_CONTEXT__), a && a.call(this, h), h && h._registeredComponents && h._registeredComponents.add(r);
  }, l._ssrRegister = p) : a && (p = s ? function() {
    a.call(
      this,
      (l.functional ? this.parent : this).$root.$options.shadowRoot
    );
  } : a), p)
    if (l.functional) {
      l._injectStyles = p;
      var d = l.render;
      l.render = function(m, v) {
        return p.call(v), d(m, v);
      };
    } else {
      var c = l.beforeCreate;
      l.beforeCreate = c ? [].concat(c, p) : [p];
    }
  return {
    exports: e,
    options: l
  };
}
var ti = function() {
  var t = this, n = t._self._c;
  return t._self._setupProxy, n("div", [n("a", { attrs: { target: "_blank" }, on: { click: function(i) {
    return t.openInTab();
  } } }, [n(Jn, { staticClass: "doc-card mb-2", attrs: { elevation: "2", outlined: "", "max-width": "350px" } }, [n(Kn, { staticClass: "text-subtitle-1 title" }, [n("div", { staticClass: "d-flex align-start flex-row" }, [n(rt, { staticClass: "mr-2", attrs: { left: "", size: "30" } }, [t._v(" " + t._s(t.icon) + " ")]), t._v(" " + t._s(t.document.name) + " ")], 1)]), n(Wn, [n("div", { staticClass: "preview" }, [t.isImage ? n(Gn, { staticClass: "preview-component", attrs: { src: t.document.data, "max-width": "200px", alt: "Image preview..." } }) : t.document.type === "application/pdf" ? n("vue2-pdf-embed", { staticClass: "preview-component", attrs: { source: t.document.data } }) : n("div", { staticClass: "preview-text" }, [t._v("Keine Vorschau verf\xFCgbar")]), n("div", [n("div", { staticClass: "footer" }, [t._v(t._s(t.formatBytes(0)))]), t.readonly ? t._e() : [n(pn, { staticClass: "remove-button ma-1", attrs: { elevation: "1", icon: "" }, on: { click: function(i) {
    return i.stopPropagation(), t.removeDocument.apply(null, arguments);
  } } }, [n(rt, [t._v(" mdi-delete")])], 1)]], 2)], 1)])], 1)], 1)]);
}, ni = [], ii = /* @__PURE__ */ dn(
  ei,
  ti,
  ni,
  !1,
  null,
  "9a5b4618",
  null,
  null
);
const ai = ii.exports;
function oi(e) {
  return e && e.__esModule && Object.prototype.hasOwnProperty.call(e, "default") ? e.default : e;
}
function je() {
  this._types = /* @__PURE__ */ Object.create(null), this._extensions = /* @__PURE__ */ Object.create(null);
  for (let e = 0; e < arguments.length; e++)
    this.define(arguments[e]);
  this.define = this.define.bind(this), this.getType = this.getType.bind(this), this.getExtension = this.getExtension.bind(this);
}
je.prototype.define = function(e, t) {
  for (let n in e) {
    let i = e[n].map(function(a) {
      return a.toLowerCase();
    });
    n = n.toLowerCase();
    for (let a = 0; a < i.length; a++) {
      const o = i[a];
      if (o[0] !== "*") {
        if (!t && o in this._types)
          throw new Error(
            'Attempt to change mapping for "' + o + '" extension from "' + this._types[o] + '" to "' + n + '". Pass `force=true` to allow this, otherwise remove "' + o + '" from the list of extensions for "' + n + '".'
          );
        this._types[o] = n;
      }
    }
    if (t || !this._extensions[n]) {
      const a = i[0];
      this._extensions[n] = a[0] !== "*" ? a : a.substr(1);
    }
  }
};
je.prototype.getType = function(e) {
  e = String(e);
  let t = e.replace(/^.*[/\\]/, "").toLowerCase(), n = t.replace(/^.*\./, "").toLowerCase(), i = t.length < e.length;
  return (n.length < t.length - 1 || !i) && this._types[n] || null;
};
je.prototype.getExtension = function(e) {
  return e = /^\s*([^;\s]*)/.test(e) && RegExp.$1, e && this._extensions[e.toLowerCase()] || null;
};
var ri = je, si = { "application/andrew-inset": ["ez"], "application/applixware": ["aw"], "application/atom+xml": ["atom"], "application/atomcat+xml": ["atomcat"], "application/atomdeleted+xml": ["atomdeleted"], "application/atomsvc+xml": ["atomsvc"], "application/atsc-dwd+xml": ["dwd"], "application/atsc-held+xml": ["held"], "application/atsc-rsat+xml": ["rsat"], "application/bdoc": ["bdoc"], "application/calendar+xml": ["xcs"], "application/ccxml+xml": ["ccxml"], "application/cdfx+xml": ["cdfx"], "application/cdmi-capability": ["cdmia"], "application/cdmi-container": ["cdmic"], "application/cdmi-domain": ["cdmid"], "application/cdmi-object": ["cdmio"], "application/cdmi-queue": ["cdmiq"], "application/cu-seeme": ["cu"], "application/dash+xml": ["mpd"], "application/davmount+xml": ["davmount"], "application/docbook+xml": ["dbk"], "application/dssc+der": ["dssc"], "application/dssc+xml": ["xdssc"], "application/ecmascript": ["es", "ecma"], "application/emma+xml": ["emma"], "application/emotionml+xml": ["emotionml"], "application/epub+zip": ["epub"], "application/exi": ["exi"], "application/express": ["exp"], "application/fdt+xml": ["fdt"], "application/font-tdpfr": ["pfr"], "application/geo+json": ["geojson"], "application/gml+xml": ["gml"], "application/gpx+xml": ["gpx"], "application/gxf": ["gxf"], "application/gzip": ["gz"], "application/hjson": ["hjson"], "application/hyperstudio": ["stk"], "application/inkml+xml": ["ink", "inkml"], "application/ipfix": ["ipfix"], "application/its+xml": ["its"], "application/java-archive": ["jar", "war", "ear"], "application/java-serialized-object": ["ser"], "application/java-vm": ["class"], "application/javascript": ["js", "mjs"], "application/json": ["json", "map"], "application/json5": ["json5"], "application/jsonml+json": ["jsonml"], "application/ld+json": ["jsonld"], "application/lgr+xml": ["lgr"], "application/lost+xml": ["lostxml"], "application/mac-binhex40": ["hqx"], "application/mac-compactpro": ["cpt"], "application/mads+xml": ["mads"], "application/manifest+json": ["webmanifest"], "application/marc": ["mrc"], "application/marcxml+xml": ["mrcx"], "application/mathematica": ["ma", "nb", "mb"], "application/mathml+xml": ["mathml"], "application/mbox": ["mbox"], "application/mediaservercontrol+xml": ["mscml"], "application/metalink+xml": ["metalink"], "application/metalink4+xml": ["meta4"], "application/mets+xml": ["mets"], "application/mmt-aei+xml": ["maei"], "application/mmt-usd+xml": ["musd"], "application/mods+xml": ["mods"], "application/mp21": ["m21", "mp21"], "application/mp4": ["mp4s", "m4p"], "application/msword": ["doc", "dot"], "application/mxf": ["mxf"], "application/n-quads": ["nq"], "application/n-triples": ["nt"], "application/node": ["cjs"], "application/octet-stream": ["bin", "dms", "lrf", "mar", "so", "dist", "distz", "pkg", "bpk", "dump", "elc", "deploy", "exe", "dll", "deb", "dmg", "iso", "img", "msi", "msp", "msm", "buffer"], "application/oda": ["oda"], "application/oebps-package+xml": ["opf"], "application/ogg": ["ogx"], "application/omdoc+xml": ["omdoc"], "application/onenote": ["onetoc", "onetoc2", "onetmp", "onepkg"], "application/oxps": ["oxps"], "application/p2p-overlay+xml": ["relo"], "application/patch-ops-error+xml": ["xer"], "application/pdf": ["pdf"], "application/pgp-encrypted": ["pgp"], "application/pgp-signature": ["asc", "sig"], "application/pics-rules": ["prf"], "application/pkcs10": ["p10"], "application/pkcs7-mime": ["p7m", "p7c"], "application/pkcs7-signature": ["p7s"], "application/pkcs8": ["p8"], "application/pkix-attr-cert": ["ac"], "application/pkix-cert": ["cer"], "application/pkix-crl": ["crl"], "application/pkix-pkipath": ["pkipath"], "application/pkixcmp": ["pki"], "application/pls+xml": ["pls"], "application/postscript": ["ai", "eps", "ps"], "application/provenance+xml": ["provx"], "application/pskc+xml": ["pskcxml"], "application/raml+yaml": ["raml"], "application/rdf+xml": ["rdf", "owl"], "application/reginfo+xml": ["rif"], "application/relax-ng-compact-syntax": ["rnc"], "application/resource-lists+xml": ["rl"], "application/resource-lists-diff+xml": ["rld"], "application/rls-services+xml": ["rs"], "application/route-apd+xml": ["rapd"], "application/route-s-tsid+xml": ["sls"], "application/route-usd+xml": ["rusd"], "application/rpki-ghostbusters": ["gbr"], "application/rpki-manifest": ["mft"], "application/rpki-roa": ["roa"], "application/rsd+xml": ["rsd"], "application/rss+xml": ["rss"], "application/rtf": ["rtf"], "application/sbml+xml": ["sbml"], "application/scvp-cv-request": ["scq"], "application/scvp-cv-response": ["scs"], "application/scvp-vp-request": ["spq"], "application/scvp-vp-response": ["spp"], "application/sdp": ["sdp"], "application/senml+xml": ["senmlx"], "application/sensml+xml": ["sensmlx"], "application/set-payment-initiation": ["setpay"], "application/set-registration-initiation": ["setreg"], "application/shf+xml": ["shf"], "application/sieve": ["siv", "sieve"], "application/smil+xml": ["smi", "smil"], "application/sparql-query": ["rq"], "application/sparql-results+xml": ["srx"], "application/srgs": ["gram"], "application/srgs+xml": ["grxml"], "application/sru+xml": ["sru"], "application/ssdl+xml": ["ssdl"], "application/ssml+xml": ["ssml"], "application/swid+xml": ["swidtag"], "application/tei+xml": ["tei", "teicorpus"], "application/thraud+xml": ["tfi"], "application/timestamped-data": ["tsd"], "application/toml": ["toml"], "application/trig": ["trig"], "application/ttml+xml": ["ttml"], "application/ubjson": ["ubj"], "application/urc-ressheet+xml": ["rsheet"], "application/urc-targetdesc+xml": ["td"], "application/voicexml+xml": ["vxml"], "application/wasm": ["wasm"], "application/widget": ["wgt"], "application/winhlp": ["hlp"], "application/wsdl+xml": ["wsdl"], "application/wspolicy+xml": ["wspolicy"], "application/xaml+xml": ["xaml"], "application/xcap-att+xml": ["xav"], "application/xcap-caps+xml": ["xca"], "application/xcap-diff+xml": ["xdf"], "application/xcap-el+xml": ["xel"], "application/xcap-ns+xml": ["xns"], "application/xenc+xml": ["xenc"], "application/xhtml+xml": ["xhtml", "xht"], "application/xliff+xml": ["xlf"], "application/xml": ["xml", "xsl", "xsd", "rng"], "application/xml-dtd": ["dtd"], "application/xop+xml": ["xop"], "application/xproc+xml": ["xpl"], "application/xslt+xml": ["*xsl", "xslt"], "application/xspf+xml": ["xspf"], "application/xv+xml": ["mxml", "xhvml", "xvml", "xvm"], "application/yang": ["yang"], "application/yin+xml": ["yin"], "application/zip": ["zip"], "audio/3gpp": ["*3gpp"], "audio/adpcm": ["adp"], "audio/amr": ["amr"], "audio/basic": ["au", "snd"], "audio/midi": ["mid", "midi", "kar", "rmi"], "audio/mobile-xmf": ["mxmf"], "audio/mp3": ["*mp3"], "audio/mp4": ["m4a", "mp4a"], "audio/mpeg": ["mpga", "mp2", "mp2a", "mp3", "m2a", "m3a"], "audio/ogg": ["oga", "ogg", "spx", "opus"], "audio/s3m": ["s3m"], "audio/silk": ["sil"], "audio/wav": ["wav"], "audio/wave": ["*wav"], "audio/webm": ["weba"], "audio/xm": ["xm"], "font/collection": ["ttc"], "font/otf": ["otf"], "font/ttf": ["ttf"], "font/woff": ["woff"], "font/woff2": ["woff2"], "image/aces": ["exr"], "image/apng": ["apng"], "image/avif": ["avif"], "image/bmp": ["bmp"], "image/cgm": ["cgm"], "image/dicom-rle": ["drle"], "image/emf": ["emf"], "image/fits": ["fits"], "image/g3fax": ["g3"], "image/gif": ["gif"], "image/heic": ["heic"], "image/heic-sequence": ["heics"], "image/heif": ["heif"], "image/heif-sequence": ["heifs"], "image/hej2k": ["hej2"], "image/hsj2": ["hsj2"], "image/ief": ["ief"], "image/jls": ["jls"], "image/jp2": ["jp2", "jpg2"], "image/jpeg": ["jpeg", "jpg", "jpe"], "image/jph": ["jph"], "image/jphc": ["jhc"], "image/jpm": ["jpm"], "image/jpx": ["jpx", "jpf"], "image/jxr": ["jxr"], "image/jxra": ["jxra"], "image/jxrs": ["jxrs"], "image/jxs": ["jxs"], "image/jxsc": ["jxsc"], "image/jxsi": ["jxsi"], "image/jxss": ["jxss"], "image/ktx": ["ktx"], "image/ktx2": ["ktx2"], "image/png": ["png"], "image/sgi": ["sgi"], "image/svg+xml": ["svg", "svgz"], "image/t38": ["t38"], "image/tiff": ["tif", "tiff"], "image/tiff-fx": ["tfx"], "image/webp": ["webp"], "image/wmf": ["wmf"], "message/disposition-notification": ["disposition-notification"], "message/global": ["u8msg"], "message/global-delivery-status": ["u8dsn"], "message/global-disposition-notification": ["u8mdn"], "message/global-headers": ["u8hdr"], "message/rfc822": ["eml", "mime"], "model/3mf": ["3mf"], "model/gltf+json": ["gltf"], "model/gltf-binary": ["glb"], "model/iges": ["igs", "iges"], "model/mesh": ["msh", "mesh", "silo"], "model/mtl": ["mtl"], "model/obj": ["obj"], "model/step+xml": ["stpx"], "model/step+zip": ["stpz"], "model/step-xml+zip": ["stpxz"], "model/stl": ["stl"], "model/vrml": ["wrl", "vrml"], "model/x3d+binary": ["*x3db", "x3dbz"], "model/x3d+fastinfoset": ["x3db"], "model/x3d+vrml": ["*x3dv", "x3dvz"], "model/x3d+xml": ["x3d", "x3dz"], "model/x3d-vrml": ["x3dv"], "text/cache-manifest": ["appcache", "manifest"], "text/calendar": ["ics", "ifb"], "text/coffeescript": ["coffee", "litcoffee"], "text/css": ["css"], "text/csv": ["csv"], "text/html": ["html", "htm", "shtml"], "text/jade": ["jade"], "text/jsx": ["jsx"], "text/less": ["less"], "text/markdown": ["markdown", "md"], "text/mathml": ["mml"], "text/mdx": ["mdx"], "text/n3": ["n3"], "text/plain": ["txt", "text", "conf", "def", "list", "log", "in", "ini"], "text/richtext": ["rtx"], "text/rtf": ["*rtf"], "text/sgml": ["sgml", "sgm"], "text/shex": ["shex"], "text/slim": ["slim", "slm"], "text/spdx": ["spdx"], "text/stylus": ["stylus", "styl"], "text/tab-separated-values": ["tsv"], "text/troff": ["t", "tr", "roff", "man", "me", "ms"], "text/turtle": ["ttl"], "text/uri-list": ["uri", "uris", "urls"], "text/vcard": ["vcard"], "text/vtt": ["vtt"], "text/xml": ["*xml"], "text/yaml": ["yaml", "yml"], "video/3gpp": ["3gp", "3gpp"], "video/3gpp2": ["3g2"], "video/h261": ["h261"], "video/h263": ["h263"], "video/h264": ["h264"], "video/iso.segment": ["m4s"], "video/jpeg": ["jpgv"], "video/jpm": ["*jpm", "jpgm"], "video/mj2": ["mj2", "mjp2"], "video/mp2t": ["ts"], "video/mp4": ["mp4", "mp4v", "mpg4"], "video/mpeg": ["mpeg", "mpg", "mpe", "m1v", "m2v"], "video/ogg": ["ogv"], "video/quicktime": ["qt", "mov"], "video/webm": ["webm"] }, li = { "application/prs.cww": ["cww"], "application/vnd.1000minds.decision-model+xml": ["1km"], "application/vnd.3gpp.pic-bw-large": ["plb"], "application/vnd.3gpp.pic-bw-small": ["psb"], "application/vnd.3gpp.pic-bw-var": ["pvb"], "application/vnd.3gpp2.tcap": ["tcap"], "application/vnd.3m.post-it-notes": ["pwn"], "application/vnd.accpac.simply.aso": ["aso"], "application/vnd.accpac.simply.imp": ["imp"], "application/vnd.acucobol": ["acu"], "application/vnd.acucorp": ["atc", "acutc"], "application/vnd.adobe.air-application-installer-package+zip": ["air"], "application/vnd.adobe.formscentral.fcdt": ["fcdt"], "application/vnd.adobe.fxp": ["fxp", "fxpl"], "application/vnd.adobe.xdp+xml": ["xdp"], "application/vnd.adobe.xfdf": ["xfdf"], "application/vnd.ahead.space": ["ahead"], "application/vnd.airzip.filesecure.azf": ["azf"], "application/vnd.airzip.filesecure.azs": ["azs"], "application/vnd.amazon.ebook": ["azw"], "application/vnd.americandynamics.acc": ["acc"], "application/vnd.amiga.ami": ["ami"], "application/vnd.android.package-archive": ["apk"], "application/vnd.anser-web-certificate-issue-initiation": ["cii"], "application/vnd.anser-web-funds-transfer-initiation": ["fti"], "application/vnd.antix.game-component": ["atx"], "application/vnd.apple.installer+xml": ["mpkg"], "application/vnd.apple.keynote": ["key"], "application/vnd.apple.mpegurl": ["m3u8"], "application/vnd.apple.numbers": ["numbers"], "application/vnd.apple.pages": ["pages"], "application/vnd.apple.pkpass": ["pkpass"], "application/vnd.aristanetworks.swi": ["swi"], "application/vnd.astraea-software.iota": ["iota"], "application/vnd.audiograph": ["aep"], "application/vnd.balsamiq.bmml+xml": ["bmml"], "application/vnd.blueice.multipass": ["mpm"], "application/vnd.bmi": ["bmi"], "application/vnd.businessobjects": ["rep"], "application/vnd.chemdraw+xml": ["cdxml"], "application/vnd.chipnuts.karaoke-mmd": ["mmd"], "application/vnd.cinderella": ["cdy"], "application/vnd.citationstyles.style+xml": ["csl"], "application/vnd.claymore": ["cla"], "application/vnd.cloanto.rp9": ["rp9"], "application/vnd.clonk.c4group": ["c4g", "c4d", "c4f", "c4p", "c4u"], "application/vnd.cluetrust.cartomobile-config": ["c11amc"], "application/vnd.cluetrust.cartomobile-config-pkg": ["c11amz"], "application/vnd.commonspace": ["csp"], "application/vnd.contact.cmsg": ["cdbcmsg"], "application/vnd.cosmocaller": ["cmc"], "application/vnd.crick.clicker": ["clkx"], "application/vnd.crick.clicker.keyboard": ["clkk"], "application/vnd.crick.clicker.palette": ["clkp"], "application/vnd.crick.clicker.template": ["clkt"], "application/vnd.crick.clicker.wordbank": ["clkw"], "application/vnd.criticaltools.wbs+xml": ["wbs"], "application/vnd.ctc-posml": ["pml"], "application/vnd.cups-ppd": ["ppd"], "application/vnd.curl.car": ["car"], "application/vnd.curl.pcurl": ["pcurl"], "application/vnd.dart": ["dart"], "application/vnd.data-vision.rdz": ["rdz"], "application/vnd.dbf": ["dbf"], "application/vnd.dece.data": ["uvf", "uvvf", "uvd", "uvvd"], "application/vnd.dece.ttml+xml": ["uvt", "uvvt"], "application/vnd.dece.unspecified": ["uvx", "uvvx"], "application/vnd.dece.zip": ["uvz", "uvvz"], "application/vnd.denovo.fcselayout-link": ["fe_launch"], "application/vnd.dna": ["dna"], "application/vnd.dolby.mlp": ["mlp"], "application/vnd.dpgraph": ["dpg"], "application/vnd.dreamfactory": ["dfac"], "application/vnd.ds-keypoint": ["kpxx"], "application/vnd.dvb.ait": ["ait"], "application/vnd.dvb.service": ["svc"], "application/vnd.dynageo": ["geo"], "application/vnd.ecowin.chart": ["mag"], "application/vnd.enliven": ["nml"], "application/vnd.epson.esf": ["esf"], "application/vnd.epson.msf": ["msf"], "application/vnd.epson.quickanime": ["qam"], "application/vnd.epson.salt": ["slt"], "application/vnd.epson.ssf": ["ssf"], "application/vnd.eszigno3+xml": ["es3", "et3"], "application/vnd.ezpix-album": ["ez2"], "application/vnd.ezpix-package": ["ez3"], "application/vnd.fdf": ["fdf"], "application/vnd.fdsn.mseed": ["mseed"], "application/vnd.fdsn.seed": ["seed", "dataless"], "application/vnd.flographit": ["gph"], "application/vnd.fluxtime.clip": ["ftc"], "application/vnd.framemaker": ["fm", "frame", "maker", "book"], "application/vnd.frogans.fnc": ["fnc"], "application/vnd.frogans.ltf": ["ltf"], "application/vnd.fsc.weblaunch": ["fsc"], "application/vnd.fujitsu.oasys": ["oas"], "application/vnd.fujitsu.oasys2": ["oa2"], "application/vnd.fujitsu.oasys3": ["oa3"], "application/vnd.fujitsu.oasysgp": ["fg5"], "application/vnd.fujitsu.oasysprs": ["bh2"], "application/vnd.fujixerox.ddd": ["ddd"], "application/vnd.fujixerox.docuworks": ["xdw"], "application/vnd.fujixerox.docuworks.binder": ["xbd"], "application/vnd.fuzzysheet": ["fzs"], "application/vnd.genomatix.tuxedo": ["txd"], "application/vnd.geogebra.file": ["ggb"], "application/vnd.geogebra.tool": ["ggt"], "application/vnd.geometry-explorer": ["gex", "gre"], "application/vnd.geonext": ["gxt"], "application/vnd.geoplan": ["g2w"], "application/vnd.geospace": ["g3w"], "application/vnd.gmx": ["gmx"], "application/vnd.google-apps.document": ["gdoc"], "application/vnd.google-apps.presentation": ["gslides"], "application/vnd.google-apps.spreadsheet": ["gsheet"], "application/vnd.google-earth.kml+xml": ["kml"], "application/vnd.google-earth.kmz": ["kmz"], "application/vnd.grafeq": ["gqf", "gqs"], "application/vnd.groove-account": ["gac"], "application/vnd.groove-help": ["ghf"], "application/vnd.groove-identity-message": ["gim"], "application/vnd.groove-injector": ["grv"], "application/vnd.groove-tool-message": ["gtm"], "application/vnd.groove-tool-template": ["tpl"], "application/vnd.groove-vcard": ["vcg"], "application/vnd.hal+xml": ["hal"], "application/vnd.handheld-entertainment+xml": ["zmm"], "application/vnd.hbci": ["hbci"], "application/vnd.hhe.lesson-player": ["les"], "application/vnd.hp-hpgl": ["hpgl"], "application/vnd.hp-hpid": ["hpid"], "application/vnd.hp-hps": ["hps"], "application/vnd.hp-jlyt": ["jlt"], "application/vnd.hp-pcl": ["pcl"], "application/vnd.hp-pclxl": ["pclxl"], "application/vnd.hydrostatix.sof-data": ["sfd-hdstx"], "application/vnd.ibm.minipay": ["mpy"], "application/vnd.ibm.modcap": ["afp", "listafp", "list3820"], "application/vnd.ibm.rights-management": ["irm"], "application/vnd.ibm.secure-container": ["sc"], "application/vnd.iccprofile": ["icc", "icm"], "application/vnd.igloader": ["igl"], "application/vnd.immervision-ivp": ["ivp"], "application/vnd.immervision-ivu": ["ivu"], "application/vnd.insors.igm": ["igm"], "application/vnd.intercon.formnet": ["xpw", "xpx"], "application/vnd.intergeo": ["i2g"], "application/vnd.intu.qbo": ["qbo"], "application/vnd.intu.qfx": ["qfx"], "application/vnd.ipunplugged.rcprofile": ["rcprofile"], "application/vnd.irepository.package+xml": ["irp"], "application/vnd.is-xpr": ["xpr"], "application/vnd.isac.fcs": ["fcs"], "application/vnd.jam": ["jam"], "application/vnd.jcp.javame.midlet-rms": ["rms"], "application/vnd.jisp": ["jisp"], "application/vnd.joost.joda-archive": ["joda"], "application/vnd.kahootz": ["ktz", "ktr"], "application/vnd.kde.karbon": ["karbon"], "application/vnd.kde.kchart": ["chrt"], "application/vnd.kde.kformula": ["kfo"], "application/vnd.kde.kivio": ["flw"], "application/vnd.kde.kontour": ["kon"], "application/vnd.kde.kpresenter": ["kpr", "kpt"], "application/vnd.kde.kspread": ["ksp"], "application/vnd.kde.kword": ["kwd", "kwt"], "application/vnd.kenameaapp": ["htke"], "application/vnd.kidspiration": ["kia"], "application/vnd.kinar": ["kne", "knp"], "application/vnd.koan": ["skp", "skd", "skt", "skm"], "application/vnd.kodak-descriptor": ["sse"], "application/vnd.las.las+xml": ["lasxml"], "application/vnd.llamagraphics.life-balance.desktop": ["lbd"], "application/vnd.llamagraphics.life-balance.exchange+xml": ["lbe"], "application/vnd.lotus-1-2-3": ["123"], "application/vnd.lotus-approach": ["apr"], "application/vnd.lotus-freelance": ["pre"], "application/vnd.lotus-notes": ["nsf"], "application/vnd.lotus-organizer": ["org"], "application/vnd.lotus-screencam": ["scm"], "application/vnd.lotus-wordpro": ["lwp"], "application/vnd.macports.portpkg": ["portpkg"], "application/vnd.mapbox-vector-tile": ["mvt"], "application/vnd.mcd": ["mcd"], "application/vnd.medcalcdata": ["mc1"], "application/vnd.mediastation.cdkey": ["cdkey"], "application/vnd.mfer": ["mwf"], "application/vnd.mfmp": ["mfm"], "application/vnd.micrografx.flo": ["flo"], "application/vnd.micrografx.igx": ["igx"], "application/vnd.mif": ["mif"], "application/vnd.mobius.daf": ["daf"], "application/vnd.mobius.dis": ["dis"], "application/vnd.mobius.mbk": ["mbk"], "application/vnd.mobius.mqy": ["mqy"], "application/vnd.mobius.msl": ["msl"], "application/vnd.mobius.plc": ["plc"], "application/vnd.mobius.txf": ["txf"], "application/vnd.mophun.application": ["mpn"], "application/vnd.mophun.certificate": ["mpc"], "application/vnd.mozilla.xul+xml": ["xul"], "application/vnd.ms-artgalry": ["cil"], "application/vnd.ms-cab-compressed": ["cab"], "application/vnd.ms-excel": ["xls", "xlm", "xla", "xlc", "xlt", "xlw"], "application/vnd.ms-excel.addin.macroenabled.12": ["xlam"], "application/vnd.ms-excel.sheet.binary.macroenabled.12": ["xlsb"], "application/vnd.ms-excel.sheet.macroenabled.12": ["xlsm"], "application/vnd.ms-excel.template.macroenabled.12": ["xltm"], "application/vnd.ms-fontobject": ["eot"], "application/vnd.ms-htmlhelp": ["chm"], "application/vnd.ms-ims": ["ims"], "application/vnd.ms-lrm": ["lrm"], "application/vnd.ms-officetheme": ["thmx"], "application/vnd.ms-outlook": ["msg"], "application/vnd.ms-pki.seccat": ["cat"], "application/vnd.ms-pki.stl": ["*stl"], "application/vnd.ms-powerpoint": ["ppt", "pps", "pot"], "application/vnd.ms-powerpoint.addin.macroenabled.12": ["ppam"], "application/vnd.ms-powerpoint.presentation.macroenabled.12": ["pptm"], "application/vnd.ms-powerpoint.slide.macroenabled.12": ["sldm"], "application/vnd.ms-powerpoint.slideshow.macroenabled.12": ["ppsm"], "application/vnd.ms-powerpoint.template.macroenabled.12": ["potm"], "application/vnd.ms-project": ["mpp", "mpt"], "application/vnd.ms-word.document.macroenabled.12": ["docm"], "application/vnd.ms-word.template.macroenabled.12": ["dotm"], "application/vnd.ms-works": ["wps", "wks", "wcm", "wdb"], "application/vnd.ms-wpl": ["wpl"], "application/vnd.ms-xpsdocument": ["xps"], "application/vnd.mseq": ["mseq"], "application/vnd.musician": ["mus"], "application/vnd.muvee.style": ["msty"], "application/vnd.mynfc": ["taglet"], "application/vnd.neurolanguage.nlu": ["nlu"], "application/vnd.nitf": ["ntf", "nitf"], "application/vnd.noblenet-directory": ["nnd"], "application/vnd.noblenet-sealer": ["nns"], "application/vnd.noblenet-web": ["nnw"], "application/vnd.nokia.n-gage.ac+xml": ["*ac"], "application/vnd.nokia.n-gage.data": ["ngdat"], "application/vnd.nokia.n-gage.symbian.install": ["n-gage"], "application/vnd.nokia.radio-preset": ["rpst"], "application/vnd.nokia.radio-presets": ["rpss"], "application/vnd.novadigm.edm": ["edm"], "application/vnd.novadigm.edx": ["edx"], "application/vnd.novadigm.ext": ["ext"], "application/vnd.oasis.opendocument.chart": ["odc"], "application/vnd.oasis.opendocument.chart-template": ["otc"], "application/vnd.oasis.opendocument.database": ["odb"], "application/vnd.oasis.opendocument.formula": ["odf"], "application/vnd.oasis.opendocument.formula-template": ["odft"], "application/vnd.oasis.opendocument.graphics": ["odg"], "application/vnd.oasis.opendocument.graphics-template": ["otg"], "application/vnd.oasis.opendocument.image": ["odi"], "application/vnd.oasis.opendocument.image-template": ["oti"], "application/vnd.oasis.opendocument.presentation": ["odp"], "application/vnd.oasis.opendocument.presentation-template": ["otp"], "application/vnd.oasis.opendocument.spreadsheet": ["ods"], "application/vnd.oasis.opendocument.spreadsheet-template": ["ots"], "application/vnd.oasis.opendocument.text": ["odt"], "application/vnd.oasis.opendocument.text-master": ["odm"], "application/vnd.oasis.opendocument.text-template": ["ott"], "application/vnd.oasis.opendocument.text-web": ["oth"], "application/vnd.olpc-sugar": ["xo"], "application/vnd.oma.dd2+xml": ["dd2"], "application/vnd.openblox.game+xml": ["obgx"], "application/vnd.openofficeorg.extension": ["oxt"], "application/vnd.openstreetmap.data+xml": ["osm"], "application/vnd.openxmlformats-officedocument.presentationml.presentation": ["pptx"], "application/vnd.openxmlformats-officedocument.presentationml.slide": ["sldx"], "application/vnd.openxmlformats-officedocument.presentationml.slideshow": ["ppsx"], "application/vnd.openxmlformats-officedocument.presentationml.template": ["potx"], "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": ["xlsx"], "application/vnd.openxmlformats-officedocument.spreadsheetml.template": ["xltx"], "application/vnd.openxmlformats-officedocument.wordprocessingml.document": ["docx"], "application/vnd.openxmlformats-officedocument.wordprocessingml.template": ["dotx"], "application/vnd.osgeo.mapguide.package": ["mgp"], "application/vnd.osgi.dp": ["dp"], "application/vnd.osgi.subsystem": ["esa"], "application/vnd.palm": ["pdb", "pqa", "oprc"], "application/vnd.pawaafile": ["paw"], "application/vnd.pg.format": ["str"], "application/vnd.pg.osasli": ["ei6"], "application/vnd.picsel": ["efif"], "application/vnd.pmi.widget": ["wg"], "application/vnd.pocketlearn": ["plf"], "application/vnd.powerbuilder6": ["pbd"], "application/vnd.previewsystems.box": ["box"], "application/vnd.proteus.magazine": ["mgz"], "application/vnd.publishare-delta-tree": ["qps"], "application/vnd.pvi.ptid1": ["ptid"], "application/vnd.quark.quarkxpress": ["qxd", "qxt", "qwd", "qwt", "qxl", "qxb"], "application/vnd.rar": ["rar"], "application/vnd.realvnc.bed": ["bed"], "application/vnd.recordare.musicxml": ["mxl"], "application/vnd.recordare.musicxml+xml": ["musicxml"], "application/vnd.rig.cryptonote": ["cryptonote"], "application/vnd.rim.cod": ["cod"], "application/vnd.rn-realmedia": ["rm"], "application/vnd.rn-realmedia-vbr": ["rmvb"], "application/vnd.route66.link66+xml": ["link66"], "application/vnd.sailingtracker.track": ["st"], "application/vnd.seemail": ["see"], "application/vnd.sema": ["sema"], "application/vnd.semd": ["semd"], "application/vnd.semf": ["semf"], "application/vnd.shana.informed.formdata": ["ifm"], "application/vnd.shana.informed.formtemplate": ["itp"], "application/vnd.shana.informed.interchange": ["iif"], "application/vnd.shana.informed.package": ["ipk"], "application/vnd.simtech-mindmapper": ["twd", "twds"], "application/vnd.smaf": ["mmf"], "application/vnd.smart.teacher": ["teacher"], "application/vnd.software602.filler.form+xml": ["fo"], "application/vnd.solent.sdkm+xml": ["sdkm", "sdkd"], "application/vnd.spotfire.dxp": ["dxp"], "application/vnd.spotfire.sfs": ["sfs"], "application/vnd.stardivision.calc": ["sdc"], "application/vnd.stardivision.draw": ["sda"], "application/vnd.stardivision.impress": ["sdd"], "application/vnd.stardivision.math": ["smf"], "application/vnd.stardivision.writer": ["sdw", "vor"], "application/vnd.stardivision.writer-global": ["sgl"], "application/vnd.stepmania.package": ["smzip"], "application/vnd.stepmania.stepchart": ["sm"], "application/vnd.sun.wadl+xml": ["wadl"], "application/vnd.sun.xml.calc": ["sxc"], "application/vnd.sun.xml.calc.template": ["stc"], "application/vnd.sun.xml.draw": ["sxd"], "application/vnd.sun.xml.draw.template": ["std"], "application/vnd.sun.xml.impress": ["sxi"], "application/vnd.sun.xml.impress.template": ["sti"], "application/vnd.sun.xml.math": ["sxm"], "application/vnd.sun.xml.writer": ["sxw"], "application/vnd.sun.xml.writer.global": ["sxg"], "application/vnd.sun.xml.writer.template": ["stw"], "application/vnd.sus-calendar": ["sus", "susp"], "application/vnd.svd": ["svd"], "application/vnd.symbian.install": ["sis", "sisx"], "application/vnd.syncml+xml": ["xsm"], "application/vnd.syncml.dm+wbxml": ["bdm"], "application/vnd.syncml.dm+xml": ["xdm"], "application/vnd.syncml.dmddf+xml": ["ddf"], "application/vnd.tao.intent-module-archive": ["tao"], "application/vnd.tcpdump.pcap": ["pcap", "cap", "dmp"], "application/vnd.tmobile-livetv": ["tmo"], "application/vnd.trid.tpt": ["tpt"], "application/vnd.triscape.mxs": ["mxs"], "application/vnd.trueapp": ["tra"], "application/vnd.ufdl": ["ufd", "ufdl"], "application/vnd.uiq.theme": ["utz"], "application/vnd.umajin": ["umj"], "application/vnd.unity": ["unityweb"], "application/vnd.uoml+xml": ["uoml"], "application/vnd.vcx": ["vcx"], "application/vnd.visio": ["vsd", "vst", "vss", "vsw"], "application/vnd.visionary": ["vis"], "application/vnd.vsf": ["vsf"], "application/vnd.wap.wbxml": ["wbxml"], "application/vnd.wap.wmlc": ["wmlc"], "application/vnd.wap.wmlscriptc": ["wmlsc"], "application/vnd.webturbo": ["wtb"], "application/vnd.wolfram.player": ["nbp"], "application/vnd.wordperfect": ["wpd"], "application/vnd.wqd": ["wqd"], "application/vnd.wt.stf": ["stf"], "application/vnd.xara": ["xar"], "application/vnd.xfdl": ["xfdl"], "application/vnd.yamaha.hv-dic": ["hvd"], "application/vnd.yamaha.hv-script": ["hvs"], "application/vnd.yamaha.hv-voice": ["hvp"], "application/vnd.yamaha.openscoreformat": ["osf"], "application/vnd.yamaha.openscoreformat.osfpvg+xml": ["osfpvg"], "application/vnd.yamaha.smaf-audio": ["saf"], "application/vnd.yamaha.smaf-phrase": ["spf"], "application/vnd.yellowriver-custom-menu": ["cmp"], "application/vnd.zul": ["zir", "zirz"], "application/vnd.zzazz.deck+xml": ["zaz"], "application/x-7z-compressed": ["7z"], "application/x-abiword": ["abw"], "application/x-ace-compressed": ["ace"], "application/x-apple-diskimage": ["*dmg"], "application/x-arj": ["arj"], "application/x-authorware-bin": ["aab", "x32", "u32", "vox"], "application/x-authorware-map": ["aam"], "application/x-authorware-seg": ["aas"], "application/x-bcpio": ["bcpio"], "application/x-bdoc": ["*bdoc"], "application/x-bittorrent": ["torrent"], "application/x-blorb": ["blb", "blorb"], "application/x-bzip": ["bz"], "application/x-bzip2": ["bz2", "boz"], "application/x-cbr": ["cbr", "cba", "cbt", "cbz", "cb7"], "application/x-cdlink": ["vcd"], "application/x-cfs-compressed": ["cfs"], "application/x-chat": ["chat"], "application/x-chess-pgn": ["pgn"], "application/x-chrome-extension": ["crx"], "application/x-cocoa": ["cco"], "application/x-conference": ["nsc"], "application/x-cpio": ["cpio"], "application/x-csh": ["csh"], "application/x-debian-package": ["*deb", "udeb"], "application/x-dgc-compressed": ["dgc"], "application/x-director": ["dir", "dcr", "dxr", "cst", "cct", "cxt", "w3d", "fgd", "swa"], "application/x-doom": ["wad"], "application/x-dtbncx+xml": ["ncx"], "application/x-dtbook+xml": ["dtb"], "application/x-dtbresource+xml": ["res"], "application/x-dvi": ["dvi"], "application/x-envoy": ["evy"], "application/x-eva": ["eva"], "application/x-font-bdf": ["bdf"], "application/x-font-ghostscript": ["gsf"], "application/x-font-linux-psf": ["psf"], "application/x-font-pcf": ["pcf"], "application/x-font-snf": ["snf"], "application/x-font-type1": ["pfa", "pfb", "pfm", "afm"], "application/x-freearc": ["arc"], "application/x-futuresplash": ["spl"], "application/x-gca-compressed": ["gca"], "application/x-glulx": ["ulx"], "application/x-gnumeric": ["gnumeric"], "application/x-gramps-xml": ["gramps"], "application/x-gtar": ["gtar"], "application/x-hdf": ["hdf"], "application/x-httpd-php": ["php"], "application/x-install-instructions": ["install"], "application/x-iso9660-image": ["*iso"], "application/x-iwork-keynote-sffkey": ["*key"], "application/x-iwork-numbers-sffnumbers": ["*numbers"], "application/x-iwork-pages-sffpages": ["*pages"], "application/x-java-archive-diff": ["jardiff"], "application/x-java-jnlp-file": ["jnlp"], "application/x-keepass2": ["kdbx"], "application/x-latex": ["latex"], "application/x-lua-bytecode": ["luac"], "application/x-lzh-compressed": ["lzh", "lha"], "application/x-makeself": ["run"], "application/x-mie": ["mie"], "application/x-mobipocket-ebook": ["prc", "mobi"], "application/x-ms-application": ["application"], "application/x-ms-shortcut": ["lnk"], "application/x-ms-wmd": ["wmd"], "application/x-ms-wmz": ["wmz"], "application/x-ms-xbap": ["xbap"], "application/x-msaccess": ["mdb"], "application/x-msbinder": ["obd"], "application/x-mscardfile": ["crd"], "application/x-msclip": ["clp"], "application/x-msdos-program": ["*exe"], "application/x-msdownload": ["*exe", "*dll", "com", "bat", "*msi"], "application/x-msmediaview": ["mvb", "m13", "m14"], "application/x-msmetafile": ["*wmf", "*wmz", "*emf", "emz"], "application/x-msmoney": ["mny"], "application/x-mspublisher": ["pub"], "application/x-msschedule": ["scd"], "application/x-msterminal": ["trm"], "application/x-mswrite": ["wri"], "application/x-netcdf": ["nc", "cdf"], "application/x-ns-proxy-autoconfig": ["pac"], "application/x-nzb": ["nzb"], "application/x-perl": ["pl", "pm"], "application/x-pilot": ["*prc", "*pdb"], "application/x-pkcs12": ["p12", "pfx"], "application/x-pkcs7-certificates": ["p7b", "spc"], "application/x-pkcs7-certreqresp": ["p7r"], "application/x-rar-compressed": ["*rar"], "application/x-redhat-package-manager": ["rpm"], "application/x-research-info-systems": ["ris"], "application/x-sea": ["sea"], "application/x-sh": ["sh"], "application/x-shar": ["shar"], "application/x-shockwave-flash": ["swf"], "application/x-silverlight-app": ["xap"], "application/x-sql": ["sql"], "application/x-stuffit": ["sit"], "application/x-stuffitx": ["sitx"], "application/x-subrip": ["srt"], "application/x-sv4cpio": ["sv4cpio"], "application/x-sv4crc": ["sv4crc"], "application/x-t3vm-image": ["t3"], "application/x-tads": ["gam"], "application/x-tar": ["tar"], "application/x-tcl": ["tcl", "tk"], "application/x-tex": ["tex"], "application/x-tex-tfm": ["tfm"], "application/x-texinfo": ["texinfo", "texi"], "application/x-tgif": ["*obj"], "application/x-ustar": ["ustar"], "application/x-virtualbox-hdd": ["hdd"], "application/x-virtualbox-ova": ["ova"], "application/x-virtualbox-ovf": ["ovf"], "application/x-virtualbox-vbox": ["vbox"], "application/x-virtualbox-vbox-extpack": ["vbox-extpack"], "application/x-virtualbox-vdi": ["vdi"], "application/x-virtualbox-vhd": ["vhd"], "application/x-virtualbox-vmdk": ["vmdk"], "application/x-wais-source": ["src"], "application/x-web-app-manifest+json": ["webapp"], "application/x-x509-ca-cert": ["der", "crt", "pem"], "application/x-xfig": ["fig"], "application/x-xliff+xml": ["*xlf"], "application/x-xpinstall": ["xpi"], "application/x-xz": ["xz"], "application/x-zmachine": ["z1", "z2", "z3", "z4", "z5", "z6", "z7", "z8"], "audio/vnd.dece.audio": ["uva", "uvva"], "audio/vnd.digital-winds": ["eol"], "audio/vnd.dra": ["dra"], "audio/vnd.dts": ["dts"], "audio/vnd.dts.hd": ["dtshd"], "audio/vnd.lucent.voice": ["lvp"], "audio/vnd.ms-playready.media.pya": ["pya"], "audio/vnd.nuera.ecelp4800": ["ecelp4800"], "audio/vnd.nuera.ecelp7470": ["ecelp7470"], "audio/vnd.nuera.ecelp9600": ["ecelp9600"], "audio/vnd.rip": ["rip"], "audio/x-aac": ["aac"], "audio/x-aiff": ["aif", "aiff", "aifc"], "audio/x-caf": ["caf"], "audio/x-flac": ["flac"], "audio/x-m4a": ["*m4a"], "audio/x-matroska": ["mka"], "audio/x-mpegurl": ["m3u"], "audio/x-ms-wax": ["wax"], "audio/x-ms-wma": ["wma"], "audio/x-pn-realaudio": ["ram", "ra"], "audio/x-pn-realaudio-plugin": ["rmp"], "audio/x-realaudio": ["*ra"], "audio/x-wav": ["*wav"], "chemical/x-cdx": ["cdx"], "chemical/x-cif": ["cif"], "chemical/x-cmdf": ["cmdf"], "chemical/x-cml": ["cml"], "chemical/x-csml": ["csml"], "chemical/x-xyz": ["xyz"], "image/prs.btif": ["btif"], "image/prs.pti": ["pti"], "image/vnd.adobe.photoshop": ["psd"], "image/vnd.airzip.accelerator.azv": ["azv"], "image/vnd.dece.graphic": ["uvi", "uvvi", "uvg", "uvvg"], "image/vnd.djvu": ["djvu", "djv"], "image/vnd.dvb.subtitle": ["*sub"], "image/vnd.dwg": ["dwg"], "image/vnd.dxf": ["dxf"], "image/vnd.fastbidsheet": ["fbs"], "image/vnd.fpx": ["fpx"], "image/vnd.fst": ["fst"], "image/vnd.fujixerox.edmics-mmr": ["mmr"], "image/vnd.fujixerox.edmics-rlc": ["rlc"], "image/vnd.microsoft.icon": ["ico"], "image/vnd.ms-dds": ["dds"], "image/vnd.ms-modi": ["mdi"], "image/vnd.ms-photo": ["wdp"], "image/vnd.net-fpx": ["npx"], "image/vnd.pco.b16": ["b16"], "image/vnd.tencent.tap": ["tap"], "image/vnd.valve.source.texture": ["vtf"], "image/vnd.wap.wbmp": ["wbmp"], "image/vnd.xiff": ["xif"], "image/vnd.zbrush.pcx": ["pcx"], "image/x-3ds": ["3ds"], "image/x-cmu-raster": ["ras"], "image/x-cmx": ["cmx"], "image/x-freehand": ["fh", "fhc", "fh4", "fh5", "fh7"], "image/x-icon": ["*ico"], "image/x-jng": ["jng"], "image/x-mrsid-image": ["sid"], "image/x-ms-bmp": ["*bmp"], "image/x-pcx": ["*pcx"], "image/x-pict": ["pic", "pct"], "image/x-portable-anymap": ["pnm"], "image/x-portable-bitmap": ["pbm"], "image/x-portable-graymap": ["pgm"], "image/x-portable-pixmap": ["ppm"], "image/x-rgb": ["rgb"], "image/x-tga": ["tga"], "image/x-xbitmap": ["xbm"], "image/x-xpixmap": ["xpm"], "image/x-xwindowdump": ["xwd"], "message/vnd.wfa.wsc": ["wsc"], "model/vnd.collada+xml": ["dae"], "model/vnd.dwf": ["dwf"], "model/vnd.gdl": ["gdl"], "model/vnd.gtw": ["gtw"], "model/vnd.mts": ["mts"], "model/vnd.opengex": ["ogex"], "model/vnd.parasolid.transmit.binary": ["x_b"], "model/vnd.parasolid.transmit.text": ["x_t"], "model/vnd.sap.vds": ["vds"], "model/vnd.usdz+zip": ["usdz"], "model/vnd.valve.source.compiled-map": ["bsp"], "model/vnd.vtu": ["vtu"], "text/prs.lines.tag": ["dsc"], "text/vnd.curl": ["curl"], "text/vnd.curl.dcurl": ["dcurl"], "text/vnd.curl.mcurl": ["mcurl"], "text/vnd.curl.scurl": ["scurl"], "text/vnd.dvb.subtitle": ["sub"], "text/vnd.fly": ["fly"], "text/vnd.fmi.flexstor": ["flx"], "text/vnd.graphviz": ["gv"], "text/vnd.in3d.3dml": ["3dml"], "text/vnd.in3d.spot": ["spot"], "text/vnd.sun.j2me.app-descriptor": ["jad"], "text/vnd.wap.wml": ["wml"], "text/vnd.wap.wmlscript": ["wmls"], "text/x-asm": ["s", "asm"], "text/x-c": ["c", "cc", "cxx", "cpp", "h", "hh", "dic"], "text/x-component": ["htc"], "text/x-fortran": ["f", "for", "f77", "f90"], "text/x-handlebars-template": ["hbs"], "text/x-java-source": ["java"], "text/x-lua": ["lua"], "text/x-markdown": ["mkd"], "text/x-nfo": ["nfo"], "text/x-opml": ["opml"], "text/x-org": ["*org"], "text/x-pascal": ["p", "pas"], "text/x-processing": ["pde"], "text/x-sass": ["sass"], "text/x-scss": ["scss"], "text/x-setext": ["etx"], "text/x-sfv": ["sfv"], "text/x-suse-ymp": ["ymp"], "text/x-uuencode": ["uu"], "text/x-vcalendar": ["vcs"], "text/x-vcard": ["vcf"], "video/vnd.dece.hd": ["uvh", "uvvh"], "video/vnd.dece.mobile": ["uvm", "uvvm"], "video/vnd.dece.pd": ["uvp", "uvvp"], "video/vnd.dece.sd": ["uvs", "uvvs"], "video/vnd.dece.video": ["uvv", "uvvv"], "video/vnd.dvb.file": ["dvb"], "video/vnd.fvt": ["fvt"], "video/vnd.mpegurl": ["mxu", "m4u"], "video/vnd.ms-playready.media.pyv": ["pyv"], "video/vnd.uvvu.mp4": ["uvu", "uvvu"], "video/vnd.vivo": ["viv"], "video/x-f4v": ["f4v"], "video/x-fli": ["fli"], "video/x-flv": ["flv"], "video/x-m4v": ["m4v"], "video/x-matroska": ["mkv", "mk3d", "mks"], "video/x-mng": ["mng"], "video/x-ms-asf": ["asf", "asx"], "video/x-ms-vob": ["vob"], "video/x-ms-wm": ["wm"], "video/x-ms-wmv": ["wmv"], "video/x-ms-wmx": ["wmx"], "video/x-ms-wvx": ["wvx"], "video/x-msvideo": ["avi"], "video/x-sgi-movie": ["movie"], "video/x-smv": ["smv"], "x-conference/x-cooltalk": ["ice"] };
let pi = ri;
var ci = new pi(si, li), un = { exports: {} }, ft = { exports: {} }, mn = function(t, n) {
  return function() {
    for (var a = new Array(arguments.length), o = 0; o < a.length; o++)
      a[o] = arguments[o];
    return t.apply(n, a);
  };
}, di = mn, G = Object.prototype.toString;
function vt(e) {
  return Array.isArray(e);
}
function st(e) {
  return typeof e > "u";
}
function ui(e) {
  return e !== null && !st(e) && e.constructor !== null && !st(e.constructor) && typeof e.constructor.isBuffer == "function" && e.constructor.isBuffer(e);
}
function fn(e) {
  return G.call(e) === "[object ArrayBuffer]";
}
function mi(e) {
  return G.call(e) === "[object FormData]";
}
function fi(e) {
  var t;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? t = ArrayBuffer.isView(e) : t = e && e.buffer && fn(e.buffer), t;
}
function vi(e) {
  return typeof e == "string";
}
function hi(e) {
  return typeof e == "number";
}
function vn(e) {
  return e !== null && typeof e == "object";
}
function we(e) {
  if (G.call(e) !== "[object Object]")
    return !1;
  var t = Object.getPrototypeOf(e);
  return t === null || t === Object.prototype;
}
function gi(e) {
  return G.call(e) === "[object Date]";
}
function xi(e) {
  return G.call(e) === "[object File]";
}
function bi(e) {
  return G.call(e) === "[object Blob]";
}
function hn(e) {
  return G.call(e) === "[object Function]";
}
function wi(e) {
  return vn(e) && hn(e.pipe);
}
function yi(e) {
  return G.call(e) === "[object URLSearchParams]";
}
function ki(e) {
  return e.trim ? e.trim() : e.replace(/^\s+|\s+$/g, "");
}
function Ei() {
  return typeof navigator < "u" && (navigator.product === "ReactNative" || navigator.product === "NativeScript" || navigator.product === "NS") ? !1 : typeof window < "u" && typeof document < "u";
}
function ht(e, t) {
  if (!(e === null || typeof e > "u"))
    if (typeof e != "object" && (e = [e]), vt(e))
      for (var n = 0, i = e.length; n < i; n++)
        t.call(null, e[n], n, e);
    else
      for (var a in e)
        Object.prototype.hasOwnProperty.call(e, a) && t.call(null, e[a], a, e);
}
function lt() {
  var e = {};
  function t(a, o) {
    we(e[o]) && we(a) ? e[o] = lt(e[o], a) : we(a) ? e[o] = lt({}, a) : vt(a) ? e[o] = a.slice() : e[o] = a;
  }
  for (var n = 0, i = arguments.length; n < i; n++)
    ht(arguments[n], t);
  return e;
}
function Fi(e, t, n) {
  return ht(t, function(a, o) {
    n && typeof a == "function" ? e[o] = di(a, n) : e[o] = a;
  }), e;
}
function Ri(e) {
  return e.charCodeAt(0) === 65279 && (e = e.slice(1)), e;
}
var j = {
  isArray: vt,
  isArrayBuffer: fn,
  isBuffer: ui,
  isFormData: mi,
  isArrayBufferView: fi,
  isString: vi,
  isNumber: hi,
  isObject: vn,
  isPlainObject: we,
  isUndefined: st,
  isDate: gi,
  isFile: xi,
  isBlob: bi,
  isFunction: hn,
  isStream: wi,
  isURLSearchParams: yi,
  isStandardBrowserEnv: Ei,
  forEach: ht,
  merge: lt,
  extend: Fi,
  trim: ki,
  stripBOM: Ri
}, Q = j;
function jt(e) {
  return encodeURIComponent(e).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
var gn = function(t, n, i) {
  if (!n)
    return t;
  var a;
  if (i)
    a = i(n);
  else if (Q.isURLSearchParams(n))
    a = n.toString();
  else {
    var o = [];
    Q.forEach(n, function(l, p) {
      l === null || typeof l > "u" || (Q.isArray(l) ? p = p + "[]" : l = [l], Q.forEach(l, function(c) {
        Q.isDate(c) ? c = c.toISOString() : Q.isObject(c) && (c = JSON.stringify(c)), o.push(jt(p) + "=" + jt(c));
      }));
    }), a = o.join("&");
  }
  if (a) {
    var r = t.indexOf("#");
    r !== -1 && (t = t.slice(0, r)), t += (t.indexOf("?") === -1 ? "?" : "&") + a;
  }
  return t;
}, Pi = j;
function Oe() {
  this.handlers = [];
}
Oe.prototype.use = function(t, n, i) {
  return this.handlers.push({
    fulfilled: t,
    rejected: n,
    synchronous: i ? i.synchronous : !1,
    runWhen: i ? i.runWhen : null
  }), this.handlers.length - 1;
};
Oe.prototype.eject = function(t) {
  this.handlers[t] && (this.handlers[t] = null);
};
Oe.prototype.forEach = function(t) {
  Pi.forEach(this.handlers, function(i) {
    i !== null && t(i);
  });
};
var Ui = Oe, ji = j, Oi = function(t, n) {
  ji.forEach(t, function(a, o) {
    o !== n && o.toUpperCase() === n.toUpperCase() && (t[n] = a, delete t[o]);
  });
}, xn = function(t, n, i, a, o) {
  return t.config = n, i && (t.code = i), t.request = a, t.response = o, t.isAxiosError = !0, t.toJSON = function() {
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
  }, t;
}, bn = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, Le, Ot;
function wn() {
  if (Ot)
    return Le;
  Ot = 1;
  var e = xn;
  return Le = function(n, i, a, o, r) {
    var s = new Error(n);
    return e(s, i, a, o, r);
  }, Le;
}
var Ie, St;
function Si() {
  if (St)
    return Ie;
  St = 1;
  var e = wn();
  return Ie = function(n, i, a) {
    var o = a.config.validateStatus;
    !a.status || !o || o(a.status) ? n(a) : i(e(
      "Request failed with status code " + a.status,
      a.config,
      null,
      a.request,
      a
    ));
  }, Ie;
}
var Be, Ct;
function Ci() {
  if (Ct)
    return Be;
  Ct = 1;
  var e = j;
  return Be = e.isStandardBrowserEnv() ? function() {
    return {
      write: function(i, a, o, r, s, l) {
        var p = [];
        p.push(i + "=" + encodeURIComponent(a)), e.isNumber(o) && p.push("expires=" + new Date(o).toGMTString()), e.isString(r) && p.push("path=" + r), e.isString(s) && p.push("domain=" + s), l === !0 && p.push("secure"), document.cookie = p.join("; ");
      },
      read: function(i) {
        var a = document.cookie.match(new RegExp("(^|;\\s*)(" + i + ")=([^;]*)"));
        return a ? decodeURIComponent(a[3]) : null;
      },
      remove: function(i) {
        this.write(i, "", Date.now() - 864e5);
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
  }(), Be;
}
var Me, _t;
function _i() {
  return _t || (_t = 1, Me = function(t) {
    return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(t);
  }), Me;
}
var $e, Tt;
function Ti() {
  return Tt || (Tt = 1, $e = function(t, n) {
    return n ? t.replace(/\/+$/, "") + "/" + n.replace(/^\/+/, "") : t;
  }), $e;
}
var He, At;
function Ai() {
  if (At)
    return He;
  At = 1;
  var e = _i(), t = Ti();
  return He = function(i, a) {
    return i && !e(a) ? t(i, a) : a;
  }, He;
}
var Ve, Dt;
function Di() {
  if (Dt)
    return Ve;
  Dt = 1;
  var e = j, t = [
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
  return Ve = function(i) {
    var a = {}, o, r, s;
    return i && e.forEach(i.split(`
`), function(p) {
      if (s = p.indexOf(":"), o = e.trim(p.substr(0, s)).toLowerCase(), r = e.trim(p.substr(s + 1)), o) {
        if (a[o] && t.indexOf(o) >= 0)
          return;
        o === "set-cookie" ? a[o] = (a[o] ? a[o] : []).concat([r]) : a[o] = a[o] ? a[o] + ", " + r : r;
      }
    }), a;
  }, Ve;
}
var Je, Nt;
function Ni() {
  if (Nt)
    return Je;
  Nt = 1;
  var e = j;
  return Je = e.isStandardBrowserEnv() ? function() {
    var n = /(msie|trident)/i.test(navigator.userAgent), i = document.createElement("a"), a;
    function o(r) {
      var s = r;
      return n && (i.setAttribute("href", s), s = i.href), i.setAttribute("href", s), {
        href: i.href,
        protocol: i.protocol ? i.protocol.replace(/:$/, "") : "",
        host: i.host,
        search: i.search ? i.search.replace(/^\?/, "") : "",
        hash: i.hash ? i.hash.replace(/^#/, "") : "",
        hostname: i.hostname,
        port: i.port,
        pathname: i.pathname.charAt(0) === "/" ? i.pathname : "/" + i.pathname
      };
    }
    return a = o(window.location.href), function(s) {
      var l = e.isString(s) ? o(s) : s;
      return l.protocol === a.protocol && l.host === a.host;
    };
  }() : function() {
    return function() {
      return !0;
    };
  }(), Je;
}
var Ke, zt;
function Se() {
  if (zt)
    return Ke;
  zt = 1;
  function e(t) {
    this.message = t;
  }
  return e.prototype.toString = function() {
    return "Cancel" + (this.message ? ": " + this.message : "");
  }, e.prototype.__CANCEL__ = !0, Ke = e, Ke;
}
var We, qt;
function Lt() {
  if (qt)
    return We;
  qt = 1;
  var e = j, t = Si(), n = Ci(), i = gn, a = Ai(), o = Di(), r = Ni(), s = wn(), l = bn, p = Se();
  return We = function(c) {
    return new Promise(function(m, v) {
      var E = c.data, k = c.headers, q = c.responseType, L;
      function pe() {
        c.cancelToken && c.cancelToken.unsubscribe(L), c.signal && c.signal.removeEventListener("abort", L);
      }
      e.isFormData(E) && delete k["Content-Type"];
      var x = new XMLHttpRequest();
      if (c.auth) {
        var ze = c.auth.username || "", ie = c.auth.password ? unescape(encodeURIComponent(c.auth.password)) : "";
        k.Authorization = "Basic " + btoa(ze + ":" + ie);
      }
      var ce = a(c.baseURL, c.url);
      x.open(c.method.toUpperCase(), i(ce, c.params, c.paramsSerializer), !0), x.timeout = c.timeout;
      function de() {
        if (!!x) {
          var O = "getAllResponseHeaders" in x ? o(x.getAllResponseHeaders()) : null, J = !q || q === "text" || q === "json" ? x.responseText : x.response, $ = {
            data: J,
            status: x.status,
            statusText: x.statusText,
            headers: O,
            config: c,
            request: x
          };
          t(function(Z) {
            m(Z), pe();
          }, function(Z) {
            v(Z), pe();
          }, $), x = null;
        }
      }
      if ("onloadend" in x ? x.onloadend = de : x.onreadystatechange = function() {
        !x || x.readyState !== 4 || x.status === 0 && !(x.responseURL && x.responseURL.indexOf("file:") === 0) || setTimeout(de);
      }, x.onabort = function() {
        !x || (v(s("Request aborted", c, "ECONNABORTED", x)), x = null);
      }, x.onerror = function() {
        v(s("Network Error", c, null, x)), x = null;
      }, x.ontimeout = function() {
        var J = c.timeout ? "timeout of " + c.timeout + "ms exceeded" : "timeout exceeded", $ = c.transitional || l;
        c.timeoutErrorMessage && (J = c.timeoutErrorMessage), v(s(
          J,
          c,
          $.clarifyTimeoutError ? "ETIMEDOUT" : "ECONNABORTED",
          x
        )), x = null;
      }, e.isStandardBrowserEnv()) {
        var ue = (c.withCredentials || r(ce)) && c.xsrfCookieName ? n.read(c.xsrfCookieName) : void 0;
        ue && (k[c.xsrfHeaderName] = ue);
      }
      "setRequestHeader" in x && e.forEach(k, function(J, $) {
        typeof E > "u" && $.toLowerCase() === "content-type" ? delete k[$] : x.setRequestHeader($, J);
      }), e.isUndefined(c.withCredentials) || (x.withCredentials = !!c.withCredentials), q && q !== "json" && (x.responseType = c.responseType), typeof c.onDownloadProgress == "function" && x.addEventListener("progress", c.onDownloadProgress), typeof c.onUploadProgress == "function" && x.upload && x.upload.addEventListener("progress", c.onUploadProgress), (c.cancelToken || c.signal) && (L = function(O) {
        !x || (v(!O || O && O.type ? new p("canceled") : O), x.abort(), x = null);
      }, c.cancelToken && c.cancelToken.subscribe(L), c.signal && (c.signal.aborted ? L() : c.signal.addEventListener("abort", L))), E || (E = null), x.send(E);
    });
  }, We;
}
var P = j, It = Oi, zi = xn, qi = bn, Li = {
  "Content-Type": "application/x-www-form-urlencoded"
};
function Bt(e, t) {
  !P.isUndefined(e) && P.isUndefined(e["Content-Type"]) && (e["Content-Type"] = t);
}
function Ii() {
  var e;
  return (typeof XMLHttpRequest < "u" || typeof process < "u" && Object.prototype.toString.call(process) === "[object process]") && (e = Lt()), e;
}
function Bi(e, t, n) {
  if (P.isString(e))
    try {
      return (t || JSON.parse)(e), P.trim(e);
    } catch (i) {
      if (i.name !== "SyntaxError")
        throw i;
    }
  return (n || JSON.stringify)(e);
}
var Ce = {
  transitional: qi,
  adapter: Ii(),
  transformRequest: [function(t, n) {
    return It(n, "Accept"), It(n, "Content-Type"), P.isFormData(t) || P.isArrayBuffer(t) || P.isBuffer(t) || P.isStream(t) || P.isFile(t) || P.isBlob(t) ? t : P.isArrayBufferView(t) ? t.buffer : P.isURLSearchParams(t) ? (Bt(n, "application/x-www-form-urlencoded;charset=utf-8"), t.toString()) : P.isObject(t) || n && n["Content-Type"] === "application/json" ? (Bt(n, "application/json"), Bi(t)) : t;
  }],
  transformResponse: [function(t) {
    var n = this.transitional || Ce.transitional, i = n && n.silentJSONParsing, a = n && n.forcedJSONParsing, o = !i && this.responseType === "json";
    if (o || a && P.isString(t) && t.length)
      try {
        return JSON.parse(t);
      } catch (r) {
        if (o)
          throw r.name === "SyntaxError" ? zi(r, this, "E_JSON_PARSE") : r;
      }
    return t;
  }],
  timeout: 0,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
  maxContentLength: -1,
  maxBodyLength: -1,
  validateStatus: function(t) {
    return t >= 200 && t < 300;
  },
  headers: {
    common: {
      Accept: "application/json, text/plain, */*"
    }
  }
};
P.forEach(["delete", "get", "head"], function(t) {
  Ce.headers[t] = {};
});
P.forEach(["post", "put", "patch"], function(t) {
  Ce.headers[t] = P.merge(Li);
});
var gt = Ce, Mi = j, $i = gt, Hi = function(t, n, i) {
  var a = this || $i;
  return Mi.forEach(i, function(r) {
    t = r.call(a, t, n);
  }), t;
}, Ge, Mt;
function yn() {
  return Mt || (Mt = 1, Ge = function(t) {
    return !!(t && t.__CANCEL__);
  }), Ge;
}
var $t = j, Xe = Hi, Vi = yn(), Ji = gt, Ki = Se();
function Ze(e) {
  if (e.cancelToken && e.cancelToken.throwIfRequested(), e.signal && e.signal.aborted)
    throw new Ki("canceled");
}
var Wi = function(t) {
  Ze(t), t.headers = t.headers || {}, t.data = Xe.call(
    t,
    t.data,
    t.headers,
    t.transformRequest
  ), t.headers = $t.merge(
    t.headers.common || {},
    t.headers[t.method] || {},
    t.headers
  ), $t.forEach(
    ["delete", "get", "head", "post", "put", "patch", "common"],
    function(a) {
      delete t.headers[a];
    }
  );
  var n = t.adapter || Ji.adapter;
  return n(t).then(function(a) {
    return Ze(t), a.data = Xe.call(
      t,
      a.data,
      a.headers,
      t.transformResponse
    ), a;
  }, function(a) {
    return Vi(a) || (Ze(t), a && a.response && (a.response.data = Xe.call(
      t,
      a.response.data,
      a.response.headers,
      t.transformResponse
    ))), Promise.reject(a);
  });
}, S = j, kn = function(t, n) {
  n = n || {};
  var i = {};
  function a(d, c) {
    return S.isPlainObject(d) && S.isPlainObject(c) ? S.merge(d, c) : S.isPlainObject(c) ? S.merge({}, c) : S.isArray(c) ? c.slice() : c;
  }
  function o(d) {
    if (S.isUndefined(n[d])) {
      if (!S.isUndefined(t[d]))
        return a(void 0, t[d]);
    } else
      return a(t[d], n[d]);
  }
  function r(d) {
    if (!S.isUndefined(n[d]))
      return a(void 0, n[d]);
  }
  function s(d) {
    if (S.isUndefined(n[d])) {
      if (!S.isUndefined(t[d]))
        return a(void 0, t[d]);
    } else
      return a(void 0, n[d]);
  }
  function l(d) {
    if (d in n)
      return a(t[d], n[d]);
    if (d in t)
      return a(void 0, t[d]);
  }
  var p = {
    url: r,
    method: r,
    data: r,
    baseURL: s,
    transformRequest: s,
    transformResponse: s,
    paramsSerializer: s,
    timeout: s,
    timeoutMessage: s,
    withCredentials: s,
    adapter: s,
    responseType: s,
    xsrfCookieName: s,
    xsrfHeaderName: s,
    onUploadProgress: s,
    onDownloadProgress: s,
    decompress: s,
    maxContentLength: s,
    maxBodyLength: s,
    transport: s,
    httpAgent: s,
    httpsAgent: s,
    cancelToken: s,
    socketPath: s,
    responseEncoding: s,
    validateStatus: l
  };
  return S.forEach(Object.keys(t).concat(Object.keys(n)), function(c) {
    var h = p[c] || o, m = h(c);
    S.isUndefined(m) && h !== l || (i[c] = m);
  }), i;
}, Qe, Ht;
function En() {
  return Ht || (Ht = 1, Qe = {
    version: "0.26.1"
  }), Qe;
}
var Gi = En().version, xt = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach(function(e, t) {
  xt[e] = function(i) {
    return typeof i === e || "a" + (t < 1 ? "n " : " ") + e;
  };
});
var Vt = {};
xt.transitional = function(t, n, i) {
  function a(o, r) {
    return "[Axios v" + Gi + "] Transitional option '" + o + "'" + r + (i ? ". " + i : "");
  }
  return function(o, r, s) {
    if (t === !1)
      throw new Error(a(r, " has been removed" + (n ? " in " + n : "")));
    return n && !Vt[r] && (Vt[r] = !0, console.warn(
      a(
        r,
        " has been deprecated since v" + n + " and will be removed in the near future"
      )
    )), t ? t(o, r, s) : !0;
  };
};
function Xi(e, t, n) {
  if (typeof e != "object")
    throw new TypeError("options must be an object");
  for (var i = Object.keys(e), a = i.length; a-- > 0; ) {
    var o = i[a], r = t[o];
    if (r) {
      var s = e[o], l = s === void 0 || r(s, o, e);
      if (l !== !0)
        throw new TypeError("option " + o + " must be " + l);
      continue;
    }
    if (n !== !0)
      throw Error("Unknown option " + o);
  }
}
var Zi = {
  assertOptions: Xi,
  validators: xt
}, Fn = j, Qi = gn, Jt = Ui, Kt = Wi, _e = kn, Rn = Zi, Y = Rn.validators;
function re(e) {
  this.defaults = e, this.interceptors = {
    request: new Jt(),
    response: new Jt()
  };
}
re.prototype.request = function(t, n) {
  typeof t == "string" ? (n = n || {}, n.url = t) : n = t || {}, n = _e(this.defaults, n), n.method ? n.method = n.method.toLowerCase() : this.defaults.method ? n.method = this.defaults.method.toLowerCase() : n.method = "get";
  var i = n.transitional;
  i !== void 0 && Rn.assertOptions(i, {
    silentJSONParsing: Y.transitional(Y.boolean),
    forcedJSONParsing: Y.transitional(Y.boolean),
    clarifyTimeoutError: Y.transitional(Y.boolean)
  }, !1);
  var a = [], o = !0;
  this.interceptors.request.forEach(function(m) {
    typeof m.runWhen == "function" && m.runWhen(n) === !1 || (o = o && m.synchronous, a.unshift(m.fulfilled, m.rejected));
  });
  var r = [];
  this.interceptors.response.forEach(function(m) {
    r.push(m.fulfilled, m.rejected);
  });
  var s;
  if (!o) {
    var l = [Kt, void 0];
    for (Array.prototype.unshift.apply(l, a), l = l.concat(r), s = Promise.resolve(n); l.length; )
      s = s.then(l.shift(), l.shift());
    return s;
  }
  for (var p = n; a.length; ) {
    var d = a.shift(), c = a.shift();
    try {
      p = d(p);
    } catch (h) {
      c(h);
      break;
    }
  }
  try {
    s = Kt(p);
  } catch (h) {
    return Promise.reject(h);
  }
  for (; r.length; )
    s = s.then(r.shift(), r.shift());
  return s;
};
re.prototype.getUri = function(t) {
  return t = _e(this.defaults, t), Qi(t.url, t.params, t.paramsSerializer).replace(/^\?/, "");
};
Fn.forEach(["delete", "get", "head", "options"], function(t) {
  re.prototype[t] = function(n, i) {
    return this.request(_e(i || {}, {
      method: t,
      url: n,
      data: (i || {}).data
    }));
  };
});
Fn.forEach(["post", "put", "patch"], function(t) {
  re.prototype[t] = function(n, i, a) {
    return this.request(_e(a || {}, {
      method: t,
      url: n,
      data: i
    }));
  };
});
var Yi = re, Ye, Wt;
function ea() {
  if (Wt)
    return Ye;
  Wt = 1;
  var e = Se();
  function t(n) {
    if (typeof n != "function")
      throw new TypeError("executor must be a function.");
    var i;
    this.promise = new Promise(function(r) {
      i = r;
    });
    var a = this;
    this.promise.then(function(o) {
      if (!!a._listeners) {
        var r, s = a._listeners.length;
        for (r = 0; r < s; r++)
          a._listeners[r](o);
        a._listeners = null;
      }
    }), this.promise.then = function(o) {
      var r, s = new Promise(function(l) {
        a.subscribe(l), r = l;
      }).then(o);
      return s.cancel = function() {
        a.unsubscribe(r);
      }, s;
    }, n(function(r) {
      a.reason || (a.reason = new e(r), i(a.reason));
    });
  }
  return t.prototype.throwIfRequested = function() {
    if (this.reason)
      throw this.reason;
  }, t.prototype.subscribe = function(i) {
    if (this.reason) {
      i(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(i) : this._listeners = [i];
  }, t.prototype.unsubscribe = function(i) {
    if (!!this._listeners) {
      var a = this._listeners.indexOf(i);
      a !== -1 && this._listeners.splice(a, 1);
    }
  }, t.source = function() {
    var i, a = new t(function(r) {
      i = r;
    });
    return {
      token: a,
      cancel: i
    };
  }, Ye = t, Ye;
}
var et, Gt;
function ta() {
  return Gt || (Gt = 1, et = function(t) {
    return function(i) {
      return t.apply(null, i);
    };
  }), et;
}
var tt, Xt;
function na() {
  if (Xt)
    return tt;
  Xt = 1;
  var e = j;
  return tt = function(n) {
    return e.isObject(n) && n.isAxiosError === !0;
  }, tt;
}
var Zt = j, ia = mn, ye = Yi, aa = kn, oa = gt;
function Pn(e) {
  var t = new ye(e), n = ia(ye.prototype.request, t);
  return Zt.extend(n, ye.prototype, t), Zt.extend(n, t), n.create = function(a) {
    return Pn(aa(e, a));
  }, n;
}
var M = Pn(oa);
M.Axios = ye;
M.Cancel = Se();
M.CancelToken = ea();
M.isCancel = yn();
M.VERSION = En().version;
M.all = function(t) {
  return Promise.all(t);
};
M.spread = ta();
M.isAxiosError = na();
ft.exports = M;
ft.exports.default = M;
(function(e) {
  e.exports = ft.exports;
})(un);
const nt = /* @__PURE__ */ oi(un.exports);
var ve, ra = new Uint8Array(16);
function sa() {
  if (!ve && (ve = typeof crypto < "u" && crypto.getRandomValues && crypto.getRandomValues.bind(crypto) || typeof msCrypto < "u" && typeof msCrypto.getRandomValues == "function" && msCrypto.getRandomValues.bind(msCrypto), !ve))
    throw new Error("crypto.getRandomValues() not supported. See https://github.com/uuidjs/uuid#getrandomvalues-not-supported");
  return ve(ra);
}
const la = /^(?:[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}|00000000-0000-0000-0000-000000000000)$/i;
function pa(e) {
  return typeof e == "string" && la.test(e);
}
var R = [];
for (var it = 0; it < 256; ++it)
  R.push((it + 256).toString(16).substr(1));
function ca(e) {
  var t = arguments.length > 1 && arguments[1] !== void 0 ? arguments[1] : 0, n = (R[e[t + 0]] + R[e[t + 1]] + R[e[t + 2]] + R[e[t + 3]] + "-" + R[e[t + 4]] + R[e[t + 5]] + "-" + R[e[t + 6]] + R[e[t + 7]] + "-" + R[e[t + 8]] + R[e[t + 9]] + "-" + R[e[t + 10]] + R[e[t + 11]] + R[e[t + 12]] + R[e[t + 13]] + R[e[t + 14]] + R[e[t + 15]]).toLowerCase();
  if (!pa(n))
    throw TypeError("Stringified UUID is invalid");
  return n;
}
function da(e, t, n) {
  e = e || {};
  var i = e.random || (e.rng || sa)();
  if (i[6] = i[6] & 15 | 64, i[8] = i[8] & 63 | 128, t) {
    n = n || 0;
    for (var a = 0; a < 16; ++a)
      t[n + a] = i[a];
    return t;
  }
  return ca(i);
}
function Un(e, t) {
  return function() {
    return e.apply(t, arguments);
  };
}
const { toString: jn } = Object.prototype, { getPrototypeOf: bt } = Object, wt = ((e) => (t) => {
  const n = jn.call(t);
  return e[n] || (e[n] = n.slice(8, -1).toLowerCase());
})(/* @__PURE__ */ Object.create(null)), V = (e) => (e = e.toLowerCase(), (t) => wt(t) === e), Te = (e) => (t) => typeof t === e, { isArray: ne } = Array, oe = Te("undefined");
function ua(e) {
  return e !== null && !oe(e) && e.constructor !== null && !oe(e.constructor) && X(e.constructor.isBuffer) && e.constructor.isBuffer(e);
}
const On = V("ArrayBuffer");
function ma(e) {
  let t;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? t = ArrayBuffer.isView(e) : t = e && e.buffer && On(e.buffer), t;
}
const fa = Te("string"), X = Te("function"), Sn = Te("number"), yt = (e) => e !== null && typeof e == "object", va = (e) => e === !0 || e === !1, ke = (e) => {
  if (wt(e) !== "object")
    return !1;
  const t = bt(e);
  return (t === null || t === Object.prototype || Object.getPrototypeOf(t) === null) && !(Symbol.toStringTag in e) && !(Symbol.iterator in e);
}, ha = V("Date"), ga = V("File"), xa = V("Blob"), ba = V("FileList"), wa = (e) => yt(e) && X(e.pipe), ya = (e) => {
  const t = "[object FormData]";
  return e && (typeof FormData == "function" && e instanceof FormData || jn.call(e) === t || X(e.toString) && e.toString() === t);
}, ka = V("URLSearchParams"), Ea = (e) => e.trim ? e.trim() : e.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "");
function se(e, t, { allOwnKeys: n = !1 } = {}) {
  if (e === null || typeof e > "u")
    return;
  let i, a;
  if (typeof e != "object" && (e = [e]), ne(e))
    for (i = 0, a = e.length; i < a; i++)
      t.call(null, e[i], i, e);
  else {
    const o = n ? Object.getOwnPropertyNames(e) : Object.keys(e), r = o.length;
    let s;
    for (i = 0; i < r; i++)
      s = o[i], t.call(null, e[s], s, e);
  }
}
function Cn(e, t) {
  t = t.toLowerCase();
  const n = Object.keys(e);
  let i = n.length, a;
  for (; i-- > 0; )
    if (a = n[i], t === a.toLowerCase())
      return a;
  return null;
}
const _n = (() => typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : typeof window < "u" ? window : global)(), Tn = (e) => !oe(e) && e !== _n;
function pt() {
  const { caseless: e } = Tn(this) && this || {}, t = {}, n = (i, a) => {
    const o = e && Cn(t, a) || a;
    ke(t[o]) && ke(i) ? t[o] = pt(t[o], i) : ke(i) ? t[o] = pt({}, i) : ne(i) ? t[o] = i.slice() : t[o] = i;
  };
  for (let i = 0, a = arguments.length; i < a; i++)
    arguments[i] && se(arguments[i], n);
  return t;
}
const Fa = (e, t, n, { allOwnKeys: i } = {}) => (se(t, (a, o) => {
  n && X(a) ? e[o] = Un(a, n) : e[o] = a;
}, { allOwnKeys: i }), e), Ra = (e) => (e.charCodeAt(0) === 65279 && (e = e.slice(1)), e), Pa = (e, t, n, i) => {
  e.prototype = Object.create(t.prototype, i), e.prototype.constructor = e, Object.defineProperty(e, "super", {
    value: t.prototype
  }), n && Object.assign(e.prototype, n);
}, Ua = (e, t, n, i) => {
  let a, o, r;
  const s = {};
  if (t = t || {}, e == null)
    return t;
  do {
    for (a = Object.getOwnPropertyNames(e), o = a.length; o-- > 0; )
      r = a[o], (!i || i(r, e, t)) && !s[r] && (t[r] = e[r], s[r] = !0);
    e = n !== !1 && bt(e);
  } while (e && (!n || n(e, t)) && e !== Object.prototype);
  return t;
}, ja = (e, t, n) => {
  e = String(e), (n === void 0 || n > e.length) && (n = e.length), n -= t.length;
  const i = e.indexOf(t, n);
  return i !== -1 && i === n;
}, Oa = (e) => {
  if (!e)
    return null;
  if (ne(e))
    return e;
  let t = e.length;
  if (!Sn(t))
    return null;
  const n = new Array(t);
  for (; t-- > 0; )
    n[t] = e[t];
  return n;
}, Sa = ((e) => (t) => e && t instanceof e)(typeof Uint8Array < "u" && bt(Uint8Array)), Ca = (e, t) => {
  const n = (e && e[Symbol.iterator]).call(e);
  let i;
  for (; (i = n.next()) && !i.done; ) {
    const a = i.value;
    t.call(e, a[0], a[1]);
  }
}, _a = (e, t) => {
  let n;
  const i = [];
  for (; (n = e.exec(t)) !== null; )
    i.push(n);
  return i;
}, Ta = V("HTMLFormElement"), Aa = (e) => e.toLowerCase().replace(
  /[_-\s]([a-z\d])(\w*)/g,
  function(t, n, i) {
    return n.toUpperCase() + i;
  }
), Qt = (({ hasOwnProperty: e }) => (t, n) => e.call(t, n))(Object.prototype), Da = V("RegExp"), An = (e, t) => {
  const n = Object.getOwnPropertyDescriptors(e), i = {};
  se(n, (a, o) => {
    t(a, o, e) !== !1 && (i[o] = a);
  }), Object.defineProperties(e, i);
}, Na = (e) => {
  An(e, (t, n) => {
    if (X(e) && ["arguments", "caller", "callee"].indexOf(n) !== -1)
      return !1;
    const i = e[n];
    if (X(i)) {
      if (t.enumerable = !1, "writable" in t) {
        t.writable = !1;
        return;
      }
      t.set || (t.set = () => {
        throw Error("Can not rewrite read-only method '" + n + "'");
      });
    }
  });
}, za = (e, t) => {
  const n = {}, i = (a) => {
    a.forEach((o) => {
      n[o] = !0;
    });
  };
  return ne(e) ? i(e) : i(String(e).split(t)), n;
}, qa = () => {
}, La = (e, t) => (e = +e, Number.isFinite(e) ? e : t), Ia = (e) => {
  const t = new Array(10), n = (i, a) => {
    if (yt(i)) {
      if (t.indexOf(i) >= 0)
        return;
      if (!("toJSON" in i)) {
        t[a] = i;
        const o = ne(i) ? [] : {};
        return se(i, (r, s) => {
          const l = n(r, a + 1);
          !oe(l) && (o[s] = l);
        }), t[a] = void 0, o;
      }
    }
    return i;
  };
  return n(e, 0);
}, u = {
  isArray: ne,
  isArrayBuffer: On,
  isBuffer: ua,
  isFormData: ya,
  isArrayBufferView: ma,
  isString: fa,
  isNumber: Sn,
  isBoolean: va,
  isObject: yt,
  isPlainObject: ke,
  isUndefined: oe,
  isDate: ha,
  isFile: ga,
  isBlob: xa,
  isRegExp: Da,
  isFunction: X,
  isStream: wa,
  isURLSearchParams: ka,
  isTypedArray: Sa,
  isFileList: ba,
  forEach: se,
  merge: pt,
  extend: Fa,
  trim: Ea,
  stripBOM: Ra,
  inherits: Pa,
  toFlatObject: Ua,
  kindOf: wt,
  kindOfTest: V,
  endsWith: ja,
  toArray: Oa,
  forEachEntry: Ca,
  matchAll: _a,
  isHTMLForm: Ta,
  hasOwnProperty: Qt,
  hasOwnProp: Qt,
  reduceDescriptors: An,
  freezeMethods: Na,
  toObjectSet: za,
  toCamelCase: Aa,
  noop: qa,
  toFiniteNumber: La,
  findKey: Cn,
  global: _n,
  isContextDefined: Tn,
  toJSONObject: Ia
};
function w(e, t, n, i, a) {
  Error.call(this), Error.captureStackTrace ? Error.captureStackTrace(this, this.constructor) : this.stack = new Error().stack, this.message = e, this.name = "AxiosError", t && (this.code = t), n && (this.config = n), i && (this.request = i), a && (this.response = a);
}
u.inherits(w, Error, {
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
      config: u.toJSONObject(this.config),
      code: this.code,
      status: this.response && this.response.status ? this.response.status : null
    };
  }
});
const Dn = w.prototype, Nn = {};
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
].forEach((e) => {
  Nn[e] = { value: e };
});
Object.defineProperties(w, Nn);
Object.defineProperty(Dn, "isAxiosError", { value: !0 });
w.from = (e, t, n, i, a, o) => {
  const r = Object.create(Dn);
  return u.toFlatObject(e, r, function(s) {
    return s !== Error.prototype;
  }, (s) => s !== "isAxiosError"), w.call(r, e.message, t, n, i, a), r.cause = e, r.name = e.name, o && Object.assign(r, o), r;
};
var Ba = typeof self == "object" ? self.FormData : window.FormData;
const Ma = Ba;
function ct(e) {
  return u.isPlainObject(e) || u.isArray(e);
}
function zn(e) {
  return u.endsWith(e, "[]") ? e.slice(0, -2) : e;
}
function Yt(e, t, n) {
  return e ? e.concat(t).map(function(i, a) {
    return i = zn(i), !n && a ? "[" + i + "]" : i;
  }).join(n ? "." : "") : t;
}
function $a(e) {
  return u.isArray(e) && !e.some(ct);
}
const Ha = u.toFlatObject(u, {}, null, function(e) {
  return /^is[A-Z]/.test(e);
});
function Va(e) {
  return e && u.isFunction(e.append) && e[Symbol.toStringTag] === "FormData" && e[Symbol.iterator];
}
function Ae(e, t, n) {
  if (!u.isObject(e))
    throw new TypeError("target must be an object");
  t = t || new (Ma || FormData)(), n = u.toFlatObject(n, {
    metaTokens: !0,
    dots: !1,
    indexes: !1
  }, !1, function(m, v) {
    return !u.isUndefined(v[m]);
  });
  const i = n.metaTokens, a = n.visitor || p, o = n.dots, r = n.indexes, s = (n.Blob || typeof Blob < "u" && Blob) && Va(t);
  if (!u.isFunction(a))
    throw new TypeError("visitor must be a function");
  function l(m) {
    if (m === null)
      return "";
    if (u.isDate(m))
      return m.toISOString();
    if (!s && u.isBlob(m))
      throw new w("Blob is not supported. Use a Buffer instead.");
    return u.isArrayBuffer(m) || u.isTypedArray(m) ? s && typeof Blob == "function" ? new Blob([m]) : Buffer.from(m) : m;
  }
  function p(m, v, E) {
    let k = m;
    if (m && !E && typeof m == "object") {
      if (u.endsWith(v, "{}"))
        v = i ? v : v.slice(0, -2), m = JSON.stringify(m);
      else if (u.isArray(m) && $a(m) || u.isFileList(m) || u.endsWith(v, "[]") && (k = u.toArray(m)))
        return v = zn(v), k.forEach(function(q, L) {
          !(u.isUndefined(q) || q === null) && t.append(
            r === !0 ? Yt([v], L, o) : r === null ? v : v + "[]",
            l(q)
          );
        }), !1;
    }
    return ct(m) ? !0 : (t.append(Yt(E, v, o), l(m)), !1);
  }
  const d = [], c = Object.assign(Ha, {
    defaultVisitor: p,
    convertValue: l,
    isVisitable: ct
  });
  function h(m, v) {
    if (!u.isUndefined(m)) {
      if (d.indexOf(m) !== -1)
        throw Error("Circular reference detected in " + v.join("."));
      d.push(m), u.forEach(m, function(E, k) {
        (!(u.isUndefined(E) || E === null) && a.call(
          t,
          E,
          u.isString(k) ? k.trim() : k,
          v,
          c
        )) === !0 && h(E, v ? v.concat(k) : [k]);
      }), d.pop();
    }
  }
  if (!u.isObject(e))
    throw new TypeError("data must be an object");
  return h(e), t;
}
function en(e) {
  const t = {
    "!": "%21",
    "'": "%27",
    "(": "%28",
    ")": "%29",
    "~": "%7E",
    "%20": "+",
    "%00": "\0"
  };
  return encodeURIComponent(e).replace(/[!'()~]|%20|%00/g, function(n) {
    return t[n];
  });
}
function kt(e, t) {
  this._pairs = [], e && Ae(e, this, t);
}
const qn = kt.prototype;
qn.append = function(e, t) {
  this._pairs.push([e, t]);
};
qn.toString = function(e) {
  const t = e ? function(n) {
    return e.call(this, n, en);
  } : en;
  return this._pairs.map(function(n) {
    return t(n[0]) + "=" + t(n[1]);
  }, "").join("&");
};
function Ja(e) {
  return encodeURIComponent(e).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
function Ln(e, t, n) {
  if (!t)
    return e;
  const i = n && n.encode || Ja, a = n && n.serialize;
  let o;
  if (a ? o = a(t, n) : o = u.isURLSearchParams(t) ? t.toString() : new kt(t, n).toString(i), o) {
    const r = e.indexOf("#");
    r !== -1 && (e = e.slice(0, r)), e += (e.indexOf("?") === -1 ? "?" : "&") + o;
  }
  return e;
}
class Ka {
  constructor() {
    this.handlers = [];
  }
  use(t, n, i) {
    return this.handlers.push({
      fulfilled: t,
      rejected: n,
      synchronous: i ? i.synchronous : !1,
      runWhen: i ? i.runWhen : null
    }), this.handlers.length - 1;
  }
  eject(t) {
    this.handlers[t] && (this.handlers[t] = null);
  }
  clear() {
    this.handlers && (this.handlers = []);
  }
  forEach(t) {
    u.forEach(this.handlers, function(n) {
      n !== null && t(n);
    });
  }
}
const tn = Ka, In = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, Wa = typeof URLSearchParams < "u" ? URLSearchParams : kt, Ga = FormData, Xa = (() => {
  let e;
  return typeof navigator < "u" && ((e = navigator.product) === "ReactNative" || e === "NativeScript" || e === "NS") ? !1 : typeof window < "u" && typeof document < "u";
})(), Za = (() => typeof WorkerGlobalScope < "u" && self instanceof WorkerGlobalScope && typeof self.importScripts == "function")(), B = {
  isBrowser: !0,
  classes: {
    URLSearchParams: Wa,
    FormData: Ga,
    Blob
  },
  isStandardBrowserEnv: Xa,
  isStandardBrowserWebWorkerEnv: Za,
  protocols: ["http", "https", "file", "blob", "url", "data"]
};
function Qa(e, t) {
  return Ae(e, new B.classes.URLSearchParams(), Object.assign({
    visitor: function(n, i, a, o) {
      return B.isNode && u.isBuffer(n) ? (this.append(i, n.toString("base64")), !1) : o.defaultVisitor.apply(this, arguments);
    }
  }, t));
}
function Ya(e) {
  return u.matchAll(/\w+|\[(\w*)]/g, e).map((t) => t[0] === "[]" ? "" : t[1] || t[0]);
}
function eo(e) {
  const t = {}, n = Object.keys(e);
  let i;
  const a = n.length;
  let o;
  for (i = 0; i < a; i++)
    o = n[i], t[o] = e[o];
  return t;
}
function Bn(e) {
  function t(n, i, a, o) {
    let r = n[o++];
    const s = Number.isFinite(+r), l = o >= n.length;
    return r = !r && u.isArray(a) ? a.length : r, l ? (u.hasOwnProp(a, r) ? a[r] = [a[r], i] : a[r] = i, !s) : ((!a[r] || !u.isObject(a[r])) && (a[r] = []), t(n, i, a[r], o) && u.isArray(a[r]) && (a[r] = eo(a[r])), !s);
  }
  if (u.isFormData(e) && u.isFunction(e.entries)) {
    const n = {};
    return u.forEachEntry(e, (i, a) => {
      t(Ya(i), a, n, 0);
    }), n;
  }
  return null;
}
const to = {
  "Content-Type": void 0
};
function no(e, t, n) {
  if (u.isString(e))
    try {
      return (t || JSON.parse)(e), u.trim(e);
    } catch (i) {
      if (i.name !== "SyntaxError")
        throw i;
    }
  return (n || JSON.stringify)(e);
}
const De = {
  transitional: In,
  adapter: ["xhr", "http"],
  transformRequest: [function(e, t) {
    const n = t.getContentType() || "", i = n.indexOf("application/json") > -1, a = u.isObject(e);
    if (a && u.isHTMLForm(e) && (e = new FormData(e)), u.isFormData(e))
      return i && i ? JSON.stringify(Bn(e)) : e;
    if (u.isArrayBuffer(e) || u.isBuffer(e) || u.isStream(e) || u.isFile(e) || u.isBlob(e))
      return e;
    if (u.isArrayBufferView(e))
      return e.buffer;
    if (u.isURLSearchParams(e))
      return t.setContentType("application/x-www-form-urlencoded;charset=utf-8", !1), e.toString();
    let o;
    if (a) {
      if (n.indexOf("application/x-www-form-urlencoded") > -1)
        return Qa(e, this.formSerializer).toString();
      if ((o = u.isFileList(e)) || n.indexOf("multipart/form-data") > -1) {
        const r = this.env && this.env.FormData;
        return Ae(
          o ? { "files[]": e } : e,
          r && new r(),
          this.formSerializer
        );
      }
    }
    return a || i ? (t.setContentType("application/json", !1), no(e)) : e;
  }],
  transformResponse: [function(e) {
    const t = this.transitional || De.transitional, n = t && t.forcedJSONParsing, i = this.responseType === "json";
    if (e && u.isString(e) && (n && !this.responseType || i)) {
      const a = !(t && t.silentJSONParsing) && i;
      try {
        return JSON.parse(e);
      } catch (o) {
        if (a)
          throw o.name === "SyntaxError" ? w.from(o, w.ERR_BAD_RESPONSE, this, null, this.response) : o;
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
    FormData: B.classes.FormData,
    Blob: B.classes.Blob
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
u.forEach(["delete", "get", "head"], function(e) {
  De.headers[e] = {};
});
u.forEach(["post", "put", "patch"], function(e) {
  De.headers[e] = u.merge(to);
});
const Et = De, io = u.toObjectSet([
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
]), ao = (e) => {
  const t = {};
  let n, i, a;
  return e && e.split(`
`).forEach(function(o) {
    a = o.indexOf(":"), n = o.substring(0, a).trim().toLowerCase(), i = o.substring(a + 1).trim(), !(!n || t[n] && io[n]) && (n === "set-cookie" ? t[n] ? t[n].push(i) : t[n] = [i] : t[n] = t[n] ? t[n] + ", " + i : i);
  }), t;
}, nn = Symbol("internals");
function ae(e) {
  return e && String(e).trim().toLowerCase();
}
function Ee(e) {
  return e === !1 || e == null ? e : u.isArray(e) ? e.map(Ee) : String(e);
}
function oo(e) {
  const t = /* @__PURE__ */ Object.create(null), n = /([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g;
  let i;
  for (; i = n.exec(e); )
    t[i[1]] = i[2];
  return t;
}
function ro(e) {
  return /^[-_a-zA-Z]+$/.test(e.trim());
}
function an(e, t, n, i) {
  if (u.isFunction(i))
    return i.call(this, t, n);
  if (u.isString(t)) {
    if (u.isString(i))
      return t.indexOf(i) !== -1;
    if (u.isRegExp(i))
      return i.test(t);
  }
}
function so(e) {
  return e.trim().toLowerCase().replace(/([a-z\d])(\w*)/g, (t, n, i) => n.toUpperCase() + i);
}
function lo(e, t) {
  const n = u.toCamelCase(" " + t);
  ["get", "set", "has"].forEach((i) => {
    Object.defineProperty(e, i + n, {
      value: function(a, o, r) {
        return this[i].call(this, t, a, o, r);
      },
      configurable: !0
    });
  });
}
class Ne {
  constructor(t) {
    t && this.set(t);
  }
  set(t, n, i) {
    const a = this;
    function o(s, l, p) {
      const d = ae(l);
      if (!d)
        throw new Error("header name must be a non-empty string");
      const c = u.findKey(a, d);
      (!c || a[c] === void 0 || p === !0 || p === void 0 && a[c] !== !1) && (a[c || l] = Ee(s));
    }
    const r = (s, l) => u.forEach(s, (p, d) => o(p, d, l));
    return u.isPlainObject(t) || t instanceof this.constructor ? r(t, n) : u.isString(t) && (t = t.trim()) && !ro(t) ? r(ao(t), n) : t != null && o(n, t, i), this;
  }
  get(t, n) {
    if (t = ae(t), t) {
      const i = u.findKey(this, t);
      if (i) {
        const a = this[i];
        if (!n)
          return a;
        if (n === !0)
          return oo(a);
        if (u.isFunction(n))
          return n.call(this, a, i);
        if (u.isRegExp(n))
          return n.exec(a);
        throw new TypeError("parser must be boolean|regexp|function");
      }
    }
  }
  has(t, n) {
    if (t = ae(t), t) {
      const i = u.findKey(this, t);
      return !!(i && (!n || an(this, this[i], i, n)));
    }
    return !1;
  }
  delete(t, n) {
    const i = this;
    let a = !1;
    function o(r) {
      if (r = ae(r), r) {
        const s = u.findKey(i, r);
        s && (!n || an(i, i[s], s, n)) && (delete i[s], a = !0);
      }
    }
    return u.isArray(t) ? t.forEach(o) : o(t), a;
  }
  clear() {
    return Object.keys(this).forEach(this.delete.bind(this));
  }
  normalize(t) {
    const n = this, i = {};
    return u.forEach(this, (a, o) => {
      const r = u.findKey(i, o);
      if (r) {
        n[r] = Ee(a), delete n[o];
        return;
      }
      const s = t ? so(o) : String(o).trim();
      s !== o && delete n[o], n[s] = Ee(a), i[s] = !0;
    }), this;
  }
  concat(...t) {
    return this.constructor.concat(this, ...t);
  }
  toJSON(t) {
    const n = /* @__PURE__ */ Object.create(null);
    return u.forEach(this, (i, a) => {
      i != null && i !== !1 && (n[a] = t && u.isArray(i) ? i.join(", ") : i);
    }), n;
  }
  [Symbol.iterator]() {
    return Object.entries(this.toJSON())[Symbol.iterator]();
  }
  toString() {
    return Object.entries(this.toJSON()).map(([t, n]) => t + ": " + n).join(`
`);
  }
  get [Symbol.toStringTag]() {
    return "AxiosHeaders";
  }
  static from(t) {
    return t instanceof this ? t : new this(t);
  }
  static concat(t, ...n) {
    const i = new this(t);
    return n.forEach((a) => i.set(a)), i;
  }
  static accessor(t) {
    const n = (this[nn] = this[nn] = {
      accessors: {}
    }).accessors, i = this.prototype;
    function a(o) {
      const r = ae(o);
      n[r] || (lo(i, o), n[r] = !0);
    }
    return u.isArray(t) ? t.forEach(a) : a(t), this;
  }
}
Ne.accessor(["Content-Type", "Content-Length", "Accept", "Accept-Encoding", "User-Agent"]);
u.freezeMethods(Ne.prototype);
u.freezeMethods(Ne);
const H = Ne;
function at(e, t) {
  const n = this || Et, i = t || n, a = H.from(i.headers);
  let o = i.data;
  return u.forEach(e, function(r) {
    o = r.call(n, o, a.normalize(), t ? t.status : void 0);
  }), a.normalize(), o;
}
function Mn(e) {
  return !!(e && e.__CANCEL__);
}
function le(e, t, n) {
  w.call(this, e != null ? e : "canceled", w.ERR_CANCELED, t, n), this.name = "CanceledError";
}
u.inherits(le, w, {
  __CANCEL__: !0
});
const po = null;
function co(e, t, n) {
  const i = n.config.validateStatus;
  !n.status || !i || i(n.status) ? e(n) : t(new w(
    "Request failed with status code " + n.status,
    [w.ERR_BAD_REQUEST, w.ERR_BAD_RESPONSE][Math.floor(n.status / 100) - 4],
    n.config,
    n.request,
    n
  ));
}
const uo = B.isStandardBrowserEnv ? function() {
  return {
    write: function(e, t, n, i, a, o) {
      const r = [];
      r.push(e + "=" + encodeURIComponent(t)), u.isNumber(n) && r.push("expires=" + new Date(n).toGMTString()), u.isString(i) && r.push("path=" + i), u.isString(a) && r.push("domain=" + a), o === !0 && r.push("secure"), document.cookie = r.join("; ");
    },
    read: function(e) {
      const t = document.cookie.match(new RegExp("(^|;\\s*)(" + e + ")=([^;]*)"));
      return t ? decodeURIComponent(t[3]) : null;
    },
    remove: function(e) {
      this.write(e, "", Date.now() - 864e5);
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
function mo(e) {
  return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(e);
}
function fo(e, t) {
  return t ? e.replace(/\/+$/, "") + "/" + t.replace(/^\/+/, "") : e;
}
function $n(e, t) {
  return e && !mo(t) ? fo(e, t) : t;
}
const vo = B.isStandardBrowserEnv ? function() {
  const e = /(msie|trident)/i.test(navigator.userAgent), t = document.createElement("a");
  let n;
  function i(a) {
    let o = a;
    return e && (t.setAttribute("href", o), o = t.href), t.setAttribute("href", o), {
      href: t.href,
      protocol: t.protocol ? t.protocol.replace(/:$/, "") : "",
      host: t.host,
      search: t.search ? t.search.replace(/^\?/, "") : "",
      hash: t.hash ? t.hash.replace(/^#/, "") : "",
      hostname: t.hostname,
      port: t.port,
      pathname: t.pathname.charAt(0) === "/" ? t.pathname : "/" + t.pathname
    };
  }
  return n = i(window.location.href), function(a) {
    const o = u.isString(a) ? i(a) : a;
    return o.protocol === n.protocol && o.host === n.host;
  };
}() : function() {
  return function() {
    return !0;
  };
}();
function ho(e) {
  const t = /^([-+\w]{1,25})(:?\/\/|:)/.exec(e);
  return t && t[1] || "";
}
function go(e, t) {
  e = e || 10;
  const n = new Array(e), i = new Array(e);
  let a = 0, o = 0, r;
  return t = t !== void 0 ? t : 1e3, function(s) {
    const l = Date.now(), p = i[o];
    r || (r = l), n[a] = s, i[a] = l;
    let d = o, c = 0;
    for (; d !== a; )
      c += n[d++], d = d % e;
    if (a = (a + 1) % e, a === o && (o = (o + 1) % e), l - r < t)
      return;
    const h = p && l - p;
    return h ? Math.round(c * 1e3 / h) : void 0;
  };
}
function on(e, t) {
  let n = 0;
  const i = go(50, 250);
  return (a) => {
    const o = a.loaded, r = a.lengthComputable ? a.total : void 0, s = o - n, l = i(s), p = o <= r;
    n = o;
    const d = {
      loaded: o,
      total: r,
      progress: r ? o / r : void 0,
      bytes: s,
      rate: l || void 0,
      estimated: l && r && p ? (r - o) / l : void 0,
      event: a
    };
    d[t ? "download" : "upload"] = !0, e(d);
  };
}
const xo = typeof XMLHttpRequest < "u", bo = xo && function(e) {
  return new Promise(function(t, n) {
    let i = e.data;
    const a = H.from(e.headers).normalize(), o = e.responseType;
    let r;
    function s() {
      e.cancelToken && e.cancelToken.unsubscribe(r), e.signal && e.signal.removeEventListener("abort", r);
    }
    u.isFormData(i) && (B.isStandardBrowserEnv || B.isStandardBrowserWebWorkerEnv) && a.setContentType(!1);
    let l = new XMLHttpRequest();
    if (e.auth) {
      const h = e.auth.username || "", m = e.auth.password ? unescape(encodeURIComponent(e.auth.password)) : "";
      a.set("Authorization", "Basic " + btoa(h + ":" + m));
    }
    const p = $n(e.baseURL, e.url);
    l.open(e.method.toUpperCase(), Ln(p, e.params, e.paramsSerializer), !0), l.timeout = e.timeout;
    function d() {
      if (!l)
        return;
      const h = H.from(
        "getAllResponseHeaders" in l && l.getAllResponseHeaders()
      ), m = {
        data: !o || o === "text" || o === "json" ? l.responseText : l.response,
        status: l.status,
        statusText: l.statusText,
        headers: h,
        config: e,
        request: l
      };
      co(function(v) {
        t(v), s();
      }, function(v) {
        n(v), s();
      }, m), l = null;
    }
    if ("onloadend" in l ? l.onloadend = d : l.onreadystatechange = function() {
      !l || l.readyState !== 4 || l.status === 0 && !(l.responseURL && l.responseURL.indexOf("file:") === 0) || setTimeout(d);
    }, l.onabort = function() {
      !l || (n(new w("Request aborted", w.ECONNABORTED, e, l)), l = null);
    }, l.onerror = function() {
      n(new w("Network Error", w.ERR_NETWORK, e, l)), l = null;
    }, l.ontimeout = function() {
      let h = e.timeout ? "timeout of " + e.timeout + "ms exceeded" : "timeout exceeded";
      const m = e.transitional || In;
      e.timeoutErrorMessage && (h = e.timeoutErrorMessage), n(new w(
        h,
        m.clarifyTimeoutError ? w.ETIMEDOUT : w.ECONNABORTED,
        e,
        l
      )), l = null;
    }, B.isStandardBrowserEnv) {
      const h = (e.withCredentials || vo(p)) && e.xsrfCookieName && uo.read(e.xsrfCookieName);
      h && a.set(e.xsrfHeaderName, h);
    }
    i === void 0 && a.setContentType(null), "setRequestHeader" in l && u.forEach(a.toJSON(), function(h, m) {
      l.setRequestHeader(m, h);
    }), u.isUndefined(e.withCredentials) || (l.withCredentials = !!e.withCredentials), o && o !== "json" && (l.responseType = e.responseType), typeof e.onDownloadProgress == "function" && l.addEventListener("progress", on(e.onDownloadProgress, !0)), typeof e.onUploadProgress == "function" && l.upload && l.upload.addEventListener("progress", on(e.onUploadProgress)), (e.cancelToken || e.signal) && (r = (h) => {
      !l || (n(!h || h.type ? new le(null, e, l) : h), l.abort(), l = null);
    }, e.cancelToken && e.cancelToken.subscribe(r), e.signal && (e.signal.aborted ? r() : e.signal.addEventListener("abort", r)));
    const c = ho(p);
    if (c && B.protocols.indexOf(c) === -1) {
      n(new w("Unsupported protocol " + c + ":", w.ERR_BAD_REQUEST, e));
      return;
    }
    l.send(i || null);
  });
}, Fe = {
  http: po,
  xhr: bo
};
u.forEach(Fe, (e, t) => {
  if (e) {
    try {
      Object.defineProperty(e, "name", { value: t });
    } catch {
    }
    Object.defineProperty(e, "adapterName", { value: t });
  }
});
const wo = {
  getAdapter: (e) => {
    e = u.isArray(e) ? e : [e];
    const { length: t } = e;
    let n, i;
    for (let a = 0; a < t && (n = e[a], !(i = u.isString(n) ? Fe[n.toLowerCase()] : n)); a++)
      ;
    if (!i)
      throw i === !1 ? new w(
        `Adapter ${n} is not supported by the environment`,
        "ERR_NOT_SUPPORT"
      ) : new Error(
        u.hasOwnProp(Fe, n) ? `Adapter '${n}' is not available in the build` : `Unknown adapter '${n}'`
      );
    if (!u.isFunction(i))
      throw new TypeError("adapter is not a function");
    return i;
  },
  adapters: Fe
};
function ot(e) {
  if (e.cancelToken && e.cancelToken.throwIfRequested(), e.signal && e.signal.aborted)
    throw new le(null, e);
}
function rn(e) {
  return ot(e), e.headers = H.from(e.headers), e.data = at.call(
    e,
    e.transformRequest
  ), ["post", "put", "patch"].indexOf(e.method) !== -1 && e.headers.setContentType("application/x-www-form-urlencoded", !1), wo.getAdapter(e.adapter || Et.adapter)(e).then(function(t) {
    return ot(e), t.data = at.call(
      e,
      e.transformResponse,
      t
    ), t.headers = H.from(t.headers), t;
  }, function(t) {
    return Mn(t) || (ot(e), t && t.response && (t.response.data = at.call(
      e,
      e.transformResponse,
      t.response
    ), t.response.headers = H.from(t.response.headers))), Promise.reject(t);
  });
}
const sn = (e) => e instanceof H ? e.toJSON() : e;
function te(e, t) {
  t = t || {};
  const n = {};
  function i(p, d, c) {
    return u.isPlainObject(p) && u.isPlainObject(d) ? u.merge.call({ caseless: c }, p, d) : u.isPlainObject(d) ? u.merge({}, d) : u.isArray(d) ? d.slice() : d;
  }
  function a(p, d, c) {
    if (u.isUndefined(d)) {
      if (!u.isUndefined(p))
        return i(void 0, p, c);
    } else
      return i(p, d, c);
  }
  function o(p, d) {
    if (!u.isUndefined(d))
      return i(void 0, d);
  }
  function r(p, d) {
    if (u.isUndefined(d)) {
      if (!u.isUndefined(p))
        return i(void 0, p);
    } else
      return i(void 0, d);
  }
  function s(p, d, c) {
    if (c in t)
      return i(p, d);
    if (c in e)
      return i(void 0, p);
  }
  const l = {
    url: o,
    method: o,
    data: o,
    baseURL: r,
    transformRequest: r,
    transformResponse: r,
    paramsSerializer: r,
    timeout: r,
    timeoutMessage: r,
    withCredentials: r,
    adapter: r,
    responseType: r,
    xsrfCookieName: r,
    xsrfHeaderName: r,
    onUploadProgress: r,
    onDownloadProgress: r,
    decompress: r,
    maxContentLength: r,
    maxBodyLength: r,
    beforeRedirect: r,
    transport: r,
    httpAgent: r,
    httpsAgent: r,
    cancelToken: r,
    socketPath: r,
    responseEncoding: r,
    validateStatus: s,
    headers: (p, d) => a(sn(p), sn(d), !0)
  };
  return u.forEach(Object.keys(e).concat(Object.keys(t)), function(p) {
    const d = l[p] || a, c = d(e[p], t[p], p);
    u.isUndefined(c) && d !== s || (n[p] = c);
  }), n;
}
const Hn = "1.2.2", Ft = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach((e, t) => {
  Ft[e] = function(n) {
    return typeof n === e || "a" + (t < 1 ? "n " : " ") + e;
  };
});
const ln = {};
Ft.transitional = function(e, t, n) {
  function i(a, o) {
    return "[Axios v" + Hn + "] Transitional option '" + a + "'" + o + (n ? ". " + n : "");
  }
  return (a, o, r) => {
    if (e === !1)
      throw new w(
        i(o, " has been removed" + (t ? " in " + t : "")),
        w.ERR_DEPRECATED
      );
    return t && !ln[o] && (ln[o] = !0, console.warn(
      i(
        o,
        " has been deprecated since v" + t + " and will be removed in the near future"
      )
    )), e ? e(a, o, r) : !0;
  };
};
function yo(e, t, n) {
  if (typeof e != "object")
    throw new w("options must be an object", w.ERR_BAD_OPTION_VALUE);
  const i = Object.keys(e);
  let a = i.length;
  for (; a-- > 0; ) {
    const o = i[a], r = t[o];
    if (r) {
      const s = e[o], l = s === void 0 || r(s, o, e);
      if (l !== !0)
        throw new w("option " + o + " must be " + l, w.ERR_BAD_OPTION_VALUE);
      continue;
    }
    if (n !== !0)
      throw new w("Unknown option " + o, w.ERR_BAD_OPTION);
  }
}
const dt = {
  assertOptions: yo,
  validators: Ft
}, K = dt.validators;
class Ue {
  constructor(t) {
    this.defaults = t, this.interceptors = {
      request: new tn(),
      response: new tn()
    };
  }
  request(t, n) {
    typeof t == "string" ? (n = n || {}, n.url = t) : n = t || {}, n = te(this.defaults, n);
    const { transitional: i, paramsSerializer: a, headers: o } = n;
    i !== void 0 && dt.assertOptions(i, {
      silentJSONParsing: K.transitional(K.boolean),
      forcedJSONParsing: K.transitional(K.boolean),
      clarifyTimeoutError: K.transitional(K.boolean)
    }, !1), a !== void 0 && dt.assertOptions(a, {
      encode: K.function,
      serialize: K.function
    }, !0), n.method = (n.method || this.defaults.method || "get").toLowerCase();
    let r;
    r = o && u.merge(
      o.common,
      o[n.method]
    ), r && u.forEach(
      ["delete", "get", "head", "post", "put", "patch", "common"],
      (v) => {
        delete o[v];
      }
    ), n.headers = H.concat(r, o);
    const s = [];
    let l = !0;
    this.interceptors.request.forEach(function(v) {
      typeof v.runWhen == "function" && v.runWhen(n) === !1 || (l = l && v.synchronous, s.unshift(v.fulfilled, v.rejected));
    });
    const p = [];
    this.interceptors.response.forEach(function(v) {
      p.push(v.fulfilled, v.rejected);
    });
    let d, c = 0, h;
    if (!l) {
      const v = [rn.bind(this), void 0];
      for (v.unshift.apply(v, s), v.push.apply(v, p), h = v.length, d = Promise.resolve(n); c < h; )
        d = d.then(v[c++], v[c++]);
      return d;
    }
    h = s.length;
    let m = n;
    for (c = 0; c < h; ) {
      const v = s[c++], E = s[c++];
      try {
        m = v(m);
      } catch (k) {
        E.call(this, k);
        break;
      }
    }
    try {
      d = rn.call(this, m);
    } catch (v) {
      return Promise.reject(v);
    }
    for (c = 0, h = p.length; c < h; )
      d = d.then(p[c++], p[c++]);
    return d;
  }
  getUri(t) {
    t = te(this.defaults, t);
    const n = $n(t.baseURL, t.url);
    return Ln(n, t.params, t.paramsSerializer);
  }
}
u.forEach(["delete", "get", "head", "options"], function(e) {
  Ue.prototype[e] = function(t, n) {
    return this.request(te(n || {}, {
      method: e,
      url: t,
      data: (n || {}).data
    }));
  };
});
u.forEach(["post", "put", "patch"], function(e) {
  function t(n) {
    return function(i, a, o) {
      return this.request(te(o || {}, {
        method: e,
        headers: n ? {
          "Content-Type": "multipart/form-data"
        } : {},
        url: i,
        data: a
      }));
    };
  }
  Ue.prototype[e] = t(), Ue.prototype[e + "Form"] = t(!0);
});
const Re = Ue;
class Rt {
  constructor(t) {
    if (typeof t != "function")
      throw new TypeError("executor must be a function.");
    let n;
    this.promise = new Promise(function(a) {
      n = a;
    });
    const i = this;
    this.promise.then((a) => {
      if (!i._listeners)
        return;
      let o = i._listeners.length;
      for (; o-- > 0; )
        i._listeners[o](a);
      i._listeners = null;
    }), this.promise.then = (a) => {
      let o;
      const r = new Promise((s) => {
        i.subscribe(s), o = s;
      }).then(a);
      return r.cancel = function() {
        i.unsubscribe(o);
      }, r;
    }, t(function(a, o, r) {
      i.reason || (i.reason = new le(a, o, r), n(i.reason));
    });
  }
  throwIfRequested() {
    if (this.reason)
      throw this.reason;
  }
  subscribe(t) {
    if (this.reason) {
      t(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(t) : this._listeners = [t];
  }
  unsubscribe(t) {
    if (!this._listeners)
      return;
    const n = this._listeners.indexOf(t);
    n !== -1 && this._listeners.splice(n, 1);
  }
  static source() {
    let t;
    return {
      token: new Rt(function(n) {
        t = n;
      }),
      cancel: t
    };
  }
}
const ko = Rt;
function Eo(e) {
  return function(t) {
    return e.apply(null, t);
  };
}
function Fo(e) {
  return u.isObject(e) && e.isAxiosError === !0;
}
const ut = {
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
Object.entries(ut).forEach(([e, t]) => {
  ut[t] = e;
});
const Ro = ut;
function Vn(e) {
  const t = new Re(e), n = Un(Re.prototype.request, t);
  return u.extend(n, Re.prototype, t, { allOwnKeys: !0 }), u.extend(n, t, null, { allOwnKeys: !0 }), n.create = function(i) {
    return Vn(te(e, i));
  }, n;
}
const F = Vn(Et);
F.Axios = Re;
F.CanceledError = le;
F.CancelToken = ko;
F.isCancel = Mn;
F.VERSION = Hn;
F.toFormData = Ae;
F.AxiosError = w;
F.Cancel = F.CanceledError;
F.all = function(e) {
  return Promise.all(e);
};
F.spread = Eo;
F.isAxiosError = Fo;
F.mergeConfig = te;
F.AxiosHeaders = H;
F.formToJSON = (e) => Bn(u.isHTMLForm(e) ? new FormData(e) : e);
F.HttpStatusCode = Ro;
F.default = F;
const C = F, _ = "";
class Po extends Error {
  constructor(t, n) {
    super(n), this.field = t, this.name = "RequiredError";
  }
}
const T = "https://example.com", b = function(e, t, n) {
  if (n == null)
    throw new Po(t, `Required parameter ${t} was null or undefined when calling ${e}.`);
}, A = async function(e, t, n, i) {
  if (i && i.accessToken) {
    const a = typeof i.accessToken == "function" ? await i.accessToken(t, n) : await i.accessToken;
    e.Authorization = "Bearer " + a;
  }
};
function mt(e, t, n = "") {
  typeof t == "object" ? Array.isArray(t) ? t.forEach((i) => mt(e, i, n)) : Object.keys(t).forEach(
    (i) => mt(e, t[i], `${n}${n !== "" ? "." : ""}${i}`)
  ) : e.has(n) ? e.append(n, t) : e.set(n, t);
}
const D = function(e, ...t) {
  const n = new URLSearchParams(e.search);
  mt(n, t), e.search = n.toString();
}, N = function(e) {
  return e.pathname + e.search + e.hash;
}, z = function(e, t, n, i) {
  return (a = t, o = n) => {
    const r = { ...e.options, url: ((i == null ? void 0 : i.basePath) || o) + e.url };
    return a.request(r);
  };
}, Uo = function(e) {
  return {
    getFileNames: async (t, n, i = {}) => {
      b("getFileNames", "taskId", t), b("getFileNames", "filePath", n);
      const a = "/rest/task/file/{taskId}".replace("{taskId}", encodeURIComponent(String(t))), o = new URL(a, T);
      let r;
      e && (r = e.baseOptions);
      const s = { method: "GET", ...r, ...i }, l = {}, p = {};
      await A(l, "spring_oauth", [], e), n !== void 0 && (p.filePath = n), D(o, p);
      let d = r && r.headers ? r.headers : {};
      return s.headers = { ...l, ...d, ...i.headers }, {
        url: N(o),
        options: s
      };
    },
    getPresignedUrlForFileDeletion: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDeletion", "taskId", t), b("getPresignedUrlForFileDeletion", "filename", n), b("getPresignedUrlForFileDeletion", "filePath", i);
      const o = "/rest/task/file/{taskId}/{filename}".replace("{taskId}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "DELETE", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileDownload: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDownload", "taskId", t), b("getPresignedUrlForFileDownload", "fileName", n), b("getPresignedUrlForFileDownload", "filePath", i);
      const o = "/rest/task/file/{taskId}/{fileName}".replace("{taskId}", encodeURIComponent(String(t))).replace("{fileName}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "GET", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileUpload: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileUpload", "taskId", t), b("getPresignedUrlForFileUpload", "filename", n), b("getPresignedUrlForFileUpload", "filePath", i);
      const o = "/rest/task/file/{taskId}/{filename}".replace("{taskId}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "POST", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    }
  };
}, jo = function(e) {
  const t = Uo(e);
  return {
    async getFileNames(n, i, a) {
      const o = await t.getFileNames(n, i, a);
      return z(o, C, _, e);
    },
    async getPresignedUrlForFileDeletion(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDeletion(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileDownload(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDownload(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileUpload(n, i, a, o) {
      const r = await t.getPresignedUrlForFileUpload(n, i, a, o);
      return z(r, C, _, e);
    }
  };
}, he = function(e, t, n) {
  const i = jo(e);
  return {
    getFileNames(a, o, r) {
      return i.getFileNames(a, o, r).then((s) => s(n, t));
    },
    getPresignedUrlForFileDeletion(a, o, r, s) {
      return i.getPresignedUrlForFileDeletion(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileDownload(a, o, r, s) {
      return i.getPresignedUrlForFileDownload(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileUpload(a, o, r, s) {
      return i.getPresignedUrlForFileUpload(a, o, r, s).then((l) => l(n, t));
    }
  };
}, Oo = function(e) {
  return {
    getFileNames2: async (t, n, i = {}) => {
      b("getFileNames2", "instanceId", t), b("getFileNames2", "filePath", n);
      const a = "/rest/service/instance/file/{instanceId}".replace("{instanceId}", encodeURIComponent(String(t))), o = new URL(a, T);
      let r;
      e && (r = e.baseOptions);
      const s = { method: "GET", ...r, ...i }, l = {}, p = {};
      await A(l, "spring_oauth", [], e), n !== void 0 && (p.filePath = n), D(o, p);
      let d = r && r.headers ? r.headers : {};
      return s.headers = { ...l, ...d, ...i.headers }, {
        url: N(o),
        options: s
      };
    },
    getPresignedUrlForFileDeletion2: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDeletion2", "instanceId", t), b("getPresignedUrlForFileDeletion2", "filename", n), b("getPresignedUrlForFileDeletion2", "filePath", i);
      const o = "/rest/service/instance/file/{instanceId}/{filename}".replace("{instanceId}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "DELETE", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileDownload2: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDownload2", "instanceId", t), b("getPresignedUrlForFileDownload2", "fileName", n), b("getPresignedUrlForFileDownload2", "filePath", i);
      const o = "/rest/service/instance/file/{instanceId}/{fileName}".replace("{instanceId}", encodeURIComponent(String(t))).replace("{fileName}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "GET", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileUpload2: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileUpload2", "instanceId", t), b("getPresignedUrlForFileUpload2", "filename", n), b("getPresignedUrlForFileUpload2", "filePath", i);
      const o = "/rest/service/instance/file/{instanceId}/{filename}".replace("{instanceId}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "POST", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    }
  };
}, So = function(e) {
  const t = Oo(e);
  return {
    async getFileNames2(n, i, a) {
      const o = await t.getFileNames2(n, i, a);
      return z(o, C, _, e);
    },
    async getPresignedUrlForFileDeletion2(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDeletion2(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileDownload2(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDownload2(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileUpload2(n, i, a, o) {
      const r = await t.getPresignedUrlForFileUpload2(n, i, a, o);
      return z(r, C, _, e);
    }
  };
}, ge = function(e, t, n) {
  const i = So(e);
  return {
    getFileNames2(a, o, r) {
      return i.getFileNames2(a, o, r).then((s) => s(n, t));
    },
    getPresignedUrlForFileDeletion2(a, o, r, s) {
      return i.getPresignedUrlForFileDeletion2(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileDownload2(a, o, r, s) {
      return i.getPresignedUrlForFileDownload2(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileUpload2(a, o, r, s) {
      return i.getPresignedUrlForFileUpload2(a, o, r, s).then((l) => l(n, t));
    }
  };
}, Co = function(e) {
  return {
    getFileNames1: async (t, n, i = {}) => {
      b("getFileNames1", "definitionKey", t), b("getFileNames1", "filePath", n);
      const a = "/rest/service/start/file/{definitionKey}".replace("{definitionKey}", encodeURIComponent(String(t))), o = new URL(a, T);
      let r;
      e && (r = e.baseOptions);
      const s = { method: "GET", ...r, ...i }, l = {}, p = {};
      await A(l, "spring_oauth", [], e), n !== void 0 && (p.filePath = n), D(o, p);
      let d = r && r.headers ? r.headers : {};
      return s.headers = { ...l, ...d, ...i.headers }, {
        url: N(o),
        options: s
      };
    },
    getPresignedUrlForFileDeletion1: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDeletion1", "definitionKey", t), b("getPresignedUrlForFileDeletion1", "filename", n), b("getPresignedUrlForFileDeletion1", "filePath", i);
      const o = "/rest/service/start/file/{definitionKey}/{filename}".replace("{definitionKey}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "DELETE", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileDownload1: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileDownload1", "definitionKey", t), b("getPresignedUrlForFileDownload1", "fileName", n), b("getPresignedUrlForFileDownload1", "filePath", i);
      const o = "/rest/service/start/file/{definitionKey}/{fileName}".replace("{definitionKey}", encodeURIComponent(String(t))).replace("{fileName}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "GET", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    },
    getPresignedUrlForFileUpload1: async (t, n, i, a = {}) => {
      b("getPresignedUrlForFileUpload1", "definitionKey", t), b("getPresignedUrlForFileUpload1", "filename", n), b("getPresignedUrlForFileUpload1", "filePath", i);
      const o = "/rest/service/start/file/{definitionKey}/{filename}".replace("{definitionKey}", encodeURIComponent(String(t))).replace("{filename}", encodeURIComponent(String(n))), r = new URL(o, T);
      let s;
      e && (s = e.baseOptions);
      const l = { method: "POST", ...s, ...a }, p = {}, d = {};
      await A(p, "spring_oauth", [], e), i !== void 0 && (d.filePath = i), D(r, d);
      let c = s && s.headers ? s.headers : {};
      return l.headers = { ...p, ...c, ...a.headers }, {
        url: N(r),
        options: l
      };
    }
  };
}, _o = function(e) {
  const t = Co(e);
  return {
    async getFileNames1(n, i, a) {
      const o = await t.getFileNames1(n, i, a);
      return z(o, C, _, e);
    },
    async getPresignedUrlForFileDeletion1(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDeletion1(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileDownload1(n, i, a, o) {
      const r = await t.getPresignedUrlForFileDownload1(n, i, a, o);
      return z(r, C, _, e);
    },
    async getPresignedUrlForFileUpload1(n, i, a, o) {
      const r = await t.getPresignedUrlForFileUpload1(n, i, a, o);
      return z(r, C, _, e);
    }
  };
}, xe = function(e, t, n) {
  const i = _o(e);
  return {
    getFileNames1(a, o, r) {
      return i.getFileNames1(a, o, r).then((s) => s(n, t));
    },
    getPresignedUrlForFileDeletion1(a, o, r, s) {
      return i.getPresignedUrlForFileDeletion1(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileDownload1(a, o, r, s) {
      return i.getPresignedUrlForFileDownload1(a, o, r, s).then((l) => l(n, t));
    },
    getPresignedUrlForFileUpload1(a, o, r, s) {
      return i.getPresignedUrlForFileUpload1(a, o, r, s).then((l) => l(n, t));
    }
  };
};
class To {
  constructor(t = {}) {
    this.apiKey = t.apiKey, this.username = t.username, this.password = t.password, this.accessToken = t.accessToken, this.basePath = t.basePath, this.baseOptions = t.baseOptions, this.formDataCtor = t.formDataCtor;
  }
  isJsonMime(t) {
    const n = new RegExp("^(application/json|[^;/ 	]+/[^;/ 	]+[+]json)[ 	]*(;.*)?$", "i");
    return t !== null && (n.test(t) || t.toLowerCase() === "application/json-patch+json");
  }
}
var Pe = /* @__PURE__ */ ((e) => (e.INFO = "info", e.WARNING = "warning", e.ERROR = "error", e))(Pe || {});
class be extends Error {
  constructor({ level: t = "error", message: n = "Ein unbekannter Fehler ist aufgetreten, bitte den Administrator informieren." }) {
    super(n), this.stack = new Error().stack, this.level = t, this.message = n;
  }
}
class W {
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
  static getAxiosConfig(t) {
    const n = new To();
    return n.baseOptions = t, n;
  }
  static getPOSTConfig(t) {
    return {
      method: "POST",
      body: t ? JSON.stringify(t) : void 0,
      headers: W.getHeaders(),
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getPUTConfig(t) {
    const n = W.getHeaders();
    return t.version && n.append("If-Match", t.version), {
      method: "PUT",
      body: t ? JSON.stringify(t) : void 0,
      headers: n,
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static defaultResponseHandler(t, n = "Es ist ein unbekannter Fehler aufgetreten.", i) {
    if (!t.ok)
      throw i && i[t.status] && i[t.status](t), t.status === 403 ? new be({
        level: Pe.ERROR,
        message: "Sie haben nicht die n\xF6tigen Rechte um diese Aktion durchzuf\xFChren."
      }) : (t.type === "opaqueredirect" && location.reload(), new be({
        level: Pe.WARNING,
        message: n
      }));
  }
  static defaultCatchHandler(t, n = "Es ist ein unbekannter Fehler aufgetreten.") {
    throw t instanceof be ? t : new be({
      level: Pe.WARNING,
      message: n
    });
  }
  static getPATCHConfig(t) {
    const n = W.getHeaders();
    return t.version !== void 0 && n.append("If-Match", t.version), {
      method: "PATCH",
      body: t ? JSON.stringify(t) : void 0,
      headers: n,
      mode: "cors",
      credentials: "same-origin",
      redirect: "manual"
    };
  }
  static getHeaders() {
    const t = new Headers({
      "Content-Type": "application/json"
    }), n = this._getXSRFToken();
    return n !== "" && t.append("X-XSRF-TOKEN", n), t;
  }
  static _getXSRFToken() {
    const t = document.cookie.match("(^|;)\\s*XSRF-TOKEN\\s*=\\s*([^;]+)");
    return t ? t.pop() : "";
  }
}
const Ao = cn({
  props: [
    "valid",
    "readonly",
    "hasFocused",
    "value",
    "options",
    "schema",
    "fullKey",
    "dense",
    "label",
    "disabled",
    "rules",
    "on"
  ],
  setup(e) {
    let t = "", n = fe(null), i = {}, a = fe([]), o = fe(""), r = fe(!1), s = "";
    const l = Ut("apiEndpoint"), p = Ut("formContext"), d = (f) => {
      if (!!e.on)
        return e.schema.uuidEnabled ? e.on.input({
          key: s,
          amount: f
        }) : e.on.input({ amount: f });
    }, c = ee(() => e.disabled || e.readonly || e.schema.readOnly || r.value), h = ee(() => a.value.length < 10), m = ee(() => {
      let f = e.schema.filePath ? e.schema.filePath : "";
      return e.schema.uuidEnabled && (f = f !== "" ? f + "/" + s : s), f;
    }), v = async () => {
      try {
        r.value = !0;
        const f = await ce();
        for (const g of f)
          await E(g);
        o.value = "", a.value.length > 0 && (n.value = [], n.value.push(new File([""], a.value[0].name)), d(a.value.length));
      } catch {
        o.value = "Die Dateien konnten nicht geladen werden.";
      }
      r.value = !1;
    }, E = async (f) => {
      const g = await ue(f), y = await nt.get(g, {
        responseType: "arraybuffer"
      });
      let U = Z(y.data), I = k(U);
      const qe = x(
        f,
        q(f),
        me(U),
        I
      );
      a.value.push(qe);
    }, k = (f) => Pt(f) ? window.atob(f).length : f.length, q = (f) => {
      const g = ci.getType(f);
      return g || "plain/text";
    }, L = async (f, g) => {
      const y = new Date().getTime();
      r.value = !0;
      try {
        r.value = !0, pe(f);
        const U = await de(g);
        await nt.put(U, f);
        let I = Z(f);
        const qe = x(
          g.name,
          g.type,
          me(I),
          f.byteLength
        );
        a.value.push(qe), o.value = "", r.value = !1, d(a.value.length);
      } catch (U) {
        U.response && U.response.status && U.response.status == 409 ? o.value = "Das Dokument existiert bereits." : o.value || (o.value = "Das Dokument konnte nicht hochgeladen werden."), setTimeout(() => {
          r.value = !1;
        }, Math.max(0, 5e3 - (new Date().getTime() - y)));
      }
      r.value = !1;
    }, pe = (f) => {
      if (f.byteLength > 10485760)
        throw o.value = "Die Datei ist muss kleiner als 10MB sein.", new Error("File too large.");
    }, x = (f, g, y, U) => ({
      type: g,
      name: f,
      data: ze(g, y),
      size: U
    }), ze = (f, g) => `data:${f};base64, ${g}`, ie = () => {
      const f = W.getAxiosConfig(W.getGETConfig());
      return f.baseOptions.headers = { "Content-Type": "application/json" }, f.basePath = l, f;
    }, ce = async () => {
      const f = ie();
      let g;
      return p.type === "start" ? g = await xe(f).getFileNames1(
        p.id,
        m.value
      ) : p.type == "task" ? g = await he(f).getFileNames(
        p.id,
        m.value
      ) : g = await ge(f).getFileNames2(
        p.id,
        m.value
      ), g.data;
    }, de = async (f) => {
      const g = ie();
      let y;
      return p.type === "start" ? y = await xe(g).getPresignedUrlForFileUpload1(
        p.id,
        f.name,
        m.value
      ) : p.type == "task" ? y = await he(g).getPresignedUrlForFileUpload(
        p.id,
        f.name,
        m.value
      ) : y = await ge(g).getPresignedUrlForFileUpload2(
        p.id,
        f.name,
        m.value
      ), y.data;
    }, ue = async (f) => {
      const g = ie();
      let y;
      return p.type === "start" ? y = await xe(
        g
      ).getPresignedUrlForFileDownload1(
        p.id,
        f,
        m.value
      ) : p.type == "task" ? y = await he(
        g
      ).getPresignedUrlForFileDownload(
        p.id,
        f,
        m.value
      ) : y = await ge(g).getPresignedUrlForFileDownload2(
        p.id,
        f,
        m.value
      ), y.data;
    }, O = async (f) => {
      const g = W.getAxiosConfig(W.getDELETEConfig());
      g.basePath = l;
      let y;
      return p.type === "start" ? y = await xe(
        g
      ).getPresignedUrlForFileDeletion1(
        p.id,
        f,
        m.value
      ) : p.type == "task" ? y = await he(
        g
      ).getPresignedUrlForFileDeletion(
        p.id,
        f,
        m.value
      ) : y = await ge(g).getPresignedUrlForFileDeletion2(
        p.id,
        f,
        m.value
      ), y.data;
    }, J = () => {
      !n.value || (o.value = "", n.value.forEach((f) => {
        const g = new FileReader();
        g.onload = (y) => {
          var U;
          try {
            L((U = y.target) == null ? void 0 : U.result, f);
          } catch (I) {
            o = I.message;
          }
        }, g.readAsArrayBuffer(f);
      }));
    }, $ = async (f) => {
      for (let g = 0; g < a.value.length; g++)
        if (a.value[g].name == f.name)
          try {
            const y = await O(
              f.name
            );
            await nt.delete(y), a.value.splice(g, 1), a.value.length == 0 && (n.value = null);
            break;
          } catch {
            o.value = "Die Datei konnte nicht gel\xF6scht werden.";
          }
      d(a.value.length);
    }, me = (f) => Pt(f) ? f : window.btoa(f), Z = (f) => {
      let g = "";
      const y = new Uint8Array(f), U = y.byteLength;
      for (let I = 0; I < U; I++)
        g += String.fromCharCode(y[I]);
      return g;
    }, Pt = (f) => {
      var g = /^([0-9a-zA-Z+/]{4})*(([0-9a-zA-Z+/]{2}==)|([0-9a-zA-Z+/]{3}=))?$/;
      return g.test(f);
    };
    return Qn(() => {
      if (!p.id) {
        o.value = "no contextId";
        return;
      }
      e.schema.uuidEnabled && (e.value && e.value.key ? s = e.value.key : s = da()), v();
    }), {
      model: t,
      fileValue: n,
      data: i,
      documents: a,
      errorMessage: o,
      isLoading: r,
      uuid: s,
      changeInput: J,
      canAddDocument: h,
      isReadonly: c,
      removeDocument: $
    };
  }
});
var Do = function() {
  var t = this, n = t._self._c;
  return t._self._setupProxy, n("div", { staticClass: "pa-0" }, [n(Xn, t._b({ attrs: { disabled: t.isReadonly || !t.canAddDocument, "error-messages": t.errorMessage, label: t.label, loading: t.isLoading, rules: t.rules ? t.rules : !0, multiple: "", outlined: "", "truncate-length": "50", type: "file" }, on: { change: t.changeInput }, scopedSlots: t._u([{ key: "append-outer", fn: function() {
    return [t.schema.description ? n(Zn, { attrs: { "open-on-hover": !1, left: "" }, scopedSlots: t._u([{ key: "activator", fn: function({ on: i }) {
      return [n(pn, { attrs: { icon: "", "retain-focus-on-click": "" }, on: { blur: i.blur, click: i.click } }, [n(rt, [t._v(" mdi-information")])], 1)];
    } }], null, !1, 3199788639) }, [n("div", { staticClass: "tooltip" }, [t._v(t._s(t.schema.description))])]) : t._e()];
  }, proxy: !0 }]), model: { value: t.fileValue, callback: function(i) {
    t.fileValue = i;
  }, expression: "fileValue" } }, "v-file-input", t.schema["x-props"], !1)), t.documents && t.documents.length > 0 ? n("div", { staticClass: "listWrapper" }, [t._l(t.documents, function(i) {
    return [n(ai, { key: i.name, attrs: { document: i, readonly: t.isReadonly }, on: { "remove-document": t.removeDocument } })];
  })], 2) : t._e(), n("div", { staticStyle: { clear: "both" } })], 1);
}, No = [], zo = /* @__PURE__ */ dn(
  Ao,
  Do,
  No,
  !1,
  null,
  "0a83f5ed",
  null,
  null
);
const Io = zo.exports;
export {
  Io as DwfMultiFileInput
};
