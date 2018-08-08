# Lambda Architecture. MarketData
### Applied to electricity market

* ###Description

    MarketData is a Big Data, Data science and Data engineering project in which I am implemented a **lambda architecture** to treat electricity market data. This project is been developed jointly with the European Institute For Energy Research (EIFER) in Karlsruhe, Germany and the University Institute of Intelligent Systems and Numeric Applications in Engineering (SIANI) in Gran Canaria, Spain.
    
    To implement the lambda architecture, I have used two IntelliJIDEA plugins developed by SIANI:
    
    1. Tara
    2. Intino
    
* ###Context

    I am doing this project as an internship in EIFER for three months. The main problem is that the dataset is a timeseries oriented dataset, which fits perfectly into a lambda architecture as it could be considered a functional architecture in which all the different events are immutable.
    
* ###Lambda Architectures
![Lambda architecture image](./assets/lambda_architecture.PNG)

This architecture was design by Nathan Marz in order to succesfully addressed big data requirements. He came up with this architecture thanks to his experience  working on distributed data processing at Twitter. Some examples of those requirement are:
* Fault-tolerance against hardware failures and humans errors
* Support for a variety of use cases that include low latency querying as well as updates
* Linear scale-out capabilities, meaning that throwing more machines at the problem should help with getting the job done
* Extensibility so that the system is manageable and can accommodate newer features easily

As you can see in the above picture extracted from [mapr.com](https://mapr.com/developercentral/lambda-architecture/), a lambda architecture has three main components:
* **Batch layer:** Manage the master dataset, an immutable-only set of raw data. It also pre-computes query functions which are called batch views.
* **Serving layer:** Indexes the batch views so they can be queried in low latency. 
* **Speed layer:** Accommodates all request that are subject to low latency requirements.

Each of these layers can be implemented different existing technologies like Spark Streaming for the speed layer or Cassendra for the serving layer.

In this case, I am using as mentioned in the beginning two plugins developed by SIANI. Tara and Intino. The one that includes the three layers in this case is **Intino**.

* ###Project Structure

