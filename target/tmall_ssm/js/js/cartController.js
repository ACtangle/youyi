/**
 * Created by melon on 18-4-15.
 */
angular.module('cart',[])
.controller('cartCtrl',function ($scope,$location,$http) {

    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //临时对象
    $scope.searchProducts = {};
    //搜索关键字
    $scope.search = {};
    //跳转路径
    var locationUrl = $location.absUrl().replace("cart","searchResult");
    //用户订单项数组
    $scope.userOrderItems = [];
    //购物车数量
    $scope.cartCount = localStorage.getItem("cartCount");

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
                if(resp.length == 0) {
                    alert("您还未加入商品进购物车，请先加入购物车");
                    location.href = $location.absUrl().replace("cart","home");
                }
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
                alert("请登录");
                location.href = $location.absUrl().replace("cart","userLogin");
            })
    }
    $scope.showOrderItems();

    //删除购物车订单项

    $scope.deleteOrderItem = function (id) {
        var oiid = id;
        $http.post("deleteOrderItem",{
            "oiid":oiid
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