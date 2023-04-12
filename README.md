# Ask-Away

'Ask-Away' is an application where users can post any question and others can answer them. 


There is a bit of a twist, though: each user has a number of tokens attributed to his account. You can win tokens if you answer someone's question and they choose your answer as the best one.


When posting a question, you can choose if you want to reward the best answer with a chosen number of tokens from your account, or if you let the system reward the best answer with one token without giving away any of your own. Anyhow, the bigger the token reward on a question, the more people will try to give the best answer!


The app also has a badge system. Each season, users can get a badge(1st, 2nd or 3rd place) if they are in the top of users with the most tokens earned in that season. If there are multiple users with the same amount of tokens, they will all get a badge for their corresponding place.


Each season lasts 3 months and the tokens get reset at the start of each one.


Users also receive a number of tokens from the system when creating a new account.


How the application was made:


Ask-Away is a RESTful Web Application. I have used the Spring Boot framework and MySQL for the database.


There are three main entities: User, Post and Comment. Each one has a Model, a Controller, a Service and a Repository that handle operations like save, update, delete etc.
There are also entities for Season and Badge.


I have created an Event Scheduler that handles the Season reset every 3 months and also resets the users' number of tokens.


The application is a secure web-based platform built using Basic Authentication and Spring Security. It allows users to access various resources and functionalities based on their roles and permissions.


I implemented pagination for the GET methods for users, posts and comments.