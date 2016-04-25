UPDATE 4/25/2016
not using HTTP reqs, just a simple socket.
look at the ServerTester.java file for example.


get reqs

1. n/a
2. user stat
3. user achievement
4. follower list
5. highscore of user
6. following list
7. friend list
8. highscore of game mode

header
reqType = 1~8
argument = username or gamemode number

post reqs

1. new user
2. new highscore
3. n/a
4. new follower

header
reqType = 1~4

body
1. {'username':'ayush'}
2. {'username':'actualUserName', 'gamemode':'1', 'score':'123456789'}
3. n/a
4. {'followingName':'ayush', 'followerName':'albret'}
