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
        $scope.isLoaderShown = false;
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
                $scope.readFileUpload=value.name;
                document.getElementById("readFileUpload").value=value.name;
            });
        };

        $scope.uploadFiles = function () {

        $scope.submitted = true;
        if($scope.documentType === undefined || $scope.documentType == null) {
            $scope.documentTypeError = true;
        } else {
            $scope.documentTypeError = false;
        }
        if(formdata.get("file") == null || formdata.get("file") === undefined) {
            $scope.fileError = true;
        } else {
            $scope.fileError = false;
        }

        if($scope.fileError == true || $scope.documentTypeError == true) {
            return;
        }

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
        $scope.isLoaderShown = true;
//        $scope.showLoader('submitDiv');
        $http(request).success(function (d) {
                formdata = new FormData();
                $scope.hideLoader('submitDiv');
                $scope.successfullyUploaded = true;
                document.getElementById("readFileUpload").value="";
                document.getElementById('buttonOpenModal').click();
                $scope.isLoaderShown = false;
            })
            .error(function () {
                formdata = new FormData();
                $scope.hideLoader('submitDiv');
            });

//        $scope.hideLoader('submitDiv');
        };

        $scope.getPendingDocs =function() {
            UserService.getPendingDocs($scope.username).then(function(response) {
                $scope.pendingDocs = response.data;
            });
        }

        $scope.showMissingDocs = false;
        $scope.showAllUsersData = false;
        $scope.successfullyUploaded = false;
        $scope.getPendingDocsForUser =function(name) {
            $scope.hiddenUsername = name;
            UserService.getPendingDocs(name).then(function(response) {
                $scope.pendingDocs = response.data;
                $scope.showMissingDocs = true;
                $scope.showAllUsersData = false;
            });
        }
          $scope.getUsername =function() {
                    UserService.getUsername().then(function(response) {
                        $scope.username = response.data.name;
                        $scope.isAdminUser = (response.data.userType == 'ADMIN');
                        $scope.getPendingDocs();
                    });
          }

        $scope.getAllUsers =function() {
            UserService.getAllUsers('').then(function(data) {
                $scope.userList = data.data;
            });
        }

        $scope.getUsername();
        $scope.getAllUsers();

        $scope.getAllUserReport = function() {
            UserService.getAllUserReport().then(function(data){
                $scope.userReport = data.data;
                $scope.showMissingDocs = false;
                $scope.showAllUsersData = true;
            });
        };

        $scope.toggleTable = false;
        $scope.toggleTableFunction = function() {
            $scope.toggleTable = !$scope.toggleTable;
        };

        $scope.chngFunc = function(item) {
            $scope.documentType=item;
        };

        $scope.showLoader = function(divId) {
            $scope.loading = true;
            var myEl = angular.element( document.querySelector( divId) );
            myEl.removeClass('loader-hidden');
            myEl.addClass('loader-overlay');
        };

        $scope.hideLoader = function(divId) {
            $scope.loading = false;
            var myEl = angular.element( document.querySelector( divId) );
            myEl.removeClass('loader-overlay');
            myEl.addClass('loader-hidden');
        };

        $scope.isLastElement = function(check) {
            var isLastElem = check ? true : false;
            return isLastElem;
        };

        $scope.openModal = function(){
            $scope.modalInstance = $uibModal.open({
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: 'modalWindow.html',
                controller :'ModelHandlerController',
                controllerAs: '$ctrl',
                size: 'lg',
                resolve: {
                }
            });

        }

        $scope.reloadWindow = function() {
            window.location.reload();
        }

    }
]);
