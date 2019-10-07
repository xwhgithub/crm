var $checkableTree = null;

function initTree(voData) {

    $checkableTree = $('#role-tree').treeview({
        data: voData,
        showIcon: false,
        showCheckbox: true,
        highlightSelected: false,
        onNodeChecked: function (event, node) {
            allChecked(node, true);
        },
        onNodeUnchecked: function (event, node) {
            allChecked(node, false);
        }
    });

    var allChecked = function (node, isChecked) {
        if (node.nodes) {
            for (var i in node.nodes) {
                var child = node.nodes[i];
                // 子节点全部选中
                $checkableTree.treeview(isChecked ? 'checkNode' : 'uncheckNode', [child.nodeId, {silent: false}]);
            }
        }
    };

    // Check/uncheck all
    $('#role-check-all').on('ifChanged', function (e) {
        $checkableTree.treeview(e.target.checked ? 'checkAll' : 'uncheckAll', {silent: true});
    });
    $('#role-expanded-all').on('ifChanged', function (e) {
        $checkableTree.treeview(e.target.checked ? 'expandAll' : 'collapseAll', {silent: true});
    });
}

$(function () {
    $("#send").on("click", function () {
        var data = $("#save-form").serializeObject();
        var checkedNodes = $('#role-tree').treeview("getChecked");
        data.rights = [];
        // 将选中的数据data取出即可
        $.each(checkedNodes, function (idx, node) {
            data.rights.push(node.data);
        });
        //  ajax提交给后台
        var url = $("#save-form").attr("action");
        $.ajax({
            url: url,
            method: "POST",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code == 0) {
                    toastr.success('操作成功！');
                } else {
                    toastr.error('操作失败！');
                }
            },
            error: function () {
                toastr.error("操作异常，请重试！");
            }
        })
    })

    $("#back").on("click", function () {
        window.history.back();
    });
})