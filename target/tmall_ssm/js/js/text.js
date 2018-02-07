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
                                if(resp){
                                    alert("添加成功");
                                    // console.log(resp);
                                }
                                else if(!resp){
                                    alert("添加失败");
                                    console.log(resp);
                                }else {
                                    alert("其他错误");
                                }
                        })
                        .error(function (resp) {
                                alert("未知错误");
                        })
                }
    })

