var myApp = angular.module("myApp", ["ngRoute"]);
myApp.config(function ($routeProvider, $locationProvider,$httpProvider) {
    $httpProvider.interceptors.push('responseObserver');
    $locationProvider.hashPrefix("");
    $routeProvider
        .when("/",{
            templateUrl:"/page/trangchu.html",
            controller :"homeCtrl"
        })
        .when("/account", {
            templateUrl: "page/my-account.html",
            controller: "ThongTinCaNhanController"
        })

        .when("/signup", {
            templateUrl: "page/signup.html",
            controller: "registerCtrl"
        })
        .when("/login", {
            templateUrl: "page/signin.html",
            controller:"loginCtrl"
        })


        .when("/my-info", {
            templateUrl: "/page/my-info.html",
            controller: "ThongTinCaNhanController"
        })
        .when("/my-address", {
            templateUrl: "/page/address.html",
            controller: "AddressCtrl"
        })

        .when("/changePass", {
            templateUrl: "page/changePass.html",
            controller: "ChangePassCtrl"
        })

        .when("/changePassword/:token", {
            templateUrl: "page/forgotpassUpdate.html",
            controller : "forgotUpdateCtrl"
        })
        .otherwise({
            redirectTo: "/",
        });
});



myApp.factory('responseObserver', function responseObserver($q, $window) {
    return {
        'responseError': function(errorResponse) {
            switch (errorResponse.status) {
                case 403:
                    $window.localStorage.removeItem('currentUser');
                    // $http.defaults.headers.common.Authorization = "";
                    $window.location.reload();
                    break;
                // case 500:
                //   $window.location.href = '/login';
                //   break;
            }
            return $q.reject(errorResponse);
        }
    };
});


myApp.controller("indexCtrl", function ($rootScope,$scope, $http,$window, $location){
    let currentUser = localStorage.getItem("currentUser");
    if(currentUser){
        $scope.currentUser = currentUser ? JSON.parse(currentUser) : {};
        $rootScope.currentUser =$scope.currentUser;
        $window.localStorage.setItem('currentUser', JSON.stringify($scope.currentUser));
        $http.defaults.headers.common.Authorization = "Bearer " + $scope.currentUser.token;
    }
    else{
        $rootScope.currentUser ="";
        $http.defaults.headers.common.Authorization = "";
    }

    $rootScope.logouts = function (){
        $window.localStorage.removeItem('currentUser');
        $http.defaults.headers.common.Authorization = "";
        Swal.fire({
            icon: "warning",
            title: "Đã đăng xuất!",
            text: "Bạn hãy đăng nhập để tiếp tục mua hàng nhé!",
            showConfirmButton: true,
            closeOnClickOutside: false,
            allowOutsideClick: false,
            timer: 1600,
        });
        setTimeout(function (){
            $location.path("/login")
            $window.location.reload();
        },2000)

    }
})