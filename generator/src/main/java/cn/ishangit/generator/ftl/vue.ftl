<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <#list fieldList as field>
          <#if field.nameHump!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
          <th>${field.nameCn}</th>
          </#if>
        </#list>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="${domain} in ${domain}s">
        <#list fieldList as field>
          <#if field.nameHump!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
          <td>{{${domain}.${field.nameHump}}}</td>
          </#if>
        </#list>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" v-on:click="edit(${domain})">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger" v-on:click="del(${domain}.id)">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                      aria-hidden="true">×</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <#list fieldList as field>
                <#if field.nameHump!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                <div class="form-group">
                  <label class="col-sm-2 control-label">${field.nameCn}</label>
                  <div class="col-sm-10">
                    <input v-model="${domain}.${field.nameHump}" class="form-control">
                  </div>
                </div>
                </#if>
              </#list>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Pagination from "../../components/pagination";
export default {
  name: "${domain}",
  components: {Pagination},
  data: function () {
    return {
      ${domain}:{},
      ${domain}s: []
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    /**
     * 点击新增
     */
    add() {
      let _this = this;
      _this.${domain} = {};
      $("#form-modal").modal("show");
    },
    /**
     * 点击保存 id有值为修改，无值为新增
     * @param page
     */
    save(page){
      let _this = this;
      // 保存校验
    if (1 != 1
    <#list fieldList as field>
      <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
        <#if !field.nullAble>
      || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
        </#if>
        <#if (field.length > 0)>
      || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length?c})
        </#if>
      </#if>
    </#list>
    ) {
      return;
    }

      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/save',_this.${domain}).then((response)=>{
        Loading.hide();
        _this.${domain} = {};
        if(response.data.success){
          $("#form-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功")
        }else {
          Toast.success(response.data.message)
        }
      })
    },
    /**
     * 列表
     * @param page
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        Loading.hide();
        _this.${domain}s = response.data.content.list;
        _this.$refs.pagination.render(page, response.data.content.total);
      })
    },
    /**
     * 点击修改
     * @param ${domain}
     */
    edit(${domain}){
      let _this = this;
      _this.${domain} = $.extend({}, ${domain});
      $("#form-modal").modal("show");
    },
    /**
     * 点击删除
     * @param id
     */
    del(id) {
      let _this = this;
      Confirm.show("删除${tableNameCn}后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax.delete(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/delete/' + id).then((response) => {
          let res = response.data;
          if (res.success) {
            Loading.hide();
            _this.list(1);
            Toast.success("删除成功")
          }
        })
      })
    }
  }
}
</script>