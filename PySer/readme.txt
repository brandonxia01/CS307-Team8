the server is ArzaServer.py
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


type = 3
	multiplayer connection.
	all data send and received after the initial type = 3 should follow this format
		1. 4 digit data length
		2. data
		ex. "0039" "Connected to server, looking for match."

	A typical sequence of event:
	1.		client connect to server - "0039" "Connected to server, looking for match."
	2.		match found - "0012" "Match found!"
	3.		while game is going on - "????" "GAME DATA"
	4.		when a player dies
	 a.		if opponent dies, receive from server - "0004" "dead"
	 b.		if self dies, send to server - "0004" "dead"
	5.		decision point, both players need to send to server, one of the following
	 a.			"0001" 0" if user choose to not have a rematch
	 b.			"0001" 1" if user choose to have a rematch
	6.
	 a.		if both player choose to have a rematch server perform step 2
	 b.		server sends "0003" "end"

	refer to clientTester.py for example on how client side should send and receive data.
	probably use a input stream and output stream on separate threads
	
	this is what i discussed with brandon, but feel free to do whatever else
	"you keep calling ayush.getData and it returns either null or data"
	so maybe you have a thread pulling data, and a variable in the network class that holds it,
	after brandon calls ayush.getData you set the variable back to null