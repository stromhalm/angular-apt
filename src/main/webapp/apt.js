angular.module("apt", [])
.value("serverEndpoint", "http://angular-apt.azurewebsites.net/api/")
.service("apt", ["$http", "serverEndpoint", function apt($http, serverEndpoint) {
    this.getCoverabilityGraph = function(aptCode) {
        return $http.post(serverEndpoint + "coverabilityGraph", null)
    }

    this.getSynthesizedNet = function(aptCode) {
        return $http.post(serverEndpoint + "coverabilityGraph", null)
    }
}]);