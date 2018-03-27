/**
 * Angular js module and config SPA
 */

var app = angular.module('app', [ 'ngRoute', 'ngCookies' ])
app.config(function($routeProvider) {
	$routeProvider.when('/register', {
		templateUrl : 'views/registrationform.html',// all variable functions
		// added from frontend
		controller : 'UserController'// $scope
	})

	.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'UserController' // $scope
	})

	.when('/edituserprofile', {
		templateUrl : 'views/edituserprofile.html',
		controller : 'UserController'
	})

	.when('/addjob', {
		templateUrl : 'views/jobform.html',
		controller : 'JobCtrl'
	})

	.when('/alljobs', {
		templateUrl : 'views/joblist.html',
		controller : 'JobCtrl'// $scope

	})

	.when('/getjob/:id', {
		templateUrl : 'views/jobdetail.html',
		controller : 'JobCtrl'

	}).when('/addblog', {
		templateUrl : 'views/blogform.html',
		controller : 'BlogCtrl'

	})

	.when('/blogsnotapproved', {
		templateUrl : 'views/blogsnotapproved.html',
		controller : 'BlogCtrl' // list of blogs
	})

	.when('/blogsapproved', {
		templateUrl : 'views/blogsapproved.html',
		controller : 'BlogCtrl'
	})

	.when('/getblog/:id', {
		templateUrl : 'views/blogdetails.html',
		controller : 'BlogDetailsCtrl' // single blogpost object ,queries
										// getblog(),updateblog ,add comment
	})

	.when('/getblognotapproved/:id', {
		templateUrl : 'views/blogapprovalform.html',
		controller : 'BlogDetailsCtrl' 
	})

	.when('/getnotification/:id', {
		templateUrl : 'views/notificationdetails.html',
		controller : 'NotificationCtrl'
	})

	.when('/home', {
		templateUrl : 'views/home.html',
		controller : 'NotificationCtrl',
	})

	.when('/uploadprofilepic', {
		templateUrl : 'views/uploadprofilepic.html'
	})

	.when('/suggestedusers', {
		templateUrl : 'views/suggestedusers.html',
		controller : 'FriendCtrl'
	})

	.when('/pendingrequests', {
		templateUrl : 'views/pendingrequests.html',
		controller : 'FriendCtrl'
	})

	.when('/friends', {
		templateUrl : 'views/friendList.html',
		controller : 'FriendCtrl'
	})

	.otherwise({
		templateUrl : 'views/home.html',
		controller : 'NotificationCtrl',
	})
})

app.run(function($location, $rootScope, $cookieStore, UserService) {
	if ($rootScope.loggedInUser == undefined)
		$rootScope.loggedInUser = $cookieStore.get('currentuser')
	$rootScope.logout = function() {
		console.log('entering logout')
		UserService.logout().then( // usersevive calling the logout function

		function(response) {
			delete $rootScope.loggedInUser;
			$cookieStore.remove('currentuser')
			$rootScope.message = "successfully loggedout..."
			$location.path('/login');
		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')
		})

	}

})