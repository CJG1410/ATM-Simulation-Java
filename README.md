# ATM Simulation using Java


## Overview

This Java-based ATM Simulation project with a Graphical User Interface (GUI) and MySQL integration aims to provide a realistic simulation of an Automated Teller Machine (ATM). It allows users to perform various banking operations, such as account balance inquiries, cash withdrawals, and deposits, while securely storing user data in a MySQL database.

## Features

- User-friendly GUI for intuitive interactions.
- User account creation and login system.
- Realistic ATM operations:
  - Account balance inquiries.
  - Cash withdrawals with denominations.
  - Cash deposits, including the ability to deposit multiple denominations.
- Transaction history to keep track of your account activity.
- Secure storage of user data in a MySQL database.
- Improved error handling and data validation.

## Prerequisites

Before you get started, ensure that you have the following software and resources installed:

- **Java Development Kit (JDK):** This project uses Java, so make sure you have the JDK installed on your system.

- **MySQL Server:** You will need a MySQL database server for storing user account data and transaction history.

- **Java Integrated Development Environment (IDE):** We recommend using a Java IDE such as IntelliJ IDEA or Eclipse to work with the project.

## Getting Started

Follow these steps to set up and run the ATM Simulation project:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/atm-simulation.git
   ```

2. **Import the Project:**

   Open the project in your chosen Java IDE.

3. **Database Setup:**

   - Create a MySQL database named `atm_simulation`.
   - Run the SQL script provided in the `database` directory to create the necessary tables.

4. **Configure Database Connection:**

   Open the `DatabaseConfig.java` file in the project and update the database URL, username, and password with your MySQL credentials.

5. **Build and Run:**

   Build the project and run it from your IDE.

6. **Use the ATM Simulation:**

   Interact with the provided GUI to simulate various ATM operations.

## Project Structure

The project structure is organized as follows:

- `src/`: Contains the Java source code.
- `database/`: Holds SQL scripts for initializing the database.
- `atmsim1.png` and `atmsim2.png`: Images showcasing the ATM Simulation GUI.

## Database Schema

The MySQL database consists of the following tables:

- `users`: Stores user information, including user ID, username, password, and account balance.
- `transactions`: Logs all user transactions, capturing details like transaction ID, user ID, transaction type, amount, and timestamp.

## Screenshots

### Main Menu

![ATM Simulation GUI](atmsim1.png)

### Transaction History

![ATM Simulation GUI](atmsim2.png)

## Contributing

We welcome contributions to enhance the project. If you'd like to contribute, please follow our [contributing guidelines](CONTRIBUTING.md).

## License

This project is licensed under the MIT License. For more details, please refer to the [LICENSE](LICENSE) file.


## Contact

For questions, feedback, or support, please contact us at chrisjosephgeorge@gmail.com

Enjoy your banking experience with the ATM Simulation project!

```

