/**
 * Created by melon on 18-2-5.
 */

angular.module('login',[])
.controller('loginCtrl',function($scope,$http,$location) {

    //客户端数据源
    $scope.userData = {};
    //获取地址栏的全部路径即绝对路径
    var absUrl = $location.absUrl();
    //跳转路径变量
    var locationUrl = "";


    //参数解析
    function UrlSearch() {
        var name, value;
        var str = location.href; //取得整个地址栏
        var num = str.indexOf("?");
        str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

        var arr = str.split("&"); //各个参数放到数组里
        for (var i = 0; i < arr.length; i++) {
            num = arr[i].indexOf("=");
            if (num > 0) {
                name = arr[i].substring(0, num);
                value = arr[i].substr(num + 1);
                this[name] = value;
            }
        }
    }
    // var Request = new UrlSearch(); //实例化
    // alert("Request.orgid = "+ Request.orgid);



    //注册页面跳转
    $scope.toRegister = function() {
        locationUrl = absUrl.replace("userLogin","userRegister");
        location.href = locationUrl;
    }



    //登录功能
    $scope.login = function () {
        $http.post("loginUser", {data: $scope.userData})
            .success(function (resp){
                if(resp["0"] == true) {
                    //页面跳转
                    locationUrl = absUrl.replace("userLogin","text") + "?id=" + resp[1].id;
                    sessionStorage.setItem("name",$scope.userData.name);
                    sessionStorage.setItem("password",$scope.userData.password);
                    alert("登录成功");
                    location.href = locationUrl;
                }else{
                    alert("用户名或密码错误");
                }
            })
            .error(function (resp) {
                    alert("用户名或密码错误");
            })
    }
})