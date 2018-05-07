angular.module('category',[])
.controller('categoryCtrl',function($scope,$http,$location) {

    //用户信息
    $scope.userData = {};
    //判断是否登录，默认为true显示login标签友好提示
    $scope.flag = true;
    //获取地址栏的全部路径即绝对路径
    var absUrl = $location.absUrl();
    //跳转路径单个参数变量
    var cid;
    //跳转路径单个参数变量
    var sort;
    //url解析获取单个参数值
    cid = parseInt(absUrl.substr(absUrl.indexOf("=")+1));
    //url解析获取单个参数值
    // sort = absUrl.substr(absUrl.indexOf("&sort=")+6);
    //购物车数量
    $scope.cartCount = localStorage.getItem("cartCount");
    //将参数转为json数据
    // angular.toJson(pid);
    //产品对象
    $scope.category = {};
    $scope.category.id = cid;
    $scope.productSort = {};
    // $scope.productSort.sort = sort;
    // console.log(sort);
    //临时对象
    $scope.searchProducts = {};
    //搜索关键字
    $scope.search = {};
    //跳转路径
    var locationUrl = $location.absUrl().replace("category","searchResult");

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

    //展示单个分类页
    $scope.showCategoryProducts = function () {
        $http.post('showCategoryProducts',
            {
            "data": $scope.category,
            "sort":$scope.productSort
            })
            .success(function (resp) {
               $scope.category = resp;
                // alert("成功");
            })
            .error(function (resp) {
                // alert("失败");
            })
    }


    //根据条件降序查询
    $scope.showProductsSort = function (type) {
        $scope.productSort.sort = type;
        $http.post('showProductsSort',
            {
            "data":$scope.category,
            "sort":$scope.productSort
            })
            .success(function (resp) {
            $scope.category = resp;
            // alert("成功")
        }).error(function (resp) {
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
})