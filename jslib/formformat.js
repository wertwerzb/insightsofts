var bugformstr;
var groupformdisp=function( ggg , formstr,awidth,bwidth )
   {
 var groupstr;
 var grostr;
 var tablestr;
 var trbox;
  var tdbox;
  

  
var obString;


 for(var i=0;i<ggg.total;i++)
 {
 
 if( grostr!=ggg.rows[i].group){
     tablestr=$(document.createElement("table")); 
    
     
    tablestr.attr("border","1px");
  var groupstr =$(document.createElement("div")); 
  groupstr.attr("class", "easyui-panel");
  groupstr.attr("title", ggg.rows[i].group);
  groupstr.append(tablestr);
  grostr= ggg.rows[i].group;
  
  formstr.append( groupstr);
 }
  
  if(i==0)
  {
  trbox = $(document.createElement("tr"));
 }
 var editortype;
 var searchst;
var typestr;

 var widthstr;
 if ((ggg.rows[i].editor.type).indexOf("#")>0) {
 
  editortype= ggg.rows[i].editor.type.split("#")[0];
 searchst=" <img onclick=\"chiocepuin('"+ ggg.rows[i].editor.type.split("#")[1] +"') \" src='/jslib/jquery-easyui-1.3/themes/icons/search.png' /> "
 
 widthstr="80%";
 typestr ="";
 
} 
else if ((ggg.rows[i].editor.type).indexOf("_")>0) 

{
typestr =" type=" +ggg.rows[i].editor.type.split("_")[1];
 
}

else
{
 editortype= ggg.rows[i].editor.type;
 searchst="";
 widthstr="100%";
 typestr="";
}


			var bb= (object2String(ggg.rows[i].editor.options)).trim();
			
			var jjj= bb.substring(1,bb.length-1);
			
			 obString ='<input '+typestr+' class=\"easyui-'+ editortype.trim()+'\"'+
			  ' data-options=\"'+ jjj+ 
			 '\" style=\" height:100%; width:'+ widthstr+'\" name =\"'+ ggg.rows[i].field+
			 '\"></input>'+ searchst ;
			

tdbox = $(document.createElement("td"));
 tdbox.append(ggg.rows[i].name);
 tdbox.attr("align","right");
  tdbox.attr("width", (awidth)+"px" ); 
 trbox.append(tdbox);

 tdbox = $(document.createElement("td"));
 tdbox.append( obString );
 	if	( ggg.rows[i].editor.options.height!=undefined )
 	 tdbox.attr("height",ggg.rows[i].editor.options.height);
 var colsp= ggg.rows [i].grouprow.substring(1,2);

  if( parseInt(  colsp )>1)
tdbox.attr("colspan", colsp );
  tdbox.attr("width", (bwidth*colsp )+"px" ); 
  trbox.append(tdbox);
 
  
  if ( ggg.rows[i].grouprow.substring(2,3) =="1")
 {
 var rowsp= ggg.rows[i].grouprow.substring(0,1);
  
  tablestr.append(trbox);
  
  trbox = $(document.createElement("tr"));
  
 if( parseInt(  rowsp )>1)
trbox.attr("rowspan",rowsp);
 }
 } 
 bugformstr= formstr.html();

   }
