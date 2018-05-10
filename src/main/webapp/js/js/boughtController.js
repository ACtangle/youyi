/**
 * Created by melon on 18-4-24.
 */
angular.module('bought',[])
.controller('boughtCtrl',function ($scope,$http,$location) {
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
    var locationUrl = $location.absUrl().replace("bought","searchResult");
    //订单
    $scope.orders = [];



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


    //获取用户的所有已支付后的订单
    $scope.showOrder = function () {
        $http.post("showBought",{
            "userData":$scope.userData
        })
            .success(function (resp) {
                if(resp.length == 0) {
                    alert("您还未有订单，请下单后查看");
                    location.href = $location.absUrl().replace("bought","home");
                }
                $scope.orders = resp;
                // console.log($scope.orders);
                // alert("成功");
            })
            .error(function (resp) {
                alert("请登录");
                location.href = $location.absUrl().replace("bought","userLogin");
            })
    }
    $scope.showOrder();

    //删除指定订单
    $scope.deleteOrder = function (id) {
        var oid = id;
        $http.post("deleteOrder",{
            "oid":oid
        })
            .success(function (resp) {
                if(resp==true)
                    alert("删除成功");
                location.reload();
            })
            .error(function(resp) {
                alert("删除失败");
            })
    }
})