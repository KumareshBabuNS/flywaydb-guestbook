angular.module('guestbook', [ 'ngRoute' ])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/message', {
		templateUrl : 'message.html',
		controller : 'message'
	})
	.otherwise('/');

  })
  .controller('message', function($scope, $http) {
	  var _this=this;
	  this.loadData = function() {
		  $http.get("/message/all").then(function successCallback(response){
			  console.log("Get messages: " + JSON.stringify(response));
			  $scope.messageList = response.data;
		  });
	  }
	  $scope.save=function(){
		  $scope.waiting = true;
		  var message = {
				  name: $scope.message.name,
				  message: $scope.message.message
		  	};
		  $http.post("/message", message).then(function successCallback(response){
			  console.log("Saved: " + response);
			  _this.loadData();
			  $scope.message.name = "";
			  $scope.message.message = "";
			  $scope.waiting = false;
			  $scope.error = false;
		  }, function errorCallback(response) {
			  console.log("Failed: " + response);
			  $scope.error = true;
			  $scope.waiting = false;
		  });
	  }
	  this.loadData();
  })
  .controller('home', function($scope, $http, $location) {
	  $scope.goKillApp = function() {
		  $http.get("/killApp").success(function(data){
			  console.log("Killed: " + data);
		  });
	  }
	  $scope.goMessage = function() {
		  $location.path("/message");
	  }
	  this.initCtrl = function() {
		  $http.get("/cloudinfo").success(function(data){
			  $scope.cloudinfo = data;
		  });  
	  }
	  this.initCtrl();
  });