var tabformdisp=function( ggg , formstr,awidth,bwidth )
   {
     
 var groupstr;
 var grostr;
 var tablestr;
 var trbox;
  var tdbox;
  var tabstr;

  
var obString;
 var tabstr =$(document.createElement("div")); 
 tabstr.attr("class", "easyui-tabs");
  

 for(var i=0;i<ggg.total;i++)
 {
 
 if( grostr!=ggg.rows[i].group){
 
 
 
     tablestr=$(document.createElement("table")); 
  tablestr.attr("border","0");
  var groupstr =$(document.createElement("div")); 
  groupstr.attr("title", ggg.rows[i].group);
  groupstr.append(tablestr);
  grostr= ggg.rows[i].group;
  tabstr.append( groupstr);
  formstr.append( tabstr);
 }
  
  if(i==0)
  {
  trbox = $(document.createElement("tr"));
 }
 var editortype;
 var searchst;

var typestr;

 var widthstr;
 if ((ggg.rows[i].editor.type).indexOf("#")>0) {
 
  editortype= ggg.rows[i].editor.type.split("#")[0];
 searchst=" <img onclick=\"chiocepuin('"+ ggg.rows[i].editor.type.split("#")[1] +"') \" src='/jslib/jquery-easyui-1.3/themes/icons/search.png' /> "
 
 widthstr="80%";
 typestr ="";
}
else if ((ggg.rows[i].editor.type).indexOf("_")>0) 

{
typestr =" type=" +ggg.rows[i].editor.type.split("_")[1];
 
}

else
{
 editortype= ggg.rows[i].editor.type;
 searchst="";
 widthstr="100%";
 typestr ="";
}
			var bb= (object2String(ggg.rows[i].editor.options)).trim();
			
			var jjj= bb.substring(1,bb.length-1);
			
			 obString ='<input '+typestr+' class=\"easyui-'+ editortype.trim()+'\"'+
			  ' data-options=\"'+ jjj+ 
			 '\" style=\" height:100%; width:'+ widthstr+'\" name =\"'+ ggg.rows[i].field+
			 '\"></input>'+ searchst ;
		
tdbox = $(document.createElement("td"));
 tdbox.append(ggg.rows[i].name);
 tdbox.attr("align","right");
  tdbox.attr("width", (awidth)+"px" ); 
 trbox.append(tdbox);

 tdbox = $(document.createElement("td"));
 tdbox.append( obString );
 tdbox.attr("height","25px");
 
 	if	( ggg.rows[i].editor.options.height!=undefined )
 	 tdbox.attr("height",ggg.rows[i].editor.options.height);
 var colsp= ggg.rows [i].grouprow.substring(1,2);

  if( parseInt(  colsp )>1)
tdbox.attr("colspan", colsp );
  tdbox.attr("width", (bwidth*colsp )+"px" ); 
  trbox.append(tdbox);
 
  
  if ( ggg.rows[i].grouprow.substring(2,3) =="1")
 {
 var rowsp= ggg.rows[i].grouprow.substring(0,1);
  
  tablestr.append(trbox);
  
  trbox = $(document.createElement("tr"));
  
 if( parseInt(  rowsp )>1)
trbox.attr("rowspan",rowsp);
 }
 }
 
 bugformstr= formstr.html();

  }
   
   var divformformat=function( ggg, formstr,awidth,bwidth )
   {
   $.extend($.fn.validatebox.defaults.rules, {
 equals: { 
 validator: function(value,param){ 
 return value == $(param[0]).val(); 
  }, 
   message: '字段不相同.'
   } 
   });
 
 var groupstr;
 var tablestr;
 var trbox;
  var tdbox;
var obString;
  tablestr=$(document.createElement("table")); 
  //groupstr.append(tablestr);
//  tablestr.attr("border","1px");
  formstr.append( tablestr );
 
   for(var i=0;i<ggg.total;i++)
 {
  
  if(i==0)
  {
  trbox = $(document.createElement("tr"));
 }
 var editortype;
 var searchst;

 var widthstr;
 var typestr ;
 
 if ((ggg.rows[i].editor.type).indexOf("#")>0) {
 
  editortype= ggg.rows[i].editor.type.split("#")[0];
// searchst=" <img onclick=\"chiocepuin('"+ ggg.rows[i].editor.type.split("#")[1] +"') \" src='/jslib/jquery-easyui-1.3/themes/icons/search.png' /> "
searchst=" <div  id='"+ ggg.rows[i].editor.type.split("#")[1] +"'/> ";
 
 widthstr="80%";
 typestr ="";
}
else if ((ggg.rows[i].editor.type).indexOf("_")>0) 

{
typestr =" type=" +ggg.rows[i].editor.type.split("_")[1];
 
}

else
{
 editortype= ggg.rows[i].editor.type;
 searchst="";
 widthstr="100%";
 typestr="";
}
	var bb= (object2String(ggg.rows[i].editor.options)).trim();
			
			var jjj= bb.substring(1,bb.length-1);
			
			 obString ='<input '+ typestr+' class=\"easyui-'+ editortype.trim()+'\"'+
			  ' data-options=\"'+ jjj+ 
			 '\" style=\" height:100%; width:'+ widthstr+'\" name =\"'+ ggg.rows[i].field+
			 '\"></input>'+ searchst ;


tdbox = $(document.createElement("td"));
 tdbox.append(ggg.rows[i].name);
 tdbox.attr("align","right");
  
  
  tdbox.attr("width", (awidth)+"px" ); 
 trbox.append(tdbox);
 
 tdbox = $(document.createElement("td"));
 tdbox.append( obString );
 tdbox.attr("height","25px");
 
 	if	( ggg.rows[i].editor.options.height!=undefined )
 	 tdbox.attr("height",ggg.rows[i].editor.options.height);

//if( ggg.rows[i].field=='rand' )tdbox.attr("id","rand");
 var colsp= ggg.rows [i].grouprow.substring(1,2);
  
  if( parseInt(  colsp )>1)
tdbox.attr("colspan", colsp );
  tdbox.attr("width", (bwidth*colsp )+"px" );
  trbox.append(tdbox);
  if ( ggg.rows[i].grouprow.substring(2,3) =="1")
 {
 var rowsp= ggg.rows[i].grouprow.substring(0,1);
  tablestr.append(trbox);
  trbox = $(document.createElement("tr"));
  if( parseInt(  rowsp )>1)
trbox.attr("rowspan",rowsp);
 }

 } 
 
 bugformstr= formstr.html();
  
 
  }
  
  
  var twocolumnformat=function( ggg, formstr,awidth,bwidth )
   {
    var tablestr;
 var trbox;
  var tdbox;
  
  
 var obString;
tablestr=$(document.createElement("table")); 

 for(var i=0;i<ggg.total;i++)
 {
  trbox = $(document.createElement("tr"));
 
 var editortype;
 var searchst;
 var widthstr;
 var fonstr;
 var typestr;
  if ((ggg.rows[i].editor.type).indexOf("#")>0) {
 editortype= ggg.rows[i].editor.type.split("#")[0];
 searchst=" <img onclick=\"chiocepuin('"+ ggg.rows[i].editor.type.split("#")[1] +"') \" src='/jslib/jquery-easyui-1.4/themes/icons/search.png' /> ";
 widthstr="80%";
 typestr ="";
 
}
else if ((ggg.rows[i].editor.type).indexOf("_")>0) 

{
typestr =" type=" +ggg.rows[i].editor.type.split("_")[1];
 
}

else
{
 editortype= ggg.rows[i].editor.type;
 searchst="";
 widthstr="100%";
 typestr="";
}

	var bb= (object2String(ggg.rows[i].editor.options)).trim();
			
			var jjj= bb.substring(1,bb.length-1);
			
			 obString ='<input '+ typestr+' class=\"easyui-'+ editortype.trim()+'\"'+
			  ' data-options=\"'+ jjj+ 
			 '\" style=\" height:90%; width:'+ widthstr+'\" name =\"'+ ggg.rows[i].field+
			 '\"></input>'+ searchst ;




tdbox = $(document.createElement("td"));
 tdbox.append(ggg.rows[i] .name);
 tdbox.attr("align","right");
  if( ggg.rows[i].field=='rand' )
 tdbox.attr("id","rand");
  
  tdbox.attr("width", (awidth)+"px" ); 
 trbox.append(tdbox);
 tdbox = $(document.createElement("td"));
 tdbox.append( obString );
 	if	( ggg.rows[i].editor.options.height!=undefined )
 	 tdbox.attr("height",ggg.rows[i].editor.options.height);
 	 else tdbox.attr("height","36px");
 
tdbox.attr("width", (bwidth)+"px" ); 

 trbox.append(tdbox);
tablestr.append(trbox);
  
 }

 formstr.append( tablestr );
   bugformstr= formstr.html();

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


  
  
  