/**
 * Created by melon on 18-3-1.
 */
angular.module('categoryMenu',[])
.controller('categoryMenuCtrl',function($scope,$http) {
    //产品分类源
    // $scope.categorys = {
    //     ids:[],
    //     names:[],
    // };
    $scope.categoryss = [];

    //获取后台category数据源
    $scope.showCategorys = function () {

        $http.get("showCategorys")
            .success(function (resp) {
                for(var i=0;i<17;i++){
                    // $scope.categorys.ids[i] = resp[i].id;
                    // $scope.categorys.names[i] = resp[i].name;
                    $scope.categoryss[i] = resp[i];
                    console.log($scope.categoryss[i]);
                }
            })
            .error(function (resp) {
                alert("失败");
            })
    }
})