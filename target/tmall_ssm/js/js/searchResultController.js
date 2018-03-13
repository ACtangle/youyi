angular.module('searchResult',[])
    .controller('searchResultCtrl',function($scope,$http,$location) {
        //用户信息
        $scope.userData = {};
        //判断是否登录，默认为true显示login标签友好提示
        $scope.flag = true;
        var searchproducts = localStorage.getItem("searchproducts");
        $scope.products  = JSON.parse(searchproducts);
        console.log($scope.products);
        //跳转路径
        var locationUrl = $location.absUrl().replace("searchResult","searchResult");
        //临时对象
        $scope.searchProducts = {};
        //搜索关键字
        $scope.search = {};


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
    })
