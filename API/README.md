
# Routes - API  
  
## Stacks  
 - Java - V8
 - Maven - V3
 - SpringBoot - V2.3.3

## APIs
**FindBestRoute** - GET - */api/v1/travel-routes?from=GRU&to=BRC* - Look for the cheap route
**Request:**
```
QUERY PARAM:
from=GRU
&
to=BRC
```
**Response:**
```json
{
"router": "GRU-BRC",
"value": 10
}
```
| Http Code | Http Type   | Action            |
|-----------|-------------|-------------------|
|    200    | Success     | Route found       |
|    204    | No Content  | Route not found   |
|    400    | Bad Request | Param not informed|

**AddNewRoutes** - POST - */api/v1/travel-routes* - Insert new routes by CSV file
***Request:*** CSV File
```
HEADER
Content-Type:multipart/form-data; 
```

***Response:*** Body Empty

***Template CSV:***
```csv
Router,Value
"GRU-BRC-SCL-ORL-CDG","40"
"GRU-ORL-CGD","64"
"GRU-CDG","75"
"GRU-SCL-ORL-CDG","48"
"GRU-BRC-CDG","45"
```

| Http Code | Http Type             | Action            |
|-----------|-----------------------|-------------------|
|    201    | Success               | Create success    |
|    500    | Internal Server Error | Param not informed|
|    500    | Internal Server Error | Nonstandard file  |

## 1 - Build
This step is necessary to generate the jar and download the dependencies.
> Build with test `mvn clean install test`

or

> Build without test `mvn clean install`

## 2 - Run Application
> Start the API application `mvn spring-boot:run`