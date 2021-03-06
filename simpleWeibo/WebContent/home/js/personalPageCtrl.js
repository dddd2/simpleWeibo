angular.module('personalPage',[])
	.controller('personalPageCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals', '$window', 
				'$compile', 
		function($scope, $state, $stateParams, $http, locals, $window, $compile) {
		/**
		 * 此页面localUser = user;
		 */
		
		//定义newMessag
		$scope.newMessage = "";
		
		$scope.testddd = ""
		
		//定义pictures
		$scope.pictures = {};
		$scope.keywords = [];
		
		//创建reader接口
		$scope.reader = new FileReader();
		
		//通过$stateParams获取url参数 id   
		$scope.userId = $stateParams.id;
		
		//定义元素
		$scope.user = {};
		$scope.fans = [];
		$scope.focusPeoples = [];
		$scope.messages = [];
		$scope.pageSize = 20;
		$scope.currentPage = 1;
		$scope.puserName;
		$scope.aboutUsers = [];
		$scope.aboutMeMessages = [];
		
		//默认进入首页
		$scope.mainHome = true;
		
		$scope.changeToMainHome = function() {
			$scope.mainHome = true;
		}
		
		$scope.changeToAboutMe = function() {
			$scope.mainHome = false;
			$scope.findAboutMeMessagesByUserId();
			$scope.cleanAboutMe();
		}
		
		$scope.goUserList = function(type) {
			$state.go("userList",{type:type,id:$scope.localUser.userId});
		}
		
		$scope.goUserHome = function(id) {
			$state.go('mainHome',{id:id});
		}
		
		$scope.logout = function() {
			locals.clear("localUser");
			$state.go("login");
		}
		
		$scope.go = function(key) {
			$state.go(key, {id:$scope.localUser.userId});
		}
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
		$scope.reloadRoute = function() {
		    $window.location.reload();
		};
		
		//显示模态框
		$scope.show=function(){
	        $("#crewmodal").modal('show');
	    };
	    
	    //隐藏模态框
	    $scope.hide=function() {
	    	$("#crewmodal").modal('hide');
	    }
	    
	    //图片预览
	    $scope.showPicture = function(files) {
	    	$scope.guid = (new Date()).valueOf(); 
	    	$scope.reader.readAsDataURL(files[0]); // FileReader的方法，把图片转成base64
	    	$scope.reader.onload = function(ev) {
	    		$scope.$apply(function(){
	                $scope.pictures[$scope.guid] = {
	                    imgSrc : ev.target.result,  // 接收base64
	                }
	                $scope.keywords.push($scope.guid);
	            });
	    	}
		}
	    
	    //显示表情table
	    $scope.showBiaoqing = function() {
	    	$("#p_biaoqing").css("visibility","visible");
	    }
	    
	    //添加表情
	    $scope.addBiaoqing = function($event) {
	    	var compileFn = $compile("<img src='./images/biaoqing/1.gif'/>");
	    	
	    	var $dom = angular.copy($event.target);
	    	
	    	$("#p_textfield2").append($dom);
	    	
	    	$("#p_biaoqing").css("visibility","hidden");
	    }
	    
	    //删除图片
	    $scope.rmImg = function(key) {
	    	var guidArr = [];
	        for(var p in $scope.pictures){
	            guidArr.push(p);
	        }
	        $scope.keywords.splice(key,1);
	        delete $scope.pictures[guidArr[key]];
	    }
/*==============================查看过@我的==============================*/
	    $scope.cleanAboutMe = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{method:"cleanAboutMe", userId:$scope.userId})
			.success(function(data) {
				$scope.user.aboutMe = 0;
			})
			.error(function(error){
				alert(error);
			});
	    }
/*================================点赞======================================*/	    
		$scope.loveMessage = function(message) {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,{method:"loveMessage", userId:$scope.userId, 
							message:JSON.stringify(message)})
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

/*===================================评论==============================*/
		$scope.commentTo = function(message, name) {
			message.comment = '回复 ' + name + ':';
		}
		
		$scope.dealComment = function(comment) {
			var index = comment.indexOf(":");
			$scope.puserName = comment.substring(1,index);
		}
		
		$scope.submitComment = function (message) {
			if(message.comment.charAt(0) == '@') {
				$scope.dealComment(message.comment);
			}
			var url = "http://localhost:8080/simpleWeibo/servlet/CommentServlet"
			$http.post(url,{method:"submitComment", userId:$scope.userId,
				comment:message.comment,messageId:message.messageId,
				puserName:$scope.puserName})
			.success(function(data) {
				if(data == "true") {
					$scope.reloadRoute();
				} else {
					alert("error");
				}
			})
			.error(function(error){
				alert(error);
			});
		}
		
