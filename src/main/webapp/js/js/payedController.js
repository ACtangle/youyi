/**
 * Created by melon on 18-4-21.
 */

angular.module('payed',[])
.controller('payedCtrl',function ($scope,$location,$http) {
    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //购物车数量
    $scope.cartCount = localStorage.getItem("cartCount");
    //用户订单项数组
    $scope.userOrderItems = [];
    //临时对象
    $scope.searchProducts = {};
    //搜索关键字
    $scope.search = {};
    //跳转路径
    var locationUrl = $location.absUrl().replace("payed","searchResult");



    var oid = $location.absUrl().substr($location.absUrl().indexOf("?")+1).split("&")[0];
    var total = $location.absUrl().substr($location.absUrl().indexOf("?")+1).split("&")[1];
    $scope.total = parseFloat(total.substr(total.indexOf("=")+1));
    $scope.oid = parseInt(oid.substr(oid.indexOf("=")+1));
    //客户端订单信息
    $scope.orderInfo = {};



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


    //获取用户购物车件数
    $scope.showOrderItems = function() {
        $http.post('showCart',{user:$scope.userData})
            .success(function(resp) {
                // $scope.cartCount = localStorage.getItem("cartCount");
                for(var i=0 ;i<resp.length; i++) {
                    $scope.userOrderItems = resp;
                    localStorage.getItem("cartCount",resp.length);
                }
                // $scope.cartCount = localStorage.getItem("cartCount");
                // alert("成功");
            })
            .error(function(resp) {
                // alert("操作错误");
            })
    }
    $scope.showOrderItems();

    //获取订单信息
    $scope.orderInfo = function () {
        $http.post("showOrder",{
            "oid":$scope.oid,
            "userData":$scope.userData
        })
            .success(function (resp) {
                $scope.orderInfo = resp;
                console.log($scope.orderInfo);
                alert("成功");
            })
            .error(function (resp) {
                alert("失败");
            })
    }
    $scope.orderInfo();
})