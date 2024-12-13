# Cosmos Spring Boot + Session Token

This is a sample Spring Boot application that demonstrates how to interact with Azure Cosmos DB using session tokens for consistency. The application provides RESTful APIs to perform CRUD operations on `Transaction` objects stored in Cosmos DB.

## Prerequisites

- Java 17 or higher
- Maven
- Azure Cosmos DB account

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/fonsecamar/cosmos-spring-sessiontoken.git
    cd cosmos-spring-sessiontoken
    ```

2. Update the [application.properties](http://_vscodecontentref_/1) file with your Azure Cosmos DB credentials:
    ```properties
    azure.cosmos.uri=YOUR_COSMOS_DB_URI
    azure.cosmos.key=YOUR_COSMOS_DB_KEY
    azure.cosmos.database=YOUR_DATABASE_NAME
    ```

3. Run the application

## REST API Endpoints

### Create Transaction

- **URL:** `/transaction`
- **Method:** `POST`
- **Description:** Create a new transaction.
- **Request Body:**
    ```json
    {
        "id": "transaction-id",
        "accountId": "account-id",
        "merchant": "merchant-name",
        "amount": "transaction-amount"
    }
    ```
- **Response:**
    ```json
    "session-token"
    ```


### Get Transaction by ID

- **URL:** `/transaction/{accountId}/{id}?sessionToken={sessionToken}`
- **Method:** `GET`
- **Description:** Retrieve a transaction by its ID and account ID.
- **Query Parameters:**
    - [sessionToken](http://_vscodecontentref_/2) (required): The session token for consistency.
- **Response:**
    ```json
    {
        "id": "transaction-id",
        "accountId": "account-id",
        "merchant": "merchant-name",
        "amount": "transaction-amount",
        "dateTime": "transaction-date-time"
    }
    ```

### List Transactions by Account ID

- **URL:** `/transaction/{accountId}?sessionToken={sessionToken`
- **Method:** `GET`
- **Description:** Retrieve all transactions for a given account ID.
- **Query Parameters:**
    - [sessionToken](http://_vscodecontentref_/3) (required): The session token for consistency.
- **Response:**
    ```json
    [
        {
            "id": "transaction-id",
            "accountId": "account-id",
            "merchant": "merchant-name",
            "amount": "transaction-amount",
            "dateTime": "transaction-date-time"
        },
        ...
    ]
    ```
