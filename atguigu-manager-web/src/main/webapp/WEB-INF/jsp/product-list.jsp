<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<jsp:include page="/common/head.jsp" />
<title>产品列表</title>
<link rel="stylesheet"
	  href="/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
</head>
<body class="pos-r">
<div class="pos-a"
	 style="width: 200px; left: 0; top: 0; bottom: 0; height: 100%; border-right: 1px solid #e5e5e5; background-color: #f5f5f5; overflow: auto;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="margin-left: 200px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		产品管理 <span class="c-gray en">&gt;</span> 产品列表 <a
			class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l"> 
				<a href="javascript:;" onclick="product_dels()"
				   class="btn btn-danger radius">
					<i class="Hui-iconfont">&#xe6e2;</i>批量删除
				</a>
				<a class="btn btn-primary radius"
				   onclick="product_edit('编辑产品','product-add')" href="javascript:;">
						<i class="Hui-iconfont">&#xe60c;</i> 编辑产品
				</a>
				</span>
		</div>
		<div id="testIframe" class="mt-20">
			<table
					class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
				<tr class="text-c">
					<th><input type="checkbox" class="checkall" /></th>
					<th width="90">商品ID</th>
					<th >商品标题</th>
					<th width="150">商品价格(单位为分)</th>
					<th width="80">库存</th>
				</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript"
		src="/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
		src="/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript"
		src="/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript"
		src="/lib/datatables/jquery.jeditable.js"></script>
<script type="text/javascript" src="/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
    var setting = {
        //定义树显示方式
        view : {
            dblClickExpand : false,
            showLine : false,
            selectedMulti : false
        },
        //定义数据参数
        data : {
            simpleData : {
                enable : true,
                idKey : "id",
                pIdKey : "parentId",
                rootPId : ""
            }
        },
        callback : {
            beforeClick : function(treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
                //判断是否有子类目,没子类目不展开
                if (treeNode.isParent) {
                    return false;
                } else {
                    //demoIframe.attr("src","kasg.jsp");
                    //判断是顶级类目，查询该类下商品
                    findProduct(treeNode.id);
                    return true;
                }
            }

        }
    };

    //初始化商品分类菜单树
    $(document).ready(function() {
        var zNodes;
        $.ajax({
            url : "/restful/page/category",
            type : "GET",
            dataType : "json",
            async : false,
            success : function(data) {
                zNodes = data;
            }
        });
        var t = $("#treeDemo");
        t = $.fn.zTree.init(t, setting, zNodes);
        //demoIframe = $("#testIframe");
        //sdemoIframe.on("load", loadReady);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        //zTree.selectNode(zTree.getNodeByParam("id", '2'));
        refreshDataTable(null);
    });
    var vcid = null;
    var refreshDataTable=function(cid) {
        vcid=cid;
        //重新构建table
        $('.table-sort').dataTable().fnClearTable();
        $('.table-sort').dataTable().fnDestroy();

        var table = $('.table-sort').dataTable({
            "bProcessing" : false, //是否显示加载
            "sAjaxSource" : '/restful/page/product/', //请求资源路径
            "serverSide" : true, //开启服务器处理模式
            "destroy" : true, //重新加载表格
            /*
            使用ajax，在服务端处理数据
            sSource:即是"sAjaxSource"
            aoData:要传递到服务端的参数
            fnCallback:处理返回数据的回调函数
             */
            "fnServerData" : retrieveData,

            "columns" : [
                {
                    "sClass" : "text-c",
                    "data": "id",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false
                },
                {data : "id"},
                {data : "title"},
                {data : "price"},
                {data : "num"}
            ]
        });
    }
    function retrieveData(sSource, aoData, fnCallback) {
        $.ajax({
            'type' : 'GET',
            "url" : sSource,
            "dataType" : "json",
            "data" : {
                "aodata" : JSON.stringify(aoData),
                "cid" : vcid
            },
            "success" : function(resp) {
                fnCallback(resp);
            }
        });
    }
    //查询分类下商品
    function findProduct(cid) {
        vcid=cid;
        refreshDataTable(cid);
    }

    /*产品批量删除*/
    function product_dels(){
        layer.confirm('确认要删除选中商品吗？',function(index){
            //取出所有选中
            var ids  = $('input:checkbox:checked').map(function(){
                if($(this).val()!="on"){
                    return $(this).val();
                };
            }).get().join(",");
            if(!ids){
                layer.msg('没有选择商品!',{icon:2,time:1000});
                return ;
            }

            $.ajax({
                type: 'post',
                url: '/restful/page/product',
                dataType: 'json',
                data:{"ids":ids,"_method":"DELETE"},
                success: function(data){
                    refreshDataTable(null);
                    layer.msg('已删除!',{icon:1,time:1000});
                },
                error:function(data) {
                    console.log(data.msg);
                },
            });
        });
    }


    //添加产品
    function product_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
</script>
</body>
</html>