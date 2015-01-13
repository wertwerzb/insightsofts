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
 <% String ggg=(String)session.getAttribute("usernamec");
 String ipadress= request.getRemoteAddr();
 %>;
var ipadress='<%=ipadress%>';

var usercode='<%=ggg%>';
 
 
 // www.insightsofts.com/MainInit?hre=Ocpzl_getfirstinit_mainview.OuterList
$(function(){
  $.get("/MainInit",
  {
  hre: hrefstr+"_getfirstinit_mainview.OuterList"
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
// var ggg= JSON.parse( jsonstr1 );
  idf= ggg.idfield;
  
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
  // alert("xcc");
  
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
				 idField:idf
				 });  
				
		 });
	 })
 

function getpagerecord(condstr ) {
   var nnnn = $('#tt').datagrid('options').queryParams;
  nnnn.condstr= condstr ;
  $('#tt').datagrid('load', nnnn) ;
 
}


function getSelected()
{
   selected = $('#tt').datagrid('getSelections');
   if( selected==undefined )alert("请先选择一项");
}

function add() 
	{	 
	 statemod="add";
	 $('#secondform').window({
	
  //collapsible:false,
  //minimizable:false,
  title: mtitle + "新增",
   top:80,
   left:20,
width:560,
modal:true,
href: '/Djsp/outersecond.jsp',
});
  $('#myForm').form('clear'); 
idstrs=undefined;

displayList();
}
		
 function edit() 
	{	 
	
	 statemod="edit";
	 getSelected();
	idstrs= selected[0][idf];
	
	
	if (usefield !=undefined)useflaged= selected[0][usefield];
	$('#secondform').window({
	
  //collapsible:false,
  //minimizable:false,
  title: mtitle + "修改",
   top:80,
   left:20,
width:560,
modal:true,
href: '/Djsp/outersecond.jsp',
});

  $('#myForm').form('load', '/MainDisp?hre='+ hrefstr+'_getidjsoin_mainview.OuterList&idstr='+ selected[0][idf] ); 
 
 
displayList();
  
}
 function look() 
	{	 
	
	 statemod="look";
	 getSelected();
	idstrs= selected[0][idf];
	if (usefield !=undefined)useflaged= selected[0][usefield];
	$('#secondform').window({
	
  title: mtitle + "浏览",
   top:80,
   left:20,
width:560,
modal:true,
href: '/Djsp/outersecond.jsp',
});

  $('#myForm').form('load', '/MainDisp?hre='+ hrefstr+'_getidjsoin_mainview.OuterList&idstr='+ selected[0][idf] ); 
 
displayList();
  
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


‪<div region="center"   style="margin:0px;padding:0px;background:#eee;border:0;">
<table id="tt"></table> 
</div>



</div>
<div id="secondform">
</div>
	 
</body>
 </html>
