<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/jquery.form.js"></script>


<title>表格模板管理</title>
<script type="text/javascript">
var request ={QueryString:function(val) 
　　{var uri = window.location.search; 
　　var re = new RegExp("" +val+ "=([^&?]*)", "ig"); 
　　return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
     }}
    var hrefstr= request.QueryString("hrefstr");

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
 
 var jsonstr1;
 if( datastr.indexOf("⊙")>0 )
 {
 jsonstr1= datastr.split("⊙")[0];

showextend( datastr.split("⊙")[1] );
 }
 else jsonstr1= datastr;
 
try{
 var ggg=eval("("+ jsonstr1 +")");
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
				 var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}
				var urlf = '';
			 	 	if (inserted.length > 0) {
			 urlf = 'Add';
			}
			 		if (updated.length > 0) {
				 		urlf = 'Edit';
				 		 	}
				 		 	
 var gh= object2Str( rowData ) ;
 // +"_"+ urlf +"_otheractions.Form"+ urlf +"_editgrid" 
				  $.ajax({ 
       url:"/MainEdit", //提交给哪个执行 
        data:"hre="+ hrefstr +"_"+ urlf +"_otheractions.Form"+ urlf +"_Meditgrid&"+ gh ,
   
     type:"POST", 
     success: ( function(result) {    
     		$.messager.show({
								msg : result ,
								title : '成功'
							});
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
				datagrid.datagrid('endEdit', editRow);
			}

			if (editRow == undefined) {
				editRow = datagrid.datagrid('getRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}
	}

	 	 	function add(fff) {
	 	 	alert(fff);
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
						var p = datagrid.datagrid('getParent', editRow.id);
						if (p) {
							datagrid.datagrid('reload', p.id);
						} else {
							datagrid.datagrid('reload');
						}
						editRow = undefined;
					}
			 }
			 
	 function del(){
	 editRow = $('#tt').datagrid('getSelected');
    $.ajax({ 
     url:"/MainEdit",  data:"hre="+ hrefstr+"_Del_otheractions.FormEdit_editgrid&id="+ editRow.id ,
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
		
showextend=function (extstr ) {

extjson=eval("("+ extstr+")" );
for( var i =0; i < extjson.length; i ++ )


{
$('#layout').layout('add',{


region: extjson[i].id ,
id: extjson[i].id ,
width: 180,
height:100,
title: '过滤',


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
