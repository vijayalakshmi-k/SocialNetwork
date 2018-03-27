/**
 * 
 */
app.controller('NotificationCtrl', function($scope, $rootScope, $location,$routeParams,NotificationService) {
	var id=$routeParams.id
	function getNotificationsNotViewed() {
		NotificationService.getNotificationsNotViewed().then(function(response) {
			$rootScope.notifications = response.data
			$rootScope.notificationCount = $rootScope.notifications.length
		}, function(response) {
			$rootScope.error = response.data // ErrorClazzObject
			if (response.status == 401)
				$location.path('/login')
		})
	}

	// two statements
	// select * from notification where id=?
	// update notification set viewed=1 where id=?

	if (id != undefined) {

		NotificationService.getNotification(id).then(function(response) {
			console.log(response.data)
			$scope.notification = response.data
		}, function(response) {
			
			$rootScope.error = response.data // ErrorClazz object
			if (response.status == 401) // not logged in
				$location.path('/login')
		})

		NotificationService.updateNotification(id).then(function(response) {
			getNotificationsNotViewed()
		}, function(response) {

			$rootScope.error = response.data // ErrorClazz object
			if (response.status == 401) // not logged in
				$location.path('/login')

		})

	}
	getNotificationsNotViewed()

})