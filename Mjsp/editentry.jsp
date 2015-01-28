<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>

<script type="text/javascript" src="/jslib/formformat.js"></script>
	


<title>主从明细模板管理</title>
<script type="text/javascript">

     
  var hrefstr= sy.getUrlParam ("hrefstr"); 
var idf;
var selected=undefined;
var condistr="1=1";
var titlestr;
 var flclass ;
 var mtitle;
 var usefielded;
 var useflaged;
 var statemoded;
 var idstrs;
 
$(function(){
  $.get("/MainInit?hre="+hrefstr +"_get2init_mainview.EntList ",function(datastr) { 
try{

 var ggg=eval("("+ datastr +")");  
 var mtoolba= ggg.firstbutton ; 
  idf= ggg.idfield;
  var mpagesize= ggg.rowcount ;
  var mpagelis="["+ mpagesize+","+( mpagesize*2 ) +","+( mpagesize*3 ) +","+( mpagesize*4)+"]";
var mgriddata= ggg.columns ; 
 var mpagelist= eval( mpagelis ); 
 var Xtoolba = ggg.mxlistbutton;
 
var exterstr= ggg.exterstr ;
  var mform = ggg.mformatstr ;
  var Mawidth = exterstr.Mawidth ;
 var Mbwidth = exterstr.Mbwidth ;
var Xpagesize= ggg.Xrowcount ;
  var xpagelis="["+ Xpagesize+","+( Xpagesize*2 ) +","+( Xpagesize*3 ) +","+( Xpagesize*4)+"]";
  
   var Xpagelist= eval( xpagelis ); 
 
  var Xgriddata = ggg.xcolumns ;
  }
  catch(err) { 
  alert( datastr+ err );
  }
  $('#ttumx').datagrid({
	 toolbar: Xtoolba,
 });
  $('#tt').datagrid({  
    url:'/MainDisp',
    queryParams:{ 
    hre: hrefstr+ '_getpagerecord_mainview.EntList',
    pagestr:1,
    condstr:'1=1',
    rows:mpagesize
    },
 		pagination:true, 
 		 pageList: mpagelist ,
 		 	pageSize:mpagesize,
				fitColumns: true,
				singleSelect: true,
		columns: mgriddata,
				width:"100%",
				border:0,
				 rownumbers : true,
				//fit:true,
	   	toolbar: mtoolba,
			 striped: true,
				 idField:idf,
		onSelect:getSelected
				 });  
		  var formstr=$("#myuForm");
  divformformat( mform ,formstr, Mawidth ,Mbwidth );
  
   $.parser.parse();
   udatagrid= $('#ttu').datagrid({  
   rownumbers : true,
   pagination:true, 
 		 pageList: Xpagelist ,
 		 	pageSize:Xpagesize,
				fitColumns: true,
		singleSelect: true,
	    columns: Xgriddata,
				width:'100%',
				border:0,
		  method: 'get',
				idField: 'id',
			 striped: true
	  })
            

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
 $('#myuForm').form('load', '/MainDisp?hre=Oxjcg_getMidjsoin_mainview.EntList&idstr='+idstrs); 
   $('#ttu').datagrid('load',
   '/MainDisp?hre=Oxjcg_getXpagerecord_mainview.EntList&page=1&rows=12&idstr='+idstrs);
	 $('#layout').layout('expand','east') ;
	 statemod="Look";
}

 function add(cfg) 
	{	 
	alert("gggg");
	if( statemod!="Add" ){
	 statemod="Add";
	 
	  $('#myuForm').form('clear');
   
idstrs=undefined;
	 $('#layout').layout('expand','east') ;

}
else
{

$("#hrefstr").val( hrefstr+"-getaddsql-otheractions.FormAdd-Meditentry");
$("#myuForm").form("submit",{
			url :"/FunTest",
			
			onSubmit : function(param) {
				var isValid = $("#myuForm").form('validate');
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
			 	 statemod="Look";
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
$("#hrefstr").val( hrefstr+"-getmodsql-otheractions.FormEdit-Meditentry-Meditentry");
$("#myuForm").form("submit",{
			url :"/FunTest",
			
			onSubmit : function(param) {
				var isValid = $("#myuForm").form('validate');
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
 
</script>

</head>
<body>
<div id="layout" class="easyui-layout"data-options="fit:true ">  


‪<div id="center" region="center"   style="margin:0px;padding:0px;">
<table id="tt"></table> 
</div>
<div id="east" region="east"   collapsed="true"  style="width:60%;overflow: hidden;">
	<div id="headtool">
<table id="ttuhead"></table>
</div>
<form id="myuForm">
<input id="hrefstr" type="hidden" name="hre"/>
<input id="idstr" type="hidden" name="id"/>



</form>
<div id="mxtool">

<table id="ttumx"></table>
</div>
<div id="mxgrid">
<table id="ttu"></table> 
</div>

</div>

</div>
</body>
 </html>
