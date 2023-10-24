'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $transitions, $interval, $location) {

        let tick = function () {
            $scope.clock = Date.now();
        }
        tick();
        $interval(tick, 1000);

        $scope.subscriptions = {}
        $scope.searchBills = {}
        $scope.searchBillNumber = ''
        $scope.user_username = ''
        $scope.user_email = ''

        restApiFindAllSubscriptions()

        function restApiFindAllSubscriptions() {
            RestAPI.get("/subscriptions")
                .then(function (response) {
                    console.log(response.data)
                    $scope.subscriptions = JSON.parse(JSON.stringify( response.data));
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        $scope.findBillByNumber = function () {
            if ($scope.searchBillNumber === '') {
                alert("Please enter bill number to search")
                return;
            }
            let path = "/bills/" + $scope.searchBillNumber;
            $scope.searchBills = {}
            $scope.searchBillNumber = ''
            RestAPI.get(path)
                .then(function (response) {
                    $scope.searchBills = JSON.parse(JSON.stringify( response.data));
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        $scope.subscribe = function () {
            let path = "/bills"
            let jsRequest = JSON.parse(JSON.stringify({
                user: {
                    username: $scope.user_username,
                    email: $scope.user_email
                },
                active: true,
                bills: $scope.searchBills.bills
            }))
            RestAPI.post(path, jsRequest)
                .then(function (response) {
                    $scope.searchBills = {}
                }, function (reason) {
                    $scope.error = reason.data
                    alert(reason.data)
                });
        }

        $scope.initModalState = function () {
            $scope.searchBillNumber = ''
            $scope.searchBills = {}
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
            </div>
            <div class="card-body">
               <div class="table-responsive">
                  <table class="table table-sm">
                     <thead>
                        <tr>
                           <th scope="col">Id</th>
                           <th scope="col">User</th>
                           <th scope="col">Email</th>
                           <th scope="col">Bills</th>
                           <th scope="col">Active</th>
                           <th scope="col">Date</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr ng-repeat="item in subscriptions track by $index">
                           <td>{{item.id}}</td>
                           <td>{{item.user.username}}</td>
                           <td>{{item.user.email}}</td>
                           <td>
                              <div ng-repeat="bill in item.bills track by $index"> number: {{bill.number}}, link: <a ng-href='{{bill.link}}'>{{bill.link}}</div>
                           </td>
                           <td>{{item.active}}</td>
                           <td>{{item.createdDate | date:'HH:mm:ss'}}</td>
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
      <div class="col-12">
         <!-- Button trigger modal -->
         <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
         Create new
         </button>
         <!-- Modal -->
         <div class="modal fade" 
            id="staticBackdrop" 
            data-bs-backdrop="static" 
            data-bs-keyboard="false" 
            tabindex="-1" 
            aria-labelledby="staticBackdropLabel" 
            aria-hidden="true">
            <div class="modal-dialog modal-xl">
               <div class="modal-content">
                  <div class="modal-header">
                     <h5 class="modal-title" id="staticBackdropLabel">Create new subscription</h5>
                     <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                     <form>
                        <div class="mb-3">
                           <label for="recipient-name" class="col-form-label">Bill number:</label>
                           <input type="text"
                              ng-model="searchBillNumber" 
                              class="form-control" 
                              id="bill-number">
                        </div>
                        <div class="mb-3">
                           <button class="btn btn-primary form-control" 
                              id="find-bill-btn"
                              ng-click="findBillByNumber()">Search</button>
                        </div>
                        <div class="mb-3">
                           <div ng-repeat="item in searchBills.bills track by $index">
                              <div>
                                 <input type="checkbox">
                                 <div>number: {{item.number}} </div>
                                 <div>name: {{item.name}} </div>
                                 <div>Registration date: {{item.registrationDate}}</div>
                              </div>
                           </div>
                           <div ng-show="searchBills.bills">
                              <div class="mb-3">
                                 <label for="recipient-name" class="col-form-label">Username:</label>
                                 <input type="text"                                    
                                    ng-model="user_username" 
                                    class="form-control" 
                                    id="bill-number">
                              </div>
                              <div class="mb-3">
                                 <label for="recipient-name" class="col-form-label">Email:</label>
                                 <input type="text"
                                    ng-model="user_email" 
                                    class="form-control" 
                                    id="bill-number">
                              </div>
                           </div>
                        </div>
                        <div class="mb-3">
                           <button type="button" 
                              class="btn btn-primary" 
                              data-bs-dismiss="modal"
                              ng-click="subscribe()">Subscribe</button>
                        </div>
                     </form>
                  </div>
                  <div class="modal-footer">
                     <button type="button" 
                        class="btn btn-secondary" 
                        data-bs-dismiss="modal"
                        ng-click="initModalState()">Close</button>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
</div>
`
});