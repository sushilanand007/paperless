'use strict'
var module = angular.module('demo.controllers', []);
module.directive('ngFiles', ['$parse', function ($parse) {

                   function fn_link(scope, element, attrs) {
                       var onChange = $parse(attrs.ngFiles);
                       element.on('change', function (event) {
                           onChange(scope, { $files: event.target.files });
                       });
                   };

                   return {
                       link: fn_link
                   }
               } ]);

module.controller("UserController", ["$scope", "$http","UserService",
    function($scope, $http, UserService) {
        $scope.userDto = {
            userId: null,
            userName: null,
            skillDtos: []
        };
        $scope.skills = [];
//        UserService.getUserById(1).then(function(value) {
//            console.log(value.data);
//        }, function(reason) {
//            console.log("error occured");
//        }, function(value) {
//            console.log("no callback");
//        });

        var formdata = new FormData();
        $scope.getTheFiles = function($files) {
            angular.forEach($files, function(value, key) {
                formdata.append("file", value);
            });
        };

        $scope.uploadFiles = function () {
        formdata.append('documentType',$scope.documentType);
        formdata.append('name',$scope.username);
        var request = {
            method: 'POST',
            url: '/paperless/upload',
            data: formdata,
            headers: {
            'Content-Type': undefined
            }
        };
        // SEND THE FILES.
        $http(request)
            .success(function (d) {
            alert(d);
            formdata = new FormData();
            })
            .error(function () {
            formdata = new FormData();
            });
        };

        $scope.getPendingDocs =function() {
            UserService.getPendingDocs($scope.username).then(function(data) {
                $scope.pendingDocs = data.data;
            });
        }

        $scope.showMissingDocs = false;
        $scope.getPendingDocsForUser =function(name) {
            UserService.getPendingDocs(name).then(function(data) {
                $scope.pendingDocs = data.data;
                $scope.showMissingDocs = true;
            });
        }
          $scope.getUsername =function() {
                    UserService.getUsername().then(function(response) {
                        $scope.username = response.data.name;
                        $scope.getPendingDocs();
                    });
          }

        $scope.getUsername();
            $scope.getPendingDocs();


        $scope.getAllUsers =function() {
            UserService.getAllUsers('').then(function(data) {
                $scope.userList = data.data;
            });
        }

        $scope.getAllUsers();

        $scope.getAllUserReport = function(){
            UserService.getAllUserReport().then(function(data){
                $scope.userReport = data.data
            });
        }

        $scope.toggleTable = false;
        $scope.toggleTableFunction = function() {
            $scope.toggleTable = !$scope.toggleTable;
        };

    }
]);
