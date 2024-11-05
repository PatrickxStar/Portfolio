# Restaurant Management System

## Project Description

The Restaurant Management System is a Java-based application designed to help restaurant staff and managers efficiently manage daily operations. The system includes features such as user login, menu management, order processing, table management, inventory management, and sales reporting. This project is a GUI-based application that utilizes Java Swing for the graphical interface and various Java libraries for backend functionality.

## Features

- **User Login**
  - Secure login system with hashed passwords.
  - Role-based access control for staff and managers.

- **Menu Management**
  - Add, remove, and edit menu items.
  - Menu items include name, description, preparation time, price, and ingredients list.
  - Changes are saved and loaded between sessions using file I/O.

- **Order Processing**
  - Handle customer orders with properties such as order ID, items ordered, total price, and status.
  - Manage multiple orders simultaneously and update order status in real-time.

- **Table Management**
  - Manage restaurant tables with properties such as table ID, size, and status (available, reserved, occupied).
  - Assign customers to specific tables.

- **Inventory Management**
  - Track ingredient usage in the kitchen.
  - Alert staff when ingredients are running low.
  - Real-time updates as orders are processed.

- **Sales Reporting**
  - Generate daily sales reports with information on total revenue, popular items, and active tables.
  - Export reports as text files using Java's file I/O capabilities.

## Technology Stack

- **Programming Language:** Java
- **Build Tool:** Maven
- **GUI Framework:** Java Swing
- **Data Persistence:** File I/O
- **Password Hashing:** BCrypt

## Project Structure
```bash
CTAC-FS103-Agile-Project/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── com/
│ │ │ │ ├── restaurant/
│ │ │ │ │ ├── controller/
│ │ │ │ │ ├── gui/
│ │ │ │ │ ├── model/
│ │ │ │ │ ├── service/
│ │ │ │ │ └── util/
│ │ └── resources/
│ └── test/
│ ├── java/
│ └── resources/
├── pom.xml
└── README.md
```
## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6.3 or higher
- IDE with Maven support (e.g., IntelliJ IDEA, Eclipse, NetBeans)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/restaurant-management-system.git
   cd restaurant-management-system
    ```
2. **Build the project using Maven:**
  ```bash
  mvn clean install
  ```
3. **Run the application:**
  ```bash
  mvn exec:java -Dexec.mainClass="com.restaurantmanagement.Main"
  ```
## Usage
### Login:

Start the application and log in with a user account.
The application distinguishes between staff and managers, with different access levels.
### Menu Management:

Use the menu management panel to add, edit, or delete items.
Save changes to persist them between sessions.
### Order Processing:

Process customer orders in the order processing panel.
Track the status of each order from waiting to ready.
### Table Management:

Manage the status of restaurant tables.
Assign tables to customers as needed.
### Inventory Management:

Monitor ingredient levels in real-time.
Receive alerts when inventory is low.
### Sales Reporting:

Generate and export daily sales reports for management review.
## Contributing
Contributions are welcome! If you find any bugs or want to add new features, feel free to open an issue or submit a pull request.

### Fork the project.
Create a new branch (git checkout -b feature/YourFeature).
Commit your changes (git commit -am 'Add YourFeature').
Push to the branch (git push origin feature/YourFeature).
Open a pull request.
## License
This project is licensed under the MIT License - see the LICENSE file for details.

### Initial Contributors:
- **Thomas Mulligan:** [Tmull1](https://github.com/Tmull1)
- **Chloe Evans:** [chloe514](https://github.com/chloe514)
- **Patrick Xiong:** [PatrickxStar](https://github.com/PatrickXStar)
