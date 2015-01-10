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
 
 

$(function(){
  $.get("/MainInit",
  {
  hre: hrefstr+"_get2init_mainview.OuterList"
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
	 statemod="Look";
}

function add(cfg) 
	{	 
	alert( cfg ) ;
	if( statemod!="Add" ){
	 statemod="Add";
	 
	  $('#myForm').form('clear'); 
idstrs=undefined;
	 $('#layout').layout('expand','east') ;

}
else
{

$("#hrefstr").val( hrefstr+"-getaddsql-otheractions.FormAdd-Mouter" );
$("#myForm").form("submit",{
			url :"/FunTest",
			
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
		
 function edit() 
	{	 
	
