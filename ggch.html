<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var centerTabs;
	var tabsMenu;
<%String tiyeqihuan = (String)session.getAttribute("tiyeqihuan");
	if( tiyeqihuan ==null) tiyeqihuan ="0";
	%>
var tiyeqihuan ='<%=tiyeqihuan %>'; 

	$(function() {
		tabsMenu = $('#tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('type');

				if (type === 'refresh') {
					refreshTab(curTabTitle);
					return;
				}

				if (type === 'close') {
					var t = centerTabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						centerTabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = centerTabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closepppOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					centerTabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});

		centerTabs = $('#centerTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
	})
	function addTab(node,ver) {
	 var titlestr="";
	 var contentstr;
	 var srcstr;
	 
	if( tiyeqihuan==0) srcstr='/home/' + node.attributes.atr1+"/" + node.attributes.atr1+ '.html?hrefstr=' + node.attributes.atr2;
	else srcstr='/help/lyb/menuhelp.jsp?iddstr=' + node.attributes.atr2;

	 
	 if(ver==0)
				{
				 titlestr= '[试]'+ node.text;
				 contentstr= '<iframe src="'+ srcstr+ '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>';
					
				}
			if	 (ver==1)
			{
			 titlestr= '[稳]'+ node.text;
			 contentstr= '<iframe src="/home/' + node.attributes.atr1+ "/"+ node.attributes.atr1+ '.jsp?hrefstr=' + node.attributes.atr2 + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>';
		}
		if (centerTabs.tabs('exists', titlestr )) {
			centerTabs.tabs('select', titlestr );
		} else {
			if (node.attributes.atr1&& node.attributes.atr1.length > 0) 
			{
			
			 	if (node.attributes.atr1.indexOf() == -1) {/*数据源监控页面不需要开启等待提示*/
					$.messager.progress({
						text : '页面加载中....',
						interval : 100
					});
					window.setTimeout(function() {
						try {
							$.messager.progress('close');
						} catch (e) {
						}
					}, 5000);
				}
				centerTabs.tabs('add', {
					title : titlestr ,
					closable : true,
					iconCls : node.iconCls,
					content : contentstr,
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab( titlestr );
						}
					} ]
				});
			} else {
				centerTabs.tabs('add', {
					title : titlestr ,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="/jsp/error/err.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab( titlestr );
						}
					} ]
				});
			}
		}
		
	}
	function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}
</script>
<div id="centerTabs">
	<div title="首页" border="false" href="/jsp/layout/wygl.jsp" style="overflow: hidden;"></div>
</div>
<div id="tabsMenu" style="width: 120px;display:none;">
	<div type="refresh">刷新</div>
	<div class="menu-sep"></div>
	<div type="close">关闭</div>
	<div type="closeOther">关闭其他</div>
	<div type="closeAll">关闭所有</div>
</div>
