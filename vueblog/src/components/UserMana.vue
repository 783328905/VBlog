<template>
  <div v-loading="loading">
    <div style="margin-top: 10px;display: flex;justify-content: center">
      <el-input
        placeholder="默认展示部分用户，可以通过用户名搜索用户..."
        prefix-icon="el-icon-search"
        v-model="keywords" style="width: 400px" size="small">
      </el-input>
      <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 3px" @click="searchClick">搜索
      </el-button>
    </div>
    <div style="display: flex;justify-content: space-around;flex-wrap: wrap">
      <el-card style="width:330px;margin-top: 10px;" v-for="(user,index) in users" :key="index"
               v-loading="cardloading[index]">
        <div slot="header" style="text-align: left">
          <span>{{user.name}}</span>
          <el-button style="float: right; padding: 3px 0;color: #ff0509" type="text" icon="el-icon-delete"
                     @click="deleteUser(user.id)">删除
          </el-button>
        </div>
        <div>
          <div><img :src="user.avatar" :alt="user.name" style="width: 70px;height: 70px"></div>
          <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
            <span>用户名:</span><span>{{user.username}}</span>
          </div>
          <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
            <span>电子邮箱:</span><span>{{user.email}}</span>
          </div>
          <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
            <span>注册时间:</span><span>{{user.created | formatDateTime}}</span>
          </div>
          <div
            style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px;display: flex;align-items: center">
            <span>用户状态:</span>
            <el-switch
              v-model="user.enabled"
              active-text="启用"
              active-color="#13ce66"
              @change="enabledChange(user.enabled,user.username,user.id,index)"
              inactive-text="禁用" style="font-size: 12px">
            </el-switch>
          </div>
          <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
            <span>用户角色:</span>
            <el-tag
              v-for="(role,index) in user.roles"
              :key="role.rid"
              size="mini"
              style="margin-right: 8px"
              type="success">
              {{role.rname}}
            </el-tag>
            <el-popover
              placement="right"
              title="角色列表"
              width="200"
              :key="index+''+user.id"
              @hide="saveRoles(user.id,index)"
              trigger="click" v-loading="eploading[index]">
              <el-select v-model="roles" multiple placeholder="请选择" size="mini">
                <el-option
                  v-for="(item,index) in allRoles"
                  :key="item.rid"
                  :label="item.rname"
                  :value="item.rid">
                </el-option>
              </el-select>
              <el-button type="text" icon="el-icon-more" style="padding-top: 0px" slot="reference"
                         @click="showRole(user.roles,user.id,index)"></el-button>
            </el-popover>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
  import {getRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  import {deleteRequest} from '../utils/api'
  export default{
    mounted: function () {
      this.loading = true;
      this.loadUserList();
      this.cardloading = Array.apply(null, Array(20)).map(function (item, i) {
        return false;
      });
      this.eploading = Array.apply(null, Array(20)).map(function (item, i) {
        return false;
      });
    },
    methods: {
      saveRoles(id, index){
        var selRoles = this.roles;
        if (this.cpRoles.length == selRoles.length) {
          for (var i = 0; i < this.cpRoles.length; i++) {
            for (var j = 0; j < selRoles.length; j++) {
              if (this.cpRoles[i].id == selRoles[j]) {
                selRoles.splice(j, 1);
                break;
              }
            }
          }
          if (selRoles.length == 0) {
            return;
          }
        }
        var _this = this;
        _this.cardloading.splice(index, 1, true)
        putRequest("/admin/user/role", {rids: this.roles, id: id}).then(resp=> {
          if (resp.status == 200 && resp.data.status == 'success') {
            _this.$message({type: resp.data.status, message: resp.data.msg});
            _this.loadOneUserById(id, index);
          } else {
            _this.cardloading.splice(index, 1, false)
            _this.$message({type: 'error', message: '更新失败!'});
          }
        }, resp=> {
          _this.cardloading.splice(index, 1, false)
          if (resp.response.status == 403) {
            var data = resp.response.data;
            _this.$message({type: 'error', message: data});
          }
        });
      },
      showRole(aRoles, id, index){
        this.cpRoles = aRoles;
        this.roles = [];
        this.loadRoles(index);
        for (var i = 0; i < aRoles.length; i++) {
          this.roles.push(aRoles[i].id);
        }
      },
      deleteUser(id){
        var _this = this;
        this.$confirm('删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
          deleteRequest("/admin/user/" + id).then(resp=> {
            if (resp.status == 200 && resp.data.status == 'success') {
              _this.$message({type: 'success', message: '删除成功!'})
              _this.loadUserList();
              return;
            }
            _this.loading = false;
            _this.$message({type: 'error', message: '删除失败!'})
          }, resp=> {
            _this.loading = false;
            _this.$message({type: 'error', message: '删除失败!'})
          });
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      enabledChange(enabled, username,id,index){
        var dateString = '';
        var _this = this;
        _this.cardloading.splice(index, 1, true);
        if(!enabled) {
          this.$prompt('请输入封禁时间', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputPattern: /^[1-9]\d*|0$^[\u4e00-\u9fa5]{0,}$/,
            inputErrorMessage: '时间格式不正确'
          }).then(({value}) => {
            dateString = value;
            _this.cardloading.splice(index, 1, true);

            putRequest("/admin/user/enabled", {
              enabled: enabled,
              username: username,
              dateString: dateString
            }).then(resp => {
              if (resp.status != 200) {
                _this.$message({type: 'error', message: '更新失败!'})
               _this.loadOneUserById(id, index);
                return;
              }
              _this.cardloading.splice(index, 1, false);
              _this.$message({type: 'success', message: '更新成功!'})
            });

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '操作已取消'
            });

          });
        }else {
//          this.$confirm('解封该用户, 是否继续?', '提示', {
//            confirmButtonText: '确定',
//            cancelButtonText: '取消',
//            type: 'warning'
//          }).then(() => {

            _this.cardloading.splice(index, 1, true);
            putRequest("/admin/user/enabled", {
              enabled: enabled,
              username: username,
              dateString: dateString
            }).then(resp => {
              if (resp.status != 200) {
                _this.$message({type: 'error', message: '更新失败!'})
                _this.loadOneUserById(id, index);
                return;
              }
              _this.cardloading.splice(index, 1, false);
              _this.$message({type: 'success', message: '更新成功!'})
            });

//          }).catch(() => {
//              _this.$message({
//                type: 'info',
//                message: '操作已取消'
//              });
//
//            });

        }
      },
      loadRoles(index){
        var _this = this;
        _this.eploading.splice(index, 1, true)
        getRequest("/admin/roles").then(resp=> {
          _this.eploading.splice(index, 1, false)
          if (resp.status == 200) {
            _this.allRoles = resp.data;
          } else {
            _this.$message({type: 'error', message: '数据加载失败!'});
          }
        }, resp=> {
          _this.eploading.splice(index, 1, false)
          if (resp.response.status == 403) {
            var data = resp.response.data;
            _this.$message({type: 'error', message: data});
          }
        });
      },
      loadOneUserById(id, index){
        var _this = this;
        getRequest("/admin/user/" + id).then(resp=> {
          _this.cardloading.splice(index, 1, false)
          if (resp.status == 200) {
            _this.users.splice(index, 1, resp.data);
          } else {
            _this.$message({type: 'error', message: '数据加载失败!'});
          }
        }, resp=> {
          _this.cardloading.splice(index, 1, false)
          if (resp.response.status == 403) {
            var data = resp.response.data;
            _this.$message({type: 'error', message: data});
          }
        });
      },
      loadUserList(){
        var _this = this;
        getRequest("/admin/user?nickname="+this.keywords).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            _this.users = resp.data;
          } else {
            _this.$message({type: 'error', message: '数据加载失败!'});
          }
        }, resp=> {
          _this.loading = false;
          if (resp.response.status == 403) {
            var data = resp.response.data;
            _this.$message({type: 'error', message: data});
          }
        });
      },
      searchClick(){
        this.loading = true;
        this.loadUserList();
      }
    },
    data(){
      return {
        loading: false,
        eploading: [],
        cardloading: [],
        keywords: '',
        users: [],
        allRoles: [],
        roles: [],
        cpRoles: []
      }
    }
  }
</script>
