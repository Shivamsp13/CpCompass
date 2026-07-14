# 🚀 CP Compass

Live Link: https://cp-compass.vercel.app/

> A Competitive Programming Analytics Platform for Codeforces users.

CP Compass is a full-stack web application that helps competitive programmers analyze their Codeforces performance through detailed topic analytics, rating insights, contest history, and personalized problem recommendations.

---

## ✨ Features

### 🔐 Authentication

- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Secure Protected APIs

---

### 👤 User Profile

- Connect Codeforces Handle
- Current Rating
- Maximum Rating
- Last Sync Timestamp
- Manual Sync with Cooldown Protection

---

### 📊 Dashboard

Displays an overview of the user's Codeforces profile including:

- Current Rating
- Maximum Rating
- Total Contests
- Total Submissions
- Total Problems Solved
- Problems Solved (Last 30 Days)
- Strong Topics
- Weak Topics
- Strong Rating Bands
- Growth Zone

---

### 📈 Topic Analytics

Analyze performance across Codeforces topics.

For every topic:

- Attempts
- Accepted Solutions
- Acceptance Rate

Examples:

- Graphs
- DP
- Binary Search
- Greedy
- Math
- Implementation
- etc.

---

### 📉 Rating Insights

Performance grouped by difficulty.

Examples:

- 800–1000
- 1000–1200
- 1200–1400
- 1400–1600
- 1600–1800
- 1800+

Each band displays:

- Attempts
- Solved
- Acceptance Rate

---

### 🏆 Contest History

Complete Codeforces contest history including:

- Contest Name
- Rank
- Rating Change
- New Rating

---

### 🎯 Problem Recommendations

Recommends unsolved problems based on:

- Weak Topics
- Growth Zone
- Current Rating

Recommendation updates only after syncing.

---

### 🔄 Codeforces Synchronization

Synchronizes user data directly from the Codeforces API.

Includes:

- Contest History
- Complete Submission History
- Topic Analytics
- Rating Analytics

Optimized using incremental synchronization.

- First Sync → Complete submission history
- Future Syncs → Only newly added submissions

---

### ⚡ Redis Caching

Frequently accessed analytics are cached using Redis (Upstash).

Benefits:

- FDaily Recommendation Caching: Stores each user's daily recommended Codeforces problem to ensure consistency throughout the day and reduce    recomputation.
- Analytics Caching: Caches computed dashboard and analytics data (dashboard, topic insights, rating insights, etc.) to improve response time   and reduce database load.

---

## 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Redis
- Maven
- JWT Authentication

---

### Frontend

- React 19
- Vite
- React Router
- Axios
- Material UI (MUI)
- CSS

---

### Deployment

Backend

- AWS EC2
- Docker
- Nginx Reverse Proxy
- DuckDNS
- Let's Encrypt SSL

Frontend

- Vercel

Database

- PostgreSQL (Hosted on EC2)

Caching

- Upstash Redis

---

## 🏗 Project Architecture

```
React Frontend
       │
       ▼
Spring Boot REST APIs
       │
 ┌─────┴─────────┐
 │               │
 ▼               ▼
PostgreSQL    Redis Cache
       │
       ▼
Codeforces API
```

---

## 📂 Project Structure

```
cp-compass-backend
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── client
├── config
└── exception

cp-compass-frontend
│
├── components
├── pages
├── services
├── hooks
├── assets
└── styles
```

---

## ⚙️ Synchronization Flow

```
User clicks Sync
        │
        ▼
Fetch Contest History
        │
        ▼
Fetch Submission History
        │
        ▼
Store New Submissions
        │
        ▼
Compute Topic Analytics
        │
        ▼
Compute Rating Analytics
        │
        ▼
Cache Results
        │
        ▼
Return Updated Dashboard
```

---

## 🔒 Security

- JWT Authentication
- BCrypt Password Encoding
- Stateless Authentication
- Protected Endpoints
- CORS Configuration

---

## 🚀 Deployment

Backend is containerized using Docker and deployed on AWS EC2.

Deployment includes:

- Docker
- Nginx
- HTTPS (Let's Encrypt)
- DuckDNS
- PostgreSQL
- Redis

---

## 📸 Screenshots

<img width="1440" height="808" alt="image" src="https://github.com/user-attachments/assets/7eeb10bd-3438-4086-ba6e-4cd9e603b542" />

<img width="1440" height="805" alt="image" src="https://github.com/user-attachments/assets/04e999b5-94b8-40a4-830a-12c0e1025d9f" />



---

## 👨‍💻 Author

**Shivam Pandey**

GitHub: https://github.com/Shivamsp13

LinkedIn: https://linkedin.com/in/shivam-pandey-472782203/
