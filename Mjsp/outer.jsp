<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/formformat.js"></script>

<script type="text/javascript">


  var hrefstr= sy.getUrlParam("hrefstr");
var idstrs;
var idf;//id字段
var selected=undefined;
var condistr="1=1";
 
 var mtitle;
 var usefield;
 var useflaged;
 var extjson;
 var innerjson;
 <% String ggg=(String)session.getAttribute("usernamec");
 String ipadress= request.getRemoteAddr();
 %>;
var ipadress='<%=ipadress%>';

var usercode='<%=ggg%>';
 
 

$(function(){
  $.get("/MainInit",
  {
  hre: hrefstr+"_get2init_mainview.OuterList"
 },function(datastr) {

try{
 var ggg=eval("("+ datastr +")");
 extjson =ggg.firstcom;
 innerjson =ggg.innerparam;
 if( extjson.length>0)showextend();
  idf= ggg.idfield;
  var desc= ggg.formatstr ;
  var exterstr = ggg.exterstr ;
  var awidth= exterstr.awidth; 
  var bwidth= exterstr.bwidth;
  var formattable = exterstr.formattable ;
  
  var pagesize= ggg.rowcount ;
  var pagelis="["+ pagesize+","+( pagesize*2 ) +","+( pagesize*3 ) +","+( pagesize*4)+"]";
  
  var toolba= ggg.button ; 
  var griddata= ggg. columns ; 
 
 
  var pagelist= eval( pagelis ); 
  frozenstr =  ggg.frocolumns ; 
  

  var fldata= ggg.exterstr ;
  
  
  mtitle= fldata.mtitle;
  usefield = fldata.useflag;
  
  }
  catch(err) { 
  alert( datastr+ err );
  }
  
  $('#tt').datagrid({  
   url:'/MainDisp',
    queryParams:{ 
    hre: hrefstr+'_getpagerecord_mainview.OuterList',
    page:1,
    condstr:'1=1',
    rows:pagesize
    },
 		pagination:true, 
 		 pageList: pagelist ,
 		 	pageSize:pagesize,
				fitColumns: true,
				singleSelect: true,
		frozenColumns: frozenstr,
				columns: griddata ,
				width:"100%",
				border:0,
				//fit:true,
	   	toolbar: toolba,
			 striped: true,
				 idField:idf,
				 
   onSelect:getSelected,
				 });  
				 statemod="Look";
	 var formstr=$("#myForm");
 


groupformdisp ( desc, formstr, awidth,bwidth );

   $.parser.parse();

		 });
	 })
 

function getpagerecord(condstr ) {
   var nnnn = $('#tt').datagrid('options').queryParams;
  nnnn.condstr= condstr ;
  $('#tt').datagrid('load', nnnn) ;
 
}


function getSelected( index,row )
{

   	idstrs= row[idf];

	 if( idstrs!=undefined ) 
 $('#myForm').form('load', '/MainDisp?hre='+ hrefstr+'_getidjsoin_mainview.OuterList&idstr='+idstrs); 
   
	 $('#layout').layout('expand','east') ;
	 if($("#south").length>0) $('#south').panel('refresh');
	 statemod="Look";
}

function add(cfg) 
	{	 
	if( statemod!="Add" ){
	 statemod="Add";
	 
	  $('#myForm').form('clear'); 
idstrs=undefined;
	 $('#layout').layout('expand','east') ;

}
else
{
// Edit_otheractions.FormEdit_MtreegridAdd_otheractions.FormAdd_Mtreegrid
$("#hrefstr").val( hrefstr+"_"+cfg );
$("#myForm").form("submit",{
			url :"/MainEdit",
			
			onSubmit : function(param) {
				var isValid = $("#myForm").form('validate');
				if (!isValid) {
			 $.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) { 
    alert( result );
     		$.messager.show({
								msg : result ,
								title : '成功'
							}); 	 
			 	 $('#tt').datagrid('reload'); 	
			}
			});
   
}
}
		
 function edit(fgg) 
	{	 
	if( statemod!="Edit" ){
	 $('#layout').layout('expand','east') ;

  statemod="Edit";
  }
  else
  {
$("#hrefstr").val( hrefstr+"_"+fgg );
$("#myForm").form("submit",{
			url :"/MainEdit",
			
			onSubmit : function(param) {
				var isValid = $("#myForm").form('validate');
				if (!isValid) {
			 $.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) { 
    alert( result );
     		$.messager.show({
								msg : result ,
								title : '成功'
							}); 	 
			 	 $('#tt').datagrid('reload'); 	
			}
			});
   

  }
}

function delet() {
getSelected();
    	if (usefield !=undefined)useflaged= selected[0][usefield];
    	if( useflaged==1 )
    	{alert("已生效不能删除");
    	return;
    	}
    
  idstrs= selected[0][idf] ;
 
    $.ajax({ 
     url:"/MainEdit", //提交给哪个执行 
     data:"hre="+ hrefstr+"_Del_mainactions.Modicom&id="+ idstrs ,
     type:"POST", 
    success: ( function(result) {    
   if( result.charAt(0)=="1" )
   {
   var sub_stri = result.substring(2);
   
    	var delrow= 	 $('#tt').datagrid('getRowIndex', idstrs );
          	
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
 
 function chiocepuin (nntn) 
	{	 
	searchstr=nntn;
	 if(useflaged!=1)
	$('#putindiv').window({
	collapsible:false,
  minimizable:false,
  title:titlestr+"选择导入",
   top:80,
   left:20,
   width:560,
   fit:true,
   modal:true,
   href: '/html/putingrid/putingrid2.html'
});
else alert("生效后不能操作");
}

 
function useflag() {


getSelected();
	idstrs= selected[0][idf];
	 
	 $.ajax({ 
     url:"/MainUse", //提交给哪个执行 
     data:"hre="+ hrefstr+"&id="+ idstrs ,
     type:"POST", 
    success: ( function(result) {    
    alert( result );
   if( result.charAt(0)=="1" )
   {
   var sub_stri = result.substring(2);
   
          	 $('#tt').datagrid('reload');

			      	$.messager.show({
									msg : sub_stri ,
									title : '提示'
								});
								 $('#tt').datagrid('reload');
		    
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
<div id="layout" class="easyui-layout"data-options="fit:true ">  


‪<div id="center" region="center"   style="margin:0px;padding:0px;">
<table id="tt"></table> 
</div>
<div id="east" region="east"   collapsed="true"  style="width:60%;overflow: hidden;">

<form id="myForm">
<input id="hrefstr" type="hidden" name="hre"/>

</form>

<div id="save" style=" display:none">
<input type="button"  onclick="updateList()"  value="保存"></input>
</div>
</div>

</div>
</body>
 </html>
