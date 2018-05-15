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
    // var locationUrl = "";
    var locationUrl = absUrl.replace("userRegister", "userLogin");
    //信息
    $scope.message = "";

    $scope.register = function () {
        if($scope.registerForm.$invalid){
            // alert("请检查您的信息");
            setTimeout("location.reload();",1700);
        }else {
            $http.post("addUser", {data: $scope.userData})
                .success(function (resp) {

                    if (resp) {
                        setTimeout("jump()",1700);
                        alert("注册成功，点击确定返回登录");
                        // $scope.message = "注册成功";
                        // location.href = locationUrl;

                        // console.log(resp);
                        // console.log($scope.userData);
                    }
                    else if (!resp) {
                        setTimeout("location.reload();",1700);
                        alert("用户名已注册，请重新注册");
                        // console.log(resp);

                        // alert("该用户已被注册，请重新注册");
                    }
                })
                .error(function (resp) {
                    alert("其他错误");
                    setTimeout("location.reload();",1700);
                })
        }
    }

    jump = function () {
        location.href = locationUrl;
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