/*==========================================发表微博===================================*/		
		$scope.createMessage = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,{newMessage:$scope.newMessage,method:"createMessage", 
				userId:$scope.userId,imgs:JSON.stringify($scope.pictures),
				keywords:$scope.keywords})
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
		
/*=====================================================@好友===================================*/		
		$scope.selected = [];
	    $scope.selectedTags = [];
		 
        var updateSelected = function(action,id,name){
            if(action == 'add' && $scope.selected.indexOf(id) == -1){
        	    $scope.selected.push(id);
                $scope.selectedTags.push(name);
            }
            if(action == 'remove' && $scope.selected.indexOf(id)!= -1){
                var idx = $scope.selected.indexOf(id);
                $scope.selected.splice(idx,1);
                $scope.selectedTags.splice(idx,1);
            }
        }
 
        $scope.updateSelection = function($event, id){
            var checkbox = $event.target;
            var action = (checkbox.checked?'add':'remove');
            updateSelected(action,id,checkbox.name);
        }
        
	    $scope.isSelected = function(id){
	        return $scope.selected.indexOf(id)>=0;
	    }
		
 		$scope.addAboutFriend = function () {
 			if($scope.selectedTags.length > 0) {
 				$scope.newMessage += " ";
 	 			angular.forEach($scope.selectedTags, function(tag){
 	 				$scope.newMessage += "@" + tag + " ";
 	 			})
 			}
 			$scope.hide();
 		}
/*========================================初始化查询======================================*/	
  
		$scope.findFocusMessagesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,
			{	
//				currentPage:1,
//				pageSize:1,
				method:"findFocusMessagesByUserId", 
				userId:$scope.userId 
			})
			.success(function(data) {
				$scope.messages = data;
				$scope.dealMessagesInit($scope.messages);
				$scope.dealMessagesPic($scope.messages);
				$scope.dealMessagesBiaoqing($scope.messages);
				$scope.parseTouxiangForMessageList($scope.messages);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findAboutMeMessagesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,{	
//				currentPage:1,
//				pageSize:1,
				userName:$scope.user.name,
				method:"findAboutMeMessagesByUserId", 
				userId:$scope.userId 
			})
			.success(function(data) {
				$scope.aboutMeMessages = data;
				$scope.dealMessagesInit($scope.aboutMeMessages);
				$scope.dealMessagesPic($scope.aboutMeMessages);
				$scope.dealMessagesBiaoqing($scope.aboutMeMessages);
				$scope.parseTouxiangForMessageList($scope.aboutMeMessages);
			})
			.error(function(error){
				alert(error);
			});
		}
/*====================================处理@好友标签================================*/		
		$scope.dealMessagesInit = function(messages) {
			angular.forEach(messages, function(message){
				if(message.text) {
					var arrs = message.text.split(" ");
					message.text = "";
					angular.forEach(arrs, function(arr){
						if(arr.charAt(0) == "@") {
							arr = '<a>' + arr +'</a>';
						}
						message.text += arr;
					})
				}
			})
		}
		
/*==================================处理图片======================================*/		
		$scope.dealMessagesPic = function(messages) {
			angular.forEach(messages, function(message){
				if(message.imgs) {
					var arrs = message.imgs.split("$$$");
					message.imgs=[];
					angular.forEach(arrs, function(arr){
						arr = "data:image/jpg;base64," + arr;
						message.imgs.push(arr);
					})
				}
			})
		} 
/*==================================头像=======================================*/
		$scope.parseTouxiangForList = function(users) {
			angular.forEach(users, function(user) {
				if(user.touxiang) {
					user.touxiang = "data:image/jpg;base64," + user.touxiang;
				}
			})
		} 
		
		$scope.parseTouxiang = function(user) {
			if(user.touxiang) {
				user.touxiang = "data:image/jpg;base64," + user.touxiang;
			}
		} 
		
		$scope.parseTouxiangForMessageList = function(messages) {
			angular.forEach(messages, function(message) {
				if(message.user.touxiang) {
					message.user.touxiang = "data:image/jpg;base64," + message.user.touxiang;
				}
				if(message.comments.length > 0) {
					angular.forEach(message.comments, function(comment){
						if(comment.user.touxiang) {
							comment.user.touxiang = "data:image/jpg;base64," + comment.user.touxiang;
						}
					})
				}
			})
		}
/*=================================处理表情======================================*/
		$scope.dealMessagesBiaoqing = function(messages) {
			angular.forEach(messages, function(message) {
				if(message.text) {
					message.text = message.text.replace(/imgng-/g, "img ng-");
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