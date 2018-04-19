
angular.module('buy',[])
.controller('buyCtrl',function ($scope,$location,$http) {
    // $scope.test = 346;
    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //获取当前路径的绝对路径
    var absUrl = $location.absUrl();
    //订单项参数
    var oiid = parseInt(absUrl.substr(absUrl.indexOf("=")+1));
    // console.log(oiid);
    //oiid对象
    $scope.orderItem = {};
    $scope.orderItem.id = oiid;
    $scope.orderItemProduct = [];
    $scope.total = {};
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
    }

    //退出登录
    $scope.loggout = function () {
        sessionStorage.clear();
    }

    //获取订单项id并展示所购买的产品信息
    $scope.showOrderItem = function () {
        $http.post("onebuy",{
            "data":$scope.orderItem,
            "userData":$scope.userData
        })
            .success(function (resp) {

                    $scope.orderItemProduct = resp[0];
                    $scope.total = resp[1];
                    console.log($scope.orderItemProduct);

                console.log($scope.total);
                // alert("成功");
            })
            .error(function (resp) {
                // console.log($scope.orderItem);
                alert("失败");
            })
    }
    $scope.showOrderItem();

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
})
