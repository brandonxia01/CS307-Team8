import ast
import queue
import socketserver
import sqlite3
import threading
from MySqlHandler import SqlHandler

gameWaitingQueue = queue.Queue()
gwqLock = threading.Lock()


class SqlHandler:
    # TODO check new highscore before inserting
    # TODO performAction reqType #3, update userstat's specific column
    # TODO somehow do multiplayer
    # TODO change how dates are stored to yyyymmdd
    # TODO change highscore table to unique(username, gamemode)
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
        self.conn = sqlite3.connect('Arza.db', detect_types=sqlite3.PARSE_DECLTYPES, check_same_thread=False)

    def getTuple(self, reqType, argument):
        c = self.conn.cursor()
        sqlCall = self.getReqSqlCmd[int(reqType) - 1]
        sqlCall += argument
        sqlCall += "'"
        print(sqlCall)
        c.execute(sqlCall)
        response = c.fetchall()
        print(response)
        return self.formatResponseForAyush(response)

    def performAction(self, reqType, bodyContent):
        print(reqType, 'reqType', bodyContent, 'bodyContent')
        c = self.conn.cursor()
        sqlCall = self.postReqSqlCmd[int(reqType) - 1]
        if reqType == 1:
            sqlCall += bodyContent['username']
            sqlCall += "', 0)"
        elif reqType == 2:
            sqlCall += bodyContent['username']
            sqlCall += "', "
            sqlCall += str(bodyContent['gamemode'])
            sqlCall += ", "
            sqlCall += str(bodyContent['score'])
            sqlCall += ")"
        elif reqType == 4:
            sqlCall += bodyContent['followingName']
            sqlCall += "', '"
            sqlCall += bodyContent['followerName']
            sqlCall += "')"

        print(sqlCall)
        c.execute(sqlCall)
        self.conn.commit()
        return 1  # some error code or 0 for success, or maybe null if it broke which is kinda an error code

    def formatResponseForAyush(self, response):
        formatted = ""
        for list in response:
            for strn in list:
                formatted += str(strn) + ','
            formatted = formatted[0:formatted.__len__()-1]
            formatted += ';'
        formatted = formatted[0:formatted.__len__() - 1]
        return formatted


class MyRequestHandler(socketserver.BaseRequestHandler):
    sql = SqlHandler()

    def handle(self):
        type = int(self.request.recv(1))
        if type == 1:
            reqType = int(self.request.recv(2))
            print(reqType)
            argument = str(self.request.recv(1024), 'ascii')
            print(argument)
            response = bytes(str(self.sql.getTuple(reqType, argument)), 'ascii')
            self.request.sendall(response)
            self.request.close()
        elif type == 2:
            reqType = int(self.request.recv(2))
            print(reqType)
            argument = ast.literal_eval(str(self.request.recv(1024), 'ascii'))
            print(argument)
            response = bytes(str(self.sql.performAction(reqType, argument)), 'ascii')
            self.request.sendall(response)
            self.request.close()
        elif type == 3:
            self.lookForMatch()

    def lookForMatch(self):
        # get Queue Lock and check if there is a match waiting
        # if so join and start game, else make new one
        response = bytes("0039", 'ascii')
        self.request.sendall(response)
        response = bytes("Connected to server, looking for match.", 'ascii')
        self.request.sendall(response)
        with gwqLock:
            if gameWaitingQueue.empty():
                newHandler = MultiplayerHandler(self)
                gameWaitingQueue.put_nowait(newHandler)
                joinNum = 0
            else:
                existingHandler = gameWaitingQueue.get_nowait()
                existingHandler.player2Join(self)
                joinNum = 1
        if joinNum == 0:
            while not newHandler.gameEnd:
                pass
        else:
            while not existingHandler.gameEnd:
                pass


class MultiplayerHandler():
    def __init__(self, player1):
        self.players = [player1]
        self.dataForPlayers = ["Match found!", "Match found!"]
        self.dataLocks = [threading.Lock(), threading.Lock()]
        self.dead = False
        self.rematchChoice = [True, True]
        self.done = 0
        self.gameEnd = False

    def player2Join(self, player2):
        self.players.append(player2)
        self.startGame()

    def initGame(self):
        self.dataForPlayers = [b"Match found!", b"Match found!"]
        self.dataLocks = [threading.Lock(), threading.Lock()]
        self.dead = False
        self.rematchChoice = [True, True]
        self.done = 0
        self.gameEnd = False

        self.dataLocks[0].acquire(False)
        self.dataLocks[1].acquire(False)
        self.sendData(0)
        self.sendData(1)

    def startGame(self):
        self.initGame()
        while True:
            for x in range(0,2):
                y = abs(x-1)
                if self.dataLocks[x].acquire(False):
                    print('datalock',x)
                    if self.dataForPlayers[x] == '':
                        # start thread to get data
                        print(threading.current_thread().ident)
                        threading.Thread(target=self.readData, args=(x, y)).start()
                    else:
                        # send data
                        if self.dataForPlayers[x] == 'dead':
                            self.dead = True
                        if self.dead and self.rematchChoice[x]:
                            self.rematchChoice[x] = False
                            if self.dataForPlayers[x] == '0':
                                self.done += 1
                            else:
                                self.done += 2
                        if not self.rematchChoice[0] and not self.rematchChoice[1]:
                            if self.done == 4:
                                self.initGame()
                            else:
                                self.dataForPlayers = ["end","end"]
                                self.dataLocks[0].acquire(False)
                                self.dataLocks[1].acquire(False)
                                self.sendData(0)
                                self.sendData(1)
                                self.gameEnd = True
                                return
                        self.sendData(x)
                        pass

    def readData(self, x, y):
        print('r',threading.current_thread().ident)
        lineLen = int(self.players[y].request.recv(4))
        self.dataForPlayers[x] = self.players[y].request.recv(lineLen)
        self.dataLocks[x].release()
        print('r',x, y, self.dataForPlayers[y],threading.current_thread().ident)

    def sendData(self, x):
        print('w',x, self.dataForPlayers[x])
        response = str(self.dataForPlayers[x].__len__())
        while not response.__len__() == 4:
            response = '0' + response
        response = bytes(response, 'ascii')
        print(x, response)
        self.players[x].request.sendall(response)
        response = self.dataForPlayers[x]
        print(x, response)
        self.players[x].request.sendall(response)
        self.dataForPlayers[x] = ''
        self.dataLocks[x].release()
        print('w done')


class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass


t_server = ThreadedTCPServer(('', 9000), MyRequestHandler)
test_ip, test_port = t_server.server_address
server_thread = threading.Thread(target=t_server.serve_forever)
server_thread.daemon = False
server_thread.start()
