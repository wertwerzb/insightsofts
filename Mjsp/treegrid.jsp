<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
	<script type="text/javascript" src="/jslib/jquery.form.js"></script>

<title>树管理</title>
<script type="text/javascript">

  var hrefstr= sy.getUrlParam ("hrefstr"); 
var editRow;
	var editType;
	var treegrid;
	
 var usefielded;
 var useflaged;
var nodei
var idstrs;
var extjson;
$(function(){
 $.get('/MainInit',
  {
  hre: hrefstr+ '_getinithtml_mainview.TreeList'
 },function(datastr) { 
 
try{
 var tdata= eval("("+ datastr +")"); 
 
 extjson = tdata.firstcom;
 if( extjson.length>0)showextend();
 var ggg= tdata.columns ; 
 var fname= tdata.namefield ;
 		
 var toolba= tdata.button ; 
 
 var extxml= tdata.exterstr ; 
 usefielded = extxml.useflag;
  }
  catch(err) { 
  alert( datastr+ err );
  }
 usefielded = extxml.useflag;
  
  treegrid= $('#tt').treegrid({  
			 rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '/MainInit?hre='+ hrefstr+ '_getrecord_mainview.TreeList',
				
			 toolbar: toolba,
				method: 'get',
				idField: 'id',
		  	treeField: fname  ,
				columns: ggg,
				 onSelect : function( rowIndex, rowData )
				 {
				 nodeid= rowIndex;
				 
				 },
				 onUnselect : function( rowIndex, rowData )
				 {
				 nodeid=undefined;
				 
				 },
				 
				 
					onAfterEdit : function(row,changes)
		 {
				 	if (editType == undefined) 
				 	{
					   editRow = undefined;
					   treegrid.treegrid('unselectAll');
					   return;
				   }

	
     var gh= object2Str( row) ;
				  $.ajax({ 
       url:"/MainEdit", //提交给哪个执行 
     data:"hre="+ hrefstr+ "_"+ editType +"&"+ gh ,
     type:"POST", 
     success: ( function(result) {  
     alert(result)  ;
     		$.messager.show({
								msg : result ,
								title : '成功'
							});
						editRow = undefined;
						editType = undefined;
					 $('#tt').treegrid('unselectAll');
				
					 }    
       )
            
       });

				
     }

				
				 });  
	});
});

		function edit(fffg){
		 var node = treegrid.treegrid('getSelected');
		if (node && node.id) {
			if (editRow != undefined) {
			return;	
			}
	  if (editRow == undefined) {
				treegrid.treegrid('beginEdit', node.id);
				editRow = node;
				editType = fffg;
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}

 
	 	}
	 	 	function add(addstr) {
	 	 	if (editRow != undefined) {
		return;

		} 		
		if (editRow == undefined) 
		{
		 
		 //node = treegrid.treegrid('getSelected');
		 var fff;
		 		if( nodeid==undefined)
		 		fff="";else fff= nodeid.id;
		 $.get("/MainDisp?hre="+ hrefstr+ "_getaddid_mainview.TreeList&pid="+fff,
    function(newid) {
 
 		var row = [ {
		 	id: newid ,
			 
				 _parent: nodeid == null ? '0' : nodeid.id
			} ];
	 treegrid.treegrid('append', {
				parent : nodeid == null? '' : nodeid.id,
				data : row
			});

			editRow = row[0];
			editType = addstr;
			treegrid.treegrid('select', editRow.id);
			treegrid.treegrid('beginEdit', editRow.id);

 });
 
		 

   
   }
	}
	
		 	 function save(){
			 if (editRow != undefined) {
						treegrid.treegrid('endEdit', editRow.id);
				 	}
}
	 function cancel(){
			 	if (editRow) {
						treegrid.treegrid('cancelEdit', editRow.id);
						var p = treegrid.treegrid('getParent', editRow.id);
						if (p) {
							treegrid.treegrid('reload', p.id);
						} else {
							treegrid.treegrid('reload');
						}
						editRow = undefined;
						editType = undefined;
					}
			 }
		function delet() {
		 if (usefielded !=undefined)useflaged= selected[0][usefielded];
    	if( useflaged==1 )
    	{alert("已生效不能删除");
    	return;
    	}
    
    
    
		var node = treegrid.treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【'+node.id+'】？', function(b) {
			alert( '/MainEdit?hre='+ hrefstr+"_Del_otheractions.FormDel_Mtreegrid&id="+ node.id );
				if (b) {

			$.ajax({
						url : '/MainEdit?hre='+ hrefstr+"_Del_otheractions.FormDel_Mtreegrid&id="+ node.id ,
						
						cache : false,
						success : function(r) {
						
								treegrid.treegrid('remove', node.id );
								$.messager.show({
									msg : r,
									title : '提示'
								});
								editRow = undefined;
						
							}
					
					}); 
				}
			});
		}
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
           default: output += i + "=" + encodeURI( val) + "&";
        } 
      } 
      output= output.substring(0,output.length-1) ; 
    } 
    return output; 
  } 

function useflag() {
if( nodeid==undefined)
alert("请选择");
		 if (usefielded !=undefined)useflaged= nodeid[usefielded];
    	if( useflaged==1 )
    	{alert("已生效不能删除");
    	return;
    	}
    

  $.ajax({ 
     url:"/MainUse", //提交给哪个执行 
     data:"hre="+ hrefstr+"_U&id="+ nodeid.id ,
     type:"POST", 
    success: ( function(result) {    
    
   if( result.charAt(0)=="1" )
   {
   var sub_stri = result.substring(2);
   
          	 $('#tt').datagrid('reload');

			      	$.messager.show({
									msg : sub_stri ,
									title : '提示'
								});
							 	treegrid.treegrid('reload');
		    
		      //显示操作结果
            }
           else
           {
           $.messager.show({
									msg : "操作失败" ,
									title : '提示'
								});
           } 
            
        })
    });
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
‪	
<div id="layout" class="easyui-layout"data-options="fit:true">  
<div region="center"   style="margin:0px;padding:0px;background:#eee;border:0;">
<table id="tt"></table> 


</div>
</div>

‪ 

</body>
 </html>
