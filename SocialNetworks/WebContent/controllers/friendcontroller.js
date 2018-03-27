/**
 * 
 */
app.controller('FriendCtrl', function($scope, $location, $rootScope,
		FriendService) {
	function getAllSuggestedUsers() {
		console.log("entering friend controller")
		FriendService.getAllSuggestedUsers().then(function(response) {
			$scope.suggestedUsers = response.data // list<User>->s in (A
			// minus(B Union C)
		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')

		})
	}

	function getPendingRequests() {
		FriendService.getPendingRequests().then(function(response) {
			$scope.pendingRequests = response.data
		}, function(response) {
			$rootScope = error = response.data
			if (response.status == 401)
				$location.path('/login')
		})
	}

	$scope.addFriend = function(toId) {
		FriendService.addFriend(toId).then(function(response) {
			alert('Friend Request has been sent succesfully...')
			getAllSuggestedUsers()

		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')
		})
	}

	$scope.acceptRequest = function(request) {
		FriendService.acceptRequest(request).then(function(response) {
			alert("request from the friend controller")
			getPendingRequests()
		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')
		})
	}

	$scope.deleteRequest = function(request) {
		FriendService.deleteRequest(request).then(function(response) {
			getPendingRequests()
		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')
		})
	}
	
	
	FriendService.getAllFriends().then(function(response){
		$scope.friends=response.data
	},function(response){
		$rootScope.error=response.data
		if(response.status==401)
	    $location.path=('/login')
		
		
	})
	
	getAllSuggestedUsers()
	getPendingRequests()
})