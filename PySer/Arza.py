import socket
import threading
from MyHTTPRequestHandler import ThreadedTCPServer, MyRequestHandler


def client(ip, port, num, message):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect((ip, port))
        sock.sendall(bytes(int(12)))
        sock.sendall(bytes(message, 'ascii'))
        response = str(sock.recv(1024), 'ascii')
        print(response)

t_server = ThreadedTCPServer(('localhost', 9000), MyRequestHandler)
test_ip, test_port = t_server.server_address
server_thread = threading.Thread(target=t_server.serve_forever)
server_thread.daemon = False
server_thread.start()

# client(test_ip, test_port, 2, "first")
# client(test_ip, test_port, 2, "second")
# client(test_ip, test_port, 3, "third")
# client(test_ip, test_port, 4, "fourth")
# client(test_ip, test_port, 5, "fifth")
# client(test_ip, test_port, 6, "sixth")
# client(test_ip, test_port, 7, "seventh")
# client(test_ip, test_port, 8, "eighth")
# client(test_ip, test_port, 9, "ninth ")
# client(test_ip, test_port, 10, "tenth")

# t_server.shutdown()
# t_server.server_close()
