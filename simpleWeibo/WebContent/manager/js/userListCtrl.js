angular.module('userList',[])
	.controller('userListCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals', 
				'$window',
		function($scope, $state, $stateParams, $http, locals, $window) {
		
		$scope.users = [];
		
		$scope.currentPage = 1;
		$scope.pageSize = 8;
		
		//刷新页面
		$scope.reloadRoute = function() {
		    $window.location.reload();
		};
		
		$scope.display = function(id) {
			$state.go('mainHome.list.userForm',{operator:'display',id:id})
		}
		
		$scope.nextPage = function() {
			if(!$scope.hasMore) {
				return;
			}
			$scope.currentPage++;
			$scope.findAllUsers();
		}
		
		$scope.prePage = function() {
			if($scope.currentPage == 1) {
				return;
			}
			$scope.currentPage--;
			$scope.findAllUsers();
		}
		
		$scope.deleteUser = function(id) {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"deleteUser", 
				userId:id
			})
			.success(function(data) {
				$scope.reloadRoute();
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findAllUsers = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				currentPage:$scope.currentPage,
				pageSize:$scope.pageSize,
				method:"findAllUsers", 
			})
			.success(function(data) {
				$scope.users = data.data;
				$scope.total = data.total;
				if($scope.currentPage * $scope.pageSize < data.total) {
					$scope.hasMore = true;
				} else {
					$scope.hasMore = false;
				}
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findAllUsers();
	}])