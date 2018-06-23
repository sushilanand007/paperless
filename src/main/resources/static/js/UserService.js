'use strict'
angular.module('demo.services', []).factory('UserService', ["$http", "CONSTANTS", function($http, CONSTANTS) {
    var service = {};
    service.getUserById = function(userId) {
        var url = CONSTANTS.getUserByIdUrl + userId;
        return $http.get(url);
    }
    service.getAllUsers = function() {
        return $http.get(CONSTANTS.getAllUsers);
    }
    service.saveUser = function(userDto) {
        return $http.post(CONSTANTS.saveUser, userDto);
    }
    service.getPendingDocs = function(name) {
        return $http.get('/paperless/missing-doc?name='+name);
    }
    service.getUsername = function() {
            return $http.get('/paperless/getUsername');
    }
    service.submitFile = function(request) {
        return $http(request);
    }
    service.getAllUsers = function(name) {
        return $http.get('/paperless/users?name='+name);
    }
    service.getAllUserReport = function() {
        return $http.get('/paperless/report');
    }
    return service;
}]);
