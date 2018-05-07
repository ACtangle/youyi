
angular.module('buy',[])
.controller('buyCtrl',function ($scope,$location,$http) {
    // $scope.test = 346;
    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //获取当前路径的绝对路径
    var absUrl = $location.absUrl();
    //父路径
    var url = absUrl.split(absUrl.substr(absUrl.indexOf("?")))[0];
    // console.log(url);
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
    //用户地理位置信息(地址,邮政编码,收件人名字,手机号码)
    $scope.userInfo = {
        address:"",
        postCode:"",
        receiver:"",
        mobile:""
    };

    // 广州市花都区新华街道学府路一号华南理工大学广州学院
    // 510800
    // 李毓翰
    // 18826237620
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
                    // console.log($scope.orderItemProduct);

                // console.log($scope.total);
                // alert("成功");
            })
            .error(function (resp) {
                // console.log($scope.orderItem);
                alert("失败");
            })
    }
    $scope.showOrderItem();



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



    //获取从购物车点击过来的oiid所有值并放入params数组内
    getUrlParams = function() {
        var locationUrl = $location.absUrl();
        var paramsUrl = locationUrl.substring(locationUrl.indexOf('?')+1);
        $scope.params =paramsUrl.split("&oiid=");
        for(var i = 1;i<$scope.params.length;i++) {
            $scope.params[i] = parseInt($scope.params[i]);
        }
        $scope.params[0]=oiid;
        console.log($scope.params);
        $http.post('onebuy',{"oiids":$scope.params})
            .success(function (resp) {
                $scope.orderItemProduct = resp[0];
                $scope.total = resp[1];
                // alert("成功");
            })
            .error(function () {
                alert("失败");
            })
    }
    getUrlParams();


    //提交订单
    $scope.addOrder = function () {
        if($scope.registerForm.$invalid){
            alert("请检查您的信息");
        }else{
        $http.post('createOrder',{
            "user":$scope.userData,
            "oiids":$scope.params,
            "userInfo":$scope.userInfo
        })
            .success(function (resp) {
                // console.log($scope.userInfo);
                var oid=resp[0].id;
                var total = resp[1];
                location.href= url.replace("buy","alipay")+ "?oid=" + oid + "&total=" + total;
                // alert("成功");
            })
            .error(function (resp) {
                alert("失败");
            })
        }
    }
})
