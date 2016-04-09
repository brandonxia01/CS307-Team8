# WILL AND DETERMINATION AND ELECTROLYTES

# date format is this 2003,6,25 (YYYY-MM-DD)
# INSERT datetime.date(2003,6,25)

import sqlite3
import datetime


class SqlHandler:
    getReqSqlCmd = ['',  # in the future this will get the various things used to populate the user profile page
                    "SELECT signupDate, numberOfAyush FROM userStat WHERE username = '",
                    "SELECT achievement FROM user WHERE username = '",
                    "SELECT followerName FROM follower WHERE username = '",
                    "SELECT gameMode, scoreValue FROM highScore WHERE username = '",
                    "SELECT username FROM follower WHERE followerName = '",
                    "SELECT b.username FROM follower a INNER JOIN follower b ON a.username = b.followerName AND \
a.followerName = b.username WHERE a.username = '",
                    "SELECT username, scoreValue FROM highScore WHERE highScore.gameMode = '"]

    postReqSqlCmd = ["INSERT INTO user VALUES ('",
                     "INSERT INTO highScore VALUES('",
                     "",
                     "INSERT INTO follower VALUES ('"]

    def __init__(self):
        self.conn = sqlite3.connect('Arza.db', detect_types=sqlite3.PARSE_DECLTYPES)

    def getTuple(self, reqType, argument):
        c = self.conn.cursor()
        sqlCall = self.getReqSqlCmd[int(reqType) - 1]
        sqlCall += argument
        sqlCall += "'"
        print(sqlCall)
        c.execute(sqlCall)
        response = c.fetchall()
        print(response)
        return response

    def performAction(self, reqType, argument, bodyContent):
        print(reqType, 'reqType', argument, 'argument', bodyContent, 'bodyContent')
        c = self.conn.cursor()
        sqlCall = self.postReqSqlCmd[int(reqType) - 1]
        if reqType == '1':
            sqlCall += bodyContent['username'][0]
            sqlCall += "', 0)"
        elif reqType == '2':
            sqlCall += bodyContent['username'][0]
            sqlCall += "', "
            sqlCall += bodyContent['gamemode'][0]
            sqlCall += ", "
            sqlCall += bodyContent['score'][0]
            sqlCall += ")"
        elif reqType == '4':
            sqlCall += bodyContent['followingName'][0]
            sqlCall += "', '"
            sqlCall += bodyContent['followerName'][0]
            sqlCall += "')"

        print(sqlCall)
        c.execute(sqlCall)
        self.conn.commit()
        return 1  # some error code or 0 for success


if __name__ == "__main__":
    conn = sqlite3.connect('Arza.db', detect_types=sqlite3.PARSE_DECLTYPES)

    c = conn.cursor()
    c.execute("CREATE TABLE user (username text primary key, achievement integer)")
    c.execute("CREATE TABLE userStat (username text primary key, signupDate date, numberOfAyush integer)")
    c.execute("CREATE TABLE follower (username text, followerName text)")
    c.execute("CREATE TABLE highScore (username text primary key, gameMode integer, scoreValue integer)")

    c.execute("INSERT INTO user VALUES ('ayushVijayjayjay', 64)")
    c.execute("INSERT INTO user VALUES ('ayashWibbleWobble', 0)")
    c.execute("INSERT INTO user VALUES ('ayishVijiwirgi', 15)")
    c.execute("INSERT INTO userStat VALUES ('ayushVijayjayjay', ?, 3)", (datetime.date(2016, 4, 6),))
    c.execute("INSERT INTO follower VALUES ('ayushVijayjayjay', 'ayashWibbleWobble')")
    c.execute("INSERT INTO follower VALUES ('ayushVijayjayjay', 'ayishVijiwirgi')")
    c.execute("INSERT INTO follower VALUES ('ayashWibbleWobble', 'ayishVijiwirgi')")
    c.execute("INSERT INTO follower VALUES ('ayishVijiwirgi', 'ayushVijayjayjay')")
    c.execute("INSERT INTO highScore VALUES ('ayushVijayjayjay', 1, 456789)")
    c.execute("INSERT INTO highScore VALUES ('ayashWibbleWobble', 1, 567890)")
    c.execute("INSERT INTO highScore VALUES ('ayishVijiwirgi', 1, 678901)")

    conn.commit()
    conn.close()

    conn = sqlite3.connect('Arza.db', detect_types=sqlite3.PARSE_DECLTYPES)
    c = conn.cursor()
    c.execute("SELECT * FROM user")
    print(c.fetchall())
    c.execute("SELECT * FROM userStat")
    print(c.fetchall())
    c.execute("SELECT * FROM follower")
    print(c.fetchall())
    c.execute("SELECT * FROM highScore")
    print(c.fetchall())

    conn.close()
