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

    <div class="row">
      <div v-for="course in courses" class="col-md-4">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
          <img v-show="course.image" class="media-object" v-bind:src="course.image" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
                {{COURSE_LEVEL | optionKV(course.level)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_CHARGE | optionKV(course.charge)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_STATUS | optionKV(course.status)}}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>
            <p>
              <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
            </p>
            <p>{{course.summary}}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">排序：{{course.sort}}</span>
              <span class="badge badge-info">时长：{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                大章
              </button>&nbsp;
              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                编辑
              </button>&nbsp;
              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

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
                  <div class="form-group">
                    <label class="col-sm-2 control-label">分类</label>
                    <div class="col-sm-10">
                      <ul id="tree" class="ztree"></ul>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <input v-model="course.name" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">概述</label>
                    <div class="col-sm-10">
                      <input v-model="course.summary" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">时长</label>
                    <div class="col-sm-10">
                      <input v-model="course.time" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">价格（元）</label>
                    <div class="col-sm-10">
                      <input v-model="course.price" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">封面</label>
                    <div class="col-sm-10">
                      <input v-model="course.image" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">级别</label>
                    <div class="col-sm-10">
                      <select v-model="course.level" class="form-control">
                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">收费</label>
                    <div class="col-sm-10">
                      <select v-model="course.charge" class="form-control">
                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-10">
                      <select v-model="course.status" class="form-control">
                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">报名数</label>
                    <div class="col-sm-10">
                      <input v-model="course.enroll" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">顺序</label>
                    <div class="col-sm-10">
                      <input v-model="course.sort" class="form-control">
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
  name: "course",
  components: {Pagination},
  data: function () {
    return {
      course:{},
      courses: [],
      COURSE_LEVEL: COURSE_LEVEL,
      COURSE_CHARGE: COURSE_CHARGE,
      COURSE_STATUS: COURSE_STATUS,
      category:{},
      tree:{},
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
    _this.allCategory();
  },
  methods: {
    /**
     * 点击新增
     */
    add() {
      let _this = this;
      _this.course = {};
      _this.tree.checkAllNodes(false);
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
      || !Validator.require(_this.course.name, "名称")
      || !Validator.length(_this.course.name, "名称", 1, 50)
      || !Validator.length(_this.course.summary, "概述", 1, 2000)
    ) {
      return;
    }
      // 校验是否选好分类
      let categorys = _this.tree.getCheckedNodes();
      if (Tool.isEmpty(categorys)) {
        Toast.warning("请选择分类！");
        return;
      }
      _this.course.categorys = categorys;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/course/save',_this.course).then((response)=>{
        Loading.hide();
        _this.course = {};
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
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/course/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        Loading.hide();
        _this.courses = response.data.content.list;
        _this.$refs.pagination.render(page, response.data.content.total);
      })
    },
    /**
     * 点击修改
     * @param course
     */
    edit(course){
      let _this = this;
      _this.listCategory(course.id);
      _this.course = $.extend({}, course);
      $("#form-modal").modal("show");
    },
    /**
     * 点击删除
     * @param id
     */
    del(id) {
      let _this = this;
      Confirm.show("删除课程后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/course/delete/' + id).then((response) => {
          let res = response.data;
          if (res.success) {
            Loading.hide();
            _this.list(1);
            Toast.success("删除成功")
          }
        })
      })
    },
    /**
     * 点击【大章】
     */
    toChapter(course) {
      let _this = this;
      SessionStorage.set("course", course);
      _this.$router.push("/business/chapter");
    },
    /**
     *  获取所有分类信息
     */
    allCategory() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all').then((response)=>{
        Loading.hide();
        let resp = response.data;
        _this.categorys = resp.content;
        _this.initTree();
      })
    },
    /**
     *  初始化下拉树
     */
    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "00000000",
            enable: true
          }
        }
      };
      let zNodes = _this.categorys;
      // 存储分类数据
      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
    },
    /**
     * 查找课程下所有分类
     * @param courseId
     */
    listCategory(courseId) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res)=>{
        Loading.hide();
        console.log("查找课程下所有分类结果：", res);
        let response = res.data;
        let categorys = response.content;
        // 勾选查询到的分类,先将树中勾选清空，再根据查询分类数据进行勾选
        _this.tree.checkAllNodes(false);
        for (let i = 0; i < categorys.length; i++) {
          //比对节点数据，进行勾选
          let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
          _this.tree.checkNode(node, true);
        }
      })
    },
  }
}
</script>

<style scoped>
.caption h3 {
  font-size: 20px;
}
</style>