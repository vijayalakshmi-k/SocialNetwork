/**
 * JobService
 */
app.factory('JobService', function($http) {                 //$http to make a http request
	var jobService = {}

	jobService.addJob = function(job) {                                                       

		return $http.post("http://localhost:8091/ScocialNetworksMiddle/addjob",job)    
	}

	jobService.getAllJobs = function() {

		return $http.get("http://localhost:8091/ScocialNetworksMiddle/alljobs")
	}
	
	
	jobService.getJob = function(id) {

		return $http.get("http://localhost:8091/ScocialNetworksMiddle/getjob/"+id)
	}



	return jobService;
})


