<template>
  <div>
    <h3>{{course.name}}</h3>
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
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
        <th>ID</th>
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{ chapter.id }}</td>
        <td>{{ chapter.name }}</td>
        <td>{{ chapter.courseId }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" v-on:click="edit(chapter)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger" v-on:click="del(chapter.id)">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal"  class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">×</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label  class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
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
  name: "chapter",
  components: {Pagination},
  data: function () {
    return {
      chapter:{},
      chapters: [],
      course:{},
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 5;
    let course = SessionStorage.get("course") || {};
    if (Tool.isEmpty(course)) {
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.list(1);
  },
  methods: {
    /**
     * 点击新增
     */
    add() {
      let _this = this;
      _this.chapter = {};
      $("#form-modal").modal("show");
    },
    /**
     * 点击保存 id有值为修改，无值为新增
     * @param page
     */
    save(page){
      let _this = this;
      // 保存校验
      if (!Validator.require(_this.chapter.name, "名称")) {
        return;
      }
      _this.chapter.courseId = _this.course.id;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/save',_this.chapter).then((response)=>{
        Loading.hide();
        _this.chapter = {};
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
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list', {
        page: page,
        size: _this.$refs.pagination.size,
        courseId: _this.course.id,
      }).then((response) => {
        Loading.hide();
        _this.chapters = response.data.content.list;
        _this.$refs.pagination.render(page, response.data.content.total);
      })
    },
    /**
     * 点击修改
     * @param chapter
     */
    edit(chapter){
      let _this = this;
      _this.chapter = $.extend({}, chapter);
      $("#form-modal").modal("show");
    },
    /**
     * 点击删除
     * @param id
     */
    del(id) {
      let _this = this;
      Confirm.show("删除大章后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/chapter/delete/' + id).then((response) => {
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