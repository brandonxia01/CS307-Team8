import socketserver
import ast
from MySqlHandler import SqlHandler


class MyRequestHandler(socketserver.BaseRequestHandler):
    sql = SqlHandler()

    def handle(self):
        type = int(self.request.recv(1))
        reqType = int(self.request.recv(2))
        print(reqType)
        if type == 1:
            argument = str(self.request.recv(1024), 'ascii')
            print(argument)
            response = bytes(str(self.sql.getTuple(reqType, argument)), 'ascii')
        else:
            argument = ast.literal_eval(str(self.request.recv(1024), 'ascii'))
            print(argument)
            response = bytes(str(self.sql.performAction(reqType, argument)), 'ascii')
        self.request.sendall(response)
        self.request.close()


class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass
