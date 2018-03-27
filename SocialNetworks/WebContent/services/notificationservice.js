/**
 * 
 */
app.factory('NotificationService',function($http)
		
{
var notificationService={}
notificationService .getNotificationsNotViewed=function()
{
	
	return $http.get("http://localhost:8091/ScocialNetworksMiddle/getnotifications")

}

notificationService .getNotifications=function(id)
{
	
	return $http.get("http://localhost:8091/ScocialNetworksMiddle/getnotification/"+id)

}

notificationService .updateNotification=function(id)
{
	
	return $http.put("http://localhost:8091/ScocialNetworksMiddle/updatenotification/"+id)

}

return notificationService;
})