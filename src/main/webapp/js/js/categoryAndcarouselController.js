/**
 * Created by melon on 18-2-28.
 */

angular.module('categoryAndcarousel',[])
.controller('categoryAndcarouselCtrl',function ($scope,$http) {

    // $scope.categorys={
    //     ids:[],
    //     names:[]
    // };
    $scope.categorys = [];

    $scope.showCategory = function () {
        $http.get("showCategorys")
            .success(function (resp) {
                for(var i=0;i<4;i++){
                    // $scope.categorys.ids[i] = resp[i].id;
                    // $scope.categorys.names[i] = resp[i].name;
                    $scope.categorys[i] = resp[i];
                    console.log($scope.categorys[i]);
                }
            }).error(function (resp) {
            alert("错误");
        })
    }
})

// .factory('some',function ($q,$http) {
//     return $http.get('');
// })
