<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/jquery.form.js"></script>


<title>表格模板管理</title>
<script type="text/javascript">  
var hrefstr = sy.getUrlParam ("hrefstr");

	var edittype;
var editRow;
	var datagrid;
 <% String role;
 if( session.getAttribute("rolecode")==null )
 role="01";
 else role=(String)session.getAttribute("rolecode");

 %>;
var rolecode='<%=role%>';
var condistr="1=1";
$(function(){
 $.get('/MainInit',
  { hre: hrefstr+ '_getinithtml_mainview.GridList'
 },function(datastr) { 
 
try{
 
 var ggg=eval("("+ datastr +")");
 extjson =ggg.firstcom;
 if( extjson.length>0)showextend();
 var columnsinfo= ggg.columns; 
 
 var fname= ggg.namefield ;
 var pagesize= ggg.rowcount ;
 var pagelis="["+ pagesize+","+( pagesize*2 ) +","+( pagesize*3 ) +","+( pagesize*4)+"]";
var pagelist= eval( pagelis ); 
var toolba  = ggg.button ;
}
  catch(err) { 
  alert( datastr+ err );
  }
datagrid= $('#tt').datagrid({  
   url:'/MainDisp',
    queryParams:{ 
   hre: hrefstr+ '_getpagerecord_mainview.GridList',
    page:1,
    condstr: condistr ,
    rows:pagesize
    }, 
   	pagination:true, 
 		 pageList: pagelist ,
 		 	pageSize:pagesize,
				fitColumns: true,
				singleSelect: true,
				 toolbar: toolba,
		  method: 'get',
				idField: 'id',
				columns: columnsinfo ,
			onAfterEdit : function(rowIndex, rowData, changes)
		 {
				/* var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) 
				*/
				 	if (edittype == undefined) {
				 	
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}
			var gh= object2Str( rowData ) ;
 // +"_"+ urlf +"_otheractions.Form"+ urlf +"_editgrid" 
				  $.ajax({ 
       url:"/MainEdit", //提交给哪个执行 
        data: "hre="+ hrefstr+"_" +edittype+"&"+gh ,
    type:"POST", 
     success: ( function(result) {    
     		$.messager.show({
								msg : result ,
								title : '成功'
							});
							 edittype= undefined;
									editRow = undefined;
					 $('#tt').datagrid('unselectAll');
				    })
            
       });

				
     }

				
				 });  
	 });
	 
});
function getpagerecord(condstr ) {
   var nnnn = $('#tt').datagrid('options').queryParams;
  nnnn.condstr= condstr ;
  $('#tt').datagrid('load', nnnn) ;
 
}


		function edit(fff){
		alert(fff);
		 	var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if (editRow != undefined) {
		//		datagrid.datagrid('endEdit', editRow);
				
				edittype= undefined;
				return;
			}

			if (editRow == undefined) {
				editRow = datagrid.datagrid('getRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
				
				edittype= fff;
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}
	}

	 	 	function add(fffd) {
	 	 	alert(fffd);
	 	 	if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}

		if (editRow == undefined) {
		var ddd = datagrid.datagrid('selectRow',0);
			datagrid.datagrid('unselectAll');

		
			var row = {
				
			};
			datagrid.datagrid('insertRow', {
				index : 0,
				row : row
			});
			editRow = 0;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);
			
				edittype= fffd;
		 }
	}
	
		 	 function save(fff){
		 alert(fff)	 ;
			 	if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
		  }
		 }
	 function cancel(){
			 	if (editRow) {
						datagrid.datagrid('cancelEdit', editRow.id);
					
							datagrid.datagrid('reload');
						
						editRow = undefined;
					}
			 }
			 
	 function delet(delstr){
	 editRow = $('#tt').datagrid('getSelected');
    $.ajax({ 
     url:"/MainEdit",  data:"hre="+ hrefstr+"_"+delstr+"&id="+ editRow.id ,
     type:"POST", 
    success: ( function(result) {    
   if( result.charAt(0)=="1" )
   {
   var sub_stri = result.substring(2);
   
          var delrow= 	 $('#tt').datagrid('getRowIndex',editRow);
          	 $('#tt').datagrid('deleteRow', delrow );
								
          	$.messager.show({
									msg : sub_stri ,
									title : '提示'
								});
							        //显示操作结果
            }
           else
           {
           $.messager.show({
								msg : "未删除成功" ,
									title : '提示'
								});
           } 
       })
  });
}
function object2Str(obj) 
   { 
   var val, output = ""; 
   if (obj) 
   { output = ""; 
     for (var i in obj) 
     { val = obj[i];
        switch (typeof val) 
        { 
          case ("object"): 
           output += i +object2Str(val) + "&"; 
           break; 
           case ("string"): output += i + "="+val+ "&"; 
           break; 
           default: output += i + "=" +val + "&";
           
           // encodeURI( 
        } 
      } 
      output= output.substring(0,output.length-1) ; 
    } 
    return output; 
  } 
  
function object2String(obj) { var val, output = ""; 
if (obj) { output += "{"; 

for (var i in obj) 
{ val = obj[i];
 switch (typeof val) { 
 case ("object"): 
 if (val[0]) {
  output += i +array2String(val) + ","; 
  } else {
  output += i +object2String(val) + ","; 
  } 
  break; 
 case ("string"): output += i + ":'"+val+ "',"; 
break; 

default: output += i + ":" + encodeURI( val) + ",";
} 
} output= output.substring(0,output.length-1) + "}"; 
} return output; 
} 
function array2String(array) { 
var output = ":"; 
if (array) { 
output += "["; 
for (var i in array) 
{ val = array[i]; 
switch (typeof val) { 
case ("object"): 
if (val[0]) { 
output += array2String(val) + ","; 
} else { 
output += object2String(val) + ","; 

} break;
 case ("string"): 
 output += "'" + encodeURI(val) + ","; 
 break; 
 default: output += val + ",";
  } } output=output.substring(0,output.length-1) + "]"; 
  } return output; 
  } 

 fff= function(value,row,index)
 { 			
 var imageFile = '/images/icons/' + row.icons;
 
 return '<img class="item-img" src="'+imageFile+
 	'"/><span class="item-text">'+row.text+'</span>';
				}
comicon =function(v) {
							return '<span class='+v.value +' style=\"display:inline-block;vertical-align:middle;width:16px;height:16px;\"> '+v.text+' </span>';
						}
		
showextend=function ( ) {

for( var i =0; i < extjson.length; i ++ )


{
$('#layout').layout('add',{

region: extjson[i].id ,
id: extjson[i].id ,
width: 580,
height: extjson[i].height ,
title: extjson[i].title ,


href: extjson[i].href,
split: true,
collapsed:true

});




}
 
}
						
</script>



</head>
<body>
<div id="layout" class="easyui-layout"data-options="fit:true">  

‪
‪<div region="center"   style="margin:0px;padding:0px;background:#eee;border:0;">
<table id="tt"></table> 
</div>



</div>
</body>
 </html>
