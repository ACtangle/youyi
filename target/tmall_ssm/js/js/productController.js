
angular.module('product',[])

.controller('productCtrl',function($scope,$http,$location) {
    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //获取地址栏的全部路径即绝对路径
    var absUrl = $location.absUrl();
    //跳转路径单个参数变量
    var pid;
    //url解析获取单个参数值
    pid = parseInt(absUrl.substr(absUrl.indexOf("=")+1));
    //产品对象
    $scope.product = {};
    $scope.product.id = pid;
    //产品返回对象
    var searchproducts = localStorage.getItem("searchproducts");
    $scope.products  = JSON.parse(searchproducts);
    console.log($scope.products);
    //跳转路径
    var locationUrl = $location.absUrl().replace("product.html?pid="+$scope.product.id,"searchResult.html");
    //临时对象
    $scope.searchProducts = {};
    //搜索关键字
    $scope.search = {};
    //订单项对象
    $scope.orderItem={};
    //临时对象
    $scope.tempdatas=[];

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


    //展示单个产品页
    $scope.showProduct = function () {
        $http.post("showProduct", {data: $scope.product})
            .success(function (resp) {
                for(var i=0;i<resp.length;i++){
                    $scope.product[i] =resp[i];
                }
                // alert("成功");
            })
            .error(function (resp) {
                // console.log($scope.product);
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

    //立即购买(单品)
    $scope.jump=function(obj){
        if(sessionStorage.getItem("name") == null && sessionStorage.getItem("password") == null) {
            alert("请先登录后购买商品");
            window.location.href="userLogin.html";
        }else {
            $scope.tempdatas = angular.copy(obj);
            $scope.num = document.getElementById("count").value;
            $http.post("buyone",{
                "user":$scope.userData,
                "pid":$scope.product.id,
                "num":$scope.num
             }
            ).success(function (resp) {
                window.location.href = "buy.html?oiid=" + resp ;
            }).error(function (resp) {
                alert("失败");
            })

        }
    }
})