simple socket. connnection - look at the ServerTester.java file for example.


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

receive format:
	type = 1
		comma separated columns and semicolon separated rows
		ex a table like this
				columnA		columnB		columnC
		row1	asdf		zxcv		12356
		row2	qwer		fghj		8520
		will respond like this as a string
		asdf,zxcv,12356;qwer,fghj,8520

	type = 2
		1 if success else probably failed 



the database looks like this, note date should be put in as string with this format 'yyyymmdd'

TABLE user (username text primary key, achievement integer)
TABLE userStat (username text primary key, signupDate text, numberOfAyush integer)
TABLE follower (username text, followerName text, PRIMARY KEY (username, followerName))
TABLE highScore (username text primary key, gameMode integer, scoreValue integer)