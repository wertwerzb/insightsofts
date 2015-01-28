<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/formformat.js"></script>


<script type="text/javascript">   
 
 var myForm ;
 var flagfields;
  var innerjson;
  var extjson;
 var hrefstr= sy.getUrlParam("hrefstr");
 
 $(function(){
 $.get("/MainInit",
  {
 hre: hrefstr +"_getinithtml_mainview.LybList" 
 },function( datastr ) {

 try{ 
 var tdata=eval("("+ datastr +")");
 extjson = tdata.firstcom;
 innerjson = tdata.innerparam;
 if( extjson.length>0)showextend();
 var ggg= tdata.formatstr ;
 var exterstr = tdata.exterstr ;
  var awidth= exterstr.awidth; 
  var bwidth= exterstr.bwidth;
flagfields = exterstr.flagfields ;
if(flagfields==undefined ) flagfields ="ALL⊙";
 
}
  catch(err) { 
  alert( datastr+ err );
  }
 var formstr=$("#myForm");
  divformformat( ggg, formstr,awidth,bwidth );
  $.parser.parse();
 
 if( innerjson.length>0) showinnerext();
 // displayList();
 }); 
}); 

 
   function add() {

 $("#hrefstr").val( hrefstr+"_Add_otheractions.FormAdd_Mlyb" );
$("#myForm").form("submit",{
			url :"/MainEdit", 			onSubmit : function(param) 
			{
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
			}
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


showinnerext=function ( ) {

for( var i =0; i <innerjson.length; i ++ )
{
$('#'+ innerjson[i].id ).panel({
href: innerjson[i].href,
});

$('#'+ innerjson[i].id ).panel( 'refresh');

 

}
 
}
dispdiv =function ( ) {
alert( $("#myForm").html() );
}
</script>
<div id="layout" class="easyui-layout"data-options="fit:true ">  


‪<div id="center" region="center"   style="margin:0px;padding:0px;">
<form id="myForm">
<input id="hrefstr" type="hidden" name="hre"/>
</form>

<input id="savebutton" type="button"  onclick="add()"  value="保存"></input>


</div>
</div>
	


		
