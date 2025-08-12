# 🍽️ Swiggy – Online Food Ordering System

> A full-stack Java-based web application simulating a real-world food ordering system, built using **Java Servlets**, **JDBC**, **MySQL**, and modern **HTML/CSS/JavaScript**.  
> Designed to demonstrate backend integration, database design, and frontend-to-backend connectivity in a production-like environment.

## 📌 Highlights

✅ Fully functional **user registration** and **login system**  
✅ Dynamic **menu display** from a MySQL database  
✅ Seamless **order placement** flow using Java Servlets and JDBC  
✅ Frontend powered by **HTML/CSS/JS** with **Fetch API** integration  
✅ Secure database interactions with **prepared statements**  
✅ Structured and modular **codebase** ready for extension  

---

## 💻 Tech Stack

| Layer       | Technology           |
|-------------|----------------------|
| Backend     | Java Servlets (JEE), JDBC |
| Frontend    | HTML, CSS, JavaScript |
| Database    | MySQL                |
| Server      | Apache Tomcat        |
| IDE         | Eclipse / IntelliJ   |
| Tools       | MySQL Workbench, Postman (for API testing) |

---

## 📂 Features Breakdown

| Feature               | Description                                                                 |
|------------------------|-----------------------------------------------------------------------------|
| 👤 User Auth           | Register & log in with credentials stored in MySQL                         |
| 📋 Menu Management     | View all available food items from the database                             |
| 🛒 Place Orders        | Submit orders by selecting item and quantity                                |
| ⏱️ Order Timestamping  | Automatic tracking of order time using SQL `TIMESTAMP`                      |
| 🔐 Security Practices  | Use of constraints & prepared statements to avoid SQL injection             |

---

## 🧱 Database Schema

**Database Name:** `swiggy`

```sql
CREATE DATABASE IF NOT EXISTS swiggy;
USE swiggy;

-- Drop existing tables (optional)
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS users;

-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Menu Table
CREATE TABLE menu (
    id INT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    description TEXT,
    price INT NOT NULL
);

-- Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    quantity INT,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES menu(id)
);
