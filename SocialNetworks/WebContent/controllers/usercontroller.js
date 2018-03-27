/**
 * 
 * UserController - to receive data from view and to send to view $scope-
 * variable and functions UserService- 1.registerUser(user) function
 * 
 */

app.controller('UserController', function($scope, $rootScope, $location,
		UserService, $cookieStore) {
	$scope.registerUser = function(user) { // user is from view
		// -registrationform.html
		alert('entering usercontroller registerUser function in frontend' + user)
		// console.log('entering usercontroller registerUser function in
		// frontend' +user)
		UserService.registerUser(user)
		.then(function(response)
				{ // success [200,user]
			alert('Registered Success Fully....please login again...')
			$location.path('/home')
		}, function(response) { // error [409 ErrorClazz/ 500 ErrorClazz]
			$scope.error = response.data
		})

	}

	    $scope.login = function(user) {
		console.log('UserController ->login')
		//console.log('login successfully')
	     console.log(user);
	   UserService.login(user)
		.then(function(response) {
			$rootScope.loggedInUser = response.data // user object
			$cookieStore.put('currentuser', response.data)
			//console.log('success')
			//console.log(response.data)
			$location.path('/home')
		}, function(response) {
           // console.log('error')
			$scope.error = response.data
			$location.path('/login')
		})
	}
	
	//statement which will executed automatically when controller gets loaded
	//Controller to view 
	
	    if($rootScope.loggedInUser!=undefined){
		 UserService.getUser().then(
		   function(response){
			$scope.user=response.data //result of the query :select *from where=?
			console.log('in get user:')
			console.log($scope.user);
			},
			function(response){
				if(response.status==401)
					$location.path('/login')
					
				
			})
		}
	//view to Controller
	$scope.updateUser=function(user)
	{
		
		UserService.updateUser(user)
		.then(function(response)
		{
			alert('update user profile successfully...,')
			$rootScope.loggedInUser=response.data
			$cookieStore.put('loggedInUser',response.data)
			$location.path('/home')
		},function(response)		
		{
			if(response.status==401)
				$location.path('/login')
		else
			$scope.error=response.data;

		})
		
	}
	
}) 







