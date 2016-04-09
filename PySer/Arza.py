from http.server import HTTPServer
import time
from MyHTTPRequestHandler import MyHTTPRequestHandler

hostName = ""  # "localhost"
hostPort = 9000

myServer = HTTPServer((hostName, hostPort), MyHTTPRequestHandler)
print(time.asctime(), "Server Starts - %s:%s" % (hostName, hostPort))

try:
    myServer.serve_forever()
except KeyboardInterrupt:
    pass

myServer.server_close()
print(time.asctime(), "Server Stops - %s:%s" % (hostName, hostPort))