/**
 * JobController
 * #/getalljobs
 * #/getjob/751
 */

    app.controller('JobCtrl', function($scope, $rootScope, $location, JobService,$routeParams) {
	var id=$routeParams.id
	//call this function from jobform.html
	$scope.addJob = function(job) {
		JobService.addJob(job).then(
			function(response) {
			alert('Job details posted successfully..')
			$location.path('/alljobs')
		}, function(response) {
			// 3 return statement for error
			// 1.not authenticated..2.not authorized--401 3.
			$rootScope.error = response.data
			if (response.status == 401)
				$location.path('/login')
		})
	}

		JobService.getAllJobs().then(function(response) {
			$scope.jobs = response.data
		}, function(response) {
			$rootScope.error = response.data
			if (response.status == 401) // not logged in
				$location.path('/login')
		})
		
		if (id != undefined) {
			jobService.getJob(id).then(function(response) {
				$scope.job = response.data  //result of the query:select * from job 
			}, function(response) {

				$rootScope.error = response.data
				if (response.status == 401) // not logged in
					$location.path('/login')
			})
		}

	
})
