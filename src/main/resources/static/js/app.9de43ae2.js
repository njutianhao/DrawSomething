(function(t){function e(e){for(var o,c,s=e[0],r=e[1],l=e[2],u=0,h=[];u<s.length;u++)c=s[u],Object.prototype.hasOwnProperty.call(a,c)&&a[c]&&h.push(a[c][0]),a[c]=0;for(o in r)Object.prototype.hasOwnProperty.call(r,o)&&(t[o]=r[o]);d&&d(e);while(h.length)h.shift()();return i.push.apply(i,l||[]),n()}function n(){for(var t,e=0;e<i.length;e++){for(var n=i[e],o=!0,s=1;s<n.length;s++){var r=n[s];0!==a[r]&&(o=!1)}o&&(i.splice(e--,1),t=c(c.s=n[0]))}return t}var o={},a={app:0},i=[];function c(e){if(o[e])return o[e].exports;var n=o[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,c),n.l=!0,n.exports}c.m=t,c.c=o,c.d=function(t,e,n){c.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},c.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},c.t=function(t,e){if(1&e&&(t=c(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(c.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)c.d(n,o,function(e){return t[e]}.bind(null,o));return n},c.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return c.d(e,"a",e),e},c.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},c.p="/";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],r=s.push.bind(s);s.push=e,s=s.slice();for(var l=0;l<s.length;l++)e(s[l]);var d=r;i.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},1195:function(t,e,n){t.exports=n.p+"img/avatar.6afa7222.png"},"3ab4":function(t,e,n){},"56d7":function(t,e,n){"use strict";n.r(e);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("7a23"),a={id:"container"},i={id:"header"},c={id:"display"},s={id:"aside"},r={key:0,id:"drawTool"},l={class:"toolLine"},d={class:"toolLine"},u={class:"toolLine"},h={class:"toolLine"},b={class:"colorLine"},m={class:"colorLine"},f={class:"colorLine"},g={class:"colorLine"},p={class:"tool"},w={class:"colorLine"},v={class:"tool"},y={id:"mainContainer"},O={id:"canvasContainer"},j={key:0,id:"problem"},k={key:2,class:"turnEndContent"},x=Object(o["l"])("游戏开始"),C={key:1,style:{position:"relative",top:"80%",margin:"10px auto",width:"40%",height:"10%","line-height":"50px","text-align":"center"}},D={id:"messageContainer"},X={class:"answer"},I=Object(o["m"])("div",{class:"history",id:"answerHistory"},null,-1),Y={class:"chat"},B=Object(o["m"])("div",{class:"history",id:"chatHistory"},null,-1);function M(t,e,n,M,F,H){var S=this,T=Object(o["M"])("el-button"),P=Object(o["M"])("player"),E=Object(o["M"])("el-slider");return Object(o["D"])(),Object(o["i"])("div",a,[Object(o["m"])("div",i,[Object(o["m"])(T,{size:"mini",style:{margin:"5px auto"},type:"info",icon:"el-icon-back",onClick:H.backToHall},null,8,["onClick"])]),Object(o["m"])("div",c,[Object(o["m"])("div",s,[(Object(o["D"])(!0),Object(o["i"])(o["b"],null,Object(o["K"])(F.players,(function(t){return Object(o["D"])(),Object(o["i"])(P,Object(o["t"])({key:t.seat},t),null,16)})),128))]),1==this.state?(Object(o["D"])(),Object(o["i"])("div",r,[Object(o["m"])("div",l,[Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/铅笔.svg)"},onClick:e[1]||(e[1]=function(t){return S.tool="pencil"})}),Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/拾色器.svg)"},onClick:e[2]||(e[2]=function(t){return S.tool="colorGetter"})})]),Object(o["m"])("div",d,[Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/实心圆.svg)"},onClick:e[3]||(e[3]=function(t){return S.tool="filledCircle"})}),Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/空心圆.svg)"},onClick:e[4]||(e[4]=function(t){return S.tool="circle"})})]),Object(o["m"])("div",u,[Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/实心矩形.svg)"},onClick:e[5]||(e[5]=function(t){return S.tool="filledRectangular"})}),Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/空心矩形.svg)"},onClick:e[6]||(e[6]=function(t){return S.tool="rectangular"})})]),Object(o["m"])("div",h,[Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/橡皮擦.svg)"},onClick:e[7]||(e[7]=function(t){return S.tool="eraser"})}),Object(o["m"])("div",{class:"tool",style:{"background-image":"url(/清空.svg)"},onClick:e[8]||(e[8]=function(t){S.ws.send(JSON.stringify({command:"Draw",content:{type:"clear"}}))})})]),Object(o["m"])("div",b,[Object(o["m"])("div",{class:"tool",style:{"background-color":"black"},onClick:e[9]||(e[9]=function(t){return S.color="#000000"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"aqua"},onClick:e[10]||(e[10]=function(t){return S.color="#00FFFF"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"blue"},onClick:e[11]||(e[11]=function(t){return S.color="#0000FF"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"brown"},onClick:e[12]||(e[12]=function(t){return S.color="#A52A2A"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"yellow"},onClick:e[13]||(e[13]=function(t){return S.color="#FFFF00"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"red"},onClick:e[14]||(e[14]=function(t){return S.color="#FF0000"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"green"},onClick:e[15]||(e[15]=function(t){return S.color="#008000"})})]),Object(o["m"])("div",m,[Object(o["m"])("div",{class:"tool",style:{"background-color":"orange"},onClick:e[16]||(e[16]=function(t){return S.color="#FFA500"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"purple"},onClick:e[17]||(e[17]=function(t){return S.color="#800080"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"azure"},onClick:e[18]||(e[18]=function(t){return S.color="#F0FFFF"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"fuchsia"},onClick:e[19]||(e[19]=function(t){return S.color="#FF00FF"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"gray"},onClick:e[20]||(e[20]=function(t){return S.color="#808080"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"silver"},onClick:e[21]||(e[21]=function(t){return S.color="#C0C0C0"})}),Object(o["m"])("div",{class:"tool",style:{"background-color":"tomato"},onClick:e[22]||(e[22]=function(t){return S.color="#FF6347"})})]),Object(o["m"])("div",f,[Object(o["cb"])(Object(o["m"])("input",{id:"colorChooser",type:"color",class:"tool","onUpdate:modelValue":e[23]||(e[23]=function(t){return F.color=t})},null,512),[[o["X"],F.color]])]),Object(o["m"])("div",g,[Object(o["m"])("div",p,[Object(o["m"])(E,{modelValue:F.penSize,"onUpdate:modelValue":e[24]||(e[24]=function(t){return F.penSize=t}),min:1,max:50},null,8,["modelValue"])])]),Object(o["m"])("div",w,[Object(o["m"])("div",v,[Object(o["m"])(E,{modelValue:F.opcacity,"onUpdate:modelValue":e[25]||(e[25]=function(t){return F.opcacity=t}),max:255},null,8,["modelValue"])])])])):Object(o["j"])("",!0),Object(o["m"])("div",y,[Object(o["m"])("div",O,[2==this.state||1==this.state?(Object(o["D"])(),Object(o["i"])("div",j,Object(o["Q"])(this.problem),1)):Object(o["j"])("",!0),Object(o["m"])("canvas",{id:"myCanvas",onMousedown:e[26]||(e[26]=function(t){return H.down(t)}),onMousemove:e[27]||(e[27]=function(t){return F.moveEvent?F.moveEvent(t):null}),onMouseup:e[28]||(e[28]=function(t){return H.up(t)}),onMouseout:e[29]||(e[29]=function(t){return H.up(t)})},null,32),2==this.state||1==this.state?(Object(o["D"])(),Object(o["i"])("progress",{key:1,id:"progress",value:F.progress,max:"60000"},null,8,["value"])):Object(o["j"])("",!0),0==this.state&&F.isOwner||3==this.state?(Object(o["D"])(),Object(o["i"])("div",k,[0==this.state&&F.isOwner?(Object(o["D"])(),Object(o["i"])(T,{key:0,id:"startButton",type:"primary",onClick:H.startGame},{default:Object(o["bb"])((function(){return[x]})),_:1},8,["onClick"])):Object(o["j"])("",!0),3==this.state?(Object(o["D"])(),Object(o["i"])("div",C,Object(o["Q"])(this.turnEndMessage),1)):Object(o["j"])("",!0)])):Object(o["j"])("",!0)]),Object(o["m"])("div",D,[Object(o["m"])("div",X,[I,Object(o["cb"])(Object(o["m"])("input",{class:"sendMessage",disabled:this.answerDisable,"onUpdate:modelValue":e[30]||(e[30]=function(t){return S.answerInput=t}),onKeyup:e[31]||(e[31]=function(t){return H.sendAnswer(t)}),placeholder:"在这里输入答案"},null,40,["disabled"]),[[o["X"],this.answerInput]])]),Object(o["m"])("div",Y,[B,Object(o["cb"])(Object(o["m"])("input",{class:"sendMessage","onUpdate:modelValue":e[32]||(e[32]=function(t){return S.chatInput=t}),onKeyup:e[33]||(e[33]=function(t){return H.sendChat(t)}),placeholder:"在这里输入聊天内容"},null,544),[[o["X"],this.chatInput]])])])])])])}n("cb29"),n("fb6a"),n("4d90"),n("d3b7"),n("25f0"),n("38cf"),n("b0c0");var F=Object(o["fb"])("data-v-3cf2a4f8");Object(o["G"])("data-v-3cf2a4f8");var H={class:"player"},S={id:"playerName"},T={id:"score"};Object(o["E"])();var P=F((function(t,e,a,i,c,s){var r=Object(o["M"])("el-header"),l=Object(o["M"])("el-container");return Object(o["D"])(),Object(o["i"])("div",H,[Object(o["m"])(l,{class:"container"},{default:F((function(){return[Object(o["m"])("img",{src:n("1195")},null,8,["src"]),Object(o["m"])(l,{class:"playerInf"},{default:F((function(){return[Object(o["m"])(r,{height:"50%"},{default:F((function(){return[Object(o["m"])("div",S,Object(o["Q"])(a.name),1)]})),_:1}),Object(o["m"])(r,{height:"50%"},{default:F((function(){return[Object(o["m"])("div",T,"分数:"+Object(o["Q"])(a.score),1)]})),_:1})]})),_:1})]})),_:1})])})),E=(n("a9e3"),{props:{seat:Number,name:String,score:Number}});n("a45c");E.render=P,E.__scopeId="data-v-3cf2a4f8";var N=E,L={methods:{startGame:function(){this.ws.send(JSON.stringify({command:"StartGame",content:null}))},down:function(t){if(1==this.state)switch(this.drawBeginX=t.offsetX,this.drawBeginY=t.offsetY,this.moveEvent=this.move,this.context.lineWidth=this.penSize,this.tool){case"colorGetter":this.getColor(t);break;case"pencil":this.ws.send(JSON.stringify({command:"Draw",content:{type:"setLine",color:this.hexToRgba(this.color).rgba,lineWidth:this.penSize}}));break;case"eraser":this.ws.send(JSON.stringify({command:"Draw",content:{type:"setColor",color:this.hexToRgba("#FFFFFF").rgba,lineWidth:this.penSize}}));break;case"filledCircle":case"circle":case"filledRectangular":case"rectangular":this.imageData=this.context.getImageData(0,0,this.canvas.width,this.canvas.height),this.ws.send(JSON.stringify({command:"Draw",content:{type:"setColor",color:this.hexToRgba(this.color).rgba}}));break}},up:function(t){var e,n,o;if(1==this.state&&null!=this.moveEvent)switch(this.moveEvent=null,this.tool){case"colorGetter":this.getColor(t);break;case"pencil":case"eraser":this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawLine",beginX:this.drawBeginX,beginY:this.drawBeginY,endX:t.offsetX,endY:t.offsetY}}));break;case"filledCircle":this.context.putImageData(this.imageData,0,0),e=(this.drawBeginX+t.offsetX)/2,n=(this.drawBeginY+t.offsetY)/2,o=Math.abs(this.drawBeginX-t.offsetX)/2,this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawFilledCircle",X:e,Y:n,r:o}}));break;case"circle":this.context.putImageData(this.imageData,0,0),e=(this.drawBeginX+t.offsetX)/2,n=(this.drawBeginY+t.offsetY)/2,o=Math.abs(this.drawBeginX-t.offsetX)/2,this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawCircle",X:e,Y:n,r:o}}));break;case"filledRectangular":this.context.putImageData(this.imageData,0,0),this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawFilledRectangular",X1:t.offsetX,Y1:t.offsetY,X2:this.drawBeginX,Y2:this.drawBeginY}}));break;case"rectangular":this.context.putImageData(this.imageData,0,0),this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawRectangular",X1:t.offsetX,Y1:t.offsetY,X2:this.drawBeginX,Y2:this.drawBeginY}}));break}},move:function(t){var e,n,o;if(1==this.state)switch(this.tool){case"colorGetter":this.getColor(t);break;case"pencil":case"eraser":this.ws.send(JSON.stringify({command:"Draw",content:{type:"drawLine",beginX:this.drawBeginX,beginY:this.drawBeginY,endX:t.offsetX,endY:t.offsetY}})),this.drawBeginX=t.offsetX,this.drawBeginY=t.offsetY;break;case"filledCircle":this.context.putImageData(this.imageData,0,0),e=(this.drawBeginX+t.offsetX)/2,n=(this.drawBeginY+t.offsetY)/2,o=Math.abs(this.drawBeginX-t.offsetX)/2,this.context.beginPath(),this.context.arc(e,n,o,0,2*Math.PI),this.context.fill(),this.context.closePath();break;case"circle":this.context.putImageData(this.imageData,0,0),e=(this.drawBeginX+t.offsetX)/2,n=(this.drawBeginY+t.offsetY)/2,o=Math.abs(this.drawBeginX-t.offsetX)/2,this.context.beginPath(),this.context.arc(e,n,o,0,2*Math.PI),this.context.stroke(),this.context.closePath();break;case"filledRectangular":this.context.putImageData(this.imageData,0,0),e=Math.min(this.drawBeginX,t.offsetX),n=Math.min(this.drawBeginY,t.offsetY),this.context.beginPath(),this.context.fillRect(e,n,Math.abs(this.drawBeginX-t.offsetX),Math.abs(this.drawBeginY-t.offsetY)),this.context.closePath();break;case"rectangular":this.context.putImageData(this.imageData,0,0),e=Math.min(this.drawBeginX,t.offsetX),n=Math.min(this.drawBeginY,t.offsetY),this.context.beginPath(),this.context.rect(e,n,Math.abs(this.drawBeginX-t.offsetX),Math.abs(this.drawBeginY-t.offsetY)),this.context.stroke(),this.context.closePath();break}},hexToRgba:function(t){var e="rgba("+parseInt("0x"+t.slice(1,3))+","+parseInt("0x"+t.slice(3,5))+","+parseInt("0x"+t.slice(5,7))+","+this.opcacity/255+")";return{red:parseInt("0x"+t.slice(1,3)),green:parseInt("0x"+t.slice(3,5)),blue:parseInt("0x"+t.slice(5,7)),rgba:e}},getColor:function(t){var e=this.context.getImageData(t.offsetX,t.offsetY,1,1).data;this.color="#"+e[0].toString(16).padStart(2,"0")+e[1].toString(16).padStart(2,"0")+e[2].toString(16).padStart(2,"0"),this.opcacity=e[3]},sendAnswer:function(t){"13"==t.keyCode&&""!=this.answerInput&&(this.ws.send(JSON.stringify({command:"Guess",content:this.answerInput})),this.answerInput="")},sendChat:function(t){"13"==t.keyCode&&""!=this.chatInput&&(this.ws.send(JSON.stringify({command:"Talk",content:this.chatInput})),this.chatInput="")},backToHall:function(){window.location.href="/hall"}},data:function(){return{isOwner:!1,playerNum:0,players:[],rounds:0,ws:void 0,problem:"",hints:[],color:"#000000",penSize:5,tool:"pencil",state:0,canvas:null,context:null,drawBeginX:null,drawBeginY:null,drawEndX:null,drawEndY:null,opcacity:255,moveEvent:null,imageData:null,blankImageData:null,answerInput:"",chatInput:"",answerHistory:null,chatHistory:null,turnEndMessage:"",answerDisable:!0,progress:0,endTime:0,observer:!1}},name:"App",components:{player:N},mounted:function(){var t=this;this.canvas=document.getElementById("myCanvas"),this.answerHistory=document.getElementById("answerHistory"),this.chatHistory=document.getElementById("chatHistory"),this.context=this.canvas.getContext("2d"),this.canvas.height=500,this.canvas.width=900,this.context.fillStyle="#FFFFFF",this.context.fillRect(0,0,this.canvas.width,this.canvas.height),this.context.lineCap="round",this.blankImageData=this.context.getImageData(0,0,this.canvas.width,this.canvas.height),this.ws=new WebSocket("ws://"+window.location.host+"/game"),this.ws.addEventListener("open",(function(e){"beOwner"==e.data&&(t.isOwner=!0)})),this.ws.addEventListener("message",(function(e){var n,o,a,i,c,s,r,l,d=JSON.parse(e.data);if("BeOwner"!=d.command){if("BeObserver"==d.command)return n=document.createElement("p"),o=document.createTextNode("房间玩家已满,以旁观者身份进入"),n.appendChild(o),t.answerHistory.appendChild(n),t.answerHistory.scrollTop=t.answerHistory.scrollHeight,void(t.observer=!0);if("BePlayer"==d.command)return t.observer=!1,n=document.createElement("p"),o=document.createTextNode("你成为了玩家"),n.appendChild(o),t.answerHistory.appendChild(n),t.answerHistory.scrollTop=t.answerHistory.scrollHeight,void(2==t.state&&(t.answerDisable=!1));if("UpdatePlayers"!=d.command){if("DrawTurnBegin"==d.command)return t.problem=d.content,t.state=1,t.endTime=Date.now()+6e4,t.progress=t.endTime-Date.now(),clearInterval(),void setInterval((function(){t.progress=t.endTime-Date.now(),0==t.progress&&clearInterval()}),100);if("GuessTurnBegin"==d.command)return t.problem="_ ".repeat(d.content),t.state=2,0==t.observer&&(t.answerDisable=!1),t.endTime=Date.now()+6e4,t.progress=t.endTime-Date.now(),clearInterval(),void setInterval((function(){t.progress=t.endTime-Date.now(),0==t.progress&&clearInterval()}),100);if("TurnEnd"==d.command)return t.context.putImageData(t.blankImageData,0,0),t.state=3,null==d.content?t.turnEndMessage="本轮结束，所有人回答正确":t.turnEndMessage="本轮结束,"+d.content+"人未答出",n=document.createElement("p"),o=document.createTextNode("------------"),n.appendChild(o),t.answerHistory.appendChild(n),void(t.answerHistory.scrollTop=t.answerHistory.scrollHeight);if("EndGame"==d.command)return t.state=0,n=document.createElement("p"),o=document.createTextNode("本局结束"),n.appendChild(o),t.answerHistory.appendChild(n),void(t.answerHistory.scrollTop=t.answerHistory.scrollHeight);if("Guess"==d.command)n=document.createElement("p"),o=document.createTextNode(d.content),n.appendChild(o),t.answerHistory.appendChild(n),t.answerHistory.scrollTop=t.answerHistory.scrollHeight;else if("Talk"==d.command)n=document.createElement("p"),o=document.createTextNode(d.content),n.appendChild(o),t.chatHistory.appendChild(n),t.chatHistory.scrollTop=t.chatHistory.scrollHeight;else{if("AnswerRight"==d.command){t.answerDisable=!0;var u=new Audio("/right.mp3");return void u.play()}if("Hint"==d.command){if(""==d.content)return;n=document.createElement("p"),o=document.createTextNode("提示:"+d.content),n.appendChild(o),t.answerHistory.appendChild(n),t.answerHistory.scrollTop=t.answerHistory.scrollHeight}else if("Draw"==d.command)switch(d.content.type){case"setColor":t.context.fillStyle=t.context.strokeStyle=d.content.color;break;case"setLine":t.context.lineWidth=d.content.lineWidth,t.context.fillStyle=t.context.strokeStyle=d.content.color;break;case"drawLine":t.context.beginPath(),t.context.moveTo(d.content.beginX,d.content.beginY),t.context.lineTo(d.content.endX,d.content.endY),t.context.stroke(),t.context.closePath();break;case"drawFilledCircle":t.context.beginPath(),t.context.arc(d.content.X,d.content.Y,d.content.r,0,2*Math.PI),t.context.fill(),t.context.closePath();break;case"drawCircle":t.context.beginPath(),t.context.arc(d.content.X,d.content.Y,d.content.r,0,2*Math.PI),t.context.stroke(),t.context.closePath();break;case"drawFilledRectangular":a=d.content.X1,i=d.content.Y1,c=d.content.X2,s=d.content.Y2,r=Math.min(a,c),l=Math.min(i,s),t.context.beginPath(),t.context.fillRect(r,l,Math.abs(a-c),Math.abs(i-s)),t.context.closePath();break;case"drawRectangular":a=d.content.X1,i=d.content.Y1,c=d.content.X2,s=d.content.Y2,r=Math.min(a,c),l=Math.min(i,s),t.context.beginPath(),t.context.rect(r,l,Math.abs(a-c),Math.abs(i-s)),t.context.stroke(),t.context.closePath();break;case"clear":t.context.putImageData(t.blankImageData,0,0);break}}}else t.players=d.content}else t.isOwner=!0})),this.ws.addEventListener("close",(function(){alert("连接断开")}))}};n("8ef0");L.render=M;var J=L,R=n("3fd4"),_=(n("7dd6"),n("3ef0")),G=n.n(_),A=function(t){t.use(R["a"],{locale:G.a})},V=n("bc3a"),z=n.n(V),U=Object(o["h"])(J);U.config.globalProperties.$axios=z.a,A(U),U.mount("#app")},"8ef0":function(t,e,n){"use strict";n("3ab4")},a45c:function(t,e,n){"use strict";n("fac0")},fac0:function(t,e,n){}});
//# sourceMappingURL=app.9de43ae2.js.map