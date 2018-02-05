/**
 * Created by melon on 18-2-4.
 */

    angular.module('app',[])
            .controller('addUserCtrl',function($scope,$http){
                $scope.data = {};
                //添加用户方法
                $scope.addUser = function () {
                    $http.post("addUser",{data:$scope.data})
                        .success(function(resp){
                                alert("添加成功");
                        })
                        .error(function (resp) {
                                alert("错误");
                        })
                }
    })

