(this.webpackJsonppt_web=this.webpackJsonppt_web||[]).push([[0],{103:function(e,t,a){},104:function(e,t,a){},105:function(e,t,a){},118:function(e,t,a){},121:function(e,t,a){},122:function(e,t,a){},124:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),c=a(8),o=a.n(c),i=(a(95),a(61)),s=a(12),l=a(7),m=(a(96),"NEW_USER"),u="USER_CREATED",p="CREATE_ROOM",f="ROOM_CREATED",E="JOIN_ROOM",d="ROOM_JOINED",g="NEW_QUESTION",O="ANSWER",b="GET_ROOMS",A="START",v="SCORE",h="USER_LEFT",j="USER_JOIN",y="NEW_ROOM",C="TOP_SCORE",S="LEAVE_ROOM",I="DELETE_ROOM",Q="WRONG_QUESTIONS",P=1,w=2,N=3,D=4,R=5,J=6,T=(a(97),a(125)),k=a(36),M=a(73),L=a.n(M),U=a(45),B=F(),z=!0,Y=1500,G=setInterval(x,Y);function x(){if(B.readyState===WebSocket.CLOSED||B.readyState===WebSocket.CLOSING){console.log("Attempting to connect...");try{B=F()}catch(e){}}}function F(){var e=new WebSocket("wss://pt-quiz-it.herokuapp.com/websocket/chat");return e.onopen=function(e){return console.log("Connected"),void(!0===z&&(clearInterval(G),z=!1))},e.onclose=function(e){return console.log("Disconnected"),void(!0!==z&&(G=setInterval(x,Y)))},e.onmessage=function(e){return function(e){var t=JSON.parse(e.data),a=new CustomEvent(t.type,{detail:t.data});"PING"===t.type&&K({type:"PONG",data:void 0});document.dispatchEvent(a)}(e)},e}function V(){K({type:b,data:void 0})}function K(e){B.send(JSON.stringify(e))}var Z=a(155),q=a(170);function W(e){Object(U.a)(e);var t=Object(n.useState)(""),a=Object(l.a)(t,2),c=a[0],o=a[1],i=Object(n.useState)(!1),s=Object(l.a)(i,2),u=s[0],p=s[1],f=Object(n.useState)(""),E=Object(l.a)(f,2),d=E[0],g=E[1],O=function(e){return o(e.trim()),e.trim().length>0?(p(!1),g(""),!0):(p(!0),g("O nome n\xe3o pode estar vazio!"),!1)},b=function(e){return O(c)&&function(e){K({type:m,data:e})}(c),e.preventDefault(),!1};return r.a.createElement("div",{className:"form-container"},r.a.createElement("div",{className:"input-container"},r.a.createElement("form",{onSubmit:function(e){return b(e)},noValidate:!0,autoComplete:"off"},r.a.createElement(q.a,{error:u,helperText:d,className:"text-input",onChange:function(e){return O(e.target.value)},label:"Nome",variant:"outlined"}))),r.a.createElement("div",{className:"submit-container"},r.a.createElement(Z.a,{className:"submit-btn",variant:"contained",color:"primary",onClick:function(e){return b(e)}},"Iniciar")))}var X=a(30);function H(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function _(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),m=i[0],p=i[1];function f(e){a(function(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?H(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):H(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}({},t,{id:e.detail.id,username:e.detail.name,currentPage:P})),it(p,c,"/rooms")}Object(n.useEffect)((function(){return document.addEventListener(u,f),function(){document.removeEventListener(u,f)}}),[]);var E="username-page page animated ";return E+=m?"zoomOut fast":"zoomIn",r.a.createElement("div",{className:E},r.a.createElement("img",{className:"logo",src:L.a}),r.a.createElement(T.a,{className:"container"},r.a.createElement("div",{className:"title-container"},r.a.createElement(k.a,{variant:"h4",component:"h3"},"Escolhe um nome:")),r.a.createElement(W,null)))}a(103);var $=a(154),ee=a(159),te=a(160),ae=a(157);function ne(e){Object(U.a)(e);var t=Object(n.useState)(""),a=Object(l.a)(t,2),c=a[0],o=a[1],i=Object(n.useState)(!1),s=Object(l.a)(i,2),m=s[0],u=s[1],f=Object(n.useState)(""),E=Object(l.a)(f,2),d=E[0],g=E[1],O=function(e){return o(e.trim()),e.trim().length>0?(g(""),u(!1),!0):(u(!0),g("O nome da sala n\xe3o pode estar vazio."),!1)},b=function(e){return O(c)&&K({type:p,data:c}),e.preventDefault(),!1};return r.a.createElement("div",{className:"create-room-container"},r.a.createElement("form",{onSubmit:function(e){return b(e)},noValidate:!0,autoComplete:"off"},r.a.createElement(q.a,{error:m,helperText:d,onChange:function(e){return O(e.target.value)},className:"text-input",label:"Nome da sala",variant:"outlined"})),r.a.createElement(Z.a,{className:"submit-btn",variant:"contained",color:"primary",onClick:function(e){return b(e)}},"Criar"))}var re=a(156),ce=a(158);function oe(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function ie(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?oe(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):oe(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function se(e){var t=e.rooms,a=e.clicked;return r.a.createElement($.a,{component:"nav",className:"container"},t.map((function(e){return r.a.createElement(me,{key:e.id,room:e,clicked:a})})))}function le(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),s=i[0],m=i[1];function u(e){a(ie({},t,{rooms:e.detail}))}function p(e){t.rooms.push(e.detail),a(ie({},t,{rooms:t.rooms}))}function g(e){a(ie({},t,{room:e.detail,currentPage:w})),it(m,c,"/room")}function O(e){a(ie({},t,{room:e.detail,currentPage:w})),it(m,c,"/room")}Object(n.useEffect)((function(){return V(),document.addEventListener(b,u),document.addEventListener(y,p),document.addEventListener(f,g),document.addEventListener(d,O),function(){document.removeEventListener(b,u),document.removeEventListener(y,p),document.removeEventListener(f,g),document.removeEventListener(d,O)}}),[]);var A="animated ",v="top-scores-fab animated ";return A+=s?"slideOutLeft faster":"slideInRight faster",v+=s?"slideOutDown faster":"slideInUp faster",r.a.createElement("div",{className:"all-container"},r.a.createElement(re.a,{className:"header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Escolhe uma sala")),r.a.createElement("div",{className:A},r.a.createElement(T.a,{className:"rooms-page page"},r.a.createElement(ne,null),r.a.createElement(ae.a,null),r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Salas dispon\xedveis:"),r.a.createElement(se,{rooms:t.rooms,clicked:function(e){K({type:E,data:e})}}))),r.a.createElement(ce.a,{onClick:function(){a(ie({},t,{currentPage:R})),it(m,c,"/scores")},className:v,color:"primary",variant:"extended"},"Top Scores"))}function me(e){var t=e.room,a=t.id,c=t.name,o=e.clicked;return r.a.createElement(n.Fragment,null,r.a.createElement(ee.a,{onClick:function(){return o(a)},button:!0},r.a.createElement(te.a,{primary:c})),r.a.createElement(ae.a,null))}a(104);var ue=a(172),pe=a(164),fe=a(162),Ee=a(163),de=a(161);function ge(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function Oe(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?ge(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):ge(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function be(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=t.room,i=o.name,s=o.participants,m=o.creator,u=r.a.useState(!1),p=Object(l.a)(u,2),f=p[0],E=p[1],d=r.a.useState(!1),O=Object(l.a)(d,2),b=O[0],v=O[1],y=function(){E(!1),a(Oe({},t,{currentPage:P})),it(v,c,"/rooms")};function C(e){t.rooms=t.rooms.filter((function(t){return t.id!==e.detail}));var n=Oe({},t,{room:{},rooms:t.rooms});t.room.id!==e.detail||t.room.creator?n.currentPage=P:E(!0),a(n),it(v,c,"/rooms")}function Q(e){var n=t.room;n.participants.push(e.detail),a(Oe({},t,{room:n}))}function w(e){for(var n=t.room,r=0;r<n.participants.length;r++)if(n.participants[r].id===e.detail.id){n.participants.splice(r,1);break}a(Oe({},t,{room:n}))}function D(e){a(Oe({},t,{currentPage:P,room:{}})),it(v,c,"/rooms")}function R(e){a(Oe({},t,{currentPage:N})),it(v,c,"/questions")}function J(e){t.questions.push(e.detail),a(Oe({},t,{questions:t.questions,currentQuestion:e.detail}))}Object(n.useEffect)((function(){return document.addEventListener(g,J),document.addEventListener(h,w),document.addEventListener(j,Q),document.addEventListener(A,R),document.addEventListener(S,D),document.addEventListener(I,C),function(){document.removeEventListener(g,J),document.removeEventListener(A,R),document.removeEventListener(h,w),document.removeEventListener(j,Q),document.removeEventListener(S,D),document.removeEventListener(I,C)}}),[]);var M="animated ";return M+=b?"slideOutRight faster":"slideInRight faster",r.a.createElement("div",{className:"all-container"},r.a.createElement(re.a,{className:"header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},i)),r.a.createElement("div",{className:M},r.a.createElement(T.a,{className:"room-page page "},r.a.createElement("div",{className:"room-container"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Jogadores participantes:"),r.a.createElement($.a,{className:"participants-list"},s?s.map((function(e){return e?r.a.createElement(Ae,{key:e.id,participant:e}):void 0})):void 0),r.a.createElement("div",{className:"room-options-container"},r.a.createElement("div",{className:"creater-options"},m?r.a.createElement(Z.a,{className:"creator-btn btn",onClick:function(){K({type:A,data:void 0})},variant:"contained",color:"primary"},"Iniciar"):void 0,m?r.a.createElement(Z.a,{className:"creator-btn btn",onClick:function(){K({type:I,data:void 0})},variant:"contained",color:"primary"},"Apagar sala"):void 0),r.a.createElement("div",{className:"participant-options"},m?void 0:r.a.createElement(Z.a,{onClick:function(){K({type:S,data:void 0}),a(Oe({},t,{room:void 0,currentPage:P}))},variant:"contained",color:"primary"},"Sair")))))),r.a.createElement(ue.a,{open:f,onClose:y,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description"},r.a.createElement(de.a,{id:"alert-dialog-title"},"Sala apagada!"),r.a.createElement(fe.a,null,r.a.createElement(Ee.a,{id:"alert-dialog-description"},"A sala em que estava foi apagada!")),r.a.createElement(pe.a,null,r.a.createElement(Z.a,{onClick:y,color:"primary",autoFocus:!0},"Voltar para as salas"))))}function Ae(e){var t=e.participant.name;return r.a.createElement(n.Fragment,null,r.a.createElement(ee.a,null,r.a.createElement(te.a,{primary:t})),r.a.createElement(ae.a,null))}a(105);var ve=a(168),he=a(167),je=function(e,t){return Object(n.memo)(e,(function(e,a){for(var n=!0,r=0;r<t.length;r++){var c=t[r];if(JSON.stringify(e[c])!==JSON.stringify(a[c])){n=!1;break}}return n}))},ye=a(78),Ce=a(165),Se=a(166),Ie=a(52),Qe=Object(ye.a)({palette:{primary:Ce.a}}),Pe=Object(ye.a)({palette:{primary:Se.a}}),we=(Object(ye.a)({palette:{primary:Ie.a}}),a(75)),Ne=a.n(we),De=a(43),Re=a.n(De);function Je(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function Te(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?Je(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):Je(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}var ke=new Worker(Ne.a),Me=1;var Le=je((function(e){var t=e.onClick,a=e.disabled,c=e.text,o=e.theme,i=e.classes,s=Object(n.useState)(!0),m=Object(l.a)(s,2),u=m[0],p=m[1],f=Object(n.useRef)(""),E=Object(n.useRef)(!0);return Object(n.useEffect)((function(){return E.current=!0,function(){E.current=!1}}),[]),f.current!==c?(f.current=c,p(!0)):u&&setTimeout((function(){E.current&&p(!1)}),1500),r.a.createElement(he.a,{theme:o},r.a.createElement(Z.a,{className:u?i:"",disableRipple:"#f1f8e9"===o.palette.primary[50],variant:"contained",color:"primary",disabled:a,onClick:t},r.a.createElement("span",null,c)))}),["disabled","text","theme"]);function Ue(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),s=i[0],m=i[1],u=t.currentQuestion,p=u.question,f=u.options,E=(u.currentQuestion,Object(n.useState)(1)),d=Object(l.a)(E,2),b=d[0],A=d[1],h=Object(n.useState)(Me),j=Object(l.a)(h,2),y=j[0],C=j[1],S=Object(n.useState)(100),I=Object(l.a)(S,2),P=I[0],w=I[1],N=Object(n.useState)(20),R=Object(l.a)(N,2),J=R[0],M=R[1],L=Object(n.useState)(!1),U=Object(l.a)(L,2),B=U[0],z=U[1],Y=Object(n.useState)([]),G=Object(l.a)(Y,2),x=G[0],F=G[1],V=Object(n.useState)(0),Z=Object(l.a)(V,2),q=Z[0],W=Z[1],H=Object(n.useState)(-1),_=Object(l.a)(H,2),$=_[0],ee=_[1],te=Object(n.useRef)(!1),ae=Object(n.useRef)(!0),ne=Object(n.useRef)(void 0),ce=Object(n.useRef)(void 0);function oe(e){if(!te.current){for(var a,n=0,r=x[e],c=0;c<f.length;c++)if(r===f[c]){n=c;break}ee(e),te.current=!0,a={participantId:t.id,roomId:t.room.id,answer:n},K({type:O,data:a})}}var ie=function(e){return e===q&&B?Pe:Qe},se=function(e){var t=te.current;return te.current&&(t=!0),(!B||q!==e)&&(e!==$&&t)};function le(e){Me++,t.questions.push(e.detail),a(Te({},t,{questions:t.questions,currentQuestion:e.detail})),te.current=!1,A(e.detail.chapter),z(!1),C(y+1),ke.postMessage("START")}function me(e){ne.current=e.detail,ce.current&&a(Te({},t,{lastGameScore:e.detail,wrongChapters:ce.current,currentPage:D})),ke.postMessage("STOP"),it(m,c,"/result")}function ue(e){ce.current=e.detail,ne.current&&a(Te({},t,{wrongChapters:e.detail,lastGameScore:ne.current,currentPage:D}))}Object(n.useEffect)((function(){C(Me)}),[t,y]),Object(n.useEffect)((function(){return ke.onmessage=function(e){if("time"===e.data.type){var t=e.data.data,a=t.percentage,n=t.seconds;ae.current&&function(e,t){t<=0?(te.current=!0,w(100),z(!0),ke.postMessage("STOP")):w(t),M(e)}(n,a)}},ke.postMessage("START"),document.addEventListener(v,me),document.addEventListener(g,le),document.addEventListener(Q,ue),function(){document.removeEventListener(g,le),document.removeEventListener(Q,ue),document.removeEventListener(v,me),ae.current=!1,ce.current=void 0,ne.current=void 0,Me=1,a(Te({},t,{questions:[]}))}}),[]),Object(n.useEffect)((function(){console.log("CORRECT: "+f[t.currentQuestion.answer]);var e=f.concat();!function(e){for(var t=e.length-1;t>0;t--){var a=Math.floor(Math.random()*t),n=e[t];e[t]=e[a],e[a]=n}}(e),F(e),e&&(ee(-1),W(function(e){for(var a=f[t.currentQuestion.answer],n=0;n<e.length;n++)if(a===e[n])return n;return 0}(e)))}),[t.currentQuestion.options]);var pe="question-page page animated ";return pe+=s?"slideOutLeft faster":"slideInLeft faster",r.a.createElement("div",{className:"question-page container"},r.a.createElement(X.a,{when:t.currentPage!==D,message:function(e){return"Se sair vai perder esta"}}),r.a.createElement(re.a,{className:"header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Quest\xe3o:",r.a.createElement(Re.a,{inline:!0,text:y,springConfig:De.presets.wobbly}))),r.a.createElement("div",{className:pe},r.a.createElement(T.a,{className:"container question-option-container"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},r.a.createElement(Re.a,{text:p,springConfig:De.presets.wobbly})),r.a.createElement("div",{className:"chapter-and-progress-container"},r.a.createElement(k.a,{style:{textAlign:"left",marginTop:10,marginLeft:8},variant:"h6"},"Cap\xedtulo: ",b),r.a.createElement("div",{className:"progress-container"},r.a.createElement(ve.a,{size:60,variant:"static",value:P}),r.a.createElement("span",{className:"left-time"},J))),r.a.createElement("div",{className:"options-container"},r.a.createElement(Le,{classes:"animated pulse fast",theme:ie(0),text:f?x[0]:"A",onClick:function(){return oe(0)},disabled:se(0)}),r.a.createElement(Le,{classes:"animated delay-300 pulse fast",theme:ie(1),text:f?x[1]:"B",onClick:function(){return oe(1)},disabled:se(1)}),r.a.createElement(Le,{classes:"animated delay-500 pulse fast",theme:ie(2),text:f?x[2]:"C",onClick:function(){return oe(2)},disabled:se(2)}),r.a.createElement(Le,{classes:"animated delay-700 pulse fast",theme:ie(3),text:f?x[3]:"D",onClick:function(){return oe(3)},disabled:se(3)})))))}a(118);var Be=a(53),ze=a.n(Be);function Ye(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function Ge(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?Ye(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):Ye(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function xe(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),s=i[0],m=i[1],u=t.lastGameScore,p=u.score,f=u.correct,E=u.wrong;function d(e){a(Ge({},t,{wrongChapters:e.detail}))}Object(n.useEffect)((function(){return document.addEventListener(Q,d),function(){document.removeEventListener(Q,d)}}),[t]),Object(n.useEffect)((function(){t.questions.length>0&&a(Ge({},t,{questions:[]}))}),[t]);var g="animated ";return g+=s?"slideOutRight faster":"slideInRight faster",r.a.createElement("div",{className:"result-page page"},r.a.createElement(re.a,{className:"score-header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Resultado final")),r.a.createElement("div",{className:g},r.a.createElement(T.a,{className:"score-container"},r.a.createElement("div",{className:"final-score-container"},r.a.createElement(k.a,{variant:"h4"},"Pontua\xe7\xe3o final:"),r.a.createElement(k.a,{variant:"h5"},r.a.createElement(ze.a,{end:p,duration:5}))),r.a.createElement("div",{className:"wrong-and-right-numbers-container"},r.a.createElement("div",{className:"correct-or-wrong-container"},r.a.createElement(k.a,{variant:"h5"},"Respostas corretas:"),r.a.createElement(k.a,{variant:"h6"},r.a.createElement(ze.a,{end:f,duration:5}))),r.a.createElement("div",{className:"correct-or-wrong-container"},r.a.createElement(k.a,{variant:"h5"},"Respostas erradas:"),r.a.createElement(k.a,{variant:"h6"},r.a.createElement(ze.a,{end:E,duration:5})))),r.a.createElement(ae.a,{className:"result-page-divider-margin"}),r.a.createElement(Z.a,{className:"wrongChapters-submit-btn",variant:"contained",color:"primary",onClick:function(){a(Ge({},t,{currentPage:J})),it(m,c,"/wrong")}},"Ver perguntas erradas"),r.a.createElement(Z.a,{className:"score-submit-btn",variant:"contained",color:"primary",onClick:function(){a(Ge({},t,{currentPage:P})),it(m,c,"/rooms")}},"Voltar a jogar"))))}a(121);function Fe(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function Ve(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?Fe(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):Fe(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function Ke(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),s=i[0],m=i[1];function u(e){a(Ve({},t,{topScores:e.detail}))}Object(n.useEffect)((function(){return document.addEventListener(C,u),K({type:C,data:void 0}),function(){document.removeEventListener(C,u)}}),[]);var p="high-score-container animated ";return p+=s?"slideOutDown faster":"slideInUp faster",r.a.createElement("div",{className:"high-score-page page"},r.a.createElement(re.a,{className:"score-header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Highscores")),r.a.createElement(T.a,{className:p},r.a.createElement(k.a,{style:{marginTop:10},variant:"h6"},"Highscores:"),r.a.createElement($.a,null,t.topScores.map((function(e){return r.a.createElement(Ze,{key:e.score/Math.random(),topScore:e})}))),r.a.createElement(Z.a,{className:"score-submit-btn animated slideInUp fast",variant:"contained",color:"primary",onClick:function(){a(Ve({},t,{currentPage:P})),it(m,c,"/rooms")}},"Voltar")))}function Ze(e){var t=e.topScore,a=t.name,c=t.score;return r.a.createElement(n.Fragment,null,r.a.createElement(ee.a,null,r.a.createElement(te.a,{primary:a,secondary:c})),r.a.createElement(ae.a,null))}a(122);var qe=a(169),We=a(76),Xe=a.n(We),He=a(77),_e=a.n(He);function $e(e){var t=e.chapterData,a=Object(n.useState)(!1),c=Object(l.a)(a,2),o=c[0],i=c[1];return r.a.createElement(n.Fragment,null,r.a.createElement(ee.a,{button:!0,onClick:function(){i(!o)}},r.a.createElement(te.a,{primary:"Cap\xedtulo "+t[0]}),o?r.a.createElement(Xe.a,null):r.a.createElement(_e.a,null)),r.a.createElement(qe.a,{in:o,timeout:"auto",unmountOnExit:!0},r.a.createElement($.a,{component:"div",disablePadding:!0},t[1].map((function(e){return r.a.createElement(et,{key:e.id,question:e})})))))}function et(e){var t=e.question,a=t.question,n=t.answer,c=t.options;return r.a.createElement(ee.a,null,r.a.createElement(te.a,{primary:a,secondary:"Resposta: "+c[n]}))}function tt(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function at(e){var t=e.state,a=e.setState,c=Object(X.h)(),o=Object(n.useState)(!1),i=Object(l.a)(o,2),m=i[0],u=i[1],p="animated ";return p+=m?"slideOutLeft faster":"slideInLeft faster",r.a.createElement("div",{className:"wrongChapters-page page"},r.a.createElement(re.a,{className:"wrongChapters-header",style:{height:56},position:"static"},r.a.createElement(k.a,{style:{marginTop:10,marginLeft:8},variant:"h6"},"Cap\xedtulos")),r.a.createElement("div",{className:p},r.a.createElement(T.a,{className:"wrongChapters-container "},r.a.createElement(k.a,{style:{marginTop:10},variant:"h6"},"Respostas erradas:"),r.a.createElement(ae.a,{style:{marginTop:10}}),r.a.createElement($.a,null,t.wrongChapters?Object.entries(t.wrongChapters).map((function(e){return r.a.createElement($e,{key:e[0],chapterData:e})})):void 0),r.a.createElement(Z.a,{className:"wrongChapters-return-btn",variant:"contained",color:"primary",onClick:function(){a(function(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?tt(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):tt(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}({},t,{currentPage:D})),it(u,c,"/result")}},"Voltar aos resultados"))))}var nt=a(19);function rt(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function ct(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?rt(Object(a),!0).forEach((function(t){Object(s.a)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):rt(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}var ot=Object(n.createContext)({username:"",room:{},questions:[],currentPage:0,topScores:[]});function it(e,t,a){e(!0),setTimeout((function(){return t.replace(a)}),300)}function st(e){var t=e.children,a=e.state,n=Object(i.a)(e,["children","state"]);return r.a.createElement(X.c,Object.assign({},n,{render:function(e){var n=e.location;return a&&""!==a.username?t:r.a.createElement(X.b,{to:{pathname:"/",state:{from:n}}})}}))}function lt(e){var t=e.children,a=e.state,n=Object(i.a)(e,["children","state"]);return r.a.createElement(X.c,Object.assign({},n,{render:function(e){var n=e.location;return a&&""!==a.username&&a.room&&""!=a.room.id?t:r.a.createElement(X.b,{to:{pathname:"/result",state:{from:n}}})}}))}var mt=function(e,t,a,n,c){var i=document.createElement("div");i.setAttribute("custom-confirmation-navigation",""),document.body.appendChild(i);var s=function(e){n(ct({},c,{room:{}})),o.a.unmountComponentAtNode(i),t(e)};o.a.render(r.a.createElement(ue.a,{open:!0,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description"},r.a.createElement(de.a,{id:"alert-dialog-title"},"Pretende desistir?"),r.a.createElement(fe.a,null,r.a.createElement(Ee.a,{id:"alert-dialog-description"},"Se sair vai perder esta partida!")),r.a.createElement(pe.a,null,r.a.createElement(Z.a,{onClick:function(){return s(!1)},color:"primary",autoFocus:!0},"cancelar"),r.a.createElement(Z.a,{onClick:function(){return s(!0)},color:"secondary",autoFocus:!0},"Sair"))),i)},ut=function(){var e=this,t=Object(X.h)(),a=Object(n.useState)({topScores:[],currentPage:0,lastGameScore:{score:35.1,correct:5,wrong:5},username:"",id:"",room:{id:0,name:"Testing Room",participants:[{id:"testingId",name:"Testing"}]},questions:[],currentQuestion:{id:0,question:"is A == A?",answer:0,options:["A","B","C"]},rooms:[]}),c=Object(l.a)(a,2),o=c[0],i=c[1];return Object(n.useEffect)((function(){return t.listen((function(a,n){"/username"===a.pathname&&t.replace("/"),"/rooms"!==a.pathname&&("PUSH"===n?a.pathname===e.currentPathname&&a.search===e.currentSearch||(e.currentPathname=a.pathname,e.currentSearch=a.search,t.push({pathname:a.pathname,search:a.search})):t.go(1))})),function(){}}),[]),r.a.createElement(nt.a,{getUserConfirmation:function(e,a){return mt(e,a,t,i,o)}},r.a.createElement("div",{className:"App"},r.a.createElement(he.a,{theme:Qe},r.a.createElement(ot.Provider,{value:{state:o,setState:i}},r.a.createElement(ot.Consumer,null,(function(e){var t=e.state,a=e.setState;return r.a.createElement(X.e,null,r.a.createElement(X.c,{exact:!0,path:"/"},r.a.createElement(_,{state:t,setState:a})),r.a.createElement(st,{state:t,path:"/rooms"},r.a.createElement(le,{state:t,setState:a})),r.a.createElement(lt,{state:t,path:"/room"},r.a.createElement(be,{state:t,setState:a})),r.a.createElement(lt,{state:t,path:"/questions"},r.a.createElement(Ue,{state:t,setState:a})),r.a.createElement(st,{state:t,path:"/result"},r.a.createElement(xe,{state:t,setState:a})),r.a.createElement(st,{state:t,path:"/scores"},r.a.createElement(Ke,{state:t,setState:a})),r.a.createElement(st,{state:t,path:"/wrong"},r.a.createElement(at,{state:t,setState:a})),r.a.createElement(X.b,{to:"/"}))}))))))};Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));o.a.render(r.a.createElement(nt.a,null,r.a.createElement(ut,null)),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()}))},73:function(e,t){e.exports="data:image/png;base64,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"},75:function(e,t){var a=function(){var e,t=0;self.addEventListener("message",(function(a){a&&("START"===a.data?(t=0,clearInterval(e),e=setInterval((function(){t+=100;var e=Math.trunc(20-t/1e3),a=100-t/2e4*100;a<0&&(a=0),e<0&&(e=0),postMessage({type:"time",data:{seconds:e,percentage:a}})}),100)):"STOP"===a.data&&clearInterval(e))}))}.toString();a=a.substring(a.indexOf("{")+1,a.lastIndexOf("}"));var n=new Blob([a],{type:"application/javascript"}),r=URL.createObjectURL(n);e.exports=r},90:function(e,t,a){e.exports=a(124)},95:function(e,t,a){},96:function(e,t,a){},97:function(e,t,a){}},[[90,1,2]]]);