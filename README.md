# 🍽 Online Food Ordering System

A full-stack web project built using Java Servlets, JDBC, MySQL, and HTML/JavaScript.  
It allows users to register, login, view a menu from the database, and place orders.

---

## 💻 Tech Stack

- Java (Servlets + JDBC)
- HTML + CSS + JavaScript (Frontend)
- MySQL (Database)
- Apache Tomcat (Server)

---

## ✨ Features

✅ User Registration & Login  
✅ View Food Menu from MySQL  
✅ Place Orders (User to Item)  
✅ Frontend connected to backend via Fetch API

---

## 🛠 How to Run Locally

1. 🧱 Create MySQL Database: `food_ordering`  
2. 📋 Create tables:

```sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100)
);

CREATE TABLE menu (
  id INT AUTO_INCREMENT PRIMARY KEY,
  item_name VARCHAR(100),
  description TEXT,
  price DECIMAL(10,2)
);

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  item_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (item_id) REFERENCES menu(id)
);
