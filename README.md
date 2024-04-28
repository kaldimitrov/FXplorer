# FXplorer

FXplorer is a versatile financial tool designed to provide users with quick and accurate foreign exchange information and currency conversion services. With a user-friendly interface and robust backend functionality, this application serves as a fundamental component for financial applications and services.

# Setup
## Requirements
 - **Docker**

## Installation Steps

1. **Clone the Repository**: First, clone the repository to your local machine using Git:

   ```bash
   git clone https://github.com/kaldimitrov/FXplorer.git
   cd FXplorer
   ```

2. **Start The Application**: Once inside the project directory, run the application:

   ```bash
   docker-compose up -d
   ```

3. **Access the App**: The server will start, usually on port 8080.

# API Documentation
[![Documentation](https://i.imgur.com/rdviwJh.jpeg)](https://i.imgur.com/rdviwJh.jpeg)

You can access the full OpenAPI definition by going to the following endpoints:
 - **JSON** - http://localhost:8080/v3/api-docs
 - **YAML** - http://localhost:8080/v3/api-docs.yaml

## Endpoints

## Exchange Rate Endpoint
**GET** `/exchanges?base=USD&target=EUR`

**Content-Type**: `application/json`

**Response**: 
```json
{
  "rate":0.93404000
}
```

## Conversion History Endpoint
**GET** `/exchanges/history?id=<uuid>&startDate=<date>8&endDate=<date>&page=0&pageSize=10`

**Content-Type**: `application/json`

**Response**: 
```json
{
  "content": [
    {
      "id": "3bd0975f-db26-4336-a7ce-699d23774709",
      "exchangeRateId": 44,
      "input": 1000,
      "output": 934.04,
      "createdAt": "2024-04-28T14:11:51.797904"
    },
    {
      "id": "861051ad-a183-4400-b6d1-e4cb306077c9",
      "exchangeRateId": 44,
      "input": 1000,
      "output": 934.04,
      "createdAt": "2024-04-28T14:11:52.175874"
    },
    {
      "id": "3cb8426f-9dfa-499b-9d3e-03aad8d191e5",
      "exchangeRateId": 44,
      "input": 1000,
      "output": 934.04,
      "createdAt": "2024-04-28T14:11:52.544977"
    }
  ],
  "pageable": {
    "pageNumber": 1,
    "pageSize": 3,
    "sort": {
      "sorted": false,
      "empty": true,
      "unsorted": true
    },
    "offset": 3,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalPages": 3,
  "totalElements": 8,
  "size": 3,
  "number": 1,
  "sort": {
    "sorted": false,
    "empty": true,
    "unsorted": true
  },
  "first": false,
  "numberOfElements": 3,
  "empty": false
}
```

## Currency Conversion Endpoint

**POST** `/exchanges/convert`

**Content-Type**: `application/json`

**Request**: 
```json
{
    "base": "USD",
    "target": "EUR",
    "amount": 1000
}
```

**Response**: 
```json
{
    "id": "7fa29903-eb42-4af7-b575-cc98dd153277",
    "exchangeRateId": 44,
    "input": 1000,
    "output": 934.04000000,
    "createdAt": "2024-04-28T14:11:53.332609122"
}
```

# License

This project is licensed under the MIT License. This implies that you are free to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software, given that you include the original copyright notice and the permission notice in all copies or substantial portions of the software. For more information, please see the [LICENSE](LICENSE) file in our project repository or visit the Open Source Initiative website.
