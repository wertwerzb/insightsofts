<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/formformat.js"></script>

<script type="text/javascript" src="/jsext/datagrid-detailview.js"></script>


<script type="text/javascript"> 
//var myForm 
  var editRow;
	var xdatagrid;
	var otherinfo;
	var secondextjson;
	  var hrefstr= sy.getUrlParam("hrefstr");
	 
 var detailvi;
  var idstrs= sy.getUrlParam("iddstr");
  
  
  if( idstrs==undefined ) idstrs=1;
 $(function(){
 
 $.get("/MainInit",
  {
 hre: hrefstr +"_getinithtml_mainview.TianbaList" 
 },function( datastr ) { 
 alert( datastr );
 var jsonstr1;
 if( datastr.indexOf("⊙")>0 )
 {
 jsonstr1= datastr.split("⊙")[0];
 secondextjson=eval("("+ datastr.split("⊙")[1] +")" );


 showsecondextend();
  }
 else jsonstr1= datastr;
 
try{
 var initstr =eval("("+ jsonstr1 +")");
 
   $("#idstrs").val(idstrs);
   
 var Xtoolba = initstr.button;




var griddata = initstr.columns ;
var ggg = initstr.formatstr ;

otherinfo= initstr.otherinfo ;
 var Xpagesize= otherinfo.Xrowcount ;
  var pagelis="["+ Xpagesize+","+( Xpagesize*2 ) +","+( Xpagesize*3 ) +","+( Xpagesize*4)+"]";
   var Xpagelist= eval( pagelis ); 




  
  var exterstr = initstr.exterstr ;
 
  var awidth= exterstr.awidth; 
  var bwidth= exterstr.bwidth;
 detailvi = exterstr.deview;

 
  var formstr=$("#myForm");
 divformformat( ggg, formstr,awidth,bwidth );

 }
  catch(err) { 
  alert( datastr+ err );
  }  

 $('#tthead').datagrid({  
 toolbar: Xtoolba ,
 });
 
 
 
 xdatagrid= $('#tty').datagrid({ 
  url:'/MainDisp',
  queryParams:{ 
    hre: hrefstr+ '_getXpagerecord_mainview.TianbaList',
    page:1,
    rows: Xpagesize,
    condstr:idstrs
    },
   pagination:true, 
 		 pageList: Xpagelist ,
 		 	pageSize:Xpagesize,
				fitColumns: true,
		singleSelect: true,
	    columns: griddata,
				width:'100%',
				border:0,
				//fit:true,
				
	   method: 'get',
				idField: 'id',
			 striped: true,
			view: detailview,
				detailFormatter:hjj
		
 });  
     $.parser.parse();

  }); 
}); 
	hjj= function(rowIndex,rowData){
	 			var mt= '<table><tr>' +
							'<td style="border:0">' ;
		
				var tdstr= detailvi.split(",");
				for(var i=0;i<tdstr.length;i++ )
				{
				var nnn= tdstr[i].split(":");
				
				mt +='<p>'+ nnn[1] +':'+ rowData[ nnn[0] ]+'</p>';
				}
			 return  mt+'</td>' +'</tr></table>';
			
		} 
	
	 function del(){
	 editRow = xdatagrid.datagrid('getSelected');
	 
    $.ajax({ 
     url:"/MainEdit", //提交给哪个执行 
     
     data:"hre="+ hrefstr+"_Del_otheractions.FormEdit_Mtianba&id="+ editRow.id+"&jionstr="+idstrs,
     type:"POST", 
    success: ( function(result) {    
   if( result.charAt(0)=="1" )
   {
   var sub_stri = result.substring(2);
   
          var delrow =xdatagrid.datagrid('getRowIndex',editRow);
          xdatagrid.datagrid('deleteRow', delrow );
								
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
  
   function add() {
  
   $("#hrefstr").val( hrefstr+"_Add_mainactions.Mtanba");
   
   
   
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
			
     	/*	$.messager.show({
								msg : result ,
								title : '成功'
							}); 	 */
		 $('#tty').datagrid('reload');
		 	}
			});

 }
 function useflag(){
 
 }
 
showsecondextend=function ( ) {

for( var i =0; i <secondextjson.length; i ++ )
{
$('#'+ secondextjson[i].id ).panel({
href: secondextjson[i].href,
height:'300px'

});



//$('#'+ secondextjson[i].id ).panel( 'refresh');



}
 
}

</script>
<body>
<table id="tty"></table> 
<table id="tthead"></table>
<form id="myForm" method="POST">
<input id="hrefstr" type="hidden" name="hre"/>

<input id="idstrs" type="hidden" name="idstrs"/>

</form>
<div id="helpdis" >
	</div>
	
<div id="loadxmldiv">
	</div>

</body>
</html>
		
