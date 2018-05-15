/**
 * Created by melon on 18-3-7.
 */

angular.module('productsAsideCategorys',[])
    .controller('productsAsideCatrgorysCtrl',function($scope,$http) {

        //商品分类数组数据源
        $scope.categoryss = [];

        $scope.show=function () {
            $http.get("showCategorys")
                .success(function (resp) {
                    // console.log(resp.length);
                    for(var i=0;i<resp.length;i++){
                        $scope.categoryss[i] = resp[i];
                        // console.log("-------------------------------------------");

                    }
                    // console.log($scope.categoryss);
                    // alert("成功");
                })
                .error(function (resp) {
                    alert("失败");
                })
        }
    })
