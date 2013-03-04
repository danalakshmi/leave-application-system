var Echoice=function(){};

//不用确认的窗口关闭
Echoice.closeWindow=function(){
	window.opener=null;
	window.open('','_self');
	window.close();
}
Echoice.popCenterWin = function(url,w,h){
    x=w;
    y=h;
    l=(screen.width/2)-(x/2);
    t=(screen.height/2)-(y/2);
    s="toolbar=no,location=no,status=no,menubar=no,resizable=yes,scrollbars=yes";
    s+=" width=" + x + ", height=" + y + ", left=" + l + ", top=" + t;
    MRV=window.open(url,"",s);
}
Echoice.popCenterModalDialog = function(url,w,h){
    x=w;
    y=h;
    l=(screen.width/2)-(x/2);
    t=(screen.height/2)-(y/2);
    s="status=no;resizable=yes;scrollbars=yes;help=no;";
    s+="dialogWidth=" + x + "px;dialogHeight=" + y + "px;dialogLeft=" + l + ";dialogTop=" + t;
    MRV=window.showModalDialog(url,"",s);
}

Echoice.transferOption=function(sourceId,targetId){
	var target = $("#"+targetId);
	target.append($("option[selected]", $("#"+sourceId)));
}

Echoice.transferOptionAll=function(sourceId,targetId){
	var target = $("#"+targetId);
	target.append($("option", $("#"+sourceId)));
}
Echoice.getEventObj=function(evt){
	obj = evt.srcElement?evt.srcElement:evt.target;
	return obj;
}