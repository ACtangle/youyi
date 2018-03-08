/**
 * Created by melon on 18-3-8.
 */

angular.module('homepageCategoryProducts',[])
.controller('homepageCategoryProductsCtrl',function($scope,$http){

    $scope.categoryss = [];
    //分类产品栏展示数目
    $scope.productcount=5;

    $scope.showProducts=function () {
        $http.get("showCategorys")
            .success(function (resp) {
                for(var i=0 ;i<resp.length;i++){
                    $scope.categoryss[i] = resp[i];
                    // console.log($scope.categoryss[i]);
                }
                alert("成功");
            })
            .error(function (resp) {
                alert("失败");
            })
    }
})