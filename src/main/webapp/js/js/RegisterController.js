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
        if($scope.registerForm.$invalid){
            alert("请检查您的信息");
        }else {
            $http.post("addUser", {data: $scope.userData})
                .success(function (resp) {
                    if (resp) {
                        locationUrl = absUrl.replace("userRegister", "userLogin");
                        alert("注册成功，点击确定返回登录");
                        location.href = locationUrl;
                        // console.log(resp);
                        // console.log($scope.userData);
                    }
                    else if (!resp) {
                        alert("注册失败");
                        // console.log(resp);
                    }
                })
                .error(function (resp) {
                    alert("注册失败");
                })
        }
    }
})
//两次密码确认识别
.directive('compare',function () {
    var o = {};
    o.strict = 'AE';
    o.scope = {
        orgText : '=compare'
    }
    o.require = 'ngModel';
    o.link = function(sco,ele,att,con) {
        con.$validators.compare = function (v) {
            return v == sco.orgText;
        }
        sco.$watch('orgText',function () {
            con.$validate();
        })
    }
    return o;
})