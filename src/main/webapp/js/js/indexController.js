/**
 * Created by melon on 18-2-12.
 */

angular.module('index',[])

.controller('indexCtrl',function($scope,$http,$location) {

    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //轮播上方分类数组数据源
    $scope.categorys = [];
    //商品分类数组数据源
    $scope.categoryss = [];
    //分类产品栏展示数目
    $scope.productcount=5;
    //临时对象
    $scope.searchProducts = {};
    //搜索关键字
    $scope.search = {};
    //购物车数量
    $scope.cartCount = {};
    //跳转路径
    var locationUrl = $location.absUrl().replace("home","searchResult");

    //判断是否已经登录
    if(sessionStorage.getItem("name") != null && sessionStorage.getItem("password") !=null && sessionStorage.getItem("id") !=null )  {
        $scope.userData.id = sessionStorage.getItem("id");
        $scope.userData.name = sessionStorage.getItem("name");
        $scope.userData.password = sessionStorage.getItem("password");
        $scope.flag = false;
    }else {
        localStorage.removeItem("cartCount");
    }
    //退出登录
    $scope.loggout = function () {
        sessionStorage.clear();
        localStorage.removeItem("cartCount");
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
                    $scope.categoryss[i] = resp[i];
                }
            })
            .error(function (resp) {
                alert("失败");
            })
    }

    //获取分类产品的subTitle
    $scope.show = function () {
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

    //搜索
    $scope.searchFunction = function () {
        $http.post('searchProduct',{data:$scope.search.keyword})
            .success(function (resp) {
                for(var i=0 ; i<resp.length ; i++){
                    $scope.searchProducts[i] = resp[i];
                    $scope.searchProducts.length = i+1;
                }
                var searchproducts = JSON.stringify($scope.searchProducts);
                localStorage.setItem("searchproducts",searchproducts);
                location.href = locationUrl;
            })
            .error(function (resp) {
                alert("失败");
            })
    }

    //展示购物车数据列表
    $scope.showOrderItems = function() {
        $http.post('showCart',{user:$scope.userData})
            .success(function(resp) {

                // for(var i=0 ;i<resp.length; i++) {
                //     $scope.userOrderItems = resp;
                localStorage.setItem("cartCount",resp.length);
                $scope.cartCount = localStorage.getItem("cartCount");
                // }
                // alert("成功");
            })
            .error(function(resp) {
                // alert("操作错误");
            })
    }
    $scope.showOrderItems();
})
