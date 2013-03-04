(function($) {
	$.acflow=$.acflow||{};
	
	$.acflow.tableGrid=function(tablId,custOptions){
		var defaultOptions={title:'查询列表',striped:false,fit:true,singleSelect:true,nowrap:true};
		$.extend(defaultOptions,custOptions);
		$(tablId).datagrid(defaultOptions);
	}
})(jQuery);	