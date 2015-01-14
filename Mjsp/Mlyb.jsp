<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" src="/jslib/formformat.js"></script>


<script type="text/javascript">   
 
 var myForm ;
 var flagfields;
  var secondextjson;
 var hrefstr= sy.getUrlParam("hrefstr");
 
 $(function(){
 $.get("/MainInit",
  {
 hre: hrefstr +"_getinithtml_mainview.LybList" 
 },function( datastr ) {
 var jsonstr1;
 if( datastr.indexOf("⊙")>0 )
 {
 secondextjson=eval("("+ datastr.split("⊙")[1] +")" );
 jsonstr1= datastr.split("⊙")[0] ;

  }
 else jsonstr1= datastr;
 try{
 var tdata= eval("("+ jsonstr1 +")"); 
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
 if( datastr.indexOf("⊙")>0 )
 showsecondextend();
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
showsecondextend=function ( ) {

for( var i =0; i <secondextjson.length; i ++ )
{
alert( secondextjson[i].href );
$('#'+ secondextjson[i].id ).panel({
href: secondextjson[i].href,


});

$('#'+ secondextjson[i].id ).panel( 'refresh');

 

}
 
}
dispdiv =function ( ) {
alert(bugformstr);
}
</script>
<div>
<form id="myForm">
<input id="hrefstr" type="hidden" name="hre"/>
</form>

<input id="savebutton" type="button"  onclick="add()"  value="保存"></input>

<input id="saveb" type="button"  onclick="dispdiv()"  value="vghj"></input>

</div>


<div id="helpdis">
	</div>
	
<div id="loadxmldiv">
	</div>
<div id="putindiv">

	</div>
	 
<div id="imagedis">
	</div>

		
