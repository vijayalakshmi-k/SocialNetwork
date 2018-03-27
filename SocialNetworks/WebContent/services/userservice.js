/**
 * 
 */
app.factory('UserService',function($http)
		
{
var userService={}	
userService.registerUser=function(user) // get user all the details from the
										// controller frontend
{
	alert('Entering userservice registeruserfunction user')
	// format of users -JSON
	console.log('in userservice')
	console.log(user)
	return $http.post("http://localhost:8091/ScocialNetworksMiddle/registeruser",user)   
	}

  userService.login=function(user)
 {
	alert('entering userservice loggeinfunction user')
	console.log('in userservice')
	console.log(user)
	return $http.post("http://localhost:8091/ScocialNetworksMiddle/login",user)

}
userService.logout=function()
{
	
return $http.put("http://localhost:8091/ScocialNetworksMiddle/logout")
}
  userService.getUser=function()
 {
	return $http.get("http://localhost:8091/ScocialNetworksMiddle/getuser")
	
 }

 userService.updateUser=function(user)
 {
	return $http.put("http://localhost:8091/ScocialNetworksMiddle/updateuser",user)
	
 }

 return userService;
})

