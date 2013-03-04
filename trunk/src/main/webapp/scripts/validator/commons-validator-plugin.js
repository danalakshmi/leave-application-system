(function($) {
	$.commonsValidator=$.commonsValidator||{};
	$.commonsValidator.mode=3;
	$.commonsValidator.showState=function(elem,errMsg){
		if($.commonsValidator.mode=3){
			try{
				var span = document.createElement("SPAN");
				span.id = "__ErrorMessagePanel";
				span.style.color = "red";
				elem.parentNode.appendChild(span);
				span.innerHTML = errMsg;
			}catch(e){alert(e.description);} 			
		}
	}
	
	$.commonsValidator.clearState=function(elem){
		if($.commonsValidator.mode=3){
			with(elem){
				if(style.color == "red")
					style.color = "";
				var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
				if(lastNode.id == "__ErrorMessagePanel")
					parentNode.removeChild(lastNode);
			}			
		}
	}
	$.commonsValidator.showStateAlert=function(filesErrMsgArr){
		if($.commonsValidator.mode=1){
			alert(filesErrMsgArr.join('\n'));
		}
	}
})((jQuery))