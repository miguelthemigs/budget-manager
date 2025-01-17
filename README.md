# Budget Manager Application  

## Overview  
Budget Manager is a web application designed to help users manage their finances efficiently. With a clean, responsive interface and powerful features, it provides users with tools to track expenses, analyze spending habits, and manage budgets. The application is built using **React** for the frontend and **Spring Boot** for the backend, with data persisted in a **PostgreSQL** database. 

Key features include role-based access control, insightful visualizations powered by **Recharts**, and seamless deployment using **Docker** and **GitHub Actions** for CI/CD.

---

## Features  

### User Authentication & Role-Based Access Control  
- **Register and Log In**: Secure authentication for all users.  
- **User Roles**:  
  - **User**: Access personal dashboards, manage expenses, and budgets.  
  - **Admin**: Access admin dashboard to view and manage user roles.  

### Expense Management  
- **Add Expenses**: Log your expenses with details and categories.  
- **Filter by Month**: View and analyze expenses for specific time periods.  
- **Category Budgets**: Set and edit budgets for different expense categories.  

### Insights and Visualizations  
- **Overview Page**:  
  - Analyze spending habits with date filtering options.  
  - Interactive charts and graphs powered by **Recharts**.  
- **Profile Page**:  
  - Detailed insights into spending habits.  
  - Graphs and analytics on spending by category and budgets.  

### User Settings  
- Update profile details.  
- Add or edit category budgets for more personalized financial tracking.  

---

## Technical Details  

### Frontend  
- **Framework**: React.js  
- **Charts Library**: Recharts  
- **State Management**: SessionStorage for token management.  

### Backend  
- **Framework**: Spring Boot (Java)  
- **Database**: PostgreSQL (Dockerized)  

### DevOps  
- **Hosting**: Backend and database hosted in Docker for ease of sharing and deployment.  
- **CI/CD**: GitHub Actions for continuous integration and deployment.  
- **Code Quality**: SonarQube for static code analysis and test coverage.  

---

## Installation and Setup  

### Prerequisites  
- Docker installed on your machine.
- npm installed on your machine
- Java JDK 17
Back-End repository for Budget Manager Project
- You can find the Front-End here: https://github.com/miguelthemigs/budget-manager-front

### Steps  
1. **Clone the Repository**:  
    ```bash  
    git clone https://github.com/miguelthemigs/budget-manager  
    cd budget-manager  
    ```  

2. **Backend Setup**:  
    - Navigate to the backend directory and build the project:  
        ```bash  
        ./gradlew clean install  
        ```  
    - Start the backend using Docker:  
        ```bash  
        docker-compose up --build -d
        ```  

3. **Frontend Setup**:  
    - Navigate to the frontend directory:  
        ```bash  
        cd frontend  
        ```  
    - Install dependencies:  
        ```bash  
        npm install  
        ```  
    - Start the application:  
        ```bash  
        npm run dev 
        ```  
