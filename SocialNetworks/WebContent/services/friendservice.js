/**
 * 
 */
app.factory('FriendService',function($http) {
					var friendService = {}

					friendService.getAllSuggestedUsers = function() {
					return $http.get("http://localhost:8091/ScocialNetworksMiddle/suggestedusers")
					}
					
					friendService.addFriend = function(toId) {
					return $http.post("http://localhost:8091/ScocialNetworksMiddle/addfriend",toId)
					}
					
					
					friendService.getPendingRequests = function() {
						return $http.get("http://localhost:8091/ScocialNetworksMiddle/pendingrequests")
						}
					
					friendService.acceptRequest = function(request) {
						return $http.put("http://localhost:8091/ScocialNetworksMiddle/acceptrequest",request)
						}

					friendService.deleteRequest = function(request) {
						return $http.put("http://localhost:8091/ScocialNetworksMiddle/deleterequest",request)
						}
					
					
					friendService.getAllFriends = function() {
						return $http.get("http://localhost:8091/ScocialNetworksMiddle/friends")
						}
					

					

				return friendService;
				}

		)