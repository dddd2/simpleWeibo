angular.module('admin',[])
	.controller('adminCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.password = "";
		$scope.phone = "";
		$scope.user = {};
		
		$scope.admin = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
								phone:$scope.phone,
								method:"login", 
								password:$scope.password, 
						   },{
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },    
	    	transformRequest: function(obj) {    
	    		var str = [];    
	    		for (var p in obj) {    
	    			str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));    
	    		}    
	    		return str.join("&");    
	    	}
		  })
			.success(function(data) {
				$scope.user = eval(data);

				locals.setObject("localUser", $scope.user);
				$state.go('personalPage',{id:$scope.user.userId});
			})
			.error(function(error){
				alert(error);
			});
		}
	}])