/**
 * Created by melon on 18-5-8.
 */

angular.module('review',[])
.controller('reviewCtrl',function ($scope,$http,$location) {
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
    var locationUrl = $location.absUrl().replace("review","searchResult");

    var oid = $location.absUrl().substr($location.absUrl().indexOf("?")+1).split("&")[0];
    $scope.oid = parseInt(oid.substr(oid.indexOf("=")+1));
    console.log($scope.oid);
    //订单
    $scope.order = {};
    //商品
    $scope.prodcut = {};
    //评价
    $scope.reviews = {};
    //提交评价内容
    $scope.contentsa = {};


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

    //展示商品
    $scope.showReviewList = function () {
        $http.post('showReview',{
            "oid":$scope.oid
        })
            .success(function (resp) {
                $scope.order = resp[0];
                $scope.product = resp[1];
                $scope.reviews = resp[2];
                // console.log($scope.order);
                // console.log($scope.product);
                // console.log($scope.reviews);
                // alert("成功");
            })
            .error(function (resp) {
                alert("失败");
            })
    }
    $scope.showReviewList();
    
    $scope.enterReview = function () {
        $http.post('doReview',{
            "oid":$scope.oid,
            "pid":$scope.product.id,
            "content":$scope.contentsa,
            "uid":$scope.userData.id
        })
            .success(function (resp) {
                console.log($scope.content);
                alert("评价成功");
                location.reload();
            })
            .error(function (resp) {
                alert("失败");

            })
    }
})