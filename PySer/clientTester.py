import socket
import threading

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
    sock.connect(("localhost", 9000))
    sock.sendall(bytes("3", 'ascii'))
    outputLen = int(sock.recv(4))
    output = str(sock.recv(outputLen))
    print(output)

    outputLen = int(sock.recv(4))
    output = str(sock.recv(outputLen))
    print(output)

    def read():
        print(threading.current_thread())
        while True:
            outputLen = int(sock.recv(4))
            output = str(sock.recv(outputLen))
            print(output)

    def write():
        print(threading.current_thread())
        while True:
            inputString = input()
            inputLen = "" + str(inputString.__len__())
            while not inputLen.__len__() == 4:
                inputLen = '0' + inputLen
            sock.sendall(bytes(inputLen, 'ascii'))
            sock.sendall(bytes(inputString, 'ascii'))


    print(threading.current_thread())
    threading.Thread(target=read, args=()).start()
    # _thread.start_new_thread(read(), ())
    print('2')
    threading.Thread(target=write, args=()).start()
    # _thread.start_new_thread(write(), ())
    print('3')
    while True:
        pass
