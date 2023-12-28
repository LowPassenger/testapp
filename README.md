# Test Application
Test task

# Warning! 
All endpoints in my application use the HTTP protocol. As this is a test application, I strongly discourage doing this in your own REST services. At a minimum, you should use the HTTPS protocol for your application components' communication. The best solution would be to use services like Okta or OAuth for authorizing/authenticating your users.

## About
This simple backend API task lets you receive and save data in JSON format to a database. The application includes four API endpoints that share a common base URL: localhost:8080/api/v1

1. Our application uses Spring Security, so you will first need to register a new user via the endpoint localhost:8080/api/v1/user/add using the HTTP POST method. Please use this type of JSON:

```json
{
  "username": "username",
  "password" : "password"
}
```
Your password should be greater than 8 characters in length.

2. Next, authenticate to receive your JWT token via the URL localhost:8080/api/v1/user/authenticate using the HTTP POST method. Use the same JSON structure as above:

```json
{
  "username": "username",
  "password" : "password"
}
```
The application will return a Bearer JWT token, which you should save and use for subsequent steps.

3. Now, let's create a table in our database and add some records using the URL localhost:8080/api/v1/products/add. Use the HTTP POST method with a Bearer token for authorization. Add the generated token to the header and use the same JSON structure as follows:

```json
{
  "table" : "products",
  "records" : [
    {
      "entryDate": "03-01-2023",
      "itemCode": "11111",
      "itemName": "Test Inventory 1",
      "itemQuantity": "20",
      "status": "Paid"
    },
    {
      "entryDate": "03-01-2023",
      "itemCode": "11111",
      "itemName": "Test Inventory 2",
      "itemQuantity": "20",
      "status": "Paid"
    }
  ]
}
```
The "table" parameter is used to create a table in your MySQL database using the 'testdatabase' schema. In this instance, the table name is 'products'; feel free to modify this parameter accordingly.

4. Lastly, check all records in our 'table' via the URL localhost:8080/api/v1/products/all. Use the HTTP GET method with a Bearer token for authorization, adding your generated token to the header.