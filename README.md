# 🚀 CP Compass

**Live Demo:** https://cp-compass.vercel.app/

> A full-stack Competitive Programming Analytics Platform built for Codeforces users.

CP Compass synchronizes a user's Codeforces profile and transforms thousands of submissions into meaningful analytics. It helps competitive programmers identify their strengths, weaknesses, growth areas, and provides personalized problem recommendations for focused practice.

---

## ✨ Features

### 📊 Dashboard
- View your current and maximum Codeforces rating.
- Track total contests, submissions, and problems solved.
- Identify your strongest and weakest topics.
- Discover your current Growth Zone.
- Get a personalized daily problem recommendation.
- Monitor your recent solving activity.

---

### 🎯 Recommendations
- **Smart Recommendation** automatically suggests an unsolved Codeforces problem based on:
  - Current Rating
  - Growth Zone
  - Weak Topics
  - Previously Solved Problems
- **Custom Recommendation** allows users to generate recommendations based on their preferred criteria.

---

### 🏆 Contest History
- View complete rated contest history.
- Contest rank and rating changes.
- Previous and new ratings.
- Problems solved during each contest.
- Direct links to Codeforces contests.

---

### 📚 Topic Insights
- Topic-wise analytics based on Codeforces tags.
- Acceptance rate for every topic.
- Attempts and accepted submissions.
- Automatically identifies strong and weak topics.
- Visual progress indicators for every topic.

---

### 📈 Progress Review
Analyze your performance over:
- Last 7 Days
- Last 30 Days
- Last 90 Days
- Custom Date Range

Track:
- Problems Solved
- Contests Participated
- Rating Change
- Highest Rated Problem Solved
- Average Problem Rating Solved
- Problems Solved by Rating
- Daily Solving Activity

---

## 🔄 Smart Synchronization

- One-click synchronization with the Codeforces API.
- First sync imports the user's complete submission history.
- Subsequent syncs fetch only newly created submissions, making synchronization significantly faster.
- Automatically updates all analytics after every sync.

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- Hibernate
- Maven

### Frontend
- React
- Vite
- Axios
- CSS

### Database & Cache
- PostgreSQL
- Redis (Upstash)

### Deployment
- Docker
- AWS EC2
- Nginx
- Let's Encrypt SSL
- Vercel

---

## 📸 Screenshots

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Recommendations
![Recommendations](screenshots/recommendations.png)

### Contest History
![Contest History](screenshots/contest-history.png)

### Topic Insights
![Topic Insights](screenshots/topic-insights.png)

### Progress Review
![Progress Review](screenshots/progress-review.png)

---

## 🎯 Why CP Compass?

Codeforces provides a huge amount of data, but it can be difficult to extract meaningful insights from it.

CP Compass helps answer questions such as:

- Which topics am I strongest in?
- Which topics need improvement?
- What rating range should I practice next?
- What problem should I solve now?
- How has my performance changed over time?

Instead of manually analyzing thousands of submissions, CP Compass generates these insights automatically after synchronization.

---

## 🌐 Live Website

https://cp-compass.vercel.app/

---

## 👨‍💻 Author

**Shivam Pandey**

If you have any suggestions or feedback, feel free to open an issue or reach out!
