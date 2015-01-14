<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/jquery.form.js"></script>

	<script type="text/javascript" src="/jsext/datagrid-detailview.js"></script>
	 	<script type="text/javascript" src="/html/fzcom/fzcom2.js"></script>
<title>表格模板管理</title>
<script type="text/javascript">
var request ={QueryString:function(val) 
　　{var uri = window.location.search; 
　　var re = new RegExp("" +val+ "=([^&?]*)", "ig"); 
　　return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
     }}
     
		 var paramP;
 var paramR;
 var jspnewsource ;
 var idstr;
     var condistr="1=1";
  var hrefstr= request.QueryString("hrefstr");

	var deview ;
var editRow;
	var datagrid;

$(function(){
 $.get('/MainInit',
  {
  hre: hrefstr+ '_getinit_mainview.DetailList'
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

 var extendstr= ggg.exterstr ;
deview=extendstr.detailview;
}
  catch(err) { 
  alert( datastr+ err );
  }
datagrid= $('#tt').datagrid({  
   url:'/MainDisp',
    queryParams:{ 
    hre: hrefstr+ '_getpagerecord_mainview.DetailList',
    page:1,
    condstr:'1=1',
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
				view: detailview,
				      detailFormatter:function(index,row){  
				 return '<div class="ddv" style="padding:5px 0"></div>'; },
				 
				 onExpandRow: function(index,row){
		 var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv'); 
	 paramP = row.paramP;
 paramR = row.paramR;
 jspnewsource = row. jspnewsource;
 idstr= row.id;
 
	ddv.panel({ 
border:false, 
cache:false, 
href:'/html/fzdiv/accessctrl.html', 
				             onLoad:function(){ 
  $('#tt').datagrid('fixDetailRowHeight',index); } 
				  }); 
	    $('#tt').datagrid('fixDetailRowHeight',index);  
	  
	    
	    }
				 });  
	 });
	 
});
	  function CallFunc1(rowIndex,rowData)  { 
	 
	var  mm= deview.name ;

 return eval(mm+"(rowIndex ,rowData)");
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

 function updatecheck(ddd) {
  var queryString="" ;
  var form1= $("#"+ddd) ;
  $.each( form1.serializeArray(), function(index) {
queryString += this['name'];
	
		});

$.ajax({ 
     url:"/MainEdit",  data:"hre="+ hrefstr+"_getmodsql_otheractions.FormEdit_Mdetailview&paramR="+ queryString+"&id="+ ddd ,
     type:"POST", 
    success: ( function(result) {  
  alert( result );
  })
  })
  }

</script>

</head>
<body> <div id="layout" class="easyui-layout"data-options="fit:true">  
<div region="center"   style="margin:0px;padding:0px;background:#eee;border:0;">
<table id="tt"></table> 
</div>



</div>
</div>

‪ 
</body>
 </html>
