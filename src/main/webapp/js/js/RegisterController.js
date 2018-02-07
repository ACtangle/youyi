/**
 * Created by melon on 18-2-7.
 */
angular.module('register',[])
.controller('registerCtrl', function ($scope,$location,$http) {
    //客户端数据源
    $scope.userData = {};
    //获取地址栏的绝对路径
    var absUrl = $location.absUrl();
    //页面跳转路径
    var locationUrl = "";

    $scope.register = function () {
        $http.post("addUser",{data:$scope.userData})
            .success(function(resp){
                if(resp){
                    locationUrl = absUrl.replace("userRegister","userLogin");
                    alert("添加成功");
                    location.href = locationUrl;
                    // console.log(resp);
                }
                else if(!resp){
                    alert("添加失败");
                    console.log(resp);
                }
            })
            .error(function (resp) {
                alert("未知错误");
            })
    }
})