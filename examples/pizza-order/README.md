# Pizza order example

*This example demonstrates how to use miranum connect and how you can decouple micro-services using it.*

There are four services in total:

* `waiter`: responsible for placing the order, serving beverages and issuing the check
* `kitchen`: responsible for the cooking
* `email`: responsible for notifying the customer for the order confirmation
* `frontend`: frontend to place order to the waiter service by REST

## Run the project

1. Start all services, namely the `WaiterC8Application`, `KitchenC8Application`, `EmailC8Application` and the `PizzaOrderFrontEnd`
2. Make sure that you have a running instance of the Camunda8 Engine running. <br> *If not, just start the camunda-8 stack under `stack/camunda-8`.*
3. Also make sure to deploy the `pizza-order-c8.bpmn` to the running Camunda8-Engine.
4. Now navigate to http://localhost:5050 to view the Frontend-Application. You will end up seeing something like this:

<br>
<div align="center">
    <img src="./../../images/pizza-order-frontend.png" alt="pizza-order-frontend" width="300" align="center">
</div>
<br>

Now you can start a new order by filling out the form. Provide food (one or a comma separated list), drinks
your name and your email address. The email is not actually sent. For the sake of this example it is just a print on the console of the email service.

*Info: When using any jetbrains related IDE you can start all four services by adding a compound run configuration.*

After submitting your order, you should notice the success message below.

<br>
<div align="center">
    <img src="./../../images/pizza-order-success-msg.png" alt="pizza-order-success-message" width="300" align="center">
</div>
<br>

You now can follow the logs in the different service consoles.

## The process

<img src="./../../images/pizza-order-bpmn.png" alt="pizza-order-bpmn">

