(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d560326"],{a537:function(t,e,o){"use strict";o("f6ea")},b0c0:function(t,e,o){var r=o("83ab"),n=o("9bf2").f,u=Function.prototype,a=u.toString,s=/^\s*function ([^ (]*)/,c="name";r&&!(c in u)&&n(u,c,{configurable:!0,get:function(){try{return a.call(this).match(s)[1]}catch(t){return""}}})},c9de:function(t,e,o){"use strict";o.r(e);var r=function(){var t=this,e=t.$createElement,o=t._self._c||e;return t.currentRoute?o("div",{staticClass:"edit-form"},[o("h4",[t._v("Route")]),o("form",[o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"title"}},[t._v("Name")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.name,expression:"currentRoute.name"}],staticClass:"form-control",attrs:{type:"text",id:"name"},domProps:{value:t.currentRoute.name},on:{input:function(e){e.target.composing||t.$set(t.currentRoute,"name",e.target.value)}}})]),o("hr"),o("div",{staticClass:"form-group"},[o("h5",[t._v("Coordinates")]),o("label",{attrs:{for:"x"}},[t._v("X")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.coordinates.x,expression:"currentRoute.coordinates.x"}],staticClass:"form-control",attrs:{type:"number",id:"x"},domProps:{value:t.currentRoute.coordinates.x},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.coordinates,"x",e.target.value)}}}),o("label",{attrs:{for:"x"}},[t._v("Y")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.coordinates.y,expression:"currentRoute.coordinates.y"}],staticClass:"form-control",attrs:{type:"number",id:"y"},domProps:{value:t.currentRoute.coordinates.y},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.coordinates,"y",e.target.value)}}})]),o("hr"),o("div",{staticClass:"form-group"},[o("h5",[t._v("Location (from)")]),o("label",{attrs:{for:"from_x"}},[t._v("X")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.from.x,expression:"currentRoute.from.x"}],staticClass:"form-control",attrs:{type:"number",id:"from_x"},domProps:{value:t.currentRoute.from.x},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.from,"x",e.target.value)}}}),o("label",{attrs:{for:"from_y"}},[t._v("Y")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.from.y,expression:"currentRoute.from.y"}],staticClass:"form-control",attrs:{type:"number",id:"from_y"},domProps:{value:t.currentRoute.from.y},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.from,"y",e.target.value)}}}),o("label",{attrs:{for:"from_z"}},[t._v("Z")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.from.z,expression:"currentRoute.from.z"}],staticClass:"form-control",attrs:{type:"number",id:"from_z"},domProps:{value:t.currentRoute.from.z},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.from,"z",e.target.value)}}}),o("label",{attrs:{for:"from_name"}},[t._v("Name")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.from.name,expression:"currentRoute.from.name"}],staticClass:"form-control",attrs:{type:"text",id:"from_name"},domProps:{value:t.currentRoute.from.name},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.from,"name",e.target.value)}}})]),o("hr"),o("div",{staticClass:"form-group"},[o("h5",[t._v("Location (to)")]),o("label",{attrs:{for:"to_x"}},[t._v("X")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.to.x,expression:"currentRoute.to.x"}],staticClass:"form-control",attrs:{type:"number",id:"to_x"},domProps:{value:t.currentRoute.to.x},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.to,"x",e.target.value)}}}),o("label",{attrs:{for:"to_y"}},[t._v("Y")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.to.y,expression:"currentRoute.to.y"}],staticClass:"form-control",attrs:{type:"number",id:"to_y"},domProps:{value:t.currentRoute.to.y},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.to,"y",e.target.value)}}}),o("label",{attrs:{for:"to_z"}},[t._v("Z")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.to.z,expression:"currentRoute.to.z"}],staticClass:"form-control",attrs:{type:"number",id:"to_z"},domProps:{value:t.currentRoute.to.z},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.to,"z",e.target.value)}}}),o("label",{attrs:{for:"to_name"}},[t._v("Name")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.currentRoute.to.name,expression:"currentRoute.to.name"}],staticClass:"form-control",attrs:{type:"text",id:"to_name"},domProps:{value:t.currentRoute.to.name},on:{input:function(e){e.target.composing||t.$set(t.currentRoute.to,"name",e.target.value)}}})])]),o("button",{staticClass:"badge badge-success mr-2",attrs:{type:"submit"},on:{click:t.updateRoute}},[t._v(" Update ")]),o("button",{staticClass:"badge badge-danger mr-2 mb-5",on:{click:t.deleteRoute}},[t._v(" Delete ")])]):o("div",[o("br"),o("p",[t._v("Please click on a route")])])},n=[],u=(o("b0c0"),o("ca0e")),a={name:"route",data:function(){return{currentRoute:null}},methods:{getRoute:function(t){var e=this;u["a"].getRoute(t).then((function(t){e.currentRoute=t.data,console.log(t.data)})).catch((function(t){console.log(t)}))},updateRoute:function(){u["a"].updateRoute(this.currentRoute.id,this.currentRoute.name,this.currentRoute.coordinates.x,this.currentRoute.coordinates.y,this.currentRoute.from.x,this.currentRoute.from.y,this.currentRoute.from.z,this.currentRoute.from.name,this.currentRoute.to.x,this.currentRoute.to.y,this.currentRoute.to.z,this.currentRoute.to.name).then((function(t){console.log(t.data),alert("Route was updated successfully!")})).catch((function(t){console.log(t)}))},deleteRoute:function(){var t=this;u["a"].deleteRoute(this.currentRoute.id).then((function(e){console.log(e.data),t.$router.push({name:"routes"})})).catch((function(t){console.log(t)}))}},mounted:function(){this.getRoute(this.$route.params.id)}},s=a,c=(o("a537"),o("2877")),i=Object(c["a"])(s,r,n,!1,null,null,null);e["default"]=i.exports},f6ea:function(t,e,o){}}]);
//# sourceMappingURL=chunk-3d560326.5c3dd604.js.map