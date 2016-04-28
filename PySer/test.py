import socket
import threading
import time
import _thread

def client(ip, port, num, message):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect((ip, port))
        sock.sendall(bytes(int(12)))
        sock.sendall(bytes(message, 'ascii'))
        response = str(sock.recv(1024), 'ascii')
        print(response)

# client("ec2-52-34-71-58.us-west-2.compute.amazonaws.com", 9000, 1, "first")


class MultiplayerHandler():
    def __init__(self, player1):
        self.players = [player1, 2]
        self.dataForPlayers = ["", ""]
        self.dataLocks = [threading.Lock(), threading.Lock()]

    def startGame(self):
        for x in range(0, 10):
            for y in range(0, 2):
                if self.dataLocks[y].acquire(False):
                    if self.dataForPlayers[y] == '':
                        print(threading.current_thread().ident)
                        threading.Thread(target=self.readOnePacket, args=(self.players[y], y)).start()
                    else:
                        print('\t\t\t',y,self.dataForPlayers[y])
                        self.dataForPlayers[y] = ''
                        self.dataLocks[y].release()

    def readOnePacket(self, player, num):
        print('start',num,threading.current_thread().ident)
        # with self.dataLocks[num]:
        self.dataForPlayers[num] = "asdf"
        print('end',num,threading.current_thread().ident)
        self.dataLocks[num].release()

testing = MultiplayerHandler(1)
testing.startGame()
# threading.Thread(target=testing.startGame()).start()