UPDATE 4/25/2016
not using HTTP reqs, just a simple socket.
look at the ServerTester.java file for example.

send through socket: 1 digit type, 2 digit reqType, arg. (flush?)

type = 1
	get reqs: argument = username or gamemode number
		1. n/a
		2. user stat
		3. user achievement
		4. follower list
		5. highscore of user
		6. following list
		7. friend list
		8. highscore of game mode
type = 2
	post reqs
		1. new user - {'username':'ayush'}
		2. new highscore - {'username':'actualUserName', 'gamemode':'1', 'score':'123456789'}
		3. n/a - n/a
		4. new follower - {'followingName':'ayush', 'followerName':'albret'}