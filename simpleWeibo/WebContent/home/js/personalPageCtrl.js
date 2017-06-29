angular.module('personalPage',[])
	.controller('personalPageCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals', '$window',
		function($scope, $state, $stateParams, $http, locals, $window) {
		/**
		 * 此页面localUser = user;
		 */
		
		//定义newMessag
		$scope.newMessage = "";
		
		//通过$stateParams获取url参数 id   
		$scope.userId = $stateParams.id;
		
		//定义元素
		$scope.user = {};
		$scope.fans = [];
		$scope.focusPeoples = [];
		$scope.messages = [];
		$scope.pageSize = 20;
		$scope.currentPage = 1;
		
		//初始化方法
		$scope.init = function () {
			$scope.findUserById();
			//从缓存中读取localUser
			$scope.localUser = locals.getObject("localUser");
			$scope.findFansByUserId();
			$scope.findFocusPeoplesByUserId();
			$scope.findFocusMessagesByUserId();
		}
		
		//刷新页面
		$scope.reloadRoute = function () {
		    $window.location.reload();
		};
		
		$scope.loveMessage = function(message) {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
				$http.post(url,{	
									method:"loveMessage", 
									userId:$scope.userId ,
									message:JSON.stringify(message),
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
					if(data > 0) {
						if(!message.hasOwnProperty("loveNum")) {
							message["loveNum"] = "";
						}
						message.loveNum++;
					}
				})
				.error(function(error){
					alert(error);
				});
		}
		
		$scope.submitComment = function (message) {
			var url = "http://localhost:8080/simpleWeibo/servlet/CommentServlet"
				$http.post(url,{	
									method:"submitComment", 
									userId:$scope.userId,
									comment:message.comment,
									messageId:message.messageId,
									puserId:message.user.userId
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
					alert(data);
				})
				.error(function(error){
					alert(error);
				});
		}
		
		$scope.findFocusMessagesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
				$http.post(url,{	
									currentPage:$scope.currentPage,
									pageSize:$scope.pageSize,
									method:"findFocusMessagesByUserId", 
									userId:$scope.userId 
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
					$scope.messages = data;
				})
				.error(function(error){
					alert(error);
				});
		}
		
		$scope.findFansByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
				$http.post(url,{
									currentPage:1,
									pageSize:3,
									method:"findFansByUserId", 
									userId:$scope.userId, 
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
					$scope.fans = data;
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
					$scope.focusPeoples = data;
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
					$scope.user = data;
				})
				.error(function(error){
					alert(error);
				});
		}
		
		$scope.createMessage = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
				$http.post(url,{
									newMessage:$scope.newMessage,
									method:"createMessage", 
									userId:$scope.userId, 
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
					if(data == "true") {
						$scope.newMessage = "";
						$scope.reloadRoute();
					} else {
						alert("发表失败");
					}
				})
				.error(function(error){
					alert(error);
				});
		}
		
		$scope.init();
	}])