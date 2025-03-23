# Order API


## Data Models (Order-API)
### ER Diagram
```mermaid
erDiagram
    CUSTOMER ||--o{ ORDER : places
    ORDER ||--o{ ORDER_ITEM : contains
    ORDER {
        string orderId PK
        string customerId FK
        decimal totalAmount
        string status
        date createdAt
    }
    ORDER_ITEM {
        string orderItemId PK
        string orderId FK
        string productId
        int quantity
        decimal price
    }
```

## Class Diagram
```mermaid
classDiagram
    class Order {
        +String orderId
        +String customerId
        +BigDecimal totalAmount
        +String status
        +Date createdAt
        +List<OrderItem> items
    }

    class OrderItem {
        +String orderItemId
        +String productId
        +int quantity
        +BigDecimal price
    }

    Order "1" --> "many" OrderItem : contains
```