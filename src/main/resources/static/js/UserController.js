'use strict'
var module = angular.module('demo.controllers', []);
module.controller("UserController", ["$scope", "UserService",
    function($scope, UserService) {
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

        $scope.getPendingDocs =function() {
            UserService.getPendingDocs('shripati.bhat').then(function(data) {
                $scope.pendingDocs = data.data;
            });
        }
        $scope.getPendingDocs();
    }
]);
