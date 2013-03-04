(function($) {
	$.acflow=$.acflow||{};
	
	$.acflow.addFrameTab=function(options){
			var frameId=options.frameId;
			var isExist=$('#tabs_center').tabs('exists',options.title);
			if(!isExist){
				frameId=frameId?frameId:'frame_ID'+Math.round(Math.random()*1000000);
		  		$('#tabs_center').tabs('add',{
					title:options.title,
					content:'<iframe scrolling="yes" frameborder="0"  src="" id="'+frameId+'" style="width:100%;height:100%;"></iframe>',
					closable:true,
					iconCls:options.iconCls
				});		  		
		  		$("#"+frameId).attr("src",options.href);
			}else{
				$('#tabs_center').tabs('select',options.title);
				if(options.isReload){
					$("#"+frameId).attr("src",options.href);
				}
			}			
	}
	$.acflow.init=function(){
		$('#tabs_center').tabs({fit:true,border:false});		
		$('#menu-accordion-id').accordion({fit:true,border:false});
		$('#menu-accordion-id a').click(function(){
			var btTitle=$(this).attr('title');
			var href=$(this).attr('name');
			var frameId=$(this).attr('frameId');			
			var iconCls = $(this).attr('iconCls');
			$.acflow.addFrameTab({title:btTitle,href:href,frameId:frameId,isReload:true,iconCls:iconCls});
		});
		
	}	
})(jQuery);