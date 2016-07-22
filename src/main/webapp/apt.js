angular.module("apt", [])
.value("serverEndpoint", "http://angular-apt.azurewebsites.net/api/")
.service("apt", ["$http", "serverEndpoint", function apt($http, serverEndpoint) {
    this.getCoverabilityGraph = function(aptCode) {
        return $http.post(serverEndpoint + "coverabilityGraph", {apt: aptCode})
    }

    this.getSynthesizedNet = function(aptCode) {
        return $http.post(serverEndpoint + "coverabilityGraph", {apt: aptCode})
    }
}]);