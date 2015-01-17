<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>

<script type="text/javascript">   
 var myForm ;
 
 

  var flagfields;
 var hrefstr= sy.getUrlParam("hrefstr");
 $(function(){
 $.get("/MainCheck",
  {
 hre: hrefstr +"_init" 
 },function( datastr ) { 
 

try{
 var titlestr = datastr.split("⊙");
 var ggg= eval("("+ titlestr[0] +")");
  var exterstr = eval("("+ titlestr[1] +")");
   var awidth= exterstr.awidth; 
  var bwidth= exterstr.bwidth;
}
  catch(err) { 
  alert( datastr+ err );
  }
 var formstr=$("#myForm");
  twocolumnformat ( ggg, formstr,awidth,bwidth );

   $.parser.parse();
}); 
}); 

 
 
   function check() {
$("#hrefstr").val( hrefstr+"_checkstr" );
$("#myForm").form("submit",{
			url :"/MainCheck",
			onSubmit : function(param) {
				var isValid = $("#myForm").form('validate');
				if (!isValid) {
			 $.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) { 	
			if( result.indexOf("=")>0 )
			
				$("#rand").html( result );
			else top.location= result ;
			// window.navigate( result );
			}
			
			
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
</script>
<div>
<form id="myForm">

<input id="hrefstr" type="hidden" name="hre"/>


</form>

<input id="savebutton" type="button"  onclick="check()"  value="确定"></input>
</div>

<div id="putindiv">
	</div>

		
