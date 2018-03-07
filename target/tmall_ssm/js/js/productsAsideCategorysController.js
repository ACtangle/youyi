/**
 * Created by melon on 18-3-7.
 */

angular.module('productsAsideCategorys',[])
    .controller('productsAsideCatrgorysCtrl',function($socpe,$http) {
        $http.get("showCategorys")
            .success(function (resp) {
                
            })
            .error(function (resp) {

            })

    })
