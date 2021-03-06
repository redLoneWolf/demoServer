## demoServer

```/organisations``` - create/retrieve/update/delete organisations

POST request 
```json
  {
   "name": "ABC Logistics"
  }
```
response
```json
  {
    "id": 1,
    "name": "ABC Logistics",
    "createdAt": "2022-06-15 16:36:45"
  }
```
PATCH request ```/organisations?id=1```
```json
  {
    "name": "ABCD Logistics"
  }
```
response
```json
  {
    "id": 1,
    "name": "ABCD Logistics",
    "createdAt": "2022-06-15 16:36:45"
  }
```
#

```/warehouses``` - create/retrieve/update/delete warehouses

POST request 
```json
  {
    "orgId": 1,
    "name": "ABC Warehouse at Madurai"
  }
```
response
```json
  {
    "id": 2,
    "name": "ABC Warehouse at Madurai",
    "orgId": 1
  }
```
#

```/items``` - create/retrieve/update/delete items

POST request
```json
  {
    "orgId": 1,
    "name": "Blue pen",
    "description": "Blue pen from cello",
    "costPrice": 15,
    "sellingPrice":20
  }
```
response
```json
   {
      "id": 2,
      "name": "Blue pen",
      "description": "Blue pen from cello",
      "costPrice": 15,
      "sellingPrice": 20,
      "orgId": 1,
      "createdAt": "2022-06-15 16:37:32.0",
      "totalStock": 390
    }
```
#

```/stocks``` - create/retrieve/update/delete stock entry for items in their warehouse

POST request
```json
  {
    "itemId": 2,
    "warId": 1,
    "count": 200   
  }
```

response
```json
  {
    "id": 4,
    "warId": 1,
    "itemId": 2,
    "count": 200
  }
```

PATCH request  ```/stocks?id=4```
```json
  {
    "count": 150   
  }
```
response
```json
{
    "id": 4,
    "warId": 1,
    "itemId": 2,
    "count": 150
}
```
#

```/invoices``` - create/retrieve/update/delete invoice for orders accepts orders as list

POST request
```json
  {
    "orgId": 1,
    "customerName": "sudhar",  
    "discount": 5.78,
    "tax": 4.69,
    "orders": [
        {
          "itemId": 2,
          "quantity": 10,
          "warId":2
        },
        {
          "itemId": 1,
          "quantity": 10,
          "warId":1
        }
    ]
  }
```
response
```json
{
    "id": 1,
    "orgId": 1,
    "customerName": "sudhar",
    "createdAt": "2022-06-15 16:38:43.0",
    "discount": 5.78,
    "tax": 4.69,
    "totalPrice": 300.0,
    "withTaxDis": 295.91675,
    "orders": [
        {
            "id": 1,
            "invoiceId": 1,
            "customerName": "sudhar",
            "itemId": 2,
            "orgId": 1,
            "price": 15,
            "quantity": 10,
            "createdAt": "2022-06-15 16:38:43.0",
            "warId": 2,
            "amount": 150
        },
        {
            "id": 2,
            "invoiceId": 1,
            "customerName": "sudhar",
            "itemId": 1,
            "orgId": 1,
            "price": 15,
            "quantity": 10,
            "createdAt": "2022-06-15 16:38:43.0",
            "warId": 1,
            "amount": 150
        }
    ]
}
```
#
```/reports/items``` report for items options eg.```?sortBy=soldUnits```

available options : ```soldUnits,stockUnits,itemPrice,totalSaleAmount```

sample ```/reports/items?sortBy=-totalSaleAmount```
```json 
[
    {
        "createdAt": 1655448707000,
        "sellingPrice": 500,
        "totalSaleAmount": 25000,
        "item_id": 1,
        "name": "shoe",
        "stockUnits": 250,
        "soldUnits": 50
    },
    {
        "createdAt": 1655448707000,
        "sellingPrice": 15,
        "totalSaleAmount": 150,
        "item_id": 2,
        "name": "pen",
        "stockUnits": 100,
        "soldUnits": 10
    },
 ]
 ```

#
```/reports/customer``` report for customer options eg.```?sortBy=cname```

available options : ```cname,totalPrice,invoices```

sample ```/reports/items?sortBy=totalPrice```
```json 
[
    {
        "invoices": 1,
        "totalPrice": 4000,
        "cname": "zack"
    },
    {
        "invoices": 5,
        "totalPrice": 16100,
        "cname": "sudhar"
    }
]
 ```

```/groups``` create delete item groups

POST request

```json lines
{
    "orgId": 1,
    "name": "Pens",
    "items":[
        {
          "name":"Red Pen",
           "costPrice": 11,
            "sellingPrice":20
        },{
          "name":"Blue Pen",
           "costPrice": 12,
            "sellingPrice":20
        },
        {
          "name":"Green Pen",
           "costPrice": 13,
            "sellingPrice":20
        }
    ]
}
```

response 

```json lines
{
    "id": 3,
    "name": "Pens",
    "orgId": 1,
    "items": [
        {
            "id": 8,
            "name": "Red Pen",
            "description": "null",
            "costPrice": 11,
            "sellingPrice": 20,
            "orgId": 1,
            "createdAt": "2022-06-20 14:03:00.0",
            "totalStock": 0,
            "groupId": 3,
            "stocks": []
        },
        {
            "id": 9,
            "name": "Blue Pen",
            "description": "null",
            "costPrice": 12,
            "sellingPrice": 20,
            "orgId": 1,
            "createdAt": "2022-06-20 14:03:00.0",
            "totalStock": 0,
            "groupId": 3,
            "stocks": []
        },
        {
            "id": 10,
            "name": "Green Pen",
            "description": "null",
            "costPrice": 13,
            "sellingPrice": 20,
            "orgId": 1,
            "createdAt": "2022-06-20 14:03:00.0",
            "totalStock": 0,
            "groupId": 3,
            "stocks": []
        }
    ]
}
```

