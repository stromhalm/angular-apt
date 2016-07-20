angular.module("apt", [])
.value("serverEndpoint", "http://angular-apt.azurewebsites.net/api/")
.service("apt", ["$http", "serverEndpoint", function apt($http, serverEndpoint) {
    this.getCoverabilityGraph = function(aptCode) {
        return $http.get(serverEndpoint + "coverabilityGraph")
    }

    this.getSynthesizedNet = function(aptCode) {
        return $http.get(serverEndpoint + "coverabilityGraph")
    }
}]);