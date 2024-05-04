**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

You are tasked with building a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to maintain a transaction history. The system should allow users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances. The ledger should maintain a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.


Implement the event sourcing pattern to record all banking transactions as immutable events. Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

Here’s a breakdown of the key criteria we’ll be considering when grading your submission:

**Adherence to Design Patterns:** We’ll evaluate whether your implementation follows established design patterns such as following the event sourcing model.

**Correctness**: We’ll assess whether your implementation effectively implements the desired pattern and meets the specified requirements.

**Testing:** We’ll assess the comprehensiveness and effectiveness of your test suite, including unit tests, integration tests, and possibly end-to-end tests. Your tests should cover critical functionalities, edge cases, and potential failure scenarios to ensure the stability of the system.

**Documentation and Clarity:** We’ll assess the clarity of your documentation, including comments within the code, README files, architectural diagrams, and explanations of design decisions. Your documentation should provide sufficient context for reviewers to understand the problem, solution, and implementation details.

# Candidate README
## Project Structure
```
Structure: dev.codescreen
    ├── Application.java
    ├── Controller
    │   └── UserController.java
    ├── Service
    │   └── UserService.java
    ├── Repository
    │   ├── UserRepository.java
    │   └── TransactionRepository.java
    └── Entity
        ├── Amount.java
        ├── AuthorizationRequest.java
        ├── AuthoriizationResponse.java
        ├── DebitCredit.java
        ├── Error.java
        ├── LoadRequest.java
        ├── LoadResponse.java
        ├── Ping.java
        ├── ResponseCode.java
        ├── Transaction.java
        └── User.java
Structure: resources
    └── application.properties
```
## Bootstrap instructions
To run this server locally, do the following:
1. Clone the repository to your local machine using `git clone <repository-url>`.
2. Navigate to the project directory using `cd <directory-name>`.
3. If you are using Maven, you can compile and run the application using the following commands: 
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
4. The application will start running at `http://localhost:8080`.
5. You can test the endpoints using any HTTP client like curl or Postman. Here's an example of how to use curl to test the endpoints:
    ```bash
    curl http://localhost:8080/ping
    ```

## Testing
Unit and integration tests are included in the project. To run the tests, use the following command:
    ```bash
    mvn test
    ```
## Database Setup
To successfully run the code, it is necessary to set up a database yourself. I have attached my PostgreSQL configuration for reference.

## Design Considerations
1. I decided to use a permanent SQL database for storing User information and transaction information, as the banking system needs to strictly follow ACID principles. This ensures data consistency and reliability, which are crucial in a banking system.
2. I decided to use a temporary data storing method for `messageId` because I need to ensure uniqueness of `messageId` for a short period of time. This is to prevent processing the same request multiple times.
3. I used UUID for storing transaction information, as I stick to the form in the provided `service.yml` file. UUID provides a fast and easy way to generate unique identifiers, which is essential for tracking each transaction.

## Assumptions
1. Because `LoadRequest` and `AuthorizationRequest` already include a `messageId` attribute, it's reasonable to assume that the `messageId` is being generated on the client side before the request is sent to the service. This is to ensure that each request is unique and can be tracked individually.
2. I assume that a User has been created and both PUT endpoints should not create a new User. When using the `findById` method, we search for an existing user, and if the user is not found, an error is thrown. This is to ensure that transactions are only made for existing users.

## Deployment Considerations
If I were to deploy this, I would host it on a cloud platform like AWS or Google Cloud Platform. These platforms provide scalable and reliable hosting services, which are essential for a banking system that needs to handle a large number of transactions. I would use Docker for containerization to ensure that the application runs consistently across different environments. For continuous integration and deployment, I would use a service like Jenkins or Travis CI. This would automate the testing and deployment process, ensuring that any changes to the code are immediately tested and deployed to the production environment.

## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/0929c54c-16a1-4ed7-92c0-f961800ac685" target="_blank">this screen</a>.