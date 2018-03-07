/**
 * Created by melon on 18-2-12.
 */

angular.module('index',[])
.controller('indexCtrl',function($scope,$http) {

    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //轮播上方分类数组数据源
    $scope.categorys = [];
    //商品分类数组数据源
    $scope.categoryss = [];

    //判断是否已经登录
    if(sessionStorage.getItem("name") != null && sessionStorage.getItem("password") !=null && sessionStorage.getItem("id") !=null )  {
        $scope.userData.id = sessionStorage.getItem("id");
        $scope.userData.name = sessionStorage.getItem("name");
        $scope.userData.password = sessionStorage.getItem("password");
        $scope.flag = false;
    }

    //退出登录
    $scope.loggout = function () {
        sessionStorage.clear();
    }

    //轮播图上方到导航栏展示
    $scope.showCategory = function () {
        $http.get("showCategorys")
            .success(function (resp) {
                for(var i=0;i<4;i++){
                    // $scope.categorys.ids[i] = resp[i].id;
                    // $scope.categorys.names[i] = resp[i].name;
                    $scope.categorys[i] = resp[i];
                }
            }).error(function (resp) {
            alert("错误");
        })
    }



    //获取后台category数据源
    $scope.showCategorys = function () {

        $http.get("showCategorys")
            .success(function (resp) {
                for(var i=0;i<17;i++){
                    // $scope.categorys.ids[i] = resp[i].id;
                    // $scope.categorys.names[i] = resp[i].name;
                    $scope.categoryss[i] = resp[i];
                    // console.log($scope.categorys[i]);
                }
            })
            .error(function (resp) {
                alert("失败");
            })
    }

    $scope.show=function () {
        $http.get("showCategorys")
            .success(function (resp) {
                // console.log(resp.length);
                for(var i=0;i<resp.length;i++){
                    $scope.categoryss[i] = resp[i];
                    // console.log("-------------------------------------------");

                }
                // console.log($scope.categoryss);
            })
            .error(function (resp) {
                alert("失败");
            })
    }

})