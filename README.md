# Company Search Application

This Spring Boot application provides a REST API to search for companies and their officers using the TruProxy API.
## Features

- Search for companies by name or registration number.
- Filter search results to show only active companies.
- Retrieve a list of active officers for each company.

## Project Setup

1. **Prerequisites**
   - Java 17 or higher
   - Maven 
   - Your preferred IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)

2. **Clone the Repository**
   ```bash
   git clone <your-repository-url>
   cd company-search
   ```

3. **Configure API Key**
   - Obtain your TruProxy API key.
   - Set the `TRUPROXYAPI_KEY` environment variable:
     ```bash
     export TRUPROXYAPI_KEY=your-actual-api-key
     ```
   **Important: Do not check the API Key into the repository!**

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

## Code Structure

```
COMPANYSEARCH
├── src
│   ├── main
│   │   ├── java/com/sourav/companysearch/application
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   │   └── CompanyController.java
│   │   │   ├── model
│   │   │   │   ├── Address.java
│   │   │   │   ├── Company.java
│   │   │   │   ├── CompanySearchRequest.java
│   │   │   │   ├── CompanySearchResponse.java
│   │   │   │   ├── Officer.java
│   │   │   │   └── OfficerResponse.java
│   │   │   └── service
│   │   │       ├── CompanyService.java
│   │   │       └── CompanySearchApplication.java
│   │   └── resources
│   │       ├── static
│   │       │   └── index.html
│   │       ├── templates
│   │       ├── application.properties
│   │       └── application-prod.properties
│   └── test
│       └── java/com/sourav/companysearch/companysearch
│           ├── controller
│           │   └── CompanyControllerTest.java
│           └── service
│               └── CompanyServiceTest.java
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

- **`com.sourav.companysearch.application`**
  - **`controller`**: Contains the `CompanyController`, which handles incoming API requests.
  - **`model`**: Defines the data models for `Company`, `Officer`, `Address`, `CompanySearchRequest`, `CompanySearchResponse`, and `OfficerResponse`.
  - **`service`**: Contains the `CompanyService`, which handles the core business logic of fetching company and officer data from the TruProxy API .
  - **`config`**: Provides configuration classes, such as `WebClientConfig` for setting up the `WebClient` used for API calls.

## API Endpoints

- **`POST /api/companies/search`**
  - Searches for companies based on the provided criteria.
  - Headers:
    - `x-api-key`: Your TruProxyAPI key
  - Request Body:
    ```json
    {
        "companyName": "string" (optional),
        "companyNumber": "string" (optional)
    }
    ```
  - Query Parameter: `activeOnly` (boolean, default: false)
  - If both `companyName` and `companyNumber` are provided, `companyNumber` is used.
  - Response:
    ```json
    {
        "total_results": "integer",
        "items": [
            { 
                // ... Company object structure (see `Company.java`)
            }
        ]
    }
    ```

## Sample Requests and Responses

**Search by Company Number**

- Request:
  ```bash
  curl -X POST http://localhost:8080/api/companies/search \
       -H 'Content-Type: application/json' \
       -H 'x-api-key: your-api-key-here' \
       -d '{"companyNumber": "06500244"}'
  ```

- Response:
  ```json
  {
      "total_results": 1,
      "items": [
          {
              "company_number": "06500244",
              "company_type": "ltd",
              "title": "BBC LIMITED",
              "company_status": "active",
              "date_of_creation": "2008-02-11",
              "address": { 
                  "locality": "Retford",
                  "postal_code": "DN22 0AD",
                  "premises": "Boswell Cottage Main Street",
                  "address_line_1": "North Leverton",
                  "country": "England"
              },
              "officers": [
                  {
                      "name": "BOXALL, Sarah Victoria",
                      "officer_role": "secretary",
                      "appointed_on": "2008-02-11",
                      "address": { 
                          "premises": "5",
                          "locality": "London",
                          "address_line_1": "Cranford Close",
                          "country": "England",
                          "postal_code": "SW20 0DP"
                      }
                  }
              ]
          }
      ]
  }
  ```

**Search by Company Name**

- Request:
  ```bash
  curl -X POST http://localhost:8080/api/companies/search \
       -H 'Content-Type: application/json' \
       -H 'x-api-key: your-api-key-here' \
       -d '{"companyName": "BBC LIMITED"}'
  ```

- Response: (Similar to the previous response)

**Filter for Active Companies Only**

- Request:
  ```bash
  curl -X POST http://localhost:8080/api/companies/search?activeOnly=true \
       -H 'Content-Type: application/json' \
       -H 'x-api-key: your-api-key-here' \
       -d '{"companyName": "Some Company"}' 
  ```

- Response: Will only include companies with `company_status` as "active".

## Additional Notes

- **Testing**: The project includes unit and integration tests for the `CompanyController` and `CompanyService`. Run `mvn test` to execute the tests. WireMock is used to mock `TruProxyAPI` calls in tests.
- **Error Handling**: The `CompanyService` includes error handling and retry mechanisms to gracefully handle potential API issues.
- **Paging**: Paging is not implemented in this version.


## Screenshot

![View Application Screenshot](https://drive.google.com/file/d/1lx2r_rOoDmrtucIbOT7yegjSXNv4oLtm/view?usp=drive_link)

## PS: The updated code is available under the master branch.


