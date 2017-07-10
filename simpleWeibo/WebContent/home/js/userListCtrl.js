angular.module('userList',[])
	.controller('userListCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.userId = $stateParams.id;
		
		$scope.type = $stateParams.type;
		
		$scope.user = {};
		$scope.fans = [];
		$scope.focusPeoples = [];

		$scope.localUser = locals.getObject('localUser');
		
		$scope.go = function(key) {
			$state.go(key, {id:$scope.localUser.userId});
		}
		
		$scope.jump = function(key, id) {
			$state.go(key,{id:id});
		}
		
		$scope.logout = function() {
			locals.clear("localUser");
			$state.go("login");
		}
		
		$scope.init = function() {
			$scope.findFansByUserId();
			$scope.findFocusPeoplesByUserId();
			$scope.findUserById();
		}
		
		$scope.parseTouxiang = function(user) {
			if(user.touxiang) {
				user.touxiang = "data:image/jpg;base64," + user.touxiang;
			}
		} 
		
		$scope.parseTouxiangForList = function(users) {
			angular.forEach(users, function(user) {
				if(user.touxiang) {
					user.touxiang = "data:image/jpg;base64," + user.touxiang;
				}
			})
		} 
		
		$scope.findFansByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				currentPage:1,
				pageSize:3,
				method:"findFansByUserId", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.fans = data;
				$scope.parseTouxiangForList($scope.fans);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findFocusPeoplesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{	
				currentPage:1,
				pageSize:3,
				method:"findFocusPeoplesByUserId", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.focusPeoples = data;
				$scope.parseTouxiangForList($scope.focusPeoples);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findUserById = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"findUserById", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.user = data;
				$scope.parseTouxiang($scope.user);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.init();
		
	}])