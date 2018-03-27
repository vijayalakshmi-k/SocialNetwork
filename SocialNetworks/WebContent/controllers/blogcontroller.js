/**
 * BlogCtrl
 */

app
		.controller(
				'BlogCtrl',
				function($scope, $rootScope, $location, BlogService) {
					$scope.addBlog = function(blog) { // user is from view
						console.log("entering blog controller")
						BlogService
								.addBlog(blog)
								.then(
										function(response) {
											alert("BlogPost is added successfully and it is waiting for approval")
											$location.path('/home') // getAllBlogs
										}, function(response) { // error [409
																// ErrorClazz/
																// 500
																// ErrorClazz]
											$rootScope.error = response.data
											if (response.status == 401)
												$location.path('/login')

										})
					}
					if ($rootScope.loggedInUser.role == 'ADMIN') {
						alert("entering into the blogs")
						BlogService.getBlogsWaitingForApproval().then(
										function(response) {
											console.log(response.data)
											$scope.blogsWaitingForApproval = response.data
										}, function(response) {
											$rootScope.error = response.data
											if (response.status == 401)
												$location.path('/login')
										})
					}

					BlogService.getBlogsApproved().then(function(response) {
						console.log(response.data)
						$scope.blogsApproved = response.data
					}, function(response) {

						$rootScope.error = response.data
						if (response.status == 401)
							$location.path('/login')
					})
				})
