'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $transitions, $interval) {

        let tick = function () {
            $scope.clock = Date.now();
        }
        tick();
        $interval(tick, 1000);

        $scope.subscriptions = {}

        restApiFindAllSubscriptions();

        function restApiFindAllSubscriptions() {
            RestAPI.get("/subscriptions")
                .then(function (response) {
                    $scope.subscriptions = JSON.parse(JSON.stringify(response.data));
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }
    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               <svg class="blink_1_second bd-placeholder-img rounded me-2" width="10" height="10" 
                  xmlns="http://www.w3.org/2000/svg" 
                  aria-hidden="true" 
                  preserveAspectRatio="xMidYMid slice" focusable="false">
                  <rect width="100%" height="100%" fill="cornflowerblue"></rect>
               </svg>
               <b>User subscriptions</b>
               <br>
               {{ clock | date:'medium'}}
               <br>
               username: {{ subscriptions.user.username}}
               <br>
               email: {{ subscriptions.user.email}}
            </div>
            <div class="card-body">
               <div class="table-responsive">
                  <table class="table table-sm">
                     <thead>
                        <tr>
                           <th scope="col">Number</th>
                           <th scope="col">Name</th>
                           <th scope="col">RegistrationDate</th>
                           <th scope="col">Link</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr ng-repeat="bill in subscriptions.bills track by $index">
                           <td>{{bill.number}}</td>
                           <td>{{bill.name}}</td>
                           <td>{{bill.registrationDate}}</td>
                           <td> <a ng-href='{{bill.link}}'>{{bill.link}}</td>
                        </tr>
                     </tbody>
                  </table>
               </div>
            </div>
            <div class="card-footer text-muted">
               <p></p>
            </div>
         </div>
      </div>
   </div>
</div>
`
});