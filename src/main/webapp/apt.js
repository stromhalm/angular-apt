angular.module("apt")
.value("serverEndpoint", "http://angular-apt.azurewebsites.net/api/")
.service("apt", ["$http", "serverEndpoint", function apt($http, serverEndpoint) {
    this.getCoverabilityGraph = function(aptCode) {
        $http.post(serverEndpoint + "coverabilityGraph", null)
            .then(function(response) {
                return response.coverabilityGraph
            })
    }

    this.getSynthesizedNet = function(aptCode) {
        $http.post(serverEndpoint + "coverabilityGraph", null)
            .then(function(response) {
                return response.coverabilityGraph
            })
    }
}]);